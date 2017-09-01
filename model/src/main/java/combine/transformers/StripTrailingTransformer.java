package combine.transformers;

public class StripTrailingTransformer implements StringTransformer {
    @Override
    public String transform(String str) {
        return str.replaceAll("((\\[|\\()[\\s]*(([0-9]+[\\s]+ref[\\w]+[\\s]*)|([\\w]+[\\s]*)|(vol[^\\)\\]]+))(\\]|\\))[^\\w]*)+$", " ");
    }
}
