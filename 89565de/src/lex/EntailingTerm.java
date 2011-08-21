package lex;

import java.util.List;

import pos.Pos;

public class EntailingTerm extends Term {
	public EntailingTerm(String term, Pos pos) {
		super(term,pos);
		// go to all known sources and get entailments
	}
	public List<Entailment> getEntailments(){
		return entailments;
	}
	private List<Entailment> entailments; 
}
