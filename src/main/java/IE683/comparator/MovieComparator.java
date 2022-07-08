package IE683.comparator;

import IE683.model.Movie;
import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.Comparator;
import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.ComparatorLogger;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.SimilarityMeasure;

import java.io.Serial;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class MovieComparator<T> implements Comparator<Movie, Attribute> {

    @Serial
    private static final long serialVersionUID = 1L;
    private final SimilarityMeasure<T> sim;
    private final Method getValueMethod;
    private ComparatorLogger comparisonLog;

    public MovieComparator(SimilarityMeasure<T> sim, String attributeName) throws NoSuchMethodException {
        this.getValueMethod = Movie.class.getMethod("get" + attributeName);
        this.sim = sim;
    }


    public T getValue(Movie record) throws InvocationTargetException, IllegalAccessException {
        //noinspection unchecked
        return (T) getValueMethod.invoke(record);
    }

    @Override
    public double compare(Movie record1, Movie record2,
                          Correspondence<Attribute, Matchable> schemaCorrespondences) {
        T value1 = null;
        T value2 = null;
        try {
            value1 = getValue(record1);
            value2 = getValue(record2);
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        Double similarity = value1 == null || value2 == null ? (double) 0 : sim.calculate(value1, value2);

        if (this.comparisonLog != null) {
            this.comparisonLog.setComparatorName(getClass().getName());

            this.comparisonLog.setRecord1Value(value1 == null ? "" : value1.toString());
            this.comparisonLog.setRecord2Value(value2 == null ? "" : value2.toString());

            this.comparisonLog.setSimilarity(similarity.toString());
        }
        return similarity;
    }

    @Override
    public ComparatorLogger getComparisonLog() {
        return this.comparisonLog;
    }

    @Override
    public void setComparisonLog(ComparatorLogger comparatorLog) {
        this.comparisonLog = comparatorLog;
    }

    @Override
    public boolean hasMissingValue(Movie record1, Movie record2, Correspondence<Attribute, Matchable> schemaCorrespondence) {
        try {
            return getValue(record1) == null || getValue(record2) == null;
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            return true;
        }
    }
}

