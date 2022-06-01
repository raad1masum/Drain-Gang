package com.drain.Controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Map;

import com.drain.Model.Database;

import org.apache.tomcat.util.json.ParseException;
import org.json.simple.JSONObject;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PyTools {
    @GetMapping(value = "/get-scores")
    @ResponseBody
    public String getScores(@RequestParam String jwt, @RequestParam String file)
            throws IOException, InterruptedException, SQLException {

        String username = Authentication.verifyJWT(jwt);
        if (username == null || !Database.checkFileOwned(username, file)) {
            return "BAD_CREDENTIALS";
        }

        String scriptPath = new ClassPathResource("scripts/get_scores.py").getFile().getAbsolutePath();
        String dataPath = new ClassPathResource("data/" + file).getFile().getAbsolutePath();

        ProcessBuilder processBuilder = new ProcessBuilder("python3", scriptPath, dataPath);
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();
        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line;
        String ret = "";
        while ((line = in.readLine()) != null) {
            ret = ret + line;
        }

        process.waitFor();
        in.close();

        return ret;
    }

    @PostMapping(value = "/replace-scores")
    @ResponseBody
    public String replaceScores(@RequestBody JSONObject jsonData, @RequestParam String jwt, @RequestParam String file)
            throws IOException, InterruptedException, ParseException, SQLException {

        String username = Authentication.verifyJWT(jwt);
        if (username == null || !Database.checkFileOwned(username, file)) {
            return "BAD_CREDENTIALS";
        }

        @SuppressWarnings("unchecked")
        Map<String, Integer> a = (Map<String, Integer>) jsonData;

        String data = "\"{";
        for (Map.Entry<String, Integer> set : a.entrySet()) {
            data = data + "\\\"" + set.getKey() + "\\\":" + set.getValue() + ", ";
        }
        data = data.substring(0, data.length() - 2);
        data = data + "}\"";

        String scriptPath = new ClassPathResource("scripts/replace_scores.py").getFile().getAbsolutePath();
        String dataPath = new ClassPathResource("data").getFile().getAbsolutePath();

        System.out.println("python3" + " " + scriptPath + " " + data + " " + dataPath + " " + file);
        ProcessBuilder processBuilder = new ProcessBuilder("python3", scriptPath, data, dataPath, file);
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();

        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line;
        String ret = "";
        while ((line = in.readLine()) != null) {
            ret = ret + line;
        }

        process.waitFor();
        in.close();

        return ret;
    }
}
