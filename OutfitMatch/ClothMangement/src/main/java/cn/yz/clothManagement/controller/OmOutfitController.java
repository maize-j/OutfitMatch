package cn.yz.clothManagement.controller;

import cn.yz.clothManagement.entity.CommonResult;
import cn.yz.clothManagement.entity.OmOutfit;
import cn.yz.clothManagement.entity.StatusCode;
import cn.yz.clothManagement.service.IOmOutfitService;
import cn.yz.clothManagement.utils.CommonUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName OmOutfitController
 * @date 2022/2/12 16:49
 */
@RestController
public class OmOutfitController {

    @Resource
    private IOmOutfitService omOutfitService;

    @GetMapping("/outfit/getOutfitByUser")
    public CommonResult<List<OmOutfit>> getOutfitByUser(@RequestParam("userId") int userId){
        List<OmOutfit> outfitByUser = omOutfitService.getOutfitByUser(userId);
        return new CommonResult<>(StatusCode.SUCCESS,outfitByUser);
    }

}
