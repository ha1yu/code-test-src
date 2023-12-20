package com.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * 文件上传正确编码案例
 */
@Controller
@RequestMapping("/upload1")
public class Upload1Controller {

    /**
     * 错误编码实例1
     * <p>
     * 请求地址：
     * http://127.0.0.1:8080/upload/upload11.html
     * http://127.0.0.1:8080/upload1/demo1
     * <p>
     * 文件上传，无后缀名判断
     *
     * @param multipartFile 上传文件
     * @return 文件上传状态
     */
    @PostMapping("/demo1")
    @ResponseBody
    public String demo1(@RequestParam("file") MultipartFile multipartFile) {
        System.out.println("/*************************************************************/");
        String status = "文件上传状态1";
        String basePath = "C:\\Upload\\demo1";
        try {
            // 获取上传文件的文件名
            String fileName = multipartFile.getOriginalFilename();
            System.out.println("上传文件名：" + fileName);

            File filePath = new File(basePath);
            if (!filePath.exists()) {
                filePath.mkdirs();
            }

            File saveFile = new File(basePath + File.separator + fileName);
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
            status = "<h1>文件上传失败！</h1><br>";
            e.printStackTrace();
        }
        return status;
    }

    /**
     * 错误编码实例2
     * <p>
     * 请求地址：
     * http://127.0.0.1:8080/upload/upload12.html
     * http://127.0.0.1:8080/upload1/demo2
     * <p>
     * 双写后缀名绕过白名单，比如 name.png.jsp
     *
     * @param multipartFile 上传文件
     * @return 文件上传状态
     */
    @PostMapping("/demo2")
    @ResponseBody
    public String demo2(@RequestParam("file") MultipartFile multipartFile) {
        System.out.println("/*************************************************************/");
        String status = "文件上传状态2";
        String basePath = "C:\\Upload\\demo2";
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
            // 使用lastIndexOf可以避免双写绕过的问题
            String ext = fileName.substring(fileName.lastIndexOf("."));
            System.out.println("上传文件后缀名：" + ext);

            boolean flag = false;

            for (String str : extsList) {
                if (ext.contains(str)) { // 上传文件后缀名存在于白名单中，则返回true
                    flag = true;
                }
            }

            if (!flag) {
                throw new Exception("非法文件后缀名");
            }

            File filePath = new File(basePath);
            if (!filePath.exists()) {
                filePath.mkdirs();
            }

            File saveFile = new File(basePath + File.separator + fileName);
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
//            status = "<h1>文件上传失败！</h1><br>非法文件后缀名";
            status = "<h1>文件上传失败！</h1><br>" + e.getMessage();
            e.printStackTrace();
        }
        return status;
    }


    /**
     * 错误编码实例3
     * <p>
     * 请求地址：
     * http://127.0.0.1:8080/upload/upload13.html
     * http://127.0.0.1:8080/upload1/demo3
     * <p>
     * windows .jsp. 绕过
     *
     * @param multipartFile 上传文件
     * @param path          上传路径
     * @return 文件上传状态
     */
    @PostMapping("/demo3")
    @ResponseBody
    public String demo3(@RequestParam("file") MultipartFile multipartFile,
                        @RequestParam("path") String path) {
        System.out.println("/*************************************************************/");
        String status = "文件上传状态3";
        String basePath = "C:\\Upload\\demo3";

        try {
            // 文件白名单
            List<String> extsList = Arrays.asList(
                    ".jpg",
                    ".png",
                    ".doc",
                    ".docx"
            );

            // 获取上传文件的文件名
            String fileName = multipartFile.getOriginalFilename();
            System.out.println("上传文件名：" + fileName);

            File saveFile = new File(basePath + File.separator + path + File.separator + fileName);
            System.out.println("上传文件相对路径：" + saveFile.getPath());
            System.out.println("上传文件绝对路径：" + saveFile.getAbsolutePath());

            // 获取上传文件后缀名
            String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
            System.out.println("上传文件后缀名：" + ext);

            // 如果后缀名为null并且后缀名不在白名单里
            // 使用"".equals可以避免此漏洞
            if ("".equals(ext) && !extsList.contains(ext)) {
                throw new Exception("文件名后缀不能为空或文件后缀名非法");
            }

            File filePath = new File(basePath + File.separator + path);
            if (!filePath.exists()) {
                filePath.mkdirs();
            }

            // 保存文件
            multipartFile.transferTo(saveFile);
            status = String.format("<h1>文件上传成功！</h1><br>上传文件名：%s<br>上传文件相对路径：%s<br>上传文件绝对路径：%s",
                    fileName,
                    saveFile.getPath(),
                    saveFile.getAbsolutePath()
            );

        } catch (Exception e) {
            status = "<h1>文件上传失败！</h1><br>" + e.getMessage();
            e.printStackTrace();
        }
        return status;
    }


    /**
     * 错误编码实例4
     * <p>
     * 请求地址：
     * http://127.0.0.1:8080/upload/upload14.html
     * http://127.0.0.1:8080/upload1/demo4
     * <p>
     * 黑名单绕过
     *
     * @param multipartFile 上传文件
     * @return 文件上传状态
     */
    @PostMapping("/demo4")
    @ResponseBody
    public String demo4(@RequestParam("file") MultipartFile multipartFile) {
        System.out.println("/************************************************************/");
        String status = "文件上传状态4";
        String basePath = "C:\\Upload\\demo4";

        try {
            // 文件黑名单
            // 完善黑名单后缀，可以防止黑名单被绕过
            List<String> extsList = Arrays.asList(
                    ".jsp",
                    ".exe",
                    ".jspx",
                    ".jspf",
                    ".php",
                    ".asp",
                    ".aspx",
                    ".js"
            );

            // 获取上传文件的文件名
            String fileName = multipartFile.getOriginalFilename();
            System.out.println("上传文件名：" + fileName);

            // 获取上传文件后缀名
            String ext = fileName.substring(fileName.lastIndexOf("."));
            System.out.println("上传文件后缀名：" + ext);

            boolean flag = true;

            // 上传文件后缀名存在于黑名单中，则返回false
            if (extsList.contains(ext)) {
                flag = false;
            }

            if (!flag) {
                throw new Exception("非法文件后缀名");
            }

            File filePath = new File(basePath);
            if (!filePath.exists()) {
                filePath.mkdirs();
            }

            File saveFile = new File(basePath + File.separator + fileName);
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
//            status = "<h1>文件上传失败！</h1><br>非法文件后缀名";
            status = "<h1>文件上传失败！</h1><br>" + e.getMessage();
            e.printStackTrace();
        }
        return status;
    }

}
