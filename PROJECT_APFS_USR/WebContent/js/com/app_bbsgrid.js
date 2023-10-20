/**
*******************************************************************************
*** 파일명    : app_bbsgrid.js
*** 설명      : 게시판용 목록(그리드) 컨트롤
***
***             bbsUtils.appInvestCaseGrid : 모태펀드 그리드
***
*** -----------------------------    Modified Log   ---------------------------
*** ver             date                author                  description
*** ---------------------------------------------------------------------------
*** 1.0         2023.04.20              LSH
*** 1.1         2023.06.09               JH
*******************************************************************************
**/
//===========================================================================//

function decodeHtml(html) {
    var txt = document.createElement("textarea");
    txt.innerHTML = html;
    return txt.value;
}

function emptyText() {
    var div = $('<div class="pb-56px py-40px text-center"></div>');
    var p = $('<p class="fs-24px fw-600 lh-1 mb-2">검색결과가 없습니다.</p>');
    var span = $('<span class="fs-15px fw-300">조건 검색에서 조건을 수정하여 다시 검색해보세요.</span>');
	div.append('<div class="app-imgarea"><img src="'+getUrl('/images/sub/searchicon.png')+'" alt="icon"/></div>');
    div.append(p);
    div.append(span);
    return div.prop('outerHTML');
}

function truncateString(str, num) {
    // HTML 태그와 이미지 제거
    var plainText = str.replace(/<[^>]+>/g, '').replace(/<img[^>]+>/g, '');
    // 공백 제거
    var trimmedText = plainText.replace(/\s+/g, ' ').trim();
    // 길이 제한
    var truncated = '';
    var count = 0;
    for (var i = 0; i < trimmedText.length; i++) {
        var char = trimmedText[i];
        // 한글인 경우 길이 1.5로 계산
        if (/[\u3131-\uD79D]/.test(char)) {
            count += 1.5;
        } else {
            count += 1;
        }
        if (count > num) {
            break;
        }
        truncated += char;
    }
    return truncated + (count > num ? '...' : '');
}

function notTagString(str) {
    // HTML 태그와 이미지 제거
    var plainText = str.replace(/<[^>]+>/g, '').replace(/<img[^>]+>/g, '');
    // 공백 제거
    var trimmedText = plainText.replace(/\s+/g, ' ').trim();
    return trimmedText;
}

$.bbsUtils = {};

//상단제목 검색박스 또는 버튼 표시
$.bbsUtils.showHeaderBox = function( args ) {
	
	let elm = $('#appHeaderBox');
	elm.html('');

	// 검색박스형태인 경우
	if (args.type == 'searchbox') {
		let fnSearch = function() {
			let v = $('#'+args.id).val();
			args.callback(v);
			return false;
		};
		let inp = $('<input type="text" title="search"/>');
		inp.prop('id'  , args.id);
		inp.prop('name', args.name);
		if (args.placeholder)
			inp.prop('placeholder', args.placeholder);
		if (args.value)
			inp.prop('value', args.value);
		
		let btn = $('<a href="javascript:void(0)" class="icon-search"><span class="hiddenATag">숨김</span></a>');
		btn.click(fnSearch);
		
		bindEnter(inp, btn);
	
		let div = $('<div class="form-area-box1"></div>');
		div.append('<div class="ele-icon-box"></div>');
		div.find('.ele-icon-box').append(inp);
		div.find('.ele-icon-box').append(btn);
		
		elm.append(div);
	}
};

// 우수투자사례 슬라이드 기본정보
$.bbsUtils.getInvestCaseInfo = function(o, mode) {
	
	//우수투자사례
	let pTitle          = '<p class="txt1 investCaseText1">' + notTagString(o.pstTtl) + '</p>';
	// 필요한 HTML 요소 생성
	let decodedString = decodeHtml(o.pstCn);
	let pContent = $("<p></p>").addClass("txt2 ckEdit investCaseText2").html(notTagString(decodedString));
	// HTML 콘텐츠 설정
	let bottomRgtr      = '<div class="col"><p class="txt1"><i class="icon-user"></i>' + (o.rgtrNm == null ? '관리자' : o.rgtrNm) + '</p></div>';
	let bottomRegYmd    = '<div class="col"><p class="txt1"><i class="icon-alarm-check"></i>' + o.regDate + '</p></div>' 
	let bottomInqCnt    = '<div class="col"><p class="txt1"><i class="icon-eye"></i>' + o.inqCnt + '</p></div>';
	
	let box = $('<div class="txt-box w-100"></div>');
	box.append(pTitle);
	box.append(pContent);
	let bottomDiv = $('<div class="bottom"></div>');
	let rowDiv = $('<div class="row"></div>');

	rowDiv.append(bottomRgtr);
	rowDiv.append(bottomRegYmd);
	rowDiv.append(bottomInqCnt);

	bottomDiv.append(rowDiv);
	box.append(bottomDiv);

	box.append(bottomDiv);
	
	return box;
};

