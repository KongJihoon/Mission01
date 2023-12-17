<%@ page import="com.example.wifi_db.Wifi_info" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>와이파이 정보 구하기</title>
</head>
<style>
    * {
        text-align: center;
    }
</style>
<body>
<%
    Wifi_info wifiInfo = new Wifi_info();
    wifiInfo.init();
    wifiInfo.saveBatch();
%>

<h1><%=wifiInfo.totalCnt()%>개의 WIFI 정보를 정상적으로 저장하였습니다.</h1>
<a href="index.jsp">홈으로 가기</a>
</body>
</html>
