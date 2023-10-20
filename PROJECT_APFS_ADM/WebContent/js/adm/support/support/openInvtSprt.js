/**
******************************************************************************************
*** 파일명 : openInvtSprt.js
*** 설명글 : 지원사업관리-세부지원사업관리 화면 스크립트
***
*** -----------------------------    Modified Log   --------------------------------------
*** ver             date                author                  description
*** --------------------------------------------------------------------------------------
*** 1.0         2023.05.17               LHB                      신규 작성
*** 1.1         2023.06.07               LHB         파일명 listInvtSprt -> openInvtSprt 변경
******************************************************************************************
**/
$(function() {

    //========================================================//
    // 화면 스크립트 내에 사용할 객체,상수 정의
    //--------------------------------------------------------//
	var P_TITLE  = '세부지원사업관리'  ; // 화면 제목
    var P_GRID   = $('#appGrid'   ); // 그리드 객체
    var P_SFORM  = $('#searchForm'); // 검색폼 객체

    //========================================================//
    // 목록 GRID 정의 (일반 GRID)
    //--------------------------------------------------------//
    P_GRID.appBbsGrid({
		// 목록 KEY필드
		idField:    'prgrmNo',
		// 목록 제목
		title:		'세부지원사업관리',
		// 검색 URL
		url:		getUrl('/adm/support/support/getListInvtSprt.do'),
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
		resultText: '<em class="ml-4px">{total}</em>개의 세부지원사업이 있습니다.',
		// 리스트옵션
		listOptions: {
			// 검색결과 없을경우 메세지
			emptyText:  '검색된 {title}가 없습니다.',
			// 목록칼럼 너비
			colgroup: ['50px','80px','120px','70px','70px','40px'],
			// 목록칼럼 정의
			columns: [
				{rownumbers:true      ,cls:'app-c', label:'번호'},
				{name:'prgrmClsfCdNm' ,cls:'app-c', label:'프로그램분류'},
				{name:'prgrmNm'       ,cls:'app-l', label:'프로그램명'},
	            {name:'sprtBgngYmd'   ,cls:'app-c', label:'지원시작일'},
				{name:'sprtEndYmd'    ,cls:'app-c', label:'지원종료일'},
	            {name:'useYn'         ,cls:'app-c', label:'표출'}
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

    // 세부지원사업 검색처리
    //--------------------------------------------------------//
    function doSearch() {
        // 검색폼 데이터 객체화
        var obj = P_SFORM.serializeObject();

        // 검색폼 그리드 검색
        P_GRID.appBbsGrid('search', obj);

		// 투자유치 후, 농식품크라우드펀딩 프로그램분류 숨김 처리
		if (obj['sprtAplySeCd'] == 'SA') {
			$('.app-grid-table thead th:nth-child(1), .app-grid-table tbody tr td:nth-child(1)').show();
		} else if (obj['sprtAplySeCd'] == 'SC') {
			$('.app-grid-table thead th:nth-child(1), .app-grid-table tbody tr td:nth-child(1)').hide();
		}

        return false;
    }

    // 세부지원사업 수정하기
    //--------------------------------------------------------//
    function doSelect(row) {
		// 상세조회 모달팝업 오픈
		P_MODAL.doOpenSelect(row);
		
        return false;
    }

    // 세부지원사업 등록하기
    //--------------------------------------------------------//
    function doRegist() {
        P_MODAL.doOpenRegist({});

        return false;
    }

	// 세부지원사업 탭 변경
    //--------------------------------------------------------//
    function doChangeTab() {
		$(".tabmenu-1 ul > li > a").removeClass("active");
		$(this).addClass("active");
		
		var sprtAplySeCd;
		if($(this).parent("li").index() == 0) {
			sprtAplySeCd = 'SA';
			$("#prgrmClsfCdDiv").show();
		} else if($(this).parent("li").index() == 1) {
			sprtAplySeCd = 'SC';
			$("#prgrmClsfCdDiv").hide();	
		}
		
		$.formUtil.toForm({
			sprtAplySeCd     : sprtAplySeCd, // 사업구분
		}, P_SFORM);

		doSearch();
	
        return false;
    }

    // 등록버튼 클릭시 이벤트 처리
    $('#btnRegist').bind('click', doRegist);

    // 취소버튼 클릭시 이벤트 처리
    $('#btnUndo'  ).bind('click', doRegist);

	// 탭 변경 클릭시 이벤트 처리
	$(".tabmenu-1 ul > li > a").bind('click', doChangeTab);

	$.formUtil.toForm({
		sprtAplySeCd  : 'SA',
	}, P_SFORM);

    // 검색 실행
    doSearch();

    // 검색어 입력 엔터 이벤트 처리
    bindEnter($('#srchText'), $('#btnSearch'));

	// 검색조건 열기/닫기 버튼 숨김
	$("button.btnFc-1").hide();
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
			//cls:        'w-ver1',
			title:      '세부지원사업관리 상세조회',
			url:        getUrl('/adm/support/support/modalInvtSprtForm.do'),
			params:     JSON.stringify({
				prgrmNo     : row['prgrmNo'],
				sprtAplySeCd: row['sprtAplySeCd'],
			})
		}).open();
	},
	// 신규등록 모달팝업 오픈
	doOpenRegist: function(row) {
		P_MODAL.modal.appModal({
			//cls:        'w-ver1',
			title:      '세부지원사업관리 신규등록',
			url:        getUrl('/adm/support/support/modalInvtSprtForm.do'),
			params:     JSON.stringify({
				mode        : MODE.INSERT,
				sprtAplySeCd: $('#sprtAplySeCd_S').val(),
			})
		}).open();
	},
	// 수정하기 모달팝업 오픈
	doOpenUpdate: function(row) {
		P_MODAL.modal.appModal({
			//cls:        'w-ver1',
			title:      '세부지원사업관리 수정하기',
			url:        getUrl('/adm/support/support/modalInvtSprtForm.do'),
			params:     JSON.stringify({
				mode         : MODE.UPDATE,
				prgrmNo      : row['prgrmNo'],
				sprtAplySeCd : row['sprtAplySeCd'],
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
		
		// 투자유치 후, 농식품크라우드펀딩 프로그램분류 숨김 처리
		if (obj['sprtAplySeCd'] == 'SA') {
			$('.app-grid-table thead th:nth-child(1), .app-grid-table tbody tr td:nth-child(1)').show();
		} else if (obj['sprtAplySeCd'] == 'SC') {
			$('.app-grid-table thead th:nth-child(1), .app-grid-table tbody tr td:nth-child(1)').hide();
		}
	}
};