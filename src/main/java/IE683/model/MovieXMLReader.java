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
package IE683.model;

import de.uni_mannheim.informatik.dws.winter.model.FusibleFactory;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroup;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.XMLMatchableReader;
import org.w3c.dom.Node;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

/**
 * A {@link XMLMatchableReader} for {@link Movie}s.
 *
 * @author Oliver Lehmberg (oli@dwslab.de)
 */
public class MovieXMLReader extends XMLMatchableReader<Movie, Attribute> implements FusibleFactory<Movie, Attribute> {

    @Override
    public Movie createModelFromElement(Node node, String provenanceInfo) {
        String id = getValueFromChildElement(node, "id");

        // create the object with id and provenance information
        Movie Movie = new Movie(id, provenanceInfo);

        // fill the attributes
        Movie.setId(getValueFromChildElement(node, "id"));
        Movie.setTitle(getValueFromChildElement(node, "title"));
        Movie.setSource(getValueFromChildElement(node, "source"));
        Movie.setOriginal_title(getValueFromChildElement(node, "original_title"));
        Movie.setReviews_from_critics(getValueFromChildElement(node, "reviews_from_critics"));
        Movie.setReviews_from_users(getValueFromChildElement(node, "review_from_users"));
        Movie.setTitle_id(getValueFromChildElement(node, "title_id"));
        Movie.setImage(getValueFromChildElement(node, "image"));
        Movie.setImdb_link(getValueFromChildElement(node, "imdb_link"));
        Movie.setNetflix_link(getValueFromChildElement(node, "netflix_link"));
        Movie.setPoster(getValueFromChildElement(node, "poster"));
        Movie.setSeries_or_movie(getValueFromChildElement(node, "series_or_Netflix"));
        Movie.setDisney_flag(getValueFromChildElement(node, "disney_flag"));
        Movie.setHulu_flag(getValueFromChildElement(node, "hulu_flag"));
        Movie.setNetflix_flag(getValueFromChildElement(node, "netflix_flag"));
        Movie.setPrime_video_flag(getValueFromChildElement(node, "prime_video_flag"));
        Movie.setImdb_votes(getDoubleFromChildElement(node, "imdb_votes"));
        Movie.setImdb_score(getDoubleFromChildElement(node, "imdb_score"));
        Movie.setAvg_vote(getDoubleFromChildElement(node, "avg_vote"));
        Movie.setBudget(getDoubleFromChildElement(node, "budget"));
        Movie.setHidden_gem_score(getDoubleFromChildElement(node, "hidden_gem_score"));
        Movie.setDuration(getDoubleFromChildElement(node, "duration"));
        Movie.setRelease_year(getDoubleFromChildElement(node, "release_year"));
        Movie.setNetflix_release_date(getDateFromChildElement(node, "netflix_release_date"));
        Movie.setProduction_companies(getListFromChildElement(node, "production_companies"));
        Movie.setGenres(getListFromChildElement(node, "genres"));
        Movie.setActors(getListFromChildElement(node, "actors"));
        Movie.setDirectors(getListFromChildElement(node, "directors"));
        Movie.setLanguages(getListFromChildElement(node, "languages"));
        Movie.setWriters(getListFromChildElement(node, "writers"));
        Movie.setCountries(getListFromChildElement(node, "countries"));
        Movie.setCountries_availability(getListFromChildElement(node, "countries_availability"));
        Movie.setTags(getListFromChildElement(node, "tags"));

        return Movie;
    }

    @Override
    protected String getValueFromChildElement(Node node, String childName) {
        String value = super.getValueFromChildElement(node, childName);
        return value == null || value.equals("nan") ? null : value;
    }

    protected Double getDoubleFromChildElement(Node node, String childName) {
        String value = getValueFromChildElement(node, childName);
        return value == null ? null : Double.parseDouble(value);
    }

    protected LocalDateTime getDateFromChildElement(Node node, String childName) {
        String value = getValueFromChildElement(node, childName);
        return value == null ? null : LocalDateTime.parse("1894-10-09 00:00:00",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    public Movie createInstanceForFusion(RecordGroup<Movie, Attribute> cluster) {
        return new Movie(cluster.getRecords().stream().map(Movie::getIdentifier).sorted()
                .collect(Collectors.joining("+")), "fused");
    }
}
