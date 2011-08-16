package classifier;

import java.util.List;
/**
 * Classifier such as SVM
 * @author DJ
 *
 */
public interface Classifier {
	/**
	 * 
	 * @param features the features of a candidate entailment
	 * @param doesEntail do these features match an entailments
	 */
	void learn(List<Feature> features, boolean doesEntail);
	/**
	 * 
	 * @param features the feature list of a candidate entailment
	 * @return true if the feature list belongs to an entailment, otherwise returns false 
	 */
	boolean doesEntail(List<Feature> features);
}
