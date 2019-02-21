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

<body>
	<%@ include file="/WEB-INF/views/cmm/include/includeMainBody.jsp" %>
	<tiles:insertAttribute name="body" />
</body>
</html>
