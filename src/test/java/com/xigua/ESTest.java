package com.xigua;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xigua.entity.User;
import com.xigua.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.*;
import org.elasticsearch.cluster.metadata.MappingMetadata;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.SearchHit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ESTest
 * @Description TODO
 * @Author wangjinfei
 * @Date 2023/12/15 14:33
 */

@SpringBootTest
public class ESTest {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RestHighLevelClient highLevelClient;


    //创建索引
    @Test
    void createIndex() throws IOException {
        Map<String, Object> mapping = new HashMap<>();
        Map<String,Object> properties = new HashMap<>();

        CreateIndexRequest userIndex = new CreateIndexRequest("user");
        Map<String,Object> id = new HashMap<>();
        id.put("type","integer");
        Map<String,Object> name = new HashMap<>();
        name.put("type","keyword");
        Map<String,Object> age = new HashMap<>();
        age.put("type","integer");
        Map<String,Object> createBy = new HashMap<>();
        createBy.put("type","keyword");
        Map<String,Object> createTime = new HashMap<>();
        createTime.put("type","date");
        createTime.put("format","yyyy-MM-dd HH:mm:ss");
        Map<String,Object> updateBy = new HashMap<>();
        updateBy.put("type","keyword");
        Map<String,Object> updateTime = new HashMap<>();
        updateTime.put("type","date");
        updateTime.put("format","yyyy-MM-dd HH:mm:ss");


        properties.put("id",id);
        properties.put("name",name);
        properties.put("age",age);
        properties.put("create_by",createBy);
        properties.put("create_time",createTime);
        properties.put("update_by",updateBy);
        properties.put("update_time",updateTime);
        mapping.put("properties",properties);
        userIndex.mapping(mapping);
        CreateIndexResponse createIndexResponse = highLevelClient.indices().create(userIndex, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse.isAcknowledged());
    }

    //获取索引mapping
    @Test
    void getMapping() throws IOException {
        IndicesClient indices = highLevelClient.indices();
        GetMappingsRequest getMappingsRequest = new GetMappingsRequest();
        getMappingsRequest.indices("user");
        GetMappingsResponse getMappingsResponse = indices.getMapping(getMappingsRequest, RequestOptions.DEFAULT);
        Map<String, MappingMetadata> mappings = getMappingsResponse.mappings();
        Map<String, Object> userProperties = mappings.get("user").getSourceAsMap();
        System.out.println(userProperties);

    }

    //删除索引
    @Test
    void deleteIndex() throws IOException {
        IndicesClient indices = highLevelClient.indices();
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest();
        deleteIndexRequest.indices("user");
        AcknowledgedResponse delete = indices.delete(deleteIndexRequest, RequestOptions.DEFAULT);
        boolean acknowledged = delete.isAcknowledged();
        System.out.println(acknowledged);
    }

    //判断索引是否存在
    @Test
    void indexExists() throws IOException {
        IndicesClient indices = highLevelClient.indices();
        GetIndexRequest getIndexRequest = new GetIndexRequest("user");
        boolean exists = indices.exists(getIndexRequest, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    @Test
    void createIndex02() throws IOException {
//        String index = "user";
//        IndicesClient indices = highLevelClient.indices();
//        GetIndexRequest getIndexRequest = new GetIndexRequest( index);
//        boolean exists = indices.exists(getIndexRequest, RequestOptions.DEFAULT);
//        System.out.println(exists);
//        if(exists){
//            DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest();
//            deleteIndexRequest.indices(index);
//            indices.delete(deleteIndexRequest, RequestOptions.DEFAULT);
//        }
    }

    //添加文档
    @Test
    void addDocument() throws IOException {
        String index = "user";
        User user = new User();
        user.setId(1);
        user.setName("zz");
        user.setAge(20);
        String jsonUser = JSONObject.toJSONString(user);

        IndexRequest source = new IndexRequest(index).id("1").source(jsonUser, XContentType.JSON);
        IndexResponse indexResponse = highLevelClient.index(source, RequestOptions.DEFAULT);
        // 处理响应结果
        if (indexResponse.getResult() == DocWriteResponse.Result.CREATED) {
            System.out.println("Document added successfully");
        } else if (indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {
            System.out.println("Document updated successfully");
        } else {
            System.out.println("Failed to add document");
        }
    }

    //获取文档
    @Test
    void getDocument() throws IOException {
        String index = "user";
        GetRequest getRequest = new GetRequest(index,"1");
        GetResponse documentFields = highLevelClient.get(getRequest, RequestOptions.DEFAULT);
        Map<String, Object> sourceAsMap = documentFields.getSourceAsMap();
        User user = JSONObject.parseObject(JSONObject.toJSONString(sourceAsMap), User.class);
        System.out.println(user);
    }

    //更新文档
    @Test
    void updateDocument() throws IOException {
        String index = "user";
        UpdateRequest updateRequest = new UpdateRequest(index, "1");
        User user = new User();
        user.setId(1);
        user.setName("zzss");
        user.setAge(30);
        updateRequest.doc(JSONObject.toJSONString(user), XContentType.JSON);
        UpdateResponse update = highLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        System.out.println(update.status());
    }

    //删除文档
    @Test
    void deleteDocument() throws IOException {
        String index = "user";
        DeleteRequest deleteRequest = new DeleteRequest(index,"1");
        DeleteResponse delete = highLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        System.out.println(delete.status());
    }

    //获取所有文档
    @Test
     void getAllDocuments() throws IOException {
        String index = "user";
        SearchRequest searchRequest = new SearchRequest(index);
        SearchResponse search = highLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHit[] hits = search.getHits().getHits();
        List<User> userList = new ArrayList<>();

//        objectMapper.re
        for (SearchHit hit : hits) {
//            System.out.println(hit);
//            objectMapper.readValue((JsonParser) hit.getSourceAsMap(),User.class);
            User user = JSONObject.parseObject(JSONObject.toJSONString(hit.getSourceAsMap()), User.class);
            userList.add(user);
        }
        for (User user : userList) {
            System.out.println(user);
        }
    }

    //批量添加
    @Test
    void bulkDocument() throws IOException {
        String index = "user";
        List<User> userList = userMapper.selectList(null);
        BulkRequest bulkRequest = new BulkRequest();
        for (User user : userList) {
            String userJson = JSONObject.toJSONString(user);
            IndexRequest indexRequest = new IndexRequest(index);
            indexRequest.source(userJson,XContentType.JSON);
            bulkRequest.add(indexRequest);
        }
        BulkResponse bulk = highLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(bulk.status());
    }
}
