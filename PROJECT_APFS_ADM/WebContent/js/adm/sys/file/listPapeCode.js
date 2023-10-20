/**
******************************************************************************************
*** 파일명 : listPapeCode.js
*** 설명글 : 신청서류관리 화면 스크립트
***
*** -----------------------------    Modified Log   --------------------------------------
*** ver             date                author                  description
*** --------------------------------------------------------------------------------------
*** 1.0         2023.06.21    LSH   First Coding.
*** 1.1         2023.07.21    J H   작업완료
******************************************************************************************
**/
$(function() {
	//========================================================//
	// 화면 스크립트 내에 사용할 객체,상수 정의
	//--------------------------------------------------------//
    var P_GRID   = $('#appGrid'   ); // 그리드 객체
    var P_SFORM  = $('#searchForm'); // 검색폼 객체
    var P_RFORM  = $('#registForm'); // 등록폼 객체
	
    //========================================================//
    // 목록 GRID 정의 (EasyUI datagrid)
    //--------------------------------------------------------//
    P_GRID.appBbsGrid({
		// 목록 KEY필드
		idField:    'dcmntCd',
    	// 목록 제목
		title:		'서류코드관리',
		// 검색 URL
		url:		getUrl('/adm/sys/file/getListPapeCode.do'),
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
		resultText: '<em class="ml-4px">{total}</em>개의 공통코드가 있습니다.',
		// 리스트옵션
		listOptions: {
			// 검색결과 없을경우 메세지
			emptyText:  '검색된 {title}가 없습니다.',
			// 목록칼럼 너비
			colgroup: ['100px','80px','80px','50px','80px','80px'],
			// 목록칼럼 정의
			columns: [
	            {name:'dcmntNm'     ,cls:'app-l' ,label:'서류명' },
	            {name:'dcmntCd'     ,cls:'app-c' ,label:'서류코드'},
	            {name:'upDcmntCd'   ,cls:'app-c' ,label:'상위코드', formatter:$.commFormat.rootNm},
	            {name:'cdOrdr'      ,cls:'app-c' ,label:'코드순서', formatter:$.commFormat.number},
	            {name:'useYn'       ,cls:'app-c' ,label:'사용여부', formatter:$.commFormat.useyn},
	            {name:'regDate'     ,cls:'app-c' ,label:'수정일자'},
	        ],
	        // 행선택시 상세조회
	        select: doSelect	
		},
	}).appBbsGrid('init');
	// 로딩시 초기화 및 검색실행
	doReset();
    
    // 게시판 검색처리
    //--------------------------------------------------------//
    function doSearch() {
        // 검색폼 데이터 객체화
        var obj = P_SFORM.serializeObject();
        // 검색폼 그리드 검색
		P_GRID.appBbsGrid('search', obj);
        return false;
    }
    
    // 게시판 검색리셋
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
		// 상세조회 모달팝업 오픈
		P_MODAL.doOpenSelect(row);
        return false;
    }

    // 제출서류 신청 등록하기
    //--------------------------------------------------------//
    function doRegist() {
        P_MODAL.doOpenRegist({});
        return false;
    }
    // 등록버튼 클릭시 이벤트 처리
    $('#btnRegist').bind('click', doRegist);
	// 초기화버튼 클릭시 이벤트처리
    $('#btnReset' ).bind('click', doReset);
	// 검색버튼 클릭시 이벤트 처리
    $('#btnSearch').bind('click', doSearch);

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
  			title:      '공통서류관리 상세조회',
  			url:        getUrl('/adm/sys/file/modalPapeCodeForm.do'),
  			params:     JSON.stringify({
  				dcmntCd      	: row['dcmntCd'  ],
  				upDcmntCd      	: row['upDcmntCd'  ],
  			})
  		}).open();
  	},
  	// 신규등록 모달팝업 오픈
  	doOpenRegist: function( row ) {
  		P_MODAL.modal.appModal({
  			cls:        'w-ver1',
  			title:      '공통서류관리 신규등록',
  			url:        getUrl('/adm/sys/file/modalPapeCodeForm.do'),
  			params:     JSON.stringify({
  				mode       : MODE.INSERT,
  			})
  		}).open();
  	},
  	// 수정하기 모달팝업 오픈
  	doOpenUpdate: function( row) {
  		P_MODAL.modal.appModal({
  			cls:        'w-ver1',
  			title:      '공통서류관리 수정하기',
  			url:        getUrl('/adm/sys/file/modalPapeCodeForm.do'),
  			params:     JSON.stringify({
  				mode       : MODE.UPDATE,
  				dcmntCd      	: row['dcmntCd'  ],
  				upDcmntCd       : row['upDcmntCd'  ],
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
