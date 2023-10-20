/**
***************************************************************************************************
*** 파일명 : openInvtSprtAply.js
*** 설명글 : 지원사업관리-신청현황관리 화면 스크립트
***
*** -----------------------------    Modified Log   -----------------------------------------------
*** ver             date                author                  description
*** -----------------------------------------------------------------------------------------------
*** 1.0         2023.05.21               LHB                      신규 작성
*** 1.1         2023.06.07               LHB          파일명 listInvtSprtAply -> openInvtSprtAply 변경
***************************************************************************************************
**/
$(function() {
    //========================================================//
    // 화면 스크립트 내에 사용할 객체,상수 정의
    //--------------------------------------------------------//
	var P_TITLE  = '신청현황관리'   ; // 화면 제목
    var P_GRID   = $('#appGrid'   ); // 그리드 객체
	var P_GRID3  = $('#grid-event'); // 투자설명회 그리드 객체
    var P_SFORM  = $('#searchForm'); // 검색폼 객체
    var P_RFORM  = $('#registForm'); // 등록폼 객체
	
	// 검색상자 DIV
	var P_SRCH_PCF = false; // 프로그램분류
	var P_SRCH_PN  = false; // 프로그램명

    //========================================================//
    // 목록 GRID 정의 (EasyUI datagrid)
    //--------------------------------------------------------//
    P_GRID.appBbsGrid({
		// 목록 KEY필드
		idField:    'sprtAplyNo',
		// 목록 제목
		title:		'신청현황관리',
		// 검색 URL
		url:		getUrl('/adm/support/support/getListInvtSprtAply.do'),
        // 조회 파라미터 전달
		queryParams: {
	    },
		//목록 검색 페이징 객체
		pagination: { display: 20 },
		// 페이지 표시 객체
		paging:     '#appGridPagination',
		// 검색결과 표시 객체
		result:     '#appGridResult',
		// 검색결과 메세지
		resultText: '<em class="ml-4px">{total}</em>개의 신청서 목록이 있습니다.',
		// 리스트옵션
		listOptions: {
			// 검색결과 없을경우 메세지
			emptyText:  '검색된 {title}가 없습니다.',
			// 목록칼럼 너비
			colgroup: ['30px','60px','90px','50px','130px','80px','80px','80px','70px','50px'],
			// 목록칼럼 정의
			columns: [
				{rownumbers:true		,cls:'app-c', label:'번호'},
				{name:'rgtrNm'			,cls:'app-l', label:'성명'},
				{name:'rgtrId'			,cls:'app-l', label:'아이디'},
				{name:'bzentySeCdNm'	,cls:'app-c', label:'회원유형'},
				{name:'bzentyNm'		,cls:'app-l', label:'소속'},
				{name:'sprtAplySeCdNm'	,cls:'app-c', label:'지원사업명'},
				{name:'prgrmClsfCdNm'	,cls:'app-c', label:'프로그램분류'},
				{name:'prgrmNm'			,cls:'app-c', label:'프로그램명'},
				{name:'rcptYmd'			,cls:'app-c', label:'신청서 접수일'},
				{name:'prgrsSttsCdNm'	,cls:'app-c', label:'처리결과'},
			],
			// 목록형인 경우 기본 스타일시트
			//gridCls: "table-box-1",
	        // 행선택시 상세조회
	        select: doSelect	
		},
    }).appBbsGrid('init');

    //========================================================//
    // 콤보박스 정의
    //--------------------------------------------------------//

	/* 검색조건상자 */
	// 지원신청구분
	$('#sprtAplySeCd').appComboBox({
		params: {upCdId: CODE.SPRT_APLY_SE.code},
		loadFilter: function(data) {
			data.unshift(COMBO.INIT_ALL);
			return data;
		},
		change: function(data) {
			var value = data.target.value;
			if (value[1]) {
				$('#prgrsSttsCd').appComboBox({
					params: {upCdId: CODE.PRGRS_STTS1.code},
					loadFilter: function(data2) {
						var arr = data2.filter(function(e) { return e.code.indexOf(value[1]) > -1; }); // 사업에 맞는 코드값 세팅
						arr.unshift(COMBO.INIT_ALL);
						return arr;
					},
				});
				
				P_SRCH_PCF.hide();
				P_SRCH_PCF.find('select').val('');
				P_SRCH_PN.hide();
				P_SRCH_PN.find('select').val('');
				
				if (value === 'SB') {			// 투자유치 전
				} else if (value === 'SA') {	// 투자유치 후
					P_SRCH_PCF.show();
					P_SRCH_PN.show();
				} else if (value === 'SC') {	// 크라우드 펀딩
					P_SRCH_PN.show();
					// 프로그램명, 크라우드 펀딩은 프로그램분류가 존재하지 않음
					$('#prgrmNo').appComboBox({
						url: getUrl('/adm/support/support/getListInvtSprt.do'),
						params: {sprtAplySeCd: value},
						loadFilter: function(data2) {
							var prgrmClsfCdArr = data2.rows.map(function(e) { return {code: e.prgrmNo, text: e.prgrmNm }; } );
							prgrmClsfCdArr.unshift(COMBO.INIT_ALL);
							return prgrmClsfCdArr;
						},
					});
				}
			} else {
				$('#prgrsSttsCd').html('<option value="">전체</option>');
			}
		}
	});
	// 프로그램분류
	$('#prgrmClsfCd').appComboBox({
		params: {upCdId: CODE.PROG_TY.code},
		loadFilter: function(data) {
			data.unshift(COMBO.INIT_ALL);
			return data;
		},
		change: function(data) {
			var prgrmClsfCd = data.target.value;
			// 프로그램명
			$('#prgrmNo').appComboBox({
				url: getUrl('/adm/support/support/getListInvtSprt.do'),
				params: {prgrmClsfCd: prgrmClsfCd},
				loadFilter: function(data2) {
					var prgrmClsfCdArr = data2.rows.map(function(e) { return {code: e.prgrmNo, text: e.prgrmNm }; } );
					prgrmClsfCdArr.unshift(COMBO.INIT_ALL);
					return prgrmClsfCdArr;
				},
			});
		},
	});
	
	//========================================================//
    // 검색상자 DIV 정의
    //--------------------------------------------------------//
	P_SRCH_PCF	= $('.search-prgrmClsfCd');	// 프로그램 분류
	P_SRCH_PN	= $('.search-prgrmNo');		// 프로그램명

    //========================================================//
    // 등록폼 VALIDATION RULE 정의
    //--------------------------------------------------------//

    // 신청현황관리 검색처리
    //--------------------------------------------------------//
    function doSearch() {
		if (CONDITION.yearCheck($.formUtil.getValue(P_SFORM, 'srchBgngYmd'),
		                        $.formUtil.getValue(P_SFORM, 'srchEndYmd' ),
                                '접수일 시작일과 종료일의 값이 올바르지 않습니다.')) {
			// 접수일이 정상적인 데이터인 경우
			
			// 검색폼 데이터 객체화
	        var obj = P_SFORM.serializeObject();
	
	        // 그리드 목록조회 URL
	        P_GRID.appBbsGrid('search', obj);
	
	        return false;
		}
    }

    // 신청현황관리 검색리셋
    //--------------------------------------------------------//
    function doReset() {
        // 검색폼 입력데이터 초기화
        P_SFORM.form('reset');
        // 검색폼 그리드 검색 처리
        doSearch();

        return false;
    }

    // 신청현황관리 수정하기
    //--------------------------------------------------------//
    function doSelect(row) {
    	if($('.detail-pop-1.w-ver1.trans') != null ){
			$('.detail-pop-1.w-ver1.trans').remove();
		}
		// 상세조회 모달팝업 오픈
		P_MODAL.doOpenSelect(row);
		
        return false;
    }

	// 신청현황관리 엑셀 다운로드
    //--------------------------------------------------------//
	function doExcel() {
		$.commMsg.alert('검색 건수가 많을 경우 시간이 오래 걸릴 수 있습니다.', function() {
			$.formUtil.submitForm(
				getUrl('/adm/support/support/downInvtSprtAply.do'),
				{ formId : "searchForm" }
			);
		});
		return false;
	}

	// 초기화버튼 클릭시 이벤트처리
    $('#btnReset' ).bind('click', doReset);
	// 검색버튼 클릭시 이벤트 처리
    $('#btnSearch').bind('click', doSearch);
	// 엑셀 다운로드 버튼 클릭시 이벤트 처리
	$('#btnDownload').bind('click', doExcel);

    // 검색 실행
	if (initParams) {
		P_GRID.appBbsGrid('search', initParams);	
	} else {
		doSearch();
	}

    // 검색어 입력 엔터 이벤트 처리
    bindEnter($('#srchText'), $('#btnSearch'));
});

