package com.example.wifi_db.DAO;

import com.example.wifi_db.DB_Info.DB;
import com.example.wifi_db.DB_Info.JDBCTemplate;
import com.example.wifi_db.DTO.HistoryDTO;

import java.net.URL;
import java.net.URLEncoder;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HistotyDAO extends JDBCTemplate {
    public int insert(HistoryDTO historyDTO){
        try{
            Class.forName(DB.CLASS);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        Connection connection = null;
        PreparedStatement ps = null;
        int affect = 0;

        try{

            connection = DriverManager.getConnection(DB.URL);
            String sql = "INSERT INTO History (LAT, LNT, SEARCH_DTTM) VALUES (?, ?, datetime('now', 'localtime'));";
            ps = connection.prepareStatement(sql);
            ps.setDouble(1, historyDTO.getLat());
            ps.setDouble(2, historyDTO.getLnt());

            affect = ps.executeUpdate();
            if(affect > 0){
                System.out.println("히스토리 데이터 삽입 성공");
            }else {
                System.out.println("히스토리 데이터 삽입 실패");
            }


        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close(ps);
            close(connection);
        }
        return affect;
    }
    public int delete(int id){
        try{
            Class.forName(DB.CLASS);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        Connection connection = null;
        PreparedStatement ps = null;
        int affect = 0;

        try {
            connection = DriverManager.getConnection(DB.URL);

            String sql = "DELETE FROM History WHERE id = ?;";

            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            affect = ps.executeUpdate();

            if(affect > 0){
                System.out.println("히스토리 데이터 삭제 성공");
            }else {
                System.out.println("히스토리 데이터 삭제 실패");
            }


        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close(ps);
            close(connection);
        }
        return affect;
    }


    public int count(){
        int cnt = 0;
        try{
            Class.forName(DB.CLASS);
        }catch (ClassNotFoundException e){
            throw new RuntimeException(e);
        }

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            connection = DriverManager.getConnection(DB.URL);
            String sql = "SELECT COUNT(*) FROM History;";
            ps = connection.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next()){
                cnt = rs.getInt(1);
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }finally {
            close(rs);
            close(ps);
            close(connection);
        }
        return cnt;

    }

    public List<HistoryDTO> selectList(){
        List<HistoryDTO> historyDTOList = new ArrayList<>();
        try{
            Class.forName(DB.CLASS);
        }catch (ClassNotFoundException e){
            throw new RuntimeException(e);
        }

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            connection = DriverManager.getConnection(DB.URL);
            String sql = "SELECT * FROM History ORDER BY ID DESC";

            ps = connection.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next()){
                int id = rs.getInt("id");
                double lat = rs.getDouble("LAT");
                double lnt = rs.getDouble("LNT");
                Date srcDttm = rs.getDate("SEARCH_DTTM");

                HistoryDTO historyDTO = new HistoryDTO();
                historyDTO.setId(id);
                historyDTO.setLat(lat);
                historyDTO.setLnt(lnt);
                historyDTO.setSrcDttm(srcDttm);

                historyDTOList.add(historyDTO);

            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close(rs);
            close(ps);
            close(connection);
        }

        return historyDTOList;
    }


}
