package IE683.comparator.string;

import de.uni_mannheim.informatik.dws.winter.similarity.string.LevenshteinSimilarity;

import java.util.Locale;

public class LowerLevenSimilarity extends LevenshteinSimilarity {
    private static final long serialVersionUID = 1L;

    @Override
    public double calculate(String first, String second) {
        first = first.toLowerCase();
        second = second.toLowerCase();

        return super.calculate(first, second);
    }
}
