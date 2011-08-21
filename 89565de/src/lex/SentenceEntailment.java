package lex;

import java.util.LinkedList;
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

	private Sentence hypothesis;
	private Sentence sentence;
	@Override
	public String toString() {
		return "SentenceEntailment [decision=" + decision + ", entailments="
				+ entailments + ", hypothesis=" + hypothesis + ", sentence="
				+ sentence + "]";
	}

	private List<Entailment> entailments;

	private boolean decision;
}
