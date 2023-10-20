/**
*******************************************************************************
*** 파일명    : app_search.js
*** 설명      : 통합검색 컨트롤
***
*** -----------------------------    Modified Log   ---------------------------
*** ver             date                author                  description
*** ---------------------------------------------------------------------------
*** 1.0         2023.07.03               LHB
*******************************************************************************
**/
$.fn.appSearch = function ( args ) {
	
	var options = $.extend(true, {
		//모드 (SEARCH: 검색/MATCH: 매칭)
		mode: MODE.SEARCH,
		//제목
		title: '통합검색',
		//URL
		url: '/usr/inform/search/getListSearch.do',
		//header 관련
		headerCls: false,
		headerId: 'appSearchHeader',
		//CLS
		cls: false,
		titleCls: false,
		titboxCls: false,
		//폼객체
		form: false,
		//그리드객체
		grid: false,
		//파라미터
		params: false,
		//처리함수
		callbacks: {
			// 초기화 함수
			onClickReset: false,
			// 검색 함수
			onClickSearch: false,
			// 로딩후 실행함수
			onLoad: false
		},
		//항목정의
		items: [[],[]]
	}, args);
	
	//현재객체
	var thisCmp = this;
	//목록테이블객체
	var thisElm = $(this);
	//검색박스객체
	var thisBox = {};

	this.create = function() {
		if (options.cls)
			thisElm.addClass(options.cls);
		thisElm.html('');
		thisElm.append(thisCmp.createHeader());
		
		if (options.autoload)
			load();
		
		return;
		
		let div = $('<div class="smart_search_toggle_target"></div>');
		div.append(thisCmp.createItems());
		
		
		// 검색인 경우에만 버튼목록 표시
		if (options.mode == MODE.SEARCH) {
			div.append(thisCmp.createButtons());
		}
		thisElm.append(div);
		if (options.callbacks.onLoad)
			options.callbacks.onLoad(thisBox, thisElm, thisCmp);
	};
	
	// 헤더영역
	this.createHeader = function() {
		
		let div = $('<div></div>');
		if (options.headerId) {
			div.prop('id', options.header);
		}
		if (options.headerCls) {
			div.addClass(options.headerCls);
		}
		
		return div;
	};
	
	// 버튼영역
	this.createButtons = function() {
		
		let t = $('<div class="bottom-box-btn my-16px smart-search-button"></div>');
		t.append('<div class="row"></div>');
		t.find('.row').append('<div class="col"></div>');
		t.find('.row').append('<div class="col"></div>');
		// 초기화 버튼
		let b1 = $('<button type="button" class="btn-combi-3 w-100 bs-l"><i class="icon-rotate-left"></i>초기화</button>');
		b1.click(function() { thisCmp.clear(); });
		// 검색 버튼
		let b2 = $('<button type="button" class="btn-primary w-100 bs-l"><i class="icon-search"></i>검색</button>');
		b2.click(function() { thisCmp.search(); });
		
		t.find('.col:first').append(b1);
		t.find('.col:last' ).append(b2);
		
		return t;		
	};
	
	// 항목영역
	this.createItems = function() {
		
		let _fnTitle = function( b, item ) {
			b.append('<p class="txt1"></p>');
			b.find('p.txt1:last').append(item.label);
			if (item.must)
				b.find('p.txt1:last').append('<em style="margin-left: 2px;"></em>');
			// INFO 아이콘 표시
			if (item.info) {
				let i = $(item.info.html);
				i.click(item.info.click);
				b.find('p.txt1:last').append(i);
			}
		};
		
		let _fnItems = function( b, item ) {
			if (item.type == 'appSelectBox') {
				let p = $('<div class="row"></div>').appSelectBox(item.inputOptions);
				b.append('<div class="box-lev-1"></div>');
				b.find(".box-lev-1:last").append(p);
				thisBox[item.name] = p;
			}
			else if (item.type == 'appComboBox') {
				let p = $('<select id="'+item.name+'" name="'+item.name+'"></select>').appComboBox(item.inputOptions);
				b.append('<div class="form-area-box"><div class="ele-icon-box"></div></div>');
				b.find(".ele-icon-box:last").append(p);
				thisBox[item.name] = p;
			}
			else if (item.type == 'appSlideBox') {
				let p = $('<div></div>').appSlideBox(item.inputOptions);
				b.append(p);
				thisBox[item.name] = p;
			}
		};
		
		let t = $('<div class="sub-box-1 smart-search-items"></div>');
		
		$.each(options.items, function(i, row) {
			
			let bx = $('<div class="box"></div>');

			if (row.length > 1) {
				bx.append('<div class="row"></div>');
			}
			$.each(row, function(j, item) {
				if (row.length > 1) {
					bx.find('.row:last').append('<div class="col"></div>');
					_fnTitle(bx.find('.col:last'), item);
					_fnItems(bx.find('.col:last'), item);
				}
				else {
					_fnTitle(bx, item);
					_fnItems(bx, item);
				}
			});
			t.append(bx);
		});
		return t;
	};
	
	// 항목리셋
	this.clear = function( name ) {
		if (name) {
			if (thisBox[name].clear)
				thisBox[name].clear();
			return;
		}
		$.each(thisBox, function(key,box) {
			if (box.clear)
				box.clear();
		});
		// 폼데이터 초기화 처리
		options.form[0].reset();
		// 그리드 초기화 처리
		//options.grid.doClear();
		// 그리드 검색 처리
		options.grid.doSearch();
		
		if (options.callbacks.onClickReset)
			options.callbacks.onClickReset();
	};
	
	this.load = function() {
		$.ajaxUtil.ajaxLoad(
            options.url, 
			options.params,
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
	}
	
	// 항목검색(선택)
	this.search = function() {
		
		// 그리드 검색 처리
		options.grid.doSearch();
		
		if (options.callbacks.onClickSearch)
			options.callbacks.onClickSearch();
	};
	
	this.create();
	
	return this;
};
