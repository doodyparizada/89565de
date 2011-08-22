package test.source;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import lex.Entailment;
import lex.Term;

import org.junit.Test;

import pos.Pos;

import source.SourceException;
import source.WordNetAdapter;

public class WordNetAdapterTest {

	@Test
	public final void testGetEntailments() throws SourceException {
		WordNetAdapter wn = new WordNetAdapter();
		wn.init();
		Term t = new Term("dog", Pos.NOUN);
		List<Entailment> ents = wn.getEntailments(t);
	
		printEnts(ents);
		
		Entailment[] expected = new Entailment[]{
		new Entailment(t, new Term("canine", Pos.NOUN), new BigDecimal(1), wn),
		new Entailment(t, new Term("carnivore", Pos.NOUN), new BigDecimal(1*wn.DEGRATE), wn),
		new Entailment(t, new Term("bloke", Pos.NOUN), new BigDecimal(1), wn),
		new Entailment(t, new Term("villain", Pos.NOUN), new BigDecimal(1*wn.DEGRATE), wn),
		new Entailment(t, new Term("meat", Pos.NOUN), new BigDecimal(1*wn.DEGRATE*wn.DEGRATE), wn),
		};
		
		Assert.assertTrue(ents.containsAll(Arrays.asList(expected)));
	}
	
	void printEnts(List<Entailment> ents) {
		for (Entailment e : ents) {
			System.out.println(e.toString());
		}
	}
}
