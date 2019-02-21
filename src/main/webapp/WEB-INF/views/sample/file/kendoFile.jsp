<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

chuck 방식(적용할 방식) :: 
<input name="files" id="fileChunk" type="file" />
:::: ${boardId}
<div>
	<a href="/sys/file/download.do?fileId=15">15파일다운로드 샘플</a>
</div>
<script>
	$(function() {
		var data = {
			fileType: 01,
			fileGroupId: 02
		}
		// chunk 방식
		$('#fileChunk').kendoUpload({
			async : {
				chunkSize : 10280, // 10MB 제한
				saveUrl : "/sys/file/kendoUpload.do",
				removeUrl : "/sys/file/kendoDelete.do",
				autoUpload : true
			},
			localization: {
				select: "파일선택"
			},
			success: function(e){
				switch(e.operation){
					case 'upload':
						e.files.forEach(function(item,i){
							if(item.uid == e.response.fileUid){
								item.resp = e.response;
								return;
							}
						});
						break;
					case 'remove':
						console.log(e)
						break;
					default:
						break;
				}
			},
			upload: function(e){
// 				console.log('upload : ',e)
				e.data = data;
			},
			remove: function(e){
				// 업로드 후 저장한 값을 호출 - 단건식 보낸다는 가정.
				if(!e.hasOwnProperty('data')){
					e.data = {};
				}
				var fileId = e || e.files[0] || e.files[0].resp || e.files[0].resp.fileId || undefined;
				if(fileId){
					e.data.fileId = e.files[0].resp.fileId;
				}
				return;
			},
			error: function(e){
				console.log('error : ',e)
			},
			validation: {
//                 allowedExtensions: [".jpg"],
//                 maxFileSize: 900000,
//                 minFileSize: 300000
            }
		})
		
	})
</script>