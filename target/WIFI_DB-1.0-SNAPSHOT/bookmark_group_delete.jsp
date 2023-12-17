<%@ page import="com.example.wifi_db.DAO.BookMarkGroupDAO" %>
<%@ page import="com.example.wifi_db.DTO.BookMarkGroupDTO" %><%--
  Created by IntelliJ IDEA.
  User: rwg12
  Date: 2023-12-17
  Time: 오전 4:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>와이파이 정보 구하기</title>
    <style>
        #link-list {
            margin-bottom: 20px;
        }

        #table-list {
            font-family: Arial, Helvetica, sans-serif;
            border-collapse: collapse;
            width: 100%;
        }

        #table-list td, #table-list th {
            border: 1px solid #ddd;
            font-size: 14px;
            padding: 8px;
        }

        #table-list tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        #table-list th {
            padding-top: 12px;
            padding-bottom: 12px;
            background-color: #04AA6D;
            text-align: center;
            color: white;
            width: 20%;
        }
    </style>
</head>
<body>
<h1>즐겨찾기 그룹 관리</h1>

<div id = "link-list">
    <a href="index.jsp">홈</a>
    &#124;
    <a href="history.jsp">위치 히스토리 목록</a>
    &#124;
    <a href="loading_wifi.jsp">Open API 와이파이 정보 가져오기</a>
    &#124;
    <a href="bookmark.jsp">즐겨찾기 보기</a>
    &#124;
    <a href="bookmark_group.jsp">즐겨찾기 그룹 관리</a>
</div>
<%
    String id = request.getParameter("id");
    BookMarkGroupDAO bookMarkGroupDAO = new BookMarkGroupDAO();
    BookMarkGroupDTO bookMarkGroupDTO = bookMarkGroupDAO.select(Integer.parseInt(id));
%>

<form method="post" action="bookmark_group_delete_submit.jsp">
    <table id = "table-list">
        <tr>
            <th>북마크 이름</th>
            <td>
                <%= bookMarkGroupDTO.getName()%>
            </td>
        </tr>
        <tr>
            <th>순서</th>
            <td>
                <%= bookMarkGroupDTO.getSequence()%>
            </td>
        </tr>
        <tr>
            <td colspan="2" style="text-align: center">
                <a href="bookmark_group.jsp">돌아가기</a>
                &#124;
                <input type="submit" name="delete" value="삭제">
                <input type="hidden" name="id" value="<%= bookMarkGroupDTO.getId() %>">
            </td>
        </tr>
    </table>

</form>
</body>
</html>
