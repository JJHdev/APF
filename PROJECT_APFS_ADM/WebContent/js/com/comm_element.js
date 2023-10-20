/**
******************************************************************************************
*** 파일명    : comm_element.js
*** 설명      : FORM ELEMENTS 사용자 정의 컴포넌트
***             $.fn.appComboBox
***             $.fn.appSelectBox
***             $.fn.appStepComboBox
***             $.fn.appTermBox
***             $.fn.appSortBox
***             $.fn.appIconBox
***             $.fn.appSquareBox
***             $.fn.appDropdownBox
***             $.fn.appSlideBox
***             $.fn.appSlideBaseBox
***             $.fn.appInputBox
***             $.fn.appLabelBox
***             $.fn.appSwitchBox
***             $.fn.appGridRadioBox
***
*** -----------------------------    Modified Log   --------------------------------------
*** ver             date                author                  description
*** --------------------------------------------------------------------------------------
*** 1.0         2021-08-05              LSH
******************************************************************************************
**/
//===========================================================================//
//스위치박스
//===========================================================================//
$.fn.appSwitchBox = function ( args ) {
	
	var options = $.extend({
		
		//ID
		id: '',
		//NAME
		name: '',
		//Style
		style: false,
		//CLS
		cls: false,
		//박스CLS
		boxCls: "check-radio-box toggle-ver",
		//제목
		label: '',
		//off시 명칭
		offLabel: false,
		//off레이블이 있을 경우 레이블의 스타일시트
		offLabelCls: 'fs-16px lh-1 pr-6px',
		//off레이블이 있을 경우 스위치박스의 추가 스타일시트
		offCls: 'd-flex align-items-center justify-content-center',
		//텍스트
		value: '',
		//선택함수
		select: false,
		//변경함수
		change: false,
	}, args);

	var thisElm = $(this);
	var thisCmp = this;
	var thisVal = false;

	if (!$.commUtil.empty(thisElm.data('value')))
		thisVal = thisElm.data('value');

	function _init() {
		
		thisElm.addClass(options.boxCls);
						
		let input = $('<input type="checkbox" />');
		input.prop('id'   , options.id);
		input.prop('name' , options.name);
		input.prop('value', options.value);

		if (options.cls  ) input.addClass(options.cls);
		if (options.style) input.prop('style', options.style);
		
		// off Label이 있는 경우
		if (options.offLabel) {
			thisElm.addClass(options.offCls);
			let off = $('<label for="'+options.id+'"></label>');
			off.addClass(options.offLabelCls);
			off.append(options.offLabel);
			thisElm.append(off);
		}
		
		let label = $('<label for="'+options.id+'"></label>');
		label.append('<span class=""><em></em></span>');
		if (options.label)
			label.append(options.label);
		
		thisElm.append(input);
		thisElm.append(label);
		
		if (options.select) {
			thisElm.find('input').click(options.select);
		}
		if (options.change) {
			thisElm.find('input').change(options.change);
		}
		
		if (thisVal) {
			thisElm.find('input').data('value', thisVal);
			thisCmp.setValue(thisVal);
		}
	};
	
	//입력값 바인딩
	this.setValue = function( value ) {
		if (value == options.value)
			thisElm.find('input').prop('checked', true);
		else
			thisElm.find('input').prop('checked', false);
	};
	//입력값 반환
	this.getValue = function() {
		return thisElm.find('input:checked').val();
	};
	
	//입력값 리셋
	this.clear = function() {
		thisElm.find('input').prop('checked', false);
	};
	//박스명칭 반환
	this.getName = function() {
		return options.name;
	};
	//입력박스 Readonly
	this.readonly = function( onoff ) {
		thisElm.find('input').prop('readonly', onoff);
	};
	//입력박스 Disabled
	this.disabled = function( onoff ) {
		thisElm.find('input').prop('disabled', onoff);
	};
	
	_init();
	
	return this;
};

//===========================================================================//
//레이블박스
//===========================================================================//
$.fn.appLabelBox = function ( args ) {
	
	var options = $.extend({
		
		//감싸는 영역
		wrap: {
			//영역CLS
			areaWrap: 'form-area-box-input',
			//박스CLS
			boxWrap: 'ele-icon-box',
			//값CLS
			valueWrap: 'value-box'
		},
		//ID
		id: false,
		//Style
		style: false,
		//CLS
		cls: false,
		//아이콘
		icon: false,
		//제목
		title: '',
		//텍스트
		value: '',
		//포맷함수
		format: false,
	}, args);
	
	var thisElm = $(this);
	var thisVal = options.value;

	function _init() {
		
		let box = $('<div></div>');
		box.addClass(options.wrap.boxWrap);
		if (options.icon) {
			box.append('<i class="'+options.wrap.icon+'"></i>');
		}
		box.append(_createBox());
		
		thisElm.addClass(options.wrap.areaWrap);
		if (options.title) {
			thisElm.append('<label>'+options.title+'</label>');
		}
		thisElm.append(box);
	};
	
	function _createBox() {
		let div = $('<div></div>');
		div.addClass(options.wrap.valueWrap);
		
		if (options.format)
			div.append(options.format(thisVal, options));
		else
			div.append(thisVal);
		
		if (options.id   ) div.prop('id', options.id);
		if (options.style) div.prop('style', options.style);
		if (options.cls  ) div.addClass(options.cls);
		
		return div;
	};
	//입력값 바인딩
	this.setValue = function( value ) {
		thisVal = value;
		thisElm.find('.'+options.valueWrap).html(thisVal);
	};
	
	//입력값 반환
	this.getValue = function() {
		return thisVal;
	};
	
	//입력값 리셋
	this.clear = function() {
		thisElm.find('.'+options.valueWrap).html(options.value);
	};
	
	_init();
	
	return this;
};

//===========================================================================//
//입력박스
//===========================================================================//
$.fn.appInputBox = function ( args ) {
	
	var options = $.extend(true, {
		
		//감싸는 영역
		wrap: {
			//영역CLS
			areaWrap: 'form-area-box',
			//박스CLS
			boxWrap: 'ele-icon-box',
			//아이콘
			icon:    false,
			prefix:  false, // text, cls
			postfix: false, // text, cls
		},
		//감싸는 영역 사용여부
		area: false,
		//INPUT ID
		id: '',
		//INPUT 명칭
		name: '',
		//INPUT 값
		value: '',
		//INPUT placeholder
		placeholder: false,
		//INPUT style
		style: false,
		//INPUT cls
		cls: false,
		//INPUT maxlength
		maxlength: false,
		
		//readonly 여부
		readonly: false,
		//disabled 여부
		disabled: false,
		//달력인 경우
		calendar: false,
		//숫자만 입력시
		number: false,
		//금액만 입력시
		money: false,

		//이벤트처리
		events: {},
		
		//콜백함수
		callback: false
	}, args);

	var thisCmp = this;
	var thisElm = $(this);
	var thisBox = false;

	//INPUT객체 생성
	this.init = function() {
		
		let box = this.createBox();
		
		if (options.area) {
			let o = options.wrap;
			thisElm.addClass(o.areaWrap);
			
			let b = $('<div class="'+o.boxWrap+'"></div>');
			if (o.icon)
				b.append('<i class="'+o.icon+'"></i>');
			b.append(box);
			
			if (o.prefix || o.postfix) {
				let d = $('<div class="row"></div>');
				if (o.prefix)
					d.append($('<div class="col wave '+o.prefix.cls+'"></div>').append(o.prefix.text));
				d.append($('<div class="col"></div>').append(b));
				if (o.postfix)
					d.append($('<div class="col wave '+o.postfix.cls+'"></div>').append(o.postfix.text));
				thisElm.append($('<div class="day"></div>').append(d));
			}
			else {
				thisElm.append(b);
			}
		}
		else {
			thisElm.append(box);
		}
		if (options.callback)
			options.callback(thisCmp, thisElm, thisBox);
	};
									
	this.createBox = function() {
		
		thisBox = $('<input type="text"/>');
		thisBox.prop('name', options.name);
		
		if (options.id         ) thisBox.prop('id'         , options.id);
		if (options.value      ) thisBox.prop('value'      , options.value);
		if (options.placeholder) thisBox.prop('placeholder', options.placeholder);
		if (options.maxlength  ) thisBox.prop('maxlength'  , options.maxlength);
		if (options.readonly   ) thisBox.prop('readonly'   , true);
		if (options.readonly   ) thisBox.addClass('readonly');
		if (options.disabled   ) thisBox.prop('disabled'   , true);
		if (options.style      ) thisBox.prop('style'      , options.style);
		if (options.cls        ) thisBox.addClass(options.cls);

		// 달력인 경우
		if (options.calendar) {
			thisBox.addClass("datepicker-input");
			// 달력설정 (comm_const.js 참고)
			thisBox.datepicker(OPTIONS.DATEPICKER);
			// 날짜입력 마스크(YYYY-MM-DD)
			thisBox.inputmask("9999-99-99");
		}
		// 숫자만 입력시
		if (options.number) {
			bindOnlyNumber(thisBox);
		}
		// 금액만 입력시
		if (options.money) {
			bindEditMoney(thisBox);
		}
		
		// 이벤트가 있으면
		if (options.events) {
			$.each(options.events, function(key, fn) {
				thisBox.bind(key, fn);
			});
		}
		return thisBox;
	};
	//입력값 바인딩
	this.setValue = function( value ) {
		thisBox.val(value);
	};
	
	//입력값 반환
	this.getValue = function() {
		return thisBox.val();
	};
	
	//입력값 리셋
	this.clear = function() {
		thisCmp.setValue(options.value);
	};
	//박스명칭 반환
	this.getName = function() {
		return options.name;
	};
	//입력박스 Readonly
	this.readonly = function( onoff ) {
		thisBox.prop('readonly', onoff);
	};
	//입력박스 Disabled
	this.disabled = function( onoff ) {
		thisBox.prop('disabled', onoff);
	};
	
	this.init();
	
	return this;
};

