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
	 * @throws SourceException 
	 */
	private void findBestMatches() throws SourceException {
	//	List<Entailment> allMatches = findAllMatches();
		// need to create a competitive algorithm for the best matches.
		// we have a bipartite tree.
		// we want to match all the RHS nodes
		// we want the maximum total weight of all edges chosen
		// a node in LHS might have edges to more than one node in RHS
		// and vise versa.
		// this is a multi graph because different sources can create
		// the same entailments (with different scores)
		
		findAllMatches();
		for (Word w : hypothesis.getWords()) {
			EntailedTerm t = (EntailedTerm)w.getTerm();
			List<Entailment> ents = t.getEntailments();
			// do this only if there is more than one element
			if (ents.size() > 1) {
				Entailment maxEnt = ents.get(0);
				for (Entailment ent : ents) {
					if (ent.getScore() > maxEnt.getScore()) {
						maxEnt = ent;
					}
				}
				// found the max scored entailment, so we leave only that one in.
				ents.clear();
				ents.add(maxEnt);
			}
		}
	}
	/**
	 * get all possible matches of a hyponym in the hypothesis to a hypernym
	 * in the candidate sentence.
	 * @return
	 * @throws SourceException
	 */
	private void findAllMatches() throws SourceException {
		for (Word w : sentence.getWords()) {
			EntailingTerm lhs = (EntailingTerm)w.getTerm();
			lhs.init(); // Gets the possible entailments
			for (Entailment ent : lhs.getEntailments()) {
				// add the term to the entailedTerms of the hypothesis
				EntailedTerm rhs = (EntailedTerm)hypothesis.getTerm(ent.getHypernym());
				if (rhs != null) {
						rhs.addEntailment(ent);
				}
			}
			lhs.clear();
		}

		featureScore = FeatureManager.getInstance().getFeatureVector(hypothesis);
	}


	/**
	 * from the assignment file:
	 * topic-id hypothesis-id document-id sentence-id (the last 2 ids are of the entailing sentence)
	 * @return a string in the format of the Result file
	 */
	public String getOutputString() {
		String hypeID = hypothesis.getSentenceId();
		String docID = sentence.getDocumentId();
		String sentID = sentence.getSentenceId();
		return String.format("%s\t%s\t%s\t%s", topic, hypeID, docID, sentID);
	}

	@Override
	public String toString() {
		return "SentenceEntailment [decision=" + decision 
		+ ", hypothesis=" + hypothesis + ", sentence="
				+ sentence + "]";
	}

	/**
	 *
	 * @return a string in the format of the Rule application file
	 */
	public List<String> getRuleStrings() {
		List<String> strings = new LinkedList<String>();
		for (Word w : hypothesis.getWords()) {
			EntailedTerm t = (EntailedTerm)w.getTerm();
			for (Entailment ent : t.getEntailments()) {
				strings.add(ent.getRuleString());
			}
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
	//	findBestMatches();
	}
	public void clear() {
		sentence.clear();
		hypothesis.clear();
	}
	
	private  List<Double> featureScore;

	private Sentence hypothesis;
	private Sentence sentence;
	/**
	 * the topic ID of the entailment found in the given Set.
	 */
	private String topic;

	private boolean isDecisionSet;
	private boolean decision;

}
