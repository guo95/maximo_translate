package com.maximo.gsl.translate;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author : guosl
 * @version : 1.0
 * @date : 2021/6/14 15:17
 * @description : com.maximo.gsl.translate
 */
public class BaiduTranslate {

    public String translate(String langFrom, String langTo, String word) throws Exception {
        StringBuilder response = null;
        String appid = "20210614000862591";
        String key = "lMOfkdcsdy0gAoaMpJrW";
        String slat = String.valueOf(System.currentTimeMillis());
        try {
            String q = URLEncoder.encode(word, "UTF-8");
            String url = "https://api.fanyi.baidu.com/api/trans/vip/translate?" +
                    "q=" + q +
                    "&from=" + langFrom +
                    "&to=" + langTo.toLowerCase() +
                    "&appid=" + appid +
                    "&salt=" + slat +
                    "&sign=" + md51(appid.concat(word).concat(slat).concat(key));
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            response = new StringBuilder();
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

    private String parseResult(String inputJson) throws Exception {
        JSONObject jsonObject = JSONObject.parseObject(inputJson);
        if (jsonObject.containsKey("error_code")) {
            throw new Exception("接口调用错误:".concat(jsonObject.getString("error_code")).concat("\t").concat(jsonObject.getString("error_msg")));
        }
        JSONArray jsonArray = jsonObject.getJSONArray("trans_result");
        JSONObject jsonObject1 = jsonArray.getJSONObject(0);
        return jsonObject1.getString("dst");
    }

    /** 首先初始化一个字符数组，用来存放每个16进制字符 */
    private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    /**
     * 获得一个字符串的MD5值
     *
     * @param input 输入的字符串
     * @return 输入字符串的MD5值
     *
     */
    public static String md51(String input) throws UnsupportedEncodingException {
        if (input == null) {
            return null;
        }
        try {
            // 拿到一个MD5转换器（如果想要SHA1参数换成”SHA1”）
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            // 输入的字符串转换成字节数组
            byte[] inputByteArray = input.getBytes(StandardCharsets.UTF_8);
            // inputByteArray是输入字符串转换得到的字节数组
            messageDigest.update(inputByteArray);
            // 转换并返回结果，也是字节数组，包含16个元素
            byte[] resultByteArray = messageDigest.digest();
            // 字符数组转换成字符串返回
            return byteArrayToHex(resultByteArray);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    private static String byteArrayToHex(byte[] byteArray) {
        // new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
        char[] resultCharArray = new char[byteArray.length * 2];
        // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = HEX_DIGITS[b >>> 4 & 0xf];
            resultCharArray[index++] = HEX_DIGITS[b & 0xf];
        }

        // 字符数组组合成字符串返回
        return new String(resultCharArray);

    }
}
