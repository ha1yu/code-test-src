package com.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping("/commandOk")
public class CommandInjectOkController extends HttpServlet {
    @RequestMapping("")
    public String index(){
        return "commandInjectOk";
    }

    @RequestMapping ("/demo1")
    @ResponseBody
    public void demo1(@RequestParam String cmd , HttpServletResponse response) throws IOException {
        ServletOutputStream outputStream = response.getOutputStream();
        InputStream inputStream = null;
        try {
            Process exec = Runtime.getRuntime().exec("ping " + cmd);
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
