package source;
import java.util.List;

import lex.Entailment;
import lex.Term;

public interface Source {
	/**
	 * 
	 * @return name of source
	 */
	String getName();
	List<Entailment> getEntailments(Term t);
}
