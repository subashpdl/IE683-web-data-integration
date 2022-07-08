package IE683.fuser.numeric.average;

import IE683.model.Movie;

public class ImdbScoreFuser extends MovieAverageFuser {
    public ImdbScoreFuser() throws NoSuchMethodException {
        super(Movie.IMDB_SCORE);
    }
}
