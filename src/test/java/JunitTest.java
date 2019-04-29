import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import run.download.av.handler.BilibiliHandler;
import run.download.av.response.episodeinfo.Data;
import run.download.av.response.episodeinfo.Durl;
import run.download.av.response.episodeinfo.EpisodeInfo;
import run.download.av.response.episodelist.EpisodeList;
import run.download.av.response.episodelist.Pages;
import run.download.av.util.DownloadManager;
import util.FileUtil;
import util.MergeFlvFiles;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @date 2019-04-26 19:17
 */
public class JunitTest {

    @Test
    public void testEpisodeList() {
        EpisodeList episodeList = BilibiliHandler.getEpisodeList(40000000);
        List<Pages> pages = episodeList.getData().getPages();
        for (Pages page : pages) {
            System.out.println(page.getCid());
        }
    }

    @Test
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

    @Test
    public void handleFileNameDeleteIllegal() {
        String fileName = "weff?/<>";
        Pattern pattern = Pattern.compile("[\\s\\\\/:\\*\\?\\\"<>\\|]");
        Matcher matcher = pattern.matcher(fileName);
        fileName = matcher.replaceAll("-");
        System.out.println(fileName);
    }

    @Test
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

    @Test
    public void mergeFlvFiles() {
        String folderPath = "C:\\Users\\Administrator\\Desktop\\新建文件夹\\";
        File[] files = new File[2];
        files[0] = new File(folderPath + "1.flv");
        files[1] = new File(folderPath + "2.flv");
        File dest = new File(folderPath + "flvResult.flv");
        MergeFlvFiles mergeFlvFiles = new MergeFlvFiles();
        try {
            mergeFlvFiles.merge(files, dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testOkhttp() throws IOException {
        String url = "https://api.github.com";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = okHttpClient.newCall(request).execute();
        System.out.println(response.body().string());
    }

    @Test
    public void testOkhttpUtils() throws IOException {
        String url = "https://api.github.com";
    }

    @Test
    public void testDownload() throws IOException {
        String url = "http://upos-hz-mirrorks3u.acgvideo.com/upgcxcode/65/89/78328965/78328965-1-32.flv?e=ig8euxZM2rNcNbhg7WdVhoMzhbUVhwdEto8g5X10ugNcXBlqNxHxNEVE5XREto8KqJZHUa6m5J0SqE85tZvEuENvNC8xNEVE9EKE9IMvXBvE2ENvNCImNEVEK9GVqJIwqa80WXIekXRE9IMvXBvEuENvNCImNEVEua6m2jIxux0CkF6s2JZv5x0DQJZY2F8SkXKE9IB5QK==&deadline=1556544043&gen=playurl&nbs=1&oi=1885693412&os=ks3u&platform=pc&trid=1be0222de42b4110b9181633d3459ecf&uipk=5&upsig=551c7ef26587b4e6bf25e183921b3588&uparams=e,deadline,gen,nbs,oi,os,platform,trid,uipk";
        String path = "C:\\Users\\Administrator\\Desktop\\新建文件夹\\r.flv";
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Range", "bytes=0-");
        headerMap.put("Referer", "https://www.bilibili.com/");
        InputStream inputStream = DownloadManager.getInputStream(url, headerMap);
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(path));
        //已下载字节数
        long downloadBytes = 0;
        //拷贝字节
        byte[] buffer = new byte[1024 * 8];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
            downloadBytes += bytesRead;
            System.out.println(downloadBytes);
        }
        outputStream.flush();
    }

}
