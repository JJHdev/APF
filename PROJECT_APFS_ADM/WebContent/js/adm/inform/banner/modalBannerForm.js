/**
*******************************************************************************
***    명칭: modalBannerForm.js
***    설명: 운영관리-배너관리 화면
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

	var P_FILE_BOX  = $('#attachFile');  // 첨부파일 컨트롤 객체
	
	//========================================================//
    // 첨부파일 초기화
    //--------------------------------------------------------//
	
	P_FILE_BOX.appBbsFile({
		cls: 'border-0 mb-0 pb-0',
		// 처리모드
		mode: P_MODE,
		// 다운로드 가능 여부
		canDownload: true,
		// 설명글
		comment: {
			check: '',
			input: '100MB 이내 1개 파일 가능'
		},
		initData: {
			fileType: CODE.FILE_TYPE.BBS,
			fileSeCd: '00',
			taskSeCd: 'BB3',
			docuNo  : P_BANNER_NO,
			fileYn  : 'Y',
			needYn  : 'Y',
		},
		label: false,
		extensions: COMMONS.IMAGE_EXTENSIONS
	}).appBbsFile('init');

	//========================================================//
    // 목록 GRID 정의 (easyui DATAGRID)
    //--------------------------------------------------------//

    //========================================================//
    // FORM ELEMENTS 정의
    //--------------------------------------------------------//
	// 노출여부 라디오버튼
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
            bannerNm       : {required: true,
                              maxlength: 100},
			bannerUrl	   : {url: true},
			bannerExpln    : {required: false,
			                  maxlength: 1000},
            pstgBgngYmd    : {required: true,
                              date: true},
			pstgEndYmd     : {required: true,
                              date: true,
                              afterDate: '#pstgBgngYmd'},
            use_yn    	   : {required: false}
        },
        // 검증메세지 정의
        messages: {
            bannerNm      : {required: '배너명은 필수 입력사항입니다.',
                             maxlength: jQuery.validator.format('최대 {0}자 이하')},
            bannerUrl	  : {url: '배너URL의 형식이 올바르지 않습니다.'},
			bannerExpln   : {required: false,
			                 maxlength: jQuery.validator.format('최대 {0}자 이하')},
            pstgBgngYmd   : {required: '게시시작일자는 필수 입력사항입니다.',
	                         date: '게시시작일자의 형식이 올바르지 않습니다.'},
			pstgEndYmd    : {required: '게시종료일자는 필수 입력사항입니다.',
	                         date: '게시종료일자의 형식이 올바르지 않습니다.',
                             afterDate: '게시종료일자는 게시시작일자보다 이후여야 합니다.'},
			use_yn        : {required: false}
        },
        invalidHandler: validateHandler,
        errorPlacement: validatePlacement
    });
	
	// 배너관리 저장하기
    //--------------------------------------------------------//
    function doSave() {

        // 등록폼의 VALIDATION 기능 활성화
        if (P_RFORM.validate().settings)
            P_RFORM.validate().settings.ignore = false;

        //FORM VALIDATION
        if (P_RFORM.valid() === false)
            return false;

		// 첨부파일 VALIDATION (comm_component.js)
		var fvalidate = P_FILE_BOX.appBbsFile('validate', {
			isAlert: true,
			isExt:   true,
			isLimit: true
		});
		
        if (!fvalidate)
            return false;

        $.commMsg.confirm("저장하시겠습니까?", function() {
            // 등록폼을 AJAX로 저장처리
            P_RFORM.ajaxForm({
                url: getUrl('/adm/inform/banner/saveBanner.do'),
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

	// 배너관리 삭제하기
    //--------------------------------------------------------//
    function doRemove() {
	
        $.commMsg.confirm("정말로 삭제하시겠습니까?", function() {
			$.formUtil.toForm({
				mode     : MODE.REMOVE // 삭제모드
			}, P_RFORM);
	
            // 등록폼을 AJAX로 저장처리
            P_RFORM.ajaxForm({
                url: getUrl('/adm/inform/banner/saveBanner.do'),
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
	
	P_FILE_BOX.appBbsFile('loadBox', false, {
		fileType: CODE.FILE_TYPE.BBS,
		fileSeCd: '00',
		docuNo  : P_BANNER_NO
	});
	
});