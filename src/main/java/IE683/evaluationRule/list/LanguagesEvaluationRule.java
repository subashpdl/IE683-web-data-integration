package IE683.evaluationRule.list;

import IE683.model.Movie;

public class LanguagesEvaluationRule extends MovieListEvaluationRule {
    public LanguagesEvaluationRule(Double innerSimilarityThreshold) throws NoSuchMethodException {
        super(innerSimilarityThreshold, Movie.LANGUAGES);
    }
}
