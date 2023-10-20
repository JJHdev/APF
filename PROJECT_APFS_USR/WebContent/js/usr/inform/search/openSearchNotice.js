/**
*******************************************************************************
***    명칭: openSearchNotice.js
***    설명: 통합검색-공지사항 화면
***
***    -----------------------------    Modified Log   ------------------------
***    버전        수정일자        수정자            내용
*** ---------------------------------------------------------------------------
***    1.0      2023.07.04      LHB         First Coding.
*******************************************************************************
**/
$(function() {
	
	let P_SFORM				= $('#searchForm');
	let INIT_SRCH_TEXT		= $('#srchText');
	let SRCH_HEADER_BOX		= null;
	
	SRCH_HEADER_BOX = $.bizUtils.showHeaderBox({
	    type		: 'searchbox',
	    id			: 'srchTextHeader',
	    name		: 'srchText',
		value		: INIT_SRCH_TEXT.val(),
	    placeholder	: '검색어를 입력해주세요.',
		color		: 'black',
	    callback: function(v) {
			doSearch();
	    }
	});
	
	function doSearch() {
		$.formUtil.setValue(P_SFORM, 'srchText', $('#srchTextHeader').val());
		
		//const params = P_SFORM.serializeObject();
		
		P_GRID = $.bbsUtils.appSearchGrid({
			form		: P_SFORM,
			gridOptions	: {
				id			: '#appSearch',
				url			: getUrl('/usr/inform/search/getListSearch.do'),
				changeCls	: '',
				cardOptions	: {
					// 기본 스타일시트
					//cardCls: "",
					// 열 스타일시트
					lineCls: "col-12",
					// 결과없음
					emptyText: [
						'<p class="fs-24px fw-600 lh-1 mb-2">검색결과가 없습니다.</p>',
						'<span class="fs-15px fw-300">조건 검색에서 조건을 수정하여 다시 검색해보세요.</span>'
					].join(''),
				},
				formatter: function(dom, o) {
					let box = $.bbsUtils.getNoticeInfo(o, MODE.LIST);
					dom.append(box);
				},
				callback : function(target, rows, page) {
					$('#appSearchCnt').html(page.total);
				},
			},
			// 상세조회용 필터함수
			selectFilter: function( params ) {
				return params;
			},
		});
		
	}
	
	doSearch();
	
});

let P_GRID = null;