package com.tg.elastic;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;

public class DeleteApiTest {

	@Test
    public void deleteApiSyncTest() {
        try (final RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")
                )
        )) {
        	
        	final SearchSourceBuilder builder = new SearchSourceBuilder()
            		.query(QueryBuilders.matchAllQuery())
                    .from(0)
                    .size(100)
                    .timeout(TimeValue.timeValueMinutes(2));

            final SearchRequest request = new SearchRequest()
            		.indices("testflow")
                    .source(builder);

            final SearchResponse response = client.search(request, RequestOptions.DEFAULT);

            SearchHit[] hits = response.getHits().getHits();
            
            for ( SearchHit hit : hits) {
            	
            	DeleteRequest deleteRequest = new DeleteRequest()
                .index("testflow")
                .type("doc")
                .id(hit.getId())
                .timeout(TimeValue.timeValueMinutes(2));
            	
            	DeleteResponse deleteResponse = client.delete(deleteRequest, RequestOptions.DEFAULT);
            	
            	System.out.println("DELETE reposonse :" + deleteResponse.getId());
                
            }
        	
            

        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
}
