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

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/ssrf")
public class SSRFController extends HttpServlet {

    @GetMapping("")
    public String sqlIndex(Model model) {
        return "ssrf";
    }

    @ResponseBody
    @RequestMapping({"/demo1"})
    public void ssrf1(String url, HttpServletResponse resp) throws Exception {
        URL u = new URL(url);
        URLConnection urlConnection = u.openConnection();
        //获取内容输出前端
        InputStream inputStream = urlConnection.getInputStream();
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        resp.getOutputStream().write(bytes);
        inputStream.close();
    }

    @ResponseBody
    @RequestMapping({"/demo2"})
    public void ssrf2(String url, HttpServletResponse resp) throws Exception {
        //http://127.0.0.1:8080/ssrf/demo2?url=http://127.0.0.1:8080/download?filename=../../../../Windows/win.ini%26a=
        url = url + "/file/img/xx.jpg?key=123456";
        URL u = new URL(url);
        HttpURLConnection urlConnection = (HttpURLConnection)u.openConnection();
        //获取内容输出前端
        InputStream inputStream = urlConnection.getInputStream();
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        resp.getOutputStream().write(bytes);
        inputStream.close();
    }

    @ResponseBody
    @RequestMapping({"/demo3"})
    public void ssrf3(String path, HttpServletResponse resp) throws Exception {
        //http://127.0.0.1:8080/ssrf/demo3?path=@127.0.0.1:8080/download?filename=../../../../../../Windows/win.ini
        String url = "http://123.11.123.11" + path;
        URL u = new URL(url);
        URLConnection urlConnection = u.openConnection();
        //获取内容输出前端
        InputStream inputStream = urlConnection.getInputStream();
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        resp.getOutputStream().write(bytes);
        inputStream.close();
    }
}
