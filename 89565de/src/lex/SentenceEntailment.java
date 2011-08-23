package lex;

import java.util.LinkedList;
import java.util.List;

public class SentenceEntailment {

	public SentenceEntailment(
			Sentence hypothesis,
			Sentence sentence,
			String topic,
			boolean decision) {
		this.hypothesis = hypothesis;
		this.sentence = sentence;
		this.topic = topic;
		this.decision = decision;
	}
	public SentenceEntailment(Sentence hypothesis, Sentence sentence,
			String topic) {
		this.hypothesis = hypothesis;
		this.sentence = sentence;
		this.topic = topic;
	}
	/**
	 * try to match every word in the hypothesis (hyponym) to ONE word in the
	 * candidate sentence (hypernym) using the sentence's EntailingTerms.
	 * whilst maximizing the entailment scores.
	 * @return
	 */
	private List<Entailment> findBestMatches() {
		List<Entailment> allMatches = findAllMatches();
		// need to create a competetive algorithm for the best matchs.
		// we have a bipartite tree.
		// we want to match all the RHS nodes
		// we want the maximum total weight of all edges chosen
		// a node in LHS might have edges to more than one node in RHS
		// and vise versa.
		// this is a multi graph because different sources can create
		// the same entailments (with diffent scores)
		return null;
	}
	/**
	 * get all possible matches of a hyponym in the hypothesis to a hypernym
	 * in the candidate sentence.
	 * @return
	 */
	private List<Entailment> findAllMatches() {
		List<Entailment> retval = new LinkedList<Entailment>();
		for (Word w : sentence.getWords()) {
			EntailingTerm et = (EntailingTerm)w.getTerm();
			for (Entailment ent : et.getEntailments()) {
				if (hypothesis.containsTerm(ent.getHypernym())) {
					retval.add(ent);
				}
			}
		}
		return retval;
	}


	/**
	 * from the assignment file:
	 * topic-id hypothesis-id document-id sentence-id (the last 2 ids are of the entailing sentence)
	 * @return a string in the format of the Result file
	 */
	public String getOutputString() {
		return String.format("%s\t%s\t%s\t%s", topic, hypothesis.getSentenceId(), sentence.getDocumentId(), sentence.getSentenceId());
	}

	@Override
	public String toString() {
		return "SentenceEntailment [decision=" + decision + ", entailments="
				+ entailments + ", hypothesis=" + hypothesis + ", sentence="
				+ sentence + "]";
	}

	/**
	 *
	 * @return a string in the format of the Rule application file
	 */
	public List<String> getRuleStrings() {
		List<String> strings = new LinkedList<String>();
		for (Entailment ent : entailments) {
			strings.add(ent.getRuleString());
		}
		return strings;
	}


	private Sentence hypothesis;
	private Sentence sentence;
	/**
	 * the topic ID of the entailment found in the given Set.
	 */
	private String topic;
	private List<Entailment> entailments;

	private boolean decision;
}
