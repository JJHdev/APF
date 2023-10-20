/**
*******************************************************************************
***    명칭: modalInvestFieldForm.js
***    설명: 운영관리-투자분야관리 화면
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자           내용
*** ---------------------------------------------------------------------------
***    1.0      2023.06.09      LHB       First Coding.
*******************************************************************************
**/
$(function() {

    //========================================================//
    // 화면 스크립트 내에 사용할 객체,상수 정의
    //--------------------------------------------------------//
    let P_RFORM		= $('#registForm');			// 등록폼 객체
    let P_MODE		= $('#mode'      ).val();	// 처리 모드
    let P_BANNER_NO	= $('#bannerNo'  ).val();	// 게시글 키정보

	//========================================================//
    // 목록 GRID 정의 (easyui DATAGRID)
    //--------------------------------------------------------//

    //========================================================//
    // FORM ELEMENTS 정의
    //--------------------------------------------------------//
	// 노출여부 라디오버튼
	//$('#appUseYn').appSwitchBox({id: 'useYn', name: 'useYn', value: 'Y'});
    
    //========================================================//
    // VALIDATION RULE 정의
    //--------------------------------------------------------//
    P_RFORM.validate({
        debug: false,
        onsubmit: false,
        onfocusout: false,
        // 검증룰 정의
        rules: {
            invtFldNm	: {required: true,
                           maxlength: 100},
			invtFldCn	: {required: true,
			               maxlength: 1000},
            useBgngYmd	: {required: true,
                           date: true},
			useEndYmd	: {date: true,
                           afterDate: '#useBgngYmd'},
        },
        // 검증메세지 정의
        messages: {
			invtFldNm	: {required: '투자분야명은 필수 입력사항입니다.',
                           maxlength: jQuery.validator.format('최대 {0}자 이하')},
			invtFldCn	: {required: '설명은 필수 입력사항입니다.',
			               maxlength: jQuery.validator.format('최대 {0}자 이하')},
            useBgngYmd	: {required: '사용시작일자는 필수 입력사항입니다.',
                           date: '사용시작일자의 형식이 올바르지 않습니다.'},
			useEndYmd	: {date: '사용종료일자의 형식이 올바르지 않습니다.',
			               afterDate: '사용종료일자는 사용시작일자보다 이후여야 합니다.'},
        },
        invalidHandler: validateHandler,
        errorPlacement: validatePlacement
    });
	
	// 투자분야관리 저장하기
    //--------------------------------------------------------//
    function doSave() {

        // 등록폼의 VALIDATION 기능 활성화
        if (P_RFORM.validate().settings)
            P_RFORM.validate().settings.ignore = false;

        //FORM VALIDATION
        if (P_RFORM.valid() === false)
            return false;

        $.commMsg.confirm("저장하시겠습니까?", function() {
            // 등록폼을 AJAX로 저장처리
            P_RFORM.ajaxForm({
                url: getUrl('/adm/invest/investfield/saveInvestField.do'),
                // 오류시 처리로직
                error: $.ajaxUtil.error,
                // 저장후 처리로직
                success: function(ret) {
					$.commMsg.success(ret, '성공적으로 저장되었습니다.', function() {
						P_MODAL.doSearch();
					});
                }
            }).submit();
        });
        return false;
    }

	// 투자분야관리 삭제하기
    //--------------------------------------------------------//
    function doRemove() {
	
        $.commMsg.confirm("정말로 삭제하시겠습니까?", function() {
			$.formUtil.toForm({
				mode     : MODE.REMOVE, // 삭제모드
			}, P_RFORM);
	
            // 등록폼을 AJAX로 저장처리
            P_RFORM.ajaxForm({
                url: getUrl('/adm/invest/investfield/saveInvestField.do'),
                // 오류시 처리로직
                error: $.ajaxUtil.error,
                // 저장후 처리로직
                success: function(ret) {
					$.commMsg.success(ret, '성공적으로 삭제되었습니다.', function() {
                        P_MODAL.doSearch();
					});
                }
            }).submit();
        });
        return false;
    }
	
    // 취소버튼 클릭시 이벤트처리
    //--------------------------------------------------------//
    $('#btnCancel').bind('click', function() {
		// 부모창의 doClose 호출
		P_MODAL.doClose();
        return false;
	});
	
	$('.datepicker-input').datepicker(OPTIONS.DATEPICKER);

	// 저장버튼 클릭시 이벤트 처리
    $('#btnSave'  ).bind('click', doSave);
	// 삭제버튼 클릭시 이벤트 처리
	$('#btnRemove').bind('click', doRemove);

});