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
 * @ClassName entity
 * @date 2022/1/1 18:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutfitData implements Serializable {
    private Integer id;
    private String outfitId;
    private String itemId;
    private List<String> itemIds;
}
