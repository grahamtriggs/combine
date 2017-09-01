package combine.transformers;

import java.util.ArrayList;
import java.util.List;

public class SignificantTokensTransformer implements StringTransformer {
    @Override
    public String transform(String str) {
        List<String> tokens = new ArrayList<String>();

        for (int chIdx = 0; chIdx < str.length(); chIdx++) {
            if (!Character.isLetterOrDigit(str.charAt(chIdx))) {
                do {
                    chIdx++;
                } while (chIdx < str.length() && !Character.isLetterOrDigit(str.charAt(chIdx)));

                if (chIdx >= str.length()) {
                    break;
                }
            }

            if (Character.isDigit(str.charAt(chIdx))) {
                boolean hasDash = false;
                boolean hasSlash = false;
                boolean hasComma = false;
                boolean hasPoint = false;

                int startIdx = chIdx;
                while (chIdx < str.length()) {
                    char ch = str.charAt(chIdx);
                    if (!Character.isDigit(ch)) {
                        if (!peekForDigit(str, chIdx)) {
                            break;
                        }
                        if (ch == ',') {
                            if (!tokenizeDate(tokens, str, startIdx, chIdx, hasComma, hasPoint, hasSlash, hasDash)) {
                                hasComma = true;
                            } else {
                                hasDash = false;
                                hasSlash = false;
                                do {
                                    chIdx++;
                                } while (chIdx < str.length() && !Character.isDigit(str.charAt(chIdx)));
                                startIdx = chIdx;
                            }
                        } else if (ch == '.') {
                            hasPoint = true;
                        } else if (ch == '/') {
                            hasSlash = true;
                        } else if (ch == '-') {
                            hasDash = true;
                        } else {
                            break;
                        }
                    }
                    chIdx++;
                }

                boolean isDate = tokenizeDate(tokens, str, startIdx, chIdx, hasComma, hasPoint, hasSlash, hasDash);

                if (!isDate) {
                    if (!hasPoint && chIdx < str.length() - 1) {
                        char suffixCh  = str.charAt(chIdx);
                        char suffixCh2 = str.charAt(chIdx + 1);
                        if (
                                (suffixCh == 's' && suffixCh2 == 't') ||
                                (suffixCh =='n' && suffixCh2 == 'd') ||
                                (suffixCh == 'r' && suffixCh2 == 'd')
                                ) {
                            if (chIdx == str.length() - 2 || !Character.isLetterOrDigit(str.charAt(chIdx + 2))) {
                                chIdx += 2;
                            }
                        }
                    }

                    if (chIdx - startIdx > 2) {
                        tokens.add(str.substring(startIdx, chIdx));
                    }
                }
            } else {
                int startIdx = chIdx;
                do {
                    chIdx++;
                } while (chIdx < str.length() && Character.isLetter(str.charAt(chIdx)));

                if (chIdx - startIdx > 1) {
                    if (chIdx < str.length()) {
                        tokenizeWords(tokens, str.substring(startIdx, chIdx));
                    } else {
                        tokenizeWords(tokens, str.substring(startIdx));
                    }
                }
            }
        }

        return tokensToString(tokens, str);
    }

    private boolean peekForDigit(String str, int chIdx) {
        char ch;
        do {
            ch = str.charAt(chIdx);
            if (Character.isDigit(ch)) {
                return true;
            }
            chIdx++;
        } while (chIdx < str.length() && (ch == ',' || ch == '.' || ch == '-' || ch == '/'));

        return false;
    }

