package org.springboot.demo.upload.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;


/**
 * @author djsona
 */
@Slf4j
@RestController
@RequestMapping("/upload")
@CrossOrigin
public class UploadController {

    @Value("${prop.upload-folder}")
    private String upLoad_Folder;

    @PostMapping("/singleFile")
    public Object singleFileUpload(MultipartFile file) {
        log.debug("传入的文件参数：{}", JSON.toJSONString(file, true));
        if (Objects.isNull(file) || file.isEmpty()) {
            log.error("文件为空");
            return "文件为空，请重新上传";
        }

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(upLoad_Folder + file.getOriginalFilename());
            //如果没有files文件夹，则创建
            if (!Files.isWritable(path)) {
                Files.createDirectories(Paths.get(upLoad_Folder));
            }
            //文件写入指定路径
            Files.write(path, bytes);
            log.debug("文件写入成功...");
            return "文件上传成功";
        } catch (IOException e) {
            e.printStackTrace();
            return "后端异常...";
        }
    }
}
