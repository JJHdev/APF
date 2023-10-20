/**
*******************************************************************************
***    명칭: viewPbanc.js
***    설명: 지원서비스 - 지원사업 통합검색 상세조회 화면
***
***    -----------------------------    Modified Log   ------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.06.02    LSH        First Coding.
*******************************************************************************
**/
$(function() {
	
	// 신청페이지로 새창이동
	function doLink() {
		let url = $(this).data('value');
		if (url)
			goNewUrl(url);
		return false;
	}
		
	// 목록페이지로 이동
	function doList() {
		$.formUtil.submitForm( getUrl('/usr/support/pbanc/listPbanc.do'), {
			formId: 'searchForm'
		});
		return false;
	}
	
	// 신청하기 버튼 클릭
	$('#btnLink').bind('click', doLink);
	// 목록으로 버튼 클릭
	$('#btnList').bind('click', doList);
	
});
