package cn.yz.clothManagement.service.impl;

import cn.yz.clothManagement.dao.IItemDataDao;
import cn.yz.clothManagement.dao.IKeywordsDao;
import cn.yz.clothManagement.dao.IOutfitDataDao;
import cn.yz.clothManagement.entity.ItemData;
import cn.yz.clothManagement.entity.ItemDataSetDto;
import cn.yz.clothManagement.entity.Keywords;
import cn.yz.clothManagement.entity.OutfitDataSetDto;
import cn.yz.clothManagement.service.IItemDataService;
import cn.yz.clothManagement.utils.LogUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.List;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName ItemDataServiceImpl
 * @date 2022/1/2 14:01
 */
@Service
public class ItemDataServiceImpl implements IItemDataService {

    @Resource
    private IItemDataDao itemDataDao;

    @Resource
    private IOutfitDataDao outfitDataDao;

    @Resource
    private IKeywordsDao keywordsDao;


    public static LinkedList<String> trainOutfitQueue;
    public static LinkedList<String> validOutfitQueue;
    public static LinkedList<String> testOutfitQueue;

    @PostConstruct
    public void init(){
        trainOutfitQueue = outfitDataDao.getUnDownloadOutfitIds(0);
        validOutfitQueue = outfitDataDao.getUnDownloadOutfitIds(1);
        testOutfitQueue = outfitDataDao.getUnDownloadOutfitIds(2);
    }


    @Override
    public void updateSeason(int limit) {
        List<ItemData> itemDataList = itemDataDao.getItemDataBySeason(limit);
        for(ItemData itemData:itemDataList){
            String title = itemData.getTitle();
            String season = "无";
            if(title.contains("春") || title.contains("秋")){
                season = "春秋";
            }else if(title.contains("夏")){
                season = "夏";
            }else if(title.contains("冬")) {
                season = "冬";
            }
            itemDataDao.updateSeasonById(itemData.getId(),season);
        }
    }

    @Override
    public void getOutfitData(int flag) throws IOException {
        // 获得start-end的outfitId
        List<String> outfitDataIds = outfitDataDao.getOutfitByFlag(flag);
        List<OutfitDataSetDto> outfitDatas = new ArrayList<>();
        for(String id:outfitDataIds){
            // 获取一个套装中所有的单品
            List<ItemData> itemDataByOutfit = itemDataDao.getItemDataByOutfit(id);
            List<ItemDataSetDto> items = new ArrayList<>();
            int i = 1;
            for(ItemData it : itemDataByOutfit){
                String desc = it.getDesc();
                ItemDataSetDto itemDataSetDto = new ItemDataSetDto(i++,desc,it.getPicUrl(),0);
                items.add(itemDataSetDto);
            }
            OutfitDataSetDto outfitDataSetDto = new OutfitDataSetDto(id,0,items);
            outfitDatas.add(outfitDataSetDto);
        }
        String s = JSONObject.toJSONString(outfitDatas);
        String filename = "";
        if(flag == 0){
            filename = "E:\\work\\研三\\毕业\\python_workspace\\polyvore\\data\\label\\outfit_train.json";
        }else if(flag == 1){
            filename = "E:\\work\\研三\\毕业\\python_workspace\\polyvore\\data\\label\\outfit_valid.json";
        }else{
            filename = "E:\\work\\研三\\毕业\\python_workspace\\polyvore\\data\\label\\outfit_test.json";
        }
        LogUtil.logWithOutTime(filename,s);
    }

    @Override
    public void updateCount(int start, int end) {
        List<Keywords> keywords = keywordsDao.getKeywords();
        List<String> itemIds = outfitDataDao.getItemIds(start, end);
        List<ItemData> itemDataByIds = itemDataDao.getItemDataByIds(itemIds);
        System.out.println("itemDataByIds:"+itemDataByIds);

        for(ItemData itemData:itemDataByIds){
            String title = itemData.getTitle();
            StringBuilder desc = new StringBuilder("");
            for(Keywords keyword:keywords){
                String word = keyword.getWord();
                if(title.contains(word)){
                    desc.append(word).append(" ");
                    keyword.setCount(keyword.getCount()+1);
                }
            }
            if(null != desc && !"".equals(desc) && desc.length() != 0){
                itemDataDao.updateDescById(itemData.getId(),desc.substring(0,desc.length()-1));
            }
        }
        //更新关键字对应的count
        for(Keywords keyword:keywords){
            keywordsDao.updataCountById(keyword.getId(),keyword.getCount());
        }
    }

    @Override
    public void download(String filePath,String logFilePath,String flag) throws IOException {
//        List<String> outfitDataIds = outfitDataDao.getOutfitData(start, end);
        while (true){
            String outfitDataId = null;
            synchronized (this){
                switch (flag){
                    case "train" : outfitDataId = trainOutfitQueue.poll(); break;
                    case "valid" : outfitDataId = validOutfitQueue.poll(); break;
                    case "test" : outfitDataId = testOutfitQueue.poll();
                }

            }
            if(outfitDataId == null){
                //终止线程
                LogUtil.log(logFilePath,
                        Thread.currentThread().getName(),"所有的都处理完啦！");
                break;
            }
            URL url = null;
            HttpURLConnection con = null;
            InputStream is = null;
            try{
                //当文件夹不存在时生成文件夹
                String filePathWithId = filePath+outfitDataId;   //下载路径及下载图片名称
                File file = new File(filePathWithId);
                if(!file.exists()){  //当文件夹不存在时创建文件夹
                    file.mkdir();
                }
                // 获取一个套装中所有的单品
                List<ItemData> itemDataByOutfit = itemDataDao.getItemDataByOutfit(outfitDataId);
                int i = 1;
                for(ItemData itemData:itemDataByOutfit){
                    String urlStr = itemData.getPicUrl();
                    if(urlStr.contains("https:")){
                        LogUtil.log(logFilePath,
                                Thread.currentThread().getName() + "-https",outfitDataId+"\t"+urlStr);
                        continue;
                    }
                    if(!urlStr.contains("http:")){
                        urlStr = "http:"+urlStr;
                    }

                    // 构造URL
                    url = new URL(urlStr);
                    // 打开连接
                    con = (HttpURLConnection) url.openConnection();
                    int responseCode = con.getResponseCode();
                    String content = responseCode + "\t" + itemData.getItemId() + "\t" + urlStr;
                    if(responseCode != 200){
                        LogUtil.log(logFilePath,
                                Thread.currentThread().getName() + "-error",content);
                        continue;
                    }
                    LogUtil.log(logFilePath,
                            Thread.currentThread().getName(),content);
                    // 输入流
                    is = con.getInputStream();
                    BufferedImage image = ImageIO.read(is);
                    BufferedImage resizedImage = new BufferedImage(image.getWidth()/3, image.getHeight()/3, BufferedImage.TYPE_INT_RGB);
                    Graphics2D graphics2D = resizedImage.createGraphics();
                    graphics2D.drawImage(image, 0, 0, image.getWidth()/3, image.getHeight()/3, null);
                    graphics2D.dispose();
                    //文件名称
                    String filename = filePathWithId + "/" + (i++) + ".png";
                    file = new File(filename);
                    ImageIO.write(resizedImage, "png", file);
                }
                is.close();
                //更新下载状态
                outfitDataDao.updateDownloadByOutfitId(outfitDataId,1);
            }catch (Exception e){
                LogUtil.log(logFilePath,
                        Thread.currentThread().getName() + "-exception",outfitDataId+"\t"+e.getMessage());
            }
        }
    }
}
