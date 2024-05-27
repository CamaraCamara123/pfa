package com.example.pfa.services;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.api.ApiResponse;
import com.cloudinary.utils.ObjectUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileUploadImpl {

    private final Cloudinary cloudinary;

    public String uploadFile(MultipartFile multipartFile) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(multipartFile.getBytes(), ObjectUtils.emptyMap());
        return (String) uploadResult.get("url");
    }

    public void deleteFile(String publicIdToDelete) throws Exception {
        try {
            ApiResponse apiResponse = cloudinary.api().deleteResources(Arrays.asList(publicIdToDelete.toString()),
               ObjectUtils.asMap("type", "upload", "resource_type", "image"));
           System.out.println(apiResponse);
         } catch (IOException exception) {
           System.out.println("erreur :::::::::::::::::: "+exception.getMessage());
         }
    }

}
