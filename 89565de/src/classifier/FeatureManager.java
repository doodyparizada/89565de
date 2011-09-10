package classifier;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import lex.Sentence;
// singleton
public class FeatureManager {
	/**
	 * 
	 * @param hypo a sentence with EntailedTerms to extract features from.
	 * @return a vector of scores. each score is from a different feature.
	 */
	public List<Double> getFeatureVector(Sentence hypo) {
		List<Double> scores = new LinkedList<Double>();
		for (Feature feat : features) {
			scores.add(new Double(feat.score(hypo)));
		}
		return scores;
	}
	/**
	 * 
	 * @param f the feature to add to the manager
	 */
	public FeatureManager addFeature(Feature f) {
		features.add(f);
		return instance;
	}
	
	List<Feature> features;


	/**
	 * Private Ctor.
	 * Adds some general features to the feature map.
	 */
	private FeatureManager() {
		features = new LinkedList<Feature>(Arrays.asList(new Feature[] {
				new TotalFeature(),
				new TotalScorelessFeature()
				// add more features....
		}));
		}
	/**
	 * the singleton instance
	 */
	private static FeatureManager instance;
	public static FeatureManager getInstance() {
		if (instance == null){
			instance = new FeatureManager();
		}
		return instance;
	}
	}
