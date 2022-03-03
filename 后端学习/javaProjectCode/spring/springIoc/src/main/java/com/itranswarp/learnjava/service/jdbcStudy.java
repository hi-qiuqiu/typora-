package com.itranswarp.learnjava.service;

import java.sql.*;

public class jdbcStudy {

    public static String getName(){
        String JDBC_URL = "jdbc:mysql://localhost:3306/jdbcstudy?useSSL=false&characterEncoding=utf8";
        String JDBC_USER = "root";
        String JDBC_PASSWORD = "root";
        String name = "";
        // 获取连接:
        Connection conn = null;
        {
            try {
                conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        {
            try (Statement stmt = conn.createStatement()) {
                try (ResultSet rs = stmt.executeQuery("SELECT id, score, name, class FROM study WHERE id=1")) {
                    while (rs.next()) {
                        long id = rs.getLong(1); // 注意：索引从1开始
                        long score = rs.getLong(2);
                        name = rs.getString(3);
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return name;
    }


    // TODO: 访问数据库...
    // 关闭连接:
//    conn.close();
}
