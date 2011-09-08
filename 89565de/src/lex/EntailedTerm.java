package lex;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import pos.Pos;
import source.Source;



public class EntailedTerm extends Term {

	public EntailedTerm(String term, Pos pos) {
		super(term, pos);
		entailments = new LinkedList<Entailment>();
	}
	public void addEntailment(Entailment ent) {
	//	System.out.println("added " + ent + " to " + term);
		entailments.add(ent);
	}
	public List<Entailment> getEntailments(){
		return entailments;
	}
	private List<Entailment> entailments;
	public List<BigDecimal> getScores(Source source) {
		LinkedList<BigDecimal> scores = new LinkedList<BigDecimal>();
		for(Entailment entailment:entailments){
			if(entailment.getScore().equals(source)){
				scores.add(entailment.getScore());
			}
		}
		return scores;
	}

}
