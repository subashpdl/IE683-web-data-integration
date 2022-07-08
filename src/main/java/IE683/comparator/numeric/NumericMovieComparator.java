package IE683.comparator.numeric;

import IE683.comparator.MovieComparator;
import de.uni_mannheim.informatik.dws.winter.similarity.numeric.DeviationSimilarity;

public abstract class NumericMovieComparator extends MovieComparator<Double> {
    NumericMovieComparator(String attributeName) throws NoSuchMethodException {
        super(new DeviationSimilarity(), attributeName);
    }
}

