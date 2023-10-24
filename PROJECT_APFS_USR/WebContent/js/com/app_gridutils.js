/**
*******************************************************************************
*** 파일명    : app_gridutils.js
*** 설명      : 업무용 각종목록 컨트롤
***
***            $.gridUtils.appPbancGrid         : 정부지원사업 그리드
***            $.gridUtils.appFundGrid          : 모태펀드 그리드
***            $.gridUtils.appEntGrid           : 경영체 그리드
***            $.gridUtils.appGisEntGrid        : gis 경영체 그리드
***            $.gridUtils.appEventEntGrid      : IR검토의견등록 - 투자설명회 참여경영체 그리드(카드)
***	
***            $.gridUtils.FORMAT.fundNm        : 펀드명 포맷
***            $.gridUtils.FORMAT.invtNm        : 투자자명 포맷 (대표홈페이지 링크처리)
***            $.gridUtils.FORMAT.fundPrd       : 투자기간 포맷
***            $.gridUtils.FORMAT.btnFundApply  : 지원하기버튼 포맷
***            $.gridUtils.FORMAT.btnEntView    : 경영체 상세보기버튼 포맷
***            $.gridUtils.FORMAT.btnEntBookmark: 경영체 북마크버튼 포맷
***            $.gridUtils.FORMAT.pbancIcon     : 공고아이콘 포맷
***            $.gridUtils.FORMAT.pbancDate     : 공고일자 포맷
***            $.gridUtils.FORMAT.pbancBookmark : 지원사업 북마크버튼 포맷
***            $.gridUtils.FORMAT.entCard       : 경영체 카드포맷
***	
***
*** -----------------------------    Modified Log   ---------------------------
*** ver             date                author                  description
*** ---------------------------------------------------------------------------
*** 1.0         2023.04.20              LSH
*** 1.1 		2023.06.26				KYW			마이페이지 - IR검토의견등록 - 투자설명회 참여경영체 그리드 추가
*******************************************************************************
**/

$.gridUtils = {};

