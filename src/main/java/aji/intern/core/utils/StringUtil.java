package aji.intern.core.utils;

public class StringUtil {
    public static String maskString(String content, int postfixLength) {
        if (content == null || content.length() <= postfixLength) return content;
        return content.substring(0, postfixLength) + "*".repeat(content.length() - postfixLength);
    }
}
