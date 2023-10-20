/**
*******************************************************************************
*** 파일명    : app_entir.js
*** 설명      : 경영체 - IR작성하기 관련 컨트롤
***
*** -----------------------------    Modified Log   ---------------------------
*** ver             date                author                  description
*** ---------------------------------------------------------------------------
*** 1.0         2023.04.20              LSH
*******************************************************************************
**/

// IR작성하기 입력정보
//=============================================================================
$.fn.appEntTabs = function( args ) {
	
	let options = $.extend({
		admin   : false,
		contId  : 'appContent'
	}, args);
		
    //========================================================//
    // 구분 탭 생성
    //--------------------------------------------------------//
	$(this).appBbsTabs({
		wrapCls : 'shadow-box-1 mb-24px px-6px px-md-24px',
		items   : [
			{code: CODE.MYPG_ENTIR_SE.DASH, text: '데이터 대시보드'},
			{code: CODE.MYPG_ENTIR_SE.INFO, text: '사업계획서 및 홍보자료'}
		],
		value   : $('#pageCd').val(),
		select  : function(v) {
			
			$('#pageCd').val(v);
		    //========================================================//
		    // 입력항목 생성
		    //--------------------------------------------------------//
			$('#'+options.contId).appEntIr({
				admin    : options.admin,
				pageCd   : v, // 페이지구분코드
				bzentyNo : $('#bzentyNo').val(), 
				irNo     : $('#irNo'    ).val(),
				updateYn : $('#updateYn').val(),
				selectYn : $('#selectYn').val()
			});
		}
	});
	return this;
};

// IR작성하기 입력정보
//=============================================================================
$.fn.appEntIr = function( args ) {
	
	let options = $.extend({
		mode    : MODE.UPDATE,
		admin   : false,  // 관리자여부
		pageCd  : false,  // 페이지구분
		bzentyNo: false,  // 업체번호
		irNo    : false,  // IR번호
		updateYn: false,
		selectYn: false
	}, args);
	
	let _this = $(this);

	_this.html('');
	
	// 업체기본정보 조회
	$.ajaxUtil.ajaxLoad(
		// 조회 URL
        getUrl('/usr/invest/ent/getEntAll.do'),
		// 조회 조건
		{	mode     : options.mode, 
			pageCd   : options.pageCd,
			bzentyNo : options.bzentyNo,
			irNo     : options.irNo
		},
		// 조회 콜백
        function(result) {
            var data = result.Data;
            if (data) {
				let params = $.extend({
					data   : data,
					params : {bzentyNo: data['bzentyNo'], irNo: data['irNo']},
					mode   : options.mode,
					admin  : options.admin,
					url    : {
						openUrl     : getUrl('/usr/mypage/ent/openEntIr.do'),
						saveInfoUrl : getUrl('/usr/mypage/ent/saveEntIr.do'),
						saveFileUrl : getUrl('/usr/mypage/ent/saveEntIrFile.do'),
						savePopupUrl: getUrl('/usr/mypage/ent/saveEntOthsptHst.do'),
					}
				}, options);
				if (options.admin) {
					$.extend(params.url, {
						openUrl     : getUrl('/usr/invest/ent/openEntIr.do'),
						saveInfoUrl : getUrl('/usr/invest/ent/saveEntIr.do'),
						saveFileUrl : getUrl('/usr/invest/ent/saveEntIrFile.do'),
						savePopupUrl: getUrl('/usr/invest/ent/saveEntOthsptHst.do'),
					});
				}
				
				if (options.pageCd == CODE.MYPG_ENTIR_SE.DASH) {
					// 대시보드 생성
					_this.appEntDashboard(params);
				}
				else {
					// 상세정보 생성
					_this.appEntMoreInfo(params);
				}
            }
        }
    );
	return this;
};

