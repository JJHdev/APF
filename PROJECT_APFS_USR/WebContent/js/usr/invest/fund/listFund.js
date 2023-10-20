/**
*******************************************************************************
***    명칭: listFund.js
***    설명: 투자서비스 - 투자자검색 - 스마트검색 화면
***
***    -----------------------------    Modified Log   ------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.04.19    LSH        First Coding.
*******************************************************************************
**/
$(function() {
	
	// 스마트검색 폼
	let P_FORM = $('#searchForm');
	// 모태펀드 그리드 (app_gridutils.js)
	let P_GRID = $.gridUtils.appFundGrid({mode: MODE.SEARCH});

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
				html: '<a class="icon-info-circle ml-5px text-primary"></a>',
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
				// 첫번쨰값 선택처리
				selectIndex: '0',
				url:    getUrl('/com/common/getInvtRlmComboCode.do'),
				init:  {code:'', text:'전체', click: bindCheckAll},
				tooltipCls: 'app-pre'
			}
		}],[{
			label: '모집여부', 
			name:  'prgrsSttsCd', 
			type:  'appComboBox', 
			inputOptions: {
				init:   COMBO.INIT_ALL,
				type:   'static',
				rows:   STORE.INVT_YN
			}
		},{
			label: '펀드규모', 
			name:  'fundOperScale',
			type:  'appSlideBox', 
			inputOptions: {
				input:  {id: 'fundScaleCd', name: 'fundScaleCd', value: ''},
				params: {upCdId: CODE.FUND_SIZE.code}
			}
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
		placeholder: '펀드명, 투자자명을 검색해 보세요.',
		callback: function(v) {
			P_GRID.doSearch({srchText: v});
		}
	});
});

