package combine.transformers;

/**
 * Creates an approximate version of the original string for fast comparisons.
 */
public class ApproximationTransformer implements StringTransformer {
    private final LeadingNumericsTransformer leadingNumerics = new LeadingNumericsTransformer();
    private final SignificantTokensTransformer significantTokens = new SignificantTokensTransformer();
    private final StripTrailingTransformer stripTrailing = new StripTrailingTransformer();

    @Override
    public String transform(String str) {
        StringBuilder builder = new StringBuilder();

        // Remove probably insignificant items from the end of the string
        String stripped = stripTrailing.transform(str);

        // Take any leading numbers
        builder.append(leadingNumerics.transform(stripped));

        // Add any significant tokens
        builder.append(significantTokens.transform(stripped));

        // If nothing, make an approximation of the original string
        if (builder.length() == 0) {
            builder.append(leadingNumerics.transform(str));
            builder.append(significantTokens.transform(str));
        }

        // If nothing, return the original string
        if (builder.length() == 0) {
            return str;
        }

        return builder.toString();
    }
}
