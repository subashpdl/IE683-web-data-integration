package IE683.identityresolution;

import IE683.blocker.TitleBlocker;
import IE683.blocker.ReleaseYearBlocker;
import IE683.blocker.Title2Blocker;
import IE683.comparator.string.TitleComparator;
import IE683.comparator.numeric.Year2Comparator;
import IE683.comparator.list.DirectorsOverlapComparator;
import IE683.comparator.list.GenresComparator;
import IE683.comparator.list.GenresOverlapComparator;
import IE683.comparator.list.LanguageOverlapComparator;
import IE683.model.Movie;
import IE683.utils.Utils;
import de.uni_mannheim.informatik.dws.winter.matching.MatchingEngine;
import de.uni_mannheim.informatik.dws.winter.matching.MatchingEvaluator;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.StandardRecordBlocker;
import de.uni_mannheim.informatik.dws.winter.matching.rules.LinearCombinationMatchingRule;
import de.uni_mannheim.informatik.dws.winter.matching.rules.LinearCombinationMatchingRuleWithPenalty;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.HashedDataSet;
import de.uni_mannheim.informatik.dws.winter.model.MatchingGoldStandard;
import de.uni_mannheim.informatik.dws.winter.model.Performance;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.CSVCorrespondenceFormatter;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import de.uni_mannheim.informatik.dws.winter.utils.WinterLogManager;
import org.slf4j.Logger;

import java.io.File;


public class NetflixStreamingLinearMatcher {

    private static final Logger logger = WinterLogManager.activateLogger("default");

    public static void main(String[] args) throws Exception {

        logger.info("\tLoading datasets\t");
        HashedDataSet<Movie, Attribute> dataStreaming = Utils.loadDataset("streaming");
        HashedDataSet<Movie, Attribute> dataNetflix = Utils.loadDataset("netflix");
        
        // load the gold standard (test set)
        logger.info("*\tLoading gold standard\t*");
        MatchingGoldStandard gsTest = new MatchingGoldStandard();
        gsTest.loadFromCSVFile(new File(
                "data/Movies/gold_standards/gold_standard_netflix_streaming.csv"));
        
        // Load the Linear Combination Matcher
        /*
        logger.info("\tLoading the matching rule class\t");
        LinearCombinationMatchingRule<Movie, Attribute> matchingRule = new LinearCombinationMatchingRule<>(
                0.825);
        // add comparators
        matchingRule.addComparator(new TitleComparator(), 0.5);
        matchingRule.addComparator(new Year2Comparator(), 0.175);
        matchingRule.addComparator(new GenresOverlapComparator(), 0.225);
        matchingRule.addComparator(new DirectorsOverlapComparator(), 0.05);
        matchingRule.addComparator(new LanguageOverlapComparator(), 0.05);
        */
        /*
        // Load the Linear Combination Matcher With Penalty
        logger.info("\tLoading the matching rule class\t");
        LinearCombinationMatchingRuleWithPenalty<Movie, Attribute> matchingRule = new LinearCombinationMatchingRuleWithPenalty<>(
                0.795);
        // add comparators
        matchingRule.addComparator(new TitleComparator(), 0.5,0.0);
        matchingRule.addComparator(new Year2Comparator(), 0.175, 0.175);
        matchingRule.addComparator(new GenresOverlapComparator(), 0.225,0.225);
        matchingRule.addComparator(new DirectorsOverlapComparator(), 0.05, 0.05);
        matchingRule.addComparator(new LanguageOverlapComparator(), 0.05,  0.05);
		*/
        
        // Load the Weka Model For Prototyping
        String options[] = new String[1];
        options[0] = "";
        String logisticRegression = "SimpleLogistic";
        
        logger.info("\tLoading the matching rule class\t");
        LinearCombinationMatchingRuleWithPenalty<Movie, Attribute> matchingRule = new LinearCombinationMatchingRuleWithPenalty<>(
                0.795);
        // add comparators
        matchingRule.addComparator(new TitleComparator(), 0.5,0.0);
        matchingRule.addComparator(new Year2Comparator(), 0.175, 0.175);
        matchingRule.addComparator(new GenresOverlapComparator(), 0.225,0.225);
        matchingRule.addComparator(new DirectorsOverlapComparator(), 0.05, 0.05);
        matchingRule.addComparator(new LanguageOverlapComparator(), 0.05,  0.05);
        

        // Adding a blocking strategy
        logger.info("\tLoading the blocker\t");
        // StandardRecordBlocker<Movie, Attribute> blocker = new StandardRecordBlocker<Movie, Attribute>(new TitleBlocker());
        StandardRecordBlocker<Movie, Attribute> blocker = new StandardRecordBlocker<Movie, Attribute>(new Title2Blocker());
        // StandardRecordBlocker<Movie, Attribute> blocker = new StandardRecordBlocker<Movie, Attribute>(new ReleaseYearBlocker());
        blocker.collectBlockSizeData("data/output/blocking_results_debugger.csv", 1000);

        // Initialize Matching Engine
        logger.info("\tLoading the matching engine\t");
        MatchingEngine<Movie, Attribute> engine = new MatchingEngine<>();

        // Execute the matching
        logger.info("\tRunning identity resolution\t");
        Processable<Correspondence<Movie, Attribute>> correspondences = engine.runIdentityResolution(
                dataStreaming, dataNetflix, null, matchingRule,
                blocker);

        // Create a top-16 global matching
        correspondences = engine.getTopKInstanceCorrespondences(correspondences, 1, 0.0);

        // write the correspondences to the output file
        // new CSVCorrespondenceFormatter().writeCSV(new File("data/output/gold_standard_base.csv"), correspondences);
        new CSVCorrespondenceFormatter().writeCSV(new File("data/Movies/output/correspondences_netflix_streaming.csv"), correspondences);
        
        
        logger.info("*\tEvaluating result\t*");
        // evaluate your result
        MatchingEvaluator<Movie, Attribute> evaluator = new MatchingEvaluator<>();
        Performance perfTest = evaluator.evaluateMatching(correspondences,
                gsTest);

        // print the evaluation result
        logger.info("Streaming <-> Netflix");
        logger.info(String.format(
                "Precision: %.4f",perfTest.getPrecision()));
        logger.info(String.format(
                "Recall: %.4f",	perfTest.getRecall()));
        logger.info(String.format(
                "F1: %.4f",perfTest.getF1()));   
    }
}