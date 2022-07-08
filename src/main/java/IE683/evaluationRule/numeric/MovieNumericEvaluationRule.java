package IE683.evaluationRule.numeric;

import IE683.evaluationRule.MovieEvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.numeric.DeviationSimilarity;

public abstract class MovieNumericEvaluationRule extends MovieEvaluationRule<Double> {
    public MovieNumericEvaluationRule(Attribute attribute) throws NoSuchMethodException {
        super(new DeviationSimilarity(), attribute);
    }
}
