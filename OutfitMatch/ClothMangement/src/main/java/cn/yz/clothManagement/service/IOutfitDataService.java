package cn.yz.clothManagement.service;

import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName OutfitDataService
 * @date 2022/1/1 22:17
 */
@Service
public interface IOutfitDataService {

    /**检查outfit下载是否正确*/
    void checkDownLoad(int flag) throws IOException;

    /**删除数据集，3件的保留1/5，其余的保留1/3*/
    void delDateSet(int flag) throws IOException;


}
