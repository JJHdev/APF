/**
******************************************************************************************
*** 파일명    : comm_utils.js
*** 설명      : 공통 JavaScript
***             $.commUtil
***             getUrl()
***             getRealUrl()
***             goUrl()
***             goSite()
***             isIE()
***             $.setCookie()
***             $.getCookie()
***             $.lpad()
***             $.rpad()
***
*** -----------------------------    Modified Log   --------------------------------------
*** ver             date                author                  description
*** --------------------------------------------------------------------------------------
*** 1.0         2021-02-10              ntarget
******************************************************************************************
**/
/**
 * ContextPath 포함 URL 반환 
 */
function getUrl( url ) {
	return ROOT_PATH + url;
}

/**
 * ContextPath 제거 URL 반환 
 */
function getRealUrl( url ) {
	if (url != null && url.indexOf(ROOT_PATH) == 0)
		return url.substring(ROOT_PATH.length);
	return url;
}

/**
 * 페이지 이동
 */
function goUrl( url ) {
	window.location.href = url;
};

/**
 * 이전페이지로 이동
 */
function goBack() {
	if (document.referer)
		history.back();
	else
		goUrl(ROOT_PATH + '/');
};

/**
 * 새창 페이지 이동
 */
function goNewUrl(url) {
    var newWindowSite = window.open("about:blank");
    newWindowSite.location.href = url;
};

/**
 * 관련사이트 이동 (디자인영역)
 */
function goSite(obj) {
    var url =  obj.children('select').val();
    if (url != '') {
        var newWindowSite = window.open("about:blank");
        newWindowSite.location.href = url;
    }
};

/**
 * 브라우저가 IE인지 확인
 */
function isIE() {
	const agent = navigator.userAgent.toLowerCase();
	if ((navigator.appName == 'Netscape' && navigator.userAgent.search('Trident') != -1) || 
		(agent.indexOf("msie") != -1) ) {
		return true;
	}
	return false;
};

/**
 * 공통 함수 관련
 */
