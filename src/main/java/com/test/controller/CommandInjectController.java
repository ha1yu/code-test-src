package com.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping("/command")
public class CommandInjectController extends HttpServlet {
    @RequestMapping("")
    public String index(){
        return "commandInject";
    }

    @RequestMapping ("/demo1")
    @ResponseBody
    public void demo1(@RequestParam String cmd , HttpServletResponse response) throws IOException {
        ServletOutputStream outputStream = response.getOutputStream();
        InputStream inputStream = null;
        try {
            Process exec = Runtime.getRuntime().exec(cmd);
            //读取执行内容
            inputStream = exec.getInputStream();

            byte[] bytes = new byte[1024];
            int len = 0;
            while((len = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            inputStream.close();
        }
    }

    @RequestMapping ("/demo2")
    @ResponseBody
    public void demo2(@RequestParam String cmd , HttpServletResponse response) throws IOException {
        ServletOutputStream outputStream = response.getOutputStream();
        InputStream inputStream = null;
        cmd = "cmd /C ping " + cmd;
        try {
            Process exec = Runtime.getRuntime().exec(cmd);
            //读取执行内容
            inputStream = exec.getInputStream();

            byte[] bytes = new byte[1024];
            int len = 0;
            while((len = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            inputStream.close();
            outputStream.close();
        }
    }
}
