package IE683.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.uni_mannheim.informatik.dws.winter.model.io.XMLFormatter;

/**
 * {@link XMLFormatter} for {@link Movie}s.
 *
 *
 */
public class MovieXMLFormatter extends XMLFormatter<Movie> {

	// Converting the double values into string.
	public String doubleToStringConvertor(Double value) {
		if (!value.equals(null)) {
		String string_value = Double.toString(value);
		return string_value;
		}
		return null;
	}

	// Converting the date values into string.
	public String dateToStringConvertor(LocalDateTime value) {
		if (!value.equals(null)) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String string_value = value.format(formatter); 
		return string_value;
		}
		return null;
	}
	
	@Override
	public Element createRootElement(Document doc) {
		return doc.createElement("movies");
	}

	@Override
	public Element createElementFromRecord(Movie record, Document doc) {
		Element movie = doc.createElement("movie");

		movie.appendChild(createTextElement("id", record.getIdentifier(), doc));
		// movie.appendChild(createTextElement("source",record.getSource(), doc)); // Not present in the final gold standard file.		
		movie.appendChild(createTextElementWithProvenance("title",
				record.getTitle(),
				record.getMergedAttributeProvenance(Movie.TITLE), doc));
		movie.appendChild(createTextElementWithProvenance("imdb_votes",
				doubleToStringConvertor(record.getImdb_votes()),
				record.getMergedAttributeProvenance(Movie.IMDB_VOTES), doc));		
		movie.appendChild(createTextElement("original_title",
				record.getOriginal_title(),
				doc));
		movie.appendChild(createTextElement("reviews_from_critics",
				record.getReviews_from_critics(),
				doc));
		movie.appendChild(createTextElement("reviews_from_users",
				record.getReviews_from_users(),
				doc));
		movie.appendChild(createTextElement("title_id",
				record.getTitle_id(),
				doc));
		movie.appendChild(createTextElement("image",
				record.getImage(),
				doc));
		movie.appendChild(createTextElement("imdb_link",
				record.getImdb_link(),
				doc));
		movie.appendChild(createTextElement("netflix_link",
				record.getNetflix_link(),
				doc));
		movie.appendChild(createTextElement("poster",
				record.getPoster(),
				doc));
		movie.appendChild(createTextElement("series_or_movie",
				record.getSeries_or_movie(),
				doc));
		movie.appendChild(createTextElement("disney_flag",
				record.getDisney_flag(),
				doc));
		movie.appendChild(createTextElement("hulu_flag",
				record.getHulu_flag(),
				doc));
		movie.appendChild(createTextElement("netflix_flag",
				record.getNetflix_flag(),
				doc));
		movie.appendChild(createTextElement("prime_video_flag",
				record.getPrime_video_flag(),
				doc));
		movie.appendChild(createTextElementWithProvenance("imdb_score",
				doubleToStringConvertor(record.getImdb_score()),
				record.getMergedAttributeProvenance(Movie.IMDB_SCORE), doc));
		movie.appendChild(createTextElementWithProvenance("avg_vote",
				doubleToStringConvertor(record.getAvg_vote()),
				record.getMergedAttributeProvenance(Movie.AVG_VOTE), doc));
		movie.appendChild(createTextElementWithProvenance("budget",
				doubleToStringConvertor(record.getBudget()),
				record.getMergedAttributeProvenance(Movie.BUDGET), doc));
		movie.appendChild(createTextElement("hidden_gem_score",
				doubleToStringConvertor(record.getHidden_gem_score()),
				doc));
		movie.appendChild(createTextElement("duration",
				doubleToStringConvertor(record.getDuration()),
				doc));
		movie.appendChild(createTextElement("release_year",
				doubleToStringConvertor(record.getRelease_year()),
				doc));
		movie.appendChild(createTextElement("netflix_release_date",
				dateToStringConvertor(record.getNetflix_release_date()),
				doc));
		movie.appendChild(createListElementWithProvenance("production_companies",
				record.getProduction_companies(),
				record.getMergedAttributeProvenance(Movie.PRODUCTION_COMPANIES),
				doc));
		movie.appendChild(createListElementWithProvenance("genres",
				record.getGenres(),
				record.getMergedAttributeProvenance(Movie.GENRES),
				doc));
		movie.appendChild(createListElementWithProvenance("actors",
				record.getActors(),
				record.getMergedAttributeProvenance(Movie.ACTORS),
				doc));		
		movie.appendChild(createListElementWithProvenance("directors",
				record.getDirectors(),
				record.getMergedAttributeProvenance(Movie.DIRECTORS),
				doc));
		movie.appendChild(createListElementWithProvenance("languages",
				record.getLanguages(),
				record.getMergedAttributeProvenance(Movie.LANGUAGES),
				doc));
		movie.appendChild(createListElementWithProvenance("writers",
				record.getWriters(),
				record.getMergedAttributeProvenance(Movie.WRITERS),
				doc));
		movie.appendChild(createListElementWithProvenance("countries",
				record.getCountries(),
				record.getMergedAttributeProvenance(Movie.COUNTRIES),
				doc));
		movie.appendChild(createListElement("countries_availability",
				record.getCountries(),
				doc));
		movie.appendChild(createListElement("tags",
				record.getTags(),
				doc));

		return movie;
	}

	protected Element createTextElementWithProvenance(String name,
			String value, String provenance, Document doc) {
		Element elem = createTextElement(name, value, doc);
		elem.setAttribute("provenance", provenance);
		return elem;
	}
	
	
	protected Element createListElementWithProvenance(String name,
			List<String> value, String provenance, Document doc) {
		Element elemRoot = doc.createElement(name);
		HashMap<String, String> tagXMLMap = new HashMap<>();
		tagXMLMap.put("country", "countries");
		tagXMLMap.put("genre", "genres");
		tagXMLMap.put("actor_names", "actors");
		tagXMLMap.put("director", "directors");
		tagXMLMap.put("language", "languages");
		tagXMLMap.put("production_company", "production_companies");
		tagXMLMap.put("writer", "writers");

		if (value != null) {
		for (String val : value) {
			elemRoot.appendChild(createTextElement(tagXMLMap.get(name), val, doc));
			}
		}	
		elemRoot.setAttribute("provenance", provenance);
		return elemRoot;
	}
	
	protected Element createListElement(String name, List<String> value, Document doc) {
		Element elemRoot = doc.createElement(name);
		HashMap<String, String> tagXMLMap = new HashMap<>();
		tagXMLMap.put("tags", "tags");
		tagXMLMap.put("countries_availability", "country_availability");
		if (value != null) {
		for (String val : value) {
			elemRoot.appendChild(createTextElement(tagXMLMap.get(name), val, doc));
			}
		}	
		return elemRoot;
	}
	
}
