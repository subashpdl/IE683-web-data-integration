package IE683.comparator.numeric;

import IE683.comparator.MovieComparator;
import de.uni_mannheim.informatik.dws.winter.similarity.numeric.AbsoluteDifferenceSimilarity;

public class Year2Comparator extends MovieComparator<Double> {
    public Year2Comparator() throws NoSuchMethodException {
        super(new AbsoluteDifferenceSimilarity(2), "Release_year");
    }
}

