/**
******************************************************************************************
*** 파일명 : listMenu.js
*** 설명글 : 메뉴관리 관리 화면 스크립트
***
*** -----------------------------    Modified Log   --------------------------------------
*** ver             date                author                  description
*** --------------------------------------------------------------------------------------
*** 1.0         2021.09.05    LSH
*** 1.0         2021.11.02    LSH   디자인적용 및 개발 수정
******************************************************************************************
**/
$(function() {

    //========================================================//
    // 화면 스크립트 내에 사용할 객체,상수 정의
    //--------------------------------------------------------//
    var P_GRID   	= $('#appGrid'    ); // 그리드 객체
    var P_SFORM  	= $('#searchForm' ); // 검색폼 객체
    var P_RFORM  	= $('#registForm' ); // 등록폼 객체
    //========================================================//
    // FORM ELEMENTS 정의
    //--------------------------------------------------------//
    // 검색용 상위코드 콤보박스 (EasyUI 콤보박스)
	$('#srchSysCd').appComboBox({
		params: {upCdId: CODE.SYS_SE.code},
		loadFilter : function(data) {
			return data;
		},
		change : function() {
			var value = $(this).val();
			$('#srchUpId').appComboBox({
				url : getUrl('/com/common/getComboMenu.do'),
				params : {
					sysCd : value
				},
				loadFilter : function(data2) {
					data2.unshift(COMBO.INIT_SELECT);
					return data2;
				},
			});
		},
		callback : function(){
			doSearch();
		}
	});
	
	$('#srchUpId').appComboBox({
		url : getUrl('/com/common/getComboMenu.do'),
		params : {
			sysCd : SYSTEM.ADMIN.code
		},
		loadFilter : function(data2) {
			data2.unshift(COMBO.INIT_SELECT);
			return data2;
		},
	});
    
    //========================================================//
    // 목록 GRID 정의 (EasyUI datagrid)
    //--------------------------------------------------------//
    P_GRID.appBbsGrid({
		// 목록 KEY필드
		idField:    'menuId',
    	// 목록 제목
		title:		'메뉴관리',
		// 검색 URL
		url:		getUrl('/adm/sys/menu/getListMenu.do'),
        // 조회 파라미터 전달
		queryParams: {
		},
	    //목록 검색 페이징 객체
		pagination: { display: 20 },
		// 페이지 표시 객체
		paging:     '#appGridPagination',
		// 검색결과 표시 객체
		result:     '#appGridResult',
		// 검색결과 메세지
		resultText: '<em class="ml-4px">{total}</em>개의 공통코드가 있습니다.',
		// 리스트옵션
		listOptions: {
			// 검색결과 없을경우 메세지
			emptyText:  '검색된 {title}가 없습니다.',
			// 목록칼럼 너비
			colgroup: ['150px','80px','150px','80px','80px','80px','80px'],
			// 목록칼럼 정의
			columns: [
	            {name:'menuId'         ,cls:'app-l' ,label:'메뉴ID'	},
	            {name:'menuNm'         ,cls:'app-l' ,label:'메뉴명'	},
	            {name:'trgtUrl'        ,cls:'app-l' ,label:'타겟 URL' },
	            {name:'menuLvl'        ,cls:'app-c' ,label:'레벨'		},
	            {name:'menuOrdr'       ,cls:'app-c' ,label:'순서'    , formatter:$.commFormat.number},
	            {name:'popupYn'        ,cls:'app-c' ,label:'팝업 여부' , formatter:$.commFormat.useyn},
	            {name:'useYn'          ,cls:'app-c' ,label:'사용 여부' , formatter:$.commFormat.useyn},
	        ],
	        // 행선택시 상세조회
	        select: doSelect	
		},
	}).appBbsGrid('init');
	// 로딩시 초기화 및 검색실행
	doReset();
    
    // 게시판 검색처리
    //--------------------------------------------------------//
    function doSearch() {
        // 검색폼 데이터 객체화
        var obj = P_SFORM.serializeObject();
        // 검색폼 그리드 검색
		P_GRID.appBbsGrid('search', obj);
        return false;
    }
    
    // 게시판 검색리셋
    //--------------------------------------------------------//
    function doReset() {
        // 검색폼 입력데이터 초기화
    	P_SFORM.form('reset');
        // 검색폼 그리드 검색 처리
        doSearch();
        return false;
    }
    // 신청현황관리 수정하기
    //--------------------------------------------------------//
    function doSelect(row) {
		// 상세조회 모달팝업 오픈
		P_MODAL.doOpenSelect(row);
        return false;
    }

    // 제출서류 신청 등록하기
    //--------------------------------------------------------//
    function doRegist() {
        P_MODAL.doOpenRegist({});
        return false;
    }
    // 등록버튼 클릭시 이벤트 처리
    $('#btnRegist').bind('click', doRegist);
	// 초기화버튼 클릭시 이벤트처리
    $('#btnReset' ).bind('click', doReset);
	// 검색버튼 클릭시 이벤트 처리
    $('#btnSearch').bind('click', doSearch);

    // 검색어 입력 엔터 이벤트 처리
    bindEnter($('#srchText'), $('#btnSearch'));
});    
    
