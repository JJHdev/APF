/**
******************************************************************************************
*** 파일명    : comm_layout.js
*** 설명      : 시스템 공통레이아웃 스크립트
***             loadTopBar()
***             loadSideBar()
***             showBreadcrumbs()
***             createTabMenu()
***
*** -----------------------------    Modified Log   --------------------------------------
*** ver             date                author                  description
*** --------------------------------------------------------------------------------------
*** 1.0         2023.04.03              LSH
******************************************************************************************
**/
$(document).ready(function() {
	
	// 사이드바 로딩
	loadSideBar(
		'#sidebar', 
		getUrl('/com/common/listMenu.do'),
		{sysCd: SYSTEM.ADMIN['code']}
	);

	// 상단경로 표시
	showBreadcrumbs('#page-breadcrumbs');
	
	// 메뉴영역 폴딩 (commons.js 의 menu_collapse)
	$('.menu-collapse').click(function() {
	 	$('.wrap>.row>div.left').toggleClass('trans');
	 	$('.trans .lev-menu-box .lev1>li').mouseover(function(){
			$(this).addClass('hover').siblings().removeClass('hover');
		});
		$('.trans .lev-menu-box .lev2').mouseout(function(){
			$('.trans .lev-menu-box .lev1>li').removeClass('hover');
		});
	});
	
	// 검색영역 폴딩 (commons.js 의 close_btn)
	$('#btn_search_close').click(function() {
		$('.content-wrap>div.left-search').toggleClass('trans');
	    $('.btnFc-1').toggleClass('active');
	    if ($('.btnFc-1').hasClass('active')){
			$('.btnFc-1').html('<i class="icon-search mr-3px"></i>검색조건 열기');
	    }else{
			$('.btnFc-1').html('<i class="icon-search-minus mr-3px"></i>검색조건 닫기');
	    }
	});
	// 상세영역 닫기 (commons.js 의 detail_pop)
	$('#btn_detail_close').click(function() {
		$('.detail-pop').removeClass('trans');
		$('.detail-pop-2').removeClass('trans');
		$('.content-wrap>div.content').removeClass('detail-show');
	});
	
	// 로그아웃 클릭
	$('#btn_logout').click(function() {
		goUrl( getUrl('/com/common/logout.do') );
		return false;
	});
	
	// 달력아이콘 클릭
	$('.icon-calendar').click(function() {
		$(this).siblings('.datepicker-input').datepicker('show');
	});
	// 검색조건 버튼 Toggle
	$('.btnFc-1').click(function(){
		$(this).toggleClass('active');
		if ($(this).hasClass('active')){
			$(this).html('<i class="icon-search mr-3px"></i>검색조건 열기');
		}else{
			$(this).html('<i class="icon-search-minus mr-3px"></i>검색조건 닫기');
		}
		$('.content-wrap>div.left-search').toggleClass('trans');
	});
});

// 상세영역 열기 (commons.js 의 detail_pop)
const showDetail = function() {
	$('.detail-pop').addClass('trans');
	$('.content-wrap>div.content').addClass('detail-show');
}

//=======================================================================//
//사이드바 메뉴 AJAX 로딩 함수
//-----------------------------------------------------------------------//
const loadSideBar = function(divKey, url, params) {
	
	var div = $(divKey);
	if (!div)
		return;
	
	var first = div.data('first');
	var cmenu = div.data('menu');
	
	var data  = $.ajaxUtil.ajaxDefault(url, params);
	if (data) {
		tree = buildTree(data, 0);
		div.append(tree);
	}
	
	div.find('ul > li > a').each(function() {
		$(this).click(function() {
			$(this).parent().addClass("active").siblings().removeClass("active");
		});
	});
	
	// 현재메뉴 활성화처리
	div.find('ul.lev1 > li').each(function() {
		let menu = $(this).data('menu');
		if (menu == first) {
			$(this).addClass('active');
			$(this).find('a').removeClass('HoverIcon');
			$(this).find('a > i').addClass('F');
			$(this).find('a').each(function() {
				if ($(this).data('menu') == cmenu) {
					$(this).addClass('active');
				}
			});
		}
	});
	
	div.find('a.HoverIcon').hover(function(){
		$(this).addClass('F');
		$(this).find("[class^='icon-']").addClass('F');
   	}, function(){
		$(this).removeClass('F');
		$(this).find("[class^='icon-']").removeClass('F');
	});
};

var buildTree = function(list, level) {
	var ul = $('<ul></ul>');
	if (level == 0)
		ul.addClass("lev1");
	else
		ul.addClass("lev2");
							
	$.each(list, function(i,o) {
		var ac = $('<a></a>');
		ac.data('menu', o['menuId']);
		
		var li = $('<li></li>');
		li.data('menu', o['menuId']);
		if (o['level'] == 1) {
			ac.prop('href', 'javascript:void(0);');
			ac.addClass('HoverIcon');
			ac.append('<i class="'+MENU_ICONS[o['menuId']]+'"></i>');
			ac.append('<span class="txt">'+o['menuNm']+'</span>');
			ac.append('<span class="bg"></span>');
		}
		else {
			ac.append(o['menuNm']);
		}
		// 드롭다운 표시
		if (o.children) {
			ac.append('<i class="arrow icon-angle-right-small"></i>');
			ac.append('<span class="bg"></span>');
			li.append(ac);
			li.append(buildTree(o.children, o['level']));
		}
		else {
			ac.prop('href', getUrl(o['trgtUrl']));
			li.append(ac);
		}
		ul.append(li);
	});
	return ul;
}


//=======================================================================//
//상단 우측 페이지 경로 표시
//-----------------------------------------------------------------------//
var showBreadcrumbs = function(divKey) {
	
	let div = $(divKey);
	if (!div)
		return;

	let home = '<a href="'+getUrl('/adm/main/main.do')+'"><i class="icon-home F"></i>HOME</a>';
	div.append('<li class="icon">'+home+'</li>');

	let path = div.data('path');
	let purl = div.data('url' );
	if (path == null)
		return;

	let purls = purl.split(',');

	$.each(path.split(','), function(i,m) {
		div.append('<li class="arrow"><i class="icon-angle-right"></i></li>');
		div.append('<li><a href="'+purls[i]+'">'+m+'</a></li>');
	});
	div.find("li:last").addClass("now");
};

//=======================================================================//
// 탭형 서브메뉴 표시
//-----------------------------------------------------------------------//
var createTabMenu = function(tabElm, tabList) {

	if (!tabElm)
		return;
	let curl = $(location).attr("pathname");
	tabElm.append('<div class="tabWrap type1"></div>');
	tabElm.append('<div class="app-space10"></div>');
	tabElm.find('.tabWrap').append('<ul class="li-2 box"></ul>');
	
	$.each(tabList, function(i,t) {
		let li = $('<li><a></a></li>');
		li.find('a').append(t.title);
		li.find('a').prop('href', t.url);
		if (t.url == curl)
			li.addClass("on");
		tabElm.find('ul').append(li);
	});
};
