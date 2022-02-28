package cn.yz.clothManagement.controller;

import cn.yz.clothManagement.entity.CommonResult;
import cn.yz.clothManagement.entity.OmKeyword;
import cn.yz.clothManagement.entity.StatusCode;
import cn.yz.clothManagement.service.IOmKeyWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName OmKeyWordController
 * @date 2022/2/27 15:47
 */
@RestController
public class OmKeyWordController {

    @Resource
    private IOmKeyWordService omKeyWordService;

    @GetMapping("/keyword/getKeyWords")
    public CommonResult<List<OmKeyword>> getKeyWords(){
        List<OmKeyword> keywordByUser = omKeyWordService.getKeywordByUser();
        return new CommonResult<>(StatusCode.SUCCESS,keywordByUser);
    }

}
