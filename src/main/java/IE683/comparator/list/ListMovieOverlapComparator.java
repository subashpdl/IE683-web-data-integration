package IE683.comparator.list;

import IE683.comparator.Movie2Comparator;
import de.uni_mannheim.informatik.dws.winter.similarity.list.OverlapSimilarity;
import java.util.List;

import java.util.Collection;

public abstract class ListMovieOverlapComparator extends Movie2Comparator<List<String>> {
    public ListMovieOverlapComparator(String attributeName) throws NoSuchMethodException {
        super(new OverlapSimilarity(), attributeName);
    }
}
