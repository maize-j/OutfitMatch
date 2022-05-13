package cn.yz.clothManagement.entity.dto;

import cn.yz.clothManagement.entity.OmCloth;
import cn.yz.clothManagement.entity.OmKeyword;
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
import java.util.List;
import java.util.Map;
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
public class OmClothDto extends OmCloth {
    private Map<String, List<Integer>> keywords;
}