//===========================================================================//
//버튼그룹박스
//===========================================================================//
$.fn.appButtonGroupBox = function ( args ) {
	
	var options = $.extend({
		items: [{value:'', icon: '', label: '', cls: ''}],
		//선택이벤트
		select: false,
		//초기값
		value: false
	}, args);

	var thisCmp = this;
	var thisElm = $(this);
		
    // 버튼 클릭 이벤트 처리
	this.onClick = function() {
		thisCmp.clear();
		$(this).addClass('active');
		let value = $(this).data('value');
		options.select(value);
	};
	this.init = function() {
		thisElm.addClass("capsule-box");
		
		let row = $('<div class="row"></div>');
		$.each(options.items, function(i, item) {
			
			let b = $('<div class="app-btngroup-box"></div>');
			b.data('value', item.value);
			b.append('<i class="'+item.icon+' mr-5px"></i>')
			b.append(item.label);
			
			if (item.cls)
				b.addClass(item.cls);
			
			b.bind('click', thisCmp.onClick);
			
			row.append(b);
		});
		thisElm.append(row);
		
		if (options.value) {
			thisElm.find('.app-btngroup-box').each(function() {
				if ($(this).data('value') == options.value)
					$(this).trigger('click');
			});
		}
	};
	
	this.clear = function() {
		thisElm.find('.app-btngroup-box').removeClass('active');
	};
	
	this.getDom = function() {
		return thisElm;
	};
	
	this.init();
	
	return this;
};

//===========================================================================//
//정렬버튼박스
//===========================================================================//
$.fn.appSortBox = function ( args ) {
	
	var options = $.extend({
		inputs: {
			field: {id: '', name: '', value: ''},
			order: {id: '', name: '', value: ''},
		},
		items: [{field:'', order:'', icon: '', label: ''}],
		//선택이벤트
		select: false,
	}, args);

	var thisCmp = this;
	var thisElm = $(this);

	if (!$.commUtil.empty(thisElm.data('value')))
		options.value = thisElm.data('value');
		
    // 정렬버튼 클릭 이벤트 처리
	this.onClick = function() {
		thisCmp.clear();
		$(this).addClass('active');
		let field = $(this).data('field');
		let order = $(this).data('order');
		// 정렬값 정의
		thisElm.find('.app-sort-field-input').val(field);
		thisElm.find('.app-sort-order-input').val(order);
		options.select({
			field: field,
			order: order
		});
	};
	this.init = function() {
		
		let field = $('<input type="hidden" class="app-sort-field-input"/>');
		if (options.inputs.field.id)
			field.prop('id', options.inputs.field.id);
		field.prop('name' , options.inputs.field.name);
		field.prop('value', options.inputs.field.value);
		
		let order = $('<input type="hidden" class="app-sort-order-input"/>');
		if (options.inputs.order.id)
			order.prop('id'   , options.inputs.order.id);
		order.prop('name' , options.inputs.order.name);
		order.prop('value', options.inputs.order.value);
		
		thisElm.addClass("capsule-box");
		thisElm.append(field);
		thisElm.append(order);
		
		let row = $('<div class="row mx-0"></div>');
		$.each(options.items, function(i, item) {
			
			let b = $('<div class="btnOrder"></div>');
			b.data('field', item.field);
			b.data('order', item.order);
			b.append('<i class="'+item.icon+' mr-5px"></i>')
			b.append(item.label);
			
			if (item.cls)
				b.addClass(item.cls);
			
			b.bind('click', thisCmp.onClick);
			
			row.append(b);
		});
		thisElm.append(row);
	};
	
	this.clear = function() {
		thisElm.find('.app-sort-field-input').val('');
		thisElm.find('.app-sort-order-input').val('');
		thisElm.find('.btnOrder').removeClass('active');
	};
	
	this.getDom = function() {
		return thisElm;
	};
	
	this.init();
	
	return this;
};

//===========================================================================//
//아이콘버튼박스
//===========================================================================//
$.fn.appIconBox = function ( args ) {
	
	var options = $.extend({
		input:  false, // {id: '', name: '',value: ''},
		//선택이벤트
		select: false,
		//최초선택값
		value:  false,
		//아이콘목록
		icons:[{icon:'', label:'', code:''}]
	}, args);

	var thisCmp = this;
	var thisElm = $(this);

    // 클릭 이벤트 처리
	this.onClick = function() {
		thisElm.find('.app-icon-item').removeClass('active');
		$(this).addClass('active');
		let code = $(this).data('code');
		if (options.input) {
			thisElm.find('.app-icon-input').val(code);
		}
		options.select(code);
	};
	this.init = function() {
		
		thisElm.addClass("capsule-box");
		
		if (options.input) {
			let input = $('<input type="hidden" class="app-icon-input"/>');
			if (options.input.id)
				input.prop('id'   , options.input.id);
			input.prop('name' , options.input.name);
			input.prop('value', options.input.value);
			thisElm.append(input);
		}
		
		let row = $('<div class="row"></div>');
		$.each(options.icons, function(i, item) {
			let b = $('<div class="col app-icon-item"></div>');
			b.data('code', item.code);
			b.append('<i class="'+item.icon+' mr-5px"></i>')
			b.append(item.label);
			if (item.cls)
				b.addClass(item.cls);
			b.bind('click', thisCmp.onClick);
			
			row.append(b);
		});
		thisElm.append(row);
		
		if (options.value) {
			thisCmp.setValue(options.value);
		}
	};
	
	this.setValue = function(value) {
		thisElm.find('.app-icon-item').each(function() {
			if ($(this).data('code') == value) {
				$(this).trigger('click');
				return false;
			}
		});
	};
	
	this.clear = function() {
		if (options.value) {
			thisCmp.setValue(options.value);
		}
	};
	
	this.getDom = function() {
		return thisElm;
	};
	
	this.init();
	
	return this;
};

