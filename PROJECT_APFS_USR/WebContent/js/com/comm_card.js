/**
*******************************************************************************
*** 파일명    : app_card.js
*** 설명      : 카드 정의 컴포넌트
***
***             $.fn.appCard          : 카드 컴포넌트 정의
***             $.fn.appAccordionCard : 아코디언 카드 컴포넌트 정의
***             $.fn.appDesignCard    : 디자인박스 카드 컴포넌트 정의
***
*** -----------------------------    Modified Log   ---------------------------
*** ver             date                author                  description
*** ---------------------------------------------------------------------------
*** 1.0         2023.07.02              LSH
*******************************************************************************
**/

//===========================================================================//
// 카드 컴포넌트 정의
//===========================================================================//
$.fn.appCard = function ( args ) {
	
	var options = $.extend(true, {
		// 조회 AJAX URL (url이 있을경우 검색처리)
		url: false,
		// 조회 조건 객체
		params: {},
		// 조회 데이터 (url이 없을경우 데이터사용)
		data: false,
		// 데이터 셀렉터
		selector     : 'app-card-box',
		// 추가 스타일시트
		cls          : false,
		cardBox      : "shadow-box-1 p-32px",
		boxCls       : "grid-ratio-1",
		rowCls       : "row",
		itemCls      : "app-card-item",
		itemOptions  : {
			colCls   : "col-6 col-xl-4",
			wrapCls  : "form-area-box-input",
			boxCls   : "ele-icon-box",
			valueCls : "value-box",
		},
		titleOptions : {
			title    : false,
			icon     : '<img src="'+getUrl('/images/sub/iconimg1.svg')+'"  alt="icon"/>',
			comment  : false,
			buttons  : [/* {id:'btnPrint', cls: 'bs-l btn-combi-2', icon: 'icon-print', value: '보고서 인쇄', click: function() {}} */]
		},
		// 칼럼목록
		items: [/* {colCls: 'col-6 col-xl-4', name: '', label: '', labelIcon: 'icon-pen-line cycle-ver-1', formatter} */],
		// 내용HTML
		formatHtml: false,
		// 선택이벤트
		select: false,
		// 항목의 값이 없을 경우 표시되는 문자열 (있을경우에만 표시됨)
		defaultValue: false
		
	}, args);
	
	let _data = false;
	let _this = $(this);

	function _create() {
		
		if (options.cls)
			_this.addClass(options.cls);
		
		if (options.titleOptions)
			_this.append($.domUtil.getTitleBox(options.titleOptions));

		let card = $('<div></div>');
		card.addClass(options.cardBox);
		card.append('<div class="'+options.selector+' '+options.boxCls+'"></div>');

		_this.append(card);
		
		// 데이터 로드
		let p = _this.find('.'+options.selector);
		p.html('');
		if (options.url)
			_data = $.ajaxUtil.ajaxDefault(options.url, {params: options.params})['Data'];
		else
			_data = options.data;
		
		p.append(_createRow(_data));
	};

	function _createRow(json) {
		
		if (options.formatHtml) {
			return options.formatHtml(json);
		}
		let row = $('<div></div>');
		row.addClass(options.rowCls);

		$.each(options.items, function(i, item) {
			row.append(_createItem(i, item, json));
		});
		return row;
	}
	
	function _createItem(index, item, json) {

		let wcls = item.wrapCls  || options.itemOptions.wrapCls ;
		let bcls = item.boxCls   || options.itemOptions.boxCls  ;
		let vcls = item.valueCls || options.itemOptions.valueCls;
		let ccls = item.colCls   || options.itemOptions.colCls  ;

		let col = $('<div></div>');
		col.addClass(ccls);
		col.addClass(options.itemCls); // 항목별 LOOP를 위한 CLS
		
		if (item.formatHtml) {
			if ($.type(item.formatHtml) === 'function')
				col.append(item.formatHtml(json, index));
			else
				col.append(item.formatHtml);
			return col;
		}
		
		let value = json[item.name];
		if (item.formatter)
			value = item.formatter(value, json, item, index, item.name);
		
		if ($.commUtil.empty(value) && options.defaultValue)
			value = options.defaultValue;
		
		let vbox = $('<div></div>');
		vbox.addClass(vcls);
		if (item.cls)
			vbox.addClass(item.cls);
		vbox.append(value);
		if (item.click)
			vbox.click(item.click);

		let box = $('<div class="'+wcls+'"></div>');
		box.append('<label></label>');
		box.find('label').append(item.label);
		if (item.labelIcon) {
			box.find('label').append('<i class="'+item.labelIcon+' ml-5px"></i>');
		}
		box.append('<div class="'+bcls+'"></div>');
		box.find('.'+bcls).append(vbox);
		col.append(box);
		return col;
	};

	// 카드생성
	_create();

	return this;
};

