/**
******************************************************************************************
*** 파일명 : openPbanc.js
*** 설명글 : 사업공고관리 화면 스크립트
***
*** -----------------------------    Modified Log   --------------------------------------
*** ver             date                author                  description
*** --------------------------------------------------------------------------------------
*** 1.0         2023.05.25               LHB                      신규 작성
******************************************************************************************
**/
$(function() {

    //========================================================//
    // 화면 스크립트 내에 사용할 객체,상수 정의
    //--------------------------------------------------------//
	var P_TITLE		= '사업공고관리'     ; // 화면 제목
    var P_GRID		= $('#appGrid'      ); // 그리드 객체
    var P_SFORM		= $('#searchForm'); // 검색폼 객체
    var P_RFORM		= $('#registForm'); // 등록폼 객체
	var P_MODE      = $('#mode'   ).val();// 처리 모드
	
	var P_REGPOP	= $('.switch-target1');			// 등록 팝업
	var P_PRVWPOP	= $('.switch-fold-target1'); 	// 미리보기 팝업
	
	var P_FILEINPUT	= $('#file-input');	// 첨부파일 객체
	
	var P_FILE_TYPE = CODE.FILE_TYPE.BIZ; // 첨부파일 종류
	var P_FILE_BOX  = $('#attachFile' );  // 첨부파일 컨트롤 객체
	
	

    //========================================================//
    // 목록 GRID 정의 (EasyUI datagrid)
    //--------------------------------------------------------//
    P_GRID.appBbsGrid({
		// 목록 KEY필드
		idField:    'bizPbancNo',
		// 목록 제목
		title:		'사업공고',
		// 검색 URL
		url:		getUrl('/adm/support/pbanc/getListPbanc.do'),
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
		resultText: '<em class="ml-4px">{total}</em>개의 사업공고가 있습니다.',
		// 리스트옵션
		listOptions: {
			// 검색결과 없을경우 메세지
			emptyText:  '검색된 {title}가 없습니다.',
			// 목록칼럼 너비
			colgroup: ['50px','200px','200px','200px','200px','80px','80px','70px','150px'],
			// 목록칼럼 정의
			columns: [
				{rownumbers:true		,cls:'app-c', label:'번호'},
	            {name:'bizPbancNm'		,cls:'app-l', label:'제목'},
	            {name:'rcptSeCdNm'		,cls:'app-c', label:'접수기간'},
				{name:'bizFldNm'		,cls:'app-c', label:'사업분야'},
				{name:'bizTrgtNm'		,cls:'app-c', label:'사업대상'},
				{name:'bizTrgtAgeNm'	,cls:'app-c', label:'사업대상연령'},
				{name:'bizTrgtFntnPdNm'	,cls:'app-c', label:'사업대상업력'},
				{name:'regDate'			,cls:'app-c', label:'작성일'},
				{name:'crdnsBzentyNm'	,cls:'app-c', label:'작성기관'},
			],
			// 목록형인 경우 기본 스타일시트
			gridCls: "table-box-1 overtable",
	        // 행선택시 상세조회
	        select: doSelect	
		},
    }).appBbsGrid('init');

    //========================================================//
    // 콤보박스 정의
    //--------------------------------------------------------//
	// 사업분야
	$('#bizFld').appComboBox({
		params: {upCdId: CODE.BIZ_RLM.code},
		loadFilter: function(data) {
			data.unshift(COMBO.INIT_ALL);
			return data;
		},
	});
	// 사업대상
	$('#bizTrgt').appComboBox({
		params: {upCdId: CODE.SPRT_TRGT.code},
		loadFilter: function(data) {
			data.unshift(COMBO.INIT_ALL);
			return data;
		},
	});
	// 사업대상연령
	$('#bizTrgtAge').appComboBox({
		params: {upCdId: CODE.SPRT_AGE.code},
		loadFilter: function(data) {
			data.unshift(COMBO.INIT_ALL);
			return data;
		},
	});
	// 사업대상업력
	$('#bizTrgtFntnPd').appComboBox({
		params: {upCdId: CODE.FNTN_WHL.code},
		loadFilter: function(data) {
			data.unshift(COMBO.INIT_ALL);
			return data;
		},
	});
	// 접수기간
	$('#rcptSeCd').appComboBox({
		params: {upCdId: CODE.RCPT_WHL_SE.code},
		loadFilter: function(data) {
			data.unshift(COMBO.INIT_ALL);
			return data;
		},
	});
    

    // 사업공고 검색처리
    //--------------------------------------------------------//
    function doSearch() {
		if (CONDITION.yearCheck($.formUtil.getValue(P_SFORM, 'rcptBgngYmd'),
		                        $.formUtil.getValue(P_SFORM, 'rcptEndYmd' ),
                                '접수기간 시작일과 종료일의 값이 올바르지 않습니다.')) {
			if (CONDITION.yearCheck($.formUtil.getValue(P_SFORM, 'srchBgngYmd'),
		                            $.formUtil.getValue(P_SFORM, 'srchEndYmd' ),
                                    '작성일 시작일과 종료일의 값이 올바르지 않습니다.')) {
				// 검색폼 데이터 객체화
		        var obj = P_SFORM.serializeObject();
		        // 검색폼 그리드 검색
		        P_GRID.appBbsGrid('search', obj);
		        return false;
			}
		}
    }

    // 사업공고 검색리셋
    //--------------------------------------------------------//
    function doReset() {
    	// 검색폼 입력데이터 초기화
        P_SFORM.form('reset');
        // 검색폼 그리드 검색 처리
        doSearch();

        return false;
    }

	// 배너관리 수정하기
    //--------------------------------------------------------//
    function doSelect(row) {
		// 상세조회 모달팝업 오픈
		//P_MODAL.doOpenSelect(row);
		P_MODAL.doOpenUpdate(row);
		
        return false;
    }

	// 배너관리 등록하기
    //--------------------------------------------------------//
    function doRegist() {
		P_MODAL.doOpenRegist({});
		
		return false;
    }
	

    // 검색버튼 클릭시 이벤트 처리
    $('#btnSearch').bind('click', doSearch);
    // 리셋버튼 클릭시 이벤트처리
    $('#btnReset' ).bind('click', doReset);
    // 등록버튼 클릭시 이벤트처리
    $('#btnRegist').bind('click', doRegist);

    // 검색 실행
    doSearch();

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
	// 임시데이터
	data : {},
	// 닫기 구분
	closeSe : true,
	// 신규등록 모달팝업 오픈
	doOpenRegist: function(row) {
		P_MODAL.data = row;
		row['mode'] = MODE.INSERT;
		P_MODAL.modal.appModal({
			cls:        'w-ver1',
			title:      '사업공고 신규등록',
			url:        getUrl('/adm/support/pbanc/modalPbancForm.do'),
			params:     JSON.stringify(row),
			closeBtnId: 'btnCancel'
		}).open();
	},
	// 수정하기 모달팝업 오픈
	doOpenUpdate: function(row) {
		P_MODAL.data = row;
		row['mode'] = MODE.UPDATE;
		P_MODAL.modal.appModal({
			cls:        'w-ver1',
			title:      '사업공고 상세보기',
			url:        getUrl('/adm/support/pbanc/modalPbancForm.do'),
			params:     JSON.stringify({
				mode		: MODE.UPDATE,
				bizPbancNo	: row['bizPbancNo'],
			}),
			closeBtnId: 'btnCancel'
		}).open();
	},
	// 미리보기 모달팝업 오픈
	doOpenSelect: function(row) {
		P_MODAL.data = Object.assign({}, row);
		row['mode'] = MODE.VIEW;
		P_MODAL.modal.appModal({
			cls:        'w-ver1',
			title:      '사업공고 미리보기',
			url:        getUrl('/adm/support/pbanc/modalPbancForm.do'),
			params:     JSON.stringify(row),
			closeBtnId: 'btnCancel'
		}).open();
	},
	// [모달팝업 호출용] 모달팝업 닫기
	doClose: function() {
		if (P_MODAL.modal && P_MODAL.modal.close) {
			P_MODAL.modal.close();	
		}
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