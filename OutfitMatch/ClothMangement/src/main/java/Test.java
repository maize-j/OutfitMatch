import org.springframework.http.HttpStatus;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.List;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName Test
 * @date 2022/1/8 15:43
 */
public class Test {

    public static void main(String[] args) throws Exception {
//        download("http://gw.alicdn.com/imgextra/i3/198/O1CN012Pe5PL1DKiwLX9KhT_!!198-2-luban.png",1);
        LinkedList<String> strList = new LinkedList<>(Arrays.asList("123", "456"));
//                (Queue<String>)strList;
        for(int i = 0;i<5;i++){
            System.out.println(strList.poll());
        }
    }

    //java 通过url下载图片保存到本地
    public static void download(String urlString, int i) throws Exception {
        // 构造URL
        URL url = new URL(urlString);
        // 打开连接
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        // 输入流
        InputStream is = con.getInputStream();
        BufferedImage image = ImageIO.read(is);
        BufferedImage resizedImage = new BufferedImage(image.getWidth()/2, image.getHeight()/2, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(image, 0, 0, image.getWidth()/2, image.getHeight()/2, null);
        graphics2D.dispose();
        String filename = "E:\\work\\研三\\毕业\\python_workspace\\001653ed35c97f39cc89186a0729e5d5";   //下载路径及下载图片名称
        File file = new File(filename);
        if(!file.exists()){  //当文件夹不存在时创建文件夹
            file.mkdir();
        }
        // 输出的文件流
        filename = filename + "/" + i + ".png";  //下载路径及下载图片名称
        file = new File(filename);
        ImageIO.write(resizedImage, "png", file);

    }
}
