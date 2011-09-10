package lex;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sentence {
	public Sentence(List<Word> words) {
		this.words = words;
		this.wordMap = new HashMap<Term,Term>();
		for (Word w: words) {
			wordMap.put(w.getTerm(),w.getTerm());
		}
	}

	private Map<Term,Term> wordMap;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.documentId+", ");
		sb.append(this.sentenceId+", ");
		for (Word w : words) {
			sb.append(w.getTerm() + ", ");
		}
		return sb.toString();
	}

	public List<Word> getWords() {
		return words;
	}

	public boolean containsTerm(Term term) {
		return wordMap.containsKey(term);
	}

	public Term getTerm(Term t) {
		return wordMap.get(t);
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
