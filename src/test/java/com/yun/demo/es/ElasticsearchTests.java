/*
package com.yun.demo.es;

import net.minidev.json.JSONValue;
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
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@SpringBootTest
public class ElasticsearchTests {

 @Autowired
 private RestHighLevelClient restHighLevelClient;

 @Test
 void testCreateIndex() throws IOException {
  //1.创建索引请求
  CreateIndexRequest request = new CreateIndexRequest("ljx666");
  //2.客户端执行请求IndicesClient，执行create方法创建索引，请求后获得响应
  CreateIndexResponse response=
    restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
  System.out.println(response);
 }

 @Test
 void testExistIndex() throws IOException {
        //1.查询索引请求
  GetIndexRequest request=new GetIndexRequest("ljx666");
        //2.执行exists方法判断是否存在
  boolean exists=restHighLevelClient.indices().exists(request,RequestOptions.DEFAULT);
  System.out.println(exists);
 }

 @Test
 void testDeleteIndex() throws IOException {
        //1.删除索引请求
  DeleteIndexRequest request=new DeleteIndexRequest("ljx666");
        //执行delete方法删除指定索引
  AcknowledgedResponse delete = restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
  System.out.println(delete.isAcknowledged());
 }

 @Test
 void testAddUser() throws IOException {
  //1.创建对象
  User user=new User("Go",21,new String[]{"内卷","吃饭"});
  //2.创建请求
  IndexRequest request=new IndexRequest("ljx666");
  //3.设置规则 PUT /ljx666/_doc/1
  //设置文档id=6，设置超时=1s等，不设置会使用默认的
  //同时支持链式编程如 request.id("6").timeout("1s");
  request.id("6");
  request.timeout("1s");

  //4.将数据放入请求，要将对象转化为json格式
  //XContentType.JSON，告诉它传的数据是JSON类型
  request.source(JSONValue.toJSONString(user), XContentType.JSON);

  //5.客户端发送请求，获取响应结果
  IndexResponse indexResponse=restHighLevelClient.index(request,RequestOptions.DEFAULT);
  System.out.println(indexResponse.toString());
  System.out.println(indexResponse.status());
 }

 @Test
 void testGetUser() throws IOException {
  //1.创建请求,指定索引、文档id
  GetRequest request=new GetRequest("ljx666","1");
  GetResponse getResponse=restHighLevelClient.get(request,RequestOptions.DEFAULT);

  System.out.println(getResponse);//获取响应结果
  //getResponse.getSource() 返回的是Map集合
  System.out.println(getResponse.getSourceAsString());//获取响应结果source中内容，转化为字符串

 }

 @Test
 void testUpdateUser() throws IOException {
  //1.创建请求,指定索引、文档id
  UpdateRequest request=new UpdateRequest("ljx666","6");
 // 注意：需要将User对象中的属性全部指定值，不然会被设置为空，如User只设置了名称，那么只有名称会被修改成功，其他会被修改为null
  User user =new User("GoGo",21,new String[]{"内卷","吃饭"});
  //将创建的对象放入文档中
  request.doc(JSONValue.toJSONString(user),XContentType.JSON);

  UpdateResponse updateResponse=restHighLevelClient.update(request,RequestOptions.DEFAULT);
  System.out.println(updateResponse.status());//更新成功返回OK
 }

 @Test
 void testDeleteUser() throws IOException {
  //创建删除请求，指定要删除的索引与文档ID
  DeleteRequest request=new DeleteRequest("ljx666","6");

  DeleteResponse updateResponse=restHighLevelClient.delete(request,RequestOptions.DEFAULT);
  System.out.println(updateResponse.status());//删除成功返回OK，没有找到返回NOT_FOUND
 }

 @Test
 void testBulkAddUser() throws IOException {
  BulkRequest bulkRequest=new BulkRequest();
  //设置超时
  bulkRequest.timeout("10s");

  ArrayList<User> list=new ArrayList<>();
  list.add(new User("Java",25,new String[]{"内卷"}));
  list.add(new User("Go",18,new String[]{"内卷"}));
  list.add(new User("C",30,new String[]{"内卷"}));
  list.add(new User("C++",26,new String[]{"内卷"}));
  list.add(new User("Python",20,new String[]{"内卷"}));

  int id=1;
  //批量处理请求
  for (User u :list){
   //不设置id会生成随机id
   bulkRequest.add(new IndexRequest("ljx666")
           .id(""+(id++))
           .source(JSONValue.toJSONString(u),XContentType.JSON));
  }

  BulkResponse bulkResponse=restHighLevelClient.bulk(bulkRequest,RequestOptions.DEFAULT);
  System.out.println(bulkResponse.hasFailures());//是否执行失败,false为执行成功
 }

 @Test
 void testSearch() throws IOException {
  SearchRequest searchRequest=new SearchRequest("ljx666");//里面可以放多个索引
  SearchSourceBuilder sourceBuilder=new SearchSourceBuilder();//构造搜索条件

  //此处可以使用QueryBuilders工具类中的方法
  //1.查询所有
  sourceBuilder.query(QueryBuilders.matchAllQuery());
  //2.查询name中含有Java的
  sourceBuilder.query(QueryBuilders.multiMatchQuery("java","name"));
  //3.分页查询
  sourceBuilder.from(0).size(5);

  //4.按照score正序排列
  //sourceBuilder.sort(SortBuilders.scoreSort().order(SortOrder.ASC));
  //5.按照id倒序排列（score会失效返回NaN）
  //sourceBuilder.sort(SortBuilders.fieldSort("_id").order(SortOrder.DESC));

  //6.给指定字段加上指定高亮样式
  HighlightBuilder highlightBuilder=new HighlightBuilder();
  highlightBuilder.field("name").preTags("<span style='color:red;'>").postTags("</span>");
  sourceBuilder.highlighter(highlightBuilder);

  searchRequest.source(sourceBuilder);
  SearchResponse searchResponse=restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);

  //获取总条数
  System.out.println(searchResponse.getHits().getTotalHits().value);
  //输出结果数据（如果不设置返回条数，大于10条默认只返回10条）
  SearchHit[] hits=searchResponse.getHits().getHits();
  for(SearchHit hit :hits){
   System.out.println("分数:"+hit.getScore());
   Map<String,Object> source=hit.getSourceAsMap();
   System.out.println("index->"+hit.getIndex());
   System.out.println("id->"+hit.getId());
   for(Map.Entry<String,Object> s:source.entrySet()){
    System.out.println(s.getKey()+"--"+s.getValue());
   }
  }
 }

}*/
