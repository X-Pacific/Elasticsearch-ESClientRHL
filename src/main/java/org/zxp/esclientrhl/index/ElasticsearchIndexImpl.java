package org.zxp.esclientrhl.index;

import org.zxp.esclientrhl.util.IndexTools;
import org.zxp.esclientrhl.util.MappingData;
import org.zxp.esclientrhl.util.MetaData;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * program: esdemo
 * description: 索引结构基础方法实现类
 * author: X-Pacific zhang
 * create: 2019-01-29 10:05
 **/
@Component
public class ElasticsearchIndexImpl<T> implements ElasticsearchIndex<T> {
    @Autowired
    RestHighLevelClient client;
    private static final String NESTED = "nested";


    @Override
    public void createIndex(Class<T> clazz) throws Exception{
        MetaData metaData = IndexTools.getMetaData(clazz);
        CreateIndexRequest request = new CreateIndexRequest(metaData.getIndexname());

        StringBuffer source = new StringBuffer();
        source.append("  {\n" +
                "    \""+metaData.getIndextype()+"\": {\n" +
                "      \"properties\": {\n");
        MappingData[] mappingDataList = IndexTools.getMappingData(clazz);

        boolean isNgram = false;
        for (int i = 0; i < mappingDataList.length; i++) {
            MappingData mappingData = mappingDataList[i];
            if(mappingData == null || mappingData.getField_name() == null){
                continue;
            }
            source.append(" \""+mappingData.getField_name()+"\": {\n");
            source.append(" \"type\": \""+mappingData.getDatatype()+"\"\n");

            if (!mappingData.getDatatype().equals(NESTED)) {
                if (mappingData.isNgram() &&
                        (mappingData.getDatatype().equals("text") || mappingData.getDatatype().equals("keyword"))) {
                    isNgram = true;
                }
                source.append(oneField(mappingData));
            } else {
                source.append(" ,\"properties\": { ");
                if(mappingData.getNested_class() != null && mappingData.getNested_class() != Object.class){
                    MappingData[] submappingDataList = IndexTools.getMappingData(mappingData.getNested_class());
                    for (int j = 0; j < submappingDataList.length; j++) {
                        MappingData submappingData = submappingDataList[j];
                        if(submappingData == null || submappingData.getField_name() == null){
                            continue;
                        }
                        source.append(" \""+submappingData.getField_name()+"\": {\n");
                        source.append(" \"type\": \""+submappingData.getDatatype()+"\"\n");

                        if(j == submappingDataList.length - 1){
                            source.append(" }\n");
                        }else{
                            source.append(" },\n");
                        }
//子对象暂不支持配置复杂mapping
//                        source.append(oneField(mappingDataList[j]));
                    }
                }else{
                    throw new Exception("无法识别的Nested_class");
                }
                source.append(" }");
            }
            if(i == mappingDataList.length - 1){
                source.append(" }\n");
            }else{
                source.append(" },\n");
            }
        }
        source.append(" }\n");
        source.append(" }\n");
        source.append(" }\n");
        System.out.println(source.toString());
        if(isNgram){
            request.settings(Settings.builder()
                    .put("index.number_of_shards", metaData.getNumber_of_shards())
                    .put("index.number_of_replicas", metaData.getNumber_of_replicas())
                    .put("analysis.filter.autocomplete_filter.type","edge_ngram")
                    .put("analysis.filter.autocomplete_filter.min_gram",1)
                    .put("analysis.filter.autocomplete_filter.max_gram",20)
                    .put("analysis.analyzer.autocomplete.type","custom")
                    .put("analysis.analyzer.autocomplete.tokenizer","standard")
                    .putList("analysis.analyzer.autocomplete.filter",new String[]{"lowercase","autocomplete_filter"})
            );
        }else{
            request.settings(Settings.builder()
                    .put("index.number_of_shards", metaData.getNumber_of_shards())
                    .put("index.number_of_replicas", metaData.getNumber_of_replicas())
            );
        }

        request.mapping(metaData.getIndextype(),//类型定义
                source.toString(),//类型映射，需要的是一个JSON字符串
                XContentType.JSON);
        try {
            CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
            //返回的CreateIndexResponse允许检索有关执行的操作的信息，如下所示：
            boolean acknowledged = createIndexResponse.isAcknowledged();//指示是否所有节点都已确认请求
            System.out.println(acknowledged);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 非nested mapping
     * @param mappingData
     * @return
     */
    private String oneField(MappingData mappingData) {
        StringBuilder source = new StringBuilder();
        if (!StringUtils.isEmpty(mappingData.getCopy_to())) {
            source.append(" ,\"copy_to\": \"" + mappingData.getCopy_to() + "\"\n");
        }
        if (!StringUtils.isEmpty(mappingData.getNull_value())) {
            source.append(" ,\"null_value\": \"" + mappingData.getNull_value() + "\"\n");
        }
        if (!mappingData.isAllow_search()) {
            source.append(" ,\"index\": false\n");
        }
        if (mappingData.isNgram() && (mappingData.getDatatype().equals("text") || mappingData.getDatatype().equals("keyword"))) {
            source.append(" ,\"analyzer\": \"autocomplete\"\n");
            source.append(" ,\"search_analyzer\": \"standard\"\n");

        } else if (mappingData.getDatatype().equals("text")) {
            source.append(" ,\"analyzer\": \"" + mappingData.getAnalyzer() + "\"\n");
            source.append(" ,\"search_analyzer\": \"" + mappingData.getSearch_analyzer() + "\"\n");
        }

        if (mappingData.isKeyword() && !mappingData.getDatatype().equals("keyword") && mappingData.isSuggest()) {
            source.append(" \n");
            source.append(" ,\"fields\": {\n");

            source.append(" \"keyword\": {\n");
            source.append(" \"type\": \"keyword\",\n");
            source.append(" \"ignore_above\": " + mappingData.getIgnore_above());
            source.append(" },\n");

            source.append(" \"suggest\": {\n");
            source.append(" \"type\": \"completion\",\n");
            source.append(" \"analyzer\": \"" + mappingData.getAnalyzer() + "\"\n");
            source.append(" }\n");

            source.append(" }\n");
        } else if (mappingData.isKeyword() && !mappingData.getDatatype().equals("keyword") && !mappingData.isSuggest()) {
            source.append(" \n");
            source.append(" ,\"fields\": {\n");
            source.append(" \"keyword\": {\n");
            source.append(" \"type\": \"keyword\",\n");
            source.append(" \"ignore_above\": " + mappingData.getIgnore_above());
            source.append(" }\n");
            source.append(" }\n");
        } else if (!mappingData.isKeyword() && mappingData.isSuggest()) {
            source.append(" \n");
            source.append(" ,\"fields\": {\n");
            source.append(" \"suggest\": {\n");
            source.append(" \"type\": \"completion\",\n");
            source.append(" \"analyzer\": \"" + mappingData.getAnalyzer() + "\"\n");
            source.append(" }\n");
            source.append(" }\n");
        }
        return source.toString();
    }

    @Override
    public void dropIndex(Class<T> clazz) throws Exception {
        MetaData metaData = IndexTools.getIndexType(clazz);
        String indexname = metaData.getIndexname();
        DeleteIndexRequest request = new DeleteIndexRequest(indexname);
        client.indices().delete(request, RequestOptions.DEFAULT);
    }

    @Override
    public boolean exists(Class<T> clazz) throws Exception{
        MetaData metaData = IndexTools.getIndexType(clazz);
        String indexname = metaData.getIndexname();
        String indextype = metaData.getIndextype();
        GetIndexRequest request = new GetIndexRequest();
        request.indices(indexname);
        request.types(indextype);
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        return exists;
    }
}
