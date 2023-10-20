/**
*******************************************************************************
*** 파일명    : comm_component.js
*** 설명      : 사용자 정의 컴포넌트
***
***             $.domUtil.getRow        : Bootstrap Grid 생성
***             $.domUtil.getTitleBox   : 제목 박스 반환
***             $.domUtil.getEmptyBox   : 빈텍스트용 박스 반환
***             $.domUtil.getInputBox   : 입력 박스 반환
***             $.domUtil.getEleIconBox : Ele-icon 박스 반환
***             $.fn.appTabs            : 기본형 탭 생성
***             $.fn.appBbsTabs         : 게시판 탭 생성
***             $.fn.appButtonTabs      : 버튼형 탭 생성
***             $.fn.appSwiperGrid      : Swiper Grid 컴포넌트 생성
***
*** -----------------------------    Modified Log   ---------------------------
*** ver             date                author                  description
*** ---------------------------------------------------------------------------
*** 1.0         2023.03.21              LSH
*******************************************************************************
**/

$.domUtil = {};

// Bootstrap Grid 생성 함수
$.domUtil.getRow = function( args ) {
	var options = $.extend({
		cls:    false,
		items:  [/* colCls, cls, formatHtml, format */],
		rowCls: "row",
		colCls: "col",
	},args);
	let row = $('<div></div>');
	row.addClass(options.rowCls);
		if (options.cls)
			row.addClass(item.options);
	
	$.each(options.items, function(i, item) {
		
		let col = $('<div></div>');
		if ($.type(item) === 'string') {
			col.addClass(options.colCls);
			col.append(item);
			row.append(col);
			return true;
		}
		if (item.colCls)
			col.addClass(item.colCls);
		else
			col.addClass(options.colCls);
		if (item.cls)
			col.addClass(item.cls);
		if (item.formatHtml) {
			if ($.type(item.formatHtml) === 'array') {
				$.each(item.formatHtml, function(j,h) {
					col.append(h);
				});
			}
			else
				col.append(item.formatHtml);
		}
		if (item.format)
			col.append(item.format());
		row.append(col);
	});
	return row;
};

// 제목박스 반환
$.domUtil.getTitleBox = function( args ) {
	
	var options = $.extend({
		// 제목
		title:    '',
		// 제목아이콘
		icon:    '<img src="'+getUrl('/images/sub/iconimg1.svg')+'"/>',
		titleCls: 'txt1',
		titleBox: 'tit-box-3 mb-16px',
		buttonCls:'bs-l btn-combi-2 ml-5px',
		comment:  false,
		// 버튼목록
		buttons: [
			//{id:'btnPrint', cls: 'bs-l btn-combi-2', icon: 'icon-print', value: '보고서 인쇄', click: function() {}}
		],
		// 우측영역 포맷터 (버튼이 없을 경우에만 적용됨)
		rightFormatter: false,
		// 제목영역 추가 CLS
		addCls: false
	}, args);

	// 제목텍스트
	let txt = $('<p></p>');
	txt.addClass(options.titleCls);
	if (options.icon)
		txt.append(options.icon);
	txt.append(options.title);
	if (options.comment)
		txt.append('<em class="ml-5px">'+options.comment+'</em>');
		
	let tit = $('<div></div>');
	tit.addClass(options.titleBox);
	tit.append('<div class="row"></div>');
	tit.find('.row').append('<div class="col titbox"></div>');
	tit.find('.titbox').append(txt);
	if (options.addCls)
		tit.find('.titbox:last').addClass(options.addCls);
	
	let _getButton = function(b) {

		if (!b.value)
			return $('<div class="col"></div>').append(b);

		let btn = $('<button type="button"></button>');
		if (b.data) {
			$.each(b.data, function(key, val) {
				btn.data(key, val);
			});
		}
		if (b.cls)
			btn.addClass(b.cls);
		else
			btn.addClass(options.buttonCls);
		if (b.icon)
			btn.append('<i class="'+b.icon+' mr-5px"></i>');
		btn.append(b.value);
		if (b.id)
			btn.prop('id', b.id);
		if (b.click)
			btn.click(b.click);
			
		return $('<div class="col btnbox"></div>').append(btn);
	};
	
	// 버튼추가
	if (options.buttons && 
		options.buttons.length > 0) {
		$.each(options.buttons, function(i,b) {
			tit.find('.row:last').append(_getButton(b));
		});
	}
	// 우측영역 포맷터가 있을 경우
	else if (options.rightFormatter){
		options.rightFormatter(tit.find('.btnbox'));
	}
	return tit;
};

