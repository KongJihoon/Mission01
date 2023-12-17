package com.example.wifi_db.DAO;

import com.example.wifi_db.DB_Info.DB;
import com.example.wifi_db.DB_Info.JDBCTemplate;
import com.example.wifi_db.DTO.WifiDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WifiDAO extends JDBCTemplate {
    public int insert(WifiDTO wifiDTO){
        try {
            Class.forName(DB.CLASS);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        Connection connection = null;
        PreparedStatement ps = null;
        int affect = 0;

        try{
            connection = DriverManager.getConnection(DB.URL);

            String sql = "INSERT into Public_Wifi values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            ps = connection.prepareStatement(sql);
            ps.setString(1, wifiDTO.getMgrNo());
            ps.setString(2, wifiDTO.getWrdofc());
            ps.setString(3, wifiDTO.getMainNm());
            ps.setString(4, wifiDTO.getAdres1());
            ps.setString(5, wifiDTO.getAdres2());
            ps.setString(6, wifiDTO.getInstl_Floor());
            ps.setString(7, wifiDTO.getInstl_Ty());
            ps.setString(8, wifiDTO.getInstl_Mby());
            ps.setString(9, wifiDTO.getSvc_Se());
            ps.setString(10, wifiDTO.getCmcWr());
            ps.setInt(11, wifiDTO.getCnstc_Year());
            ps.setString(12, wifiDTO.getInout_Door());
            ps.setString(13, wifiDTO.getReMars3());
            ps.setDouble(14, wifiDTO.getLat());
            ps.setDouble(15, wifiDTO.getLnt());
            ps.setString(16, wifiDTO.getWork_Dttm());

            affect = ps.executeUpdate();
            if(affect > 0){
                System.out.println("와이파이 데이터 삽입 성공");
            }else {
                System.out.println("와이파이 데이터 삽입 실패");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close(ps);
            close(connection);
        }
        return affect;
    }
    public WifiDTO select(String mgrNo){
        WifiDTO wifiDTO = new WifiDTO();

        try{
            Class.forName(DB.CLASS);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            connection = DriverManager.getConnection(DB.URL);

            String sql = "SELECT * FROM Public_Wifi WHERE X_SWIFI_MGR_NO = ?;";

            ps = connection.prepareStatement(sql);
            ps.setString(1,mgrNo);

            rs = ps.executeQuery();

            while (rs.next()){
                String mgrNo2 = rs.getString("X_SWIFI_MGR_NO");
                String wrdofc = rs.getString("X_SWIFI_WRDOFC");
                String mainNm = rs.getString("X_SWIFI_MAIN_NM");
                String adres1 = rs.getString("X_SWIFI_ADRES1");
                String adres2 = rs.getString("X_SWIFI_ADRES2");
                String instlFloor = rs.getString("X_SWIFI_INSTL_FLOOR");
                String instlTy = rs.getString("X_SWIFI_INSTL_TY");
                String instlMby = rs.getString("X_SWIFI_INSTL_MBY");
                String svcSe = rs.getString("X_SWIFI_SVC_SE");
                String cmcwr = rs.getString("X_SWIFI_CMCWR");
                int cnstcYear = rs.getInt("X_SWIFI_CNSTC_YEAR");
                String inoutDoor = rs.getString("X_SWIFI_INOUT_DOOR");
                String remars3 = rs.getString("X_SWIFI_REMARS3");
                double lat = rs.getDouble("LAT");
                double lnt = rs.getDouble("LNT");
                String workDttm = rs.getString("WORK_DTTM");

                wifiDTO.setMgrNo(mgrNo2);
                wifiDTO.setWrdofc(wrdofc);
                wifiDTO.setMainNm(mainNm);
                wifiDTO.setAdres1(adres1);
                wifiDTO.setAdres2(adres2);
                wifiDTO.setInstl_Floor(instlFloor);
                wifiDTO.setInstl_Ty(instlTy);
                wifiDTO.setInstl_Mby(instlMby);
                wifiDTO.setSvc_Se(svcSe);
                wifiDTO.setCmcWr(cmcwr);
                wifiDTO.setCnstc_Year(cnstcYear); ;
                wifiDTO.setInout_Door(inoutDoor);
                wifiDTO.setReMars3(remars3);
                wifiDTO.setLat(lat);
                wifiDTO.setLnt(lnt);
                wifiDTO.setWork_Dttm(workDttm);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close(rs);
            close(ps);
            close(connection);
        }
        return wifiDTO;
    }

    public void deleteAll(){
        try {
            Class.forName(DB.CLASS);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        Connection connection = null;
        PreparedStatement ps = null;

        try{
            connection = DriverManager.getConnection(DB.URL);
            String sql = "DELETE FROM Public_Wifi";
            ps = connection.prepareStatement(sql);

            int affect = ps.executeUpdate();
            if(affect > 0){
                System.out.println("모든 와이파이 데이터 제거 성공");
            }else {
                System.out.println("모든 와이파이 데이터 제거 실패");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close(ps);
            close(connection);
        }


    }
    public List<WifiDTO> selectList(Double lat, Double lnt){
        List<WifiDTO> wifiDTOList = new ArrayList<>();
        try{
            Class.forName(DB.CLASS);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            connection = DriverManager.getConnection(DB.URL);
            String sql =  "SELECT *, " +
                    "round(6371*acos(cos(radians(?))*cos(radians(LAT))*cos(radians(LNT)" +
                    "-radians(?))+sin(radians(?))*sin(radians(LAT))), 4) " +
                    "AS DISTANCE " +
                    "FROM Public_Wifi " +
                    "ORDER BY DISTANCE " +
                    "LIMIT 20;";
            ps = connection.prepareStatement(sql);
            ps.setDouble(1,lat);
            ps.setDouble(2,lnt);
            ps.setDouble(3,lat);

            rs = ps.executeQuery();

            while (rs.next()){
                double distance = rs.getDouble("DISTANCE");
                String mgrNo = rs.getString("X_SWIFI_MGR_NO");
                String wrodofc = rs.getString("X_SWIFI_WRDOFC");
                String mainNm = rs.getString("X_SWIFI_MAIN_NM");
                String adres1 = rs.getString("X_SWIFI_ADRES1");
                String adres2 = rs.getString("X_SWIFI_ADRES2");
                String instl_floor = rs.getString("X_SWIFI_INSTL_FLOOR");
                String instl_ty = rs.getString("X_SWIFI_INSTL_TY");
                String instl_mby = rs.getString("X_SWIFI_INSTL_MBY");
                String svcSe = rs.getString("X_SWIFI_SVC_SE");
                String cmcwr = rs.getString("X_SWIFI_CMCWR");
                int cnstc_year = rs.getInt("X_SWIFI_CNSTC_YEAR");
                String inoutDoor = rs.getString("X_SWIFI_INOUT_DOOR");
                String remars3 = rs.getString("X_SWIFI_REMARS3");
                double lat1 = rs.getDouble("LAT");
                double lnt1 = rs.getDouble("LNT");
                String workDttm = rs.getString("WORK_DTTM");

                WifiDTO wifiDTO = new WifiDTO();
                wifiDTO.setDistance(distance);
                wifiDTO.setMgrNo(mgrNo);
                wifiDTO.setWrdofc(wrodofc);
                wifiDTO.setMainNm(mainNm);
                wifiDTO.setAdres1(adres1);
                wifiDTO.setAdres2(adres2);
                wifiDTO.setInstl_Floor(instl_floor);
                wifiDTO.setInstl_Ty(instl_ty);
                wifiDTO.setInstl_Mby(instl_mby);
                wifiDTO.setSvc_Se(svcSe);
                wifiDTO.setCmcWr(cmcwr);
                wifiDTO.setCnstc_Year(cnstc_year); ;
                wifiDTO.setInout_Door(inoutDoor);
                wifiDTO.setReMars3(remars3);
                wifiDTO.setLat(lat);
                wifiDTO.setLnt(lnt);
                wifiDTO.setWork_Dttm(workDttm);

                wifiDTOList.add(wifiDTO);

            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close(rs);
            close(ps);
            close(connection);
        }

        return wifiDTOList;

    }


}