    private boolean tokenizeDate(List<String> tokens, String str, int startIdx, int chIdx, boolean hasComma, boolean hasPoint, boolean hasSlash, boolean hasDash) {
        if (chIdx - startIdx > 5 && chIdx - startIdx < 11) {
            if (!hasComma && !hasPoint) {
                if ( (hasSlash && !hasDash) || (hasDash && !hasSlash)) {
                    if (chIdx < str.length()) {
                        return tokenizeDate(tokens, str.substring(startIdx, chIdx), hasSlash ? '/' : '-');
                    } else {
                        return tokenizeDate(tokens, str.substring(startIdx), hasSlash ? '/' : '-');
                    }
                }
            }
        }

        return false;
    }
    private boolean tokenizeDate(List<String> tokens, String str, char separator) {
        char[] date = str.toCharArray();

        StringBuilder token = new StringBuilder();

        if (str.length() == 10) {
            if (date[4] == separator && date[7] == separator) {
                // YYYY-MM-DD
                token.append(date[0]).append(date[1]).append(date[2]).append(date[3]);
                token.append(date[5]).append(date[6]);
                token.append(date[8]).append(date[9]);
            } else if (date[2] == separator && date[5] == separator) {
                // DD-MM-YYYY
                token.append(date[6]).append(date[7]).append(date[8]).append(date[9]);
                token.append(date[3]).append(date[4]);
                token.append(date[0]).append(date[1]);
            }
        } else if (str.length() == 9) {
            if (date[4] == separator && date[7] == separator) {
                // YYYY-MM-D
                token.append(date[0]).append(date[1]).append(date[2]).append(date[3]);
                token.append(date[5]).append(date[6]);
                token.append("0").append(date[8]);
            } else if (date[4] == separator && date[6] == separator) {
                // YYYY-M-DD
                token.append(date[0]).append(date[1]).append(date[2]).append(date[3]);
                token.append("0").append(date[5]);
                token.append(date[7]).append(date[8]);
            } else if (date[2] == separator && date[4] == separator) {
                // DD-M-YYYY
                token.append(date[5]).append(date[6]).append(date[7]).append(date[8]);
                token.append("0").append(date[3]);
                token.append(date[0]).append(date[1]);
            } else if (date[1] == separator && date[4] == separator) {
                // D-MM-YYYY
                token.append(date[5]).append(date[6]).append(date[7]).append(date[8]);
                token.append(date[2]).append(date[3]);
                token.append("0").append(date[0]);
            }
        } else if (str.length() == 8) {
            if (date[4] == separator && date[6] == separator) {
                // YYYY-M-D
                token.append(date[0]).append(date[1]).append(date[2]).append(date[3]);
                token.append("0").append(date[5]);
                token.append("0").append(date[7]);
            } else if (date[2] == separator && date[5] == separator) {
                // D-M-YYYY
                token.append(date[4]).append(date[5]).append(date[6]).append(date[7]);
                token.append("0").append(date[2]);
                token.append("0").append(date[0]);
            }
        } else if (str.length() == 7) {
            if (date[4] == separator) {
                // YYYY-MM
                token.append(date[0]).append(date[1]).append(date[2]).append(date[3]);
                token.append(date[5]).append(date[6]);
            } else if (date[2] == separator) {
                // MM-YYYY
                token.append(date[3]).append(date[4]).append(date[5]).append(date[6]);
                token.append(date[0]).append(date[1]);
            }
        } else if (str.length() == 6) {
            if (date[4] == separator) {
                // YYYY-M
                token.append(date[0]).append(date[1]).append(date[2]).append(date[3]);
                token.append("0").append(date[5]);
            } else if (date[1] == separator) {
                // M-YYYY
                token.append(date[2]).append(date[3]).append(date[4]).append(date[5]);
                token.append("0").append(date[0]);
            }
        }

        if (token.length() > 0) {
            tokens.add(token.toString());
            return true;
        }

        return false;
    }

    private void tokenizeWords(List<String> tokens, String str) {
        do {
            if (str.length() < 3) {
                if (keepShortWord(str)) {
                    tokens.add(str);
                }
                return;
            }

            if (isStopword(str)) {
                return;
            }

            str = expandAcronym(str);
            str = addTokenOrPrefix(tokens, str);
        } while (str != null && str.length() > 1);
    }

    private boolean isStopword(String str) {
        return "and".equals(str) ||
            "the".equals(str) ||
            "this".equals(str) ||
            "that".equals(str) ||
            "with".equals(str) ||
            "for".equals(str) ||
            "not".equals(str) ||
            "you".equals(str) ||
            "but".equals(str) ||
            "from".equals(str);
    }

    private boolean keepShortWord(String str) {
        return "mu".equals(str) ||
                "nu".equals(str) ||
                "pi".equals(str) ||
                "xi".equals(str);
    }

    private String expandAcronym(String str) {
        if ("msm".equals(str)) {
            return "microsimulation";
        } else if ("thz".equals(str)) {
            return "terahertz";
        }

        return str;
    }

