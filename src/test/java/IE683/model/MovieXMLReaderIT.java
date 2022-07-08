package IE683.model;

import IE683.utils.Utils;
import de.uni_mannheim.informatik.dws.winter.model.HashedDataSet;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class MovieXMLReaderIT {

    @Test
    void testIdentityResolution() {
        List<HashedDataSet<Movie, Attribute>> dataSets =
                Stream.of("streaming", "imdb", "netflix").map(Utils::loadDataset).collect(Collectors.toList());
        System.out.println("");
    }
}