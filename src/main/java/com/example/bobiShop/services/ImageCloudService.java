package com.example.bobiShop.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
public class ImageCloudService {


        private final Cloudinary cloudinary;

        public ImageCloudService() {

            cloudinary = new Cloudinary(ObjectUtils.asMap(
                    "cloud_name", "dlygen7nm",
                    "api_key", "776776882234135",
                    "api_secret", "9a7oGhMiZiP2F5rV4bbEpq-jzg0",
                    "secure", true));
        }

        public String saveImage(MultipartFile multipartFile) {
            String imageId = UUID.randomUUID().toString();
            Map params = ObjectUtils.asMap(
                    "public_id", imageId,
                    "overwrite", true,
                    "resource_type", "image"
            );
            File tmpFile = new File(imageId);

        try {
                Files.write(tmpFile.toPath(), multipartFile.getBytes());
                cloudinary.uploader().upload(tmpFile, params);
                Files.delete(tmpFile.toPath());

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return String.format("https://res.cloudinary.com/dlygen7nm/image/upload/v1684412903/" +
                    imageId + "." + getFileExtension(Objects.requireNonNull(multipartFile.getOriginalFilename())));
        }
        private String getFileExtension(String filename){
            return filename.substring(filename.lastIndexOf(".")+1);
        }

    }



