package lex;

import java.util.List;

public class Sentence {
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
	private String sentenceId;
	private String documentId;
	private List<Word> words;

}
