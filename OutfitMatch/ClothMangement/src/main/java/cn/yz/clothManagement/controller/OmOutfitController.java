package cn.yz.clothManagement.controller;

import cn.yz.clothManagement.config.shiro.ShiroUtil;
import cn.yz.clothManagement.dao.IOmOutfitDao;
import cn.yz.clothManagement.entity.CommonResult;
import cn.yz.clothManagement.entity.OmOutfit;
import cn.yz.clothManagement.entity.StatusCode;
import cn.yz.clothManagement.service.IOmOutfitService;
import cn.yz.clothManagement.utils.CommonUtil;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    @Resource
    private IOmOutfitDao omOutfitDao;

    @GetMapping("/outfit/getOutfitByUser")
    public CommonResult<List<OmOutfit>> getOutfitByUser(){
        int userId = ShiroUtil.getUserIdBySubject();
        List<OmOutfit> outfitByUser = omOutfitService.getOutfitByUser(userId);
        return new CommonResult<>(StatusCode.SUCCESS,outfitByUser);
    }

    @GetMapping("/outfit/deleteOutfit")
    public CommonResult<String> deleteOutfit(@RequestParam("outfitId") int outfitId){
        int i = omOutfitDao.deleteOutfitId(outfitId);
        if(i > 1){
            return new CommonResult<>(StatusCode.SUCCESS,"删除成功");
        }
        return new CommonResult<>(StatusCode.ERROR,"删除失败");
    }

}
