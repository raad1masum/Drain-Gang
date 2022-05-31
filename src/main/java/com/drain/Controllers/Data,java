package com.drain.Controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

import com.drain.Model.Database;
import org.springframework.core.io.ClassPathResource;

import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class Data {
    @GetMapping("/data")
    public String data() {
        return "data";
    }

    @GetMapping("/upload-form")
    public String uploadForm() {
        return "upload-form";
    }

    @GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "upload-status";
    }

    @GetMapping("/otherStatus")
    public String otherStatus() {
        return "upload-status-bad";
    }

    @PostMapping(value="/upload")
    public String uploadFile(@RequestParam String jwt, @RequestParam("file") MultipartFile file) throws SQLException {
        String username = Authentication.verifyJWT(jwt);

        if ( username == null ) {
            return "403";
        }

        if (file.isEmpty()) {
            return "redirect:otherStatus";
        }

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(new ClassPathResource("/data").getFile().getAbsolutePath() + "/" + file.getOriginalFilename());
            System.out.println(path.toString());
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:otherStatus";
        }

        Database.addFile("alvin", file.getOriginalFilename());

		return "redirect:data";
    }
}