$.domUtil.getEmptyBox = function( emptyText, cls ) {
	let r = $('<div class="text-box-1 p-24px bg-primary-t5"></div>');
	if (cls)	
		r.addClass(cls);
	r.append($.domUtil.getRow({colCls:'col-12', items:[emptyText]}));
	return r;
};

$.domUtil.getInputBox = function( c, wrapCls ) {
	if (c.type == 'select') {
		let o = $.extend({
			id:    c.id,
			name:  c.name,
			value: c.value,
			callback: c.callback,
		}, c.options);
		
		/*
		let g = $('<div></div>');
		if (wrapCls) 
			g.addClass(wrapCls);
		return g.appDropdownBox(o);
		*/
		if (wrapCls) {
			o['wrapCls'] = wrapCls;
			return $('<div></div>').appComboBox(o);
		}
		else {
			return $('<select></select>').appComboBox(o);
		}
	}
	else if (c.type == 'radio' || 
	         c.type == 'checkbox') {
		let o = $.extend({
			name: c.name,
			form: c.type,
			value: c.value,
			callback: c.callback,
			wrapCls: 'check-radio-box'
		}, c.options);
		let g = $('<div></div>');
		if (wrapCls) 
			g.addClass(wrapCls);
		return g.appSelectBox(o);
	}
	else if (c.type == 'gridradio') {
		let o = $.extend({
			name: c.name,
			form: c.type,
			value: c.value,
			callback: c.callback,
			wrapCls: 'check-radio-box'
		}, c.options);
		let g = $('<div></div>');
		if (wrapCls) 
			g.addClass(wrapCls);
		return g.appGridRadioBox(o);
	}
	else if (c.type == 'textarea') {
		let e = $('<textarea></textarea>');
		e.prop('name' , c.name);
		e.prop('title', 'textarea');
		if (c.id)          e.prop('id'   , c.id);
		if (c.rows)        e.prop('rows' , c.rows);
		if (c.cols)        e.prop('cols' , c.cols);
		if (c.value)       e.prop('value', c.value);
		if (c.maxlength)   e.prop('maxlength', c.maxlength);
		if (c.disabled)    e.prop('disabled', true);
		if (c.readonly)    e.prop('readonly', true);
		if (c.placeholder) e.prop('placeholder', c.placeholder);
		if (c.cls)         e.addClass(c.cls);
		if (c.readonly)    e.addClass('readonly');
		
		if (wrapCls) {
			return $('<div class="'+wrapCls+'"></div>').append(e);
		}
		return e;
	}
	else {
		let e = $('<input/>');
		e.prop('type' , c.type);
		e.prop('name' , c.name);
		e.prop('title', 'input');
		if (c.id)          e.prop('id'   , c.id);
		if (c.maxlength)   e.prop('maxlength', c.maxlength);
		if (c.disabled)    e.prop('disabled', true);
		if (c.readonly)    e.prop('readonly', true);
		if (c.placeholder) e.prop('placeholder', c.placeholder);
		if (c.cls)         e.addClass(c.cls);
		if (c.readonly)    e.addClass('readonly');
		if (c.value) {
			if (c.valueFormatter)
				e.prop('value', c.valueFormatter(c.value));
			else
				e.prop('value', c.value);
		}
		
		// 달력인 경우
		if (c.calendar) {
			e.addClass("datepicker-input");
			// 달력설정 (comm_const.js 참고)
			e.datepicker(OPTIONS.DATEPICKER);
			// 날짜입력 마스크(YYYY-MM-DD)
			e.inputmask("9999-99-99");
		}
		// 숫자만 입력시
		if (c.number) {
			bindOnlyNumber(e);
		}
		// 음수포함숫자만 입력시
		if (c.minus) {
			bindOnlyMinusNumber(e);
		}
		// 소수점포함숫자만 입력시
		if (c.float) {
			bindOnlyFloat(e);
		}
		// 금액만 입력시
		if (c.money) {
			bindEditMoney(e);
		}
		// 전화번호 하이픈 이벤트 처리
		if (c.phone) {
			bindPhoneHyphen(e);
		}
		// 엔터입력시 이벤트 처리
		if (c.enter) {
			e.bind('keydown', function(e) {
				if (e.keyCode != 13)
					return;
				c.enter();
			});
		}
		
		if (c.type == 'hidden')
			return e;
		if (wrapCls) {
			let w = $('<div class="'+wrapCls+'"></div>').append(e);
			if (c.calendar) {
				w.append('<a href="javascript:void(0)" class="icon-calendar"><span class="hiddenATag">숨김</span></a>');
			}
			if (c.clear) {
				let i = $('<i class="icon-times-circle F text-ele-TextDisable"></i>');
				i.bind('click', bindClear);
				w.append(i);
			}
			if (c.postfix) {
				w.append(c.postfix);
			}
			return w;
		}
		return e;
	}
};

