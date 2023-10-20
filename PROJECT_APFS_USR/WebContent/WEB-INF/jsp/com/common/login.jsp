<%--
******************************************************************************************
*** 파일명 : login.jsp
*** 설명   : 사용자 로그인 화면
***
*** -----------------------------    Modified Log   --------------------------------------
*** 버전        수정일자            수정자                  내용
*** --------------------------------------------------------------------------------------
*** 1.0         2020-09-28          ntarget                 First Coding.
*** 1.0         2021-11-08          LSH                     식별아이디 인증 추가
*** 1.0         2022-01-05          LSH                     시스템분리 (사용자/관리자)
******************************************************************************************
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

<%-- 로그인 스크립트 --%>
<script type="text/javascript" src="<c:url value='/js/com/comm_login.js'/>"></script>

<%-- 회원가입 공통 스크립트 --%>
<script type="text/javascript" src="<c:url value='/js/com/app_join.js'/>"></script>

<%-- 사용자 로그인 START ================================================== --%>
<form id="loginForm" name="loginForm" method="post" onsubmit="return false;">
	<div class="pt-36px pt-md-72px">
		<div class="shadow-box-1 mx-auto px-16px px-md-32px py-32px py-md-64px" style="max-width: 400px;">
			<div class="row align-items-center justify-content-between pb-24px ">
				<div class="col">
					<p class="fs-40px fw-200" style="color: #000;">이메일</p>
				</div>
				<div class="col flex-grow-0 white-space-nowrap">
					<button type="button" id="btnJoin" class="bs-l btn-primary-ouline"><i class="icon-user-edit"></i>
						회원가입
					</button>
				</div>
			</div>
			<div class="row" style="margin:-8px;">
				<div class="col-12 p-8px">
					<div class="form-area-box">
						<div class="ele-icon-box">
							<i class="icon-user text-primary"></i>
							<input type="text" name="username" id="username" maxlength="50" placeholder="이메일을 입력해 주세요">
							<i class="icon-times-circle F text-ele-TextDisable" onclick="clearInput('#username')"></i>
						</div>
					</div>
				</div>
				<div class="col-12 p-8px">
					<div class="form-area-box">
						<div class="ele-icon-box">
							<i class="icon-lock text-primary"></i>
							<input type="password" name="password" id="password" maxlength="30" placeholder="비밀번호를 입력해 주세요">
							<i class="icon-times-circle F text-ele-TextDisable" onclick="clearInput('#password')"></i>
						</div>
					</div>
				</div>
				<div class="col-12 p-8px">
					<button type="button" id="btnLogin" class="bs-l btn-primary w-100"><i class="icon-log-in"></i>
						로그인
					</button>
				</div>
				<div class="col-6 p-8px">
					<button type="button" 
						class="bs-l btn-primary px-0 w-100 position-relative btnOauth" 
						data-code="naver" 
						data-url="<c:out value="${naverAuthUrl}"/>" 
						style="background: #2DB400;">
						<i class="icon-naver F"></i>
						네이버로 로그인
						<span class="app-last-login" data-code="N"></span> 
					</button>
				</div> 
				<div class="col-6 p-8px">
					<button type="button" 
						class="bs-l btn-primary px-0 w-100 position-relative btnOauth" 
						data-code="kakao" 
						data-url="<c:out value="${kakaoAuthUrl}"/>" 
						style="color: #391B1B;background: #FAE300;">
						<i class="icon-kakao F"></i>
						카카오로 로그인
						<span class="app-last-login" data-code="K"></span> 
					</button>
				</div>
			</div>
		</div>
		<div class=" mt-16px mx-auto text-end " style="max-width: 400px;"> 
			<a href="#" id="btnFindId"   class="fs-13px fw-300 text-primary">아이디 찾기</a>
			<text class="text-primary"> / </text>
			<a href="#" id="btnFindPswd" class="fs-13px fw-300 text-primary">비밀번호 찾기</a>
		</div>
	</div>
</form>
<%-- 사용자 로그인 END ==================================================== --%>

<%-- ############################# 내용 (종료) ############################# --%>