//===========================================================================//
// Accordion Card 컴포넌트 정의
//===========================================================================//
$.fn.appAccordionCard = function ( args ) {
	
	var options = $.extend(true, {
		// 조회 AJAX URL
		url: false,
		// 조회 조건 객체
		params: {},
		// 조회 데이터
		data: false,
		// 추가 스타일시트
		cls: false,
		// 기본 스타일시트
		appCls: 'accordionbox1',
		// 최초 active 여부
		active: true,
		// 칼럼 모드 
		// GRID : 그리드형태
		// HTML : 컨텐츠형태
		// FILE : 첨부파일형태
		// ROW  : 행스타일
		// COL  : 열스타일
		mode: 'ROW',
		// 열스타일인 경우 열단위 갯수
		colCnt: 4,
		// 제목
		title: false,
		// 제목 아이콘(접기기능)
		icon: '<i class="icon-angle-down-circle"></i>',
		// 제목 스타일시트
		titleCls: 'tit',
		// 박스 스타일시트
		boxCls: 'target',
		// 테이블 스타일시트
		tableCls: 'table-box-1 m-overbox',
		// 테이블 옵션
		tableOptions: {
			theadCls : "bs-1 ts-l",
			tbodyCls : "bs-1 t-t-c ts-m",
			thRowCls : "px-v-l",
			tbRowCls : "bs-1 t-t-c ts-m",
		},
		// 칼럼목록
		columns: [],//{name: '', label: ''formatter}
		// 그리드 ID
		gridId: false,
		// 그리드 옵션
		gridOptions: false,
		// 컨텐츠
		content: false,
		// 컨텐츠 스타일시트
		contentCls: 'bg-primary-t5 fs-14px p-16px round-8px',
		// 첨부파일 ID
		fileId: false,
		// 첨부파일 컴포넌트명
		fileComponent: false,
		// 첨부파일 옵션
		fileOptions: false,
	}, args);

	var thisElm  = $(this);
	
	function _createTable( json ) {
		let table = $('<table></table>');
		table.append('<caption>'+options.title+'</caption>');
		if (options.colgroup) {
			table.append('<colgroup></colgroup>');
			$.each(options.colgroup, function(i,w) {
				table.find('colgroup').append('<col width="'+w+'"/>');
			});
		}
		// 행스타일이면
		if (options.mode == 'ROW') {
			let thr = $('<tr></tr>');
			thr.addClass(options.tableOptions.thRowCls);
			let tbr = $('<tr></tr>');
			tbr.addClass(options.tableOptions.tbRowCls);

			$.each(options.columns, function(i,c) {
				thr.append(_createHead(c, json));
				tbr.append(_createItem(c, json));
			});
			let thead = $('<thead></thead>');
			thead.addClass(options.tableOptions.theadCls);
			thead.append(thr);
			let tbody = $('<tbody></tbody>');
			tbody.addClass(options.tableOptions.tbodyCls);
			tbody.append(tbr);
			table.append(thead);
			table.append(tbody);
		}
		// 열스타일이면
		else {
			let len = options.colCnt;
			let rnd = 0;
			let tbody = $('<tbody></tbody>');
			tbody.addClass(options.tableOptions.tbodyCls);

			$.each(options.columns, function(i,c) {
				if (rnd == 0) {
					tbody.append('<tr></tr>');
					tbody.find('tr:last').addClass(options.tableOptions.tbRowCls);
				}
				tbody.find('tr:last').append(_createHead(c, json));
				tbody.find('tr:last').append(_createItem(c, json));
				rnd++;
				if (rnd == len)
					rnd = 0;
			});
			if (rnd > 0 && len-rnd > 0) {
				for (let i = 0; i < (len-rnd); i++) {
					tbody.find('tr:last').append('<th></th>');
					tbody.find('tr:last').append('<td></td>');
				}
			}
			table.append(tbody);
		}
		return $('<div class="'+options.tableCls+'"></div>').append(table);
	}
	
	function _createItem(c, json) {
		var value = json[c.name] || '';
		var col = $('<td></td>');
		if (c.cls)     col.addClass(c.cls);
		if (c.width)   col.prop('width' ,c.width);
		if (c.colspan) col.prop('colspan' ,c.colspan);
		if (c.rowspan) col.prop('rowspan' ,c.rowspan);
		if (c.align)   col.addClass(c.align);
		if (c.formatter)
			value = c.formatter(value, json);
		if (c.input) {
			let o = $.extend({}, c.input, {
				name  : c.input.name || c.name, 
				value : value
			});
			// (comm_component.js 참고)
			value = $('<div class="form-area-box"></div>').append($.domUtil.getInputBox(o, 'ele-icon-box'));
		}
		col.append(value);
		return col;
	}
	
	function _createHead(c, json) {
		let th = $('<th scope="col"></th>');
		if (c.thCls)	
			th.addClass(c.thCls);
		th.append(c['label']);
		
		return th;
	}
	
	function _create(json) {
		
		let box = $('<div></div>');
		box.addClass(options.boxCls);
		
		// 그리드 형태인 경우
		if (options.mode == 'GRID') {
			box.append('<div id="'+options.gridId+'" class="app-accord-grid"></div>');
			thisElm.append(box);
			box.find('.app-accord-grid').appGrid(options.gridOptions).appGrid('init');
		}
		// 컨텐츠 형태인 경우
		else if (options.mode == 'HTML') {
			let div = $('<div></div>');
			div.addClass(options.contentCls);
			div.append(options.content);
			box.append(div);
			thisElm.append(box);
		}
		// 첨부파일 형태인 경우
		else if (options.mode == 'FILE') {
			box.append('<div id="'+options.fileId+'" class="app-accord-file"></div>');
			thisElm.append(box);
			box.find('.app-accord-file')[options.fileComponent](options.fileOptions)[options.fileComponent]('init');
		}
		else {
			box.append(_createTable(json));
			thisElm.append(box);
		}
	}

	function _init() {
		thisElm.addClass(options.appCls);
		if (options.cls)
			thisElm.addClass(options.cls);
		
		let data = {};
		if (options.data)
			data = options.data;
		else {
			// 데이터 로드
			let ret = $.ajaxUtil.ajaxDefault(options.url, options.params);
			if (ret && ret['Data'])
				data = ret['Data'];
		}
		if (options.title) {
			let tit = $('<p></p>');
			tit.addClass(options.titleCls);
			tit.append(options.title);
			tit.append(options.icon);
			tit.click(function() {
				$(this).parent().toggleClass('active');
			});
			thisElm.append(tit);
		}
		_create(data);
		
		if (options.active)
			thisElm.find('.'+options.titleCls).trigger('click');
	}
	
	this.validate = function() {
		let data = $.extend({}, options.params);
		// 첨부파일 형태인 경우
		if (options.mode == 'FILE') {
			// 첨부파일 업로드 VALIDATION
			let file = $('#'+options.fileId)[options.fileComponent]('validate', {required: true});
			if (!file)
	            return false;
			$.extend(true, data, file);
		}
		return data;
	}
	_init();
	return this;
};


