package aji.intern.core.utils;

public class StringUtil {
    public static String maskString(String content, int prefixLength) {
        if (content == null || content.length() <= prefixLength) return content;
        return content.substring(0, prefixLength) + "*".repeat(content.length() - prefixLength);
    }
}
