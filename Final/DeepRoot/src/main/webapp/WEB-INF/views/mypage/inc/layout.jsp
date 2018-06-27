<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="se" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

	<!-- Latest compiled Bootstrap Common CSS -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <!-- Bootstrap Common CSS END -->
    
    <!-- jQuery Confirm START -->
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.0/jquery-confirm.min.css">
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.0/jquery-confirm.min.js"></script>
	<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
	<!-- jQuery Confirm END -->

	<!-- Common Script START -->
    <!-- Latest compiled JavaScript & CSS -->
    <script  src="${pageContext.request.contextPath}/js/mypage/modal.js"></script>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css" integrity="sha384-DNOHZ68U8hZfKXOrtjWvjxusGo9WQnrNx2sqG0tfsghAvtVlRW3tvkXWZh58N9jp" crossorigin="anonymous">
    <!-- Script Common JavaScript & CSS END -->

	<!--  jsTree CSS & JS START -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
	<script  src="${pageContext.request.contextPath}/js/jstree.min.js"></script>
	
	<!-- Header Footer -->
    <link href="${pageContext.request.contextPath}/css/mainpage/header.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/mainpage/responsive.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/mainpage/footer.css" rel="stylesheet">
    <!-- Header Footer END -->
    
	<!-- Google Icon CDN -->
	<link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/icon?family=Material+Icons">
	<!-- Google Icon CDN END -->

	<!-- jQuery Ajax Form START -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.form/4.2.2/jquery.form.min.js" integrity="sha384-FzT3vTVGXqf7wRfy8k4BiyzvbNfeYjK+frTVqZeNDFl8woCbF0CYG6g2fMEFFo/i" crossorigin="anonymous"></script>
	<!-- jQuery Ajax Form END -->
	
