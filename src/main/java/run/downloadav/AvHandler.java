package run.downloadav;

import util.FileUtil;
import util.download.DownloadManager;
import run.downloadav.response.episodelist.Data;
import run.downloadav.response.episodelist.EpisodeList;
import run.downloadav.response.episodelist.Pages;

import java.io.File;
import java.util.List;

/**
 * av处理器
 */
public class AvHandler {
    /**
     * 下载av
     *
     * @param aid
     * @param rootPath
     */
    public static void downloadAv(long aid, String rootPath) {
        //获取每集列表信息
        EpisodeList episodeList = BilibiliHandler.getEpisodeList(aid);
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
        //遍历处理每个p
        List<Pages> pages = data.getPages();
        for (Pages page : pages) {
            PageHandler pageHandler = new PageHandler(aid, page, folder);
            pageHandler.downloadPage();
        }
        //关线程池
        DownloadManager.shutdownExecutorService();
    }
}
