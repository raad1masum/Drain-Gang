package com.drain.Controllers;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller 
public class PyTools {

    @GetMapping("/getScores")
    public String getScores(String dataPath) throws IOException, InterruptedException {

        /* Logic to check if user is authorized to look for files at this path

        */

        // Build process to run script
        String scriptPath = "C:\\Users\\akshay\\Desktop\\CSA\\CSA_Projects\\Drain-Gang\\src\\main\\resources\\scripts\\get_scores.py";
        ProcessBuilder processBuilder = new ProcessBuilder("python", scriptPath, dataPath);

        processBuilder.redirectErrorStream(true);
        
        // Start python script process
        Process process = processBuilder.start();

        // Read output buffer
        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));

        // Save output as variable 'ret'
        String line;
        String ret = "";
        while ((line = in.readLine()) != null) {
            ret = ret + line;
        }
        
        process.waitFor();
        
        in.close();

        return ret;
    }

    public String replacewithNewScores(String script, String data, ArrayList<Double> newScores) throws Exception {
        String path = "";
        String args = script + " replace " + data + " " + newScores.toString();
        ProcessBuilder processBuilder = new ProcessBuilder("python", args);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();
        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
        process.waitFor();
        in.close();
        return path;
    }

    // public ArrayList<Double> returnScores(String script, String data) throws Exception {
    //     String args = script + " return " + data;
    //     ProcessBuilder processBuilder = new ProcessBuilder("python", args);
    //     processBuilder.redirectErrorStream(true);
    //     Process process = processBuilder.start();
    //     BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));

    //     String line;
    //     Double t;
    //     while ((line = in.readLine()) != null) {
    //         t = Double.valueOf(line);
    //     }

    //     process.waitFor();
    //     in.close();
    // }

    public void min_and_max(String path) throws Exception {
        // Path p1 = Paths.get("min_and_max.py");
        ProcessBuilder processBuilder = new ProcessBuilder("python", "\\Users\\akshay\\Desktop\\CSA\\CSA_Projects\\Drain-Gang\\src\\main\\java\\com\\drain\\Controllers\\min_and_max.py");
        processBuilder.redirectErrorStream(true);
        
        Process process = processBuilder.start();
        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
        // List<String> results = readProcessOutput(process.getInputStream());

        String line;
        while ((line = in.readLine()) != null) {
            System.out.println(line);
        }

        process.waitFor();

        in.close();
    }

    public void average(String path) throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder("python", "\\Users\\akshay\\Desktop\\CSA\\CSA_Projects\\Drain-Gang\\src\\main\\java\\com\\drain\\Controllers\\average.py");
        processBuilder.redirectErrorStream(true);
        
        Process process = processBuilder.start();
        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line;
        while ((line = in.readLine()) != null) {
            System.out.println(line);
        }

        process.waitFor();

        in.close();
    }


}
