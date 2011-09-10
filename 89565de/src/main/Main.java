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
import source.SourceException;
import source.SourceFactory;
import source.WordNetAdapter;

public class Main {

	static final String TRAIN = "train";
	static final String DECIDE = "decide";
	static final String EVALUATE = "evaluate";
	static final String TRAIN_EVALUATE = "train_evaluate";
	static final String __TEST = "__test";
	static final String USAGE =
		"Usage: progname " + TRAIN + " <filename>" +
		"\nOR   : progname " + DECIDE + " <filename> <resultFile> <ruleFile>" +
		"\nOR   : progname " + EVALUATE + " <filename>" +
		"\nOR   : progname " + TRAIN_EVALUATE + " <filename>";
	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		boolean isTraining = false;
		boolean isEvaluate = false;
		boolean isTrain_Evaluate = false;
		boolean is__test = false;

		// parse arguments:
		if (args.length  < 2) {
			throw new Exception(USAGE);
		}
		// train the classifier
		if (args[0].equals(TRAIN_EVALUATE)) {
			isTraining = true;
			isTrain_Evaluate = true;
		} else if (args[0].equals(TRAIN)) {
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
		String filename = args[1];
		// init lexical sources
		SourceFactory sfact = SourceFactory.getInstance();
		sfact.clear();
		DirectAdapter.register();
		WordNetAdapter.register();
		NaiveSource.register();
		System.out.println("after registrating");
		// init parser
		Parser parser = new Parser(filename, isTraining);	//	"Processed_DevSet.txt"

		if (is__test) {
			__test(parser);
		} else if (isTrain_Evaluate) {
			train(parser, 20);
			evaluate(new Parser(filename, true),20);
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
			while (!sentenceEntailments.isEmpty()) {
				SentenceEntailment sentenceEntailment = sentenceEntailments.remove(0);
				sentenceEntailment.init();
				List<Double> feats = sentenceEntailment.getFeatureScore();
				total.addAll(feats);
			}
		}
		System.out.println(total.size());
		}

	static private void train(Parser parser) throws Exception {
		train(parser,0);
	}

	static private void train(Parser parser, double percent) throws Exception {
		Classifier classifier = new Svm();
		List<SentenceEntailment> sentenceEntailments = null;
		// create SentenceEntailment Objects
		while((sentenceEntailments = parser.next())!=null){
			// generate Features
			int limit = (int)(sentenceEntailments.size()*percent/100.);
			System.out.println("limit " + limit);
			while (sentenceEntailments.size() > limit) {
				SentenceEntailment sentenceEntailment = sentenceEntailments.remove(0);
				sentenceEntailment.init();
				List<Double> feats = sentenceEntailment.getFeatureScore();
				// put in classifier
				classifier.addLearningExample(feats, sentenceEntailment.DoesEntail());
			}

		}
		classifier.createModel();
	}

	static private void decide(Parser parser, String resultsFileName, String rulesFileName) throws Exception {
		Classifier classifier = new Svm();
		classifier.readModelFromFile();
		List<SentenceEntailment> sentenceEntailments = null;

		PrintStream resultsStream = new PrintStream(new FileOutputStream(new File(resultsFileName)));
		PrintStream rulesStream = new PrintStream(new FileOutputStream(new File(rulesFileName)));

		// create SentenceEntailment Objects
		while((sentenceEntailments = parser.next())!=null){
			// generate Features
			while (!sentenceEntailments.isEmpty()) {
				SentenceEntailment sentenceEntailment = sentenceEntailments.remove(0);
				sentenceEntailment.init();
				List<Double> feats = sentenceEntailment.getFeatureScore();
				// get classifiers decision
				sentenceEntailment.setDecision(classifier.doesEntail(feats));
				// output results
				if (sentenceEntailment.DoesEntail()) {
					resultsStream.println(sentenceEntailment.getOutputString());
					// write to rule file
					for (String rule : sentenceEntailment.getRuleStrings()) {
						rulesStream.println(rule);
					}
				}
			}
		}
	}

	static private void evaluate(Parser parser) throws Exception {
		evaluate(parser, 100);
	}

	static private void evaluate(Parser parser, double percent) throws Exception {
		Classifier classifier = new Svm();
		classifier.readModelFromFile();
		List<SentenceEntailment> sentenceEntailments = null;

		int doEntailCount = 0;
		int foundEntailingCount = 0;
		int correctEntailmentsCount = 0;

		// create SentenceEntailment Objects
		while((sentenceEntailments = parser.next())!=null){
			// generate Features
			int limit = (int)(sentenceEntailments.size()*percent/100.);
			System.out.println("limit " + limit);
			while (sentenceEntailments.size() > limit) {
				sentenceEntailments.remove(0);
			}
			while (!sentenceEntailments.isEmpty()) {
				SentenceEntailment sentenceEntailment = sentenceEntailments.remove(0);
				sentenceEntailment.init();
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
			}

		}

		double recall =  correctEntailmentsCount == 0.0 ? 0 : (100 * (double)(correctEntailmentsCount + EPSILON)/ (doEntailCount + EPSILON));
		double percision = correctEntailmentsCount == 0.0 ? 0 : (100 * (double)(correctEntailmentsCount + EPSILON) / (foundEntailingCount  + EPSILON));
		double F1 = correctEntailmentsCount == 0.0 ? 0 : ((2* recall * percision)/ (recall+percision));

		System.out.println(
				"doEntail: " + doEntailCount +
				"\nfoundEntailingCount: " + foundEntailingCount +
				"\ncorrectEntailmentsCount: " + correctEntailmentsCount +
				"\nRecall: " + recall  +
				"\nPercision: " + percision +
				"\nF1: " + F1);
	}

	private static double EPSILON = Double.MIN_VALUE;
}
