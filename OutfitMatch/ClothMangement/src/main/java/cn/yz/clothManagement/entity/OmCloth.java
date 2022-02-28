package cn.yz.clothManagement.entity;

import cn.hutool.core.date.DateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName OmCloth
 * @date 2022/1/10 21:43
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class OmCloth implements Serializable {
    private int clothId;
    @NotNull(message = "服装图片不能为空")
    private String clothUri;
    private int userId;
    /**接收前端的分类名称*/
    private String categoryName;
    @NotNull(message = "服装分类不能为空")
    private int categoryId;
    /**服装适用季节 0表示没有，1表示春，2表示夏，3表示秋，4表示冬*/
    private char clothSeason;
    private String clothKeyword;
    private String desc;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    private int delFlag;
}
