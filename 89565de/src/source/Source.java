package source;
import java.util.List;

import lex.Entailment;
import lex.Term;

public interface Source {
	public List<Entailment> getEntailments(Term t) throws SourceException;

	public String getName();
	@Override
	public int hashCode();
	@Override
	public boolean equals(Object obj);
}
