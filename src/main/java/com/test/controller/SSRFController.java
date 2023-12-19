package com.test.controller;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SSRFController extends HttpServlet {

    @RequestMapping({"/ssrf1"})
    public void ssrf1(String url, HttpServletResponse resp) throws Exception {
        URL u = new URL(url);
        URLConnection urlConnection = u.openConnection();
        //获取内容输出前端
        InputStream inputStream = urlConnection.getInputStream();
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        resp.getOutputStream().write(bytes);
    }

    @RequestMapping({"/ssrf2"})
    public void ssrf2(String url, HttpServletResponse resp) throws Exception {
        //http://127.0.0.1:8080/ssrf2?url=http://127.0.0.1:8080/download?filename=../../../../Windows/win.ini%26a=
        url = url + "/file/img/xx.jpg?key=123456";
        URL u = new URL(url);
        HttpURLConnection urlConnection = (HttpURLConnection)u.openConnection();
        //获取内容输出前端
        InputStream inputStream = urlConnection.getInputStream();
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        resp.getOutputStream().write(bytes);
    }

    @RequestMapping({"/ssrf3"})
    public void ssrf3(String path, HttpServletResponse resp) throws Exception {
        //http://127.0.0.1:8080/ssrf3?path=@baidu.com
        String url = "http://123.11.123.11:56789" + path;
        URL u = new URL(url);
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
