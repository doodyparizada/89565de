package lex;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Sentence {
	public Sentence(List<Word> words) {
		this.words = words;
		this.wordMap = new HashSet<Term>();
		for (Word w: words) {
			wordMap.add(w.getTerm());	
		}
	}
	
	private Set wordMap;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Word w : words) {
			sb.append(w.getTerm() + ", ");
		}
		return sb.toString();
	}

	public List<Word> getWords() {
		return words;
	}
	

	public boolean containsTerm(Term term) {
		return wordMap.contains(term);
	}

	public Term getTerm(Term t) {
		if (wordMap.contains(t)) {
			// have to implememt XXX
		}
	}
	
	public String getSentenceId() {
		return sentenceId;
	}
	public void setSentenceId(String sentenceId) {
		this.sentenceId = sentenceId;
	}
	public String getDocumentId() {
		return documentId;
	}
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public void clear() {
		words.clear();
	}
	private String sentenceId;
	private String documentId;
	private List<Word> words;

}
