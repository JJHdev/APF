/**
*******************************************************************************
***    명칭: openEntIr.js
***    설명: 마이페이지 - IR작성하기 화면
***
***    -----------------------------    Modified Log   ------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.07.20    LSH        First Coding.
*******************************************************************************
**/
$(function() {
	// 업체정보 조회
	$('#appBzenty').appEnt({});
});

// 업체선택 정의
//=============================================================================
$.fn.appEnt = function () {
	
	let options = {
		mode    : $('#mode'    ).val(),
		pageCd  : $('#pageCd'  ).val(),
		selectYn: $('#selectYn').val(),
		updateYn: $('#updateYn').val(),
		bzentyNo: $('#bzentyNo').val(),
		bzentyNm: $('#bzentyNm').val(),
		irNo    : $('#irNo'    ).val(),
	};
	
	let _this = $(this);

	_this.html('');
	
	// 기능함수목록
	let _functions = {
		// 업체검색
		//---------------------------------------
		doPopup: function() {
			popup.openEnt({
				callback: _functions.doCallback
			});
			return false;
		},
		// 팝업창에서 호출하는 함수
		//---------------------------------------
		doCallback: function( data ) {
			if (!data || !data['bzentyNo'])
				return;
			$('#app-bzenty-nm').html(data['bzentyNm']);
			// 업체IR번호 조회
			const ret = $.ajaxUtil.ajaxDefault(getUrl('/usr/invest/ent/getEntIr.do'), {bzentyNo: data['bzentyNo']});

			$.formUtil.toForm({
				mode     : ret["mode"    ],
				pageCd   : ret["pageCd"  ],
				bzentyNo : ret["bzentyNo"],
				updateYn : ret["updateYn"],
				selectYn : ret["selectYn"],
				irNo     : ret["irNo"],
			}, $('#registForm'));

			_functions.doCreate();
		},
		// 탭생성
		//---------------------------------------
		doCreate: function() {
			$('#appMenuTab').html('');
			$('#appContent').html('');
			// IR 작성하기 탭 생성
			$('#appMenuTab').appEntTabs({
				admin   : true,
				contId  : 'appContent'
			});	
		}
	};
	
	// 업체영역 정의
	$('#appBzenty').appCard({
		titleOptions: {
			title: '업체정보 선택', 
			icon: '<i class="cycle-ver-1 icon-unlock titicon"></i>'
		},
		items: [{colCls: 'col', formatHtml: function() {
			let btn = $('<button type="button" id="btnPopup" class="btn-primary bs-m bs-md-l"><i class="icon-edit"></i>업체검색</button>');
			btn.bind('click', _functions.doPopup);
			let div = $('<div class="form-area-box-input"></div>');
			div.append('<div class="day"></div>');
			div.find('.day').append('<div class="row"></div>');
			div.find('.day > .row').append('<div class="col"><div class="ele-icon-box"><div class="value-box" id="app-bzenty-nm">'+options.bzentyNm+'</div></div></div>');
			div.find('.day > .row').append('<div class="col flex-grow-0 white-space-nowrap app-button"></div>');
			div.find('.app-button').append(btn);
			return div;
		}}]
	});
	
	if (!$.commUtil.empty(options.irNo)) {
		_functions.doCreate(options);
	}
	return this;
};
