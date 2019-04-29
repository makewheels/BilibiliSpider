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
    private List<RowComponent> data = new ArrayList<>();
    private Font font = new Font("黑体", Font.PLAIN, 16);


    public JLabel newLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setHorizontalAlignment(CENTER);
        label.setVerticalAlignment(CENTER);
        return label;
    }

    public Renderer(List<List<Object>> tableData) {
        for (List<Object> rowData : tableData) {
            RowComponent rowComponent = new RowComponent();
            rowComponent.setNo(newLabel(rowData.get(0).toString()));
            JLabel label_pageName = newLabel(rowData.get(1).toString());
            label_pageName.setHorizontalAlignment(LEFT);
            rowComponent.setPageName(label_pageName);
            rowComponent.setSize(newLabel(rowData.get(2).toString()));
            rowComponent.setVideoLength(newLabel(rowData.get(3).toString()));
            JProgressBar progressBar = new JProgressBar();
            progressBar.setFont(font);
            progressBar.setMinimum(0);
            progressBar.setStringPainted(true);
            rowComponent.setProgressBar(progressBar);
            rowComponent.setSpeed(newLabel(rowData.get(5).toString()));
            rowComponent.setTimeRemaining(newLabel(rowData.get(6).toString()));
            rowComponent.setState(newLabel(rowData.get(7).toString()));
            data.add(rowComponent);
        }
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        RowComponent rowComponent = data.get(row);
        switch (column) {
            case 0:
                return rowComponent.getNo();
            case 1:
                return rowComponent.getPageName();
            case 2:
                return rowComponent.getSize();
            case 3:
                return rowComponent.getVideoLength();
            case 4:
                return rowComponent.getProgressBar();
            case 5:
                return rowComponent.getSpeed();
            case 6:
                return rowComponent.getTimeRemaining();
            case 7:
                return rowComponent.getState();
            default:
                return new JLabel("");
        }
    }

    public void updateProgress(int rowIndex, int current, int max) {
        JProgressBar progressBar = data.get(rowIndex).getProgressBar();
        progressBar.setMaximum(max);
        progressBar.setValue(current);
        progressBar.updateUI();
    }

}