//===========================================================================//
//Square버튼박스
//===========================================================================//
$.fn.appSquareBox = function ( args ) {
	
	var options = $.extend({
		
		input: {id: '', name: '',value: ''},
		//선택이벤트
		select: false,
		//데이터
		rows: false,
		//데이터 타입(static, dynamic)
		type: 'dynamic',
		//데이터 검색 AJAX URL
		url: getUrl('/com/common/getComboCode.do'),
		//데이터 조건
		params: {},
		//데이터 필터함수
		filter: false,
		//초기값
		init: false, // code,text
		//항목생성함수
		createItem: false,
		//박스CLS
		boxCls: 'shapes-box',
		//아이콘 태그
		iconTag: 'em',
		//초기값
		value: false,
		//CLS
		cls: false,
	}, args);

	var thisCmp = this;
	var thisElm = $(this);

	if (!$.commUtil.empty(thisElm.data('value')))
		options.value = thisElm.data('value');
		
    // 버튼 클릭 이벤트 처리
	this.onClick = function( obj ) {
		thisCmp.clear();
		obj.addClass('text-primary');
		obj.find(options.iconTag).removeClass('bg-gray').addClass('bg-primary');
		
		let code = obj.data('code');
		// 조건값 정의
		thisElm.find('.app-iconbtn-input').val(code);
		if (options.select)
			options.select(code);
	};

	this.clear = function() {
		thisElm.find('.app-iconbtn-input').val('');
		thisElm.find('.'+options.boxCls).removeClass('text-primary');
		thisElm.find('.'+options.boxCls+' > '+options.iconTag).removeClass('bg-primary').addClass('bg-gray');
	};
	
	this.getDom = function() {
		return thisElm;
	};

	//값 셋팅
	this.setValue = function(value) {
		thisElm.find('.'+options.boxCls).each(function() {
			if ($(this).data('code') == value) {
				thisCmp.onClick($(this));
				return true;
			}
		});
	};
	
	//값 반환
	this.getValue = function() {
		return thisElm.val();
	};
	//박스명칭 반환
	this.getName = function() {
		return thisElm.prop('name');
	};
	//입력박스 Readonly
	this.readonly = function( onoff ) {
		thisElm.prop('readonly', onoff);
	};
	//입력박스 Disabled
	this.disabled = function( onoff ) {
		thisElm.prop('disabled', onoff);
	};
	
	//항목 생성
	this.createItem = function(elm, item) {
		if (options.createItem) {
			options.createItem(elm, item, thisCmp);
			return;
		}
		let b = $('<p class="'+options.boxCls+'"></p>');
		b.data('code', item.code);
		if (item.icon)
			b.append('<'+options.iconTag+' class="'+item.icon+' bg-gray"></'+options.iconTag+'>');
		else
			b.append('<'+options.iconTag+' class="square bg-gray"></'+options.iconTag+'>');
			
		b.append(item.text);
		b.bind('click', function() {
			thisCmp.onClick($(this));
		});
		let col = $('<div class="col"></div>');
		col.append(b);
		elm.append( col );
	};
	
	this.create = function(rows) {
		
		thisElm.html('');
		
		let input = $('<input type="hidden" class="app-iconbtn-input"/>');
		if (options.input.id)
			input.prop('id'   , options.input.id);
		input.prop('name' , options.input.name);
		input.prop('value', options.input.value);
		thisElm.append(input);
		
		if (options.init) {
			thisCmp.createItem(thisElm, options.init);
		}
		
		$.each(rows, function(i, item) {
			let chk = true;
			// 필터함수가 있으면
			if (options.filter) {
				chk = options.filter(item);
			}
			if (chk) {
				thisCmp.createItem(thisElm, item);
			}
		});
		if (options.value)
			thisCmp.setValue(options.value);
	};

	//데이터 AJAX 로딩
	this.load = function() {

		if (!thisElm.hasClass('row'))
			thisElm.addClass('row');
		if (options.cls)
			thisElm.addClass(options.cls);

		//static 일 경우
		if (options.type == 'static') {
			thisCmp.create(options.rows);
			return;
		}
		var p = options.params;
		$.ajax({
			url: options.url,
			dataType: 'json',
			type: 'post',
			data: p,
			traditional : true,
			success: function(data){
				if (!data)
					return;

				thisCmp.create(data);
			},
			error: function(){}
		});
	};
	
	this.load();
	
	return this;
};

//===========================================================================//
//드롭다운박스
//===========================================================================//
$.fn.appDropdownBox = function ( args ) {
	
	var options = $.extend({
		
		//제목
		title: false,
		//항목제목
		label: '',
		//첫번쨰값
		init: false, // {code:'',text:'전체'}
		//숨김박스옵션
		input: {id: '',name: '',value: ''},
		//숨김박스ID (값이 있을경우 input.id 대체)
		id: false, 
		//숨김박스NAME (값이 있을경우 input.name 대체)
		name: false, 
		//숨김박스VALUE (값이 있을경우 input.value 대체)
		value: false, 
		//선택INDEX
		selectIndex: false,
		//콤보표시방식
		//1 : 텍스트
		//2 : 코드
		//3 : 코드: 텍스트
		//4 : 텍스트 [코드]
		mode: 1,
		
		//콤보데이터 타입(static, dynamic)
		type: 'dynamic',
		
		//콤보데이터 검색 AJAX URL
		url: getUrl('/com/common/getComboCode.do'),
		
		//콤보데이터 조건 객체
		params: {},
		
		//콤보데이터 결과 배열
		rows: false,
		
		//콤보 선택이벤트
		select: false,
		
		//콤보생성 후 처리함수
		callback: false,
		
		//콤보 로딩전 이벤트
		before: false,
		
		//데이터 필터함수
		filter: false,
		
		//로딩 필터
		loadFilter: false,
		
		area: true,
		
		areaCls: "form-area-box",
		
		boxCls:  "ele-icon-box",  
		
		iconCls:  false,
		
		cls: false
	}, args);

	var thisCmp = this;
	var thisElm = $(this);
	var thisBox = false;
	

	if (!$.commUtil.empty(thisElm.data('value')))
		options.input.value = thisElm.data('value');
		
	this.init = function() {
		
		let input = $('<input type="hidden" class="app-dropdown-input"/>');
		
		if (options.id || options.input.id)
			input.prop('id', options.id    || options.input.id);
		input.prop('name'  , options.name  || options.input.name);
		input.prop('value' , options.value || options.input.value);
		
		if (options.cls)
			thisElm.addClass(options.cls);
		
		if (options.title)
			thisElm.append('<label>'+options.title+'</label>');

		if (options.area) {
			thisElm.addClass(options.areaCls);
			thisElm.append('<div class="'+options.boxCls+'"></div>');
			if (options.iconCls) {
				thisElm.find('.'+options.boxCls).append('<i class="'+options.iconCls+'"></i>');
			}
			thisElm.find('.'+options.boxCls).append(input);
			thisElm.find('.'+options.boxCls).append('<div class="dropdown"></div>');
		}
		else {
			if (options.iconCls) {
				thisElm.append('<i class="'+options.iconCls+'"></i>');
			}
			thisElm.append(input);
			thisElm.append('<div class="dropdown"></div>');
		}
		thisBox = thisElm.find('.dropdown');
		this.load();
	};		
	
	//콤보데이터 생성
	this.create = function(rows) {
		
		if (options.loadFilter) {
			rows = options.loadFilter(rows);
		}
		
		//{code, text}
		options.rows = rows;
		
		//콤보옵션 초기화
		thisBox.html('');
		
		let btn = $('<button type="button" class="dropdown-toggle"></button>');
		btn.attr('data-bs-toggle', 'dropdown');
		btn.attr('aria-expanded', 'false');
		btn.append(options.label);
		thisBox.append(btn);

		let data = [];
		if (options.init)
			data.push(options.init);
		data = data.concat(rows);
		
		let lst = $('<ul class="dropdown-menu w-100 bg-white"></ul>');
		$.each(data, function(i,o) {
			let chk = true;
			// 필터함수가 있으면
			if (options.filter) {
				chk = options.filter(o);
			}
			if (chk) {
				
				var val =(o['code'] || '');
				var txt =(o['text'] || '');
				if (options.mode == 2)
				    txt = val;
		        else if (options.mode == 3)
				    txt = val + ' : ' + txt;
		        else if (options.mode == 4)
				    txt = txt + ' ['+val+']';
				
				lst.append('<li><a class="dropdown-item" data-value="'+val+'" href="javascript:void(0)">'+txt+'</a></li>');
			}
		});
		thisBox.append(lst);
		
		thisBox.find('.dropdown-item').bind('click', function() {
			thisCmp.onClick($(this));
		});
		
		thisBox.bind('click', function() {
			$(this).parent().parent().addClass('focus');
		});

		if (options.callback)
			options.callback(thisCmp);

		// 빈값 로딩이거나 최초 로딩시 콤보 선택값이 있는 경우
		if (options.value) {
			thisCmp.setValue(options.value);
		}
		if (options.selectIndex || 
			options.selectIndex === 0)
			thisBox.find('.dropdown-item').eq(options.selectIndex).trigger('click');
	};
	
	//콤보데이터 AJAX 로딩
	this.load = function( addparams ) {
		
		//static 일 경우
		if (options.type == 'static') {
			thisCmp.create(options.rows);
			return;
		}
		
		var p = options.params;
		
		if (addparams)
			p = $.extend(p, addparams);

		if (options.before)
			options.before(p);
			
		$.ajax({
			url: options.url,
			dataType: 'json',
			type: 'post',
			data: p,
			traditional : true,
			success: function(data){
				if (!data)
					return;

				thisCmp.create(data);
			},
			error: function(){}
		});
	};
	
	//선택
	this.onClick = function( obj ) {
		thisElm.find('input.app-dropdown-input').val(obj.data('value'));
		thisBox.find('button').html(obj.text());
		thisBox.find('.dropdown-item').removeClass('active');
		obj.addClass('active');
		if (options.select)
			options.select(obj.data('value'), obj);
	};
	
	//콤보값 셋팅
	this.setValue = function(value) {
		thisBox.find('.dropdown-item').each(function() {
			if ($(this).data('value') == value) {
				thisCmp.onClick($(this));
				return true;
			}
		});
	}
	
	//콤보값 반환
	this.getValue = function() {
		return thisElm.find('input.app-dropdown-input').val();
	};
	
	//박스명칭 반환
	this.getName = function() {
		return options.name || options.input.name;
	};
	//입력박스 Readonly
	this.readonly = function( onoff ) {
		thisCmp.disabled( onoff );
	};
	//입력박스 Disabled
	this.disabled = function( onoff ) {
		if (onoff) {
			thisBox.addClass('disabled');
			thisBox.find('.dropdown-item').removeClass('active');
			thisBox.find('.dropdown-toggle').prop('disabled', true);
		}
		else {
			thisBox.removeClass('disabled');
			thisBox.find('.dropdown-toggle').prop('disabled', false);
		}
	};
	
	this.clear = function() {
		thisElm.find('input.app-dropdown-input').val(options.value || '');
		thisCmp.create(options.rows);
	};	
	
	this.getDom = function() {
		return thisElm;
	};
	
	this.init();
	
	return this;
};

