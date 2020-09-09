package org.zxp.esclientrhl.auto.autoindex;

import org.elasticsearch.action.admin.indices.alias.get.GetAliasesRequest;
import org.elasticsearch.client.RequestOptions;
import org.zxp.esclientrhl.annotation.ESMetaData;
import org.zxp.esclientrhl.auto.util.EnableESTools;
import org.zxp.esclientrhl.index.ElasticsearchIndex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.zxp.esclientrhl.util.IndexTools;
import org.zxp.esclientrhl.util.MetaData;
import org.zxp.esclientrhl.util.Tools;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * program: esdemo
 * description: 用于扫描ESMetaData注解的类，并自动创建索引mapping
 * 启动时调用，但如果需要让spring知道哪些bean配置了ESMetaData注解，需要ElasticProcessor
 * author: X-Pacific zhang
 * create: 2019-01-30 18:43
 **/
@Configuration
public class CreateIndex implements ApplicationListener, ApplicationContextAware {
    @Autowired
    ElasticsearchIndex elasticsearchIndex;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ApplicationContext applicationContext;

    /**
     * 扫描ESMetaData注解的类，并自动创建索引mapping
     * @param event
     */
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        Map<String, Object> beansWithAnnotationMap = this.applicationContext.getBeansWithAnnotation(ESMetaData.class);
        beansWithAnnotationMap.forEach((beanName,bean) ->
                {
                    try {
                        MetaData metaData = IndexTools.getMetaData(bean.getClass());
                        if(metaData.isAlias()) {//当配置了别名后自动创建索引功能将失效
                            elasticsearchIndex.createAlias(bean.getClass());
                        }else if(!elasticsearchIndex.exists(bean.getClass())){
                            elasticsearchIndex.createIndex(bean.getClass());
                            if(EnableESTools.isPrintregmsg()) {
                                logger.info("创建索引成功，索引名称："+metaData.getIndexname()+"索引类型："+metaData.getIndextype());
                            }
                        }
                    } catch (Exception e) {
                        logger.error("创建索引不成功",e);
                    }
                }
        );
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
