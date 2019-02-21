<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <script src="/web/lib/template/vendor/jquery/jQuery.MultiFile.min.js"></script>

 <script>
	 $(function(){


    //use jQuery MultiFile Plugin 
    $('#multiform input[name=file]').MultiFile({
        max: 5, //업로드 최대 파일 갯수 (지정하지 않으면 무한대)
        accept: 'jpg|png|gif', //허용할 확장자(지정하지 않으면 모든 확장자 허용)
        maxfile: 5024, //각 파일 최대 업로드 크기
        maxsize: 10024,  //전체 파일 최대 업로드 크기
        STRING: { //Multi-lingual support : 메시지 수정 가능
            remove : "제거", //추가한 파일 제거 문구, 이미태그를 사용하면 이미지사용가능
            duplicate : "$file 은 이미 선택된 파일입니다.", 
            denied : "$ext 는(은) 업로드 할수 없는 파일확장자입니다.",
            selected:'$file 을 선택했습니다.', 
            toomuch: "업로드할 수 있는 최대크기를 초과하였습니다.($size)", 
            toomany: "업로드할 수 있는 최대 갯수는 $max개 입니다.",
            toobig: "$file 은 크기가 매우 큽니다. (max $size)"
        },
        list:"#afile3-list" //파일목록을 출력할 요소 지정가능
    });
	 });
</script>
</head>

<form id="multiform" action="fileUpload" method="post" enctype="multipart/form-data">
        <input type="file" id="fileUp" name="file"/>
        <div id="afile3-list"></div> 


        <br/><br/>
        <input type="button" value="전송하기" onClick="fileSubmit();">
</form>




<script>
function fileSubmit() {
    var formData = new FormData($("#multiform")[0]);
    $.ajax({
        type : 'post',
        url : '/sys/file/fileUpload.do',
        data : formData,
        processData : false,
        contentType : false,
        dataType : "text",
        success : function (e) {
    		console.log(e);
            alert("파일 업로드하였습니다.");
        },
        error : function(error) {
            alert("파일 업로드에 실패하였습니다.");
            console.log(error);
            console.log(error.status);
        }
    });
}


</script>