/**
*******************************************************************************
***    명칭: viewNotice.jsp
***    설명: 고객센터 - 공지사항 상세보기 화면
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.05.18    LSH        First Coding.
***    1.1      2023.06.02    J H        그리드 표출및 상세조회.
***    1.2      2023.06.28    J H        작업완료.
*******************************************************************************
**/
$(function() {

    //========================================================//
    // 화면 스크립트 내에 사용할 객체,상수 정의
    //--------------------------------------------------------//
    let P_FILE_TYPE 	= CODE.FILE_TYPE.BBS;	// 첨부파일 종류
    let P_FILE_BOX  	= $('#attachFile' );  	// 첨부파일 컨트롤 객체
    let P_PST_NO    	= $('#pstNo'  ).val();	// 게시글 키정보
    let P_BBS_CD    	= $('#bbsSeCd').val();	// 게시판 구분코드
    let P_PAGE	    	= $('#page').val();		// 게시판 페이지
    let P_PST_CLSF_CD 	= $('#pstClsfCd').val();// 게시판 구분코드
    let P_SRCHTXT 		= $('#srchText').val();// 게시판 구분코드
    let P_RFORM     	= $('#selectForm' );  	// 등록폼 객체
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
    
    function goList(){
		const attr = ['bbsSeCd','page','pstClsfCd','srchText'];
		const value = [P_BBS_CD,P_PAGE,P_PST_CLSF_CD,P_SRCHTXT];
		let f = document.createElement('form');
		f.setAttribute('method', 'post');
		f.setAttribute('action', getUrl('/usr/inform/bbs/listNotice.do'));
		for(let i=0; i<attr.length; i++){
			let obj;
			obj = document.createElement('input');
			obj.setAttribute('type' , 'hidden'  );
			obj.setAttribute('id'   ,  attr[i] 	);   // input field의 id을 설정합니다.
			obj.setAttribute('name' ,  attr[i]	);
			obj.setAttribute('value',  value[i]	);   // input field의 value를 설정합니다.
			f.appendChild(obj					);
		}
		document.body.appendChild(f);
		f.submit();
		return false;
	}
    //========================================================//
    // 버튼 이벤트 처리
    //--------------------------------------------------------//
    // 목록버튼 클릭시 이벤트 처리
    $('#btnList'  ).bind('click', goList);
});