$.gridUtils.FORMAT = {
	// 펀드명 포맷
	//---------------------------------------
	fundNm: function(v,o) {
		// 진행상태에 따른 색상변경
		let p = $('<p class="shapes-box app-l"></p>');
		p.append('<i style="margin-right:8px;">●</i>');
		p.append(v);
		if (o['prgrsSttsCd'] == CODE.PRGRE_STUS1.ING) {
			p.addClass('text-primary');
			p.find('i').addClass('text-primary');
		}
		else {
			p.addClass('text-gray');
			p.find('i').addClass('text-gray');
		}
		return p;
	},
	// 투자자명 포맷 (대표홈페이지 링크처리) 2023.10.24 홈페이지 주소 http 없을경우 상대경로로 주소가 꼬여 http 추가 기능
	//---------------------------------------
	invtNm: function(v, o) {
	    let h = o['invtHmpgAddr'];
	    
	    if (!$.commUtil.empty(h)) {
	        // 만약 http:// 또는 https://가 앞에 없으면 http:// 추가
	        if (!h.startsWith('http://') && !h.startsWith('https://')) {
	            h = 'http://' + h;
	        }
	        return '<a href="'+h+'" target="_blank">'+v+'</a>';
	    }
	    
	    return v;
	},
	// 투자기간 포맷
	//---------------------------------------
	fundPrd: function(v,o) {
		return [
			$.formatUtil.toDotYm(o['invtBgngYmd']),
			$.formatUtil.toDotYm(o['invtEndYmd' ])	
		].join('~<br>');
	},
	// 지원하기버튼 포맷
	//---------------------------------------
	btnFundApply: function(v,o) {
		let b = $('<button type="button" class="bs-s w-100"></button>');
		b.data('fundno'  , v);
		b.data('bzentyno', o['invtBzentyNo']);
		// 모집상태 코드객체 가져오기
		let c = STORE.getItem('FUND_STTS', o['prgrsSttsCd']);
		if (!c)
			return b;
		// 경영체인 경우
		if (SCREEN.ROLE.ENT && c.key == 'ING') {
			// 2023.09.01 지원전인 경우
			if ($.commUtil.empty(o['sprtYmd'])) {
				b.addClass('btn-primary');
				b.addClass('btn-grid-apply');
				b.append('<i class="icon-edit"></i>');
				b.append('지원하기');
			}
			else {
				b.addClass('btn-gray');
				b.append('<i class="icon-check"></i>');
				b.append('지원완료');
			}
		}
		else {
			b.addClass('btn-gray');
			b.append('<i class="'+c.icon+'"></i>');
			b.append(c.text);
		}
		return b;
	},
	// 경영체 상세보기버튼 포맷
	//---------------------------------------
	btnEntView: function(v) {
		let b = $('<button type="button" class="bs-s w-100"></button>');
		b.data('bzentyno', v);
		b.addClass('btn-primary');
		b.addClass('btn-grid-view');
		b.append('<i class="icon-file-text-search"></i>');
		b.append('상세보기');
		b.click(function() {
			$.formUtil.submitForm(
				getUrl('/usr/invest/ent/viewEnt.do'), {
					params: {bzentyNo: $(this).data('bzentyno')}
				}
			);
		});
		return b;
	},
	// 경영체 북마크버튼 포맷
	//---------------------------------------
	btnEntBookmark: function(v) {
		if (v == 'Y')
			return $('<i class="fs-22px icon-bookmark-check F text-primary"></i>');
		else
			return $('<i class="fs-20px icon-bookmark-check text-ele-Text"></i>');
	},
	// 공고아이콘 포맷
	//---------------------------------------
	pbancIcon: function(v,o) {
		let i = $('<span></span>');
		// 예정인 경우
		if (v == '10') {
			i.addClass("bg-brown-t10 text-brown");
			i.append('<i class="icon-alarm-edit"></i>');
			i.append('예정 - ');
			i.append($.formatUtil.toKorMonth(o['rcptBgngDt']));
		}
		// 상시모집
		else if (v == '20') {
			i.addClass("bg-primary-t10 text-primary");
			i.append('<i class="icon-presentation-pen"></i>');
			i.append('상시모집');
		}
		// 모집마감
		else if ($.commUtil.isPastDate(o['rcptEndDt'])) {
			i.addClass("bg-red-t10 text-red");
			i.append('<i class="icon-calendar-minus"></i>');
			i.append('모집마감');
		}
		else {
			i.addClass("bg-mint-t10 text-mint");
			i.append('<i class="icon-bell-on"></i>');
			i.append('D-');
			i.append($.commUtil.getDays(o['rcptEndDt'])+'일');
		}
		return i;
	},
	// 공고일자 포맷
	//---------------------------------------
	pbancDate: function(v,o) {
		let i = $('<span></span>');
		// 예정인 경우
		if (v == '10') {
			i.addClass("text-brown");
			i.append('<i class="icon-alarm-edit"></i>');
			i.append('모집예정');
		}
		// 상시모집
		else if (v == '20') {
			i.addClass("text-primary");
			i.append('<i class="icon-presentation-pen"></i>');
			i.append('상시모집');
		}
		else {
			i.append('<i class="icon-calendar-clock"></i>');
			i.append('<em>마감일자</em>');
			i.append($.formatUtil.toDashDate(o['rcptEndDt']));
		}
		return i;
	},
	// 지원사업 북마크버튼 포맷
	//---------------------------------------
	pbancBookmark: function(v, o) {
		let bk = $('<i></i>');
		bk.data('doc'  , o['bizPbancNo'   ]);
		bk.data('trgt' , o['crdnsBzentyNo']);
		bk.data('sn' , o['sn']);
		bk.data('secd' , CODE.BKMK_SE.PBANC);
		
		if (v == 'Y') 	bk.addClass("icon-bookmark-check F active");
		else			bk.addClass("icon-bookmark-plus");

		if (SCREEN.ROLE.BIZ) {
			bk.addClass('app-pointer');
			bk.bind('click', function() {
				// 북마크 선택/해제
				$.bizUtils.saveBookmark({
					btn: $(this),
					params: {
						trgtBzentyNo : $(this).data('trgt'),
						docNo        : $(this).data('doc' ),
						bkmkSeCd     : $(this).data('secd'),
						sn     		 : $(this).data('sn')
					},
					callback: function( mode ) {
						if (mode == MODE.INSERT) {
							bk.removeClass("icon-bookmark-plus").addClass("icon-bookmark-check F active");
						}
						else {
							bk.removeClass("icon-bookmark-check F active").addClass("icon-bookmark-plus");
						}
					}
				});
			});
		}
		return bk;
	},
	// 경영체 카드포맷 (cardOptions.formatter에 사용됨)
	//---------------------------------------
	entCard: function(dom,o) {
		
		// 대표이미지
		dom.append( $.bizUtils.getEntImage(o, MODE.LIST) );

		// 북마크
		let bk = $('<i></i>');
		bk.data('trgt' , o['bzentyNo']);
		bk.data('secd' , CODE.BKMK_SE.ENT);
		
		if (o['gsBkmkYn'] == 'Y') bk.addClass("icon-bookmark-check F active");
		else		              bk.addClass("icon-bookmark-plus");
			
		//if (o['srchMode'] == CODE.SRCH_MODE.BOOKMARK) {
		bk.addClass('app-pointer');
		bk.bind('click', function() {
			// 북마크 선택/해제
			$.bizUtils.saveBookmark({
				btn: $(this),
				params: {
					trgtBzentyNo : $(this).data('trgt'),
					bkmkSeCd     : $(this).data('secd')
				},
				callback: function( mode ) {
					if (mode == MODE.INSERT) {
						bk.removeClass("icon-bookmark-plus").addClass("icon-bookmark-check F active");
					}
					else {
						bk.removeClass("icon-bookmark-check F active").addClass("icon-bookmark-plus");
					}
				}
			});
		});
		//}
		dom.append(bk);

		// app_bizutils.js
		let box = $.bizUtils.getEntInfo(o, MODE.LIST);
		dom.append(box);
	},
	// 2023.09.07 GIS 경영체목록
	// 경영체명 마우스오버시 분야정보 툴팁 보이기
	//---------------------------------------
	showTooltip: function(e) {
		
		if ($('#tooltip').length == 0)
			$('body').append('<span id="tooltip" class="app-toltip"></span>');
	
		if ($('#tooltip').hasClass('on')) {
			$('#tooltip').removeClass('on');
		}
		else {
			$('#tooltip').html('');
			$('#tooltip').append('<p class="mt-10px">투자분야</p><span>'+$(this).data('invtfld')+'</span>');
			$('#tooltip').append('<p class="mt-10px">사업분야</p><span>'+$(this).data('bizfld')+'</span>');
			$('#tooltip').css('top' , (e.clientY + 30) + 'px');
			$('#tooltip').css('left', (e.clientX + 30) + 'px');
			$('#tooltip').addClass('on');
		}
	},
	// 2023.09.12 항목 마우스오버시 분야정보 툴팁 보이기 이벤트
	//---------------------------------------
	bindOverFld: function(dom, v, r) {
		dom.data('invtfld', $.commUtil.nvl(r['invtFldText'], STRING.NOINFO));
		dom.data('bizfld' , $.commUtil.nvl(r['bizFldText' ], STRING.NOINFO));
		dom.mouseover($.gridUtils.FORMAT.showTooltip)
		   .mouseout ($.gridUtils.FORMAT.showTooltip);
	},
	// 2023.09.12 투자분야/사업분야 포맷
	//---------------------------------------
	fldText: function(v,o) {
		let s = v;
		if (v.length > 20)
			s = s.substring(0,20)+'...';
		return s;
	}
};

