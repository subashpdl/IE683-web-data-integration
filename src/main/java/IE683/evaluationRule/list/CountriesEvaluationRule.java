package IE683.evaluationRule.list;

import IE683.model.Movie;

public class CountriesEvaluationRule extends MovieListEvaluationRule {
    public CountriesEvaluationRule(Double innerSimilarityThreshold) throws NoSuchMethodException {
        super(innerSimilarityThreshold, Movie.COUNTRIES);
    }
}
