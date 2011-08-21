package lex;

import pos.Pos;

public class Term {
	protected String term;
	protected Pos pos;
	//protected String topic;
	public Term(String term, Pos pos){
		this.term = term;
		this.pos = pos;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public Pos getPos() {
		return pos;
	}
	@Override
	public String toString() {
		return "Term [pos=" + pos + ", term=" + term + "]";
	}
	public void setPos(Pos pos) {
		this.pos = pos;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pos == null) ? 0 : pos.hashCode());
		result = prime * result + ((term == null) ? 0 : term.hashCode());
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
		Term other = (Term) obj;
		if (pos == null) {
			if (other.pos != null)
				return false;
		} else if (!pos.equals(other.pos))
			return false;
		if (term == null) {
			if (other.term != null)
				return false;
		} else if (!term.equals(other.term))
			return false;
		return true;
	}

}
