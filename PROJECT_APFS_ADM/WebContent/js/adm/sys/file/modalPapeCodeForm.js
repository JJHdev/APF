/**
*******************************************************************************
***    명칭: modalPapeCodeForm.js
***    설명: 운영관리-제출서류관리 화면
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***   1.0         2023.06.21    LSH   First Coding.
***   1.1         2023.07.21    J H   작업완료
***   1.1    	  2023.08.04    JH    파일 경로이동및 다운로드기능 추가.
*******************************************************************************
**/
$(function() {
    //========================================================//
    // 화면 스크립트 내에 사용할 객체,상수 정의
    //--------------------------------------------------------//
	let P_FILE_TYPE 			= CODE.FILE_TYPE.FRM; 				// 첨부파일 종류
	var P_BZFLD	   				= false; 							// 사업분야 체크박스 객체
	var P_RFORM					= $('#registForm'	);				// 등록폼 객체
	var P_MODE					= $('#mode'      	).val();		// 처리 모드
	let P_FILE_BOX  			= $('#attachFile' 	);  			// 첨부파일 컨트롤 객체
	let P_FILE_BOX2  			= $('#attachFile2' 	);  			// 첨부파일 컨트롤 객체
	let P_DCMNT_CD    			= $('#dcmntCd'  	).val();		// 게시글 키정보
	let P_UP_DCMNT_CD  			= $('#upDcmntCd'	).val();		// 게시판 구분코드
	
    //========================================================//
    // 첨부파일 초기화 (app_bbsfile.js 참고)
    //--------------------------------------------------------//
	P_FILE_BOX.appPapeFile({
		mode: P_MODE,    // 처리모드
		initCount: 1,    // 초기 표시 갯수
		multiple: false,  // 다중 가능 여부
		message: '※500MB 이내 여러 파일 업로드 희망 시 압축파일 권장',  // 하단 메세지
		initData: { // 파일 조건 데이터
			fileSe		: CODE.FILE_SE.FILE,
			fileType	: P_FILE_TYPE,
			docuCd  	: P_UP_DCMNT_CD,
			docuNo  	: P_DCMNT_CD,
		}  
	}).appPapeFile('init');
	
	P_FILE_BOX2.appPapeFile({
        label: false,
        // 처리모드
        mode: MODE.VIEW,
        initData: {
			fileSe		: CODE.FILE_SE.FILE,
			fileType	: P_FILE_TYPE,
			docuCd  	: P_UP_DCMNT_CD,
			docuNo  	: P_DCMNT_CD,
        }
    }).appPapeFile('init');
	
	//========================================================//
    // FORM ELEMENTS 정의
    //--------------------------------------------------------//
	$('#upDcmntCd').appComboBox({
		url : getUrl('/com/common/getComboPape.do'),
		params: {upDcmntCd : 'NONE'},
		callback: function(){
			if(P_RFORM.find('[name="mode"]').val() == MODE.UPDATE){
				$('#dcmntCd'	).prop("readonly", true);
				$("#upDcmntCd"	).css("pointer-events", "none");
			}
		},
		change : function(data){
			$('#cdOrdr').val('');
		},
		loadFilter : function(data) {
			if(P_RFORM.find('[name="mode"]').val() != MODE.INSERT){
				data.unshift(ROOT_CODE);
			}
			return data;
		},
	});
	
    // 등록용 사용여부 라디오박스	
	$('#appUseYn').appSelectBox({
		form: 'radio',
		name: 'useYn',
		type: 'static',
		rows: STORE['USE_YN']
	});
	
	// 등록용 사용여부 라디오박스	
	$('#appDwnldTrgtYn').appSelectBox({
		form: 'radio',
		name: 'dwnldTrgtYn',
		type: 'static',
		rows: STORE['DOWNTRGT_YN']
	});
	
	//========================================================//
    // 등록폼 VALIDATION RULE 정의
    //--------------------------------------------------------//
	P_RFORM.validate({
        debug: false,
        onsubmit: false,
        onfocusout: false,
        // 검증룰 정의
        rules: {
        	upDcmntCd    	: {required			: true	},
            dcmntCd      	: {required			: true	,
				 			   maxlength		: 10	},
		    dcmntNm    		: {required			: true	,
		 		 			   maxlength		: 300	},
 			cdOrdr    		: {maxlength		: 5		},
            useYn     		: {required			: true	},
            dwnldTrgtYn     : {required			: true	}
        },
        // 검증메세지 정의
        messages: {
        	upDcmntCd    	: {required			: '상위서류ID는 필수 입력 사항입니다.'								},
        	dcmntCd    		: {required			: '서류코드ID는 필수 입력 사항입니다.'								,
		  			 		   maxlength		: jQuery.validator.format('서류코드ID는 최대 {0}자 이하 입니다.' )},
	 		dcmntNm    		: {required 		: '서류코드명은 필수 입력 사항입니다.'							 	,
	 						   maxlength		: jQuery.validator.format('서류코드명은 최대 {0}자 이하 입니다.' )},
		    cdOrdr    		: {maxlength		: jQuery.validator.format('서류코드순서는 최대 {0}자 이하 입니다.')},
		    useYn     		: {required			: '사용여부는 필수 선택 사항입니다.'								},
		    dwnldTrgtYn     : {required			: '다운로드 대상여부는 필수 선택 사항입니다.'							}
        },
        invalidHandler: validateHandler,
        errorPlacement: validatePlacement
    });
	// 숫자만입력
	bindOnlyNumber($("#cdOrdr"));
	
	// 코드관리 저장하기
    //--------------------------------------------------------//
    function doSave() {

        // 등록폼의 VALIDATION 기능 활성화
        if (P_RFORM.validate().settings)
            P_RFORM.validate().settings.ignore = false;

        //FORM VALIDATION
        if (P_RFORM.valid() === false)
            return false;
        
		// 첨부파일 업로드 VALIDATION (app_bbsfile.js)
		if (P_FILE_BOX.appPapeFile('validate', {
				isAlert: true,
				isExt:   true,
				isLimit: true
			}) === false)
            return false;
        
        let msg = "공통서류 코드를 수정하시겠습니까?";
		if (P_RFORM.find('[name="mode"]').val() == MODE.INSERT)
			msg = "공통서류 코드를 등록하시겠습니까?";
		
        $.commMsg.confirm(msg, function() {
            // 등록폼을 AJAX로 저장처리
            P_RFORM.ajaxForm({
                url: getUrl('/adm/sys/file/savePapeCode.do'), 
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
    
    // 코드관리 삭제하기
    //--------------------------------------------------------//
    function doRemove() {
    	var message = "정말로 삭제하시겠습니까?";
    	
    	if(P_RFORM.find('[name="upDcmntCd"]').val() == ROOT_CODE.code){
    		message = '최상위 코드 삭제시 하위코드까지 삭제 됩니다. 정말로 삭제하시겠습니까?';
    	}
        $.commMsg.confirm(message, function() {
			$.formUtil.toForm({
				mode     : MODE.REMOVE, // 삭제모드
			}, P_RFORM);
			
			// 등록폼을 AJAX로 저장처리
            P_RFORM.ajaxForm({
                url: getUrl('/adm/sys/file/savePapeCode.do'),
                // 오류시 처리로직
                error: $.ajaxUtil.error,
                // 저장후 처리로직
                success: function(ret) {
					$.commMsg.success(ret, '성공적으로 삭제되었습니다.', function() {
                        P_MODAL.doSearch();
					});
                }
            }).submit();
        });
        return false;
    }
    
    // 취소버튼 클릭시 이벤트처리
    //--------------------------------------------------------//
    $('#btnCancel').bind('click', function() {
		// 부모창의 doClose 호출
		P_MODAL.doClose();
        return false;
	});
    // 저장버튼 클릭시 이벤트 처리
    $('#btnSave'  ).bind('click', doSave);
	// 삭제버튼 클릭시 이벤트 처리
    $('#btnRemove').bind('click', doRemove);
});