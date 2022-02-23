package cn.yz.clothManagement.controller;

import cn.yz.clothManagement.entity.OmCloth;
import cn.yz.clothManagement.entity.dto.OmClothDto;
import cn.yz.clothManagement.utils.CommonUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName ClothManagementController
 * @date 2022/1/10 15:25
 */
@RestController
@Validated
public class ClothManagementController {

    //文件上传
    @PutMapping("/upload")
    public String upload(MultipartFile multipartFile) throws IOException {
        //获取文件名
        String fileName = multipartFile.getOriginalFilename();
        //获取文件后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        System.out.println("上传文件名称："+fileName+"."+suffixName);
        //重新生成文件名
        fileName = UUID.randomUUID()+suffixName;
        //指定本地文件夹存储图片
        String filePath = "E:\\work\\研三\\毕业\\python_workspace\\polyvore\\clothfile/";
        multipartFile.transferTo(new File(filePath+fileName));
        System.out.println("文件名称："+filePath+fileName);
        return "上传成功";
    }



    @PostMapping("/testOptional")
    public String test(@RequestBody @Validated OmClothDto omClothDto){
        OmCloth omCloth = new OmCloth();
        BeanUtils.copyProperties(omClothDto,omCloth);

        System.out.println("Season:"+omCloth.getClothSeason());
        if(CommonUtil.isNotEmpty(omClothDto.getDesc())){
            if(CommonUtil.isEmpty(omClothDto.getDesc().get())){
                omCloth.setDesc("");
            }else{
                omCloth.setDesc(omClothDto.getDesc().get());
            }
        }
        System.out.println("desc:"+omCloth.getDesc());

        return "yes";
    }
}
