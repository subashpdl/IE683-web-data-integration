package IE683.evaluationRule.numeric;

import IE683.model.Movie;

public class ImdbScoreEvaluationRule extends MovieNumericEvaluationRule {
    public ImdbScoreEvaluationRule() throws NoSuchMethodException {
        super(Movie.IMDB_SCORE);
    }
}
