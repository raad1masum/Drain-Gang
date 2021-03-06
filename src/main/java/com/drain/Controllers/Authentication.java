package com.drain.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;

import com.auth0.jwt.*;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.drain.Model.Database;

@Controller
public class Authentication {

    @GetMapping(value = "/login-form")
    public String loginForm() {
        return "login";
    }

    @PostMapping(value = "/login")
    @ResponseBody
    public String getJWT(@RequestParam String username, @RequestParam String password) throws SQLException {
        String token = JWT.create().withAudience(username).sign(Algorithm.HMAC256("development_secret"));

        if (Database.checkCredentials(username, password)) {
            return token;
        }

        return "BAD_CREDENTIALS";
    }

    @GetMapping(value="/get-name")
    @ResponseBody
    public String getName(@RequestParam String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256("development_secret")).build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getAudience().get(0);
        } catch (JWTVerificationException e) {
            return "NULL";
        }
    }

    public static String verifyJWT(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256("development_secret")).build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getAudience().get(0);
        } catch (JWTVerificationException e) {
            return null;
        }
    }
}
