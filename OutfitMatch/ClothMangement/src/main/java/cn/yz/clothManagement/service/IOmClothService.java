package cn.yz.clothManagement.service;

import cn.yz.clothManagement.entity.OmCloth;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName IOmClothService
 * @date 2022/1/11 17:08
 */
@Service
public interface IOmClothService {

    List<OmCloth> getClothByUser(int user);

    List<OmCloth> getClothByUserAndCate(int userId,int categoryId);
}
