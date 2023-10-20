/**
******************************************************************************************
*** 파일명 : listRoleMenu.js
*** 설명글 : 역할별메뉴관리 관리 화면 스크립트
***
*** -----------------------------    Modified Log   --------------------------------------
*** ver             date                author                  description
*** --------------------------------------------------------------------------------------
*** 1.0         2021.09.06    			LSH
*** 1.1      	2023.07.23    			J H        				작업 완료 .
******************************************************************************************
**/
$(function() {

    //========================================================//
    // 화면 스크립트 내에 사용할 객체,상수 정의
    //--------------------------------------------------------//
    var P_SFORM  		= $('#searchForm' 	 ); // 검색폼 객체
    var P_RO_GRID   	= $('#appRoleGrid'   ); // 그리드 객체
    var P_ME_GRID   	= $('#appMenuGrid'   ); // 그리드 객체
    var ROLE_MENU_DATA 	= {};
    
    var P_FORMAT = {
	    roleId: function(value, row) {
		    return row['roleNm']+' ['+row['roleId']+']';
        	}
    	};
	
    //========================================================//
    // 콤보박스 정의
    //--------------------------------------------------------//
	$('#srchRoleId').appComboBox({
		url: getUrl('/com/common/getComboRole.do'),
		loadFilter : function(data) {
			return data;
		},
		callback: doSearch
	});
	$('#srchSysCd').appComboBox({
		params: {upCdId: CODE.SYS_SE.code},
		loadFilter : function(data) {
			return data;
		},
		callback: doSearch
	});
    
    //========================================================//
    // 목록 GRID 정의 (EasyUI datagrid)
    //--------------------------------------------------------//
	P_RO_GRID.appBbsGrid({
		// 목록 KEY필드
		idField:    'menuId',
    	// 목록 제목
		title:		'권한 적용 메뉴 목록',
		// 검색 URL
		url:		getUrl('/adm/sys/role/getListRoleMenu.do'),
        // 조회 파라미터 전달
		queryParams: { srchRoleId:'ROLE_ADM_MNG' },
	    //목록 검색 페이징 객체
		pagination: { display: 20 },
		// 페이지 표시 객체
		paging:     '#appGridPagination',
		// 검색결과 표시 객체
		result:     '#appRoleGridResult',
		// 검색결과 메세지
		resultText: '권한 적용 메뉴 목록',
		// 리스트옵션
		listOptions: {
			// 검색결과 없을경우 메세지
			emptyText:  '검색된 {title}가 없습니다.',
			// 목록칼럼 너비
			colgroup: ['60px','150px','150px','130px'],
			// 목록칼럼 정의
			columns: [
				{name:'menuId'	, 
				cls:'app-c' ,
				label:'<div class="check-radio-box"><input type="checkbox" name="" id="RO_Checkbox"><label for="RO_Checkbox"></label></div>', 
				formatter: function(value, row) {
					var checkbox = document.createElement('input');
					checkbox.type = 'checkbox';
					checkbox.value = row.menuId;
					checkbox.id = 'roCheckbox';
					checkbox.title = 'input';
					ROLE_MENU_DATA[checkbox.value] = row; // map에 row 데이터 저장
					return checkbox.outerHTML;
				}},
	            {name:'roleId'       ,cls:'app-c' ,label:'역할' , formatter: P_FORMAT.roleId},
	            {name:'menuNm'       ,cls:'app-c' ,label:'메뉴명'},
	            {name:'menuId'       ,cls:'app-c' ,label:'메뉴ID'},
	        ],
		},
	}).appBbsGrid('init');
    
    //========================================================//
    // 목록 GRID 정의 (EasyUI datagrid)
    //--------------------------------------------------------//
	P_ME_GRID.appBbsGrid({
		// 목록 KEY필드
		idField:    'menuId',
    	// 목록 제목
		title:		'추가 대상 메뉴관리',
		// 검색 URL
		url:		getUrl('/adm/sys/role/getListNotRoleMenu.do'),
        // 조회 파라미터 전달
		queryParams: { srchRoleId:'ROLE_ADM_MNG'},
	    //목록 검색 페이징 객체
		pagination: { display: 20 },
		// 페이지 표시 객체
		paging:     '#appGridPagination',
		// 검색결과 표시 객체
		result:     '#appMenuGridResult',
		// 검색결과 메세지
		resultText: '추가 대상 메뉴 목록',
		// 리스트옵션
		listOptions: {
			// 검색결과 없을경우 메세지
			emptyText:  '검색된 {title}가 없습니다.',
			// 목록칼럼 너비
			colgroup: ['80px','300px','250px'],
			// 목록칼럼 정의
			columns: [
				{	name:'menuId'	, 
					cls:'app-c' ,
					label:'<div class="check-radio-box"><input type="checkbox" name="" id="ME_Checkbox"><label for="ME_Checkbox"></label></div>', 
					formatter: function(value, row) {
						var checkbox = document.createElement('input');
						checkbox.type = 'checkbox';
						checkbox.value = row.menuId;
						checkbox.id = 'meCheckbox';
						checkbox.title = 'input';
						ROLE_MENU_DATA[checkbox.value] = row; // map에 row 데이터 저장
						return checkbox.outerHTML;
					}
				},
	            {name:'menuNm'       ,cls:'app-l' ,label:'메뉴명'},
	            {name:'menuId'       ,cls:'app-l' ,label:'메뉴ID'},
	        ],
		},
	}).appBbsGrid('init');
    
	// 로딩시 초기화 및 검색실행
	doReset();
	
	$('#RO_Checkbox').change(function() {
	    var isChecked = $(this).is(':checked');
	    $('#roCheckbox[type="checkbox"]').prop('checked', isChecked).trigger('change');
	});

	$('#ME_Checkbox').change(function() {
	    var isChecked = $(this).is(':checked');
	    $('#meCheckbox[type="checkbox"]').prop('checked', isChecked).trigger('change');
	});
	
    // 역할별프로그램관리 검색처리
    //--------------------------------------------------------//
    function doSearch() {
        doSearchRole();
        doSearchMenu();
    }
    // 역할별메뉴목록 검색처리
    //--------------------------------------------------------//
    function doSearchRole() {
        // 검색폼 데이터 객체화
        var obj = P_SFORM.serializeObject();
        // 검색폼 그리드 검색
        P_RO_GRID.appBbsGrid('search', obj);
    }
    // 추가할메뉴목록 검색처리
    //--------------------------------------------------------//
    function doSearchMenu() {
        // 검색폼 데이터 객체화
        var obj = P_SFORM.serializeObject();
        // 검색폼 그리드 검색
        P_ME_GRID.appBbsGrid('search', obj);
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

    // 역할별메뉴관리 삭제하기
    //--------------------------------------------------------//
    function doRemove() {
    	// 역할별메뉴목록의 선택 항목
        var check_RO_Rows 	=  [];
        $("#appRoleGrid input[type=checkbox]:checked").each(function() {
            var row = ROLE_MENU_DATA[$(this).val()];
            if(row) {
                check_RO_Rows.push(row);
            }
        });
        const rows = check_RO_Rows;
        
        if (rows.length == 0) {
            $.commMsg.alert('제외할 메뉴를 선택하세요.');
            return false;
        }
        
        $.commMsg.confirm(rows.length+"개의 메뉴를 제외하시겠습니까?", function() {
            // AJAX로 저장처리
            $.ajaxUtil.ajaxSave(
                getUrl('/adm/sys/role/saveRoleMenu.do'), 
                JSON.stringify({
                    mode: MODE.REMOVE,
                    roleList: rows
                }),
                function(ret) {
                    $.commMsg.success(ret, '성공적으로 제외되었습니다.', doSearch); 
                }
            );
        });
        return false;
    }

    // 역할별메뉴관리 추가하기
    //--------------------------------------------------------//
    function doAppend() {
    	// 역할별메뉴목록의 선택 항목
        var check_ME_Rows 	=  [];
        $("#appMenuGrid input[type=checkbox]:checked").each(function() {
            var row = ROLE_MENU_DATA[$(this).val()];
            if(row) {
            	check_ME_Rows.push(row);
            }
        });
        const rows = check_ME_Rows;
    	
        // 역할ID
        const roleId = $('#srchRoleId').val();

        if (rows.length == 0) {
	        $.commMsg.alert('추가할 메뉴를 선택하세요.');
            return false;
        }
        if ($.commUtil.empty(roleId)) {
            $.commMsg.alert('역할ID를 확인할 수 없습니다.');
            return false;
        }
        
        $.commMsg.confirm(rows.length+"개 메뉴를 추가하시겠습니까?", function() {
            // AJAX로 저장처리
            $.ajaxUtil.ajaxSave(
                getUrl('/adm/sys/role/saveRoleMenu.do'), 
                JSON.stringify({
	                mode: MODE.INSERT,
                    roleId: roleId,
                    roleList: rows
                }),
                function(ret) {
					$.commMsg.success(ret, '성공적으로 추가되었습니다.', doSearch);
                }
            );
        });
        return false;
    }
    // 추가 클릭시 이벤트 처리
    $('#btnAppend').bind('click', doAppend);
    // 삭제버튼 클릭시 이벤트처리
    $('#btnRemove').bind('click', doRemove);
	// 초기화버튼 클릭시 이벤트처리
    $('#btnReset' ).bind('click', doReset);
	// 검색버튼 클릭시 이벤트 처리
    $('#btnSearch').bind('click', doSearch);
    // 기존 테이블 스타일 1280px로 화면비율 맞지 않아 수정
    $('[class*="table-box-"].overtable table').css('min-width', '600px');
    // 검색어 입력 엔터 이벤트 처리
    bindEnter($('#srchText'), $('#btnSearch'));
});
