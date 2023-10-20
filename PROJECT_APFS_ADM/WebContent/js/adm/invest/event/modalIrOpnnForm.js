/**
*******************************************************************************
***    명칭: modalIrOpnnForm.js
***    설명: 투자정보관리-IR검토의견서관리 화면
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.06.07      LHB      First Coding.
*******************************************************************************
**/
$(function() {

    //========================================================//
    // 화면 스크립트 내에 사용할 객체,상수 정의
    //--------------------------------------------------------//
    var P_RFORM			= $('#registForm');			// 등록폼 객체

    var P_MODE			= $('#mode'      ).val();	// 처리 모드
    var P_EVNT_NO		= $('#evntNo'    ).val();	// 행사 키정보
	var P_EVNT_TYPE		= $('#evntType'  );			// 업체 타입 (투자자, 참여 경영체)

	var P_ENTGRID		= $('#appGrid-ent' );		// 투자자, 참여경영체 전체 그리드 (모달X)
	
	var P_MODAL1		= $('#myModal1');			// 투자자별 IR 검토의견서 모달
	var P_MODAL2		= $('#myModal2');			// 경영체별 IR 검토의견서 모달
	
	var P_EVNT_TYPE_BTN	= $('.evntType > a');		// 참여 투자자/경영체 탭 버튼

	//========================================================//
    // 목록 GRID 정의 (EasyUI datagrid)
    //--------------------------------------------------------//
	var columns = [];
	if (P_EVNT_TYPE.val() == 'INVT') {
		// 참여 투자자
		columns = [
			{field:'evntNo'		,width:80, halign:'center',align:'center',title:'행사번호',	hidden: true},
			{field:'bzentyNo'	,width:80, halign:'center',align:'center',title:'업체번호',	hidden: true},
			{field:'userNo'		,width:80, halign:'center',align:'center',title:'사용자번호',	hidden: true},
			{field:'invtBzentyNm'	,width:180,halign:'center',align:'center',title:'운용사명'},
            {field:'userNmNo'	,width:180,halign:'center',align:'center',title:'작성자'},
            {field:'irOpnn'		,width:70, halign:'center',align:'center',title:'의견서',
			 formatter: function(value, row, index) {
				if (row['isRgst']) {
					return '<button type="button" class="btn-primary w-100 bs-s" data-bzentyNo="' + row['bzentyNo'] + '" data-userNo="' + row['userNo'] + '" data-bs-target="" onclick="doClickIrOpnnInvt(this)">의견서</button>';	
				} else {
					return '-';
				}
			 }
		    }
		];
	} else if (P_EVNT_TYPE.val() == 'ENT') {
		// 참여 경영체 사업계획서
		columns = [
			{field:'evntPartcptnNo'	,width:80, halign:'center',align:'center',title:'행사참여번호', hidden: true},
			{field:'evntNo'			,width:80, halign:'center',align:'center',title:'행사번호',    hidden: true},
			{field:'bzentyNo'		,width:80, halign:'center',align:'center',title:'업체번호',    hidden: true},
			{field:'bzentyNm'		,width:180,halign:'center',align:'center',title:'경영체명',},
			{field:'rprsvNm'		,width:100, halign:'center',align:'center',title:'대표자'},
			{field:'telno'			,width:90, halign:'center',align:'center',title:'연락처', formatter:$.formatUtil.toPhone},
			{field:'irOpnn'			,width:70, halign:'center',align:'center',title:'의견서',
			 formatter: function(value, row, index) {
				if (row['isRgst']) {
					return '<button type="button" class="btn-primary w-100 bs-s" data-bzentyNo="' + row['bzentyNo'] + '" data-evntPartcptnNo="' + row['evntPartcptnNo'] + '" data-bs-target="" onclick="doClickIrOpnnEnt(this)">의견서</button>';	
				} else {
					return '-';
				}
			 }
		    }
		];
	}

    P_ENTGRID.datagrid({
		// 화면영역 맞춤
		fit: true,
        // 그리드 결과데이터 타입
        contentType: 'application/json',
        // 그리드 결과데이터 타입
        dataType: 'json',
        // 그리드 데이터연동 방식
        method: 'POST',
        // 그리드 단일행만 선택여부
        singleSelect: false,
        // 그리드 페이징처리 여부
        pagination: true,
        // 그리드 행번호 표시여부
        rownumbers: true,
        // 한 페이지 출력수
        pageSize: 30,
        // 칼럼정의
        columns: [columns],
        // 행선택시 수정하기
        onSelect: function(index, row) {
		},
		// no-data 표출 메시지
		emptyMsg: (P_EVNT_TYPE.val() == 'INVT') ? '투자자 목록이 없습니다.' : '참여 경영체 목록이 없습니다.',
		// 목록로딩 완료후 처리
		onLoadSuccess: function(data) {
			$('#appGridResult-ent').text(data.total);
		}
    });

    //========================================================//
    // FORM ELEMENTS 정의
    //--------------------------------------------------------//

	// 탭 전환 클릭 이벤트
    //--------------------------------------------------------//
	function changeTab() {
		var idx = $(this).parent().index();
		P_EVNT_TYPE_BTN.removeClass('active');
		$(this).addClass('active');
		
		if (idx == 0) {
			// 참여 투자자 탭
			P_EVNT_TYPE.val('INVT');
		} else if (idx == 1) {
			// 참여 경영체 사업계획서 탭
			P_EVNT_TYPE.val('ENT');
		}
		
		// 해당 투자설명회 재검색
		var row = P_RFORM.serializeObject();
		P_MODAL.doOpenUpdate(row);
	}
	
	// 참여기업 검색처리
    //--------------------------------------------------------//
    function doSearchEnt() {
		// 선택된 항목 CLEAR
		P_ENTGRID.datagrid('clearSelections');
		
        // 참여 투자자 등록폼 데이터 객체화
		var obj = P_RFORM.serializeObject();
		
        // 그리드 목록조회 URL
        P_ENTGRID.datagrid('options')['url'] = getUrl('/adm/invest/event/getListIrOpnn.do');

        // 검색폼 그리드 검색
        P_ENTGRID.datagrid('load', obj);

        return false;
    }
	
    // 취소버튼 클릭시 이벤트 처리
    //--------------------------------------------------------//
    $('#btnCancel').bind('click', function() {
		// 부모창의 doClose 호출
		P_MODAL.doClose();
        return false;
	});
	
	// 투자자별 IR 검토의견서 모달 팝업시 발생하는 함수
	P_MODAL1.on('shown.bs.modal', function (e) {
		$('.modal-backdrop').hide();
	});
	// 경영체별 IR 검토의견서 모달 팝업시 발생하는 함수
	P_MODAL2.on('shown.bs.modal', function (e) {
		$('.modal-backdrop').hide();
	});
	
	// 참여 투자자/경영체 탭 클릭시 이벤트 처리
	P_EVNT_TYPE_BTN.bind('click', changeTab);
	
	// modal backdrop의 rootElement를 해당 모달로 변경해서 정상적으로 표출되도록 함
	//new bootstrap.Modal('#myModal1')._backdrop._config.rootElement = $('.detail-pop-1');
	//new bootstrap.Modal('#myModal2')._backdrop._config.rootElement = $('.detail-pop-1');
	//유동적으로 '의견서' 버튼을 생성하다 보니 적용할 수 없음..
	
	// 참여 투자자 or 경영체 조회
	doSearchEnt();
});

