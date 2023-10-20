<%--
*******************************************************************************
***	명칭: loginCheck.jsp
***	설명: OAUTH 로그인완료 후 분기할 팝업 페이지
***
***	-----------------------------    Modified Log   ---------------------------
***	버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***	1.0      2023.03.15    LSH        First Coding.
*******************************************************************************
--%>
<!DOCTYPE html>
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

<c:choose>
	<c:when test="${act eq 'MAIN'}">
		<script> opener.location.href = getUrl(CURL.MAIN); </script>
	</c:when>
	<c:when test="${act eq 'LOGIN'}">
		<script> opener.location.href = getUrl(CURL.LOGIN); </script>
		<c:if test="${success == 'N'}">
			<%-- 로그인 스크립트 --%>
			<script type="text/javascript" src="<c:url value='/js/com/comm_login.js'/>"></script>
			<%-- 회원가입 공통 스크립트 --%>
			<script type="text/javascript" src="<c:url value='/js/com/app_join.js'/>"></script>
			<script>
				Login.doReApplyOauth('${failFlag}', '${message}');
			</script>
		</c:if>
	</c:when>
	<c:when test="${act eq 'JOIN'}">
		<script> $.bizUtils.goJoinBiz({child: true});</script>
	</c:when>
	<c:when test="${act eq 'DONE'}">
		<script> $.bizUtils.goJoinDone({child: true});</script>
	</c:when>
	<c:otherwise></c:otherwise>
</c:choose>
<c:if test="${empty message}">
	<script> window.close(); </script>
</c:if>
<c:if test="${!empty message}">

	<!--  wrap << -->
	<div class="wrap sub">
	
		<div class="sub-banner">
			<div class="row">
				<div class="col-12 col-xl">
					<h2>오류 메시지</h2>
				</div>
			</div>
		</div>
		
		<!--  sub-content << -->
		<div class="sub-content">
			
			<!--  datum-point << -->
			<div class="datum-point">
	
				<%-- 오류 메시지 START ================================================== --%>
				<div class="shadow-box-1 pb-56px py-40px text-center m-40px">
					<div class="app-imgarea"><img src="<c:url value='/images/sub/searchicon2.png'/>"/></div>
					<p class="fs-18px fw-600 mb-16px exception_message">
						<c:out value="${message}" escapeXml="false"/>
					</p>
				</div>
				<div class="bottom-box-btn">
					<div class="row">
						<div class="col">
							<button type="button" class="bs-xl btn-combi-3 w-100" onclick="window.close()"><i class="icon-times"></i>닫기</button>
						</div>
					</div>
				</div>
				<%-- 오류 메시지 END ==================================================== --%>
	
			<!--  datum-point >> -->
			</div>
			
		<!--  sub-content >> -->
		</div>
		
	<!--  wrap >> -->
	</div>
</c:if>

<%-- ############################# 내용 (종료) ############################# --%>
