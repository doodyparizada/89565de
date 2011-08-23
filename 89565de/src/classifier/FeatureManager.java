package classifier;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import lex.Sentence;
// singleton
public class FeatureManager {

	List<Double> getFeatureVector(Sentence hypo) {
		List<Double> scores = new LinkedList<Double>();	
		for (Feature feat : features) {
			scores.add(new Double(feat.score(hypo)));
		}
		return scores;
	}
	
	List<Feature> features;
	
	
	private FeatureManager() {
		features = Arrays.asList(new Feature[] {
				new NounFeature(),
				// add more features....
		});
		}
	
	private static FeatureManager instance;
	public static FeatureManager getInstance() {
		if (instance == null){
			instance = new FeatureManager();
		}
		return instance;
	}
	}
