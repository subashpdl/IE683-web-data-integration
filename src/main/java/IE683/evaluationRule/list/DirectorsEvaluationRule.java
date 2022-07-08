package IE683.evaluationRule.list;

import IE683.model.Movie;

public class DirectorsEvaluationRule extends MovieListEvaluationRule {
    public DirectorsEvaluationRule(Double innerSimilarityThreshold) throws NoSuchMethodException {
        super(innerSimilarityThreshold, Movie.DIRECTORS);
    }
}
