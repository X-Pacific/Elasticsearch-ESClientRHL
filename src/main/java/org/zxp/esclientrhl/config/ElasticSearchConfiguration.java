package org.zxp.esclientrhl.config;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClientBuilder;
import org.zxp.esclientrhl.util.Constant;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.util.StringUtils;

/**
 * program: esdemo
 * description: 自动配置注入restHighLevelClient
 * author: X-Pacific zhang
 * create: 2019-01-07 14:09
 **/
@Configuration
@ComponentScan("org.zxp.esclientrhl")
public class ElasticSearchConfiguration  {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${elasticsearch.host:127.0.0.1:9200}")
    private String host;

    /**
     * 连接池里的最大连接数
     */
    @Value("${elasticsearch.max_connect_total:30}")
    private Integer maxConnectTotal;

    /**
     * 某一个/每服务每次能并行接收的请求数量
     */
    @Value("${elasticsearch.max_connect_per_route:10}")
    private Integer maxConnectPerRoute;

    /**
     * http clilent中从connetcion pool中获得一个connection的超时时间
     */
    @Value("${elasticsearch.connection_request_timeout_millis:2000}")
    private Integer connectionRequestTimeoutMillis;

    /**
     * 响应超时时间，超过此时间不再读取响应
     */
    @Value("${elasticsearch.socket_timeout_millis:30000}")
    private Integer socketTimeoutMillis;

    /**
     * 链接建立的超时时间
     */
    @Value("${elasticsearch.connect_timeout_millis:2000}")
    private Integer connectTimeoutMillis;


    private RestHighLevelClient restHighLevelClient;

//    由于@Bean(destroyMethod="close")，所以不需要下面注释掉的释放方式
//    public void close() {
//        if (restHighLevelClient != null) {
//            try {
//                restHighLevelClient.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    @PreDestroy
//    public void destroyMethod() throws Exception {
//        close();
//    }

    @Bean(destroyMethod="close")//这个close是调用RestHighLevelClient中的close
    @Scope("singleton")
    public RestHighLevelClient createInstance() {
        try {
            if(StringUtils.isEmpty(host)){
                host = Constant.DEFAULT_ES_HOST;
            }
            String[] hosts = host.split(",");
            HttpHost[] httpHosts = new HttpHost[hosts.length];
            for (int i = 0; i < httpHosts.length; i++) {
                String h = hosts[i];
                httpHosts[i] = new HttpHost(h.split(":")[0], Integer.parseInt(h.split(":")[1]), "http");
            }

            RestClientBuilder builder = RestClient.builder(httpHosts);
            builder.setRequestConfigCallback(requestConfigBuilder -> {
                requestConfigBuilder.setConnectTimeout(connectTimeoutMillis);
                requestConfigBuilder.setSocketTimeout(socketTimeoutMillis);
                requestConfigBuilder.setConnectionRequestTimeout(connectionRequestTimeoutMillis);
                return requestConfigBuilder;
            });

            builder.setHttpClientConfigCallback(httpClientBuilder -> {
                httpClientBuilder.disableAuthCaching();
                httpClientBuilder.setMaxConnTotal(maxConnectTotal);
                httpClientBuilder.setMaxConnPerRoute(maxConnectPerRoute);
                return httpClientBuilder;
            });

            restHighLevelClient = new RestHighLevelClient(builder);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return restHighLevelClient;
    }
}
