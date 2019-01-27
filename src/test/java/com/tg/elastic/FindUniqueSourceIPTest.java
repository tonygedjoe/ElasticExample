package com.tg.elastic;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tg.elastic.entity.FlowDocument;
import com.tg.elastic.entity.NetworkFlow;


public class FindUniqueSourceIPTest {

	 @Test
	    public void searchNetflowApiSyncTest() throws IOException {
	        try (final RestHighLevelClient client = new RestHighLevelClient(
	        		RestClient.builder(
	                        new HttpHost("localhost", 9200, "http")
	                )
	        )) {
	        	
	        	
	        	
	        	SearchRequest request = new SearchRequest("testflow");
	        	
	        	QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
	        	
	        	
	        	SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
	        	
	        	searchSourceBuilder.query(queryBuilder);
	        	
	        	request.source(searchSourceBuilder);

	            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
	            
	            
	            SearchHits searchHits = response.getHits();
	            
	            Map<String, Integer> sourceMap = new HashMap<>();
	            
	            for ( SearchHit hit : searchHits.getHits()) {

	            	String jsonStr = hit.getSourceAsString();
	            	
	            	ObjectMapper mapper = new ObjectMapper();
	            	FlowDocument flowDocument = mapper.readValue(jsonStr, FlowDocument.class);
	            	
	            	for ( NetworkFlow flow : flowDocument.getFlowList()) {
	            		Integer count = 1;
	            		if ( sourceMap.containsKey(flow.getSourceIPAddr())) {
	            			count = sourceMap.get(flow.getSourceIPAddr());
	            			count++;
	            		}
	            		sourceMap.put(flow.getSourceIPAddr(), count);
	            	}
	            	
	            	
	            }
	            
	            
	            return;
	        }
	        			
	        
	        	
	        	
	            
	    }
}