//===========================================================================//
// 디자인박스카드 컴포넌트 정의
//===========================================================================//
$.fn.appDesignCard = function ( args ) {
	
	var options = $.extend(true, {
		// 조회 AJAX URL
		url: false,
		// 조회 조건 객체
		params: {},
		// 조회 데이터
		data: false,
		// 데이터 Element
		selector: 'app-design-box',
		// 추가 스타일시트
		cls: false,
		cardCls: "txt-design-1 shadow-box-1 p-24px pb-16px",
		// 제목
		titleOptions: {
			liCls  : 'top',
			boxCls : 'txtbox1',
			txtCls : 'txt1',
			icon   : false,
			title  : false,
			comment: false
		},
		itemOptions: {
			liCls    : false,
			btnCls   : 'bs-m btn-black',
			boxCls   : 'txtbox2',
			labelCls : 'txt2',
			valueCls : 'txt3',
			lcolCls  : 'col flex-b106px',
			vcolCls  : 'col',
			bcolCls  : 'col flex-grow-0 white-space-nowrap'
		},
		// 칼럼목록
		items: [
			//{name: '', label: ''}
		],
		// 항목의 값이 없을 경우 표시되는 문자열
		// (있을경우에만 표시됨)
		defaultValue: false,
		// 데이터로딩후 콜백함수
		callback: false
	}, args);

	var _this = $(this);

	function _init() {
		_this.addClass(options.cardCls);
		if (options.cls)
			_this.addClass(options.cls);
		_this.append('<ul class="'+options.selector+'"></ul>');
		_load();
	};
	// 데이터 로드
	function _load() {
		let p = _this.find('.'+options.selector);
		p.html('');
		if (options.url) {
	        $.ajaxUtil.ajaxLoad(
	            options.url, 
				options.params,
	            function(data) {
					_createRow(p, data['Data'] || {});
	            }
	        );
		}
		else
			_createRow(p, options.data || {});
	};
	
	function _getTitle() {
		let p = $('<p></p>');
		p.addClass(options.titleOptions.txtCls);
		p.append(options.titleOptions.title);
		if (options.titleOptions.comment)
			p.append($('<span></span>').append(options.titleOptions.comment));
		
		let box = $('<div></div>');
		box.addClass(options.titleOptions.boxCls);
		if (options.titleOptions.icon)
			box.append(options.titleOptions.icon);
		box.append(p);
		
		let tit = $('<li></li>');
		tit.addClass(options.titleOptions.liCls);
		tit.append(box);
		return tit;
	};
	
	function _createRow(p, json) {
		if (options.titleOptions) {
			p.append(_getTitle());
		}
		$.each(options.items, function(i, item) {
			p.append(_getItem(i,item, json));
		});
		if (options.callback)
			options.callback(_this, json);
	}
	
	function _getItem(index, item, json) {

		let licls   = item.liCls    || options.itemOptions.liCls   ;
		let boxcls  = item.boxCls   || options.itemOptions.boxCls  ;
		let valcls  = item.valueCls || options.itemOptions.valueCls;
		let lblcls  = item.labelCls || options.itemOptions.labelCls;
		let lcolcls = item.lcolCls  || options.itemOptions.lcolCls ;
		let vcolcls = item.vcolCls  || options.itemOptions.vcolCls ;
		let bcolcls = item.bcolCls  || options.itemOptions.bcolCls ;
		
		let li = $('<li></li>');
		if (licls)
			li.addClass(li);
		
		let box = $('<div></div>');
		box.addClass(boxcls);

		let row = $('<div class="row"></div>');
		row.append(
			$('<div class="'+lcolcls+'"></div>').append(
				$('<div class="'+lblcls+'"></div>').append(item.label)
			) 
		);
		
		if (item.name) {
			let value = json[item.name];
			if (item.formatter)
				value = item.formatter(value, json, item, index);
			if ($.commUtil.empty(value) && options.defaultValue)
				value = options.defaultValue;
	
			let valueDom = $('<div class="'+valcls+'"></div>').append(value);
			if (item.cls)
				valueDom.addClass(item.cls);
			if (item.click)
				valueDom.click(item.click);
			row.append(
				$('<div class="'+vcolcls+'"></div>').append( valueDom )
			);
		}
		else {
			row.append(
				$('<div class="'+vcolcls+'"></div>').append('')
			);
		}
		if (item.icon) {
			let icon = $(item.icon);
			if (item.clickIcon)
				icon.click(item.clickIcon);
			row.append(
				$('<div class="'+bcolcls+'"></div>').append( icon )
			);
		}
		else if (item.formatButton) {
			row.append(
				$('<div class="'+bcolcls+'"></div>').append( item.formatButton(json, index, item) )
			);
		}
		else if (item.button) {
			let b   = item.button;
			let btn = $('<button type="button"></button>');
			if (b.cls)
				btn.addClass(b.cls);
			else
				btn.addClass(options.itemOptions.btnCls);
				
			if (b.icon)
				btn.append('<i class="'+b.icon+' mr-5px"></i>');
			btn.append(b.value);
			if (b.id)
				btn.prop('id', b.id);
			if (b.click)
				btn.click(b.click);
			row.append(
				$('<div class="'+bcolcls+'"></div>').append( btn )
			);
		}
		box.append(row);
		li.append(box);
		
		return li;
	};
	_init();
	
	return this;
};
