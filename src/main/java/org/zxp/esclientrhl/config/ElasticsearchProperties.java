package org.zxp.esclientrhl.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @program: esclientrhl
 * @description:
 * @author: X-Pacific zhang
 * @create: 2019-10-23 15:52
 **/
@Component
@ConfigurationProperties("elasticsearch") //描述了当前pojo对应的配置文件前缀
public class ElasticsearchProperties {
    private String host = "127.0.0.1:9200";
    private String username;
    private String password;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
