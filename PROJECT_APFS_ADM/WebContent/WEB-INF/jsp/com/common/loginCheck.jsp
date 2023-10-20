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
		<script> opener.location.href = getUrl('/usr/main/main.do'); </script>
	</c:when>
	<c:when test="${act eq 'LOGIN'}">
		<script> opener.location.href = getUrl('/com/common/login.do'); </script>
	</c:when>
	<c:when test="${act eq 'JOIN'}">
		<script> opener.location.href = getUrl('/com/user/openCorp.do'); </script>
	</c:when>
	<c:when test="${act eq 'DONE'}">
		<script> opener.location.href = getUrl('/com/user/doneJoin.do'); </script>
	</c:when>
	<c:otherwise></c:otherwise>
</c:choose>
<c:if test="${empty message}">
	<script> window.close(); </script>
</c:if>
<c:if test="${!empty message}">
	<div class="app-error">
		<div class="error-title">
			<h2>오류 메시지</h2>
		</div>
		<div class="error-wrap">
			<p>${message}</p>
		</div>
		<a href="javascript:window.close();" class="blue" title="닫기">닫기</a>
	</div>
</c:if>

<%-- ############################# 내용 (종료) ############################# --%>
