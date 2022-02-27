package cn.yz.clothManagement.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Optional;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName OmClothDto
 * @date 2022/2/20 22:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OmClothDto {
    private int clothId;
    @NotNull(message = "服装uri不能为空")
    private String clothUri;
    private int userId;
    /**接收前端的分类名称*/
    private String categoryName;
    private int categoryId;
    /**服装适用季节 0表示没有，1表示春，2表示夏，3表示秋，4表示冬*/
    private Optional<Integer> clothSeason;
    private Optional<String> desc;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    private int delFlag;
}
