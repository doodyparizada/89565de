package source;

import java.util.List;

import lex.Entailment;
import lex.Term;

public class WordNetAdapter implements Source {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "wordnet";
	}

	@Override
	public List<Entailment> getEntailments(Term t) {
		// TODO Auto-generated method stub
		// use JWNL API to return entailmetns.
		return null;
	}

}
