/**
*******************************************************************************
***    명칭: openNotice.jsp
***    설명: 운영관리-공지사항 화면
***
***    -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.04.04    LSH        First Coding.
***    1.1      2023.05.30    LSH        appGrid -> appBbsGrid 로 컴포넌트 변경
***    1.2      2023.06.27    J H        작업완료
*******************************************************************************
**/
$(function() {
	//========================================================//
	// 화면 스크립트 내에 사용할 객체,상수 정의
	//--------------------------------------------------------//
	let P_GRID    = $('#appGrid'        ); // 목록 GRID
	let P_FORM    = $('#searchForm'     ); // 검색폼	
	let bbsSeCd   = $('#bbsSeCd'     	).val();
	
    //========================================================//
    // 목록 GRID 정의 (appBbsGrid : app_bbsgrid.js)
    //--------------------------------------------------------//
    P_GRID.appBbsGrid({
		// 목록 KEY필드
		idField:    'pstNo',
		// 목록 제목
		title:      '공지사항',
		// 검색 URL
		url:         getUrl('/adm/inform/bbs/getListBbs.do'),
		// bbsSeCd코드 전달
		queryParams: {
			bbsSeCd: bbsSeCd
	    },
		//목록 검색 페이징 객체
		pagination: { display: 20},
		// 페이지 표시 객체
		paging:     '#appGridPagination',
		// 검색결과 표시 객체
		result:     '#appGridResult',
		// 리스트옵션
		listOptions: {
			// 검색결과 없을경우 메세지
			emptyText:  '검색된 {title}가 없습니다.',
			// 목록칼럼 너비
			colgroup: ['80px','450px','155px','100px','100px','100px'],
			// 목록칼럼 정의
			columns: [
	            {name:'pstNo'        ,cls:'app-c',label:'번호', rownumbers: true, // 연번 처리
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
					}
				},
	            {name:'pstTtl'       ,cls:'app-l',label:'제목'	},
	            {name:'regDttm'      ,cls:'app-c',label:'등록일자' },
	            {name:'popupYn'      ,cls:'app-c',label:'팝업여부' },
	            {name:'inqCnt'       ,cls:'app-r',label:'조회수'	 , formatter: $.formatUtil.toNumber},
	            {name:'mdfDttm'      ,cls:'app-c',label:'첨부파일'  , formatter: function (v, o) {
													                    var icon = $('<i class="icon-paperclip"></i>');
													                    var url = getUrl('/'+SYSTEM.CODE.toLowerCase()+'/file/getListBbsFile.do'); // 실제 URL로 대체해야 합니다.
													                    var param = {pstNo: o['pstNo'] , fileSeCd : CODE.FILE_SE.FILE };
													                    var rawData = $.ajaxUtil.ajaxDefault(url, param);
													                    if(rawData.rows.length>0){
													                    	return icon;
													                    }
													                    return '';
												                	}
            	}
			],
	        // 행선택시 상세조회
	        select: doSelect
		}
    }).appBbsGrid('init');
		
	// 로딩시 초기화 및 검색실행
	doReset();

    // 게시판 검색처리
    //--------------------------------------------------------//
    function doSearch() {
        // 검색폼 데이터 객체화
        var obj = P_FORM.serializeObject();
        // 검색폼 그리드 검색
		P_GRID.appBbsGrid('search', obj);
        return false;
    }
	
    // 게시판 검색리셋
    //--------------------------------------------------------//
    function doReset() {
        // 검색폼 입력데이터 초기화
        P_FORM.form('reset');
        // 검색폼 그리드 검색 처리
        doSearch();
        return false;
    }

    // 게시판 등록하기
    //--------------------------------------------------------//
    function doRegist() {
		// 신규등록 모달팝업 오픈
		P_MODAL.doOpenRegist({bbsSeCd: bbsSeCd});
        return false;
    }

    // 게시판 상세조회
    //--------------------------------------------------------//
    function doSelect(row) {
		// 상세조회 모달팝업 오픈
		P_MODAL.doOpenSelect( row );
        return false;
    }
    
    // 콤보박스 정의
    //--------------------------------------------------------//
	// 검색용 권한
	$('#srchType').appComboBox({
	    rows: [
	        {code: 'all'	, text: '전체'},
	        {code: 'title'	, text: '제목'},
	        {code: 'content', text: '내용'},
	    ],
	    type: 'static'  // 이 옵션은 'rows'
	});
	
    // 검색버튼 클릭시 이벤트 처리
    $('#btnSearch').bind('click', doSearch);
    // 리셋버튼 클릭시 이벤트처리
    $('#btnReset' ).bind('click', doReset);
    // 등록버튼 클릭시 이벤트처리
    $('#btnRegist').bind('click', doRegist);

    // 검색어 입력 엔터 이벤트 처리
    bindEnter($('#srchText'), $('#btnSearch'));
});

//========================================================//
// 등록,수정,상세 모달팝업 정의
//  - 오픈된 모달창에서 사용하기 위해 외부에 정의함
//--------------------------------------------------------//
let P_MODAL = {
	// 모달팝업 객체
	modal: $('<div></div>'),
	// 상세조회 모달팝업 오픈
	doOpenSelect: function( row ) {
		P_MODAL.modal.appModal({
			cls:        'w-ver1',
			title:      '공지사항 상세조회',
			url:        getUrl('/adm/inform/bbs/modalNoticeView.do'),
			params:     JSON.stringify({
				pstNo      : row['pstNo'  ],
				bbsSeCd    : row['bbsSeCd'],
				movePage   : row['movePage']
			})
		}).open();
	},
	// 신규등록 모달팝업 오픈
	doOpenRegist: function( row ) {
		P_MODAL.modal.appModal({
			cls:        'w-ver1',
			title:      '공지사항 신규등록',
			url:        getUrl('/adm/inform/bbs/modalNoticeForm.do'),
			params:     JSON.stringify({
				mode       : MODE.INSERT,
				bbsSeCd    : row['bbsSeCd']
			})
		}).open();
	},
	// 수정하기 모달팝업 오픈
	doOpenUpdate: function( row) {
		P_MODAL.modal.appModal({
			cls:        'w-ver1',
			title:      '공지사항 수정하기',
			url:        getUrl('/adm/inform/bbs/modalNoticeForm.do'),
			params:     JSON.stringify({
				mode       : MODE.UPDATE,
				pstNo      : row['pstNo'  ],
				bbsSeCd    : row['bbsSeCd']
			})
		}).open();
	},
	// [모달팝업 호출용] 모달팝업 닫기
	doClose: function() {
		if (P_MODAL.modal && 
			P_MODAL.modal.close)
			P_MODAL.modal.close();
	},
	// [모달팝업 호출용] 목록 재검색 및 팝업 닫기
	doSearch: function() {
		// 모달팝업 닫기
		P_MODAL.modal.close();
	    // 검색폼 데이터 객체화
	    var obj = $('#searchForm').serializeObject();
	    // 검색폼 그리드 검색
		$('#appGrid').appBbsGrid('search', obj);
	}
};
