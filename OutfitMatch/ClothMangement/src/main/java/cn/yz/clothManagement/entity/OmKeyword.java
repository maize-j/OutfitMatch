package cn.yz.clothManagement.entity;

import cn.hutool.core.date.DateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName OmKeyword
 * @date 2022/1/10 20:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OmKeyword implements Serializable {
    private int keywordId;
    private String keyword;
    private int userId;
//    private int count;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    private int delFlag;
    private String relatedWord;
    private String classification;
}
