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
<html lang="ko">
<head>
  	<title><spring:message code="title.sysname"/></title>

	<!--  fullPage CSS -->
	<link rel="stylesheet" type="text/css" href="<c:url value='/jquery/fullpage/jquery.fullPage.css'/>" />
  	
	<!-- Tiles RESOURCE -->
	<tiles:insertAttribute name="resource"/>
	
	<!--  fullPage CSS -->
    <script type="text/javascript" src="<c:url value='/jquery/fullpage/jquery.fullPage.js'/>"></script>

<c:if test="${not empty scriptPage}">
    <!-- Business Script-->
    <script type="text/javascript" src="<c:url value='/js${scriptPage}.js?version=${ver}'/>"></script>
</c:if>
	
</head>
<body>

<!--  wrap << -->
<div class="wrap">

	<!-- Tiles HEADER START << ---------->
	<tiles:insertAttribute name="header"/>
	<!-- Tiles HEADER END   >> ---------->

	<!-- MAIN START << ------------------>
	<main id="fullpage" class="main-content">

<!-- =============================  BODY START ============================= -->
		<tiles:insertAttribute name="body"/>
<!-- =============================  BODY END   ============================= -->
		<section class="section fp-auto-height footer-ver">
			<!-- Tiles FOOTER START << ---------->
			<tiles:insertAttribute name="footer" />
			<!-- Tiles FOOTER END   >> ---------->
		</section>
				
	<!-- MAIN END   << ------------------>
	</main>
	
<!--  wrap >> -->
</div>

</body>
</html>
