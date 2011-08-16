package source;

import java.util.List;

import lex.Entailment;
import lex.Term;

public class WikipediaAdapter implements Source {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "wikipedia";
	}

	@Override
	public List<Entailment> getEntailments(Term t) {
		// TODO Auto-generated method stub
		// use the imported DB to generate entailments.
		return null;
	}

}
