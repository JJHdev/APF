/**
*******************************************************************************
***    명칭: openMatchingSprt.js
***    설명: 지원서비스-매칭서비스-매칭설정 화면
***
***    -----------------------------    Modified Log   ------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.04.29    LSH        First Coding.
*******************************************************************************
**/
$(function() {

	// 매칭결과목록 생성 (app_matching.js)
	$('#appMatchingResult').appMatchingResult({
		showMode: $('#showMode').val(),
		srchMode: $('#srchMode').val(),
		mtchMode: $('#mtchMode').val()
	});
	
	// 회원가입 버튼 클릭
	$('#btnJoin').bind('click', function() {
		goUrl( getUrl(CURL.JOIN) );
		return false;
	});
	// 로그인 버튼 클릭
	$('#btnLogin').bind('click', function() {
		goUrl( getUrl(CURL.LOGIN) );
		return false;
	});
	// 매칭설정 버튼 클릭
	$('#btnMatching').bind('click', function() {
		goUrl( getUrl('/usr/mypage/matching/openMatching.do') );
		return false;
	});
	
	// 상단 매칭설정버튼 표시 (app_bizutils.js 참고)
	$.bizUtils.showHeaderBox({
		type : 'button',
		id   : 'btnTopMatching',
		icon : 'icon-settings',
		value: '매칭설정',
		callback: function() {
			goUrl( getUrl('/usr/mypage/matching/openMatching.do') );
			return false;
		}
	});
});
