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

import source.DirectAdapter;
import source.Source;
import source.SourceException;
import source.SourceFactory;
import source.WordNetAdapter;

public class WordNetAdapterTest {
	public WordNetAdapterTest() {
		SourceFactory sfact = SourceFactory.getInstance();
		sfact.clear();
		WordNetAdapter.register();
		wn = sfact.getSources().get(0);
	}
	
	private Source wn;
	
	@Test
	public final void testGetNounEntailments() throws SourceException {
		Term t = new Term("dog", Pos.NOUN);
		List<Entailment> ents = wn.getEntailments(t);
	
		//printEnts(ents);
		
		Entailment[] expected = new Entailment[]{
		new Entailment(t, new Term("dog", Pos.NOUN), new BigDecimal(1), wn),
		new Entailment(t, new Term("canine", Pos.NOUN), new BigDecimal(1*WordNetAdapter.DEGRADE), wn),
		new Entailment(t, new Term("carnivore", Pos.NOUN), new BigDecimal(1*WordNetAdapter.DEGRADE*WordNetAdapter.DEGRADE), wn),
		new Entailment(t, new Term("bloke", Pos.NOUN), new BigDecimal(1*WordNetAdapter.DEGRADE), wn),
		new Entailment(t, new Term("villain", Pos.NOUN), new BigDecimal(1*WordNetAdapter.DEGRADE), wn),
		new Entailment(t, new Term("meat", Pos.NOUN), new BigDecimal(1*WordNetAdapter.DEGRADE*WordNetAdapter.DEGRADE), wn),
		};
		
		Assert.assertTrue(ents.containsAll(Arrays.asList(expected)));
	}
	@Test
	public final void testGetVerbEntailments() throws SourceException {
		Term t = new Term("flick", Pos.VERB);
		List<Entailment> ents = wn.getEntailments(t);
	
		//printEnts(ents);
		
		Entailment[] expected = new Entailment[]{
		new Entailment(t, new Term("flip", Pos.VERB), new BigDecimal(1), wn),
		new Entailment(t, new Term("jerk", Pos.VERB), new BigDecimal(1), wn),
		new Entailment(t, new Term("throw", Pos.VERB), new BigDecimal(1*WordNetAdapter.DEGRADE), wn),
		new Entailment(t, new Term("touch", Pos.VERB), new BigDecimal(1*WordNetAdapter.DEGRADE*WordNetAdapter.DEGRADE), wn),
		new Entailment(t, new Term("wink", Pos.VERB), new BigDecimal(1*WordNetAdapter.DEGRADE), wn),
		new Entailment(t, new Term("peruse", Pos.VERB), new BigDecimal(1*WordNetAdapter.DEGRADE), wn),
		new Entailment(t, new Term("radiate", Pos.VERB), new BigDecimal(1*WordNetAdapter.DEGRADE*WordNetAdapter.DEGRADE), wn),
		};
		
		Assert.assertTrue(ents.containsAll(Arrays.asList(expected)));
	}
	
	void printEnts(List<Entailment> ents) {
		for (Entailment e : ents) {
			System.out.println(e.toString());
		}
	}
}
