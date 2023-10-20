/**
*******************************************************************************
***    명칭: openInvestCase.jsp
***    설명: 운영관리-우수투자사례 화면
***
***    -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.06.09    JH        First Coding.
***    1.1      2023.06.27    JH        작업 완료.
*******************************************************************************
**/
$(function() {
	//========================================================//
	// 화면 스크립트 내에 사용할 객체,상수 정의
	//--------------------------------------------------------//
	let P_GRID    = $('#appGrid'        ); // 목록 GRID
	let P_FORM    = $('#searchForm'     ); // 검색폼	
	let bbsSeCd   = $('#bbsSeCd'     ).val();
    //========================================================//
    // 목록 GRID 정의 (appBbsGrid : app_bbsgrid.js)
    //--------------------------------------------------------//
    P_GRID.appBbsGrid({
		// 목록 KEY필드
		idField:    'pstNo',
		// 목록 제목
		title:      '우수투자사례',
		// 검색 URL
		url:         getUrl('/adm/inform/bbs/getListBbs.do'),
		// bbsSeCd코드 전달
		queryParams: {
			bbsSeCd: bbsSeCd
	    },
		//목록 검색 페이징 객체
		pagination: { display: 20},
		// 페이지 표시 객체
		paging:     '#appGridPagination',
		// 검색결과 표시 객체
		result:     '#appGridResult',
		// 리스트옵션
		listOptions: {
			// 검색결과 없을경우 메세지
			emptyText:  '검색된 {title}가 없습니다.',
			// 목록칼럼 너비
			colgroup: ['80px','450px','100px','100px','155px'],
			// 목록칼럼 정의
			columns: [
	            {name:'pstNo'        ,cls:'app-c',label:'번호' 	, rownumbers: true},
	            {name:'pstTtl'       ,cls:'app-l',label:'제목'	},
	            {name:'rgtrNm'       ,cls:'app-c',label:'작성자'	, formatter: function(v, o) {
													                return o.mdfrNm || v;
													            }},
	            {name:'inqCnt'       ,cls:'app-r',label:'조회수'	, formatter: $.formatUtil.toNumber},
	            {name:'regDttm'      ,cls:'app-c',label:'등록일자'	}
			],
	        // 행선택시 상세조회
	        select: doSelect
		}
    }).appBbsGrid('init');
		
	// 로딩시 초기화 및 검색실행
	doReset();

    // 게시판 검색처리
    //--------------------------------------------------------//
    function doSearch() {
        // 검색폼 데이터 객체화
        var obj = P_FORM.serializeObject();
        // 검색폼 그리드 검색
		P_GRID.appBbsGrid('search', obj);
        return false;
    }
	
    // 게시판 검색리셋
    //--------------------------------------------------------//
    function doReset() {
        // 검색폼 입력데이터 초기화
        P_FORM.form('reset');
        // 검색폼 그리드 검색 처리
        doSearch();
        return false;
    }

    // 게시판 등록하기
    //--------------------------------------------------------//
    function doRegist() {
		// 신규등록 모달팝업 오픈
		P_MODAL.doOpenRegist({bbsSeCd: bbsSeCd});
        return false;
    }

    // 게시판 상세조회
    //--------------------------------------------------------//
    function doSelect(row) {
		// 상세조회 모달팝업 오픈
		P_MODAL.doOpenSelect( row );
        return false;
    }
    
    // 콤보박스 정의
    //--------------------------------------------------------//
	// 검색용 권한
	$('#srchType').appComboBox({
	    rows: [
	        {code: 'all', text: '전체'},
	        {code: 'title', text: '제목'},
	        {code: 'content', text: '내용'},
	    ],
	    type: 'static'  // 이 옵션은 'rows'
	});
    
    // 검색버튼 클릭시 이벤트 처리
    $('#btnSearch').bind('click', doSearch);
    // 리셋버튼 클릭시 이벤트처리
    $('#btnReset' ).bind('click', doReset);
    // 등록버튼 클릭시 이벤트처리
    $('#btnRegist').bind('click', doRegist);
    

    // 검색어 입력 엔터 이벤트 처리
    bindEnter($('#srchText'), $('#btnSearch'));
});

//========================================================//
//등록,수정,상세 모달팝업 정의
//- 오픈된 모달창에서 사용하기 위해 외부에 정의함
//--------------------------------------------------------//
let P_MODAL = {
	// 모달팝업 객체
	modal: $('<div></div>'),
	// 상세조회 모달팝업 오픈
	doOpenSelect: function( row ) {
		P_MODAL.modal.appModal({
			cls:        'w-ver1',
			title:      '우수투자사례 상세조회',
			url:        getUrl('/adm/inform/bbs/modalInvestCaseView.do'),
			params:     JSON.stringify({
				pstNo      : row['pstNo'  ],
				bbsSeCd    : row['bbsSeCd'],
				movePage   : row['movePage']
			})
		}).open();
	},
	// 신규등록 모달팝업 오픈
	doOpenRegist: function( row ) {
		P_MODAL.modal.appModal({
			cls:        'w-ver1',
			title:      '우수투자사례 신규등록',
			url:        getUrl('/adm/inform/bbs/modalInvestCaseForm.do'),
			params:     JSON.stringify({
				mode       : MODE.INSERT,
				bbsSeCd    : row['bbsSeCd']
			})
		}).open();
	},
	// 수정하기 모달팝업 오픈
	doOpenUpdate: function( row) {
		P_MODAL.modal.appModal({
			cls:        'w-ver1',
			title:      '우수투자사례 수정하기',
			url:        getUrl('/adm/inform/bbs/modalInvestCaseForm.do'),
			params:     JSON.stringify({
				mode       : MODE.UPDATE,
				pstNo      : row['pstNo'  ],
				bbsSeCd    : row['bbsSeCd']
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
		P_MODAL.modal.close();
	    // 검색폼 데이터 객체화
	    var obj = $('#searchForm').serializeObject();
	    // 검색폼 그리드 검색
		$('#appGrid').appBbsGrid('search', obj);
	}
};