package run.downloadav;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import util.FileUtil;
import util.download.DownloadManager;
import run.downloadav.response.episodeinfo.Durl;
import run.downloadav.response.episodeinfo.EpisodeInfo;
import run.downloadav.response.episodelist.Pages;

import java.io.File;
import java.util.List;

/**
 * 单p处理器
 */
public class PageHandler {
    private long aid;
    private Pages page;
    private File folder;

    public PageHandler(long aid, Pages page, File folder) {
        this.aid = aid;
        this.page = page;
        this.folder = folder;
    }

    /**
     * 返回单durl的下载地址
     *
     * @param durl
     * @return
     */
    private static String getSingleDurlDownloadUrl(Durl durl) {
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
            throw new RuntimeException("没找到下载url");
        }
        return downloadUrl;
    }

    /**
     * 下载单p
     */
    public void downloadPage() {
        long cid = page.getCid();
        //单p名
        String pageName = page.getPart();
        //获取单p信息
        EpisodeInfo episodeInfo = BilibiliHandler.getSingleEpisodeInfo(aid, cid);
        run.downloadav.response.episodeinfo.Data data = episodeInfo.getData();
        //视频格式
        String format = data.getFormat();
        //去掉数字
        format = format.replaceAll("\\d+", "");
        //下载地址列表
        List<Durl> durlList = data.getDurl();
        //列表大小
        int durlListSize = durlList.size();
        //初始化下载进度
        downloadStateArray = new Boolean[durlListSize];
        //初始化碎片文件
        pieces = new File[durlListSize];
        //遍历url列表，下载所有碎片
        for (int i = 0; i < durlListSize; i++) {
            Durl durl = durlList.get(i);
            //文件大小
            long fileSize = durl.getSize();
            //序号
            int order = durl.getOrder();
            //TODO 补零
            String no = page.getPage() + "";
            //替换非法字符
            pageName = FileUtil.replaceIllegalFileName(pageName, " ");
            //文件名
            String fileName = no + "_" + pageName + "." + format;
            //如果有多个碎片，追加.part1
            if (durlListSize > 1) {
                fileName += ".part" + order;
            }
            //下载地址
            String url = getSingleDurlDownloadUrl(durl);
            //碎片文件
            File file = new File(folder, fileName);
            //添加碎片文件到数组，以备回调
            pieces[i] = file;
            //下载碎片
            DownloadManager.downloadFile(url, fileSize, file, aid, i, this);
        }
    }

    /**
     * 碎片下载进度状态
     * 默认都是null，代表还没提交下载任务
     * true代表已经下载成功
     * false代表已经下载失败
     */
    private Boolean[] downloadStateArray;
    //文件碎片数组
    private File[] pieces;

    /**
     * 单个碎片下载完成时回调
     *
     * @param index     碎片序号
     * @param isSucceed 下载是否成功
     */
    public void onSingleFileDownloadFinish(int index, boolean isSucceed) {
        int length = downloadStateArray.length;
        //如果单p不分碎片，则返回
        if (length == 1) {
            return;
        }
        //设置下载状态
        downloadStateArray[index] = isSucceed;
        //检查是不是所有的碎片下载任务都有结果了
        for (Boolean downloadState : downloadStateArray) {
            //如果有没结果的任务，则什么都不做
            if (downloadState == null) {
                return;
            }
        }
        //到这里说明所有任务都有结果了
        //检查是否所有碎片都下载成功
        boolean isAllsucceed = true;
        for (int i = 0; i < length; i++) {
            for (Boolean downloadState : downloadStateArray) {
                //只要有没下载成功的，就跳出
                if (downloadState == false) {
                    isAllsucceed = false;
                    System.err.println("有下载失败碎片: index = " + i + " file = " + pieces[i]);
                }
            }
        }
        //如果都成功
        if (isAllsucceed) {
            File pieceFile = pieces[0];
            //合并碎片
            String fileName = pieceFile.getName();
            fileName = fileName.substring(0, fileName.lastIndexOf("."));
            FileUtil.mergeFiles(pieces, new File(pieceFile.getParent(), fileName));
            //删除所有碎片
            for (File file : pieces) {
                file.delete();
            }
            //如果有失败的
        } else {
            System.err.println("单p下载结束，存在失败碎片");
        }
    }

}
