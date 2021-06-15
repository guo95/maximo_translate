package com.maximo.gsl.jdbc;

import java.sql.*;

/**
 * @author : guosl
 * @version : 1.0
 * @date : 2021/6/12 23:18
 * @description : com.maximo.gsl.jdbc
 */
public class Db2 {

    /*public static void main(String[] args) {
        String jdbcClassName="com.ibm.db2.jcc.DB2Driver";
        String url="jdbc:db2://m.shuto.cn:45000/maxdb";
        String user="maximo";
        String password="Guosl@shuto.cn";

        Connection connection = null;
        try {
            //Load class into memory
            Class.forName(jdbcClassName);
            //Establish connection
            connection = DriverManager.getConnection(url, user, password);
            connection.setAutoCommit(false);

            String sql = "";


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally{
            if(connection!=null){
                System.out.println("Connected successfully.");
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }*/

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

    public Connection getConnection() {
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
            e.printStackTrace();
        }

        return connection;
    }

    public void closeAll(ResultSet rs, PreparedStatement ps, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.rollback();
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
