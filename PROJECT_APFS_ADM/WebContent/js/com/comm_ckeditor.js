/**
******************************************************************************************
*** 파일명    : comm_ckeditor.js
*** 설명      : CKEditor 관련 공통 설정 스크립트
***
*** -----------------------------    Modified Log   --------------------------------------
*** ver             date                author                  description
*** --------------------------------------------------------------------------------------
*** 1.0         2021-07-21              LSH
*** 1.1         2022-10-26              ntarget		     CkEditorImageUpload에서 처리 (URL)
******************************************************************************************
**/
const CKEDITOR_CONFIG = {
	// 파일 업로드를 처리 할 경로 설정(CK필터).
	//filebrowserImageUploadUrl: ROOT_PATH+'/images/ckimage' 
	filebrowserUploadUrl: ROOT_PATH+'/com/common/ckupload.do',
};

// CKEDITOR 초기화 함수
const CKEDITOR_INIT = function( id ) {
	if (CKEDITOR) {
	    // CKEDITOR 설정바인딩
	    CKEDITOR.replace(id, CKEDITOR_CONFIG);
		
	    // 2022.10.26 ntarget 추가 (이미지업로드 탭 정의)
	    CKEDITOR.on('dialogDefinition', function (ev) {
	        var dialogName = ev.data.name;
	        var dialog = ev.data.definition.dialog;
	        var dialogDefinition = ev.data.definition;
	
	        if (dialogName == 'image') {
	            dialog.on('show', function (obj) {
	                this.selectPage('Upload'); //업로드탭으로 시작
	            });
	            
	            dialogDefinition.removeContents('advanced'); // 자세히탭 제거
	            dialogDefinition.removeContents('Link'); // 링크탭 제거
	        }
	    });
	}
};
