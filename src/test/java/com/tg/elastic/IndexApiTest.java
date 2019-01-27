package com.tg.elastic;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.junit.Test;

public class IndexApiTest {

	@Test
    public void indexApiSyncTest() {
        try (final RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")
                )
        )) {
            // Map
            final Map<String, Object> map = new HashMap<>();
            map.put("user", "tony3");
            map.put("message", "Tony3 sample example");

            // XContentBuilder
            final XContentBuilder builder = XContentFactory.jsonBuilder();
            builder.startObject();
            {
                builder.field("user", "tony2");
                builder.field("message", "Tony2 sample example");
            }
            builder.endObject();

            final IndexRequest request = new IndexRequest()
                    .index("testindex")
                    .type("logs")
                    .id("id")
                    .timeout(TimeValue.timeValueMinutes(2))
                    // Map
                    .source(map);
                    // XContentBUilder
                    // .source(builder);
                    // Object key-pairs
                    // .source("user", "hainet",
                    //         "message", "elasticsearch-rest-high-level-client-sample")

            final IndexResponse response = client.index(request);

            return;
            
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }



}
