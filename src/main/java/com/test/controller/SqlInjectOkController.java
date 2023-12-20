package com.test.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.test.bean.Users;
import com.test.mapper.NewUserMapper;
import com.test.mapper.UserMapper;
import com.test.vo.SQLVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/sqlOk")
public class SqlInjectOkController {
    @Autowired
    DataSource dataSource;

    @Autowired
    UserMapper userMapper;

    @Autowired
    NewUserMapper newUserMapper;

    @GetMapping("")
    public String sqlIndex(Model model) {
        model.addAttribute("mechoy", "mechoy");
        System.out.println("被请求了");
        return "sqlInjectOk";
    }


    @ResponseBody
    @RequestMapping("/jdbcSql")
    public SQLVo jdbcSql(@RequestParam(name = "username", defaultValue = " ") String username) {
        System.out.println("1111");
        String sql = "SELECT email FROM users WHERE username = ?";
        ArrayList<String> emails = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String string = resultSet.getString(1);
                emails.add(string);
            }
            sql = preparedStatement.toString().substring(preparedStatement.toString().indexOf("SELECT"));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return new SQLVo(sql, emails.toString());
    }

    @PostMapping("/mybatisSql")
    @ResponseBody
    public SQLVo mybatisSql(@RequestParam(name = "username", defaultValue = "") String username) {
        List<Users> users = userMapper.selectUserOk(username);


        ArrayList<String> emails = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            emails.add(users.get(i).getEmail());
        }
        SQLVo sqlVo = new SQLVo();
        sqlVo.setSql("SELECT id,username,password,email FROM users WHERE username = '" + username.replace("'", "\\'") + "'");
        sqlVo.setEmail(emails.toString());
        return sqlVo;
    }

    @PostMapping("/mybatisPlusSql")
    @ResponseBody
    public SQLVo mybatisPlusSql(@RequestParam(name = "username", defaultValue = "") String username) {
        QueryWrapper<Users> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.apply("username={0}", username);
        List<Users> users = userMapper.selectList(userQueryWrapper);

        ArrayList<String> emails = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            emails.add(users.get(i).getEmail());
        }
        SQLVo sqlVo = new SQLVo();
        sqlVo.setSql("SELECT  id,username,password,email  FROM `users` WHERE (username='" + username.replace("'", "\\'") + "')");
        sqlVo.setEmail(emails.toString());

        return sqlVo;
    }

    @PostMapping("/tkMapperSql")
    @ResponseBody
    public SQLVo tkMapperSql(@RequestParam(name = "username", defaultValue = "") String username) {
        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andCondition("username=", username);

        ArrayList<String> emails = new ArrayList<>();
        List<Users> users = newUserMapper.selectByExample(example);
        for (int i = 0; i < users.size(); i++) {
            emails.add(users.get(i).getEmail());
        }
        SQLVo sqlVo = new SQLVo();
        sqlVo.setSql("SELECT  id,username,password,email  FROM `users`  WHERE ( ( username='" + username.replace("'", "\\'") + "' ) )");
        sqlVo.setEmail(emails.toString());
        return sqlVo;
    }
}