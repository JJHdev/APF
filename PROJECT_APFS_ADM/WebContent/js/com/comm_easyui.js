/**
******************************************************************************************
*** 파일명    : comm_easyui.js
*** 설명      : Easy UI 관련 공통 스크립트
***
*** -----------------------------    Modified Log   --------------------------------------
*** ver             date                author                  description
*** --------------------------------------------------------------------------------------
*** 1.0         2021-07-19              LSH
******************************************************************************************
**/
/**
 * 2021.07.16 LSH
 * EasyUI combobox 의 기본설정 정의
 * 기본 공통코드 URL, 필드명, 기타 옵션 등 시스템전반의 콤보박스의 기본옵션을 사전정의해 놓는다.
 * 개별 박스간의 변경이 필요한 경우엔 해당 옵션을 박스 생성시 재정의하면 재정의한 옵션으로 적용된다.
 * 이 부분은 전체적으로 적용되는 공통부분이므로 함부로 변경하지 않도록 한다.
 * 이후에 공통 JS를 만들어 정의할 예정임
 */
$.extend($.fn.combobox.defaults, {
	// 콤보박스 높이
	panelHeight: 'auto',
	// 콤보박스 입력가능여부
	editable: false,
	// 콤보박스 옵션의 텍스트필드에 해당되는 칼럼명
	textField:  'text',
	// 콤보박스 옵션의 값필드에 해당되는 칼럼명
	valueField: 'code'
});
$.extend($.fn.datagrid.defaults, {
	striped: true,
	loader: function(param, success, error){
		var opts = $(this).datagrid('options');
		if (!opts.url) return false;
		$.ajax({
			type: opts.method,
			url: opts.url,
			data: param,
			dataType: 'json',
			// 배열형태의 파라메터를 조건으로 넘기기 위한 설정
			traditional : true,
			success: function(data){
				success(data);
			},
			error: function(){
				$.easyMsg.alert('검색 중 오류가 발생하였습니다.<br>담당자에게 문의 바랍니다.');
				error.apply(this, arguments);
			}
		});
	}
});

$.extend($.fn.datebox.defaults, {
	// 공통 Calendar 정의
	sharedCalendar: '#layerCalendar',
	// 공통 Formatter 정의
	formatter: function(d) {
		if (!d)
			return '';
		var y = d.getFullYear();
		var m = d.getMonth()+1;
		var d = d.getDate();
		return y+'-'+(m<10? '0':'')+m+'-'+(d<10? '0':'')+d;
	},
	// 공통 Parser 정의
	parser: function(s) {
		var t = Date.parse(s);
		if (!isNaN(t)){
			return new Date(t);
		} else {
			return new Date();
		}
	}
});

//EasyUI GRID 용 공통 Formatter 정의
$.commFormat = {
	rootNm: function(value, row) {
		if (row['upCdId'] == ROOT_CODE['code'])
			return ROOT_CODE['text'];
		return value;
	},
	usestts: function(value, row, index) {
		return (value == '1' ? '사용' : '중지');
	},
	sxdst: function(value, row, index) {
		if      (value == 'F') return '여성';
		else if (value == 'M') return '남성';
		return value;
	},
	address: function(value, row, index) {
		var s = '';
		if (row['zipcd']) s += '['+row['zipcd']+'] ';
		if (row['addr1']) s += row['addr1'];
		if (row['addr2']) s += row['addr2'];
		return s;
	},
	useyn: function(value, row, index) {
		return (value == 'Y' ? '사용' : '중지');
	},
	popupyn: function(value, row, index) {
		return (value == 'Y' ? '팝업' : '일반');
	},
	yesno: function(value, row, index) {
		return (value == 'Y' ? '예' : '아니오');
	},
	perm: function(value, row, index) {
		return (value == 'Y' ? '허용' : '불가');
	},
	system: function(value, row, index) {
		if (value == SYSTEM.ADMIN['code'])
			return SYSTEM.ADMIN['text'];
		else if (value == SYSTEM.USER['code'])
			return SYSTEM.USER['text'];
		return value;
	},
	rate: function(value, row, index) {
		return value +'%';
	},
	number: function(value, row, index) {
		return $.number(value);
	},
	numberPos: function(value, row, index) {
		if (value > 0)
			return $.number(value);
		return '-';
	},
	typeName: function(value, row, index) {
		return row['typeName'];
	},
	fileSize: function(value, row, index) {
		return $.fileUtil.formatBytes(value);
	},
	// 첨부파일 다운로드 버튼
	downBtn: function(value, row, index) {
		if (value > 0)
			return '<a href="javascript:void(0);" class="btnGridDown" data-no="'+value+'"></a>';
		return ' ';
	},
	// 로드 버튼
	loadBtn: function(value, row, index) {
		if (value > 0)
			return '<a href="javascript:void(0);" class="btnGridLoad" data-no="'+value+'"></a>';
		return ' ';
	},
	// datebox YYYYMMDD 포맷
	yyyymmdd: function(d) {
		var y = d.getFullYear();
		var m = d.getMonth()+1;
		var d = d.getDate();
		return y+(m<10? '0':'')+m+(d<10? '0':'')+d;
	},
	hour: function(value) {
		return value+'시';
	},
	year: function(value) {
		return value+'년';
	},
	month: function(value) {
		return value+'월';
	},
    downTrgtYn: function(value, row, index) {
        return (value == 'Y' ? '대상' : '아님');
    },
    esntlYn: function(value, row, index) {
        return (value == 'Y' ? '필수' : '아님');
    },

};

