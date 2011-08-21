package source;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import pos.Pos;

import net.didion.jwnl.JWNL;
import net.didion.jwnl.JWNLException;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.data.PointerUtils;
import net.didion.jwnl.data.Synset;
import net.didion.jwnl.data.Word;
import net.didion.jwnl.data.list.PointerTargetNode;
import net.didion.jwnl.data.list.PointerTargetNodeList;
import net.didion.jwnl.dictionary.Dictionary;

import lex.Entailment;
import lex.Term;
import lex.TermFactory;

public class WordNetAdapter implements Source {

	public final double DEGRATE = 0.8;
	
	@Override
	public String getName() {
		return "wordnet";
	}

	public void init() {
		try {
		InputStream is = new FileInputStream("wordnet.config.xml");
			JWNL.initialize(is);
		} catch (JWNLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public List<Entailment> getEntailments(Term t) throws SourceException {
		try {
			// use JWNL API to return entailmetns.
			Dictionary dict = Dictionary.getInstance();
			IndexWord word = dict.lookupIndexWord(POS.NOUN, "dog");
			//System.out.println(word.getSenses()[0]);
			//	PointerUtils pu = PointerUtils.getInstance();
			//PointerTargetTree ptree = pu.getHypernymTree(word.getSense(2));

			List<Entailment> entailments = new LinkedList<Entailment>();
			for (Synset s : word.getSenses()) {
				entailments.addAll((getSense(s, t, 1)));
			}
			return entailments;
		} catch (JWNLException e) {
			throw new SourceException("WordNet Crashed: " + e.getMessage());
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
    private List<Entailment> getSense(Synset s, Term t, double score) throws JWNLException {
    	// get direct hypernyms (on level up)
        PointerTargetNodeList hypernyms = PointerUtils.getInstance().getDirectHypernyms(s);
        List<Entailment> entailments = new LinkedList<Entailment>();
        
        for(int i = 0; i < hypernyms.size(); i++){
            Synset parentSyn = ((PointerTargetNode)hypernyms.get(i)).getSynset();
            for(int j = 0; j < parentSyn.getWordsSize(); j++){
            	// the entailed word
            	Word hypernym = parentSyn.getWord(j);         	
            	// create an Entailment object
            	Entailment ent = new Entailment(t, TermFactory.instance.get(hypernym.getLemma(),
            			translatePOS(hypernym.getPOS())),new BigDecimal(score), this);
            	// add Enailment to the return list
            	entailments.add(ent);
            	// get parent entailments
            	entailments.addAll(getSense(parentSyn,t, DEGRATE*score));
            }
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
}
