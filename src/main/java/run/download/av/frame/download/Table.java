package run.download.av.frame.download;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.List;

/**
 * 下载表格
 *
 * @date 2019-04-29 10:24
 */
public class Table extends JTable {
    private List<List<Object>> tableData;
    private Model model;

    public Table(List<List<Object>> tableData) {
        //表格全局字体
        setFont(new Font("黑体", Font.PLAIN, 16));
        //数据
        this.tableData = tableData;
        //模型
        model = new Model(tableData);
        setModel(model);
        //渲染
        //其它居中
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        setDefaultRenderer(Object.class, centerRenderer);
        //p名列左对齐
        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);
        getColumnModel().getColumn(1).setCellRenderer(leftRenderer);
        //进度条列
        getColumnModel().getColumn(4).setCellRenderer(new ProgressRenderer());
        //表头
        JTableHeader tableHeader = getTableHeader();
        tableHeader.setBackground(Color.CYAN);
        tableHeader.setFont(new Font("黑体", Font.BOLD, 20));
        //行高
        setRowHeight(30);
        //列宽
        setAutoResizeMode(AUTO_RESIZE_OFF);
        TableColumnModel columnModel = getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(1).setPreferredWidth(387);
        columnModel.getColumn(2).setPreferredWidth(165);
        columnModel.getColumn(3).setPreferredWidth(80);
        columnModel.getColumn(4).setPreferredWidth(250);
        columnModel.getColumn(5).setPreferredWidth(100);
        columnModel.getColumn(6).setPreferredWidth(90);
        columnModel.getColumn(7).setPreferredWidth(110);
    }

    public void updateNo(int rowIndex, String text) {
        boolean isUpdate = model.update(rowIndex, 0, text);
        if (isUpdate) {
            repaint();
        }
    }

    public void updatePageName(int rowIndex, String text) {
        boolean isUpdate = model.update(rowIndex, 1, text);
        if (isUpdate) {
            repaint();
        }
    }

    public void updateSize(int rowIndex, String text) {
        boolean isUpdate = model.update(rowIndex, 2, text);
        if (isUpdate) {
            repaint();
        }
    }

    public void updateVideoLength(int rowIndex, String text) {
        boolean isUpdate = model.update(rowIndex, 3, text);
        if (isUpdate) {
            repaint();
        }
    }

    public void updateProgress(int rowIndex, long current, long max) {
        int value = (int) (current * 100 / max);
        model.update(rowIndex, 4, value);
    }

    public void updateSpeed(int rowIndex, String text) {
        boolean isUpdate = model.update(rowIndex, 5, text);
        if (isUpdate) {
            repaint();
        }
    }

    public void updateTimeRemaining(int rowIndex, String text) {
        boolean isUpdate = model.update(rowIndex, 6, text);
        if (isUpdate) {
            repaint();
        }
    }

    public void updateState(int rowIndex, String text) {
        boolean isUpdate = model.update(rowIndex, 7, text);
        if (isUpdate) {
            repaint();
        }
    }
}
