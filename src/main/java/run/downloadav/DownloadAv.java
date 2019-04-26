package run.downloadav;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import util.BilibiliHandler;
import util.download.DownloadManager;
import util.response.episodeinfo.Durl;
import util.response.episodeinfo.EpisodeInfo;
import util.response.episodelist.Data;
import util.response.episodelist.EpisodeList;
import util.response.episodelist.Pages;

import java.io.File;
import java.util.List;

/**
 * 下载av
 *
 * @date 2019-04-26 20:35
 */
public class DownloadAv {

    public static void main(String[] args) {
        //avid
        long aid = 44743619;
        //根路径
        String rootPath = "D:\\zBILIBILI";
        downloadAv(aid, rootPath);
    }

    /**
     * 下载
     *
     * @param aid
     * @param rootPath
     */
    private static void downloadAv(long aid, String rootPath) {
        //获取每集列表信息
        EpisodeList episodeList = BilibiliHandler.getEpisodeList(aid);
        Data data = episodeList.getData();
        //p总数
        int pageAmount = data.getVideos();
        //av标题
        String title = data.getTitle();
        //创建文件夹
        File folder = new File(rootPath, title);
        if (folder.exists() == false) {
            folder.mkdirs();
        }
        //遍历处理每个p
        List<Pages> pages = data.getPages();
        for (Pages page : pages) {
            handleSinglePage(aid, page, folder);
        }
    }

    /**
     * 处理单p
     *
     * @param aid
     * @param page
     * @param folder
     */
    private static void handleSinglePage(long aid, Pages page, File folder) {
        long cid = page.getCid();
        //单p名
        String pageName = page.getPart();
        //获取单p信息
        EpisodeInfo episodeInfo = BilibiliHandler.getSingleEpisodeInfo(aid, cid);
        util.response.episodeinfo.Data data = episodeInfo.getData();
        //视频格式
        String format = data.getFormat();
        //去掉数字
        format = format.replaceAll("\\d+", "");
        //获取下载地址
        List<Durl> durlList = data.getDurl();
        if (durlList == null || durlList.size() != 1) {
            throw new RuntimeException("单P有多个durl");
        }
        Durl durl = durlList.get(0);
        //视频下载地址
        String downloadUrl = null;
        String url = durl.getUrl();
        List<String> backupUrlList = durl.getBackup_url();
        //如果有url
        if (StringUtils.isNotEmpty(url)) {
            downloadUrl = url;
            //如果没有url，看有没有backupUrl
        } else if (CollectionUtils.isNotEmpty(backupUrlList)) {
            url = backupUrlList.get(0);
            //如果backupUrl也没有
        } else {
            throw new RuntimeException("没有视频下载url");
        }
        //文件大小
        long size = durl.getSize();
        //文件名
        String filename = page.getPage() + "_" + pageName + "." + format;
        //下载单p
        DownloadManager.submitMission(downloadUrl, size, new File(folder, filename), aid);
        System.out.println("Finished!");
    }
}
