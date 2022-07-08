package IE683.blocker;

import IE683.model.Movie;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.generators.RecordBlockingKeyGenerator;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.Pair;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.DataIterator;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;

public class ReleaseYearBlocker extends
        RecordBlockingKeyGenerator<Movie, Attribute> {
    @Override
    public void generateBlockingKeys(Movie record, Processable<Correspondence<Attribute, Matchable>> correspondences,
                                     DataIterator<Pair<String, Movie>> resultCollector) {
        Double releaseYear = record.getRelease_year();
        resultCollector.next(new Pair<>(releaseYear == null ? "" : releaseYear.toString(), record));
    }
}