//===========================================================================//
//콤보박스
//===========================================================================//
$.fn.appComboBox = function ( args ) {
	
	var options = $.extend({
		
		//빈값여부
		init: false,
		
		//최초로딩
		first: true,
		
		//자동로딩여부
		autoload: true,

		//로딩완료여부
		loaded: false,
		
		//콤보표시방식
		//1 : 텍스트
		//2 : 코드
		//3 : 코드: 텍스트
		//4 : 텍스트 [코드]
		mode: 1,
		
		//콤보데이터 타입(static, dynamic)
		type: 'dynamic',
		
		//콤보데이터 검색 AJAX URL
		url: getUrl('/com/common/getComboCode.do'),
		
		//콤보데이터 조건 객체
		params: {},
		
		//콤보데이터 결과 배열
		rows: false,
		
		//콤보 선택값
		value: false,
		
		//콤보 선택이벤트
		change: false,
		
		//콤보생성 후 처리함수
		callback: false,
		
		//콤보 로딩전 이벤트
		before: false,
		
		//데이터 필터함수
		filter: false,
		
		//로딩 필터
		loadFilter: false,
		
		// 감싸는 레이어
		wrapCls: false,
		
		// 감싸는 레이어인 경우 ID
		id: false,
		// 감싸는 레이어인 경우 NAME
		name: false,
		// 콤보박스 cls
		boxCls: false
		
	}, args);

	//현재컴포넌트
	var thisCmp = this;
	
	//현재객체
	var thisElm = false;
	//콤보객체
	var thisBox = false;
	
	if (options.wrapCls) {
		thisElm = $(this);
		thisElm.addClass(options.wrapCls);
		thisElm.append('<select></select>');
		thisBox = thisElm.find('select');
	}
	else {
		thisElm = $(this);
		thisBox = $(this);
	}
	if (options.name)
		thisBox.prop('name', options.name);
	if (options.id)
		thisBox.prop('id', options.id);
	if (options.boxCls)	
		thisBox.addClass(options.boxCls);
	
	
	if (!$.commUtil.empty(thisBox.data('value')))
		options.value = thisBox.data('value');

	//콤보값객체 가져오기
	this.getOptionRow = function(index) {
		if (!options.rows)
			return false;
		if (options.rows.length == 0)
			return false;
		if (options.rows.length < index)
			return false;
		
		return options.rows[index];
	};
	
	//콤보데이터 변경
	this.change = function(val) {
		thisBox.val(val);
		thisBox.trigger('change');
	};
	
	//콤보값 반환
	this.getValue = function() {
		return thisBox.val();
	};
	//콤보값 셋팅
	this.setValue = function(val) {
		thisBox.val(val);
		thisBox.trigger('change');
	};
	//박스명칭 반환
	this.getName = function() {
		return thisBox.prop('name');
	};
	//입력박스 Readonly
	this.readonly = function( onoff ) {
		if (onoff) {
			thisBox.find("option").not(':selected').prop('disabled',true);
		}
		else {
			thisBox.find("option").prop('disabled', false);
		}
	};
	//입력박스 Disabled
	this.disabled = function( onoff ) {
		thisBox.prop('disabled', onoff);
	};
	
	//콤보데이터 리셋
	this.clear = function() {
		thisBox.val(options.value);
		//thisBox.find("option").remove();
		//if (options.init && 
		//    options.init['text'])
		//	thisBox.append(thisCmp.createOption(options.init));
	};
	
	this.createOption = function(obj) {
		
		var val =(obj['code'] || '');
		var txt =(obj['text'] || '');
		if (options.mode == 2)
		    txt = val;
        else if (options.mode == 3)
		    txt = val + ' : ' + txt;
        else if (options.mode == 4)
		    txt = txt + ' ['+val+']';
		
		var opt = '<option '
		        + ' value="'+val+'">'
                + txt
                + '</option>';
		return opt;
	};
	
	//콤보데이터 생성
	this.create = function(rows) {
		
		if (options.loadFilter) {
			rows = options.loadFilter(rows);
		}
		
		//{code, text}
		options.rows = rows;
		
		//콤보옵션 초기화
		thisBox.html('');
		
		if (options.init && options.init['text'])
			thisBox.append(thisCmp.createOption(options.init));
		
		$.each(rows, function(i,o) {
			let chk = true;
			// 필터함수가 있으면
			if (options.filter) {
				chk = options.filter(o);
			}
			if (chk) {
				thisBox.append(thisCmp.createOption(o));
			}
		});
		
		if (options.change)
			thisBox.bind('change', options.change);

		if (options.callback)
			options.callback(thisCmp);

		// 빈값 로딩이거나 최초 로딩시 콤보 선택값이 있는 경우
		if ((options.init || options.first) && options.value) {
			thisBox.find('option').each(function(i) {
				if ($(this).prop('value') == options.value) {
					thisBox.val(options.value);
					//thisBox.trigger('change');
				}
			});
		}
	};
	
	this.addOption = function( data ) {
		thisBox.append(thisCmp.createOption(data));
	};
	
	//콤보데이터 AJAX 로딩
	this.load = function( addparams ) {
		
		//static 일 경우
		if (options.type == 'static') {
			thisCmp.create(options.rows);
			return;
		}
		
		var p = options.params;
		
		if (addparams)
			p = $.extend(p, addparams);

		if (options.before)
			options.before(p);
			
		$.ajax({
			url: options.url,
			dataType: 'json',
			type: 'post',
			data: p,
			traditional : true,
			success: function(data){
				if (!data)
					return;

				thisCmp.create(data);
				
				//빈값 로딩시엔 first 값 유지
				//if (options.init) {
				//	options.init = false;
				//}
				//else {
				//	options.first = false;
				//}
				//로딩완료여부 변경
				options.loaded = true;
			},
			error: function(){}
		});
	};
	
	if (options.autoload)
		this.load();
	
	return this;
};

