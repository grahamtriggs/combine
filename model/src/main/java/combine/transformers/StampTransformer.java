package combine.transformers;

public class StampTransformer implements StringTransformer {
    private final LeadingNumericsTransformer leadingNumerics = new LeadingNumericsTransformer();
    private final SignificantTokensTransformer significantTokens = new SignificantTokensTransformer();
    private final StripTrailingTransformer stripTrailing = new StripTrailingTransformer();

    @Override
    public String transform(String str) {
        StringBuilder builder = new StringBuilder();

        String stripped = stripTrailing.transform(str);

        builder.append(leadingNumerics.transform(stripped));
        builder.append(significantTokens.transform(stripped));

        if (builder.length() == 0) {
            builder.append(leadingNumerics.transform(str));
            builder.append(significantTokens.transform(str));
        }

        return builder.toString();
    }
}
