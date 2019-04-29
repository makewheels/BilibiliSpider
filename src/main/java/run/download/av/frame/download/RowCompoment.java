package run.download.av.frame.download;

import lombok.Data;

import javax.swing.*;

/**
 * 下载一行的组件
 *
 * @date 2019-04-29 10:46
 */
@Data
public class RowCompoment {
    private JLabel no;
    private JLabel pageName;
    private JLabel size;
    private JLabel videoLength;
    private JProgressBar progressBar;
    private JLabel speed;
    private JLabel timeRemaining;
    private JLabel state;

}
