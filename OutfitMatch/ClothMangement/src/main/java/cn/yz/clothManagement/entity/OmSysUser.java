package cn.yz.clothManagement.entity;

import cn.hutool.core.date.DateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName OmSysUser
 * @date 2022/1/12 14:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OmSysUser implements Serializable {
    private int userId;
    private String username;
    private String password;
    /**头像*/
    private String photo;
    private String salt;
    private String nickname;
    /**0男，1女*/
    private int sex;
    private int delFlag;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    private List<OmSysRole> roles;

    private List<Map<String, List<Map<String, String>>>> permissions;

    public OmSysUser(String username,String password,String salt){
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.createTime = new DateTime();
    }
}
