/**
******************************************************************************************
*** 파일명 : listProg.js
*** 설명글 : 프로그램관리 관리 화면 스크립트
***
*** -----------------------------    Modified Log   --------------------------------------
*** ver             date                author                  description
*** --------------------------------------------------------------------------------------
*** 1.0         2021.09.05    LSH
*** 1.0         2021.11.02    LSH   디자인적용 및 개발 수정
******************************************************************************************
**/
$(function() {

    //========================================================//
    // 화면 스크립트 내에 사용할 객체,상수 정의
    //--------------------------------------------------------//
	var P_TITLE  = '프로그램'      ; // 화면 제목
    var P_GRID   = $('#appGrid'    ); // 그리드 객체
    var P_SFORM  = $('#searchForm' ); // 검색폼 객체
    var P_RFORM  = $('#registForm' ); // 등록폼 객체
    
  //========================================================//
    // 콤보박스 정의
    //--------------------------------------------------------//
	// 검색용 연결메뉴 EasyUI Combo Tree
    $('#srchSysCd').appComboBox({
		params: {upCdId: CODE.SYS_SE.code},
		loadFilter : function(data) {
			return data;
		},
		change : function() {
			var value = $(this).val();
			$('#srchMenuId').appComboBox({
				url : getUrl('/com/common/getComboMenu.do'),
				params : {
					sysCd : value
				},
				loadFilter : function(data2) {
					data2.unshift(COMBO.INIT_SELECT);
					return data2;
				},
			});
		},
		callback : function(){
			doSearch();
		}
	});
	
	$('#srchMenuId').appComboBox({
		url : getUrl('/com/common/getComboMenu.do'),
		params : {
			sysCd : 'all'
		},
		loadFilter : function(data2) {
			data2.unshift(COMBO.INIT_SELECT);
			return data2;
		},
	});
    
	//========================================================//
    // 목록 GRID 정의 (EasyUI datagrid)
    //--------------------------------------------------------//
    P_GRID.appBbsGrid({
		// 목록 KEY필드
		idField:    'prgrmId',
    	// 목록 제목
		title:		'프로그램 관리',
		// 검색 URL
		url:		getUrl('/adm/sys/prog/getListProg.do'),
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
		resultText: '<em class="ml-4px">{total}</em>개의 프로그램관리코드가 있습니다.',
		// 리스트옵션
		listOptions: {
			// 검색결과 없을경우 메세지
			emptyText:  '검색된 {title}가 없습니다.',
			// 목록칼럼 너비
			colgroup: ['150px','150px','150px','150px'],
			// 목록칼럼 정의
			columns: [
	            {name:'prgrmId'         ,cls:'app-l' ,label:'프로그램ID'},
	            {name:'prgrmNm'         ,cls:'app-l' ,label:'프로그램명'},
	            {name:'prgrmUrl'        ,cls:'app-l' ,label:'프로그램URL'},
	            {name:'menuId'          ,cls:'app-l' ,label:'연결 메뉴 Id'},
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
			title:      '프로그램관리 상세조회',
			url:        getUrl('/adm/sys/prog/modalProgForm.do'),
			params:     JSON.stringify({
				prgrmId        : row['prgrmId'  ],
				menuId      	: row['menuId'  ],
				sysCd      	  : row['sysCd'  ],
			})
		}).open();
	},
	// 신규등록 모달팝업 오픈
	doOpenRegist: function( row ) {
		P_MODAL.modal.appModal({
			cls:        'w-ver1',
			title:      '프로그램관리 신규등록',
			url:        getUrl('/adm/sys/prog/modalProgForm.do'),
			params:     JSON.stringify({
				mode       : MODE.INSERT,
			})
		}).open();
	},
	// 수정하기 모달팝업 오픈
	doOpenUpdate: function( row) {
		P_MODAL.modal.appModal({
			cls:        'w-ver1',
			title:      '프로그램관리 수정하기',
			url:        getUrl('/adm/sys/prog/modalProgForm.do'),
			params:     JSON.stringify({
				mode        : MODE.UPDATE,
				prgrmId        : row['prgrmId'  ],
				menuId      	: row['menuId'  ],
				sysCd      	  : row['sysCd'  ],
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

