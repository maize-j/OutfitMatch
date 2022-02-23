import cn.yz.clothManagement.ClothMangementMain;
import cn.yz.clothManagement.controller.OmOutfitController;
import cn.yz.clothManagement.dao.IOmClothDao;
import cn.yz.clothManagement.dao.IOmOutfitDao;
import cn.yz.clothManagement.entity.CommonResult;
import cn.yz.clothManagement.entity.OmCloth;
import cn.yz.clothManagement.entity.OmClothRedis;
import cn.yz.clothManagement.entity.OmOutfit;
import cn.yz.clothManagement.service.IOmClothService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName ClothTest
 * @date 2022/2/12 16:24
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClothMangementMain.class)
public class ClothTest {

    @Resource
    private IOmOutfitDao omOutfitDao;

    @Resource
    private OmOutfitController omOutfitController;

    @Resource
    private IOmClothDao omClothDao;

    @Resource
    private IOmClothService omClothService;

    @Test
    public void testCloth(){
        List<Integer> outfitByUserId = omOutfitDao.getOutfitByUserId(5);
        System.out.println(outfitByUserId);
    }

    @Test
    public void testGetOutfitByUser(){
        CommonResult<List<OmOutfit>> outfitByUser = omOutfitController.getOutfitByUser(5);
        List<OmOutfit> data = outfitByUser.getData();
        data.forEach(omOutfit -> {
            System.out.println(omOutfit.getOutfitId());
            List<OmCloth> clothList = omOutfit.getClothList();
            clothList.forEach(cloth->{
                System.out.println(cloth);
            });
        });
    }

    @Test
    public void testDelCloth(){
        boolean b = omClothService.deleteClothById(13);
        System.out.println(b);
    }

    @Test
    public void testGetClothByRedis(){
        List<String> clothFromRedis = omClothService.getClothFromRedis(5);
        clothFromRedis.forEach(cloth-> System.out.println(cloth));
    }

}