$.domUtil.getEleIconBox = function( args ) {
	let d = $('<div></div>');
	if (args.cls)
		d.addClass(args.cls);
	else
		d.addClass("form-area-box");
	if (args.label)
		d.append($('<label></label>').append(args.label));
	d.append('<div class="ele-icon-box"></div>');
	d.find('.ele-icon-box').append(args.input);
	if (args.icon) {
		let i = $(args.icon);
		if (args.clickIcon)
			i.click(args.clickIcon);
		d.find('.ele-icon-box').append(i);
	}
	return d;
};

$.domUtil.getEleIconGroup = function( args, inputs ) {

	let d = $('<div></div>');
	if (args.cls)
		d.addClass(args.cls);
	d.append('<div class="row"></div>');
	
	$.each(inputs, function(i,inp) {
		let c= $('<div class="col"></div>');
		if (inp.cls)
			c.addClass(inp.cls);
		if (inp.box)
			c.append( $('<div class="ele-icon-box"></div>').append(inp.box) );
		else if (inp.dom)
			c.append(inp.dom);
		d.find('.row:last').append(c);
	});
	return d;
};

$.domUtil.getButton = function( args ) {
	let b = $('<button type="button"></button>');
	if (args.data) {
		$.each(args.data, function(key, val) {
			b.data(key, val);
		});
	}
	if (args.id)
		b.prop('id', args.id);
	if (args.cls)
		b.addClass(args.cls);
	if (args.icon)
		b.append('<i class="'+args.icon+'"></i>');
	if (args.value)
		b.append(args.value);
	if (args.iconr)
		b.append('<i class="'+args.iconr+'"></i>');
	if (args.click)
		b.click(args.click);
	return b;
};

//===========================================================================//
// 버튼영역 정의
//===========================================================================//
$.fn.appButtons = function ( args ) {

	var options = $.extend({
		cls:     false,
		boxCls:  "bottom-box-btn",
		// 버튼목록
		buttons: [],
		// 함수목록
		functions: false,
	}, args);
	
	let _this = $(this);
	
	let _createButton = function(b) {
		if (options.functions) {
			$.extend(b, {click: options.functions[b.click]});
		}
		return $('<div class="col"></div>').append($.domUtil.getButton(b));
	};
	
	let row = $('<div class="row"></div>');
	$.each(options.buttons, function(i,btn) {
		if ($.type(btn) === 'array') {
			let r = $('<div class="row"></div>');
			if (i == 0)
				r.addClass('justify-content-start');
			else if (i == (options.buttons.length-1))
				r.addClass('justify-content-end');
			else
				r.addClass('justify-content-center');
			$.each(btn, function(j,bitem) {
				r.append( _createButton(bitem) );
			});
			row.append( $('<div class="col"></div>').append(r) );
		}
		else {
			row.append( _createButton(btn) );
		}
	});
	_this.html('');
	_this.append(row);
	
	if (options.boxCls)
		_this.addClass(options.boxCls);
	if (options.cls)
		_this.addClass(options.cls);

	return this;
};

