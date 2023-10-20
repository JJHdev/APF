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
	var P_BZFLD	   				= false; 						// 사업분야 체크박스 객체
	var P_RFORM					= $('#registForm'	);					// 등록폼 객체
	var P_MODE					= $('#mode'      	).val();			// 처리 모드
	var P_DTL_SE_CD				= $('#dtlSeCd'		).data('value'); 	// 처리 모드
	var P_DCMNT_TASK_SE_CD		= $('#dcmntTaskSeCd').data('value'); 	// 처리 모드
	var P_APLY_SE_CD			= $('#aplySeCd'		).data('value'); 	// 처리 모드
	
	$('#chckDcmntTaskSeCd'  ).val($('#dcmntTaskSeCd').data('value'));
	$('#chckDtlSeCd'      	).val($('#dtlSeCd'		).data('value'));
	$('#chckAplySeCd'      	).val($('#aplySeCd'		).data('value'));
	$('#chckDcmntCd'      	).val($('#dcmntCd'		).data('value'));
	//========================================================//
    // FORM ELEMENTS 정의
    //--------------------------------------------------------//
	// 필수제출 라디오버튼
	$('#appEsntlYn').appSelectBox({
		form:   'radio', 
		name:   'esntlYn',
		type: 	'static',
		rows: 	RADIO['USE_YN']
	});
	
	$('#appUseYn').appSelectBox({
		form:   'radio', 
		name:   'useYn',
		type: 	'static',
		rows: 	RADIO['ESNTL_YN']
	});
	
	if(P_RFORM.find('[name="mode"]').val() == MODE.UPDATE){
		
		$('#dcmntTaskSeCd').appComboBox({
			url: getUrl('/com/common/getComboUpDcmntTask.do'),
			params: {upSrchDcmntCd: 'NONE'},
			loadFilter: function(data) {
				data.unshift(COMBO.INIT_SELECT);
				return data;
			},callback: function() {
		        $('#dcmntTaskSeCd'	).prop('disabled', true);
		        $('#dtlSeCd'		).prop('disabled', true);
		        $('#dcmntCd'		).prop('disabled', true);
		        $('#aplySeCd'		).prop('disabled', true);
		    }
		});
//-------------------------------------- 수정시 초기값 설정 시작		
		if(P_DCMNT_TASK_SE_CD === CODE.PAPE_GROUP.AFTER){
			$('#dcmntCd').appComboBox({
				url: getUrl('/com/common/getComboUpDcmnt.do'),
				params: {upSrchDcmntCd: 'SELECT', upDcmntCd: P_DCMNT_TASK_SE_CD},
				loadFilter: function(data) {
					data.unshift(COMBO.INIT_SELECT);
					return data;
				},
			});
			
		} else if(P_DCMNT_TASK_SE_CD === CODE.PAPE_GROUP.CROWD) {
			$('#dcmntCd').appComboBox({
				url: getUrl('/com/common/getComboUpDcmnt.do'),
				params: {upSrchDcmntCd: 'SELECT', upDcmntCd: P_DCMNT_TASK_SE_CD},
				loadFilter: function(data) {
					data.unshift(COMBO.INIT_SELECT);
					return data;
				},
			});
			
		} else {
			$('#dcmntCd').appComboBox({
				url: getUrl('/com/common/getComboUpDcmnt.do'),
				params: {upSrchDcmntCd: 'SELECT', upDcmntCd: P_DCMNT_TASK_SE_CD},
				loadFilter: function(data) {
					data.unshift(COMBO.INIT_SELECT);
					return data;
				},
			});
		}
		
		if (P_DCMNT_TASK_SE_CD === CODE.PAPE_GROUP.AFTER) {
			// 프로그램분류
			$('#aplySeCd').appComboBox({
				params: {upCdId: CODE.APLY_SE.code},
				loadFilter: function(data) {
					data.unshift(COMBO.INIT_SELECT);
					var filteredData = data.filter(function(item) {return item.code !== '10';});
					return filteredData;
				}
			});
		} else {
			// 프로그램분류
			$('#aplySeCd').appComboBox({
				params: {upCdId: CODE.APLY_SE.code},
				loadFilter: function(data) {
					data.unshift(COMBO.INIT_SELECT);
					return data;
				}
			});
		}
		
		$('#dtlSeCd').appComboBox({
			url: getUrl('/com/common/getComboDcmnt.do'),
			params: {sprtAplySeCd: P_DTL_SE_CD.substring(0, 2)},
			loadFilter: function(data) {
				return data;
			},
		});
		
//-------------------------------------- 수정시 초기값 설정 종료	
		
	} else {
		$('#dcmntCd').appComboBox({
			loadFilter: function(data) {
				data.unshift(COMBO.INIT_SUBMITFILE_SELECT);
				return data;
			},
		});
		
		$('#aplySeCd').appComboBox({
			loadFilter: function(data) {
				data.unshift(COMBO.INIT_SUBMITFILE_SELECT);
				return data;
			},
		});
		
		$('#dtlSeCd').appComboBox({
			loadFilter: function(data) {
				data.unshift(COMBO.INIT_SUBMITFILE_SELECT);
				return data;
			},
		});
		
		$('#dcmntTaskSeCd').appComboBox({
			url: getUrl('/com/common/getComboUpDcmntTask.do'),
			params: {upSrchDcmntCd: 'NONE'},
			loadFilter: function(data) {
				data.unshift(COMBO.INIT_SELECT);
				return data;
			},
			change: function(data) {
				var value = data.target.value;
				if (value) {
					if(value == 'D01'){
						$('#dtlSeCd').appComboBox({
							url: getUrl('/com/common/getComboDcmnt.do'),
							params: {sprtAplySeCd: CODE.SPRT_APLY_SE.AFTER},
							loadFilter: function(data2) {
								data2.unshift(COMBO.INIT_SELECT);
								return data2;
							},
						});
						
						$('#dcmntCd').appComboBox({
							url: getUrl('/com/common/getComboUpDcmnt.do'),
							params: {upSrchDcmntCd: 'SELECT', upDcmntCd: value},
							loadFilter: function(data) {
								data.unshift(COMBO.INIT_SELECT);
								return data;
							},
						});
						
					} else if(value == 'D02') {
						$('#dtlSeCd').appComboBox({
							url: getUrl('/com/common/getComboDcmnt.do'),
							params: {sprtAplySeCd: CODE.SPRT_APLY_SE.CROWD},
							loadFilter: function(data2) {
								data2.unshift(COMBO.INIT_SELECT);
								return data2;
							},
						});
						
						$('#dcmntCd').appComboBox({
							url: getUrl('/com/common/getComboUpDcmnt.do'),
							params: {upSrchDcmntCd: 'SELECT', upDcmntCd: value},
							loadFilter: function(data) {
								data.unshift(COMBO.INIT_SELECT);
								return data;
							},
						});
						
					} else {
						$('#dtlSeCd').appComboBox({
							url: getUrl('/com/common/getComboDcmnt.do'),
							params: {sprtAplySeCd: CODE.SPRT_APLY_SE.BEFORE},
							loadFilter: function(data2) {
								data2.unshift(COMBO.INIT_SELECT);
								return data2;
							},
						});
						
						$('#dcmntCd').appComboBox({
							url: getUrl('/com/common/getComboUpDcmnt.do'),
							params: {upSrchDcmntCd: 'SELECT', upDcmntCd: value},
							loadFilter: function(data) {
								data.unshift(COMBO.INIT_SELECT);
								return data;
							},
						});
						
					}
					
					if (value === CODE.PAPE_GROUP.AFTER) {
						// 프로그램분류
						$('#aplySeCd').appComboBox({
							params: {upCdId: CODE.APLY_SE.code},
							loadFilter: function(data) {
								data.unshift(COMBO.INIT_SELECT);
								var filteredData = data.filter(function(item) {return item.code !== '10';});
								return filteredData;
							}
						});
					} else {
						// 프로그램분류
						$('#aplySeCd').appComboBox({
							params: {upCdId: CODE.APLY_SE.code},
							loadFilter: function(data) {
								data.unshift(COMBO.INIT_SELECT);
								return data;
							}
						});
					}
				} else {
					$('#prgrsSttsCd').html('<option value="">전체</option>');
				}
			}
		});
	}
	
    //========================================================//
    // 등록폼 VALIDATION RULE 정의
    //--------------------------------------------------------//

    P_RFORM.validate({
        debug: false,
        onsubmit: false,
        onfocusout: false,
        // 검증룰 정의
        rules: {
        	dcmntTaskSeCd      : {required		: true},  	//지원사업명
                  dtlSeCd  	   : {required		: true},    //프로그램명
                  dcmntCd	   : {required		: true},    //서류명
    		  	 aplySeCd      : {required		: true}, 	//신청구분
    		  	  esntlYn      : {required		: true}, 	//필수 제출
    		  	  	useYn      : {required		: true}, 	//항목 표출
        },
        // 검증메세지 정의
        messages: {
        	dcmntTaskSeCd      : {required		: '지원사업명은 필수 입력사항입니다.'},
	        	  dtlSeCd      : {required		: '프로그램명은 필수 입력사항입니다.'},
	        	  dcmntCd      : {required		: '서류명은 필수 입력사항입니다.'},
	        	 aplySeCd      : {required		: '신청구분은 필수 입력사항입니다.'},
	        	  esntlYn      : {required		: '필수 제출은 필수 입력사항입니다.'},
	        	    useYn      : {required		: '항목 표출은 필수 입력사항입니다.'},
        },
        invalidHandler: validateHandler,
        errorPlacement: validatePlacement
    });
	
	// 세부지원사업 저장하기
    //--------------------------------------------------------//
    function doSave() {

        // 등록폼의 VALIDATION 기능 활성화
        if (P_RFORM.validate().settings)
            P_RFORM.validate().settings.ignore = false;

        //FORM VALIDATION
        if (P_RFORM.valid() === false)
            return false;
        
		let msg = "제출서류관리를 수정하시겠습니까?";
		if (P_RFORM.find('[name="mode"]').val() == MODE.INSERT)
			msg = "제출서류관리를 등록하시겠습니까?";
        
        $.commMsg.confirm(msg, function() {
        	$('#dcmntTaskSeCd'	).prop('disabled', false);
        	$('#dtlSeCd'		).prop('disabled', false);
        	$('#dcmntCd'		).prop('disabled', false);
        	$('#aplySeCd'		).prop('disabled', false);
            // 등록폼을 AJAX로 저장처리
            P_RFORM.ajaxForm({
                url: getUrl('/adm/invest/submitfile/saveSubmitFile.do'),
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
    
	// 상담일지목록 삭제하기
    //--------------------------------------------------------//
    function doRemove() {
	
        $.commMsg.confirm("정말로 삭제하시겠습니까?", function() {
        	$('#dcmntTaskSeCd'	).prop('disabled', false);
        	$('#dtlSeCd'		).prop('disabled', false);
        	$('#dcmntCd'		).prop('disabled', false);
        	$('#aplySeCd'		).prop('disabled', false);
			$.formUtil.toForm({
				mode     : MODE.REMOVE, // 삭제모드
			}, P_RFORM);
			
			
            // 등록폼을 AJAX로 저장처리
            P_RFORM.ajaxForm({
                url: getUrl('/adm/invest/submitfile/saveSubmitFile.do'),
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
    
	$('.datepicker-input').datepicker(OPTIONS.DATEPICKER);
	
});