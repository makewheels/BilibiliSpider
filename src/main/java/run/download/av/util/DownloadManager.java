package run.download.av.util;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import run.download.av.handler.PageHandler;

import java.io.*;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 下载工具类
 *
 * @date 2019-04-26 21:24
 */
public class DownloadManager {

    //多线程数量
    private static int THREAD_AMOUNT = 3;
    private static ExecutorService executorService;

    static {
        executorService = Executors.newFixedThreadPool(THREAD_AMOUNT);
    }

    /**
     * 关线程池
     */
    public static void shutdownExecutorService() {
        executorService.shutdown();
    }

    /**
     * 获得下载的输入流
     *
     * @param url
     * @param headerMap
     * @return
     */
    public static InputStream getInputStream(String url, Map<String, String> headerMap) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet();
        Set<String> keySet = headerMap.keySet();
        for (String key : keySet) {
            httpGet.setHeader(key, headerMap.get(key));
        }
        httpGet.setURI(URI.create(url));
        CloseableHttpResponse response = client.execute(httpGet);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != 200 && statusCode != 206) {
            throw new RuntimeException("http响应码异常, statusCode = " + statusCode + ", url = " + url);
        }
        return response.getEntity().getContent();
    }

    /**
     * 下载单个文件，在下载完成时回调
     *
     * @param url
     * @param size
     * @param file
     * @param aid
     * @param index       碎片索引
     * @param pageHandler
     */
    public static void downloadFile(String url, long size, File file, long aid, int index,
                                    PageHandler pageHandler) {
        //单文件多线程下载，分发任务

//        RandomAccessFile randomAccessFile;
//        try {
//            randomAccessFile = new RandomAccessFile(file, "rwd");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                //正式开始下载，设置ui界面中的状态为downloading
                pageHandler.onStartDownload();
                Map<String, String> headerMap = new HashMap<>();
                headerMap.put("Range", "bytes=0-");
                headerMap.put("Referer", "https://www.bilibili.com/video/av" + aid);
                try {
                    InputStream inputStream = getInputStream(url, headerMap);
                    BufferedOutputStream outputStream = new BufferedOutputStream(
                            new FileOutputStream(file), 1024 * 512);
                    //已下载字节数
                    long downloadBytes = 0;
                    //拷贝字节
                    byte[] buffer = new byte[1024 * 8];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                        downloadBytes += bytesRead;
                        //回调进度变化
                        pageHandler.onDownloading(index, downloadBytes);
                    }
                    outputStream.flush();
                    pageHandler.onDownloading(index, downloadBytes);
                    //下载完成，并且成功时回调
                    pageHandler.downloadPartFinishCallback(index, true);
                } catch (IOException e) {
                    e.printStackTrace();
                    //发生错误时回调
                    pageHandler.downloadPartFinishCallback(index, false);
                }
            }
        });
    }

}