    private String addTokenOrPrefix(List<String> tokens, String str) {
        if (str.length() > 8) {
            if (addPrefix(tokens, str, "diphenyl")) { return str.substring(8); }
        }

        if (str.length() > 7) {
            if (addPrefix(tokens, str, "electro")) { return str.substring(7); }
            if (addPrefix(tokens, str, "magneto")) { return str.substring(7); }
            if (addPrefix(tokens, str, "phospho")) { return str.substring(7); }
        }

        if (str.length() > 6) {
            if (addPrefix(tokens, str, "chloro")) { return str.substring(6); }
            if (addPrefix(tokens, str, "methyl")) { return str.substring(6); }
            if (addPrefix(tokens, str, "psuedo")) { return str.substring(6); }
            if (addPrefix(tokens, str, "school")) { return str.substring(6); }
            if (addPrefix(tokens, str, "thermo")) { return str.substring(6); }
        }

        if (str.length() > 5) {
            if (addPrefix(tokens, str, "flexi")) { return str.substring(5); }
            if (addPrefix(tokens, str, "infra")) { return str.substring(5); }
            if (addPrefix(tokens, str, "inter")) { return str.substring(5); }
            if (addPrefix(tokens, str, "intra")) { return str.substring(5); }
            if (addPrefix(tokens, str, "macro")) { return str.substring(5); }
            if (addPrefix(tokens, str, "micro")) { return str.substring(5); }
            if (addPrefix(tokens, str, "multi")) { return str.substring(5); }
            if (addPrefix(tokens, str, "nitro")) { return str.substring(5); }
            if (addPrefix(tokens, str, "north")) { return str.substring(5); }
            if (addPrefix(tokens, str, "quasi")) { return str.substring(5); }
            if (addPrefix(tokens, str, "socio")) { return str.substring(5); }
            if (addPrefix(tokens, str, "south")) { return str.substring(5); }
            if (addPrefix(tokens, str, "super")) { return str.substring(5); }
            if (addPrefix(tokens, str, "tetra")) { return str.substring(5); }
            if (addPrefix(tokens, str, "trade")) { return str.substring(5); }
            if (addPrefix(tokens, str, "trans")) { return str.substring(5); }
            if (addPrefix(tokens, str, "ultra")) { return str.substring(5); }
        }

        if (str.length() > 4) {
            if (addPrefix(tokens, str, "anti")) { return str.substring(4); }
            if (addPrefix(tokens, str, "east")) { return str.substring(4); }
            if (addPrefix(tokens, str, "meth")) { return str.substring(4); }
            if (addPrefix(tokens, str, "nano")) { return str.substring(4); }
            if (addPrefix(tokens, str, "octa")) { return str.substring(4); }
            if (addPrefix(tokens, str, "omni")) { return str.substring(4); }
            if (addPrefix(tokens, str, "para")) { return str.substring(4); }
            if (addPrefix(tokens, str, "post")) { return str.substring(4); }
            if (addPrefix(tokens, str, "semi")) { return str.substring(4); }
            if (addPrefix(tokens, str, "west")) { return str.substring(4); }
        }

        if (str.length() > 3) {
            if (addPrefix(tokens, str, "bio")) { return str.substring(3); }
            if (addPrefix(tokens, str, "fro")) { return str.substring(3); }
            if (addPrefix(tokens, str, "hot")) { return str.substring(3); }
            if (addPrefix(tokens, str, "mid")) { return str.substring(3); }
            if (addPrefix(tokens, str, "non")) { return str.substring(3); }
            if (addPrefix(tokens, str, "pre")) { return str.substring(3); }
            if (addPrefix(tokens, str, "sub")) { return str.substring(3); }
            if (addPrefix(tokens, str, "syn")) { return str.substring(3); }
            if (addPrefix(tokens, str, "tri")) { return str.substring(3); }
        }

        tokens.add(str);
        return null;
    }

    private boolean addPrefix(List<String> tokens, String str, String prefix) {
        if (str.startsWith(prefix)) {
            tokens.add("!!" + prefix);
            return true;
        }

        return false;
    }


    private String tokensToString(List<String> tokens, String str) {
        StringBuilder builder = new StringBuilder();

        boolean includeEndOfToken;
        int minTokenSize;
        switch (tokens.size()) {
            case 3:
                includeEndOfToken = true;
                minTokenSize = 2;
                break;

            case 2:
                includeEndOfToken = true;
                minTokenSize = 3;
                break;

            case 1:
                includeEndOfToken = true;
                minTokenSize = 4;
                break;

            default:
                includeEndOfToken = false;
                minTokenSize = 2;
        }

        for (String token : tokens) {
            if (builder.length() > 0) {
                builder.append("|");
            }

            addToken(builder, token, minTokenSize, includeEndOfToken);
        }

        addTrailingNumeric(builder, str);

        // add trailing numeric
        return builder.reverse().toString();
    }