//========================================================//
//등록,수정,상세 모달팝업 정의
//- 오픈된 모달창에서 사용하기 위해 외부에 정의함
//--------------------------------------------------------//
let P_MODAL = {
	// 모달팝업 객체
	modal: $('<div></div>'),
	// 상세조회 모달팝업 오픈
	doOpenSelect: function( row ) {
		P_MODAL.modal.appModal({
			cls:        'w-ver1',
			title:      '메뉴관리 상세조회',
			url:        getUrl('/adm/sys/menu/modalMenuForm.do'),
			params:     JSON.stringify({
				menuId        : row['menuId'  ],
				upMenuId      : row['upMenuId'  ],
				sysCd      	  : row['sysCd'  ],
			})
		}).open();
	},
	// 신규등록 모달팝업 오픈
	doOpenRegist: function( row ) {
		P_MODAL.modal.appModal({
			cls:        'w-ver1',
			title:      '메뉴관리 신규등록',
			url:        getUrl('/adm/sys/menu/modalMenuForm.do'),
			params:     JSON.stringify({
				mode       : MODE.INSERT,
			})
		}).open();
	},
	// 수정하기 모달팝업 오픈
	doOpenUpdate: function( row) {
		P_MODAL.modal.appModal({
			cls:        'w-ver1',
			title:      '메뉴관리 수정하기',
			url:        getUrl('/adm/sys/menu/modalMenuForm.do'),
			params:     JSON.stringify({
				mode        : MODE.UPDATE,
				menuId      : row['menuId'  ],
				upMenuId    : row['upMenuId'  ],
				sysCd    	: row['sysCd'  ],
			})
		}).open();
	},
	// [모달팝업 호출용] 모달팝업 닫기
	doClose: function() {
		if (P_MODAL.modal && 
			P_MODAL.modal.close)
			P_MODAL.modal.close();
	},
	// [모달팝업 호출용] 목록 재검색 및 팝업 닫기
	doSearch: function() {
		// 모달팝업 닫기
		P_MODAL.modal.close();
	    // 검색폼 데이터 객체화
	    var obj = $('#searchForm').serializeObject();
	    // 검색폼 그리드 검색
		$('#appGrid').appBbsGrid('search', obj);
	}
};
    
   /* //========================================================//
    // 콤보박스 정의
    //--------------------------------------------------------//
	// 검색용 상위메뉴 EasyUI Combo Tree
	let S_UPPER = $('#srchUpId').combotree({
		editable: true,
		prompt: ' 상위메뉴 선택',
		iconWidth: 22,
        icons:[{
            iconCls:'icon-clear',
            handler: function() {
                $('#srchUpId').combotree('clear');
            }
        }]
	});
    // 등록용 상위메뉴 EasyUI Combo Tree
	let R_UPPER = $('#upMenuId').combotree();
    // 검색용 시스템구분 콤보박스
	$('#srchSysCd').appComboBox({
		params: {upCdId: CODE.SYSTEM},
		value:   SYSTEM.ADMIN['code'],
		change:  function() {
			doLoadComboTree(S_UPPER, {sysCd: $(this).val()});
		},
        callback: function() {
			doLoadComboTree(S_UPPER, {sysCd: $('#srchSysCd').val()});
            doSearch();
        }
	});
    // 등록용 시스템구분 콤보박스
	$('#sysCd').appComboBox({
		params: {upCdId: CODE.SYSTEM},
		value:   SYSTEM.ADMIN['code'],
		change:  function() {
			doLoadComboTree(R_UPPER, {sysCd: $(this).val()}, ROOT_MENU['code']);
		},
        callback: function() {
			doLoadComboTree(R_UPPER, {sysCd: $('#sysCd').val()}, ROOT_MENU['code']);
        }
	});
    // 사용여부 라디오박스
	$('#appUseYn').appSelectBox({
		form: 'radio',
		name: 'useYn',
		type: 'static',
		rows: STORE['USE_YN']
	});
    // 팝업여부 라디오박스
	$('#appPopupYn').appSelectBox({
		form: 'radio',
		name: 'popupYn',
		type: 'static',
		rows: STORE['POP_YN']
	});
	
	// 메뉴 트리형태 콤보를 로드한다.
    //--------------------------------------------------------//
	function doLoadComboTree( combo, params, value ) {
		// 콤보의 선택값 초기화
		combo.combotree('clear');
		var data = $.ajaxUtil.ajaxDefault(getUrl('/com/common/getComboRole.do'),
		    $.extend(params, {
				rootId:   ROOT_MENU['code'],
				rootText: ROOT_MENU['text']
			})
		);
		if (value) {
			if ($('#mode').val() == MODE.UPDATE) {
				// 선택된 메뉴 및 하위메뉴를 제거한다.
				data = $.easyUtils.removeNode(data, $('#menuId').val());
			}
		}
		combo.combotree('loadData', data);
		if (value) {
			combo.combotree('setValue', value);
		}
	}

    //========================================================//
    // 등록폼 VALIDATION RULE 정의
    //--------------------------------------------------------//
    P_RFORM.validate({
        debug: false,
        onsubmit: false,
        onfocusout: false,
        // 검증룰 정의
        rules: {
            sysCd      : {required: true},
            upMenuId   : {required: true},
            useYn      : {required: true},
            menuId     : {required: true},
            menuNm     : {required: true},
            menuLvl    : {required: true}
        },
        // 검증메세지 정의
        messages: {
            sysCd      : {required: '시스템은 필수 선택 사항입니다.'},
            upMenuId   : {required: '상위메뉴는 필수 선택 사항입니다.'},
            useYn      : {required: '사용여부는 필수 선택 사항입니다.'},
            menuId     : {required: '메뉴ID는 필수 입력 사항입니다.'},
            menuNm     : {required: '메뉴명은 필수 입력 사항입니다.'},
            menuLvl    : {required: '메뉴레벨은 필수 입력 사항입니다.'}
        },
        //invalidHandler: $.easyValidate.handler,
        //errorPlacement: $.easyValidate.placement
        invalidHandler: validateHandler,
        errorPlacement: validatePlacement
    });

    // 메뉴관리 검색처리
    //--------------------------------------------------------//
    function doSearch() {
		// 선택된 항목 CLEAR
		P_GRID.treegrid('clearSelections');
        // 검색폼 데이터 객체화
        var frmobj = $.extend(P_SFORM.serializeObject(), {tree:'Y'});
        // 그리드 목록조회 URL
        P_GRID.treegrid('options')['url'] = getUrl('/adm/sys/menu/getListMenu.do');
        // 검색폼 그리드 검색
        P_GRID.treegrid('load', frmobj);

        return false;
    }

    // 메뉴관리 검색리셋
    //--------------------------------------------------------//
    function doReset() {
    	// 등록폼 초기화
    	doRegist();
        // 검색폼 입력데이터 초기화
        P_SFORM.form('reset');
        // 검색폼 그리드 검색 처리
        doSearch();

        return false;
    }

    // 메뉴관리 등록취소
    //--------------------------------------------------------//
    function doUndo() {
        doRegist();
        return false;
    }

    // 메뉴관리 수정하기
    //--------------------------------------------------------//
    function doUpdate(row) {

        var params = {menuId: row['menuId']};

        $.ajaxUtil.ajaxLoad(
            getUrl('/adm/sys/menu/getMenu.do'),
			params,
            function(result) {
                var data = result.Data;
                if (data) {
                    // 등록패널 제목변경
					$('#registTitle').html(P_TITLE+'수정');
					// 수정모드 정의
					data['mode'] = MODE.UPDATE;
                    // 폼데이터 셋팅
                    $.formUtil.toForm(data, P_RFORM);
                    // 상위메뉴 재로딩
					doLoadComboTree(R_UPPER, {sysCd: data['sysCd']}, data['upMenuId']);
                    // ID입력값 입력비활성화
					$('#menuId').prop('readonly', true);
                    // 삭제버튼 표시
                    $('#btnRemove').show();
                }
            }
        );
        return false;
    }

    // 메뉴관리 등록하기
    //--------------------------------------------------------//
    function doRegist() {
        // 등록패널 제목변경
    	$('#registTitle').html(P_TITLE+'등록');
        // 등록폼 리셋
        P_RFORM.form('reset');
        // ID입력값 입력활성화
		$('#menuId').prop('readonly', false);
		// 등록기본값 정의
        $.formUtil.toForm({
			mode   : MODE.INSERT,       // 등록모드
			useYn  : 'Y',               // 사용여부(Y)
			popupYn: 'Y',               // 팝업여부(N)
			upMenuId: ROOT_MENU['code'] // 상위메뉴
		}, P_RFORM);
        // 삭제버튼 숨김
        $('#btnRemove').hide();

        return false;
    }

    // 메뉴관리 삭제하기
    //--------------------------------------------------------//
    function doRemove() {
        var params = $.formUtil.toObject(P_RFORM, ['menuId']);
        if ($.commUtil.empty(params['menuId'])) {
            $.easyMsg.alert('삭제할 대상을 선택하세요.');
            return false;
        }

    	$.easyMsg.confirm("정말 삭제하시겠습니까?", function() {
			// 삭제모드 정의
			$.formUtil.toForm({mode:MODE.REMOVE}, P_RFORM);
            // 등록폼을 AJAX로 저장처리
            P_RFORM.ajaxForm({
                url: getUrl('/adm/sys/menu/saveMenu.do'),
                // 오류시 처리로직
                error: $.ajaxUtil.error,
                // 저장후 처리로직
                success: function(ret) {
					$.easyMsg.success(ret, '성공적으로 삭제되었습니다.', function() {
                        doRegist();
                        doSearch();
					});
                }
            }).submit();
    	});
        return false;
    }

    // 메뉴관리 저장하기
    //--------------------------------------------------------//
    function doSave() {

        // 등록폼의 VALIDATION 기능 활성화
        if (P_RFORM.validate().settings)
            P_RFORM.validate().settings.ignore = false;

        //FORM VALIDATION
        if (P_RFORM.valid() === false)
            return false;

        $.easyMsg.confirm("저장하시겠습니까?", function() {
            // 등록폼을 AJAX로 저장처리
            P_RFORM.ajaxForm({
                url: getUrl('/adm/sys/menu/saveMenu.do'),
                // 오류시 처리로직
                error: $.ajaxUtil.error,
                // 저장후 처리로직
                success: function(ret) {
					$.easyMsg.success(ret, '성공적으로 저장되었습니다.', function() {
                        doRegist();
                        doSearch();
					});
                }
            }).submit();
        });
        return false;
    }


    // 검색버튼 클릭시 이벤트 처리
    $('#btnSearch').bind('click', doSearch);
    // 리셋버튼 클릭시 이벤트처리
    $('#btnReset' ).bind('click', doReset);
    // 등록버튼 클릭시 이벤트처리
    $('#btnRegist').bind('click', doRegist);

    // 저장버튼 클릭시 이벤트 처리
    $('#btnSave'  ).bind('click', doSave);
    // 삭제버튼 클릭시 이벤트처리
    $('#btnRemove').bind('click', doRemove);
    // 취소버튼 클릭시 이벤트처리
    $('#btnUndo'  ).bind('click', doUndo);

    // 등록폼 초기화
    doRegist();
    // 검색어 입력 엔터 이벤트 처리
    bindEnter($('#srchText'), $('#btnSearch'));*/
