package com.test.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

@RestController
@RequestMapping("/pathOk")
public class PathTraversalOkController extends HttpServlet {

    @RequestMapping("/demo1")
    public void demo1(String filename, HttpServletResponse resp) throws Exception {
        ServletOutputStream outputStream = resp.getOutputStream();
        //拼接校验路径
        String filepath = "C:/Users/lb/Downloads/"+ filename;
        if(!checkFile(filepath)){
            return;
        }

        outputStream.write(("filepath：" + filepath + "\r").getBytes());
        outputStream.write("---------------------------------------------------\r".getBytes());
        //读取文件
        FileInputStream fileInputStream = new FileInputStream(new File(filepath));
        byte[] bytes = new byte[fileInputStream.available()];
        fileInputStream.read(bytes);

        //输出
        outputStream.write(bytes);
    }


    @RequestMapping("/unzipdemo")
    public void unzip(String zipfile) throws Exception {
        File file = new File(zipfile);
        ZipFile zf = new ZipFile(file);
        ZipInputStream zis = new ZipInputStream(new FileInputStream(file));
        ZipEntry entry;
        //遍历zip中的文件
        while((entry = zis.getNextEntry()) != null){
            //构造输出流
            String zipfilename = entry.getName();
            String filepath = "C:/Users/lb/Downloads/" + zipfilename;
            //校验zip包中的文件，由于解压存在写入文件问题，故需要对文件格式进行校验
            if (!checkFile(filepath) || !checkExt(filepath)){
                throw new Exception("文件非法！");
            }
            File outfile = new File(filepath);
            FileOutputStream fos = new FileOutputStream(outfile);
            //将zip中的内容读出
            InputStream inputStream = zf.getInputStream(entry);
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            //写文件并关闭连接
            fos.write(bytes);
            fos.close();
            inputStream.close();
        }
    }


    //文件名校验，存在问题返回false
    public boolean checkFile(String path) throws Exception {
        //处理截断问题
        path = path.replaceAll("\\p{C}", "");
        if(path.contains("..")){
            return false;
        }
        return true;
    }

    public boolean checkExt(String path){
        String ext = path.substring(path.lastIndexOf("."), path.length());
        List<String> exts = Arrays.asList(".jpg", ".png", ".jpeg", ".gif");
        return exts.contains(ext);
    }
}
