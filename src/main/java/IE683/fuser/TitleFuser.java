package IE683.fuser;

import IE683.model.Movie;
import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.string.LongestString;

public class TitleFuser extends MovieFuser<String> {
    public TitleFuser() throws NoSuchMethodException {
        super(new LongestString<>(), Movie.TITLE, String.class);
    }
}
