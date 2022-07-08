package IE683.comparator.list;

public class CountriesComparator extends ListMovieComparator {
    public CountriesComparator(Double innerSimilarityThreshold) throws NoSuchMethodException {
        super("Countries", innerSimilarityThreshold);
    }
}

