/**
*******************************************************************************
***    명칭: viewInvestCase.jsp
***    설명: 고객센터 - 공지사항 상세보기 화면
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.06.07    J H        First Coding.
***    1.1      2023.06.28    J H        작업완료.
*******************************************************************************
**/
$(function() {

    //========================================================//
    // 화면 스크립트 내에 사용할 객체,상수 정의
    //--------------------------------------------------------//
    let P_FILE_TYPE       = CODE.FILE_TYPE.BBS; // 첨부파일 종류
    let P_FILE_BOX        = $('#attachFile' );  // 첨부파일 컨트롤 객체
    let P_PST_NO          = $('#pstNo'  ).val();   // 게시글 키정보
    let P_BBS_CD          = $('#bbsSeCd').val();   // 게시판 구분코드
    let P_PST_LINK_URL    = $('#pstLinkUrl').val();// 영상 주소 조회
    
    // 주어진 Youtube URL을 변환작업.
    var oldUrl = P_PST_LINK_URL;
    var videoId = oldUrl.split('v=')[1];
    var newUrl = "https://www.youtube.com/embed/" + videoId;
    var pstLinkUrlEmbed = document.createElement("embed");
    pstLinkUrlEmbed.src = newUrl;
    pstLinkUrlEmbed.width = 854;
    pstLinkUrlEmbed.height = 480;
    
    // 생성한 embed 태그를 원하는 위치에 추가합니다.
	if (document.getElementById('bbsPstLinkUrl')) {
		document.getElementById('bbsPstLinkUrl').appendChild(pstLinkUrlEmbed);	
	} // 23.08.18 LHB #bbsPstLinkUrl을 찾지 못하는 에러로 인한 조건 처리 
    
    
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
    
    // 목록이동
    //--------------------------------------------------------//
    function goList() {
       $.formUtil.submitForm(getUrl("/usr/inform/bbs/listInvestCase.do"), {
           formId : "selectForm"
       });
    return false;
    }
    
    //========================================================//
    // 버튼 이벤트 처리
    //--------------------------------------------------------//
    // 목록버튼 클릭시 이벤트 처리
    $('#btnList'  ).bind('click', goList);
});