//우수투자사례 (CARD) / 그리드
$.bbsUtils.appInvestCaseGrid = function ( args) {
	var options = $.extend(true, {
		form : $('#searchForm'),
		// 상세조회 필터 함수
		selectFilter: false,
		gridOptions: {
			// 목록 ID
			id:         '#appGrid',
			// 목록 유형
			mode:       'CARD',
			// 목록 KEY필드
			idField:    'pstNo',
			// 목록 제목
			title:      '우수투자사례',
			// 검색 URL
			url:         getUrl('/usr/inform/bbs/getListInvestCase.do'),
			// 페이지 표시 객체
			paging:     '#appGridPagination',
			// 자동로딩
			autoload: true,
			// 모드변경 CLASS
			changeCls:  'shadow-box-1 p-24px',
			// 카드옵션
			cardOptions: {
				// 한페이지 표시 갯수
				display: 10,
				// 기본 스타일시트
				cardCls: "img-list-1",
				// 열 스타일시트
				lineCls: "col-12 col-xl-6",
				// 박스 스타일시트
				boxCls: "card-box-colspan",
				// 카드선택이벤트
				select: function(row) {
					_doSelect({pstNo: row['pstNo']});
				},
				// 카드포맷
				formatter: function(dom,o) {
					let box = $.bbsUtils.getInvestCaseInfo(o, MODE.LIST);
					dom.append(box);
					if (o['files']) {
					    // 대표이미지
					    dom.append('<img src="'+getUrl('/usr/file/linkBbsFile.do?sn='+o['files'])+'" class="img1" alt="우수투자사례">');
					}
					else {
					    // 임시이미지
					    dom.append('<img src="'+getUrl('/images/sub/img4.svg')+'" class="img1" alt="우수투자사례">');
					}
				},
				emptyText: [
					'<p class="fs-24px fw-600 lh-1 mb-2">검색결과가 없습니다.</p>',
					'<span class="fs-15px fw-300">조건 검색에서 조건을 수정하여 다시 검색해보세요.</span>'
				].join('')
				}
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
	    doSearch: function(page ) {
			_doSearch(page);
	    }
	}, args);
	
	function _doSelect(row) {
      // 검색폼 데이터 객체화
      	var obj = options.form.serializeObject();
		let pg  = $(options.gridOptions.id).appGrid('getPagination');
		
		$.extend(obj, {page: pg['page']});
		$.extend(obj, {pstNo: row.pstNo});
		
		if (options.selectFilter)
			obj = options.selectFilter(obj);
		if (!obj)
			return false;
		// 상세보기
		$.formUtil.submitForm(getUrl('/usr/inform/bbs/viewInvestCase.do'), {
			params: obj
		});
	};
	
	function _doSearch(page) {
        // 검색폼 데이터 객체화
        var obj = options.form.serializeObject();
        // 검색폼 그리드 검색
		$(options.gridOptions.id).appGrid('search', obj,page);
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

// 홍보영상 슬라이드 기본정보
$.bbsUtils.getPromotionInfo = function(o) {
	let videoUrl = o.pstLinkUrl;
	let videoId = videoUrl.split("v=")[1];  // URL에서 video-id 추출
	let thumbnailUrl = "https://img.youtube.com/vi/" + videoId + "/0.jpg";
	let decodedString = decodeHtml(o.pstTtl);
	
	let pstTtlBox        =    $("<p class='txt1'>"+truncateString(decodedString,200)+"</p>");
	let regDateBox       =    $('<div>', { class: 'col' }).html('<p class="txt3"><i class="icon-alarm-check"></i>' + o.regDate + '</p>');
	let inqCntBox        =    $('<div>', { class: 'col' }).html('<p class="txt3"><i class="icon-eye"></i>' + o.inqCnt + '</p>');

	let rowBox           =    $('<div>', { class: 'row' }).append(regDateBox, inqCntBox);
	let infoBox          =    $('<div>', { class: 'info' }).append(rowBox);

	let txtBox           =   $('<div class="txt-box"></div>').append(pstTtlBox, infoBox);

	let positionBox      =   $('<div class="position-relative"></div>');
	let pstLinkUrlBox  	 = 	 $('<img>', { src: thumbnailUrl, class: 'img1', alt: "video" }).add('<i class="icon-play-circle"></i>');
	
	positionBox.append(pstLinkUrlBox);

	let box              =   $('<div></div>').append(positionBox, txtBox);
	return box;
};

// 홍보영상 (CARD) / 그리드
$.bbsUtils.appPromotionGrid = function ( args ) {
	var options = $.extend(true, {
		form : $('#searchForm'),
		// 상세조회 필터 함수
		selectFilter: false,
		gridOptions: {
			// 목록 ID
			id:         '#appGrid',
			// 목록 유형
			mode:       'CARD',
			// 목록 KEY필드
			idField:    'pstNo',
			// 목록 제목
			title:      '홍보영상',
			// 검색 URL
			url:         getUrl('/usr/inform/bbs/getListPromotion.do'),
			// 페이지 표시 객체
			paging:     '#appGridPagination',
			// 자동로딩
			autoload: true,
			// 모드변경 CLASS
			changeCls:  'shadow-box-1 p-24px',
			// 카드옵션
			cardOptions: {
				// 한페이지 표시 갯수
				display: 12,
				// 기본 스타일시트
				cardCls: "img-list-1",
				// 열 스타일시트
				lineCls: "col-12 col-xl-3",
				// 박스 스타일시트
				boxCls: "card-box-main",
				// 카드선택이벤트
				select: function(row) {
					_doSelect({pstLinkUrl: row['pstLinkUrl']});
				},
				// 카드포맷
				formatter: function(dom,o) {
					let box = $.bbsUtils.getPromotionInfo(o, MODE.LIST);
					dom.append(box);
				},
				emptyText: [
					'<p class="fs-24px fw-600 lh-1 mb-2">검색결과가 없습니다.</p>',
					'<span class="fs-15px fw-300">조건 검색에서 조건을 수정하여 다시 검색해보세요.</span>'
				].join('')
				}
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
	
	
	function _doSelect(row) {
		window.open(row.pstLinkUrl, '_blank');
	};
	
	function _doSearch( params ) {
        // 검색폼 데이터 객체화
        var obj = options.form.serializeObject();
        if (params)
        	$.extend(obj,params);
        // 검색폼 그리드 검색
		$(options.gridOptions.id).appGrid('search', obj);
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

// 통합검색 슬라이드 기본정보
$.bbsUtils.getSearchInfo = function(o, mode, form) {
	const srchText = $.formUtil.getValue(form, 'srchText');
	// 통합검색
	let titleText = o.pstTtl;
	if (!$.commUtil.isEmpty(srchText)) {
		titleText = o.pstTtl.replaceAll(srchText, "<mark>"+srchText+"</mark>");
	}
	let pTitle          = '<p class="txt1">' + titleText + '</p>';
	
	// 필요한 HTML 요소 생성
	let decodedString = truncateString(decodeHtml(o.pstCn), 400);
	
	// mark 처리
	if (!$.commUtil.isEmpty(srchText)) {
		decodedString = decodedString.replaceAll(srchText, "<mark>"+srchText+"</mark>");
	}
	
	let pContent = $("<p></p>").addClass("txt2").html(decodedString);
	pContent.css("overflow", "hidden");
	
	// HTML 콘텐츠 설정
	let bottomRgtr      = '<div class="col"><p class="txt1"><i class="icon-user"></i>' + o.rgtrNm + '</p></div>';
	let bottomRegYmd    = '<div class="col"><p class="txt1"><i class="icon-alarm-check"></i>' + o.regDate + '</p></div>' 
	let bottomInqCnt    = '<div class="col"><p class="txt1"><i class="icon-eye"></i>' + o.inqCnt + '</p></div>';
	
	let box = $('<div class="txt-box w-100"></div>');
	box.append(pTitle);
	box.append(pContent);
	let bottomDiv = $('<div class="bottom"></div>');
	let rowDiv = $('<div class="row"></div>');

	rowDiv.append(bottomRgtr);
	rowDiv.append(bottomRegYmd);
	rowDiv.append(bottomInqCnt);

	bottomDiv.append(rowDiv);
	box.append(bottomDiv);

	box.append(bottomDiv);
	
	return box;
};

// 통합검색 (CARD) / 그리드
$.bbsUtils.appSearchGrid = function ( args ) {
	var options = $.extend(true, {
		form : $('#searchForm'),
		// 상세조회 필터 함수
		selectFilter: false,
		gridOptions: {
			// 목록 ID
			id:         '#appSearch',
			// 목록 유형
			mode:       'CARD',
			// 목록 KEY필드
			idField:    'pstNo',
			// 목록 제목
			title:      '홍보영상',
			// 검색 URL
			url:         getUrl('/usr/inform/bbs/getListNotice.do'),
			// 페이지 표시 객체
			paging:     '#appGridPagination',
			// 자동로딩
			autoload: true,
			// 모드변경 CLASS
			changeCls:  'shadow-box-1 p-24px',
			// 카드옵션
			cardOptions: {
				// 한페이지 표시 갯수
				display: 12,
				// 기본 스타일시트
				cardCls: "img-list-1",
				// 열 스타일시트
				lineCls: "col-12 col-xl-3",
				// 박스 스타일시트
				boxCls: "card-box-colspan",
				// 카드선택이벤트
				select: function(row) {
					console.log(row);
					_doSelect(row);
				},
				// 카드포맷
				formatter: function(dom,o) {
					let box = $.bbsUtils.getSearchInfo(o, MODE.LIST, args.form);
					dom.append(box);
				},
				emptyText: [
					'<p class="fs-24px fw-600 lh-1 mb-2">검색결과가 없습니다.</p>',
					'<span class="fs-15px fw-300">조건 검색에서 조건을 수정하여 다시 검색해보세요.</span>'
				].join('')
			}
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
	
	
	function _doSelect(row) {
		//window.open(row.pstLinkUrl, '_blank');
		// 검색폼 데이터 객체화
      	var obj = options.form.serializeObject();
		let pg  = $(options.gridOptions.id).appGrid('getPagination');
		
		$.extend(obj, {page: pg['page']});
		$.extend(obj, {pstNo: row.pstNo});
		
		if (options.selectFilter)
			obj = options.selectFilter(obj);
		if (!obj)
			return false;
			
		var url;
		var bbsSeCd = row['bbsSeCd'];
		
		if (bbsSeCd == 'B10') {
			// 공지사항
			url = '/usr/inform/bbs/viewNotice.do';
		} else if (bbsSeCd == 'B30') {
			// 자료실
			url = '/usr/inform/bbs/viewData.do';
		} else if (bbsSeCd == 'B40') {
			// 우수투자사례
			url = '/usr/inform/bbs/viewInvestCase.do';
		}
		
		// 상세보기
		$.formUtil.submitForm(getUrl(url), {
			params: obj
		});
	};
	
	function _doSearch( params ) {
        // 검색폼 데이터 객체화
        var obj = options.form.serializeObject();
        if (params)
        	$.extend(obj,params);
        // 검색폼 그리드 검색
		$(options.gridOptions.id).appGrid('search', obj);
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
	// 데이터 검색
	function search( target, params, page ) {
		load(target, $.extend(params,{page: (page || 1)}), true);
	};
	
	_doInit();
		
	return options;
};

// 자료실 슬라이드 기본정보
$.bbsUtils.getDataInfo = function(o, mode) {
	
	// 자료실
	let pTitle          = '<p class="txt1">' + o.pstTtl + '</p>';
	
	// 필요한 HTML 요소 생성
	let decodedString = decodeHtml(o.pstCn);
	//decodedString = decodedString.replace(/<\/p>/gi, "<br>").replace(/<p>/gi, "");
	decodedString = decodedString.replace(/<\/p>/gi, " ").replace(/<p>/gi, "");
	decodedString = decodedString.replace(/<\/br>/gi, " ").replace(/<br>/gi, "");
	decodedString = decodedString.replace(/[<]br [/][>]/gi, " ");  

	
	let pContent = $("<p></p>").addClass("txt2").html(decodedString);
	pContent.css("overflow", "hidden");
	
	// HTML 콘텐츠 설정
	let bottomRgtr      = '<div class="col"><p class="txt1"><i class="icon-user"></i>' + o.rgtrNm + '</p></div>';
	let bottomRegYmd    = '<div class="col"><p class="txt1"><i class="icon-alarm-check"></i>' + o.regDate + '</p></div>' 
	let bottomInqCnt    = '<div class="col"><p class="txt1"><i class="icon-eye"></i>' + o.inqCnt + '</p></div>';
	
	let box = $('<div class="txt-box w-100"></div>');
	box.append(pTitle);
	box.append(pContent);
	let bottomDiv = $('<div class="bottom"></div>');
	let rowDiv = $('<div class="row"></div>');

	rowDiv.append(bottomRgtr);
	rowDiv.append(bottomRegYmd);
	rowDiv.append(bottomInqCnt);

	bottomDiv.append(rowDiv);
	box.append(bottomDiv);

	box.append(bottomDiv);
	
	return box;
};

// 자료실 (CARD) / 그리드
$.bbsUtils.appDataGrid = function ( args ) {
	var options = $.extend(true, {
		form : $('#searchForm'),
		// 상세조회 필터 함수
		selectFilter: false,
		gridOptions: {
			// 목록 ID
			id:         '#appDataGrid',
			// 목록 유형
			mode:       'CARD',
			// 목록 KEY필드
			idField:    'pstNo',
			// 목록 제목
			title:      '자료실',
			// 검색 URL
			url:         getUrl('/usr/inform/bbs/getListData.do'),
			// 페이지 표시 객체
			paging:     '#appGridPagination',
			// 자동로딩
			autoload: true,
			// 모드변경 CLASS
			changeCls:  'shadow-box-1 p-24px',
			// 카드옵션
			cardOptions: {
				// 한페이지 표시 갯수
				display: 12,
				// 기본 스타일시트
				cardCls: "img-list-1",
				// 열 스타일시트
				lineCls: "col-12 col-xl-3",
				// 박스 스타일시트
				boxCls: "card-box-colspan",
				// 카드선택이벤트
				select: function(row) {
					_doSelect({pstLinkUrl: row['pstLinkUrl']});
				},
				// 카드포맷
				formatter: function(dom,o) {
					let box = $.bbsUtils.getDataInfo(o, MODE.LIST);
					dom.append(box);
				},
				emptyText: [
					'<p class="fs-24px fw-600 lh-1 mb-2">검색결과가 없습니다.</p>',
					'<span class="fs-15px fw-300">조건 검색에서 조건을 수정하여 다시 검색해보세요.</span>'
				].join('')
			}
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
	
	
	function _doSelect(row) {
		window.open(row.pstLinkUrl, '_blank');
	};
	
	function _doSearch( params ) {
        // 검색폼 데이터 객체화
        var obj = options.form.serializeObject();
        if (params)
        	$.extend(obj,params);
        // 검색폼 그리드 검색
		$(options.gridOptions.id).appGrid('search', obj);
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

(function($){
	const WIDGET = 'appBbsGrid';
	
	//----------------------------------------------------------------------//
	// 객체 단위 실행 함수
	// 일반 게시판 Gird
	//----------------------------------------------------------------------//
	// 객체 생성
	function _createObject(target) {
		var opts = $.data(target, WIDGET).options;
		
		let table = $('<table class="app-grid-table"></table>');
		if (opts.listOptions.tableCls)
			table.addClass(opts.listOptions.tableCls);
		// 2023.08.29 CAPTION 추가
		table.append('<caption>'+$.commUtil.nvl(opts.title)+'</caption>');
		
		if (opts.listOptions.colgroup) {
			table.append('<colgroup></colgroup>');
			$.each(opts.listOptions.colgroup, function(i,w) {
				table.find('colgroup').append('<col width="'+w+'"/>');
			});
		}
		table.append('<thead></thead>');
		table.append('<tbody></tbody>');
		if (opts.listOptions.headCls)
			table.find('thead').addClass(opts.listOptions.headCls);
		if (opts.listOptions.bodyCls)
			table.find('tbody').addClass(opts.listOptions.bodyCls);
		
		table.find('thead').append('<tr class="'+opts.listOptions.headtrCls+'"></tr>');
		
		// 헤더칼럼목록이 있는경우 해당목록을 헤더를 생성함
		if (opts.listOptions.headers) {
			$.each(opts.listOptions.headers, function(i,c) {
				// 2023.08.29 SCOPE 추가
				let h = $('<th scope="col"></th>');
				h.addClass(opts.listOptions.headthCls);
				h.append(c.label);
				if (c.colspan) h.attr('colspan', c.colspan);
				if (c.rowspan) h.attr('rowspan', c.rowspan);
				if (c.cls    ) h.addClass(c.cls);
				table.find('thead > tr').append(h);
			});
		}
		else {
			$.each(opts.listOptions.columns, function(i,c) {
				// 2023.08.29 SCOPE 추가
				table.find('thead > tr').append('<th scope="col" class="'+opts.listOptions.headthCls+'">'+c.label+'</th>');
			});
		}
		
		_clearObject(target, table);
		
		let div = $('<div class="grid-list-items"></div>');
		div.addClass(opts.listOptions.gridCls);
		div.append(table);
		
		return div;
	};
	// 객체 초기화
	function _clearObject(target, elm) {
		var opts = $.data(target, WIDGET).options;
		var tdom = $(target);
		
		elm = elm || tdom.find('.app-grid-table');
		
		//초기화
		elm.find('tbody').html('');
		if (opts._state_variables['_data_rows'] && 
			opts._state_variables['_data_rows'].length == 0 &&
		    opts.listOptions.emptyText) {
			let txt = opts.listOptions.emptyText;
			txt = txt.replace(/{title}/g, opts.title);
			
			let row = $('<tr></tr>');
			row.addClass(opts.listOptions.bodytrCls);
			row.append('<td colspan="'+opts.listOptions.columns.length+'">'+txt+'</td>');
			elm.find('tbody').append(row);
		}

	};
	
	// 데이터 항목생성
	function _loadItem(target, elm, json, rowidx) {

		var opts = $.data(target, WIDGET).options;
		
		rowidx = rowidx || elm.find('tbody > tr').length;

		var row = $('<tr></tr>');
		row.addClass(opts.listOptions.bodytrCls);
		row.data('value', json[opts.idField]);
		
		$.each(opts.listOptions.columns, function(colidx, c) {
			var value = json[c.name] || '';
			// 연번 칼럼인 경우
			if (c.rownumbers) {
				let start = 0;
				if (opts.paging) {
					var page  = opts._state_variables['_page_params']['page'];
					var disp  = opts._state_variables['_page_params']['display'];
					start = disp * (page - 1);
				}
				value = rowidx + start + 1;
			}
			var col = $('<td></td>');
			if (c.cls)     col.addClass(c.cls);
			if (c.colspan) col.prop('colspan', c.colspan);
			if (c.rowspan) col.prop('rowspan', c.rowspan);
			if (c.width)   col.prop('width',c.width);
			if (c.align)   col.addClass(c.align);
			if (c.formatter)
				value = c.formatter(value, json, rowidx, colidx, opts._state_variables['_page_params']);
			col.append(value);

			// 열포맷이 필요한 경우			
			if (c.formatCell)
				c.formatCell(col, json[c.name], json);
			// 행포맷이 필요한 경우			
			if (c.formatRow)
				c.formatRow(row, json);
		
			row.append(col);
		});
		
		//행선택이벤트
		if (opts.listOptions.select) {
			row.bind('click', function() {
				opts.listOptions.select(json, rowidx);
			});
			row.addClass('app-pointer');
		}
		// 행단위 확장이벤트가 있고 토글처리가 true인 경우
		if (opts.listOptions.extendRow &&
		    opts.listOptions.extendToggle) {
			row.bind('click', function() {
				if ($(this).next() &&
					$(this).next().data('value') == $(this).data('value')) {
					if ($(this).next().is(':visible')) {
						$(this).removeClass('active');
						$(this).next().removeClass('on');
						$(this).next().hide();
					}
					else {
						$(this).addClass('active');
						$(this).next().addClass('on');
						$(this).next().show();
					}
				}
			});
		}
		
		//마이페이지-문의내역 사용 - 내용에 표 있을 시 오류 해결
		if(opts.listOptions.bodyCls.indexOf('myGrid')>0) { 
			elm.find('.myGrid').append(row);
		} else {
			elm.find('tbody').append(row);
		}
		
		//행단위 확장이벤트가 있는 경우
		if (opts.listOptions.extendRow) {
			let add = opts.listOptions.extendRow(json, rowidx);
			add.data('value', json[opts.idField]);
			
			//마이페이지-문의내역 사용 - 내용에 표 있을 시 오류 해결
			if(opts.listOptions.bodyCls.indexOf('myGrid')>0) { 
				elm.find('.myGrid').append(add);
			} else {
				elm.find('tbody').append(add);
			}
			
		}
	};
	
	// 객체 데이터 로드
	function _loadObject(target) {
		var opts = $.data(target, WIDGET).options;
		var tdom = $(target);
		var elm  = tdom.find('.app-grid-table');

		//초기화
		_clearObject(target, elm);
			
		$.each(opts._state_variables['_data_rows'], function(i,o) {
			//데이터행 로드
			_loadItem(target, elm, o, i);
		});
		//데이터 로드후 CALLBACK
		if (opts.callback)
			opts.callback(elm, opts._state_variables['_data_rows'], opts._state_variables['_page_params']);

	};
	
	// mode타입별 페이징객체 반환
	function _getPagination(target) {
		
		var opts = $.data(target, WIDGET).options;
		
		switch (opts._state_variables['_current_mode']) {
			case 'CARD':
				return _fnCard.getPagination(target);
			case 'SINGLE':
				return _fnSingle.getPagination(target);
			default:
				return $.extend({}, opts.pagination);
		}
	};

	//----------------------------------------------------------------------//
	// 내부 실행 함수
	//----------------------------------------------------------------------//
	// 초기화
	function init( target ) {
		var opts = $.data(target, WIDGET).options;
		
		opts._state_variables['_toolbar_boxes'] = {};
		opts._state_variables['_extrbar_boxes'] = {};
		opts._state_variables['_last_params'  ] = {};
		opts._state_variables['_data_rows'    ] = [];
		opts._state_variables['_page_params'  ] = _getPagination(target);

		// 그리드영역 생성
		create(target);
		return this;
	};
	
	// 그리드영역 생성
	function create( target ) {

		var opts = $.data(target, WIDGET).options;
		var tdom = $(target);
		
		tdom.html('');

		if (opts.cls)
			tdom.addClass(opts.cls);

		// 객체 생성
		tdom.append(_createObject(target));
		
		if (opts.result)
			createResult(target, true);

		if (opts.autoload)
			load(target, false, true);
	};

	// 페이지객체 가져오기
	function getPagination( target ) {
		
		var opts = $.data(target, WIDGET).options;
		
		return opts._state_variables['_page_params'];
	};

	// 결과데이터 가져오기
	function getRows( target ) {
		
		var opts = $.data(target, WIDGET).options;
		
		return opts._state_variables['_data_rows'];
	};

	// 결과메세지 가져오기
	function getResult( target ) {

		var opts = $.data(target, WIDGET).options;

		if (!opts.resultText)
			return null;

		let msg = opts.resultText;
		msg = msg.replace(/{total}/g, $.formatUtil.toNumber(opts._state_variables['_page_params']['total']));
		msg = msg.replace(/{title}/g, opts.title);
		return msg;
	};

	// 그리드결과메세지 생성 (별도의 영역)
	function createResult( target, init ) {

		var opts = $.data(target, WIDGET).options;
		
		if (!opts.result)
			return;		
		if (init) {
			$(opts.result).html('');
			$(opts.result).append('<div class="row"></div>');
			$(opts.result).find('.row').append('<div class="col app-grid-result"></div>');
			
			if (opts.resultFormatter) {
				opts.resultFormatter($(opts.result));
			}
		}
		// 결과메세지
		let msg = getResult(target);
		let p = $('<p class="txt1"></p>');
		if (opts.resultIcon)
			p.append(opts.resultIcon);
		p.append(msg);
		$(opts.result).find('.app-grid-result').html('');
		$(opts.result).find('.app-grid-result').append(p);
	};
	
	// 페이징 생성 (별도의영역)
	function createPaging(target, pgparams) {

		var opts = $.data(target, WIDGET).options;
		
		if (!opts.paging)
			return;

		opts._state_variables['_page_params'] = pgparams || _getPagination(target);

		var p   = opts._state_variables['_page_params'];
		var ul  = $(opts.paging);
		
		ul.addClass("d-inline-block");
		ul.html('');
		ul.append('<li></li>');
		ul.append('<li></li>');
		ul.append('<li></li>');
		
		let icons = {
			prev : $('<a href="#void" title="이전" class="prev"><span><i class="icon-angle-left"></i></span>이전</a>'),
			next : $('<a href="#void" title="다음" class="next"><span><i class="icon-angle-right"></i></span>다음</a>'),
			first: $('<a href="#void" title="처음" class="first"><span><i class="icon-angle-first"></i></span>처음</a>'),
			last : $('<a href="#void" title="마지막" class="last"><span><i class="icon-angle-last"></i></span>마지막</a>')
		};
		icons.first.data('page', '1');
		icons.last.data ('page', p.pages);
		icons.prev.data ('page', Math.max((p.page-1),1));
		icons.next.data ('page', Math.min((p.page+1), p.pages));
		ul.find('li:first').append(icons.first);
		ul.find('li:first').append(icons.prev);
		
		// 페이지 표시 건수
		let pages_block = 10;
        let start = Math.max(p.page - (pages_block / 2), 0);
        let end   = Math.min(start + pages_block, p.pages);

		for (i = (start+1); i <= end; i++) {
			let no = $('<a href="javascript:void(0);" class="app-page"></a>');
			no.append('<span>'+i+'</span>');
			no.data('page', i);
			
			if (p.page == i)
				no.find('span').addClass('active');
			ul.find('li:eq(1)').append(no);
		}
		ul.find('li:last').append(icons.next);
		ul.find('li:last').append(icons.last);
		
		ul.find('a').bind('click', function() {
			var page = $(this).data('page');
			if (page)
				load(target, {page: page});
		});
	};
	
	// 목록결과 초기화
	function clear( target ) {

		var opts = $.data(target, WIDGET).options;

		opts._state_variables['_data_rows'  ] = [];
		opts._state_variables['_delete_rows'] = [];
		opts._state_variables['_insert_rows'] = [];

		if (opts.result)
			$(opts.result).html('');

		// 타입별 초기화 실행
		_clearObject(target);

		// 페이징 리셋
		createPaging(target);
		
		return;
	};
	
	// 데이터 검색
	function search( target, params, page ) {
		load(target, $.extend(params,{page: (page || 1)}), true);
	};

	// 데이터 로드
	function loadRows( target, rows ) {

		var opts = $.data(target, WIDGET).options;
		
		opts._state_variables['_last_params'] = [];
		opts._state_variables['_data_rows'  ] = rows;
		//데이터 재생성
		_loadObject(target);
	};

	// 데이터 로드
	function load( target, params, init ) {
		
		var opts = $.data(target, WIDGET).options;
		
		// 설정데이터가 있는경우 AJAX LOAD안함 (페이징X)
		if (opts.dataRows) {
			opts._state_variables['_last_params'] = [];
			opts._state_variables['_data_rows'  ] = opts.dataRows;
			//데이터 재생성
			_loadObject(target);
			return;
		}

		let p = opts._state_variables['_last_params'];
		if (init)
			p = $.extend({}, opts.params, {page:1});
		if (params) {
			$.extend(p, params);
		}
		opts._state_variables['_last_params'] = p;
		opts._state_variables['_data_rows'  ] = [];

		//확인검색시 페이지 리셋처리
		if (p.page) opts._state_variables['_page_params']['page'] = p.page;
		else        opts._state_variables['_page_params']['page'] = 1;
		// 페이지건수 표시
		p['rows'] = opts._state_variables['_page_params']['display'];

        $.ajaxUtil.ajaxLoad(
            opts.url, 
			p,
            function(data) {
				if (!data)
					return;
					
				// 배열인 경우
				if ($.isArray(data)) {
					opts._state_variables['_data_rows'] = data;
					//데이터 재생성
					_loadObject(target);
					return;
				}
				if (data.page) {
					$.extend(opts._state_variables['_page_params'], {
						total: data.total,
						pages: data.pages
					});
					createPaging(target, opts._state_variables['_page_params']);
				}
				if (!data.rows)
					return;
					
				opts._state_variables['_data_rows'] = data.rows;
				//데이터 재생성
				_loadObject(target);
				
				createResult(target);
            }
        );
	};

	//----------------------------------------------------------------------//
	// 위젯 정의
	//----------------------------------------------------------------------//
	$.fn[WIDGET] = function(options, param1, param2){
		if (typeof options == 'string'){
			return $.fn[WIDGET].methods[options](this, param1, param2);
		}
		options = options || {};
		
		return this.each(function(){
			var state = $.data(this, WIDGET);
			if (state){
				// deep copy
				$.extend(true, state.options, options);
			} else {
				$.data(this, WIDGET, {
					// deep copy
					options: $.extend(true, {}, $.fn[WIDGET].defaults, options)
				});
			}
		});
	};
	
	//----------------------------------------------------------------------//
	// 기본 함수
	//----------------------------------------------------------------------//
	$.fn[WIDGET].methods = {
		options: function(jq){
			return $.data(jq[0], WIDGET).options;
		},
		// 초기화
		init: function( jq ) {
			return jq.each(function(){
				init(this);
			});
		},
		// 결과데이터 가져오기
		getRows: function( jq ) {
			return getRows(jq[0]);
		},
		// 결과메세지 가져오기
		getResult: function( jq ) {
			return getResult(jq[0]);
		},
		// 페이징객체 가져오기
		getPagination: function( jq ) {
			return getPagination(jq[0]);
		},
		// 검색항목 리셋
		reset: function( jq ) {
			return jq.each(function(){
				resetBoxes(this);
			});
		},
		// 목록결과 초기화
		clear: function( jq ) {
			return jq.each(function(){
				clear(this);
			});
		},
		// 데이터 로드 (검색처리)
		search: function( jq, params, page ) {
			return jq.each(function(){
				search(this, params, page);
			});
		},
		// 데이터 로드 (데이터 인자)
		loadRows: function( jq, rows ) {
			return jq.each(function(){
				loadRows(this, rows);
			});
		},
	};
	
	//----------------------------------------------------------------------//
	// 기본 설정 옵션
	//----------------------------------------------------------------------//
	$.fn[WIDGET].defaults = {
		//제목 (결과메세지에 사용됨)
		title: '',
		//목록 검색 AJAX URL
		url: false,
		//목록 데이터 (있을 경우 AJAX 로딩안함)
		dataRows: false,
		//페이징 영역
		paging: false,
		//결과텍스트 영역
		result: false,
		//항목 KEY ID
		idField: 'no',
		//칼럼 정의
		columns: [],
		//목록 검색 조건
		params: {},
		//목록 검색 페이징 객체
		pagination: {
			page   :  1,
			total  :  0,
			pages  :  0,
			display: 10
		},
		// 기본 스타일시트
		cls:      false,
		
		// 목록형 옵션
		listOptions: {
			//칼럼너비설정 ex) ['180px','*','50px']
			colgroup: false,
			//헤더칼럼목록 (있을경우에만 사용됨)
			headers:  false,
			// 목록형인 경우 테이블 스타일시트
			tableCls: false,
			// 목록형인 경우 기본 스타일시트
			gridCls: "table-box-1 m-overbox",
			// 목록형인 경우 헤더 스타일시트
			headCls: "bs-1 ts-l",
			// 목록형인 경우 내용 스타일시트
			bodyCls: "bs-1 t-t-c ts-m",
			// 목록형인 경우 헤더 tr 스타일시트
			headtrCls: "px-v-l",
			// 목록형인 경우 헤더 th 스타일시트
			headthCls: "border-top-0 bg-white",
			// 목록형인 경우 내용 tr 스타일시트
			bodytrCls: "px-v-xl t-t-c",
			//행단위 행확장이벤트
			extendRow: false,
            //행확장 토글이벤트 사용여부
            extendToggle: false,
			//행선택 이벤트
			select: false,
			//데이터가 없을경우 메세지
			emptyText: '검색된 {title}가 없습니다.',
		},
		//검색결과 아이콘
		resultIcon: '<i class="icon-document"></i>',
		//검색결과 메세지
		resultText: false, // '<em>{total}</em><span>개의 {title}가 있습니다.</span>'
		//검색결과영역 format callback
		resultFormatter: false,

		//검색후 처리함수
		callback: false,
		//최초로딩 여부
		autoload: false,
		// 내부변수
		_state_variables : {
			// 검색결과 데이터 배열
			_data_rows     : [],
			// 페이징 조건 데이터
			_page_params   : {},
			// 최종검색 조건 데이터
			_last_params   : false
		}
	};
	
})(jQuery);

//=======================================================================//
//FAQ페이지 호출 및 Pageing 처리
//-----------------------------------------------------------------------//
function loadAccordion(ulKey, url, params) {
	var ul = $(ulKey);
	if (!ul)
		return;
	
	var currentPage = params.page;       // 현재 페이지 
	
	var data  = $.ajaxUtil.ajaxDefault(url, params);
	// 필요한 HTML 요소 생성
	if (data) {
		ul.empty(); // 이전 아이템들을 제거
		$.each(data.rows, function(key, value) {
			
			let decodedString = decodeHtml(value['pstCn']);

			decodedString1 = decodedString.replace(/<\/p>/gi, "<br>").replace(/<p>/gi, "");

			var li = $('<li></li>');
			var question = $('<a class="question" >' + value['pstTtl'] + '</a>');
			var answer = $('<div class="answer ckEdit" style="height: auto; overflow: auto; padding-right:15px;"></div>');
			answer.append($("<p>").html(decodedString1));

			li.append(question);
			li.append(answer);
			ul.append(li);
		});
		
		$(ulKey + '>li>a').click(function(){
			$(this).parent().addClass('active').siblings().removeClass("active");
		});
		
		// 페이지 번호 생성 및 이벤트 추가
		var pagination = $(".paging-box");
		pagination.empty();

		var pageUl = $('<ul class="d-inline-block"></ul>');

		// 첫 페이지 및 이전 페이지 버튼 생성
		var firstPrevLi = $('<li></li>');
		firstPrevLi.append('<a href="#none" title="처음" class="first"><span><i class="icon-angle-first"></i></span>처음</a>');
		firstPrevLi.append('<a href="#none" title="이전" class="prev"><span><i class="icon-angle-left"></i></span>이전</a>');
		pageUl.append(firstPrevLi);
		
		// 이전 버튼 클릭 이벤트
		firstPrevLi.find('.prev').click(function(e) {
			e.preventDefault();
			if (currentPage > 1) {
				currentPage--;
				var updatedParams = $.extend({}, params, { page: currentPage });
				loadAccordion(ulKey, url, updatedParams);
			}
			return false;
		});
		
		// 첫 페이지 버튼 클릭 이벤트
		firstPrevLi.find('.first').click(function(e) {
			e.preventDefault();
			if (currentPage !== 1) {
				currentPage = 1;
				var updatedParams = $.extend({}, params, { page: currentPage });
				loadAccordion(ulKey, url, updatedParams);
			}
			return false;
		});

		// 페이지 번호 생성
		var pagesLi = $('<li></li>');
		for(var i = 1; i <= data.pages; i++) {
			var a = $('<a href="#none"></a>');

			// 현재 페이지 번호에 "active" 클래스 적용
			if(i == data.page) {
				var span = $('<span class="active"></span>').text(i);
			} else {
				var span = $('<span></span>').text(i);
			}
			a.append(span);
			
			// 페이지 번호 클릭 이벤트
			a.click(function(e){
				e.preventDefault();
				currentPage = $(this).text();
				var updatedParams = $.extend({}, params, { page: currentPage });
				loadAccordion(ulKey, url, updatedParams);
			});

		    pagesLi.append(a);
		}
		pageUl.append(pagesLi);

		// 다음 페이지 및 마지막 페이지 버튼 생성
		var nextLastLi = $('<li></li>');
		nextLastLi.append('<a href="#none" title="다음" class="next"><span><i class="icon-angle-right"></i></span>다음</a>');
		nextLastLi.append('<a href="#none" title="마지막" class="last"><span><i class="icon-angle-last"></i></span>마지막</a>');
		pageUl.append(nextLastLi);
		
		// 다음 버튼 클릭 이벤트
		nextLastLi.find('.next').click(function(e) {
			e.preventDefault();
			if (currentPage < data.pages) {
				currentPage++;
				var updatedParams = $.extend({}, params, { page: currentPage });
				loadAccordion(ulKey, url, updatedParams);
			}
			return false;
		});

		// 마지막 페이지 버튼 클릭 이벤트
		nextLastLi.find('.last').click(function(e) {
			e.preventDefault();
			if (currentPage !== data.pages) {
				currentPage = data.pages;
				var updatedParams = $.extend({}, params, { page: currentPage });
				loadAccordion(ulKey, url, updatedParams);
			}
			return false;
		});
		
		if (!data.rows || data.rows.length === 0) {
		  ul.empty(); // 이전 아이템들을 제거
		  
		  var div = $('<div class="shadow-box-1 pb-56px py-40px text-center"></div>');

		  var p = $('<p class="fs-24px fw-600 lh-1 mb-2">검색결과가 없습니다.</p>');
		  var span = $('<span class="fs-15px fw-300">조건 검색에서 조건을 수정하여 다시 검색해보세요.</span>');
		  
		  div.append('<div class="app-imgarea"><img src="'+getUrl('/images/sub/searchicon.png')+'" alt="icon"/></div>');
		  div.append(p);
		  div.append(span);
		  
		  ul.append(div);
		}
		pagination.append(pageUl);
	}
};
