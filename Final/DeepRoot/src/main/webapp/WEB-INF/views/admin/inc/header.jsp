<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	

<div id="app">
	<div class="wrapper">

		<header class="main-header">
			<span class="logo-mini"><a href="${pageContext.request.contextPath}/index.do"
				data-duration="0.2s"><span
					class="img-responsive center-block logo">뿌리깊은마크</span></a> </span>
			<!-- header menu bar START -->
			<nav role="navigation" class="navbar navbar-static-top">
				<a href="javascript:;" data-toggle="offcanvas" role="button"
					class="sidebar-toggle"><span class="sr-only">Toggle
						navigation</span></a>
				<div class="navbar-custom-menu">
					<ul class="nav navbar-nav">
						<li class="dropdown messages-menu"><a href="javascript:;"
							data-toggle="dropdown" class="dropdown-toggle"><i
								class="fas fa-envelope"></i> <span class="label label-success">3</span></a>
							<!-- Message Alarm START -->
							<ul class="dropdown-menu">
								<li class="header">You have 1 message(s)</li>
								<li>
									<ul class="menu">
										<li><a href="javascript:;">
												<h4>
													Support Team <small><i class="fas fa-clock"></i> 5
														mins</small>
												</h4>
												<p>Why not consider this a test message?</p>
										</a></li>
									</ul>
								</li>
								<li class="footer"><a href="javascript:;">See All
										Messages</a></li>
							</ul> <!-- Message Alarm END --></li>
						<!-- Notice Alarm START -->
						<li class="dropdown notifications-menu"><a
							href="javascript:;" data-toggle="dropdown"
							class="dropdown-toggle"><i class="fas fa-bell fa-lg"></i> <span
								class="label label-warning">0</span></a>
							<ul class="dropdown-menu">
								<li class="header">You have 0 notification(s)</li>
							</ul></li>
						<!-- Notice Alarm END -->

						<!-- Admin Infomation START -->
						<li class="dropdown user user-menu"><a href="javascript:;"
							data-toggle="dropdown" class="dropdown-toggle"><img
								src="https://s3.amazonaws.com/uifaces/faces/twitter/GavicoInd/128.jpg"
								alt="User Image" class="user-image"> <span
								class="hidden-xs">관리자(Admin)</span></a></li>
						<!-- Admin Infomation END -->
					</ul>
				</div>
			</nav>
			<!-- header menu bar END -->
		</header>

		<!-- SideMenu START -->
		<aside class="main-sidebar">
			<section class="sidebar">
				<ul class="sidebar-menu">
					<!-- Sidemenu Chart START -->
					<li class="header"><i class="fas fa-chart-area"></i>&nbsp;&nbsp;CHART</li>
					<li class="pageLink router-link-active"><a href="main.do"
						class="transition">&nbsp;&nbsp;&nbsp;<i
							class="fas fa-chart-line"></i> <span class="page">&nbsp;&nbsp;Chart</span></a></li>
					<!-- Sidemenu Chart END -->

					<!-- Sidemenu Pages Bookmark list START -->
					<li class="header"><i class="fas fa-book"></i>&nbsp;&nbsp;Bookmark
						List</li>
					<li class="pageLink"><a href="mainBookList.do">&nbsp;&nbsp;&nbsp;<i
							class="fas fa-bookmark fa-fw"></i><span class="page">&nbsp;&nbsp;Main
								Page Bookmark</span></a></li>
					<li class="pageLink"><a href="social.do">&nbsp;&nbsp;&nbsp;<i
							class="fas fa-bookmark fa-fw"></i><span class="page">&nbsp;&nbsp;Social
								Page Bookmark</span></a></li>
					<!-- Sidemenu Pages Bookmark list END -->

					<!-- Sidemenu List START -->
					<li class="header"><i class="fa fa-list-alt"></i>&nbsp;&nbsp;List</li>
					<li class="pageLink"><a href="groupListTable.do">&nbsp;&nbsp;&nbsp;<i
							class="fas fa-list-ul"></i><span class="page">&nbsp;&nbsp;Group List</span></a></li>
					<li class="pageLink"><a href="userListTable.do">&nbsp;&nbsp;&nbsp;<i
							class="fas fa-list-ul"></i><span class="page">&nbsp;&nbsp;User List</span></a></li>
					<!-- Sidemenu List END -->
				</ul>
			</section>
		</aside>
		<!-- Sidemenu END -->