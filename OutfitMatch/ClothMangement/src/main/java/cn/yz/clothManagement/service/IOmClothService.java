package cn.yz.clothManagement.service;

import cn.yz.clothManagement.entity.CommonResult;
import cn.yz.clothManagement.entity.OmCloth;
import cn.yz.clothManagement.entity.OmClothRedis;
import cn.yz.clothManagement.entity.dto.OmClothDto;
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

    List<OmCloth> getAllClothByUser(int userId);

    List<OmCloth> getClothByUserAndCate(int userId,int categoryId);

    boolean deleteClothById(int clothId);

    CommonResult<String> insertCloth(OmClothDto omClothDto);

    List<String> getClothFromRedis(int userId);

    OmClothDto getClothById(int clothId);

    List<OmClothDto> getClothByCate(String categoryAccName);

    int update(int clothId,OmClothDto omClothDto);
}
