package IE683.identityresolution;

import IE683.blocker.Title2Blocker;
import IE683.comparator.list.*;
import IE683.comparator.numeric.ImdbScoreAbsoluteComparator;
import IE683.comparator.numeric.Year2Comparator;
import IE683.comparator.string.TitleComparator;
import IE683.model.Movie;
import IE683.utils.Utils;
import de.uni_mannheim.informatik.dws.winter.matching.MatchingEngine;
import de.uni_mannheim.informatik.dws.winter.matching.MatchingEvaluator;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.NoBlocker;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.StandardRecordBlocker;
import de.uni_mannheim.informatik.dws.winter.matching.rules.LinearCombinationMatchingRule;
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

public class NetflixIMDbLinearMatcher {
    private static final Logger logger = WinterLogManager.activateLogger("default");

    public static void main( String[] args ) throws Exception
    {
        // loading data
        logger.info("*\tLoading datasets\t*");
        HashedDataSet<Movie, Attribute> dataImdb = Utils.loadDataset("imdb");
        HashedDataSet<Movie, Attribute> dataNetflix = Utils.loadDataset("netflix");

        // load the gold standard (test set)
        logger.info("*\tLoading gold standard\t*");
        MatchingGoldStandard gsTest = new MatchingGoldStandard();
        gsTest.loadFromCSVFile(new File(
                "data/Movies/gold_standards/gs_netflix_imdb.csv"));

        // create a matching rule
        LinearCombinationMatchingRule<Movie, Attribute> matchingRule = new LinearCombinationMatchingRule<>(
                0.7);
        matchingRule.activateDebugReport("data/Movies/output/netflix_imdb_debugResultsMatchingRule.csv", 5000, gsTest);

        // add comparators
        matchingRule.addComparator(new TitleComparator(), 0.4);
        //matchingRule.addComparator(new TitleJaccardLevenComparator(), 0.4);

        matchingRule.addComparator(new Year2Comparator(), 0.15);
        //matchingRule.addComparator(new Year5Comparator(), 0.4);

        matchingRule.addComparator(new ImdbScoreAbsoluteComparator(), 0.15);
        matchingRule.addComparator(new DirectorsOverlapComparator(), 0.15 );
        //matchingRule.addComparator(new WritersOverlapComparator(), 0.15 );
        matchingRule.addComparator(new ActorsOverlapComparator(), 0.15 );
        //matchingRule.addComparator(new GenresOverlapComparator(), 0.025 );
        //matchingRule.addComparator(new LanguageOverlapComparator(), 0.025 );

        // create a blocker (blocking strategy)
        //StandardRecordBlocker<Movie, Attribute> blocker = new StandardRecordBlocker<Movie, Attribute>(new Title2Blocker());
		NoBlocker<Movie, Attribute> blocker = new NoBlocker<>();
        //SortedNeighbourhoodBlocker<Movie, Attribute, Attribute> blocker = new SortedNeighbourhoodBlocker<>(new TitleBlocker(), 100);
        //blocker.setMeasureBlockSizes(true);
        //Write debug results to file:
        //blocker.collectBlockSizeData("data/Movies/output/netflix_imdb_debugResultsBlocking.csv", 100);

        // Initialize Matching Engine
        MatchingEngine<Movie, Attribute> engine = new MatchingEngine<>();

        // Execute the matching
        logger.info("*\tRunning identity resolution\t*");
        Processable<Correspondence<Movie, Attribute>> correspondences = engine.runIdentityResolution(
                dataNetflix, dataImdb, null, matchingRule,
                blocker);

        // Create a top-1 global matching
//		  correspondences = engine.getTopKInstanceCorrespondences(correspondences, 1, 0.0);

//		 Alternative: Create a maximum-weight, bipartite matching
//		 MaximumBipartiteMatchingAlgorithm<Movie,Attribute> maxWeight = new MaximumBipartiteMatchingAlgorithm<>(correspondences);
//		 maxWeight.run();
//		 correspondences = maxWeight.getResult();

        // write the correspondences to the output file
        new CSVCorrespondenceFormatter().writeCSV(new File("data/Movies/output/netflix_2_imdb_correspondences.csv"), correspondences);

        logger.info("*\tEvaluating result\t*");
        // evaluate your result
        MatchingEvaluator<Movie, Attribute> evaluator = new MatchingEvaluator<>();
        Performance perfTest = evaluator.evaluateMatching(correspondences,
                gsTest);

        // print the evaluation result
        logger.info("Netflix <-> Imdb");
        logger.info(String.format(
                "Precision: %.4f",perfTest.getPrecision()));
        logger.info(String.format(
                "Recall: %.4f",	perfTest.getRecall()));
        logger.info(String.format(
                "F1: %.4f",perfTest.getF1()));
    }
}
