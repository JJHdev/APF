/**
*******************************************************************************
***    명칭: openOpnnIrEnt.js
***    설명: 마이페이지 - IR검토의견등록 - 투자설명회 상세
***
***    -----------------------------    Modified Log   ------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.06.23    KYW        First Coding.
*******************************************************************************
**/
$(function() {
	//========================================================//
	// 화면 스크립트 내에 사용할 객체,상수 정의
	//--------------------------------------------------------//
	let P_FORM = $('#searchForm'); // 검색 폼
	let P_SEARCH   = {}; // 화면 내의 기본검색조건
	let P_GRID = false; // 그리드 객체
	let P_SHOW_MODE = P_SEARCH['showMode']; // 그리드 목록유형
	let P_EVNT_NO	= $('#evntNo').val();

	// 그리드 옵션
	let P_GRID_OPTIONS = {
		id:     '#appGrid',
		// 검색 URL
		url: getUrl('/usr/mypage/opnn/getListEventEnt.do'),
		// 검색 조건
		params: {evntNo: P_EVNT_NO},
	};
	//========================================================//
    // 목록 카드형 GRID 정의 (EasyUI datagrid)
    //--------------------------------------------------------//
	P_GRID = $.gridUtils.appEventEntGrid({
		form       : $('#searchForm'),
		gridOptions: P_GRID_OPTIONS,
	});
	
	// 목록으로 버튼 클릭
	$('#btnList').bind('click', function() {
        $.formUtil.submitForm( getUrl('/usr/mypage/opnn/openOpnnIr.do'), {
			params: {
				page      : $('#page').val(),
				srchYr	  : $('#srchYr').val()
			}
		});
        return false;
	})
	
	
});

