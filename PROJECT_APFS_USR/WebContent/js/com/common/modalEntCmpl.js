/**
*******************************************************************************
***    명칭: modalEntCmpl.js
***    설명: 공통 - 반려 업체 제출 서류 보완 제출 팝업
***
***    -----------------------------    Modified Log   ------------------------
***    버전        수정일자        수정자               내용
*** ---------------------------------------------------------------------------
***    1.0      2023.09.04      LSH            First Coding.
*******************************************************************************
**/
$(document).ready(function () {

	let P_CARD = $('#p_appCard').appJoinBiz({
		mode       : MODE.UPDATE,
		cls        : '',
		params     : {
			bzentySeCd : $('#p_bzentySeCd' ).val(),
			bzentyNo   : $('#p_bzentyNo'   ).val(),
			brno       : $('#p_brno'       ).val(),
			userNo     : $('#p_userNo'     ).val(),
			userId     : $('#p_userId'     ).val(),
			kdCd       : $('#p_kdCd'       ).val(),
			rprsYn     : $('#p_rprsYn'     ).val(),
			kodataYn   : $('#p_kodataYn'   ).val(),
			matchYn    : $('#p_matchYn'    ).val(),
			existYn    : $('#p_existYn'    ).val(),
		}
	});

	// 제출 버튼 클릭시 이벤트 처리
	$('#btnSubmit').bind('click', function() {
		P_CARD.doSaveUpdate(function() {
			modal.close();
		});
		return false;
	});
	// 닫기 버튼 클릭시 이벤트 처리
	$('#btnClose').bind('click', function() {
		modal.close();
		return false;
	});
});
