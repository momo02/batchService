<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<meta name="robots" content="noindex, nofollow" />
<title>배치 모니터링</title>

<script src="http://code.jquery.com/jquery-1.9.0.js"></script>
<script src="http://code.jquery.com/jquery-migrate-1.2.1.js"></script>

<script type="text/javascript">
var list = "${batchList}";

$(document).ready(function() {
	
	$('#btnAllStart').click(function() {
    	
    	$("#flag").val("allStart");
    	$('#frm').attr("action", "/schedule/update.do");
    	
    	fnSubmit("모든 스케줄러가 재시작 됩니다. 실행 하시겠습니까?");
    });
	
	$('#btnAllStop').click(function() {
    	
    	$("#flag").val("allStop");
    	$('#frm').attr("action", "/schedule/update.do");
    	
    	fnSubmit("모든 스케줄러가 중지 됩니다. 실행 하시겠습니까?");
    });
	
	$('.btnPause').click(function() {
    	var triggerName = $(this).attr("triggerName");
    	
    	$("#flag").val("pause");
    	$("#triggerName").val(triggerName);
    	$('#frm').attr("action", "/schedule/update.do");
    	
    	fnSubmit("일시정지 하시겠습니까?");
    });
	
	$('.btnResume').click(function() {
		var triggerName = $(this).attr("triggerName");
    	
    	$("#flag").val("resume");
    	$("#triggerName").val(triggerName);
    	$('#frm').attr("action", "/schedule/update.do");
    	
    	fnSubmit("재시작 하시겠습니까?");
    });
	
	$('.btnCronChange').click(function() {
		var triggerName = $(this).attr("triggerName");
		var batchType = $(this).attr("batchType");
		var idx = $(this).attr("idx");
		var txtCronExp = $("#txtCronExp_" + idx).val();
		var description = $("#description_" + idx).val();
		
		if(batchType != 'cron')
		{
			if(txtCronExp < 1000)
			{
				alert("반복시간은 1000ms 보다 작을 수 없습니다.");
				return false;
			}
		}
    	
    	$("#flag").val("change");
    	$("#triggerName").val(triggerName);
    	$("#batchType").val(batchType);
    	$("#cronExpression").val(txtCronExp);
    	$("#description").val(description);
    	$('#frm').attr("action", "/schedule/update.do");
    	
    	fnSubmit("배치시간을 변경하시겠습니까?");
    });
});

function fnSubmit(msg)
{
	if(!confirm(msg))
		return false;
	
	$("#frm").submit();	
}

</script>

</head>
<body>
<form name="frm" id="frm" method="post" action="">
<input type="hidden" name="flag" id="flag" value="" />
<input type="hidden" name="batchType" id="batchType" value="" />
<input type="hidden" name="triggerName" id="triggerName" value="" />
<input type="hidden" name="cronExpression" id="cronExpression" value="" />
<input type="hidden" name="description" id="description" value="" />
<br/>
<table border="1" width="100%">
	<colgroup>
        <col width="100%" />
    </colgroup>
	<tbody>
		<tr>
			<td><br/>현재시간 : <fmt:formatDate value="${nowDate}" pattern="yyyy-MM-dd HH:mm:ss"/> <br/><br/></td>
		</tr>
	</tbody>
</table>

<table border="1" width="100%">
	<colgroup>
        <col width="10%" />
        <col width="10%" />
        <col width="*" />
        <col width="10%" />
        <col width="12%" />
        <col width="10%" />
        <col width="10%" />
        <col width="9%" />
        <col width="8%" />
        <col width="8%" />
    </colgroup>
	<tbody>
<c:if test="${isShutdown != true}">
		<tr>
			<td colspan="10">
				<br/>
				<input type="button" id="btnAllStart" value="전체시작" <c:if test="${isInStandbyMode == false}">disabled="disabled"</c:if>/>
				<input type="button" id="btnAllStop" value="전체중지" <c:if test="${isInStandbyMode == true}">disabled="disabled"</c:if>/> 
				<br/><br/>
			</td>
		</tr>
</c:if>
		<tr>
			<td align="center"><b>Job Name</b></td>
			<td align="center"><b>Trigger Name</b></td>
			<td align="center"><b>Description</b></td>
			<td align="center"><b>실행 메소드</b></td>
			<td align="center"><b>Cron 표현식<br/>또는 반복시간(ms)</b></td>			
			<td align="center"><b>이전 실행시간</b></td>
			<td align="center"><b>다음 실행시간</b></td>
			<td align="center"><b>상태</b></td>
			<td align="center"><b>일시정지</b></td>
			<td align="center"><b>재시작</b></td>
		</tr>
<c:forEach var="list" items="${batchList}" varStatus="status">
		<tr>
			<td align="center"><c:out value="${list.jobName}" /></td>
			<td align="center"><c:out value="${list.triggerName}" /></td>
			<%-- <td><c:out value="${list.description}" /></td> --%>
			<td><input type="text" id="description_${status.index}" name="description_${status.index}" value="<c:out value="${list.description}"/>" /></td>
			
			<td align="center"><c:out value="${list.executeMethod}" /></td>
			<td align="center">
				<input type="text" id="txtCronExp_${status.index}" name="txtCronExp_${status.index}" value="<c:out value="${list.cronExpression}" />" size="10"/> 
				<input type="button" id="btnCronChange_${status.index}" value="변경" class="btnCronChange" batchType="${list.batchType}" triggerName="${list.triggerName}" idx="${status.index}" <c:if test="${isInStandbyMode == true}">disabled="disabled"</c:if> />
			</td>			
			<td align="center"><fmt:formatDate value="${list.previousFireTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td align="center"><fmt:formatDate value="${list.nextFireTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td align="center"><c:out value="${list.txtStatus}" /></td>
			<td align="center">
				<!-- 트리거가 이미 일시정지 상태이면 일시정지 버튼 비활성화 -->
				<input type="button" id="btnPause_${status.index}" value="일시정지"
							class="btnPause" triggerName="${list.triggerName}"
							<c:if test="${list.triggerState == 'PAUSED' || isInStandbyMode == true}">disabled="disabled"</c:if> />
							
			</td>
			<td align="center">
					<!-- 트리거가 일시정지 상태이면 재시작 버튼 활성화 -->		
					<input type="button"
							id="btnResume_${status.index}" value="재시작" class="btnResume"
							triggerName="${list.triggerName}"
							<c:if test="${list.triggerState != 'PAUSED' || isInStandbyMode == true}">disabled="disabled"</c:if> />
			</td>
		</tr>
</c:forEach>
	</tbody>
</table>

</form>
</body>
</html>






























