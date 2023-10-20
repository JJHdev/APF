<%--
*******************************************************************************
***    명칭: openMatchingInvt.jsp
***    설명: 투자서비스-매칭서비스-매칭설정 화면
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.04.29    LSH        First Coding.
*******************************************************************************
--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ taglib prefix="tiles"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="app"    uri="/WEB-INF/tld/app.tld" %>
<%@ taglib prefix="f"      uri="/WEB-INF/tld/f.tld" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec"    uri="http://www.springframework.org/security/tags" %>

<%-- ############################ 내용 (시작) ############################ --%>

<%-- 매칭서비스 관련 공통 스크립트 --%>
<script type="text/javascript" src="<c:url value='/js/com/app_matching.js'/>"></script>

<form id="matchForm" name="matchForm" method="post" onsubmit="return false;">
	<input type="hidden" id="showMode" name="showMode" value="<c:out value="${showMode}"/>"/>
	<input type="hidden" id="srchMode" name="srchMode" value="<c:out value="${srchMode}"/>"/>
	<input type="hidden" id="mtchMode" name="mtchMode" value="<c:out value="${mtchMode}"/>"/>
</form>

<%-- 비로그인시 -------------------------------------------------------------%>
<c:if test="${showMode == 'X'}">
	<div class="userlogin-1">
		<div class="app-imgarea img1"><img src="<c:url value='/images/sub/matching.png'/>"/></div>
		<p class="txt1"><em>비회원님</em> 반갑습니다!</p>
		<p class="txt2">로그인을 하시면<br/>경영체 투자자 맞춤정보를 매칭해 드려요!</p>
	</div>
	<div class="bottom-box-btn py-32px">
		<div class="row">
			<div class="col">
				<button type="button" id="btnJoin" class="btn-combi-3 w-100 bs-xl"><i class="icon-user-plus"></i>회원가입하기</button>
			</div>
			<div class="col">
				<button type="button" id="btnLogin" class="btn-primary w-100 bs-xl"><i class="icon-log-in"></i>로그인</button>
			</div>
		</div>
	</div>
</c:if>
<%-- 매칭미설정시 -----------------------------------------------------------%>
<c:if test="${showMode == 'N'}">
	<div class="userlogin-1">
		<div class="app-imgarea img1"><img src="<c:url value='/images/sub/matching.png'/>"/></div>
		<p class="txt2">매칭받고 싶은 정보를 설정하시면<br/>맞춤정보를 매칭해 드려요!</p>
	</div>
	<div class="bottom-box-btn py-32px">
		<div class="row">
			<div class="col">
				<button type="button" id="btnMatching" class="btn-primary w-100 bs-xl">
					매칭 설정하기
					<i class="icon-angle-right pl-60px"></i>
				</button>
			</div>
		</div>
	</div>
</c:if>

<%-- 일반회원인 경우  -------------------------------------------------------%>
<%-- 경영체인경우  ----------------------------------------------------------%>
<%-- 관리자/투자자/유관기관인경우  ------------------------------------------%>
<c:if test="${fn:contains('UBI', showMode)}">
	<!-- 매칭화면 영역 -->
	<div id="appMatchingResult"></div>
</c:if>

<%-- ############################ 내용 (종료) ############################ --%>
