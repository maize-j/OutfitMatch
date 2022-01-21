package cn.yz.clothManagement.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName Keywords
 * @date 2022/1/6 19:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Keywords implements Serializable {
    private int id;
    private String word;
    private int count;
}
