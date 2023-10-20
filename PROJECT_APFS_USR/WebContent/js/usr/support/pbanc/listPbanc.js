/**
*******************************************************************************
***    명칭: listPbanc.js
***    설명: 지원서비스 - 지원사업 통합검색 화면
***
***    -----------------------------    Modified Log   ------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.04.30    LSH        First Coding.
*******************************************************************************
**/
// 인자로 넘어온 검색조건 데이터
let P_SEARCH = {
	page          : false,
	showMode      : 'CARD',
	ordrField     : false,
	srchText      : '',
	exPrgrsCd     : false,
	prgrsSttsCd   : false,
	sprtFldCd     : false,
	bizFld        : false,
	bizTrgt       : false,
	bizTrgtAge    : false,
	bizTrgtFntnPd : false,
	rcptSeCd      : false,
	rcptMthdCd    : false,
	pbancSttsCd   : false,
};

// 인자로 넘어온 검색조건
function SetSearch( args ) {
	$.each(args, function(key, value) {
		if ($.commUtil.empty(value))
			return true;
		P_SEARCH[key] = value;
	});
}

$(function() {
	// 그리드 객체
	let P_GRID = false;
	// 그리드 목록유형
	let P_SHOW_MODE = P_SEARCH['showMode'];
	// 그리드 옵션
	let P_GRID_OPTIONS = {
		id:     '#appGrid',
		paging: '#appGridPagination',
		result: '#appGridResult',
		toolbar:'#appGridToolbar',
		extrbar:'#appGridExtrbar',
		// 검색 URL
		url: getUrl('/usr/support/pbanc/getListPbanc.do'),
		// 검색 조건
		params: {},
		// 툴바옵션
		toolbarOptions: {
			cls: 'shadow-box-1 px-24px py-16px',
			boxCls: 'design-txt-3',
			rowCls: 'custum-row-1',
			items: [{
				cls:   'col-12 col-md flex-b-md-226px',
				key:   'showMode',
				type:  'appIconBox',
				options: {
					icons: [
						{code:'CARD'  ,label:'카드형',icon:'icon-grid-square'},
						{code:'SINGLE',label:'목록형',icon:'icon-list'}
					],
					value: P_SEARCH['showMode'],
					select: function(code) {
						P_SHOW_MODE = code;
						$('#appGrid').appGrid('changeMode', code);
					},
				}
			},{	cls:   'col-12 col-md flex-b-md-190px px-5px',
				format: function() {
					let div = $('<div class="form-area-box3"></div>');
					div.append('<div class="ele-icon-box"></div>');
					div.find('.ele-icon-box').append('<i class="icon-search"></i>');
					div.find('.ele-icon-box').append('<input type="text" name="srchText" value="'+P_SEARCH['srchText']+'" placeholder="검색해주세요" title="srchText">');
					return div;
				}
			},{	cls:   'col px-5px',
				key:   'prgrsSttsCd',
				type:  'appSquareBox',
				value: P_SEARCH['prgrsSttsCd'],
				options: {
					type: 'static',
					cls: 'custum-row-1 justify-content-end',
					boxCls: 'text-icon-1',
					iconTag: 'i',
					rows: [
						{code: CODE.PRGRE_STUS1.ING, text:'모집중', icon:'icon-bell-on cycle-ver-1'},
						{code: CODE.PRGRE_STUS1.PRE, text:'모집예정', icon:'icon-bell-exclamation cycle-ver-1'},
					],
					input:{name: 'prgrsSttsCd',value:''},
					select: function() {
						doSearch();
					},
				}
			},{	cls:   'col-4 col-md flex-b-md-166px',
				key:   'ordrField',
				type:  'appDropdownBox',
				value: P_SEARCH['ordrField'],
				options: {
					label: '정렬선택',
					type:  'static',
					cls:   'small-ver2',
					rows:   STORE.SORT_PBANC, // comm_const.js
					input: {id:'ordrField', name:'ordrField', value:''},
					select: function() {
						doSearch();
					},
				}
			}],
		},
		// EXTRA바옵션
		extrbarOptions: {
			cls: 'bottom px-24px py-16px',
			rowCls: 'align-items-start',
			items: [{
				cls: 'col-12 col-md flex-b-md-175px',
				items: [{
					cls: 'custum-row-1 mb-10px',
					items: [{
						cls: 'col-12',
						format: function() {
							let p = $('<p class="text-icon-2"></p>');
							p.append('<i class="icon-search-text lefticon"></i>맞춤형 검색');
							p.append('<i class="icon-rotate-left cycle-ver-1 app-refresh app-pointer"></i>');
							p.find('i.app-refresh').bind('click', function() {
								// 검색항목 리셋
								doReset();
							});
							return p;
						}
					}]
				},{
					cls: 'custum-row-1',
					items: [{
					},{	cls: 'col-12',
						format: function() {
							let input = $('<input type="checkbox" />');
							input.prop('id'   , 'exPrgrsCd');
							input.prop('name' , 'exPrgrsCd');
							input.prop('value', CODE.PRGRE_STUS1.END);
							let label = $('<label for="exPrgrsCd"></label>');
							label.append('<span class=""><em></em></span>');
							label.append('모집마감 제외');

							let p = $('<div class="check-radio-box toggle-ver"></div>');
							p.append(input);
							p.append(label);
							if (P_SEARCH['exPrgrsCd'] == CODE.PRGRE_STUS1.END) {
								p.find('[type="checkbox"]').prop('checked', true);
							}
							return p;
						}
					}]
				}]
			},{	cls: 'col-12 col-md',
				items: [{
					cls: 'custum-row-2',
					items: [{
						cls:  'col-6 col-md',
						key:  'bizFld',
						type: 'appDropdownBox',
						value: P_SEARCH['bizFld'],
						options: {
							label: '사업분야',
							cls:   'small-ver2',
							init:   COMBO.INIT_ALL,
							params: {upCdId: CODE.BIZ_RLM.code},
							input:  {id:'bizFld', name:'bizFld', value:''}
						}
					},{ cls:  'col-6 col-md',
						key:  'bizTrgt',
						type: 'appDropdownBox',
						value: P_SEARCH['bizTrgt'],
						options: {
							label: '지원대상',
							cls:   'small-ver2',
							init:   COMBO.INIT_ALL,
							params: {upCdId: CODE.SPRT_TRGT.code},
							input:  {id:'bizTrgt', name:'bizTrgt', value:''},
							select: function() {
								// 창업기간 로드
								let box = $('#appGrid').appGrid('getAppBox', 'bizTrgtFntnPd');
								box.load();
							}
						}
					},{ cls:  'col-6 col-md',
						key:  'bizTrgtAge',
						type: 'appDropdownBox',
						value: P_SEARCH['bizTrgtAge'],
						options: {
							label: '지원연령',
							cls:   'small-ver2',
							init:   COMBO.INIT_ALL,
							params: {upCdId: CODE.SPRT_AGE.code},
							input:  {id:'bizTrgtAge', name:'bizTrgtAge', value:''}
						}
					},{ cls:  'col-6 col-md',
						key:  'bizTrgtFntnPd',
						type: 'appDropdownBox',
						value: P_SEARCH['bizTrgtFntnPd'],
						options: {
							label: '창업기간',
							cls:   'small-ver2',
							init:   COMBO.INIT_ALL,
							params: {upCdId: CODE.FNTN_WHL.code},
							input:  {id:'bizTrgtFntnPd', name:'bizTrgtFntnPd', value:''},
							// 지원대상에 따른 필터처리
							loadFilter: function(rows) {
								let fld = $('#bizTrgt').val();
								if (!fld)         return rows; // 전체
								if (fld ==   '')  return rows; // 전체
		
								for(var i = 0; i < rows.length; i++){ 
									// 예비창업자인 경우 
									if (fld == CODE.SPRT_TRGT.PRE) {
										// 예비창업자만 포함
										if (rows[i]['code'] != CODE.FNTN_WHL.PRE) { 
									    	rows.splice(i, 1); 
									    	i--; 
									  	}
									} 
									// 그외
									else {
										// 예비창업자 제외
										if (rows[i]['code'] == CODE.FNTN_WHL.PRE) { 
									    	rows.splice(i, 1); 
									    	i--; 
									  	}
									} 
								}
								return rows;
							}
						}
					}]
				},{
					cls:  'custum-row-3 mt-5px',
					key:  'crdnsList',
					type: 'appSelectBox',
					value: P_SEARCH['crdnsBzentyNo'],
					options: {
						form: 'checkbox',
						name: 'crdnsList',
						label: '유관기관',
						cls: 'small-ver2',
						colCls: 'col-6 col-md flex-grow-0',
						labelCls: 'fs-14px',
						// 첫번쨰값 선택처리
						selectIndex: '0',
						valueField: 'cdCn',
						labelField: 'text',
						params:{upCdId: CODE.CRDNS_SE.code},
						init:  {cdCn:'', text:'전체', click: bindCheckAll},
						filter: function(o) {
							return (o['code'] != '99');
						}
					}
				}]
			},{ cls: 'col-12 col-md flex-b-md-100px',
				items: [{
					cls: 'custum-row-1 mb-10px',
					items: [{
						cls: 'col-12',
						format: function() {
							let btn = $('<button type="submit" class="btn-primary bs-m w-100"></button>');
							btn.append('<i class="icon-check-double"></i>');
							btn.append('적용');
							btn.bind('click', function() {
								doSearch();
							});
							return btn;
						}
					}]
				},{
					cls: 'custum-row-1',
					items: [{
						cls: 'col-12',
						format: function() {
							let btn = $('<button type="button" class="btn-primary bs-m w-100"></button>');
							btn.append('<i class="icon-edit"></i>');
							btn.append('공고작성');
							btn.bind('click', function() {
								goUrl(getUrl('/usr/mypage/pbanc/formPbanc.do'));
								return false;
							});
							return btn;
						}
					}]
				}]
			}]
		},
	};
	// 유관기관이 아닌 경우
	if (!SCREEN.ROLE.RIS) {
		// (comm_utils.js 의 treeUtil 참고)
		// 툴바항목의 가장 최종 항목을 삭제한다. (공고작성 버튼)
		$.treeUtil.deleteLast(P_GRID_OPTIONS.extrbarOptions.items);
	}
	// 정부지원사업 카드형 그리드 객체
	P_GRID = $.gridUtils.appPbancGrid({
		form       : $('#searchForm'),
		gridOptions: P_GRID_OPTIONS,
		// 상세조회용 필터함수
		selectFilter: function( params ) {
			$.extend(params, {showMode: P_SHOW_MODE});
			return params;
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
	if (P_SEARCH['page'] > 1)
		doSearch( P_SEARCH['page'] );
});
