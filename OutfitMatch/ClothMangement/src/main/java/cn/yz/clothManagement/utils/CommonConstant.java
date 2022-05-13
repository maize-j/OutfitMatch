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
//    public static final String CLOTH_PIC_PATH = System.getProperty("user.dir")+"\\"+"src\\main\\resources\\static\\images\\";
    public static final String CLOTH_PIC_PATH = "E:\\work\\研三\\毕业\\python_workspace\\OutfitMatch\\OutfitMatch\\images\\";

    /**redis存储服装图片时间*/
    public static final long CLOTH_REDIS_EXPIRE_TIME = 60*60;

    /**图片静态资源地址*/
    public static final String PIC_PATH = "http://localhost:8081/outfit_match/static/images/";

    /**关键字类型，风格style、质感body、颜色color*/
    public static final String STYLE_KEYWORD = "style";
    public static final String BODY_KEYWORD = "body";
    public static final String COLOR_KEYWORD = "color";

}