//===========================================================================//
// 탭 정의
//===========================================================================//
$.fn.appTabs = function ( args ) {
	
	var options = $.extend({
		//선택이벤트
		select:  false,
		//초기값
		value:   false,
		cls:     "shadow-box-1 mb-32px p-8px",
		tabCls:  "capsule-box",
		itemCls: "app-tab-item",
		colCls:  "col fs-md-16px lh-md-38px",
		url:     false,
		params:  false,
		icons:   false,//[]
		// 탭목록
		items: [
			//{tab: '#appTab1', label:'대시보드', icon:'icon-grid-square'},
			//{tab: '#appTab2', label:'상세정보', icon:'icon-list'}
		]
	}, args);

	var thisElm = $(this);
	
	function _createTabs() {
		
		let row = $('<div class="row"></div>');
		
		if (options.url) {

			let ret = $.ajaxUtil.ajaxDefault(options.url, options.params);
			if (ret && ret.length > 0) {
				
				$.each(ret, function(index, item) {
					
					let tab = $('<div class="'+options.itemCls+'"></div>');
					tab.data('index', index);
					tab.data('value', item['code']);
					tab.data('label', item['text']);
					
					if (options.icons) {
						let n = index;
						if (options.icons.length <= index)
							n = (options.icons.length % index);
						tab.append('<i class="'+options.icons[n]+' mr-5px fs-22px"></i>')
					}
					tab.append(item['text']);
					tab.addClass(options.colCls);
					
				    // 버튼 클릭 이벤트 처리
					tab.bind('click', function() {
						thisElm.find('.'+options.itemCls).each(function() {
							$(this).removeClass('active');
							$($(this).data('tab')).hide();
						});
						$(this).addClass('active');
						$($(this).data('tab')).show();
						
						if (options.select) {
							options.select($(this).data('value'), $(this).data('label'));
						}
					});
					row.append(tab);
				});
			}
		}
		else {
			$.each(options.items, function(index, item) {
				
				let tab = $('<div class="'+options.itemCls+'"></div>');
				tab.data('index', index);
				tab.data('value', item.value);
				tab.data('tab'  , item.tab);
				tab.append('<i class="'+item.icon+' mr-5px fs-22px"></i>')
				tab.append(item.label);
				
				if (item.id)
					tab.prop('id', item.id);
				if (item.cls)
					tab.addClass(item.cls);
				else
					tab.addClass(options.colCls);
				
			    // 버튼 클릭 이벤트 처리
				tab.bind('click', function() {
					thisElm.find('.'+options.itemCls).each(function() {
						$(this).removeClass('active');
						$($(this).data('tab')).hide();
					});
					$(this).addClass('active');
					$($(this).data('tab')).show();
					
					if (options.select) {
						options.select($(this).data('value'));
					}
				});
				
				row.append(tab);
			});
		}
		
		let div = $('<div></div>');
		div.addClass(options.tabCls);
		div.append(row);
		
		return div;
	};
	/* 2023.06.13 디자인변경 -> 사용안함
	function _createBox() {
		let div = $('<div></div>');
		div.addClass(options.boxCls);
		
		div.append('<div class="row"></div>');
		div.find('.row').append('<div class="col-12"></div>');
		div.find('.col-12').append(_createTabs());
		
		return div;
	};
	*/
	
	function _init() {
		
		thisElm.addClass(options.cls);
		thisElm.append(_createTabs());
		
		if (options.value) {
			thisElm.find('.'+options.itemCls).each(function() {
				if ($(this).data('value') == options.value)
					$(this).trigger('click');
			});
		}
	};
	_init();
	
	return this;
};