function doSearchIrOpnn(data) {
	data = data ? data : {};
	
	$.ajaxUtil.ajaxLoad(
        getUrl('/adm/invest/event/viewIrOpnn.do'), 
        data,
        function(ret) {
            $.ajaxUtil.success(ret, function() {
				$('#myModal1').modal('show');
				var data = ret.Data;
				
				$.formUtil.toForm(data, $('#modalForm1'));
				
				$('#page').text(ret.page);
				$('#total').text(ret.total);
				
				if (data['regDate']) {
					$('#modal1Date').text(data['regDate'] + ' 등록');
				}
				
				Object.keys(data).forEach(function(e, i) {
					var elem = $('#modalForm1').find('.value-box.' + e);
					if (elem.length > 0) {
						elem.text(data[e]);
					} else {
						elem = $("#modalForm1").find('input[name="' + e + '"][value="' + data[e] + '"]')
						if (elem.length > 0) {
							elem.prop("checked", true);	
						}
					}
				});
				
				if (ret.page == 1) {
					$('#prevIrOpnn').hide();
					$('#nextIrOpnn').show();
				} else if (ret.page == ret.total) {
					$('#prevIrOpnn').show();
					$('#nextIrOpnn').hide();
				} else {
					$('#prevIrOpnn').show();
					$('#nextIrOpnn').show();
				}
            });
        }
    );
}

