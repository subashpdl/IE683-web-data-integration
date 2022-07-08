package IE683.evaluationRule.list;

import IE683.model.Movie;

public class GenresEvaluationRule extends MovieListEvaluationRule {
    public GenresEvaluationRule(Double innerSimilarityThreshold) throws NoSuchMethodException {
        super(innerSimilarityThreshold, Movie.GENRES);
    }
}
