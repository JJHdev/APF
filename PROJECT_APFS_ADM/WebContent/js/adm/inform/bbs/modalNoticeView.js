/**
*******************************************************************************
***    명칭: openNotice.jsp
***    설명: 운영관리-공지사항 화면
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.04.04    LSH        First Coding.
*******************************************************************************
**/
$(function() {

    //========================================================//
    // 화면 스크립트 내에 사용할 객체,상수 정의
    //--------------------------------------------------------//
    let P_FILE_TYPE = CODE.FILE_TYPE.BBS; 			// 첨부파일 종류
    let P_FILE_BOX  = $('#attachFile' 	);  		// 첨부파일 컨트롤 객체
    let P_PST_NO    = $('#pstNo'  		).val();	// 게시글 키정보
    let P_BBS_CD    = $('#bbsSeCd'		).val();	// 게시판 구분코드

    //========================================================//
    // 첨부파일 초기화 (app_bbsfile.js 참고)
    //--------------------------------------------------------//
    P_FILE_BOX.appBbsFile({
        label: false,
        // 처리모드
        mode: MODE.VIEW,
        initData: {
			fileSe	: CODE.FILE_SE.FILE,
			fileType: P_FILE_TYPE,
			docuCd	: BBS_TASK_CD.CODE,
			docuNo	: P_PST_NO
        }
    }).appBbsFile('init');
    // 수정하기
    //--------------------------------------------------------//
    function doModify() {
		// 조회폼 데이터 객체화
		let params = $('#selectForm').serializeObject();
		// 처리모드 조건값 추가
		$.extend(params, { mode : MODE.UPDATE});
		// 부모창의 doOpenUpdate를 호출하여 이동한다.
		P_MODAL.doOpenUpdate(params);
        return false;
    }
    // 삭제하기
    //--------------------------------------------------------//
    function doRemove() {
        $.commMsg.confirm("정말 삭제하시겠습니까?", function() {
            // AJAX로 삭제처리
            $.ajaxUtil.ajaxLoad(
                getUrl('/adm/inform/bbs/saveBbs.do'), 
                {pstNo   : P_PST_NO,
				 bbsSeCd : P_BBS_CD,
                 mode    : MODE.REMOVE
                }, 
                function(ret) {
                    $.ajaxUtil.success(ret, function() {
                        // 목록으로 이동
                        $.commMsg.alert('성공적으로 삭제처리되었습니다.', function() {
							// 부모창의 목록재검색 처리 및 팝업닫기
							P_MODAL.doSearch();
						});
                    });
                }
            );
        });
        return false;
    }
    // 이전 게시물 이동
    //--------------------------------------------------------//
    function goNext() {
		// 조회폼 데이터 객체화
		let params = $('#selectForm').serializeObject();
		// 버튼타입 조건값 추가
		$.extend(params, { movePage : "next"});
		// 부모창의 doOpenSelect를 호출하여 이동한다.
		P_MODAL.doOpenSelect(params);
        return false;
    }
    // 다음 게시물 이동
    //--------------------------------------------------------//
    function goBefore() {
		// 조회폼 데이터 객체화
		let params = $('#selectForm').serializeObject();
		// 버튼타입 조건값 추가
		$.extend(params, { movePage : "before"});
		// 부모창의 doOpenSelect를 호출하여 이동한다.
		P_MODAL.doOpenSelect(params);
        return false;
    }
       
    //========================================================//
    // 버튼 이벤트 처리
    //--------------------------------------------------------//
    // 수정버튼 클릭시 이벤트처리
    $('#btnModify').bind('click', doModify);
    // 삭제버튼 클릭시 이벤트처리
    $('#btnRemove').bind('click', doRemove);
    // 이전버튼 클릭시 이벤트처리
    $('#btnNext  ').bind('click', goNext);
    // 다음버튼 클릭시 이벤트처리
    $('#btnBefore').bind('click', goBefore);
});
