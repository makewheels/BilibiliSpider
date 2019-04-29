package run.download.av.frame;

import lombok.Data;
import run.download.av.handler.AvHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 主窗口
 *
 * @date 2019-04-27 23:40
 */
@Data
public class MainFrame extends JFrame {
    private JPanel panel_head = new JPanel();
    private JButton btn_newDownload = FrameUtil.newButton("New Download", 170);
    private JButton btn_pause = FrameUtil.newButton("New Download", 170);
    private JButton btn_2 = FrameUtil.newButton("New Download", 170);

    public MainFrame() {
        addComponent();
        addListener();
        initFrame();
    }

    private void addComponent() {
        panel_head.setBounds(0, 0, 500, 50);
        panel_head.setBackground(Color.GREEN);
        panel_head.add(btn_newDownload);
        add(panel_head);
    }

    private void addListener() {
        //新建下载任务
        btn_newDownload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog(MainFrame.this,
                        "please input AID", "4548006");
                if (input != null) {
                    long aid = Long.parseLong(input);
                    newDownload(aid);
                }
            }
        });
    }

    private void newDownload(long aid) {
        AvHandler avHandler = new AvHandler(aid, "D:\\zBILIBILI", MainFrame.this);
        avHandler.downloadAv();
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

        newDownload(4548006);
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}