    private void addToken(StringBuilder builder, String token, int minTokenSize, boolean includeEndOfToken) {
        boolean vowel = false;
        boolean consonant = false;

        if (token.startsWith("!!")) {
            builder.append(token.substring(2));
            return;
        }

        if (Character.isDigit(token.charAt(0))) {
            builder.append(token);
        }

        int idx = 0;
        while (idx < token.length() && (!vowel || !consonant || idx < minTokenSize)) {
            switch(token.charAt(idx)) {
                case 'a':
                case 'e':
                case 'i':
                case 'o':
                case 'u':
                    vowel = true;
                    break;

                default:
                    consonant = true;
                    break;
            }

            idx++;
        }

        if (idx < token.length()) {
            builder.append(token.substring(0, idx));
        }

        if (includeEndOfToken) {
            vowel = false;
            consonant = false;
            idx = token.length();
            while (idx-- > 0 && (!vowel || !consonant)) {
                switch (token.charAt(idx)) {
                    case 'a':
                    case 'e':
                    case 'i':
                    case 'o':
                    case 'u':
                        vowel = true;
                        break;

                    default:
                        consonant = true;
                        break;
                }
            }

            if (idx > -1) {
                builder.append(".");
                builder.append(token.substring(idx));
            }
        }
    }

    private final String TRAILING_SEPERATOR = "_";
    private void addTrailingNumeric(StringBuilder builder, String str) {
        int idx = str.length() - 1;
        while (idx > -1) {
            if (Character.isDigit(str.charAt(idx))) {
                int endIdx = idx + 1;
                idx--;
                while (idx > -1 && Character.isDigit(str.charAt(idx))) {
                    idx--;
                }

                if (endIdx - idx == 2 && idx > 3 && str.charAt(idx) == '-') {
                    if (idx == 4 || !Character.isDigit(str.charAt(idx - 5))) {
                        if (
                                Character.isDigit(str.charAt(idx - 1)) &&
                                Character.isDigit(str.charAt(idx - 2)) &&
                                Character.isDigit(str.charAt(idx - 3)) &&
                                Character.isDigit(str.charAt(idx - 4))) {
                            builder.append(TRAILING_SEPERATOR).append(str.substring(idx - 4, idx - 2));
                            if (endIdx < str.length()) {
                                builder.append(str.substring(idx + 1, endIdx));
                            } else {
                                builder.append(str.substring(idx + 1));
                            }
                            return;
                        }
                    }
                }

                if (idx > -1 && !Character.isLetter(str.charAt(idx))) {
                    builder.append(TRAILING_SEPERATOR);
                    if (endIdx < str.length()) {
                        builder.append(str.substring(idx + 1, endIdx));
                    } else {
                        builder.append(str.substring(idx + 1));
                    }
                    return;
                }
            } else {
                char ch = str.charAt(idx);
                if (ch == 'i' || ch == 'v' || ch == 'x' || ch == 'l' || ch == 'm') {
                    int endIdx = idx + 1;
                    idx--;

                    while (idx > -1) {
                        ch = str.charAt(idx);
                        if (ch == 'i' || ch == 'v' || ch == 'x' || ch == 'l' || ch == 'm') {
                            idx--;
                        } else {
                            break;
                        }
                    }

                    if (idx > -1 && !Character.isLetterOrDigit(str.charAt(idx))) {
                        String roman;
                        if (endIdx < str.length()) {
                            roman = str.substring(idx + 1, endIdx);
                        } else {
                            roman = str.substring(idx + 1);
                        }
                        int value = convertRomanNumeral(roman);
                        if (value > 0) {
                            builder.append(TRAILING_SEPERATOR).append(value);
                        }
                    }

                    return;
                } else if (Character.isLetter(ch)) {
                    return;
                }
            }

            idx--;
        }
    }

    private int convertRomanNumeral(String roman) {
        int value = 0;
        int lastAddition = 0;

        for (int idx = roman.length() - 1; idx > -1; idx--) {
            switch (roman.charAt(idx)) {
                case 'i':
                    if(lastAddition > 1) {
                        value -= 1;
                    }
                    else {
                        value += 1;
                        lastAddition = 1;
                    }
                    break;
                case 'v':
                    if(lastAddition > 5) {
                        value -= 5;
                    }
                    else {
                        value += 5;
                        lastAddition = 5;
                    }
                    break;
                case 'x':
                    if(lastAddition > 10) {
                        value -= 10;
                    }
                    else {
                        value += 10;
                        lastAddition = 10;
                    }
                    break;
                case 'l':
                    if(lastAddition > 50) {
                        value -= 50;
                    }
                    else {
                        value += 50;
                        lastAddition = 50;
                    }
                    break;
                case 'c':
                    if(lastAddition > 100) {
                        value -= 100;
                    }
                    else {
                        value += 100;
                        lastAddition = 100;
                    }
                    break;
                case 'm':
                    if(lastAddition > 1000) {
                        value -= 1000;
                    }
                    else {
                        value += 1000;
                        lastAddition = 1000;
                    }
                    break;
                default:
                    return -1;
            }
        }

        return value;
    }
}

