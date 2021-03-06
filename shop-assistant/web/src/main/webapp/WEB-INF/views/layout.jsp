<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@	taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
	<meta name="description" content="" />
	<meta name="author" content="" />
	<!--[if IE]>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<![endif]-->
	<title><tiles:insertAttribute name="title"/></title>
	<!-- BOOTSTRAP CORE STYLE  -->
	<link href="/assets/css/bootstrap.css" rel="stylesheet" />
	<!-- FONT AWESOME ICONS  -->
	<link href="/assets/css/font-awesome.css" rel="stylesheet" />
	<!-- CUSTOM STYLE  -->
	<link href="/assets/css/style.css" rel="stylesheet" />
	<!-- HTML5 Shiv and Respond.js for IE8 support of HTML5 elements and media queries -->
	<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
	<!--[if lt IE 9]>
	<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
	<script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
	<![endif]-->

	<tiles:importAttribute name="javascripts"/>
	<c:forEach var="script" items="${javascripts}">
		<script src="<c:url value="${script}"/>"></script>
	</c:forEach>
</head>
<body>
<header>
	<div class="container">
		<div class="row">
			<div class="col-md-6" style="text-align: left;">
				<strong>Shop Assistant</strong>
			</div>

			<sec:authorize access="isAuthenticated()">
				<sec:authentication property="principal.username" var="username"/>
				<div class="col-md-6">
					<strong>Welcome, ${username}!</strong>
				</div>
			</sec:authorize>
		</div>
	</div>
</header>
<!-- HEADER END-->
<!-- LOGO HEADER END-->
<section class="menu-section">
	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<div class="navbar-collapse collapse ">
					<ul id="menu-top" class="nav navbar-nav navbar-right">
						<sec:authorize access="isAuthenticated()">
							<c:set var="activeTab" ><tiles:insertAttribute name="activeTab"/></c:set>
							<li><a ${(activeTab == 'products') ? ' class="menu-top-active"' : ''} href="/products">Products</a></li>
							<li><a ${(activeTab == 'orders') ? ' class="menu-top-active"' : ''} href="/orders">Orders</a></li>
							<li><a ${(activeTab == 'settings') ? ' class="menu-top-active"' : ''} href="/settings">Settings</a></li>
							<li><a href="/logout">Logout</a></li>
						</sec:authorize>
						<sec:authorize access="isAnonymous()">
							<li>&nbsp;</li>
						</sec:authorize>
					</ul>
				</div>
			</div>
		</div>
	</div>
</section>
<!-- MENU SECTION END-->
<div class="content-wrapper">
	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<h4 id="pageTitle" class="page-head-line"><tiles:getAsString name="pageTitle" /></h4>
			</div>
		</div>

		<tiles:insertAttribute name="notification" />

		<div class="row">
			<tiles:insertAttribute name="body"/>
		</div>
	</div>
</div>
<!-- CONTENT-WRAPPER SECTION END-->
<footer>
	<div class="container">
		<div class="row">
			<div class="col-md-12">
				&copy; 2015 ShopAssistant</a>
			</div>

		</div>
	</div>
</footer>
<!-- FOOTER SECTION END-->

</body>
</html>
