<%@ page import="com.example.wifi_db.DTO.BookMarkDTO" %>
<%@ page import="com.example.wifi_db.DAO.BookMarkDAO" %><%--
  Created by IntelliJ IDEA.
  User: rwg12
  Date: 2023-12-17
  Time: 오전 5:28
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

    String groupId = request.getParameter("groupId");
    String marNo = request.getParameter("mgrNo");

    if (groupId.equals("none")) {
        response.sendRedirect(request.getHeader("Referer"));
        return;
    }

    BookMarkDTO bookMarkDTO = new BookMarkDTO();
    bookMarkDTO.setGroupId(Integer.parseInt(groupId));
    bookMarkDTO.setMgrNo(marNo);

    BookMarkDAO bookMarkDAO = new BookMarkDAO();
    int affect = bookMarkDAO.insert(bookMarkDTO);
%>


<script>
    <%
        String text = affect > 0 ? "북마크에 데이터를 추가하였습니다." : "북마크에 데이터를 추가하지 못했습니다.";
    %>
    alert("<%= text %>")
    location.href = "bookmark.jsp";
</script>

</body>
</html>
