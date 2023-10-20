<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
##========================================================================================
## 화면 상단 HTML 공통 영역
##
##========================================================================================
 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="app" uri="/WEB-INF/tld/app.tld"%>
<%@ taglib prefix="f" uri="/WEB-INF/tld/f.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator"%>
<%@ taglib prefix="ckeditor" uri="http://ckeditor.com"%>

<%@ page import="common.user.UserInfo" %>
<%@ page import="common.util.UserUtils"%>
<%
// UserInfo 세션
UserInfo userInfo = UserUtils.getUserInfo(request);
String cookLatestTime = UserUtils.getCookieLatestTime();
String cookExpireTime = UserUtils.getCookieExpireTime();
%>
<!-- 2020.10.04 세션시간관리[ntarget] -->
<script type="text/javascript" >
    var isLogin           = <%=userInfo.isLogin()%>;
    var COOK_LATEST_TIME  = '<%=cookLatestTime%>';
    var COOK_EXPIRE_TIME  = '<%=cookExpireTime%>';
    
    <%-- 230629 LHB 헤더의 통합검색 스크립트 임시로 JSP에 처리함 --%>
	$(function() {
		$("#srchText-h").bind('keydown', function(e) {
			if (e.keyCode != 13)
				return;
			// 버튼의 클릭이벤트를 발생시킨다.
			$.formUtil.submitForm(ROOT_PATH +"/usr/inform/search/openSearch.do", {
				params : {
					srchText : $("#srchText-h").val(),
				}
			});
		});
	});
</script>
<script type="text/javascript" src="<c:url value='/js/com/comm_session.js?version=${ver}'/>"></script>

<c:set var="userInfo" value="<%=userInfo%>" />
<c:set var="pagePath" value="${requestScope['javax.servlet.forward.servlet_path']}" />
<!-- TOP ARROW -->
<div class="floating-btn">
	<i class="icon-arrow-up"></i>
</div>

<!--head-->
<div class="head-1">
 
	<%-- MAIN인 경우 --%>
	<%-- <c:if test="${pagePath.startsWith('/usr/main/main')}">
		<div class="time-limit-box d-none d-xl-block">
			<div class="row">
				<div class="col">
					<i class="icon-bell-on"></i><span class="txt1">농림수산식품 경영체 대상 정부지원사업을 확인하고 신청해 보세요.</span>
					<button type="button">자세히 보기 <i class="icon-arrow-right"></i></button>
				</div>
				<div class="col justify-content-end">
					<span class="txt2">3일간 보지않기</span>
					<button type="button" class="icon-times close" onclick="time_limit();"></button>
				</div>
			</div>
		</div>
	</c:if> --%>

	<div class="datum-point">
		<div class="row">
			<div class="col menu-btn">
				<a href="javascript:void(0)" class="" id="btn-menu-open"><i class="icon-menu"></i><span class="hiddenATag">숨김</span></a>
			</div>
			<div class="col logo-box">
				<div class="logo-img">
					<a class="d-block h-100" href="<c:url value="/usr/main/main.do"/>">
						<img src="<c:url value='/images/logo/property1-white.svg'/>" class="chage1" alt="ASSIST"/>
						<img src="<c:url value='/images/logo/property1-blue2.svg'/>" class="chage2" alt="ASSIST"/>
					</a>							
				</div>
			</div>						
			<div class="col d-none d-xl-inline-flex search-box">
				<div class="form-area-box1">
					<div class="ele-icon-box">
						<i class="icon-search"></i>
						<input type="text" name="srchText" id="srchText-h" placeholder="검색해주세요" title="srch">
					</div>
				</div>
				<div class="search-word-box2">
					<p class="tit">인기검색어</p>
				</div>
				<div class="search-word-box overflow-hidden">
					<div class="swiper search-word">
						<div class="swiper-wrapper"></div>
						
					</div>
					<!-- Initialize Swiper (인기검색어 스크립트 추가) -->
					<script>
						$(function() {
							$.ajaxUtil.ajaxLoad(
					                getUrl("/usr/inform/search/getListSrchWrd.do"), {}, 
					                function(ret) {
					                    $.ajaxUtil.success(ret, function() {
					                        ret.forEach(function(e, i) {
						                        var swiperSlide = $('<div class="swiper-slide"></div>');
						                        swiperSlide.append($('<a href="javascript:void(0);" onclick="doSearchAll(this);">'+ e['srchwrd'] +'</a>'))
						                        $('.swiper.search-word>.swiper-wrapper').append(swiperSlide);
					                        })
					                    });
					                }
					            );
							var swiper = new Swiper(".swiper.search-word", {							
								direction: "vertical",
								slidesPerView:1,
								autoplay: {
									delay: 2500,
									disableOnInteraction: false,
								},
							});
						});
						function doSearchAll(e) {
							$.formUtil.submitForm(getUrl("/usr/inform/search/openSearch.do"), {
								params : {
									srchText : e.innerHTML,
								}
							}); 
						}
						
					</script>
				</div>
			</div>
			<%-- 상단네비바 loadTopBar (comm_layout.js) --%>
			<div class="col d-none d-xl-inline-flex suport" id="topnavbar"></div>
			
			<%-- 로그인후 --%>
			<c:if test="${userInfo.login}">
				<div class="col d-none d-xl-inline-flex suport2-login">
					<div class="row">
						<div class="col">
							<a href="<c:url value='/com/common/logout.do'/>"><i class="icon-log-out"></i>로그아웃</a>
						</div>
						<c:if test="${userInfo.roleId != 'ROLE_USR_SYS'}"><%-- 관리자가 아닌 경우 --%>
							<div class="col">
								<a href="<c:url value="/usr/mypage/matching/openMatching.do"/>"><i class="icon-user"></i><c:out value="${userInfo.userNm}"/>님</a>
							</div>
						</c:if>
					</div>
				</div>
			</c:if>
			<%-- 로그인전 --%>
			<c:if test="${!userInfo.login}">
				<div class="col d-none d-xl-inline-flex suport2">
					<div class="row">
						<div class="col">
							<a href="<c:url value='/com/common/login.do'/>" class=""><i class="icon-log-in"></i>로그인</a>
						</div>
						<div class="col">
							<a href="<c:url value='/com/user/openJoin.do'/>" class=""><i class="icon-user-plus"></i>회원가입</a>
						</div>
					</div>
				</div>
			</c:if>
		</div>
	</div>
	<img src="<c:url value='/images/logo/hovermeunimg1.png'/>" class="img1 changImage" alt="ASSIST"/>
</div>
<div class="m-menu-box">
	<div class="head-m-menu">
		<div class="row">
			<div class="col home-btn">
				<a href="javascript:void(0)" class="" id="btn-menu-close"><i class="icon-times"></i><span class="hiddenATag">숨김</span></a>
			</div>
			<div class="col logo">
				<a href="<c:url value="/usr/main/main.do"/>" class="">
					<img src="<c:url value='/images/logo/property1-white.svg'/>" alt="ASSIST">
				</a>
			</div>
			 <div class="col close-btn">
			</div> 
		</div>
	</div>
	<div class="top">				
		<div class="left-menu-box">
			<%-- 상단네비바 loadTopBar (comm_layout.js) --%>
			<ul class="left-menu-list" id="topnavbar-layer"></ul>
		</div>
	</div>
	
</div>

<!--//head-->
			