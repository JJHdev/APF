/**
*******************************************************************************
***    명칭: modalInvtSprtForm.js
***    설명: 지원사업관리-세부지원사업관리 화면
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.06.07      LHB      First Coding.
*******************************************************************************
**/
$(function() {

    //========================================================//
    // 화면 스크립트 내에 사용할 객체,상수 정의
    //--------------------------------------------------------//
    let P_RFORM			= $('#registForm');			// 등록폼 객체
    let P_MODE			= $('#mode'      ).val();	// 처리 모드
    let P_PRGRM_NO		= $('#prgrmNo'    ).val();	// 게시글 키정보
	let P_SA_SE_CD		= $('#sprtAplySeCd').val();	// 구분

    //========================================================//
    // FORM ELEMENTS 정의
    //--------------------------------------------------------//
	// 프로그램분류 콤보박스
	$('#prgrmClsfCd').appComboBox({
		params: {upCdId: CODE.PROG_TY.code},
		loadFilter: function(data) {
			data.unshift(COMBO.INIT_SELECT);
			return data;
		},
	});
	// 표출여부 라디오버튼
	$('#appUseYn').appSwitchBox({id: 'useYn', name: 'useYn', value: 'Y'});
    
    //========================================================//
    // VALIDATION RULE 정의
    //--------------------------------------------------------//
    P_RFORM.validate({
        debug: false,
        onsubmit: false,
        onfocusout: false,
        // 검증룰 정의
        rules: {
			prgrmClsfCd  : {required: function() {
								if (P_SA_SE_CD == 'SA') {
									return true;
								} else {
									return false;
								}
				           }},
            prgrmNm      : {required: true,
                            maxlength: 100},
			sprtBgngYmd  : {required: true,
			                date: true},
			sprtEndYmd   : {required: true,
			                date: true},
        },
        // 검증메세지 정의
        messages: {
			prgrmClsfCd   : {required: '프로그램분류는 필수 선택사항입니다.'},
            prgrmNm       : {required: '프로그램명은 필수 입력사항입니다.',
                             maxlength: jQuery.validator.format('최대 {0}자 이하')},
			sprtBgngYmd   : {required: '지원시작일은 필수 입력사항입니다.',
                             date: '지원시작일을 형식에 맞게 입력해주세요.'},
			sprtEndYmd    : {required: '지원종료일은 필수 입력사항입니다.',
                             date: '지원종료일을 형식에 맞게 입력해주세요.'},
        },
        invalidHandler: validateHandler,
        errorPlacement: validatePlacement
    });

	// 세부지원사업 저장하기
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
                url: getUrl('/adm/support/support/saveInvtSprt.do'),
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

	// 세부지원사업 삭제하기
    //--------------------------------------------------------//
    function doRemove() {
	
        $.commMsg.confirm("정말로 삭제하시겠습니까?", function() {
			$.formUtil.toForm({
				mode     : MODE.REMOVE, // 삭제모드
			}, P_RFORM);
	
            // 등록폼을 AJAX로 저장처리
            P_RFORM.ajaxForm({
                url: getUrl('/adm/support/support/saveInvtSprt.do'),
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

    // 저장버튼 클릭시 이벤트 처리
    $('#btnSave'  ).bind('click', doSave);
	// 삭제버튼 클릭시 이벤트 처리
    $('#btnRemove').bind('click', doRemove);
	
    // 취소버튼 클릭시 이벤트처리
    //--------------------------------------------------------//
    $('#btnCancel').bind('click', function() {
		// 부모창의 doClose 호출
		P_MODAL.doClose();
        return false;
	});
	
	$('.datepicker-input').datepicker(OPTIONS.DATEPICKER);
});