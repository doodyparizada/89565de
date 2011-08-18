package lex;

import java.util.List;

public class Sentence {
	private List<Word> words;

	public Sentence(List<Word> words) {
		this.words = words;
	}

	@Override
	public String toString() {
		return "Sentence [words=" + words + "]";
	}

}
