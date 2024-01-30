package com.test.controller;

import com.test.vo.XXEVo;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.InputSource;

import javax.servlet.http.HttpServlet;
import java.io.ByteArrayInputStream;
import java.io.IOException;


@Controller
@RequestMapping("/xxe")
public class XXEController extends HttpServlet {
    @RequestMapping("")
    public String index(){
        return "xxe";
    }

    @PostMapping("/demo1")
    @ResponseBody
    public XXEVo demo1(@RequestBody String s) throws DocumentException, IOException {
        System.out.println(s);
        SAXReader saxReader = new SAXReader();
        Document read = saxReader.read(new InputSource(new ByteArrayInputStream(s.getBytes("utf-8"))));
        XXEVo xxeVo = new XXEVo();
        xxeVo.setDocumentText(read.getRootElement().getStringValue());
        return xxeVo;
    }
}
