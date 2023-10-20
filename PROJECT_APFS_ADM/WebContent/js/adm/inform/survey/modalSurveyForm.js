/**
*******************************************************************************
***    명칭: modalSurveyForm.jsp
***    설명: 설문관리 - 설문신규등록 화면
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.07.13    KYW        First Coding.
*******************************************************************************
**/
$(function() {
	//========================================================//
    // 화면 스크립트 내에 사용할 객체,상수 정의
    //--------------------------------------------------------//
	let P_RFORM		= $('#registForm');   	// 등록 FORM
	let P_MODE      = $('#mode'   ).val();	// 처리 모드
	let QT_NUM 		= $('#qtNum').val();	// 문항수
	let SRVY_NO		= $('#srvyNo').val();	// 설문번호
	let QT_TYPE 	= 'QT1';				// 설문유형
    //========================================================//
    // 콤보박스 정의
    //--------------------------------------------------------//
	// 분류 구분
	$('#srchTrgtModal').appComboBox({
		url: getUrl('/com/common/getComboCode.do'),
		prompt: '설문대상 분류코드',
		params: { 'upCdId': 'CT.QSTNN_TRGT' }, // 파라미터 설정
		loadFilter: function(data) {
			data.unshift({code: '', text: '전체'}); //첫번째 배열에 전체 추가
			return data;
		},
	});
	
	
	setQtBox(QT_NUM, P_MODE);
	if(P_MODE == MODE.UPDATE) {
       	$.ajaxUtil.ajaxLoad(
            getUrl('/adm/inform/survey/getListSurveyInfo.do'), 
            {srvyNo   : SRVY_NO}, 
            function(ret) {
                $.ajaxUtil.success(ret, function() {
					var data = ret.Data;
                    var list = getDataList(data);
					makeUpdateForm(list);
                });
            }
        );
		setDisabled();
	}
	
	
	// 수정불가형태로 바꾸기
	function setDisabled() {
		document.querySelectorAll('.srvy-info').forEach(function(e) {
			e.disabled = true;
			/*if(e.tagName == 'INPUT' ) { //date-picker disabled
				e.readOnly = true;
				if(e.classList.contains('datepicker-input'))
					$(e).datepicker('destroy');
			} else {
				e.disabled = true;
			}*/
		});
	}
	
	// 수정 형식으로 바꾸기
	function makeUpdateForm(list) {
		list.forEach(function(e, i) {
			var qtContBox = $('.contentBox').children('.qtContBox').eq(i);
			// 설문유형 설정
			qtContBox.find('.qtType').val(e.qitemType).prop('selected', true);
			QT_TYPE = e.qitemType;
			makeCont(qtContBox, e.artclCn.length);
			
			setDataValue(qtContBox, e);
		})
		
	}
	
	//  데이터 맞게 입력
	function setDataValue(qtContBox, el) {
		Object.keys(el).forEach(function(e) {
			var elem = qtContBox.find('input[name^="' + e + '"]');
			if(Array.isArray(el[e])) {
				if(e == 'artclNo')
					elem = qtContBox.find('input[name="' + e + 'Arr"]');
				el[e].forEach(function(item, i) {
					elem.eq(i).val(item);
					if(item.length == 0) {// 기타 설정
						var inputRow = elem.eq(i).closest('.inputRow');
						setEtcRow(inputRow);
						inputRow.siblings('.btnRow').find('.addEtc').attr('disabled', true); //기타 버튼 클릭
					}
				})		
			} else {		
				elem.val(el[e]);
			}
		});
		
    	return false;
	};
	
	// 설문 데이터 틀 수정
	function getDataList(data) {
		var list = [];
		var dataMap = new Object();
		var artclNoList = [];
		var artclCnList = [];
		
		data.forEach(function(e, i) {
			if(e.artclMarkNo == 1){ //새로운 값 dataMap 생성
				if(i > 0 ) {
					list.push(dataMap);
					dataMap = {}; //초기화
					artclNoList = [];
					artclCnList = [];
				} 
				dataMap['qitemNo'] = e.qitemNo;
				dataMap['qitemCn'] = e.qitemCn;
				dataMap['qitemType'] = e.qitemType;
				dataMap['artclNo'] = artclNoList;
				dataMap['artclCn'] = artclCnList;
			}
			artclNoList.push(e.artclNo);
			artclCnList.push(e.artclCn);
		})
		list.push(dataMap);
		return list;
	}
	
	function setQtBox(num, mode) {
		for(let i=0; i<num; i++) {
			var box = makeContTitle(i);
			$('.contentBox').append(box);
			if(mode==MODE.INSERT) makeCont(box, 3);
		} 
		
		// 설문유형 구분
		$('.qtTypeBox').appComboBox({
			rows: [
		        {code: 'QT1',   text: '객관식'},
		        {code: 'QT2',  	text: '다중선택'},
		        {code: 'QT3', 	text: '서술형'},
		    ],
		    type: 'static',
			wrapCls: true,
			name: 'qitemType',
			boxCls: 'qtType srvy-info',
			change: doChange
		});
		$('.qtTypeBox').append($('<i class="icon-angle-down"></i>'));
	}

	// 설문문제, 문항 동적 생성
	//-------------------------------------------------------------------------------------------------------------//
	
	function makeContTitle(i) {
		// 전체 틀
		var box = $('<div class="box mb-24px qtContBox"></div>');
		box.append($('<div class="box-in-box py-16px"><div class="input-grid-2" data-num="'+i+'"></div></div>'));
		i++;
		// Q.i 설문유형
		var type = $.domUtil.getRow({
			rowCls: 'row mb-16px',
			items: [{
					formatHtml: '<label for="" class="bs-m btn-primary fs-18px px-18px">Q.'+ i +'</label>'
				},{
					cls: 'flex-b143px',
					formatHtml: '<div class="form-area-box1"><label>설문유형 </label><div class="ele-icon-box qtTypeBox"></div></div>',
				}
			]
		});
		
		// 문항제목
		var title = $('<div class="row mb-16px"><div class="col-12"></div></div>');
		title.find('.col-12').append($('<div class="form-area-box1"><label>문항제목</label><div class="ele-icon-box"></div></div>'));
		title.find('.ele-icon-box').append($('<input type="text" name="qitemCn" placeholder="제목입력"><i class="icon-times-circle F text-ele-TextDisable emptyText"></i>'));
		
		box.find('.input-grid-2').append(type, title);		
		
		return box;
	}
	
	// 설문 내용 추가
	function makeCont(box, aNum) {
		// 문항내용
		var content = makeContByType(QT_TYPE, aNum);
		box.find('.input-grid-2').append(content);
		
	}
	
	//설문유형에 따른 형식
	function makeContByType(qtType, aNum) {
		// 문항내용
		var content = $('<div class="row mb-16px innerCont"><div class="col-12 pb-8px"></div></div>');
		switch(qtType) {
			case 'QT1': case 'QT2':
				qtType=='QT1' ? qtType='radio' : qtType='checkbox';
				
				for(let j=0; j<aNum; j++) { // default 항목 3개 
					var inputRow = makeInputRow(qtType); //일반 요소추가
					content.find('.col-12').append(inputRow);
				}
				
				var bottomBtn = $('<div class="row content-none mb-0 mt-4 right-border-ver btnRow"><div class="col pb-0 align-self-center"></div></div>');
				
				var btnElem = $('<button>').addClass('btn-combi-3 bs-s addElem').append('<i class="icon-plus"></i>요소 추가');
				btnElem.bind('click', addElem);
				var btnEtc = $('<button>').addClass('btn-combi-3 bs-s addEtc').append('<i class="icon-plus"></i>기타 추가');
				btnEtc.bind('click', addEtc);
				
				var buttons = $.domUtil.getRow({
					rowCls: 'row interve-3',
					colCls: 'col flex-grow-0 white-space-nowrap',
					items: [{
							formatHtml: btnElem
						},{
							formatHtml: btnEtc,
						}
					]
				});
				bottomBtn.find('.col').append(buttons);
				content.find('.col-12').append(bottomBtn);
			break;
			
			case 'QT3':
				var input = $('<textarea class="w-100" name="artclCn" style="height:100px; resize:none;" placeholder="서술형을 입력해주세요." readonly></textarea>');
				var checkLen = $('<div class="lenBox text-primary" style="font-size:12px;"><span class="txtLen text-primary" style="font-size:12px;">0</span>/200자</div>');

				content.find('.col-12').append(input, checkLen);
			break;
		}
		
		return content;
	}
	
	function makeInputRow(qtType) { // input-radio,checkbox + 입력textbox + 삭제버튼 1
		qtType == 'QT1' || qtType == 'radio' ? qtType='radio' : qtType='checkbox';
		return $.domUtil.getRow({
					rowCls: 'row interve-3 align-items-center inputRow',
					items: [{
							cls: 'flex-grow-0',
							formatHtml: '<div class="check-radio-box only-ver"><input type="'+qtType+'" name="" id=""><label for=""></label></div>'
						},{
							formatHtml: '<div class="form-area-box1"><div class="ele-icon-box"><input type="text" class="elemTxt" name="artclCn" placeholder="답변항목"></div></div>',
						},{
							cls: 'flex-grow-0',
							formatHtml: '<button class="bs-m btn-black px-9px btnDelRow"><i class="icon-trash"></i></button>'
						}
					]
				});
	}
	//-------------------------------------------------------------------------------------------------------------//
	
	// 동적 생성 버튼 클릭 이벤트
	//-------------------------------------------------------------------------------------------------------------//
	// 행 삭제 버튼 클릭 이벤트 
	$(document).on('click', '.btnDelRow', function() {
		var rowCls = $(this).closest('.row');
		var rowNum = $(this).closest('.row').siblings('.inputRow').length; // 나를 제외한 inputRow 개수 
		
		// 기타추가 버튼 활성화
		if(rowCls.attr("class").indexOf('etcRow') > 0) { // 기타 행일때
			rowCls.closest('.addEtc').attr('disabled', false);
			rowCls.next().find('.addEtc').attr('disabled', false);	
		}
		if(rowNum > 0)
			rowCls.remove(); // 해당 행 삭제
		
	});
	
	// 요소 추가 버튼 클릭 이벤트
	function addElem() {
		var qtType = $(this).closest('.qtContBox').find('.qtType').val();
		var inputRow = makeInputRow(qtType);
		var btnRow = $(this).closest('.btnRow');
		
		btnRow.siblings('.inputRow:last').after(inputRow); //기타 있을때 기타 앞에 추가됨
	};
	// 기타 추가 버튼 클릭 이벤트
	function addEtc() {
		var qtType = $(this).closest('.qtContBox').find('.qtType').val();
		var inputRow = makeInputRow(qtType);
		var btnRow = $(this).closest('.btnRow');
		
		setEtcRow(inputRow);
		btnRow.before(inputRow); //추가버튼 위에 추가
		$(this).attr('disabled', true); //기타 버튼 클릭
		
	};
	
	function setEtcRow(inputRow) {
		inputRow.addClass('etcRow'); // 기타 행 클래스 구분
		inputRow.removeClass('inputRow'); // 기존 클래스 삭제
		// 버튼 클릭불가, 설명 수정
		inputRow.find('.elemTxt').attr('disabled', true);
		inputRow.find('.elemTxt').attr('placeholder', '기타...');
	}
	//-------------------------------------------------------------------------------------------------------------//
	
	// 설문유형 change 이벤트
	function doChange() {
		var num = $('.qtContBox .qtType').index(this);
		var box = $('.qtContBox').eq(num);
		box.find('.innerCont').remove();
		QT_TYPE = $(this).val();
		
		makeCont(box, 3);
	};
	
	// 서술형 입력시 textarea 글자수 체크
	/*$(document).on('keyup', '.txtBox', function() {
		let txtLen = $(this).val().length;
		$(this).next().find('.txtLen').text(txtLen);
	});*/
	
	// 문항수 change 이벤트
	$('#qtNum').bind('change', function() {
		$('.qtContBox').remove();
		QT_NUM = $(this).val();
		QT_TYPE = 'QT1'
		setQtBox(QT_NUM, MODE.INSERT);
	});
	
	// 제목 x 클릭시 이벤트
	$('.emptyText').bind('click', function() {
		$(this).prev().val('');
		$(this).prev().focus();
	});
	
	//jquery validator 메서드 추가
	jQuery.validator.addMethod("greaterThan", function(value, element, params) {
        if (!/Invalid|NaN/.test(new Date(value))) {
            return new Date(value) >= new Date($(params).val());
        }
        return isNaN(value) && isNaN($(params).val()) 
            || (Number(value) >= Number($(params).val())); 
    },'종료 날짜는 시작 날짜보다 이후이어야 합니다.');
	
	//========================================================//
    // VALIDATION RULE 정의
    //--------------------------------------------------------//
    P_RFORM.validate({
        // true일 경우 디버깅이 가능하도록 
        // 입력값이 유효해서 submit하지 않는다.
        debug: false,
        // true일 경우 포커스가 떠날때 유효성 검사를 한다.
        onfocusout: false,
        // true일 경우 유효성체크없이 무조건 submit한다.
        onsubmit: false,
        // 검증룰 정의
        rules: {
	            srvyCn     : {required: true,
	    					  maxlength: 1000},
                srvyBgngYmd: {required: true},
    			srvyEndYmd : {required: true,
							  greaterThan: '#srvyBgngYmd'},
    			qitemCn    : {required: true,
	    					  maxlength: 200},
        },
        // 검증메세지 정의
        messages: {
	            srvyCn     : {required: '설문제목은 필수 입력 사항입니다.',
	    					  maxlength: jQuery.validator.format('제목은 최대 {0}자 이하 입니다.')},
                srvyBgngYmd: '설문기간은 필수 입력 사항입니다.',
    			srvyEndYmd : {required: '설문기간은 필수 입력 사항입니다.',
	    					  greaterThan	: '종료 날짜는 시작 날짜보다 이후이어야 합니다.'},
    			qitemCn    : {required: '문항제목을 입력해주세요.'},
        },
        // 에러발생시 에러메세지를 처리할 핸들러 (comm_biz.js에 정의)
        invalidHandler: validateHandler,
        // 에러발생시 에러메세지를 표시할 위치처리 핸들러 (comm_biz.js에 정의)
        errorPlacement: validatePlacement
    });
	function checkValid() {
		var elem = $('.inputRow').find('[name="artclCn"]');
		for(let i=0; i<elem.length; i++) {
			if(elem[i].value.trim().length == 0) {
				return true;
			}
		}
		return false;
	}
	
	function setJsonArr() {
		var qitemType = [];
		var qitemCn = new Object();
		let artclCn = new Object();
		document.querySelectorAll('.qtContBox').forEach(function(e, i) {
			var artclArr = new Array();
			qitemType.push(e.querySelector('[name="qitemType"]').value);
			
			e.querySelectorAll('input[name="qitemCn"]').forEach(function(elem, k) {
				qitemCn['qitemCn'+i] = elem.value;
			});
			
			e.querySelectorAll('[name="artclCn"]').forEach(function(f, j) {
				artclArr.push(f.value);
			});
			artclCn['artclCn'+i] = artclArr;
		});
		
		$.formUtil.setValue(P_RFORM, 'artclCnArr', JSON.stringify(artclCn));
		$.formUtil.setValue(P_RFORM, 'qitemCnArr', JSON.stringify(qitemCn));
		$.formUtil.setValue(P_RFORM, 'qitemTypeArr', qitemType);
	}
	// 저장버튼 클릭시 이벤트 처리
    //--------------------------------------------------------//
    $('#btnSave').bind('click', function() {
        // 등록폼의 VALIDATION 기능 활성화
		P_RFORM.validate().settings;

        //FORM VALIDATION
        if (P_RFORM.valid() === false)
            return false;
		
		// 문항 validation 체크
		if(checkValid()) {
			$.commMsg.alert("답변항목을 입력해주세요.");
			return false;
		}

		let msg = "설문 내용을 수정하시겠습니까?";
		if (P_MODE == MODE.INSERT)
			msg = "설문을 등록하시겠습니까?";
	
		// 문항제목 배열, 항목 json객체 만들어서 값 넣기
		setJsonArr();
		
    	$.commMsg.confirm(msg, function() {
            // 등록폼을 AJAX로 저장처리
            P_RFORM.ajaxForm({
                url: getUrl('/adm/inform/survey/saveSurvey.do'),
                enctype : 'multipart/form-data',
                // 오류시 처리로직
                error: $.ajaxUtil.error,
                // 저장후 처리로직
                success: function(ret) {
                	$.ajaxUtil.success(ret, function(data) {
                        // 목록으로 이동
                    	$.commMsg.alert('성공적으로 저장되었습니다.', function() {
							// 부모창의 목록재검색 및 팝업닫기
							P_MODAL.doSearch();
							return false;
						});
                	});
                }
            }).submit();
    	});
        return false;
	});
});
