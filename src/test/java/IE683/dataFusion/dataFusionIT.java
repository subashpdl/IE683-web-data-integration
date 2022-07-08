package IE683.dataFusion;

import IE683.blocker.ReleaseYearBlocker;
import IE683.comparator.numeric.AvgVoteComparator;
import IE683.constants.Constants;
import IE683.evaluationRule.list.ActorsEvaluationRule;
import IE683.evaluationRule.string.TitleEvaluationRule;
import IE683.fuser.list.WritersFuser;
import IE683.fuser.TitleFuser;
import IE683.model.Movie;
import IE683.model.MovieXMLReader;
import IE683.utils.Utils;
import de.uni_mannheim.informatik.dws.winter.datafusion.CorrespondenceSet;
import de.uni_mannheim.informatik.dws.winter.datafusion.DataFusionEngine;
import de.uni_mannheim.informatik.dws.winter.datafusion.DataFusionStrategy;
import de.uni_mannheim.informatik.dws.winter.matching.MatchingEngine;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.StandardRecordBlocker;
import de.uni_mannheim.informatik.dws.winter.matching.rules.LinearCombinationMatchingRuleWithPenalty;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.FusibleDataSet;
import de.uni_mannheim.informatik.dws.winter.model.HashedDataSet;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import org.junit.jupiter.api.Test;

import static IE683.model.Movie.ACTORS;
import static IE683.model.Movie.TITLE;

public class dataFusionIT {
    @Test
    void testDataFusion() throws Exception {
        FusibleDataSet<Movie, Attribute> streamingData = Utils.loadFusibleDataset("streaming");
        FusibleDataSet<Movie, Attribute> netflixData = Utils.loadFusibleDataset("netflix");
        CorrespondenceSet<Movie, Attribute> correspondences = new CorrespondenceSet<>();
        correspondences.loadCorrespondences(Constants.OUTPUT_DIR.resolve("gold_standard_year_blocker_base.csv")
                .toFile(), streamingData, netflixData);
        DataFusionStrategy<Movie, Attribute> strategy = new DataFusionStrategy<>(new MovieXMLReader());
        strategy.addAttributeFuser(TITLE, new TitleFuser(), new TitleEvaluationRule());
        strategy.addAttributeFuser(ACTORS, new WritersFuser(), new ActorsEvaluationRule(0.7));

        DataFusionEngine<Movie, Attribute> engine = new DataFusionEngine<>(strategy);
        FusibleDataSet<Movie, Attribute> fusedDataSet = engine.run(correspondences, null);

        System.out.println("");
//        asdf.hasValue()


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
