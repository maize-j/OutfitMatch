package cn.yz.clothManagement.controller;

import cn.hutool.core.date.DateTime;
import cn.yz.clothManagement.config.shiro.ShiroUtil;
import cn.yz.clothManagement.dao.IOmCategoryDao;
import cn.yz.clothManagement.dao.IOmClothDao;
import cn.yz.clothManagement.entity.CommonResult;
import cn.yz.clothManagement.entity.OmSysUser;
import cn.yz.clothManagement.entity.StatusCode;
import cn.yz.clothManagement.entity.OmCloth;
import cn.yz.clothManagement.service.IOmClothService;
import cn.yz.clothManagement.utils.CommonConstant;
import cn.yz.clothManagement.utils.CommonUtil;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName OmClothController
 * @date 2022/1/11 17:07
 */
@RestController
@Validated
public class OmClothController {

    @Resource
    private IOmClothService omClothService;

    @Resource
    private IOmClothDao omClothDao;

    @Resource
    private IOmCategoryDao omCategoryDao;

    /**
     *@author 苞谷洁子
     * @param categoryAccName
     *@return 
     *@throws
     *@date  
     */
    @GetMapping("/cloth/getclothByCate")
    public CommonResult<List<OmCloth>> getClothByCate(@RequestParam("categoryAccName") String categoryAccName){
        List<OmCloth> clothByCate = omClothService.getClothByCate(categoryAccName);
        CommonResult<List<OmCloth>> result = new CommonResult<>(StatusCode.SUCCESS, clothByCate);
        return result;
    }

    @PostMapping("/cloth/uploadPic")
    public CommonResult<String> upload(@RequestParam("clothPicFile") MultipartFile multipartFile){
        int userId = ShiroUtil.getUserIdBySubject();
        try {
            //获取文件名
            String fileName = multipartFile.getOriginalFilename();
            //获取文件后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            //重新生成文件名
            fileName = userId +"-"+ UUID.randomUUID()+suffixName;
            //指定本地文件夹存储图片
            String filePath = CommonConstant.CLOTH_PIC_PATH;
            if(!new File(filePath).exists()){
                new File(filePath).mkdir();
            }
            multipartFile.transferTo(new File(filePath+fileName));
            return new CommonResult<>(StatusCode.SUCCESS,fileName);
        }catch (IOException e) {
            return new CommonResult<>(StatusCode.ERROR,"上传失败");
        }
    }

    @PostMapping("/cloth/addCloth")
    public CommonResult<String> addCloth(@RequestBody @Validated OmCloth omCloth){
        return omClothService.insertCloth(omCloth);
    }

    @DeleteMapping("/cloth/deleteClothPic")
    public CommonResult<String> deleteClothPic(@RequestParam("clothUri") String clothUri){

        File file = new File(clothUri);
        if(file.exists()){
            file.delete();
        }
        return new CommonResult<>(StatusCode.SUCCESS,"删除成功");
    }

    @DeleteMapping("/cloth/deleteCloth/{clothId}")
    public CommonResult<String> deleteCloth(@PathVariable("clothId") int clothId){
        boolean b = omClothService.deleteClothById(clothId);
        if(b){
            return new CommonResult<>(StatusCode.SUCCESS,"删除成功");
        }else{
            return new CommonResult<>(StatusCode.SUCCESS,"删除失败或服装不存在");
        }
    }

    @GetMapping("/cloth/getClothById/{clothId}")
    public CommonResult<OmCloth> getClothById(@PathVariable("clothId") int clothId){
        OmCloth clothById = omClothService.getClothById(clothId);
        return new CommonResult<>(StatusCode.SUCCESS,clothById);
    }

    @PutMapping("/cloth/updateCloth/{clothId}")
    public CommonResult<String> updateCloth(@PathVariable("clothId") int clothId,@RequestBody OmCloth omCloth){
        omCloth.setUpdateTime(new DateTime());
        omCloth.setClothId(clothId);
        int update = omClothDao.update(omCloth);
        if(update == 1){
            return new CommonResult<>(StatusCode.SUCCESS,"修改成功！");
        }
        return new CommonResult<>(StatusCode.ERROR,"修改失败！");
    }

}
