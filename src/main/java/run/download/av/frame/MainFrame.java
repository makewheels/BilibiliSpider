package run.download.av.frame;

import run.download.av.handler.AvHandler;

import javax.swing.*;
import java.awt.*;

/**
 * 主窗口
 *
 * @date 2019-04-27 23:40
 */
public class MainFrame extends JFrame {
    private String rootPath = "D:\\zBILIBILI";

    private JPanel panel_head = new JPanel();
    private JButton btn_newDownload = FrameUtil.newButton("New Download", 170);

    public MainFrame() {
        addComponent();
        addListener();
        initFrame();
    }

    private void addComponent() {
        panel_head.setBounds(0, 10, 400, 50);
        panel_head.add(btn_newDownload);
        add(panel_head);
    }

    private void addListener() {
        //新建下载任务
        btn_newDownload.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(MainFrame.this,
                    "please input AID", "4548006");
            if (input != null) {
                long aid = Long.parseLong(input);
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        btn_newDownload.setEnabled(false);
                    }
                });
                newDownload(aid);
            }
        });
    }

    private void initFrame() {
        int width = 1300;
        int height = 800;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int locationX = screenSize.width / 2 - width / 2;
        int locationY = screenSize.height / 2 - height / 2;
        setSize(width, height);
        setLocation(locationX, locationY);
        setTitle("Bilibili av downloader");
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void newDownload(long aid) {
        AvHandler avHandler = new AvHandler(aid, rootPath, this);
        avHandler.downloadAv();
    }

}
