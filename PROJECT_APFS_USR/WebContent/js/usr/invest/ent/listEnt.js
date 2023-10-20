/**
*******************************************************************************
***    명칭: listEnt.js
***    설명: 투자서비스 - 경영체검색 - 스마트검색 화면
***
***    -----------------------------    Modified Log   ------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.04.28    LSH        First Coding.
*******************************************************************************
**/
$(function() {
	
	// 스마트검색 폼
	let P_FORM = $('#searchForm');
	// 경영체 그리드 (app_gridutils.js)
	let P_GRID = $.gridUtils.appEntGrid({
		params: {srchMode: 'S'}
	});

	// 스마트검색 컨트롤 (app_smartsearch.js)
	$('#appSmartSearch').appSmartSearch({
		// 모드: 검색
		mode: MODE.SEARCH,
		// 검색폼 객체
		form: P_FORM,
		// 그리드 객체
		grid: P_GRID,
		// 검색조건 항목
		items: [[{
			label: '투자분야',
			name:  'invtFldList',
			type:  'appSelectBox', 
			info: {
				click: function() {
					// 투자분야선택 팝업오픈(app_popup.js)
					popup.openInvestField({
						// 검색버튼 클릭
						onClickSearch: function( values ) {
							$.formUtil.setValue(P_FORM, "invtFldList", values);
							P_GRID.doSearch();
						}
					});
					
				}
			}, 
			inputOptions: {
				form:   'checkbox', 
				name:   'invtFldList',
				colCls: 'col',
				url:    getUrl('/com/common/getInvtRlmComboCode.do'),
				// 첫번쨰값 선택처리
				selectIndex: '0',
				init:  {code:'', text:'전체', click: bindCheckAll}
			}
		}],[{
			label: '사업분야',
			name:  'bizFldList',
			type:  'appSelectBox', 
			info: {
				click: function() {
					// 사업분야 팝업오픈(app_popup.js)
					popup.openBizField({
						// 검색버튼 클릭
						onClickSearch: function( values ) {
							$.formUtil.setValue(P_FORM, "bizFldList", values);
							P_GRID.doSearch();
						}
					});
				}
			}, 
			inputOptions: {
				form:   'checkbox', 
				name:   'bizFldList',
				colCls: 'col',
				// 첫번쨰값 선택처리
				selectIndex: '0',
				url:    getUrl('/com/common/getBizRlmComboCode.do'),
				params: {upCdId: CODE.BIZ_RLM.code},
				init:  {code:'', text:'전체', click: bindCheckAll}
			}
		}],[{
			label: '투자희망금액', 
			name:  'invtHopeCd',
			type:  'appSlideBox', 
			inputOptions: {
				input:  {id: 'invtHopeCd', name: 'invtHopeCd', value: ''},
				params: {upCdId: CODE.FUND_SIZE.code}
			}
		},{
			label: '정부지원사업 이력', 
			name:  'entSptHstYn', 
			type:  'appSelectBox', 
			inputOptions: {
				elmCls: 'custum-row-1',
				type:   'static',
				name:   'entSptHstYn', 
				rows:[
					{code:'' , text:'전체(미보유포함)'},
					{code:'Y', text:'보유기업'}
				]
			}
		}],[{
			label: '지역 (시도)', 
			name:  'lctnCd', 
			type:  'appComboBox', 
			inputOptions: {
				name:   'lctnCd',
				init:   COMBO.INIT_ALL,
				params: {upCdId: CODE.CTPV_SE.code}
			}
		},{
			label: '', 
		}]],
		callbacks: {
			onBeforeReset: function() {
				$('#srchText').val('');
			},
			onLoad: function(box, elm, cmp) {
				setTimeout(function() {
					elm.find('.smart_search_toggle').trigger('click');
				}, 1000);
			}
		}
	});
	
	// 상단 검색박스 표시 (app_bizutils.js 참고)
	$.bizUtils.showHeaderBox({
		type : 'searchbox',
		id   : 'srchText',
		name : 'srchText',
		placeholder: '경영체명, 분야명, 대표자명을 검색해 보세요.',
		callback: function(v) {
			P_GRID.doSearch({srchText: v});
		}
	});
	
});

