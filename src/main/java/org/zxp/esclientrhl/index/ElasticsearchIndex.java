package org.zxp.esclientrhl.index;

import java.util.Map;

/**
 * program: esdemo
 * description: 索引结构基础方法接口
 * author: X-Pacific zhang
 * create: 2019-01-25 16:52
 **/
public interface ElasticsearchIndex<T> {
    /**
     * 创建索引
     * @param clazz
     * @throws Exception
     */
    public void createIndex(Class<T> clazz) throws Exception;

    /**
     * 创建索引
     * @param settings
     * @param mappingJson
     * @throws Exception
     */
    public void createIndex(Map<String,String> settings, Map<String,String[]> settingsList, String mappingJson, String indexName,String indexType) throws Exception;

    /**
     * 删除索引
     * @param clazz
     * @throws Exception
     */
    public void dropIndex(Class<T> clazz) throws Exception;

    /**
     * 索引是否存在
     * @param clazz
     * @throws Exception
     */
    public boolean exists(Class<T> clazz) throws Exception;

}
