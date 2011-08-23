package source;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.StatelessSession;
import org.hibernate.cfg.Configuration;

import pos.Pos;

import lex.Entailment;
import lex.Term;
import model.direct.Nouns;
import model.direct.Verbs;

public class DirectAdapter implements Source {

	private DirectAdapter() {}
	static StatelessSession statelessSession =
		new Configuration().
		configure().
		buildSessionFactory().
		openStatelessSession();
	String name =  "direct";
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}
	//XXX : what should I do when it's not in {v,n}??
	@Override
	public List<Entailment> getEntailments(Term t) {
		// TODO Auto-generated method stub
		// use the imported DB to generate entailments.
		Pos pos= t.getPos();
		String term=t.getTerm();
		List<Entailment> entailments = new ArrayList<Entailment>();
		entailments.add(new Entailment(t, t,new BigDecimal(1) , this));
		if(pos.equals(Pos.NOUN)){
			List<Nouns> entailedNounsList =
				(List<Nouns>)statelessSession.
				createQuery("from Nouns as n where n.id.lhs=:term").
				setParameter("term",term).list();
			for(Nouns noun:entailedNounsList){
				entailments.add(
						new Entailment(t, new Term(noun.getId().getRhs(),Pos.NOUN), noun.getScore(), this));
			}
			return entailments;
		}else if(pos.equals(Pos.VERB)){
			List<Verbs> entailedVerbsList =
				(List<Verbs>)statelessSession.
				createQuery("from Verbs as v where v.id.lhs=:term").
				setParameter("term",term).list();
			for(Verbs verb:entailedVerbsList){
				entailments.add(
						new Entailment(t, new Term(verb.getId().getRhs(),Pos.VERB), verb.getScore(), this));
			}
			return entailments;
		}else{
			return entailments;
		}

	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DirectAdapter other = (DirectAdapter) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public static void register() {
		SourceFactory.getInstance().register(new DirectAdapter());
	}
}
