package org.zxp.esclientrhl.util;

import org.springframework.util.StringUtils;
import org.zxp.esclientrhl.annotation.ESMapping;
import org.zxp.esclientrhl.annotation.ESMetaData;

import java.lang.reflect.Field;

/**
 * program: esdemo
 * description: 索引信息操作工具类
 * author: X-Pacific zhang
 * create: 2019-01-29 14:29
 **/
public class IndexTools {
    /**
     * 获取索引元数据：indexname、indextype
     * @param clazz
     * @return
     */
    public static MetaData getIndexType(Class<?> clazz){
        String indexname = "";
        String indextype = "";
        if(clazz.getAnnotation(ESMetaData.class) != null){
            indexname = clazz.getAnnotation(ESMetaData.class).indexName();
            indextype = clazz.getAnnotation(ESMetaData.class).indexType();
            if(indextype == null || indextype.equals("")){indextype = "_doc";}
            MetaData metaData = new MetaData(indexname,indextype);
            metaData.setPrintLog(clazz.getAnnotation(ESMetaData.class).printLog());
            if(Tools.arrayISNULL(clazz.getAnnotation(ESMetaData.class).searchIndexNames())) {
                metaData.setSearchIndexNames(new String[]{indexname});
            }else{
                metaData.setSearchIndexNames((clazz.getAnnotation(ESMetaData.class).searchIndexNames()));
            }
            return metaData;
        }
        return null;
    }

    /**
     * 获取索引元数据：主分片、备份分片数的配置
     * @param clazz
     * @return
     */
    public static MetaData getShardsConfig(Class<?> clazz){
        int number_of_shards = 0;
        int number_of_replicas = 0;
        if(clazz.getAnnotation(ESMetaData.class) != null){
            number_of_shards = clazz.getAnnotation(ESMetaData.class).number_of_shards();
            number_of_replicas = clazz.getAnnotation(ESMetaData.class).number_of_replicas();
            MetaData metaData = new MetaData(number_of_shards,number_of_replicas);
            metaData.setPrintLog(clazz.getAnnotation(ESMetaData.class).printLog());
            return metaData;
        }
        return null;
    }

    /**
     * 获取索引元数据：indexname、indextype、主分片、备份分片数的配置
     * @param clazz
     * @return
     */
    public static MetaData getMetaData(Class<?> clazz){
        String indexname = "";
        String indextype = "";
        int number_of_shards = 0;
        int number_of_replicas = 0;
        if(clazz.getAnnotation(ESMetaData.class) != null){
            indexname = clazz.getAnnotation(ESMetaData.class).indexName();
            indextype = clazz.getAnnotation(ESMetaData.class).indexType();
            if(indextype == null || indextype.equals("")){indextype =  "_doc";}
            number_of_shards = clazz.getAnnotation(ESMetaData.class).number_of_shards();
            number_of_replicas = clazz.getAnnotation(ESMetaData.class).number_of_replicas();
            MetaData metaData = new MetaData(indexname,indextype,number_of_shards,number_of_replicas);
            metaData.setPrintLog(clazz.getAnnotation(ESMetaData.class).printLog());
            if(Tools.arrayISNULL(clazz.getAnnotation(ESMetaData.class).searchIndexNames())) {
                metaData.setSearchIndexNames(new String[]{indexname});
            }else{
                metaData.setSearchIndexNames((clazz.getAnnotation(ESMetaData.class).searchIndexNames()));
            }
            metaData.setAlias(clazz.getAnnotation(ESMetaData.class).alias());
            metaData.setAliasIndex(clazz.getAnnotation(ESMetaData.class).aliasIndex());
            metaData.setWriteIndex(clazz.getAnnotation(ESMetaData.class).writeIndex());
            metaData.setRollover(clazz.getAnnotation(ESMetaData.class).rollover());
            metaData.setRolloverMaxIndexAgeCondition(clazz.getAnnotation(ESMetaData.class).rolloverMaxIndexAgeCondition());
            metaData.setRolloverMaxIndexAgeTimeUnit(clazz.getAnnotation(ESMetaData.class).rolloverMaxIndexAgeTimeUnit());
            metaData.setRolloverMaxIndexDocsCondition(clazz.getAnnotation(ESMetaData.class).rolloverMaxIndexDocsCondition());
            metaData.setRolloverMaxIndexSizeCondition(clazz.getAnnotation(ESMetaData.class).rolloverMaxIndexSizeCondition());
            metaData.setRolloverMaxIndexSizeByteSizeUnit(clazz.getAnnotation(ESMetaData.class).rolloverMaxIndexSizeByteSizeUnit());
            return metaData;
        }
        return null;
    }

    /**
     * 获取配置于Field上的mapping信息，如果未配置注解，则给出默认信息
     * @param field
     * @return
     */
    public static MappingData getMappingData(Field field){
        if(field == null){
            return null;
        }
        field.setAccessible(true);
        MappingData mappingData = new MappingData();
        mappingData.setField_name(field.getName());
        if(field.getAnnotation(ESMapping.class) != null){
            ESMapping esMapping = field.getAnnotation(ESMapping.class);
            mappingData.setDatatype(esMapping.datatype().toString().replaceAll("_type",""));
//            mappingData.setAnalyzedtype(esMapping.analyzedtype().toString());
            mappingData.setAnalyzer(esMapping.analyzer().toString());
            mappingData.setNgram(esMapping.ngram());
            mappingData.setIgnore_above(esMapping.ignore_above());
            mappingData.setSearch_analyzer(esMapping.search_analyzer().toString());
            if(mappingData.getDatatype().equals("text")) {
                mappingData.setKeyword(esMapping.keyword());
            }else{
                mappingData.setKeyword(false);
            }
            mappingData.setSuggest(esMapping.suggest());
            mappingData.setAllow_search(esMapping.allow_search());
            mappingData.setCopy_to(esMapping.copy_to());
            mappingData.setNested_class(esMapping.nested_class());
            if(!StringUtils.isEmpty(esMapping.null_value())){
                mappingData.setNull_value(esMapping.null_value());
            }
        }else{
            //todo 待优化为根据类型 BigDecimal Long Integer
            mappingData.setDatatype("text");
//            mappingData.setAnalyzedtype("analyzed");
            mappingData.setAnalyzer("standard");
            mappingData.setNgram(false);
            mappingData.setIgnore_above(256);
            mappingData.setSearch_analyzer("standard");
            mappingData.setKeyword(true);
            mappingData.setSuggest(false);
            mappingData.setAllow_search(true);
            mappingData.setCopy_to("");
            mappingData.setNested_class(null);
        }
        return mappingData;
    }

    /**
     * 批量获取配置于Field上的mapping信息，如果未配置注解，则给出默认信息
     * @param clazz
     * @return
     */
    public static MappingData[] getMappingData(Class<?> clazz){
        Field[] fields = clazz.getDeclaredFields();
        MappingData[] mappingDataList = new MappingData[fields.length];
        for (int i = 0; i < fields.length; i++) {
            if(fields[i].getName().equals("serialVersionUID")){
                continue;
            }
            mappingDataList[i] = getMappingData(fields[i]);
        }
        return mappingDataList;
    }

}
