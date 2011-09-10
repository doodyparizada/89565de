package classifier;

import java.math.BigDecimal;
import java.util.List;

import pos.Pos;
import source.Source;
import lex.EntailedTerm;
import lex.Entailment;
import lex.Sentence;
import lex.Word;

public class Feature {
	protected Pos pos;
	protected String source;

	private double EPSILON = 0.0001;

	public double score(Sentence hypo) {
		int posOccurrencesCounter = 0;
		double avgSum = 0;
		for (Word word : hypo.getWords()) {
			EntailedTerm term = (EntailedTerm)word.getTerm();
			if (term.getPos() == pos) {
				List<Entailment> ents = term.getEntailments();
				double totalEntail = 0;
				int sameSource = 0;
				for(Entailment ent : ents)
					if(ent.getSource().equals(source)){
					totalEntail += ent.getScore();
					sameSource += 1;
				}
				avgSum += totalEntail / sameSource;
				posOccurrencesCounter += 1;
			}
		}
		return (avgSum)/(posOccurrencesCounter);
	}
}
