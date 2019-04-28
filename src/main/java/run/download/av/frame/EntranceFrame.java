package run.download.av.frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * 主入口窗口
 *
 * @date 2019-04-27 23:40
 */
public class EntranceFrame extends JFrame {
    private Font font = new Font("黑体", Font.PLAIN, 26);

    private JLabel folderPath_label = new JLabel("Folder path:");
    private JTextField folderPath_textField = new JTextField();

    private JLabel delete_label = new JLabel("What you want to delete:");
    private JTextField delete_textField1 = new JTextField();
    private JTextField delete_textField2 = new JTextField();
    private JTextField delete_textField3 = new JTextField();

    private JButton rename_button = new JButton("Let's Rename!");

    public EntranceFrame() {
        addComponent();
        addListener();
        initFrame();
    }

    private void addComponent() {
        folderPath_label.setBounds(70, 30, 180, 40);
        folderPath_label.setFont(font);
        add(folderPath_label);

        folderPath_textField.setBounds(30, 80, 930, 40);
        folderPath_textField.setFont(font);
        add(folderPath_textField);

        delete_label.setBounds(70, 150, 340, 40);
        delete_label.setFont(font);
        add(delete_label);

        delete_textField1.setBounds(30, 210, 270, 40);
        delete_textField1.setFont(font);
        add(delete_textField1);

        delete_textField2.setBounds(360, 210, 270, 40);
        delete_textField2.setFont(font);
        add(delete_textField2);

        delete_textField3.setBounds(690, 210, 270, 40);
        delete_textField3.setFont(font);
        add(delete_textField3);

        rename_button.setBounds(350, 300, 300, 50);
        rename_button.setFont(font);
        add(rename_button);
    }

    private void addListener() {
        // Rename button listener
        rename_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

    }

    private void initFrame() {
        int width = 1000;
        int height = 430;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int locationX = screenSize.width / 2 - width / 2;
        int locationY = screenSize.height / 2 - height / 2;
        setSize(width, height);
        setLocation(locationX, locationY);
        setTitle("RenameTool By QB");
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new EntranceFrame();
    }
}
