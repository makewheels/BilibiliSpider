package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
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
     * 合并文件
     *
     * @param files   碎片
     * @param outFile 目标文件
     */
    public static void mergeFiles(File[] files, File outFile) {
        FileChannel outChannel = null;
        try {
            outChannel = new FileOutputStream(outFile).getChannel();
            for (File f : files) {
                Charset charset = Charset.forName("utf-8");
                CharsetDecoder chdecoder = charset.newDecoder();
                CharsetEncoder chencoder = charset.newEncoder();
                FileChannel fc = new FileInputStream(f).getChannel();
                ByteBuffer bb = ByteBuffer.allocate(1024 * 8);
                CharBuffer charBuffer = chdecoder.decode(bb);
                ByteBuffer nbuBuffer = chencoder.encode(charBuffer);
                while (fc.read(nbuBuffer) != -1) {
                    bb.flip();
                    nbuBuffer.flip();
                    outChannel.write(nbuBuffer);
                    bb.clear();
                    nbuBuffer.clear();
                }
                fc.close();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (outChannel != null) {
                    outChannel.close();
                }
            } catch (IOException ignore) {
            }
        }
    }
}
