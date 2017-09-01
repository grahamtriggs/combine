package combine.transformers;

public class LeadingNumericsTransformer implements StringTransformer {
    @Override
    public String transform(String str) {
        for (int idx = 0; idx < str.length(); idx++) {
            if (Character.isDigit(str.charAt(idx))) {
                int startIdx = idx;
                for ( ; idx < str.length(); idx++) {
                    if (!Character.isDigit(str.charAt(idx))) {
                        if (str.charAt(idx) != '.' && str.charAt(idx) != ',') {
                            break;
                        }
                        if (idx + 1 < str.length() && !Character.isDigit(str.charAt(idx+1))) {
                            break;
                        }
                    }
                }

                if (idx < str.length()) {
                    return str.substring(startIdx, idx);
                }
            } else if (Character.isLetter(str.charAt(idx))) {
                break;
            }
        }

        return "";
    }
}
