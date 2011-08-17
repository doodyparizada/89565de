package source;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.StatelessSession;
import org.hibernate.cfg.Configuration;

import lex.Entailment;
import lex.Term;
import model.direct.Nouns;
import model.direct.Verbs;

public class DirectAdapter implements Source {
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
	@Override
	public List<Entailment> getEntailments(Term t) {
		// TODO Auto-generated method stub
		// use the imported DB to generate entailments.
		String pos=t.getPos();
		String term=t.getTerm();
		if(pos.equals("n")){
			List<Nouns> entailedNounsList =
				(List<Nouns>)statelessSession.
				createQuery("from Nouns as n where n.id.lhs=:term").
				setParameter("term",term).list();
			if(entailedNounsList.isEmpty()){
				return null;
			}else{
				List<Entailment> entailments = new ArrayList<Entailment>();
				for(Nouns noun:entailedNounsList){
					entailments.add(
							new Entailment(t, new Term(noun.getId().getRhs(),"n"), noun.getScore(), this));
				}
				return entailments;
			}
		}else if(pos.equals("v")){
			List<Verbs> entailedVerbsList =
				(List<Verbs>)statelessSession.
				createQuery("from Verbs as v where v.id.lhs=:term").
				setParameter("term",term).list();
			if(entailedVerbsList.isEmpty()){
				return null;
			}else{
				List<Entailment> entailments = new ArrayList<Entailment>();
				for(Verbs verb:entailedVerbsList){
					entailments.add(
							new Entailment(t, new Term(verb.getId().getRhs(),"v"), verb.getScore(), this));
				}
				return entailments;
			}
		}else{
			return null;
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

}
