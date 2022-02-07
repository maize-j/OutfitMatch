package cn.yz.clothManagement.service.impl;

import cn.yz.clothManagement.dao.IItemDataDao;
import cn.yz.clothManagement.dao.IOutfitDataDao;
import cn.yz.clothManagement.entity.ItemData;
import cn.yz.clothManagement.service.IOutfitDataService;
import cn.yz.clothManagement.utils.LogUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName OutfitDataServiceImpl
 * @date 2022/1/1 22:53
 */
@Service
public class OutfitDataServiceImpl implements IOutfitDataService {

    @Resource
    private IOutfitDataDao outfitDataDao;
    @Resource
    private IItemDataDao itemDataDao;

    static String filePath = "E:\\work\\研三\\毕业\\python_workspace\\polyvore\\data\\images/";
    static String logFilePath_outfitError = "E:\\work\\研三\\毕业\\python_workspace\\polyvore\\data\\images\\log\\outfit_error.txt";
    static String logFilePath_itemError = "E:\\work\\研三\\毕业\\python_workspace\\polyvore\\data\\images\\log\\item_error.txt";

    public static LinkedList<String> outfitQueue;
//
//    @PostConstruct
//    public void init(){
//        outfitQueue = outfitDataDao.getOutfitData();
//    }

    @Override
    public void checkDownLoad(int flag) throws IOException {
        List<String> outfitByFlag = outfitDataDao.getOutfitByFlag(flag);
        String filePath1 = "";
        if(flag == 0){
            filePath1 = filePath+"train_no_dup/";
        }else if(flag == 1){
            filePath1 = filePath+"valid_no_dup/";
        }else{
            filePath1 = filePath+"test_no_dup/";
        }

        for(String outfit:outfitByFlag){
            File file = new File(filePath1+outfit);
            if(!file.exists()){
                LogUtil.log(logFilePath_outfitError,outfit);
                continue;
            }
            int length = file.listFiles().length;
            int countByOutfitId = outfitDataDao.getCountByOutfitId(outfit);
            if(length != countByOutfitId){
                LogUtil.log(logFilePath_itemError,outfit);
                continue;
            }
        }
    }

    @Override
    public void delDateSet(int flag) throws IOException {
        List<String> outfitByCount = outfitDataDao.getOutfitByCount(flag);
//        String filepath= "E:\\work\\研三\\毕业\\python_workspace\\polyvore\\data\\images/";
//        String filename = filepath + "test_no_dup/";
//        if(flag == 0){
//            filename = filepath + "train_no_dup/";
//        }else if(flag == 1){
//            filename = filepath + "valid_no_dup/";
//        }
        int n = 10;
        int size = outfitByCount.size();
        for(int k = 0;k<size-1;k++){
            String id = outfitByCount.get(k);
            if(k%n != 0){
                continue;
            }
            outfitDataDao.updateReserve(id);

//            for(int m = 1;m<=count;m++){
//                File file = new File(filename + id + "/"+m+".png");
//                if(file.exists()){
//                    boolean delete = file.delete();
//                    if(!delete){
//                        LogUtil.log("E:\\work\\研三\\毕业\\python_workspace\\polyvore\\data\\images\\log.txt",filename + id + "/"+m+".png");
//                    }
//                }else{
//                    LogUtil.log("E:\\work\\研三\\毕业\\python_workspace\\polyvore\\data\\images\\log_notexist.txt",filename + id + "/"+count+".png");
//                }
//            }
            //删除文件夹，删除数据库
            //删除文件夹
//            File file = new File(filename + id);
//            if(file.exists()){
//                boolean delete = file.delete();
//                if(!delete){
//                    LogUtil.log("E:\\work\\研三\\毕业\\python_workspace\\polyvore\\data\\images\\log.txt",filename + id);
//                }
//            }else{
//                LogUtil.log("E:\\work\\研三\\毕业\\python_workspace\\polyvore\\data\\images\\log_notexist.txt",filename + id);
//            }
            //删除数据库
//            outfitDataDao.delByOutfitId(id);
            System.out.println("到了："+k);
        }
    }
}
