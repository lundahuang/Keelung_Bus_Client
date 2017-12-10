package com.bus.jc.keelung_bus;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by JC on 2017/12/10.
 */

public class Bus_Module {
    private String getWebContent(String urlString) throws IOException {
        URL url = new URL(urlString);
        URLConnection con = url.openConnection();
        InputStream in = con.getInputStream();
        String encoding = con.getContentEncoding();
        encoding = encoding == null ? "UTF-8" : encoding;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[8192];
        int len = 0;
        while ((len = in.read(buf)) != -1) {
            baos.write(buf, 0, len);
        }
        String body = new String(baos.toByteArray(), encoding);
        return body;
    }

    public JSONObject searchBus(String search) throws IOException, JSONException {
        String url = "http://140.121.91.62/Bus/Keelung/SearchBus.php?search=" + search;
        JSONObject result = new JSONObject(getWebContent(url));
        return  result;
    }

    public  JSONObject busStopEstimateTime(String name,boolean goback) throws IOException, JSONException {
        String url = "http://140.121.91.62/Bus/Keelung/BusStopEstimateTime.php?name=" + name + "&goBack=" + (goback?"1":"0");
        JSONObject result = new JSONObject(getWebContent(url));
        return  result;
    }

    public JSONObject busRouteEstimateTime(String id,boolean goback) throws IOException, JSONException {
        String url = "http://140.121.91.62/Bus/Keelung/BusRouteEstimateTime.php?id=" + id + "&goBack=" + (goback?"1":"0");
        JSONObject result = new JSONObject(getWebContent(url));
        return  result;
    }
}
