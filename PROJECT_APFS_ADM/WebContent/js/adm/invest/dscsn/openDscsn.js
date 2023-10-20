/**
***************************************************************************************************
*** 파일명 : openDscsn.js
*** 설명글 : 지원사업관리-신청현황관리 화면 스크립트
***
*** -----------------------------    Modified Log   -----------------------------------------------
*** ver             date                author                  description
*** -----------------------------------------------------------------------------------------------
*** 1.0         2023.06.27               J H          	      상담일지 오픈 화면 작성
***************************************************************************************************
**/
$(function() {
    //========================================================//
    // 화면 스크립트 내에 사용할 객체,상수 정의
    //--------------------------------------------------------//
    var P_GRID   = $('#appGrid'   ); // 그리드 객체
    var P_SFORM  = $('#searchForm'); // 검색폼 객체
    var P_RFORM  = $('#registForm'); // 등록폼 객체

    //========================================================//
    // 목록 GRID 정의 (EasyUI datagrid)
    //--------------------------------------------------------//
    P_GRID.appBbsGrid({
		// 목록 KEY필드
		idField:    'sn',
		// 목록 제목
		title:		'상담일지',
		// 검색 URL
		url:		getUrl('/adm/invest/dscsn/getListDscsn.do'),
        // 조회 파라미터 전달
		queryParams: { },
		//목록 검색 페이징 객체
		pagination: { display: 20 },
		// 페이지 표시 객체
		paging:     '#appGridPagination',
		// 검색결과 표시 객체
		result:     '#appGridResult',
		// 검색결과 메세지
		resultText: '<em class="ml-4px">{total}</em>개의 상담일지 목록이 있습니다.',
		// 리스트옵션
		listOptions: {
			// 검색결과 없을경우 메세지
			emptyText:  '검색된 {title}가 없습니다.',
			// 목록칼럼 너비
			colgroup: ['80px','150px','450px','150px','150px','150px'],
			// 목록칼럼 정의
			columns: [
				{name:'sn'       			,cls:'app-c', label:'번호' 	, rownumbers: true},      // 농식품클라우드펀딩
				{name:'dscsnDate'			,cls:'app-c', label:'상담일자', formatter: function(v,o) {
																		    if(v === '0001-01-01') {
																		        return ''; // If the date is '0001-01-01', return an empty string
																		    } else {
																		        return v; // Else return the date as it is
																		    }
																		}},   // 수수료
				{name:'bzentyNm'			,cls:'app-l', label:'경영체명'},   // 경영체명
				{name:'dscsnMthdNm'			,cls:'app-c', label:'상담종류'},   // 경영체회원, 개인회원
				{name:'dscsnCntrNm'			,cls:'app-c', label:'상담센터'},   // 상담센터
				{name:'cnslrNm'				,cls:'app-l', label:'상담자' },    // 경영체회원, 개인회원
			],
			// 목록형인 경우 기본 스타일시트
			//gridCls: "table-box-1",
	        // 행선택시 상세조회
	        select: doSelect	
		},
    }).appBbsGrid('init');
    
    // 검색 실행
    doReset();
   
    // 엑셀 다운로드시 핸드폰 번호 -기호 추가
    function formatPhoneNumber(phoneNumber) {
    	  var cleaned = ('' + phoneNumber).replace(/\D/g, '');
    	  var match = cleaned.match(/^(0\d{1,2})(\d{3,4})(\d{4})$/);
    	  if (match) {
    	    return match[1] + '-' + match[2] + '-' + match[3];
    	  }
    	  return phoneNumber;
	}
	// 엑셀 다운로드시 사업자 번호 -기호 추가
	function formatBusinessNumber(number) {
		  var cleaned = ('' + number).replace(/\D/g, '');
		  var match = cleaned.match(/^(\d{3})(\d{2})(\d{5})$/);
		  if (match) {
		    return match[1] + '-' + match[2] + '-' + match[3];
		  }
		  return number;
	}
	
	function downloadExcel() {   
    	//새 workbook 생성
	    var workbook = new ExcelJS.Workbook();
	    // 새 worksheet 생성
	    var worksheet = workbook.addWorksheet('상담일지 관리');
	    
	    var headerStyle = {
	    		  font: { bold: true },
	    		  fill: { type: 'pattern', pattern:'solid', fgColor:{argb:'FFFF00'}},
	    		  border: {
	    	        top: { style: 'thin' },
	    	        left: { style: 'thin' },
	    	        bottom: { style: 'thin' },
	    	        right: { style: 'thin' }
	    		  }
    		};
	    var tableStyle = {
	    	    border: {
	    	        top: { style: 'thin' },
	    	        left: { style: 'thin' },
	    	        bottom: { style: 'thin' },
	    	        right: { style: 'thin' }
	    	    }
	    	};
	    
	    // 헤더 데이터와 스타일 지정
	    worksheet.columns = [
	        {header: '번호'				, key: 'sn'				, width: 10, style:tableStyle},
	        {header: '상담일자'			, key: 'dscsnYmd'		, width: 15, style:tableStyle},
	        {header: '경영체명'			, key: 'bzentyNm'		, width: 20, style:tableStyle},
	        {header: '사업자등록번호'		, key: 'brno'			, width: 20, style:tableStyle},
	        {header: '상담종류'			, key: 'dscsnMthdNm'	, width: 15, style:tableStyle},
	        {header: '상담센터'			, key: 'dscsnCntrNm'	, width: 15, style:tableStyle},
	        {header: '상담자'				, key: 'cnslrNm'		, width: 15, style:tableStyle},
	        {header: '대표자명'			, key: 'rprsvNm'		, width: 15, style:tableStyle},
	        {header: '대표자 생년월일'		, key: 'brdt'			, width: 15, style:tableStyle},
	        {header: '성별'				, key: 'sexdstn'		, width: 10, style:tableStyle},
	        {header: '담당자명'			, key: 'picNm'			, width: 15, style:tableStyle},
	        {header: '부서/직책'			, key: 'picDeptNm'		, width: 15, style:tableStyle},
	        {header: '연락처'				, key: 'picTelno'		, width: 15, style:tableStyle},
	        {header: '이메일'				, key: 'picEmlAddr'		, width: 20, style:tableStyle},
	        {header: '설립연도'			, key: 'fndnYmd'		, width: 10, style:tableStyle},
	        {header: '임직원수(명)'			, key: 'empCnt'			, width: 15, style:tableStyle},
	        {header: '경영체 유형'			, key: 'bzentyTypeCdNm' , width: 15, style:tableStyle},
	        {header: '사업분야'			, key: 'bizFldNm'		, width: 15, style:tableStyle},
	        {header: '투자분야'			, key: 'invtFldNm'		, width: 15, style:tableStyle},
	        {header: '업종구분'			, key: 'tpbizSeNm'		, width: 15, style:tableStyle},
	        {header: '산업구분'			, key: 'industSeNm'		, width: 15, style:tableStyle},
	        {header: '사업내용(주생산품)'	, key: 'bizCn'			, width: 20, style:tableStyle},
	        {header: '전년도 매출액(백만원)'	, key: 'slsAmt'			, width: 20, style:tableStyle},
	        {header: '본사 소재지'			, key: 'lctnAddr1'		, width: 15, style:tableStyle},
	        {header: '대표공장 소재지'		, key: 'lctnAddr2'		, width: 15, style:tableStyle},
	        {header: '상담내용'			, key: 'dscsnCn'		, width: 20, style:tableStyle},
	        {header: '홈페이지'			, key: 'hmpgAddr'		, width: 20, style:tableStyle}
	    ];
	    
	    var rawData = $.ajaxUtil.ajaxDefault(getUrl('/adm/invest/dscsn/getListDscsn.do')).rows;
	    
	 // 2. 데이터를 2차원 배열로 변환합니다.
		var data = rawData.map(function(item, index) {
		  return [
			index + 1,
		    item.dscsnYmd,
		    item.bzentyNm,
		    formatBusinessNumber(item.brno),
		    item.dscsnMthdNm,
		    item.dscsnCntrNm,
		    item.cnslrNm,
		    item.rprsvNm,
		    item.brdt,
		    item.sexdstn === 'M' ? '남자' : (item.sexdstn === 'F' ? '여자' : ''),
		    item.picNm,
		    item.picDeptNm,
		    formatPhoneNumber(item.picTelno),
		    item.picEmlAddr,
		    item.fndnYmd,
		    item.empCnt,
		    item.bzentyTypeCdNm,
		    item.bizFldNm,
		    item.invtFldNm,
		    item.tpbizSeNm,
		    item.industSeNm,
		    item.bizCn,
		    item.slsAmt,
		    item.lctnAddr1,
		    item.lctnAddr2,
		    item.dscsnCn,
		    item.hmpgAddr
		  ];
		});	    
		
		// 데이터를 워크시트에 추가
		data.forEach(function(rowData) {
			var row = worksheet.addRow({
			    sn: rowData[0],
			    dscsnYmd: rowData[1],
			    bzentyNm: rowData[2],
			    brno: rowData[3],
			    dscsnMthdNm: rowData[4],
			    dscsnCntrNm: rowData[5],
			    cnslrNm: rowData[6],
			    rprsvNm: rowData[7],
			    brdt: rowData[8],
			    sexdstn: rowData[9],
			    picNm: rowData[10],
			    picDeptNm: rowData[11],
			    picTelno: rowData[12],
			    picEmlAddr: rowData[13],
			    fndnYmd: rowData[14],
			    empCnt: rowData[15],
			    bzentyTypeCdNm: rowData[16],
			    bizFldNm: rowData[17],
			    invtFldNm: rowData[18],
			    tpbizSeNm: rowData[19],
			    industSeNm: rowData[20],
			    bizCn: rowData[21],
			    slsAmt: rowData[22],
			    lctnAddr1: rowData[23],
			    lctnAddr2: rowData[24],
			    dscsnCn: rowData[25],
			    hmpgAddr: rowData[26]
			});
		});
		
		// 첫 번째 행에 헤더 스타일 적용
		worksheet.getRow(1).eachCell({ includeEmpty: true }, function(cell) {
		  cell.style = headerStyle;
		});
	
	    // 엑셀 파일로 저장
	    workbook.xlsx.writeBuffer().then(function(buffer) {
	        saveAs(new Blob([buffer]), '상담일지.xlsx');
	    });
    }

    //========================================================//
    // 콤보박스 정의
    //--------------------------------------------------------//

	$('#openDscsnCntrCd').appComboBox({
	    params: {upCdId: CODE.DSCSN_CNTR.code},
	    loadFilter: function(data) {
	    	data.unshift(COMBO.INIT_SELECT);
	        return data;
	    },
	});	
		
	$('#openDscsnMthdCd').appComboBox({
		params: {upCdId: CODE.DSCSN_MTHD.code},
		loadFilter: function(data) {
			data.unshift(COMBO.INIT_SELECT);
			return data;
		},
	});
	
    // 신청현황관리 검색처리
    //--------------------------------------------------------//
    function doSearch() {
		if (CONDITION.yearCheck($.formUtil.getValue(P_SFORM, 'srchBgngYmd'),
		                        $.formUtil.getValue(P_SFORM, 'srchEndYmd' ),
                                '상담일자 시작일과 종료일의 값이 올바르지 않습니다.')) {
			// 검색폼 데이터 객체화
	        var obj = P_SFORM.serializeObject();
	        // 그리드 목록조회 URL
	        P_GRID.appBbsGrid('search', obj);
	        return false;
		}
    }

    // 신청현황관리 검색리셋
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
    
    // 세부지원사업 등록하기
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
    // 엑셀다운로드 버튼 클릭시 이벤트 처리
    $('#btnExcel').bind('click', downloadExcel);
    
   /* $('#btnExcel').bind('click', exportExcel);*/
    // 검색어 입력 엔터 이벤트 처리
    bindEnter($('#srchText'), $('#btnSearch'));
});


