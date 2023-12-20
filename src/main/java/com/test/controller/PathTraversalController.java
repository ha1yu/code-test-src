package com.test.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

@RestController
@RequestMapping("/readfile1")
public class PathTraversalController extends HttpServlet{
    @RequestMapping("/download")
    public void download(String filename, HttpServletResponse resp) throws Exception {
        //请求：http://127.0.0.1:8080/readfile1/download?filename=../../../../Windows/win.ini
        ServletOutputStream outputStream = resp.getOutputStream();

        //读取文件
        String filepath = "C:/Users/lb/Downloads/"+ filename;
        outputStream.write(("filepath：" + filepath + "\r").getBytes());
        outputStream.write("---------------------------------------------------\r".getBytes());
        FileInputStream fileInputStream = new FileInputStream(new File(filepath));
        byte[] bytes = new byte[fileInputStream.available()];
        fileInputStream.read(bytes);

        //输出
        outputStream.write(bytes);
    }



    @RequestMapping("/download2")
    public void download2(String filepath ,String filename, HttpServletResponse resp) throws Exception {
        //请求：http://127.0.0.1:8080/readfile1/download2?filepath=../../../../Windows&filename=win.ini
        ServletOutputStream outputStream = resp.getOutputStream();

        //对filename参数进行校验
        if(filename.contains("..")){
            resp.getWriter().print("error!");
            return;
        }

        //读取文件
        String file = "C:/Users/lb/Downloads/"+ filepath + File.separator + filename;
        outputStream.write(("filepath：" + file + "\r").getBytes());
        outputStream.write("---------------------------------------------------\r".getBytes());
        FileInputStream fileInputStream = new FileInputStream(new File(file));
        byte[] bytes = new byte[fileInputStream.available()];
        fileInputStream.read(bytes);

        //输出
        outputStream.write(bytes);
    }




    @RequestMapping("/download3")
    public void download3(String filename, HttpServletResponse resp) throws Exception {
        //请求：http://127.0.0.1:8080/readfile1/download3?filename=..././..././..././..././..././Windows/win.ini
        ServletOutputStream outputStream = resp.getOutputStream();

        //拼接路径
        String file = "C:/Users/lb/Downloads/" + filename;

        //替换../
        file = file.replace("../","").replace("..\\","");

        //读取文件
        outputStream.write(("filepath：" + file +"\r").getBytes());
        outputStream.write("---------------------------------------------------\r".getBytes());
        FileInputStream fileInputStream = new FileInputStream(new File(file));
        byte[] bytes = new byte[fileInputStream.available()];
        fileInputStream.read(bytes);

        //输出
        outputStream.write(bytes);
    }

    @RequestMapping("/unzip")
    public void unzip(String zipfile) throws Exception {
        File file = new File(zipfile);
        ZipFile zf = new ZipFile(file);
        ZipInputStream zis = new ZipInputStream(new FileInputStream(file));
        ZipEntry entry;
        //遍历zip中的文件
        while((entry = zis.getNextEntry()) != null){
            //构造输出流
            String zipfilename = entry.getName();
            File outfile = new File("C:/Users/lb/Downloads/" + zipfilename);
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


    @RequestMapping("/readImg")
    public String readImg(String imgfile) throws Exception {
        String path = "C:/Users/lb/Downloads/" + imgfile + ".jpg";
        FileInputStream fis = new FileInputStream(new File(path));
        byte[] bytes = new byte[fis.available()];
        fis.read(bytes);
        return new String(bytes);
    }


}


