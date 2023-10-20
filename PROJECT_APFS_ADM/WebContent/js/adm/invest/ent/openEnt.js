/**
*******************************************************************************
***    명칭: openCompany.jsp
***    설명: 회원관리-업체관리 화면
***
***    -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자               내용
*** ---------------------------------------------------------------------------
***    1.0      2023.06.12      LHB            First Coding.
*******************************************************************************
**/
$(function() {
	
	//========================================================//
    // 화면 스크립트 내에 사용할 객체,상수 정의
    //--------------------------------------------------------//
	var P_TITLE			= '업체관리'        ;		// 화면 제목
    var P_GRID			= $('#appGrid'   );		// 그리드 객체
    var P_SFORM			= $('#searchForm');		// 검색폼 객체

	//========================================================//
    // 목록 GRID 정의 (일반 GRID)
    //--------------------------------------------------------//
    P_GRID.appBbsGrid({
		// 목록 KEY필드
		idField:    'bzentyNo',
		// 목록 제목
		title:		'업체정보',
		// 검색 URL
		url:		getUrl('/adm/invest/ent/getListEnt.do'),
        // 조회 파라미터 전달
		queryParams: {
			useSttsCd: 0,
	    },
		//목록 검색 페이징 객체
		pagination: { display: 20 },
		// 페이지 표시 객체
		paging:     '#appGridPagination',
		// 검색결과 표시 객체
		result:     '#appGridResult',
		// 검색결과 메세지
		resultText: '<em class="ml-4px">{total}</em>개의 업체정보가 있습니다.',
		// 리스트옵션
		listOptions: {
			// 검색결과 없을경우 메세지
			emptyText:  '검색된 {title}가 없습니다.',
			// 목록칼럼 너비
			colgroup: ['50px','300px','100px','100px','50px'],
			// 목록칼럼 정의
			columns: [
				{rownumbers:true    ,cls:'app-c',label:'번호',},
	            {name:'bzentyNm'	,cls:'app-l',label:'업체명'},
	            {name:'bzentySeNm'	,cls:'app-c',label:'기업유형'},
	            {name:'regDate'		,cls:'app-c',label:'등록일'},
				{name:'useSttsCdNm'	,cls:'app-c',label:'상태'},
			],
			// 목록형인 경우 기본 스타일시트
			gridCls: "table-box-1",
	        // 행선택시 상세조회
	        select: doSelect	
		}
	}).appBbsGrid('init');
	
	//========================================================//
    // 콤보박스 정의
    //--------------------------------------------------------//
	// 검색용 기업유형
	$('#srchBzentySeCd').appComboBox({
		params: {upCdId: CODE.BZENTY_SE.code},
		loadFilter: function(data) {
			data = data.filter(function(e) { return e.code == '10' || e.code == '20' || e.code == '30'; })
			data.unshift(COMBO.INIT_ALL);
			return data;
		}
	});
	// 검색용 상태
	$('#srchAprvSttsCd').appComboBox({
		params: {upCdId: CODE.APRV_STTS.code},
		loadFilter: function(data) {
			data.unshift(COMBO.INIT_ALL);
			return data;
		}
	});
	
	// 업체정보 검색처리
	//--------------------------------------------------------//
    function doSearch() {
        // 검색폼 데이터 객체화
        var obj = P_SFORM.serializeObject();
        // 검색폼 그리드 검색
        P_GRID.appBbsGrid('search', obj);
        return false;
    }

	// 업체정보 검색리셋
    //--------------------------------------------------------//
    function doReset() {
        // 검색폼 입력데이터 초기화
        P_SFORM.form('reset');
        // 검색폼 그리드 검색 처리
        doSearch();

        return false;
    }

    // 업체정보 수정하기
    //--------------------------------------------------------//
    function doSelect(row) {
		// 상세조회 모달팝업 오픈
		P_MODAL.doOpenSelect(row);
		
        return false;
    }

	// 검색버튼 클릭시 이벤트 처리
    $('#btnSearch').bind('click', doSearch);
    // 리셋버튼 클릭시 이벤트처리
	$('#btnReset' ).bind('click', doReset);

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
var P_MODAL = {
	// 모달팝업 객체
	modal: $('<div></div>'),
	// 상세조회 모달팝업 오픈
	doOpenSelect: function(row) {
		P_MODAL.modal.appModal({
			cls:        'w-ver1',
			title:      '업체정보 상세조회',
			url:        getUrl('/adm/invest/ent/modalEntForm.do'),
			params:     JSON.stringify({
				bzentyNo     : row['bzentyNo']
			})
		}).open();
	},
	// 신규등록 모달팝업 오픈
	doOpenRegist: function(row) {
		P_MODAL.modal.appModal({
			cls:        'w-ver1',
			title:      '업체정보 신규등록',
			url:        getUrl('/adm/invest/ent/modalEntForm.do'),
			params:     JSON.stringify({
				mode       : MODE.INSERT
			})
		}).open();
	},
	// 수정하기 모달팝업 오픈
	doOpenUpdate: function(row) {
		P_MODAL.modal.appModal({
			cls:        'w-ver1',
			title:      '업체정보 수정하기',
			url:        getUrl('/adm/invest/ent/modalEntForm.do'),
			params:     JSON.stringify({
				mode       : MODE.UPDATE,
				bzentyNo     : row['bzentyNo']
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
		P_MODAL.doClose();
	    // 검색폼 데이터 객체화
	    var obj = $('#searchForm').serializeObject();
	    // 검색폼 그리드 검색
		$('#appGrid').appBbsGrid('search', obj);
	}
};