//===========================================================================//
//단계콤보박스
//===========================================================================//
$.fn.appStepComboBox = function ( args ) {
	
	var options = $.extend({
		// 단계 객체ID 배열
		items: []
	}, args);
	//콤보배열
	var thisCmb = [];
	
	let len = options.items.length;
	
	$.each(options.items, function(i, item) {

		let o = {
			url:    item['url'   ],
			params: item['params'],
			init:   item['init'  ]
		};
		
		if (i < (len-1)) {
			o['change'] = function() {
				for (let n = 0 ; n < (len-1); n++) {
					thisCmb[n].clear();
				}
			};
			o['callback'] = function() {
				for (let n = 0 ; n < (len-1); n++) {
					thisCmb[n].clear();
				}
				if (item['callback'])
					item['callback']();
			};
		}
		if (i > 0) {
			o['before'] = function(param) {
				for (let n = i ; n < len; n++) {
					param[options.items[n].key] = thisCmb[n].getValue();
				}
			};
		}
		thisCmb.push($('#'+item['id']).appComboBox(o));
	});
	
	this.init = function() {
		if (thisCmb.length > 0)
			thisCmb[0].trigger('change');
	};
	
	return this;
};

//===========================================================================//
//라디오/체크박스
//===========================================================================//
$.fn.appSelectBox = function ( args ) {
	
	var options = $.extend({
		
		//폼타입 ('radio', 'checkbox')
		form: 'radio',

		//첫번째값
		init: false,
		
		//선택INDEX
		selectIndex: false,
		
		//데이터 타입(static, dynamic)
		type: 'dynamic',
		
		//데이터 검색 AJAX URL
		url: getUrl('/com/common/getComboCode.do'),
		
		//데이터 조건 객체
		params: {},
		
		//데이터 결과 배열
		rows: false,

		//데이터 값 필드명
		valueField: 'code',
		//데이터 표시 필드명
		labelField: 'text',
		
		//박스 name (필수)
		name: false,
		
		//선택값 (배열도 가능)
		value: false,
		
		// 스타일시트
		cls: false,
		
		// 영역 스타일시트
		wrapCls: "check-radio-box d-block checkFc",
		
		// 칼럼레이어 스타일시트 ("col")
		colCls: false,
		
		// 현재객체 스타일시트
		elmCls: false,

		//클릭이벤트
		click: false,
		
		//생성 후 처리함수
		callback: false,
		
		//데이터 필터함수
		filter: false,
		
		//값 구분자
		separator: false // ','
	}, args);

	//현재객체
	var thisCmp = this;
	
	//박스객체
	var thisElm = $(this);

	if (!$.commUtil.empty(thisElm.data('value')))
		options.value = thisElm.data('value');

	//데이터값객체 가져오기
	this.getRow = function(index) {
		if (!options.rows)
			return false;
		if (options.rows.length == 0)
			return false;
		if (options.rows.length < index)
			return false;
		
		return options.rows[index];
	};
	
	//선택값 반환
	this.getValue = function() {
		let v = [];		
		thisElm.find('input[name="'+options.name+'"]').each(function() {
			if ($(this).is(':checked')) {
				v.push($(this).val());
			}
		});
		if (options.type == 'radio')
			return v[0];
		return v;
	};

	//값 선택처리
	this.setValue = function(value) {
		if ($.commUtil.empty(value))
			return;
		// 값이 배열형태인 경우
		if ($.type(value) === 'array') {
			thisCmp.setValues(value);
		}
		// 구분자가 있는 경우
		else if (options.separator) {
			thisCmp.setSplitValues(value, options.separator);
		}
		// 단일선택 처리
		else {
			thisElm.find('input[name="'+options.name+'"]').each(function() {
				$(this).prop('checked', false);
				if (value == $(this).val())
					$(this).prop('checked', true);
			});
		}
	};

	//값 다중선택처리
	this.setValues = function(values) {
		thisElm.find('input[name="'+options.name+'"]').each(function() {
			if ($.inArray($(this).val(), values) >= 0)
				$(this).prop('checked', true);
		});
	};

	//값 다중선택처리
	//2023.05.22 구분자로 병합된 값을 split하여 선택처리한다.
	this.setSplitValues = function(str, sep) {
		let values = str.split(sep);
		thisElm.find('input[name="'+options.name+'"]').each(function() {
			if ($.inArray($(this).val(), values) >= 0)
				$(this).prop('checked', true);
		});
	};
	
	//값 리셋처리
	this.clear = function() {
		thisElm.find('input[name="'+options.name+'"]').each(function() {
			$(this).prop('checked', false);
		});
	};

	//박스명칭 반환
	this.getName = function() {
		return options.name;
	};
	//입력박스 Readonly
	this.readonly = function( onoff ) {
		if (onoff) {
			thisElm.find('.app-checkbox-wrap').addClass('readonly');
		}
		else {
			thisElm.find('.app-checkbox-wrap').removeClass('readonly');
		}
		thisElm.find('input[name="'+options.name+'"]').each(function() {
			if (onoff) {
				$(this).attr('onclick', 'return false');
			}
			else {
				$(this).attr('onclick', '');
			}
		});
	};
	//입력박스 Disabled
	this.disabled = function( onoff ) {
		thisElm.find('input[name="'+options.name+'"]').each(function() {
			$(this).prop('disabled', onoff);
		});
	};

	//박스생성
	this.createBox = function(index, boxOpts) {

		let value   = boxOpts[options.valueField];
		let label   = boxOpts[options.labelField];
		let tooltip = boxOpts['tooltip'];
		let click   = boxOpts['click'];

		let input = $('<input/>');
		input.prop("type" , options.form);
		input.prop("name" , options.name);
		input.prop("id"   , options.name+index);
		input.prop("value", value);
		if (options.cls)
			input.prop("class", options.cls);
		if (click)
			input.click(click);
		
		let wrap = $('<div class="app-checkbox-wrap mr-5px"></div>');
		wrap.addClass(options.wrapCls);
		wrap.append(input);
		wrap.append('<label for="'+options.name+index+'"> '+label+' </label>');
		
		if (tooltip) {
			let tip = $('<span class="toltip"></span>');
			tip.append('<em class="close icon-times-F"></em>');
			tip.append('<p>'+label+'</p>');
			tip.append('<span class="dot"></span>');
			tip.find('.dot').append(tooltip);
			tip.find('em.close').click(function() {
				$(this).closest('.app-checkbox-wrap').find('span.toltip').removeClass('on');
			});
			wrap.append(tip);
			wrap.find('label').click(function() {
				if ($(this).closest('.app-checkbox-wrap').find('span.toltip').hasClass('on'))
					$(this).closest('.app-checkbox-wrap').find('span.toltip').removeClass('on');
				else
					$(this).closest('.app-checkbox-wrap').find('span.toltip').addClass('on');
			});
			wrap.find('span.toltip').mouseover(function() {
				if (!$(this).hasClass('on'))
					$(this).addClass('on');
			}).mouseout(function() {
				$(this).removeClass('on');
			});
			wrap.find('input[type="checkbox"]').click(function() {
				let v = $(this).val();
				thisElm.find('span.toltip').each(function() {
					if ($(this).closest('.app-checkbox-wrap').find('input[type="checkbox"]').val() != v)
						$(this).removeClass('on');
				});
			});
		}
		if (options.colCls) {
			let col = $('<div></div>');
			col.addClass(options.colCls);
			col.append(wrap);
			return col;
		}
		return wrap;
	}
	
	//값 선택처리
	this.select = function(value) {
		thisCmp.setValue(value);
	}
	
	//클릭이벤트 바인딩
	this.clickBind = function(callback) {
		thisElm.find('input[name="'+options.name+'"]').bind('click', callback);
	}
	
	//데이터 생성
	this.create = function(rows) {
		//{code, text}
		options.rows = rows;
		
		//ELM 초기화
		thisElm.html('');
		if (options.elmCls) {
			thisElm.addClass(options.elmCls);
		}
		
		var idx = 0;
		
		if (options.init && 
		    options.init['text']) {
			idx++;
			thisElm.append( thisCmp.createBox(idx, options.init) );
		}
		$.each(rows, function(i,o) {
			o[options.valueField] = (o[options.valueField] || '');
			let chk = true;
			// 필터함수가 있으면
			if (options.filter) {
				chk = options.filter(o);
			}
			if (chk) {
				idx++;
				thisElm.append( thisCmp.createBox(idx, o) );
			}
		});
		
		if (options.value) {
			thisCmp.select(options.value);
		}
		if (options.click)
		    thisCmp.clickBind(options.click);

		if (options.callback)
			options.callback(thisCmp);
	};
	
	this.addOption = function( data ) {
		let idx = thisElm.find('input[type="'+options.form+'"]').length;
		thisElm.append(
			thisCmp.createBox(idx+1, data)
		);
	};
	
	//데이터 AJAX 로딩
	this.load = function( addparams ) {
		
		//static 일 경우
		if (options.type == 'static') {
			thisCmp.create(options.rows);
			return;
		}
		
		var p = options.params;
		
		if (addparams)
			p = $.extend(p, addparams);

		$.ajax({
			url: options.url,
			dataType: 'json',
			type: 'post',
			data: p,
			success: function(data){
				
				if (!data)
					return;
				thisCmp.create(data);
				
				if (options.selectIndex || 
					options.selectIndex === 0)
					thisElm.find('input[name="'+options.name+'"]').eq(options.selectIndex).trigger('click');
			},
			error: function(){}
		});
	};
	this.load();
	
	return this;
};

