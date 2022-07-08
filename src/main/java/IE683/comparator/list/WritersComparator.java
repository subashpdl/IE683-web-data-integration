package IE683.comparator.list;

public class WritersComparator extends ListMovieComparator {
    public WritersComparator(Double innerSimilarityThreshold) throws NoSuchMethodException {
        super("Writers", innerSimilarityThreshold);
    }
}

