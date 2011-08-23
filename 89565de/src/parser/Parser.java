package parser;

import lex.EntailedTerm;
import lex.EntailingTerm;
import lex.Sentence;
import lex.SentenceEntailment;
import lex.Term;
import lex.Word;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import pos.Pos;

public class Parser {
	static String NL = System.getProperty("line.separator");
	public Parser(String filename) {
		try {
			this.scanner = new Scanner(new File(filename));
			this.scanner.useDelimiter("("+NL+")?Hypo:\t\\d+\t\\[");
				
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<SentenceEntailment> next() {
		// get the next hypothesis and all of its candidate sentences.
		// psudo code:
		// read hypothesis:
		//     read all words and create terms and create sentence.
		// for sentence in sentences:
		//     for word in sentence
		//         create an EntailingTerm (using TermFactory)
		//     create a Sentence
		//     create SentenceEntailment, add to list
		List<SentenceEntailment> sentenceEntailments = new ArrayList<SentenceEntailment>(10);
		//SentenceEntailment sentenceEntailment = null;
		// XXX explain this line ????
		if(!this.scanner.hasNext()){
			return sentenceEntailments;
		}
		String nextBulk = this.scanner.next();
		if(nextBulk.matches("Topic:\t[\\w\\d]+[\\s$]+")){
			nextBulk = this.scanner.next();
		}
		//System.out.println(nextBulk);
		//first row of nextBulk is the hypo
		//
		Scanner bulkScanner = new Scanner(nextBulk).useDelimiter(NL+"Sent:\t[\\S]+\t\\d+\t\\[");
		//System.out.println("hypo "+ bulkScanner.next());
		Sentence hypo = extractHypo(bulkScanner.next());
		while (bulkScanner.hasNext()) {
			String candidateStr = bulkScanner.next().replaceFirst(NL,"");
			
			// XXX decision is only in training mode
			boolean decision = candidateStr.charAt(candidateStr.length()-1) != '0';
			
			sentenceEntailments.add(
					new SentenceEntailment(
							hypo,
							extractSentence(candidateStr.replaceAll("\\] [01]","")),
							currentTopic, // XXX where to we get the topic???
							decision
							));
		}
		return sentenceEntailments;
	}

	private Sentence extractSentence(String next) {
		List<Word> words = new ArrayList<Word>();
		Scanner scanner = new Scanner(next).useDelimiter(", ");
		while (scanner.hasNext()) {
			String[] tri = scanner.next().split(":");
			words.add(new Word(new EntailingTerm(tri[2], Pos.fromString(tri[0])), tri[1]));
		}
		return new Sentence(words);
	}
	// XXX yes its ugly... :(
	private Sentence extractHypo(String next) {
		List<Word> words = new ArrayList<Word>();
		Scanner scanner = new Scanner(next).useDelimiter(", ");
		while (scanner.hasNext()) {
			String[] tri = scanner.next().split(":");
			words.add(new Word(new EntailedTerm(tri[2], Pos.fromString(tri[0])), tri[1]));
		}
		return new Sentence(words);
	}
	private String currentTopic = "";
	private Scanner scanner;

}
