/**
*******************************************************************************
***    명칭: modalEntForm.js
***    설명: 회원관리-업체관리 화면
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.06.12      LHB      First Coding.
*******************************************************************************
**/
$(function() {

    //========================================================//
    // 화면 스크립트 내에 사용할 객체,상수 정의
    //--------------------------------------------------------//
    var P_RFORM			= $('#registForm');				// 등록폼 객체
    var P_MODE			= $('#mode'      ).val();		// 처리 모드
    var P_BZENTY_NO		= $('#bzentyNo'  ).val();		// 키 정보
	var P_BZENTY_SE_CD	= $('#bzentySeCd').val();		// 업체 구분

	var P_FILE_BOX		= $('#attachFile');  			// 첨부파일  컨트롤 객체
	var P_FILE_BOX2		= $('#attachFile2');  			// 첨부파일2 컨트롤 객체
	
	
	//========================================================//
    // 첨부파일 초기화
    //--------------------------------------------------------//
	// 사업자 등록증
	P_FILE_BOX.appEntFile({
		mode: MODE.VIEW,
		initData: {
            fileType: CODE.FILE_TYPE.ENT,
			docuCd:   CODE.FILE_SE.BREG,
            docuNo:   P_BZENTY_NO,
		}
	}).appEntFile('init');
	
	// 위임장 (경영체인 경우에만)
	P_FILE_BOX2.appEntFile({
		mode: MODE.VIEW,
		initData: {
            fileType: CODE.FILE_TYPE.ENT,
			docuCd:   CODE.FILE_SE.DLGT, // 위임장
            docuNo:   P_BZENTY_NO,
		}
	}).appEntFile('init');

	//========================================================//
    // 목록 GRID 정의 (easyui DATAGRID)
    //--------------------------------------------------------//

    //========================================================//
    // FORM ELEMENTS 정의
    //--------------------------------------------------------//
    
    //========================================================//
    // VALIDATION RULE 정의
    //--------------------------------------------------------//
    P_RFORM.validate({
        debug: false,
        onsubmit: false,
        onfocusout: false,
        // 검증룰 정의
        rules: {
            mngrMemo	: {required: false,
                           maxlength: 300}
        },
        // 검증메세지 정의
        messages: {
            mngrMemo	: {maxlength: jQuery.validator.format('최대 {0}자 이하') + '만 입력할 수 있습니다.'},
        },
        invalidHandler: validateHandler,
        errorPlacement: validatePlacement
    });
	
	// 업체정보 저장하기
    //--------------------------------------------------------//
    function doSave() {

        // 등록폼의 VALIDATION 기능 활성화
        if (P_RFORM.validate().settings)
            P_RFORM.validate().settings.ignore = false;

        //FORM VALIDATION
        if (P_RFORM.valid() === false)
            return false;

        $.commMsg.confirm("저장하시겠습니까?", function() {
			$.formUtil.toForm({
				useSttsCd : null,	// 저장 상태값 변경 안함
			}, P_RFORM);
	
            // 등록폼을 AJAX로 저장처리
            P_RFORM.ajaxForm({
                url: getUrl('/adm/invest/ent/saveEnt.do'),
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

	// 업체정보 반려하기
    //--------------------------------------------------------//
    function doReject() {

        // 등록폼의 VALIDATION 기능 활성화
        if (P_RFORM.validate().settings)
            P_RFORM.validate().settings.ignore = false;

        //FORM VALIDATION
        if (P_RFORM.valid() === false)
            return false;

        $.commMsg.confirm("반려하시겠습니까?", function() {
			$.formUtil.toForm({
				useSttsCd : '9',	// 반려
			}, P_RFORM);
	
            // 등록폼을 AJAX로 저장처리
            P_RFORM.ajaxForm({
                url: getUrl('/adm/invest/ent/saveEnt.do'),
                // 오류시 처리로직
                error: $.ajaxUtil.error,
                // 저장후 처리로직
                success: function(ret) {
					$.commMsg.success(ret, '성공적으로 반려되었습니다.', function() {
						P_MODAL.doSearch();
					});
                }
            }).submit();
        });
        return false;
    }

	// 업체정보 승인하기
    //--------------------------------------------------------//
    function doApprove() {

        // 등록폼의 VALIDATION 기능 활성화
        if (P_RFORM.validate().settings)
            P_RFORM.validate().settings.ignore = false;

        //FORM VALIDATION
        if (P_RFORM.valid() === false)
            return false;

        $.commMsg.confirm("승인하시겠습니까?", function() {
			$.formUtil.toForm({
				useSttsCd : '1'	// 승인
			}, P_RFORM);
	
            // 등록폼을 AJAX로 저장처리
            P_RFORM.ajaxForm({
                url: getUrl('/adm/invest/ent/saveEnt.do'),
                // 오류시 처리로직
                error: $.ajaxUtil.error,
                // 저장후 처리로직
                success: function(ret) {
					console.log(ret);
					$.commMsg.success(ret, ret['Message'], function() {
						P_MODAL.doSearch();
					});
                }
            }).submit();
        });
        return false;
    }

	// 업체정보 삭제하기
    //--------------------------------------------------------//
    function doRemove() {
	
        $.commMsg.confirm("정말로 삭제하시겠습니까?", function() {
			$.formUtil.toForm({
				mode     : MODE.REMOVE // 삭제모드
			}, P_RFORM);
	
            // 등록폼을 AJAX로 저장처리
            P_RFORM.ajaxForm({
                url: getUrl('/adm/invest/ent/saveEnt.do'),
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

	// 삭제버튼 클릭시 이벤트 처리
	$('#btnRemove' ).bind('click', doRemove );
	// 반려버튼 클릭시 이벤트 처리
	$('#btnReject' ).bind('click', doReject );
	// 승인버튼 클릭시 이벤트 처리
	$('#btnApprove').bind('click', doApprove);
	// 저장버튼 클릭시 이벤트 처리
    $('#btnSave'   ).bind('click', doSave   );
	
	// 대표번호 세팅
	$.formUtil.splitData('rprsTelno', 'phone');
	// 사업자등록번호 세팅
	$.formUtil.splitData('brno'     , 'bzno' );
});