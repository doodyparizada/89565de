package parser;
import lex.EntailedTerm;
import lex.EntailingTerm;
import lex.Sentence;
import lex.SentenceEntailment;
import lex.Term;
import lex.Word;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import pos.Pos;



public class Parser {
	static String NL = System.getProperty("line.separator");
	private boolean isTranining=false;
	private int lineNum = 0;
	private Pattern bulkPattern=Pattern.compile("("+NL+"Sent:\t)"//[\\S]+\t\\d+\t\\[)"
			+"|("+NL+"Topic:\\t)");
	private int i;
	//public HashSet<String> posSet=new HashSet<String>();
	public Parser(String filename,boolean isTraining) {
		try {
			this.isTranining = isTraining;
			this.scanner = new Scanner(new File(filename));
			this.scanner.useDelimiter("("+NL+")?Hypo:\t");//\\d+\t\\[");

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
			System.out.println("Topic: "+currentTopic);
			nextBulk = this.scanner.next();
		}
		//System.out.println(nextBulk);
		//first row of nextBulk is the hypo
		//
		Scanner bulkScanner = new Scanner(nextBulk).useDelimiter(this.bulkPattern);

		//System.out.println("hypo "+ bulkScanner.next());
		String hypoBulk=bulkScanner.next().replaceAll("[\\[\\]]","");

		System.out.println(lineNum++);

		while (bulkScanner.hasNext()) {
			lineNum++;
			Sentence hypo = extractSentence(hypoBulk,true);
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
				//
				//System.out.println(candidateStr.replaceAll("(\\[)|(\\]\t[01])",""));
				sentenceEntailments.add(
						new SentenceEntailment(
								hypo,
								extractSentence(candidateStr.replaceAll("(\\[)|(\\]\t[01])",""),false),
								currentTopic,
								decision
						));
			}else{
				sentenceEntailments.add(
						new SentenceEntailment(
								hypo,
								extractSentence(candidateStr.replaceAll("(\\[)|(\\])",""),false),
								currentTopic
						));
			}


		}
//		for (SentenceEntailment sentenceEntailment : sentenceEntailments) {
//			try {
//				System.out.println(sentenceEntailment.DoesEntail());
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		return sentenceEntailments;
	}

	private Sentence extractSentence(String next,boolean isHypo) {
		//System.out.println("1> "+next);
		//get documentId + sentenceId
		String[] split = next.split("\t",3);
		List<Word> words = new ArrayList<Word>();
		if(isHypo){
			next=split[1];
		}else{
			next=split[2];
		}
		Scanner scanner = new Scanner(next).useDelimiter(", ");
		while (scanner.hasNext()) {
			String[] tri = scanner.next().split(":");
			//posSet.add(tri[0]);
			Term term=isHypo?
					new EntailedTerm(new String(tri[2]), Pos.fromString(tri[0]))
					:new EntailingTerm(new String(tri[2]), Pos.fromString(tri[0]));
			words.add(new Word(term, new String(tri[1])));

		}
		Sentence sentence= new Sentence(words);
		if(isHypo){
			sentence.setSentenceId(split[0]);
		}else{
			sentence.setDocumentId(split[0]);
			sentence.setSentenceId(split[1]);
		}

		//System.out.println("2> "+next+"\n3> "+sentence);
		/*if(i++>5){
			System.exit(1);
		}*/
		return sentence;
	}

	private String currentTopic = "";
	private Scanner scanner;

}
