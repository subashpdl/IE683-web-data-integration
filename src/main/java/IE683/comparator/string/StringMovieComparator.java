package IE683.comparator.string;

import IE683.comparator.MovieComparator;
import de.uni_mannheim.informatik.dws.winter.similarity.string.LevenshteinSimilarity;

public abstract class StringMovieComparator extends MovieComparator<String> {
    StringMovieComparator(String attributeName) throws NoSuchMethodException {
        super(new LowerLevenSimilarity(), attributeName);
    }
}

