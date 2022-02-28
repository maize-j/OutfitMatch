import cn.hutool.core.date.DateTime;
import cn.yz.clothManagement.utils.CommonConstant;
import cn.yz.clothManagement.utils.LogUtil;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName CommonTest
 * @date 2022/1/14 18:20
 */
public class CommonTest {

    /**simpleDateFormat*/
    @Test
    public void testSDF(){
        String date = CommonConstant.DATE_FORMAT.format(new Date());
        System.out.println(date);
    }

    /**DateTime*/
    @Test
    public void testDateTime(){
        DateTime dateTime = new DateTime();
        System.out.println(dateTime);
    }

    /**测试删除文件*/
    @Test
    public void delFile(){
        String filename = "E:\\work\\研三\\毕业\\python_workspace\\polyvore\\data\\images\\train_no_dup\\000149be6840e483ecfb39f80e12014d";
//        String id = "000149be6840e483ecfb39f80e12014d";
        File file = new File(filename);
        if(file.exists()){
            file.setWritable(true);
            boolean delete = file.delete();
            System.out.println(delete);
        }
    }

    /**生成服装测试文件*/
    @Test
    public void predictionFileGen() throws IOException {
        String filepath = "E:\\work\\研三\\毕业\\python_workspace\\polyvore\\data\\images\\test_no_dup";
        File file = new File(filepath);
        File[] list = file.listFiles();
        String predFilePath = "E:\\work\\研三\\毕业\\python_workspace\\polyvore\\data\\label\\outfit_comp_pred.txt";

        for(int i = 0;i<4000;i++){
            int num = 3 + (int) (Math.random() * 4);
            String str = "0";
            for(int j = 0;j<num;j++){
                int i1 = (int) (Math.random() * 3633);
                File file1 = list[i1];
                int length = file1.list().length;
                int num1 = 1 + (int) (Math.random() * length);
                str += " " + file1.getName() + "_" + num1;
            }
            LogUtil.logWithOutTime(predFilePath,str);
        }
    }


    /**获取当前路径*/
    @Test
    public void sysPath(){
        System.out.println(CommonConstant.CLOTH_PIC_PATH);
    }

    @Test
    public void testSync() throws ExecutionException, InterruptedException {
        CompletableFuture future = new CompletableFuture();

        // 在 Java8 中，推荐使用 Lambda 来替代匿名 Runnable 实现类
        new Thread(
                () -> {
                    try {
                        // 模拟一段耗时的操作
                        Thread.sleep(2000);
                        future.complete("I have completed");
                    } catch (Exception e) {
                    }
                }
        ).start();

        System.out.println(future.get());
//        System.out.println(123);
    }

    @Test
    public void testStream(){
        List<String> list = new ArrayList<>();
        list.add("aaa");
        list.add("bbb");
        list.add("ccc");
        String collect = list.stream().collect(Collectors.joining(","));
        System.out.println(collect);
    }

}
