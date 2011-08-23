package test.parser;

import static org.junit.Assert.*;

import java.util.List;

import lex.SentenceEntailment;

import org.junit.Test;

import parser.Parser;
import source.DirectAdapter;
import source.SourceFactory;

public class ParserTest {

	@Test
	public final void testNext() {
		Parser parser = new Parser("Processed_DevSet.txt",true);
		SourceFactory sfact = SourceFactory.getInstance();
		sfact.clear();
		DirectAdapter.register();
		List<SentenceEntailment> entailments =  parser.next();
		while(entailments!=null){
			//System.out.println(entailments);
			//System.out.println();
			entailments =  parser.next();
		}
		/*for(String pos:parser.posSet){
			System.out.println(pos);
		}*/
		//fail("Not yet implemented"); // TODO
	}

}
