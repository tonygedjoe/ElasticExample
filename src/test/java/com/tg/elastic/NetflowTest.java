package com.tg.elastic;

import java.io.IOException;
import java.util.UUID;

import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tg.elastic.entity.FlowDocument;
import com.tg.elastic.entity.NetworkCollector;
import com.tg.elastic.entity.NetworkFlow;

public class NetflowTest {

	@Test
    public void NetflowUploadTest() {
        try (final RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")
                )
        )) {
            NetworkCollector collector = new NetworkCollector();
            collector.setDeviceName("SWITCH 1");
            collector.setIpAddress("10.1.1.251");
            
            NetworkFlow flow1 = new NetworkFlow();
            flow1.setSourceIPAddr("10.1.1.1");
            flow1.setSourcePort(1024);
            flow1.setDestIPAddr("10.1.1.2");
            flow1.setDestPort(80);           
            
            NetworkFlow flow2 = new NetworkFlow();
            flow2.setSourceIPAddr("10.1.1.3");
            flow2.setSourcePort(1024);
            flow2.setDestIPAddr("10.1.1.2");
            flow2.setDestPort(21);
            
            NetworkFlow flow3 = new NetworkFlow();
            flow3.setSourceIPAddr("10.1.1.3");
            flow3.setSourcePort(1024);
            flow3.setDestIPAddr("10.1.1.4");
            flow3.setDestPort(80);
            
            NetworkFlow flow4 = new NetworkFlow();
            flow4.setSourceIPAddr("10.1.1.1");
            flow4.setSourcePort(1024);
            flow4.setDestIPAddr("10.1.1.4");
            flow4.setDestPort(445);
            
            NetworkFlow flow5 = new NetworkFlow();
            flow5.setSourceIPAddr("10.1.1.4");
            flow5.setSourcePort(1024);
            flow5.setDestIPAddr("10.1.1.3");
            flow5.setDestPort(8080);
            
            
            
            NetworkFlow flow6 = new NetworkFlow();
            flow6.setSourceIPAddr("10.1.1.4");
            flow6.setSourcePort(80);
            flow6.setDestIPAddr("10.1.1.1");
            flow6.setDestPort(1234);
            
            NetworkFlow flow7 = new NetworkFlow();
            flow7.setSourceIPAddr("10.1.1.3");
            flow7.setSourcePort(1024);
            flow7.setDestIPAddr("10.1.1.1");
            flow7.setDestPort(8080);
            
            NetworkFlow flow8 = new NetworkFlow();
            flow8.setSourceIPAddr("10.1.1.4");
            flow8.setSourcePort(1024);
            flow8.setDestIPAddr("10.1.1.2");
            flow8.setDestPort(8080);
            
            NetworkFlow flow9 = new NetworkFlow();
            flow9.setSourceIPAddr("10.1.1.2");
            flow9.setSourcePort(1024);
            flow9.setDestIPAddr("10.1.1.4");
            flow9.setDestPort(8080);
            
            
            NetworkFlow flow10 = new NetworkFlow();
            flow10.setSourceIPAddr("10.2.2.2");
            flow10.setSourcePort(1024);
            flow10.setDestIPAddr("10.2.2.4");
            flow10.setDestPort(8080);
            
            
            NetworkFlow flow11 = new NetworkFlow();
            flow11.setSourceIPAddr("10.2.2.2");
            flow11.setSourcePort(1024);
            flow11.setDestIPAddr("10.1.1.4");
            flow11.setDestPort(8080);
            
           
           doRequest(client, collector, flow1);
           doRequest(client, collector, flow2);
           doRequest(client, collector, flow3);
           doRequest(client, collector, flow4);
           doRequest(client, collector, flow5);
           doRequest(client, collector, flow6);
           doRequest(client, collector, flow7);
           doRequest(client, collector, flow8);
           doRequest(client, collector, flow9);
           doRequest(client, collector, flow10);
           doRequest(client, collector, flow11);
           
           

            return;
            
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
	
	
	
	
	
	
	
	
	
	
	
	private IndexResponse doRequest(RestHighLevelClient client, NetworkCollector collector, NetworkFlow flow) throws IOException {
		
		UUID uuid = UUID.randomUUID();
		
		
		FlowDocument doc = new FlowDocument();
        doc.setCollector(collector);
        doc.addFlow(flow);
        
     
       ObjectMapper mapper = new ObjectMapper();
       String jsonDoc = mapper.writeValueAsString(doc);
		
		final IndexRequest request = new IndexRequest()
                .index("testflow")
                .type("doc")
                .id(uuid.toString())
                .timeout(TimeValue.timeValueMinutes(2))                    
                 .source(jsonDoc, XContentType.JSON);
		
		IndexResponse response = client.index(request);
		return response;
	}

}
