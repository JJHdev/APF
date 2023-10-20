/**
***************************************************************************************************
*** 파일명 : openSubmitFile.js
*** 설명글 : 운영관리-제출서류관리 화면 스크립트
***
*** -----------------------------    Modified Log   -----------------------------------------------
*** ver             date                author                  description
*** -----------------------------------------------------------------------------------------------
***  1.0     	 2023.06.26    				JH        			First Coding.
***  1.1     	 2023.06.27    				JH       			 작업 완료.
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
		idField:    'dcmntCd',
		// 목록 제목
		title:		'제출서류관리',
		// 검색 URL
		url:		getUrl('/adm/invest/submitfile/getListSubmitFile.do'),
        // 조회 파라미터 전달
		queryParams: { },
		//목록 검색 페이징 객체
		pagination: { display: 20 },
		// 페이지 표시 객체
		paging:     '#appGridPagination',
		// 검색결과 표시 객체
		result:     '#appGridResult',
		// 검색결과 메세지
		resultText: '<em class="ml-4px">{total}</em>개의 신청서 목록이 있습니다.',
		// 리스트옵션
		listOptions: {
			// 검색결과 없을경우 메세지
			emptyText:  '검색된 {title}가 없습니다.',
			// 목록칼럼 너비
			colgroup: ['130px','120px','130px','80px','80px','80px','100px'],
			// 목록칼럼 정의
			columns: [
				{name:'dcmntTaskSeNm'       ,cls:'app-l', label:'지원사업명'},    		// 농식품클라우드펀딩
				{name:'dtlSeNm'			    ,cls:'app-l', label:'프로그램명'},  		// 수수료
				{name:'dcmntNm'				,cls:'app-l', label:'서류명'   },    		// 사업자등록증
				{name:'aplySeNm'			,cls:'app-c', label:'신청구분'  },  		// 신청구분
				{name:'esntlYn'				,cls:'app-c', label:'필수여부'  },  		// 필수여부
				{name:'useYn'				,cls:'app-c', label:'항목표출'  },  		// 항목표출
				{name:'regYmd'				,cls:'app-c', label:'등록일자'  },  		// 등록일자
			],
			// 목록형인 경우 기본 스타일시트
			//gridCls: "table-box-1",
	        // 행선택시 상세조회
	        select: doSelect	
		},
    }).appBbsGrid('init');
    
    // 검색 실행
    doReset();

    //========================================================//
    // 콤보박스 정의
    //--------------------------------------------------------//

	// 지원신청구분
	$('#openDcmntTaskSeCd').appComboBox({
		url: getUrl('/com/common/getComboUpDcmntTask.do'),
		params: {upSrchDcmntCd: 'NONE'},
		loadFilter: function(data) {
			data.unshift(COMBO.INIT_ALL);
			return data;
		},
		change: function(data) {
			var value = data.target.value;
			if (value) {
				if(value == 'D01'){
					$('#openDtlSeCd').appComboBox({
						url: getUrl('/com/common/getComboDcmnt.do'),
						params: {sprtAplySeCd: CODE.SPRT_APLY_SE.AFTER},
						loadFilter: function(data2) {
							data2.unshift(COMBO.INIT_ALL);
							return data2;
						},
					});
				} else if(value == 'D02') {
					$('#openDtlSeCd').appComboBox({
						url: getUrl('/com/common/getComboDcmnt.do'),
						params: {sprtAplySeCd: CODE.SPRT_APLY_SE.CROWD},
						loadFilter: function(data2) {
							data2.unshift(COMBO.INIT_ALL);
							return data2;
						},
					});
				} else {
					$('#openDtlSeCd').appComboBox({
						url: getUrl('/com/common/getComboDcmnt.do'),
						params: {sprtAplySeCd: CODE.SPRT_APLY_SE.BEFORE},
						loadFilter: function(data2) {
							data2.unshift(COMBO.INIT_ALL);
							return data2;
						},
					});
				}
				
				if (value === CODE.PAPE_GROUP.AFTER) {
					// 프로그램분류
					$('#openAplySeCd').appComboBox({
						params: {upCdId: CODE.APLY_SE.code},
						loadFilter: function(data) {
							data.unshift(COMBO.INIT_ALL);
							var filteredData = data.filter(function(item) {return item.code !== '10';});
							return filteredData;
						}
					});
				} else {
					// 프로그램분류
					$('#openAplySeCd').appComboBox({
						params: {upCdId: CODE.APLY_SE.code},
						loadFilter: function(data) {
							data.unshift(COMBO.INIT_ALL);
							return data;
						}
					});
				}
			} else {
				$('#prgrsSttsCd').html('<option value="">전체</option>');
			}
		}
	});
	
    // 신청현황관리 검색처리
    //--------------------------------------------------------//
    function doSearch() {
        // 검색폼 데이터 객체화
        var obj = P_SFORM.serializeObject();

        // 그리드 목록조회 URL
        P_GRID.appBbsGrid('search', obj);

        return false;
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
			title:      '신청현황관리 상세조회',
			url:        getUrl('/adm/invest/submitfile/modalSubmitFileForm.do'),
			params:     JSON.stringify({
				dcmntTaskSeCd : row['dcmntTaskSeCd'],
				dcmntCd : row['dcmntCd'],
				aplySeCd : row['aplySeCd'],
				dtlSeCd : row['dtlSeCd'],
			})
		}).open();
	},
	// 신규등록 모달팝업 오픈
	doOpenRegist: function(row) {
		P_MODAL.modal.appModal({
			cls:        'w-ver1',
			title:      '신청현황관리 신규등록',
			url:        getUrl('/adm/invest/submitfile/modalSubmitFileForm.do'),
			params:     JSON.stringify({
				mode        : MODE.INSERT,
				dcmntCd     : row['dcmntCd'],
				aplySeCd 	: row['aplySeCd'],
			})
		}).open();
	},
	// 수정하기 모달팝업 오픈
	doOpenUpdate: function(row) {
		P_MODAL.modal.appModal({
			cls:        'w-ver1',
			title:      '신청현황관리 수정하기',
			url:        getUrl('/adm/invest/submitfile/modalSubmitFileForm.do'),
			params:     JSON.stringify({
				mode        	: MODE.UPDATE,
				dcmntTaskSeCd 	: row['dcmntTaskSeCd'],
				dcmntCd 		: row['dcmntCd'],
				aplySeCd 		: row['aplySeCd'],
				dtlSeCd 		: row['dtlSeCd'],
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