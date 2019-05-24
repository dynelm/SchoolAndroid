package com.dynelm.robotdarttest.getWeatherinfo;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


/**
 * Created by dynelm on 2017/8/2 0002.
 */

public class json {
    InputStreamTOString inputStreamTOString = null;
    public String json()throws Exception {
        inputStreamTOString = new InputStreamTOString();
        //参数url化
        String city = URLEncoder.encode("马鞍山", "utf-8");

        //拼地址
        String apiUrl = String.format("http://www.sojson.com/open/api/weather/json.shtml?city=%s",city);
        //开始请求
        URL url= new URL(apiUrl);
        URLConnection open = url.openConnection();
        InputStream input = open.getInputStream();
        //这里转换为String，带上包名，怕你们引错包
        String result = inputStreamTOString.InputStreamTOString(input,"utf-8");
        //输出
        System.out.println(result);
        return  result;
    }
}
