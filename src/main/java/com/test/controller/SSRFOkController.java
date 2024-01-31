package com.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/ssrfOk")
public class SSRFOkController {

    @GetMapping("")
    public String sqlIndex(Model model) {
        return "ssrfOk";
    }

    @ResponseBody
    @RequestMapping({"/demo1"})
    public void ssrf1(String url, HttpServletResponse resp) throws Exception {
        URL u = new URL(url);
        //url合法性校验
        if(!checkUrl(u)){
            resp.getOutputStream().write("非法URL".getBytes("utf-8"));
            return;
        }
        URLConnection urlConnection = u.openConnection();
        //获取内容输出前端
        InputStream inputStream = urlConnection.getInputStream();
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        resp.getOutputStream().write(bytes);
    }

    public boolean checkUrl(URL url) throws MalformedURLException {
        String s1 = url.toString();
        //判断是否为http、https
        if (!Pattern.matches("^http?://.*/*$", s1)) {
            return false;
        } else {
            String ip = "";
            if (url.getPort() != -1) {
                ip = url.getHost() + ":" + url.getPort();
            } else {
                ip = url.getHost();
            }
            //白名单校验ip端口
            String[] s = new String[]{"192.168.100.100:8080", "172.16.100.100:8080", "baidu.com"};
            return Arrays.asList(s).contains(ip);
        }
    }
}
