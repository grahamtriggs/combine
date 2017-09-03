package combine.transformers;

public class Transform {
    private static final StringTransformer[] normalizationTransformers = {
            new LowerCaseTransformer(),
            new UnicodeTransformer(),
            new StripTagsTransformer(),
            new ExpressionsTransformer(),
            new HyphensTransformer()
    };

    private static final StringTransformer approximation = new ApproximationTransformer();

    public static final String normalize(String str) {
        for (StringTransformer transformer : normalizationTransformers) {
            str = transformer.transform(str);
        }

        return str;
    }

    public static final String makeApproximation(String str) {
        str = normalize(str);
        if (str != null) {
            str = approximation.transform(str);
        }

        return str;
    }
}
