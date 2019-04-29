package run.download.av.frame.download;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * 下载table数据模型
 *
 * @date 2019-04-29 09:08
 */
public class Model extends AbstractTableModel {
    private List<String> header = new ArrayList<>();
    private List<List<Object>> data;

    public Model(List<List<Object>> data) {
        this.data = data;
        header.add("No");//序号
        header.add("Page Name");//p名
        header.add("Size");//大小
        header.add("Length");//视频时长
        header.add("Progress");//进度
        header.add("Speed");//速度
        header.add("Time");//剩余时间
        header.add("State");//状态

    }

    public void update(int rowIndex, int columnIndex, Object obj) {
        setValueAt(obj, rowIndex, columnIndex);
        data.get(rowIndex).set(columnIndex, obj);
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return header.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data.get(rowIndex).get(columnIndex);
    }

    @Override
    public String getColumnName(int column) {
        return header.get(column);
    }
}
