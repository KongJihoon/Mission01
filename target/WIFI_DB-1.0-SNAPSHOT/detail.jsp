<%@ page import="com.example.wifi_db.DAO.WifiDAO" %>
<%@ page import="com.example.wifi_db.DTO.WifiDTO" %>
<%@ page import="com.example.wifi_db.DAO.BookMarkGroupDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.wifi_db.DTO.BookMarkGroupDTO" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>와이파이 정보 구하기</title>
    <style>
        #link-list {
            margin-bottom: 20px;
        }

        #bookmark-list {
            margin-bottom: 5px;
        }

        #bookmark-list select {
            float: left;
            margin-right: 5px;
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

        #table-list tr:nth-child(odd) {
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
<h1>와이파이 정보 구하기</h1>
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
    String mgrNo = request.getParameter("mgrNo");
    String distance = request.getParameter("distance");

    WifiDAO wifiDAO = new WifiDAO();
    WifiDTO wifiDTO = wifiDAO.select(mgrNo);

    BookMarkGroupDAO bookMarkGroupDAO = new BookMarkGroupDAO();
    List<BookMarkGroupDTO> bookMarkGroupDTOList = bookMarkGroupDAO.selectList();
%>
<div id = bookmark-list>
    <form action="bookmark_add_submit.jsp" method="post" id="bookmark-form">
        <select name="groupId">
            <option value="none" selected>북마크 그룹 이름 선택</option>
            <%
                for(BookMarkGroupDTO item : bookMarkGroupDTOList){
            %>
            <option value="<%= item.getId()%>">
                <%= item.getName()%>
            </option>
            <%
                }
            %>
        </select>

        <input type="submit" value="즐겨찾기 추가하기">
        <input type="hidden" name="mgrNo" value="<%= mgrNo %>">
    </form>

</div>

<table id="table-list">
    <tr>
        <th>거리(Km)</th>
        <td><%= distance %>
        </td>
    </tr>
    <tr>
        <th>관리번호</th>
        <td><%= wifiDTO.getMgrNo() %>
        </td>
    </tr>
    <tr>
        <th>자치구</th>
        <td><%= wifiDTO.getWrdofc() %>
        </td>
    </tr>
    <tr>
        <th>와이파이명</th>
        <td><%=wifiDTO.getMainNm() %>
        </td>
    </tr>
    <tr>
        <th>도로명주소</th>
        <td><%=wifiDTO.getAdres1() %>
        </td>
    </tr>
    <tr>
        <th>상세주소</th>
        <td><%=wifiDTO.getAdres2() %>
        </td>
    </tr>
    <tr>
        <th>설치위치(층)</th>
        <td><%=wifiDTO.getInstl_Floor() %>
        </td>
    </tr>
    <tr>
        <th>설치유형</th>
        <td><%=wifiDTO.getInstl_Ty() %>
        </td>
    </tr>
    <tr>
        <th>설치기관</th>
        <td><%=wifiDTO.getInstl_Mby() %>
        </td>
    </tr>
    <tr>
        <th>서비스구분</th>
        <td><%=wifiDTO.getSvc_Se() %>
        </td>
    </tr>
    <tr>
        <th>망종류</th>
        <td><%=wifiDTO.getCmcWr() %>
        </td>
    </tr>
    <tr>
        <th>설치년도</th>
        <td><%=wifiDTO.getCnstc_Year() %>
        </td>
    </tr>
    <tr>
        <th>실내외구분</th>
        <td><%=wifiDTO.getInout_Door() %>
        </td>
    </tr>
    <tr>
        <th>WIFI접속환경</th>
        <td><%=wifiDTO.getReMars3() %>
        </td>
    </tr>
    <tr>
        <th>X좌표</th>
        <td><%=wifiDTO.getLnt() %>
        </td>
    </tr>
    <tr>
        <th>Y좌표</th>
        <td><%=wifiDTO.getLat() %>
        </td>
    </tr>
    <tr>
        <th>작업일자</th>
        <td><%=wifiDTO.getWork_Dttm() %>
        </td>
    </tr>
</table>

</body>
</html>
