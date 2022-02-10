package cn.yz.clothManagement.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName OmOutfit
 * @date 2022/2/10 16:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OmOutfit {
    private int id;
    private int outfitId;
    private int userId;
    private List<OmCloth> clothList;
}
