package com.mkyong;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class UrfuPageParser{
    public UrfuPageParser() throws IOException {
    }
    public static void main(String[] args) throws IOException, URISyntaxException {
        URI uri = new URI(
                "http",
                "urfu.ru",
                "/ru/about/personal-pages/",
                null);
        URL url = uri.toURL();

        final CloseableHttpClient httpclient = HttpClients.createDefault();

        final HttpUriRequest httpGet = new HttpGet("https://urfu.ru/ru/about/personal-pages/");
        try (
                CloseableHttpResponse response1 = httpclient.execute(httpGet)
        ) {
            final HttpEntity entity1 = response1.getEntity();
            System.out.println(EntityUtils.toString(entity1));
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        final HttpPost httpPost = new HttpPost("http://jsonplaceholder.typicode.com/posts");
        final List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("title", "foo"));
        params.add(new BasicNameValuePair("body", "bar"));
        params.add(new BasicNameValuePair("userId", "1"));
        httpPost.setEntity(new UrlEncodedFormEntity(params));


        try (
                CloseableHttpResponse response2 = httpclient.execute(httpPost)
        ) {
            final HttpEntity entity2 = response2.getEntity();
            System.out.println(EntityUtils.toString(entity2));
        }

        httpclient.close();
    }
}