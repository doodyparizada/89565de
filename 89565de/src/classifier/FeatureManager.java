package classifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.mapping.Array;

import pos.Pos;


import lex.Sentence;
// singleton
public class FeatureManager {

	public List<Double> getFeatureVector(Sentence hypo) {
		List<Double> scores = new LinkedList<Double>();
		for (Feature feat : features) {
			scores.add(new Double(feat.score(hypo)));
		}
		return scores;
	}

	List<Feature> features;


	private FeatureManager() {
		features = Arrays.asList(new Feature[] {
				new NaiveNouneFeature(),
				new NaiveVerbFeature(),
				new NaiveAdjectiveFeature(),
				new NaiveAdverbFeature(),
				new NaiveCarFeature(),
				new NaiveOFeature(),
				new NaivePFeature(),
				new NaiveDFeature(),
				new NaiveCFeature(),
				new WordNetNounFeature(),
				new WordNetAdjectiveFeature(),
				new WordNetVerbFeature(),
				new WordNetAdverbFeature(),
				new DirectNounFeature(),
				new DirectVerbFeature(),
				new TotalFeature(),
				new TotalScorelessFeature()

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
