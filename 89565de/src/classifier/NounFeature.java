package classifier;

import java.util.List;

import lex.Entailment;
import lex.Sentence;

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
		// do some voodoo

		return 0;
	}
}
