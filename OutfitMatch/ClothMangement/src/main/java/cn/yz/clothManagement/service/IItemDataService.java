package cn.yz.clothManagement.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName IItemDataService
 * @date 2022/1/2 14:01
 */
@Service
public interface IItemDataService {
    void updateSeason(int limit);

    void getOutfitData(int flag) throws IOException;

    void updateCount(int start, int end);

    void download(String filePath,String logFilePath,String flag) throws IOException;
}
