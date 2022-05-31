package com.drain.Controllers;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

import com.drain.Model.Database;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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

    @RequestMapping(path = "/download-file", method = RequestMethod.GET)
    public ResponseEntity<Resource> downloadFile(@RequestParam String jwt, @RequestParam String file)
            throws IOException, SQLException {

        System.out.println(Authentication.verifyJWT(jwt));
        if (Authentication.verifyJWT(jwt) == null
                ||
                !Database.checkFileOwned(Authentication.verifyJWT(jwt), file)) {
            return ResponseEntity.badRequest().body(null);
        }

        String dataPath = new ClassPathResource("data/" + file).getFile().getAbsolutePath();
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(Paths.get(dataPath)));

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "text/plain");

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"" + file + "\"")
                .contentLength(resource.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }

    @PostMapping(value = "/get-file-list")
    @ResponseBody
    public String getFileList(@RequestParam String jwt) throws SQLException {
        String username = Authentication.verifyJWT(jwt);
        if (username == null) {
            return "400";
        }

        return Database.getFileList(username);
    }

    @PostMapping(value = "/upload")
    public String uploadFile(@RequestParam String jwt, @RequestParam("file") MultipartFile file) throws SQLException {
        String username = Authentication.verifyJWT(jwt);

        if (username == null) {
            return "403";
        }

        if (file.isEmpty()) {
            return "redirect:otherStatus";
        }

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths
                    .get(new ClassPathResource("/data").getFile().getAbsolutePath() + "/" + file.getOriginalFilename());
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