// 대시보드 입력정보
//=============================================================================
$.fn.appEntDashboard = function( args ) {
	
	let options = $.extend(true, {
		admin   : false,  // 관리자여부
		mode    : false,  // 저장모드
		data    : false,  // 업체정보
		params  : false,  // 조회조건
		pageCd  : false,  // 페이지구분
		bzentyNo: false,  // 업체번호
		updateYn: false,
		selectYn: false,
		url     : {
			openUrl     : false,
			saveInfoUrl : false,
			saveFileUrl : false,
			savePopupUrl: false
		},
		buttons    : [{
			data   : {oper:'CANCEL'},
			cls    : 'btn-combi-3 w-100 bs-xl',
			icon   : 'icon-times',
			value  : '취소',
			click  : 'doClick'
		},{	data   : {oper:'NEXT'},
			cls    : 'btn-primary w-100 bs-xl',
			iconr  : 'icon-arrow-right',
			value  : '다음',
			click  : 'doClick'
		}],
	}, args);
	
	let _this  = $(this);
	let _cards = {};

	let _functions = {
		
		// 취소, 다음 버튼 클릭
	    //--------------------------------------------------------//
		doClick: function() {
			let c = $(this).data('oper');
			if      (c == 'CANCEL') _functions.doCancel();
			else if (c == 'NEXT'  ) _functions.doSave();
			return false;
		},

	    // 취소하기
	    //--------------------------------------------------------//
		doCancel: function() {
	        $.formUtil.submitForm(options.url.openUrl,{});
			return false;
		},

	    // 필수검증
	    //--------------------------------------------------------//
		doValidate: function() {
			let data = {};
			// 카드별 검증
			for (let key in ENTCARD) {
				let obj = ENTCARD[key];
				if (!obj.validate)
					continue;
				
				let c = _cards[key];
				let d = c.doValidate();
				if (!d) {
					return false;
				}
				$.extend(data, d);
			}
			return data;
		},

	    // 저장하기
	    //--------------------------------------------------------//
		doSave: function() {
			
			let data = _functions.doValidate();
			if (!data)
				return false;
			$.extend(data, options.params);
			$.extend(data, {
				mode: options.mode,
				act : ACT.IR_INFO
			});
	        $.commMsg.confirm("저장하시겠습니까?", function() {
	            // AJAX로 저장처리
	            $.ajaxUtil.ajaxSave(
	                options.url.saveInfoUrl, 
	                JSON.stringify(data),
	                function(ret) {
	                    $.ajaxUtil.success(ret, function() {
							$.extend(options.params, {pageCd: CODE.MYPG_ENTIR_SE.INFO})
							$.formUtil.submitForm(options.url.openUrl, {params: options.params});
	                    });
	                }
	            );
	        });
	        return false;				
		}
	};
	
	// 카드 생성
	$.each(ENTCARD, function(key, obj) {
		let p = $.extend({
			cls     : 'mb-32px',
			mode    : options.mode,   // 저장모드
			data    : options.data,   // 업체정보
			params  : options.params, // 조회조건
			updateYn: options.updateYn,
			selectYn: options.selectYn
		}, obj.params);
		
		if (key == 'SPHT' && options.admin) {
			p.saveUrl = options.url.savePopupUrl;
		}
		_cards[key] = $('<div id="'+obj.id+'"></div>')[obj.widget](p);
		_this.append(_cards[key]);
	});
	// 차트 생성
	$.each(ENTCARD, function(key, obj) {
		if (obj.chart) {
			_cards[key].createChart();
		}
	});

	// 버튼목록 정의
	if (options.updateYn == 'Y') {
		_this.append(
			$('<div></div>').appButtons({
				buttons   : options.buttons,
				functions : _functions
			})
		);
	}

	return this;
};

