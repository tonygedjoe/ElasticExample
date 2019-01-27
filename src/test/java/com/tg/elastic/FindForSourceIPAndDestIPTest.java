package com.tg.elastic;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
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
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tg.elastic.entity.FlowDocument;
import com.tg.elastic.entity.NetworkFlow;


public class FindForSourceIPAndDestIPTest {

	 @Test
	    public void searchNetflowApiSyncTest() throws IOException {
	        try (final RestHighLevelClient client = new RestHighLevelClient(
	        		RestClient.builder(
	                        new HttpHost("localhost", 9200, "http")
	                )
	        )) {
	        	
	        	QueryBuilder qb2 = QueryBuilders.matchQuery("flowList.sourceIPAddr", "10.1.1.1");
	        	QueryBuilder qb3 = QueryBuilders.matchQuery("flowList.destIPAddr", "10.1.1.4");
	        	QueryBuilder qb1 = QueryBuilders.boolQuery()
	        			.queryName("test query 1")
	        			.must(qb2)
	        			.must(qb3);
	        	
	        	
	        	final SearchSourceBuilder builder = new SearchSourceBuilder()
	            		.query(qb1)
	                    .from(0)
	                    .size(100)
	                    .timeout(TimeValue.timeValueMinutes(2));

	            final SearchRequest request = new SearchRequest()
	            		.indices("testflow")
	                    .source(builder);

	            final SearchResponse response = client.search(request, RequestOptions.DEFAULT);
	            
	            SearchHit[] searchHits = response.getHits().getHits();
	            
	            assertThat(searchHits.length, is(1));
	            
	            for ( SearchHit hit : response.getHits()) {
	            	
	            	String jsonStr = hit.getSourceAsString();
	            	
	            	ObjectMapper mapper = new ObjectMapper();
	            	FlowDocument flowDocument = mapper.readValue(jsonStr, FlowDocument.class);
	            	
	            	
	            	
	            	for ( NetworkFlow flow : flowDocument.getFlowList()) {
	            		assertThat(flow.getSourceIPAddr(), is("10.1.1.1"));
		            	assertThat(flow.getDestIPAddr(), is("10.1.1.4"));
		            	
		            		
	            	}
	            	
	            	
	            	
	            }
	            
	            return;
	        }
	        			
	        	
	        	
	        	
	            
	    }
}