/**
*******************************************************************************
***    명칭: openQNA.jsp
***    설명: 운영관리-질의응답 화면
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
	let P_GRID    = $('#appGrid'        ); // 목록 GRID
	let P_FORM    = $('#searchForm'     ); // 검색폼	
	let bbsSeCd   = $('#bbsSeCd'        ).val();
	
	if(bbsSeCd === CODE.BBS_SE.FAQ01) {
		//========================================================//
	    // 목록 GRID 정의 (appBbsGrid : app_bbsgrid.js)
	    //--------------------------------------------------------//
	    P_GRID.appBbsGrid({
			// 목록 KEY필드
			idField:    'pstNo',
			// 목록 제목
			title:      '자주묻는 질문',
			// 검색 URL
			url:         getUrl('/adm/inform/bbs/getListBbs.do'),
			// bbsSeCd코드 전달
			queryParams: {
				bbsSeCd: bbsSeCd
		    },
			//목록 검색 페이징 객체
			pagination: { display: 15},
			// 페이지 표시 객체
			paging:     '#appGridPagination',
			// 검색결과 표시 객체
			result:     '#appGridResult',
			// 리스트옵션
			listOptions: {
				// 검색결과 없을경우 메세지
				emptyText:  '검색된 {title}가 없습니다.',
				// 목록칼럼 너비
				colgroup: ['80px','130px','550px','100px','155px'],
				// 목록칼럼 정의
				columns: [
		            {name:'pstNo'        ,cls:'app-c',label:'번호'	,rownumbers: true},
		            {name:'pstClsfNm'    ,cls:'app-c',label:'분류'	},
		            {name:'pstTtl'       ,cls:'app-l',label:'제목'	},
		            {name:'rgtrNm'       ,cls:'app-c',label:'작성자'	,formatter: function(v, o) {
														                return o.mdfrNm || v;
														            }},
		            {name:'regDttm'      ,cls:'app-c',label:'등록일자'},
				],
		        // 행선택시 상세조회
		        select: doSelect
			}
	    }).appBbsGrid('init');
			
		// 로딩시 초기화 및 검색실행
		doReset();

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
	    // 게시판 등록하기
	    //--------------------------------------------------------//
	    function doRegist() {
			// 신규등록 모달팝업 오픈
			P_MODAL.doOpenRegist({bbsSeCd: bbsSeCd});
	        return false;
	    }
	}else{
		//========================================================//
		// 화면 스크립트 내에 사용할 객체,상수 정의
		//--------------------------------------------------------//
	    //========================================================//
	    // 목록 GRID 정의 (appBbsGrid : app_bbsgrid.js)
	    //--------------------------------------------------------//
	    P_GRID.appBbsGrid({
			// 목록 KEY필드
			idField:    'pstNo',
			// 목록 제목
			title:      '질의응답',
			// 검색 URL
			url:         getUrl('/adm/inform/bbs/getListBbs.do'),
			// bbsSeCd코드 전달
			queryParams: {
				bbsSeCd: bbsSeCd
		    },
			//목록 검색 페이징 객체
			pagination: { display: 15},
			// 페이지 표시 객체
			paging:     '#appGridPagination',
			// 검색결과 표시 객체
			result:     '#appGridResult',
			// 리스트옵션
			listOptions: {
				// 검색결과 없을경우 메세지
				emptyText:  '검색된 {title}가 없습니다.',
				// 목록칼럼 너비
				colgroup: ['80px','130px','450px','100px','155px','100px'],
				// 목록칼럼 정의
				columns: [
		            {name:'pstNo'          ,cls:'app-c',label:'번호'	  ,	rownumbers: true},
		            {name:'inqryTypeNm'    ,cls:'app-c',label:'관련분야'},
		            {name:'pstTtl'         ,cls:'app-l',label:'제목'	  },
		            {name:'rgtrNm'         ,cls:'app-c',label:'작성자'  },
		            {name:'regDttm'        ,cls:'app-c',label:'등록일자' },
		            {name:'prcsSttsCd'     ,cls:'app-c',label:'상태'	  ,formatter: function(value) {   // 셀에 대한 formatter 함수 정의
															                var color = value === '답변완료' ? 'blue' : 'red';  // 상태값에 따라 색상을 결정
															                return '<span style="color:' + color + '">' + value + '</span>';  // HTML 문자열 반환
															            }}
				],
		        // 행선택시 상세조회
		        select: doSelect
			}
	    }).appBbsGrid('init');
			
		// 로딩시 초기화 및 검색실행
		doReset();
	    
	    // 게시판 상세조회
	    //--------------------------------------------------------//
		function doSelect(row) {
			if(row['prcsSttsCd']=='답변완료'){
				// 상세조회 모달팝업 오픈
				P_MODAL.doOpenSelect( row );
			}else{
				// 신규등록 모달팝업 오픈
				P_MODAL.doOpenRegist( row );
			}
	        return false;
	    }
	}
	
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
	
	// 1:1문의 게시판 조회
    //--------------------------------------------------------//
    function doQNA() {
        let f = document.createElement('form');
        f.setAttribute('method', 'post');
        f.setAttribute('action', getUrl('/adm/inform/bbs/openQNA.do'));
        
        let obj;
        obj = document.createElement('input'		);
        obj.setAttribute('type' , 'hidden'  		);
        obj.setAttribute('name' , 'bbsSeCd' 		);   // input field의 name을 설정합니다.
        obj.setAttribute('id'   , 'bbsSeCd' 		);   // input field의 id을 설정합니다.
        obj.setAttribute('value', CODE.BBS_SE.QNA01 );   // input field의 value를 설정합니다.

        f.appendChild(obj);
        document.body.appendChild(f);
        f.submit();
    	
        return false;
    }
    
    // 자주묻는 질문 게시판 조회
    //--------------------------------------------------------//
    function doFAQ() {
        let f = document.createElement('form');
        f.setAttribute('method', 'post');
        f.setAttribute('action', getUrl('/adm/inform/bbs/openQNA.do'));
        
        let obj;
        obj = document.createElement('input'		);
        obj.setAttribute('type' , 'hidden'  		);
        obj.setAttribute('name' , 'bbsSeCd' 		);   // input field의 name을 설정합니다.
        obj.setAttribute('id'   , 'bbsSeCd' 		);   // input field의 id을 설정합니다.
        obj.setAttribute('value', CODE.BBS_SE.FAQ01 );   // input field의 value를 설정합니다.
        
        f.appendChild(obj);
        document.body.appendChild(f);
        f.submit();
    	
        return false;
    }
	// 콤보박스 정의
	//--------------------------------------------------------//
	// 검색용 권한
	$('#srchType').appComboBox({
		rows: [
			{code: 'all'	, text: '전체'},
			{code: 'title'	, text: '제목'},
			{code: 'content', text: '내용'},],
			 type: 'static'  // 이 옵션은 'rows'
	});
	
	// 검색버튼 클릭시 이벤트 처리
	$('#btnSearch'	).bind('click', doSearch);
	// 리셋버튼 클릭시 이벤트처리
	$('#btnReset' 	).bind('click', doReset);
    // 등록버튼 클릭시 이벤트처리
    $('#btnRegist'	).bind('click', doRegist);
	// 1:1문의 버튼 클릭시 이벤트처리
	$('#btnQNA'		).bind('click', doQNA);
	// 자주묻는 질문 클릭시 이벤트처리
	$('#btnFAQ'		).bind('click', doFAQ);
	
	// 검색어 입력 엔터 이벤트 처리
	bindEnter($('#srchText'), $('#btnSearch'));
});

//========================================================//
//등록,수정,상세 모달팝업 정의
//- 오픈된 모달창에서 사용하기 위해 외부에 정의함
//--------------------------------------------------------//
let P_MODAL = {
	// 모달팝업 객체
	modal: $('<div></div>'),
	// 상세조회 모달팝업 오픈
	doOpenSelect: function( row ) {
	    let url, title, params;

	    if (row['bbsSeCd'] === "B21") {
	        title = '자주묻는 질문 상세조회';
	        url = getUrl('/adm/inform/bbs/modalFAQView.do');
	        params = JSON.stringify({
	            pstNo      : row['pstNo'  ],
	            bbsSeCd    : row['bbsSeCd'],
	            movePage   : row['movePage']
	        });
	    } else {
	        title = '1:1문의 상세조회';
	        url = getUrl('/adm/inform/bbs/modalQNAView.do');
	        params = JSON.stringify({
	            pstNo      : row['pstNo'  ],
	            bbsSeCd    : row['bbsSeCd'],
	            movePage   : row['movePage']
	        });
	    }
	    
	    P_MODAL.modal.appModal({
	        cls:        'w-ver1',
	        title:      title,
	        url:        url,
	        params:     params
	    }).open();
	},
	
	// 신규등록 모달팝업 오픈
	doOpenRegist: function( row ) {
	    let url, title, params;
	    if (row['bbsSeCd'] === CODE.BBS_SE.FAQ01) {
	        title = '자주묻는 질문 등록하기';
	        url = getUrl('/adm/inform/bbs/modalFAQForm.do');
	        params = JSON.stringify({
				mode       : MODE.INSERT,
				bbsSeCd    : row['bbsSeCd']
	        });
	    } else {
	        title = '1:1문의 등록하기';
	        url = getUrl('/adm/inform/bbs/modalQNAForm.do');
	        params = JSON.stringify({
				mode       : MODE.INSERT,
				pstNo      : row['pstNo'  ],
				bbsSeCd    : row['bbsSeCd'],
				movePage   : row['movePage']
	        });
	    }
	    
		P_MODAL.modal.appModal({
			cls:        'w-ver1',
	        title:      title,
	        url:        url,
	        params:     params
		}).open();
	},
	
	// 수정하기 모달팝업 오픈
	doOpenUpdate: function( row) {
	    let url, title, params;
	    if (row['bbsSeCd'] === CODE.BBS_SE.FAQ01) {
	        title = '자주묻는 질문 등록하기';
	        url = getUrl('/adm/inform/bbs/modalFAQForm.do');
	        params = JSON.stringify({
				mode       : MODE.UPDATE,
				pstNo      : row['pstNo'  ],
				bbsSeCd    : row['bbsSeCd']
	        });
	    } else {
	        title = '1:1문의 등록하기';
	        url = getUrl('/adm/inform/bbs/modalQNAForm.do');
	        params = JSON.stringify({
				mode       : MODE.UPDATE,
				pstNo      : row['pstNo'  ],
				bbsSeCd    : row['bbsSeCd'],
				movePage   : row['movePage']
	        });
	    }
		
		P_MODAL.modal.appModal({
			cls:        'w-ver1',
	        title:      title,
	        url:        url,
	        params:     params
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
