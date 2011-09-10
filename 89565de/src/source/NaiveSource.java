package source;

import java.util.LinkedList;
import java.util.List;

import classifier.FeatureManager;
import classifier.NaiveAdjectiveFeature;
import classifier.NaiveAdverbFeature;
import classifier.NaiveCFeature;
import classifier.NaiveCarFeature;
import classifier.NaiveDFeature;
import classifier.NaiveNouneFeature;
import classifier.NaiveOFeature;
import classifier.NaivePFeature;
import classifier.NaiveVerbFeature;

import lex.Entailment;
import lex.Term;

public class NaiveSource implements Source {

	private NaiveSource() {}
	
	@Override
	public List<Entailment> getEntailments(Term t) throws SourceException {
		List<Entailment> l = new LinkedList<Entailment>();
		l.add(new Entailment(t, t, 1 , getName()));
		return l;
	}

	public static String NAME = "naive";

	@Override
	public String getName() {
		return NAME;
	}
	public static void register() {
		System.out.println("Registering Naive Source");
		// register itself to the source factory
		SourceFactory.getInstance().register(new NaiveSource());
		// register direct specific features
		FeatureManager.getInstance().addFeature(new NaiveNouneFeature())
		.addFeature(new NaiveVerbFeature())
				.addFeature(new NaiveAdjectiveFeature())
				.addFeature(new NaiveAdverbFeature())
				.addFeature(new NaiveCarFeature())
				.addFeature(new NaiveOFeature())
				.addFeature(new NaivePFeature())
				.addFeature(new NaiveDFeature())
				.addFeature(new NaiveCFeature());
	}

}
