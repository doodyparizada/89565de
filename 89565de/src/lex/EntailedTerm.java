package lex;

import java.util.List;

import pos.Pos;

public class EntailedTerm extends Term {

	public EntailedTerm(String term, Pos pos) {
		super(term, pos);
	}
	public void addEntailment(Entailment ent) {
		entailments.add(ent);	
	}
	public List<Entailment> getEntailments(){
		return entailments;
	}
	private List<Entailment> entailments; 

}
