import cn.yz.clothManagement.ClothMangementMain;
import cn.yz.clothManagement.dao.IOmSysLogDao;
import cn.yz.clothManagement.entity.OmSysLog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.text.ParseException;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName OmSysLogTest
 * @date 2022/1/14 18:16
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClothMangementMain.class)
public class OmSysLogTest {

    @Resource
    private IOmSysLogDao omSysLogDao;

    @Test
    public void testLogInsert() throws ParseException {
        OmSysLog omSysLog = new OmSysLog(0, "登录成功", 1, "admin");
        System.out.println(omSysLog);
        omSysLogDao.insert(omSysLog);
    }

    @Test
    public void testGetLog(){
        System.out.println(omSysLogDao.getById(3));
    }

}
