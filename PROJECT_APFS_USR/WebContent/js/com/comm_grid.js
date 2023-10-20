/**
*******************************************************************************
*** 파일명    : comm_grid.js
*** 설명      : 업무용 목록(그리드) 컨트롤
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
	
	const WIDGET = 'appGrid';
	
	const WIDGET_ELEMENTS = [
		 'appIconBox'    
		,'appSortBox'    
		,'appDropdownBox'
		,'appSquareBox'  
		,'appComboBox'   
		,'appSelectBox'  			
	];
	
	let WIDGET_INIT = true;
	
	// 목록형 실행함수
	//--------------------------------------------------
	let _fnList = {
		create: function(target) {
			
			var opts = $.data(target, WIDGET).options;
			var t = this;
			
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
				// 편집형인 경우 colgroup 항목추가
				t.addEditColgroup(target, table);
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
					// 레이블의 formatter가 있는 경우
					if (c.formatter)
						h.append(c.labelFormatter(c));
					else
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
					let h = $('<th scope="col"></th>');
					h.addClass(opts.listOptions.headthCls);
					// 레이블의 formatter가 있는 경우
					if (c.labelFormatter)
						h.append(c.labelFormatter(c));
					else
						h.append(c.label);
					// 레이블의 cls가 있는 경우
					if (c.labelCls)
						h.addClass(c.labelCls);
					table.find('thead > tr').append(h);
				});
			}
			// 편집형인 경우 +버튼 추가
			let json = $.extend({mode:MODE.INSERT}, opts.listOptions.editDefData || {});
			t.addEditHeader(target, table, json);
			
			this.clear(target, table);
			
			let div = $('<div></div>');
			div.addClass(opts.listOptions.gridCls);
			div.addClass("grid-list-items");
			if (opts.listOptions.editable) {
				div.addClass("app-noscroll");
			}
			
			// 스크롤이 true인 경우
			if (opts.listOptions.scroll) {
				div.css('max-height', opts.listOptions.scrollHeight);
				div.addClass('app-scroll');
			}
			div.append(table);
			
			return div;
		},
		clear: function(target, elm) {
			
			var opts = $.data(target, WIDGET).options;
			var tdom = $(target);
			
			elm = elm || tdom.find('.app-grid-table');
			
			//초기화
			elm.find('tbody').html('');
			if (opts._state_variables['_data_rows'] && 
				opts._state_variables['_data_rows'].length == 0 &&
			    opts.listOptions.emptyText) {
				let txt = opts.listOptions.emptyText;
				let len = opts.listOptions.columns.length;
				if (opts.listOptions.editable)
					len++;
				if (opts.title)
					txt = txt.replace(/{title}/g, opts.title);
				else
					txt = txt.replace(/{title}/g, '결과');
				
				let row = $('<tr></tr>');
				row.addClass(opts.listOptions.bodytrCls);
				row.append('<td class="app-grid-empty" colspan="'+len+'">'+txt+'</td>');
				elm.find('tbody').append(row);
			}
		},
		// 데이터 재생성
		load: function(target) {

			var opts = $.data(target, WIDGET).options;
			var tdom = $(target);
			var elm  = tdom.find('.app-grid-table');
			var t    = this;

			//초기화
			t.clear(target, elm);

			$.each(opts._state_variables['_data_rows'], function(i,o) {
				//데이터행 로드
				t.loadItem(target, elm, o, i);
			});
			
			// 편집형인 경우
			if (opts.listOptions.editable) {
				let dcnt = getCount(target);
				let mcnt = opts.listOptions.editDefCnt;
				// 데이터건수가 Default건수보다 작은 경우 행추가
				if (dcnt < mcnt) {
					for (let i = 0; i < (mcnt-dcnt); i++) {
						tdom.find('.app-grid-addbtn').trigger('click');
					}
				}
			}
			//데이터 로드후 CALLBACK
			if (opts.callback)
				opts.callback(elm, opts._state_variables['_data_rows'], opts._state_variables['_page_params']);
		},
		// 편집형 colgroup 추가
		addEditColgroup: function(target, table) {

			var opts = $.data(target, WIDGET).options;
			if (!opts.listOptions.editable)
				return;
			// 편집형인 경우
			table.find('colgroup').append('<col width="'+opts.listOptions.editBtnWidth+'"/>');
		},
		// 편집형 행추가버튼 추가
		addEditHeader: function(target, table, json) {

			var tdom = $(target);
			var opts = $.data(target, WIDGET).options;
			var t    = this;
			
			if (!opts.listOptions.editable)
				return;
			
			// 편집형인 경우 추가버튼 항목 추가
			let addbtn = $(opts.listOptions.editAddBtn);
			addbtn.addClass(opts.listOptions.editAddCls);
			addbtn.addClass('app-grid-addbtn');
			addbtn.addClass('app-pointer')
			addbtn.click(function() {
				// 최대 건수 확인
				if (getCount(target) >= opts.listOptions.editMaxCnt) {
					$.commMsg.alert('추가할 최대 건수는 '+opts.listOptions.editMaxCnt+'개 입니다.');
					return false;
				}
				let valid = true;
				// 추가전 검증함수가 있을 경우
				if (opts.listOptions.editCallback.beforeInsert) {
					valid = opts.listOptions.editCallback.beforeInsert(target, json, 0);
				}
				if (valid) {
					var elm = tdom.find('.app-grid-table');
					if (elm.find('.app-grid-empty').length > 0)
						elm.find('.app-grid-empty').closest('tr').remove();
					//데이터행 로드
					t.loadItem(target, elm, json);
				}
				// 추가후 콜백함수가 있을 경우
				if (opts.listOptions.editCallback.afterInsert) {
					opts.listOptions.editCallback.afterInsert(target, json, 0);
				}
			});
			// 2023.08.29 SCOPE 추가
			let add = $('<th scope="col" class="'+opts.listOptions.headthCls+'"></th>');
			add.append(addbtn);
			table.find('thead > tr').append(add);
		},
		// 편집형 행삭제버튼 추가
		addEditItem: function(target, row, json, rowidx) {

			var opts = $.data(target, WIDGET).options;
			var t = this;
			
			if (!opts.listOptions.editable)
				return;
			
			// 편집형인 경우 삭제버튼 항목 추가
			let delbtn = $(opts.listOptions.editDelBtn);
			delbtn.addClass(opts.listOptions.editDelCls);
			delbtn.addClass('app-grid-delbtn');
			delbtn.addClass('app-pointer')
			delbtn.data('value', json[opts.idField]);
			delbtn.click(function() {
				let valid = true;
				// 최소 건수 확인
				if (getCount(target) == opts.listOptions.editMinCnt) {
					$.commMsg.alert('최소 '+opts.listOptions.editMinCnt+'개 이상 등록되어야 합니다.');
					return false;
				}
				// 삭제전 검증함수가 있을 경우
				if (opts.listOptions.editCallback.beforeDelete) {
					valid = opts.listOptions.editCallback.beforeDelete(target, json, rowidx);
				}
				if (valid) {
					let tr = $(this).closest('tr');
					if (tr.data('mode') != MODE.INSERT)
						opts._state_variables['_delete_rows'].push($.extend({}, json, {mode:MODE.REMOVE}));
					tr.remove();
				}
				// 삭제후 검증함수가 있을 경우
				if (opts.listOptions.editCallback.afterDelete) {
					opts.listOptions.editCallback.afterDelete(target, json, rowidx);
				}
			});
			let del = $('<td class="app-c"></td>');
			del.append(delbtn);
			row.append(del);
		},
		// 데이터 항목생성
		loadItem: function(target, elm, json, rowidx) {

			var opts = $.data(target, WIDGET).options;
			var t = this;
			
			rowidx = rowidx || elm.find('tbody > tr.'+opts.listOptions.rowCls).length;

			var row = $('<tr></tr>');
			row.addClass(opts.listOptions.bodytrCls);
			row.addClass(opts.listOptions.rowCls);
			row.data('value', json[opts.idField]);
			row.data('mode' , json['mode'] || MODE.UPDATE);
			
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
				// 행포맷이 필요한 경우			
				if (c.formatRow)
					c.formatRow(row, json);
				
				var col = $('<td></td>');
				if (c.cls)     col.addClass(c.cls);
				if (c.colspan) col.prop('colspan', c.colspan);
				if (c.rowspan) col.prop('rowspan', c.rowspan);
				if (c.width)   col.prop('width',c.width);
				if (c.align)   col.addClass(c.align);
				if (c.formatter)
					value = c.formatter(value, json, rowidx, colidx, opts._state_variables['_page_params']);
				else if (c.formatterObject)
					value = c.formatterObject({
						column: c,						
						value : value, 
						json  : json, 
						rowidx: rowidx, 
						colidx: colidx, 
						page  : opts._state_variables['_page_params'],
					});

				
				// 열병합칼럼이 있는 경우
				if (opts.listOptions.mergeColumn &&
					opts.listOptions.mergeColumn == c.name) {
					col.addClass(opts.listOptions.mergeCls);
					var mgo = opts._state_variables['_merge_column'];
					if (mgo.value != json[c.name]) {
						if (mgo.count > 1) {
							// 이전 열에 대해 rowspan을 추가한다.
							elm.find('tbody > tr.'+opts.listOptions.rowCls).eq(mgo.index).find('td.'+opts.listOptions.mergeCls).prop('rowspan', mgo.count);
						}
						mgo['count'] = 1;
						mgo['index'] = rowidx;
						mgo['value'] = json[c.name];
					}
					else {
						mgo['count']++;
						return true;
					}
				}
				if (c.input && opts.listOptions.editable ) {
					
					if ($.type(c.input) === 'array') {
						let ir = $('<div class="row"></div>');

						$.each(c.input, function(k,inp) {
							if (inp.text) {
								let ic = $('<div class="col"></div>');
								if (inp.colCls) 
									ic.addClass(inp.colCls);
								ic.append(inp.text);
								ir.append(ic);
							}
							else {
								let ic = $('<div class="col"></div>');
								ic.addClass('form-area-box');
								if (inp.colCls)
									ic.addClass(inp.colCls);

								if ($.inArray(inp.type, ['radio','checkbox','gridradio']) >= 0) {
									ic.append($.domUtil.getInputBox($.extend({},inp,{value:json[inp.name]})));
								}
								else {
									ic.append($.domUtil.getInputBox($.extend({},inp,{value:json[inp.name]}), opts.listOptions.editBoxCls));
								}
								if (c.unit) {
									ic.addClass('d-flex');
									ic.append($('<em class="'+opts.listOptions.editUnitCls+'"></em>').append(c.unit));
								}
								ir.append(ic);
							}
						});
						let wr = $('<div></div>');
						wr.addClass('day');
						wr.append(ir);
						col.append(wr);
					}
					else {
						let o = $.extend({}, c.input, {
							name  : c.input.name || c.name, 
							value : value
						});
						// (comm_component.js 참고)
						value = $('<div class="form-area-box"></div>');
						
						if ($.inArray(o.type, ['radio','checkbox','gridradio']) >= 0) {
							value.append($.domUtil.getInputBox(o));
						}
						else {
							value.append($.domUtil.getInputBox(o, opts.listOptions.editBoxCls));
						}
						if (c.unit) {
							value.addClass('d-flex');
							value.append($('<em class="'+opts.listOptions.editUnitCls+'"></em>').append(c.unit));
						}
						col.append(value);
					}
					if (c.hidden) {
						if ($.type(c.hidden) === 'array') {
							$.each(c.hidden, function(l,hdn) {
								col.append($.domUtil.getInputBox($.extend({}, hdn, {value: json[hdn.name] || ''})));
							});
						}
						else {
							col.append($.domUtil.getInputBox($.extend({}, c.hidden, {value: json[c.hidden.name] || ''})));
						}
					}
				}
				else {
					col.append(value);
				}
				// 열포맷이 필요한 경우			
				if (c.formatCell)
					c.formatCell(col, json[c.name], json);

				row.append(col);
			});

			// 편집형인 경우 삭제버튼 추가
			t.addEditItem(target, row, json, rowidx);
			
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
			elm.find('tbody').append(row);
			//행단위 확장이벤트가 있는 경우
			if (opts.listOptions.extendRow) {
				let add = opts.listOptions.extendRow(json, rowidx);
				add.data('value', json[opts.idField]);
				elm.find('tbody').append(add);
			}
		},
		// 페이징객체 반환
		getPagination: function(target) {
			var opts = $.data(target, WIDGET).options;
			return $.extend({}, opts.pagination);
		}
	};

	// 카드형 실행함수
	//--------------------------------------------------
	let _fnCard = {
		create: function(target) {

			var opts = $.data(target, WIDGET).options;
			
			return $('<div class="'+opts.cardOptions.cardCls+' grid-card-items"></div>');
		},
		clear: function(target, elm) {
			
			var opts = $.data(target, WIDGET).options;

			var tdom = $(target);
			elm = elm || tdom.find('.grid-card-items');
			//초기화
			elm.html('');
			if (opts._state_variables['_data_rows'] && 
				opts._state_variables['_data_rows'].length == 0 &&
			    opts.cardOptions.emptyText) {
				let txt = opts.cardOptions.emptyText;
				txt = txt.replace(/{title}/g, opts.title);
				
				let box = $('<div class="app-empty-box shadow-box-1 pb-56px py-40px text-center"></div>');
				box.append('<div class="app-imgarea"><img src="'+getUrl('/images/sub/searchicon.png')+'" alt="검색결과없음"/></div>');
				box.append(txt);
				elm.append(box);
			}
		},
		// 데이터 재생성
		load: function(target) {

			var opts = $.data(target, WIDGET).options;
			var tdom = $(target);
			var elm  = tdom.find('.grid-card-items');
			var t    = this;

			//초기화
			t.clear(target, elm);
			if (opts._state_variables['_data_rows'] && opts._state_variables['_data_rows'].length > 0)
				elm.append('<div class="row grid-card-rows"></div>');

			$.each(opts._state_variables['_data_rows'], function(i,o) {
				//데이터행 로드
				t.loadItem(target, elm.find('.grid-card-rows'), o, i);
			});
			//데이터 로드후 CALLBACK
			if (opts.callback)
				opts.callback(elm, opts._state_variables['_data_rows'], opts._state_variables['_page_params']);
		},
		// 데이터 항목생성
		loadItem: function(target, elm, json, rowidx) {

			var opts = $.data(target, WIDGET).options;

			var row = $('<div class="'+opts.cardOptions.boxCls+'"></div>'); // "card-box-slide"
			row.data('value', json[opts.idField]);
			row.data('row', json);

			opts.cardOptions.formatter(row, json, rowidx, opts.columns);

			// 카드선택 이벤트
			if (opts.cardOptions.select) {
				row.bind('click', function() {
					opts.cardOptions.select(json, rowidx);
				});
				row.addClass('app-pointer');
			}
			var line = $('<div class="'+opts.cardOptions.lineCls+'"></div>'); // "col-6 col-xl-4"
			line.append(row);
			elm.append(line);
		},
		// 페이징객체 반환
		getPagination: function(target) {
			var opts = $.data(target, WIDGET).options;
			return $.extend({}, opts.pagination, {
				display : opts.cardOptions['display']
			});
		}
	};
					
	// 단일카드형 실행함수
	//--------------------------------------------------
	let _fnSingle = {
		create: function(target) {

			var opts = $.data(target, WIDGET).options;
			
			return $('<div class="'+opts.singleOptions.cardCls+' grid-card-items"></div>');
		},
		clear: function(target, elm) {
			
			var opts = $.data(target, WIDGET).options;
			var tdom = $(target);
			elm = elm || tdom.find('.grid-card-items');
			//초기화
			elm.html('');
			if (opts._state_variables['_data_rows'] && 
				opts._state_variables['_data_rows'].length == 0 &&
			    opts.singleOptions.emptyText) {
				let txt = opts.singleOptions.emptyText;
				txt = txt.replace(/{title}/g, opts.title);
				
				let box = $('<div class="app-empty-box pb-56px py-40px text-center"></div>');
				box.append('<div class="app-imgarea"><img src="'+getUrl('/images/sub/searchicon.png')+'" alt="검색결과없음"/></div>');
				box.append(txt);
				elm.append(box);
			}
		},
		// 데이터 재생성
		load: function(target) {

			var opts = $.data(target, WIDGET).options;
			var tdom = $(target);
			var elm  = tdom.find('.grid-card-items');
			var t    = this;
			var rows = opts._state_variables['_data_rows'];

			//초기화
			t.clear(target, elm);
			
			if (rows && rows.length > 0) {
				elm.append('<div class="row grid-card-rows"></div>');
	
				$.each(rows, function(i,o) {
					//데이터행 로드
					t.loadItem(target, elm.find('.grid-card-rows'), o, i);
				});
			}
			//데이터 로드후 CALLBACK
			if (opts.callback)
				opts.callback(elm, rows, opts._state_variables['_page_params']);
		},
		// 데이터 항목생성
		loadItem: function(target, elm, json, rowidx) {

			var opts = $.data(target, WIDGET).options;

			var row = $('<div class="'+opts.singleOptions.boxCls+'"></div>'); // "card-box-slide"
			row.data('value', json[opts.idField]);
			row.data('row', json);

			opts.singleOptions.formatter(row, json, rowidx, opts.columns);

			// 카드선택 이벤트
			if (opts.singleOptions.select) {
				row.bind('click', function() {
					opts.singleOptions.select(json, rowidx);
				});
				row.addClass('app-pointer');
			}
			var line = $('<div class="'+opts.singleOptions.lineCls+'"></div>'); // "col-6 col-xl-4"
			line.append(row);
			elm.append(line);
		},
		// 페이징객체 반환
		getPagination: function(target) {
			var opts = $.data(target, WIDGET).options;
			return $.extend({}, opts.pagination, {
				display : opts.singleOptions['display']
			});
		}
	};

	//----------------------------------------------------------------------//
	// 객체 단위 실행 함수
	//----------------------------------------------------------------------//
	// mode타입별 객체 생성
	function _createObject(target) {
		
		var opts = $.data(target, WIDGET).options;
		
		switch (opts._state_variables['_current_mode']) {
			case 'CARD':
				return _fnCard.create(target);
			case 'SINGLE':
				return _fnSingle.create(target);
			default:
				return _fnList.create(target);
		}
	};
	// mode타입별 객체 초기화
	function _clearObject(target) {

		var opts = $.data(target, WIDGET).options;
		
		switch (opts._state_variables['_current_mode']) {
			case 'CARD':
				_fnCard.clear(target);
				break;
			case 'SINGLE':
				_fnSingle.clear(target);
				break;
			default:
				_fnList.clear(target);
				break;
		}
	};
	// mode타입별 객체 데이터 로드
	function _loadObject(target) {

		var opts = $.data(target, WIDGET).options;
		
		switch (opts._state_variables['_current_mode']) {
			case 'CARD':
				_fnCard.load(target);
				break;
			case 'SINGLE':
				_fnSingle.load(target);
				break;
			default:
				_fnList.load(target);
				break;
		}
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
				return _fnList.getPagination(target);
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
		opts._state_variables['_current_mode' ] = opts.mode;
		opts._state_variables['_page_params'  ] = _getPagination(target);

		// 그리드영역 생성
		create(target);
		
		if (WIDGET_INIT) {
			// 그리드생성 후 처리함수
			if (opts.createCallback)
				opts.createCallback(target);
			WIDGET_INIT = false;
		}
		return this;
	};
	
	// 그리드영역 생성
	function create( target, mode ) {

		var opts = $.data(target, WIDGET).options;

		if (mode) {
			opts._state_variables['_current_mode'] = mode;
		}
		var opts = $.data(target, WIDGET).options;
		var tdom = $(target);
		
		tdom.html('');

		if (opts.cls)
			tdom.addClass(opts.cls);

		if (opts._state_variables['_current_mode'] == 'CARD')
			tdom.parent().removeClass(opts.changeCls);
		else
			tdom.parent().addClass(opts.changeCls);

		// 타입별 객체 생성
		tdom.append(_createObject(target));
		// 하단메시지가 있는 경우
		if (opts.footer) {
			tdom.append(createFooter(target));
		}
		if (!mode && opts.toolbar)
			createToolbar(target);
		if (!mode && opts.extrbar)
			createExtrbar(target);
		
		if (opts.result)
			createResult(target, true);

		if (opts.autoload)
			load(target, false, !mode);
		else {
			if (opts._state_variables['_current_mode'] == 'LIST') {
				// 편집형인 경우
				if (opts.listOptions.editable) {
					let dcnt = getCount(target);
					let mcnt = opts.listOptions.editDefCnt;
					// 데이터건수가 Default건수보다 작은 경우 행추가
					if (dcnt < mcnt) {
						for (let i = 0; i < (mcnt-dcnt); i++) {
							tdom.find('.app-grid-addbtn').trigger('click');
						}
					}
				}
			}
		}
	};
	
	// 하단설명글 추가
	function createFooter( target ) {
		var opts = $.data(target, WIDGET).options;
		// 설명항목
		let dom = $('<div class="bottom-lable"></div>');
		dom.append('<div class="row"></div>');
		dom.find('.row').append('<div class="col"></div>');
		dom.find('.col').append(opts.footer);
		return dom;
	};
	
	// 모드변경처리
	function changeMode( target, mode ) {
		var opts = $.data(target, WIDGET).options;
		if (mode != opts._state_variables['_current_mode'])
			create( target, mode );
	};

	// 그리드툴바 생성 (별도의 영역)
	function createToolbar( target ) {
		var tdom = $(target);
		var opts = $.data(target, WIDGET).options;
		if (!opts.toolbar)
			return;
		
		opts._state_variables['_toolbar_boxes'] = {};
		
		let row = $('<div class="row"></div>');
		if (opts.toolbarOptions.rowCls)
			row.addClass(opts.toolbarOptions.rowCls);
		
		$.each(opts.toolbarOptions.items, function(i,item){
			let col = $('<div></div>');
			if (item.cls) {
				if (item.cls.indexOf('col') != 0)
					col.addClass('col');
				col.addClass(item.cls);
			}
			else
				col.addClass("col");
			
			if (item.format) {
				col.append(item.format(target, item));
			}
			else {
				let d = $('<div></div>');
				//comm_element.js 의 element 생성
				$.each(WIDGET_ELEMENTS, function(i, widget) {
					if (item.key &&
						item.type == widget) {
						let o = item.options;
						if (item.value) {
							$.extend(o, {value: item.value});
						}
						opts._state_variables['_toolbar_boxes'][item.key] = d[widget](o);
						return false;
					} 
				});
				col.append(d);
			}
			row.append(col);
		});
		// design-txt-1
		let div = $('<div class="'+opts.toolbarOptions.boxCls+'"></div>');
		div.append(row);
		
		if ($(opts.toolbar).length > 0) {
			$(opts.toolbar).html('');
			$(opts.toolbar).addClass(opts.toolbarOptions.cls);
			$(opts.toolbar).append(div);
		}
		else {
			let tbar = $('<div id="'+opts.toolbar.substring(1)+'"></div>');
			tbar.addClass(opts.toolbarOptions.cls);
			tbar.append(div);
			tdom.prepend(tbar);
		}
	};
	
	// 그리드추가영역 생성 (별도의 영역)
	function createExtrbar( target ) {
		
		var opts = $.data(target, WIDGET).options;
		
		if (!opts.extrbar)
			return;
		
		opts._state_variables['_extrbar_boxes'] = {};
		
		let _createColumn = function(item) {
			
			let col = $('<div class="col"></div>');
			if (item.cls)
				col.addClass(item.cls);
			else
				col.addClass("col");
			
			if (item.items) {
				$.each(item.items, function(k,subrow) {
					
					let r = $('<div class="row"></div>');
					if (subrow.cls)
						r.addClass(subrow.cls);
					
					if (subrow.items) {
						$.each(subrow.items, function(j,subitem) {
							r.append(_createColumn(subitem));
						});
					}
					else {
						//comm_element.js 의 element 생성
						$.each(WIDGET_ELEMENTS, function(i, widget) {
							if (subrow.key &&
								subrow.type == widget) {
								let o = subrow.options;
								if (subrow.value) {
									$.extend(o, {value: subrow.value});
								}
								opts._state_variables['_extrbar_boxes'][subrow.key] = r[widget](o);
								return false;
							} 
						});
					}
					col.append(r);
				});
			}
			else {
				if (item.format) {
					col.append(item.format(target, item));
				}
				else {
					let d = $('<div></div>');
					//comm_element.js 의 element 생성
					$.each(WIDGET_ELEMENTS, function(i, widget) {
						if (item.key &&
							item.type == widget) {
							let o = item.options;
							if (item.value) {
								$.extend(o, {value: item.value});
							}
							opts._state_variables['_extrbar_boxes'][item.key] = d[widget](o);
							return false;
						} 
					});
					col.append(d);
				}
			}
			return col;
		};
		
		let row = $('<div class="row"></div>');
		if (opts.extrbarOptions.rowCls)
			row.addClass(opts.extrbarOptions.rowCls);
		
		$.each(opts.extrbarOptions.items, function(i,item) {
			row.append(_createColumn(item));
		});
		$(opts.extrbar).html('');
		$(opts.extrbar).addClass(opts.extrbarOptions.cls);
		$(opts.extrbar).append(row);
	};

	// toolbar/extrbar 에 정의된 사용자정의 폼박스 가져오기
	function getAppBox( target, boxKey ) {
		
		var opts = $.data(target, WIDGET).options;
		
		return opts._state_variables['_toolbar_boxes'][boxKey] || 
			   opts._state_variables['_extrbar_boxes'][boxKey] ;
	};

	function resetBoxes( target ) {
		
		var opts = $.data(target, WIDGET).options;
		
		$.each(opts._state_variables['_toolbar_boxes'], function(key, box) {
			if (box.clear)
				box.clear();
		});
		$.each(opts._state_variables['_extrbar_boxes'], function(key, box) {
			if (box.clear)
				box.clear();
		});
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
			prev : $('<a href="javascript:void(0);" title="이전" class="prev"><span><i class="icon-angle-left"></i></span>이전</a>'),
			next : $('<a href="javascript:void(0);" title="다음" class="next"><span><i class="icon-angle-right"></i></span>다음</a>'),
			first: $('<a href="javascript:void(0);" title="처음" class="first"><span><i class="icon-angle-first"></i></span>처음</a>'),
			last : $('<a href="javascript:void(0);" title="마지막" class="last"><span><i class="icon-angle-last"></i></span>마지막</a>')
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
		//console.log('grid load '+opts.id+', autoload='+opts.autoload);
		
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
		opts._state_variables['_delete_rows'] = [];
		opts._state_variables['_insert_rows'] = [];

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
	
	// 출력전 처리함수 (LIST만 적용)
	function beforePrint(target) {
		
		var opts = $.data(target, WIDGET).options;
		
		if (opts._state_variables['_current_mode'] != 'LIST')
			return;

		// 스크롤이 true인 경우
		if (opts.listOptions.scroll) {
			$(target).find('.grid-list-items').css('max-height', 'unset');
			$(target).find('.grid-list-items').removeClass('app-scroll');
		}
	};

	// 출력후 처리함수 (LIST만 적용)
	function afterPrint(target) {
		
		var opts = $.data(target, WIDGET).options;
		
		if (opts._state_variables['_current_mode'] != 'LIST')
			return;

		// 스크롤이 true인 경우
		if (opts.listOptions.scroll) {
			$(target).find('.grid-list-items').css('max-height', opts.listOptions.scrollHeight);
			$(target).find('.grid-list-items').addClass('app-scroll');
		}
	};
	// 그리드 ID 가져오기 (LIST만 적용)
	function getId(target) {
		var opts = $.data(target, WIDGET).options;
		return opts.id;
	};
	// 그리드 데이터 명칭 가져오기 (LIST만 적용)
	function getName(target) {
		var opts = $.data(target, WIDGET).options;
		return opts.name;
	};
	// 그리드 제목 가져오기 (LIST만 적용)
	function getTitle(target) {
		var opts = $.data(target, WIDGET).options;
		return opts.title;
	};
	// 그리드 데이터 건수 가져오기 (LIST만 적용)
	function getCount(target) {
		var opts = $.data(target, WIDGET).options;
		return $(target).find('.app-grid-table > tbody > tr.'+opts.listOptions.rowCls).length;
	};

	// 저장데이터 가져오기 (LIST 편집형인 경우에만 사용)
	function getSaveObject(target, validate) {
		
		var tdom = $(target);
		var opts = $.data(target, WIDGET).options;
		
		if (opts._state_variables['_current_mode'] != 'LIST')
			return false;
		if (opts.listOptions.editable === false)
			return [];
		// 최소 건수 확인
		if (getCount(target) < opts.listOptions.editMinCnt) {
			$.commMsg.alert(opts.title+': 최소 '+opts.listOptions.editMinCnt+'개 이상 등록되어야 합니다.');
			return false;
		}
		// 최대 건수 확인
		if (getCount(target) > opts.listOptions.editMaxCnt) {
			$.commMsg.alert(opts.title+': 등록가능한 최대 건수는 '+opts.listOptions.editMaxCnt+'개입니다.');
			return false;
		}

		// 검증결과
		let valid = true;
		// 삭제행목록
		let deltRows = opts._state_variables['_delete_rows'];
		// 저장행목록
		let saveRows = [];

		// 추가행목록
		tdom.find('.app-grid-table > tbody > tr.'+opts.listOptions.rowCls).each(function(i) {
			let tr   = $(this);
			let mode = tr.data('mode');
			let obj  = $.extend({mode: mode}, $.formUtil.getObject(tr));
			// 행단위 데이터 검증
			if (validate) {
				valid = validate(obj, i, tr);
			}
			else
				valid = true;
			if (valid) {
				saveRows.push(obj);
				return true;
			}
			return false;
		});
		if (!valid) return false;
		
		return {
			deltList: deltRows,
			saveList: saveRows
		};
	};
	// 그리드 편집시 최소건수 가져오기 (LIST 편집형인 경우에만 사용)
	function getEditMinCnt(target) {
		
		var opts = $.data(target, WIDGET).options;
		if (opts._state_variables['_current_mode'] != 'LIST')
			return 0;
		if (opts.listOptions.editable === false)
			return 0;
		// 최소 건수 반환
		return opts.listOptions.editMinCnt;
	};
	// 추가,삭제버튼 보이기 (LIST만 적용)
	function showEditable(target) {
		$(target).find('.app-grid-addbtn').show();
		$(target).find('.app-grid-delbtn').show();
		$(target).find('.app-grid-delbtn').closest('tr').find('td').removeClass('bg-ele-DisableBG');
		$(target).find('.app-grid-delbtn').closest('tr').find('input').removeClass('grid-input-DisableBG');
	};
	// 추가,삭제버튼 보이기 (LIST만 적용)
	function hideEditable(target) {
		$(target).find('.app-grid-delbtn').closest('tr').find('td').addClass('bg-ele-DisableBG');
		$(target).find('.app-grid-delbtn').closest('tr').find('input').addClass('grid-input-DisableBG');
		$(target).find('.app-grid-addbtn').hide();
		$(target).find('.app-grid-delbtn').hide();
	};

	//----------------------------------------------------------------------//
	// 위젯 정의
	//----------------------------------------------------------------------//
	$.fn[WIDGET] = function(options, param, extra){
		if (typeof options == 'string'){
			return $.fn[WIDGET].methods[options](this, param, extra);
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
		// 모드변경처리
		changeMode: function( jq, mode ) {
			return jq.each(function(){
				changeMode(this, mode);
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
		// toolbar/extrbar 에 정의된 사용자정의 폼박스 가져오기
		getAppBox: function( jq, boxKey ) {
			return getAppBox(jq[0], boxKey);
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
		// 출력전 처리함수 (LIST만 적용)
		beforePrint: function( jq ) {
			return jq.each(function(){
				beforePrint(this);
			});
		},
		// 출력후 처리함수 (LIST만 적용)
		afterPrint: function( jq ) {
			return jq.each(function(){
				afterPrint(this);
			});
		},
		// 그리드 ID 가져오기 (LIST만 적용)
		getId: function( jq ) {
			return getId(jq[0]);
		},
		// 그리드 데이터 명칭 가져오기 (LIST만 적용)
		getName: function( jq ) {
			return getName(jq[0]);
		},
		// 그리드 제목 가져오기 (LIST만 적용)
		getTitle: function( jq ) {
			return getTitle(jq[0]);
		},
		// 그리드 데이터 건수 가져오기 (LIST만 적용)
		getCount: function( jq ) {
			return getCount(jq[0]);
		},
		// 저장데이터 가져오기 (LIST 편집형인 경우에만 사용)
		getSaveObject: function( jq, validate ) {
			return getSaveObject(jq[0], validate);
		},
		// 그리드 편집시 최소건수 가져오기 (LIST 편집형인 경우에만 사용)
		getEditMinCnt: function( jq ) {
			return getEditMinCnt(jq[0]);
		},
		// 추가,삭제버튼 보이기 (LIST만 적용)
		showEditable: function( jq ) {
			return jq.each(function(){
				showEditable(this);
			});
		},
		// 추가,삭제버튼 감추기 (LIST만 적용)
		hideEditable: function( jq ) {
			return jq.each(function(){
				hideEditable(this);
			});
		}
	};
	
	//----------------------------------------------------------------------//
	// 기본 설정 옵션
	//----------------------------------------------------------------------//
	$.fn[WIDGET].defaults = {
		//그리드 ID
		id: false,
		//그리드 데이터명칭
		name: false,
		//제목 (결과메세지에 사용됨)
		title: '',
		//목록 타입 (CARD / LIST / SINGLE)
		mode: 'LIST',
		//타입 변경시 콜백함수
		callbackMode: false,
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
			//스크롤여부
			scroll: false,
			//스크롤시 그리드 높이
			scrollHeight: '500px',
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
			// 목록형인 경우 행단위 클래스 (ROW 기준)
			rowCls: 'app-grid-row',
			//행선택 이벤트
			select: false,
			//행단위 행확장이벤트
			extendRow: false,
			//행확장 토글이벤트 사용여부
			extendToggle: false,
			//데이터가 없을경우 메세지
			emptyText: '검색된 내용이 없습니다.',
			//열병합 칼럼명 (편집형일땐 사용안함)
			mergeColumn: false,
			// 열병합 칼럼의 td 스타일시트
			mergeCls: 'app-grid-merge-column',
			//편집형
			editable: false,
			//편집형 추가버튼
			//editAddBtn: '<i class="icon-plus-circle text-primary"></i>',
			editAddBtn: '<button type="button" class="bs-s btn-primary"><i class="icon-plus mr-5px"></i>추가</button>',
			//편집형 추가버튼 CLS
			editAddCls: false,//fs-15px
			//편집형 삭제버튼
			//editDelBtn: '<i class="icon-minus-circle text-red"></i>',
			editDelBtn: '<button type="button" class="bs-s btn-black"><i class="icon-trash mr-5px"></i>삭제</button>',
			//편집형 삭제버튼 CLS
			editDelCls: false,//fs-15px
			//편집형 항목크기
			editBtnWidth: '100px', //50px
			//편집형 입력박스 cls
			editBoxCls: 'ele-icon-box',
			//편집형 입력박스 단위 cls
			editUnitCls: 'app-unit',
			//편집형 최대건수
			editMaxCnt: 5,
			//편집형 최소건수
			editMinCnt: 1,
			//편집형 기본표시행수
			editDefCnt: 1,
			//편집형 행추가시 기본설정값
			editDefData: false,
			//편집형 이벤트
			editCallback: {
				// 삭제전 검증함수
				beforeDelete: false, // function() {}
				// 추가전 검증함수
				beforeInsert: false, // function() {}
				// 삭제후 콜백함수
				afterDelete: false, // function() {}
				// 추가후 콜백함수
				afterInsert: false, // function() {}
			}
		},
		// 카드형 옵션
		cardOptions: {
			// 기본 스타일시트
			cardCls: "img-list-1",
			// 박스 스타일시트
			boxCls: "card-box-slide",
			// 열 스타일시트
			lineCls: "col-12 col-sm-6 col-xl-4",
			// 한페이지 표시 갯수
			display: 9,
			// 카드 formatter
			formatter: false,
			// 카드클릭 이벤트
			select: false,
			//데이터가 없을경우 메세지
			emptyText: '<p class="fs-24px fw-600 lh-1 mb-2">검색결과가 없습니다.</p>',
		},
		// 단일카드형 옵션
		singleOptions: {
			// 기본 스타일시트
			cardCls: "one-list",
			// 박스 스타일시트
			boxCls: "box",
			// 열 스타일시트
			lineCls: "col-12",
			// 한페이지 표시 갯수
			display: 9,
			// 카드 formatter
			formatter: false,
			// 카드클릭 이벤트
			select: false,
			//데이터가 없을경우 메세지
			emptyText: '<p class="fs-24px fw-600 lh-1 mb-2">검색결과가 없습니다.</p>',
		},
		//검색결과 아이콘
		resultIcon: '<img src="'+getUrl('/images/titimg-1.svg')+'" alt="검색결과"/>',
		//검색결과 메세지
		resultText: false, // '<em>{total}</em><span>개의 {title}가 있습니다.</span>'
		//검색결과영역 format callback
		resultFormatter: false,
		// 2023.10.10 하단설명글
		footer: false,
		//검색후 처리함수
		callback: false,
		//생성후 처리함수
		createCallback: false,
		//최초로딩 여부
		autoload: false,
		// 모드변경 CLASS
		changeCls: false,
		// 툴바영역
		toolbar: false,
		// 툴바옵션설정
		toolbarOptions: false,
		// EXTRA바영역
		extrbar: false,
		// 툴바옵션설정
		extrbarOptions: false,
		// 내부변수
		_state_variables : {
			// 검색결과 데이터 배열
			_data_rows     : [],
			// 페이징 조건 데이터
			_page_params   : {},
			// 툴바 폼박스 배열
			_toolbar_boxes : {},
			// EXTRA바 폼박스 배열
			_extrbar_boxes : {},
			// 최종검색 조건 데이터
			_last_params   : false,
			// 현재 목록 유형
			_current_mode  : false,
			// 삭제 데이터 배열
			_delete_rows  : [],
			// 추가 데이터 배열
			_insert_rows  : [],
			// 열병합 칼럼정보
			_merge_column : {index:0, count:0, value:''}
		}
	};
		
})(jQuery);