// 정부지원사업 카드형(CARD)/리스트형(SINGLE) 그리드
$.gridUtils.appPbancGrid = function ( args ) {
	
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
			idField:    'bizPbancNo',
			// 목록 제목
			title:      '정부지원사업',
			// 검색 URL
			url:         getUrl('/usr/support/pbanc/getListPbanc.do'),
			// 페이지 표시 객체
			paging:     '#appGridPagination',
			// 검색결과 표시 객체
			result:     '#appGridResult',
			// 검색결과 건수표시 메세지
			resultText: '<em>{total}</em><span>개의 {title}이 있습니다.</span>',
			// 자동로딩
			autoload: true,
			// 모드변경 CLASS
			changeCls:  'shadow-box-1 p-24px',
			// 카드옵션
			cardOptions: {
				// 한페이지 표시 갯수
				display: 9,
				// 기본 스타일시트
				cardCls: "one-list-2",
				// 열 스타일시트
				lineCls: "col-12 col-xl-4",
				// 박스 스타일시트
				boxCls: "box shadow-box-1 px-24px",
				// 카드포맷
				formatter: function(dom,o) {
					// 하단영역
					let info2 = $('<div class="info-2"></div>');
					info2.append($.domUtil.getRow({
								// 업체명
						items: [{formatHtml: '<span class=""><i class="icon-building"></i>'+o['crdnsBzentyNm']+'</span>'} 
								// 등록일자
						       ,{formatHtml: '<span class=""><i class="icon-calendar-check-alt"></i><em>등록일자</em>'+o['regDate']+'</span>'} 
								// 마감아이콘
						       ,{formatHtml: $.gridUtils.FORMAT.pbancDate(o['rcptSeCd'],o)} 
						]
					}));
					
					let iconCol = $('<div class="col-12"></div>');
					iconCol.append('<div class="info-1"></div>');
					// 구분아이콘
					iconCol.find('.info-1').append($.gridUtils.FORMAT.pbancIcon(o['rcptSeCd'],o));
					// 북마크아이콘
					iconCol.find('.info-1').append($.gridUtils.FORMAT.pbancBookmark(o['gsBkmkYn'], o));
					
					// 공고명
					let title = $('<p class="tit"></p>');
					title.data('no', o['bizPbancNo']);
					title.append(o['bizPbancNm']);
					title.addClass('app-pointer');
					title.addClass('app-pre');
					// 상세보기
					title.bind('click', _doSelect);
					
					let dataCol = $('<div class="col-12"></div>');
					dataCol.append('<div class="textbox"></div>');
					dataCol.find('.textbox').append(title);
					dataCol.find('.textbox').append(info2);
						
					let row = $('<div class="row custum-row-1"></div>');
					row.append(iconCol);
					row.append(dataCol);
					dom.append(row);
				},
				emptyText: [
					'<p class="fs-24px fw-600 lh-1 mb-2">검색결과가 없습니다.</p>',
					'<span class="fs-15px fw-300">조건 검색에서 조건을 수정하여 다시 검색해보세요.</span>'
				].join('')
			},
			// 단일카드형 옵션
			singleOptions: {
				// 한페이지 표시 갯수
				display: 10,
				// 카드포맷
				formatter: function(dom,o) {
					// 하단영역
					let info2 = $('<div class="info-2"></div>');
					info2.append($.domUtil.getRow({
								// 업체명
						items: [{formatHtml: '<span class=""><i class="icon-building"></i>'+o['crdnsBzentyNm']+'</span>'} 
								// 등록일자
						       ,{formatHtml: '<span class=""><i class="icon-calendar-check-alt"></i><em>등록일자</em>'+o['regDate']+'</span>'} 
								// 마감아이콘
						       ,{formatHtml: $.gridUtils.FORMAT.pbancDate(o['rcptSeCd'],o)} 
						]
					}));
					
					// 모집구분아이콘
					let info1 = $('<div class="info-1"></div>');
					info1.append($.gridUtils.FORMAT.pbancIcon(o['rcptSeCd'],o))
					
					// 공고명
					let title = $('<p class="tit"></p>');
					title.data('no', o['bizPbancNo']);
					title.append(o['bizPbancNm']);
					title.addClass('app-pointer');
					title.addClass('app-pre');
					// 상세보기
					title.bind('click', _doSelect);
					
					let dataCol = $('<div class="col"></div>');
					dataCol.append(info1);
					dataCol.append(title);
					dataCol.append(info2);
					
					let iconCol = $('<div class="col iconbox"></div>');
					// 북마크아이콘
					iconCol.append($.gridUtils.FORMAT.pbancBookmark(o['gsBkmkYn'], o));

					let row = $('<div class="row"></div>');
					row.append(dataCol);
					row.append(iconCol);
					dom.append(row);
				},
				emptyText: [
					'<p class="fs-24px fw-600 lh-1 mb-2">검색결과가 없습니다.</p>',
					'<span class="fs-15px fw-300">조건 검색에서 조건을 수정하여 다시 검색해보세요.</span>'
				].join('')
			},
			// 툴바옵션
			toolbarOptions: {
				cls: 'shadow-box-1 mb-16px px-24px py-16px',
				boxCls: 'design-txt-1',
				items: [{
					cls:   'col flex-b210px',
					key:   'showMode',
					type:  'appIconBox',
					options: {
						icons: [
							{code:'CARD'  ,label:'카드형',icon:'icon-grid-square'},
							{code:'SINGLE',label:'목록형',icon:'icon-list'}
						],
						value: 'CARD',
						select: function(code) {
							if ($(options.gridOptions.id).appGrid('getAppBox','ordrField'))
								$(options.gridOptions.id).appGrid('getAppBox','ordrField').clear();
							$(options.gridOptions.id).appGrid('changeMode', code);
						},
					}
				},{	cls:   'col flex-b210px',
					key:   'ordrField',
					type:  'appDropdownBox',
					options: {
						label: '정렬선택',
						type:  'static',
						rows:   STORE.SORT_PBANC, // comm_const.js
						input: {id:'ordrField', name:'ordrField', value:''},
						select: function() {
							options.doSearch();
						},
					}
				}],
			},
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
	
	function _doSelect() {

        // 검색폼 데이터 객체화
        var obj = options.form.serializeObject();
		let pg  = $(options.gridOptions.id).appGrid('getPagination');
		$.extend(obj, {page: pg['page']});
		$.extend(obj, {bizPbancNo: $(this).data('no')});
		
		if (options.selectFilter)
			obj = options.selectFilter(obj);
		
		if (!obj)
			return false;

		// 상세보기
		$.formUtil.submitForm(getUrl('/usr/support/pbanc/viewPbanc.do'), {
			params: obj
		});
	};
	
	function _doSearch( page ) {

        // 검색폼 데이터 객체화
        var obj = options.form.serializeObject();
		// 전체가 선택된 경우
		if ($.inArray('', obj['crdnsList']) >= 0)
			obj['crdnsList'] = '';
		// 투자분야(다중선택)를 배열로 변환
		$.formUtil.setArrayData(obj, 'crdnsList');
        // 검색폼 그리드 검색
		$(options.gridOptions.id).appGrid('search', obj, page);
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
	};
	
	_doInit();
		
	return options;
};

