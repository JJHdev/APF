/**
*******************************************************************************
***    명칭: modalFundForm.js
***    설명: 투자정보관리 - 모태펀드등록 화면
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.06.02      LHB      First Coding.
*******************************************************************************
**/
$(function() {

    //========================================================//
    // 화면 스크립트 내에 사용할 객체,상수 정의
    //--------------------------------------------------------//
    let P_RFORM		= $('#registForm'      );			// 등록폼 객체
	let P_MFORM_NO	= $('#mmbsForm-nologin');			// 비회원 조합원폼 객체
	let P_MFORM		= $('#mmbsForm'        );			// 조합원폼 객체
    let P_MODE		= $('#mode'            ).val();		// 처리 모드
    let P_FUND_NO	= $('#fundNo'          ).val();		// 게시글 키정보
	let P_FLD_CD	= $('#invtFldCdHidden' ).val();		// 게시글 키정보

	let P_GRID2		= $('#appGridMmbs' );		// 조합원 목록 추가그리드 객체
	let P_MODAL1	= $('#myModal1');			// 조합원 등록 모달
	let P_MODAL1_BTN= $("[data-bs-target='#myModal1']");	// 조합원 목록 추가 버튼
	
	let DATEPICKER	= $('.datepickerMonth-input'); // DATEPICKER

	//========================================================//
    // 목록 GRID 정의 (easyui DATAGRID)
    //--------------------------------------------------------//
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
        singleSelect: false,
        // 그리드 페이징처리 여부
        pagination:true,
        // 그리드 행번호 표시여부
        rownumbers:true,
        // 한 페이지 출력수
        pageSize: 30,
        // 칼럼정의
        columns: [[
			{field:'fundNo'   ,width:80, halign:'center',align:'center',title:'펀드번호', hidden: true},
			{field:'bzentyNo' ,width:80, halign:'center',align:'center',title:'업체번호', hidden: true},
			{field:'chk'	  ,width:80, halign:'center',align:'center',checkbox: true,},
			{field:'bzentyNm' ,width:120,halign:'center',align:'center',title:'소속',},
            {field:'brno'     ,width:100,halign:'center',align:'center',title:'사업자번호'},
            {field:'rprsvNm'  ,width:60, halign:'center',align:'center',title:'대표자'},
            {field:'fndnYmd'  ,width:100,halign:'center',align:'center',title:'설립일'},
        ]],
        // 행선택시 수정하기
        onSelect: function(index, row) {
		},
		// no-data 표출 메시지
		emptyMsg: '조합원 목록이 없습니다.',
		// 목록로딩 완료후 처리
		onLoadSuccess: function(data) {
		}
    });

    //========================================================//
    // FORM ELEMENTS 정의
    //--------------------------------------------------------//
	// 투자분야 콤보박스
	var fldParams = {};
	if (P_MODE == MODE.UPDATE) {
		fldParams = {invtFldCd : P_FLD_CD}
	}
	$('#invtFldCd').appComboBox({
		url: getUrl('/com/common/getInvtRlmComboCode.do'),
		prompt: '투자분야',
		name: 'invtFldCd',
		params: fldParams,
		loadFilter: function(data) {
			data.unshift(COMBO.INIT_SELECT);
			return data;
		},
	});
	// 게시여부 라디오버튼
	$('#appUseYn').appSwitchBox({id: 'useYn', name: 'useYn', value: 'Y'});
    
    //========================================================//
    // VALIDATION RULE 정의
    //--------------------------------------------------------//
    P_RFORM.validate({
        debug: false,
        onsubmit: false,
        onfocusout: false,
        // 검증룰 정의
        rules: {
            fundNm       : {required: true,
                            maxlength: 100},
            invtFldCd    : {required: true},
			orgzYmd      : {required: true,
			                date: true},
			invtBgngYmd  : {required: true,
			                date: true},
			invtEndYmd   : {required: true,
			                date: true,
                            afterDate: '#invtBgngYmd'},
			fundOperScale: {required: true,
			                numeric: true},
            rprsTelno    : {required: true,
                            phoneNo: true},
			rprsHmpgAddr : {url: true},
			useYn        : {required: false},
        },
        // 검증메세지 정의
        messages: {
            fundNm        : {required: '펀드명은 필수 입력 사항입니다.',
                             maxlength: jQuery.validator.format('최대 {0}자 이하')},
            invtFldCd     : {required: '투자분야는 필수 선택 사항입니다.'},
            orgzYmd       : {required: '결성일은 필수 입력 사항입니다.',
                             date: '결성일을 형식에 맞게 입력해주세요.'},
			invtBgngYmd   : {required: '투자시작기간은 필수 입력 사항입니다.',
                             date: '투자시작기간을 형식에 맞게 입력해주세요.'},
			invtEndYmd    : {required: '투자종료기간은 필수 입력 사항입니다.',
                             date: '투자종료기간을 형식에 맞게 입력해주세요.',
                             afterDate: '투자종료기간은 투자시작기간보다 이후여야 합니다.'},
            fundOperScale : {required: '펀드운영규모는 필수 입력 사항입니다.',
                             numeric: '펀드운영규모는 정수만 입력할 수 있습니다.'},
            rprsTelno     : {required: '대표연락처는 필수 입력 사항입니다.',
                             phoneNo: '대표연락처를 형식에 맞게 입력해주세요.'},
			rprsHmpgAddr  : {url: '대표홈페이지를 형식에 맞게 입력해주세요.'},
            useYn         : {required: '플랫폼 게시는 필수 입력 사항입니다.'},
        },
        invalidHandler: validateHandler,
        errorPlacement: validatePlacement
    });
	// 숫자만입력
	bindOnlyNumber($("#fundOperScale"));
	
	P_MFORM_NO.validate({
        debug: false,
        onsubmit: false,
        onfocusout: false,
        // 검증룰 정의
        rules: {
            invtBzentyNm : {required: true,
                            maxlength: 100},
			brno         : {required: true,
			                maxlength: 10},
        },
        // 검증메세지 정의
        messages: {
            invtBzentyNm : {required: '조합원명은 필수 입력사항입니다.',
                            maxlength: jQuery.validator.format('최대 {0}자 이하')},
			brno         : {required: '사업자 번호는 필수 입력사항입니다.',
			                maxlength: jQuery.validator.format('최대 {0}자 이하')},
        },
        invalidHandler: validateHandler,
        errorPlacement: validatePlacement
    });
	bindOnlyNumber($("#brno1, #brno2, #brno3"));
	
	// 모태펀드 저장하기
    //--------------------------------------------------------//
    function doSave() {

        // 등록폼의 VALIDATION 기능 활성화
        if (P_RFORM.validate().settings)
            P_RFORM.validate().settings.ignore = false;

        //FORM VALIDATION
        if (P_RFORM.valid() === false)
            return false;

        $.commMsg.confirm("저장하시겠습니까?", function() {
            // 등록폼을 AJAX로 저장처리
            P_RFORM.ajaxForm({
                url: getUrl('/adm/invest/fund/saveFund.do'),
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

	// 모태펀드 삭제하기
    //--------------------------------------------------------//
    function doRemove() {
	
        $.commMsg.confirm("정말로 삭제하시겠습니까?", function() {
			$.formUtil.toForm({
				mode     : MODE.REMOVE, // 삭제모드
			}, P_RFORM);
	
            // 등록폼을 AJAX로 저장처리
            P_RFORM.ajaxForm({
                url: getUrl('/adm/invest/fund/saveFund.do'),
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
	
	// 조합원 등록버튼 클릭시 이벤트 처리
    $('#btnSaveInvt'       ).bind('click', doSaveInvt);
	$('#btnSaveInvtNoLogin').bind('click', doSaveInvtNoLogin);
	
	//$('.datepicker-input').datepicker(OPTIONS.DATEPICKER_MONTH);
	DATEPICKER.datepicker(OPTIONS.DATEPICKER_MONTH_ANYDATE);
	
	// 조합원 등록 팝업 오픈
    //--------------------------------------------------------//
	function openModal1() {
		P_MODAL1.modal('show');
		doSearchInvtAll({});
	}
	
	// 전체 조합원 목록 검색처리
    //--------------------------------------------------------//
    function doSearchInvtAll(obj) {
		// 선택된 항목 CLEAR
		P_GRID2.datagrid('clearSelections');
		
        // 검색폼 데이터 객체화
        //var obj = P_MFORM7.serializeObject();
        // 그리드 목록조회 URL
        P_GRID2.datagrid('options')['url'] = getUrl('/adm/invest/fund/getListInvt.do');

        // 검색폼 그리드 검색
        P_GRID2.datagrid('load', obj);

        return true;
    }

	// 비회원 조합원 저장하기
    //--------------------------------------------------------//
    function doSaveInvtNoLogin() {
		// 사업자 번호 세팅
		$.formUtil.mergeData('brno', 'bzno', 3);
		// 펀드번호 세팅
		var fundNo = $.formUtil.getValue(P_RFORM, 'fundNo')
		$.formUtil.setValue(P_MFORM_NO, 'fundNo', fundNo);

        // 등록폼의 VALIDATION 기능 활성화
        if (P_MFORM_NO.validate().settings)
            P_MFORM_NO.validate().settings.ignore = false;

        //FORM VALIDATION
        if (P_MFORM_NO.valid() === false)
            return false;

        $.commMsg.confirm("저장하시겠습니까?", function() {
            // 등록폼을 AJAX로 저장처리
            P_MFORM_NO.ajaxForm({
                url: getUrl('/adm/invest/fund/saveFundInvstr.do'),
                // 오류시 처리로직
                error: $.ajaxUtil.error,
                // 저장후 처리로직
                success: function(ret) {
					$.commMsg.success(ret, '성공적으로 저장되었습니다.', function() {
						P_MODAL.doOpenSelect({fundNo: fundNo, });
					});
                }
            }).submit();
        });

        return false;
    }

	// 조합원 저장하기
    //--------------------------------------------------------//
    function doSaveInvt() {
		var data = P_GRID2.datagrid('getChecked');
		var bzentyNoArr = data.map(function(e) { return e.bzentyNo; });
		var fundNo = $.formUtil.getValue(P_RFORM, 'fundNo');
		
		if(bzentyNoArr.length < 1) {
			$.commMsg.alert('추가할 조합원을 선택해주세요.');
			return false;
		}

		$.commMsg.confirm("저장하시겠습니까?", function() {
			// form 안에 bzentyNo의 name을 가진 input 태그 세팅
			var html = '';
			bzentyNoArr.forEach(function(e, i) {
				html += '<input type="hidden" name="bzentyNoArr" value="' + e + '"/>';
			});
			html += '<input type="hidden" name="mode" value="I"/>';
			html += '<input type="hidden" name="fundNo" value="' + fundNo + '"/>';
			P_MFORM.html(html);
	
            // 등록폼을 AJAX로 저장처리
            P_MFORM.ajaxForm({
                url: getUrl('/adm/invest/fund/saveFundInvstr.do'),
                // 오류시 처리로직
                error: $.ajaxUtil.error,
                // 저장후 처리로직
                success: function(ret) {
					$.commMsg.success(ret, '성공적으로 저장되었습니다.', function() {
						P_MODAL1.modal('hide');
						P_MODAL.doOpenSelect({fundNo: fundNo, });
					});
                }
            }).submit();
        });

        return false;
    }

	// 저장버튼 클릭시 이벤트 처리
    $('#btnSave'  ).bind('click', doSave);
	// 삭제버튼 클릭시 이벤트 처리
	$('#btnRemove').bind('click', doRemove);

	// 조합원 목록 모달 팝업시 datagrid 리사이즈
	P_MODAL1.on('shown.bs.modal', function (e) {
		P_GRID2.datagrid('resize');
		//$('.modal-backdrop').hide();
	});
	
	P_MODAL1_BTN.bind('click', openModal1);
	
	
	
	$('#btnSearchInvt').bind('click', function() {
		doSearchInvtAll({srchTextInvt : $('#srchTextInvt').val()})
	})
	
	// modal backdrop의 rootElement를 해당 모달로 변경해서 정상적으로 표출되도록 함
	new bootstrap.Modal('#myModal1')._backdrop._config.rootElement = $('.detail-pop-1');
	
	// 검색어 입력 엔터 이벤트 처리
	bindEnter($('#srchTextInvt'), $('#btnSearchInvt'));
	
	// 대표연락처 하이픈 이벤트 처리
	bindPhoneHyphen($('#rprsTelno'));
	$('#rprsTelno').trigger('keyup');
	
	// 조합원 목록 검색
	//doSearchInvt({ fundNo: P_FUND_NO });
});