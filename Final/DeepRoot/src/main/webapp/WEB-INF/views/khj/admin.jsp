<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 페이지</title>
</head>
<body>
	<h2>관리자 페이지</h2>
	
	<img src="${pageContext.request.contextPath}/images/homepage/1.png">
	
	<h3>사이트 총 방문자수 : ${total_visitors}</h3>
	
	<h3>날짜별 방문자수</h3>
	<c:forEach items="${visitor_count}" var="v">
		<ul>
			<li>${v.d}</li>
			<li>${v.c}</li>
		</ul>
	</c:forEach>
	
	<a href="readEmail.do">이메일</a>
	
	<h3>전체 회원수 : ${allUser}</h3>
	<h3>신규 가입자수 : ${newUser}</h3>
	
	<h3>회원 리스트</h3>
 	<c:forEach items="${userList}" var="user">
		<ul>
			<li>${user.uid}</li>
			<li>${user.nname}</li>
			<li>${user.pwd}</li>
			<li>${user.enabled}</li>
			<li>${user.regdate}</li>
			<li>${user.profile}</li>
		</ul>
	</c:forEach>
	
	<h3>블랙리스트 등록</h3>
	<form action="blacklist.do" method="post">
		아이디 <input type="text" id="uid" name="uid">
		<button type="submit">등록</button>
	</form>
	
	<h3>공지사항 쓰기</h3>
	<form action="noticeReg.do" method="post">
		공지사항 내용<input type="text" id="ncontent" name="ncontent">
		<button type="submit">등록</button>
	</form>
	
 	<h3>개인이 추가한 북마크수</h3>
	<c:forEach items="${uCount}" var="u">
		<ul>
			<li>${u.d}</li>
			<li>${u.c}</li>
		</ul>
	</c:forEach>
	
	<h3>그룹이 추가한 북마크수</h3>
	<c:forEach items="${gCount}" var="g">
		<ul>
			<li>${g.d}</li>
			<li>${g.c}</li>
		</ul>
	</c:forEach>

	
	<%-- <h3>소셜 개인 북마크 리스트</h3>
 	<c:forEach items="${uBookList}" var="uBook">
		<ul>
			<li>${uBook.ubid}</li>
			<li>${uBook.url}</li>
			<li>${uBook.sname}</li>
			<li>${uBook.uid}</li>
			<li>${uBook.nname}</li>
			<li>${uBook.view}</li>
			<li>${uBook.htag}</li>
			<li>${uBook.sdate}</li>
		</ul>
	</c:forEach>

	<h3>소셜 개인 북마크 삭제</h3>
	<form action="deleteSUBook.do" method="post">
		북마크 번호 <input type="text" id="ubid" name="ubid">
		<button type="submit">삭제</button>
	</form>
	
	<h3>소셜 그룹 리스트</h3>
 	<c:forEach items="${sGroupList}" var="sGroup">
		<ul>
			<li>${sGroup.gid}</li>
			<li>${sGroup.gname}</li>
			<li>${sGroup.nname}</li>
			<li>${sGroup.htag}</li>
			<li>${sGroup.regdate}</li>
			<li>${sGroup.duedate}</li>
		</ul>
	</c:forEach>

	<h3>소셜 그룹 삭제</h3>
	<form action="deleteSGroup.do" method="post">
		그룹 번호 <input type="text" id="gid" name="gid">
		<button type="submit">삭제</button>
	</form> --%>
	
	
	<%-- <h3>카테고리</h3>
 	<c:forEach items="${categoryList}" var="category">
		<ul>
			<li>${category.acid}</li>
			<li>${category.acname}</li>
		</ul>
	</c:forEach>

	<h3>카테고리 추가</h3>
	<form action="addCategory.do" method="post">
		카테고리 이름 <input type="text" id="acname" name="acname">
		<button type="submit">추가</button>
	</form>
	
	<h3>카테고리 수정</h3>
	<form action="updateCategory.do" method="post">
		카테고리 번호 <input type="text" id="acname" name="acid">
		카테고리 이름 <input type="text" id="acname" name="acname">
		<button type="submit">수정</button>
	</form>
	
	<h3>카테고리 삭제</h3>
	<form action="deleteCategory.do" method="post">
		카테고리 번호 <input type="text" id="acname" name="acid">
		<button type="submit">삭제</button>
	</form> --%>
	
	<%-- <c:forEach items="${bookList}" var="book">
		<ul>
			<li>${book.abid}</li>
			<li>${book.url}</li>
			<li>${book.urlname}</li>
			<li>${book.regdate}</li>
			<li>${book.view}</li>
			<li>${book.acid}</li>
		</ul>
	</c:forEach>
	
	<h3>관리자 URL 추가</h3>
	<form action="addBook.do" method="post">
		URL <input type="text" id="url" name="url">
		URLNAME <input type="text" id="urlname" name="urlname">
		관리자 카테고리 번호 <input type="text" id="acid" name="acid">
		<button type="submit">추가</button>
	</form>
	
	<h3>관리자 URL 수정</h3>
	<form action="updateBook.do" method="post">
		ABID <input type="text" id="abid" name="abid">
		URL <input type="text" id="url" name="url">
		URLNAME <input type="text" id="urlname" name="urlname">
		<button type="submit">수정</button>
	</form>
	
	<h3>관리자 URL 삭제</h3>
	<form action="deleteBook.do" method="post">
		ABID <input type="text" id="abid" name="abid">
		<button type="submit">삭제</button>
	</form> --%>
	
</body>
</html>