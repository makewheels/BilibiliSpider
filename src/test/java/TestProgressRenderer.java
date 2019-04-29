import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//VS4E -- DO NOT REMOVE THIS LINE!
public class Progressss extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTable jTable0;
    private JScrollPane jScrollPane0;
    private JTextField jTextField0;
    private JButton jButton0;
    private JTextArea jTextArea0;
    private JScrollPane jScrollPane1;
    private static final String PREFERRED_LOOK_AND_FEEL = "javax.swing.plaf.metal.MetalLookAndFeel";

    public Progressss() {
        initComponents();

//添加进度条
        TableColumn statusColumn = jTable0.getColumnModel().getColumn(1);
        statusColumn.setCellRenderer(new ProgressCellRender());


    }

    private void initComponents() {
        setSize(535, 240);
    }

    private JScrollPane getJScrollPane1() {
        if (jScrollPane1 == null) {
            jScrollPane1 = new JScrollPane();
            jScrollPane1.setViewportView(getJTextArea0());
        }
        return jScrollPane1;
    }

    private JTextArea getJTextArea0() {
        if (jTextArea0 == null) {
            jTextArea0 = new JTextArea();
            jTextArea0.setText("");
        }
        return jTextArea0;
    }

    private JButton getJButton0() {
        if (jButton0 == null) {
            jButton0 = new JButton();
            jButton0.setText("jButton0");
            jButton0.addMouseListener(new MouseAdapter() {

                public void mouseClicked(MouseEvent event) {
                    jButton0MouseMouseClicked(event);
                }
            });
        }
        return jButton0;
    }

    private JTextField getJTextField0() {
        if (jTextField0 == null) {
            jTextField0 = new JTextField();
            jTextField0.setText("");
        }
        return jTextField0;
    }

    private JScrollPane getJScrollPane0() {
        if (jScrollPane0 == null) {
            jScrollPane0 = new JScrollPane();
            jScrollPane0.setViewportView(getJTable0());
        }
        return jScrollPane0;
    }

    private JTable getJTable0() {
        if (jTable0 == null) {
            jTable0 = new JTable();
            jTable0.setModel(new DefaultTableModel(new Object[][]{{"55", "66",}, {"77", "88",},}, new String[]{"name", "pass",}) {
                private static final long serialVersionUID = 1L;
                Class<?>[] types = new Class<?>[]{Object.class, Object.class,};

                public Class<?> getColumnClass(int columnIndex) {
                    return types[columnIndex];
                }
            });
        }
        return jTable0;
    }

    private static void installLnF() {
        try {
            String lnfClassname = PREFERRED_LOOK_AND_FEEL;
            if (lnfClassname == null)
                lnfClassname = UIManager.getCrossPlatformLookAndFeelClassName();
            UIManager.setLookAndFeel(lnfClassname);
        } catch (Exception e) {
            System.err.println("Cannot install " + PREFERRED_LOOK_AND_FEEL
                    + " on this platform:" + e.getMessage());
        }
    }

    /**
     * Main entry of the class.
     * Note: This class is only created so that you can easily preview the result at runtime.
     * It is not expected to be managed by the designer.
     * You can modify it as you like.
     */
    public static void main(String[] args) {
        installLnF();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Progressss frame = new Progressss();
                frame.setDefaultCloseOperation(Progressss.EXIT_ON_CLOSE);
                frame.setTitle("Progressss");
                frame.getContentPane().setPreferredSize(frame.getSize());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }


    /**
     * 进度条
     */
    public class ProgressCellRender extends DefaultTableCellRenderer {
        public ProgressCellRender() {
        }

        public Component getTableCellRendererComponent(JTable table,
                                                       Object value, boolean isSelected, boolean hasFocus, int row,
                                                       int column) {
            this.setOpaque(true);//render是否透明

            JProgressBar progressBar = new JProgressBar();

            progressBar.setMinimum(0);
            progressBar.setMaximum(100);
            progressBar.setValue(0);
//进度条边框线
//progressBar.setBorder(BorderFactory.createEmptyBorder());
            progressBar.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
            progressBar.setForeground(new Color(0, 255, 128));//前景色
            progressBar.setStringPainted(true);//是否显示ToolTipText
            if (value != null) {
                int progressValue = ((Integer.valueOf(value.toString()))).intValue();
                progressBar.setValue(progressValue);
//设置背景色
                if (isSelected) {
                    progressBar.setBackground(new Color(206, 207, 255));
                } else {
                    progressBar.setBackground(Color.white);
                }
            }
            progressBar.setToolTipText(String.valueOf(progressBar.getValue())
                    + "% ");
            return progressBar;
        }
    }

    private void jButton0MouseMouseClicked(MouseEvent event) {
        String vv = jTextField0.getText().trim().toString();
        if (this.isMatchesNumber(vv)) {
            jTable0.getModel().setValueAt(vv, 1, 1);
            jTextArea0.setText("value--" + jTable0.getModel().getValueAt(1, 1).toString());
        } else {
            JOptionPane.showMessageDialog(null, "请输入正整数");
            jTextField0.setText("");
        }

    }

    //是否正整数
    public boolean isMatchesNumber(String bot) {
        boolean flag = false;
        try {
            String regex = "^[1-9]+\\d{0,1}$";
//String regex="^[[1-9]{1}\\d{0,1}|[1-9]{1}[2]{0,1}]$";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(bot);
            if (m.find()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

}