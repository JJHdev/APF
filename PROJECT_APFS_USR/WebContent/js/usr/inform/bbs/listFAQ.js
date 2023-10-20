/**
*******************************************************************************
***    명칭: listFAQ.js
***    설명: 정보서비스 - 자주묻는질문 화면
***
***    -----------------------------    Modified Log   ------------------------
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
	let P_FORM    = $('#searchForm'  					    );  // 검색폼	
    let P_BBS_CD  = $('#bbsSeCd'  					  ).val();  // 게시판 구분코드
    let P_CLSF_CD = $('#pstClsfCd'					  ).val();  // 게시판 분류코드
	let P_SRCHTXT = $('#srchText' 					  ).val();  // 검색텍스트 (이전에서 넘어온항목)
	let P_SU_PAGE = $('#searchForm input[name="page"]').val();	// 사용자 게시판 조회폼 객체
	let ROWS      = 8;                                         // 게시판 ROWS값
	
	// 게시판분류코드 기본값 처리
	if ($.commUtil.empty(P_CLSF_CD)) {
		P_CLSF_CD = CODE.NTT_CL.NTC01;
	}
	
    // 게시판 검색처리
    //--------------------------------------------------------//
    function doSearch() {
        // 검색폼 데이터 객체화
        var obj  = P_FORM.serializeObject();
        obj.rows = ROWS; // 원하는 rows 값으로 설정
        // 검색폼 그리드 검색
        loadAccordion(
        		'.accordion ul',
        		getUrl('/usr/inform/bbs/getListFAQ.do'), 
        		obj
        	);
        return false;
    }
    // 상단 검색박스 표시 (app_bizutils.js 참고)
	$.bizUtils.showHeaderBox({
		type : 'searchbox',
		id   : 'topSrchText',
		name : 'topSrchText',
		value: P_SRCHTXT,
		placeholder: '검색어를 입력하세요.',
		callback: function(v) {
			$('#srchText').val(v);
			doSearch();
		}
	});
	
    //========================================================//
    // 분류 탭 생성
    //--------------------------------------------------------//
	$('#appFAQTab').appBbsTabs({
		url:    getUrl('/usr/inform/bbs/getListBbsTab.do'),
		params: {bbsSeCd: P_BBS_CD},
		value:  P_CLSF_CD,
		select: function(v) {
			$('#pstClsfCd').val(v);
			doSearch();
		}
	});
    // 첫 번째 <a> 요소에 'active' 클래스 추가
	$('#appFAQTab ul li a').removeClass('active');
    $('#appFAQTab ul li a').first().addClass('active');
	
	loadAccordion(
		'.accordion ul',
		getUrl('/usr/inform/bbs/getListFAQ.do'), 
		{bbsSeCd: P_BBS_CD,				// 게시판 구분코드
		page    : P_SU_PAGE ,			// 현재 페이지
		rows    : ROWS }				// 총보여줄 페이지 수
	);
	
	// FAQ 자주묻는질문 게시글및 pageNation 호출
	$(document).ready(function(){
		$('.accordion>ul>li>a').click(function(){
			$(this).parent().addClass('active').siblings().removeClass("active");
		});
	});
	
	function goQna() {
		let url = getUrl('/usr/inform/bbs/formQNA.do')
		goUrl(url);
		return false;
	}
		    
	 // 이전버튼 클릭시 이벤트처리
	$('#btnGoQna ').bind('click', goQna);
});
