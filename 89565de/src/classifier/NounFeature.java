package classifier;

import java.util.List;

import lex.Entailment;

/**
 * Counts how well did the nouns fit
 * @author DJ
 * 
 */
public class NounFeature implements Feature{
	public NounFeature(List<Entailment> entailments) {
		// do some voodoo
	}

	@Override
	public double score() {
		return 0;
	}
}
