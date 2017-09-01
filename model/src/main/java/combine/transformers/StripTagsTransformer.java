package combine.transformers;

public class StripTagsTransformer implements StringTransformer {
    @Override
    public String transform(String str) {
        return str.replaceAll("<[^>]+>", " ");
    }
}
