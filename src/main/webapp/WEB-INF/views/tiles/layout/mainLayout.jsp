<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<title>Sample</title>
<!-- Meta, title, CSS, favicons, etc. -->
<meta charset="utf-8">
<title>HIAIR</title>
<meta name="keywords" content="HIAIR sample" />
<meta name="author" content="HIAIR">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<%@ include file="/WEB-INF/views/cmm/include/includeMainHead.jsp" %>

</head>

<body class="dashboard-page">
	<%@ include file="/WEB-INF/views/cmm/include/includeMainBody.jsp" %>

<!-------------------------------------------------------------+ 
  <body> Helper Classes: 
---------------------------------------------------------------+ 
  '.sb-l-o' - Sets Left Sidebar to "open"
  '.sb-l-m' - Sets Left Sidebar to "minified"
  '.sb-l-c' - Sets Left Sidebar to "closed"

  '.sb-r-o' - Sets Right Sidebar to "open"
  '.sb-r-c' - Sets Right Sidebar to "closed"
---------------------------------------------------------------+
 Example: <body class="example-page sb-l-o sb-r-c">
 Results: Sidebar left Open, Sidebar right Closed
--------------------------------------------------------------->

	<!-- Start: Main -->
	<div id="main">
		<!-- Start: Header -->
		<tiles:insertAttribute name="header" />
		<!-- End: Header -->

		<!-- Start: Sidebar -->
		<tiles:insertAttribute name="menu" />
		<!-- End: Sidebar Left -->

		<!-- Start: Content-Wrapper -->
		<section id="content_wrapper">

			<!-- Start: Topbar -->
			<header id="topbar" class="alt">
				<div class="topbar-left">
					<ol class="breadcrumb">
						<li class="crumb-active"><a href="dashboard.html">Dashboard</a>
						</li>
						<li class="crumb-icon"><a href="dashboard.html"> <span
								class="glyphicon glyphicon-home"></span>
						</a></li>
						<li class="crumb-link"><a href="index.html">Home</a></li>
						<li class="crumb-trail">Dashboard</li>
					</ol>
				</div>
			</header>
			<!-- End: Topbar -->

			<!-- Begin: Content -->
			<section id="content" class="table-layout animated fadeIn">

				<!-- begin: .tray-center -->
				<div class="tray-center">
					<tiles:insertAttribute name="body" />
				</div>
				<!-- end: .tray-center -->
			</section>
			<!-- End: Content -->

			<!-- Begin: Page Footer -->
			<tiles:insertAttribute name="footer" />
			<!-- End: Page Footer -->

		</section>
		<!-- End: Content-Wrapper -->


	</div>
	<!-- End: Main -->

</body>
</html>
