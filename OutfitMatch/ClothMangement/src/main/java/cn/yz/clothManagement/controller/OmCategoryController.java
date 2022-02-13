package cn.yz.clothManagement.controller;

import cn.yz.clothManagement.dao.IOmCategoryDao;
import cn.yz.clothManagement.entity.CommonResult;
import cn.yz.clothManagement.entity.StatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName OmCategoryController
 * @date 2022/1/16 16:57
 */
@RestController
public class OmCategoryController {

    @Resource
    private IOmCategoryDao omCategoryDao;

    @GetMapping("/category/getCategory")
    public CommonResult<List<String>> getCategory(){
        List<String> fullCateName = omCategoryDao.getFullCateName();
        return new CommonResult<>(StatusCode.SUCCESS,fullCateName);
    }

    @GetMapping("/category/getAccCategoey")
    public CommonResult<List<String>> getAccCategoey(@RequestParam("categoryName") String categoryName){
        List<String> accCateNameByCateName = omCategoryDao.getAccCateNameByCateName(categoryName);
        return new CommonResult<>(StatusCode.SUCCESS,accCateNameByCateName);
    }

}
