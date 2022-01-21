package cn.yz.clothManagement.utils;

import java.text.SimpleDateFormat;
import java.util.Random;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName CommonUtil
 * @date 2022/1/12 16:32
 */
public class CommonUtil {

    /**
     * 随机数
     * @param place 定义随机数的位数
     */
    public static String randomGen(int place) {
        String base = "qwertyuioplkjhgfdsazxcvbnmQAZWSXEDCRFVTGBYHNUJMIKLOP0123456789";
        StringBuffer sb = new StringBuffer();
        Random rd = new Random();
        for(int i=0;i<place;i++) {
            sb.append(base.charAt(rd.nextInt(base.length())));
        }
        return sb.toString();
    }

    public static boolean isNotEmpty(Object o){
        if(o != null && !o.equals("") && !o.equals("null")){
            return true;
        }
        return false;
    }

    public static boolean isEmpty(Object o){
        return !isNotEmpty(o);
    }

}