//===========================================================================//
//날짜기간박스
//===========================================================================//
$.fn.appTermBox = function ( args ) {
	
	var options = $.extend({

		//기본값
		init: {
			stValue: false,
			enValue: false
		},
		//기본값을 현재날짜로 설정할 경우
		initToday: false,
	
		// 시작일자 명칭
		stName: 'stdt',
		// 종료일자 명칭
		enName: 'endt',

		// 레이블
		label: false,
		// 레이블태그
		labelTag: 'p', // p, span, label
		
		//선택값 (배열도 가능)
		value: false,
		
		//생성 후 처리함수
		callback: false,
		
		buttons: [
			// 2021.12.27 기간변경
			// 오늘/1주일/1개월/3개월 -> 30일/60일/90일/1년/3년/5년
			//{cls:'app-term-btn', id:'today', code: '0D', text:'오늘'},
			//{cls:'app-term-btn', id:'week',  code: '7D', text:'1주일'},
			{cls:'app-term-btn', id:'month1', code: '1M', text:'30일'},
			{cls:'app-term-btn', id:'month2', code: '2M', text:'60일'},
			{cls:'app-term-btn', id:'month3', code: '3M', text:'90일'},
			{cls:'app-term-btn', id:'year1',  code: '1Y', text:'1년'},
			{cls:'app-term-btn', id:'year3',  code: '3Y', text:'3년'},
			{cls:'app-term-btn', id:'year5',  code: '5Y', text:'5년'}
		]
	}, args);

	//현재객체
	var thisCmp = this;
	
	//콤보객체
	var thisElm = $(this);

	//DOM 생성
	this.create = function() {
		
		//ELM 초기화
		thisElm.html('');
		
		// 레이블이 있으면
		if (options.label) {
			thisElm.append('<'+options.labelTag+'>'+options.label+'</'+options.labelTag+'>');
		}
		
		let stdt = $('<input type="text" />');
		stdt.prop('id'  , options.stName);
		stdt.prop('name', options.stName);
		let endt = $('<input type="text" />');
		endt.prop('id'  , options.enName);
		endt.prop('name', options.enName);
		
		// 기본값을 현재날짜로 설정할 경우
		if (options.initToday) {
			stdt.val( $.commUtil.toFormatDate(new Date(),'-') );
			endt.val( $.commUtil.toFormatDate(new Date(),'-') );
		}
		else if (options.init) {
			if (options.init.stValue)
				stdt.val(options.init.stValue);
			if (options.init.enValue)
				endt.val(options.init.enValue);
		}

		let stdom = $('<div class="cal-date"></div>');
		stdom.append(stdt);
		//stdom.append('<a href="javascript:void(0);"></a>');

		let endom = $('<div class="cal-date"></div>');
		endom.append(endt);
		//endom.append('<a href="javascript:void(0);"></a>');

		let dom = $('<div class="inputWrap cal"></div>');
		dom.append(stdom);
		dom.append('<div class="cal-span app-m3">~</div>');
		dom.append(endom);
		
		let btns = $('<div class="btnCheck"></div>');
		
		$.each(options.buttons, function(i,b) {
			let btn = $('<input type="radio" name="cal"/>');
			btn.prop('id'   , options.stName+b.id);
			btn.prop('name' , options.stName+'Btn');
			btn.prop('value', b.code);
			btn.data('code' , b.code);
			btn.data('text' , b.text);
			btn.data('stdt' , options.stName);
			btn.data('endt' , options.enName);
			btn.bind('click', thisCmp.clickBind);
			btn.addClass(b.cls);
			btns.append(btn);
			btns.append('<label for="'+options.stName+b.id+'">'+b.text+'</label>');
		});
		dom.append(btns);
		thisElm.append(dom);
		// 2021.10.25 EasyUI 적용
		$('#'+options.stName).datebox({});
		$('#'+options.enName).datebox({});
		// 2021.12.07 하이픈(-) 자동삽입 이벤트
		bindDateHyphen($('#'+options.stName).datebox('textbox') );
		bindDateHyphen($('#'+options.enName).datebox('textbox') );

		if (options.callback)
			options.callback(thisCmp);
	};
	
	//클릭이벤트 바인딩
	// 2021.10.25 EasyUI 적용
	this.clickBind = function() {
		let code = $(this).data('code');
		let stdt = $(this).data('stdt');
		let endt = $(this).data('endt');
		//let stval = $('#'+stdt).val();
		let stval = null;
		let enval = $('#'+endt).datebox('getValue');
		
		if (enval.length > 0)
			enval = $.commUtil.toDate(enval);
		else
			enval = new Date();
			
		if (code == '0D') {
			stval = new Date();
			enval = new Date();
		}
		else if (code == '7D') {
			stval = $.commUtil.addDays(enval, -7);
		}
		else if (code == '1M') {
			stval = $.commUtil.addDays(enval, -30);
		}
		else if (code == '2M') {
			stval = $.commUtil.addDays(enval, -60);
		}
		else if (code == '3M') {
			stval = $.commUtil.addDays(enval, -90);
		}
		else if (code == '1Y') {
			stval = $.commUtil.addYears(enval, -1);
			stval = $.commUtil.addDays(stval, 1);
		}
		else if (code == '3Y') {
			stval = $.commUtil.addYears(enval, -3);
			stval = $.commUtil.addDays(stval, 1);
		}
		else if (code == '5Y') {
			stval = $.commUtil.addYears(enval, -5);
			stval = $.commUtil.addDays(stval, 1);
		}
		//$('#'+stdt).val( $.commUtil.toFormatDate(stval,'-') );
		//$('#'+endt).val( $.commUtil.toFormatDate(enval,'-') );
		$('#'+stdt).datebox('setValue', $.commUtil.toFormatDate(stval,'-') );
		$('#'+endt).datebox('setValue', $.commUtil.toFormatDate(enval,'-') );
		
	}
	this.create();
	
	return this;
};

