package IE683.comparator.string;

import IE683.comparator.MovieComparator;
import de.uni_mannheim.informatik.dws.winter.similarity.string.LevenshteinSimilarity;
import de.uni_mannheim.informatik.dws.winter.similarity.string.GeneralisedStringJaccard;

public class TitleJaccardLevenComparator extends MovieComparator<String> {
    public TitleJaccardLevenComparator() throws NoSuchMethodException {
        super(new GeneralisedStringJaccard(new LowerLevenSimilarity(), 0.8, 0.8), "Title");
    }
}

