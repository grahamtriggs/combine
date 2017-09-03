package combine.transformers;

/**
 * Utility class to perform common multi-transform steps
 */
public class Transform {
    private static final StringTransformer[] normalizationTransformers = {
            new LowerCaseTransformer(),
            new UnicodeTransformer(),
            new StripTagsTransformer(),
            new ExpressionsTransformer(),
            new HyphensTransformer()
    };

    private static final StringTransformer approximation = new ApproximationTransformer();

    /**
     * Normalize a string
     * @param str String to normalize
     * @return normalized string
     */
    public static final String normalize(String str) {
        for (StringTransformer transformer : normalizationTransformers) {
            str = transformer.transform(str);
        }

        return str;
    }

    /**
     * Make an approximate match string
     * @param str String to make an approximate match for
     * @return the matching string
     */
    public static final String makeApproximation(String str) {
        str = normalize(str);
        if (str != null) {
            str = approximation.transform(str);
        }

        return str;
    }
}
