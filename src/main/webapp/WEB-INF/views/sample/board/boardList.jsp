<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div id="boardList"></div>

<script type="text/x-kendo-template" id="toolbarTemplate">
<div class="toolbar">
	<button type="button" id="create">create</button>
	<button type="button" id="destroy">destroy</button>
</div>
</script>
<script>
$(function(){
	boardGridInit();
	eventInit();
})

function boardGridInit(){
	$('#boardList').kendoGrid({
		selectable: true,
		persistSelection: true,
		editable: 'popup',
		toolbar: kendo.template($('#toolbarTemplate').html()),
		dataSource: new kendo.data.DataSource({
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
	                fields:{
	                	"insertDate" : {
	                		type: "date"
	                	} 
	                }
	                
	            },
// 	            parse: function(data){
// 	            	$.each(data,function(idx, element){
// 	            		element.insertDate = new Date(element.insertDate)
// 	            		element.updateDate = new Date(element.updateDate)
// 	            	});
// 	            	return data;
// 	            }
	        }
		}),
		columns: [{ 
// 			selectable: true, width: "50px"
			title: 'check',
			headerTemplate: '<input type="checkbox" id="header-chb" class="k-checkbox header-checkbox"><label class="k-checkbox-label" for="header-chb"></label>',
			template: function(item){
				return '<input type="checkbox" id="'+item.id+'" class="k-checkbox row-checkbox"><label class="k-checkbox-label" for="'+item.id+'"></label>';
			},
			width: '50px'
		},{
			field: 'boardId',
			title: '아이디'
		}, {
			field: 'boardTitle',
			title: '제목'
		}, {
			field: 'insertDate',
			title: '등록일',
			format: '{0:yyyy-MM-dd}'
		}],
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
	});
}
// var checkedIds = {};
function eventInit(){
	var _grid = $('#boardList').data('kendoGrid')
	// 그리드 더블 클릭 이벤트.
	_grid.element.on('dblclick','tbody tr[data-uid] td',function(e){
		var target = e.target
		if($(target).children('input[type=checkbox]').length > 0){
			abc = e;
			return;
		}
		var uid = $(e.target.closest('tr')).data('uid');
// 		var uid = $(this).data('uid');
		createDetailPop(uid);
	})
	
	// 그리드 체크박스 이벤트
	_grid.table.on('click','.row-checkbox',function(){
		var checked = this.checked;
		console.log('checked :: ',checked)
        var row = $(this).closest("tr");
        var grid = $("#boardList").data("kendoGrid");
        var dataItem = grid.dataItem(row);

// 	    checkedIds[dataItem.id] = checked;
	
	    if (checked) {
	        //-select the row
	        row.addClass("k-state-selected");
	
	        var checkHeader = true;
	
	        $.each(grid.items(), function (index, item) {
	            if (!($(item).hasClass("k-state-selected"))) {
	                checkHeader = false;
	            }
	        });
	
	        $("#header-chb").get(0).checked = checkHeader;
	    } else {
	        //-remove selection
	        row.removeClass("k-state-selected");
	        $("#header-chb")[0].checked = false;
	    }

	});
	
	$('#boardList').on('change','#header-chb',function(e){
		var checked = e.target.checked;
		$('.row-checkbox').each(function(idx, item){
			if(checked){
				item.checked = true
			}else{
				item.checked = false
			}
		});
	});
	
	
	// toolbar 이벤트.
	$('#boardList').on('click','#create',function(e){
		console.log('create click : ',e);
		confirm('생성하시겠습니까?')
		var row = _grid.dataSource.add();
		createDetailPop(row.uid);
	})
	$('#boardList').on('click','#update',function(e){
		var item = _grid.dataItem(_grid.select())
		if(item && item.uid){
			console.log(true,item.uid)
			createDetailPop(uid);
		}else{
			console.log(false)
		}
	})
	$('#boardList').on('click','#destroy',function(e){
		if(!confirm('삭제 하시겠습니까?')) return;
		var ds = $('#boardList').data('kendoGrid').dataSource;
		$('.row-checkbox').each(function(idx, item){
			if(item.checked){
				var row = ds.get(item.id);
				console.log('del row : ',row);
				ds.remove(row);
			}
		});
		ds.sync().done(function(){
			alert('성공.')
		}).fail(function(){
			alert('실패.')
		});
		
	})
}
// 추후 공통으로 변경
function createDetailPop(uid){
	// 추후 ID 생성기 제작
	var popId = 'pop';
	if(uid){
// 		popId +='-'+uid
	}
	var url = '/sample/board/boardDetail.do';
	var data = {uid: uid};
	
	// as soon as delete source : test soruce... 
// 	data.insertDate = new Date();
	
	if($('#'+popId).length<=0){
		$('body').append('<div id="'+popId+'"></div>');
	}
	$_pop = $('#'+popId);
	if(!$_pop.data('kendoWindow')){
		$_pop.data('uid',uid);
		$_pop.kendoWindow({
			content: {
				url: url,
				data: data
			},
			modal: true,
			close: function(e){
				var uid = this.element.data('uid');
				if(uid){
					var row = findByUid(uid);
					// 추후 grrid 또는 dataSource parameter로 받아서 처리
					var ds = $('#boardList').data('kendoGrid').dataSource;
					console.log(row.dirty)
					if(row.dirty){
						ds.cancelChanges(row);
						row.set('uid',uid);
					}else {
						var createdDs = ds.created();
						if(createdDs && createdDs.length > 0){
							console.log(createdDs.indexOf(row))
							if(createdDs.indexOf(row) > -1){
								ds.cancelChanges(row)
							}
						}
					}
				}
			}
		})
	}else{
		$_pop.data('uid',uid);
		$_pop.data('kendoWindow').refresh({
			url: url,
			data: data
		})
	}
	$_pop.data('kendoWindow').center().open();
}

// id로 찾기.
function findByUid(uid){
	return $('#boardList').data('kendoGrid').dataSource.getByUid(uid);
}


</script>