// EasyUI 적용 메세지 처리
$.easyMsg = {
	// 정보 메세지
	// (슬라이드 방식으로 나타났다가 없어짐)
	inform: function(msg) {
        $.messager.show({
            title: 'Information',
            msg: msg,
	        showType: 'slide', // 슬라이드 방식
            timeout: 2000      // 2초후 사라짐
        });
	},
	// 알림 메세지
	alert: function(msg, fn) {
		$.messager.alert({
			title: 'Message',
			msg: msg,
			fn: fn
		});
	},
	// 확인 메세지
	confirm: function(msg, fn) {
		$.messager.confirm({
			title: 'Confirm',
			msg: msg,
			fn: function(r){
				if (r){
					fn();
				}
			}
		});
	},
    /**
     * AJAX 통신 결과 공통 처리 로직
     * ajax의 success 함수에서 필요시 사용한다.
     * 성공 메세지
     * 2021.09.11 LSH ADD
     */
    success: function(data, msg, callback) {
        if (data['Code'] == 0) {
			if (msg)
				$.easyMsg.inform(msg);
			if (callback)
            	callback(data);
        }
		else {
			$.easyMsg.alert('[Code:' + data['Code'] + '] ' + data['Message']);
        }
    }
};

// EasyUI 유틸 함수
$.easyUtils = {
	// Layout의 Center의 사이즈를 윈도우에 맞게 조정한다.
	resize: function(layoutKey, topHeight, minHeight) {

		topHeight = (topHeight || 250);
		minHeight = (minHeight || 425);
		const fnResize = function() {
			let height = $(window).height()-topHeight;
			if (height < minHeight)
				height = minHeight;
			//console.log('topHeight='+topHeight+',minHeight='+minHeight+',height='+height);
		    $(layoutKey).layout('resize',{height: height});
		};
	    // resize 이벤트 발생시 사이즈 조정
	    $(window).resize(fnResize);
	    // 페이지 로딩시 사이즈 조정
	    fnResize();
	},
	// Tab 사이즈를 윈도우에 맞게 조정한다.
	resizeTabs: function(tabsKey, topHeight, minHeight) {

		topHeight = (topHeight || 250);
		minHeight = (minHeight || 425);
		const fnResize = function() {
			let height = $(window).height()-topHeight;
			if (height < minHeight)
				height = minHeight;
			//console.log('topHeight='+topHeight+',minHeight='+minHeight+',height='+height);
		    $(tabsKey).tabs('resize',{height: height});
		};
	    // resize 이벤트 발생시 사이즈 조정
	    $(window).resize(fnResize);
	    // 페이지 로딩시 사이즈 조정
	    fnResize();
	},
	// 콤보박스 옵션 로딩후 첫번째 항목을 선택처리한다.
	// onLoadSuccess 에서 사용하는 함수
	selectFirst: function(data) {
		if (data.length){
			var options = $(this).combobox('options');
			var first   = data[0][options.valueField];
			$(this).combobox('setValue', first);
		}
    },
	// TREE 의 해당 노드를 포함한 하위노드를 제거한다.(Recursive)
	removeNode: function(node, id) {
		var arr;
		if (node.children)
			arr = node.children;
		else
			arr = node;

		for (var i = 0; i < arr.length; i++) {
			if (arr[i]['id'] == id) {
				arr.splice(i, 1);
				return arr;
			}
			else {
				arr[i]['children'] = $.easyUtils.removeNode(arr[i], id);
			}
		}
		return arr;
	}
};

// EasyUI 적용 jQuery Validate Plugin 함수
$.easyValidate = {
	handler: function(form, validator) {
		var errors = validator.numberOfInvalids();
		if (errors) {
			var errorObj = validator.errorList[0];
	  		$.easyMsg.alert(errorObj.message, function() {
				var input = errorObj.element;
				// name과 id가 동일하다는 가정하에 정의함
				var name  = $(input).prop('name');
				var box   = $('#'+name);
				if (box)
					box.textbox('textbox').focus();
		  		//validator.errorList[0].element.textbox('textbox').focus();
	  		});
	  	}
	},
	placement: function(error, element) {
		// do nothing
		//error.appendTo('.app_error');
	}
};
