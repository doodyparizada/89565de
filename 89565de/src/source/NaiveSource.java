package source;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import lex.Entailment;
import lex.Term;

public class NaiveSource implements Source {

	@Override
	public List<Entailment> getEntailments(Term t) throws SourceException {
		List<Entailment> l = new LinkedList<Entailment>();
		l.add(new Entailment(t, t, new BigDecimal(1) , this));
		return l;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "naive";
	}
	public static void register() {
		SourceFactory.getInstance().register(new NaiveSource());
	}

}
