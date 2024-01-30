package com.test.controller;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.InputSource;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;


@RequestMapping("/xxe")
public class XXEController extends HttpServlet {
    @RequestMapping
    public String index(){
        return "xxe";
    }

    @PostMapping("/demo1")
    @ResponseBody
    public String demo1(@RequestBody String s) throws DocumentException, IOException {
        SAXReader saxReader = new SAXReader();
        Document read = saxReader.read(new InputSource(new ByteArrayInputStream(s.getBytes("utf-8"))));
        return read.getRootElement().getStringValue();
    }
}
