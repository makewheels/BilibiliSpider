import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import util.BilibiliHandler;
import util.response.episodeinfo.Data;
import util.response.episodeinfo.Durl;
import util.response.episodeinfo.EpisodeInfo;
import util.response.episodelist.EpisodeList;
import util.response.episodelist.Pages;

import java.util.List;

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

}
