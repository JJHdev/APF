<%--
*******************************************************************************
*** 파일명 : login.jsp
*** 설명   : 관리자 로그인 화면
***
*** -------------------------    Modified Log    ------------------------------
*** 버전        수정일자            수정자                  내용
*** ---------------------------------------------------------------------------
*** 1.0         2020-09-28          ntarget       First Coding.
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

<!DOCTYPE html>
<html>
<head>
  	<title><spring:message code="title.sysname"/></title>
  	
	<!-- Tiles RESOURCE -->
	<tiles:insertAttribute name="resource"/>

	<script type="text/javascript" src="<c:url value='/js/com/comm_login.js?version=${ver}'/>"></script>
	
</head>
<body>
	<div class="wrap">
		<div class="loginbox">
			<div class="box">
				<img src="<c:url value="/images/property1-blue2.svg"/>" alt="" class="mb-16px">
				<p class="fs-32px fw-300 mb-16px">
					ASSIST 관리자 시스템
				</p>

				<%-- 사용자 로그인 START ================================================== --%>
				<form id="loginForm" name="loginForm" method="post" onsubmit="return false;">
					<div class="m-auto p-32px round-16px shadow-box" style="max-width: 380px;">
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
						</div>
					</div>
				</form>
				<%-- 사용자 로그인 END ==================================================== --%>
			</div>
		</div>
	</div>

</body>
</html>
