package com.drain.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;

import com.auth0.jwt.*;
import com.auth0.jwt.algorithms.Algorithm;
import com.drain.Model.Database;

@Controller
public class Authentication {
    @PostMapping(value = "/login")
    @ResponseBody
    public String getJWT(@RequestParam String username, @RequestParam String password) throws SQLException {
        

        return "BAD CREDENTIALS";
    }
}
