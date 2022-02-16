package com.ares.system.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @description: 文件
 * @author: Young
 * @date: 2020/09/23
 * @see: com.ares.system.controller UploadApiController.java
 **/
@RestController
@RequestMapping("/upload/*")
@Api(value = "上传文件API", tags = {"上传文件"})
public class UploadApiController {

    public void upload(MultipartFile file) {

    }
}
