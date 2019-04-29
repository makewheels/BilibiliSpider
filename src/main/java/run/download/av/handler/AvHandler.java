package run.download.av.handler;

import run.download.av.frame.DownloadItemPanel;
import run.download.av.frame.MainFrame;
import run.download.av.response.episodelist.Data;
import run.download.av.response.episodelist.EpisodeList;
import run.download.av.response.episodelist.Pages;
import util.FileUtil;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import java.io.File;
import java.util.List;

/**
 * av处理器
 */
public class AvHandler {
    private static EpisodeList episodeList;
    private static MainFrame mainFrame;

    /**
     * 下载av
     *
     * @param aid
     * @param rootPath
     * @param mmainFrame
     */
    public static void downloadAv(long aid, String rootPath, MainFrame mmainFrame) {
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
        mainFrame = mmainFrame;
        //初始化ui
        initUi();
        //关线程池
//        DownloadManager.shutdownExecutorService();
    }

    //初始化ui
    private static void initUi() {
        String[] tableHeader = new String[]{"few", "ewga", "ewg", "gew", "ewfg"};
        JTable table = new JTable(new Object[][]{}, tableHeader);
        table.setBounds(10, 200, 700, 400);
        //遍历处理每个p
        Data data = episodeList.getData();
        List<Pages> pages = data.getPages();
        for (Pages page : pages) {
//            PageHandler pageHandler = new PageHandler(aid, page, folder);
//            pageHandler.downloadPage();
            DownloadItemPanel downloadItemPanel = new DownloadItemPanel(page.getPage(), page.getPart());
            table.add(downloadItemPanel);
        }
        mainFrame.add(table);
    }

}
