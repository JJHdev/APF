/**
*******************************************************************************
***    명칭: modalSubmitFielForm.js
***    설명: 운영관리-제출서류관리 화면
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***   1.0         2023.06.21    LSH   First Coding.
***   1.1         2023.07.21    J H   작업완료
*******************************************************************************
**/
$(function() {
    //========================================================//
    // 화면 스크립트 내에 사용할 객체,상수 정의
    //--------------------------------------------------------//
	var P_BZFLD	   				= false; 							// 사업분야 체크박스 객체
	var P_RFORM					= $('#registForm'	);				// 등록폼 객체
	var P_MODE					= $('#mode'      	).val();		// 처리 모드
	//========================================================//
    // FORM ELEMENTS 정의
    //--------------------------------------------------------//
	$('#upCdId').appComboBox({
		params: {upCdId: ROOT_CODE['code']},
		callback: function(){
			if($('#upCdId').data('value') == ROOT_CODE.code){
				$('#cdOrdr' ).prop("readonly", true);
			}
			if(P_RFORM.find('[name="mode"]').val() == MODE.UPDATE){
				$('#cdId'	).prop("readonly", true);
				$("#upCdId").css("pointer-events", "none");
			}
			if(P_RFORM.find('[name="mode"]').val() == MODE.INSERT){
				$('#cdOrdr').val(0);
				$('#cdOrdr').prop("readonly", true);
			}
		},
		change : function(data){
			$('#cdOrdr').val('');
			$('#cdOrdr').prop("readonly", false);
			if(data.target.value == ROOT_CODE.code){
				$('#cdOrdr').val(0);
				$('#cdOrdr').prop("readonly", true);
			}
		},
		loadFilter : function(data) {
			data.unshift(ROOT_CODE);
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
	jQuery.validator.addMethod("greaterThan", function(value, element, params) {
        if (!/Invalid|NaN/.test(new Date(value))) {
            return new Date(value) >= new Date($(params).val());
        }
        return isNaN(value) && isNaN($(params).val()) 
            || (Number(value) >= Number($(params).val())); 
    },'종료 날짜는 시작 날짜보다 이후이어야 합니다.');
	
	//========================================================//
    // 등록폼 VALIDATION RULE 정의
    //--------------------------------------------------------//
	P_RFORM.validate({
        debug: false,
        onsubmit: false,
        onfocusout: false,
        // 검증룰 정의
        rules: {
        	upCdId    : {required		: true	},
            cdId      : {required		: true	,
    					 maxlength		: 20	},
            cdNm      : {required		: true	,
				 		 maxlength		: 200	},
 		    cdCn      : {maxlength		: 2000	},
 		    cdOrdr    : {maxlength		: 5		},
            useYn     : {required		: true	},
            vldBgngYmd: {date			: true	},
            vldEndYmd : {date			: true	,
		   				 greaterThan	: '#vldBgngYmd'}
        },
        // 검증메세지 정의
        messages: {
        	upCdId    : {required		: '상위코드ID는 필수 입력 사항입니다.'							},
            cdId      : {required 		: '코드ID는 필수 입력 사항입니다.'							 ,
  			  			 maxlength		: jQuery.validator.format('코드ID는 최대 {0}자 이하 입니다.')},
  			cdNm      : {required 		: '코드명은 필수 입력 사항입니다.'							 ,
		  			 	 maxlength		: jQuery.validator.format('코드명은 최대 {0}자 이하 입니다.')},
		    cdCn      : {maxlength		: jQuery.validator.format('코드설명은 최대 {0}자 이하 입니다.')},
		    cdOrdr    : {maxlength		: jQuery.validator.format('코드순서는 최대 {0}자 이하 입니다.')},
            useYn     : {required		: '사용여부는 필수 선택 사항입니다.'							},
            vldBgngYmd: {date			: '유효시작일자를 yyyyMMdd 형식에 맞게 입력하세요.'				},
            vldEndYmd : {date			: '유효종료일자를 yyyyMMdd 형식에 맞게 입력하세요.'				}
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
        
        let msg = "공통코드를 수정하시겠습니까?";
		if (P_RFORM.find('[name="mode"]').val() == MODE.INSERT)
			msg = "공통코드를 등록하시겠습니까?";
		
        $.commMsg.confirm(msg, function() {
            // 등록폼을 AJAX로 저장처리
            P_RFORM.ajaxForm({
                url: getUrl('/adm/sys/code/saveCode.do'), 
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
    	
    	if(P_RFORM.find('[name="upCdId"]').val() == ROOT_CODE.code){
    		message = '최상위 코드 삭제시 하위코드까지 삭제 됩니다. 정말로 삭제하시겠습니까?';
    	}
    	
        $.commMsg.confirm(message, function() {
			$.formUtil.toForm({
				mode     : MODE.REMOVE, // 삭제모드
			}, P_RFORM);
			
			// 등록폼을 AJAX로 저장처리
	        P_RFORM.ajaxForm({
	            url: getUrl('/adm/sys/code/saveCode.do'),
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