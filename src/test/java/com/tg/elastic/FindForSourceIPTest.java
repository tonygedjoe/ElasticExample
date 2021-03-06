package com.tg.elastic;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tg.elastic.entity.FlowDocument;
import com.tg.elastic.entity.NetworkFlow;


public class FindForSourceIPTest {

	 @Test
	    public void searchNetflowApiSyncTest() throws IOException {
	        try (final RestHighLevelClient client = new RestHighLevelClient(
	        		RestClient.builder(
	                        new HttpHost("localhost", 9200, "http")
	                )
	        )) {
	        	
	        	QueryBuilder qb2 = QueryBuilders.matchQuery("flowList.sourceIPAddr", "10.1.1.1");
	        	QueryBuilder qb1 = QueryBuilders.boolQuery()
	        			.queryName("test query 1")
	        			.must(qb2);
	        	
	        	
	        	final SearchSourceBuilder builder = new SearchSourceBuilder()
	            		.query(qb1)
	                    .from(0)
	                    .size(5)
	                    .timeout(TimeValue.timeValueMinutes(2));

	            final SearchRequest request = new SearchRequest()
	            		.indices("testflow")
	                    .source(builder);

	            final SearchResponse response = client.search(request, RequestOptions.DEFAULT);
	            
	            
	            
	            SearchHits searchHits = response.getHits();
	            
	            Map<String, Integer> sourceMap = new HashMap<>();
	            
	            for ( SearchHit hit : searchHits.getHits()) {

	            	String jsonStr = hit.getSourceAsString();
	            	
	            	ObjectMapper mapper = new ObjectMapper();
	            	FlowDocument flowDocument = mapper.readValue(jsonStr, FlowDocument.class);
	            	
	            	for ( NetworkFlow flow : flowDocument.getFlowList()) {
	            		Integer count = 1;
	            		if ( sourceMap.containsKey(flow.getDestIPAddr())) {
	            			count = sourceMap.get(flow.getDestIPAddr());
	            			count++;
	            		}
	            		sourceMap.put(flow.getDestIPAddr(), count);
	            	}	         	
	            }
	            
	            System.out.println("10.1.1.1 talks to:");
	            
	            sourceMap.entrySet().stream()
	            	.forEach(s -> {
	            		System.out.println(s.getKey());	
	            	});
	            
	            return;
	        }
	        			
	        	
	        	
	        	
	            
	    }
}