// 상세정보 입력정보
//=============================================================================
$.fn.appEntMoreInfo = function( args ) {
	
	let options = $.extend(true, {
		cls          : 'mb-32px',
		admin        : false,
		mode         : MODE.VIEW,
		data         : false,
		params       : false,
		formId       : 'entMoreForm',
		updateYn     : false,
		selectYn     : false,
		url          : {
			openUrl     : false,
			saveInfoUrl : false,
			saveFileUrl : false,
			savePopupUrl: false
		},
		buttons      : [
			// LEFT BUTTON
			[],
			// CENTER BUTTON
			[{	data   : {oper:'CANCEL'},
				cls    : 'btn-combi-3 w-100 bs-xl',
				icon   : 'icon-times',
				value  : '취소',
				click  : 'doClick'
			},{	data   : {oper:'SAVE'},
				cls    : 'btn-primary w-100 bs-xl',
				icon   : 'icon-floppy-disk',
				value  : '저장',
				click  : 'doClick'
			}],
			// RIGHT BUTTON
			[{	data   : {oper:'PREVIEW'},
				cls    : 'align-items-center bg-black-t5 border-0 bs-xl d-inline-flex fs-15px fw-600',
				icon   : 'fs-18px icon-eye',
				value  : '미리보기',
				click  : 'doClick'
			}]
		],
		cardOptions  : {
			boxCls   : 'shadow-box-1 p-32px',
			groupCls : 'grid-ratio-1',
			wrapCls  : 'form-area-box',
			autoload : true,
			title    : false
		}
	}, args);
	
	let _this = $(this);
	let _data = false;
	let _card = false;
	let _form = false;
	
	let _functions = {
		
		// 취소, 저장, 미리보기 버튼 클릭
	    //--------------------------------------------------------//
		doClick: function() {
			let c = $(this).data('oper');
			if      (c == 'CANCEL' ) _functions.doCancel();
			else if (c == 'SAVE'   ) _functions.doSave();
			else if (c == 'PREVIEW') _functions.doPreview();
			return false;
		},

	    // 취소하기
	    //--------------------------------------------------------//
		doCancel: function() {
	        $.formUtil.submitForm(options.url.openUrl,{});
			return false;
		},

	    // 미리보기
	    //--------------------------------------------------------//
		doPreview: function() {
			$('<div></div>').appPopupEntPreview({
				params: options.params
			});
			return false;
		},

	    // 필수검증
	    //--------------------------------------------------------//
		doValidate: function() {
			// 첨부파일 VALIDATION
			if (!$('#appPlanFile').appEntFile('validate', {isAlert: true, isLimit: true, isExt: true})) {
	            return false;
			}
			if (!$('#appAdocFile').appEntFile('validate', {isAlert: true, isLimit: true, isExt: true})) {
	            return false;
			}
			if (options.admin) {
				if (!$('#appThumbnail').appEntFile('validate', {isAlert: true, isLimit: true, isExt: true})) {
		            return false;
				}
			}
			return true;
		},

	    // 필수검증 및 저장하기
	    //--------------------------------------------------------//
		doSave: function() {

			if (!_functions.doValidate())
				return false;
				
	    	$.commMsg.confirm("저장하시겠습니까?", function() {
				// 등록폼을 AJAX로 저장처리
				_form.ajaxForm({
	                url: options.url.saveFileUrl, 
	                enctype : 'multipart/form-data',
	                error: $.ajaxUtil.error,
	                success: function(ret) {
	                	$.ajaxUtil.success(ret, function() {
	                    	$.commMsg.alert('성공적으로 저장되었습니다.', function() {
								$.formUtil.submitForm(options.url.openUrl, {params: options.params});
							});
	                	});
	                }
	            }).submit();
	    	});
	        return false;				
		},
		// 카드항목생성
	    //--------------------------------------------------------//
		getItems: function() {
			let arr = [{
				cls      : 'col-12 pb-24px', 
				name     : 'planFile', 
				label    : '사업계획서',
				comment  : {icon:true, text:'100MB 이내 1개 파일 업로드 가능'},
				inputHtml: function() {
					return $('<div id="appPlanFile"></div>').appEntFile({
						mode        : options.mode,
						label       : false,
						title       : '사업계획서',
						formbtn     : true,
						initData    : {
				            fileType: CODE.FILE_TYPE.ENT,
							docuCd  : CODE.FILE_SE.PLAN,
				            docuNo  : options.bzentyNo
						}
					}).appEntFile('init');
				}
			},{	cls      : 'col-12 pb-24px', 
				name     : 'adocFile', 
				label    : '첨부자료',
				comment  : {icon:true, text:'100MB 이내 1개 파일 업로드 가능, 여러 파일 업로드 희망시 압축파일 권장'},
				inputHtml: function() {
					return $('<div id="appAdocFile"></div>').appEntFile({
						mode        : options.mode,
						label       : false,
						title       : '첨부자료',
						initData    : {
				            fileType: CODE.FILE_TYPE.ENT,
							docuCd  : CODE.FILE_SE.ADOC,
				            docuNo  : options.bzentyNo
						}
					}).appEntFile('init');
				}
			},{	cls      : 'col-12 pb-24px', 
				name     : 'prVidoUrl', 
				label    : '홍보영상',
				comment  : {icon:true, text:'홍보 영상은 유튜브 URL로 입력이 가능하며, 영상 내 동영상 URL 공유하기의 주소를 입력합니다.'},
				input    : {id:'prVidoUrl', name:'prVidoUrl', type:'text', maxlength: 100, value: options.data['prVidoUrl']},
				hidden   :[{type:'hidden', name:'bzentyNo'}
				          ,{type:'hidden', name:'irNo'}]
			}];
			if (options.admin) {
				arr.push({
					cls      : 'col-12 pb-24px', 
					name     : 'thmbFile', 
					label    : '썸네일',
					must     : true,
					comment  : {icon:true, text:'JPG,PNG,BMP만 가능, 총 100MB 이내 3개까지 등록 가능'},
					inputHtml: function() {
						return $('<div id="appThumbnail"></div>').appEntFile({
							mode        : options.mode,
							label       : false,
							title       : '썸네일',
							maxCount    : 3,
							multiple    : true,
							extensions  : ['jpg','bmp','png'],
							extra       : {type: 'radio', name: 'rprsYn', label: '대표이미지'},
							initData    : {
					            fileType: CODE.FILE_TYPE.ENT,
								docuCd  : CODE.FILE_SE.IMGE,
					            docuNo  : options.bzentyNo
							}
						}).appEntFile('init');
					}
				});
			}
			return arr;
		}
	};
	
	_data = $.extend({}, options.data);
	_card = $('<div></div>').appFormCard({
		formId      : options.formId,
		cls         : options.cls,
		boxCls      : options.cardOptions.boxCls  ,
		groupCls    : options.cardOptions.groupCls,
		wrapCls     : options.cardOptions.wrapCls ,
		autoload    : options.cardOptions.autoload,
		title       : options.cardOptions.title   ,
		defaultValue: STRING.NOINFO,
		data        : _data,
		items       : [{items: _functions.getItems()}]
	});
	_this.append(_card);
	// 폼정의
	_form = _card.getForm();
	

	// 버튼목록 정의
	if (options.updateYn == 'Y') {
		_this.append( 
			$('<div></div>').appButtons({
				cls       : 'ver1',
				buttons   : options.buttons,
				functions : _functions
			})
		);
	}
	return this;
};
