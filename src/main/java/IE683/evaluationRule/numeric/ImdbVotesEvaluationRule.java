package IE683.evaluationRule.numeric;

import IE683.model.Movie;

public class ImdbVotesEvaluationRule extends MovieNumericEvaluationRule {
    public ImdbVotesEvaluationRule() throws NoSuchMethodException {
        super(Movie.IMDB_VOTES);
    }
}
