<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/views/cmm/include/include.jsp"/>
<meta charset="utf-8" />
<title>Insert title here</title>
<script>
$(function(){
	
	var form = {
			id : "123",
			name : "123",
			testTags : [ {
				id : "1111",
				tag : "2222"
			} ]
		};

	$.ajax({
		url : "sendReqJson.do",
		method : "post",
		type : "json",
		contentType : "application/json",
		data : JSON.stringify(form),
		success : function(d) {
			$("#result").append(d.result);
			if (d.result == "success") {
				console.log(d.data);
				$("#testDiv").append( JSON.stringify(d.data) );
			}
		},
		error: function() {
			$("#result").append("error");
		}
	});

})
</script>
</head>
<body>
<h2>===== send requset json TEST =====</h2>
요청 결과 >>>> <span id="result"> </span><br/>
전달된 JSON 데이터 >>>> <span id="testDiv"> </span>
</body>
</html>