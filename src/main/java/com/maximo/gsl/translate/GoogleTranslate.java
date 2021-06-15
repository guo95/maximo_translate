package com.maximo.gsl.translate;

import com.alibaba.fastjson.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * @author : guosl
 * @version : 1.0
 * @date : 2021/6/14 0:17
 * @description : com.maximo.gsl.translate
 */
public class GoogleTranslate {

    public String translate(String langFrom, String langTo, String word)  {
        StringBuffer response = null;
        try {
            String url = "https://translate.googleapis.com/translate_a/single?" +
                    "client=gtx&" +
                    "sl=" + langFrom +
                    "&tl=" + langTo +
                    "&dt=t&q=" + URLEncoder.encode(word, "UTF-8");
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parseResult(response.toString());

    }

    private String parseResult(String inputJson) {
        JSONArray jsonArray = JSONArray.parseArray(inputJson);
        JSONArray jsonArray2 = (JSONArray) jsonArray.get(0);
        String result ="";
        for(int i =0;i < jsonArray2.size();i ++){
            result += ((JSONArray) jsonArray2.get(i)).get(0).toString();
        }
        return result;
    }
}
