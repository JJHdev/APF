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
	var P_BZFLD	   				= false; 							// 사업분야 체크박스 객체
	var P_RFORM					= $('#registForm'	);				// 등록폼 객체
	var P_MODE					= $('#mode'      	).val();		// 처리 모드
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
			$('#upMenuId').appComboBox({
				url : getUrl('/com/common/getComboMenu.do'),
				params : {
					sysCd : value
				},
				loadFilter : function(data2) {
					data2.unshift(ROOT_MENU);
					return data2;
				},
			});
		}
	});
	
	$('#upMenuId').appComboBox({
		url : getUrl('/com/common/getComboMenu.do'),
		params : {sysCd : P_MODE_STYS},
		callback: function(){
			if(P_RFORM.find('[name="mode"]').val() == MODE.UPDATE){
				$('#menuId'	).prop("readonly", true);
			}
		},
		loadFilter : function(data2) {
			data2.unshift(ROOT_MENU);
			return data2;
		},
	});
	
    // 등록용 사용여부 라디오박스	
	$('#appUseYn').appSelectBox({
		form: 'radio',
		name: 'useYn',
		type: 'static',
		rows: STORE['USE_YN']
	});
	
    // 등록용 팝업여부 라디오박스	
	$('#appPopupYn').appSelectBox({
		form: 'radio',
		name: 'popupYn',
		type: 'static',
		rows: STORE['POP_YN']
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
            menuId      	: {required			: true	,
            			  	   maxlength		: 20	},
            menuNm		    : {required			: true	,
			  	   			   maxlength		: 300	},
            menuLvl		    : {required			: true	,
	   			   			   maxlength		: 3		},
   			menuOrdr	    : {maxlength		: 5		},
   			trgtUrl	    	: {maxlength		: 500	},
   			useYn     		: {required			: true	},
        },
        // 검증메세지 정의
        messages: {
        	menuId      	: {required 		: '메뉴ID는 필수 입력 사항입니다.'							,
		  			 		   maxlength		: jQuery.validator.format('메뉴ID는 최대 {0}자 이하 입니다.')},
	 		menuNm    		: {required 		: '메뉴명은 필수 입력 사항입니다.'							,
	 						   maxlength		: jQuery.validator.format('메뉴명은 최대 {0}자 이하 입니다.')},
            menuLvl		    : {required 		: '메뉴레벨은 필수 입력 사항입니다.'							,
				   			   maxlength		: jQuery.validator.format('메뉴레벨은 최대 {0}자 이하 입니다.')},
  			menuOrdr	    : {maxlength		: jQuery.validator.format('메뉴순서는 최대 {0}자 이하 입니다.')},
   			trgtUrl	    	: {maxlength		: jQuery.validator.format('메뉴순서는 최대 {0}자 이하 입니다.')},
   			useYn     		: {required			: '사용여부는 필수 입력 사항입니다.'							},

        },
        invalidHandler: validateHandler,
        errorPlacement: validatePlacement
    });
	// 숫자만입력
	bindOnlyNumber($("#menuLvl"));
	bindOnlyNumber($("#menuOrdr"));
	
	// 코드관리 저장하기
    //--------------------------------------------------------//
    function doSave() {

        // 등록폼의 VALIDATION 기능 활성화
        if (P_RFORM.validate().settings)
            P_RFORM.validate().settings.ignore = false;

        //FORM VALIDATION
        if (P_RFORM.valid() === false)
            return false;
        
        let msg = "공통코드를 수정하시겠습니까?";
		if (P_RFORM.find('[name="mode"]').val() == MODE.INSERT)
			msg = "공통코드를 등록하시겠습니까?";
		
        $.commMsg.confirm(msg, function() {
            // 등록폼을 AJAX로 저장처리
            P_RFORM.ajaxForm({
                url: getUrl('/adm/sys/menu/saveMenu.do'), 
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
    	
    	if(P_RFORM.find('[name="upMenuId"]').val() == ROOT_CODE.code){
    		message = '최상위 코드 삭제시 하위코드까지 삭제 됩니다. 정말로 삭제하시겠습니까?';
    	}
    	
        $.commMsg.confirm(message, function() {
			$.formUtil.toForm({
				mode     : MODE.REMOVE, // 삭제모드
			}, P_RFORM);
			
			// 등록폼을 AJAX로 저장처리
            P_RFORM.ajaxForm({
                url: getUrl('/adm/sys/menu/saveMenu.do'),
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