package test.parser;

import static org.junit.Assert.*;

import java.util.List;

import lex.SentenceEntailment;

import org.junit.Test;

import parser.Parser;

public class ParserTest {

	@Test
	public final void testNext() {
		Parser parser = new Parser("Processed_DevSet.txt");
		List<SentenceEntailment> entailments =  parser.next();
		while(!entailments.isEmpty()){
			System.out.println(entailments);
			System.out.println();
			entailments =  parser.next();
		}
		//fail("Not yet implemented"); // TODO
	}

}