//========================================================//
// 등록,수정,상세 모달팝업 정의
//  - 오픈된 모달창에서 사용하기 위해 외부에 정의함
//--------------------------------------------------------//
let P_MODAL = {
	// 모달팝업 객체
	modal: $('<div></div>'),
	// 상세조회 모달팝업 오픈
	doOpenSelect: function(row) {
		P_MODAL.modal.appModal({
			cls:        'w-ver1',
			title:      '상담일지 상세조회',
			url:        getUrl('/adm/invest/dscsn/modalDscsnForm.do'),
			params:     JSON.stringify({
				sn : row['sn'],
			})
		}).open();
	},
	// 신규등록 모달팝업 오픈
	doOpenRegist: function(row) {
		P_MODAL.modal.appModal({
			cls:        'w-ver1',
			title:      '상담일지 신규등록',
			url:        getUrl('/adm/invest/dscsn/modalDscsnForm.do'),
			params:     JSON.stringify({
				mode       : MODE.INSERT,
				sn : row['sn'],
			})
		}).open();
	},
	// 수정하기 모달팝업 오픈
	doOpenUpdate: function(row) {
		P_MODAL.modal.appModal({
			cls:        'w-ver1',
			title:      '상담일지 수정하기',
			url:        getUrl('/adm/invest/dscsn/modalDscsnForm.do'),
			params:     JSON.stringify({
				mode         : MODE.UPDATE,
				sn : row['sn'],
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