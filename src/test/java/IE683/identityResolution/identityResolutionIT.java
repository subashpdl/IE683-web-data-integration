package IE683.identityResolution;

import IE683.blocker.ReleaseYearBlocker;
import IE683.blocker.TitleBlocker;
import IE683.comparator.numeric.AvgVoteComparator;
import IE683.comparator.numeric.DurationComparator;
import IE683.model.Movie;
import IE683.utils.Utils;
import de.uni_mannheim.informatik.dws.winter.matching.MatchingEngine;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.StandardRecordBlocker;
import de.uni_mannheim.informatik.dws.winter.matching.rules.LinearCombinationMatchingRuleWithPenalty;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.HashedDataSet;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import org.junit.jupiter.api.Test;

public class identityResolutionIT {
    @Test
    void testTitleBlocker() throws Exception {
        LinearCombinationMatchingRuleWithPenalty<Movie, Attribute> rule =
                new LinearCombinationMatchingRuleWithPenalty<>(0.5);
        rule.addComparator(new DurationComparator(), 1, 0);
        StandardRecordBlocker<Movie, Attribute> blocker = new StandardRecordBlocker<>(new TitleBlocker());

        resolveIdentities(rule, blocker, "netflix", "streaming");
    }

    @Test
    void testReleaseYearBlocker() throws Exception {
        LinearCombinationMatchingRuleWithPenalty<Movie, Attribute> rule =
                new LinearCombinationMatchingRuleWithPenalty<>(0.5);
        rule.addComparator(new AvgVoteComparator(), 1, 0);
        StandardRecordBlocker<Movie, Attribute> blocker = new StandardRecordBlocker<>(new ReleaseYearBlocker());

        resolveIdentities(rule, blocker, "imdb", "netflix");
    }

    private void resolveIdentities(
            LinearCombinationMatchingRuleWithPenalty<Movie, Attribute> rule,
            StandardRecordBlocker<Movie, Attribute> blocker, String dataset1, String dataset2) {
        MatchingEngine<Movie, Attribute> engine = new MatchingEngine<>();

        HashedDataSet<Movie, Attribute> loadedDataset1 = Utils.loadDataset(dataset1);
        HashedDataSet<Movie, Attribute> loadedDataset2 = Utils.loadDataset(dataset2);
        Processable<Correspondence<Movie, Attribute>> correspondences =
                engine.runIdentityResolution(loadedDataset1, loadedDataset2, null, rule, blocker);
    }
}
