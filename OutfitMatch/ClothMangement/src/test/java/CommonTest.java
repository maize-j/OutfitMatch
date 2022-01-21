import cn.hutool.core.date.DateTime;
import cn.yz.clothManagement.utils.CommonConstant;
import org.junit.Test;

import java.io.File;
import java.text.ParseException;
import java.util.Date;

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
}
