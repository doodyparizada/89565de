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
		entailments.add(ent);
	}
	public List<Entailment> getEntailments(){
		return entailments;
	}
	public void clear() {
		entailments.clear();
	}
	private List<Entailment> entailments;

}
