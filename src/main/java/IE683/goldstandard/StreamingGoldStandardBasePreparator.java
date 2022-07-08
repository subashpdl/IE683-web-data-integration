package IE683.goldstandard;

import IE683.blocker.ReleaseYearBlocker;
import IE683.comparator.string.TitleComparator;
import IE683.model.Movie;
import IE683.utils.Utils;
import de.uni_mannheim.informatik.dws.winter.matching.MatchingEngine;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.StandardRecordBlocker;
import de.uni_mannheim.informatik.dws.winter.matching.rules.LinearCombinationMatchingRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.HashedDataSet;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.CSVCorrespondenceFormatter;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import de.uni_mannheim.informatik.dws.winter.utils.WinterLogManager;
import org.slf4j.Logger;

import java.io.File;


public class StreamingGoldStandardBasePreparator {

    private static final Logger logger = WinterLogManager.activateLogger("default");

    public static void main(String[] args) throws Exception {

        logger.info("\tLoading datasets\t");
        HashedDataSet<Movie, Attribute> dataStreaming = Utils.loadDataset("streaming");
        HashedDataSet<Movie, Attribute> dataNetflix = Utils.loadDataset("netflix");

        // Load the Linear Combination Matcher
        logger.info("\tLoading the matching rule class\t");
        LinearCombinationMatchingRule<Movie, Attribute> matchingRule = new LinearCombinationMatchingRule<>(
                0.45);
        // add comparators
        matchingRule.addComparator(new TitleComparator(), 1.0);

        // Adding a blocking strategy
        logger.info("\tLoading the blocker\t");
        // StandardRecordBlocker<Movie, Attribute> blocker = new StandardRecordBlocker<Movie, Attribute>(new TitleBlocker());
        StandardRecordBlocker<Movie, Attribute> blocker = new StandardRecordBlocker<Movie, Attribute>(new ReleaseYearBlocker());
        blocker.collectBlockSizeData("data/output/debugResultsBlockingReleaseYear.csv", 100);

        // Initialize Matching Engine
        logger.info("\tLoading the matching engine\t");
        MatchingEngine<Movie, Attribute> engine = new MatchingEngine<>();

        // Execute the matching
        logger.info("\tRunning identity resolution\t");
        Processable<Correspondence<Movie, Attribute>> correspondences = engine.runIdentityResolution(
                dataStreaming, dataNetflix, null, matchingRule,
                blocker);

        // Create a top-16 global matching
        correspondences = engine.getTopKInstanceCorrespondences(correspondences, 16, 0.0);

        // write the correspondences to the output file
        // new CSVCorrespondenceFormatter().writeCSV(new File("data/output/gold_standard_base.csv"), correspondences);
        new CSVCorrespondenceFormatter().writeCSV(new File("data/output/gold_standard_year_blocker_base.csv"), correspondences);
    }


}