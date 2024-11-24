package com.example.gameRecommend.controller;

import com.example.gameRecommend.utils.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;

@RestController
@RequestMapping("/upload")
public class UploadController {
    @Value("${file.upload-dir}")
    private String uploadDir;
    @Value("${file.upload-dir2}")
    private String uploadDir2;
    @Value("${file.upload-dir3}")
    private String uploadDir3;

    @PostMapping("/blog")
    public Result uploadFile3(@RequestParam("file") MultipartFile file) {
        Result result = new Result();
        if (file.isEmpty()) {
            result.setCode(500);
            result.setMsg("上传文件为空");
            return result;
        }

        try {
            // 获取文件名和目标路径
            String fileName = file.getOriginalFilename();
            Path targetLocation = Paths.get(uploadDir3, fileName);

            // 创建上传目录，如果目录不存在
            Files.createDirectories(targetLocation.getParent());  // 创建父目录

            // 保存文件到目标路径
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            // 返回文件的URL路径，假设你通过域名访问该文件
            String fileUrl = "/blog/" + fileName;

            // 你可以将URL存入数据库
            result.setCode(200);
            result.setMsg("");
            result.setData(Collections.singletonMap("url", fileUrl));
            return result;

        } catch (IOException e) {
            e.printStackTrace();
            result.setCode(500);
            result.setMsg("上传文件失败");
            return result;
        }
    }
    @PostMapping("/game")
    public Result uploadFile2(@RequestParam("file") MultipartFile file) {
        Result result = new Result();
        if (file.isEmpty()) {
            result.setCode(500);
            result.setMsg("上传文件为空");
            return result;
        }

        try {
            // 获取文件名和目标路径
            String fileName = file.getOriginalFilename();
            Path targetLocation = Paths.get(uploadDir2, fileName);

            // 创建上传目录，如果目录不存在
            Files.createDirectories(targetLocation.getParent());  // 创建父目录

            // 保存文件到目标路径
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            // 返回文件的URL路径，假设你通过域名访问该文件
            String fileUrl = "/game/" + fileName;

            // 你可以将URL存入数据库
            result.setCode(200);
            result.setMsg("");
            result.setData(Collections.singletonMap("url", fileUrl));
            return result;

        } catch (IOException e) {
            e.printStackTrace();
            result.setCode(500);
            result.setMsg("上传文件失败");
            return result;
        }
    }
    @PostMapping("/avatar")
    public Result uploadFile(@RequestParam("file") MultipartFile file) {
        Result result = new Result();
        if (file.isEmpty()) {
            result.setCode(500);
            result.setMsg("上传文件为空");
            return result;
        }

        try {
            // 获取文件名和目标路径
            String fileName = file.getOriginalFilename();
            Path targetLocation = Paths.get(uploadDir, fileName);

            // 创建上传目录，如果目录不存在
            Files.createDirectories(targetLocation.getParent());  // 创建父目录

            // 保存文件到目标路径
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            // 返回文件的URL路径，假设你通过域名访问该文件
            String fileUrl = "/avatar/" + fileName;

            // 你可以将URL存入数据库
            result.setCode(200);
            result.setMsg("");
            result.setData(Collections.singletonMap("url", fileUrl));
            return result;

        } catch (IOException e) {
            e.printStackTrace();
            result.setCode(500);
            result.setMsg("上传文件失败");
            return result;
        }
    }

}
