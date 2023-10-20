/**
*******************************************************************************
***    명칭: listNotice.js
***    설명: 고객센터 - 공지사항 화면
***
***    -----------------------------    Modified Log   ------------------------
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
	let P_GRID   		= $('#appGrid'     	  ); // 목록 GRID
	let P_FORM   		= $('#searchForm'  	  ); // 검색폼	
    let P_BBS_CD  		= $('#bbsSeCd'  ).val(); // 게시판 구분코드
    let P_CLSF_CD		= $('#pstClsfCd').val(); // 게시글 분류코드
	let P_SRCHTXT 		= $('#srchText' ).val(); // 검색텍스트 (이전에서 넘어온항목)
	let P_GS_ROLE_ID 	= $('#gsRoleId' ).val(); // 유저권한 
	
	// 게시판분류코드 기본값 처리
	// 첫 번째 <a> 요소에 'active' 클래스 추가
	if (P_GS_ROLE_ID === 'ROLE_USR_EIS' || P_GS_ROLE_ID === 'ROLE_USR_EIV') {
		$('#appNoticeTab ul li a').removeClass('active');
		$('#appNoticeTab ul li a').first().addClass('active');
		$('#noticeTab').show();
		P_CLSF_CD = CODE.NTT_CL.NTC01; 
	}else {
		P_CLSF_CD = CODE.NTT_CL.NTC01; 
		$('#noticeTab').hide();
	}
	
    //========================================================//
    // 목록 GRID 정의 (EasyUI datagrid)
    //--------------------------------------------------------//
    P_GRID.appBbsGrid({
		// 목록 KEY필드
		idField:    'pstNo',
		// 목록 제목
		title:      '공지사항',
		// 검색 URL
		url:         getUrl('/usr/inform/bbs/getListNotice.do'),
		//목록 검색 페이징 객체
		pagination: {display: 10},
		// 페이지 표시 객체
		paging:     '#appGridPagination',
		// 리스트옵션
		listOptions: {
			// 검색결과 없을경우 메세지
			emptyText:  '검색된 {title}이 없습니다.',
			// 목록칼럼 너비
			colgroup: ['100px','650px','250px','100px'],
			// 목록칼럼 정의
			columns: [
	            {name:'pstNo'		,cls:'app-c',label:'번호', 
					formatter: function(v,o) {
						// 공지인 경우
						let currentDate = new Date();
						currentDate.setHours(0, 0, 0, 0);
						let beginDate   = new Date(o['fixingBgngYmd']);
						beginDate.setHours(0, 0, 0, 0);
						let endDate     = new Date(o['fixingEndYmd']);
						endDate.setHours(23, 59, 59, 999);
						if (o['fixingYn'] == 'Y' && currentDate >= beginDate && currentDate <= endDate)
							return '<i class="icon-megaphone fs-18px text-primary"></i>';
						return v;
					},
				 rownumbers: true
				},
	            {name:'pstTtl'       ,cls:'app-l',label:'제목'},
/*	            {name:'rgtrNm'       ,cls:'app-c',label:'작성자', formatter: function(v, o) {
													                return o.mdfrNm || v;
													            }},*/
	            {name:'regDate'      ,cls:'app-c',label:'작성일'},
	            {name:'inqCnt'       ,cls:'app-r',label:'조회수', formatter: $.formatUtil.toNumber},
			],
	        // 행선택시 상세조회
	        select: doSelect
		}
    }).appBbsGrid('init');
    
    
	$('#appNoticeTab').appBbsTabs({
		url:    getUrl('/usr/inform/bbs/getListBbsTab.do'),
		params: {bbsSeCd: P_BBS_CD, pstClsfCd:P_CLSF_CD},
		value:  P_CLSF_CD,
		select: function(v) {
			$('#pstClsfCd').val(v);
			doSearch();
		}
	});
	
    // 게시판 검색처리
    //--------------------------------------------------------//
    function doSearch() {
        // 검색폼 데이터 객체화
    	var obj = P_FORM.serializeObject();
        // 이전 페이지번호
 		var page = obj['page'];
 		// 검색폼 그리드 검색
		if (page && parseInt(page) > 1) {
			// 페이지번호 기준 검색 (초기 로딩시)
			P_GRID.appBbsGrid('search', obj, page);
			// 페이지번호 초기화
			$('#page').val('');
		}
		else
			P_GRID.appBbsGrid('search', obj);
        return false;
    }

    // 게시판 상세조회
    //--------------------------------------------------------//
    function doSelect(row) {
		let pg = P_GRID.appBbsGrid('getPagination');
        $.formUtil.submitForm( getUrl('/usr/inform/bbs/viewNotice.do'), {
			params: {
				pstNo     : row['pstNo'  ],
				bbsSeCd   : row['bbsSeCd'],
				pstClsfCd : $('#pstClsfCd').val(),
				srchText  : $('#srchText' ).val(),
				page      : pg['page'    ],
			}
		});
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
});
