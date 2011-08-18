package lex;

public class Word {
	private Term term;
	private String original;
	public Word(Term term, String original) {
		this.term = term;
		this.original = original;
	}
	@Override
	public String toString() {
		return "Word [original=" + original + ", term=" + term + "]";
	}

}
