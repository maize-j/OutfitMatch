package cn.yz.clothManagement.entity.enums;

import lombok.Data;
import lombok.Getter;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName LogType
 * @date 2022/2/21 14:50
 */
@Getter
public enum  LogType {
    LOGIN(1,"登录日志"),
    OPERATE(2,"操作日志"),
    RECORD(3,"记录日志");
    private int type;
    private String msg;
    LogType(int type,String msg){
        this.type = type;
        this.msg = msg;
    }

}
