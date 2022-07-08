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

import de.uni_mannheim.informatik.dws.winter.model.AbstractRecord;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import org.apache.commons.lang3.StringUtils;

import java.io.Serial;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.*;

/**
 * A {@link AbstractRecord} representing a movie.
 *
 * @author Oliver Lehmberg (oli@dwslab.de)
 */
public class Movie extends AbstractRecord<Attribute> implements Serializable {

	public static final Attribute SOURCE = new Attribute("source");
    public static final Attribute ACTORS = new Attribute("actors");
    public static final Attribute COUNTRIES = new Attribute("countries");
    public static final Attribute DIRECTORS = new Attribute("directors");
    public static final Attribute GENRES = new Attribute("genres");
    public static final Attribute LANGUAGES = new Attribute("languages");
    public static final Attribute PRODUCTION_COMPANIES = new Attribute("production_companies");
    public static final Attribute WRITERS = new Attribute("writers");
    public static final Attribute AVG_VOTE = new Attribute("avg_vote");
    public static final Attribute BUDGET = new Attribute("budget");
    public static final Attribute DURATION = new Attribute("duration");
    public static final Attribute IMDB_SCORE = new Attribute("imdb_score");
    public static final Attribute IMDB_VOTES = new Attribute("imdb_votes");
    public static final Attribute YEAR = new Attribute("release_year");
    public static final Attribute TITLE = new Attribute("title");
    
    /*
     <movie>
        <id>imdb_1</id>
        <source>imdb</source>
        <genres>
            <genre>Romance</genre>
        </genres>
        <title>nan</title>
        <actors>
            <actor_names>Blanche Bayliss</actor_names>
            <actor_names>William Courtenay</actor_names>
            <actor_names>Chauncey Depew</actor_names>
        </actors>
        <release_date>1894-10-09 00:00:00</release_date>
        <directors>
            <director>Alexander Black</director>
        </directors>
        <duration>nan</duration>
        <languages>
            <language>None</language>
        </languages>
        <imdb_votes>nan</imdb_votes>
        <production_companies>
            <production_company>Alexander Black Photoplays</production_company>
        </production_companies>
        <imdb_score>nan</imdb_score>
        <writers>
            <writer>Alexander Black</writer>
        </writers>
        <avg_vote>nan</avg_vote>
        <countries>
            <country>USA</country>
        </countries>
        <year>nan</year>
        <budget>nan</budget>
        <original_title>nan</original_title>
        <reviews_from_critics>nan</reviews_from_critics>
        <reviews_from_users>nan</reviews_from_users>
        <title_id>nan</title_id>
        <country_availability>nan</country_availability>
        <hidden_gem_score>nan</hidden_gem_score>
        <image>nan</image>
        <imdb_link>nan</imdb_link>
        <netflix_link>nan</netflix_link>
        <netflix_release_date>nan</netflix_release_date>
        <poster>nan</poster>
        <series_or_movie>nan</series_or_movie>
        <tags>nan</tags>
        <disney_flag>nan</disney_flag>
        <hulu_flag>nan</hulu_flag>
        <netflix_flag>nan</netflix_flag>
        <prime_video_flag>nan</prime_video_flag>
    </movie>

     */
    @Serial
    private static final long serialVersionUID = 1L;
    protected String id;
    private String source;
    private String title;
    private Double release_year;
    private Double duration;
    private Double imdb_votes;
    private Double imdb_score;
    private Double avg_vote;
    private Double budget;
    private String original_title;
    private String reviews_from_critics;
    private String reviews_from_users;
    private String title_id;
    private Double hidden_gem_score;
    private String image;
    private String imdb_link;
    private String netflix_link;
    private LocalDateTime netflix_release_date;
    private String poster;
    private String series_or_movie;
    private String disney_flag;
    private String hulu_flag;
    private String netflix_flag;
    private String prime_video_flag;
    private List<String> genres;
    private List<String> actors;
    private List<String> directors;
    private List<String> languages;
    private List<String> production_companies;
    private List<String> writers;
    private List<String> countries;
    private List<String> countries_availability;
    private List<String> tags;
    private Map<Attribute, Collection<String>> provenance = new HashMap<>();
    private Collection<String> recordProvenance;

    public Movie(String identifier, String provenance) {
        super(identifier, provenance);
        genres = new LinkedList<>();
        actors = new LinkedList<>();
        directors = new LinkedList<>();
        languages = new LinkedList<>();
        production_companies = new LinkedList<>();
        writers = new LinkedList<>();
        countries = new LinkedList<>();
        countries_availability = new LinkedList<>();
        tags = new LinkedList<>();
    }

