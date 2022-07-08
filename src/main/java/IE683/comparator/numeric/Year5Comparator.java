package IE683.comparator.numeric;

import IE683.comparator.MovieComparator;
import de.uni_mannheim.informatik.dws.winter.similarity.numeric.AbsoluteDifferenceSimilarity;

public class Year5Comparator extends MovieComparator<Double> {
    public Year5Comparator() throws NoSuchMethodException {
        super(new AbsoluteDifferenceSimilarity(5), "ReleaseYear");
    }
}

