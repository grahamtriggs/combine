package combine.transformers;

/**
 * Strip xml / html tags from a string
 */
public class StripTagsTransformer implements StringTransformer {
    @Override
    public String transform(String str) {
        return str.replaceAll("<[^>]+>", " ");
    }
}
