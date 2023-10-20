/**
*******************************************************************************
***    명칭: modalFindPswd.js
***    설명: 공통 - 비밀번호 찾기 팝업
***
***    -----------------------------    Modified Log   ------------------------
***    버전        수정일자        수정자               내용
*** ---------------------------------------------------------------------------
***    1.0      2023.07.28      LHB            First Coding.
*******************************************************************************
**/
$(document).ready(function () {
	console.log('hello');
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
			userId   : { required: true,
			             email   : true },
			userNm   : { required: true },
			mblTelno : { required: true,
			             mobile  : true }
		},
		messages: {
			userId   : { required: '사용자ID를 입력해 주세요.',
			             email   : '사용자ID의 형식이 올바르지 않습니다.'},
			userNm   : { required: '이름을 입력해 주세요.' },
			mblTelno : { required: '휴대전화번호를 입력해 주세요.',
			             mobile  : '휴대전화번호의 형식이 올바르지 않습니다.' }
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
				
				$("#result-div").hide();
				$("#result-div div:eq(0)").hide();
				$("#result-div div:eq(1)").hide();
				
				if (code === -1) {
					$("#result-div div:eq(0)").show();
					$("#result-div").show();
				} else if (code === -2) {
					doClose();
					$.commMsg.alert('시스템 에러가 발생했습니다.');
				} else {
					if (code != 2) {
						doClose();
						$.commMsg.alert('회원님의 이메일 주소로 임시비밀번호를 발송했습니다.<br/>임시비밀번호로 로그인 후 마이페이지>기본정보에서 비밀번호를 재설정하세요.');	
					} else {
						$("#result-div div:eq(1)").show();
						$("#result-div").show();
					}
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