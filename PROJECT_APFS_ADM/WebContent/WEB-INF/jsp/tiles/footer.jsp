<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="app"    uri="/WEB-INF/tld/app.tld" %>
<%@ taglib prefix="f"      uri="/WEB-INF/tld/f.tld" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec"    uri="http://www.springframework.org/security/tags" %>

<%@ page import="common.user.UserInfo" %>
<%@ page import="common.util.UserUtils"%>
<%
// UserInfo 세션
UserInfo userInfo = UserUtils.getUserInfo(request);
%>

<c:set var="userInfo" value="<%=userInfo%>" />
<c:set var="pagePath" value="${requestScope['javax.servlet.forward.servlet_path']}" />

<div class="footer">
<c:if test="${userInfo.login}">
	<div class="userinfo">
		<p class="txt1"><c:out value="${userInfo.userNm}"/> 님 
			<span class="txt2"><c:out value="${userInfo.roleNm}"/></span>
		</p>
		<button type="button" id="btn_logout" class="btn-dagray"><i class="icon-log-out"></i>로그아웃</button>
	</div>
</c:if>
	<div class="copyright">
		<spring:message code="title.com.footer.copyright"/>
	</div>
</div>

<%-- EasyUI 공통Calendar --%>
<div id="layerCalendar"></div>

<%-- 첨부파일 업로드 프레임 --%>
<div id="uploadFrame"></div>

<%-- 첨부파일 다운로드 프레임 --%>
<div id="downloadFrame"></div>

