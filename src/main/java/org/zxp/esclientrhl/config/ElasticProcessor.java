package org.zxp.esclientrhl.config;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.zxp.esclientrhl.annotation.EnableESTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @program: esdemo
 * @description: spring初始化完成后通过读取启动类EnableESTools注解上entity的路径（或者不配置，取启动类所在包），得到路径后委托ESEntityScanner扫描相关路径
 * @author: X-Pacific zhang
 * @create: 2019-01-30 17:22
 **/
@Configuration
public class ElasticProcessor implements BeanFactoryPostProcessor, ApplicationContextAware, BeanFactoryAware {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ApplicationContext applicationContext;
    private BeanFactory beanFactory;
    /**
     * 扫描ESMetaData注解的类托管给spring
     * @param beanFactory
     * @throws BeansException
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Map<String,Object> beans = applicationContext.getBeansWithAnnotation(EnableESTools.class);
        List<String> basePackages = AutoConfigurationPackages.get(this.beanFactory);

        //根据@EnableESTools的配置来初始化类
        beans.forEach((beanName,bean) ->
                {
                    Class<?> anClass = bean.getClass();
                    //获取启动注解信息
                    String[] eps = anClass.getAnnotation(EnableESTools.class).entityPath();

                    List<String> pathList = new ArrayList<>();
                    for (int i = 0; i < eps.length; i++) {
                        if(!StringUtils.isEmpty(eps[i])){
                            pathList.add(eps[i]);
                        }
                    }
                    if(pathList.size() == 0){
                        eps = new String[1];
                        pathList.addAll(basePackages);
                    }
                    eps = pathList.toArray(new String[pathList.size()]);
                    //扫描entity
                    ESEntityScanner scanner = new ESEntityScanner((BeanDefinitionRegistry) beanFactory);
                    scanner.setResourceLoader(this.applicationContext);
                    scanner.scan(eps);
                }
        );
        //通过springbootstarter的方式
        if(beans == null || beans.isEmpty()){
            List<String> pathList = new ArrayList<>();
            pathList.addAll(basePackages);
            String[] eps = pathList.toArray(new String[pathList.size()]);
            //扫描entity
            ESEntityScanner scanner = new ESEntityScanner((BeanDefinitionRegistry) beanFactory);
            scanner.setResourceLoader(this.applicationContext);
            scanner.scan(eps);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
