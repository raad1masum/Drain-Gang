package com.drain.Model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.core.io.ClassPathResource;

public class Database {
    private static String dbPath = "jdbc:sqlite:";
    static {
        try {
            dbPath += new ClassPathResource("data.db").getFile().getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkCredentials(String username, String password) throws SQLException {
        Connection conn = DriverManager.getConnection(dbPath);

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * from login WHERE username = '" + username + "' AND password = '" + password + "'");

        if(rs.next()) {
            return true;
        }

        return false;
    }

    public static boolean checkFileOwned(String username, String filename) throws SQLException {
        Connection conn = DriverManager.getConnection(dbPath);

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * from file WHERE name = '" + filename + "' AND owner = '" + username + "'");

        if(rs.next()) {
            return true;
        }

        return false;
    }

    public static void addFile(String username, String filename) throws SQLException {
        Connection conn = DriverManager.getConnection(dbPath);

        Statement stmt = conn.createStatement();
        stmt.executeUpdate("INSERT INTO file (name, owner) VALUES ('" + filename + "', '" + username + "')");
    }
}
