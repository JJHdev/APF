/**
 *******************************************************************************
 ***    명칭: main.js
 ***    설명: 사용자 메인 화면
 ***
 ***    -----------------------------    Modified Log   ------------------------
 ***    버전        수정일자        수정자        내용
 *** ---------------------------------------------------------------------------
 ***    1.0      2023.03.14    LSH        First Coding.
 *******************************************************************************
 **/
function time_limit() {
	$('.time-limit-box').addClass('trans');
	$('body').addClass('none-timelimit');
}

$(function() {
	
	time_limit();
	
	$('#fullpage').fullpage({
		//options here
		autoScrolling : true,
		scrollHorizontally : true,
		navigation : true,
		navigationPosition : 'right',
		'afterLoad' : function(anchorLink, index) {
			if (index == 1) {
				$("[class^='head-']").removeClass('menu-fixed');

			} else if (index == 2) {

			} else if (index == 3) {

			} else if (index == 4) {

			}
		},
		'onLeave' : function(index, nextIndex, direction) {
			if (index == 1 && direction == 'down') {
				// alert ('1번에서 2번으로');
				$("[class^='head-']").addClass('menu-fixed');
				$('#fp-nav').addClass('trans');
			} else if (index == 1 && direction == 'up') {

			}
			if (index == 2 && direction == 'down') {
				// alert ('2번에서 3번으로');
				$("[class^='head-']").addClass('menu-fixed');
				$('#fp-nav').addClass('trans');
			} else if (index == 2 && direction == 'up') {
				// alert ('2번에서 1번으로');

				$('#fp-nav').removeClass('trans');

			}
			if (index == 3 && direction == 'down') {
				// alert ('3번에서 4번으로');
				$("[class^='head-']").addClass('menu-fixed');
				$('#fp-nav').addClass('trans');
			} else if (index == 2 && direction == 'up') {
				// alert ('3번에서 2번으로');
				$("[class^='head-']").addClass('menu-fixed');
				$('#fp-nav').removeClass('trans');
			}
		}
	});
	
	// 우수투자사례 슬라이드 카드
	function getInvstCaseSlide() {
		$.ajaxUtil.ajaxLoad(
			getUrl('/usr/inform/bbs/getListInvestCase.do'),
			{ bbsSeCd : 'B40' },
			function(result) {
				var data = result.rows;
				if (data) {
					// 슬라이드정보
					$.bizUtils.drawMainInvestCaseSlide($('#appSlideCard'), data);
				} else {
					$('#appSlideCard').text('조회된 우수투자사례가 없습니다.');
					$('#swiper-appSlideCard').css('display', 'none');
				}
				
				
			}
		);
	}
	
	// 우수투자사례 카드 클릭 이벤트
    //--------------------------------------------------------//
	$(document).on('click','.card-box-main', function(){
		let pstNo = $(this).attr('value');
		let params = {
			pstNo	: pstNo,
			bbsSeCd : 'B40',
			mode 	: MODE.VIEW
		};
		
		$.formUtil.submitForm(getUrl('/usr/inform/bbs/viewInvestCase.do'), { 
			params : params
		});
	});

	// 공지사항 
	$('#appGrid').appBbsGrid({
		// 목록 KEY필드
		idField:    'pstNo',
		// 목록 제목
		title:      '공지사항',
		// 검색 URL
		url:         getUrl('/usr/inform/bbs/getListNotice.do'),
		//목록 검색 페이징 객체
		pagination: { display: 6},
		//목록 검색 조건
		params: 	{bbsSeCd : 'B10'},
		// 리스트옵션
		listOptions: {
			// 검색결과 없을경우 메세지
			emptyText:  '검색된 {title}이 없습니다.',
			// 목록칼럼 너비
			colgroup: ['*','160px'],
			// 목록칼럼 정의
			columns: [
			          {name:'pstTtl'       ,cls:'app-l',label:'제목'},
			          {name:'regDate'      ,cls:'app-c',label:'작성일'},
			],
			// 목록형인 경우 헤더 스타일시트
			headCls: "d-none",
			// 행선택시 상세조회
			select: doSelect
		},
		//최초로딩 여부
		autoload: true,
	}).appBbsGrid('init');

	// 게시판 상세조회
    //--------------------------------------------------------//
    function doSelect(row, rowidx) {
    	$.formUtil.submitForm( getUrl('/usr/inform/bbs/viewNotice.do'), {
			params: {
				pstNo     : row['pstNo'  ],
			}
		});
    	
    	return false;
    }

	// 배너 표출
	function getBanner() {
		$.ajaxUtil.ajaxLoad(
			getUrl('/usr/main/listBanner.do'), 
			{},
			function(result) {
				result.forEach(function(e) {
					let div = $('<div class="swiper-slide"></div>');
					div.append($('<div class="imgbox"></div>'));
					let a = $('<a href="#"></a>');
					if(e['bannerUrl'])
						a.attr("href", e['bannerUrl']);
					let img = $('<img src="'+getUrl('/usr/file/linkBbsFile.do?sn='+e['files'])+'" alt="배너">');
					div.find('.imgbox').append(a.append(img));
					$('.mySwiper4>.swiper-wrapper').append(div);
				})
			}
		);
	}
	
    getBanner();
    getInvstCaseSlide();

	
	//팝업 구현
	openLayerPopup();
	

	//Initialize Swiper - HOME 배너
	var swiper = new Swiper(".mySwiper4", {
		slidesPerView: "auto",
		pagination: {
			el: ".swiper.mySwiper4 .swiper-pagination",
			type : 'bullets',
			clickable: true
		},
		spaceBetween: 0,
		slidesPerView:1,
		autoplay: {
			delay: 2000,
			disableOnInteraction: false,
		},				
	});
	
	$("#srchText").bind('keydown', function(e) {
		if (e.keyCode != 13)
			return;
		openSearch();
	});
	$('#srchIcon').bind('click', openSearch);
	
	// 통합검색
	function openSearch() {
		$.formUtil.submitForm(ROOT_PATH +"/usr/inform/search/openSearch.do", {
			params : {
				srchText : $("#srchText").val(),
			}
		});
	}
});