<title>my BookMark</title>
</head>
<body>
	<!-- Script -->
	<script type="text/javascript"  src="${pageContext.request.contextPath}/js/mypage/mycategory.js"></script>
	<script type="text/javascript">
	
	
	</script>
	
	<div id="main-header">
		<tiles:insertAttribute name="header" />
	</div>

	<div id="main">
		<tiles:insertAttribute name="content" />
	</div>

	<div id="main-footer">
		<tiles:insertAttribute name="footer" />
	</div>

	<!-- URL 추가 모달 -->
	<div id="linkAdd_btn" class="modal fade" role="dialog">
		<div class="main-modal-controller">
			<div class="main-modal-center">
				<div class="modal-dialog">
					<!-- Modal content-->
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">&times;</button>
							<h4 class="modal-title">
								<b>URL 추가</b>
							</h4>
						</div>
		
						<div class="modal-body">
							<form id="form_btn">
								<table class="table">
									<colgroup>
										<col width="30%">
										<col width="70%">
									</colgroup>
									<tr class="addUrlLevel1">
										<td class="info" style="vertical-align: middle;">URL :</td>
										<td><input type="text" id="url_btn" name="url_btn"
											class="form-control"></td>
									</tr>
									<tr class="addUrlLevel2">
										<td class="info" style="vertical-align: middle;">제목 :</td>
										<td><input type="text" id="title_btn" name="title_btn"
											class="form-control"></td>
									</tr>
									<tr class="addUrlLevel2">
										<td class="info" style="vertical-align: middle;">카테고리 :</td>
										<td><input type="text" id="category_btn" name="category_btn"
											class="form-control" readonly="readonly"></td>
									</tr>
									<tr class="addUrlLevel3">
										<td class="info" style="vertical-align: middle;">공유제목 :</td>
										<td><input type="text" id="sname_btn" name="sname_btn"
											class="form-control"></td>
									</tr>
									<tr class="addUrlLevel3">
										<td class="info" style="vertical-align: middle;">해시태그 :</td>
										<td><input type="text" id="htag_btn" name="htag_btn"
											class="form-control" onkeydown="addHashtag()"></td>
									</tr>
									<tr class="addUrlLevel3">
										<td colspan="2"  id="htag_append"></td>
									</tr>
									<tr class="addUrlLevel2">
										<td><input type="checkbox" id="share_btn"> <label
											for="share_btn">공유하기</label></td>
										<td></td>
									</tr>
									<tr class="addUrlLevel3">
										<td colspan="2"><p style="color: red; width: 100%;">※ 회원님께서 공유하신 정보는 소셜 북마크 페이지에 자동으로 올라가게 되며, 다른 사용자가 해당 사이트를 공유할 수 있습니다. 또한, 저희 사이트 역시 해당 사이트를 데이터 수집 차원에서 사용하고 있습니다. 이 점을 숙지해주시길 바랍니다.</p></td>
									</tr>
								</table>
							</form>
							<div class="modal-footer">
								<!-- type="submit" value="Submit" -->
								<button type="button" class="btn btn-default btn-sm addUrlLevel1" onclick="openAddUrlLevel2()">다음</button>
								<button type="button" class="btn btn-default btn-sm addUrlLevel2" onclick="addUrlLevel1()">이전</button>
								<button type="button" class="btn btn-default btn-sm addUrlLevel2-1" onclick="addUrlNotShare()">추가</button>
								<button type="button" class="btn btn-default btn-sm addUrlLevel2-2" onclick="openAddUrlLevel3()">다음</button>
								<button type="button" class="btn btn-default btn-sm addUrlLevel3" onclick="addUrlLevel2_1();">이전</button>
								<!-- <button type="button" class="btn btn-default btn-sm"
									data-dismiss="modal">취소</button> -->
								<button class="btn btn-default btn-sm addUrlLevel3" onclick="addUrlShare()">추가하기</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- 폴더 추가 모달 -->
	<div class="modal fade" id="folderAdd" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">
						<b>폴더 추가</b>
					</h4>
				</div>

				<div class="modal-body">
					<form id="form2">
						<table class="table">
							<colgroup>
								<col width="30%">
								<col width="70%">
							</colgroup>
							<tr>
								<td class="info" style="vertical-align: middle;">폴더 이름</td>
								<td><input type="text" id="foldername" name="foldername"
									class="form-control"></td>
							</tr>
						</table>
					</form>
					<div class="modal-footer">
						<!-- type="submit" value="Submit" -->
						<button type="button" class="btn btn-default btn-sm"
							data-dismiss="modal">취소</button>
						<button class="btn btn-default btn-sm" id="folderAddsubmit">추가하기</button>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- URL 수정 모달 -->
	<div class="modal fade" id="editurl" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">
						<b>URL 변경</b>
					</h4>
				</div>

				<div class="modal-body">
					<form id="form3">
						<table class="table">
							<colgroup>
								<col width="30%">
								<col width="70%">
							</colgroup>
							<tr>
								<td class="info" style="vertical-align: middle;">URL</td>
								<td><input type="text" id="editurlval" name="editurlval"
									class="form-control"></td>
							</tr>
						</table>
					</form>
					<div class="modal-footer">
						<!-- type="submit" value="Submit" -->
						<button type="button" class="btn btn-default btn-sm"
							data-dismiss="modal">취소</button>
						<button class="btn btn-default btn-sm" id="editurlsubmit">수정하기</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 공유 수정 모달 -->
	<div class="modal fade" id="edit_htag" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">
						<b>공유 수정</b>
					</h4>
				</div>

				<div class="modal-body">
					<form id="edit_htag_form">
						<table class="table">
							<colgroup>
								<col width="30%">
								<col width="70%">
							</colgroup>
							<tr >
								<td class="info" style="vertical-align: middle;">공유제목 :</td>
								<td><input type="text" id="edit_sname_btn" name="edit_sname_btn"
									class="form-control"></td>
							</tr>
							<tr class="addUrlLevel3">
								<td class="info" style="vertical-align: middle;">해시태그 :</td>
								<td><input type="text" id="edit_htag_btn" name="edit_htag_btn"
									class="form-control" onkeydown="edit_addHashtag()"></td>
							</tr>
							<tr >
								<td colspan="2"  id="edit_htag_append"></td>
							</tr>
						</table>
					</form>
					<div class="modal-footer">
						<button type="button" class="btn btn-default btn-sm " data-dismiss="modal">취소</button>
						<button class="btn btn-default btn-sm " onclick="edit_htag_submit()">수정하기</button>
					</div>
				</div>
			</div>
		</div>
	</div>

</body>
	<!-- MY Page CSS -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/mypage/mypage.css?ver=2" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/social/modal.css" />
	<!-- MY Page CSS END -->
</html>