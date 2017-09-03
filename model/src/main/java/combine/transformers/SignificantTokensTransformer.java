package combine.transformers;

import java.util.ArrayList;
import java.util.List;

/**
 * Convert a string into signficant tokens, then return a string formed from them
 */
public class SignificantTokensTransformer implements StringTransformer {
    private final static String KEEP_FLAG = "!!";

    private final static String TOKEN_SEPERATOR = "A";
    private final static String MIDTOKEN_SEPERATOR = "S";
    private final static String TRAILING_SEPERATOR = "Z";

    @Override
    public String transform(String str) {
        List<String> tokens = new ArrayList<String>();

        // Check each character
        for (int chIdx = 0; chIdx < str.length(); chIdx++) {
            // Skip anything that isn't a letter or number
            if (!Character.isLetterOrDigit(str.charAt(chIdx))) {
                do {
                    chIdx++;
                } while (chIdx < str.length() && !Character.isLetterOrDigit(str.charAt(chIdx)));

                // If we've hit the end of the string, we're done
                if (chIdx >= str.length()) {
                    break;
                }
            }

            if (Character.isDigit(str.charAt(chIdx))) {
                chIdx = tokenizeNumberAtOffset(tokens, str, chIdx);
            } else {
                chIdx = tokenizeWordAtOffest(tokens, str, chIdx);
            }
        }

        StringBuilder builder = new StringBuilder();
        tokensToString(builder, tokens);
        addTrailingNumeric(builder, str);

        return builder.reverse().toString();
    }

