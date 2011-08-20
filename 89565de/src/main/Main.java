package main;

import java.util.List;

import classifier.Feature;

import lex.SentenceEntailment;
import parser.Parser;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// parse file
		Parser parser = new Parser("Processed_DevSet.txt");
		List<SentenceEntailment> sentenceEntailments = null;
		// create SentenceEntailment Objects
		while(!(sentenceEntailments = parser.next()).isEmpty()){
			// generate Features

			// put in classifier
		}
		// output results
	}

}
