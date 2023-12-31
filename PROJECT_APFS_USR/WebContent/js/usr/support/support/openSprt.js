/**
*******************************************************************************
*** 파일명 : openSprt.js
*** 설명글 : 지원사업신청 - 투자유치 전 지원 화면 스크립트
***          지원사업신청 - 투자유치 후 지원 화면 스크립트
***          지원사업신청 - 농식품 크라우드 펀딩 지원 화면 스크립트
***          app_support.js 참고
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.05.22    LSH        First Coding.
*******************************************************************************
**/
$(function() {
		
    //========================================================//
    // 화면 스크립트 내에 사용할 객체,상수 정의
    //--------------------------------------------------------//
	let P_PARAMS = {
		stepNo    : 1,
		sprtSeCd  : $('#sprtSeCd'  ).val(), // 지원구분
		bizYn     : $('#gsBzentyYn').val(), // 업체여부
	};

	// 탭 생성
	$('#appSprtTab').appSprtTab(P_PARAMS);
	// 화면설명글 표시
	$('#appContent').appSprtContents(P_PARAMS);
	// 신청버튼 표시
	$('#appButtons').appSprtAplyButtons(P_PARAMS);
	
});