//===========================================================================//
// 게시판 분류탭 정의
//===========================================================================//
$.fn.appBbsTabs = function ( args ) {
	
	var options = $.extend({
		//탭목록 검색URL
		url: false,
		//탭목록 검색조건
		params: false,
		//탭목록 필터함수(해당 항목에 대한 필터링 처리)
		filter: false,
		//선택이벤트
		select: false,
		//초기값
		value: false,
		//탭목록 배열 (url이 없는 경우)
		items: false,
		//탭메뉴 CLS
		cls: "tabmenu-1",
		//탭메뉴 ul cls
		ulCls: false,
		//탭메뉴 li cls
		liCls: false,
		//감싸는 레이어가 있는 경우 해당 cls
		wrapCls: false,
		//탭메뉴 아이콘
		icon : "icon-presentation-user",
		//icon-presentation-poll
	}, args);
	
	var thisElm = $(this);
	
	let _create = function(box) {
		
		let items = false;
		if (options.url) {
			let ret = $.ajaxUtil.ajaxDefault(options.url, options.params);
			if (!ret)
				return;
			if ($.type(ret) === 'array')
				items = ret;
			else if (ret.rows) {
				if ($.type(ret.rows) === 'array' && 
					ret.rows.length > 0)
					items = ret.rows;
				else
					return;
			}
		}
		else {
			items = options.items;
		}
		let ul = $('<ul></ul>');
		if (options.ulCls)
			ul.addClass(options.ulCls);
	
		$.each(items, function(i,r) {
			
			let f = true;
			// 항목 필터링
			if (options.filter) {
				f = options.filter(r);
			}
			if (!f)
				return true;

			let ac = $('<a href="javascript:void(0)"></a>');
			ac.data('value', r['code']);
			if (r['icon'])
				ac.append('<i class="'+r['icon']+'"></i>');
			else if (options.icon)
				ac.append('<i class="'+options.icon+'"></i>');
			ac.append(r['text']);
			
			if (options.select) {
				ac.click(function() {
					box.find('li > a').each(function() {
						$(this).removeClass('active');
					});
					$(this).addClass('active');
					options.select($(this).data('value'));
				});
			}
			let li = $('<li></li>');
			if (options.liCls)
				li.addClass(options.liCls);
			li.append(ac);
			ul.append(li);
		});
		box.append(ul);
		
		if (options.value) {
			box.find('li > a').each(function() {
				if ($(this).data('value') == options.value)
					$(this).trigger('click');
			});
		}
	};
	
	if (options.wrapCls) {
		
		let box = $('<div></div>');
		box.addClass(options.cls);
		_create(box);
		
		thisElm.addClass(options.wrapCls);
		thisElm.append(box);
	}
	else {
		thisElm.addClass(options.cls);
		_create(thisElm);
	}
	return this;
};


//===========================================================================//
// 버튼탭 정의
//===========================================================================//
$.fn.appButtonTabs = function ( args ) {
	
	var options = $.extend({
		//탭목록 검색URL
		url: false,
		//탭목록 검색조건
		params: false,
		//선택이벤트
		select: false,
		//초기값
		value: false,
		//탭목록 배열 (url이 없는 경우)
		items: false,

		cls:     "row pb-24px mb-24px",
		style:   "margin:-12px;",
		colCls:  "col-6 col-md p-12px",
		cardCls: "card-box-nomar2 shadow-box-1 hover-ani-none",
	}, args);
	
	let _this = $(this);
	
	let _loadItems = function() {
		let items = false;
		if (options.url) {
			let ret = $.ajaxUtil.ajaxDefault(options.url, options.params);
			if (!ret ||
				ret.length == 0)
				return;
			items = ret;
		}
		else {
			items = options.items;
		}
		return items;
	};
		
	let _createCard = function( r ) {
		let div = $('<div></div>');
		div.data('value', r['aplyCd']);
		div.addClass(options.cardCls);
		div.addClass("app-card-box");
		div.append('<div class="row"></div>');
		div.find('.row').append('<div class="col-12"></div>');
		div.find('.row').append('<div class="col-12"></div>');
		div.find('.row').append('<div class="col-12"></div>');
		
		div.find('.col-12:eq(0)').append('<p class="txt2">'+$.commUtil.nvlInt(r['aplyCnt'])+'<em>건</em></p>');
		div.find('.col-12:eq(1)').append('<div class="lineborder"></div>');
		div.find('.col-12:eq(2)').append('<p class="txt1 border-0 m-0 p-0">'+r['aplyNm']+'</p>');
		
		div.click(function() {
			_this.find('.app-card-box').each(function() {
				$(this).removeClass('active');
			});
			$(this).addClass('active');
			if (options.select) {
				options.select($(this).data('value'));
			}
		});
		return div;
	};

	_this.addClass(options.cls);
	if (options.style)
		_this.attr('style', options.style);
		
	let _items = _loadItems();

	$.each(_items, function(i,r) {
		let col = $('<div></div>');
		col.addClass(options.colCls);
		col.append(_createCard(r));
		_this.append(col);
	});
	
	if (options.value) {
		_this.find('.app-card-box').each(function() {
			if ($(this).data('value') == options.value)
				$(this).trigger('click');
		});
	}
	return this;
};