// 투자자별 의견서 버튼 클릭 이벤트
function doClickIrOpnnInvt(e) {
	var data;
	var evntNo;
	var bzentyNo;
	var evntPartcptnNo;
	var page = 1;
	
	if (e) {
		var elem = $(e);
		evntNo			= $.formUtil.getValue($('#registForm'), 'evntNo');
		bzentyNo		= elem.data('bzentyno');
		evntPartcptnNo	= elem.data('evntpartcptnno');
		userNo			= elem.data('userno');
		// data 변수명은 모두 소문자로
	} else {
		$.commMsg.alert("잘못된 투자자 정보입니다.");
	}
	
	data = {
		page: page,
		evntNo: evntNo,
		bzentyNo: bzentyNo,
		userNo: userNo,
		evntPartcptnNo: evntPartcptnNo
	};
	
	console.log(data);
	
	P_MODAL_INVT = $('<div></div>').appPopupBbsInvt(data, function() {
		return false;
	});
}

function loadIrOpnnInvt(page) {
	var evntNo		= $.formUtil.getValue($('#modalForm1'), 'evntNo'  );
	var bzentyNo	= $.formUtil.getValue($('#modalForm1'), 'bzentyNo');
	var userNo		= $.formUtil.getValue($('#modalForm1'), 'userNo');
	var page		= page;
	
	var params = {
		evntNo		: evntNo,
		bzentyNo	: bzentyNo,
		userNo		: userNo,
		page		: page,
	};
	
	P_MODAL_INVT.load({
		params: JSON.stringify(params),
	});
}

/***************************************************
****************************************************
***************************************************/

// 경영체별 의견서 버튼 클릭 이벤트
function doClickIrOpnnEnt(e) {
	var elem = $(e);
	var evntNo			= $.formUtil.getValue($('#registForm'), 'evntNo');
	var evntPartcptnNo	= elem.data('evntpartcptnno');
	
	data = {
		evntNo: evntNo,
		evntPartcptnNo: evntPartcptnNo
	}
	
	P_MODAL_ENT = $('<div></div>').appPopupBbsEnt(data, function() {
		return false;
	});
}

//[팝업] IR검토의견서 상세보기 (투자자별) 팝업
//=============================================================================
$.fn.appPopupBbsInvt = function( params, saveCallback ) {
	
	let options = {
		title     		: 'IR 투자자 검토의견서',
		icon      		: '<i class="icon-copy-alt"></i>',
		loadUrl			: getUrl('/adm/invest/event/modalIrOpnn.do'),
		loadParams		: params,
	};
	
	return $(this).appPopup({
		url:        	options.loadUrl,
		params:     	JSON.stringify(options.loadParams),
		title:      	options.title,
		icon:       	options.icon,
		style:			'width: 800px',
		type:      	 	'pageload',
		dialogCls:  	'w-ver1',
		appendBody: 'body',
		onloaded:   function(pop) {
			return false;
		}
	}).open();
};

//[팝업] IR검토의견서 상세보기 (경영체별) 팝업
//=============================================================================
$.fn.appPopupBbsEnt = function( params, saveCallback ) {
	
	let options = {
		title     		: 'IR 투자자 검토의견서',
		icon      		: '<i class="icon-copy-alt"></i>',
		loadUrl			: getUrl('/adm/invest/event/modalIrOpnnEnt.do'),
		loadParams		: params,
	};
	
	return $(this).appPopup({
		url:        	options.loadUrl,
		params:     	JSON.stringify(options.loadParams),
		title:      	options.title,
		icon:       	options.icon,
		style:			'width: 1000px',
		type:      	 	'pageload',
		dialogCls:  	'w-ver1',
		appendBody: 'body',
		onloaded:   function(pop) {
			return false;
		}
	}).open();
};