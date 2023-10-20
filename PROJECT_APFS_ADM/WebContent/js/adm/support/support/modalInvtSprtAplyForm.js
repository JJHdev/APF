/**
*******************************************************************************
***    명칭: modalInvtSprtAplyForm.js
***    설명: 지원사업관리-신청현황관리 화면
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.06.07      LHB      First Coding.
*******************************************************************************
**/
$(function() {

    //========================================================//
    // 화면 스크립트 내에 사용할 객체,상수 정의
    //--------------------------------------------------------//
    var P_RFORM				= $('#registForm');								// 등록폼 객체
    var P_MODE				= $('#mode'      ).val();						// 처리 모드
    var P_SPRT_APLY_NO		= $('#sprtAplyNo'  ).val();						// 지원신청번호
	var P_SPRT_APLY_SE_CD	= $.formUtil.getValue(P_RFORM, 'sprtAplySeCd');	// 지원신청구분
	var P_PRGRS_STTS_CD		= $.formUtil.getValue(P_RFORM, 'prgrsSttsCd');	// 진행상태

	var P_GRID2	 = $('#grid-prgre'); 			// 지원사업 진행현황 그리드 객체
	var P_GRID3  = $('#grid-event'); 			// 투자설명회 그리드 객체
	
	var P_BZFLD	   = false; // 사업분야 체크박스 객체
	var P_PRGRMSE  = false; // 프로그램명 셀렉트박스 객체
	var P_PRGRS    = false; // 진행상태(진행현황등록) 셀렉트박스 객체
	var P_EVENTBTN = false; // 투자설명회명 버튼 객체
	var P_PRCSCYCL = false; // 차수 DIV 객체
	var P_FTFYN    = false; // 대면여부 DIV 객체
	var P_EVENT    = false; // 투자설명회명 DIV 객체
	var P_FIXDIV   = false; // 진행현황등록 고정값(담당자, 담당자연락처, 처리일자) DIV 객체
	
	var P_MFORM1 = $('#prgrsForm1'); // 모달폼 상담완료 객체
	var P_MFORM2 = $('#prgrsForm2'); // 모달폼 진행현황등록 객체
	var P_MFORM3 = $('#prgrsForm3'); // 모달폼 반려 객체
	var P_MFORM4 = $('#prgrsForm4'); // 모달폼 보완요청 객체
	var P_MFORM5 = $('#prgrsForm5'); // 모달폼 심사완료 객체
	var P_MFORM6 = $('#prgrsForm6'); // 모달폼 사업종료 객체
	var P_MFORM7 = $('#prgrsForm7'); // 모달폼 투자설명회 객체
	var P_MFORM8 = $('#prgrsForm8'); // 모달폼 제출서류 객체
	
	var P_MODAL1_BTN_DIV = false; // 상담완료 버튼 DIV
	var P_MODAL2_BTN_DIV = false; // 진행현황등록 버튼 DIV
	var P_MODAL3_BTN_DIV = false; // 반려 버튼 DIV
	var P_MODAL4_BTN_DIV = false; // 보완요청 버튼 DIV
	var P_MODAL5_BTN_DIV = false; // 심사완료 버튼 DIV
	var P_MODAL6_BTN_DIV = false; // 사업종료 버튼 DIV
	
	var P_MODAL1 = false;
	var P_MODAL2 = false;
	var P_MODAL3 = false;
	var P_MODAL4 = false;
	var P_MODAL5 = false;
	var P_MODAL6 = false;
	var P_MODAL7 = false;
	var P_MODAL8 = false;
	
	//========================================================//
    // 첨부파일 초기화
    //--------------------------------------------------------//
	// 2023.06.13 LSH 첨부파일 컴포넌트 적용 (app_bizpapefile.js)
	$('#attachFile').appBizPapeFile({
		// 처리모드
		mode : MODE.VIEW,
		// 초기값
		initData: {
			// 서류그룹코드
			upDcmntCd : FILTER.getPapeGroup(P_SPRT_APLY_SE_CD),
			// 문서번호
			docNo     : P_SPRT_APLY_NO,
		},
	}).appBizPapeFile('init');
	
	//========================================================//
	//	지원서비스 신청현황관리 2023.09.19 제출서류 수정하기 로직  / JH
    //--------------------------------------------------------//
	$.ajax({
        url: getUrl('/adm/support/support/getInvtSprtAplyFileInfo.do'),
        method: 'GET',
        data: {sprtAplyNo: P_SPRT_APLY_NO},
        success: function(response) {
        	let P_PARAMS = {aplyNo			: response.Data.sprtAplyNo,	
				bizYn			: STORE.USE_YN[0].code,	
				btnId			: "#appButtons",
				formId			: "#selectForm",
				prgrmNm			: response.Data.prgrmNm,	
				prgrmNo			: response.Data.prgrmNo,	
				sprtSeCd		: response.Data.sprtAplySeCd,	
				sprtSeNm		: response.Data.sprtAplySeCdNm,
				stepCd			: CODES.SPRT_STEP_LIST[3].code,
				stepNm			: CODES.SPRT_STEP_LIST[3].text,	
				stepNo			: '3',
				aplyBzentyNo	: response.Data.aplyBzentyNo,
				bzentySeCd		: BZENTY_TYPE_FILTER.getBzentyType(response.Data.bzentySeCd)}
        	$('#appContent').appSprtFile  (P_PARAMS);
        },
        error: function(error) {
            console.error("Data fetching failed", error);
        }
    });
	
	
	//========================================================//
    // 목록 GRID 정의 (EasyUI datagrid)
    //--------------------------------------------------------//
	// 지원사업진행현황 그리드
	P_GRID2.datagrid({
		// 화면영역 맞춤
		fit: true,
        // 그리드 결과데이터 타입
        contentType: 'application/json',
        // 그리드 결과데이터 타입
        dataType: 'json',
        // 그리드 데이터연동 방식
        method: 'POST',
        // 그리드 단일행만 선택여부
        singleSelect: true,
        // 그리드 페이징처리 여부
        pagination:true,
        // 그리드 행번호 표시여부
        rownumbers:true,
        // 한 페이지 출력수
        pageSize: 20,
        // 칼럼정의
        columns: [[
			{field:'sn'					,width: 80,halign:'center',align:'center',title:'일련번호',    hidden: true},
			{field:'sprtAplyNo'			,width: 80,halign:'center',align:'center',title:'지원신청번호', hidden: true},
			{field:'prgrmSeCdNm'		,width:120,halign:'center',align:'center',title:'프로그램명'},
			{field:'detailInfo'			,width:120,halign:'center',align:'center',title:'상세정보'},
			{field:'prgrsDetailSttsCdNm',width:60, halign:'center',align:'center',title:'진행상태'},
			{field:'prcsYmd'			,width:100,halign:'center',align:'center',title:'처리일자'},
			{field:'picNm'				,width:100,halign:'center',align:'center',title:'담당자'},
			{field:'picTelno'			,width:120,halign:'center',align:'center',title:'담당자연락처', formatter:$.formatUtil.toPhone},
			{field:'prgrsCn'			,width:200,halign:'center',align:'center',title:'코멘트', formatter: function(value, row, index) {
																											   if (value) {
																												   return '<span title="' + value + '">' + value + '</span>';
																											   }
																										       return value;
																										   }
			},
	    ]],
        // 행선택시 수정하기
        onSelect: function(data) {
	
		},
		// no-data 표출 메시지
		emptyMsg: '등록된 진행현황이 없습니다.',
		// 목록로딩 완료후 처리
		onLoadSuccess: function(data) {
		}
    });

	// 투자설명회 그리드
	P_GRID3.datagrid({
		// 화면영역 맞춤
		fit: true,
        // 그리드 결과데이터 타입
        contentType: 'application/json',
        // 그리드 결과데이터 타입
        dataType: 'json',
        // 그리드 데이터연동 방식
        method: 'POST',
        // 그리드 단일행만 선택여부
        singleSelect: true,
        // 그리드 페이징처리 여부
        pagination:true,
        // 그리드 행번호 표시여부
        rownumbers:true,
        // 한 페이지 출력수
        pageSize: 30,
        // 칼럼정의
        columns: [[
			{field:'evntNo'		 ,width: 80,halign:'center',align:'center',title:'행사번호',hidden: true},
			{field:'evntNm'		 ,width:150,halign:'center',align:'center',title:'행사명',},
			{field:'evntBgngYmd' ,width:100,halign:'center',align:'center',title:'행사일자'},
			{field:'regDate'	 ,width:100,halign:'center',align:'center',title:'등록일자'},
	    ]],
        // 행선택시 수정하기
        onSelect: function(index, row) {
			$('#evntNo').val(row['evntNo']);
			$('#evntNm').val(row['evntNm']);
			P_MODAL7.modal('hide');
		},
		// no-data 표출 메시지
		emptyMsg: '등록된 투자설명회가 없습니다.',
		// 목록로딩 완료후 처리
		onLoadSuccess: function(data) {
		}
    });

    //========================================================//
    // FORM ELEMENTS 정의
    //--------------------------------------------------------//
	// 성별 라디오버튼
	$('#appSexdstn').appSelectBox({
		form:   'radio', 
		name:   'sexdstn',
		colCls: '',
		wrapCls: 'check-radio-box',
		params: {upCdId: CODE.SXDST_SE.code},
		click: function() { return false; },
	});
	// 상담센터
	$('#dscsnCntrCd').appComboBox({
		params: {upCdId: CODE.DSCSN_CNTR.code},
		loadFilter: function(data) {
			data.unshift(COMBO.INIT_SELECT);
			return data;
		},
	});
	// 프로그램명(진행현황등록)
	P_PRGRMSE = $('#prgrmSeCd').appComboBox({
		params: {upCdId: CODE.PRGRM_SE.code},
		loadFilter: function(data) {
			data = data.filter(function(e) { return e.code != 'SB01'; }); // 상담 제외
			data.unshift(COMBO.INIT_SELECT);
			return data;
		},
		change: function(data) {
			hideAllPrgrsDiv();  // 입력란 모두 숨기기
			resetModal({all: false,}); // 프로그램명/진행상태 제외 초기화
			
			var value = data.target.value;
			if (value === 'SB03') { // 맞춤형 컨설팅
				P_PRCSCYCL.show();
			}
				
			P_PRGRS.val(''); // 진행현황등록 진행상태 초기화
		},
	});
	// 진행상태(진행현황등록)
	P_PRGRS = $('#prgrsDetailSttsCd').appComboBox({
		params: {upCdId: CODE.PRGRS_DETAIL_STTS.code},
		loadFilter: function(data) {
			data.unshift(COMBO.INIT_SELECT);
			return data;
		},
		change: function(data) {
			resetModal({all: false,}); // 프로그램명/진행상태 제외 초기화
			var value = data.target.value;
			if (P_PRGRMSE.val() != '') {
				if (value === '10') {		// 생략
					P_FIXDIV.hide();
					P_FTFYN.hide();
				} else if (value === '20') { // 배정
					P_FIXDIV.show();
					P_FTFYN.hide();
					if (P_PRGRMSE.val() === 'SB04') // IR설명회
						P_EVENT.show();
				} else if (value === '30') { // 중단
					P_FIXDIV.hide();
					P_FTFYN.hide();
				} else if (value === '40') { // 완료
					P_FIXDIV.show();
					if (P_PRGRMSE.val() === 'SB03') // 맞춤형 컨설팅
						P_FTFYN.show();
					if (P_PRGRMSE.val() === 'SB04') // IR설명회
						P_EVENT.show();
				}	
			}
		}
	});
	// 차수
	$('#prcsCycl').appComboBox({
		params: {upCdId: CODE.PRCS_CYCL.code},
		loadFilter: function(data) {
			data.unshift(COMBO.INIT_SELECT);
			return data;
		},
	});
	
	//========================================================//
    // 모달 DIV 정의
    //--------------------------------------------------------//
	P_MODAL1_BTN_DIV	= $("[data-bs-target='#myModal1']").parent();
	P_MODAL2_BTN_DIV	= $("[data-bs-target='#myModal2']").parent();
	P_MODAL3_BTN_DIV	= $("[data-bs-target='#myModal3']").parent();
	P_MODAL4_BTN_DIV	= $("[data-bs-target='#myModal4']").parent();
	P_MODAL5_BTN_DIV	= $("[data-bs-target='#myModal5']").parent();
	P_MODAL6_BTN_DIV	= $("[data-bs-target='#myModal6']").parent();
	P_MODAL8_BTN_DIV	= $("[data-bs-target='#myModal8']").parent();
	
	P_MODAL1			= $('#myModal1'); // 상담완료 모달
	P_MODAL2			= $('#myModal2'); // 진행현황등록 모달
	P_MODAL3			= $('#myModal3'); // 반려 모달
	P_MODAL4			= $('#myModal4'); // 보완요청 모달
	P_MODAL5			= $('#myModal5'); // 심사완료 모달
	P_MODAL6			= $('#myModal6'); // 사업종료 모달
	P_MODAL7			= $('#myModal7'); // 투자설명회 모달
	P_MODAL8			= $('#myModal8'); // 
	P_EVENTBTN			= $('#eventDiv');
	
	//========================================================//
    // 인풋박스 정의
    //--------------------------------------------------------//
	P_BZFLD = $('#appBizFldBox').appSelectBox({
		form: 'checkbox',
		name: 'bizFld',
		params: {upCdId: CODE.BIZ_RLM.code},
		callback: function() {
			P_BZFLD.setSplitValues(P_BZFLD.data('value'), ',')
		},
		click: function() { return false; },
	});
	
	//========================================================//
    // 진행현황등록 입력란 DIV 정의
    //--------------------------------------------------------//
	P_PRCSCYCL = $("div.prcsCycl");
	P_FTFYN    = $("div.ftfYn");
	P_FIXDIV   = $("div.fixed-row");
	P_EVENT	   = $("div.event");
    
    //========================================================//
    // VALIDATION RULE 정의
    //--------------------------------------------------------//
    P_RFORM.validate({
        debug: false,
        onsubmit: false,
        onfocusout: false,
        // 검증룰 정의
        rules: {
            prgrmNm      : {required: true,
                            maxlength: 100},
			sprtBgngYmd  : {required: true,
			                date: true},
			sprtEndYmd   : {required: true,
			                date: true},
			useYn        : {required: false},
        },
        // 검증메세지 정의
        messages: {
            prgrmNm       : {required: '프로그램명은 필수 입력 사항입니다.',
                             maxlength: jQuery.validator.format('최대 {0}자 이하')},
			sprtBgngYmd   : {required: '지원시작기간은 필수 입력 사항입니다.',
                             date: '지원시작기간을 형식에 맞게 입력해주세요.'},
			sprtEndYmd    : {required: '지원종료기간은 필수 입력 사항입니다.',
                             date: '지원종료기간을 형식에 맞게 입력해주세요.'},
            useYn         : {required: '표출 여부는 필수 입력 사항입니다.'},
        },
        invalidHandler: validateHandler,
        errorPlacement: validatePlacement
    });
	
	// 신청현황관리 삭제하기
    //--------------------------------------------------------//
    function doRemove() {
	
        $.commMsg.confirm("정말로 삭제하시겠습니까?", function() {
			$.formUtil.toForm({
				mode     : MODE.REMOVE, // 삭제모드
			}, P_RFORM);
	
            // 등록폼을 AJAX로 저장처리
            P_RFORM.ajaxForm({
                url: getUrl('/adm/support/support/saveInvtSprtAply.do'),
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
	
	$('.datepicker-input').datepicker(OPTIONS.DATEPICKER);

	// 지원사업 진행현황 검색처리
    //--------------------------------------------------------//
    function doSearchPrgrs() {
		// 선택된 항목 CLEAR
		P_GRID2.datagrid('clearSelections');
		
        // 검색폼 데이터 객체화
        var obj = P_RFORM.serializeObject();
        // 그리드 목록조회 URL
        P_GRID2.datagrid('options')['url'] = getUrl('/adm/support/support/getListSprtBizPrgre.do');

        // 검색폼 그리드 검색
        P_GRID2.datagrid('load', obj);

		P_GRID2.datagrid('resize');

        return false;
    }

	// 투자설명회 검색처리
    //--------------------------------------------------------//
    function doSearchEvent() {
		P_MODAL7.modal('show');
		
		// 선택된 항목 CLEAR
		P_GRID3.datagrid('clearSelections');
		
        // 검색폼 데이터 객체화
        var obj = P_MFORM7.serializeObject();
        // 그리드 목록조회 URL
        P_GRID3.datagrid('options')['url'] = getUrl('/adm/invest/event/getListEvent.do');

        // 검색폼 그리드 검색
        P_GRID3.datagrid('load', obj);

        return true;
    }
	
	// 진행현황등록 입력란 모두 숨기기
    //--------------------------------------------------------//
	function hideAllPrgrsDiv() {
		P_PRCSCYCL.hide();
		P_FTFYN.hide();
		P_FIXDIV.hide();
		P_EVENT.hide();
	}
	
	// 모달 리셋
    //--------------------------------------------------------//
	function resetModal(opts) {
		
		opts = opts ? opts : {};
		
		if (opts.all) {
			P_MFORM1.form('reset');
			P_MFORM2.form('reset');
			P_MFORM3.form('reset');
			P_MFORM4.form('reset');
			P_MFORM5.form('reset');
			P_MFORM6.form('reset');
			P_MFORM7.form('reset');
			P_MFORM8.form('reset');
		} else {		// 진행현황등록 프로그램명, 진행상태 제외하고 초기화
			$.formUtil.toForm({
				evntNo  		: '',
				evntNm  		: '',
				prcsCycl		: '',
				ftfYn			: '',
				picNm			: '',
				picTelnoModal2	: '',
				picTelnoModal21 : '',
				picTelnoModal22 : '',
				picTelnoModal23 : '',
				prcsYmd			: '',
				prgrsCn			: '',
			}, P_MFORM2);
		}
	}
	
	// 모든 모달 닫기
    //--------------------------------------------------------//
	function closeAllModals() {
		$(".modal").modal('hide');
	}
	
	// 모달 버튼 DIV 전부 숨기기
    //--------------------------------------------------------//
	function resetModalBtns() {
		P_MODAL1_BTN_DIV.hide();
		P_MODAL2_BTN_DIV.hide();
		P_MODAL3_BTN_DIV.hide();
		P_MODAL4_BTN_DIV.hide();
		P_MODAL5_BTN_DIV.hide();
		P_MODAL6_BTN_DIV.hide();
		P_MODAL8_BTN_DIV.hide();
		return true;
	}
	
	// 진행현황등록 저장하기
    //--------------------------------------------------------//
	function doSavePrgrs() {
		var form = $(this).closest("form");
		
		// 휴대전화번호 병합
		$.formUtil.mergeData('picTelnoModal2', 'phone', 3);
		$.formUtil.mergeData('picTelnoModal5', 'phone', 3);
		
		var rules = {};
		var messages = {};
		if (form[0] == P_MFORM1[0]) {		// 상담완료 폼 
			rules = {
	            dscsnCntrCd : {required: true},
				picNm       : {required: true,
				               maxlength: 20},
				prcsYmd     : {required: true,
				               date: true},
				prgrsCn     : {required: true,
				               maxlength: 300},
	        };
			messages = {
	            dscsnCntrCd : {required: '상담센터는 필수 입력사항입니다.'},
				picNm       : {required: '상담담당자는 필수 입력사항입니다.',
	                           maxlength: jQuery.validator.format('상담담당자는 최대 {0}자 입니다.')},
				prcsYmd     : {required: '상담일은 필수 입력사항입니다.',
	                           date: '상담일을 형식에 맞게 입력해주세요.'},
	            prgrsCn     : {required: '상담내용은 필수 입력사항입니다.',
                               maxlength: jQuery.validator.format('상담내용은 최대 {0}자 입니다.')},
	        };
		} else if (form[0] == P_MFORM2[0]) {	// 진행현황등록 폼
			rules = {
				prgrmSeCd         : {required: true},
				prgrsDetailSttsCd : {required: true},
				prgrsCn           : {required: true,
				                     maxlength: 300}
			};
			messages = {
				prgrmSeCd         : {required: '프로그램명은 필수 입력사항입니다.'},
				prgrsDetailSttsCd : {required: '진행상태는 필수 입력사항입니다.'},
				prgrsCn           : {required: '코멘트는 필수 입력사항입니다.',
				                     maxlength: jQuery.validator.format('코멘트는 최대 {0}자 입니다.')},
			};

			var prgrmSeCd			= $.formUtil.getValue(form, 'prgrmSeCd');
			var prgrsDetailSttsCd   = $.formUtil.getValue(form, 'prgrsDetailSttsCd');
			
			if (prgrsDetailSttsCd == '20' || prgrsDetailSttsCd == '40') { // 배정, 완료
				rules['picNm']				= {required: true,
				                               maxlength: 20, };
				rules['picTelnoModal2']		= {required: true,
				                               phoneNo: true,
                                               maxlength: 20, };
				rules['prcsYmd']			= {required: true,
				                               date: true, };
				messages['picNm']			= {required: '담당자는 필수 입력사항입니다.',
				                               maxlength: jQuery.validator.format('담당자는 최대 {0}자 입니다.'), };
				messages['picTelnoModal2']	= {required: '담당자 연락처는 필수 입력사항입니다.',
				                               phoneNo: '담당자 연락처의 형식이 올바르지 않습니다.',
                                               maxlength: jQuery.validator.format('담당자 연락처는 최대 {0}자 입니다.'), };
				messages['prcsYmd']			= {required: '처리일자는 필수 입력사항입니다.',
				                               date: '처리일자의 형식이 올바르지 않습니다.', };
				if (prgrmSeCd == 'SB04') {
					rules['evntNo']		= {required: true,}
					messages['evntNo']	= {required: '투자설명회는 필수 입력사항입니다.',}
				}
			}
			
			if (prgrmSeCd == 'SB03') {
				rules['prcsCycl']		= {required: true,}
				messages['prcsCycl']	= {required: '차수는 필수 입력사항입니다.',}
				
				if (prgrsDetailSttsCd == '40') {
					rules['ftfYn']		= {required: true,}
					messages['ftfYn']	= {required: '대면여부는 필수 입력사항입니다.',}
				}
			}
		} else if (form[0] == P_MFORM3[0]) { // 반려 폼
			rules = {
				prgrsCn : {required: true,
					       maxlength: 300},
			};
			messages = {
				prgrsCn : {required: '코멘트는 필수 입력사항입니다.',
				           maxlength: jQuery.validator.format('코멘트는 최대 {0}자 입니다')},
			};
		} else if (form[0] == P_MFORM4[0]) { // 보완요청 폼
			rules = {
				prgrsCn : {required: true,
					       maxlength: 300},
			};
			messages = {
				prgrsCn : {required: '코멘트는 필수 입력사항입니다.',
				           maxlength: jQuery.validator.format('코멘트는 최대 {0}자 입니다.')},
			};
		} else if (form[0] == P_MFORM5[0]) { // 심사완료 폼
			rules = {
				picNm          : {required: true,
				                  maxlength: 20},
				picTelnoModal5 : {required: true,
				                  maxlength: 12},
				prgrsCn        : {required: true,
					              maxlength: 300},
			};
			messages = {
				picNm          : {required: '담당자는 필수 입력사항입니다.',
				                  maxlength: jQuery.validator.format('담당자는 최대 {0}자 입니다.')},
				picTelnoModal5 : {required: '담당자연락처는 필수 입력사항입니다.',
				                  maxlength: jQuery.validator.format('담당자연락처는 최대 {0}자 입니다.')},
				prgrsCn        : {required: '코멘트는 필수 입력사항입니다.',
				                  maxlength: jQuery.validator.format('코멘트는 최대 {0}자 입니다.')},
			};
		} else if (form[0] == P_MFORM6[0]) { // 사업종료 폼
			rules = {
				prcsYmd : {required: true,
					       date: true},
			};
			messages = {
				prcsYmd : {required: '지원사업 완료일자는 필수 입력사항입니다.',
				           date: '지원사업 완료일자의 형식이 올바르지 않습니다.'},
			};
		}
		
		var validation = form.validate({
	        debug: false,
	        onsubmit: false,
	        onfocusout: false,
	        // 검증룰 정의
	        rules: rules,
	        // 검증메세지 정의
	        messages: messages,
	        invalidHandler: validateHandler,
	        errorPlacement: validatePlacement
	    });
		
		// 등록폼의 VALIDATION 기능 활성화
        if (validation.settings)
            validation.settings.ignore = false;

        //FORM VALIDATION
        if (form.valid() === false)
            return false;

        $.commMsg.confirm("저장하시겠습니까?", function() {
            // 등록폼을 AJAX로 저장처리
            form.ajaxForm({
                url: getUrl('/adm/support/support/saveSprtBizPrgre.do'),
                // 오류시 처리로직
                error: $.ajaxUtil.error,
                // 저장후 처리로직
                success: function(ret) {
					$.commMsg.success(ret, '성공적으로 저장되었습니다.', function() {
						P_MODAL.doSearch();
                        doSearchPrgrs();
						closeAllModals();
						P_MODAL.doOpenSelect({sprtAplyNo: P_SPRT_APLY_NO});
					});
                }
            }).submit();
        });
        return false;
	}
	
	function hideModalBackdrop() {
		$('.modal-backdrop').hide();
	}
	
	// registForm 의 값으로 모달창 동기화
	function modalSync() {
		$.formUtil.toForm({sprtAplyNo: P_SPRT_APLY_NO, sprtAplySeCd: P_SPRT_APLY_SE_CD}, P_MFORM1);
		$.formUtil.toForm({sprtAplyNo: P_SPRT_APLY_NO, sprtAplySeCd: P_SPRT_APLY_SE_CD}, P_MFORM2);
		$.formUtil.toForm({sprtAplyNo: P_SPRT_APLY_NO, sprtAplySeCd: P_SPRT_APLY_SE_CD}, P_MFORM3);
		$.formUtil.toForm({sprtAplyNo: P_SPRT_APLY_NO, sprtAplySeCd: P_SPRT_APLY_SE_CD}, P_MFORM4);
		$.formUtil.toForm({sprtAplyNo: P_SPRT_APLY_NO, sprtAplySeCd: P_SPRT_APLY_SE_CD}, P_MFORM5);
		$.formUtil.toForm({sprtAplyNo: P_SPRT_APLY_NO, sprtAplySeCd: P_SPRT_APLY_SE_CD}, P_MFORM6);
	}
	
	// 투자설명회 모달 오픈시 이벤트
	P_MODAL7.on('shown.bs.modal', function (e) {
		P_GRID3.datagrid('resize');
	})
	
	// 진행현황등록 모달 오픈시 backdrop 숨김
	P_MODAL1.on('shown.bs.modal', hideModalBackdrop);
	P_MODAL2.on('shown.bs.modal', hideModalBackdrop);
	P_MODAL3.on('shown.bs.modal', hideModalBackdrop);
	P_MODAL4.on('shown.bs.modal', hideModalBackdrop);
	P_MODAL5.on('shown.bs.modal', hideModalBackdrop);
	P_MODAL6.on('shown.bs.modal', hideModalBackdrop);
	P_MODAL7.on('shown.bs.modal', hideModalBackdrop);
	P_MODAL8.on('shown.bs.modal', hideModalBackdrop);
	
	// 모달 버튼 클릭시 이벤트 처리
	P_MODAL1_BTN_DIV.find("a").bind('click', resetModal);
	P_MODAL2_BTN_DIV.find("a").bind('click', resetModal);
	P_MODAL3_BTN_DIV.find("a").bind('click', resetModal);
	P_MODAL4_BTN_DIV.find("a").bind('click', resetModal);
	P_MODAL5_BTN_DIV.find("a").bind('click', resetModal);
	P_MODAL6_BTN_DIV.find("a").bind('click', resetModal);
	P_MODAL8_BTN_DIV.find("a").bind('click', resetModal);
	
	// 지원사업 진행현황 저장버튼 클릭시 이벤트 처리
    $('[id^="myModalSave"]').bind('click', doSavePrgrs);
	// 신청서 삭제버튼 클릭시 이벤트 처리
    $('#btnRemove').bind('click', doRemove);
					
	doSearchPrgrs();			// 지원사업 진행현황 호출
	
	var value;
	if (P_SPRT_APLY_SE_CD == 'SB')	// 투자유치 전만 프로그램명, 상세정보를 표출함.
		value = 'showColumn';
	else
		value = 'hideColumn';
	P_GRID2.datagrid(value, 'prgrmSeCdNm');
	P_GRID2.datagrid(value, 'detailInfo');
	
	// 투자설명회명 클릭시 이벤트 처리
	P_EVENTBTN.bind('click', doSearchEvent);
	
	// 모든 모달 닫기
	closeAllModals();
	
	// 진행현황등록 입력란 숨김 처리
	hideAllPrgrsDiv();
	
	// 지원신청번호, 지원신청구분 세팅
	modalSync();
});