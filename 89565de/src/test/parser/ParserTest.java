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
		List<SentenceEntailment> entailments =  parser.next();
		while(entailments!=null){
			// System.out.println(entailments);
			//System.out.println();
			entailments =  parser.next();
		}
		/*for(String pos:parser.posSet){
			System.out.println(pos);
		}*/
		//fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testReg() {
		String str = "Sent:	AFP_ENG_20050613.0282	4	[n:Gerry:gerry, n:London:london, v:awaiting:await, a:Irish:irish, n:Dublin:dublin, n:leader:leader, n:Fein:fein, n:group:group, a:political:political, n:call:call, n:violence:violence, n:Republican:republican, n:Sinn:sinn, n:response:response, n:wing:wing, n:Adams:adam, n:Army:army, n:end:end]	0";
		str=str.replaceAll("(\\[)|(\\]\t[01])","");
		System.out.println(str);
	}
}
