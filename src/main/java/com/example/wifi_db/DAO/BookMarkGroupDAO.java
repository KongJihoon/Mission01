package com.example.wifi_db.DAO;

import com.example.wifi_db.DB_Info.DB;
import com.example.wifi_db.DB_Info.JDBCTemplate;
import com.example.wifi_db.DTO.BookMarkGroupDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookMarkGroupDAO extends JDBCTemplate {
    public int insert(BookMarkGroupDTO bookMarkGroupDTO){
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

            String sql = "INSERT INTO BOOKMARK_GROUP(NAME,SEQUENCE,REGISTER_DTTM) VALUES (?,?,datetime('now','localtime'));";

            ps = connection.prepareStatement(sql);

            ps.setString(1, bookMarkGroupDTO.getName());
            ps.setInt(2, bookMarkGroupDTO.getSequence());
            affect = ps.executeUpdate();
            if(affect > 0){
                System.out.println("북마크 그룹 데이터 삽입 성공");
            }else {
                System.out.println("북마크 그룹 데이터 삽입 실패");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close(ps);
            close(connection);
        }

        return affect;
    }
    public BookMarkGroupDTO select(int id){
        BookMarkGroupDTO bookMarkGroupDTO = new BookMarkGroupDTO();

        try {
            Class.forName(DB.CLASS);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = DriverManager.getConnection(DB.URL);

            String sql = "SELECT * FROM BOOKMARK_GROUP WHERE ID = ?;";

            ps = connection.prepareStatement(sql);
            ps.setInt(1,id);

            rs = ps.executeQuery();
            while (rs.next()){
                int id1 = rs.getInt("ID");
                String name = rs.getString("NAME");
                int sequence = rs.getInt("SEQUENCE");
                Date regDttm = rs.getDate("REGISTER_DTTM");
                Date uptDttm = rs.getDate("UPDATE_DTTM");

                bookMarkGroupDTO.setId(id1);
                bookMarkGroupDTO.setName(name);
                bookMarkGroupDTO.setSequence(sequence);
                bookMarkGroupDTO.setRegDttm(regDttm);
                bookMarkGroupDTO.setUptDttm(uptDttm);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close(rs);
            close(ps);
            close(connection);
        }
        return bookMarkGroupDTO;
    }
    public int delete(int id){
        try {
            Class.forName(DB.CLASS);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        Connection connection = null;
        PreparedStatement ps = null;
        int affect = 0;

        try {
            connection = DriverManager.getConnection(DB.URL);

            ps = connection.prepareStatement("PRAGMA foreign_keys = ON;");
            ps.executeUpdate();

            String sql = "DELETE FROM BOOKMARK_GROUP WHERE ID = ?;";

            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);

            affect = ps.executeUpdate();

            if(affect > 0){
                System.out.println("북마크 그룹 데이터 제거 성공");
            }else {
                System.out.println("북마크 그룹 데이터 제거 실패");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close(ps);
            close(connection);
        }
        return affect;
    }

    public int update(BookMarkGroupDTO bookMarkGroupDTO){
        try {
            Class.forName(DB.CLASS);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        Connection connection = null;
        PreparedStatement ps = null;
        int affect = 0;

        try {
            connection = DriverManager.getConnection(DB.URL);

            String sql = "UPDATE BOOKMARK_GROUP SET NAME = ?, SEQUENCE = ?, UPDATE_DTTM = datetime('now', 'localtime') WHERE ID = ?;";

            ps = connection.prepareStatement(sql);
            ps.setString(1, bookMarkGroupDTO.getName());
            ps.setInt(2, bookMarkGroupDTO.getSequence());
            ps.setInt(3, bookMarkGroupDTO.getId());

            affect = ps.executeUpdate();

            if(affect > 0){
                System.out.println("북마크 그룹 데이터 업데이트 성공");
            }else {
                System.out.println("북마크 그룹 데이터 업데이트 실패");
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

        try {
            Class.forName(DB.CLASS);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = DriverManager.getConnection(DB.URL);

            String sql = "SELECT COUNT(*) FROM BOOKMARK_GROUP;";

            ps = connection.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next()){
                cnt = rs.getInt(1);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close(rs);
            close(ps);
            close(connection);
        }
        return cnt;
    }

    public List<BookMarkGroupDTO> selectList(){
        List<BookMarkGroupDTO> bookMarkGroupDTOList = new ArrayList<>();

        try {
            Class.forName(DB.CLASS);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = DriverManager.getConnection(DB.URL);

            String sql = "SELECT * FROM BOOKMARK_GROUP ORDER BY ID";

            ps = connection.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next()){
                int id = rs.getInt("ID");
                String name = rs.getString("NAME");
                int sequence = rs.getInt("SEQUENCE");
                Date regDttm = rs.getDate("REGISTER_DTTM");
                Date uptDttm = rs.getDate("UPDATE_DTTM");

                BookMarkGroupDTO bookMarkGroupDTO = new BookMarkGroupDTO();
                bookMarkGroupDTO.setId(id);
                bookMarkGroupDTO.setName(name);
                bookMarkGroupDTO.setSequence(sequence);
                bookMarkGroupDTO.setRegDttm(regDttm);
                bookMarkGroupDTO.setUptDttm(uptDttm);

                bookMarkGroupDTOList.add(bookMarkGroupDTO);

            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close(rs);
            close(ps);
            close(connection);
        }
        return bookMarkGroupDTOList;

    }

}
