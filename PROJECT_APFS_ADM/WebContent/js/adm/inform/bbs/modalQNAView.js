/**
*******************************************************************************
***    명칭: openNotice.jsp
***    설명: 운영관리-공지사항 화면
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.04.04    LSH        First Coding.
***    1.1      2023.06.27    J H        작업 완료.
*******************************************************************************
**/
$(function() {
	
    //========================================================//
    // 화면 스크립트 내에 사용할 객체,상수 정의
    //--------------------------------------------------------//
    let P_SU_FORM          	= $('#selectUsrForm'                           );		// 사용자 게시판 조회폼 객체
    let P_SU_PST_NO		    = $('#selectUsrForm input[name="pstNo"]'       ).val();	// 사용자 게시판 조회폼 객체
    let P_SU_BBS_CD		    = $('#selectUsrForm input[name="bbsSeCd"]'     ).val();	// 사용자 게시판 조회폼 객체
	let P_SU_IS_NEXT	    = $('#selectUsrForm input[name="isNext"]'      ).val();	// 사용자 게시판 조회폼 객체   
	let P_SU_IS_BEFORE	    = $('#selectUsrForm input[name="isBefore"]'    ).val();	// 사용자 게시판 조회폼 객체
						    
	let P_SA_FORM          	= $('#selectAdmForm'                           );		// 관리자 게시판 조회폼 객체
    let P_SA_PST_NO		    = $('#selectAdmForm input[name="pstNo"]'       ).val();	// 관리자 게시판 조회폼 객체
    let P_SA_BBS_CD		    = $('#selectAdmForm input[name="bbsSeCd"]'     ).val();	// 관리자 게시판 조회폼 객체
    let P_SA_UP_PST_NO	    = $('#selectAdmForm input[name="upPstNo"]'     ).val();	// 관리자 게시판 조회폼 객체

    // 수정하기 클릭시 이벤트 처리
    //--------------------------------------------------------//
    function doModify() {
		// 조회폼 데이터 객체화
		let params = $('#selectUsrForm').serializeObject();
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
                {pstNo   : P_SA_PST_NO,
				 bbsSeCd : P_SA_BBS_CD,
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
    // 다음 게시물 이동
    //--------------------------------------------------------//
    function goNext() {
    	if(P_SU_IS_NEXT=='Y'){
    		// 조회폼 데이터 객체화
    		let params = $('#selectUsrForm').serializeObject();
    		// 버튼타입 조건값 추가
    		$.extend(params, { movePage : "next"});
    		// 부모창의 doOpenSelect를 호출하여 이동한다.
    		P_MODAL.doOpenSelect(params);
    	}else{
    		// 조회폼 데이터 객체화
    		let params = $('#selectUsrForm').serializeObject();
    		// 버튼타입 조건값 추가
    		$.extend(params, { movePage : "next"});
    		// 부모창의 doOpenSelect를 호출하여 이동한다.
    		P_MODAL.doOpenUpdate(params);
    	}
        return false;
    }
    // 이전 게시물 이동
    //--------------------------------------------------------//
    function goBefore() {
    	if(P_SU_IS_BEFORE=='Y'){
    		// 조회폼 데이터 객체화
    		let params = $('#selectUsrForm').serializeObject();
    		// 버튼타입 조건값 추가
    		$.extend(params, { movePage : "before"});
    		// 부모창의 doOpenSelect를 호출하여 이동한다.
    		P_MODAL.doOpenSelect(params);
    	}else{
    		// 조회폼 데이터 객체화
    		let params = $('#selectUsrForm').serializeObject();
    		// 버튼타입 조건값 추가
    		$.extend(params, { movePage : "before"});
    		// 부모창의 doOpenSelect를 호출하여 이동한다.
    		P_MODAL.doOpenUpdate(params);
    	}
        return false;
    }
       
    //========================================================//
    // 버튼 이벤트 처리
    //--------------------------------------------------------//
    // 수정및 답변버튼 클릭시 이벤트처리
    $('#btnModify').bind('click', doModify);
    // 삭제버튼 클릭시 이벤트처리
    $('#btnRemove').bind('click', doRemove);
    // 이전버튼 클릭시 이벤트처리
    $('#btnNext  ').bind('click', goNext);
    // 다음버튼 클릭시 이벤트처리
    $('#btnBefore').bind('click', goBefore);
});
