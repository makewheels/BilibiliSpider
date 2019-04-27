import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import run.downloadav.BilibiliHandler;
import util.download.DownloadManager;
import run.downloadav.response.episodeinfo.Data;
import run.downloadav.response.episodeinfo.Durl;
import run.downloadav.response.episodeinfo.EpisodeInfo;
import run.downloadav.response.episodelist.EpisodeList;
import run.downloadav.response.episodelist.Pages;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @date 2019-04-26 19:17
 */
public class Test {

    @org.junit.Test
    public void testEpisodeList() {
        EpisodeList episodeList = BilibiliHandler.getEpisodeList(40000000);
        List<Pages> pages = episodeList.getData().getPages();
        for (Pages page : pages) {
            System.out.println(page.getCid());
        }
    }

    @org.junit.Test
    public void testEpisodeInfo() {
        EpisodeInfo episodeInfo = BilibiliHandler.getSingleEpisodeInfo(44743619, 78328965);
        Data data = episodeInfo.getData();
        //视频格式
        String format = data.getFormat();
        //去掉数字
        format = format.replaceAll("\\d+", "");
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
        long size = durl.getSize();
        int quality = data.getQuality();
        System.out.println(downloadUrl);
        System.out.println(size);
        System.out.println(quality);
    }

    @org.junit.Test
    public void handleFileNameDeleteIllegal() {
        String fileName = "weff?/<>";
        Pattern pattern = Pattern.compile("[\\s\\\\/:\\*\\?\\\"<>\\|]");
        Matcher matcher = pattern.matcher(fileName);
        fileName = matcher.replaceAll("-");
        System.out.println(fileName);
    }

    @org.junit.Test
    public void downloadSingleDurl() {
        //https://api.bilibili.com/x/player/playurl?avid=4548006&cid=7375920&qn=112
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Range", "bytes=0-");
        headerMap.put("Referer", "https://www.bilibili.com/video/av4548006");
        String url = "http://upos-hz-mirrorbsyu.acgvideo.com/upgcxcode/20/59/7375920/7375920-2-80.flv?e=ig8euxZM2rNcNbUjhwdVhoMB7bdVhwdEto8g5X10ugNcXBlqNxHxNEVE5XREto8KqJZHUa6m5J0SqE85tZvEuENvNC8xNEVE9EKE9IMvXBvE2ENvNCImNEVEK9GVqJIwqa80WXIekXRE9IMvXBvEuENvNCImNEVEua6m2jIxux0CkF6s2JZv5x0DQJZY2F8SkXKE9IB5QK==&deadline=1556301900&gen=playurl&nbs=1&oi=1885693412&os=bsyu&platform=pc&trid=0ee1bada1bc24768b43249152313fea7&uipk=5&upsig=333e90957f51970ecf4f45ef626645eb&uparams=e,deadline,gen,nbs,oi,os,platform,trid,uipk";
        try {
            InputStream inputStream = DownloadManager.getInputStream(url, headerMap);
            IOUtils.copy(inputStream, new FileOutputStream(new File("C:\\Users\\Administrator\\Desktop\\新建文件夹\\2.flv")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
