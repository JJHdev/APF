/**
*******************************************************************************
***    명칭: viewEnt.js
***    설명: 투자서비스 - 경영체검색 - 경영체 상세보기
***
***    -----------------------------    Modified Log   ------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.05.04    LSH        First Coding.
*******************************************************************************
**/

$(function() {
	
    //========================================================//
    // 화면 스크립트 내에 사용할 객체,상수 정의
    //--------------------------------------------------------//
	let P_ENT_NO = $('#bzentyNo').val(); // 업체번호

    //========================================================//
    // 탭 정의
    //--------------------------------------------------------//
	$('#appTab').appTabs({
		value: 'DASH',
		items: [
			{tab: '#appDashTab', label:'데이터 대시보드', value:'DASH', icon:'icon-grid-square'},
			{tab: '#appInfoTab', label:'사업계획서 및 홍보자료', value:'INFO', icon:'icon-list'}
		]
	});

    // 경영체정보 조회하기
    //--------------------------------------------------------//
    function doLoad() {

		$.ajaxUtil.ajaxLoad(
			// 조회 URL
            getUrl('/usr/invest/ent/getEntAll.do'),
			// 조회 조건
			{mode: MODE.VIEW, bzentyNo: P_ENT_NO},
			// 조회 콜백
            function(result) {
	
                var data = result.Data;

                if (data) {
					// 슬라이드정보
					$.bizUtils.drawEntSlide($('#appSlideCard'), data);
					// 설명글
					$('#appDescript').appEntDescript();
					
					let cards = {};
					$.each(ENTCARD, function(key, obj) {
						if (key == 'FNMN' && data['fnnrManualYn'] != 'Y')
							return true;
						
						cards[key] = $('#'+obj.id)[obj.widget]( $.extend({
							mode    : MODE.VIEW,
							data    : data,
							params  : {
								bzentyNo : data['bzentyNo'],
								irNo     : data['irNo']
							}
						}, obj.params) );
					});
					$.each(ENTCARD, function(key, obj) {
						if (obj.chart && cards[key]) {
							cards[key].createChart();
						}
					});
					// 사업계획서
					$('#appEntPlanFile').appEntPlanFile( data );
					// 첨부자료
					$('#appEntAddFile').appEntAddFile( data );
					// 홍보영상
					$('#appEntPromotion').appEntPromotion( data );
					
				    // 목록으로 클릭시 이벤트 처리
					$('.btnList').removeClass('app-off');
				    $('.btnList').bind('click', doList);
                }
            }
        );
        return false;
    }

    // 목록으로 돌아가기
    //--------------------------------------------------------//
    function doList() {
		goUrl( getUrl('/usr/invest/ent/listEnt.do') );
		return false;
    }
    // 조회 실행
    doLoad();
});