// 모태펀드 그리드
$.gridUtils.appFundGrid = function ( args ) {

	var options = $.extend(true, {

		form : $('#searchForm'),
		
		gridOptions: {
			// 목록 ID
			id:         '#appGrid',
			// 목록 유형
			mode:       'LIST',
			// 목록 KEY필드
			idField:    'fundNo',
			// 목록 제목
			title:      '농림수산식품펀드',
			// 검색 URL
			url:         getUrl('/usr/invest/fund/getListFund.do'),
			// 자동 로딩
			autoload:    false,
			// 페이지 표시 객체
			paging:     '#appGridPagination',
			// 검색결과 표시 객체
			result:     '#appGridResult',
			// 툴바영역
			toolbar:    '#appGridToolbar',
			// 툴바옵션
			toolbarOptions: {
				cls:  'shadow-box-1 mb-16px px-12px px-xl-24px py-16px',
				boxCls: 'design-txt-1',
				items: [{
					cls:   'col-5 col-xl-6',
					key:   'prgrsIconCd',
					type:  'appSquareBox',
					// 2023.08.18 LSH 초기검색시 진행상태 설정
					value: CODE.PRGRE_STUS1.ING,
					options: {
						init:   RADIO.INIT_ALL,
						type:   'static',
						rows:   STORE.INVT_YN,
						input:  {id: 'prgrsIconCd', name: 'prgrsIconCd',value:''},
						select: function(code) {
							options.doSearch({prgrsIconCd: code});
						},
					}
				},{	cls: 'col-12 col-md flex-b-md-210px',
					key:   'ordrField',
					type:  'appDropdownBox',
					options: {
						label: '정렬선택',
						type:  'static',
						rows:   STORE.SORT_FUND, // comm_const.js
						input: {id:'ordrField', name:'ordrField', value:''},
						select: function() {
							options.doSearch();
						},
					}

				//},{	cls: 'col-12 col-md flex-b-md-320px',
				//	key:   'ordrField',
				//	type:  'appSortBox',
				//	options: {
				//		select: function() {
				//			options.doSearch();
				//		},
				//		inputs: {
				//			field: {id:'ordrField',name:'ordrField',value:''},
				//			order: {id:'ordrType' ,name:'ordrType' ,value:''},
				//		},
				//		items: [
				//			{cls: 'col-6', field: 'fund_oper_scale', order:  'ASC', icon: 'icon-arrow-down-from-line-F', label: '낮은 펀드규모순'},
				//			{cls: 'col-6', field: 'fund_oper_scale', order: 'DESC', icon: 'icon-arrow-up-from-line-F'  , label: '높은 펀드규모순'}
				//		]
				//	}
				}],
			},
			// 리스트옵션
			listOptions: {
				// 검색결과 없을경우 메세지
				emptyText:  '검색된 {title}가 없습니다.',
				// 목록칼럼 정의
				columns: [
					{cls:'app-l app-wordbrk', name:'fundNm'       , label:'펀드명',   formatter: $.gridUtils.FORMAT.fundNm},
					{cls:'app-l app-nowrap', name:'invtFldNm'    , label:'투자분야'},
					{cls:'app-c app-nowrap', name:'makeYr'       , label:'조성년도', formatter: $.formatUtil.toYear},
					{cls:'app-l app-nowrap', name:'invtPrd'      , label:'투자기간', formatter: $.gridUtils.FORMAT.fundPrd},
					{cls:'app-l app-nowrap', name:'invtBzentyNm' , label:'투자자',   formatter: $.gridUtils.FORMAT.invtNm},
					{cls:'app-c app-nowrap', name:'rprsTelno'    , label:'전화'    , formatter: $.formatUtil.toPhone},
					{cls:'app-r app-nowrap', name:'fundOperScale', label:'펀드규모', formatter: $.formatUtil.toFundScale},
					{cls:'app-c app-nowrap', name:'fundNo'       , label:'펀드상태', formatter: $.gridUtils.FORMAT.btnFundApply}
				],
				colgroup: ['*','145px','90px','90px','175px','135px','100px','100px']
			},
			// 검색결과 건수표시 메세지
			resultText: '<em>{total}</em><span>개의 {title}가 있습니다.</span>',
			// 검색완료 후 이벤트 처리
			callback: function(elm, rows) {
				if (rows.length == 0)
					return;
				elm.find('.btn-grid-apply').click(function() {
					popup.openApplyFund({
						params: {
							fundNo: $(this).data('fundno'),
							invtBzentyNo: $(this).data('bzentyno')
						},
						callback: function() {
							options.doSearch();
						}
					});
				});
			},
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
	    doSearch: function( params ) {
			_doSearch( params );
	    }
	}, args);
	
	function _doSearch( params ) {
        // 검색폼 데이터 객체화
        var obj = options.form.serializeObject();
		// 전체가 선택된 경우
		if ($.inArray('', obj['invtFldList']) >= 0)
			obj['invtFldList'] = '';
		// 투자분야(다중선택)를 배열로 변환
		$.formUtil.setArrayData(obj, 'invtFldList');
		// 2023.08.17 검색박스 텍스트 포함
		if (!$.commUtil.empty($('#srchText').val()))
			obj['srchText'] = $('#srchText').val();
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
	};
	
	_doInit();
		
	return options;
};

// 경영체 그리드
$.gridUtils.appEntGrid = function ( args ) {
	
	var options = $.extend(true, {

		form : $('#searchForm'),
		
		gridOptions: {
			// 목록 ID
			id:         '#appGrid',
			// 목록 유형
			mode:       'CARD',
			// 목록 KEY필드
			idField:    'bzentyNo',
			// 목록 제목
			title:      '농림수산식품 경영체',
			// 검색 URL
			url:         getUrl('/usr/invest/ent/getListEntBiz.do'),
			// 페이지 표시 객체
			paging:     '#appGridPagination',
			// 검색결과 표시 객체
			result:     '#appGridResult',
			// 툴바영역
			toolbar:    '#appGridToolbar',
			// 검색결과 건수표시 메세지
			resultText: '<em>{total}</em><span>개의 {title}가 있습니다.</span>',
			// 자동로딩여부
			autoload:    true,
			// 모드변경 CLASS
			changeCls:  'shadow-box-1 px-16px pb-24px',
			// 툴바옵션
			toolbarOptions: {
				cls: 'shadow-box-1 mb-16px px-24px py-16px',
				boxCls: 'design-txt-1',
				items: [{
					cls:   'col flex-b210px',
					key:   'showMode',
					type:  'appIconBox',
					options: {
						icons: [
							{code:'CARD',label:'카드형',icon:'icon-grid-square'},
							{code:'LIST',label:'목록형',icon:'icon-list'}
						],
						value: 'CARD',
						select: function(code) {
							$(options.gridOptions.id).appGrid('changeMode', code);
						},
					}
				},{	cls:   'col flex-b210px',
					key:   'ordrField',
					type:  'appDropdownBox',
					options: {
						label: '정렬선택',
						type:  'static',
						rows:   STORE.SORT_ENT, // comm_const.js
						input: {id:'ordrField', name:'ordrField', value:''},
						select: function() {
							options.doSearch();
						},
					}
				}],
			},
			// 리스트옵션
			listOptions: {
				// 검색결과 없을경우 메세지
				emptyText:  '검색된 {title}가 없습니다.',
				// 목록칼럼 정의
				columns: [
					{cls:'app-l', name:'bzentyNm'     , label:'경영체명'},
					{cls:'app-l', name:'invtFldText'  , label:'투자분야'    , formatter: $.gridUtils.FORMAT.fldText, formatCell: $.gridUtils.FORMAT.bindOverFld},
					{cls:'app-l', name:'bizFldText'   , label:'사업분야'    , formatter: $.gridUtils.FORMAT.fldText, formatCell: $.gridUtils.FORMAT.bindOverFld},
					{cls:'app-r', name:'invtHopeAmt'  , label:'투자희망금액', formatter: $.formatUtil.toInvtHopeAmtFmt},
					{cls:'app-c', name:'bzentyNo'     , label:'상세보기'    , formatter: $.gridUtils.FORMAT.btnEntView},
					{cls:'app-c', name:'gsBkmkYn'     , label:'북마크'      , formatter: $.gridUtils.FORMAT.btnEntBookmark}
				],
				colgroup: ['*','280px','280px','120px','120px','90px']
			},
			// 카드옵션
			cardOptions: {
				// 카드포맷
				formatter: $.gridUtils.FORMAT.entCard,
				// 카드선택이벤트
				//select: function(row) {
				//	_doSelect({bzentyNo: row['bzentyNo']});
				//},
				// 빈값메시지
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
	    doSearch: function( params ) {
			_doSearch( params );
	    }
	}, args);
	
	function _doSelect( params ) {
		// 상세보기
		$.formUtil.submitForm(getUrl('/usr/invest/ent/viewEnt.do'), {
			params: params
		});
	};
	
	function _doSearch( params ) {
        // 검색폼 데이터 객체화
        var obj = options.form.serializeObject();
		// 전체가 선택된 경우
		if ($.inArray('', obj['invtFldList']) >= 0)
			obj['invtFldList'] = '';
		// 전체가 선택된 경우
		if ($.inArray('', obj['bizFldList']) >= 0)
			obj['bizFldList'] = '';

		// 투자분야(다중선택)를 배열로 변환
		$.formUtil.setArrayData(obj, 'invtFldList');
		// 사업분야(다중선택)를 배열로 변환
		$.formUtil.setArrayData(obj, 'bizFldList');
		// 2023.08.17 검색박스 텍스트 포함
		if (!$.commUtil.empty($('#srchText').val()))
			obj['srchText'] = $('#srchText').val();
		
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
	};
	
	_doInit();
		
	return options;
};

//gis 경영체 그리드
$.gridUtils.appGisEntGrid = function ( args ) {
	
	var options = $.extend(true, {

		form : $('#searchForm'),
		
		gridOptions: {
			// 목록 ID
			id:         '#appGrid',
			// 목록 유형
			mode:       'LIST',
			// 목록 KEY필드
			idField:    'bzentyNo',
			// 목록 제목
			title:      '농림수산식품 경영체',
			// 검색 URL
			url:         getUrl('/usr/invest/ent/getListEntBiz.do'),
			// 페이지 표시 객체
			paging:     '#appGridPagination',
			// 검색결과 표시 객체
			result:     '#appGridResult',
			// 검색결과 건수표시 메세지
			resultText: '<em>{total}</em><span>개의 {title}가 있습니다.</span>',
			// 자동로딩여부
			autoload:    true,
			// 모드변경 CLASS
			changeCls:  'shadow-box-1 px-16px pb-24px',			
			// 리스트옵션
			listOptions: {
				// 검색결과 없을경우 메세지
				emptyText:  '검색된 {title}가 없습니다.',
				// 목록칼럼 정의
				colgroup: ['*','120px'],
				columns:  [
					{cls:'app-l app-tooltip-row', name:'bzentyNm', label:'경영체명', formatCell: $.gridUtils.FORMAT.bindOverFld},
					{cls:'app-l', name:'lctnNm', label:'지역 (시도)'},
					//{cls:'app-l', name:'invtFldText'  , label:'투자분야'},
					//{cls:'app-l', name:'bizFldText'   , label:'사업분야'},
					//{cls:'app-r', name:'invtHopeAmt'  , label:'투자희망금액', formatter: $.formatUtil.toInvtHopeAmtFmt},
				],
				select: function(row) {
					detail_gisPop_show(row);
				}
			},
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
	    doSearch: function( params ) {
			_doSearch( params );
	    }
	}, args);
	
	function _doSelect( params ) {
		// 상세보기
		$.formUtil.submitForm(getUrl('/usr/invest/ent/viewEnt.do'), {
			params: params
		});
	};
	
	function _doSearch( params ) {
        // 검색폼 데이터 객체화
        var obj = options.form.serializeObject();
		// 전체가 선택된 경우
		if ($.inArray('', obj['invtFldList']) >= 0)
			obj['invtFldList'] = '';
		// 전체가 선택된 경우
		if ($.inArray('', obj['bizFldList']) >= 0)
			obj['bizFldList'] = '';

		// 투자분야(다중선택)를 배열로 변환
		$.formUtil.setArrayData(obj, 'invtFldList');
		// 사업분야(다중선택)를 배열로 변환
		$.formUtil.setArrayData(obj, 'bizFldList');
		
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

//IR검토의견등록 - 투자설명회 참여경영체 그리드(카드)
$.gridUtils.appEventEntGrid = function ( args ) {
	
	var options = $.extend(true, {

		form : $('#searchForm'),
		
		gridOptions: {
			// 목록 ID
			id:         '#appGrid',
			// 목록 유형
			mode:       'CARD',
			// 목록 KEY필드
			idField:    'bzentyNo',
			// 목록 제목
			title:      '투자설명회 사업계획서',
			// 검색 URL
			url:         getUrl('/usr/mypage/opnn/getListEventEnt.do'),
			// 페이지 표시 객체
			paging:     '#appGridPagination',
			// 검색결과 표시 객체
			result:     false,
			// 툴바영역
			toolbar:    false,
			// 검색결과 건수표시 메세지
			resultText: '<em>{total}</em><span>개의 {title}가 있습니다.</span>',
			//검색후 처리함수
			callback: setDownBtn,
			// 툴바옵션
			toolbarOptions: { },
			// 리스트옵션
			listOptions: {
				// 검색결과 없을경우 메세지
				emptyText:  '검색된 {title}가 없습니다.',
				// 목록칼럼 정의
				columns: [
					{cls:'app-l', name:'bzentyNm'     , label:'경영체명'},
					{cls:'app-l', name:'rprsvNm'  	  , label:'대표자'},
					{cls:'app-l', name:'telno'	      , label:'연락처'},
					{cls:'app-l', name:'emlAddr'	  , label:'이메일'},
					{cls:'app-l', name:'mainBizCn'    , label:'주요사업내용'},
				],
				colgroup: ['*','*','*','240px','120px','*'],
			},
			// 카드옵션
			cardOptions: {
				// 카드선택이벤트
				select: false,
				// 한페이지 표시 갯수
				display: 8,
				// 박스 스타일시트
				boxCls: "card-box-nomar3 h-100 opnnIrEntCard",
				// 열 스타일시트
				lineCls: "col-12 col-sm-6 col-xl-3",
				// 카드포맷
				formatter: function(dom,o) {
					// 대표이미지
					let src = getUrl('/usr/file/linkBizFile.do?sn='+btoa(o['entImgSn']));
					
					// 임시이미지
					let bno = $.commUtil.getRandomNumber(1,10);
					let errSrc = getUrl('/images/app/ENT_IMAGE'+(bno%10)+'.jpg');
					
					dom.append('<img src="'+src+'" class="app-ent-viewimage" alt="경영체썸네일">');
					dom.find('img').attr('onerror', "this.src='"+errSrc+"'");
					
					// 경영체명
					let title = $('<p class="txt1 h-auto pb-16px"></p>');
					title.data('no', o['bzentyNo']);
					title.append(o['bzentyNm']);
					
					let info = $('<div class="info"></div>');
					
					// 하단영역
					info.append($.domUtil.getRow({
								// 대표자
						items: [{colCls: 'col-12', formatHtml: '<p class="txt3"><i class="icon-user"></i><em>대표자</em>'+o['rprsvNm']+'</p>'} 
								// 연락처
						       ,{colCls: 'col-12', formatHtml: '<p class="txt3"><i class="icon-phone"></i><em>연락처</em>'+ $.formatUtil.toPhone(o['telno'])+'</p>'}  
						       // 이메일
						       ,{colCls: 'col-12', formatHtml: '<p class="txt3"><i class="icon-envelope"></i><em>이메일</em>'+o['emlAddr']+'</p>'}  
						       // 주요사업내용
						       ,{colCls: 'col-12', formatHtml: '<p class="txt3"><i class="icon-document"></i><em>주요사업내용</em></p>'}  
						       ,{colCls: 'col-12', formatHtml: '<p class="txt3 align-items-start" style="height:42px">'+o['mainBizCn']+'</p>'}  					       
						]
					}));
					
					let div = $('<div class="btnBox"></div>');
					let btn = $('<button type="button" class="btnDownload btn-primary bs-m w-100"></button>');
					btn.append('<i class="icon-floppy-disk"></i>');
					btn.append('다운로드');
					btn.click(function() {
						$.bizUtils.downloadBizFile({
							fileType: CODE.FILE_TYPE.BIZ,
							docuCd  : CODE.TASK_SE.EVENT,
							docuNo  : o['evntPartcptnNo'],
							fileSe  : '01'
						});
						return false;
					});
					
					let txtBox = $('<div class="txt-box"></div>');
					txtBox.append(title);
					txtBox.append(info);
					dom.append(txtBox, div.append(btn));
				},
				// 빈값메시지
				emptyText: [
					'<p class="fs-24px fw-600 lh-1 mb-2">해당 투자설명회의 경영체 결과가 없습니다.</p>'
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
	    doSearch: function( params ) {
			_doSearch( params );
	    }
	}, args);
	
	function _doSelect(params) {
		
		return false;
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
	
	function setDownBtn() {
		// 조회 시 다운로드 버튼 css 수정
		$('.btnFileDown').html('');
		$('.btnFileDown').append($('<i class="icon-floppy-disk"></i>'));
		$('.btnFileDown').append('다운로드');
	}
	
	_doInit();
		
	return options;
};