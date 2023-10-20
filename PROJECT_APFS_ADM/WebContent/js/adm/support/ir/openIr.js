/**
******************************************************************************************
*** 파일명 : openIr.js
*** 설명글 : 운영관리-IR지원현황 화면 스크립트
***
*** -----------------------------    Modified Log   --------------------------------------
*** ver             date                author                  description
*** --------------------------------------------------------------------------------------
*** 1.0         2023.06.08               LHB                      신규 작성
******************************************************************************************
**/
$(function() {

    //========================================================//
    // 화면 스크립트 내에 사용할 객체,상수 정의
    //--------------------------------------------------------//
	var P_TITLE  = 'IR지원현황'      ;		// 화면 제목
    var P_GRID   = $('#appGrid'   );	// 그리드 객체
    var P_SFORM  = $('#searchForm');	// 검색폼 객체
    var P_RFORM  = $('#registForm');	// 등록폼 객체

	var P_DTBTN		= $('.btn-date');	// 행사기간 날짜 선택 버튼

    //========================================================//
    // 목록 GRID 정의 (일반 datagrid)
    //--------------------------------------------------------//
    P_GRID.appBbsGrid({
		// 목록 KEY필드
		idField:    '',
		// 목록 제목
		title:		'IR지원현황',
		// 검색 URL
		url:		getUrl('/adm/support/ir/getListIr.do'),
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
		resultText: '<em class="ml-4px">{total}</em>개의 IR지원현황이 있습니다.',
		// 리스트옵션
		listOptions: {
			// 검색결과 없을경우 메세지
			emptyText:  '검색된 {title}이 없습니다.',
			// 목록칼럼 너비
			colgroup: ['30px','80px','80px','60px','30px','60px'],
			// 목록칼럼 정의
			columns: [
				{rownumbers:true		,cls:'app-c', label:'번호'},
	            {name:'bzentyNm'		,cls:'app-l', label:'지원자'},
	            {name:'fundNm'			,cls:'app-l', label:'펀드명'},
	            {name:'sprtYmd'			,cls:'app-c', label:'지원일자'},
				{name:'sprtSttsCdNm'	,cls:'app-c', label:'검토결과', formatter: function(v, o) {
					if (o['sprtSttsCd'] == '10') {
						// 미검토
						return '<text style="color: red;">' + v + '</text>';
					} else if (o['sprtSttsCd'] == '20') {
						// 보류
						return '<text style="color: gray;">' + v + '</text>';
					} else if (o['sprtSttsCd'] == '90') {
						// 적합
						return '<text style="color: blue;">' + v + '</text>';
					}
					return v;
	            }},
				{name:'rvwYmd'			,cls:'app-c', label:'검토일자'},
			],
			// 목록형인 경우 기본 스타일시트
			gridCls: "table-box-1",
	        // 행선택시 상세조회
	        select: doSelect	
		},
    }).appBbsGrid('init');
    //========================================================//
    // 콤보박스 정의
    //--------------------------------------------------------//
	$('#sprtSttsCd').appComboBox({
		params: {upCdId: CODE.SPRT_STUS.code},
		loadFilter: function(data) {
			data.unshift(COMBO.INIT_ALL);
			return data;
		},
	});

    // IR지원현황 검색처리
    //--------------------------------------------------------//
    function doSearch() {
		if (CONDITION.yearCheck($.formUtil.getValue(P_SFORM, 'srchBgngYmd'),
		                        $.formUtil.getValue(P_SFORM, 'srchEndYmd' ),
                                '지원일자 시작일과 종료일의 값이 올바르지 않습니다.')) {
			// 검색폼 데이터 객체화
	        var obj = P_SFORM.serializeObject();
	        // 검색폼 그리드 검색
	        P_GRID.appBbsGrid('search', obj);
	        return false;
		}
    }

    // IR지원현황 검색리셋
    //--------------------------------------------------------//
    function doReset() {
        // 검색폼 입력데이터 초기화
        P_SFORM.form('reset');
        // 검색폼 그리드 검색 처리
        doSearch();

        return false;
    }

    // IR지원현황 수정하기
    //--------------------------------------------------------//
    function doSelect(row) {
		// 상세조회 모달팝업 오픈
		P_MODAL.doOpenSelect(row);

        return false;
    }

    // IR지원현황 등록하기
    //--------------------------------------------------------//
    function doRegist() {
		P_MODAL.doOpenRegist({});
        return false;
    }

	// 검색조건 행사기간 버튼 이벤트
	function setSrchDate() {
		var bgngYmd	= $('#srchBgngYmd');
		var endYmd	= $('#srchEndYmd');
		var dates	= Number($(this).data('dates'));
		
		var bgngDate;
		var endDate;
		
		if (!$.commUtil.isEmpty(bgngYmd.val()) && $.commUtil.isEmpty(endYmd.val())) {
			// 시작일자는 입력되어 있지만 종료일자는 입력되지 않은 경우
			endDate = new Date(new Date(bgngYmd.val()) + 1000 * 60 * 60 * 24 * dates);
			
			endYmd.val($.commUtil.toFormatDate(endDate, '-'));
		} else if ($.commUtil.isEmpty(bgngYmd.val()) && !$.commUtil.isEmpty(endYmd.val())) {
			// 시작일자는 입력되지 않았지만 종료일자는 입력된 경우
			bgngDate = new Date(new Date(endYmd.val()) - 1000 * 60 * 60 * 24 * dates);
			
			bgngYmd.val($.commUtil.toFormatDate(bgngDate, '-'));
		} else {
			bgngDate = new Date(new Date() - 1000 * 60 * 60 * 24 * dates);
			
			bgngYmd.val($.commUtil.toFormatDate(bgngDate, '-'));
			endYmd.val($.commUtil.toFormatDate(new Date(), '-'));
		}
	}

    // 검색버튼 클릭시 이벤트 처리
    $('#btnSearch').bind('click', doSearch);
    // 리셋버튼 클릭시 이벤트처리
    $('#btnReset' ).bind('click', doReset);

	// 행사기간 날짜 선택 버튼 클릭시 이벤트 처리
	P_DTBTN.bind('click', setSrchDate);

    // 검색 실행
    doSearch();

    // 검색어 입력 엔터 이벤트 처리
    bindEnter($('#fundNm'), $('#btnSearch'));
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
			cls:        'w-ver10',
			title:      'IR지원현황 상세조회',
			url:        getUrl('/adm/support/ir/modalIrForm.do'),
			params:     JSON.stringify({
				fundNo		: row['fundNo'],
				bzentyNo	: row['bzentyNo'],
				type		: 'ENT',
			})
		}).open();
	},
	// 신규등록 모달팝업 오픈
	doOpenRegist: function(row) {
		P_MODAL.modal.appModal({
			cls:        'w-ver10',
			title:      'IR지원현황 신규등록',
			url:        getUrl('/adm/support/ir/modalIrForm.do'),
			params:     JSON.stringify({
				mode       : MODE.INSERT,
			})
		}).open();
	},
	// 수정하기 모달팝업 오픈
	doOpenUpdate: function(row) {
		P_MODAL.modal.appModal({
			cls:        'w-ver10',
			title:      'IR지원현황 상세조회',
			url:        getUrl('/adm/support/ir/modalIrForm.do'),
			params:     JSON.stringify({
				mode		: MODE.UPDATE,
				fundNo		: row['fundNo'],
				bzentyNo	: row['bzentyNo'],
				type		: row['type'] ? row['type'] : 'ENT',
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
		P_MODAL.doClose();
	    // 검색폼 데이터 객체화
	    var obj = $('#searchForm').serializeObject();
	    // 검색폼 그리드 검색
		$('#appGrid').appBbsGrid('search', obj);
	}
};