package util;

import com.alibaba.fastjson.JSON;
import util.response.episodelist.EpisodeList;

/**
 * 处理类
 *
 * @date 2019-04-26 18:31
 */
public class BilibiliHandler {


    /**
     * 返回一个av的，每一集的列表信息
     *
     * @param aid
     * @return
     */
    public static EpisodeList getEpisodeList(String aid) {
        String json = HttpUtil.get("https://api.bilibili.com/x/web-interface/view?aid=" + aid);
        return JSON.parseObject(json, EpisodeList.class);
    }

    public static void getVideoDownloadUrl(){

    }
}
