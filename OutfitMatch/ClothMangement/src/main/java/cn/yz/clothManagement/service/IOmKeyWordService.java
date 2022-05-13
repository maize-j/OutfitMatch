package cn.yz.clothManagement.service;

import cn.yz.clothManagement.entity.OmKeyword;

import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName IOmKeyWordService
 * @date 2022/2/27 15:16
 */
public interface IOmKeyWordService {

    Map<String , List<OmKeyword>> getKeywordByUser();
}
