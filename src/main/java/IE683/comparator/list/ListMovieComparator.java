package IE683.comparator.list;

import IE683.comparator.MovieComparator;
import de.uni_mannheim.informatik.dws.winter.similarity.list.GeneralisedJaccard;
import de.uni_mannheim.informatik.dws.winter.similarity.string.LevenshteinSimilarity;

import java.util.Collection;

public abstract class ListMovieComparator extends MovieComparator<Collection<String>> {
    ListMovieComparator(String attributeName, Double innerSimilarityThreshold) throws NoSuchMethodException {
        super(new GeneralisedJaccard<>(new LevenshteinSimilarity(), innerSimilarityThreshold), attributeName);
    }
}
