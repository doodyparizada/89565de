package test.parser;

import static org.junit.Assert.*;

import org.junit.Test;

import parser.Parser;

public class ParserTest {

	@Test
	public final void testNext() {
		Parser parser = new Parser("Processed_DevSet.txt");
		//for(int i=0;i<100;i++){
			parser.next();
		//}
		fail("Not yet implemented"); // TODO
	}

}