$.commUtil = {
    /**
     * 문자열이 비어 있는지 확인
     * $.commUtil.isEmpty(value);
     */
    isEmpty : function(str) {
        if(str == null){
            return true;
        }
        if($.type(str) == "string") {
            return $.trim(str) == "";
        }
        return false;
    },
    /**
     * 유효한 날짜 체크
     * $.commUtil.isDate(value);
     */
    isDate: function (str) {
        if ($.type(str) == 'string')
            str = str.replace(/\D/g, '');
        else
            str = str.val().replace(/\D/g, '');

        if (isNaN(str) || str.length != 8) {
            return false;
        }

        var year = Number(str.substring(0, 4));
        var month = Number(str.substring(4, 6));
        var day = Number(str.substring(6, 8));

        var dd = day / 0;

        if (month < 1 || month > 12) {
            return false;
        }

        var maxDaysInMonth = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
        var maxDay = maxDaysInMonth[month - 1];

        // 윤년 체크
        if (month == 2 && (year % 4 == 0 && year % 100 != 0 || year % 400 == 0)) {
            maxDay = 29;
        }

        if (day <= 0 || day > maxDay) {
            return false;
        }

        return true;
    },

    /**
     * 문자열 길이값 구하기
     * $.commUtil.getStrByteLength(value);
     */
    getStrByteLength : function(str) {
        var len = 0;

        if (str == '') {
            return len;
        } else {
            for (var i=0;i<str.length;i++) {
                var chr = str.charCodeAt(i);
                if (chr > 0 && chr < 255) {
                    len ++;
                } else {
                    len = len + 2;
                }
            }
        }
        return len;
    },

    /**
     * 문자열 Null 체크
     * $.commUtil.nvlTrim(str);
     */
    nvlTrim : function(str) {
        if (str == undefined || str == null){
            return "";
        }
        return $.trim(str);
    },

    /**
     * 문자열 전체 replace
     * $.commUtil.replaceAll(str, before, after);
     */
    replaceAll : function(str, before, after){
        if(str == undefined || str == null){
            return "";
        }

        if($.type(before) == 'regexp')
            return str.replace(before,after);
        else
            return str.replace(new RegExp(before,'g'), after);
    },
    
    /**
     * 2021.08.05 LSH
     * undefined 공백처리
     * $.commUtil.nvl(str)
     */
    nvl : function(val, def) {
    	if (val == null      ||
    		val == undefined ||
    		val == 'undefined') {
    		return (def || '');
    	}
    	return val;
    },
    
    /**
     * 2021.08.05 LSH
     * 객체데이터의 NVL처리
     * $.commUtil.nvlObject(str)
     */
    nvlObject: function(data) {
    	for (var p in data) {
    		data[p] = $.commUtil.nvl(data[p]);
    	}
    	return data;
    },
    
    /**
     * 2021.12.06 LSH
     * undefined 0으로 변환
     * $.commUtil.nvlNum(str)
     */
    nvlInt : function(val) {
    	if (val == null      ||
    		val == undefined ||
    		val == 'undefined') {
    		return 0;
    	}
		if ($.type(val) === 'string') {
			val = val.replace(/,/g,'');
		}
		return (val == '' ? 0 : parseInt(val));
    },
    nvlFloat : function(val) {
    	if (val == null      ||
    		val == undefined ||
    		val == 'undefined') {
    		return 0;
    	}
		if ($.type(val) === 'string') {
			val = val.replace(/,/g,'');
		}
		return (val == '' ? 0 : parseFloat(val));
    },

    /**
     * 2021.08.05 LSH
     * 빈값 체크
     * $.commUtil.empty(str)
     */
    empty : function(val) {

    	if (typeof val == "array") {
    		if (val.length>0)
    			return false;
    		return true;
    	}
    	if (!val)
    		return true;
    	if (val == null)
    		return true;
    	if (val == 'undefined')
    		return true;
    	if (val == '')
    		return true;

    	return false;
    },
    /**
     * 2021.10.06
     * 전화번호를 형식에 맞게 변환하여 반환한다.
	 * num : '-' 문자가 들어있지않은 숫자로된 전화번호
	 * type : 0을 보내면 가운데자리를 숨겨준다
     * $.commUtil.phoneFormatter(num,type)
     */
	phoneFormatter : function(num, type) {
		var formatNum = '';
		if (num == null)
			return '';
		num = num.replace(/ /g,'');
		num = $.commUtil.nvlTrim(num);	
		
    	if (num.length==11) {
        	if (type==0)
            	formatNum = num.replace(/(\d{3})(\d{4})(\d{4})/, '$1-****-$3');
			else
				formatNum = num.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
    	}
		else if (num.length==9) {
           	formatNum = num.replace(/(\d{2})(\d{3})(\d{4})/, '$1-$2-$3');
    	}
		else if (num.length==8) {
        	formatNum = num.replace(/(\d{4})(\d{4})/, '$1-$2');
		}
		else {
        	if (num.indexOf('02')==0) {
            	if (type==0)
                	formatNum = num.replace(/(\d{2})(\d{4})(\d{4})/, '$1-****-$3');
				else
                	formatNum = num.replace(/(\d{2})(\d{4})(\d{4})/, '$1-$2-$3');
        	}
			else {
            	if (type==0)
                	formatNum = num.replace(/(\d{3})(\d{3})(\d{4})/, '$1-***-$3');
            	else
                	formatNum = num.replace(/(\d{3})(\d{3})(\d{4})/, '$1-$2-$3');
        	}
    	}
    	return formatNum;
	},
	// 날짜를 Date 타입으로 변환
	toDate: function( str ) {
		if (str == null)
			return null;
		str = str.replace(/-/gi, "");
		str = str.replace(/\./gi, "");
		if (!/^[0-9]*$/.test(str))
			return null;
		if (str.length < 8)
			return null;
		const y = parseInt(str.substring(0,4));
		const m = parseInt(str.substring(4,6))-1;
		const d = parseInt(str.substring(6,8));
		return new Date(y,m,d);
	},
	// 날짜비교
	compareDate: function (str1, str2) {
		let d1 = $.commUtil.toDate(str1);
		let d2 = $.commUtil.toDate(str2);
		if      (d1.getTime() < d2.getTime()) return -1;
		else if (d1.getTime() > d2.getTime()) return  1; 
		else return 0; 
	},
	toFormatDate: function( d, delim ) {
		if (d == null)
			return null;
		const year  = d.getFullYear(); 
		const month = d.getMonth() + 1; 
		const date  = d.getDate(); 
		return year + delim +
		      (month >= 10 ? month : '0'+month) + delim +
			  (date  >= 10 ? date  : '0'+date );
	},
	// 날짜 더하기
	addDays: function( str, days ) {
		let d = null;
		if ($.type(str) == 'date')
			d = str;
		else {
			d = $.commUtil.toDate(str);
			if (d == null)
				d = new Date();
		}
		let nd = new Date(d.getTime());
		nd.setDate(d.getDate()+days);
		return ($.type(str) == 'date' ? nd : $.commUtil.toFormatDate(nd,''));
	},
	addMonths: function( str, months ) {
		let d = null;
		if ($.type(str) == 'date')
			d = str;
		else {
			d = $.commUtil.toDate(str);
			if (d == null)
				d = new Date();
		}
		let nd = new Date(d.getTime());
		nd.setMonth(d.getMonth()+months);
		return ($.type(str) == 'date' ? nd : $.commUtil.toFormatDate(nd,''));
	},
	addYears: function( str, years ) {
		let d = null;
		if ($.type(str) == 'date')
			d = str;
		else {
			d = $.commUtil.toDate(str);
			if (d == null)
				d = new Date();
		}
		let nd = new Date(d.getTime());
		nd.setFullYear(d.getFullYear()+years);
		return ($.type(str) == 'date' ? nd : $.commUtil.toFormatDate(nd,''));
	},
	// 해당월 마지막 날짜 구하기
	lastDay: function( str ) {
		let d = null;
		if ($.type(str) == 'date')
			d = str;
		else {
			d = $.commUtil.toDate(str);
			if (d == null)
				d = new Date();
		}
		let nd = new Date(d.getTime());
		nd.setMonth(d.getMonth()+1);
		nd.setDate(0);
		return ($.type(str) == 'date' ? nd : $.commUtil.toFormatDate(nd,''));
	},
	// 날짜입력값을 YYYYMMDD 로 변환
	toSimpleDate: function( str ) {
		if (str == null)
			return null;
		str = str.replace(/-/gi, "");
		str = str.replace(/\./gi, "");
		if (!/^[0-9]*$/.test(str))
			return null;
		if (str.length < 8)
			return null;
		return str;
	},
	// 날짜입력값에서 YYYY 를 반환
	getYearStr: function( str ) {
		str = $.commUtil.toSimpleDate(str);
		if (str == null)
			return null;
		return str.substring(0,4);
	},
	// 날짜입력값에서 MM 를 반환
	getMonthStr: function( str ) {
		str = $.commUtil.toSimpleDate(str);
		if (str == null)
			return null;
		return str.substring(4,6);
	},
	// 날짜입력값에서 DD 를 반환
	getDayStr: function( str ) {
		str = $.commUtil.toSimpleDate(str);
		if (str == null)
			return null;
		return str.substring(6,8);
	},
	// 현재날짜를 YYYYMMDD로 반환
	getCurDate: function() {
		return $.commUtil.toFormatDate(new Date(),'');
	},
	// 미래날짜인지 확인
	isFutureDate: function( str ) {
		let d1 = $.commUtil.toSimpleDate(str);
		let d2 = $.commUtil.getCurDate();
		return ($.commUtil.compareDate(d1,d2) > 0);
	},
	// 과거날짜인지 확인
	isPastDate: function( str ) {
		let d1 = $.commUtil.toSimpleDate(str);
		let d2 = $.commUtil.getCurDate();
		return ($.commUtil.compareDate(d1,d2) < 0);
	},
	// 현재기준 입력날짜와의 일수 반환
	getDays: function( str ) {
		let d1 = new Date();
		let d2 = $.commUtil.toDate(str);
		
		const diff = d1.getTime() - d2.getTime();
		let days = Math.floor(Math.abs(diff / (1000 * 60 * 60 * 24))); // 밀리세컨 * 초 * 분 * 시 = 일
		return (days+1);
	},
	// 한글 조사 반환 함수
	getPostKor: function( str, idx ) {
		const posts = [['은','는'],['을','를'],['이','가']];
		let post = posts[idx];
	    //name의 마지막 음절의 유니코드(UTF-16) 
	    const c = str.charCodeAt(str.length - 1);
	    //유니코드의 한글 범위 내에서 해당 코드의 받침 확인
	    const consonantCode = (c - 44032) % 28;
	    if (consonantCode === 0){
	        //0이면 받침 없음 -> 를
	        return str+post[1];
	    }
	    //1이상이면 받침 있음 -> 을
	    return str+post[0];
	}
};   
// END of '$.commUtil'

