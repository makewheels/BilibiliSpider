package util.download;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
    private static int THREAD_AMOUNT = 5;
    private static ExecutorService executorService;

    static {
        executorService = Executors.newFixedThreadPool(THREAD_AMOUNT);
    }

    /**
     * 获得下载的输入流
     *
     * @param url
     * @param headerMap
     * @return
     */
    private static InputStream getInputStream(String url, Map<String, String> headerMap) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet();
        Set<String> keySet = headerMap.keySet();
        for (String key : keySet) {
            httpGet.setHeader(key, headerMap.get(key));
        }
        httpGet.setURI(URI.create(url));
        try {
            CloseableHttpResponse response = client.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200 && statusCode != 206) {
                throw new RuntimeException("http响应码异常, statusCode = " + statusCode + ", url = " + url);
            }
            HttpEntity entity = response.getEntity();
            return entity.getContent();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 提交一个下载任务
     *
     * @param url
     * @param size
     * @param file
     */
    public static void submitMission(String url, long size, File file, long aid) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                Map<String, String> headerMap = new HashMap<>();
                headerMap.put("Range", "bytes=0-");
                headerMap.put("Referer", "https://www.bilibili.com/video/av" + aid);
                InputStream inputStream = getInputStream(url, headerMap);
                try {
                    IOUtils.copy(inputStream, new FileOutputStream(file));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        executorService.shutdown();
    }
}
