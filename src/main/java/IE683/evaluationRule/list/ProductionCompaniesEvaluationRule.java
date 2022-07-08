package IE683.evaluationRule.list;

import IE683.model.Movie;

public class ProductionCompaniesEvaluationRule extends MovieListEvaluationRule {
    public ProductionCompaniesEvaluationRule(Double innerSimilarityThreshold) throws NoSuchMethodException {
        super(innerSimilarityThreshold, Movie.PRODUCTION_COMPANIES);
    }
}
