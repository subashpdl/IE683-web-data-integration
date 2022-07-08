/*
 * Copyright (c) 2017 Data and Web Science Group, University of Mannheim, Germany (http://dws.informatik.uni-mannheim.de/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package winterMoviesExample;

import java.io.File;

import org.slf4j.Logger;

import de.uni_mannheim.informatik.dws.winter.matching.MatchingEngine;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.StandardRecordBlocker;
import de.uni_mannheim.informatik.dws.winter.matching.rules.LinearCombinationMatchingRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.HashedDataSet;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.CSVCorrespondenceFormatter;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import winterMoviesExample.identityresolution.MovieBlockingKeyByDecadeGenerator;
import winterMoviesExample.identityresolution.MovieBlockingKeyByTitleGenerator;
import winterMoviesExample.identityresolution.MovieBlockingKeyByYearGenerator;
import winterMoviesExample.identityresolution.MovieDateComparator10Years;
import winterMoviesExample.identityresolution.MovieDateComparator2Years;
import winterMoviesExample.identityresolution.MovieDirectorComparatorLevenshtein;
import winterMoviesExample.identityresolution.MovieTitleComparatorEqual;
import winterMoviesExample.identityresolution.MovieTitleComparatorJaccard;
import winterMoviesExample.identityresolution.MovieTitleComparatorLevenshtein;
import winterMoviesExample.model.Movie;
import winterMoviesExample.model.MovieXMLReader;
import de.uni_mannheim.informatik.dws.winter.utils.WinterLogManager;

/**
 * Class containing the standard setup to perform a identity resolution task,
 * reading input data from the movie usecase.
 * 
 * @author Oliver Lehmberg (oli@dwslab.de)
 * @author Robert Meusel (robert@dwslab.de)
 * 
 */
public class Movies_Tutorial_IdentityResolution_Step01 {
	
	/*
	 * Logging Options:
	 * 		default: 	level INFO	- console
	 * 		trace:		level TRACE     - console
	 * 		infoFile:	level INFO	- console/file
	 * 		traceFile:	level TRACE	- console/file
	 *  
	 * To set the log level to trace and write the log to winter.log and console, 
	 * activate the "traceFile" logger as follows:
	 *     private static final Logger logger = WinterLogManager.activateLogger("traceFile");
	 *
	 */

	private static final Logger logger = WinterLogManager.activateLogger("default");

	public static void main(String[] args) throws Exception {
		// loading data
		HashedDataSet<Movie, Attribute> dataAcademyAwards = new HashedDataSet<>();
		new MovieXMLReader().loadFromXML(new File("usecase/movie/input/academy_awards.xml"), "/movies/movie", dataAcademyAwards);
		HashedDataSet<Movie, Attribute> dataActors = new HashedDataSet<>();
		new MovieXMLReader().loadFromXML(new File("usecase/movie/input/actors.xml"), "/movies/movie", dataActors);
				
		// create a matching rule
		LinearCombinationMatchingRule<Movie, Attribute> matchingRule = new LinearCombinationMatchingRule<>(
				0.7);
		
		// add comparators
		matchingRule.addComparator(new MovieDateComparator2Years(), 0.5);
		matchingRule.addComparator(new MovieTitleComparatorJaccard(), 0.5);
		
		// Initialize Matching Engine
		MatchingEngine<Movie, Attribute> engine = new MatchingEngine<>();

		// create a blocker (blocking strategy)
		StandardRecordBlocker<Movie, Attribute> blocker = new StandardRecordBlocker<Movie, Attribute>(new MovieBlockingKeyByTitleGenerator());

		// Execute the matching
		Processable<Correspondence<Movie, Attribute>> correspondences = engine.runIdentityResolution(
				dataAcademyAwards, dataActors, null, matchingRule, blocker);
		
		// write the correspondences to the output file
		new CSVCorrespondenceFormatter().writeCSV(new File("usecase/movie/output/academy_awards_2_actors_correspondences.csv"), correspondences);
	}



}
