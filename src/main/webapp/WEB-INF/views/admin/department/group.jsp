<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib uri="http://java.sun.com/jsp/jstl/core"
prefix="c"%>
<c:url var="R" value="/" />
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="${R}res/common.js"></script>
    <link rel="stylesheet" type="text/css" href="${R}res/common.css" />
    <link rel="stylesheet" type="text/css" href="${R}res/table.css" />
    <link rel="stylesheet" type="text/css" href="${R}res/index.css" />
  </head>
  <body>
    <div class="top">
      <span>
        <a target="_blank" href="https://github.com/gwonkim" class="top_link">깃허브</a>
        |
        <a target="_blank" href="https://github.com/gwonkim" class="top_link">유튜브설명영상</a>
      </span>
    </div>
    <div class="nav">
      <span class="nav_left" id="navL">
        <img src="${R}image/library2.png" class="nav_img" />
      </span>
      <a class="nav_title" href="${R}">지원도서관</a>
      <sec:authorize access="authenticated">
        <span class="nav_right" id="navR">
          <a href="list" class="nav_link">사용자 그룹별 목록</a>
          <a href="create" class="nav_link">그룹 추가</a>
        </span>
      </sec:authorize>
    </div>
    <div class="container">
      <h1>그룹 목록</h1>
      <table class="list">
        <thead>
          <tr>
            <th>id</th>
            <th>그룹명</th>
            <th>대출기간</th>
            <th>대출권수</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="department" items="${ departments }">
            <tr data-url="edit?id=${ department.id }">
              <td>${ department.id }</td>
              <td>${ department.name }</td>
              <td>${ department.period }</td>
              <td>${ department.volume }</td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
  </body>
</html>