package com.tg.elastic;


import org.apache.http.HttpHost;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class GetApiTest {

    @Test
    public void getApiSyncTest() {
        try (final RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("10.210.9.123", 9200, "http")
                )
        )) {
            final GetRequest request = new GetRequest()
                    .index("elastiflow-3.3.0-2019.01.28")
                    .type("doc")
                    .id("u_JceGgB1R_xAGjqOuuL");

            final GetResponse response = client.get(request);

            assertThat(response.getIndex(), is("elastiflow-3.3.0-2019.01.28"));
            assertThat(response.getType(), is("doc"));
            assertThat(response.getId(), is("u_JceGgB1R_xAGjqOuuL"));

            if (response.isExists()) {
                assertThat(response.getSourceAsString(), is(containsString("\"key\": \"value\"")));
                assertThat(response.getSourceAsMap().get("key"), is("value"));
            }
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getApiAsyncTest() {
        try (final RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")
                )
        )) {
            final GetRequest request = new GetRequest()
                    .index("testindex")
                    .type("logs")
                    .id("id");

            client.getAsync(request, new ActionListener<GetResponse>() {
                @Override
                public void onResponse(final GetResponse response) {
                    assertThat(response.getIndex(), is("testindex"));
                    assertThat(response.getType(), is("logs"));
                    assertThat(response.getId(), is("id"));

                    if (response.isExists()) {
                        assertThat(response.getSourceAsString(), is(containsString("\"key\": \"value\"")));
                        assertThat(response.getSourceAsMap().get("key"), is("value"));
                    }
                }

                @Override
                public void onFailure(final Exception e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
}