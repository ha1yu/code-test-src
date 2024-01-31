package com.test.controller;

import com.test.vo.XXEVo;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Controller
@RequestMapping("/xxeOk")
public class XXEOKController {
    @RequestMapping("")
    public String index(){
        return "xxeOk";
    }

    @PostMapping("/demo1")
    @ResponseBody
    public XXEVo demo1(@RequestBody String s) throws Exception {
        System.out.println(s);
        SAXReader saxReader = new SAXReader();
        saxReader.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        saxReader.setFeature("http://xml.org/sax/features/external-general-entities", false);
        saxReader.setFeature("http://xml.org/sax/features/external-parameter-entities", false);

        Document read = saxReader.read(new InputSource(new ByteArrayInputStream(s.getBytes("utf-8"))));
        XXEVo xxeVo = new XXEVo();
        xxeVo.setDocumentText(read.getRootElement().getStringValue());
        return xxeVo;
    }
}
