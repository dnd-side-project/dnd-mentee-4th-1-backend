package org.dnd4.yorijori.uploader.controller;

import lombok.AllArgsConstructor;
import org.dnd4.yorijori.domain.common.Result;
import org.dnd4.yorijori.uploader.service.S3UploaderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@AllArgsConstructor
public class S3UploaderController {
    private S3UploaderService s3UploaderService;

    @PostMapping("/upload/step")
    public Result uploadStep(@RequestParam("data") MultipartFile multipartFile) throws IOException {
        String imageUrl = s3UploaderService.upload(multipartFile, "step");
        return new Result(imageUrl);
    }

    @PostMapping("/upload/profile")
    public Result uploadProfile(@RequestParam("data") MultipartFile multipartFile) throws IOException {
        String imageUrl = s3UploaderService.upload(multipartFile, "profile");
        return new Result(imageUrl);
    }

    @PostMapping("/upload/thumbnail")
    public Result upload(@RequestParam("data") MultipartFile multipartFile) throws IOException {
        String imageUrl = s3UploaderService.upload(multipartFile, "thumbnail");
        return new Result(imageUrl);
    }

}
