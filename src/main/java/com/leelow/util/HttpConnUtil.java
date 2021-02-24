package com.leelow.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConnUtil {
    public static String doGet(String urlStr){
        HttpURLConnection conn = null;
        InputStream is = null;
        BufferedReader br = null;
        StringBuilder result = new StringBuilder();
        try{
            URL url = new URL(urlStr);
            conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");

            conn.setConnectTimeout(15000);
            conn.setReadTimeout(60000);

            conn.setRequestProperty("Accept","application/json");

            conn.connect();
            if(200 == conn.getResponseCode()){
                is = conn.getInputStream();
                br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
                String line;
                while ((line = br.readLine()) != null){
                    result.append(line);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                if(br!=null) br.close();
                if(is!=null) is.close();
              }catch (Exception e){
               }
            conn.disconnect();
            }
        return result.toString();
        }

    }

