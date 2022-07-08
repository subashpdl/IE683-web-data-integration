package IE683.fuser.list;

import IE683.fuser.MovieFuser;
import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.list.Union;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

import java.util.List;

public abstract class MovieListFuser extends MovieFuser<List<String>> {
    public MovieListFuser(Attribute attribute) throws NoSuchMethodException {
        super(new Union<>(), attribute, List.class);
    }
}
