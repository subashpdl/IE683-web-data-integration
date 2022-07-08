package IE683.evaluationRule.string;

import IE683.evaluationRule.MovieEvaluationRule;
import IE683.model.Movie;
import de.uni_mannheim.informatik.dws.winter.similarity.string.TokenizingJaccardSimilarity;

public class TitleEvaluationRule extends MovieEvaluationRule<String> {
    public TitleEvaluationRule() throws NoSuchMethodException {
        super(new TokenizingJaccardSimilarity(), Movie.TITLE);
    }
}
