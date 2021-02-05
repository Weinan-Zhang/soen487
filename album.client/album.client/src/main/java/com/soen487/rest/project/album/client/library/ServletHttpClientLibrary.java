package com.soen487.rest.project.album.client.library;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ServletHttpClientLibrary {
    public static String executeGetRequest(String base_url, String query) throws IOException {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        CloseableHttpResponse response = client.execute(new HttpGet(base_url+"?"+query));
        return EntityUtils.toString(response.getEntity());
    }

    public static String executePostRequest(String base_url, HashMap<String, String> formData) throws IOException {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(base_url);

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("target", formData.get("target")));
        params.add(new BasicNameValuePair("operation", formData.get("operation")));
        params.add(new BasicNameValuePair("nickname", formData.get("nickname")));
        params.add(new BasicNameValuePair("firstname", formData.get("firstname")));
        params.add(new BasicNameValuePair("lastname", formData.get("lastname")));
        params.add(new BasicNameValuePair("biography", formData.get("biography")));
        httpPost.setEntity(new UrlEncodedFormEntity(params));

        CloseableHttpResponse response = client.execute(httpPost);
        return EntityUtils.toString(response.getEntity());
    }

    public static String executePutRequest(String base_url, HashMap<String, String> formData) throws IOException {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPut httpPut = new HttpPut(base_url);

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("target", formData.get("target")));
        params.add(new BasicNameValuePair("operation", formData.get("operation")));
        params.add(new BasicNameValuePair("nickname", formData.get("nickname")));
        params.add(new BasicNameValuePair("firstname", formData.get("firstname")));
        params.add(new BasicNameValuePair("lastname", formData.get("lastname")));
        params.add(new BasicNameValuePair("biography", formData.get("biography")));
        httpPut.setEntity(new UrlEncodedFormEntity(params));

        CloseableHttpResponse response = client.execute(httpPut);
        return EntityUtils.toString(response.getEntity());
    }

    public static String executeDeleteRequest(String base_url, String query) throws IOException {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        CloseableHttpResponse response = client.execute(new HttpDelete(base_url+"?"+query));
        return EntityUtils.toString(response.getEntity());
    }
}
