/**
*******************************************************************************
***    명칭: listInvestCase.js
***    설명: 정보서비스 - 우수투자사례 화면
***
***    -----------------------------    Modified Log   ------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.06.09     JH        우수투자사례 작업
***    1.1      2023.06.28    J H        작업완료.
*******************************************************************************
**/
$(function() {
	let P_GRID   		= $('#appGrid'     	  ); // 목록 GRID
	let P_FORM   		= $('#searchForm'  	  ); // 검색폼	
	let P_BBS_CD  		= $('#bbsSeCd'  ).val(); // 게시판 구분코드
	let P_CLSF_CD		= $('#pstClsfCd').val(); // 게시판 분류코드
	let P_CLSF			  					   ; // 게시판 분류코드
	let P_SRCHTXT 		= $('#srchText' ).val(); // 검색텍스트 (이전에서 넘어온항목)
	let P_GS_ROLE_ID 	= $('#gsRoleId' ).val(); // 유저권한 
	let P_SHOW_MODE     = 'CARD';
	let P_SRCH_TYPE 	= $('#srchType' ).val(); // 유저권한 
	
	// 상단 검색박스 표시 (app_bbsgrid.js 참고)
	$.bbsUtils.showHeaderBox({
		type : 'searchbox',
		id   : 'srchTextBox',
		name : 'srchTextBox',
		placeholder: '투자자, 경영체명, 키워드를 검색해 보세요.',
		callback: function(v) {
			$('#srchText').val(v);
			doSearch();
		}
	});
	if( $('#srchText').val()){
    	$('#srchTextBox').val($('#srchText').val());
	}
	
	// 정렬방법에 따른 게시물 기준 정렬
	function initHourTime() {
	    // '시간' 버튼이 클릭되었을 때 실행
	    $('#btnSrchType').click(function() {
	        var SrchTypeList = STORE.SORT_INVESTCASE; // STORE.getHours()에서 시간 값을 가져옴
	        // 존재하는 항목들을 모두 삭제
	        $(".dropdown-menu").eq(0).empty();
	        // 시간 값들을 <li> 항목으로 만들어서 dropdown-menu에 추가
	        for (var i = 0; i < SrchTypeList.length; i++) {
	            var SrchType = SrchTypeList[i];
	            var liItem = '<li><a class="dropdown-item" href="javascript:void(0)" data-code="' + SrchType.code + '" data-text="' + SrchType.text + '">' + SrchType.text + '</a></li>';
	            $(".dropdown-menu").eq(0).append(liItem);
	        }
	    });
	
	    // dropdown-item이 클릭되었을 때 실행
	    $(".dropdown-menu").eq(0).on('click', '.dropdown-item', function() {
	        var text = $(this).data('text'); 
	        var code = $(this).data('code'); 
	        $('#btnSrchType').text(text);
	        $('#srchType').val(code);
	        
	        doSearch($('#page').val());
	    });
	}
	$(document).ready(initHourTime);
	
	if( $('#srchType').val()){
		var SrchTypeList = STORE.SORT_INVESTCASE; // STORE.getHours()에서 시간 값을 가져옴
		for (var i = 0; i < SrchTypeList.length; i++) {
            var SrchType = SrchTypeList[i];
            var liItem = '<li><a class="dropdown-item" href="javascript:void(0)" data-code="' + SrchType.code + '" data-text="' + SrchType.text + '">' + SrchType.text + '</a></li>';
            $(".dropdown-menu").eq(0).append(liItem);
            if($('#srchType').val()==SrchType.code ){
            	$('#btnSrchType').text(SrchType.text);
            }
        }
	}
	
	//우수투자사례 그리드
	let P_GRID_OPTIONS = {
		id:         '#appGrid', 									 // 목록 ID
		paging:     '#appGridPagination',							 // 페이지 표시 객체
		toolbar:    '#appGridToolbar',								 // 툴바영역
		autoload:    false,											 // 자동로딩여부
		url:         getUrl('/usr/inform/bbs/getListInvestCase.do'), // 검색 URL
		// 검색 조건
		params: {
			pstClsfCd : $('#pstClsfCd').val(),
			srchText  : $('#srchText' ).val(),
		},
		callback:function(){
			var data = $.ajaxUtil.ajaxDefault(getUrl('/usr/inform/bbs/getListInvestCase.do'),
					{srchType:  $('#srchType' ).val() ,srchText:  $('#srchText' ).val()	});
			$("#total").text(data.rows.length);
		},
	};
	
	// 우수투자사례 카드형 그리드 객체
	P_GRID = $.bbsUtils.appInvestCaseGrid({
		form       : $('#searchForm'),
		gridOptions: P_GRID_OPTIONS,
		selectFilter: function( params ) {
			$.extend(params, {showMode: 'CARD'});
			return params;
		}
	});
	
	// 그리드 검색처리
	function doSearch(page) {
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
	doSearch($('#page').val());
});