// 업종트리 생성
//=============================================================================
$.fn.appTpbizTree = function( args ) {
	
	let options = $.extend({
		url          : getUrl('/com/common/getListTreeCode.do'),
		params       : {upCdId: CODE.INDUTY_SE.code, srchMode: MODE.SEARCH},
		formId       : 'treeForm',
		treeId       : 'appTpbizTree',
		styleOptions : {
			formCls  : 'form-box-1 border-bottom-0 mb-12px pb-0', 
			searchCls: 'form-area-box-input1',
			wrapCls  : 'form-area-box1 mt-32px',
			areaCls  : 'ele-icon-box h-auto',
			treeCls  : 'lev-tree-box p-16px',
			ulCls    : 'lev', 
			liCls    : '',
			textCls  : 'bg-yellow'
		},
		message      : '업종을 더블클릭하시면 해당 업종이 선택됩니다.',
		callback     : function() {}
	}, args);
	
	let _this   = $(this);
	let _tree   = false;
	let _params = false;
	
	function _createSearch() {
		let inp = $('<input type="text" id="p_srchText" name="srchText" placeholder="내용입력">');
		let btn = $('<button type="button" id="p_btnSearch" class="btn-combi-1 bs-l "><i class="icon-search"></i>조회</button>');
		// 검색버튼 클릭처리
		btn.click(function() {
			// 트리검색
			_loadTree({srchText: $('#p_srchText').val()});
			return false;
		});
		// 입력박스 엔터처리
		bindEnter(inp, btn);
		
		let div = $('<div></div>');
		div.addClass(options.styleOptions.searchCls);
		div.append($.domUtil.getEleIconGroup({cls:'input-btn'},[{box: inp},{dom: btn,cls:'box-btn'}]));
		return div;
	}
	
	function _createTree() {
		let tree = $('<div id="'+options.treeId+'"></div>');
		tree.addClass(options.styleOptions.treeCls);
		
		let div = $('<div></div>');
		div.addClass(options.styleOptions.wrapCls);
		div.append($('<div class="'+options.styleOptions.areaCls+'"></div>').append(tree));
		return div;
	}
	function _createMessage() {
		
		let msg = $('<p class="text-red fs-12px"></p>');
		msg.append('<i class="icon-exclamation-circle mr-5px text-red"></i>');
		msg.append(options.message);
		return msg;
	}

	function _create() {
		_this.addClass(options.styleOptions.formCls);
		_this.append(_createSearch());
		_this.append(_createTree  ());
		
		if (options.message)
			_this.after(_createMessage());
			
		_tree = $('#'+options.treeId);
		// 트리검색
		_loadTree();
	}
	
	function _loadTree( params ) {
		_tree.html('');
		_params = $.extend({}, options.params);
		if (params)
			$.extend(_params, params);
		let rows = _getLoad(_params);
		if (rows) {
			_tree.append(_createNodes(rows, 0));
			if (_params['srchText'])
				_tree.find('a').trigger('click');
		}
	}
	
	function _createNodes(rows, level) {

		let ul = $('<ul></ul>');
		ul.addClass(options.styleOptions.ulCls+(level+1));
		$.each(rows, function(i,row) {
			
			let ac = _createNode(row);

			var li = $('<li></li>');
			li.addClass(options.styleOptions.liCls);
			li.append(ac);

			if (row['children']) {
				li.append(_createNodes(row['children'], row['level']));
			}
			ul.append(li);
		});
		return ul;
	}	

	function _createNode(row) {
		let txt = row['text'];
		// 검색어가 있을 경우
		if (_params['srchText']) {
			// 검색어 강조 표시
			txt = $.commUtil.replaceAll(
				txt, 
				_params['srchText'], 
				'<b class="'+options.styleOptions.textCls+'">'+_params['srchText']+'</b>'
			);
		}
		let ac = $('<a href="javascript:void(0)"></a>');
		ac.append(txt);
		ac.data('code' , row['code' ]);
		ac.data('text' , row['text' ]);
		ac.data('level', row['level']);
		ac.data('leaf' , row['leaf' ]);
		// 하위노드가 있으면
		if (row['children']) ac.prepend('<i class="icon-folder-plus F"></i>');
		else                 ac.prepend('<i class="icon-folder"></i>');

		ac.click   (_bindClick   );
		ac.dblclick(_bindDblClick);
		
		return ac;
	}
	
	function _bindClick() {

		$(this).parent().toggleClass("active").siblings().removeClass("active");
		/*
		if ($(this).data('load'))
			return false;
		let code  = $(this).data('code' );
		let level = $(this).data('level');
		let dom   = $(this).closest('li');
		dom.append(_createNodes(code, level+1));
		$(this).data('load', true);
		*/
	}

	function _bindDblClick() {
		let code  = $(this).data('code' );
		let text  = $(this).data('text' );
		let level = $(this).data('level');
		let leaf  = $(this).data('leaf' );
		
		// 최종노드만 선택가능
		if (leaf != 'Y')
			return false;
		// 선택값 셋팅 콜백함수
		options.callback({
			code: code, 
			text: text, 
			level: level
		});
	}
	
	function _getLoad( params ) {
		return $.ajaxUtil.ajaxDefault(options.url, params);
	}
	_create();
	
	return this;
};

