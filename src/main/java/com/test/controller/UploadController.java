package com.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * 文件上传案例
 */
@Controller
@RequestMapping("/upload")
public class UploadController {

    @PostMapping("/demo1")
    @ResponseBody
    public String demo1(@RequestParam("file") MultipartFile multipartFile) throws Exception {
        System.out.println("/*************************************************************/");
        String status = "文件上传状态";
        try {
            // 获取上传文件的文件名
            String fileName = multipartFile.getOriginalFilename();
            System.out.println("上传文件名：" + fileName);

            File saveFile = new File(fileName);
            System.out.println("上传文件相对路径：" + saveFile.getPath());
            System.out.println("上传文件绝对路径：" + saveFile.getAbsolutePath());

            // 保存文件
            multipartFile.transferTo(saveFile);
            status = String.format("<h1>文件上传成功！</h1><br>上传文件名：%s<br>上传文件相对路径：%s<br>上传文件绝对路径：%s",
                    fileName,
                    saveFile.getPath(),
                    saveFile.getAbsolutePath()
            );
        } catch (Exception e) {
            throw new Exception(e);
        }
        return status;
    }

    @PostMapping("/demo2")
    @ResponseBody
    public String demo2(@RequestParam("file") MultipartFile multipartFile) throws Exception {
        System.out.println("/*************************************************************/");
        String status = "文件上传状态";
        try {
            // 文件白名单
            String[] extsList = new String[]{
                    ".jpg",
                    ".png",
                    ".doc",
                    ".docx"
            };
            // 获取上传文件的文件名
            String fileName = multipartFile.getOriginalFilename();
            System.out.println("上传文件名：" + fileName);

            // 获取上传文件后缀名
            String ext = fileName.substring(fileName.indexOf("."));
            System.out.println("上传文件后缀名：" + ext);

            boolean flag = false;

            for (String str : extsList) {
                if (ext.contains(str)) { // 白名单中存在，则返回true
                    flag = true;
                }
            }

            if (!flag) {
                throw new Exception("非法文件后缀名");
            }

            File saveFile = new File(fileName);
            System.out.println("上传文件相对路径：" + saveFile.getPath());
            System.out.println("上传文件绝对路径：" + saveFile.getAbsolutePath());

            // 保存文件
            multipartFile.transferTo(saveFile);
            status = String.format("<h1>文件上传成功！</h1><br>上传文件名：%s<br>上传文件相对路径：%s<br>上传文件绝对路径：%s",
                    fileName,
                    saveFile.getPath(),
                    saveFile.getAbsolutePath()
            );
        } catch (Exception e) {
            status = "<h1>文件上传失败！</h1><br>非法文件后缀名";
//            throw new Exception(e);
        }
        return status;
    }


    @RequestMapping("/hello")
    @ResponseBody
    public String hello(@RequestParam(name = "name", defaultValue = "unknown user") String name) {
//        return "Hello " + name;
        return "Hello";
    }
}
