import cn.yz.clothManagement.ClothMangementMain;
import cn.yz.clothManagement.service.IItemDataService;
import cn.yz.clothManagement.service.IOutfitDataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName ItemDataTest
 * @date 2022/1/2 16:02
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClothMangementMain.class)
public class ItemDataTest {

    @Resource
    private IItemDataService itemDataService;

    @Resource
    private IOutfitDataService outfitDataService;


    //生成json文件，flag为数据集标识，0训练，1验证，2测试
    @Test
    public void testOutfitData_train() throws IOException {
        itemDataService.getOutfitData(0);
    }

    //检验数据集下载的是否正确
    @Test
    public void testCheck() throws IOException {
        /**训练集下载是否正确*/
        outfitDataService.checkDownLoad(0);
//        /**验证集下载是否正确*/
//        outfitDataService.checkDownLoad(1);
//        /**测试集下载是否正确*/
//        outfitDataService.checkDownLoad(2);
    }

    //下载数据集中的图片，
    //E:\work\研三\毕业\python_workspace\polyvore\data\images\train_no_dup/ 训练集存放位置
    //E:\work\研三\毕业\python_workspace\polyvore\data\images\test_no_dup/ 测试集存放位置
    //E:\work\研三\毕业\python_workspace\polyvore\data\images\valid_no_dup/ 验证集存放位置
    //falg为下载哪个数据集，train训练，valid验证，test验证
    @Test
    public void downloadTrain() throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(100);
        CountDownLatch latch = new CountDownLatch(100);
        for(int i = 0;i<100;i++){
            executorService.submit(() -> {
                System.out.println(Thread.currentThread().getName() + "-执行了");
                try {
                    itemDataService.download("E:\\work\\研三\\毕业\\python_workspace\\polyvore\\data\\images\\train_no_dup/",
                            "E:\\work\\研三\\毕业\\python_workspace\\polyvore\\log\\train", "train");
                    latch.countDown();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        latch.await();

    }

    //删除数据集，flag为数据集标识，0训练，1验证，2测试
    //count为搭配包含单品数，3件的保留1/5，其余的保留1/3
    @Test
    public void testDelDateSet() throws IOException {
        outfitDataService.delDateSet(0);
    }

}
