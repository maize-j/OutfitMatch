package cn.yz.clothManagement.utils;

import java.text.SimpleDateFormat;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName CommonCostance
 * @date 2022/1/14 15:56
 */
public class CommonConstant {

    /**格式化日期*/
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public static final SimpleDateFormat DATE_FORMAT_WITHOUTTIME = new SimpleDateFormat("yyyy-MM-dd");

    /**redis存储用户token前缀*/
    public static final String REDIS_USER_TOKEN = "user_token:";

    /**token过期时间，负数为永不过期*/
    public static final long USER_TOKEN_EXPIRE_TIME = 8*60*60;

    /**前端请求头中token信息*/
    public static final String USER_ACCESS_TOKEN = "user_access_token";
//    public static final String USER_ACCESS_TOKEN = "Authorization";

    /**存放服装图片的位置*/
    public static final String CLOTH_PIC_PATH = "E:\\work\\研三\\毕业\\python_workspace\\OutfitMatch\\OutfitMatch\\ClothMangement\\src\\main\\resources\\static\\images\\";

}
