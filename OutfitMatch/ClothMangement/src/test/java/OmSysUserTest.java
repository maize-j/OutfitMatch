import cn.yz.clothManagement.ClothMangementMain;
import cn.yz.clothManagement.dao.IOmSysUserDao;
import cn.yz.clothManagement.entity.OmSysRole;
import cn.yz.clothManagement.entity.OmSysUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName OmSysUserTest
 * @date 2022/1/13 16:31
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClothMangementMain.class)
public class OmSysUserTest {

    @Resource
    private IOmSysUserDao omSysUserDao;

    @Test
    public void testGetUserByUserName(){
        OmSysUser userByName = omSysUserDao.getUserByName("admin");
        System.out.println(userByName);
    }
    @Test
    public void testGetRoleByUserName(){
        List<OmSysRole> roleByUser = omSysUserDao.getRoleByUser(1);
        System.out.println(roleByUser);
    }
}
