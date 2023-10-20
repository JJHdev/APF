/**
*******************************************************************************
***    명칭: modalEventEntForm.js
***    설명: 투자정보관리-참여기업등록 화면
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.06.05      LHB      First Coding.
*******************************************************************************
**/
$(function() {

    //========================================================//
    // 화면 스크립트 내에 사용할 객체,상수 정의
    //--------------------------------------------------------//
    var P_RFORM			= $('#registForm');			// 등록폼 객체
	var P_MFORM1		= $('#modalForm1');			// 투자자 등록폼 객체
	var P_MFORM2		= $('#modalForm2');			// 참여 경영체 사업계획서 등록폼 객체

    var P_MODE			= $('#mode'      ).val();	// 처리 모드
    var P_EVNT_NO		= $('#evntNo'    ).val();	// 행사 키정보
	var P_EVNT_TYPE		= $('#evntType'  );	// 업체 타입 (투자자, 참여 경영체)

	var P_ENTGRID		= $('#appGrid-ent' );		// 투자자, 참여경영체 전체 그리드 (모달X)
	var P_GRID_INVT		= $('#appGrid-invt');		// 투자자 그리드 (모달O)
	
	var P_MODAL1		= $('#myModal1');					// 투자자 추가 모달
	var P_MODAL1_BTN	= $("[data-bs-target='#myModal1']");// 투자자 추가 버튼
	var P_MODAL2		= $('#myModal2');					// 사업계획서 작성 모달
	var P_MODAL2_BTN	= $("#btnAddEnt");// 참여 경영체 추가 버튼
	
	var P_EVNT_TYPE_BTN	= $('.evntType > a');		// 참여 투자자/경영체 탭 버튼
	
	var P_FILE_BOX	= $('#attachFile' );			// 첨부파일
	var P_FILE_BOX2	= $('#attachFile2');			// 첨부파일(썸네일)
	
	//========================================================//
    // 첨부파일 초기화
    //--------------------------------------------------------//
	
	P_FILE_BOX.appBizFile({
		cls: 'border-0 mb-0 pb-0',
		// 처리모드
		mode: MODE.INSERT,
		// 설명글
		comment: {
			check: '',
			input: '100MB 이내 1개 파일 가능, 여러 파일 업로드 희망 시 압축파일 권장'
		},
		initData: {
			fileType: CODE.FILE_TYPE.BIZ,
			docuCd  : CODE.TASK_SE.EVENT,
			docuNo  : '',
			docuSeq : '',
			fileSe  : '01',
			fileYn  : 'Y',
			needYn	: 'Y'
		}
	}).appBizFile('init');
	
	P_FILE_BOX2.appBizFile({
		cls: 'border-0 mb-0 pb-0',
		// 처리모드
		mode: MODE.INSERT,
		// 설명글
		comment: {
			check: '',
			input: '100MB 이내 1개 이미지 파일 가능'
		},
		initData: {
			fileType: CODE.FILE_TYPE.BIZ,
			docuCd  : CODE.TASK_SE.EVENT,
			docuNo  : '',
			docuSeq : '',
			fileSe  : '00',
			fileYn  : 'Y',
			needYn	: 'Y'
		},
		extensions: ['jpg','bmp','png']
	}).appBizFile('init');

	//P_FILE_BOX.appBizFile('init');

	//========================================================//
    // 목록 GRID 정의 (EasyUI datagrid)
    //--------------------------------------------------------//
	var columns = [];
	if (P_EVNT_TYPE.val() == 'INVT') {
		// 참여 투자자
		columns = [
			{field:'evntNo'			,width:80, halign:'center',align:'center',title:'행사번호',    hidden: true},
			{field:'bzentyNo'		,width:80, halign:'center',align:'center',title:'업체번호',    hidden: true},
			{field:'chk'			,width:80, halign:'center',align:'center',checkbox: true,},
			{field:'bzentyNm'		,width:150,halign:'center',align:'center',title:'소속(운용사)',},
            {field:'brno'			,width:100,halign:'center',align:'center',title:'사업자번호'},
            {field:'rprsvNm'		,width:60, halign:'center',align:'center',title:'대표자'}
		];
	} else if (P_EVNT_TYPE.val() == 'ENT') {
		// 참여 경영체 사업계획서
		columns = [
			{field:'evntPartcptnNo'	,width:80, halign:'center',align:'center',title:'행사참여번호', hidden: true},
			{field:'evntNo'			,width:80, halign:'center',align:'center',title:'행사번호',    hidden: true},
			{field:'bzentyNo'		,width:80, halign:'center',align:'center',title:'업체번호',    hidden: true},
			{field:'chk'			,width:80, halign:'center',align:'center',checkbox: true,},
			{field:'bzentyNm'		,width:150,halign:'center',align:'center',title:'경영체명',},
			{field:'rprsvNm'		,width:70, halign:'center',align:'center',title:'대표자'},
			{field:'telno'			,width:100, halign:'center',align:'center',title:'연락처', formatter:$.formatUtil.toPhone},
			{field:'inqCnt'			,width:50, halign:'center',align:'center',title:'조회수'}
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

	P_GRID_INVT.datagrid({
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
        columns: [[
			{field:'evntNo'			,width:80, halign:'center',align:'center',title:'행사번호',    hidden: true},
			{field:'bzentyNo'		,width:80, halign:'center',align:'center',title:'업체번호',    hidden: true},
			{field:'chk'			,width:80, halign:'center',align:'center',checkbox: true,},
			{field:'bzentyNm'		,width:120,halign:'center',align:'center',title:'소속',},
            {field:'brno'			,width:100,halign:'center',align:'center',title:'사업자번호'},
            {field:'rprsvNm'		,width:60, halign:'center',align:'center',title:'대표자'},
			{field:'fndnYmd'		,width:100,halign:'center',align:'center',title:'설립일'}
        ]],
        // 행선택시 수정하기
        onSelect: function(index, row) {
		},
		// no-data 표출 메시지
		emptyMsg: '투자자 목록이 없습니다.',
		// 목록로딩 완료후 처리
		onLoadSuccess: function(data) {
			console.log(data);
		}
    });

    //========================================================//
    // FORM ELEMENTS 정의
    //--------------------------------------------------------//
    
    //========================================================//
    // 등록폼 VALIDATION RULE 정의
    //--------------------------------------------------------//
    P_RFORM.validate({
        debug: false,
        onsubmit: false,
        onfocusout: false,
        // 검증룰 정의
        rules: {
            evntNm       : {required: true,
                            maxlength: 100},
			evntBgngYmd  : {required: true,
			                date: true},
			rgtrNo       : {required: true},
			useYn        : {required: true}
        },
        // 검증메세지 정의
        messages: {
            evntNm        : {required: '행사명은 필수 입력 사항입니다.',
                             maxlength: jQuery.validator.format('최대 {0}자 이하')},
			evntBgngYmd   : {required: '행사일자는 필수 입력 사항입니다.',
                             date: '행사일자를 형식에 맞게 입력해주세요.'},
			rgtrNo        : {required: '작성자는 필수 입력 사항입니다.'},
            useYn         : {required: '플랫폼 게시는 필수 입력 사항입니다.'}
        },
        invalidHandler: validateHandler,
        errorPlacement: validatePlacement
    });

	P_MFORM2.validate({
        debug: false,
        onsubmit: false,
        onfocusout: false,
        // 검증룰 정의
        rules: {
            bzentyNm	: {required : true,
                           maxlength: 100},
			rprsvNm		: {required : true,
			               maxlength: 20},
			telno		: {required : true,
			               phoneNo  : true},
			emlAddr		: {required : true,
			               email    : true},
			mainBizCn	: {required : true,
			               maxlength: 2000}
        },
        // 검증메세지 정의
        messages: {
            bzentyNm	: {required : '기업명은 필수 입력사항입니다.',
                           maxlength: jQuery.validator.format('최대 {0}자 이하')},
			rprsvNm		: {required : '대표자는 필수 입력사항입니다.',
			               maxlength: jQuery.validator.format('최대 {0}자 이하')},
			telno		: {required : '연락처는 필수 입력사항입니다.',
			               phoneNo  : '연락처의 형식이 올바르지 않습니다.'},
			emlAddr		: {required : '이메일은 필수 입력사항입니다.',
			               email    : '이메일의 형식이 올바르지 않습니다.'},
			mainBizCn	: {required : '주요사업내용은 필수 입력사항입니다.',
			               maxlength: jQuery.validator.format('최대 {0}자 이하')}
        },
        invalidHandler: validateHandler,
        errorPlacement: validatePlacement
    });
	// 숫자만입력
	bindOnlyNumber($("#brno1, #brno2, #brno3, #telno1, #telno2, #telno3"));

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
        P_ENTGRID.datagrid('options')['url'] = getUrl('/adm/invest/fund/getListEventEnt.do');

        // 검색폼 그리드 검색
        P_ENTGRID.datagrid('load', obj);

        return false;
    }

	// 전체 투자자 검색처리
    //--------------------------------------------------------//
    function doSearchInvt() {
		// 선택된 항목 CLEAR
		P_GRID_INVT.datagrid('clearSelections');
		
        // 검색폼 데이터 객체화
		var obj = P_MFORM1.serializeObject();
		
        // 그리드 목록조회 URL
        P_GRID_INVT.datagrid('options')['url'] = getUrl('/adm/invest/fund/getListInvt.do');

        // 검색폼 그리드 검색
        P_GRID_INVT.datagrid('load', obj);

        return false;
    }

	// 투자자 저장하기
    //--------------------------------------------------------//
    function doSaveInvt() {
		var data		= P_GRID_INVT.datagrid('getChecked');
		var bzentyNoArr	= data.map(function(e) { return e.bzentyNo; });
		var evntNo		= $.formUtil.getValue(P_RFORM, 'evntNo');
		
		if(bzentyNoArr.length < 1) {
			$.commMsg.alert('추가할 투자자를 선택해주세요.');
			return false;
		}

		$.commMsg.confirm("저장하시겠습니까?", function() {
			// form 안에 bzentyNo의 name을 가진 input 태그 세팅
			var html = '';
			bzentyNoArr.forEach(function(e, i) {
				html += '<input type="hidden" name="bzentyNoArr" value="' + e + '"/>';
			});
			html += '<input type="hidden" name="mode"     value="I"/>';
			html += '<input type="hidden" name="evntNo"   value="' + evntNo + '"/>';
			html += '<input type="hidden" name="evntType" value="INVT"/>';
			P_MFORM1.find('div.hidden-div').html(html);
	
            // 등록폼을 AJAX로 저장처리
            P_MFORM1.ajaxForm({
                url: getUrl('/adm/invest/event/saveEventInvt.do'),
                // 오류시 처리로직
				error: $.ajaxUtil.error,
                // 저장후 처리로직
                success: function(ret) {
					$('.modal-backdrop').hide();
					$.commMsg.success(ret, '성공적으로 저장되었습니다.', function() {
						P_MODAL1.modal('hide');
						doSearchEnt();
					});
                },
            }).submit();
        });

        return false;
    }

	// 투자자 삭제하기
    //--------------------------------------------------------//
    function doDeltInvt() {
		var data		= P_ENTGRID.datagrid('getChecked');
		var bzentyNoArr	= data.map(function(e) { return e.bzentyNo; });
		var evntNo		= $.formUtil.getValue(P_RFORM, 'evntNo');
		
		if(bzentyNoArr.length < 1) {
			$.commMsg.alert('삭제할 투자자를 선택해주세요.');
			return false;
		}
		
		$.commMsg.confirm("정말로 삭제하시겠습니까?", function() {
			// form 안에 bzentyNo의 name을 가진 input 태그 세팅
			var html = '';
			bzentyNoArr.forEach(function(e, i) {
				html += '<input type="hidden" name="bzentyNoArr" value="' + e + '"/>';
			});
			html += '<input type="hidden" name="mode" value="D"/>';
			html += '<input type="hidden" name="evntNo" value="' + evntNo + '"/>';
			P_MFORM1.find('div.hidden-div').html(html);
	
            // 등록폼을 AJAX로 저장처리
            P_MFORM1.ajaxForm({
                url: getUrl('/adm/invest/event/saveEventInvt.do'),
                // 오류시 처리로직
                error: $.ajaxUtil.error,
                // 저장후 처리로직
                success: function(ret) {
					$.commMsg.success(ret, '성공적으로 삭제되었습니다.', function() {
						doSearchEnt();
					});
                }
            }).submit();
        });

        return false;
    }

	// 참여 경영체 사업계획서 저장하기
    //--------------------------------------------------------//
    function doSaveEnt() {
		// 휴대전화 병합
		$.formUtil.mergeData('telno', 'phone', 3);
		// 사업자번호 병합
		$.formUtil.mergeData('brno', 'bzno', 3);

        // 등록폼의 VALIDATION 기능 활성화
        if (P_MFORM2.validate().settings) {
			P_MFORM2.validate().settings.ignore = false;
		}

        //FORM VALIDATION
        if (P_MFORM2.valid() === false) {
			return false;
		}

		// 첨부파일 VALIDATION (comm_component.js)
		var fvalidate = P_FILE_BOX.appBizFile('validate', {
			isAlert: true,
			isExt:   true,
			isLimit: true
		});
		if(!fvalidate) {
			return false;
		}
		
		var fvalidate2 = P_FILE_BOX2.appBizFile('validate', {
			isAlert: true,
			isExt:   true,
			isLimit: true
		});
		if (!fvalidate2) {
			return false;
		}
		
		$.formUtil.toForm({
			evntNo : $.formUtil.getValue($('#registForm'), 'evntNo'),
		}, P_MFORM2);

        $.commMsg.confirm("저장하시겠습니까?", function() {
            // 등록폼을 AJAX로 저장처리
            P_MFORM2.ajaxForm({
                url: getUrl('/adm/invest/event/saveEventEnt.do'),
                // 오류시 처리로직
                error: $.ajaxUtil.error,
                // 저장후 처리로직
                success: function(ret) {
					$.commMsg.success(ret, '성공적으로 저장되었습니다.', function() {
                        // TODO. 참여 경영체 사업계획서 등록 후 작업
						P_MODAL2.modal('hide');
						doSearchEnt();
						P_MFORM2.form('reset');
					});
                }
            }).submit();
        });
        return false;
    }

	// 참여 경영체 사업계획서 삭제하기
    //--------------------------------------------------------//
    function doDeltEnt() {
		var data				= P_ENTGRID.datagrid('getChecked');
		var evntPartcptnNoArr	= data.map(function(e) { return e.evntPartcptnNo; });
		var evntNo				= $.formUtil.getValue(P_RFORM, 'evntNo');
		
		if(evntPartcptnNoArr.length < 1) {
			$.commMsg.alert('삭제할 참여 경영체 사업계획서를 선택해주세요.');
			return false;
		}
		
		$.commMsg.confirm("정말로 삭제하시겠습니까?", function() {
			// form 안에 bzentyNo의 name을 가진 input 태그 세팅
			var html = '';
			evntPartcptnNoArr.forEach(function(e, i) {
				html += '<input type="hidden" name="evntPartcptnNoArr" value="' + e + '"/>';
			});
			html += '<input type="hidden" name="mode" value="D"/>';
			html += '<input type="hidden" name="evntNo" value="' + evntNo + '"/>';
			P_MFORM1.find('div.hidden-div').html(html);
	
            // 등록폼을 AJAX로 저장처리
            P_MFORM1.ajaxForm({
                url: getUrl('/adm/invest/event/saveEventEnt.do'),
                // 오류시 처리로직
                error: $.ajaxUtil.error,
                // 저장후 처리로직
                success: function(ret) {
					$.commMsg.success(ret, '성공적으로 삭제되었습니다.', function() {
						doSearchEnt();
					});
                }
            }).submit();
        });

        return false;
	}

	// 투자자 추가 모달 팝업 이벤트
    //--------------------------------------------------------//
    function doClickModal1() {
		console.log('doClickModal1()');
		doSearchInvt();

        return false;
    }

	// 경영체 사업계획서 추가 모달 팝업 이벤트
    //--------------------------------------------------------//
    function doClickModal2() {
		// TODO 1건이라도 투자자 검토의견이 작성된 기록이 있으면 추가 불가능
		
		var obj = P_RFORM.serializeObject();
		
		$.ajaxUtil.ajaxLoad(
            getUrl('/adm/invest/event/getEventEntExist.do'), 
            obj,
            function(ret) {
				const result = ret.Message;
				if (result) {
					$.commMsg.alert('투자자 검토의견이 작성된 경우 경영체를 추가할 수 없습니다.');
				} else {
					P_MODAL2.modal('show');
				}
            }
        );

        return false;
    }
	
    // 취소버튼 클릭시 이벤트 처리
    //--------------------------------------------------------//
    $('#btnCancel').bind('click', function() {
		// 부모창의 doClose 호출
		P_MODAL.doClose();
        return false;
	});
	
	// 조합원 목록 모달 팝업시 발생하는 함수
	P_MODAL1.on('shown.bs.modal', function (e) {
		P_GRID_INVT.datagrid('resize');
	});
	
	P_MODAL2.on('shown.bs.modal', function (e) {
	});
	
	P_MODAL1_BTN.bind('click', doClickModal1);
	P_MODAL2_BTN.bind('click', doClickModal2);
	
	// 참여 투자자/경영체 탭 클릭시 이벤트 처리
	P_EVNT_TYPE_BTN.bind('click', changeTab);
	
	$('#btnSearchInvt').bind('click', function() {
		doSearchInvt();
	})
	
	// 투자자 등록버튼 클릭시 이벤트 처리
    $('#btnSaveInvt').bind('click', doSaveInvt);
	// 사업경영체 등록버튼 클릭시 이벤트 처리
	$('#btnSaveEnt' ).bind('click', doSaveEnt);
	
	// 투자자 삭제버튼 클릭시 이벤트 처리
	$('#btnRemoveInvt').bind('click', doDeltInvt);
	// 사업경영체 삭제버튼 클릭시 이벤트 처리
	$('#btnRemoveEnt ').bind('click', doDeltEnt);
	
	// 사업경영체 취소버튼 클릭시 이벤트 처리
	$('#btnCloseEnt').bind('click', function() { P_MODAL2.modal('hide'); });
	
	// modal backdrop의 rootElement를 해당 모달로 변경해서 정상적으로 표출되도록 함
	new bootstrap.Modal('#myModal1')._backdrop._config.rootElement = $('.detail-pop-1');
	new bootstrap.Modal('#myModal2')._backdrop._config.rootElement = $('.detail-pop-1');
	
	doSearchEnt();
	
	// 검색어 입력 엔터 이벤트 처리
	bindEnter($('#srchTextInvt'), $('#btnSearchInvt'));
	
});