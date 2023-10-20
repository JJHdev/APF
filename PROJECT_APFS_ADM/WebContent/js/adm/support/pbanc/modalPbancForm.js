/**
*******************************************************************************
***    명칭: modalPbancForm.js
***    설명: 운영관리-사업공고관리 화면
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자           내용
*** ---------------------------------------------------------------------------
***    1.0      2023.06.09      LHB       First Coding.
*******************************************************************************
**/
$(function() {

    //========================================================//
    // 화면 스크립트 내에 사용할 객체,상수 정의
    //--------------------------------------------------------//
    var P_RFORM			= $('#registForm');					// 등록폼 객체
    var P_MODE			= $('#mode'      ).val();			// 처리 모드
    var P_BIZ_PBANC_NO	= $('#bizPbancNo').val();			// 게시글 키정보

	var P_RCPT_SE_CD	= $('#appRcptSeCd');
	var P_BZFLD			= false;		// 사업분야 체크박스 객체
	var P_BZTRGT		= false;		// 사업대상 체크박스 객체
	var P_BZTRGTAGE		= false;		// 사업대상연령 체크박스 객체
	var P_BZTRGTFNTNPD = false;		// 사업대상업력 체크박스 객체

	var P_FILE_BOX		= $('#attachFile');  // 첨부파일(수정, 등록) 컨트롤 객체
	var P_FILE_BOX2		= $('#attachFile2');  // 첨부파일(미리보기)   컨트롤 객체
	
	var RCPT_SE_CD	= P_RCPT_SE_CD.data('value');
	var RCPT_SE_CD1 = '10'; // 접수기간 - 예정
	var RCPT_SE_CD3 = '30';	// 접수기간 - 기간
	
	//========================================================//
    // 첨부파일 초기화
    //--------------------------------------------------------//
	
	P_FILE_BOX.appBizFile({
		cls: 'border-0 mb-0 pb-0',
		// 처리모드
		mode: P_MODE,
		// 설명글
		comment: {
			check: '',
			input: '100MB 이내 1개 파일 가능, 여러 파일 업로드 희망 시 압축파일 권장'
		},
		initData: {
			fileType: CODE.FILE_TYPE.BIZ,
			docuCd  : CODE.TASK_SE.PBANC,
			docuNo  : '',
			docuSeq : '',
			fileYn  : 'Y',
		}
	}).appBizFile('init');
	
	P_FILE_BOX2.appBizFile({
		cls: 'border-0 mb-0 pb-0',
		// 처리모드
		mode: MODE.VIEW,
		// 설명글
		comment: {
			check: '',
			//input: '100MB 이내 1개 파일 가능, 여러 파일 업로드 희망 시 압축파일 권장'
		},
		initData: {
			fileType: CODE.FILE_TYPE.BIZ,
			docuCd  : CODE.TASK_SE.PBANC,
			docuNo  : P_BIZ_PBANC_NO,
			docuSeq : '',
			fileYn  : 'Y',
		}
	}).appBizFile('init');
	
	//========================================================//
    // 등록폼 VALIDATION RULE 정의
    //--------------------------------------------------------//
    P_RFORM.validate({
        debug: false,
        onsubmit: false,
        onfocusout: false,
        // 검증룰 정의
        rules: {
            crdnsBzentyNo  : {required: true},
            tkcgDeptNm     : {required: true,
                              maxlength: 20},
			picNm          : {required: true,
			                  maxlength: 20},
			picTelno       : {required: true,
			                  phoneNo: true},
			bizPbancNm     : {required: true,
                              maxlength: 100},
			bizGuidanceUrl : {required: false,
                              maxlength: 500,
                              url: true},
			rcptSeCd       : {required: true},
			rcptBgngYmd    : {
								required: function() {
									if($('input[name="rcptSeCd"]:checked').val() == RCPT_SE_CD3 || $('input[name="rcptSeCd"]:checked').val() == RCPT_SE_CD1) {
										return true;
									}
									return false;
							 	}
			},
			rcptBgngTm     : {
								required: function() {
									if($('input[name="rcptSeCd"]:checked').val() == RCPT_SE_CD3) {
										return true;
									}
									return false;
							 	},
			},
			rcptEndYmd     : {
								required: function() {
									if($('input[name="rcptSeCd"]:checked').val() == RCPT_SE_CD3 || $('input[name="rcptSeCd"]:checked').val() == RCPT_SE_CD1) {
										return true;
									}
									return false;
							 	}
			},
			rcptEndTm      : {
								required: function() {
									if($('input[name="rcptSeCd"]:checked').val() == RCPT_SE_CD3) {
										return true;
									}
									return false;
							 	},
			},
            bizFld         : {required: true,
	                          maxlength: 100},
            bizTrgt        : {required: true},
            bizTrgtAge     : {required: true},
			bizTrgtFntnPd  : {required: true},
			rcptMthdCn     : {required: true,
			                  maxlength: 2000},
			aplyQlfcCn     : {required: true,
			                  maxlength: 2000},
			aplyExclTrgtCn : {required: false,
			                  maxlength: 2000},
			sbmsnDcmntCn   : {required: true,
			                  maxlength: 2000},
			sprtCn         : {required: true,
			                  maxlength: 2000},
        },
        // 검증메세지 정의
        messages: {
			crdnsBzentyNo  : {required: '등록기관명은 필수 선택사항입니다.'},
            tkcgDeptNm     : {required: '사업담당부서는 필수 입력사항입니다.',
                              maxlength: jQuery.validator.format('최대 {0}자 이하')},
			picNm          : {required: '사업담당자는 필수 입력사항입니다.',
			                  maxlength: jQuery.validator.format('최대 {0}자 이하')},
			picTelno       : {required: '담당자 연락처는 필수 입력사항입니다.',
			                  phoneNo: '담당자 연락처의 형식이 올바르지 않습니다.'},
			bizPbancNm     : {required: '공고명은 필수 입력사항입니다.',
                              maxlength: jQuery.validator.format('최대 {0}자 이하')},
			bizGuidanceUrl : {maxlength: jQuery.validator.format('최대 {0}자 이하'),
                              url: '안내URL의 형식이 올바르지 않습니다.'},
			rcptSeCd       : {required: '접수기간은 필수 선택사항입니다.'},
			rcptBgngYmd    : {required: '접수시작일자는 필수 입력사항입니다.'},
			rcptBgngTm     : {required: '접수시작시간은 필수 선택사항입니다.'},
			rcptEndYmd     : {required: '접수종료일자는 필수 입력사항입니다.'},
			rcptEndTm      : {required: '접수종료시간은 필수 선택사항입니다.'},
            bizFld         : {required: '사업분야는 필수 입력사항입니다.',
	                          maxlength: jQuery.validator.format('최대 {0}자 이하')},
            bizTrgt        : {required: '사업대상은 필수 선택사항입니다.'},
            bizTrgtAge     : {required: '사업대상연령은 필수 선택사항입니다.'},
			bizTrgtFntnPd  : {required: '사업대상업력은 필수 선택사항입니다.'},
			rcptMthdCn     : {required: '공고접수방법은 필수 입력사항입니다.',
			                  maxlength: jQuery.validator.format('최대 {0}자 이하')},
			aplyQlfcCn     : {required: '신청자격은 필수 입력사항입니다.',
				              maxlength: jQuery.validator.format('최대 {0}자 이하')},
			aplyExclTrgtCn : {maxlength: jQuery.validator.format('최대 {0}자 이하')},
			sbmsnDcmntCn   : {required: '제출서류는 필수 입력사항입니다.',
				              maxlength: jQuery.validator.format('최대 {0}자 이하')},
			sprtCn         : {required: '지원내용은 필수 입력사항입니다.',
				              maxlength: jQuery.validator.format('최대 {0}자 이하')},
        },
        invalidHandler: validateHandler,
        errorPlacement: validatePlacement
    });

	//========================================================//
    // 목록 GRID 정의 (easyui DATAGRID)
    //--------------------------------------------------------//

    //========================================================//
    // FORM ELEMENTS 정의
    //--------------------------------------------------------//

	//========================================================//
    // 콤보박스 정의
    //--------------------------------------------------------//
	// 유관기관
	$('#crdnsBzentyNo').appComboBox({
		url: getUrl('/com/common/getCrdnsEntComboCode.do'),
		prompt: '투자분야',
		loadFilter: function(data) {
			data.unshift(COMBO.INIT_SELECT);
			return data;
		},
	});
	// 담당자연락처 유선번호
	$('#picTelno1').appComboBox({
		params: {upCdId: CODE.TELNO_SE.code},
		callback: function() {
			// 담당자 연락처 세팅
			if (P_MODE != MODE.VIEW) {
				$.formUtil.splitData('picTelno', 'mobile');	
			}
		}
	});
	
	//========================================================//
    // 인풋박스 정의
    //--------------------------------------------------------//
	// 접수기간
	P_RCPT_SE_CD = $('#appRcptSeCd').appSelectBox({
		form: 'radio',
		name: 'rcptSeCd',
		params: {upCdId: CODE.RCPT_WHL_SE.code},
		callback: function() {
			if(P_MODE != MODE.VIEW) {
				if ($('#registForm input[name="rcptSeCd"]:checked').val() == RCPT_SE_CD3) {
					// 접수기간 선택했을 때 기간 선택박스 출력
					$('#rcptBox').show();
					$('#rcptBgngTm').closest('.col').show();
					$('#rcptEndTm' ).closest('.col').show();
					
					$('.datepicker-custom').datepicker('destroy');
					$('.datepicker-custom').datepicker(OPTIONS.DATEPICKER);
				} else if ($('#registForm input[name="rcptSeCd"]:checked').val() == RCPT_SE_CD1) {
					// 예정 선택했을 때 월 선택박스 출력
					$('#rcptBox').show();
					$('#rcptBgngTm').closest('.col').hide();
					$('#rcptEndTm' ).closest('.col').hide();
					
					$('.datepicker-custom').datepicker('destroy');
					$('.datepicker-custom').datepicker(OPTIONS.DATEPICKER_MONTH_ANYDATE);
				} else {
					$('#rcptBox').hide();
				}	
			}
		},
		click: function(v, o) {
			var value = v.target.value;
			if (value == RCPT_SE_CD3) {
				// 접수기간 선택했을 때 기간 선택박스 출력
				$('#rcptBox').show();
				P_RFORM.find('#rcptBgngYmd').val('');
				P_RFORM.find('#rcptEndYmd ').val('');
				
				$('#rcptBgngTm').closest('.col').show();
				$('#rcptEndTm' ).closest('.col').show();
				$('.datepicker-custom').datepicker('destroy');
				$('.datepicker-custom').datepicker(OPTIONS.DATEPICKER);
			} else if (value == RCPT_SE_CD1) {
				// 예정 선택했을 때 월 선택박스 출력
				$('#rcptBox').show();
				P_RFORM.find('#rcptBgngYmd').val('');
				P_RFORM.find('#rcptEndYmd ').val('');
					
				$('#rcptBgngTm').closest('.col').hide();
				$('#rcptEndTm' ).closest('.col').hide();
				$('#rcptBgngTm option:eq(0)').prop("selected", true);
				$('#rcptEndTm  option:eq(0)').prop("selected", true);
				$('.datepicker-custom').datepicker('destroy');
				$('.datepicker-custom').datepicker(OPTIONS.DATEPICKER_MONTH_ANYDATE);
			} else {
				$('#rcptBox').hide();
			}
		}
	});
	// 사업분야
	P_BZFLD = $('#appBizFld').appSelectBox({
		form: 'checkbox',
		name: 'bizFldInpt',
		params: {upCdId: CODE.BIZ_RLM.code},
		init: {text: '전체', code: 'ALL'},
		click: function(data) {
			var chks = $.formUtil.getValue(P_RFORM, 'bizFldInpt');
			
			if($(data.target).val() == 'ALL' && chks.indexOf('ALL') > -1) {
				$('input[name="bizFldInpt"]').prop("checked", true);
			} else if($(data.target).val() == 'ALL' && chks.indexOf('ALL') < 0) {
				$('input[name="bizFldInpt"]').prop("checked", false);
			} else {
				if(chks.length < $('input[name="bizFldInpt"]').length && chks.indexOf('ALL') > -1) {
					$('input[name="bizFldInpt"][value="ALL"]').prop("checked", false);
				} else if (chks.length == $('input[name="bizFldInpt"]').length-1 && chks.indexOf('ALL') < 0) {
					$('input[name="bizFldInpt"][value="ALL"]').prop("checked", true);
				}
			}
		},
		callback: function() {
			// 사업분야 선택처리
			if (P_MODE != MODE.VIEW) {
				const value = $('#registForm input[name="bizFld"]').val();
				if (value) {
					P_BZFLD.setSplitValues(value, ',');
				}
			}
		}
	});
	// 사업대상
	P_BZTRGT = $('#appBizTrgt').appSelectBox({
		form: 'checkbox',
		name: 'bizTrgtInpt',
		params: {upCdId: CODE.SPRT_TRGT.code},
		init: {text: '전체', code: 'ALL'},
		click: function(data) {
			var chks = $.formUtil.getValue(P_RFORM, 'bizTrgtInpt');
			
			if($(data.target).val() == 'ALL' && chks.indexOf('ALL') > -1) {				// '전체'가 클릭되었고 체크된 경우
				$('input[name="bizTrgtInpt"]').prop("checked", true);
				$('input[name="bizTrgtFntnPd"]').prop("disabled", false);
			} else if($(data.target).val() == 'ALL' && chks.indexOf('ALL') < 0) {		// '전체'가 클릭되었지만 체크되지 않은 경우
				$('input[name="bizTrgtInpt"]').prop("checked", false);
				$('input[name="bizTrgtFntnPd"]').prop("disabled", false);
			} else {
				if(chks.length < $('input[name="bizTrgtInpt"]').length && chks.indexOf('ALL') > -1) {
					$('input[name="bizTrgtInpt"][value="ALL"]').prop("checked", false);
				} else if (chks.length == $('input[name="bizTrgtInpt"]').length-1 && chks.indexOf('ALL') < 0) {
					$('input[name="bizTrgtInpt"][value="ALL"]').prop("checked", true);
				}
				
				if(chks.indexOf('ST1') > -1 || chks.indexOf('ST2') > -1 || chks.indexOf('ST9') > -1) {
					if(chks.indexOf('ST3') < 0) {
						// 개인, 법인, 예비만 선택된 경우
						$('input[name="bizTrgtFntnPd"][value="U00"]').prop("checked",  false);
						$('input[name="bizTrgtFntnPd"][value="U00"]').prop("disabled", true);
					}
				} else {
					if(chks.indexOf('ST3') > -1) {
						// 예비창업자만 선택된 경우
						$('input[name="bizTrgtFntnPd"]').not('[value="U00"]').prop("checked",  false);
						$('input[name="bizTrgtFntnPd"]').not('[value="U00"]').prop("disabled", true);
						$('input[name="bizTrgtFntnPd"][value="U00"]').prop("checked", true);
					}
				}
			}
			
			if(chks.length < 1) {
				$('input[name="bizTrgtFntnPd"]').prop("disabled", false);
			}
		},
		callback: function() {
			// 사업대상 선택처리
			if (P_MODE != MODE.VIEW) {
				var value = $('#registForm input[name="bizTrgt"]').val();
				if (value) {
					P_BZTRGT.setSplitValues(value, ',');
				}
			}
		}
	});
	// 사업대상연령
	P_BZTRGTAGE = $('#appBizTrgtAge').appSelectBox({
		form: 'radio',
		name: 'bizTrgtAge',
		params: {upCdId: CODE.SPRT_AGE.code},
	});
	// 사업대상업력
	P_BZTRGTFNTNPD = $('#appBizTrgtFntnPd').appSelectBox({
		form: 'radio',
		name: 'bizTrgtFntnPd',
		params: {upCdId: CODE.FNTN_WHL.code},
	});

	// 사업공고 저장하기
    //--------------------------------------------------------//
    function doSave() {

		// 휴대전화 병합
		$.formUtil.mergeData('picTelno', 'phone', 3);
		
		// 다중 선택 대상 병합
		var bizFld = $.formUtil.getValue(P_RFORM, 'bizFldInpt');
		bizFld  = (bizFld instanceof Array ) ? bizFld.filter (function(e) { return e != 'ALL'; }).join(',') : bizFld;
		var bizTrgt = $.formUtil.getValue(P_RFORM, 'bizTrgtInpt');
		bizTrgt = (bizTrgt instanceof Array) ? bizTrgt.filter(function(e) { return e != 'ALL'; }).join(',') : bizTrgt;
		
		$.formUtil.toForm({
			bizFld		: bizFld,	// 사업분야
			bizTrgt		: bizTrgt,	// 사업대상
			rcptBgngDt	: $('#registForm #rcptBgngYmd').val() + $('#registForm #rcptBgngTm').val(),	// 접수시작일시
			rcptEndDt	: $('#registForm #rcptEndYmd' ).val() + $('#registForm #rcptEndTm' ).val(),	// 접수종료일시
		}, P_RFORM);

        // 등록폼의 VALIDATION 기능 활성화
        if (P_RFORM.validate().settings)
            P_RFORM.validate().settings.ignore = false;

        //FORM VALIDATION
        if (P_RFORM.valid() === false)
            return false;

		// 첨부파일 VALIDATION (comm_component.js)
		var fvalidate = P_FILE_BOX.appBizFile('validate', {
			isAlert: true,
			isExt:   true,
			isLimit: true
		});
		
        if (!fvalidate)
            return false;

        $.commMsg.confirm("저장하시겠습니까?", function() {
            // 등록폼을 AJAX로 저장처리
            P_RFORM.ajaxForm({
                url: getUrl('/adm/support/pbanc/savePbanc.do'),
                // 오류시 처리로직
                error: $.ajaxUtil.error,
                // 저장후 처리로직
                success: function(ret) {
					$.commMsg.success(ret, '성공적으로 저장되었습니다.', function() {
                        P_MODAL.doSearch();
					});
                }
            }).submit();
        });
        return false;
    }

	// 사업공고 삭제하기
    //--------------------------------------------------------//
    function doRemove() {
	
        $.commMsg.confirm("정말로 삭제하시겠습니까?", function() {
			$.formUtil.toForm({
				mode     : MODE.REMOVE, // 삭제모드
			}, P_RFORM);
	
            // 등록폼을 AJAX로 저장처리
            P_RFORM.ajaxForm({
                url: getUrl('/adm/support/pbanc/savePbanc.do'),
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

	// 사업공고 미리보기
    //--------------------------------------------------------//
	function doPreview() {
		var obj = P_RFORM.serializeObject();
		
		if (obj['bizFldInpt']) {
			obj['bizFld'] = obj['bizFldInpt'].join(',');
		}
		if (obj['bizTrgtInpt']) {
			obj['bizTrgt'] = obj['bizTrgtInpt'].join(',');
		}
		
		P_MODAL.doOpenSelect(obj);
		
		return false;
	}
	
    // 취소버튼 클릭시 이벤트처리
    //--------------------------------------------------------//
    $('#btnCancel').bind('click', function() {
		// 부모창의 doClose 호출
		//P_MODAL.doClose();
		if (P_MODAL && P_MODAL.data && P_MODAL.data.mode && P_MODAL.data.mode == MODE.INSERT) {
			if (P_MODE == MODE.VIEW) {
				// INSERT 모드에서 VIEW 모드로 온 경우만
				P_MODAL.doOpenRegist(P_MODAL.data);
			}
		}
		
        return false;
	});
	
	// 신청페이지로 새창이동
	function doLink() {
		let url = $(this).data('value');
		if (url)
			goNewUrl(url);
		return false;
	}
	
	if (P_RCPT_SE_CD == '10') {
		$('.datepicker-custom').datepicker(OPTIONS.DATEPICKER_MONTH_ANYDATE);	
	} else {
		$('.datepicker-custom').datepicker(OPTIONS.DATEPICKER);
	}

	// 저장버튼 클릭시 이벤트 처리
    $('#btnSave'  ).bind('click', doSave);
	// 삭제버튼 클릭시 이벤트 처리
	$('#btnRemove').bind('click', doRemove);
	// 미리보기버튼 클릭시 이벤트 처리
	$('#btnPrvw'  ).bind('click', doPreview);
	// 신청바로가기 버튼 클릭시 이벤트 처리
	$('#btnLink'  ).bind('click', doLink);
	
	P_FILE_BOX.appBizFile('loadBox', false, {
		fileType: CODE.FILE_TYPE.BIZ,
		docuCd  : CODE.TASK_SE.PBANC,
		docuNo  : P_BIZ_PBANC_NO,
	});
	
	P_FILE_BOX2.appBizFile('loadBox', false, {
		fileType: CODE.FILE_TYPE.BIZ,
		docuCd  : CODE.TASK_SE.PBANC,
		docuNo  : P_BIZ_PBANC_NO,
	});
	
});