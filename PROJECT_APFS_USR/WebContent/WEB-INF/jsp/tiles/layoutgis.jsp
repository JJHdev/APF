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

	<!-- Tiles RESOURCE -->
	<tiles:insertAttribute name="resource"/>

	<link rel="stylesheet" type="text/css" href="<c:url value='/js/usr/gis/gis/openlayers/v4/ol.css'/>" />
    <script type="text/javascript" src="<c:url value='/js/usr/gis/gis/openlayers/v4/ol.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/js/usr/gis/gis/openlayers/v4/proj4.js'/>"></script>
    <script src="<c:url value='/plugins/charts/chartjs-4.3.0/chart.umd.js'/>"></script>
	<script src="<c:url value='/plugins/charts/chartjs-4.3.0/chartjs-plugin-datalabels.js'/>"></script>

<c:if test="${not empty scriptPage}">
    <!-- Business Script-->
    <script type="text/javascript" src="<c:url value='/js${scriptPage}.js?version=${ver}'/>"></script>
</c:if>

</head>
<body>

<!--  wrap << -->
<div class="wrap sub">

	<!-- =============================  BODY START ============================= -->
	<tiles:insertAttribute name="body"/>
	<!-- =============================  BODY END   ============================= -->
	
	<!-- Tiles FOOTER START << ---------->
	<tiles:insertAttribute name="footer" />
	<!-- Tiles FOOTER END   >> ---------->
	
<!--  wrap >> -->
</div>

</body>
</html>
