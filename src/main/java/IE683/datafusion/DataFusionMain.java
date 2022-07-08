package IE683.datafusion;

import IE683.constants.Constants;
import IE683.evaluationRule.list.*;
import IE683.evaluationRule.numeric.*;
import IE683.evaluationRule.string.TitleEvaluationRule;
import IE683.fuser.TitleFuser;
import IE683.fuser.list.*;
import IE683.fuser.numeric.YearFuser;
import IE683.fuser.numeric.average.*;
import IE683.model.Movie;
import IE683.model.MovieCSVFormatter;
import IE683.model.MovieXMLFormatter;
import IE683.model.MovieXMLReader;
import IE683.utils.Utils;
import de.uni_mannheim.informatik.dws.winter.datafusion.CorrespondenceSet;
import de.uni_mannheim.informatik.dws.winter.datafusion.DataFusionEngine;
import de.uni_mannheim.informatik.dws.winter.datafusion.DataFusionEvaluator;
import de.uni_mannheim.informatik.dws.winter.datafusion.DataFusionStrategy;
import de.uni_mannheim.informatik.dws.winter.model.DataSet;
import de.uni_mannheim.informatik.dws.winter.model.FusibleDataSet;
import de.uni_mannheim.informatik.dws.winter.model.FusibleHashedDataSet;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroupFactory;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.utils.WinterLogManager;
import org.slf4j.Logger;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;

public class DataFusionMain {

    private static final Logger logger = WinterLogManager.activateLogger("default");

