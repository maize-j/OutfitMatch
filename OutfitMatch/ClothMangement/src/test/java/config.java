import cn.yz.clothManagement.ClothMangementMain;
import cn.yz.clothManagement.utils.CommonConstant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName config
 * @date 2022/1/11 18:24
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClothMangementMain.class)
public class config {

    @Autowired
    private RedisTemplate redisTemplate;

    /**测试redis*/
    @Test
    public void testRedis(){
//        System.out.println(redisTemplate.hasKey("12"));
        String str = "cyjj";
        redisTemplate.opsForValue().set(str,"222");
        String o = (String)redisTemplate.opsForValue().get("111");
        System.out.println(o);
    }

}
