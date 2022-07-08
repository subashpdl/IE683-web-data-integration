package IE683.evaluationRule.numeric;

import IE683.model.Movie;

public class AvgVoteEvaluationRule extends MovieNumericEvaluationRule {
    public AvgVoteEvaluationRule() throws NoSuchMethodException {
        super(Movie.AVG_VOTE);
    }
}
