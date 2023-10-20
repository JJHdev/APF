/**
*******************************************************************************
***    명칭: openPlatform.js
***    설명: 플랫폼소개 - 플랫폼 소개 화면
***
***    -----------------------------    Modified Log   ------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.07.14      J H        작업완료.
***    1.1      2023.08.17      LHB        BI 퍼블리싱 반영.
*******************************************************************************
**/
$(function() {
	$('.div-tab').on('click', function() {
		const tab = $(this);
		const idx = tab.index();
		
		$('.div-tab').removeClass('active');
		$(this).addClass('active');
		
		$('.contentDiv').css('display', 'none');
		$('.contentDiv').eq(idx).css('display', 'block');
	});
	
	// BI 파일 다운로드 버튼 이벤트
    $('#btnAi' ).bind('click', function() { $.bizUtils.downloadBiAi (); });
	$('#btnPng').bind('click', function() { $.bizUtils.downloadBiPng(); });
	$('#btnJpg').bind('click', function() { $.bizUtils.downloadBiJpg(); });
    
});