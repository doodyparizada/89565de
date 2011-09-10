package lex;

import java.util.LinkedList;
import java.util.List;

import pos.Pos;


import source.Source;
import source.SourceException;
import source.SourceFactory;

public class EntailingTerm extends Term {
	public EntailingTerm(String term, Pos pos) {
		super(term,pos);
		//entailments = new LinkedList<Entailment>();
	}

	public void init() throws SourceException {
		entailments = new LinkedList<Entailment>();
		// go to all known sources and get entailments
		for (Source s : SourceFactory.getInstance().getSources()) {
			entailments.addAll(s.getEntailments(this)); // XXX remove duplicates???
		}
	}

	public List<Entailment> getEntailments(){
		return entailments;
	}
	private List<Entailment> entailments;

	public void clear() {
		entailments.clear();
		entailments = null;
	}
}