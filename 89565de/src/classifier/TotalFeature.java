package classifier;

import java.util.List;

import lex.EntailedTerm;
import lex.Entailment;
import lex.Sentence;
import lex.Word;

public class TotalFeature extends Feature {

	public double score(Sentence hypo) {

		double avgSum = 0;
		for (Word word : hypo.getWords()) {
			EntailedTerm term = (EntailedTerm)word.getTerm();
			List<Entailment> ents = term.getEntailments();
			double totalEntail = 0;
			for(Entailment ent : ents){
				totalEntail += ent.getScore();
			}
			avgSum += totalEntail / ents.size();
		}
		return (avgSum)/(hypo.getWords().size());
	}
}
