package classifier;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import source.SourceFactory;

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
	
	public String generateModelName() {
		return "model_" + SourceFactory.getInstance().toString().replace(' ', '_') + ".dat";
	}
	
	@Override
	public void addLearningExample(List<Double> features, boolean doesEntail) {
		LabeledFeatureVector featureVector = toFeatureVector(features,doesEntail);
		if(featureVector.size()>0){
			traindata.add(featureVector);
		}
	}

	@Override
	public void createModel() {
		// Initialize a new TrainingParamteres object with the default SVM-light
	    // values
	    TrainingParameters tp = new TrainingParameters();

	    // Switch on some debugging output
	    tp.getLearningParameters().verbosity = 1;

	    System.out.println("\nTRAINING SVM-light MODEL ..");
	    LabeledFeatureVector[] a = traindata.toArray(new LabeledFeatureVector[0]);
	 //   for (int i = 0; i < a.length; i++) {
		//	System.err.println(a[i].getDimAt(0) + " " + a[i].getValueAt(0));
	//	}
		model = trainer.trainModel(a, tp);
	    System.out.println(" DONE.");

	    // Use this to store a model to a file or read a model from a URL.
	    model.writeModelToFile(generateModelName());
	    try {
			model = SVMLightModel.readSVMLightModelFromURL(new java.io.File("model.dat").toURI().toURL());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@Override
	public void readModelFromFile() {
	    try {
			model = SVMLightModel.readSVMLightModelFromURL(new java.io.File(generateModelName()).toURI().toURL());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}
	@Override
	public boolean doesEntail(List<Double> features) {
		FeatureVector featureVector = toFeatureVector(features);
		double d = model.classify(featureVector);
		return d < 0.0? false:true;
	}
	private FeatureVector toFeatureVector(List<Double> features) {
		int nVals=0;
		for(int i=0;i<features.size();i++){
			Double val = features.get(i);
			if(val>0){
				nVals++;
			}
		}
		//the !=0 indexes
		int[] dims = new int[nVals];
		//the corresponding vals
		double[] vals = new double[nVals];
		for(int i=0,k=0;i<features.size();i++){
			Double val = features.get(i);
			if(val>0){
				dims[k]=i+1;
				vals[k]=val;
				k++;
			}
		}
		FeatureVector fv =  new FeatureVector( dims, vals);
		//fv.normalizeL2();
		return fv;
	}
	private LabeledFeatureVector toFeatureVector(List<Double> features,Boolean blable) {
		int nVals=0;
		for(int i=0;i<features.size();i++){
			Double val = features.get(i);
			if(val>0){
				nVals++;
			}
		}
		double label=(blable.booleanValue()==true)?1.:-1.;
		//the !=0 indexes
		int[] dims = new int[nVals];
		//the corresponding vals
		double[] vals = new double[nVals];
		for(int i=0,k=0;i<features.size();i++){
			Double val = features.get(i);
			if(val>0){
				dims[k]=i+1;
				vals[k]=val;
				k++;
			}
		}
		LabeledFeatureVector lfv =  new LabeledFeatureVector(label, dims, vals);
		//lfv.normalizeL2();
		return lfv;
	}

}
