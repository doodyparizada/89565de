package lex;

import java.util.List;

public class SentenceEntailment {

	public SentenceEntailment(Sentence hypothesis, Sentence sentence,
			 boolean decision) {
		this.hypothesis = hypothesis;
		this.sentence = sentence;
		this.decision = decision;
	}
	/**
	 * try to match every word in the hypothesis (hyponym) to ONE word in the
	 * candidate sentence (hypernym) using the sentence's EntailingTerms.
	 * whilst maximizing the entailment scores.
	 * @return
	 */
	private List<Entailment> findBestMatches() {
		return null;
	}
	/**
	 * get all possible matches of a hyponym in the hypothesis to a hypernym
	 * in the candidate sentence.
	 * @return
	 */
	private List<Entailment> findAllMatches() {
		return null;
	}

	private Sentence hypothesis;
	private Sentence sentence;
	private List<Entailment> entailments;

	private boolean decision;
}
