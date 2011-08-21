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

	public List<Word> getWords() {
		return words;
	}

	public boolean containsTerm(Term term) {
		for (Word w : words) {
			if (w.getTerm().equals(term)) { 
				return true;
			}
		}
		return false;
	}
}
