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
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import pos.Pos;

public class Parser {
	static String NL = System.getProperty("line.separator");
	private boolean isTranining=false;
	//public HashSet<String> posSet=new HashSet<String>();
	public Parser(String filename,boolean isTranining) {
		try {
			this.isTranining = isTranining;
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
		//to indicate that there is no more data to parse returns null
		if(!this.scanner.hasNext()){
			return null;
		}
		String nextBulk = this.scanner.next();
		if(nextBulk.matches("Topic:\t[\\w\\d]+[\\s$]+")){
			this.currentTopic=nextBulk.substring(7).trim();
			System.out.println(currentTopic);
			nextBulk = this.scanner.next();
		}
		//System.out.println(nextBulk);
		//first row of nextBulk is the hypo
		//
		Scanner bulkScanner = new Scanner(nextBulk).useDelimiter
		(Pattern.compile("("+NL+"Sent:\t[\\S]+\t\\d+\t\\[)"
				+"|("+NL+"Topic:\\t)"));
		//System.out.println("hypo "+ bulkScanner.next());
		Sentence hypo = extractSentence(bulkScanner.next(),true);

		while (bulkScanner.hasNext()) {
			String candidateStr=bulkScanner.next();
			if(!bulkScanner.hasNext()){
				if(candidateStr.matches("[\\w\\d]+[\\s$]+")){
					this.currentTopic=candidateStr.trim();
					System.out.println(this.currentTopic);
					continue;
				}
			}
			candidateStr=candidateStr.trim();
			// XXX decision is only in training mode
			if(isTranining){
				char desicionChar = candidateStr.charAt(candidateStr.length()-1);
				if(desicionChar!='1'&&desicionChar!='0'){
					System.err.println("desicion can't be made!");
				}
				boolean decision = desicionChar == '1';
				//System.out.println(decision);
				sentenceEntailments.add(
						new SentenceEntailment(
								hypo,
								extractSentence(candidateStr.replaceAll("\\] [01]",""),false),
								currentTopic, // XXX where to we get the topic???
								decision
						));
			}else{
				sentenceEntailments.add(
						new SentenceEntailment(
								hypo,
								extractSentence(candidateStr.replaceAll("\\] [01]",""),false),
								currentTopic // XXX where to we get the topic???
						));
			}


		}
		return sentenceEntailments;
	}

	private Sentence extractSentence(String next,boolean isHypo) {
		List<Word> words = new ArrayList<Word>();
		Scanner scanner = new Scanner(next).useDelimiter(", ");
		while (scanner.hasNext()) {
			String[] tri = scanner.next().split(":");
			//posSet.add(tri[0]);
			Term term=isHypo?
					new EntailedTerm(tri[2], Pos.fromString(tri[0]))
					:new EntailingTerm(tri[2], Pos.fromString(tri[0]));
			words.add(new Word(term, tri[1]));

		}
		return new Sentence(words);
	}
	/*// XXX yes its ugly... :(
	private Sentence extractHypo(String next) {
		List<Word> words = new ArrayList<Word>();
		Scanner scanner = new Scanner(next).useDelimiter(", ");
		while (scanner.hasNext()) {
			String[] tri = scanner.next().split(":");
			words.add(new Word(new EntailedTerm(tri[2], Pos.fromString(tri[0])), tri[1]));
		}
		return new Sentence(words);
	}*/
	private String currentTopic = "";
	private Scanner scanner;

}
