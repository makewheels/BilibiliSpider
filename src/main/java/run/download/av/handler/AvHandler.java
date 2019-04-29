package run.download.av.handler;

import run.download.av.frame.MainFrame;
import run.download.av.frame.download.Table;
import run.download.av.response.episodelist.Data;
import run.download.av.response.episodelist.EpisodeList;
import run.download.av.response.episodelist.Pages;
import run.download.av.util.DownloadManager;
import util.FileUtil;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * av处理器
 */
public class AvHandler {
    private long aid;
    private String rootPath;
    private MainFrame mainFrame;
    private EpisodeList episodeList;

    public AvHandler(long aid, String rootPath, MainFrame mainFrame) {
        this.aid = aid;
        this.rootPath = rootPath;
        this.mainFrame = mainFrame;
    }

    /**
     * 下载av
     */
    public void downloadAv() {
        //获取每集列表信息
        episodeList = BilibiliHandler.getEpisodeList(aid);
        Data data = episodeList.getData();
        //p总数
        int pageAmount = data.getVideos();
        //av标题
        String title = data.getTitle();
        //替换非法字符
        title = FileUtil.replaceIllegalFileName(title, " ");
        //创建文件夹
        File folder = new File(rootPath, title);
        if (folder.exists() == false) {
            folder.mkdirs();
        }

        //初始化ui
        initUi();

        //遍历下载每个p
        List<Pages> pages = data.getPages();
        for (int i = 0; i < pages.size(); i++) {
            PageHandler pageHandler = new PageHandler(aid, pages.get(i), folder, table, i);
            pageHandler.downloadPage();
        }

        //关线程池
        DownloadManager.shutdownExecutorService();
    }

    //表格
    private Table table;

    /**
     * 时间转换
     * 将秒转换为可读时间
     * 例如：69 -> 1:09
     *
     * @param duration
     */
    private String getTimeString(int duration) {
        //小于一分钟
        if (duration < 60) {
            if (duration <= 9) {
                return "0:0" + duration;
            } else {
                return "0:" + duration;
            }
        } else {
            int second = duration % 60;
            int minute = duration / 60;
            int hour = duration / 60 / 60;
            //一分钟到一小时
            if (duration < 60 * 60) {
                if (second <= 9) {
                    return minute + ":0" + second;
                } else {
                    return minute + ":" + second;
                }
            } else {
                //大于一小时
                String time = "";
                time += hour + ":";
                if (minute <= 9) {
                    time += "0";
                }
                time += minute + ":";
                if (minute <= 9) {
                    time += "0";
                }
                time += second;
                return time;
            }
        }
    }

    //初始化ui
    private void initUi() {
        //表格数据
        List<List<Object>> tableData = new ArrayList<>();
        Data data = episodeList.getData();
        List<Pages> pages = data.getPages();
        //遍历处理每个p
        for (Pages page : pages) {
            //一行
            ArrayList<Object> row = new ArrayList<>();
            row.add(page.getPage());
            row.add(page.getPart());
            row.add("-");
            row.add(getTimeString(page.getDuration()));
            row.add(0);
            row.add("-");
            row.add("-");
            row.add("waiting");
            tableData.add(row);
        }

        //表格
        table = new Table(tableData);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30, 65, 1235, 665);

        //加入主窗体
        mainFrame.add(scrollPane);
    }

}