//===========================================================================//
// Swiper Grid 컴포넌트 정의
//===========================================================================//
$.fn.appSwiperGrid = function ( args ) {

	var thisCmp = this;
	var thisElm = $(this);
	
	var options = $.extend(true, {
		// 조회 AJAX URL
		url: false,
		// 조회 조건 객체
		params: {},
		// 조회 데이터
		data: false,
		// 페이징 크기
		limit: 4,
		// 데이터 건수
		total: 0,
		// 목록 Element
		selector: 'app-swiper-list',
		// 페이징 Element
		pager: 'app-swiper-pager',
		// 추가 스타일시트
		cls: false,
		// ID 필드명
		idField:  'sn',
		// 제목 필드명
		titleField: 'bizNm',
		// 제목 아이콘
		titleIcon : false,
		// Swiper 스타일시트
		swiperOptions: {
			name   : 'Myswiper',
			cls    : 'swiper overflow-hidden position-relative',
			wrapCls: 'swiper-wrapper',
			naviCls: 'navi-box',
			btnbox : 'btnbox',
			lstbox : 'lstbox',
			pageCls: 'swiper-slide',
			
		},
		// 버튼목록
		buttons: [
			//{value: '전체', active: true, click: function() {}}
			//{value: '2019년 12건', click: function() {}}
			//{value: '2020년  8건', click: function() {}}
			//{value: '2021년 25건', click: function() {}}
		],
		// 버튼 스타일시트
		buttonCls: 'btn-combi-4 bs-m',
		itemOptions: {
			boxCls   : "box",
			colCls   : "col-12 col-xl-6",
			iconCls  : "cycle-ver-1",
			titleCls : "tittop-1"
		},
		// 칼럼목록
		items: [
			//{colCls: 'col-6 col-xl-4', name: '', label: '', labelIcon: 'icon-pen-line cycle-ver-1', formatter}
		],
		// 로딩후 이벤트
		complete: false,
		// 행단위 추가HTML 함수
		// function(dom, json)
		appendHtml: false
	}, args);


	function _createRow(json) {
		
		let d = thisElm.find('.'+options.selector);
		
		let limit  = options.limit;
		let rows   = json.rows;
		let total  = json.rows.length;
		let pages  = Math.ceil(total / limit);
		
		options.total = total;
		
		let idx = 0;
		let end = 0;
		
		for (let p = 1; p <= pages; p++) {
			
			let row = $('<div class="row"></div>');
			
			end = p * limit;
			if (end > total)
				end = total;

			$.each(rows.slice(idx, end), function(i, r) {
				
				let col = $('<div></div>');
				col.addClass(options.itemOptions.colCls);
				col.append(_createItem(i,r));
				
				row.append(col);
			});
			idx += limit;
			
			let b = $('<div></div>');
			b.addClass(options.swiperOptions.pageCls);
			b.append(row);
			
			d.append(b);
		}
		if (options.complete)
			options.complete(total, json, thisCmp);
	}
	
	function _createItem(index, json) {
		
		let row = $('<div class="row app-item"></div>');
		
		$.each(options.items, function(i, item) {

			let ccls = item.colCls  || options.itemOptions.colCls  ;
			let icls = item.iconCls || options.itemOptions.iconCls ;

			let col = $('<div class="'+ccls+'"></div>');
			col.data('sn', json[options.idField]);
			if (item.cls)
				col.addClass(item.cls);
			
			if (item.formatHtml) {
				if ($.type(item.formatHtml) === 'function')
					col.append(item.formatHtml(json, index));
				else
					col.append(item.formatHtml);
			}
			else {
				let value = json[item.valueField];
				let label = json[item.labelField] || item.labelValue;
				let licon = $('<i class="'+icls+'"></i>');
				licon.addClass(item.labelIcon);

				if (item.formatter)
					value = item.formatter(value, json, item, i);
				
				col.append('<p></p>');
				col.append('<span></span>');
				if (!$.commUtil.empty(value))
					col.find('p').append(licon);
				col.find('p').append(label);
				col.find('span').append(value);
			}
			if (item.click)
				col.click(item.click);
				
			row.append(col);
		});
		
		let bcls = options.itemOptions.boxCls  ;
		let tcls = options.itemOptions.titleCls;
		
		// 제목영역
		let tit =$('<div class="'+tcls+'"></div>');
		tit.append(
			$.domUtil.getRow({
				items: [
					'<p class="txt1">'+json[options.titleField]+'</p>',
					options.titleIcon || ''
				]
			})
		);

		let box = $('<div class="'+bcls+'"></div>');
		box.append(tit);
		box.append(row);
		if (options.appendHtml) {
			options.appendHtml(box, json);
		}
		return box;
	};

	// 데이터 로드
	function _load( addparams ) {
		
		thisElm.find('.'+options.selector).html('');
		
		if (options.data) {
			_createRow(options.data);
			return;
		}
		
		let p = options.params;
		if (addparams)
			$.extend(p, addparams);

        $.ajaxUtil.ajaxLoad(
            options.url, 
			p,
            function(data) {
				if (data) {
					_createRow(data);
				}
				else {
					_createRow({});
				}
            }
        );
	};

	function _init() {
		
		thisElm.addClass(options.cls);
		
		thisElm.append('<div class="'+options.swiperOptions.btnbox+'"></div>');
		thisElm.append('<div class="'+options.swiperOptions.lstbox+'"></div>');
		
		if (options.buttons && options.buttons.length > 0)
			thisElm.find('.'+options.swiperOptions.btnbox).append(_createButtons(options.buttons));
		
		let swp = $('<div></div>');
		swp.addClass(options.swiperOptions.name);
		swp.addClass(options.swiperOptions.cls);

		swp.append('<div class="'+options.selector+' '+options.swiperOptions.wrapCls+'"></div>');
		swp.append('<div class="'+options.pager   +' '+options.swiperOptions.naviCls+'"></div>');
		
		thisElm.find('.'+options.swiperOptions.lstbox).append(swp);
		thisElm.find('.'+options.pager).append(_createNavigate());
		
		_load();
	};
	
	this.initialize = function() {
		//Initialize Swiper
		new Swiper(".swiper."+options.swiperOptions.name, {							
			slidesPerView:1,
			pagination: {
				el: "."+options.swiperOptions.name+" .swiper-pagination",
			},
			navigation: {
				nextEl: "."+options.swiperOptions.name+" .swiper-button-next",
				prevEl: "."+options.swiperOptions.name+" .swiper-button-prev",
			},
		});
	};
	
	function _createNavigate() {
		return $.domUtil.getRow({
			items:[
				'<div class="swiper-button-prev"><i class="icon-angle-left"></i>이전</div>',
				'<div class="swiper-button-next">다음<i class="icon-angle-right"></i></div>'
			]
		});
	};
	
	function _createButtons( buttons ) {
		
		thisElm.find('.'+options.swiperOptions.btnbox).html('');
		
		let row = $('<div class="row"></div>');
		
		$.each(buttons, function(i, b) {
			let btn = $('<button type="button"></button>');
			
			btn.data('grid', thisCmp);
			
			if (b.code)
				btn.data('code', b.code);
			
			if (b.data) {
				$.each(b.data, function(k,d) {
					btn.data(k,d);
				});
			}
			if (b.selector)
				btn.addClass(b.selector);

			if (b.cls)
				btn.addClass(b.cls);
			else
				btn.addClass(options.buttonCls);
				
			if (b.active)
				btn.addClass('active');

			if (b.icon)
				btn.append('<i class="'+b.icon+' mr-5px"></i>');

			btn.append(b.value);

			if (b.id)
				btn.prop('id', b.id);

			if (b.click)
				btn.click(b.click);

			row.append($('<div class="col"></div>').append(btn));
		});
		return row;
	};
	
	this.getCount = function() {
		return options.total;
	};
	
	this.createButtons = function( btns ) {
		thisElm.find('.'+options.swiperOptions.btnbox).html('');
		thisElm.find('.'+options.swiperOptions.btnbox).append( _createButtons(btns) );
	};
	
	this.doLoad = function( addparams ) {
		_load(addparams);
	};
	
	_init();

	return this;
};