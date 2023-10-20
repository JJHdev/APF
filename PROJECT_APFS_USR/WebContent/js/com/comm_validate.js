/**
******************************************************************************************
*** 파일명    : comm_validate.js
*** 설명      : jQuery Validate 관련 유틸 함수
***
*** -----------------------------    Modified Log   --------------------------------------
*** ver             date                author                  description
*** --------------------------------------------------------------------------------------
*** 1.0         2021-10-20              LSH
******************************************************************************************
**/

/**
 * 검증관련 유틸
 * 2021.09.24 LSH 추가
 */
$.validateUtil = {
	// 전화번호 검증
	phone: function( value ) {
		value = value.replace(/\(|\)|\s+|-/g,'');
		if (value.length > 9 && value.match(/^(0[0-9]{2,3})-?[0-9]{3,4}-?[0-9]{4}$/))
			return "TRUE";
		else
			return "전화번호를 정확하게 입력하세요.";
	},
	// 휴대전화번호 검증
	mobile: function( value ) {
		value = value.replace(/\(|\)|\s+|-/g,'');
		if (value.length > 9 && value.match(/^(01[016789]{1})-?[0-9]{3,4}-?[0-9]{4}$/))
			return "TRUE";
		else
			return "휴대전화를 정확하게 입력하세요.";
	},
	// 날짜형식(YYYYMMDD) 검증
	date: function( value ) {
		if (value.length == 8 && value.match(/^\d{4}((0\d)|(1[012]))(([012]\d)|3[01])$/))
			return "TRUE";
		else
			return "날짜를 정확하게 숫자로만 입력하세요.";
	},
	// 년도(YYYY) 검증
	year: function( value ) {
		if (value.length == 4 && value.match(/^\d{4}$/))
			return "TRUE";
		else
			return "년도를 정확하게 숫자로만 입력하세요.";
	},
	// 2021.08.05 jQuery Validate Plugin 사용시 공통 핸들러
	handler: function(form, validator) {
		var errors = validator.numberOfInvalids();
		if (errors) {
			var errorObj = validator.errorList[0];
			$.commMsg.alert(errorObj.message, function() {
				var input = errorObj.element;
				$(input).focus();
			});
		}
	},
	// 2021.08.05 jQuery Validate Plugin 사용시 공통 에러표시방식
	placement: function(error, element) {
		//element.closest('td').find('.app-error').append(error);
		// do nothing
		//error.appendTo('.app_error');
	},
	// 2021.10.20 jQuery Validate Plugin 사용시 공통 핸들러
	handler01: function(form, validator) {
		var errors = validator.numberOfInvalids();
		if (errors) {
			var errorObj = validator.errorList[0];
			$(errorObj.element).focus();
		}
	},
	// 2021.10.20 jQuery Validate Plugin 사용시 공통 에러표시방식
	placement01: function(error, element) {
		element.closest('td').find('.app-error').append(error);
	},
	// 2021.08.05 jQuery Validate Plugin 팝업 사용시 공통 핸들러
	popupHandler: function(form, validator) {
		var errors = validator.numberOfInvalids();
		if (errors) {
			var errorObj = validator.errorList[0];
			$.commMsg.alert(errorObj.message, function() {
				var input = errorObj.element;
				$(input).focus();
			});
		}
	},
	appendHandler: function(form, validator) {
		var errors = validator.numberOfInvalids();
		if (errors) {
			var errorObj = validator.errorList[0];
			$(form).find('[name="'+errorObj.element+'"]').focus();
		}
	},
	appendPlacement: function(error, element) {
		element.closest('.inputGroup').append(error);
	},
	tablePlacement: function(error, element) {
		let err = $('<div></div>');
		err.append(error);
		element.closest('.form-area-box').find('.app-error').append(err);
	}
}

function validateHandler(form, validator) {
	$.validateUtil.handler(form, validator);
}

function validatePlacement(error, element) {
	$.validateUtil.placement(error, element);
}

$.validator.prototype.checkForm = function() {
    this.prepareForm();
    for ( var i = 0, elements = (this.currentElements = this.elements()); elements[i]; i++ ) {
        if (this.findByName( elements[i].name ).length != undefined && this.findByName( elements[i].name ).length > 1) {
            for (var cnt = 0; cnt < this.findByName( elements[i].name ).length; cnt++) {
                this.check( this.findByName( elements[i].name )[cnt], cnt );
            }
        } else {
            this.check( elements[i] );
        }
    }
    return this.valid();
};

$.extend( $.validator.messages, {
	required: "필수 항목입니다.",
	remote: "항목을 수정하세요.",
	email: "유효하지 않은 E-Mail주소입니다.",
	url: "유효하지 않은 URL입니다.",
	date: "올바른 날짜를 입력하세요.",
	dateISO: "올바른 날짜(ISO)를 입력하세요.",
	number: "유효한 숫자가 아닙니다.",
	digits: "숫자만 입력 가능합니다.",
	creditcard: "신용카드 번호가 바르지 않습니다.",
	equalTo: "같은 값을 다시 입력하세요.",
	extension: "올바른 확장자가 아닙니다.",
	maxlength: $.validator.format( "{0}자를 넘을 수 없습니다. " ),
	minlength: $.validator.format( "{0}자 이상 입력하세요." ),
	rangelength: $.validator.format( "문자 길이가 {0} 에서 {1} 사이의 값을 입력하세요." ),
	range: $.validator.format( "{0} 에서 {1} 사이의 값을 입력하세요." ),
	max: $.validator.format( "{0} 이하의 값을 입력하세요." ),
	min: $.validator.format( "{0} 이상의 값을 입력하세요." )
});
