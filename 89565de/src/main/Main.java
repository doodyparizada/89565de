package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;

import classifier.Classifier;
import classifier.Svm;

import lex.SentenceEntailment;
import parser.Parser;
import source.DirectAdapter;
import source.NaiveSource;
import source.SourceFactory;
import source.WordNetAdapter;

public class Main {

	static final String TRAIN = "train";
	static final String DECIDE = "decide";
	static final String EVALUATE = "evaluate";
	static final String __TEST = "__test";
	static final String USAGE =
		"Usage: progname " + TRAIN + " <filename>" +
		"\nOR   : progname " + DECIDE + " <filename> <resultFile> <ruleFile>" +
		"\nOR   : progname " + EVALUATE + " <filename>";
	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		boolean isTraining = false;
		boolean isEvaluate = false;
		boolean is__test = false;
		
		// parse arguments:
		if (args.length  < 2) {
			throw new Exception(USAGE);
		}
		// train the classifier
		if (args[0].equals(TRAIN)) {
			isTraining = true;
		} else if (args[0].equals(DECIDE)) {
			isTraining = false;
		} else if (args[0].equals(EVALUATE)) {
				isTraining = true;
				isEvaluate = true;
		} else if (args[0].equals(__TEST)) {
				is__test = true;
		} else {
			throw new Exception(USAGE);
		}
		
		// init lexical sources
		SourceFactory sfact = SourceFactory.getInstance();
		sfact.clear();
		// DirectAdapter.register();
		WordNetAdapter.register();
		NaiveSource.register();
		
		Parser parser = new Parser(args[1], isTraining);	//	"Processed_DevSet.txt"
		if (is__test) {
			__test(parser);
		} else if (isEvaluate) {
			evaluate(parser);
		} else if (isTraining) {
			train(parser);
		} else {
			decide(parser, args[2], args[3]);
		}
	}

	static private void __test(Parser parser) throws Exception {
		List<SentenceEntailment> sentenceEntailments = null;

		List<Double> total = new LinkedList<Double>();
		// create SentenceEntailment Objects
		while((sentenceEntailments = parser.next())!=null){
			// generate Features
			for (SentenceEntailment sentenceEntailment : sentenceEntailments) {
				List<Double> feats = sentenceEntailment.getFeatureScore();
				total.addAll(feats);
			}
		}
		System.out.println(total.size());
		}
	
	static private void train(Parser parser) throws Exception {
		Classifier classifier = new Svm();
		List<SentenceEntailment> sentenceEntailments = null;

		int stopCounter = 0;

		// create SentenceEntailment Objects
		while((sentenceEntailments = parser.next())!=null){
			// generate Features
			for (SentenceEntailment sentenceEntailment : sentenceEntailments) {
				List<Double> feats = sentenceEntailment.getFeatureScore();
				// put in classifier
				classifier.addLearningExample(feats, sentenceEntailment.DoesEntail());
				/*stopCounter++;
				if (stopCounter > 300) {
					break;
				}*/
			}
			/*if (stopCounter > 300) {
				break;
			}*/
		}
		classifier.createModel();
	}

	static private void decide(Parser parser, String resultsFileName, String rulesFileName) throws FileNotFoundException {
		Classifier classifier = new Svm();
		classifier.readModelFromFile();
		List<SentenceEntailment> sentenceEntailments = null;

		List<SentenceEntailment> results = new LinkedList<SentenceEntailment>();

		// create SentenceEntailment Objects
		while((sentenceEntailments = parser.next())!=null){
			// generate Features
			for (SentenceEntailment sentenceEntailment : sentenceEntailments) {
				List<Double> feats = sentenceEntailment.getFeatureScore();
				// get classifiers decision
				sentenceEntailment.setDecision(classifier.doesEntail(feats));
				results.add(sentenceEntailment);
			}
		}
		// output results
		PrintStream resultsStream = new PrintStream(new FileOutputStream(new File(resultsFileName)));
		PrintStream rulesStream = new PrintStream(new FileOutputStream(new File(rulesFileName)));
		for (SentenceEntailment sentenceEntailment : results) {
			// write to results file
			resultsStream.println(sentenceEntailment.getOutputString());
			// write to rule file
			for (String rule : sentenceEntailment.getRuleStrings()) {
				rulesStream.println(rule);
			}
		}
	}


	static private void evaluate(Parser parser) throws Exception {
		Classifier classifier = new Svm();
		classifier.readModelFromFile();
		List<SentenceEntailment> sentenceEntailments = null;

		int doEntailCount = 0;
		int foundEntailingCount = 0;
		int correctEntailmentsCount = 0;

		int stopCounter = 0;

		// create SentenceEntailment Objects
		while((sentenceEntailments = parser.next())!=null){
			// generate Features
			for (SentenceEntailment sentenceEntailment : sentenceEntailments) {
				List<Double> feats = sentenceEntailment.getFeatureScore();

				boolean foundEntailing = classifier.doesEntail(feats);
				boolean doEntail = sentenceEntailment.DoesEntail();
				if (doEntail) {
					doEntailCount += 1;
				}
				if (foundEntailing) {
					foundEntailingCount += 1;
				}
				if (doEntail && foundEntailing) {
					correctEntailmentsCount += 1;
				}
				stopCounter++;
				if (stopCounter > 300) {
					break;
				}
			}
			if (stopCounter > 300) {
				break;
			}

		}

		double recall = 100 * (double)(correctEntailmentsCount)/ doEntailCount;
		double percision = 100 * (double)(correctEntailmentsCount) / foundEntailingCount;
		double F1 = (2* recall * percision)/ (recall+percision);
		System.out.println(
				"Recall: " + recall  +
				"\nPercision: " + percision +
				"\nF1: " + F1);
	}
}
