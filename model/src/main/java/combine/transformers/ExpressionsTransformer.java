package combine.transformers;

/**
 * Remove some expressions from the original string
 */
public class ExpressionsTransformer implements StringTransformer {
    @Override
    public String transform(String str) {
        return str.replaceAll("\\([a-z]\\)over-bar", "");
    }
}
