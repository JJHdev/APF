/**
******************************************************************************************
*** 파일명 : openSprtUld.js
*** 설명글 : 운영관리-경영체 데이터 업로드 화면 스크립트
***
*** -----------------------------    Modified Log   --------------------------------------
*** ver             date                author                  description
*** --------------------------------------------------------------------------------------
*** 1.0         2023.07.13               LHB                    First Coding.
******************************************************************************************
**/
$(function() {

    //========================================================//
    // 화면 스크립트 내에 사용할 객체,상수 정의
    //--------------------------------------------------------//
	var P_TITLE			= '경영체 데이터 업로드';		// 화면 제목
    var P_GRID			= $('#appGrid'    );	// 그리드 객체
    var P_SFORM			= $('#searchForm' );	// 검색폼 객체

    //========================================================//
    // 목록 GRID 정의 (일반 GRID)
    //--------------------------------------------------------//
    P_GRID.appBbsGrid({
		// 목록 KEY필드
		idField:    'uldNo',
		// 목록 제목
		title:		'업로드 현황',
		// 검색 URL
		url:		getUrl('/adm/support/biz/getListSprtUld.do'),
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
		resultText: '<em class="ml-4px">{total}</em>개의 {title}이 있습니다.',
		// 리스트옵션
		listOptions: {
			// 검색결과 없을경우 메세지
			emptyText:  '검색된 {title}이 없습니다.',
			// 목록칼럼 너비
			colgroup: ['50px','250px','150px','150px','100px'],
			// 목록칼럼 정의
			columns: [
				{rownumbers:true		, cls:'app-c', label:'번호',},
	            {name:'fileNm'			, cls:'app-l', label:'파일명'},
	            {name:'crdnsBzentyNm'	, cls:'app-c', label:'기관명'},
	            {name:'rgtrNmNo'		, cls:'app-c', label:'작성자'},
				{name:'regDate'			, cls:'app-c', label:'업로드 일자'},
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
	// 유관기관
	$('#crdnsBzentyNo').appComboBox({
		params: {upCdId: CODE.CRDNS_SE.code},
		loadFilter: function(data) {
			data = data.map(function(e, i) { return {code: e.cdCn, text: e.text }; });
			data.unshift(COMBO.INIT_ALL);
			return data;
		},
	});

    // 업로드 현황 검색처리
    //--------------------------------------------------------//
    function doSearch() {
        // 검색폼 데이터 객체화
        var obj = P_SFORM.serializeObject();
        // 검색폼 그리드 검색
        P_GRID.appBbsGrid('search', obj);
        return false;
    }

    // 업로드 현황 검색리셋
    //--------------------------------------------------------//
    function doReset() {
        // 검색폼 입력데이터 초기화
        P_SFORM.form('reset');
        // 검색폼 그리드 검색 처리
        doSearch();

        return false;
    }

    // 업로드 현황 수정하기
    //--------------------------------------------------------//
    function doSelect(row) {
		// 상세조회 모달팝업 오픈
		P_MODAL.doOpenSelect(row);
		
		// 대표연락처 초기화
		if (row['rprsTelno'] == null) {
			P_MODAL.modal.find('#rprsTelno2').val('');
			P_MODAL.modal.find('#rprsTelno3').val('');
		}
		
        return false;
    }

    // 업로드 현황 등록하기
    //--------------------------------------------------------//
    function doUpload() {
		P_MODAL2 = $('<div></div>').appPopupSprtUld({
		}, function() {
			return false;
		});
    }

	// 양식 다운로드하기
    //--------------------------------------------------------//
    function doDownload() {
		$.bizUtils.downloadSprtUld();
    }

    // 검색버튼 클릭시 이벤트 처리
    $('#btnSearch'  ).bind('click', doSearch);
    // 리셋버튼 클릭시 이벤트처리
    $('#btnReset'   ).bind('click', doReset);
	// 파일업로드 버튼 클릭시 이벤트처리
	$('#btnUpload'  ).bind('click', doUpload);
	// 파일다운로드 버튼 클릭시 이벤트처리
	$('#btnDownload').bind('click', doDownload);

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
	// 상세조회 모달팝업 오픈
	doOpenSelect: function(row) {
		P_MODAL.modal.appModal({
			cls:        'w-ver1',
			title:      '업로드 현황 상세조회',
			url:        getUrl('/adm/support/biz/modalSprtUldForm.do'),
			params:     JSON.stringify({
				uldNo     : row['uldNo'],
			})
		}).open();
	},
	// 신규등록 모달팝업 오픈
	doOpenRegist: function(row) {
		P_MODAL.modal.appModal({
			cls:        'w-ver1',
			title:      '업로드 현황 신규등록',
			url:        getUrl('/adm/support/biz/modalSprtUldForm.do'),
			params:     JSON.stringify({
				mode       : MODE.INSERT,
			})
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
		//P_MODAL.modal.doClose();
		P_MODAL.doClose();
	    // 검색폼 데이터 객체화
	    var obj = $('#searchForm').serializeObject();
	    // 검색폼 그리드 검색
		$('#appGrid').appBbsGrid('search', obj);
	}
};

let P_MODAL2;
let P_MODAL3; // 업로드 실패 팝업
//[팝업] 파일업로드 팝업
//=============================================================================
$.fn.appPopupSprtUld = function( params, saveCallback ) {
	
	let options = {
		title     		: '파일 업로드',
		icon      		: '<i class="icon-copy-alt"></i>',
		loadUrl			: getUrl('/adm/support/biz/modalSprtUld.do'),
		loadParams		: params,
	};
	
	return $(this).appPopup({
		url:        	options.loadUrl,
		params:     	JSON.stringify(options.loadParams),
		title:      	options.title,
		icon:       	options.icon,
		style:			'width: 600px',
		type:      	 	'pageload',
		dialogCls:  	'w-ver1',
		appendBody: 'body',
		onloaded:   function(pop) {
			return false;
		}
	}).open();
};