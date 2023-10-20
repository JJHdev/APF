/**
******************************************************************************************
*** 파일명    : comm_formatutils.js
*** 설명      : 공통 포맷 / 코드 / 이벤트 관련 JavaScript
***             $.formatUtil
***             $.codeUtil
***             $.eventUtil
***             formInputMask()
***             bindEnter()
***             bindDateHyphen()
***             bindPhoneHyphen()
***             bindMobileHyphen()
***             bindOnlyNumber()
***
*** -----------------------------    Modified Log   --------------------------------------
*** ver             date                author                  description
*** --------------------------------------------------------------------------------------
*** 1.0         2021-02-10              ntarget
******************************************************************************************
**/

$(document).ready(function() {
    // 입력폼 Mask
    formInputMask();
});

/**
 * Form Input Mask 사용. (jquery.inputmask)
 * 
 * @returns
 */
function formInputMask() {
    // 소문자, 대문자
    $('.lowercase').inputmask({casing:'lower'});
    $('.uppercase').inputmask({casing:'upper'});
    // 숫자만 입력 2021.08.20 LSH 추가
    $('.number').inputmask("numeric");
}

// 입력박스 초기화
function clearInput( selector ) {
	$(selector).val('');
	$(selector).focus();
}

// 입력박스 초기화 아이콘 이벤트 
function bindClear() {
	$(this).prev().val('');
	$(this).prev().focus();
}

// [디자인적용] 입력박스 포커스시 클래스 추가/삭제
function bindFocus() {
	$('.ele-icon-box input').focus(function(){
	   $(this).closest("[class^='form-area-box']").addClass('focus');
	});
	$('.ele-icon-box input').focusout(function(){
	   $(this).closest("[class^='form-area-box']").removeClass('focus');
	});
	$(".ele-icon-box > .dropdown").click(function(){
	   $(this).parent().parent().addClass('focus');
	});
}

/**
 * 2021.08.05
 * 텍스트박스 엔터 이벤트 처리
 */
function bindEnter( box, btn ) {
	box.bind('keydown', function(e) {
		if (e.keyCode != 13)
			return;
		// 버튼의 클릭이벤트를 발생시킨다.
		btn.trigger('click');
	});
}

// 날짜 하이픈(-) 삽입 이벤트
function bindDateHyphen( box ) {
	box.bind('keyup', function(e) {
		// @see DELETE 키버튼이 눌리지 않은 경우에만 실행
		if (e.keyCode == 8)
			return false;
		let v = $(this).val();
		// @see 숫자와 하이픈(-)기호의 값만 존재하는 경우 실행
		if (v.replace(/[0-9 \-]/g, "").length != 0) {
	        //alert("숫자 이외의 값은 입력하실 수 없습니다.");
	        //@see 숫자와 하이픈(-)기호 이외의 모든 값은 삭제한다.
			$(this).val(v.replace(/[^0-9 ^\-]/g,""));
	        return false;
		}
	    // @see 하이픈(-)기호를 제거한다.
	    let number = v.replace(/[^0-9]/g,"");
	    // @see 문자열의 길이에 따라 Year, Month, Day 앞에 하이픈(-)기호를 삽입한다.
	    if (number.length < 4) {
	        return number;
	    } 
		else if(number.length < 6) {
			$(this).val(number.substr(0, 4)+"-"+number.substr(4));
	    } 
		else {
			$(this).val(number.substr(0, 4)+"-"+number.substr(4, 2)+"-"+number.substr(6));
	    }
	});
}

// 전화번호 하이픈(-) 삽입 이벤트
function bindPhoneHyphen( box ) {
	box.bind('keyup', function(e) {
		// @see DELETE 키버튼이 눌리지 않은 경우에만 실행
		//if (e.keyCode == 8)
		//	return false;
		let v = $(this).val();
		let r = v.replace(/[^0-9]/g, "")
			     .replace(/(^02|^0505|^1[0-9]{3}|^0[0-9]{2})([0-9]+)?([0-9]{4})$/,"$1-$2-$3")
			     .replace("--", "-");
		$(this).val(r);
		return r;
	});
}

