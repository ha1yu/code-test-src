package com.test.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;

@RestController
public class PathTraversalController extends HttpServlet{
    @RequestMapping("/download")
    public void download(String filename, HttpServletResponse resp) throws Exception {
        //请求：http://127.0.0.1:8080/download?filename=../../../../Windows/win.ini
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
        //请求：http://127.0.0.1:8080/download2?filepath=../../../../Windows&filename=win.ini
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
        //请求：http://127.0.0.1:8080/download3?filename=..././..././..././..././..././Windows/win.ini
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
}
