package cn.yz.clothManagement.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName ItemData
 * @date 2022/1/2 13:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemData implements Serializable {
    private Integer id;
    private String itemId;
    private String cateId;
    private String picUrl;
    private String title;
    private String season;
    private String desc;
}
