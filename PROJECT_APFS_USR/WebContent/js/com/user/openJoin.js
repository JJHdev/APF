/**
******************************************************************************************
*** 파일명    : openJoin.js
*** 설명      : 회원가입 - 유형선택
***
*** -----------------------------    Modified Log   --------------------------------------
*** ver             date                author                  description
*** --------------------------------------------------------------------------------------
*** 1.0         2023.03.14              LSH
*** 1.1         2023.08.10              J H			이용약관및 개인정보 수집 및 이용동의 작업(전체 체크박스 생성)
******************************************************************************************
**/
$(function() {
    //========================================================//
    // 화면 스크립트 내에 사용할 객체,상수 정의
    //--------------------------------------------------------//
	let P_PARAMS = {
		stepCd   : $('#g_stepCd'   ).val(), // 진행단계
		joinCd   : $('#g_joinCd'   ).val(), // 가입유형
		userId   : $('#g_userId'   ).val(), // 회원ID
		naverUrl : $('#g_naverUrl' ).val(), // 네이버URL
		kakaoUrl : $('#g_kakaoUrl' ).val()  // 카카오URL
	};
	switch (P_PARAMS.stepCd) {
		case 'CHOS': $('#appContent').appJoinChoice		(P_PARAMS); break; // 유형선택
		case 'AGRE': $('#appContent').appJoinAgreeVer2  (P_PARAMS); break; // 약관동의
		case 'FUSR': $('#appContent').appJoinUsr   		(P_PARAMS); break; // 개인정보
		case 'FBIZ': $('#appContent').appJoinBiz   		(P_PARAMS); break; // 기업정보
		case 'DONE': $('#appContent').appJoinDone  		(P_PARAMS); break; // 가입완료
	}
});

// 휴대폰 본인인증 팝업창에서 호출하는 함수
function certifyCallback( data ) {
	$.appUtils.certifyCallback(data);
}
