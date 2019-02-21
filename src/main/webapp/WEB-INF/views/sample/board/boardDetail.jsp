
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<form class="boardDetailForm">
	<input type="hidden" class="k-textbox" data-bind="value: boardDetail.boardId"/>
	<div class="row col">
		boardTitle : <input type="text" class="k-textbox" data-bind="value: boardDetail.boardTitle"/>
	</div>
	<div class="row col">
		boardContent : <input type="text" class="k-textbox" data-bind="value: boardDetail.boardContent"/>
	</div>
	<div class="row col">
		insertDate : <input type="text" class="k-textbox" data-bind="value: boardDetail.insertDate"/>
	</div>
	<div class="row col">
		updateDate : <input type="text" class="k-textbox" data-bind="value: boardDetail.updateDate"/>
	</div>
	<div class="row col">
		insertId : <input type="text" class="k-textbox" data-bind="value: boardDetail.insertId"/>
	</div>
	<button type="button" data-bind="click: save">확인</button>
	<button type="button" data-bind="click: cancel">취소</button>
</form>

<script>
$(function(){
	console.log('detail onload')
	var uid = '${vo.uid}';
	var row = findByUid(uid);
	var grid = $('#boardList').data('kendoGrid');
	var ds = $('#boardList').data('kendoGrid').dataSource;
	var popup = $('#pop').data('kendoWindow');
	var viewModel = kendo.observable({
		boardDetail : row,
		save: function(){
			ds.sync().done(function(e){
				alert('성공')
				if(popup){
					popup.close();
				}
			}).fail(function(e){
				console.log('fail : ',e)
				alert('실패')
			});
		},
		cancel: function(){
			/*
			ds.cancelChanges(r);
			r.set('uid',uid)
			*/
			if(popup){
				popup.close();
			}
		}
	});
	kendo.bind($('#pop'), viewModel);
});


</script>