//===========================================================================//
//금액슬라이드박스 (단계형)
//===========================================================================//
$.fn.appSlideBox = function ( args ) {
		
	var options = $.extend({
		//초기값
		min: {code: 'MIN',text:'min'},
		max: {code: 'MAX',text:'max'},
		//숨김박스옵션
		input: {id: '',name: '',value: ''},
		//데이터 타입(static, dynamic)
		type: 'dynamic',
		//데이터 검색 AJAX URL
		url: getUrl('/com/common/getComboCode.do'),
		//데이터 조건 객체
		params: {},
		//데이터 결과 배열
		rows: false,
		//선택이벤트
		select: false,
	}, args);

	var thisCmp = this;
	var thisElm = $(this);
	
	//데이터 AJAX 로딩
	this.load = function() {
		let p = this;
		//static 일 경우
		if (options.type == 'static') {
			p.create(options.rows);
			return;
		}
		$.ajax({
			url: options.url,
			dataType: 'json',
			type: 'post',
			data: options.params,
			traditional : true,
			success: function(data){
				if (!data)
					return;
				p.create(data);
			},
			error: function(){}
		});
	};
										
	//DOM 생성
	this.create = function( data ) {

		//{code, text}
		options.rows = data;

		//ELM 초기화
		thisElm.html('');
		thisElm.addClass("zoom-in-out-box");
		thisElm.append('<div class="evenbox1"></div>');
		thisElm.append('<span class="zoom-in-out-bar"><em class="bg"></em></span>');
		thisElm.append('<input type="hidden" id="'+options.input.id+'" name="'+options.input.name+'" value="'+options.input.value+'"/>');

		let rows = [];
		// 최소값
		rows.push(options.min);
		// 데이터
		$.merge(rows, options.rows);
		if (options.max) {
			// 최대값
			rows.push(options.max);
		}
		$.each(rows, function(i,row) {
			let r = $('<span class="btns"></span>');
			r.data('code', row['code']);
			r.append('<em class="txt app-nowrap"></em>');
			r.find('em').append(row['text']);
			
			if (i == 0) {
				r.find('em').addClass('left');
			}
			else if (i == (rows.length-1)) {
				r.find('em').addClass('right');
			}
			else {
				r.find('em').addClass('center');
			}
			thisElm.find('.evenbox1').append(r);
		});
		
		let cnt = rows.length - 1;
		let per = Math.floor(100/ cnt);
		thisElm.find('.evenbox1 > .btns').each(function(n) {
			$(this).on('click', function(event) {
				if (thisElm.hasClass('disabled')) {
					event.preventDefault();
					return false;
				}
				let code = $(this).data('code');
				thisElm.find('.evenbox1 > .btns').removeClass('active');
				thisElm.find('.evenbox1 > .btns').each(function(j) {
					if (j > n) return false;
					$(this).addClass('active');
				});
				let w = 'calc('+per+'% * '+n+')';
				if      (n ==   0) w =   '0';
				else if (n == cnt) w = '100%';
				thisElm.find('.zoom-in-out-bar .bg').css('width', w);
				thisElm.find('input[name="'+options.input.name+'"]').val(code);
				if (options.select)
					options.select(code);
			});
		});
		this.clear();
	};
	
	//초기화
	this.clear = function() {
		thisElm.find('.evenbox1 > .btns:first').trigger('click');
	};
	//값 셋팅
	this.setValue = function(value) {
		thisElm.find('.evenbox1 > .btns').each(function(n) {
			let code = $(this).data('code');
			if (code == value) {
				$(this).trigger('click');
				return false;
			}
		});
	}
	//값 반환
	this.getValue = function() {
		return thisElm.find('input[name="'+options.input.name+'"]').val();
	};
	//박스명칭 반환
	this.getName = function() {
		return options.input.name;
	};
	//입력박스 Readonly
	this.readonly = function( onoff ) {
		thisCmp.disabled( onoff );
	};
	//입력박스 Disabled
	this.disabled = function( onoff ) {
		if (onoff) {
			thisElm.find('.evenbox1').addClass('disabled');
		}
		else {
			thisElm.find('.evenbox1').removeClass('disabled');
		}
	};
	
	this.load();

	return this;
};

//===========================================================================//
//금액슬라이드박스 (사용안함)
//===========================================================================//
$.fn.appSlideBaseBox = function ( args ) {
	
	var options = $.extend({
		
		min: {value:'0', id:'', name:''},
		max: {value:'0', id:'', name:''},
		
		unit: '원',

		//생성 후 처리함수
		callback: false
	}, args);

	//현재객체
	var thisCmp = this;
	var thisElm = $(this);
									
	//DOM 생성
	this.create = function() {
		
		//ELM 초기화
		thisElm.html('');
		thisElm.addClass("zoom-in-out-box");
		
		thisElm.append('<div class="zoom-in-out-bar"></div>');
		thisElm.find('.zoom-in-out-bar').append('<em class="txt left">min</em>');
		thisElm.find('.zoom-in-out-bar').append('<em class="drag-btn2"></em>');
		thisElm.find('.zoom-in-out-bar').append('<em class="bg"></em>');
		thisElm.find('.zoom-in-out-bar').append('<em class="drag-btn"><span class="drag-text fs-10px" style="position:relative;left:-20px;top:20px;word-break:keep-all;"></span></em>');
		thisElm.find('.zoom-in-out-bar').append('<em class="txt right">max</em>');
		thisElm.append('<input type="hidden" id="'+options.min.id+'" name="'+options.min.name+'" value=""/>');
		thisElm.append('<input type="hidden" id="'+options.max.id+'" name="'+options.max.name+'" value=""/>');
		
		let min = parseInt(options.min.value);
		let max = parseInt(options.max.value);
		let amt = max - min;

		thisElm.find(".drag-btn" ).draggable({
			containment:'parent',
			drag: function() {
				var drag_btn_t_val= $(this).css('left');
				$('.zoom-in-out-bar .bg').css('width',parseInt(drag_btn_t_val) + 10 + 'px');// drag-btn 반지름
				var scale_val= parseInt(drag_btn_t_val) / 110 * 100 ;
				var ratio   = Math.floor(scale_val/5) / 100;
				var value   = Math.floor(amt*ratio);
				if (value === 0) {
					$(this).find('.drag-text').html('');
					thisElm.find('input[name="'+options.min.name+'"]').val('');
					thisElm.find('input[name="'+options.max.name+'"]').val('');
					return;
				}
				$(this).find('.drag-text').html($.formatUtil.toNumber(value)+options.unit);
				thisElm.find('input[name="'+options.min.name+'"]').val(min);
				thisElm.find('input[name="'+options.max.name+'"]').val(value);
			},														
		});

		if (options.callback)
			options.callback(thisCmp);
	};
	
	this.clear = function() {
		$(this).find('.drag-text').html('');
		thisElm.find('input[name="'+options.min.name+'"]').val('');
		thisElm.find('input[name="'+options.max.name+'"]').val('');
		thisElm.find(".drag-btn").css('left', '0px');
		thisElm.find(".zoom-in-out-bar .bg").css('width', '0px');
	};
	this.create();
	
	return this;
};

