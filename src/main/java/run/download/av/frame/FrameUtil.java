package run.download.av.frame;

import javax.swing.*;
import java.awt.*;

/**
 * 窗口工具类
 *
 * @date 2019-04-28 23:29
 */
public class FrameUtil {
    public static Font FONT_BUTTON = new Font("黑体", Font.PLAIN, 21);

    public static JButton newButton(String text) {
        JButton button = new JButton();
        button.setText(text);
        button.setFont(FONT_BUTTON);
        button.setSize(150, 36);
        return button;
    }

    public static JButton newButton(String text, int width) {
        JButton button = new JButton();
        button.setText(text);
        button.setFont(FONT_BUTTON);
        button.setSize(width, 36);
        return button;
    }

}
