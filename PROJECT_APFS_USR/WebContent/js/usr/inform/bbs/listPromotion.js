/**
*******************************************************************************
***    명칭: listPromotion.js
***    설명: 정보서비스 - 홍보영상 화면
***
***    -----------------------------    Modified Log   ------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.06.15    J H        First Coding.
***    1.1      2023.06.28    J H        작업완료.
*******************************************************************************
**/

let P_GRID = null; 
//인자로 넘어온 검색조건 데이터
let P_SEARCH = {
	page          : false,
	showMode      : 'CARD',
	srchText      : '',
	srchType      : false,
};

$(function() {
	// 스마트검색 폼
	let P_FORM = $('#searchForm');
	
	// 상단 검색박스 표시 (app_bbsgrid.js 참고)
	$.bbsUtils.showHeaderBox({
		type : 'searchbox',
		id   : 'srchText',
		name : 'srchText',
		placeholder: '투자자, 경영체명, 키워드를 검색해 보세요.',
		callback: function(v) {
			P_SEARCH['srchText'] = v;
			P_GRID.doSearch({srchText: v,srchType: P_SEARCH['srchType'] });
		}
	});
});

$(function() {
	// 그리드 객체
    P_GRID = false;
	// 그리드 목록유형
	let P_SHOW_MODE = P_SEARCH['showMode'];
		
	let P_GRID_OPTIONS = {
		id:         '#appGrid', 							// 목록 ID
		paging:     '#appGridPagination',					// 페이지 표시 객체
		toolbar:    '#appGridToolbar',   					// 툴바영역
		autoload:    false,									// 자동로딩여부
		url:         getUrl('/usr/inform/bbs/getListPromotion.do'), // 검색 URL
		// 검색 조건
		params: {},
		// 툴바옵션
		toolbarOptions: {
			cls: 'shadow-box-1 mb-16px px-24px py-16px',
			boxCls: 'design-txt-1',
			items: [{
				cls:   'col flex-b-md-210px',
				format: function() {
					let p = $('<p class="fs-15px fw-600" ></p>');
					p.append('전체 <em class="fw-500 text-primary" id="total"></em>건');
					return p;
				},
			},{	cls:   'col flex-b180px',
				key:   'srchType',
				type:  'appDropdownBox',
				value: P_SEARCH['srchType'],
				options: {
					label: '정렬선택',
					type:  'static',
					rows:   STORE.SORT_INVESTCASE, // comm_const.js
					input: {id:'srchType', name:'srchType', value:''},
					select: function(value, text, item) {
						P_SEARCH['srchType'] = value;
						P_GRID.doSearch({srchType: value, srchText: P_SEARCH['srchText'] });
					}
				}
			}]
		},
		callback:function(){
			var data = $.ajaxUtil.ajaxDefault(getUrl('/usr/inform/bbs/getListPromotion.do'),
					{srchType:  P_SEARCH['srchType'],srchText:  P_SEARCH['srchText']	});
			$("#total").text(data.rows.length);
		},
	};
	
	// 홍보영상 카드형 그리드 객체
	P_GRID = $.bbsUtils.appPromotionGrid({
		form       : $('#searchForm'),
		gridOptions: P_GRID_OPTIONS,
		// 상세조회용 필터함수
		selectFilter: function( params ) {
			$.extend(params, {showMode: P_SHOW_MODE});
			return params;
		}
	});
	
	// 그리드 검색처리
	function doSearch( page ) {
		if (!P_GRID)
			return false;
		if (page && parseInt(page) > 1)
			P_GRID.doSearch( parseInt(page) );
		else
			P_GRID.doSearch();
	}
	
	// 그리드 리셋처리
	function doReset() {
		if (!P_GRID)
			return false;
		P_GRID.doReset();
		P_GRID.doSearch();
	}
	doSearch( P_SEARCH['srchText'] );
});

