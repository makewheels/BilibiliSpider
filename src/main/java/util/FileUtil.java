package util;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文件工具类
 *
 * @date 2019-04-26 23:39
 */
public class FileUtil {
    /**
     * 替换文件名中的非法字符
     *
     * @param originalFileName
     * @param replacement
     * @return
     */
    public static String replaceIllegalFileName(String originalFileName, String replacement) {
        Pattern pattern = Pattern.compile("[\\s\\\\/:\\*\\?\\\"<>\\|]");
        Matcher matcher = pattern.matcher(originalFileName);
        if (replacement == null) {
            replacement = "";
        }
        return matcher.replaceAll(replacement);
    }

    /**
     * 文件大小：字节数转可读的字符串
     *
     * @param size
     * @return
     */
    public static String getSizeString(long size) {
        return size + "bytes";
    }
}
