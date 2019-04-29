package run.download.av.frame.download;


import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 下载table渲染器
 *
 * @date 2019-04-29 09:57
 */
public class Renderer extends DefaultTableCellRenderer {
    private List<RowCompoment> data = new ArrayList<>();

    public JLabel newLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("黑体", Font.PLAIN, 16));
        label.setHorizontalAlignment(CENTER);
        label.setVerticalAlignment(CENTER);
        return label;
    }

    public Renderer(List<List<Object>> tableData) {
        for (List<Object> rowData : tableData) {
            RowCompoment rowCompoment = new RowCompoment();
            rowCompoment.setNo(newLabel(rowData.get(0).toString()));
            JLabel label_pageName = newLabel(rowData.get(1).toString());
            label_pageName.setHorizontalAlignment(LEFT);
            rowCompoment.setPageName(label_pageName);
            rowCompoment.setSize(newLabel(rowData.get(2).toString()));
            rowCompoment.setVideoLength(newLabel(rowData.get(3).toString()));
            JProgressBar progressBar = new JProgressBar();
            progressBar.setMinimum(0);
            progressBar.setStringPainted(true);
            rowCompoment.setProgressBar(progressBar);
            rowCompoment.setSpeed(newLabel(rowData.get(5).toString()));
            rowCompoment.setTimeRemaining(newLabel(rowData.get(6).toString()));
            rowCompoment.setState(newLabel(rowData.get(7).toString()));
            data.add(rowCompoment);
        }
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        RowCompoment rowCompoment = data.get(row);
        switch (column) {
            case 0:
                return rowCompoment.getNo();
            case 1:
                return rowCompoment.getPageName();
            case 2:
                return rowCompoment.getSize();
            case 3:
                return rowCompoment.getVideoLength();
            case 4:
                return rowCompoment.getProgressBar();
            case 5:
                return rowCompoment.getSpeed();
            case 6:
                return rowCompoment.getTimeRemaining();
            case 7:
                return rowCompoment.getState();
            default:
                return new JLabel("");
        }
    }

    public void updateProgress(int rowIndex, int current, int max) {
        JProgressBar progressBar = data.get(rowIndex).getProgressBar();
        progressBar.setMaximum(max);
        progressBar.setValue(current);
    }
}
