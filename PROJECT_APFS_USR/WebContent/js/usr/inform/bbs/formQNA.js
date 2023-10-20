/**
*******************************************************************************
***    명칭: formQNA.js
***    설명: 정보서비스 - 1:1문의 화면
***
***    -----------------------------    Modified Log   ------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.06.07    J H        First Coding.
***    1.1      2023.06.28    J H        작업완료.
*******************************************************************************
**/
$(function() {
	 //========================================================//
    // 화면 스크립트 내에 사용할 객체,상수 정의
    //--------------------------------------------------------//
    let P_FILE_TYPE = CODE.FILE_TYPE.BBS; // 첨부파일 종류
	let P_FILE_BOX  = $('#attachFile' );  // 첨부파일 컨트롤 객체
    let P_RFORM     = $('#registForm' );  // 등록폼 객체
    let P_MODE      = $('#mode'   ).val();// 처리 모드
    let P_PST_NO    = $('#pstNo'  ).val();// 게시글 키정보
    let P_BBS_CD    = $('#bbsSeCd').val();// 게시판 구분코드
    let P_CONTENT   = 'pstCn';            // 웹에디터 칼럼명
    
    if($('#isNext').val() != 'comple'){
    	//========================================================//
        // 첨부파일 초기화 (app_bbsfile.js 참고)
        //--------------------------------------------------------//
    	P_FILE_BOX.appBbsFile({
    		mode: P_MODE,    // 처리모드
    		initCount: 1,    // 초기 표시 갯수
    		maxCount:  3,    // 추가 최대 갯수
    		multiple: true,  // 다중 가능 여부
    		message: '※ 100MB 이내 3개 파일 가능, 여러 파일 업로드 희망 시 압축파일 권장',  // 하단 메세지
    		initData: { // 파일 조건 데이터
    			fileSe: '01',
    			fileType: P_FILE_TYPE,
    			docuCd:   P_BBS_CD,
    			docuNo:   P_PST_NO
    		}  
    	}).appBbsFile('init');
    	
    	//========================================================//
    	// 웹에디터 정의 (ckeditor)
    	//--------------------------------------------------------//
    	// CKEDITOR 초기화 (comm_ckeditor.js 참고)
    	// 개인적인 설정
    	const BBS_NOTIMAGE_CKEDITOR_CONFIG = {
    		removePlugins: 'image,image2,flash,cut,copy,paste,pastetext,pastefromword,redo,link,unlink,anchor,pagebreak,iframe',
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
    	const FINAL_CONFIG = Object.assign({}, CKEDITOR_CONFIG, BBS_NOTIMAGE_CKEDITOR_CONFIG);
    	
    	// CKEDITOR 초기화 함수
    	const CKEDITOR_INIT = function( id ) {
    		if (CKEDITOR) {
    		    // CKEDITOR 설정바인딩
    			CKEDITOR.replace(id, FINAL_CONFIG);
    			
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
        // 콤보박스 정의
        //--------------------------------------------------------//
    	// 분류 구분
    	$('#pstClsfCd').appDropdownBox({
    		title   : '문의유형<em></em>',
    		iconCls : 'icon-edit',
    		input   : {id: 'pstClsfCd', name: 'pstClsfCd', value: ''},
    		params  : {upCdId: CODE.INQRY_CL.code},
    		callback: function(){
    			var dropdownId = "pstClsfCd";
    			var selectedValue = $('#pstClsfCd').val();
    			var $dropdown = $('#' + dropdownId);
    			
    			$dropdown.find('.dropdown-item').each(function() {
    				var $item = $(this);
    				if ($item.attr('data-value') === selectedValue) {
    					$item.addClass('active');
    					$dropdown.find('.dropdown-toggle').text($item.text());
    				} else {
    					$item.removeClass('active');
    				}
    			});
    		}
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
    	        	pstClsfCd    	: {required		: true},
            		pstTtl    	 	: {required		: true,
            						   maxlength	: 80},
            },
            // 검증메세지 정의
            messages: {
            		pstClsfCd      : {required 		: '문의유형은 필수 입력 사항입니다.'},
                	pstTtl         : {required 		: '제목은 필수 입력 사항입니다.',
                					  maxlength		: jQuery.validator.format('제목은 최대 {0}자 이하 입니다.')},
            },
            
            // 에러발생시 에러메세지를 처리할 핸들러 (comm_biz.js에 정의)
            invalidHandler: validateHandler,
            // 에러발생시 에러메세지를 표시할 위치처리 핸들러 (comm_biz.js에 정의)
            errorPlacement: validatePlacement
        });

        // 저장버튼 클릭시 이벤트 처리
        //--------------------------------------------------------//
        $('#btnSave').bind('click', function() {
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

    		let msg = "1:1문의 내용를 등록하시겠습니까?";
        	$.commMsg.confirm(msg, function() {
                // 등록폼을 AJAX로 저장처리
                P_RFORM.ajaxForm({
                    url: getUrl('/usr/inform/bbs/saveQNA.do'),
                    enctype : 'multipart/form-data',
                    // 오류시 처리로직
                    error: $.ajaxUtil.error,
                    // 저장후 처리로직
                    success: function(ret) {
                    	$.ajaxUtil.success(ret, function(data) {
                            // 목록으로 이동
                    		$.formUtil.submitForm(
                				getUrl('/usr/inform/bbs/formQNA.do'), {
                					params: {isNext: 'comple'}
                				}
                			);
                			return false;
                    	});
                    }
                }).submit();
        	});
            return false;
    	});
    }else{
    	$('#btnMainPage').bind('click', function() {
    		goUrl(getUrl('/usr/main/main.do'));
    	});
    	$('#btnMyPage').bind('click', function() {
    		goUrl(getUrl('/usr/mypage/bbs/openBbs.do'));
    	});
    }
});