// 휴대전화번호 하이픈(-) 삽입 이벤트
function bindMobileHyphen( box ) {
	box.bind('keyup', function(e) {
		// @see DELETE 키버튼이 눌리지 않은 경우에만 실행
		//if (e.keyCode == 8)
		//	return false;
		let v = $(this).val();
		let r = ""; 
		v = v.replace(/-/gi, "");
		if      (v.length < 4)  r = v; 
		else if (v.length < 7)  r = (v.substr(0,3)+"-"+v.substr(3)); 
		else if (v.length < 11) r = (v.substr(0,3)+"-"+v.substr(3,3)+"-"+v.substr(6));
		else                    r = (v.substr(0,3)+"-"+v.substr(3,4)+"-"+v.substr(7));
		$(this).val(r);
		return r;
	});
}

// 숫자만 입력 이벤트
function bindOnlyNumber( box ) {
	box.bind('keyup', function(e) {
		$(this).val($(this).val().replace(/[^0-9]/g, ""));
		/*
   		if (  (e.keyCode > 48 && e.keyCode < 57 ) 
      		|| e.keyCode == 8  //backspace
      		|| e.keyCode == 37 //방향키 →
			|| e.keyCode == 39 //방향키 ←
      		|| e.keyCode == 46 //delete키
      		|| e.keyCode == 39) {
   		}
		else{
   			e.returnValue=false;
   		}
		*/
	});
}

// 소수점포함한 숫자만 입력 이벤트
function bindOnlyFloat( box ) {
	box.bind('keyup', function(e) {
		$(this).val($(this).val().replace(/[^.0-9]/g, ""));
		/*
   		if (  (e.keyCode > 48 && e.keyCode < 57 ) 
      		|| e.keyCode == 8  //backspace
      		|| e.keyCode == 37 //방향키 →
			|| e.keyCode == 39 //방향키 ←
      		|| e.keyCode == 46 //delete키
      		|| e.keyCode == 39) {
   		}
		else{
   			e.returnValue=false;
   		}
		*/
	});
}
// 금액만 입력 이벤트 (입력박스 벗어날때 포맷처리 포함)
function bindEditMoney( box ) {
	if (box.prop('readonly'))
		return;
	box.bind('focus', function(e) {
		$(this).val($(this).val().replace(/[^0-9]/g, ""));
	});
	box.bind('keyup', function(e) {
		$(this).val($(this).val().replace(/[^0-9]/g, ""));
	});
	box.bind('blur', function(e) {
		$(this).val($.formatUtil.toNumber($(this).val()));
	});
}

// 영문숫자만 입력 이벤트
function bindOnlyAlphaNumeric( box ) {
	box.bind('keyup', function(e) {
		//if (!(e.keyCode >=37 && e.keyCode<=40)) {
		$(this).val($(this).val().replace(/[^A-Za-z0-9]/gi,''));
	});
}

/**
 * 전체 선택 체크박스 클릭시 전체선택/해제 처리 (NAME 기준)
 * NAME 기준 element 가 선택되는 경우 전체선택 해제
 * 2023.06.15 LSH 추가
 */
function bindCheckAll() {
	let form  = $(this).closest('form');
	let name  = $(this).prop('name');
	let value = $(this).prop('value'); 
	let box   = $(this);
	// 전체선택 및 전체해제 처리
	let checked = $(this).is(':checked');
	form.find('[name="'+name+'"]').each(function() {
		$(this).prop('checked', checked);
		if ($(this).val() != value) {
			$(this).off('click').on('click',function() {
				if (box.is(':checked') && $(this).not(':checked')) {
					box.prop('checked', false);
				}
			});
		}
	});
}

/**
 * 전체(ALL)를 제외한 항목 선택시 전체(ALL)항목의 선택값을 해제한다.
 * 2023.06.15 LSH 추가
 */
function bindUnlockAll() {
	let form  = $(this).closest('form');
	let name  = $(this).prop('name');
	let value = $(this).prop('value');
	if (value == 'ALL')
		return;
	form.find('[name="'+name+'"][value="ALL"]').prop('checked', false);
}

