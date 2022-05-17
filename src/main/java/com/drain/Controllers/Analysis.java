package com.drain.Controllers;
import org.springframework.data.annotation.ReadOnlyProperty;
import java.lang.*;
import java.nio.file.Paths;
import java.util.List;
import java.io.*;

public class Analysis {

    public void blue() throws Exception {
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
        System.out.println("Complete");

        in.close();
        System.exit(0);
    }

    public static void main(String[] args) throws Exception {
        Analysis blue = new Analysis();
        blue.blue();
    }
}
