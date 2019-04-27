package run.download.av.handler;

import com.alibaba.fastjson.JSON;
import run.download.av.response.episodeinfo.EpisodeInfo;
import util.HttpUtil;
import run.download.av.response.episodelist.EpisodeList;

import java.util.HashMap;
import java.util.Map;

/**
 * 哔哩哔哩处理类
 *
 * @date 2019-04-26 18:31
 */
public class BilibiliHandler {

    //cookie中的SESSDATA
    private static String SESSDATA = "2a5d8151%2C1558801357%2Cc18ec541";

    /**
     * 返回一个av的，每一集的列表信息
     *
     * @param aid
     * @return
     */
    public static EpisodeList getEpisodeList(long aid) {
        String json = HttpUtil.get("https://api.bilibili.com/x/web-interface/view?aid=" + aid);
        return JSON.parseObject(json, EpisodeList.class);
    }

    /**
     * 获取每一集视频的信息，里面包含下载地址
     *
     * @param aid
     * @param cid
     * @return
     */
    public static EpisodeInfo getSingleEpisodeInfo(long aid, long cid) {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Cookie", "SESSDATA=" + SESSDATA);
        String json = HttpUtil.get("https://api.bilibili.com/x/player/playurl?avid=" + aid + "&cid=" + cid + "&qn=112", headerMap);
        return JSON.parseObject(json, EpisodeInfo.class);
    }
}