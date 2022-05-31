package com.drain.Controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

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
    public String getScores(@RequestParam String file) throws IOException, InterruptedException {

        /*
         * Logic to check if user is authorized to look for files at this path
         * 
         */

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
    public void replaceScores(@RequestBody JSONObject params)
            throws IOException, InterruptedException, ParseException {
        /*
         * Logic to check if user is authorized to look for files at this path
         * 
         */

        @SuppressWarnings("unchecked")
        Map<String, Integer> a = (Map<String, Integer>) params.get("data");

        String data = "\"{";
        for (Map.Entry<String, Integer> set : a.entrySet()) {
            data = data + "\\\"" + set.getKey() + "\\\":" + set.getValue() + ", ";
        }
        data = data.substring(0, data.length() - 2);
        data = data + "}\"";

        String filename = params.get("filename").toString();

        String scriptPath = new ClassPathResource("scripts/replace_scores.py").getFile().getAbsolutePath();
        String dataPath = new ClassPathResource("data").getFile().getAbsolutePath();

        ProcessBuilder processBuilder = new ProcessBuilder("python3", scriptPath, data, dataPath, filename);
        processBuilder.start();
    }
}
