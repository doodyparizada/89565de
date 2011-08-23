package classifier;

import lex.Sentence;

public interface Feature {
	/**
	 * 
	 * @return the score of the feature.
	 */
	double score(Sentence hypo);
}
