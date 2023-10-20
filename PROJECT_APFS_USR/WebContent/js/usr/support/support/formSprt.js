/**
*******************************************************************************
*** 파일명 : formSprt.js
*** 설명글 : 지원사업신청 - 투자유치 전 지원 신청폼 화면 스크립트
***          지원사업신청 - 투자유치 후 지원 신청폼 화면 스크립트
***          지원사업신청 - 농식품 크라우드 펀딩 지원 신청폼 화면 스크립트
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
		btnId     : '#appButtons',
		formId    : '#selectForm',
		bizYn     : $.commUtil.nvl($('#gsBzentyYn').val()), // 업체여부
		aplyNo    : $.commUtil.nvl($('#sprtAplyNo').val()), // 신청단계
		sprtSeCd  : $.commUtil.nvl($('#sprtSeCd'  ).val()), // 지원구분
		sprtSeNm  : $.commUtil.nvl($('#sprtSeNm'  ).val()), // 지원구분
		prgrmNo   : $.commUtil.nvl($('#prgrmNo'   ).val()), // 지원종류코드
		prgrmNm   : $.commUtil.nvl($('#prgrmNm'   ).val()), // 지원종류명칭
		stepNo    : $.commUtil.nvl($('#stepNo'    ).val()), // 신청단계
		stepCd    : false,
		stepNm    : false,
	};
	
	// 탭 생성
	$('#appSprtTab').appSprtTab(P_PARAMS);
	
	// 단계박스 표시
	let obj = $('#appStepBox').appSprtStepBox({
		sprtSeCd: P_PARAMS.sprtSeCd,
		prgrmNo : P_PARAMS.prgrmNo,
		stepNo  : P_PARAMS.stepNo
	});
	$.extend(P_PARAMS, {
		stepNo    : parseInt(obj['value']),
		stepCd    : obj['code'],
		stepNm    : obj['text']
	});
	
	// 지원사업 제목
	if (P_PARAMS.stepCd != 'CHOS' && $.commUtil.empty(P_PARAMS.prgrmNo) == false)
		$('#appSprtTitle').appSprtTitle(P_PARAMS);

	switch (P_PARAMS.stepCd) {
		case 'AGRE': $('#appContent').appSprtAgree (P_PARAMS); break; // 약관동의
		case 'CHOS': $('#appContent').appSprtChoose(P_PARAMS); break; // 지원사업선택
		case 'FORM': $('#appContent').appSprtForm  (P_PARAMS); break; // 신청서작성
		case 'FILE': $('#appContent').appSprtFile  (P_PARAMS); break; // 제출서류
		case 'APLY': $('#appContent').appSprtApply (P_PARAMS); break; // 접수완료
	}
});
