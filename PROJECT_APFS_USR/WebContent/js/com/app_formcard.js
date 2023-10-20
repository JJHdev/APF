/**
*******************************************************************************
*** 파일명    : app_formcard.js
*** 설명      : 등록폼 카드생성 정의 컴포넌트
***
*** -----------------------------    Modified Log   ---------------------------
*** ver             date                author                  description
*** ---------------------------------------------------------------------------
*** 1.0         2023.03.21              LSH
*******************************************************************************
**/

$.fn.appFormCard = function(args) {
	
	let options = $.extend({
		title:     '기본정보',
		titleIcon: 'icon-lightbulb',
		titleFormatter: false,
		// 폼ID
		formId: false,
		// 폼객체
		form: false,
		// 데이터
		data: false,
		// 자동로딩여부
		autoload: false,
		// 추가 스타일시트
		cls: false,
		// 추가 스타일
		style: false,
		// 박스 CLS
		boxCls: 'box pb-0',
		// 그룹 CLS
		groupCls: 'form-box-of-input shadow-box-1 p-32px',
		// 항목 CLS
		itemCls: 'app-form-card-item',
		// 입력영역 CLS
		wrapCls: 'form-area-box',
		// 조회항목 CLS
		valueCls: 'value-box',
		// 조회항목 ID prefix
		valuePrefix: 's_',
		// 항목배열
		items: [],
		// 검증룰
		validator: false,
		// 비활성항목 배열
		disableKeys: false, // []
		// 하단추가 DOM
		bottom: false,
		// 추가검증항목 배열
		validates : false, // []
		// 저장데이터 필터함수
		saveFilter: false
	}, args);
	
	let _form      = false;
	let _grid      = {};
	let _this      = $(this);
	let _thisCmp   = this;
	let _validator = {
		rules      : {},
		messages   : {}
	};
	let _loadFilter = {};
	let _saveFilter = {};
	let _loadData   = false;
	let _loadStatic = false;
	let _formWidget = {};
	let _loadFormat = {}; // ValueBox 형 데이터의 포맷터 함수
	let _loadValues = []; // ValueBox 형 데이터의 KEY 모음
	let _inputFormatter = {};

	function _create() {
		
		_this.addClass(options.boxCls);
		if (options.cls)
			_this.addClass(options.cls);
		if (options.style)
			_this.prop('style', options.style);

		if (options.form) {
			_form = options.form;
			if (options.title)
				_this.append(_createTitle());
			_this.append(_createGroup());
		}
		else {
			_form = $('<form id="'+options.formId+'" name="'+options.formId+'" method="post"></form>');
			if (options.title)
				_form.append(_createTitle());
			_form.append(_createGroup());
			_this.append(_form);
		}
		if (options.bottom)
			_this.append(options.bottom);
		
		// 비활성항목 배열이 있는 경우
		if (options.disableKeys) {
			$.each(options.disableKeys, function(i, key) {
				let obj = _form.find('[name="'+key+'"]');
				let box = obj.closest('.'+options.itemCls);
				if (box && box.length > 0) {
					// 비활성처리
					box.addClass('disabled');
					obj.prop('disabled', true);
				}
				let wgt = _formWidget[key];
				if (wgt)
					wgt.disabled(true);
			});
		}
		
		// 추가검증항목 배열이 있는 경우
		if (options.validates) {
			_addValidate( options.validates );			
		}
		
		// 데이터가 있으면서 자동로딩인 경우
		if (options.data && 
			options.autoload) {
			_thisCmp.doLoad(false, options.data, options.data['gridData']);
		}
	};
	
	function _createTitle() {
		
		if (options.titleFormatter) {
			return options.titleFormatter(options.title);
		}
		let d = $('<div class="top border-0 pb-0"></div>');
		d.append('<p class="text-icon-3"></p>');
		d.find('p').append('<i class="'+options.titleIcon+' cycle-ver-1"></i>');
		d.find('p').append(options.title);
		return d;
	};
	
	// 검증룰 추가
	function _addValidator( key, type, label, method ) {
		
		// 비활성항목 배열이 있는 경우
		if (options.disableKeys) {
			let disable = false;
			$.each(options.disableKeys, function(i, disKey) {
				if (key == disKey) {
					disable = true;
					return false;
				}
			});
			if (disable)
				return;
		}
		if (!_validator.rules   [key]) _validator.rules   [key] = {};
		if (!_validator.messages[key]) _validator.messages[key] = {};
		_validator.rules[key][method] = true;

		if (method == 'required') {
			let bsel  = $.inArray(type, ['select','radio','checkbox']) >= 0;
			_validator.messages[key][method] = [
				$.commUtil.getPostKor(label,0),
				'필수', (bsel ? '선택' : '입력'), '사항입니다.'
			].join(' ');
		}
		else {
			let vld = VALIDATE[method];
			_validator.messages[key][method] = [
				$.commUtil.getPostKor(label,1),
				vld.name, '형식에 맞게 입력하세요.',
				'예) '+ vld.ex
			].join(' ');
		}
	}
	
	// 추가검증항목 추가
	function _addValidate( rules ) {
		$.each( rules, function(i, r) {
			let key     = r.key;
			let method  = r.method;
			let message = r.message;
			let param   = r.param || true;

			if (!_validator.rules   [key]) _validator.rules   [key] = {};
			if (!_validator.messages[key]) _validator.messages[key] = {};
			_validator.rules[key][method] = param;
			_validator.messages[key][method] = message;
		});
	}
	
	// 필터 추가
	function _addFilter( key, opts ) {
		_loadFilter[key] = function(dataObj) {
			// 항목 분리
			$.formUtil.splitName(_form, key, opts.type, dataObj);
		};
		_saveFilter[key] = function(dataObj) {
			// 항목 병합
			$.formUtil.mergeName(_form, key, opts.type, opts.count, dataObj);
		};
	}
	
	// ValueBox의 포맷터 추가
	function _addFormat( key, formatter ) {
		_loadFormat[key] = formatter;
	}
	// ValueBox의 KEY 추가
	function _addValues( key ) {
		_loadValues.push(key);
	}

	// 위젯 항목의 콜백함수
	function _lazyLoad( wgt ) {
		if (_loadData) {
			let key = wgt.getName();
			let val = _loadData[key];
			if (val) {
				wgt.readonly(false);
				wgt.setValue(val);
				if (_loadStatic)
					wgt.readonly(true);
			}
		}
	};

	function _createBox( c, column ) {
		// 필수인 경우
		if (column.must) {
			// 검증안함 옵션이 있는 경우 SKIP
			if (c.notvalid) { /* nothing */ }
			else _addValidator(c.name, c.type, column.label, 'required');
		}
		// 병합항목인 경우
		if (c.merge) {
			_addFilter(c.id, c.merge);
		}
		// 검증옵션이 있는 경우
		if (c.valid) {
			_addValidator(c.name, c.type, column.label, c.valid.method);
		}
		
		if ($.inArray(c.type, ['select','radio','checkbox']) >= 0) {
			// (comm_component.js 참고)
			let g = $.domUtil.getInputBox($.extend(c, {callback: _lazyLoad}),'ele-icon-box');
			if (c.grpcls)
				g.addClass(c.grpcls);
			_formWidget[c.name] = g;
			return g;
		}
		else if (c.type == 'button') {
			return $.domUtil.getButton(c);
		}
		else {
			if (c.type == 'hidden') {
				// (comm_component.js 참고)
				return $.domUtil.getInputBox(c);
			}
			// (comm_component.js 참고)
			let g = $.domUtil.getInputBox(c, 'ele-icon-box');
			if (c.grpcls)
				g.addClass(c.grpcls);
			if (c.valueFormatter)
				_inputFormatter[c.name] = c.valueFormatter;

			return g;
		}
	};
	
	function _createValueBox(c) {
		
		let v = $('<div></div>');
		v.addClass(options.valueCls);
		v.prop('id', options.valuePrefix+c.name);
		if (c.formatter)
			_addFormat(c.name, c.formatter);
		
		_addValues(c.name);
		
		return $('<div class="ele-icon-box"></div>').append(v);
	}
	
	function _createLabel(c) {
		
		let f = $('<div></div>');
		if (c.boxCls) f.addClass(c.boxCls);
		else          f.addClass(options.wrapCls);
		
		if (c.disabled) 
			f.addClass('disabled');
		
		if (c.label) {
			if (c.labelFormatter) {
				f.append(c.labelFormatter(c));
			}
			else {
				f.append('<label></label>');
				f.find('label').append(c.label);
				if (c.must)
					f.find('label').append('<em>*</em>');
				if (c.labelCls)
					f.find('label').addClass(c.labelCls);
				if (c.labelIcon)
					f.find('label').append('<i class="'+c.labelIcon+' ml-5px"></i>');
				if (c.labelExtra)
					f.find('label').append(c.labelExtra(c));
			}
		}
		if (c.icons) {
			$.each(c.icons, function(i,icon) {
				let ic = $('<i class="fs-14px"></i>');
				ic.addClass(icon.icon);
				// 클릭이벤트
				ic.click(icon.click);
				f.append(ic);
			});
		}
		return f;
	};
	
	function _createItem(c) {
		let col = $('<div></div>');
		col.addClass(c.cls);
		
		// 레이블박스
		let f = _createLabel(c);
		f.addClass(options.itemCls);
		
		if (c.inputHtml) {
			f.append(c.inputHtml(c));
		}
		else if (c.input) {
			if ($.type(c.input) === 'array') {
				let ir = $('<div class="row"></div>');
				$.each(c.input, function(k,inp) {
					if (inp.text) {
						let ic = $('<div class="col"></div>');
						if (inp.cls) ic.addClass(inp.cls);
						ic.append(inp.text);
						ir.append(ic);
					}
					else {
						let ic = $('<div class="col"></div>');
						if (inp.colCls)
							ic.addClass(inp.colCls);
						ic.append(_createBox(inp, c));

						ir.append(ic);
					}
				});
				let wr = $('<div></div>');
				wr.addClass(c.wrapCls || 'day');
				wr.append(ir);
				
				f.append(wr);
			}
			else {
				f.append(_createBox(c.input, c));
			}
		}
		else if (c.valueBox) {
			let v = $('<div></div>');
			v.addClass(options.valueCls);
			v.prop('id', options.valuePrefix+c.name);
			if (c.value)
				v.append(c.value);
			f.append(_createValueBox(c));
		}
		if (c.hidden) {
			if ($.type(c.hidden) === 'array') {
				$.each(c.hidden, function(k,hdd) {
					f.append(_createBox(hdd, c));
				});
			}
			else {
				f.append(_createBox(c.hidden, c));
			}
		}
		if (c.comment || c.error) {
			f.append( _createComment( c ) );
		}
		col.append(f);
		if (c.grid) {
			if (c.grid.name) {
				_grid[c.grid.name] = $('<div id="'+c.grid.id+'"></div>').appGrid(c.grid.options).appGrid('init');
				col.append(_grid[c.grid.name]);
			}
			else {
				col.append(c.grid);
			}
		}
		if (c.chart) {
			let d = $('<div class="mb-16px"></div>');
			if (c.chart.cls)
				d.addClass(c.chart.cls);
			d.append([
				'<div class="align-content-center bg-primary-t10 d-flex app-chart" style="padding: 10px;">',
				'<canvas id="'+c.chart.name+'"></canvas>',
				'</div>'
			].join(''));
			if (c.chart.comment) {
				d.append(c.chart.comment);
			}
			col.append(d);
		}
		return col;
	};
	
	function _createGroup() {
		
		let grp = $('<div></div>');
		grp.addClass(options.groupCls);
		
		$.each(options.items, function(i,r) {
			
			let row = $('<div class="row app-row-items"></div>');
			if (r.cls)
				row.addClass(r.cls);
			
			$.each(r.items, function(j,c) {
				let col = _createItem(c);
				row.append(col);
			});
			grp.append(row);
		});
		return grp;
	};
	
	function _createComment( c ) {
		let t = $('<div class="bottom-lable"><div class="row"><div class="col"></div></div></div>');
		t.append('<p class="fs-10px text-primary"></p>');
		if (c.error)
			t.find('p').addClass('app-error');
		if (c.comment) {
			if ($.type(c.comment) === 'object') {
				if (c.comment.icon)
					t.find('p').append('<i class="icon-exclamation-circle"></i>');
				if (c.comment.cls)
					t.find('p').addClass(c.comment.cls);
				t.find('p').append(c.comment.text);
			}
			else {
				t.find('p').append(c.comment);
			}
		}
		return t;
	}

	// 데이터 로드
	this.doLoad = function(isStatic, loadData, gridData, loadFilter ) {
		_loadStatic = isStatic;
		if (_loadData === false)
			_loadData = {};
		
		$.each(_inputFormatter, function(key, formatter) {
			if (loadData[key]) {
				loadData[key] = formatter(loadData[key]);
			}
		});
		// 폼값 맵핑
		$.formUtil.toForm(loadData, _this);
		// 로딩 후 필터 처리		
		$.each(_loadFilter, function(key, filter) {
			filter(loadData);
		});
		if (loadFilter)
			loadFilter(loadData);

		// 조회항목 맵핑
		$.each(_loadValues, function(i, key) {
			let dataKey   = options.valuePrefix+key;
			let dataVal   = loadData[key];
			let formatter = _loadFormat[key];
			let valueDom  = _this.find("[id='"+dataKey+"']");
			if (valueDom.length > 0) {
				if (formatter)
					valueDom.html(formatter(dataVal, loadData));
				else
					valueDom.html(dataVal);
			} 
		});
		$.extend(_loadData, loadData);
		// 폼위젯 값맵핑
		$.each(_formWidget, function(key, wgt) {
			wgt.setValue(loadData[key]);
		});
		// 변경불가 데이터인 경우
		if (isStatic) {
			$.each(loadData, function(key, value) {
				// 빈값이 아닌 경우
				if (!$.commUtil.empty(value)) {
					let obj = _form.find('[name="'+key+'"]');
					let box = obj.closest('.'+options.itemCls);
					if (box && box.length > 0) {
						if (obj.hasClass('datepicker-input')) {
							obj.unbind('focus');
						}
						// 변경불가처리
						box.addClass('disabled');
						obj.prop('readonly', true);
					}
					let wgt = _formWidget[key];
					if (wgt)
						wgt.readonly(true);
				}
			});
		}
		// 그리드 데이터 로드
		if (gridData) {
			$.each(gridData, function(key, data) {
				if (_grid[key]) {
					_grid[key].appGrid('loadRows', data);
					if (isStatic && data.length > 0) {
						_grid[key].appGrid('hideEditable');
					}
				}
			});
		}
	};
	
	this.addValidate = function( rules ) {
		_addValidate( rules );
	};
	
	// 저장데이터 검증 및 반환
	this.doValidate = function( gridValidate ) {
		let f = _form;
		// 저장전 필터 처리		
		$.each(_saveFilter, function(key, filter) {
			filter();
		});
		// 입력된 검증룰을 사용할 경우
		if (options.validator) {
			// 검증룰 정의
			f.validate(options.validator);
		}
		else {
			// 검증룰 정의
			f.validate($.extend({
		        debug: false,
		        onsubmit: false,
		        onfocusout: false,
		        invalidHandler: validateHandler,
		        errorPlacement: validatePlacement,
			}, _validator));
		}
        // VALIDATION 기능 활성화
        if (f.validate().settings)
            f.validate().settings.ignore = false;
        // FORM VALIDATION
        if (!f.valid())
            return false;
		let d = f.serializeObject();
		// 그리드 검증이 필요한 경우
		for (let key in _grid) {
			let grid = _grid[key];
			let gvld  = (gridValidate ? gridValidate[key] : false);
			let data  = grid.appGrid('getSaveObject', gvld);
			let name  = grid.appGrid('getName');
			if (data) {
				d[name] = data['saveList'];
			}
			else
				return false;
		}
		// 저장데이터 필터함수가 있는 경우
		if (options.saveFilter)
			options.saveFilter(d);
		return d;
	};
	// 데이터 저장
	this.doSave = function( saveArgs ) {
		let saveUrl      = saveArgs.url;
		let saveCallback = saveArgs.callback;
		let baseParams   = saveArgs.params;
		let gridValidate = saveArgs.gridValidate;
		
		let formData = _thisCmp.doValidate( gridValidate );
		if (!formData)
			return false;
		
		let saveData = $.extend(formData, baseParams);

        $.commMsg.confirm("저장하시겠습니까?", function() {
            // AJAX로 저장처리
            $.ajaxUtil.ajaxSave(
                saveUrl, 
                JSON.stringify(saveData),
                function(ret) {
                    $.ajaxUtil.success(ret, function() {
						$.commMsg.alert('성공적으로 저장되었습니다.', saveCallback);
                    });
                }
            );
        });
        return false;		
	};
	// 데이터 조회
	this.doSelect = function( loadArgs ) {
		let url      = loadArgs.url;  
		let params   = loadArgs.params;
		let filter   = loadArgs.filter;
		let isStatic = loadArgs.isStatic;
		const res    = $.ajaxUtil.ajaxDefault(url, params);
		if (res && res['Data']) {
			let data = res['Data'];
			// 로딩필터가 있는 경우
			if (filter) {
				filter(data);
			}
			_thisCmp.doLoad(isStatic, data);
		}
	};
	
	// 검증객체 반환		
	this.getValidator = function() {
		// 입력된 검증룰을 사용할 경우
		if (options.validator) {
			return options.validator;
		}
		else {
			return $.extend({
		        debug: false,
		        onsubmit: false,
		        onfocusout: false,
		        invalidHandler: validateHandler,
		        errorPlacement: validatePlacement,
			}, _validator);
		}
	};
	
	// 저장전 필터추가
	this.addFilter = function( key, filter ) {
		_saveFilter[key] = filter;
	};
	// 저장전 필터처리
	this.doFiltering = function() {
		$.each(_saveFilter, function(key, filter) {
			filter();
		});
	};
	
	// 항목추가
	this.addItem = function( item ) {
		let col = _createItem(item);
		_this.find('.app-row-items').append(col);
	}
	
	// 폼객체반환
	this.getForm = function() {
		return _form;
	};

	// 카드생성
	_create();

	return this;
};
