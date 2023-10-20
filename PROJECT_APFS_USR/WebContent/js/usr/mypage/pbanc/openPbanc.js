/**
*******************************************************************************
***    명칭: openPbanc.js
***    설명: 마이페이지 - 사업공고등록 화면
***
***    -----------------------------    Modified Log   ------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.06.14    LSH        First Coding.
*******************************************************************************
**/

$(function() {
	let P_SRCHTXT 		= $('#srchText' ).val();// 검색텍스트 (이전에서 넘어온항목)
	let P_GRID   		= $('#appGrid'     ); // 목록 GRID
	let P_FORM   		= $('#searchForm'  ); // 검색폼	
	let P_GS_ROLE_ID 	= $('#gsRoleId' ).val();// 유저권한 
	let P_SN 			= $('#sn' ).val();// 유저권한 
	
	let P_SEARCH = {
			page          : false,
			showMode      : 'SINGLE',
			ordrField     : false,
			srchText      : '',
			divisionBkmk  : $('#divisionBkmk' ).val(),
		};
	
	//========================================================//
	// 화면 title 제목및 등록버튼 생성 정의
	//--------------------------------------------------------//
	let firstDom =  $('<div>').addClass('tit-box-3 mb-16px');
	let rowDom 	 =	$('<div>').addClass('row');
	let colDom1  =  $('<div>').addClass('col');
	let colDom2  =  $('<div>').addClass('col flex-grow-0');
	
	let pDom     =  $('<p>').addClass('txt1');
	let imageDom =  $('<img>').attr('id','pbancImage');
	imageDom.attr('alt','icon');
	let buttonDom = $('<button>').addClass('btn-primary bs-m').attr('id', 'btnRegist');
	let iDom     =  $('<i>').addClass('icon-edit');
	
	//colDom1 내용
	pDom.append(imageDom).append('사업공고 내역');
	colDom1.append(pDom);
	
	//colDom2 내용
	buttonDom.append(iDom).append('공고 등록');
	colDom2.append(buttonDom);
	
	rowDom.append(colDom1).append(colDom2);
	
	firstDom.append(rowDom);
	
	$('#pbancTitle').append(firstDom);
	$('#pbancImage').attr('src', getUrl('/images/sub/Sketch_on_smartphone.svg'));
	
	//정부지원사업 카드형(CARD)/리스트형(SINGLE) 그리드
	$.gridUtils.appMyPbancGrid = function ( args ) {
		
		var options = $.extend(true, {

			form : $('#searchForm'),
			
			// 상세조회 필터 함수
			selectFilter: false,
			
			gridOptions: {
				// 목록 ID
				id:         '#appGrid',
				// 목록 유형
				mode:       'SINGLE',
				// 목록 KEY필드
				idField:    'bizPbancNo',
				// 목록 제목
				title:      '정부지원사업',
				// 검색 URL
				url:         getUrl('/usr/support/pbanc/getListPbanc.do'),
				// 페이지 표시 객체
				paging:     '#appGridPagination',
				// 검색결과 표시 객체
				result:     '#appGridResult',
				// 검색결과 건수표시 메세지
				resultText: '<em>{total}</em><span>개의 {title}이 있습니다.</span>',
				// 자동로딩
				autoload: true,
				// 모드변경 CLASS
				changeCls:  'shadow-box-1 p-24px',
				// SINGLE 옵션
				singleOptions: {
					// 한페이지 표시 갯수
					display: 10,
					// 카드포맷
					formatter: function(dom,o) {
						// 하단영역
						let info2 = $('<div class="info-2"></div>');
						info2.append($.domUtil.getRow({
									// 업체명
							items: [{formatHtml: '<span class=""><i class="icon-building"></i>'+o['crdnsBzentyNm']+'</span>'} 
									// 등록일자
							       ,{formatHtml: '<span class=""><i class="icon-calendar-check-alt"></i><em>등록일자</em>'+o['regDate']+'</span>'} 
									// 마감아이콘
							       ,{formatHtml: $.gridUtils.FORMAT.pbancDate(o['rcptSeCd'],o)} 
							]
						}));
						
						// 모집구분아이콘
						let info1 = $('<div class="info-1"></div>');
						info1.append($.gridUtils.FORMAT.pbancIcon(o['rcptSeCd'],o))
						
						// 공고명
						let title = $('<p class="tit"></p>');
						title.data('no', o['bizPbancNo']);
						title.append(o['bizPbancNm']);
						title.addClass('app-pointer')
						// 상세보기
						title.bind('click', _doSelect);
						
						let dataCol = $('<div class="col"></div>');
						dataCol.append(info1);
						dataCol.append(title);
						dataCol.append(info2);
						
						let iconCol = $('<div class="col iconbox"></div>');
						// 북마크아이콘
						iconCol.append($.gridUtils.FORMAT.pbancBookmark(o['gsBkmkYn'], o));

						let row = $('<div class="row"></div>');
						row.append(dataCol);
						row.append(iconCol);
						dom.append(row);
					},
					emptyText: [
						'<p class="fs-24px fw-600 lh-1 mb-2">검색결과가 없습니다.</p>',
						'<span class="fs-15px fw-300">조건 검색에서 조건을 수정하여 다시 검색해보세요.</span>'
					].join('')
				},
			},
			doClear: function() {
				_doClear();
			},
			doReset: function() {
				_doReset();
			},
			doInit: function() {
				_doInit();
			},
		    doSearch: function( page ) {
				_doSearch( page );
		    }
		}, args);
		
		function _doSelect() {

	        // 검색폼 데이터 객체화
	        var obj = options.form.serializeObject();
			let pg  = $(options.gridOptions.id).appGrid('getPagination');
			$.extend(obj, {page: pg['page']});
			$.extend(obj, {bizPbancNo: $(this).data('no')});
			
			if (options.selectFilter)
				obj = options.selectFilter(obj);
			
			if (!obj)
				return false;

			// 상세보기
			$.formUtil.submitForm(getUrl('/usr/mypage/pbanc/viewPbanc.do'), {
				params: obj
			});
		};
		
		function _doSearch( page ) {
	        // 검색폼 데이터 객체화
	        var obj = options.form.serializeObject();
	        // 검색폼 그리드 검색
			$(options.gridOptions.id).appGrid('search', obj, page);
		};	
		
		function _doReset() {
			$(options.gridOptions.id).appGrid('reset');
			options.form[0].reset();
		};	
		
		function _doClear() {
			$(options.gridOptions.id).appGrid('clear');
		};	
		
		function _doInit() {
			$(options.gridOptions.id)
					 .appGrid(options.gridOptions)
					 .appGrid('init');
			// 초기검색실행
			_doSearch();
		};
		
		_doInit();
			
		return options;
	};
	
	// 그리드 목록유형
	let P_SHOW_MODE = P_SEARCH['showMode'];
	// 그리드 옵션
	let P_GRID_OPTIONS = {
		id:     '#appGrid',
		paging: '#appGridPagination',
		result: '#appGridResult',
		// 자동로딩
		autoload: true,
		// 검색 URL
		url: getUrl('/usr/mypage/pbanc/getListPbanc.do'),
		// 검색 조건
		params: {},
	};
	
	// 정부지원사업 카드형 그리드 객체
	P_GRID = $.gridUtils.appMyPbancGrid({
		form       : $('#searchForm'),
		gridOptions: P_GRID_OPTIONS,
		// 상세조회용 필터함수
		selectFilter: function( params ) {
			$.extend(params, {showMode: P_SHOW_MODE});
			return params;
		}
	});
	//========================================================//
	// 분류 탭 생성
	//--------------------------------------------------------//
	$('#appPbancBookmarkTab').appBbsTabs({
		url:    getUrl('/usr/mypage/pbanc/getListPbancTab.do'),
		params: {divisionBkmk : CODE.BKMK_SE.code},
		value:  $('#divisionBkmk' ).val() ? $('#divisionBkmk' ).val() : '00',
		select: function(v) {
			$("#divisionBkmk").val(v);
			doSearch();
		}
	});
	
	// 그리드 검색처리
	function doSearch( page ) {
		if (!P_GRID)
			return false;
		if (page && parseInt(page) > 1)
			P_GRID.doSearch( parseInt(page) );
		else
			P_GRID.doSearch();
	}
	
	// 그리드 리셋처리
	function doReset() {
		if (!P_GRID)
			return false;
		P_GRID.doReset();
		P_GRID.doSearch();
	}

	doSearch($("#page").val());
	
	$('.sub-banner.MU_USR_MYP .col-12.col-xl').eq(1).attr('id', 'appHeaderBox');
	
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
	
    // 등록 클릭시 이벤트처리
    //--------------------------------------------------------//
    $('#btnRegist').bind('click', function() {
    	goUrl(getUrl('/usr/mypage/pbanc/formPbanc.do'));
	});
	
});

