package IE683.comparator;

import de.uni_mannheim.informatik.dws.winter.similarity.date.YearSimilarity;

import java.time.LocalDateTime;

public class ReleaseDateComparator extends MovieComparator<LocalDateTime> {
    public ReleaseDateComparator() throws NoSuchMethodException {
        super(new YearSimilarity(2), "ReleaseDate");
    }
}
