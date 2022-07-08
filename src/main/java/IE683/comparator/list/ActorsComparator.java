package IE683.comparator.list;

public class ActorsComparator extends ListMovieComparator {
    public ActorsComparator(Double innerSimilarityThreshold) throws NoSuchMethodException {
        super("Actors", innerSimilarityThreshold);
    }
}

