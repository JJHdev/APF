/**
*******************************************************************************
***    명칭: modalInvestCaseForm.jsp
***    설명: 게시판관리-우수투자사례 화면
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.06.09    JH        First Coding.
***    1.1      2023.06.27    JH        작업 완료.
*******************************************************************************
**/
$(function() {
	
    //========================================================//
    // 화면 스크립트 내에 사용할 객체,상수 정의
    //--------------------------------------------------------//
    let P_FILE_TYPE      = CODE.FILE_TYPE.BBS;          // 첨부파일 종류
	let P_FILE_BOX       = $('#attachFile'    );        // 첨부파일 컨트롤 객체
	let P_THUM_FILE_BOX  = $('#thumAttachFile');        // 썸네일 첨부파일 컨트롤 객체
    let P_RFORM          = $('#registForm'    );        // 등록폼 객체
    let P_MODE           = $('#mode'          ).val();  // 처리 모드
    let P_PST_NO         = $('#pstNo'         ).val();  // 게시글 키정보
    let P_BBS_CD         = $('#bbsSeCd'       ).val();  // 게시판 구분코드
	let P_CONTENT        = 'pstCn';                     // 웹에디터 칼럼명

    //========================================================//
    // 첨부파일 초기화 (app_bbsfile.js 참고)
    //--------------------------------------------------------//
	P_THUM_FILE_BOX.appBbsFile({
		initCount: 1,
		label: false,
		// 처리모드
		mode: P_MODE,  // 처리모드
		maxLengthName:150,
		extensions : COMMONS.IMAGE_NOT_PDF_EXTENSIONS,
		initData: {
			fileSe	: CODE.FILE_SE.IMGE,
			fileType: P_FILE_TYPE,
			docuCd	: BBS_TASK_CD.CODE,
			docuNo	: P_PST_NO
		}
	}).appBbsFile('init');
	
	P_FILE_BOX.appBbsFile({
		maxCount:  3, 
		initCount: 1,
		multiple: true,
		label: false,
		// 처리모드
		mode: P_MODE,  // 처리모드
		maxLengthName:150,
		extensions : COMMONS.IMAGE_NOT_PDF_EXTENSIONS,
		initData: {
			fileSe	: CODE.FILE_SE.FILE,
			fileType: P_FILE_TYPE,
			docuCd	: BBS_TASK_CD.CODE,
			docuNo	: P_PST_NO
		}
	}).appBbsFile('init');
	

	//========================================================//
	// 웹에디터 정의 (ckeditor)
	//--------------------------------------------------------//
	// CKEDITOR 초기화 (comm_ckeditor.js 참고)
	// 개인적인 설정
	const BBS_CKEDITOR_CONFIG = {
	    extraPlugins: 'wordcount',
	    wordcount: {
	        showParagraphs: true,
	        showWordCount: true,
	        showCharCount: true,
	        countSpacesAsChars: true,
	        countHTML: false,
	        maxWordCount: -1,
	        maxCharCount: 20000,
	    }
	};

	// 공통 설정과 개인적인 설정을 병합
	const FINAL_CONFIG = Object.assign({}, CKEDITOR_CONFIG, BBS_CKEDITOR_CONFIG);
	
	// CKEDITOR 초기화 함수
	const CKEDITOR_INIT = function( id ) {
		if (CKEDITOR) {
		    // CKEDITOR 설정바인딩
			CKEDITOR.replace(id, FINAL_CONFIG);
			
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

	$(function() {
		CKEDITOR_INIT(P_CONTENT);
	});
	
    //========================================================//
    // VALIDATION RULE 정의
    //--------------------------------------------------------//
    P_RFORM.validate({
        // true일 경우 디버깅이 가능하도록 
        // 입력값이 유효해서 submit하지 않는다.
        debug: false,
        // true일 경우 포커스가 떠날때 유효성 검사를 한다.
        onfocusout: false,
        // true일 경우 유효성체크없이 무조건 submit한다.
        onsubmit: false,
        // 검증룰 정의
        rules: {
            pstTtl     		: {required : true,
    					       maxlength: 70},
        },
        // 검증메세지 정의
        messages: {
            pstTtl         : {required: '제목은 필수 입력 사항입니다.',
  			  				 maxlength: jQuery.validator.format('제목은 최대 {0}자 이하 입니다.')},
        },
        // 에러발생시 에러메세지를 처리할 핸들러 (comm_biz.js에 정의)
        invalidHandler: validateHandler,
        // 에러발생시 에러메세지를 표시할 위치처리 핸들러 (comm_biz.js에 정의)
        errorPlacement: validatePlacement
    });

    // 저장버튼 클릭시 이벤트 처리
    //--------------------------------------------------------//
    $('#btnSave').bind('click', function() {
    	// 실제 존재하는 YouTube 영상인지 유효성검사
	let P_PST_LINK_URL   = $('#pstLinkUrl').val();

	var videoId = P_PST_LINK_URL.split('v=')[1];
	var apiKey = YOUTUBE.API_KEY 
	var url = 'https://www.googleapis.com/youtube/v3/videos?id=' + videoId + '&key=' + apiKey + '&part=status';
	
	  $.get(url, function(data) {
		  
		  if (P_PST_LINK_URL.trim() !== '') {
		        var videoId = P_PST_LINK_URL.split('v=')[1];
		        var apiKey = YOUTUBE.API_KEY 
		        var url = 'https://www.googleapis.com/youtube/v3/videos?id=' + videoId + '&key=' + apiKey + '&part=status';
		        
		        $.get(url, function(data) {
		            if (data.pageInfo.totalResults == 0) {              
		                $.commMsg.alert('해당하는 영상이 없습니다.');              
		            } else {
		            	// CKEDITOR 내용 업데이트
				        CKEDITOR.instances[P_CONTENT].updateElement();

				        // 등록폼의 VALIDATION 기능 활성화
				        if (P_RFORM.validate().settings)
				            P_RFORM.validate().settings.ignore = false;

				        //FORM VALIDATION
				        if (P_RFORM.valid() === false)
				            return false;

						// 첨부파일 업로드 VALIDATION (app_bbsfile.js)
						if (P_FILE_BOX.appBbsFile('validate', {
								isAlert: true,
								isExt:   true,
								isLimit: true
							}) === false)
				            return false;
						
						// 썸네일 첨부파일 업로드 VALIDATION (app_bbsfile.js)
						if (P_THUM_FILE_BOX.appBbsFile('validate', {
								isAlert: true,
								isExt:   true,
								isLimit: true
							}) === false)
				            return false;
						
						let msg = "우수투자사례 글을 수정하시겠습니까?";
						if (P_RFORM.find('[name="mode"]').val() == MODE.INSERT)
							msg = "우수투자사례 글을 등록하시겠습니까?";
					
				    	$.commMsg.confirm(msg, function() {
				            // 등록폼을 AJAX로 저장처리
				            P_RFORM.ajaxForm({
				                url: getUrl('/adm/inform/bbs/saveBbs.do'),
				                enctype : 'multipart/form-data',
				                // 오류시 처리로직
				                error: $.ajaxUtil.error,
				                // 저장후 처리로직
				                success: function(ret) {
				                	
				                	$.ajaxUtil.success(ret, function(data) {
				                        // 목록으로 이동
				                    	$.commMsg.alert('성공적으로 저장되었습니다.', function() {
											// 부모창의 목록재검색 및 팝업닫기
											P_MODAL.doSearch();
											return false;
										});
				                	});
				                }
				            }).submit();
				    	});
				        return false;
		            }
		        });
		    } else {
		    	// CKEDITOR 내용 업데이트
		        CKEDITOR.instances[P_CONTENT].updateElement();

		        // 등록폼의 VALIDATION 기능 활성화
		        if (P_RFORM.validate().settings)
		            P_RFORM.validate().settings.ignore = false;

		        //FORM VALIDATION
		        if (P_RFORM.valid() === false)
		            return false;

				// 첨부파일 업로드 VALIDATION (app_bbsfile.js)
				if (P_FILE_BOX.appBbsFile('validate', {
						isAlert: true,
						isExt:   true,
						isLimit: true
					}) === false)
		            return false;
				
				// 썸네일 첨부파일 업로드 VALIDATION (app_bbsfile.js)
				if (P_THUM_FILE_BOX.appBbsFile('validate', {
						isAlert: true,
						isExt:   true,
						isLimit: true
					}) === false)
		            return false;
				
				let msg = "우수투자사례 글을 수정하시겠습니까?";
				if (P_RFORM.find('[name="mode"]').val() == MODE.INSERT)
					msg = "우수투자사례 글을 등록하시겠습니까?";
			
		    	$.commMsg.confirm(msg, function() {
		            // 등록폼을 AJAX로 저장처리
		            P_RFORM.ajaxForm({
		                url: getUrl('/adm/inform/bbs/saveBbs.do'),
		                enctype : 'multipart/form-data',
		                // 오류시 처리로직
		                error: $.ajaxUtil.error,
		                // 저장후 처리로직
		                success: function(ret) {
		                	
		                	$.ajaxUtil.success(ret, function(data) {
		                        // 목록으로 이동
		                    	$.commMsg.alert('성공적으로 저장되었습니다.', function() {
									// 부모창의 목록재검색 및 팝업닫기
									P_MODAL.doSearch();
									return false;
								});
		                	});
		                }
		            }).submit();
		    	});
		        return false;
		    }
	  });
	});
    
    // 취소버튼 클릭시 이벤트처리
    //--------------------------------------------------------//
    function doCancel() {
	  	// 부모창의 doClose 호출
	  	P_MODAL.doClose();
	  	return false;
    }
  
  // 이전버튼 클릭시 이벤트처리
  $('#btnCancel ').bind('click', doCancel);
	
});

