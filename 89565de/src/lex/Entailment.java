package lex;

import source.Source;

public class Entailment {
	public Entailment(Term hyponym, Term hypernym, int score, Source source) {
		this.hypernym = hypernym;
		this.hyponym = hyponym;
		this.score = score;
		this.source = source;
	}
	
	private Term hyponym;
	private Term hypernym;
	private int score;
	private Source source;
}