// 입력박스 포커스 이동 이벤트
function bindFocusNext( box, nextbox ) {
	box.bind('keyup', function() {
        var charLimit = $(this).attr("maxlength");
        if (this.value.length >= charLimit) {
            nextbox.focus().select();
            return false;
        }
	});
}

/**
 * 포맷관련 유틸
 * 2021.10.15 LSH 추가
 */
$.formatUtil = {
	// yyyymmdd를 yyyy년 mm월 dd일로 변환
	toKorDate: function( s ) {
		if ($.commUtil.empty(s))
			return '';
		let d = s; 
		d = s.replace(/\-/gi, ""); // 구분자 '-' 제거
		d = d.replace(/\./gi, ""); // 구분자 '.' 제거
		
		if (d.length < 4)
			return s;
		
		let r  = d.substring(0,4)+'년';
		if (d.length >= 6)
			r +=(' '+d.substring(4,6)+'월');
		if (d.length == 8)
			r +=(' '+d.substring(6)+'일');
		return r;
	},
	// yyyymm를 yyyy년 mm월로 변환
	toKorMonth: function( s ) {
		if ($.commUtil.empty(s))
			return '';
		let d = s; 
		d = s.replace(/\-/gi, ""); // 구분자 '-' 제거
		d = d.replace(/\./gi, ""); // 구분자 '.' 제거
		if (d.length < 6)
			return '';
		return d.substring(0,4)+'년 '+
		       d.substring(4,6)+'월';
	},
	// yyyymmdd를 yyyy-mm-dd로 변환
	toDashDate: function( s ) {
		if ($.commUtil.empty(s))
			return '';
		let n = s.replace(/-/gi, "");
		
		if (n.length != 8)
			return s;
		return n.substring(0,4)+'-'+
		       n.substring(4,6)+'-'+
		       n.substring(6);
	},
	// 사망여부명칭 (comm_const.js의 STORE 참조)
	toDthNm: function( s ) {
		return STORE.getName('DTH_YN', s);
	},
	// 전화번호 형식화
	toPhone: function( s ) {
		return $.commUtil.phoneFormatter(s);
	},
	// 주민번호 형식화
	toRegNo: function( s ) {
		if (!s)
			return '';
	
	    var s1 = s.replace(/-/g,"");
	    if (s1.length != 13)
			return s;
		return s1.substring(0,6)+'-'+s1.substring(6);
	},
	// 사업자번호 형식화
	toBizNo: function( s ) {
		if (!s)
			return '';
	
	    var s1 = s.replace(/-/g,"");
	    if (s1.length != 10)
			return s;
		return s1.substring(0,3)+'-'+s1.substring(3,5)+'-'+s1.substring(5);
	},
	// 금액 표시 (원)
	toKorMoney: function( s ) {
		return $.formatUtil.toMoney(s)+'원';
	},
	// 숫자 포맷
	toNumber: function( x ) {
		return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	},
	// 숫자 형식화
	toMoney: function( s ) {
		var minus = false;
		if (!s)
			return '0';
			
		if (typeof s == 'number')
			s = s+"";
	    if (s.indexOf("-") != -1)
	        minus = true;
	
	    var sMoney = s.replace(/(,|-)/g,"");
	    var tMoney = "";
	
		if (isNaN(sMoney)){
			return "0";
		}
	
	    var rMoney = "";
	    var rCheck = false;
	    if(sMoney.indexOf(".") != -1){
	        rMoney = sMoney.substring(sMoney.indexOf("."));
	  		sMoney = sMoney.substring(0, sMoney.indexOf("."));
	  		rCheck = true;
	    }
	
	    var len = sMoney.length;
	
	    if ( sMoney.length <= 3 ) return sMoney;
	
	    for(var i = 0; i < len; i++){
	        if(i != 0 && ( i % 3 == len % 3) ) tMoney += ",";
	        if(i < len ) tMoney += sMoney.charAt(i);
	    }
	    if(minus) tMoney = "-" + tMoney;
	    if(rCheck) tMoney = tMoney + rMoney;
	
	    return tMoney;
	},
	toYear: function( s ) { 
		if ($.commUtil.empty(s))
			return '';
		if (/^\d{4}/.test(s))
			return s+'년';
		return s;
	},
	toMonth: function( s ) { 
		if ($.commUtil.empty(s))
			return '';
		if (/^\d{1,2}/.test(s))
			return s+'월';
		return s;
	},
	toDate: function( s ) { 
		if ($.commUtil.empty(s))
			return '';
		if (/^\d{1,2}/.test(s))
			return s+'일';
		return s;
	},
	toFundScale: function(n) {
		let s = $.formatUtil.toFundWon(n);
		let num = s.substring(0, s.search(/[^0-9]/g));
		let han = s.substring(num.length)+'원';
		return '<em class="fs-20px text-primary">'+$.formatUtil.toNumber(num)+'</em>'+han;
	},
	toFundWonObject: function(n) {
		let s = $.formatUtil.toFundWon(n);
		let num = s.substring(0, s.search(/[^0-9]/g));
		let han = s.substring(num.length)+'원';
		return {
			digits: $.formatUtil.toNumber(num),
			word: han
		};
	},
	toFundWon: function( number, words ) {
		
		words = words || ['백만','천만','억']
		
	    var inputNumber  = number < 0 ? false : number;

		let remain = inputNumber;
		let word = words[0];
		for (let i=0; i<words.length; i++) {
			remain = Math.floor(inputNumber / Math.pow(10, [words.length - i - 1]));
			if (remain > 0) {
				word = words[words.length-i-1];
				break;
			}
		}
		return String(remain) + word;
	},
	toKorWon: function( number ) {
		
		var numberFormat = function(x) {
			return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
		};
	    var inputNumber  = number < 0 ? false : number;
	    var unitWords    = ['', '만', '억', '조', '경'];
	    var splitUnit    = 10000;
	    var splitCount   = unitWords.length;
	    var resultArray  = [];
	    var resultString = '';
	
	    for (var i = 0; i < splitCount; i++){
	        var unitResult = (inputNumber % Math.pow(splitUnit, i + 1)) / Math.pow(splitUnit, i);
	        unitResult = Math.floor(unitResult);
	        if (unitResult > 0){
	            resultArray[i] = unitResult;
	        }
	    }
	    for (var i = 0; i < resultArray.length; i++){
	        if(!resultArray[i]) continue;
	        resultString = String(numberFormat(resultArray[i])) + unitWords[i] + resultString;
	    }
	    return resultString;
	},
	toMillion: function( s ) {
		let number      = $.commUtil.nvlInt(s);
	    var inputNumber = number < 0 ? false : number;
	    var splitUnit   = 1000000;
        var unitResult  = (inputNumber % Math.pow(splitUnit, 2)) / Math.pow(splitUnit, 1);
        return Math.floor(unitResult);
	},
	toMillionNumber: function( s ) {
		let num = $.formatUtil.toMillion(s);
		var numberFormat = function(x) {
			return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
		};
        return String(numberFormat(num));
	},
	// yyyymmdd를 yyyy.mm
	toDotYm: function( s ) {
		if ($.commUtil.empty(s))
			return '';
		let d = s; 
		d = d.replace(/\-/gi, ""); // 구분자 '-' 제거
		d = d.replace(/\./gi, ""); // 구분자 '.' 제거
		if (d.length < 6)
			return s;
		return s.substring(0,4)+'.'+s.substring(4,6);
	},
	// yyyymmdd를 yyyy
	toYmdYear: function( s ) {
		if ($.commUtil.empty(s))
			return '';
		if (s.length < 4)
			return s;
		return s.substring(0,4);
	},
	// yyyymmdd를 yyyy.mm.dd로 변환
	toDotDate: function( s ) {
		if ($.commUtil.empty(s))
			return '';
		let n = s.replace(/-/gi, "");
		
		if (n.length != 8)
			return s;
		return n.substring(0,4)+'.'+
		       n.substring(4,6)+'.'+
		       n.substring(6);
	},
	toAge: function( s ) {
		return s+'세';
	},
	toRate: function( s ) {
		return s+'%';
	}
};

