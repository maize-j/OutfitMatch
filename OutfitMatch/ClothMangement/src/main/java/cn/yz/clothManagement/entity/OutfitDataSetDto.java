package cn.yz.clothManagement.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName OutfitDataSetDto
 * @date 2022/1/6 19:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutfitDataSetDto implements Serializable {
    private String set_id;
    private int likes;
    private List<ItemDataSetDto> items;
}
