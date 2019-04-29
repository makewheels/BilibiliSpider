package run.download.av.frame.download;

import javax.swing.*;
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
    private Renderer renderer;

    public Renderer getRenderer() {
        return renderer;
    }

    public Table(List<List<Object>> tableData) {
        //数据
        this.tableData = tableData;
        //模型
        model = new Model(tableData);
        setModel(model);
        //渲染
        renderer = new Renderer(tableData);
        setDefaultRenderer(Object.class, renderer);
        //表头
        JTableHeader tableHeader = getTableHeader();
        tableHeader.setBackground(Color.CYAN);
        tableHeader.setFont(new Font("黑体", Font.BOLD, 20));
        //行高
        setRowHeight(30);
        //列宽
        setAutoResizeMode(AUTO_RESIZE_OFF);
        TableColumnModel columnModel = getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(60);
        columnModel.getColumn(1).setPreferredWidth(437);
        columnModel.getColumn(2).setPreferredWidth(100);
        columnModel.getColumn(3).setPreferredWidth(80);
        columnModel.getColumn(4).setPreferredWidth(250);
        columnModel.getColumn(5).setPreferredWidth(100);
        columnModel.getColumn(6).setPreferredWidth(90);
        columnModel.getColumn(7).setPreferredWidth(110);
    }

    public void updateNo(int rowIndex, String text) {
        model.update(rowIndex, 0, text);
    }

    public void updatePageName(int rowIndex, String text) {
        model.update(rowIndex, 1, text);
    }

    public void updateSize(int rowIndex, String text) {
        model.update(rowIndex, 2, text);
    }

    public void updateVideoLength(int rowIndex, String text) {
        model.update(rowIndex, 3, text);
    }

    public void updateProgress(int rowIndex, long current, long max) {
        renderer.updateProgress(rowIndex, (int) current, (int) max);
    }

    public void updateSpeed(int rowIndex, String text) {
        model.update(rowIndex, 5, text);
    }

    public void updateTimeRemaining(int rowIndex, String text) {
        model.update(rowIndex, 6, text);
    }

    public void updateState(int rowIndex, String text) {
        model.update(rowIndex, 7, text);
    }
}