//===========================================================================//
// 2023.07.26 그리드형 라디오박스
//===========================================================================//
$.fn.appGridRadioBox = function ( args ) {
	
	var options = $.extend({

		//첫번째값
		init: false,

		//선택INDEX
		selectIndex: false,
		
		//데이터 타입(static, dynamic)
		type: 'dynamic',
		
		//데이터 검색 AJAX URL
		url: getUrl('/com/common/getComboCode.do'),
		
		//데이터 조건 객체
		params: {},
		
		//데이터 결과 배열
		rows: false,
		
		//박스 name (필수)
		name: false,
		
		//선택값
		value: false,
		
		// 스타일시트
		cls: false,
		
		// 영역 스타일시트
		wrapCls: "check-radio-box",
		
		// 칼럼레이어 스타일시트 ("col")
		colCls: false,
		
		// 현재객체 스타일시트
		elmCls: false,

		//클릭이벤트
		click: false,
		
		//생성 후 처리함수
		callback: false,
		
		//데이터 필터함수
		filter: false,
		
		//값 구분자
		separator: false // ','
	}, args);

	//현재객체
	var thisCmp = this;
	
	//박스객체
	var thisElm = $(this);
	
	//고유명칭
	var thisName = self.crypto.randomUUID();

	if (!$.commUtil.empty(thisElm.data('value')))
		options.value = thisElm.data('value');

	//데이터값객체 가져오기
	this.getRow = function(index) {
		if (!options.rows)
			return false;
		if (options.rows.length == 0)
			return false;
		if (options.rows.length < index)
			return false;
		
		return options.rows[index];
	};
	
	//선택값 반환
	this.getValue = function() {
		// 히든값 반환
		return thisElm.find('input[name="'+options.name+'"]').val();
	};

	//값 선택처리
	this.setValue = function(value) {
		// 히든값 셋팅
		thisElm.find('input[name="'+options.name+'"]').val('');
		if ($.commUtil.empty(value))
			return;
		thisElm.find('input[name="'+thisName+'"]').each(function() {
			$(this).prop('checked', false);
			if (value == $(this).val()) {
				$(this).prop('checked', true);
				// 히든값 셋팅
				thisElm.find('input[name="'+options.name+'"]').val(value);
			}
				
		});
	};
	
	//값 리셋처리
	this.clear = function() {
		thisElm.find('input[name="'+thisName+'"]').each(function() {
			$(this).prop('checked', false);
		});
		// 히든값 셋팅
		thisElm.find('input[name="'+options.name+'"]').val('');
	};

	//박스명칭 반환
	this.getName = function() {
		return options.name;
	};
	//입력박스 Readonly
	this.readonly = function( onoff ) {
		if (onoff) {
			thisElm.find('.app-checkbox-wrap').addClass('readonly');
		}
		else {
			thisElm.find('.app-checkbox-wrap').removeClass('readonly');
		}
		thisElm.find('input[name="'+thisName+'"]').each(function() {
			if (onoff) {
				$(this).attr('onclick', 'return false');
			}
			else {
				$(this).attr('onclick', '');
			}
		});
	};
	//입력박스 Disabled
	this.disabled = function( onoff ) {
		thisElm.find('input[name="'+thisName+'"]').each(function() {
			$(this).prop('disabled', onoff);
		});
	};

	//박스생성
	this.createBox = function(index, boxOpts) {

		let value   = boxOpts['code'];
		let label   = boxOpts['text'];
		let tooltip = boxOpts['tooltip'];
		let click   = boxOpts['click'];

		let input = $('<input/>');
		input.prop("type" , 'radio');
		input.prop("name" , thisName);
		input.prop("id"   , thisName+index);
		input.prop("value", value);
		if (options.cls)
			input.prop("class", options.cls);
		if (click)
			input.click(click);
		
		let wrap = $('<div class="app-checkbox-wrap mr-5px"></div>');
		wrap.addClass(options.wrapCls);
		wrap.append(input);
		wrap.append('<label for="'+thisName+index+'"> '+label+' </label>');
		
		if (tooltip) {
			let tip = $('<span class="toltip"></span>');
			tip.append('<em class="close icon-times-F"></em>');
			tip.append('<p>'+label+'</p>');
			tip.append('<span class="dot"></span>');
			tip.find('.dot').append(tooltip);
			tip.find('em.close').click(function() {
				$(this).closest('.app-checkbox-wrap').find('span.toltip').removeClass('on');
			});
			wrap.append(tip);
			wrap.find('label').click(function() {
				if ($(this).closest('.app-checkbox-wrap').find('span.toltip').hasClass('on'))
					$(this).closest('.app-checkbox-wrap').find('span.toltip').removeClass('on');
				else
					$(this).closest('.app-checkbox-wrap').find('span.toltip').addClass('on');
			});
			wrap.find('span.toltip').mouseover(function() {
				if (!$(this).hasClass('on'))
					$(this).addClass('on');
			}).mouseout(function() {
				$(this).removeClass('on');
			});
			wrap.find('input[type="checkbox"]').click(function() {
				let v = $(this).val();
				thisElm.find('span.toltip').each(function() {
					if ($(this).closest('.app-checkbox-wrap').find('input[type="checkbox"]').val() != v)
						$(this).removeClass('on');
				});
			});
		}
		if (options.colCls) {
			let col = $('<div></div>');
			col.addClass(options.colCls);
			col.append(wrap);
			return col;
		}
		return wrap;
	}
	
	//값 선택처리
	this.select = function(value) {
		thisCmp.setValue(value);
	}
	
	//클릭이벤트 바인딩
	this.clickBind = function(callback) {
		thisElm.find('input[name="'+thisName+'"]').bind('click', callback);
	}
	
	//데이터 생성
	this.create = function(rows) {
		//{code, text}
		options.rows = rows;
		
		//ELM 초기화
		thisElm.html('');
		thisElm.html('<input type="hidden" name="'+options.name+'" value=""/>');
		
		if (options.elmCls) {
			thisElm.addClass(options.elmCls);
		}
		
		var idx = 0;
		
		if (options.init && 
		    options.init['text']) {
			idx++;
			thisElm.append( thisCmp.createBox(idx, options.init) );
		}
		$.each(rows, function(i,o) {
			o['code'] = (o['code'] || '');
			let chk = true;
			// 필터함수가 있으면
			if (options.filter) {
				chk = options.filter(o);
			}
			if (chk) {
				idx++;
				thisElm.append( thisCmp.createBox(idx, o) );
			}
		});
		
		if (options.value) {
			thisCmp.select(options.value);
		}
		if (options.click)
		    thisCmp.clickBind(options.click);

		thisElm.find('input[name="'+thisName+'"]').bind('click', function() {
			// 히든값 셋팅
			thisElm.find('input[name="'+options.name+'"]').val($(this).val());
		});


		if (options.callback)
			options.callback(thisCmp);
	};
	
	this.addOption = function( data ) {
		let idx = thisElm.find('input[type="radio"]').length;
		thisElm.append(
			thisCmp.createBox(idx+1, data)
		);
	};
	
	//데이터 AJAX 로딩
	this.load = function( addparams ) {
		
		//static 일 경우
		if (options.type == 'static') {
			thisCmp.create(options.rows);
			return;
		}
		
		var p = options.params;
		
		if (addparams)
			p = $.extend(p, addparams);

		$.ajax({
			url: options.url,
			dataType: 'json',
			type: 'post',
			data: p,
			success: function(data){
				
				if (!data)
					return;
				thisCmp.create(data);
				if (options.selectIndex || 
					options.selectIndex === 0)
					thisElm.find('input[name="'+thisName+'"]').eq(options.selectIndex).trigger('click');
			},
			error: function(){}
		});
	};
	this.load();
	
	return this;
};

