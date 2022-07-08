package IE683.comparator.list;

public class DirectorsComparator extends ListMovieComparator {
    public DirectorsComparator(Double innerSimilarityThreshold) throws NoSuchMethodException {
        super("Directors", innerSimilarityThreshold);
    }
}

