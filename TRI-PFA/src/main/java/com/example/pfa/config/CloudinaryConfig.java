package com.example.pfa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;

@Configuration
public class CloudinaryConfig {

    @Bean(name = "cloudinaryBean")
    public Cloudinary cloudinaryConfig() {
        return new Cloudinary("cloudinary://651432129861668:TyR4ikA-QBvlzY3u4iSIYAUhzjM@dwdj6qahk");
    }
}
