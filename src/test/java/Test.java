import util.BilibiliHandler;
import util.response.episodelist.EpisodeList;
import util.response.episodelist.Pages;

import java.util.List;

/**
 * @date 2019-04-26 19:17
 */
public class Test {

    @org.junit.Test
    public void testEpisodeList() {
        EpisodeList episodeList = BilibiliHandler.getEpisodeList("40000000");
        List<Pages> pages = episodeList.getData().getPages();
        for(Pages page:pages){
            System.out.println(page.getCid());
        }
    }
}
