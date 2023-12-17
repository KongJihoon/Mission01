<%@ page import="com.example.wifi_db.DTO.BookMarkGroupDTO" %>
<%@ page import="com.example.wifi_db.DAO.BookMarkGroupDAO" %><%--
  Created by IntelliJ IDEA.
  User: rwg12
  Date: 2023-12-17
  Time: 오전 4:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>와이파이 정보 구하기</title>
</head>
<body>
<%
    request.setCharacterEncoding("UTF-8");

    String id = request.getParameter("id");
    String name = request.getParameter("name");
    String sequence = request.getParameter("sequence");

    BookMarkGroupDTO bookMarkGroupDTO = new BookMarkGroupDTO();
    bookMarkGroupDTO.setId(Integer.parseInt(id));
    bookMarkGroupDTO.setName(name);
    bookMarkGroupDTO.setSequence(Integer.parseInt(sequence));

    BookMarkGroupDAO bookMarkGroupDAO = new BookMarkGroupDAO();
    int affect = bookMarkGroupDAO.update(bookMarkGroupDTO);
%>

<script>
    <%
        String text = affect > 0 ? "북마크 그룹 데이터를 수정하였습니다." : "북마크 그룹 데이터를 수정하지 못했습니다.";
    %>
    alert("<%= text%>")
    location.href = "bookmark_group.jsp";
</script>

</body>
</html>
