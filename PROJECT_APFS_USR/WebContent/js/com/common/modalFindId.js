/**
*******************************************************************************
***    명칭: modalFindId.js
***    설명: 공통 - 아이디 찾기 팝업
***
***    -----------------------------    Modified Log   ------------------------
***    버전        수정일자        수정자               내용
*** ---------------------------------------------------------------------------
***    1.0      2023.07.28      LHB            First Coding.
*******************************************************************************
**/
$(document).ready(function () {
	//========================================================//
    // 화면 스크립트 내에 사용할 객체,상수 정의
    //--------------------------------------------------------//
	var P_SFORM = $('#searchForm');
	
	//========================================================//
    // VALIDATION RULE 정의
    //--------------------------------------------------------//
	P_SFORM.validate({
		debug: false,
		onfocusout: false,
		onsubmit: false,
		rules: {
			userNm   : { required: true },
			mblTelno : { required: true }
		},
		messages: {
			userNm   : { required: '이름을 입력해 주세요.' },
			mblTelno : { required: '휴대전화번호를 입력해 주세요.' }
		},
		invalidHandler: validateHandler,
		errorPlacement: validatePlacement
	});
	
	// 아이디 찾기
    //--------------------------------------------------------//
	function doSearch() {
		if (P_SFORM.validate().settings) {
			P_SFORM.validate().settings.ignore = false;
		}
		if (!P_SFORM.valid()) {
			return false;
		}
		
		const params = P_SFORM.serializeObject();
		
		$.ajaxUtil.ajaxLoad(
            getUrl('/com/common/getUserByIdPswd.do'),
			params,
            function(result) {
				const code = result['Code'];
				const data = result['Data'];
				
				$("#result-div").hide();
				$("#result-div p:eq(0)").hide();
				$("#result-div p:eq(1)").hide();
				
				if (code === -1) {
					$("#result-div p:eq(0)").show();
					$("#result-div").show();
				} else {
					$("#result-div p:eq(1) em").text(data['userId']);
					$("#result-div p:eq(1)").show();
					$("#result-div").show();
				}
            }
        );
	}
	
	function doClose() {
		modal.close();
	}
	
	// 찾기 버튼 클릭시 이벤트 처리
	$('#btnSearch').bind('click', doSearch);
	// 닫기 버튼 클릭시 이벤트 처리
	$('#btnClose').bind('click', doClose);
	
	// 숫자만 입력
	bindOnlyNumber($("#mblTelno"));
});