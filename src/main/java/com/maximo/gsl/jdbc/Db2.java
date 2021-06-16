package com.maximo.gsl.jdbc;

import java.sql.*;

/**
 * @author : guosl
 * @version : 1.0
 * @date : 2021/6/12 23:18
 * @description : com.maximo.gsl.jdbc
 */
public class Db2 {

    public Db2(){
    }

    private String ip = "";
    private String port = "";
    private String user = "";
    private String password = "";
    private Connection connection = null;

    public Db2(String ip, String port, String user, String password) {
        this.ip = ip;
        this.port = port;
        this.user = user;
        this.password = password;
    }

    public Connection getConnection() throws SQLException {
        String jdbcClassName="com.ibm.db2.jcc.DB2Driver";
        String url=String.format("jdbc:db2://%s:%s/maxdb", ip, port);
        //Load class into memory
        try {
            Class.forName(jdbcClassName);
            //Establish connection
            connection = DriverManager.getConnection(url, user, password);
            connection.setAutoCommit(false);
            System.out.println(connection!=null ? "Connected successfully." : "Connected fail.");
        } catch (ClassNotFoundException e) {
            System.out.println("db2 class not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("sql connection is error.");
            throw new SQLException("数据库连接失败\n".concat(e.getMessage()));
        }

        return connection;
    }

    public void closeRsPs(ResultSet rs, PreparedStatement ps) throws SQLException {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            throw new SQLException("关闭事务出错\n".concat(e.getMessage()));
        }
    }
}
