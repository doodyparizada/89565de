package classifier;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import jnisvmlight.FeatureVector;
import jnisvmlight.LabeledFeatureVector;
import jnisvmlight.SVMLightInterface;
import jnisvmlight.SVMLightModel;
import jnisvmlight.TrainingParameters;

public class Svm implements Classifier {
	SVMLightInterface trainer = new SVMLightInterface();
	ArrayList<LabeledFeatureVector> traindata = new ArrayList<LabeledFeatureVector>();
	SVMLightModel model;
	public Svm() {
		SVMLightInterface.SORT_INPUT_VECTORS = true;
	}
	@Override
	public void addLearningExample(List<Double> features, boolean doesEntail) {
		LabeledFeatureVector featureVector = toFeatureVector(features,doesEntail);
		traindata.add(featureVector);
	}

	@Override
	public void createModel() {
		// Initialize a new TrainingParamteres object with the default SVM-light
	    // values
	    TrainingParameters tp = new TrainingParameters();

	    // Switch on some debugging output
	    tp.getLearningParameters().verbosity = 1;

	    System.out.println("\nTRAINING SVM-light MODEL ..");
	    LabeledFeatureVector[] a=new LabeledFeatureVector[traindata.size()];
		model = trainer.trainModel(traindata.toArray(a), tp);
	    System.out.println(" DONE.");

	    // Use this to store a model to a file or read a model from a URL.
	    model.writeModelToFile("jni_model.dat");
	    try {
			model = SVMLightModel.readSVMLightModelFromURL(new java.io.File("jni_model.dat").toURI().toURL());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public boolean doesEntail(List<Double> features) {
		FeatureVector featureVector = toFeatureVector(features,null);
		double d = model.classify(featureVector);
		return d < 0.0? false:true;
	}
	private LabeledFeatureVector toFeatureVector(List<Double> features,Boolean lable) {
		// TODO Auto-generated method stub
		return null;
	}

}
