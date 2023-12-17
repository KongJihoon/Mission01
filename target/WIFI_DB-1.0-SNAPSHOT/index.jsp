<%@ page import="java.util.List" %>
<%@ page import="com.example.wifi_db.DTO.HistoryDTO" %>
<%@ page import="com.example.wifi_db.DAO.HistotyDAO" %>
<%@ page import="com.example.wifi_db.DAO.WifiDAO" %>
<%@ page import="com.example.wifi_db.DTO.WifiDTO" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%
    request.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>와이파이 정보 구하기</title>
  <style>
      #link-list {
        margin-bottom: 20px;
      }

      #form-list {
        margin-bottom: 20px;
      }

      #table-list {
          font-family: Arial, Helvetico, sans-serif;
      }

      #table-list td, #table-list th {
          border: 1px solid #ddd;
          text-align: center;
          font-size: 14px;
          padding: 8px;
      }

      #table-list tr:nth-child(odd){
          background-color: #f2f2f2;
      }

      #table-list tr:hover {
          background-color: #ddd;
      }

      #table-list th{
          padding-top: 12px;
          padding-bottom: 12px;
          background-color: #04AA6D;
          color: white;
      }
  </style>
</head>
<body>
<h1>와이파이 정보 구하기</h1>
<div id = "link-list">
    <a href="index.jsp">홈</a>
    &#124;
    <a href="history.jsp">위치 히스토리 목록</a>
    &#124;
    <a href="loading_wifi.jsp">Open API 와이파이 정보 가져오기</a>
    &#124;
    <a href="bookmark.jsp">즐겨 찾기 보기</a>
    &#124;
    <a href="bookmark_group.jsp">즐겨 찾기 그룹 관리</a>
</div>

<form method="get" action="index.jsp" id="form-list">
    <label>
        LAT: <input type = "text" id = "lat" name = "lat" value = "0.0">
    </label>
    <label>
        LNT: <input type = "text" id = "lnt" name = "lnt" value = "0.0">
    </label>
    <input type = "button" value = "내 위치 가져오기" onclick = "getLocation();">
    <input type = "submit" value = "근처 WIFI 정보 보기">

</form>

<table id = "table-list">

    <thead>
    <tr>
        <th>거리(Km)</th>
        <th>관리번호</th>
        <th>자치구</th>
        <th>와이파이명</th>
        <th>도로명주소</th>
        <th>상세주소</th>
        <th>설치위치(층)</th>
        <th>설치유형</th>
        <th>설치기관</th>
        <th>서비스구분</th>
        <th>망종류</th>
        <th>설치년도</th>
        <th>실내외구분</th>
        <th>WIFI접속환경</th>
        <th>X좌표</th>
        <th>Y좌표</th>
        <th>작업일자</th>
    </tr>
    </thead>
    <tbody>
    <%
        String lat = request.getParameter("lat");
        String lnt = request.getParameter("lnt");

        double latValue = 0.0;
        double lntValue = 0.0;

        if(lat != null && !lat.isEmpty()){
            try{
                latValue = Double.parseDouble(lat);
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
        }

        if(lnt != null && !lnt.isEmpty()){
            try{
                lntValue = Double.parseDouble(lnt);
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
        }

        if(latValue == 0.0 && lntValue == 0.0){


    %>
        <tr>
            <td colspan="17">위치 정보를 입력한 후에 조회해 주세요.</td>
        </tr>
    <%
        } else {
            HistoryDTO historyDTO = new HistoryDTO();
            historyDTO.setLat(latValue);
            historyDTO.setLnt(lntValue);

            HistotyDAO histotyDAO = new HistotyDAO();
            histotyDAO.insert(historyDTO);

            WifiDAO wifiDAO = new WifiDAO();
            List<WifiDTO> wifiDaoList = wifiDAO.selectList(lntValue, latValue);

            for (WifiDTO item : wifiDaoList){
    %>
    <tr>
       <td>
           <%= item.getDistance()%>
       </td>
        <td>
            <%= item.getMgrNo()%>
        </td>
        <td>
            <%= item.getWrdofc()%>
        </td>
        <td>
            <a href="detail.jsp?mgrNo=<%= item.getMgrNo() %>&distance=<%= item.getDistance() %>">
                <%= item.getMainNm()%>
            </a>
        </td>
        <td>
            <%= item.getAdres1()%>
        </td>
        <td>
            <%= item.getAdres2()%>
        </td>
        <td>
            <%= item.getInstl_Floor()%>
        </td>
        <td>
            <%= item.getInstl_Ty()%>
        </td>
        <td>
            <%= item.getInstl_Mby()%>
        </td>
        <td>
            <%= item.getSvc_Se()%>
        </td>
        <td>
            <%= item.getCmcWr()%>
        </td>
        <td>
            <%= item.getCnstc_Year()%>
        </td>
        <td>
            <%= item.getInout_Door()%>
        </td>
        <td>
            <%= item.getReMars3()%>
        </td>
        <td>
            <%= item.getLnt()%>
        </td>
        <td>
            <%= item.getLat()%>
        </td>
        <td>
            <%= item.getWork_Dttm()%>
        </td>
    </tr>
    <%

            }
        }
    %>

    </tbody>
</table>

<script>
    const params = new URLSearchParams(window.location.search)
    const lnt = params.get("lnt")
    const lat = params.get("lat")
    if(lnt){
        const lntElem = document.getElementById("lnt");
        lntElem.setAttribute("value", lnt)
    }

    if(lat){
        const latElem = document.getElementById("lat");
        lntElem.setAttribute("value", lat)
    }

    function getLocation(){
        if(navigator.geolocation){
            navigator.geolocation.getCurrentPosition(showPosition);

        }else {
            alert("이 브라우저는 위치 정보를 지원하지 않습니다.")
        }
    }
    function showPosition(position){
        const lat = position.coords.latitude;
        const lnt = position.coords.longitude;

        document.getElementById("lat").value = lat;
        document.getElementById("lnt").value = lnt;
    }

</script>


</body>
</html>