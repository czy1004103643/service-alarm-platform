package com.newegg.ec.utils.httpclient;


import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author lf52
 * @date 2017/9/29
 */
public class HttpClientUtil {

    private static final String CONNECTION = "Connection";

    private static final String CONTENT_TYPE = "Content-Type";

    private static final String KEEP_ALIVE = "Keep-Alive";

    private static final String ACCEPT = "Accept";

    private static final String APPLICATION_JSON = "application/json";

    private static final String UTF8 = "utf-8";

    private static CloseableHttpClient httpclient;

    static {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setDefaultSocketConfig(SocketConfig.custom().setSoKeepAlive(true).setSoTimeout(10 * 60 * 1000).build());
        // 最大连接数
        cm.setMaxTotal(50);
        // 最大并发数
        cm.setDefaultMaxPerRoute(50);
        httpclient = HttpClientBuilder.create()
                // set keep_alive strategy
                .setKeepAliveStrategy(new ConnectionKeepAliveStrategy() {

                    /**
                     * 实例化连接池，设置连接池管理器。
                     * 这里需要以参数形式注入上面实例化的连接池管理器
                     *
                     * @return
                     */
                    @Override
                    public long getKeepAliveDuration(HttpResponse httpResponse, HttpContext httpContext) {
                        HeaderElementIterator it = new BasicHeaderElementIterator(httpResponse.headerIterator(HTTP.CONN_KEEP_ALIVE));
                        while (it.hasNext()) {
                            HeaderElement he = it.nextElement();
                            String param = he.getName();
                            String value = he.getValue();
                            if (value != null && param.equalsIgnoreCase("timeout")) {
                                try {
                                    return Long.parseLong(value) * 1000;
                                } catch (NumberFormatException ignore) {
                                }
                            }
                        }
                        return 5 * 1000;
                    }
                })
                .setConnectionManager(cm).setRetryHandler(new DefaultHttpRequestRetryHandler(2, true)).build();
        // start expiredConnections thread
        IdleConnectionMonitorThread idleConnectionMonitorThread = new IdleConnectionMonitorThread(cm);
        idleConnectionMonitorThread.start();
    }

    /**
     * 设置代理及其他配置
     * @return
     */
    public static RequestConfig getRequestConfig() {
        HttpHost proxy = new HttpHost("10.16.46.161", 3333);
        RequestConfig requestConfig = RequestConfig.custom()
                .setProxy(proxy)
                .setConnectTimeout(10000)
                .setSocketTimeout(10000)
                .setConnectionRequestTimeout(3000)
                .build();
        return requestConfig;
    }

    /**
     * @param url
     * @param postJson
     * @return
     * @throws IOException
     */
    public static String getPostResponse(String url, JSONObject postJson) throws IOException {
        HttpPost httpPost = postForm(url, postJson);
        HttpResponse response;
        String result = null;
        try {
            response = httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity, UTF8);
            }
        } finally {
            httpPost.releaseConnection();
        }
        return result;
    }

    /**
     * @param url
     * @param postJson
     * @return
     */
    private static HttpPost postForm(String url, JSONObject postJson) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader(CONNECTION, KEEP_ALIVE);
        httpPost.setHeader(CONTENT_TYPE, APPLICATION_JSON);
        httpPost.setHeader(ACCEPT, APPLICATION_JSON);
        try {
            //解决中文乱码问题
            StringEntity entity = new StringEntity(postJson.toString(), UTF8);
            httpPost.setEntity(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return httpPost;
    }

    public static String getGetResponse(String url) throws IOException {
        HttpGet httpGet = getForm(url);
        HttpResponse response;
        try {
            response = httpclient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                return EntityUtils.toString(entity, UTF8);
            }
        } finally {
            httpGet.releaseConnection();
        }
        return null;
    }

    /**
     * @param url
     * @return
     */
    private static HttpGet getForm(String url) {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(getRequestConfig());
        httpGet.setHeader(CONNECTION, KEEP_ALIVE);
        httpGet.setHeader(CONTENT_TYPE, APPLICATION_JSON);
        return httpGet;
    }

    /**
     * 通过回调的方式用户自己扩展HttpClientUtil中未实现的API：如OPTIONS TRACE HEAD等不常用的操作
     *
     * @param requestHandler
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T request(RequestHandler<T> requestHandler) throws Exception {
        return requestHandler.callback(httpclient);
    }

    static class IdleConnectionMonitorThread extends Thread {

        private final HttpClientConnectionManager connMgr;

        private volatile boolean shutdown;

        public IdleConnectionMonitorThread(HttpClientConnectionManager connMgr) {
            super("Idle Connection Monitor Thread");
            this.connMgr = connMgr;
        }

        @Override
        public void run() {
            while (!shutdown) {
                synchronized (this) {
                    try {
                        wait(1000 * 5);
                        //close expired && free up 30s connections
                        connMgr.closeExpiredConnections();
                        connMgr.closeIdleConnections(30, TimeUnit.SECONDS);
                    } catch (Throwable ignore) {
                    }
                }
            }
        }

        public void shutdown() {
            shutdown = true;
            synchronized (this) {
                notifyAll();
            }
        }
    }


}
