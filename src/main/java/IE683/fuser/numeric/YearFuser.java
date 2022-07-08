package IE683.fuser.numeric;

import IE683.conflictresolution.Minimum;
import IE683.fuser.MovieFuser;
import IE683.model.Movie;
import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.numeric.Median;

public class YearFuser extends MovieFuser<Double> {
    public YearFuser() throws NoSuchMethodException {
        super(new Minimum<>(), Movie.YEAR, Double.class);
    }
}
