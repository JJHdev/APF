/**
*******************************************************************************
*** 파일명    : app_smartsearch.js
*** 설명      : 업무용 스마트검색 컨트롤
***
*** -----------------------------    Modified Log   ---------------------------
*** ver             date                author                  description
*** ---------------------------------------------------------------------------
*** 1.0         2023.04.20              LSH
*******************************************************************************
**/
$.fn.appSmartSearch = function ( args ) {
	
	var options = {
		// SEARCH : 검색
		// MATCH  : 매칭
		// GIS    : GIS
		// JOIN   : 회원가입
		// MYPAGE : 마이페이지
		mode: MODE.SEARCH,
		//폼객체
		form: false,
		//그리드객체
		grid: false,
		//제목
		title: '스마트검색',
		//CLS
		cls: false,
		titleCls: false,
		titboxCls: false,
		// 항목제목 CLS
		titCls: 'txt1',
		// 항목그룹 CLS (2023.07.05 추가)
		groupCls: 'sub-box-1',
		// 항목 CLS (2023.07.05 추가)
		itemCls: 'box-lev-1',
		// 항목박스 CLS
		boxCls:  'box',
		// 항목레이블 CLS
		labelCls: false,
		// 스타일
		style: false,
		// 닫기버튼 사용여부 (2023.07.09 추가)
		toggleable: false,
		// 검색버튼 사용여부 (2023.07.09 추가)
		searchable: false,
		//처리함수
		callbacks: {
			// 초기화 실행전 함수
			onBeforeReset: false,
			// 초기화 함수
			onClickReset: false,
			// 검색 함수
			onClickSearch: false,
			// 로딩후 실행함수
			onLoad: false
		},
		//항목정의
		items: [[],[]]
		
	};
	
	// 2023.07.09 모드기준 기본설정 변경
	if (args.mode == MODE.SEARCH) {
		$.extend(options, {
			toggleable: true,
			searchable: true
		});
	}
	else if (args.mode == MODE.MATCH) {
		$.extend(options, {
			cls      : 'mb-24px',
			titleCls : 'fs-20px',
			titboxCls: 'mb-16px'
		});
	}
	else if (args.mode == MODE.GIS) {
		$.extend(options, {
			titCls  : 'tit',
			boxCls  : 'box',
			itemCls : 'box-lev-2',
			groupCls: 'sub-box-2',
			searchable: true
		});
	}
	else if (args.mode == MODE.JOIN) {
		$.extend(options, {
			style    : 'max-width: 650px;',
			title    : '',
			titCls   : 'tit',
			titleCls : 'fs-20px',
			titboxCls: 'mb-16px',
			labelCls : 'fs-14px fs-md-16px fw-600',
			boxCls   : 'box mb-16px',
			itemCls  : 'round-16px shadow-box-1 box-lev-2 mx-auto p-24px',
			groupCls : 'post-details-2 mb-24px mx-auto'
		});
	}
	else if (args.mode == MODE.MYPAGE) {
		$.extend(options, {
			title    : '',
			cls      : 'mb-24px',
			titleCls : 'fs-20px',
			titboxCls: 'mb-16px',
			labelCls : 'fs-14px fs-md-16px'
		});
	}
	options = $.extend(true, options, args);
	//현재객체
	let thisCmp = this;
	//목록테이블객체
	let thisElm = $(this);
	//검색박스객체
	let thisBox = {};

	this.create = function() {

		thisElm.html('');

		if (options.cls)
			thisElm.addClass(options.cls);
		if (options.style)
			thisElm.prop('style', options.style);
		
		if (options.title)
			thisElm.append(thisCmp.createHeader());
		
		let div = $('<div></div>');

		// 닫기버튼 사용시에만 CLS 추가
		if (options.toggleable)
			div.addClass("smart_search_toggle_target");

		div.append(thisCmp.createItems());
		
		
		// 검색버튼 사용시에만 버튼목록 표시
		if (options.searchable) {
			div.append(thisCmp.createButtons());
		}
		thisElm.append(div);
		if (options.callbacks.onLoad)
			options.callbacks.onLoad(thisBox, thisElm, thisCmp);
	};
	
	// 헤더영역
	this.createHeader = function() {
		
		let txt = $('<p class="'+options.titCls+'"></p>');
		txt.append(options.title);
		if (options.titleCls)
			txt.addClass(options.titleCls);
		
		let t = $('<div class="tit-box-1 smart-search-header"></div>');
		if (options.titboxCls)
			t.addClass(options.titboxCls);
		t.append('<div class="row"></div>');
		t.find('.row').append('<div class="col"></div>');
		t.find('.row').append('<div class="col"></div>');
		t.find('.col:first').append(txt);
			
		// 닫기버튼 사용시에만 표시
		if (options.toggleable) {
			let b = $('<a href="#" class="bs-m btn-primary-ouline round-16px smart_search_toggle"></a>');
			b.append('<i class="icon-times"></i>&nbsp;');
			b.append('스마트 검색 닫기');
			b.click(function() {
				if ($(this).hasClass('Fc')){
					$(this).html('<i class="icon-times"></i>스마트 검색 닫기');
					$(this).removeClass('btn-primary');
					$(this).addClass('btn-primary-ouline');
				}else{										
					$(this).html('<i class="icon-menu"></i>스마트 검색 열기');
					$(this).removeClass('btn-primary-ouline');
					$(this).addClass('btn-primary');
				}						
				$(this).toggleClass('Fc');
				$('.smart_search_toggle_target').toggleClass('trans');
			});
			t.find('.col:last').append(b);
		}
		return t;
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
			let tit = $('<p class="'+options.titCls+'"></p>');
			tit.append(item.label);
			
			let cls = item.labelCls || options.labelCls;
			if (cls)
				tit.addClass(cls);

			if (item.must)
				tit.append('<em style="margin-left: 2px;"></em>');
			// INFO 아이콘 표시
			if (item.info) {
				let i = false;
				if (item.info.html)
					i = $(item.info.html);
				else
					i = $('<a class="icon-info-circle ml-5px text-primary"></a>');
				if (item.info.click)
					i.click(function() {
						item.info.click(thisCmp, this);
					});
				tit.append(i);
			}
			// 항목초기화 버튼이 있을 경우
			if (item.initicon) {
				let top = $('<div class="top"></div>');
				top.append('<div class="row"></div>');
				top.find('.row').append( $('<div class="col"></div>').append(tit) );
				
				let btn = $('<p class="tit2"><i class="icon-rotate-left mr-5px"></i>초기화</p>');
				btn.click(function() {
					thisCmp.clear(item.name);
				});
				top.find('.row').append( $('<div class="col flex-b80px"></div>').append(btn) );
				b.append(top);
			}
			else {
				b.append(tit);
			}
		};
		
		let _fnItems = function( b, item ) {
			if (item.type == 'appSelectBox') {
				let p = $('<div'+(item.id ? 'id="'+item.id+'"' :'')+' class="row"></div>').appSelectBox(item.inputOptions);
				b.append( $('<div class="'+options.itemCls+'"></div>').append(p) );
				thisBox[item.name] = p;
			}
			else if (item.type == 'appComboBox') {
				let p = $('<select id="'+item.name+'" name="'+item.name+'"></select>').appComboBox(item.inputOptions);
				let e = $('<div class="form-area-box"></div>');
				e.append($('<div class="ele-icon-box"></div>').append(p));
				e.append('<label for="'+item.name+'"></label>');
				b.append(e);
				thisBox[item.name] = p;
			}
			else if (item.type == 'appSlideBox') {
				let p = $('<div'+(item.id ? 'id="'+item.id+'"' :'')+'></div>').appSlideBox(item.inputOptions);
				b.append(p);
				thisBox[item.name] = p;
			}
			else if (item.type == 'appInputBox') {
				let p = $('<div'+(item.id ? 'id="'+item.id+'"' :'')+'></div>').appInputBox(item.inputOptions);
				b.append( $('<div class="'+options.itemCls+'"></div>').append(p) );
				thisBox[item.name] = p;
			}
			else if (item.type == 'appFile') {
				// 첨부파일 객체
				let p = $('<div class="'+options.itemCls+'"'+(item.id ? 'id="'+item.id+'"' :'')+'"></div>')[item.typeKey](item.inputOptions)[item.typeKey]('init');
				b.append(p);
			}
		};
		
		let t = $('<div class="'+options.groupCls+' smart-search-items"></div>');
		
		$.each(options.items, function(i, row) {
			
			let bx = $('<div class="'+options.boxCls+'"></div>');

			if (row.length > 1) {
				bx.append('<div class="row"></div>');
			}
			$.each(row, function(j, item) {
				if (row.length > 1) {
					let ce = $('<div></div>');
					if (row.colCls)
						ce.addClass(row.colCls);
					else
						ce.addClass("col");
					
					_fnTitle(ce, item);
					_fnItems(ce, item);
					bx.find('.row:last').append(ce);
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

		if (options.callbacks.onBeforeReset)
			options.callbacks.onBeforeReset();

		// 그리드 초기화 처리
		//options.grid.doClear();
		// 그리드 검색 처리
		options.grid.doSearch();
		
		if (options.callbacks.onClickReset)
			options.callbacks.onClickReset();
	};
	
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
