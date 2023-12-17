package com.example.wifi_db.DB_Info;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCTemplate {
    public void close(ResultSet rs){
        try {
            if(rs != null && !rs.isClosed()){
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close(PreparedStatement ps){
        try {
            if(ps != null && !ps.isClosed()){
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void close(Connection connection){
        try {
            if(connection != null && !connection.isClosed()){
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
