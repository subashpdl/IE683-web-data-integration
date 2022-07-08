package IE683.fuser.numeric.average;

import IE683.fuser.MovieFuser;
import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.numeric.Average;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

public abstract class MovieAverageFuser extends MovieFuser<Double> {
    public MovieAverageFuser(Attribute attribute) throws NoSuchMethodException {
        super(new Average<>(), attribute, Double.class);
    }
}
