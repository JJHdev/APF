/**
*******************************************************************************
***    명칭: modalQNAForm.jsp
***    설명: 운영관리-공지사항 화면
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.04.04    LSH        First Coding.
***    1.1      2023.06.27    J H        작업 완료.
*******************************************************************************
**/
$(function() {

    //========================================================//
    // 화면 스크립트 내에 사용할 객체,상수 정의
    //--------------------------------------------------------//
    let P_SU_FORM          	= $('#selectUsrForm'                           );		// 사용자 게시판 조회폼 객체
    let P_SU_PST_NO		    = $('#selectUsrForm input[name="pstNo"]'       ).val();	// 사용자 게시판 조회폼 객체
    let P_SU_BBS_CD		    = $('#selectUsrForm input[name="bbsSeCd"]'     ).val();	// 사용자 게시판 조회폼 객체
    let P_SU_PST_CLSF_CD    = $('#selectUsrForm input[name="pstClsfCd"]'   ).val();	// 사용자 게시판 조회폼 객체
	let P_SU_IS_NEXT	    = $('#selectUsrForm input[name="isNext"]'      ).val();	// 사용자 게시판 조회폼 객체   
	let P_SU_IS_BEFORE	    = $('#selectUsrForm input[name="isBefore"]'    ).val();	// 사용자 게시판 조회폼 객체
						    
	let P_SA_FORM          	= $('#registerAdmForm'                         );		// 관리자 게시판 조회폼 객체
    let P_SA_PST_NO		    = $('#registerAdmForm input[name="pstNo"]'     ).val();	// 관리자 게시판 조회폼 객체
    let P_SA_BBS_CD		    = $('#registerAdmForm input[name="bbsSeCd"]'   ).val();	// 관리자 게시판 조회폼 객체
    let P_SA_UP_PST_NO	    = $('#registerAdmForm input[name="upPstNo"]'   ).val();	// 관리자 게시판 조회폼 객체
	
    let P_CONTENT           = 'pstCn';                                              // 웹에디터 칼럼명
    let P_FILE_BOX          = $('#attachFile' );                                    // 첨부파일 컨트롤 객체
    let P_FILE_TYPE 		= CODE.FILE_TYPE.BBS; 									// 첨부파일 종류
    
    //========================================================//
    // 첨부파일 초기화 (app_bbsfile.js 참고)
    //--------------------------------------------------------//
    P_FILE_BOX.appBbsFile({
        label: false,
        // 처리모드
        mode: MODE.VIEW,
        initData: {
			fileSe  : CODE.FILE_SE.FILE,
            fileType: P_FILE_TYPE,
			docuCd	: BBS_TASK_CD.CODE,
            docuNo	: P_SU_PST_NO
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

	CKEDITOR_INIT(P_CONTENT);
    
    //========================================================//
    // VALIDATION RULE 정의
    //--------------------------------------------------------//
	P_SA_FORM.validate({
        // true일 경우 디버깅이 가능하도록 
        // 입력값이 유효해서 submit하지 않는다.
        debug: false,
        // true일 경우 포커스가 떠날때 유효성 검사를 한다.
        onfocusout: false,
        // true일 경우 유효성체크없이 무조건 submit한다.
        onsubmit: false,
        // 검증룰 정의
        rules	: {pstCn      	: {required			: true	}},
        // 검증메세지 정의
        messages: {pstCn        : {required 		: '내용은 필수 입력 사항입니다.'}},
        // 에러발생시 에러메세지를 처리할 핸들러 (comm_biz.js에 정의)
        invalidHandler: validateHandler,
        // 에러발생시 에러메세지를 표시할 위치처리 핸들러 (comm_biz.js에 정의)
        errorPlacement: validatePlacement
    });

	// 수정하기, 답변하기 클릭시 이벤트 처리
    //--------------------------------------------------------//
    function doSave() {
    	 // CKEDITOR 내용 업데이트
        CKEDITOR.instances[P_CONTENT].updateElement();
    	
    	// 등록폼의 VALIDATION 기능 활성화
        if (P_SA_FORM.validate().settings)
        	P_SA_FORM.validate().settings.ignore = false;
        //FORM VALIDATION
        if (P_SA_FORM.valid() === false)
            return false;
        
        let msg = "1:1문의에 대한 답변을 수정하시겠습니까?";
		if (P_SA_FORM.find('[name="mode"]').val() == MODE.INSERT)
			msg = "1:1문의에 대한 답변을 등록하시겠습니까?";
        
		$.commMsg.confirm(msg, function() {
            // 등록폼을 AJAX로 저장처리
			P_SA_FORM.ajaxForm({
                url: getUrl('/adm/inform/bbs/saveBbs.do'),
                enctype : 'multipart/form-data',
                data: {
                    'upPstNo'  : P_SU_PST_NO,
                    'pstClsfCd': P_SU_PST_CLSF_CD
                },
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
	
    // 다음 게시물 이동
    //--------------------------------------------------------//
    function goNext() {
    	if(P_SU_IS_NEXT=='Y'){
    		// 조회폼 데이터 객체화
    		let params = $('#selectUsrForm').serializeObject();
    		// 버튼타입 조건값 추가
    		$.extend(params, { movePage : "next"});
    		// 부모창의 doOpenSelect를 호출하여 이동한다.
    		P_MODAL.doOpenSelect(params);
    	}else{
    		// 조회폼 데이터 객체화
    		let params = $('#selectUsrForm').serializeObject();
    		// 버튼타입 조건값 추가
    		$.extend(params, { movePage : "next"});
    		// 부모창의 doOpenSelect를 호출하여 이동한다.
    		P_MODAL.doOpenUpdate(params);
    	}
        return false;
    }
    // 이전 게시물 이동
    //--------------------------------------------------------//
    function goBefore() {
    	if(P_SU_IS_BEFORE=='Y'){
    		// 조회폼 데이터 객체화
    		let params = $('#selectUsrForm').serializeObject();
    		// 버튼타입 조건값 추가
    		$.extend(params, { movePage : "before"});
    		// 부모창의 doOpenSelect를 호출하여 이동한다.
    		P_MODAL.doOpenSelect(params);
    	}else{
    		// 조회폼 데이터 객체화
    		let params = $('#selectUsrForm').serializeObject();
    		// 버튼타입 조건값 추가
    		$.extend(params, { movePage : "before"});
    		// 부모창의 doOpenSelect를 호출하여 이동한다.
    		P_MODAL.doOpenUpdate(params);
    	}
        return false;
    }
    
    // 취소버튼 클릭시 이벤트처리
    //--------------------------------------------------------//
    function doCancel() {
		// 부모창의 doClose 호출
		P_MODAL.doClose();
        return false;
	}
    
    // 이전버튼 클릭시 이벤트처리
    $('#btnNext  ').bind('click', goNext);
    // 다음버튼 클릭시 이벤트처리
    $('#btnBefore').bind('click', goBefore);
    // 이전버튼 클릭시 이벤트처리
    $('#btnCancel ').bind('click', doCancel);
    // 수정및 답변버튼 클릭시 이벤트처리
    $('#btnSave').bind('click', doSave);
});
