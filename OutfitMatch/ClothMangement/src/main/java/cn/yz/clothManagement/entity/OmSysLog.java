package cn.yz.clothManagement.entity;

import cn.hutool.core.date.DateTime;
import cn.yz.clothManagement.utils.CommonConstant;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.ParseException;
import java.util.Date;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName OmSysLog
 * @date 2022/1/14 16:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OmSysLog {
    private int logId;
    private int logType;
    private String logContent;
    private int userId;
    private String username;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    public OmSysLog(int logType,String logContent,int userId,String username){
        this.logType = logType;
        this.logContent = logContent;
        this.userId = userId;
        this.username = username;
        this.createTime = new DateTime();
    }
}
