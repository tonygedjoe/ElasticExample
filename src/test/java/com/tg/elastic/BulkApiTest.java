package com.tg.elastic;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.junit.Test;

public class BulkApiTest {

    @Test
    public void bulkApiTest() {
        try (final RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")
                )
        )) {
            final BulkRequest request = new BulkRequest()
                    .timeout(TimeValue.timeValueMinutes(2));
            request.add(new IndexRequest()
                    .index("index2")
                    .type("logs")
                    .id("id")
                    .source("user", "hainet")
            );
            request.add(new DeleteRequest()
                    .index("index2")
                    .type("logs")
                    .id("id")
            );

            final BulkResponse bulkResponse = client.bulk(request);

            bulkResponse.forEach(bulkItemResponse -> {
                final DocWriteResponse response = bulkItemResponse.getResponse();

                assertThat(response.getIndex(), is("index2"));
                assertThat(response.getType(), is("logs"));
                assertThat(response.getId(), is("id"));

                if (bulkItemResponse.getOpType() == DocWriteRequest.OpType.INDEX) {
                    final IndexResponse indexResponse = (IndexResponse) response;
                } else if (bulkItemResponse.getOpType() == DocWriteRequest.OpType.DELETE) {
                    final DeleteResponse deleteResponse = (DeleteResponse) response;
                }
            });
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void bulkApiAsyncTest() {
        try (final RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")
                )
        )) {
            final BulkRequest request = new BulkRequest();
            request.add(new IndexRequest("index2", "logs", "id").source("user", "hainet"));

            client.bulkAsync(request, new ActionListener<BulkResponse>() {
                @Override
                public void onResponse(BulkResponse bulkItemResponses) {
                    // do something
                }

                @Override
                public void onFailure(Exception e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
}