    /**
     * Generate numeric tokens
     *
     * @param tokens List of tokens to add to
     * @param str String we are processing
     * @param offset Offset into the string to start from
     * @return end offset of
     */
    private int tokenizeNumberAtOffset(List<String> tokens, String str, int offset) {
        boolean hasDash = false;
        boolean hasSlash = false;
        boolean hasComma = false;
        boolean hasPoint = false;

        int startIdx = offset;
        while (offset < str.length()) {
            char ch = str.charAt(offset);
            // If we haven't got a number
            if (!Character.isDigit(ch)) {
                // Check to see that the next important character is a number (not a letter)
                if (!peekForDigitFromOffset(str, offset)) {
                    break;
                }

                // If we have a comma
                if (ch == ',') {
                    // First, see if we can make a date out of the numbers before the comma
                    if (!tokenizeDate(tokens, str, startIdx, offset, hasComma, hasPoint, hasSlash, hasDash)) {
                        // Not a date, so we have a comma
                        hasComma = true;
                    } else {
                        hasDash = false;
                        hasSlash = false;
                        do {
                            offset++;
                        } while (offset < str.length() && !Character.isDigit(str.charAt(offset)));
                        startIdx = offset;
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
            offset++;
        }

        // See if we can make a date out of what we've just found
        boolean isDate = tokenizeDate(tokens, str, startIdx, offset, hasComma, hasPoint, hasSlash, hasDash);

        // If it wasn't a date
        if (!isDate) {

            // If there isn't a point
            if (!hasPoint && offset < str.length() - 1) {
                // Look for a suffix (e.g. 1st, 2nd, 3rd or 4th
                char suffixCh  = str.charAt(offset);
                char suffixCh2 = str.charAt(offset + 1);
                if (
                        (suffixCh == 's' && suffixCh2 == 't') ||
                            (suffixCh =='n' && suffixCh2 == 'd') ||
                            (suffixCh == 'r' && suffixCh2 == 'd') ||
                            (suffixCh == 't' && suffixCh2 == 'h')
                        ) {
                    // Ensure that there is punctuation after the suffix, in which case, include it
                    if (offset == str.length() - 2 || !Character.isLetterOrDigit(str.charAt(offset + 2))) {
                        offset += 2;
                    }
                }
            }

            // Add the token to the list
            if (offset - startIdx > 2) {
                tokens.add(str.substring(startIdx, offset));
            }
        }

        // Return the end point of parsing
        return offset;
    }

    /**
     * Look for a digit as the next significant character
     *
     * @param str String to check
     * @param offset Offset to start looking from
     * @return true if number, false if letter
     */
    private boolean peekForDigitFromOffset(String str, int offset) {
        char ch;
        do {
            ch = str.charAt(offset);
            if (Character.isDigit(ch)) {
                return true;
            }
            offset++;
        } while (offset < str.length() && (ch == ',' || ch == '.' || ch == '-' || ch == '/'));

        return false;
    }

    /**
     * Look to see if the substring can be treated as a date
     * @param tokens List of tokens to add to
     * @param str String to parse
     * @param startIdx start of substring
     * @param endIdx end of substring
     * @param hasComma is there a comma in the substring
     * @param hasPoint is there a period in the substring
     * @param hasSlash is there a slash in the substring
     * @param hasDash is there a dash in the substring
     * @return
     */
    private boolean tokenizeDate(List<String> tokens, String str, int startIdx, int endIdx, boolean hasComma, boolean hasPoint, boolean hasSlash, boolean hasDash) {
        if (!hasComma && endIdx - startIdx > 5 && endIdx - startIdx < 11) {
            if (
                    (hasPoint  && !hasSlash && !hasDash) ||
                    (!hasPoint && hasDash   && !hasSlash) ||
                    (!hasPoint && !hasDash  && hasSlash)
            ) {
                char separator = hasPoint ? '.' : (hasSlash ? '/' : '-');
                if (endIdx < str.length()) {
                    return tokenizeDate(tokens, str.substring(startIdx, endIdx), separator);
                } else {
                    return tokenizeDate(tokens, str.substring(startIdx), separator);
                }
            }
        }

        return false;
    }

    /**
     * Check data formatting and add to token list
     *
     * @param tokens List of tokens
     * @param str Potential date string
     * @param separator Separator in use
     * @return true if date, false if not
     */
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

    /**
     * Create a token using word rules
     *
     * @param tokens List of tokens to add to
     * @param str String to parse
     * @param offset Offset to parse from
     * @return
     */
    private int tokenizeWordAtOffest(List<String> tokens, String str, int offset) {
        // Extract the next word
        StringBuilder word = new StringBuilder();
        char ch = str.charAt(offset);
        do {
            word.append(ch);
            offset++;
            if (offset < str.length()) {
                ch = str.charAt(offset);
            }
        } while (offset < str.length() && Character.isLetter(str.charAt(offset)));

        // If we have a word, apply the tokenizing rules
        if (word.length() > 0) {
            tokenizeWord(tokens, word.toString());
        }

        return offset;
    }

    /**
     * Apply tokenizing rules to the word
     *
     * @param tokens List of tokens to add to
     * @param word Word to parse
     */
    private void tokenizeWord(List<String> tokens, String word) {
        do {
            // If the word is less than three characters
            if (word.length() < 3) {
                // Check if it is an important word
                if (keepShortWord(word)) {
                    tokens.add(KEEP_FLAG + word);
                }

                // Finished processing
                return;
            }

            // Is this a stop word?
            if (isStopword(word)) {
                return;
            }

            // Expand any acronyms
            word = expandAcronym(word);

            // If there is a prefix, split the word otherwise add it to the token list
            word = addTokenOrPrefix(tokens, word);
        } while (word != null && word.length() > 1);
    }

    /**
     * Check for known stop words
     *
     * @param word String to check
     * @return true if stopword, false if not
     */
    private boolean isStopword(String word) {
        return "and".equals(word) ||
            "the".equals(word) ||
            "this".equals(word) ||
            "that".equals(word) ||
            "with".equals(word) ||
            "for".equals(word) ||
            "not".equals(word) ||
            "you".equals(word) ||
            "but".equals(word) ||
            "from".equals(word);
    }

    /**
     * Check for important short words
     *
     * @param word Word to check
     * @return true if short word, false if not
     */
    private boolean keepShortWord(String word) {
        return "mu".equals(word) ||
            "nu".equals(word) ||
            "pi".equals(word) ||
            "xi".equals(word);
    }

    /**\
     * Expand known acronyms
     *
     * @param word Word to check
     * @return The expanded word, or the original one
     */
    private String expandAcronym(String word) {
        if ("msm".equals(word)) {
            return "microsimulation";
        } else if ("thz".equals(word)) {
            return "terahertz";
        }

        return word;
    }

    /**
     * Check token for prefix. If we have a known prefix, add the prefix to the token list, and split from the word
     *
     * @param tokens Tokens to add to
     * @param word Word to check
     * @return Remainder of word to process
     */
    private String addTokenOrPrefix(List<String> tokens, String word) {
        if (word.length() > 8) {
            if (addPrefix(tokens, word, "diphenyl")) { return word.substring(8); }
        }

        if (word.length() > 7) {
            if (addPrefix(tokens, word, "electro")) { return word.substring(7); }
            if (addPrefix(tokens, word, "magneto")) { return word.substring(7); }
            if (addPrefix(tokens, word, "phospho")) { return word.substring(7); }
        }

        if (word.length() > 6) {
            if (addPrefix(tokens, word, "chloro")) { return word.substring(6); }
            if (addPrefix(tokens, word, "methyl")) { return word.substring(6); }
            if (addPrefix(tokens, word, "psuedo")) { return word.substring(6); }
            if (addPrefix(tokens, word, "school")) { return word.substring(6); }
            if (addPrefix(tokens, word, "thermo")) { return word.substring(6); }
        }

        if (word.length() > 5) {
            if (addPrefix(tokens, word, "flexi")) { return word.substring(5); }
            if (addPrefix(tokens, word, "infra")) { return word.substring(5); }
            if (addPrefix(tokens, word, "inter")) { return word.substring(5); }
            if (addPrefix(tokens, word, "intra")) { return word.substring(5); }
            if (addPrefix(tokens, word, "macro")) { return word.substring(5); }
            if (addPrefix(tokens, word, "micro")) { return word.substring(5); }
            if (addPrefix(tokens, word, "multi")) { return word.substring(5); }
            if (addPrefix(tokens, word, "nitro")) { return word.substring(5); }
            if (addPrefix(tokens, word, "north")) { return word.substring(5); }
            if (addPrefix(tokens, word, "quasi")) { return word.substring(5); }
            if (addPrefix(tokens, word, "socio")) { return word.substring(5); }
            if (addPrefix(tokens, word, "south")) { return word.substring(5); }
            if (addPrefix(tokens, word, "super")) { return word.substring(5); }
            if (addPrefix(tokens, word, "tetra")) { return word.substring(5); }
            if (addPrefix(tokens, word, "trade")) { return word.substring(5); }
            if (addPrefix(tokens, word, "trans")) { return word.substring(5); }
            if (addPrefix(tokens, word, "ultra")) { return word.substring(5); }
        }

        if (word.length() > 4) {
            if (addPrefix(tokens, word, "anti")) { return word.substring(4); }
            if (addPrefix(tokens, word, "east")) { return word.substring(4); }
            if (addPrefix(tokens, word, "meth")) { return word.substring(4); }
            if (addPrefix(tokens, word, "nano")) { return word.substring(4); }
            if (addPrefix(tokens, word, "octa")) { return word.substring(4); }
            if (addPrefix(tokens, word, "omni")) { return word.substring(4); }
            if (addPrefix(tokens, word, "para")) { return word.substring(4); }
            if (addPrefix(tokens, word, "post")) { return word.substring(4); }
            if (addPrefix(tokens, word, "semi")) { return word.substring(4); }
            if (addPrefix(tokens, word, "west")) { return word.substring(4); }
        }

        if (word.length() > 3) {
            if (addPrefix(tokens, word, "bio")) { return word.substring(3); }
            if (addPrefix(tokens, word, "fro")) { return word.substring(3); }
            if (addPrefix(tokens, word, "hot")) { return word.substring(3); }
            if (addPrefix(tokens, word, "mid")) { return word.substring(3); }
            if (addPrefix(tokens, word, "non")) { return word.substring(3); }
            if (addPrefix(tokens, word, "pre")) { return word.substring(3); }
            if (addPrefix(tokens, word, "sub")) { return word.substring(3); }
            if (addPrefix(tokens, word, "syn")) { return word.substring(3); }
            if (addPrefix(tokens, word, "tri")) { return word.substring(3); }
        }

        tokens.add(word);
        return null;
    }

    /**
     * Add prefix to the token list
     *
     * @param tokens Tokens to add to
     * @param word Word to check
     * @param prefix Prefix to look for
     * @return true if we had a prefix, false if not
     */
    private boolean addPrefix(List<String> tokens, String word, String prefix) {
        // If word starts with prefix
        if (word.startsWith(prefix)) {
            // Add token (with a flag to indicate it's a prefix)
            tokens.add(KEEP_FLAG + prefix);

            // return true
            return true;
        }

        // No prefix, return false
        return false;
    }

    /**
     * Convert the tokens into a string
     *
     * @param tokens Tokens to process
     */
    private void tokensToString(StringBuilder builder, List<String> tokens) {
        boolean includeEndOfToken;
        int minTokenSize;

        // Decide on how large the tokens should be
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

        // Go through all the tokens
        for (String token : tokens) {
            // Format the token
            String formatted = formatToken(token, minTokenSize, includeEndOfToken);

            // If we have a token, add it to the string
            if (formatted != null && !"".equals(formatted)) {
                if (builder.length() > 0) {
                    builder.append(TOKEN_SEPERATOR);
                }
                builder.append(formatted);
            }
        }
    }

    /**
     * Format the token for output
     *
     * @param token Token to format
     * @param minTokenSize Minimum token size required
     * @param includeEndOfToken Flag to include the end of token
     * @return The formatted token
     */
    private String formatToken(String token, int minTokenSize, boolean includeEndOfToken) {
        StringBuilder builder = new StringBuilder();
        boolean vowel = false;
        boolean consonant = false;

        // If the token is flagged to be kept, strip the flag but keep the token
        if (token.startsWith(KEEP_FLAG)) {
            return token.substring(KEEP_FLAG.length());
        }

        // If the token is numeric, keep it
        if (Character.isDigit(token.charAt(0))) {
            builder.append(token);
        }

        // For all words, find the minimum characters, including vowels and consonants
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

        // Add start of buffer to the token
        if (idx < token.length()) {
            builder.append(token.substring(0, idx));
        }

        // If we are keeping the end of the token, do the above, backwards
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
                builder.append(MIDTOKEN_SEPERATOR);
                builder.append(token.substring(idx));
            }
        }

        // Return the retained token
        return builder.toString();
    }

    /**
     * Add the trailing numeric to the string
     *
     * @param builder
     * @param str
     */
    private void addTrailingNumeric(StringBuilder builder, String str) {
        // Work backwards from the end of the string
        int idx = str.length() - 1;
        while (idx > -1) {
            // If we find a number
            if (Character.isDigit(str.charAt(idx))) {
                // Keep going back to the first non-number
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
                // Check for a roman numeral
                char ch = str.charAt(idx);
                if (ch == 'i' || ch == 'v' || ch == 'x' || ch == 'l' || ch == 'd' || ch == 'm') {
                    int endIdx = idx + 1;
                    idx--;

                    while (idx > -1) {
                        ch = str.charAt(idx);
                        if (ch == 'i' || ch == 'v' || ch == 'x' || ch == 'l' || ch == 'd' || ch == 'm') {
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

    /**
     * Convert a roman numeral to decimal
     *
     * @param roman Roman numeral
     * @return integer value
     */
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
                case 'd':
                    if(lastAddition > 500) {
                        value -= 500;
                    }
                    else {
                        value += 500;
                        lastAddition = 500;
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

