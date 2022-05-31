package com.drain.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.auth0.jwt.*;
import com.auth0.jwt.algorithms.Algorithm;

@Controller
public class Authentication {
    @GetMapping(value = "/login")
    @ResponseBody
    public String getJWT(@RequestParam String username, @RequestParam String password) {
        String token = JWT.create().withAudience(username).sign(Algorithm.HMAC256("development_secret"));

        return token;
    }
}
