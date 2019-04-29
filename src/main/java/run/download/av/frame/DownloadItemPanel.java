package run.download.av.frame;

import lombok.Data;

import javax.swing.*;
import java.awt.*;

/**
 * 下载项
 *
 * @date 2019-04-29 01:14
 */
@Data
public class DownloadItemPanel extends JPanel {
    private JLabel label_no;
    private JLabel label_pageName;
    private JProgressBar progressBar = new JProgressBar();
    private JLabel label_speed;
    private JLabel label_progress;
    private JLabel label_state;

    public static Font FONT_LABEL = new Font("黑体", Font.PLAIN, 15);

    public JLabel newLabel(String str) {
        JLabel label = new JLabel(str);
        label.setFont(FONT_LABEL);
        return label;
    }

    public DownloadItemPanel(int no, String pageName) {
        label_no = newLabel(no + "");
        label_pageName = newLabel(pageName);
        label_speed = newLabel("");
        label_progress = newLabel("");
        label_state = newLabel("waiting");
        setSize(300, 50);
        addComponent();
    }

    public void addComponent() {
        add(label_no);
        add(label_pageName);
        add(progressBar);
        add(label_speed);
        add(label_progress);
        add(label_state);
    }

}
