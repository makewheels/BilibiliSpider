package run.downloadav;

/**
 * 下载av
 *
 * @date 2019-04-26 20:35
 */
public class Run {

    public static void main(String[] args) {
        //avid
        long aid = 4548006;
        //根路径
        String rootPath = "D:\\zBILIBILI";
        AvHandler.downloadAv(aid, rootPath);
    }

}
