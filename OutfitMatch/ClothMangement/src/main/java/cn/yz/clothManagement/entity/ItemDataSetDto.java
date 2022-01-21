package cn.yz.clothManagement.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName DataSetDto
 * @date 2022/1/6 19:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDataSetDto implements Serializable {
    private int index;
    private String name;
    private String image;
    private int likes;
}