/**
 * 공통코드 유틸
 * 2021.10.15 LSH 추가
 */
$.codeUtil = {
	// 사망여부
	dthYn: function(value) {
		return (value == 'Y' ? '사망' : '생존');
	}
};

/**
 * 화면 이벤트 관련 유틸
 * 2021.11.23 LSH 추가
 */
$.eventUtil = {
	// 첨부파일 파일선택 변경시 텍스트박스에 파일명을 표시해주는 이벤트
	fileAttach: function( selector , maxcount ) {
		
		let elm = selector || '.file_wrap';
		let max = maxcount || 10;
		let frm = $(elm).closest('form');
		
		$(elm).on('change','.input_file', function() {
			// 파일명만 추출한다.
			var fname = $(this).val().split("\\").pop();
			// 텍스트박스에 셋팅한다.
			$(this).closest(elm).find('.input_text').val(fname);
		});
		
		// 파일선택 추가 이벤트
		$(elm).on("click", '.btn_add', function() {
			var oform = $(this).closest(elm);
			// 최대갯수 체크
			if (frm.find('.input_file').length >= max) {
				$.commMsg.alert('추가할 최대 갯수는 '+max+'개 입니다.');
				return false;
			}
			// 객체복사
			var cform = oform.clone(true).hide();
			cform.find('input[name="fileNo"]'  ).val("");
			cform.find('input[name="fileName"]').val("");
			cform.end().find('.input_file').off("change");
			cform.insertAfter(oform);
			cform.fadeIn(200);
			return false;
		});
		// 파일선택 삭제 이벤트
		$(elm).on("click", '.btn_del', function() {
			var oform = $(this).closest(elm);
			var title = oform.find('.input_text').attr("title");
			var len   = oform.parent().find('.input_text'+"[title='" + title + "']").length;
			var obox  = oform.find('.input_text');
			if (len <= 1) {
				$.commMsg.alert('단일 항목은 삭제할 수 없습니다.');
				return false;
			}
			// 화면에서 파일항목을 삭제한다.
			var fnDelete = function() {
				oform.fadeOut(200, function() { oform.remove(); });
				return false;
			};
			if (obox.val() === "") {
				fnDelete();
				return false;
			}
			if (confirm('파일을 삭제하시겠습니까?')) {
				fnDelete();
				return false;
			}
			return false;
		});
					
	},
	// 탭클릭 이벤트 처리
	tabClick: function( selector, index, callback, triggerAll ) {

		let elm = selector || '.boxWrap';
		let idx = index    || 0;

		$(elm+" > .tabWrap li").on("click",function(){
			var i = $(this).index()
			$(this).parent().find("li").removeClass("on");
			$(this).addClass("on");
			$(elm+' > .tabInner > ul > li').removeClass("on");
			$(elm+' > .tabInner > ul > li').eq(i).addClass("on");
			if (callback) {
				callback($(this), i);
			}
		})
		if (triggerAll) {
			$(elm+" > .tabWrap li").each(function() {
				$(this).trigger('click');
			});
		}
		// 첫번째 탭 클릭 이벤트 발생
		$(elm+" > .tabWrap li").eq(idx).trigger('click');
	},
	// TEXTAREA 자동높이조절
	autoTextarea: function() {
		$('.app-textarea').on( 'keyup', 'textarea', function (e){
			$(this).css('height', 'auto' );
			$(this).height( this.scrollHeight );
  		});
  		$('.app-textarea').find( 'textarea' ).keyup();
	},
	// TEXTAREA 입력문자열 길이표시
	showStringLength: function( textSelector, showSelector, maxLength ) {
		$(textSelector).on( 'keyup', function (e){
			let s = $(this).val();
			$(showSelector).html( s.length + ' / ' + maxLength);
  		});
	},
	
};

