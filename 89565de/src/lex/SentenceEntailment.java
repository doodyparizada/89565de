package lex;

import java.util.LinkedList;
import java.util.List;

import source.SourceException;

import classifier.FeatureManager;

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
		this.isDecisionSet = true;
	}
	public SentenceEntailment(
			Sentence hypothesis,
			Sentence sentence,
			String topic) {
		this.hypothesis = hypothesis;
		this.sentence = sentence;
		this.topic = topic;
		this.isDecisionSet = false;
	}
	/**
	 * try to match every word in the hypothesis (hyponym) to ONE word in the
	 * candidate sentence (hypernym) using the sentence's EntailingTerms.
	 * whilst maximizing the entailment scores.
	 * @return
	 */
	private List<Entailment> findBestMatches() {
	//	List<Entailment> allMatches = findAllMatches();
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
	 * @throws SourceException
	 */
	private void findAllMatches() throws SourceException {
		// System.out.println("finding entailments for [" + sentence + "] to [" + hypothesis + "]");
		//System.out.println("in find all mathces");
		for (Word w : sentence.getWords()) {
			EntailingTerm lhs = (EntailingTerm)w.getTerm();
			lhs.init(); // Gets the possible entailments
		//	System.out.println("finding matches for "+ lhs);
			for (Entailment ent : lhs.getEntailments()) {
				// add the term to the entailedTerms of the hypothesis
				EntailedTerm rhs = (EntailedTerm)hypothesis.getTerm(ent.getHypernym());
				if (rhs != null) {
						rhs.addEntailment(ent);
						//break; // XXX break inner loop, because if there are more
						// than two same EntailedTerms in the hypothesis,
						// they are the same instance from the factory.
				}
			}
			lhs.clear();
		}

		sentence.clear();
		sentence = null;

		featureScore = FeatureManager.getInstance().getFeatureVector(hypothesis);
	}


	/**
	 * from the assignment file:
	 * topic-id hypothesis-id document-id sentence-id (the last 2 ids are of the entailing sentence)
	 * @return a string in the format of the Result file
	 */
	public String getOutputString() {
		hypothesis.getSentenceId();
		 sentence.getDocumentId();
		 sentence.getSentenceId();
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



	public  List<Double> getFeatureScore() {
		return featureScore;
	}

	public boolean DoesEntail() throws Exception{
		if (! isDecisionSet) {
			throw new Exception("decision not set!");
		}
		return decision;
	}

	public void setDecision(boolean decision) {
		isDecisionSet = true;
		this.decision = decision;
	}
	public void init() throws SourceException {
		findAllMatches(); // generates the feature vector
	}

	private  List<Double> featureScore;

	private Sentence hypothesis;
	private Sentence sentence;
	/**
	 * the topic ID of the entailment found in the given Set.
	 */
	private String topic;
	private List<Entailment> entailments;

	private boolean isDecisionSet;
	private boolean decision;

}
