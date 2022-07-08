package IE683.utils;

import IE683.model.Movie;
import IE683.model.MovieXMLReader;
import de.uni_mannheim.informatik.dws.winter.model.FusibleDataSet;
import de.uni_mannheim.informatik.dws.winter.model.FusibleHashedDataSet;
import de.uni_mannheim.informatik.dws.winter.model.HashedDataSet;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.File;
import java.io.IOException;

import static IE683.constants.Constants.XML_FILES_DIR;

public class Utils {
    public static HashedDataSet<Movie, Attribute> loadDataset(String source) {
        HashedDataSet<Movie, Attribute> dataSet = new HashedDataSet<>();
        try {
            new MovieXMLReader().loadFromXML(new File(XML_FILES_DIR + "//" + source + ".xml"),
                    "movies/movie", dataSet);
        } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException e) {
            e.printStackTrace();
        }
        return dataSet;
    }

    public static FusibleDataSet<Movie, Attribute> loadFusibleDataset(String source) {
        FusibleDataSet<Movie, Attribute> dataSet = new FusibleHashedDataSet<>();
        try {
            new MovieXMLReader().loadFromXML(new File(XML_FILES_DIR + "//" + source + ".xml"),
                    "movies/movie", dataSet);
        } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException e) {
            e.printStackTrace();
        }
        return dataSet;
    }
}

