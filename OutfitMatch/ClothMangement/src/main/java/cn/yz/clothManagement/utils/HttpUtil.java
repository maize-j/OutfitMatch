package cn.yz.clothManagement.utils;

import com.alibaba.fastjson.JSONObject;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName HttpUtil
 * @date 2022/1/12 22:10
 */
public class HttpUtil {

    public static JSONObject getHttpJson(String url, int comefrom) throws Exception {

        // 定制Trust
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {

            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType){
            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType){
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }

        } };

        // 定制Verifier
        class NullHostNameVerifier  implements HostnameVerifier {

            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        }

        try {
            URL realUrl = new URL(url);
            //绕过证书问题
            HttpsURLConnection.setDefaultHostnameVerifier(new NullHostNameVerifier());
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null,trustAllCerts,new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();

            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setConnectTimeout(10*1000);
            connection.setReadTimeout(10*1000);
            // 建立实际的连接
            connection.connect();
            if(connection.getResponseCode() == 504){
                return null;
            }
            //请求成功
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is,"UTF-8");
                BufferedReader br = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String temp = null;
                while(null != (temp = br.readLine())){
                    sb.append(temp);
                }
                String jsonString = sb.toString();
//                baos.close();
                is.close();
                //转换成json数据处理
                // getHttpJson函数的后面的参数1，表示返回的是json数据，2表示http接口的数据在一个（）中的数据
                JSONObject jsonArray = getJsonString(jsonString, comefrom);
                return jsonArray;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject getJsonString(String str, int comefrom) throws Exception{
        JSONObject jo = null;
        if(comefrom==1){
            if(str == null || str.equals("")){
                return null;
            }
            return JSONObject.parseObject(str);
        }else if(comefrom==2){
            int indexStart = 0;
            //字符处理
            for(int i=0;i<str.length();i++){
                if(str.charAt(i)=='('){
                    indexStart = i;
                    break;
                }
            }
            String strNew = "";
            //分割字符串
            for(int i=indexStart+1;i<str.length()-1;i++){
                strNew += str.charAt(i);
            }
            return JSONObject.parseObject(strNew);
        }
        return jo;
    }
}
