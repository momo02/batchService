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
	// 
	ds = new kendo.data.DataSource({
		transport: {
			read: {
				url: '/sample/board/select.do',
				dataType: 'json'
			},
			create: {
				url: '/sample/board/insert.do',
				dataType: 'json',
				contentType: "application/json",
				type: 'post'
			},
			update: {
				url: '/sample/board/update.do',
				dataType: 'json',
				contentType: "application/json",
				type: 'post'
			},
			destroy: {
				url: '/sample/board/delete.do',
				dataType: 'json',
				contentType: "application/json",
				type: 'post'
			},
			parameterMap: function(options, operation) {
                if (operation !== "read" && options.models) {
					return kendo.stringify(options.models);
                }
                return options;
            }
		},
		requestStart: function(e) {
			
		},
		requestEnd: function(e){
			
		},
		batch: true,
        schema: {
            model: {
                id: 'boardId',
                fields: {
                	boardId: {
                		editable: false,
                		nullable: true
                	},
                	insertDate: {
                		type: 'date',
                		editable: false,
                		nullable: true
                	},
                	updateDate: {
                		type: 'date'
                	}
                	
                }
            },
//             parse: function(data){
//             	$.each(data,function(idx, element){
//             		element.insertDate = new Date(element.insertDate)
//             		element.updateDate = new Date(element.updateDate)
//             	});
//             	return data;
//             }
        }
	});
	
	// 그리드 설정
	grid = $('#boardList').kendoGrid({
		selectable: "multiple",
		editable: {
			confirmation: false
		},
		toolbar: kendo.template($('#toolbarTemplate').html()), 
		dataSource: ds,
		columns: [
// 			{ selectable: true, width: "50px" },
			{ field: 'boardId', title: '아이디'}, 
			{ field: 'boardTitle', title: '제목' }, 
			{ field: 'boardContent', title: '내용' }, 
// 			{ field: 'updateDate', title: '수정일', format: '{0:yyyy-MM-dd}',editor: gridEditorByDateInput },
			{ field: 'insertDate', title: '등록일', format: '{0:yyyy-MM-dd}'}
		],
		pageable: {
			refresh: true,
			pageSizes: true,
			buttonCount: 5
		},
		change: function(){
			/* 추후 데이터 소스 Util 생성시 참고 
			var selected = $.map(this.select(), function(item) {
				return $(item).text();
			});
			console.log("Selected: " + selected.length + " item(s), [" + selected.join(", ") + "]");
			*/
		}
	}).data('kendoGrid');

	function gridEditorByDateInput(container, options){
		$('<input required name="' + options.field + '"/>').appendTo(container)
			.kendoDatePicker({
				format: "yyyy-MM-dd"
			});
	}
	
	
	// 이벤트 설정
	grid.element.on('click','.toolbarCreate',function(){
		console.log('create')
		grid.addRow();
	})
	grid.element.on('click','.toolbarDelete',function(){
		console.log('delete')
		var selected = grid.select();
		selected.each(function(i,item){
			grid.removeRow(item);
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
}



</script>