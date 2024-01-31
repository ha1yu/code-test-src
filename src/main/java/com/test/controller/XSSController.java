package com.test.controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/xss")
public class XSSController {

    @ResponseBody
    @RequestMapping("/insert")
    public String insertNews(@RequestParam(name = "details",defaultValue = " ")String details){
        System.out.println(details);
        return details;
    }

    @ResponseBody
    @RequestMapping("/insertOk")
    public String insertNewsOk(@RequestParam(name = "details",defaultValue = " ")String details){
        System.out.println(details);
        return xssFilter(details);
    }

    @RequestMapping("")
    public String sqlIndex(Model model) {
        return "xss";
    }


    private String xssFilter(String str){

        Document document = Jsoup.parse(str);

        Element pTag = document.select("p").first();

        // 创建一个空白的HTML文档
        Document newDocument = Jsoup.parse("");
        // 创建一个新的p标签并添加到文档中
        Element newPTag = newDocument.body().appendElement("p").attr("style", pTag.attr("style"));

        processChildNodes(pTag.childNodes(), newPTag);

        return newDocument.body().toString();
    }

    private static void processChildNodes(List<Node> childNodes, Element parent) {
        for(Node childNode : childNodes){
            if(childNode instanceof Element){
                Element childElement = (Element)childNode;
                switch (childElement.tagName()) {
                    case "a":
                        Element newATag = parent.appendElement(childElement.tagName());
                        if (childElement.attr("href").startsWith("http://")||childElement.attr("href").startsWith("https://")){
                            newATag.attr("title", childElement.attr("title")).attr("href", childElement.attr("href"));
                        }else {
                            newATag.attr("title", childElement.attr("title")).attr("href", "http://"+childElement.attr("href"));
                        }

                        processChildNodes(childElement.childNodes(), newATag);
                        break;
                    case "span":
                    case "strong":
                    case "h1":
                    case "h2":
                    case "h3":
                    case "h4":
                    case "h5":
                    case "h6":
                        Element newSpanTag = parent.appendElement(childElement.tagName());
                        processChildNodes(childElement.childNodes(), newSpanTag);
                        break;
                    default:
                        String rawText = childElement.text();
                        parent.appendText(rawText);
                        break;
                }
            }else if(childNode instanceof TextNode){
                parent.appendText(((TextNode) childNode).getWholeText());
            }
        }
    }
}
