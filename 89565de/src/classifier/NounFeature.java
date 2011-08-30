package classifier;

import java.util.List;

import pos.Pos;

import lex.EntailedTerm;
import lex.Entailment;
import lex.Sentence;
import lex.Word;

/**
 * Counts how well did the nouns fit
 * @author DJ
 * 
 */
public class NounFeature implements Feature{
	public NounFeature() {
			}

	@Override
	public double score(Sentence hypo) {
		int nouns = 0;
		int entailed = 0;
		for (Word word : hypo.getWords()) {
			EntailedTerm term = (EntailedTerm)word.getTerm();
			if (term.getPos().equals(Pos.NOUN)) {
				nouns += 1;
				if (term.getEntailments().size() > 0) {
					entailed += 1;
				}
			}
		}
		return ((double)entailed)/nouns;
	}
}
