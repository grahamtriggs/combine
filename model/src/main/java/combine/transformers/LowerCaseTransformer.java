package combine.transformers;

public class LowerCaseTransformer implements StringTransformer {

    @Override
    public String transform(String str) {
        return str.toLowerCase();
    }
}