// 공지사항 팝업
//--------------------------------------------------------//
function openLayerPopup(){

	// 팝업공지 전체 리스트 조회
	var strHtmlContents = $.ajaxUtil.ajaxHtml(getUrl('/usr/main/popupNotice.do'));
	strHtmlContents = strHtmlContents.substring(strHtmlContents.indexOf("<!-- Tiles BODY -->"));
	$("#popupDiv").append(strHtmlContents);

	let n = 1;
	
	//1.for:모든 레이어 팝업 조회
	$(".divpopLayer").each(function(i, item) {
		
		var popAr 	= item.dataset.popupAr;
		var popHg 	= item.dataset.popupHg;
		
		const cacheName = 'notToday_'+item.dataset.pstNo;
		
		// 2.if:캐시가 없는 레이어만 호출
		if($.getCookie(cacheName)!="Y" || $.getCookie(cacheName) == null) {
			$('#popup_box_'+item.dataset.pstNo).show();

			$(item).css("width", popAr);
			$(item).css("max-width","1600px");
			$(item).css("min-width","250px");
			//콘텐츠 높이에 맞게 팝업창 가변
			$(item).css("height",popHg);
			
			$(item).css('margin-left', '30px');
			$(item).css('inset', '160px auto auto ' +(250+(n*80)) +'px');
			n++;

		} else {
			$('#popup_box_'+item.dataset.pstNo).hide();
		}

	});
	
	$('.divpopLayer').draggable({handle:'.window_topslice'});
}

function closePopup(pstNo) {
	if ($("#ck_close_not_today_"+pstNo).is(":checked")) {
		$.setCookie('notToday_'+pstNo, 'Y', 1);
	} 
	$("#popup_box_"+pstNo).hide();
}


