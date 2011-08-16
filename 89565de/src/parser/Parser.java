package parser;

import lex.SentenceEntailment;
import java.io.FileInputStream;
import java.util.List;

public class Parser {
	List<SentenceEntailment> next() {
		// get the next hypothesis and all of its candidate sentences.
		
		
		// psudo code:
		// read hypothesis:
		//     read all words and create terms and create sentence.
		// for sentence in sentences:
		//     for word in sentence
		//         create an EntailingTerm (using TermFactory)
		//     create a Sentence
		//     create SentenceEntailment, add to list 
		return null;
	}
	
	
	private String currentTopic;
	private String filename;
	private FileInputStream input;
	
	
}
