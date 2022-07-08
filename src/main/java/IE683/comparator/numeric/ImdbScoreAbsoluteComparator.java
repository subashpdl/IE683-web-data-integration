package IE683.comparator.numeric;
import IE683.comparator.MovieComparator;
import de.uni_mannheim.informatik.dws.winter.similarity.numeric.AbsoluteDifferenceSimilarity;

public class ImdbScoreAbsoluteComparator extends MovieComparator<Double> {
    public ImdbScoreAbsoluteComparator() throws NoSuchMethodException {
        super(new AbsoluteDifferenceSimilarity(1), "ImdbScore");
    }
}

