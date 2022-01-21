package cn.yz.clothManagement.entity;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName StatusCode
 * @date 2022/1/11 17:36
 */
public enum StatusCode {
    SUCCESS(200, "success"),
    ERROR(500, "server error"),
    LOGIN_ERROR(4,"校验错误"),
    EMPTY_ERROR(0, "空值错误");

    // 成员变量
    private int code;
    private String msg;
    // 构造方法
    StatusCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
