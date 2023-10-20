/**
******************************************************************************************
*** 파일명 : modalSprtUld.js
*** 설명글 : 운영관리-경영체 데이터 업로드의 파일업로드 화면 스크립트
***
*** -----------------------------    Modified Log   --------------------------------------
*** ver             date                author                  description
*** --------------------------------------------------------------------------------------
*** 1.0         2023.07.13               LHB                      신규 작성
******************************************************************************************
**/
$(function() {

    //========================================================//
    // 화면 스크립트 내에 사용할 객체,상수 정의
    //--------------------------------------------------------//
	var P_RFORM		= $('#registForm');
	var P_FILE_BOX	= $('#attachFile');			// 첨부파일
	
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
			input: 'xlsx 파일만 업로드 가능'
		},
		initData: {
			fileType: CODE.FILE_TYPE.BIZ,
			needYn	: 'Y',
		},
		extensions: ['xls', 'xlsx'],
		label: '엑셀파일',
	}).appBizFile('init');

    // 업로드 현황 등록하기
    //--------------------------------------------------------//
    function doSave() {
		// 첨부파일 VALIDATION (comm_component.js)
		var fvalidate = P_FILE_BOX.appBizFile('validate', {
			isAlert: true,
			isExt:   true,
			isLimit: true
		});
		if(!fvalidate) {
			return false;
		}
		
		$.commMsg.confirm("업로드하시겠습니까?", function() {
            // 등록폼을 AJAX로 저장처리
            P_RFORM.ajaxForm({
                url: getUrl('/adm/support/biz/saveSprtUld.do'),
                // 오류시 처리로직
                error: $.ajaxUtil.error,
                // 저장후 처리로직
                success: function(ret) {
					console.log(ret);
					if(ret.Code < 0) { //업로드 실패 - 매핑오류
						P_MODAL2.close();
						openModal(ret.Rows);
					} else {
						$.commMsg.success(ret, '성공적으로 업로드되었습니다.', function() {
							P_MODAL.doSearch();
							P_MODAL2.close();
						});
					}
                }
            }).submit();
        });
        return false;
    }

	function openModal(params) {
		P_MODAL3 = $('<div></div>').appPopupSprtUldResult(params, function() {
			return false;
		});
	}

	// 저장 버튼 클릭시 이벤트처리
	$('#btnSave').bind('click', doSave);
});

//[팝업] 업로드실패 팝업
//=============================================================================
$.fn.appPopupSprtUldResult = function( params, saveCallback ) {
	
	let options = {
		title     		: '업로드 실패',
		icon      		: '<i class="icon-octagon-minus"></i>',
		loadUrl			: getUrl('/adm/support/biz/modalSprtUldResult.do'),
	};
	
	return $(this).appPopup({
		url:        	options.loadUrl,
		params:     	JSON.stringify({}),
		title:      	options.title,
		icon:       	options.icon,
		style:			'width: 600px; max-height: 800px;',
		type:      	 	'pageload',
		dialogCls:  	'w-ver1',
		appendBody: 	'body',
		onloaded:   function(pop) {
			$('#dataNum').text(params.length);
			$('#appGridFail').appBbsGrid({
				//제목 (결과메세지에 사용됨)
				title: '매핑 실패 목록',
			    //목록 데이터 (있을 경우 AJAX 로딩안함)
				dataRows: params,
				autoload: true,
				// 리스트옵션
				listOptions: {
					// 검색결과 없을경우 메세지
					emptyText:  '검색된 {title}이 없습니다.',
					// 목록칼럼 너비
					colgroup: ['70px','*',],
					// 목록칼럼 정의
					columns: [
						{name:'rowNo'			, cls:'app-c', label:'행번호',},
			            {name:'brno'			, cls:'app-c', label:'사업자번호'},
					],
					// 목록형인 경우 기본 스타일시트
					gridCls: "table-box-1",
				},
			}).appBbsGrid('init');
		}
	}).open();
};