/**
 * 쿠키설정
 */
$.setCookie = function (name, value, expiredays) {
    if (expiredays == 0 || expiredays == 'undefined') {
        document.cookie = name+"="+ escape(value);
    } else {
        var todayDate = new Date();
        todayDate.setDate(todayDate.getDate() + expiredays);
        document.cookie = name + "=" + escape(value) + "; path=/; expires=" + todayDate.toGMTString() + ";";
    }
}
/**
 * 쿠키가져오기
 */
$.getCookie = function (name) {
    var nameOfCookie = name + "=";
    var x = 0;
    while (x <= document.cookie.length) {
        var y = (x + nameOfCookie.length);
        if (document.cookie.substring(x, y) == nameOfCookie) {
            if ((endOfCookie = document.cookie.indexOf(";", y)) == -1) endOfCookie = document.cookie.length;
            return unescape(document.cookie.substring(y, endOfCookie));
        }
        x = document.cookie.indexOf(" ", x) + 1;
        if (x == 0) break;
    }
    return "";
}	

/**
 * LPAD
 */
$.lpad = function (str, len, padStr) {
    if (padStr.length > len) {
        return str;
    }
    str += "";
    padStr += "";
    while (str.length < len)
        str = padStr + str;
    str = str.length >= len ? str.substring(0, len) : str;
    return str;
};

/**
 * RPAD
 */
$.rpad = function (str, len, padStr) {
    if (padStr.length > len) {
        return str;
    }
    str += "";
    padStr += "";
    while (str.length < len)
        str += padStr;
    str = str.length >= len ? str.substring(0, len) : str;
    return str;
};