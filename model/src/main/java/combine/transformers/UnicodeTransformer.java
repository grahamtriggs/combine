package combine.transformers;

import java.text.Normalizer;

public class UnicodeTransformer implements StringTransformer {
    @Override
    public String transform(String str) {
        StringBuilder builder = new StringBuilder();
        String kdString = Normalizer.normalize(str, Normalizer.Form.NFKD);
        for (char ch : kdString.toCharArray()) {
            switch (Character.getType(ch)) {
                case Character.NON_SPACING_MARK:
                case Character.COMBINING_SPACING_MARK:
                case Character.ENCLOSING_MARK:
                    break;

                default:
                    switch (ch) {
                        case '\u00b5': builder.append("micro"); break;
                        case '\u00df': builder.append("ss"); break;
                        case '\u00e6': builder.append("ae"); break;
                        case '\u00f0': builder.append("eth"); break;
                        case '\u00f8': builder.append("o"); break;
                        case '\u00fe': builder.append("thorn"); break;
                        // Greek characters
                        case '\u03b1': builder.append("alpha"); break;
                        case '\u03b2': builder.append("beta"); break;
                        case '\u03b3': builder.append("gamma"); break;
                        case '\u03b4': builder.append("delta"); break;
                        case '\u03b5': builder.append("epsilon"); break;
                        case '\u03b6': builder.append("zeta"); break;
                        case '\u03b7': builder.append("eta"); break;
                        case '\u03b8': builder.append("theta"); break;
                        case '\u03b9': builder.append("iota"); break;
                        case '\u03ba': builder.append("kappa"); break;
                        case '\u03bb': builder.append("lambda"); break;
                        case '\u03bc': builder.append("mu"); break;
                        case '\u03bd': builder.append("nu"); break;
                        case '\u03be': builder.append("xi"); break;
                        case '\u03bf': builder.append("omicron"); break;
                        case '\u03c0': builder.append("pi"); break;
                        case '\u03c1': builder.append("rho"); break;
                        case '\u03c2': builder.append("sigma"); break;
                        case '\u03c3': builder.append("sigma"); break;
                        case '\u03c4': builder.append("tau"); break;
                        case '\u03c5': builder.append("upsilon"); break;
                        case '\u03c6': builder.append("phi"); break;
                        case '\u03c7': builder.append("chi"); break;
                        case '\u03c8': builder.append("psi"); break;
                        case '\u03c9': builder.append("omega"); break;
                        default: builder.append(ch); break;
                    }
                    break;
            }
        }

        return builder.toString();
    }
}
