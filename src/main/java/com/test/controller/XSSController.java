package com.test.controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/xss")
public class XSSController {

    /**
     * 编辑器XSS测试
     * @param details 前端编辑器传入参数
     * @return 直接将前端接受回显在页面上
     */
    @ResponseBody
    @RequestMapping("/insert")
    public String insertNews(@RequestParam(name = "details",defaultValue = " ")String details){
        System.out.println(details);
        // 直接将编辑器的内容返回至前端，存储型只是将数据存到数据库中再进行读取，与直接显示在前端基本无区别
        return details;
    }

    /**
     * 编辑器无XSS的请求方法
     * @param details 前端编辑器传入参数
     * @return 将输入进行过滤再回显
     */
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


    /**
     * 对编辑器传入内容进行处理
     * @param str 编辑器传入内容
     * @return 处理完的字符串
     * 处理思路：标签白名单、属性白名单
     * 具体实现思路：由于编辑器传入的内容，相当于是一段HTML，所以可以使用Jsoup进行处理，拿到其中所有标签和属性
     *            重新构造一段HTML代码，只从前端输入中拿白名单中的标签和白名单中的属性，对于白名单中的属性或
     *            标签，直接进行忽略，这样能够解决大部分恶意标签和恶意属性；
     *            对于白名单中，并且能够造成XSS的标签：例如 a\img等标签，只接受白名单中的属性，例如title\src等
     *            对于一些可以使用伪协议的属性，比如a标签中的href属性，可以使用javascript伪协议，由于正常业务
     *            中几乎不可能使用该为协议，几乎都是http或https协议，所以此时可以通过检查传入href属性值前缀是否
     *            为http或https
     *
     *
     */
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
                        // a标签中 href属性只允许是http或https开头
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
