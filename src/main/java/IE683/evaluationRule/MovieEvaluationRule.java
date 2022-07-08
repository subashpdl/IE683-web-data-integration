package IE683.evaluationRule;

import IE683.model.Movie;
import de.uni_mannheim.informatik.dws.winter.datafusion.EvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.SimilarityMeasure;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class MovieEvaluationRule<T> extends EvaluationRule<Movie, Attribute> {

    private final Method getAttributeMethod;
    SimilarityMeasure<T> sim;


    public MovieEvaluationRule(SimilarityMeasure<T> similarityMeasure, Attribute attribute)
            throws NoSuchMethodException {
        super();
        sim = similarityMeasure;
        getAttributeMethod = Movie.class.getMethod("get" + StringUtils.capitalize(attribute.getIdentifier()));
    }

    @Override
    public boolean isEqual(Movie record1, Movie record2, Attribute schemaElement) {
        try {
            return sim.calculate((T) getAttributeMethod.invoke(record1), (T) getAttributeMethod.invoke(record2)) == 1.0;
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean isEqual(Movie record1, Movie record2,
                           Correspondence<Attribute, Matchable> schemaCorrespondence) {
        return isEqual(record1, record2, (Attribute) null);
    }

}
