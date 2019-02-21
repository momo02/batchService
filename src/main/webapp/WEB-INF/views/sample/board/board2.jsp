<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<div id="boardList"></div>


<script type="text/x-kendo-template" id="toolbarTemplate">
<div class="commonGridToolbar">
	<button type="button" class="k-button toolbarCreate">등록</button>
	<button type="button" class="k-button toolbarDelete">삭제</button>
	<button type="button" class="k-button toolbarSave">저장</button>
	<button type="button" class="k-button toolbarCancel">취소</button>
</div>
</script>
<script>
$(function(){
	boardInit();
})

var ds, grid;
function boardInit(){
	//sample option -- 추후 삭제
	
	var options = options || {
		readUrl: '/sample/board/select2.do',
		saveUrl: '/sample/board/save2.do',
		tranType: {
			r: true,
			c: true,
			u: true,
			d: true
		},
		modelId: 'boardId'
// 		nonEditColumn: ['insertDate'],
// 		notNullColumn: ['insertDate']
	}
	

	ds = new Ds(options);
	console.log('ds :: ',ds)
	
	
	
	// 그리드 설정
	grid = $('#boardList').kendoGrid({
		selectable: "multiple",
		editable: {
			mode: 'inline',
			confirmation: false
		},
		toolbar: kendo.template($('#toolbarTemplate').html()), 
		dataSource: ds,
		columns: [
			{ field: 'boardId', title: '아이디'}, 
			{ field: 'boardTitle', title: '제목' }, 
			{ field: 'boardContent', title: '내용' }, 
			{ field: 'insertDate', title: '등록일', format: '{0:yyyy-MM-dd}'}
		],
		pageable: {
			refresh: true,
			pageSizes: true,
			buttonCount: 5
		},
		change: function(){
			this.cellCo
		}
	}).data('kendoGrid');
	
// 	grid.setDataSource(ds);
	
	// 이벤트 설정
	grid.element.on('click','.toolbarCreate',function(){
		console.log('create')
		grid.addRow();
	})
	grid.element.on('click','.toolbarDelete',function(){
		console.log('delete')
		var _ds = grid.dataSource;
		var selected = grid.select();
		selected.each(function(i,item){
// 			console.log(i,item)
			grid.removeRow(item);
// 			var uid = $(item).data('uid');
// 			var row = _ds.getByUid(uid);
// 			_ds.remove(row);
		});
	})
	grid.element.on('click','.toolbarSave',function(){
		console.log('save')
		if(!grid.dataSource.hasChanges()) return;
		grid.dataSource.sync().done(function(){
			alert('저장 성공')
			grid.dataSource.read();
		}).fail(function(){
			alert('저장 실패')
		})
	})
	grid.element.on('click','.toolbarCancel',function(){
		console.log('cancel')
		grid.dataSource.cancelChanges();
	})
	grid.element.on('dblclick','tbody td',function(e){
// 	grid.element.on('click','tbody td',function(e){
		grid.editCell($(this));
	});
// 	grid.element.on("blur", "tbody td", function(e) {
// 		grid.closeCell($(this));
// 	});
}


</script>