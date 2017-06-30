package com.neusoft.hp.mybatis.plugin.xmlgenerate.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCUtil {

    static Properties              pros  = new Properties();   // 可以帮助读取和处理资源文件中的信息

    static ThreadLocal<Connection> conns = new ThreadLocal<>();

    // static { // 加载JDBCUtil类的时候调用。注意这里的话，只会被加载一次。
    // pros = new Properties();
    // try {
    // pros.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("./config/myDB.properties"));
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // }

    public static void setProperties(Properties properties) {
        pros.put("jdbc.driver_class", properties.get("driverClass"));
        pros.put("jdbc.url", properties.get("connectionURL"));
        pros.put("jdbc.username", properties.get("userId"));
        pros.put("jdbc.password", properties.get("password"));
    }

    public static Connection getOracleConn() {
        if (null != conns.get()) {
            return conns.get();
        }
        try {
            Class.forName(pros.getProperty("jdbc.driver_class"));
            Connection conn = DriverManager.getConnection(pros.getProperty("jdbc.url"),
                                                          pros.getProperty("jdbc.username"),
                                                          pros.getProperty("jdbc.password"));
            conns.set(conn);
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void close(ResultSet rs, Statement ps, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(Statement ps, Connection conn) {
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
