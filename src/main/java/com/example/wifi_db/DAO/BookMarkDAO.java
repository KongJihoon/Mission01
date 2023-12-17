package com.example.wifi_db.DAO;

import com.example.wifi_db.DB_Info.DB;
import com.example.wifi_db.DB_Info.JDBCTemplate;
import com.example.wifi_db.DTO.BookMarkDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookMarkDAO extends JDBCTemplate {
    public int insert(BookMarkDTO bookMarkDTO){
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

            String sql = "INSERT OR REPLACE INTO BOOKMARK (GROUP_ID, MGR_NO, REGISTER_DTTM) VALUES (?, ?, datetime('now', 'localtime'));";

            ps = connection.prepareStatement(sql);
            ps.setInt(1, bookMarkDTO.getGroupId());
            ps.setString(2, bookMarkDTO.getMgrNo());

            affect = ps.executeUpdate();

            if(affect > 0){
                System.out.println("북마크 데이터 삽입 성공");
            }else {
                System.out.println("북마크 데이터 삽입 실패");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close(ps);
            close(connection);
        }

        return affect;
    }

    public BookMarkDTO select(int id){
        BookMarkDTO bookMarkDTO = new BookMarkDTO();

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

            String sql = "SELECT * FROM BOOKMARK WHERE ID = ?;";

            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);

            rs = ps.executeQuery();

            while (rs.next()){
                int id1 = rs.getInt("ID");
                int groupId = rs.getInt("GROUP_ID");
                String mgrNo = rs.getString("MGR_NO");
                Date regDttm = rs.getDate("REGISTER_DTTM");

                bookMarkDTO.setId(id1);
                bookMarkDTO.setGroupId(groupId);
                bookMarkDTO.setMgrNo(mgrNo);
                bookMarkDTO.setRegDttm(regDttm);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close(rs);
            close(ps);
            close(connection);
        }
        return bookMarkDTO;
    }
    public int delete(int id) {
        try {
            Class.forName(DB.CLASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection conn = null;
        PreparedStatement ps = null;
        int affect = 0;

        try {
            conn = DriverManager.getConnection(DB.URL);

            String sql = "DELETE FROM BOOKMARK WHERE ID = ?;";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            affect = ps.executeUpdate();
            if (affect > 0) {
                System.out.println("북마크 데이터 삭제 성공");
            } else {
                System.out.println("북마크 데이터 삭제 실패");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(ps);
            close(conn);
        }
        return affect;
    }
    public int count(){
        int cnt = 0;

        try{
            Class.forName(DB.CLASS);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = DriverManager.getConnection(DB.URL);

            String sql = "SELECT COUNT(*) FROM BOOKMARK;";

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
    public List<BookMarkDTO> selectList(){
        List<BookMarkDTO> bookMarkDTOList = new ArrayList<>();

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

            String sql = "SELECT BOOKMARK.* " +
                    "FROM BOOKMARK " +
                    "INNER JOIN BOOKMARK_GROUP " +
                    "ON BOOKMARK.GROUP_ID = BOOKMARK_GROUP.ID " +
                    "ORDER BY BOOKMARK_GROUP.SEQUENCE";
            ps = connection.prepareStatement(sql);

            rs = ps.executeQuery();
            while (rs.next()){
                int id = rs.getInt("ID");
                int groupId = rs.getInt("GROUP_ID");
                String mgrNo = rs.getString("MGR_NO");
                Date regDttm = rs.getDate("REGISTER_DTTM");

                BookMarkDTO bookMarkDTO = new BookMarkDTO();
                bookMarkDTO.setId(id);
                bookMarkDTO.setGroupId(groupId);
                bookMarkDTO.setMgrNo(mgrNo);
                bookMarkDTO.setRegDttm(regDttm);

                bookMarkDTOList.add(bookMarkDTO);

            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close(rs);
            close(ps);
            close(connection);
        }
        return bookMarkDTOList;
    }

}
