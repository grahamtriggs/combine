package combine.transformers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Handle inconsistent hyphenization of some words
 */
public class HyphensTransformer implements StringTransformer {
    private final Pattern pattern = Pattern.compile("((?<pre1>\\w+-),\\s*)*(?<pre2>\\w+-)(,?\\s*and\\s*)(?<pre3>\\w+-)(?<post>[\\w-]+)");
    @Override
    public String transform(String str) {
        if (str.contains("-")) {
            StringBuffer buffer = new StringBuffer();
            Matcher matcher = pattern.matcher(str);
            while (matcher.find()) {
                String post = matcher.group("post");
                if (post != null && !post.isEmpty()) {
                    StringBuilder replacement = new StringBuilder();

                    String pre1 = matcher.group("pre1");
                    if (pre1 != null && !pre1.isEmpty()) {
                        if (replacement.length() > 0) {
                            replacement.append(" ");
                        }
                        replacement.append(pre1).append(post);
                    }

                    String pre2 = matcher.group("pre2");
                    if (pre2 != null && !pre2.isEmpty()) {
                        if (replacement.length() > 0) {
                            replacement.append(" ");
                        }
                        replacement.append(pre2).append(post);
                    }

                    String pre3 = matcher.group("pre3");
                    if (pre3 != null && !pre3.isEmpty()) {
                        if (replacement.length() > 0) {
                            replacement.append(" ");
                        }
                        replacement.append(pre3).append(post);
                    }

                    matcher.appendReplacement(buffer, replacement.toString());
                } else {
                    matcher.appendReplacement(buffer, "");
                }
            }

            matcher.appendTail(buffer);
            return buffer.toString();
        }
        return str;
    }
}