    @Override
    public String getIdentifier() {
        return id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<String> getGenre() {
        return genres;
    }

    public void setGenre(List<String> genres) {
        this.genres = genres;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getActors() {
        return actors;
    }

    public void setActors(List<String> actors) {
        this.actors = actors;
    }

    public Double getRelease_year() {
        return release_year;
    }

    public void setRelease_year(Double release_year) {
        this.release_year = release_year;
    }

    public List<String> getDirectors() {
        return directors;
    }

    public void setDirectors(List<String> directors) {
        this.directors = directors;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public Double getImdb_votes() {
        return imdb_votes;
    }

    public void setImdb_votes(Double imdb_votes) {
        this.imdb_votes = imdb_votes;
    }

    public List<String> getProduction_companies() {
        return production_companies;
    }

    public void setProduction_companies(List<String> production_companies) {
        this.production_companies = production_companies;
    }

    public Double getImdb_score() {
        return imdb_score;
    }

    public void setImdb_score(Double imdb_score) {
        this.imdb_score = imdb_score;
    }

    public List<String> getWriters() {
        return writers;
    }

    public void setWriters(List<String> writers) {
        this.writers = writers;
    }

    public Double getAvg_vote() {
        return avg_vote;
    }

    public void setAvg_vote(Double avg_vote) {
        this.avg_vote = avg_vote;
    }

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public void setbudget(Double budget) {
        this.budget = budget;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getReviews_from_critics() {
        return reviews_from_critics;
    }

    public void setReviews_from_critics(String reviews_from_critics) {
        this.reviews_from_critics = reviews_from_critics;
    }

    public String getReviews_from_users() {
        return reviews_from_users;
    }

    public void setReviews_from_users(String reviews_from_users) {
        this.reviews_from_users = reviews_from_users;
    }

    public String getTitle_id() {
        return title_id;
    }

    public void setTitle_id(String title_id) {
        this.title_id = title_id;
    }

    public List<String> getCountries_availability() {
        return countries_availability;
    }

    public void setCountries_availability(List<String> countries_availability) {
        this.countries_availability = countries_availability;
    }

    public Double getHidden_gem_score() {
        return hidden_gem_score;
    }

    public void setHidden_gem_score(Double hidden_gem_score) {
        this.hidden_gem_score = hidden_gem_score;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImdb_link() {
        return imdb_link;
    }

    public void setImdb_link(String imdb_link) {
        this.imdb_link = imdb_link;
    }

    public String getNetflix_link() {
        return netflix_link;
    }

    public void setNetflix_link(String netflix_link) {
        this.netflix_link = netflix_link;
    }

    public LocalDateTime getNetflix_release_date() {
        return netflix_release_date;
    }

    public void setNetflix_release_date(LocalDateTime netflix_release_date) {
        this.netflix_release_date = netflix_release_date;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getSeries_or_movie() {
        return series_or_movie;
    }

    public void setSeries_or_movie(String series_or_movie) {
        this.series_or_movie = series_or_movie;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getDisney_flag() {
        return disney_flag;
    }

    public void setDisney_flag(String disney_flag) {
        this.disney_flag = disney_flag;
    }

    public String getHulu_flag() {
        return hulu_flag;
    }

    public void setHulu_flag(String hulu_flag) {
        this.hulu_flag = hulu_flag;
    }

    public String getNetflix_flag() {
        return netflix_flag;
    }

    public void setNetflix_flag(String netflix_flag) {
        this.netflix_flag = netflix_flag;
    }

    public String getPrime_video_flag() {
        return prime_video_flag;
    }

    public void setPrime_video_flag(String prime_video_flag) {
        this.prime_video_flag = prime_video_flag;
    }

    public Collection<String> getRecordProvenance() {
        return recordProvenance;
    }

    public void setRecordProvenance(Collection<String> provenance) {
        recordProvenance = provenance;
    }

    public void setAttributeProvenance(Attribute attribute,
                                       Collection<String> provenance) {
        this.provenance.put(attribute, provenance);
    }

    public Collection<String> getAttributeProvenance(String attribute) {
        return provenance.get(attribute);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getMergedAttributeProvenance(Attribute attribute) {
        Collection<String> prov = provenance.get(attribute);

        if (prov != null) {
            return StringUtils.join(prov, "+");
        } else {
            return "";
        }
    }

    @Override
    public boolean hasValue(Attribute attribute) {
        Method getAttributeMethod = null;
        try {
            getAttributeMethod = this.getClass().getMethod("get" + StringUtils.capitalize(attribute.getIdentifier()));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        Class<?> attributeType = getAttributeMethod.getReturnType();
        Object attributeValue = null;
        try {
            attributeValue = getAttributeMethod.invoke(this);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        if (attributeType == String.class) {
            String stringAttribute = (String) attributeValue;
            return stringAttribute != null && !stringAttribute.isEmpty();
        } else if (attributeType == List.class) {
            List<String> listAttribute = (List<String>) attributeValue;
            return listAttribute != null && listAttribute.size() > 0;
        } else if (attributeType == Double.class) {
            return attributeValue != null;
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("[Movie %s: %s / %s / %s]", getIdentifier(), getSource(),
                getTitle(), getActors().toString());
    }

    @Override
    public int hashCode() {
        return getIdentifier().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Movie) {
            return this.getIdentifier().equals(((Movie) obj).getIdentifier());
        } else
            return false;
    }
}
