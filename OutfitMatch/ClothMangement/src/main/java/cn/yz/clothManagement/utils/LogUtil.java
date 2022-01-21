package cn.yz.clothManagement.utils;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName LogUtils
 * @date 2022/1/8 18:41
 */
@Component
public class LogUtil {

    public static void log(String filePath,String threadName,String content) throws IOException {
        File file = new File(filePath);
        FileWriter fw = null;
        PrintWriter pw = null;
        if(!file.exists()){
            file.mkdir();
        }
        file = new File(filePath+"/"+threadName+".txt");
        try{
            fw = new FileWriter(file, true);
            pw = new PrintWriter(fw);
            pw.println(CommonConstant.DATE_FORMAT.format(System.currentTimeMillis())+"\t"+content);
        }catch (Exception e){
            e.printStackTrace();
        }
        pw.flush();
        fw.flush();
        pw.close();
        fw.close();
    }

    public static void log(String filePath,String content) throws IOException {
        File file = new File(filePath);
        FileWriter fw = null;
        PrintWriter pw = null;
        try{
            fw = new FileWriter(file, true);
            pw = new PrintWriter(fw);
            pw.println(CommonConstant.DATE_FORMAT.format(System.currentTimeMillis())+"\t"+content);
        }catch (Exception e){
            e.printStackTrace();
        }
        pw.flush();
        fw.flush();
        pw.close();
        fw.close();
    }

    public static void logWithOutTime(String filePath,String content) throws IOException {
        File file = new File(filePath);
        FileWriter fw = null;
        PrintWriter pw = null;
        try{
            fw = new FileWriter(file, true);
            pw = new PrintWriter(fw);
            pw.println(content);
        }catch (Exception e){
            e.printStackTrace();
        }
        pw.flush();
        fw.flush();
        pw.close();
        fw.close();
    }
}
