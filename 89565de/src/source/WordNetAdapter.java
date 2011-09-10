package source;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import classifier.FeatureManager;
import classifier.WordNetAdjectiveFeature;
import classifier.WordNetAdverbFeature;
import classifier.WordNetNounFeature;
import classifier.WordNetVerbFeature;

import pos.Pos;

import net.sf.extjwnl.JWNLException;
import net.sf.extjwnl.data.IndexWord;
import net.sf.extjwnl.data.POS;
import net.sf.extjwnl.data.PointerUtils;
import net.sf.extjwnl.data.Synset;
import net.sf.extjwnl.data.Word;
import net.sf.extjwnl.data.list.PointerTargetNode;
import net.sf.extjwnl.data.list.PointerTargetNodeList;
import net.sf.extjwnl.dictionary.Dictionary;

import lex.Entailment;
import lex.Term;
import lex.TermFactory;

public class WordNetAdapter implements Source {

	/**
	 * available only via SourceFactory
	 */
	private WordNetAdapter() {}

	public static final double DEGRADE = 0.8;
	private Dictionary dict;

	public static String NAME = "wordnet";

	@Override
	public String getName() {
		return NAME;
	}

	public void init() throws Exception {
		try {
			InputStream is = new FileInputStream("wordnet.config.xml");
			dict = Dictionary.getInstance(is);
		} catch (JWNLException e) {
			e.printStackTrace();
			throw (e);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw (e);
		}
	}

	@Override
	public List<Entailment> getEntailments(Term t) throws SourceException {
		try {
			Set<Entailment> entailments = new HashSet<Entailment>();
			// use JWNL API to return entailmetns.
			POS translated_pos = translatePOS(t.getPos());
			if (translated_pos == null){
				return new LinkedList<Entailment>(entailments);
			}
			IndexWord word = dict.lookupIndexWord(translated_pos, t.getTerm());

			if (word == null || word.getSenses().size() == 0) {
				return new LinkedList<Entailment>(entailments);
			}
			for (Synset s : word.getSenses()) {
				entailments.addAll((getSense(s, t, 1)));
			}
			return new LinkedList<Entailment>(entailments);
		} catch (JWNLException e) {
			System.out.println("caught Wordnet exception: "+ e);
			System.out.println("continuing...");
			return new LinkedList<Entailment>();
		} catch (NullPointerException e) {
			System.out.println("caught null pointer exception: "+ e);
			System.out.println("continuing...");
			return new LinkedList<Entailment>();
		} catch (Exception e) { // What can we do ...
			System.out.println("caught exception: "+ e);
			System.out.println("continuing...");
			return new LinkedList<Entailment>();
		}
	}

	/**
	 * Get all of the hypernyms entailments of from the given synset initialized with the given score
	 * @param s synset
	 * @param t the entailing term
	 * @param score the entailment score for the first level node in the hypernym tree
	 * @return a list of entailments
	 * @throws JWNLException
	 */
    private Set<Entailment> getSense(Synset s, Term t, double score) throws JWNLException {
    	Set<Entailment> entailments = new HashSet<Entailment>();
    	if (score < 0.5) {
    		return entailments;
    	}
    	for (Word hypernym : s.getWords()) {
        	// create an Entailment object
    		Term newTerm = new Term(hypernym.getLemma().replace("_", "-"),
					translatePOS(hypernym.getPOS()));
    		if (newTerm.equals(t)) {
    			// let naive source deal with these
		} else {
    			Entailment ent = new Entailment(
        			t,
        			newTerm,
        			score,
        			getName());
        		// add Enailment to the return list
        		entailments.add(ent);
    		}
        }

    	// get parent entailments
    	PointerTargetNodeList hypernyms = PointerUtils.getDirectHypernyms(s);
    	for(int i = 0; i < hypernyms.size(); i++){
             Synset parentSyn = ((PointerTargetNode)hypernyms.get(i)).getSynset();
             entailments.addAll(getSense(parentSyn,t, DEGRADE*score));
    	}

        return entailments;
    }


    private Pos translatePOS(POS wnpos) {
    	if (wnpos == POS.NOUN) {
    		return Pos.NOUN;
    	} else 	if (wnpos == POS.ADJECTIVE) {
    		return Pos.ADJECTIVE;
    	} else 	if (wnpos == POS.VERB) {
    		return Pos.VERB;
    	} else 	if (wnpos == POS.ADVERB) {
    		return Pos.ADVERB;
    	}
    	return null;
    }
    private POS translatePOS(Pos pos) {
    	if (pos == Pos.NOUN) {
    		return POS.NOUN;
    	} else 	if (pos == Pos.ADJECTIVE) {
    		return POS.ADJECTIVE;
    	} else 	if (pos == Pos.VERB) {
    		return POS.VERB;
    	} else 	if (pos == Pos.ADVERB) {
    		return POS.ADVERB;
    	}
    	return null;
    }

	public static void register() throws Exception {
		System.out.println("Registering WordNet Source");
		WordNetAdapter wn = new WordNetAdapter();
		wn.init();
		// register itself to the source factory
		SourceFactory.getInstance().register(wn);
		// register direct specific features
		FeatureManager.getInstance().addFeature(new WordNetNounFeature())
		.addFeature(new WordNetAdjectiveFeature())
		.addFeature(new WordNetVerbFeature())
		.addFeature(new WordNetAdverbFeature());
	}
}
