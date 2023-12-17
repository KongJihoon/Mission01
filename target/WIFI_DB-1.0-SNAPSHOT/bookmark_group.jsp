<%@ page import="com.example.wifi_db.DAO.BookMarkGroupDAO" %>
<%@ page import="com.example.wifi_db.DTO.BookMarkDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.wifi_db.DTO.BookMarkGroupDTO" %>
<%@ page import="java.text.SimpleDateFormat" %><%--
  Created by IntelliJ IDEA.
  User: SAMSUNG
  Date: 2023-12-16
  Time: 오후 6:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>와이파이 정보 구하기</title>
  <style>
    #link-list{
      margin-bottom: 20px;
    }
    #table-list{
      font-family: Arial, Helvetica, sans-serif;
      border-collapse: collapse;
      width: 100%;
    }
    #table-list td, #table-list th{
      border: 1px solid #ddd;
      text-align: center;
      font-size: 14px;
      padding: 8px;
    }
    #table-list tr:nth-child(odd){
      background-color: #f2f2f2;
    }
    #table-list tr:hover{
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
<h1>즐겨찾기 그룹 관리</h1>
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
<button onclick="location.href ='bookmark_group_add.jsp';">
  북마크 그룹 이름 추가
</button>

<table id="table-list">
  <thead>
  <tr>
    <th>ID</th>
    <th>북마크 이름</th>
    <th>순서</th>
    <th>등록일자</th>
    <th>수정일자</th>
    <th>비고</th>
  </tr>
  </thead>
  <tbody>
  <%
    BookMarkGroupDAO bookMarkGroupDAO = new BookMarkGroupDAO();
    if(bookMarkGroupDAO.count() == 0){
  %>
  <tr>
    <td colspan="6">
      데이터가 존재하지 않습니다.
    </td>
  </tr>
  <%
    }else {
      List<BookMarkGroupDTO> bookMarkGroupDTOList = bookMarkGroupDAO.selectList();
      for (BookMarkGroupDTO item: bookMarkGroupDTOList){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String regDate = sdf.format(item.getRegDttm());
        String uptDate = item.getUptDttm() == null ? "" : sdf.format(item.getUptDttm());
  %>
  <tr>
    <td>
      <%= item.getId()%>
    </td>
    <td>
      <%= item.getName()%>
    </td>
    <td>
      <%= item.getSequence()%>
    </td>
    <td>
      <%= regDate%>
    </td>
    <td>
      <%= uptDate%>
    </td>
    <td>
      <a href="bookmark_group_edit.jsp?id=<%= item.getId()%>">
        수정
      </a>
      <a href="bookmark_group_delete.jsp?id=<%= item.getId()%>">
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
