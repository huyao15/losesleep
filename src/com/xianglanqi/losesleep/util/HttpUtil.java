package com.xianglanqi.losesleep.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;
import android.util.Log;

public class HttpUtil {

    public static HttpClient getHttpClient() {
        HttpClient httpClient = new DefaultHttpClient();
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 20000);
        return httpClient;
    }

    public static String getString(String url) throws ClientProtocolException, IOException, JSONException {
        Log.d("net", "get url: " + url);
        HttpGet get = new HttpGet(url);
        HttpResponse response = getHttpClient().execute(get);
        String resp = EntityUtils.toString(response.getEntity(), "utf-8");
        return resp;
    }

    public static JSONObject get(String url) throws ClientProtocolException, IOException, JSONException {
        String result = getString(url);
        if (result == null || TextUtils.isEmpty(result)) {
            return new JSONObject();
        }
        return new JSONObject(result);
    }

    public static String postString(String url, Map<String, String> params) throws ClientProtocolException,
            IOException, JSONException {
        Log.d("net", "post url: " + url);
        HttpClient httpClient = getHttpClient();
        HttpPost post = new HttpPost(url);
        List<NameValuePair> kvs = new ArrayList<NameValuePair>();
        for (Entry<String, String> entry : params.entrySet()) {
            kvs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        post.setEntity(new UrlEncodedFormEntity(kvs, "utf-8"));
        HttpResponse response = httpClient.execute(post);
        String resp = EntityUtils.toString(response.getEntity(), "utf-8");
        return resp;
    }

    public static JSONObject post(String url, Map<String, String> params) throws ClientProtocolException, IOException,
            JSONException {
        return new JSONObject(postString(url, params));
    }

}
