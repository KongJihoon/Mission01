package com.example.wifi_db;

import com.example.wifi_db.DAO.WifiDAO;
import com.example.wifi_db.DB_Info.DB;
import com.example.wifi_db.DTO.WifiDTO;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Wifi_info {
    private static final String Url = "http://openapi.seoul.go.kr:8088";
    private static final String Key = "6e6d796c7672776737347a776a5a51";
    private static final String Data_Type = "json";
    private static final String name = "TbPublicWifiInfo";

    // 서울 공공 와이파이 총 개수
    public int totalCnt() throws IOException{
        String urlBuilder = Url + "/" +
                URLEncoder.encode(Key, "UTF-8") + "/" +
                URLEncoder.encode(Data_Type,"UTF-8") + "/" +
                URLEncoder.encode(name,"UTF-8") + "/1/1";

        URL url = new URL(urlBuilder);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-type", "application/json");

        BufferedReader rd;
        if(connection.getResponseCode() >= 200 && connection.getResponseCode() <=300){
            rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        }else {
            rd = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
        }

        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = rd.readLine()) != null){
            sb.append(line);
        }
        rd.close();
        connection.disconnect();

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(sb.toString(), JsonObject.class);
        int Cnt = jsonObject.getAsJsonObject(name).get("list_total_count").getAsInt();
        return Cnt;
    }

    // 서울 공공 와이파이 저장
    public void save() throws IOException{
        int data = 1;
        for (int i = 0; i < totalCnt() / 1000 + 1; i++) {
            String urlBuilder = Url + "/" +
                    URLEncoder.encode(Key, "UTF-8") + "/" +
                    URLEncoder.encode(Data_Type,"UTF-8") + "/" +
                    URLEncoder.encode(name,"UTF-8") + "/" +
                    URLEncoder.encode(String.valueOf(data), "UTF-8") + "/" +
                    URLEncoder.encode(String.valueOf(data + 999), "UTF-8");

            URL url = new URL(urlBuilder);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-type","application/json");

            BufferedReader rd;
            if(connection.getResponseCode() >= 200 && connection.getResponseCode() <=300){
                rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            }else {
                rd = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }

            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = rd.readLine()) != null){
                sb.append(line);
            }
            rd.close();
            connection.disconnect();
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(sb.toString(), JsonObject.class);
            JsonArray rowArray = jsonObject.getAsJsonObject(name).getAsJsonArray("row");

            for (int j = 0; j < rowArray.size(); j++) {
                JsonObject rowObject = rowArray.get(j).getAsJsonObject();

                WifiDTO wifiDTO = new WifiDTO();
                wifiDTO.setMgrNo(rowObject.get("X_SWIFI_MGR_NO").getAsString());
                wifiDTO.setWrdofc(rowObject.get("X_SWIFI_WRDOFC").getAsString());
                wifiDTO.setMainNm(rowObject.get("X_SWIFI_MAIN_NM").getAsString());
                wifiDTO.setAdres1(rowObject.get("X_SWIFI_ADRES1").getAsString());
                wifiDTO.setAdres2(rowObject.get("X_SWIFI_ADRES2").getAsString());
                wifiDTO.setInstl_Floor(rowObject.get("X_SWIFI_INSTL_FLOOR").getAsString());
                wifiDTO.setInstl_Ty(rowObject.get("X_SWIFI_INSTL_TY").getAsString());
                wifiDTO.setInstl_Mby(rowObject.get("X_SWIFI_INSTL_MBY").getAsString());
                wifiDTO.setSvc_Se(rowObject.get("X_SWIFI_SVC_SE").getAsString());
                wifiDTO.setCmcWr(rowObject.get("X_SWIFI_CMCWR").getAsString());
                wifiDTO.setCnstc_Year(rowObject.get("X_SWIFI_CNSTC_YEAR").getAsInt());
                wifiDTO.setInout_Door(rowObject.get("X_SWIFI_INOUT_DOOR").getAsString());
                wifiDTO.setReMars3(rowObject.get("X_SWIFI_REMARS3").getAsString());
                wifiDTO.setLat(rowObject.get("LAT").getAsDouble());
                wifiDTO.setLnt(rowObject.get("LNT").getAsDouble());
                wifiDTO.setWork_Dttm(rowObject.get("WORK_DTTM").getAsString());

                WifiDAO wifiDAO = new WifiDAO();
                wifiDAO.insert(wifiDTO);
            }
            data += 1000;
        }

    }

    public void saveBatch() throws IOException {
        try {
            Class.forName(DB.CLASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = DriverManager.getConnection(DB.URL);
            connection.setAutoCommit(false);

            String sql = "INSERT INTO Public_Wifi VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            ps = connection.prepareStatement(sql);

            int data = 1;
            for (int i = 0; i < totalCnt() / 1000 + 1; i++) {
                String urlBuilder = Url + "/" +
                        URLEncoder.encode(Key, "UTF-8") + "/" +
                        URLEncoder.encode(Data_Type, "UTF-8") + "/" +
                        URLEncoder.encode(name, "UTF-8") + "/" +
                        URLEncoder.encode(String.valueOf(data), "UTF-8") + "/" +
                        URLEncoder.encode(String.valueOf(data + 999), "UTF-8");

                URL url = new URL(urlBuilder);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-type", "application/json");

                BufferedReader rd;
                if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                    rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                } else {
                    rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                }

                String line;
                StringBuilder sb = new StringBuilder();
                while ((line = rd.readLine()) != null) {
                    sb.append(line);
                }
                rd.close();
                conn.disconnect();

                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(sb.toString(), JsonObject.class);
                JsonArray rowArray = jsonObject.getAsJsonObject(name).getAsJsonArray("row");

                for (int j = 0; j < rowArray.size(); j++) {
                    JsonObject rowObject = rowArray.get(j).getAsJsonObject();


                    ps.setString(1, rowObject.get("X_SWIFI_MGR_NO").getAsString());
                    ps.setString(2, rowObject.get("X_SWIFI_WRDOFC").getAsString());
                    ps.setString(3, rowObject.get("X_SWIFI_MAIN_NM").getAsString());
                    ps.setString(4, rowObject.get("X_SWIFI_ADRES1").getAsString());
                    ps.setString(5, rowObject.get("X_SWIFI_ADRES2").getAsString());
                    ps.setString(6, rowObject.get("X_SWIFI_INSTL_FLOOR").getAsString());
                    ps.setString(7, rowObject.get("X_SWIFI_INSTL_TY").getAsString());
                    ps.setString(8, rowObject.get("X_SWIFI_INSTL_MBY").getAsString());
                    ps.setString(9, rowObject.get("X_SWIFI_SVC_SE").getAsString());
                    ps.setString(10, rowObject.get("X_SWIFI_CMCWR").getAsString());
                    ps.setInt(11, rowObject.get("X_SWIFI_CNSTC_YEAR").getAsInt());
                    ps.setString(12, rowObject.get("X_SWIFI_INOUT_DOOR").getAsString());
                    ps.setString(13, rowObject.get("X_SWIFI_REMARS3").getAsString());
                    ps.setDouble(14, rowObject.get("LAT").getAsDouble());
                    ps.setDouble(15, rowObject.get("LNT").getAsDouble());
                    ps.setString(16, rowObject.get("WORK_DTTM").getAsString());
                    ps.addBatch();
                }
                ps.executeBatch();
                ps.clearBatch();
                connection.commit();

                data += 1000;
            }

        }catch (SQLException e){
            e.printStackTrace();

            try {
                connection.rollback();
            }catch (SQLException ex){
                ex.printStackTrace();
            }
        }finally {
            try {
                if(ps != null && !ps.isClosed()){
                    ps.close();
                }
                if(connection != null && !connection.isClosed()){
                    connection.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void init(){
        WifiDAO wifiDAO = new WifiDAO();
        wifiDAO.deleteAll();
    }

}
