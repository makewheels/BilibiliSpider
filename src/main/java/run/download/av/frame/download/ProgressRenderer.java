package run.download.av.frame.download;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * 进度条渲染
 *
 * @date 2019-04-29 22:17
 */
public class ProgressRenderer implements TableCellRenderer {
    private JProgressBar progressBar = new JProgressBar();

    public ProgressRenderer( ) {
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        progressBar.setStringPainted(true);
    }

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value != null) {
            progressBar.setValue((Integer) value);
        }
        return progressBar;
    }
}
