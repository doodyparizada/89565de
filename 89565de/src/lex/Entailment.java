package lex;

import java.math.BigDecimal;

import source.Source;

public class Entailment {
	public Entailment(Term hyponym, Term hypernym, BigDecimal score, Source source) {
		this.hypernym = hypernym;
		this.hyponym = hyponym;
		this.score = score;
		this.source = source;
	}

	private Term hyponym;
	private Term hypernym;
	private BigDecimal score;
	private Source source;
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((hypernym == null) ? 0 : hypernym.hashCode());
		result = prime * result + ((hyponym == null) ? 0 : hyponym.hashCode());
		result = prime * result + ((score == null) ? 0 : score.hashCode());
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Entailment other = (Entailment) obj;
		if (hypernym == null) {
			if (other.hypernym != null)
				return false;
		} else if (!hypernym.equals(other.hypernym))
			return false;
		if (hyponym == null) {
			if (other.hyponym != null)
				return false;
		} else if (!hyponym.equals(other.hyponym))
			return false;
		if (score == null) {
			if (other.score != null)
				return false;
		} else if (!score.equals(other.score))
			return false;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Entailment [hypernym=" + hypernym + ", hyponym=" + hyponym
				+ ", score=" + score + ", source=" + source + "]";
	}

}
