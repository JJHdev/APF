/**
*******************************************************************************
*** 파일명    : app_bbsgrid.js
*** 설명      : 게시판용 목록(그리드) 컨트롤
***
*** -----------------------------    Modified Log   ---------------------------
*** ver             date                author                  description
*** ---------------------------------------------------------------------------
*** 1.0         2023.04.20              LSH
*******************************************************************************
**/
//===========================================================================//
// 업무용 목록(그리드) 컨트롤
//===========================================================================//
(function($){
	
	const WIDGET = 'appBbsGrid';
	
	//----------------------------------------------------------------------//
	// 객체 단위 실행 함수
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
								
		$.each(opts.listOptions.columns, function(i,c) {
			// 2023.08.29 SCOPE 추가
			table.find('thead > tr').append('<th scope="col" class="'+opts.listOptions.headthCls+'">'+c.label+'</th>');
		});
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
			elm.find('tbody').append('<tr><td colspan="'+opts.listOptions.columns.length+'">'+txt+'</td></tr>');
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
		elm.find('tbody').append(row);
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
	// 페이징객체 반환
	function _getPagination(target) {
		var opts = $.data(target, WIDGET).options;
		return $.extend({}, opts.pagination);
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
			prev : $('<a href="javascript:void(0);" title="이전"   class="prev" ><span><i class="icon-angle-left" ></i></span><span class="hiddenATag">이전</span></a>'),
			next : $('<a href="javascript:void(0);" title="다음"   class="next" ><span><i class="icon-angle-right"></i></span><span class="hiddenATag">다음</span></a>'),
			first: $('<a href="javascript:void(0);" title="처음"   class="first"><span><i class="icon-angle-first"></i></span><span class="hiddenATag">처음</span></a>'),
			last : $('<a href="javascript:void(0);" title="마지막" class="last" ><span><i class="icon-angle-last" ></i></span><span class="hiddenATag">마지막</span></a>')
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
		}
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
			// 목록형인 경우 테이블 스타일시트
			tableCls: false,
			// 목록형인 경우 기본 스타일시트
			gridCls: "table-box-1 overtable",
			// 목록형인 경우 헤더 스타일시트
			headCls: "bs-1 ts-xl",
			// 목록형인 경우 내용 스타일시트
			bodyCls: "t-t-c ts-s",
			// 목록형인 경우 헤더 tr 스타일시트
			headtrCls: "",
			// 목록형인 경우 헤더 th 스타일시트
			headthCls: "border-top-0",
			// 목록형인 경우 내용 tr 스타일시트
			bodytrCls: "",
			//행선택 이벤트
			select: false,
			//데이터가 없을경우 메세지
			emptyText: '검색된 {title}가 없습니다.',
		},
		//검색결과 아이콘
		resultIcon: '<i class="icon-document"></i>',
		//검색결과 메세지
		resultText: '<em class="ml-4px">{total}</em>개의 게시물이 있습니다.',
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
