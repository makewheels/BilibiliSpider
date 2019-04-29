package run.download.av.handler;

import run.download.av.frame.MainFrame;
import run.download.av.frame.download.Table;
import run.download.av.response.episodelist.Data;
import run.download.av.response.episodelist.EpisodeList;
import run.download.av.response.episodelist.Pages;
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
        //初始化ui
        initUi();

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

        //关线程池
//        DownloadManager.shutdownExecutorService();
    }

    //初始化ui
    private void initUi() {
        //表格数据
        List<List<Object>> tableData = new ArrayList<>();
        //遍历处理每个p
        Data data = episodeList.getData();
        List<Pages> pages = data.getPages();
        for (Pages page : pages) {
//            PageHandler pageHandler = new PageHandler(aid, page, folder);
//            pageHandler.downloadPage();
            //一行
            ArrayList<Object> row = new ArrayList<>();
            row.add(page.getPage());
            row.add(page.getPart());
            row.add("-");
            row.add(page.getDuration() + "s");
            row.add("-");
            row.add("-");
            row.add("-");
            row.add("waiting");
            tableData.add(row);
        }
        //表格
        Table table = new Table(tableData);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30, 65, 1230, 660);

        table.updateProgress(2, 40, 139);

        //加入主窗体
        mainFrame.add(scrollPane);
    }

}
