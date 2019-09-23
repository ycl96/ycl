import org.apache.commons.lang3.StringUtils;

/**
 * @author : YangChunLong
 * @date : Created in 2019/9/22 20:50
 * @description: Emoji 表情 相关工具类
 * @modified By:
 * @version: :
 */
public class EmojiUtil {
    public static boolean isNotEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD)
                || ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    public static String replaceEmoji(String source) {
        if (StringUtils.isBlank(source)) {
            return source;
        }
        int len = source.length();
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isNotEmojiCharacter(codePoint)) {
                buf.append("[emoji]" + Integer.toHexString((int) codePoint) + "[/emoji]");
            } else {
                buf.append(codePoint);
            }
        }
        return buf.toString();
    }

    public static String returnEmoji(String source) {
        if (StringUtils.isBlank(source)) {
            return "";
        }
        while (source.indexOf("[emoji]") >= 0 && source.indexOf("[/emoji]") > 0) {
            int begin = source.indexOf("[emoji]");
            int end = source.indexOf("[/emoji]");
            String code = source.substring(begin + 7, end);
            char a = '.';
            try {
                a = (char) Integer.parseInt(code, 16);
            } catch (Exception e) {
                e.printStackTrace();
            }
            source = source.substring(0, begin) + a + source.substring(end + 8);
        }
        return source;
    }
}
