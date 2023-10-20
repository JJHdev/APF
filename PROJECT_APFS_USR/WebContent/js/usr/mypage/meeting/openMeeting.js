/**
*******************************************************************************
***    명칭: openMeeting.js
***    설명: 마이페이지 - 문의내역 - 투자자미팅요청내역 화면
***
***    -----------------------------    Modified Log   ------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.06.14    LSH        First Coding.
***	   1.1		2023.06.21	  KYW		 리스트 표출
*******************************************************************************
**/
$(function() {
	//========================================================//
	// 화면 스크립트 내에 사용할 객체,상수 정의
	//--------------------------------------------------------//
	let P_GRID    = $('#appGrid'     ); // 목록 GRID
	let P_URL = getUrl('/usr/mypage/meeting/openMeeting.do');
	let P_FORM    = $('#searchForm'  ); // 검색폼	
	let P_BBS_CD  = $('#bbsSeCd'  ).val();// 게시판 구분코드
	
	//========================================================//
    // 목록 GRID 정의 (EasyUI datagrid)
    //--------------------------------------------------------//
    P_GRID.appBbsGrid({
		// 목록 KEY필드
		idField:    'pstNo',
		// 목록 제목
		title:      '투자자 미팅 요청 내역',
		// 검색 URL
		url:         getUrl('/usr/mypage/meeting/getListMeeting.do'),
		//목록 검색 페이징 객체
		pagination: { display: 10},
		// 페이지 표시 객체
		paging:     '#appGridPagination',
		// 리스트옵션
		listOptions: {
			// 목록 없을경우 메세지
			emptyText:  '{title}이 없습니다.',
			// 목록칼럼 너비
			colgroup: ['160px','*','160px'],
			// 목록칼럼 정의
			columns: [
	            {name:'aplcntNm'    ,cls:'app-c',label:'투자자'},
	            {name:'aplyCn'       ,cls:'app-c',label:'내용'},
	            {name:'regDate'      ,cls:'app-c',label:'작성일'}
			],
			// 목록형인 경우 내용 스타일시트
			bodyCls: "bs-1 t-t-c ts-m myGrid",
            //행단위 행확장이벤트
			extendRow    : function(row, rowidx) {
				let str = replaceStr(row['aplyCn']);
				let tr = $('<tr class="grid-answer-row"></tr>');
				tr.addClass('px-v-xl py-v-xl t-t-c');
				tr.append('<td class="app-l" colspan="3"><div class="answer"></div></td>');
				tr.find('.answer').append('<div class="top" style="padding-left: 205px;"></div>');
				tr.find('.answer > .top').append('<pre>'+str+'</pre>');
		    	
		    	tr.hide();
				return tr;
			},
            //행확장 토글이벤트 사용여부
            extendToggle: true,
	        // 행선택시 상세조회
	        select: doSelect
		}
    }).appBbsGrid('init');
	
    //========================================================//
    // 분류 탭 생성
    //--------------------------------------------------------//
	$('#appMenuTab').appBbsTabs({
		items: [{
			code: P_URL, 
			text: '투자자 미팅 요청 내역'
		},{
			code: getUrl('/usr/mypage/bbs/openBbs.do'),
			text: '문의 내역'
		}],
		value:  P_URL,
		select: function(v) {
			if (v != P_URL)
				goUrl(v);
			doSearch();
		}
	});	
	
	// 문의내역 검색처리
    //--------------------------------------------------------//
    function doSearch() {
        // 검색폼 데이터 객체화
        var obj = P_FORM.serializeObject();
        // 검색폼 그리드 검색
		P_GRID.appBbsGrid('search',obj);
        return false;
    }

    // 게시판 상세조회
    //--------------------------------------------------------//
    function doSelect(row, rowidx) {
    	return false;    	
    }
    
    // 최신순/오래된순 버튼 이벤트처리
    //--------------------------------------------------------//
    $('#appSortTab').appSortBox({
		// 선택 이벤트 
		select: function() {
			doSearch();
		},
		// 정렬 숨김박스
		inputs: {
			field: {id:'ordrField',name:'ordrField',value:''},
			order: {id:'ordrType' ,name:'ordrType' ,value:''},
		},
		// 정렬 항목
		items: [
			{cls: 'col active', field: 'reg_ymd', order: 'DESC', label: '최신순'},
			{cls: 'col', 		field: 'reg_ymd', order:  'ASC', label: '오래된순'}
		]
	});
	    
	//내용 태그 값 replace 함수
	//=============================================================================
    function replaceStr(str) {
    	str = str.replaceAll("&lt;", "<");
    	str = str.replaceAll("&gt;", ">");
    	str = str.replaceAll(/(?:\r\n|\r|\n)/g, '<br />');
    	return str;
    };
    
});
