package cn.yz.clothManagement.utils;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Date;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName StringUtil
 * @date 2022/1/12 17:51
 */
public class WeatherUtil {
    public static final String WEATHER_KEY = "84c6ed0aa06044459efe00f0d94553aa";
    public static final String WEATHER_BASEURL = "https://devapi.qweather.com/v7/weather/now?";
    //https://devapi.qweather.com/v7/weather/now?key=84c6ed0aa06044459efe00f0d94553aa&location=112.34,31.10&gzip=n

    public static final String IP_KEY = "kDXXALBEMUSwIYZngheVICN65xSZ4xpY";
    public static final String IP_BASEURL = "https://api.map.baidu.com/location/ip?";
    //https://api.map.baidu.com/location/ip?ak=kDXXALBEMUSwIYZngheVICN65xSZ4xpY&ip=183.93.234.152&coor=bd09ll

    /**根据IP获取天气，根据温度计算季节（不直接获取季节因为不准确）*/
    public static String getSeason(HttpServletRequest request) throws Exception {
        String ip = getIp(request);
//        String ip = "183.93.234.152";
        String addr = getAddr(ip);
        String season = getWeather(addr);
        return season;
    }

    /**获取IP地址*/
    public static String getIp(HttpServletRequest request){

        //http://ip.chinaz.com 获取本机公网IP

        String ipAddress = request.getHeader("x-forwarded-for");
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if(ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")){
                //根据网卡取本机配置的IP
                InetAddress inet=null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress= inet.getHostAddress();
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15
            if(ipAddress.indexOf(",")>0){
                ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

    /**根据IP地址获取经纬度，保留两位小数点，组合成为 经度,纬度 的格式*/
    public static String getAddr(String ip) throws Exception {
        String url = WeatherUtil.IP_BASEURL + "ak=" + WeatherUtil.IP_KEY + "&ip=" + ip + "&coor=bd09ll";
        JSONObject httpJson = HttpUtil.getHttpJson(url, 1);
        JSONObject content = (JSONObject) httpJson.get("content");
        JSONObject point = (JSONObject) content.get("point");
        double x = point.getDouble("x");
        double y = point.getDouble("y");
        String x_y = String.format("%.2f", x) + "," + String.format("%.2f", y);
        return x_y;
    }

    /**根据经纬度计算*/
    public static String getWeather(String location) throws Exception {
        String season = "";
        String url = WeatherUtil.WEATHER_BASEURL + "key=" + WeatherUtil.WEATHER_KEY + "&location=" + location + "&gzip=n";
        JSONObject httpJson = HttpUtil.getHttpJson(url, 1);
        JSONObject content = (JSONObject) httpJson.get("now");
        /**根据体感温度获取温度*/
        int temp = content.getInteger("temp");
        if(temp<10){
            season = "冬";
        }else if(temp>=10 && temp<=22){
            Calendar cal = Calendar.getInstance();
            int month = cal.get(Calendar.MONTH) + 1;
            if(month >= 6){
                season = "秋";
            }else{
                season = "春";
            }
        }else{
            season = "夏";
        }
        return season;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(getSeason(null));
    }

}
