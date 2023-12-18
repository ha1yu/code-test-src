package com.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

@Controller
@RequestMapping("/sql")
public class SqlInjectController {
    @Autowired
    DataSource dataSource;

    @ResponseBody
    @RequestMapping("/jdbcSql")
    public String jdbcSql(@RequestParam(name = "username", defaultValue = " ") String username) {
        // Payload: 23'union select password from users where '1'='1
        String sql = "SELECT email FROM users WHERE username = '" + username + "'";
        ArrayList<String> emails = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                String string = resultSet.getString(1);
                emails.add(string);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }finally {
            if(resultSet != null){
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null){
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return emails.toString();
    }

    @PostMapping("/mybatisSql")
    @ResponseBody
    public String mybatisSql(@RequestParam(name = "username",defaultValue = "")String username){

        return "111";
    }

    @PostMapping("/mybatisPlusSql")
    @ResponseBody
    public String mybatisPlusSql(@RequestParam(name = "username",defaultValue = "")String username){

        return "111";
    }

    @PostMapping("/tkMapperSql")
    @ResponseBody
    public String tkMapperSql(@RequestParam(name = "username",defaultValue = "")String username){

        return "111";
    }
}