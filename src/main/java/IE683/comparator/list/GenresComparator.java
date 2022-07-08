package IE683.comparator.list;

public class GenresComparator extends ListMovieComparator {
    public GenresComparator(Double innerSimilarityThreshold) throws NoSuchMethodException {
        super("Genres", innerSimilarityThreshold);
    }
}

