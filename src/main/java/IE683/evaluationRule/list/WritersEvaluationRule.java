package IE683.evaluationRule.list;

import IE683.model.Movie;

public class WritersEvaluationRule extends MovieListEvaluationRule {
    public WritersEvaluationRule(Double innerSimilarityThreshold) throws NoSuchMethodException {
        super(innerSimilarityThreshold, Movie.WRITERS);
    }
}