    public static void main(String[] args) throws Exception {
        // Load the Data into FusibleDataSet
        logger.info("*\tLoading datasets\t*");

        FusibleDataSet<Movie, Attribute> ds1 = Utils.loadFusibleDataset("netflix");
        FusibleDataSet<Movie, Attribute> ds2 = Utils.loadFusibleDataset("streaming");
        FusibleDataSet<Movie, Attribute> ds3 = Utils.loadFusibleDataset("imdb");

        // Maintain Provenance
        // Scores (e.g. from rating)
        ds1.setScore(3.0);
        ds2.setScore(1.0);
        ds3.setScore(2.0);

        // Date (e.g. last update)
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM-dd")
                .parseDefaulting(ChronoField.CLOCK_HOUR_OF_DAY, 0)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                .toFormatter(Locale.ENGLISH);

        ds1.setDate(LocalDateTime.parse("2021-04-27", formatter));
        ds2.setDate(LocalDateTime.parse("2020-05-23", formatter));
		ds3.setDate(LocalDateTime.parse("2020-09-15", formatter));

        // load correspondences
        logger.info("*\tLoading correspondences\t*");
        CorrespondenceSet<Movie, Attribute> correspondences = new CorrespondenceSet<>();
        correspondences.loadCorrespondences(Constants.OUTPUT_DIR.resolve("corr_net_strm_tygdl_t2.csv")
                .toFile(), ds2, ds1);
        correspondences.loadCorrespondences(Constants.OUTPUT_DIR.resolve("corr_net_imdb.csv")
                .toFile(), ds3, ds1);

        // write group size distribution
        logger.info("Group Size Distribution Results:");
        correspondences.printGroupSizeDistribution();
        
		// logger.info("Netflix Data Set Density Report:");
		// ds1.printDataSetDensityReport();
		
		// logger.info("Streaming Data Set Density Report:");
		// ds2.printDataSetDensityReport();
		
		// logger.info("IMDb Data Set Density Report:");
		// ds3.printDataSetDensityReport();
        
        /*
         * [INFO ] 2021-12-09 00:43:42.768 [de.uni_mannheim.informatik.dws.winter.datafusion.CorrespondenceSet] - Group Size Distribution of 3107 groups:
         * [INFO ] 2021-12-09 00:43:42.768 [de.uni_mannheim.informatik.dws.winter.datafusion.CorrespondenceSet] - 	Group Size 	| Frequency 
         * [INFO ] 2021-12-09 00:43:42.769 [de.uni_mannheim.informatik.dws.winter.datafusion.CorrespondenceSet] - 	————————————————————————————
         * [INFO ] 2021-12-09 00:43:42.792 [de.uni_mannheim.informatik.dws.winter.datafusion.CorrespondenceSet] - 		2	| 	3087
         * [INFO ] 2021-12-09 00:43:42.793 [de.uni_mannheim.informatik.dws.winter.datafusion.CorrespondenceSet] - 		3	| 	20
         */
        
        // load the gold standard
        logger.info("*\tEvaluating results\t*");
        DataSet<Movie, Attribute> gs = new FusibleHashedDataSet<>();
        new MovieXMLReader().loadFromXML(new File("data/Movies/gold_standards/gs_data_fusion.xml"), "/movies" +
				"/movie",	gs);

        for (Movie m : gs.get()) {
            logger.info(String.format("gs: %s", m.getIdentifier()));
        }

        // define the fusion strategy
        DataFusionStrategy<Movie, Attribute> strategy = new DataFusionStrategy<>(new MovieXMLReader());
        // write debug results to file
        strategy.activateDebugReport("data/output/datafusion_results_debugger.csv", -1, gs);


        strategy.addAttributeFuser(Movie.ACTORS, new ActorsFuser(), new ActorsEvaluationRule(0.65));
        strategy.addAttributeFuser(Movie.DIRECTORS, new DirectorsFuser(), new DirectorsEvaluationRule(0.65));
        strategy.addAttributeFuser(Movie.WRITERS, new WritersFuser(), new WritersEvaluationRule(0.65));
        strategy.addAttributeFuser(Movie.TITLE, new TitleFuser(), new TitleEvaluationRule());
        strategy.addAttributeFuser(Movie.COUNTRIES, new CountriesFuser(), new CountriesEvaluationRule(0.65));
        strategy.addAttributeFuser(Movie.LANGUAGES, new LanguagesFuser(), new LanguagesEvaluationRule(0.65));
        strategy.addAttributeFuser(Movie.GENRES, new GenresFuser(), new GenresEvaluationRule(0.65));
        strategy.addAttributeFuser(Movie.PRODUCTION_COMPANIES, new ProductionCompaniesFuser(), new ProductionCompaniesEvaluationRule(0.75));
        strategy.addAttributeFuser(Movie.AVG_VOTE, new AvgVoteFuser(), new AvgVoteEvaluationRule());
        strategy.addAttributeFuser(Movie.BUDGET, new BudgetFuser(), new BudgetEvaluationRule());
        strategy.addAttributeFuser(Movie.DURATION, new DurationFuser(), new DurationEvaluationRule());
        strategy.addAttributeFuser(Movie.IMDB_SCORE, new ImdbScoreFuser(), new ImdbScoreEvaluationRule());
        strategy.addAttributeFuser(Movie.IMDB_VOTES, new ImdbVotesFuser(), new ImdbVotesEvaluationRule());
        strategy.addAttributeFuser(Movie.YEAR, new YearFuser(), new YearEvaluationRule());

        // create the fusion engine
        DataFusionEngine<Movie, Attribute> engine = new DataFusionEngine<>(strategy);
        // print consistency report
        logger.info("Fused Data Consistency Report:");
        engine.printClusterConsistencyReport(correspondences, null);
		
        // write record groups sorted by consistency
        // engine.writeRecordGroupsByConsistency(new File("data/output/recordGroupConsistencies.csv"), correspondences, null);
        // run the fusion
        logger.info("*\tRunning data fusion\t*");
        FusibleDataSet<Movie, Attribute> fusedDataSet = engine.run(correspondences, null);
		logger.info("Fused Data Set Density Report:");
		fusedDataSet.printDataSetDensityReport();
        // evaluate
        DataFusionEvaluator<Movie, Attribute> evaluator = new DataFusionEvaluator<>(strategy, new RecordGroupFactory<Movie, Attribute>());
        double accuracy = evaluator.evaluate(fusedDataSet, gs, null);
        logger.info(String.format("*\tAccuracy: %.2f", accuracy));
        // write the result to XML file        
        // new MovieXMLFormatter().writeXML(new File("data/output/fused_data.xml"), fusedDataSet);        
    }
}
