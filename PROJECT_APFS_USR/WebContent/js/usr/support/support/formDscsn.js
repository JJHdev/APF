/**
*******************************************************************************
*** 파일명 : formDscsn.js
*** 설명글 : 지원사업신청 - 상담신청폼 화면 스크립트
***          app_support.js 참고
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.05.25    LSH        First Coding.
*******************************************************************************
**/
$(function() {

    //========================================================//
    // 화면 스크립트 내에 사용할 객체,상수 정의
    //--------------------------------------------------------//
	let P_PARAMS = {
		btnId     : '#appButtons',
		formId    : '#selectForm',
		bizYn     : $('#gsBzentyYn').val(), // 업체여부
		sprtSeCd  : $('#sprtSeCd'  ).val(), // 지원구분
		sprtSeNm  : $('#sprtSeNm'  ).val(), // 지원구분
		prgrmNo   : $('#prgrmNo'   ).val(), // 지원종류코드
		prgrmNm   : $('#prgrmNm'   ).val(), // 지원종류명칭
		stepNo    : $('#stepNo'    ).val(), // 신청단계
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
	if (!$.commUtil.empty(P_PARAMS.prgrmNo)) {
		$('#appSprtTitle').appSprtTitle(P_PARAMS);
	}

	switch (P_PARAMS.stepCd) {
		case 'FORM': $('#appContent').appDscsnForm (P_PARAMS); break; // 상담신청서작성
		case 'APLY': $('#appContent').appSprtApply (P_PARAMS); break; // 접수완료
	}
});
