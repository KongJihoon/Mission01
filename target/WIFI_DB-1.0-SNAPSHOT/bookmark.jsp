
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.example.wifi_db.DAO.BookMarkDAO" %>
<%@ page import="com.example.wifi_db.DTO.BookMarkDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.wifi_db.DAO.BookMarkGroupDAO" %>
<%@ page import="com.example.wifi_db.DTO.BookMarkGroupDTO" %>
<%@ page import="com.example.wifi_db.DAO.WifiDAO" %>
<%@ page import="com.example.wifi_db.DTO.WifiDTO" %>

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
            text-align: center;
            font-size: 14px;
            padding: 8px;
        }

        #table-list tr:nth-child(odd) {
            background-color: #f2f2f2;
        }

        #table-list tr:hover {
            background-color: #ddd;
        }

        #table-list th {
            padding-top: 12px;
            padding-bottom: 12px;
            background-color: #04AA6D;
            color: white;
        }
    </style>
</head>
<body>
<h1>즐겨찾기 보기</h1>

<div id="link-list">
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

<table id="table-list">
    <thead>
    <tr>
        <th>ID</th>
        <th>북마크 이름</th>
        <th>와이파이명</th>
        <th>등록일자</th>
        <th>비고</th>
    </tr>
    </thead>
    <tbody>
    <%
        BookMarkDAO bookmarkDao = new BookMarkDAO();
        if (bookmarkDao.count() == 0) {
    %>
    <tr>
        <td colspan="5">
            데이터가 존재하지 않습니다.
        </td>
    </tr>
    <%
    } else {
        List<BookMarkDTO> bookmarkDtoList = bookmarkDao.selectList();
        for (BookMarkDTO item : bookmarkDtoList) {
            BookMarkGroupDAO bookmarkGroupDao = new BookMarkGroupDAO();
            BookMarkGroupDTO bookmarkGroupDto = bookmarkGroupDao.select(item.getGroupId());

            WifiDAO wifiDao = new WifiDAO();
            WifiDTO wifiDto = wifiDao.select(item.getMgrNo());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String regDate = sdf.format(item.getRegDttm());
    %>
    <tr>
        <td>
            <%= item.getId() %>
        </td>
        <td>
            <%= bookmarkGroupDto.getName() %>
        </td>
        <td>
            <a href="detail.jsp?mgrNo=<%= wifiDto.getMgrNo() %>">
                <%= wifiDto.getMainNm() %>
            </a>
        </td>
        <td>
            <%= regDate %>
        </td>
        <td>
            <a href="bookmark_delete.jsp?id=<%= item.getId() %>">
                삭제
            </a>
        </td>
    </tr>
    <%
            }
        }
    %>
    </tbody>
</table>
</body>
</html>