package com.test.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/sql")
public class SqlInjectController {
    @PostMapping("/jdbcSql")
    public void jdbcSql(@RequestBody String json){

    }
}
