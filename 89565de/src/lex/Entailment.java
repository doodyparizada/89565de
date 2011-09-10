package lex;

import java.math.BigDecimal;

import source.Source;

public class Entailment {
	public Entailment(Term hyponym, Term hypernym, double score, String source) {
		this.hypernym = hypernym;
		this.hyponym = hyponym;
		this.score = score;
		this.source = source;
	}

	/**
	 *
	 * @return LHS - the entailing Term - Specific
	 */
	public Term getHyponym() {
		return hyponym;
	}
	/**
	 *
	 * @return RHS the entailed Term - General
	 */
	public Term getHypernym() {
		return hypernym;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((hypernym == null) ? 0 : hypernym.hashCode());
		result = prime * result + ((hyponym == null) ? 0 : hyponym.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		Entailment other = (Entailment)obj;
		return other.hypernym.equals(other.hypernym) && other.hyponym.equals(hyponym);
	}
	/**
	 *
	 * @return a string in the format of the Rule application file
	 */
	public String getRuleString() {
		return String.format("%s\t%s\t%s", hyponym.term, hypernym.term, source);
	}

	@Override
	public String toString() {
		return "Entailment [hyponym=" + hyponym + " --> hypernym=" + hypernym
				+ ", score=" + score + ", source=" + source + "]";
	}





	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	private Term hyponym; // entails - LHS
	private Term hypernym; // entailed - RHS
	private double score;
	private String source;




}
