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
	// 상단네비바 로딩
	loadTopBar(
		'#topnavbar', 
		'#topnavbar-layer',
		getUrl('/com/common/listMenu.do'), 
		{sysCd: SYSTEM.USER['code']
	});

	// 사이드바 로딩
	loadSideBar(
		'#sidebar', 
		getUrl('/com/common/listMenu.do'),
		{sysCd: SYSTEM.USER['code']}
	);
	// 상단경로 표시
	showBreadcrumbs('#page-breadcrumbs');
	
	// 2023.08.01 스크롤시 상단고정 스타일 적용
	var jbOffset = $("[class^='head-']").offset() && $("[class^='head-']").offset().top;
   	$(window).scroll(function() {
      if ($(document).scrollTop() > 0) {
         $(".sub [class^='head-']").addClass("menu-fixed");
      } else {
         $(".sub [class^='head-']").removeClass("menu-fixed");
      }
      if ($(document).scrollTop() > jbOffset + 100) {
         $('.floating-btn').addClass('trans');
      }else {
         $('.floating-btn').removeClass('trans');
      }
   });
   $('.floating-btn').on('click', function () {
      $('html,body').animate({ scrollTop: 0 }, 400);
   });
	
});

//=======================================================================//
//네비게이션바 메뉴 AJAX 로딩 함수 (사용자화면)
//-----------------------------------------------------------------------//
var loadTopBar = function(divKey, ulKey, url, params) {
	var dv = $(divKey);
	if (!dv)
		return;
		
	var ul = $(ulKey);

	var data  = $.ajaxUtil.ajaxDefault(url, params);
	if (data) {
		dv.append('<div class="row"></div>');
		$.each(data, function(i,o) {

			ul.append('<li class="left-menu-item lev1"><a href="javascript:void(0)">'+o['menuNm']+'</a></li>');
			dv.find(".row").append('<div class="col"><a href="javascript:void(0)">'+o['menuNm']+'</a></div>');
			
			// 2단계 드롭다운 표시
			var dr = null;
			var su = null;
			if (o.children) {
				dr = $('<ul></ul>');
				su = $('<ul class="left-menu-child lev2"></ul>');
				$.each(o.children, function(j,c) {
					if(getUrl(c['trgtUrl'])=='/usr/gis/gis/openGIS.do'){
						dr.append('<li><a href="'+getUrl(c['trgtUrl'])+'" target="_blank">'+c['menuNm']+'</a></li>');
						su.append('<li><a href="'+getUrl(c['trgtUrl'])+'" target="_blank">'+c['menuNm']+'</a></li>');
					}
					else{
						dr.append('<li><a href="'+getUrl(c['trgtUrl'])+'">'+c['menuNm']+'</a></li>');
						su.append('<li><a href="'+getUrl(c['trgtUrl'])+'">'+c['menuNm']+'</a></li>');
					}					
				});
				dv.find(".col:last").append(dr);
				ul.find("li.lev1:last").append(su);
			}
		});
	}
	
   	$("[class^='head-']>div>.row>div.suport>.row>div").mouseenter(function(){
      $("[class^='head-']").addClass('submenu');
      $(this).children('a').addClass('active');
   	});
   	$("[class^='head-']>div>.row>div.suport>.row>div").mouseleave(function(){
      $("[class^='head-']").removeClass('submenu'); 
      $(this).children('a').removeClass('active');    
	});
	$(".left-menu-list .left-menu-item>a").click(function(){
		$(this).parent().addClass("active").siblings().removeClass("active");
	});
	$(".left-menu-list .left-menu-child>li>a").click(function(){
		$(this).parent().addClass("active").siblings().removeClass("active");
	});
	$('#btn-menu-open').bind("click", function() {
		$('.m-menu-box').addClass('trans');
	});
	$('#btn-menu-close').bind("click", function() {
   		$('.m-menu-box').removeClass('trans');
	});
};

//=======================================================================//
//사이드바 메뉴 AJAX 로딩 함수
//-----------------------------------------------------------------------//
var loadSideBar = function(divKey, url, params) {
	
	var div = $(divKey);
	if (!div)
		return;

	var first = div.data('first');
	var menu  = div.data('menu');
	// 통합검색인 경우 조건처리
	if (first == MENU.SEARCH)
		$.extend(params, {useYn:'N'});

	var data  = $.ajaxUtil.ajaxDefault(url, params);
	if (data) {
		$.each(data, function(i,o) {
			var dr = null;
			if (o['id'] == first) {
				dr = $('<ul></ul>');
				$.each(o.children, function(j,c) {
					dr.append('<li><a href="'+getUrl(c['trgtUrl'])+'">'+c['menuNm']+'</a></li>');
					if (c['menuId'] == menu)
						dr.find("li:last > a").addClass("active");
				});
				div.append(dr);
			}
		});
	}
};

//=======================================================================//
//서브 메뉴 AJAX 로딩 함수
//-----------------------------------------------------------------------//
var loadSubBar = function(divKey, url) {
	
	var div = $(divKey);
	if (!div)
		return;

	var menu  = div.data('menu');
	var data = $.ajaxUtil.ajaxDefault(url, {});
	if (data) {
		var dr = $('<ul></ul>');
		$.each(data, function(i,c) {
			dr.append('<li><a href="'+getUrl(c['trgtUrl'])+'">'+c['menuNm']+'</a></li>');
			if (c['menuId'] == menu)			
				dr.find("li:last > a").addClass("active");
		});
		div.append(dr);
	}
};

//=======================================================================//
//통합검색 서브 메뉴 AJAX 로딩 함수
//-----------------------------------------------------------------------//
var loadSearchSubBar = function(divKey, url) {
	
	var div = $(divKey);
	if (!div)
		return;

	var menu  = div.data('menu');
	var data = $.ajaxUtil.ajaxDefault(url, {});
	if (data) {
		var dr = $('<ul></ul>');
		$.each(data, function(i,c) {
			// TODO 클릭하면 id: srchTextHeader, name:srchText 의 값이 같이 넘어갈 수 있도록 해야함
			var drc = $('<li><a href="#">' + c['menuNm'] + '</a></li>');
			
			drc.click(function() {
				var url = getUrl(c['trgtUrl']);
				
				var srchText = $('#srchTextHeader').val();
				if (srchText) {
					url += '?srchText=' + srchText;
				}
				
				window.location = url;
			});
			
			dr.append(drc);
			//dr.append('<li><a href="'+getUrl(c['trgtUrl'])+'">'+c['menuNm']+'</a></li>');
			if (c['menuId'] == menu)			
				dr.find("li:last > a").addClass("active");
			
		});
		
		div.append(dr);
	}
};

//=======================================================================//
//상단 우측 페이지 경로 표시
//-----------------------------------------------------------------------//
var showBreadcrumbs = function(divKey) {
	
	let div = $(divKey);
	if (!div)
		return;

	let home = '<a href="'+getUrl(CURL.MAIN)+'"><i class="icon-home-F"></i> Home</a>';
		
	div.append('<div class="row"></div>');
	
	div.find(".row").append('<div class="col home">'+home+'</div>');

	let path = div.data('path');
	let purl = div.data('url' );
	if (path == null)
		return;
		
	let purls = purl.split(',');

	$.each(path.split(','), function(i,m) {
		div.find(".row").append('<div class="col"><i class="icon-angle-right"></i> </div>');
		div.find(".row").append('<div class="col"><a href="'+purls[i]+'">'+m+'</a></div>');
	});
	div.find(".row > .col:last").addClass("now");
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
