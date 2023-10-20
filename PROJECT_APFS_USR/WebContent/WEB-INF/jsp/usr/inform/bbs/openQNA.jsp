<%--
*******************************************************************************
***    명칭: openQNA.jsp
***    설명: 정보서비스 - 투자유치가이드 화면
***
***    -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.08.21      JH        작업완료.
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

<%-- ############################# 내용 (시작) ############################# --%>
<form:form commandName="model" id="registForm" name="registForm" method="post" enctype="multipart/form-data" onsubmit="return false;">
	<form:hidden path="isNext"  /><%-- 처리모드 --%>
</form:form>
<div class="sub-content">
	<div class="datum-point">
		<div class="shadow-box-1 pb-56px py-40px text-center mb-40px">
			<div class="d-inline-block img1" style="background-image: url('<c:url value='/images/sub/3d icon_bg.png'/>');background-size: 100% 100%;">
				<img src="<c:url value='/images/sub/searchicon2.png'/>" alt="" class="">
			</div>
			<p class="fs-16px fs-md-24px fw-600 mb-16px">1:1문의가 접수되었습니다.</p>
			<p class="fs-14px fs-md-20px fw-500 mb-16px">신청 내역은 마이페이지에서 확인할 수 있으며, <br/>문의 내역에 대한 답변은 담당자 확인 후 최대한 빠르게 처리해 드리겠습니다.<br/>감사합니다.</p>
			<span class="fs-15px fw-300">기타 문의 사항은 1:1 문의 또는 자주 묻는 질문을 확인해주세요.</span>
		</div>
		<div class="bottom-box-btn">
			<div class="row">
				<div class="col">
					<button class="bs-xl btn-combi-3 w-100" id="btnMainPage"><i class="icon-home-roof"></i>메인화면으로 돌아가기</button>
				</div>
				<div class="col">
					<button class="btn-primary w-100 bs-xl" id="btnMyPage"><i class="icon-user"></i>마이페이지</button>
				</div>
			</div>
		</div>
	</div>
</div>

<%-- ############################# 내용 (종료) ############################# --%>
