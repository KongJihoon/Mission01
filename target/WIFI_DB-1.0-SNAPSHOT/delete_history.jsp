<%@ page import="com.example.wifi_db.DAO.HistotyDAO" %><%--
  Created by IntelliJ IDEA.
  User: rwg12
  Date: 2023-12-12
  Time: 오전 9:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>와이파이 정보 구하기</title>
</head>
<body>
    <%
        String id = request.getParameter("id");

        HistotyDAO histotyDAO = new HistotyDAO();
        int affect = histotyDAO.delete(Integer.valueOf(id));

    %>
    <script>
        <%
            String text = affect > 0 ? "위치 히스토리 데이터를 삭제하였습니다." : "위치 히스토리 데이터를 삭제하지 못했습니다.";
        %>
        alert("<%= text %>");
        location.href = "history.jsp";
    </script>
</body>
</html>
