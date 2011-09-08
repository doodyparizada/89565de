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
	protected Source source;

	private double EPSILON = 0.0001;

	public double score(Sentence hypo) {
		int posOccurrencesCounter = 0;
		BigDecimal entailed = new BigDecimal(0);
		for (Word word : hypo.getWords()) {
			EntailedTerm term = (EntailedTerm)word.getTerm();
			if (term.getPos().equals(this.pos)) {
				posOccurrencesCounter += 1;
				List<BigDecimal> scores
				= term.getScores(this.source);
				for(BigDecimal score : scores)
				/*if (term.getEntailments().size() > 0)*/{
					entailed.add(score);
				}
			}
		}
		// return (entailed.doubleValue() + EPSILON)/(posOccurrencesCounter + EPSILON);
		return (entailed.doubleValue())/(posOccurrencesCounter);
	}
}
