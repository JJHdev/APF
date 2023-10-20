/**
*******************************************************************************
***    명칭: modalSubmitFielForm.js
***    설명: 운영관리-제출서류관리 화면
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.06.26    JH        First Coding.
***    1.1      2023.06.27    JH        작업 완료.
*******************************************************************************
**/
$(function() {
    //========================================================//
    // 화면 스크립트 내에 사용할 객체,상수 정의
    //--------------------------------------------------------//
	var P_BZFLD	   				= false; 										// 사업분야 체크박스 객체
	var P_RFORM					= $('#registForm'	);							// 등록폼 객체
	var P_MODE					= $('#mode'      	).val();					// 처리 모드
	var P_MODE_STYS				= $('#sysCd').data('value');	// 처리 모드
	//========================================================//
    // FORM ELEMENTS 정의
    //--------------------------------------------------------//
	if(P_RFORM.find('[name="mode"]').val() == MODE.INSERT){
		P_MODE_STYS="ADM"
	}
	
	$('#sysCd').appComboBox({
		params: {upCdId: CODE.SYS_SE.code},
		loadFilter : function(data) {
			return data;
		},
		change : function() {
			var value = $(this).val();
			$('#menuId').appComboBox({
				url : getUrl('/com/common/getComboMenu.do'),
				params : {
					sysCd : value,
					underNotMenuId: 'NONE'
				},
				loadFilter : function(data2) {
					return data2;
				},
			});
		},
		callback: function(){
			$('#menuId').appComboBox({
				url : getUrl('/com/common/getComboMenu.do'),
				params : {sysCd : P_MODE_STYS,
						  underNotMenuId: 'NONE'},
				callback: function(){
					if(P_RFORM.find('[name="mode"]').val() == MODE.UPDATE){
						$('#prgrmId'	).prop("readonly", true);
					}
				},
				loadFilter : function(data2) {
					data2.unshift(COMBO.INIT_SELECT);
					return data2;
				},
			});
		},
	});
	
    // 등록용 사용여부 라디오박스	
	$('#appUseYn').appSelectBox({
		form: 'radio',
		name: 'useYn',
		type: 'static',
		rows: STORE['USE_YN']
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
            prgrmId      		: 	{required			: true	,
			  	   				 	 maxlength			: 20	},
            prgrmNm    			: 	{required			: true	,
	   				 			 	 maxlength			: 300	},
            prgrmUrl     		: 	{required			: true	,
		 			 			 	 maxlength			: 500	},
            prgrmOrdr      		: 	{maxlength			: 5		},
            menuId     			: 	{required			: true	},
            useYn     			: 	{required			: true	},
        },
        // 검증메세지 정의
        messages: {
        	prgrmId      		: {required 		: '프로그램ID는 필수 입력 사항입니다.'							,
            				   	   maxlength		: jQuery.validator.format('프로그램ID는 최대 {0}자 이하 입니다.')},
		    prgrmNm    			: {required 		: '프로그램명은 필수 입력 사항입니다.'							,
			   	   				   maxlength		: jQuery.validator.format('프로그램명은 최대 {0}자 이하 입니다.')},
			prgrmUrl      		: {required 		: '프로그램URL은 필수 입력 사항입니다.'							,
		   	  					   maxlength		: jQuery.validator.format('프로그램URL은 최대 {0}자 이하 입니다.')},
            prgrmOrdr			: {maxlength		: jQuery.validator.format('프로그램순서는 최대 {0}자 이하 입니다.')},
            menuId 				: {required 		: '메뉴ID는 필수 입력 사항입니다.'},
            useYn 				: {required 		: '사용여부는 필수 입력 사항입니다.'},
        },
        invalidHandler: validateHandler,
        errorPlacement: validatePlacement
    });
	
	// 코드관리 저장하기
    //--------------------------------------------------------//
    function doSave() {

        // 등록폼의 VALIDATION 기능 활성화
        if (P_RFORM.validate().settings)
            P_RFORM.validate().settings.ignore = false;

        //FORM VALIDATION
        if (P_RFORM.valid() === false)
            return false;
        
        let msg = "프로그램을 수정하시겠습니까?";
		if (P_RFORM.find('[name="mode"]').val() == MODE.INSERT)
			msg = "프로그램을 등록하시겠습니까?";
		
        $.commMsg.confirm(msg, function() {
            // 등록폼을 AJAX로 저장처리
            P_RFORM.ajaxForm({
                url: getUrl('/adm/sys/prog/saveProg.do'), 
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
    	
        $.commMsg.confirm("정말로 삭제하시겠습니까?", function() {
			$.formUtil.toForm({
				mode     : MODE.REMOVE, // 삭제모드
			}, P_RFORM);
			
			// 등록폼을 AJAX로 저장처리
            P_RFORM.ajaxForm({
                url: getUrl('/adm/sys/prog/saveProg.do'),
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