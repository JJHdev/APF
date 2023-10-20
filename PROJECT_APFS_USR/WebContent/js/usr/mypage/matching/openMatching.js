/**
*******************************************************************************
***    명칭: openMatching.js
***    설명: 마이페이지 - 매칭설정 화면
***
***    -----------------------------    Modified Log   ------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.06.15    LSH        First Coding.
*******************************************************************************
**/
$(function() {
	
	// 저장여부
	let P_SAVED = false;
	
	// 매칭설정 컨트롤 (app_matching.js) : 데이터 로드
	let P_MATCHING = $('#appMatchingConfig').appMatching({
		url: getUrl('/usr/mypage/matching/getMatching.do'),
		params: {}
	});

    // 매칭설정 저장하기
    //--------------------------------------------------------//
    function doSave() {
		// 필수검증 및 저장할 데이터 가져오기
		let data = P_MATCHING.validate();
		if (data) {
	        $.commMsg.confirm("설정한 항목을 저장하시겠습니까?", function() {
	            // AJAX로 저장처리
	            $.ajaxUtil.ajaxSave(
	                getUrl('/usr/mypage/matching/saveMatching.do'), 
	                JSON.stringify(data),
	                function(ret) {
						P_SAVED = true;
	                    $.ajaxUtil.success(ret, function() {
							$.commMsg.confirm(
								"매칭설정이 저장되었습니다."+
								"매칭결과를 확인하시겠습니까?", doResult);
	                    });
	                }
	            );
	        });
		}
        return false;
    }

    // 매칭설정 검색결과이동
    //--------------------------------------------------------//
    function doResult() {
		if (P_SAVED || P_MATCHING.isLoaded()) {
			goUrl(getUrl('/usr/mypage/matching/doneMatching.do'));
		}
		else {
			$.commMsg.alert('먼저 매칭설정을 저장해 주세요.');
		}
        return false;
    }

	// 저장하기 버튼 클릭
	$('#btnSave').bind('click', doSave);
	// 매칭결과 버튼 클릭
	$('#btnList').bind('click', doResult);
});
