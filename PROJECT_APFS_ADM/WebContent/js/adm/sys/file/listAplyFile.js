/**
******************************************************************************************
*** 파일명 : listAplyFile.js
*** 설명글 : 신청파일관리 화면 스크립트
***
*** -----------------------------    Modified Log   --------------------------------------
*** ver             date                author                  description
*** --------------------------------------------------------------------------------------
*** 1.0         2021.10.02    김주호
******************************************************************************************
**/
$(function() {
	//========================================================//
	// 화면 스크립트 내에 사용할 객체,상수 정의
	//--------------------------------------------------------//
	let P_GRID    = $('#appGrid'        ); // 목록 GRID
	let P_FORM    = $('#searchForm'     ); // 검색폼
    let P_COMBO   = $('#appStepBox'     ); // 단계콤보

    //========================================================//
    // 목록 GRID 정의 (EasyUI datagrid)
    //--------------------------------------------------------//
    P_GRID.datagrid({
        //fit: true,
        fitColumns: true,
        // 그리드 결과데이터 타입
        contentType: 'application/json',
        // 그리드 결과데이터 타입
        dataType: 'json',
        // 그리드 데이터연동 방식
        method: 'POST',
        // 그리드 단일행만 선택여부
        singleSelect: false,
        // 그리드 페이징처리 여부
        pagination:true,
        // 그리드 행번호 표시여부
        rownumbers:true,
        // 한 페이지 출력수
        pageSize: 30,
        // 칼럼정의
        columns: [[
	        {field:'chckId'       ,checkbox: true},
	        {field:'sn'           ,width:100,halign:'center',align:'center',title:'일련번호', hidden:true},
	        {field:'dcmtNo'       ,width:110,halign:'center',align:'center',title:'접수번호'},
	        {field:'dtySeNm'      ,width:150,halign:'center',align:'center',title:'업무구분'},
	        {field:'upPapeNm'     ,width:150,halign:'center',align:'center',title:'서류분류'},
	        {field:'papeNm'       ,width:400,halign:'center',align:'left'  ,title:'세부분류'},
			{field:'fileBtn',width:250,halign:'center',align:'center',title:' ',
	            formatter: function(v, r) {
	                let pargs = [
	                    'href="javascript:void(0);"',
	                    'class="app-btn-preview detail"',
	                    'title="미리보기"',
	                    'data-sn="'+r['sn']+'"',
	                    'data-type="'+$.fileUtil.getPreviewType(r['fileNm'])+'"',
	                ].join(' ');
	
	                let dargs = [
	                    'href="javascript:void(0);"',
	                    'class="app-btn-download detail"',
	                    'title="다운로드"',
	                    'data-sn="'+r['sn']+'"'
	                ].join(' ');
	
	                return [
	                    '<a '+pargs+'>미리보기</a>',
	                    '<a '+dargs+'>다운로드</a>'
	                ].join('');
				}
			}
	
		]],
		onLoadSuccess: function() {
                let p = $(this).datagrid('getPanel');
                p.find('.app-btn-preview').each(function() {
                    $(this).bind('click', doGirdPreview);
                });
                p.find('.app-btn-download').each(function() {
                    $(this).bind('click', doGridDownload);
                });
        }
    });

    //========================================================//
    // 콤보박스 정의
    //--------------------------------------------------------//
    // 접수일자 조건 구성
    $('#appRcptTermBox').appTermBox({
        label:'접수일자',
        stName:'srchRcptStdt',
        enName:'srchRcptEndt'
    });
    // 단계콤보 구성
	P_COMBO = $('#appStepBox').appStepComboBox({
		items: [{
			id    : 'srchDtySeCd',  
			params: {upCdId: 'CT034'},
			url   : getUrl('/com/common/getComboCode.do')
		},{
			id    : 'srchUpPapeCd',  
			init  : COMBO.INIT_ALL,
			key   : 'dtySeCd',
			url   : getUrl('/com/common/getComboUpPape.do')
		},{
			id    : 'srchPapeCd',  
			init  : COMBO.INIT_ALL,
			key   : 'upPapeCd',
			url   : getUrl('/com/common/getComboPape.do'),
		}]
	});

    //========================================================//
    // 신청첨부파일 검색처리
    //--------------------------------------------------------//
    function doSearch() {
        // 검색폼 데이터 객체화
        var obj = P_FORM.serializeObject();
		// 선택된 항목 CLEAR
		P_GRID.datagrid('clearSelections');
        // 그리드 목록조회 URL
        P_GRID.datagrid('options')['url'] = getUrl('/adm/sys/file/getListAplyFile.do');
		// 그리드 검색조건 맵핑
		P_GRID.datagrid('options')['queryParams'] = obj;
        return false;
    }

    // 첨부서류 미리보기
    //--------------------------------------------------------//
    function doGirdPreview(){
        let url  = false;
        let sn   = $(this).data('sn');
        let type = $(this).data('type');
        if      (type == 'PDF') url = getUrl('/com/file/previewAplyHwp')+sn+".do";
        else if (type == 'HWP') url = getUrl('/com/file/previewAplyHwp')+sn+".do";
        else if (type == 'TXT') url = getUrl('/com/file/previewAplyFile')+".do?sn="+sn;
        else if (type == 'IMG') url = getUrl('/com/file/previewAplyFile')+".do?sn="+sn;

        // 파일미리보기 (comm_utils.js 참고)
        $.fileUtil.preview({type: type, url: url});

        return false;
    }

    // 첨부서류 다운로드
    //--------------------------------------------------------//
    function doGridDownload(){
        const downloadUrl = getUrl('/com/file/downloadAplyFile.do');
        // 파일다운로드 (comm_utils.js 참고)
        var snVal = $(this).data('sn');
        $.fileUtil.download({
            params: {sn: btoa(snVal)},
            url   : downloadUrl,
            log   : {
                params: {
                    atchFileSn: snVal,
                    progUrl   : getRealUrl(downloadUrl)
                }
            }
        });
        return false;
    }
    // 파일 다운로드 다중 선택
    function doGridMultiDownload(){

        let rows = P_GRID.datagrid('getSelections');
        if (rows.length == 0) {
            $.commMsg.alert('다운로드할 대상을 선택하세요.');
            return false;
        }

        // 관리자용 다운로드
        const downloadUrl = getUrl('/com/file/downloadAplyFileZip.do');
        // 배열로 전달
        var sns = [];
        $.each(rows, function(index, item){
            sns.push(item.sn.toString());
        });

        $.fileUtil.download({
            params: { sns : sns },
            url   : downloadUrl,
            log   : {
                params: {
                    atchFileSns: sns,
                    progUrl    : getRealUrl(downloadUrl)
                }
            }
        });
        return false;
    }

    // 신청첨부파일 검색리셋
    //--------------------------------------------------------//
    function doReset() {
        // 검색폼 입력데이터 초기화
        P_FORM.form('reset');

        //업무구분 change 이벤트 적용
        P_COMBO.init();

        // 검색폼 그리드 검색 처리
        doSearch();

        return false;
    }

    //========================================================//
    // FORM ELEMENT EVENT 정의
    //--------------------------------------------------------//
    // 검색버튼 클릭시 이벤트 처리
    $('#btnSearch').bind('click', doSearch);
    // 리셋버튼 클릭시 이벤트처리
    $('#btnReset' ).bind('click', doReset);
    // 다운로드 클리시 이벤트처리
    $("#btnDownload").bind('click', doGridMultiDownload);
    // 로딩시 초기화 및 검색실행
    doSearch();

});
