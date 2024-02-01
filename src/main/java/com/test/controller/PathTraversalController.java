package com.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

@Controller
@RequestMapping("/path")
public class PathTraversalController extends HttpServlet{

    @RequestMapping("")
    public String index(){
        return "path";
    }
    @ResponseBody
    @RequestMapping("/download1")
    public void download(String filename, HttpServletResponse resp) throws Exception {
        //请求：http://127.0.0.1:8080/readfile1/download?filename=../../../../Windows/win.ini
        ServletOutputStream outputStream = resp.getOutputStream();

        //读取文件
        String filepath = "C:/Users/lb/Downloads/"+ filename;
        outputStream.write(("\r\n拼接后路径：" + filepath +"\r\n").getBytes());
        outputStream.write("---------------------------------------------------\r\n".getBytes());
        FileInputStream fileInputStream = new FileInputStream(new File(filepath));
        byte[] bytes = new byte[fileInputStream.available()];
        fileInputStream.read(bytes);

        //输出
        outputStream.write(bytes);
        fileInputStream.close();
    }

    @ResponseBody
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
        outputStream.write(("\r\n拼接后路径：" + file + "\r\n").getBytes());
        outputStream.write("---------------------------------------------------\r\n".getBytes());
        FileInputStream fileInputStream = new FileInputStream(new File(file));
        byte[] bytes = new byte[fileInputStream.available()];
        fileInputStream.read(bytes);

        //输出
        outputStream.write(bytes);
    }



    @ResponseBody
    @RequestMapping("/download3")
    public void download3(String filename, HttpServletResponse resp) throws Exception {
        //请求：http://127.0.0.1:8080/readfile1/download3?filename=..././..././..././..././..././Windows/win.ini
        ServletOutputStream outputStream = resp.getOutputStream();

        //拼接路径
        String file = "C:/Users/lb/Downloads/" + filename;

        //替换../
        file = file.replace("../","").replace("..\\","");

        //读取文件
        outputStream.write(("\r\n拼接后路径：" + file +"\r\n").getBytes());
        outputStream.write("---------------------------------------------------\r\n".getBytes());
        FileInputStream fileInputStream = new FileInputStream(new File(file));
        byte[] bytes = new byte[fileInputStream.available()];
        fileInputStream.read(bytes);

        //输出
        outputStream.write(bytes);
    }

    @ResponseBody
    @RequestMapping("/unzip")
    public void unzip(String zipfile,HttpServletResponse response) throws Exception {
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
            response.getOutputStream().write(("解压路径：" + outfile.getPath()).getBytes("utf-8"));
            fos.write(bytes);
            fos.close();
            inputStream.close();
        }
    }

    @ResponseBody
    @RequestMapping("/readImg")
    public String readImg(String imgfile) throws Exception {
        String path = "C:/Users/lb/Downloads/" + imgfile + ".jpg";
        FileInputStream fis = new FileInputStream(new File(path));
        byte[] bytes = new byte[fis.available()];
        fis.read(bytes);
        return new String(bytes);
    }
}


