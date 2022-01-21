package cn.yz.clothManagement.controller;

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
    @GetMapping("/getclothByCate")
    public CommonResult<List<OmCloth>> getClothByCate(@RequestParam("categoryAccName") String categoryAccName){
        int userId = ShiroUtil.getUserIdBySubject();
        int categoryId = omCategoryDao.getCateIdByAccName(categoryAccName);
        List<OmCloth> clothByCate = omClothService.getClothByUserAndCate(userId,categoryId);
        CommonResult<List<OmCloth>> result = new CommonResult<>(StatusCode.SUCCESS, clothByCate);
        return result;
    }

    @PostMapping("/uploadPic")
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
            return new CommonResult<>(StatusCode.SUCCESS,"/outfit_match/static/images/"+fileName);
        }catch (IOException e) {
            return new CommonResult<>(StatusCode.ERROR,"上传失败");
        }
    }

    @PostMapping("/addCloth")
    public CommonResult<String> addCloth(@RequestBody OmCloth omCloth){
        //前端校验，不用判空
        String categoryAccName = omCloth.getCategoryAccName();
        int cateIdByAccName = omCategoryDao.getCateIdByAccName(categoryAccName);
        omCloth.setCategoryId(cateIdByAccName);
        int userId = ShiroUtil.getUserIdBySubject();
        omCloth.setUserId(userId);
        try {
            int clothId = omClothDao.insert(omCloth);
            return new CommonResult<>(StatusCode.SUCCESS,String.valueOf(clothId));
        }catch (Exception e){
            return new CommonResult<>(StatusCode.ERROR,"上传失败："+e.getMessage());
        }
    }

    @DeleteMapping("/deleteClothPic")
    public CommonResult<String> deleteClothPic(@RequestParam("clothUri") String clothUri){

        File file = new File(clothUri);
        if(file.exists()){
            file.delete();
        }
        return new CommonResult<>(StatusCode.SUCCESS,"删除成功");
    }

    @DeleteMapping("/deleteCloth")
    public CommonResult<String> deleteCloth(@RequestParam("clothId") int clothId){
        try {
            omClothDao.delete(clothId);
            return new CommonResult<>(StatusCode.SUCCESS,"删除成功");
        }catch (Exception e){
            return new CommonResult<>(StatusCode.SUCCESS,"删除失败："+e.getMessage());
        }
    }

}
