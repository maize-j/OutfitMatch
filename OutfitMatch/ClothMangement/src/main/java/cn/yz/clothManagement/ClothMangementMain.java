package cn.yz.clothManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName ClothMangementMain
 * @date 2022/1/1 23:11
 */
@SpringBootApplication
//启动缓存
@EnableCaching
public class ClothMangementMain {
    public static void main(String[] args) {
        SpringApplication.run(ClothMangementMain.class);
    }
}
