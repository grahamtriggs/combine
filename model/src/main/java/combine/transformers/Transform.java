package combine.transformers;

public class Transform {
    private static final StringTransformer[] stampTransforms = {
            new LowerCaseTransformer(),
            new UnicodeTransformer(),
            new StripTagsTransformer(),
            new ExpressionsTransformer(),
            new HyphensTransformer(),
            new StampTransformer()
    };

    public static final String makeStamp(String str) {
        for (StringTransformer transformer : stampTransforms) {
            str = transformer.transform(str);
        }

        return str;
    }
}
