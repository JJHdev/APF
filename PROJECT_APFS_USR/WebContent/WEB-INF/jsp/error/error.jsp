<%-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
**  @(#)500.jsp
**  @author     : ntarget
**  @version    : 1.0
**  @since      : 2021/02/21
**                2022/01/12 LSH error controller 적용
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ --%>

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

</head>
<body>

<!--  wrap << -->
<div class="wrap sub">

	<!-- Tiles HEADER START << ---------->
	<tiles:insertAttribute name="header"/>
	<!-- Tiles HEADER END   >> ---------->
	
	<div class="sub-banner">
		<div class="row">
			<div class="col-12 col-xl">
				<h2>
					<c:if test="${ exceptionMessage == null }"><c:out value="${errorTitle}"/></c:if>
					<c:if test="${ exceptionMessage != null }">안내 메시지</c:if>
				</h2>
			</div>
		</div>
	</div>
	
	<!--  sub-content << -->
	<div class="sub-content">
		
		<!--  datum-point << -->
		<div class="datum-point">

			<%-- 오류 메시지 START ================================================== --%>
			<div class="shadow-box-1 pb-56px py-40px text-center m-40px">
				<!-- <img src="<c:url value='/images/sub/searchicon2.svg'/>" alt=""> -->
				<div class="app-imgarea"><img src="<c:url value='/images/sub/searchicon2.png'/>"/></div>
				<p class="fs-24px fw-600 mb-16px exception_message">
					<c:if test="${ exceptionMessage == null }"><c:out value="${errorMessage    }" escapeXml="false"/></c:if>
					<c:if test="${ exceptionMessage != null }"><c:out value="${exceptionMessage}" escapeXml="false"/></c:if>
				</p>
				<p class="fs-20px fw-500 mb-16px"></p>
				<span class="fs-15px fw-300"></span>
			</div>
			<div class="bottom-box-btn">
				<div class="row">
					<div class="col">
						<button type="button" class="bs-xl btn-combi-3 w-100" onclick="goBack()"><i class="icon-angle-left"></i>이전페이지로 이동</button>
					</div>
				</div>
			</div>
			<%-- 오류 메시지 END ==================================================== --%>

		<!--  datum-point >> -->
		</div>
		
	<!--  sub-content >> -->
	</div>
	
	<!-- Tiles FOOTER START << ----------->
	<tiles:insertAttribute name="footer" />
	<!-- Tiles FOOTER END   >> ----------->

<!--  wrap >> -->
</div>

</body>
</html>
