/**
*******************************************************************************
***    명칭: openOpnnIr.js
***    설명: 마이페이지 - IR검토의견등록 화면
***
***    -----------------------------    Modified Log   ------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.06.14    LSH        First Coding.
***    1.1		2023.06.22	  KYW		 CRUD
*******************************************************************************
**/
$(function() {
	//========================================================//
	// 화면 스크립트 내에 사용할 객체,상수 정의
	//--------------------------------------------------------//
	let P_GRID    = $('#appGrid'     ); // 목록 GRID
	let P_FORM    = $('#searchForm'  ); // 검색폼	
	//========================================================//
    // 목록 GRID 정의 (EasyUI datagrid)
    //--------------------------------------------------------//
    P_GRID.appBbsGrid({
		// 목록 KEY필드
		idField:    'evntNo',
		// 목록 제목
		title:      '투자설명회 목록',
		// 검색 URL
		url:         getUrl('/usr/mypage/opnn/getListEvent.do'),
		//목록 검색 페이징 객체
		pagination: { display	: 10 },
		// 페이지 표시 객체
		paging:     '#appGridPagination',
		// 리스트옵션
		listOptions: {
			// 목록 없을경우 메세지
			emptyText:  '{title}이 없습니다.',
			// 목록칼럼 너비
			colgroup: ['100px','*','160px','100px','160px'],
			// 목록칼럼 정의
			columns: [
	            {name:'evntNo'    	 ,cls:'app-c'	,label:'번호', rownumbers: true},
	            {name:'evntNm'       ,cls:'app-c'	,label:'설명회명'},
	            {name:'evntBgngYmd'  ,cls:'app-c'	,label:'설명회일자'},
	            {name:'isPartcpt'    ,cls:'app-c'	,label:'검토의견',
	            	formatter: function(v,o) {
	            		let btn = '-';
	            		let mode = null;
	            		if(v) { //행사 참여
							let irPrgrsSttsCd = o['irPrgrsSttsCd'];
							let irRgstYn = o['irRgstYn'];
							
	            			if(irPrgrsSttsCd == '10' && irRgstYn) { //검토의견 등록완료 && 본인 확인
								btn = $('<button class="btnRgst btn-black-ouline bs-s"></button>');
		            			btn.append('등록완료');
		            			mode = MODE.VIEW;
	            			} else { 
								if(o['userAuth'] == 'V') {
									btn = '-';
									return btn;
								} 
								btn = $('<button class="btnRgst btn-primary bs-s"></button>');
		            			btn.append('등록하기');

								if(irPrgrsSttsCd)
									mode = MODE.UPDATE;
								else
									mode = MODE.INSERT;
		            				
	            			}
	            			// 등록 버튼 클릭시 이벤트처리
	            			btn.bind('click', function(e){
	            				modal = $('<div></div>').appPopup({
	            					dialogCls:	'w-ver1',
	            					title:     	'IR 투자자 검토의견서',
	            					url:        getUrl('/usr/mypage/opnn/modalOpnnIr.do'),
	            					params:     JSON.stringify({
	            						mode       	: mode,
	            						evntNo 		: o['evntNo'],
										irRgstYn	: irRgstYn,
	            					}),
	            					type:       'pageload',
	            					appendBody: true,
									// 닫기후 콜백함수
									onhidden: doSearch,
	            				}).open();
	            				e.stopPropagation();
	            			});
	            		} else 
	            			btn = '-';
	            		
	            		return btn;
	            	}
	            },
	            {name:'regDate'      ,cls:'app-c'	,label:'등록일'}
			],
			select: function(row) {
				doSelect({
					evntNo: row['evntNo'],
					srchYr: row['evntYr'],
					evntNm: row['evntNm']
				});
			}
		}
    }).appBbsGrid('init');
    
    // 이벤트 조회
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
   
    // 상세페이지 이동  
    //--------------------------------------------------------//
    function doSelect(obj) {
    	let pg = P_GRID.appBbsGrid('getPagination');
        $.formUtil.submitForm( getUrl('/usr/mypage/opnn/openOpnnIrEnt.do'), {
			params: {
				page      : pg['page'    ],
				evntNo	  : obj.evntNo,
				srchYr	  : obj.srchYr,
				evntNm	  : obj.evntNm
			}
		});
        return false;
    }
    
    //[팝업] 작성가이드 팝업
    //--------------------------------------------------------//
    $('#btnGuide').bind('click', function() {
    	let options = {
    			title     		: '투자자 검토의견서 작성가이드',
    			icon      		: '<img src="'+getUrl('/images/sub/Tribute.svg')+'">',
    			loadUrl   		: getUrl('/usr/mypage/opnn/modalGuide.do'),
    			loadParams		: {},
    	};
		return $('<div></div>').appPopup({
			url:        	options.loadUrl,
    		params:     	JSON.stringify(options.loadParams),
    		title:      	options.title,
    		icon:       	options.icon,
    		type:      	 	'pageload',
    		dialogCls:  	'w-ver2',
    		appendBody: true,
		}).open();
    });
    
    // 년도 선택
    $('.dropdown .dropdown-srchYr').bind('click', function(){
		var this_t_v = $(this).text();
		$('#srchYr').val(this_t_v);
	});
    
    // 검색 버튼 클릭
    $('#btnSearch').bind('click', function(){
    	let year = $('#srchYr').val();
    	$('.sYear').text(year+'년 ');
    	doSearch();
    })
    
    doSearch();
});

let modal;

