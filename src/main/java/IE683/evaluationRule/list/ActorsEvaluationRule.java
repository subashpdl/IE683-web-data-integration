package IE683.evaluationRule.list;

import IE683.model.Movie;

public class ActorsEvaluationRule extends MovieListEvaluationRule {
    public ActorsEvaluationRule(Double innerSimilarityThreshold) throws NoSuchMethodException {
        super(innerSimilarityThreshold, Movie.ACTORS);
    }
}