//========================================================//
// 등록,수정,상세 모달팝업 정의
//  - 오픈된 모달창에서 사용하기 위해 외부에 정의함
//--------------------------------------------------------//
let P_MODAL = {
	// 모달팝업 객체
	modal: $('<div></div>'),
	// 상세조회 모달팝업 오픈
	doOpenSelect: function(row) {
		P_MODAL.modal.appModal({
			cls:        'w-ver1',
			title:      '신청현황관리 상세조회',
			url:        getUrl('/adm/support/support/modalInvtSprtAplyForm.do'),
			params:     JSON.stringify({
				sprtAplyNo : row['sprtAplyNo'],
			})
		}).open();
	},
	// 신규등록 모달팝업 오픈
	doOpenRegist: function(row) {
		P_MODAL.modal.appModal({
			cls:        'w-ver1',
			title:      '신청현황관리 신규등록',
			url:        getUrl('/adm/support/support/modalInvtSprtAplyForm.do'),
			params:     JSON.stringify({
				mode       : MODE.INSERT,
				sprtAplyNo : row['sprtAplyNo'],
			})
		}).open();
	},
	// 수정하기 모달팝업 오픈
	doOpenUpdate: function(row) {
		P_MODAL.modal.appModal({
			cls:        'w-ver1',
			title:      '신청현황관리 수정하기',
			url:        getUrl('/adm/support/support/modalInvtSprtAplyForm.do'),
			params:     JSON.stringify({
				mode         : MODE.UPDATE,
				sprtAplyNo : row['sprtAplyNo'],
			})
		}).open();
	},
	// [모달팝업 호출용] 모달팝업 닫기
	doClose: function() {
		if (P_MODAL.modal && 
			P_MODAL.modal.close)
			P_MODAL.modal.close();
	},
	// [모달팝업 호출용] 목록 재검색 및 팝업 닫기
	doSearch: function() {
		// 모달팝업 닫기
		//P_MODAL.modal.doClose();
		//P_MODAL.doClose();
	    // 검색폼 데이터 객체화
	    var obj = $('#searchForm').serializeObject();
	    // 검색폼 그리드 검색
		$('#appGrid').appBbsGrid('search', obj);
	}
};