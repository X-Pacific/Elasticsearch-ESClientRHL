package org.zxp.esclientrhl.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.UnsupportedEncodingException;

/**
 * @program: esclientrhl
 * @description: http客户端工具
 * @author: X-Pacific zhang
 * @create: 2019-10-10 12:53
 **/
public class HttpClientTool {
    private static HttpClient mHttpClient = null;
    public synchronized static HttpClient getESHttpClient(){
        if(mHttpClient == null){
            HttpParams params = new BasicHttpParams();
            //设置基本参数
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, Constants.CHARSET);
            HttpProtocolParams.setUseExpectContinue(params, true);
            //超时设置
            /*从连接池中取连接的超时时间*/
            ConnManagerParams.setTimeout(params, Constants.CONMANTIMEOUT);
            /*连接超时*/
            HttpConnectionParams.setConnectionTimeout(params, Constants.CONTIMEOUT);
            /*请求超时*/
            HttpConnectionParams.setSoTimeout(params, Constants.SOTIMEOUT);
            //设置HttpClient支持HTTp和HTTPS两种模式
            SchemeRegistry schReg = new SchemeRegistry();
            schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            schReg.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
            PoolingClientConnectionManager cm = new PoolingClientConnectionManager(schReg);
            cm.setMaxTotal(Constants.MAXTOTAL);
            cm.setDefaultMaxPerRoute(Constants.DEFAULTMAXPERROUTE);
            mHttpClient = new DefaultHttpClient(cm,params);
        }
        return mHttpClient;
    }

    /**
     * 执行http请求
     *
     * @param url
     * @param obj
     * @return
     * @throws Exception
     */
    public static String execute(String url, String obj) throws Exception {
        HttpClient httpClient = null;
        HttpResponse response = null;
        httpClient = HttpClientTool.getESHttpClient();
        HttpUriRequest request = postMethod(url, obj);
        response = httpClient.execute(request);
        HttpEntity entity1 = response.getEntity();
        String respContent = EntityUtils.toString(entity1, HTTP.UTF_8).trim();
        return respContent;
    }

    private static HttpUriRequest postMethod(String url, String data)
            throws UnsupportedEncodingException {
        HttpPost httpPost = new HttpPost(url);
        if (data != null) {
            httpPost.setEntity(new StringEntity(data, "UTF-8"));
        }
        httpPost.addHeader("Content-Type", "application/json");
        return httpPost;
    }

    static class Constants {
        /** 编码*/
        public static final String CHARSET = HTTP.UTF_8;
        /*从连接池中取连接的超时时间*/
        public static final int CONMANTIMEOUT = 2000;
        /*连接超时*/
        public static final int CONTIMEOUT = 2000;
        /*请求超时*/
        public static final int SOTIMEOUT = 5000;
        /*设置整个连接池最大连接数*/
        public static final int MAXTOTAL = 6;
        /*根据连接到的主机对MaxTotal的一个细分*/
        public static final int DEFAULTMAXPERROUTE = 3;
    }
}
