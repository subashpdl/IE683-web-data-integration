package IE683.evaluationRule.list;

import IE683.evaluationRule.MovieEvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.list.GeneralisedJaccard;
import de.uni_mannheim.informatik.dws.winter.similarity.string.LevenshteinSimilarity;

import java.util.Collection;

public abstract class MovieListEvaluationRule extends MovieEvaluationRule<Collection<String>> {
    public MovieListEvaluationRule(Double innerSimilarityThreshold, Attribute attribute) throws NoSuchMethodException {
        super(new GeneralisedJaccard<>(new LevenshteinSimilarity(), innerSimilarityThreshold), attribute);
    }
}
