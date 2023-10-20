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

<%@ page import="org.springframework.web.context.WebApplicationContext" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="common.user.UserInfo" %>
<%@ page import="common.util.CommUtils" %>
<%@ page import="common.util.properties.ApplicationProperty"%>

<!DOCTYPE html>
<html lang="ko">
<head>
  	<title><spring:message code="title.sysname"/></title>

	<!-- Tiles RESOURCE -->
	<tiles:insertAttribute name="resource"/>

<c:if test="${not empty scriptPage}">
    <!-- Business Script-->
    <script type="text/javascript" src="<c:url value='/js${scriptPage}.js?version=${ver}'/>"></script>
</c:if>

</head>
<body>

<!--  wrap << -->
<div class="wrap sub">

	<!-- Tiles HEADER START << ---------->
	<tiles:insertAttribute name="header"/>
	<!-- Tiles HEADER END   >> ---------->
	<div class="sub-banner sublayout">
		<div class="row">
			<div class="col-12 col-xl">
				<h2>
				<c:if test="${titleNm != null}">
					<c:out value="${titleNm}"/>
				</c:if>
				<c:if test="${titleNm == null}">
					<c:out value="${PAGEINFO.pageInfo.titleNm}"/>
				</c:if>
				</h2>
			</div>
			<div class="col-12 col-xl"></div>
			<div class="col-12 col-xl d-none d-xl-inline-flex">
				<div class="app-sublayout"></div>
			</div>
		</div>
	</div>

	<!--  sub-content << -->
	<div class="sub-content">
		
		<!--  datum-point << -->
		<div class="datum-point">
			
<!-- =============================  BODY START ============================= -->
			<tiles:insertAttribute name="body"/>
<!-- =============================  BODY END   ============================= -->
		
		<!--  datum-point >> -->
		</div>
		
	<!--  sub-content >> -->
	</div>
	
	<!-- Tiles FOOTER START << ---------->
	<tiles:insertAttribute name="footer" />
	<!-- Tiles FOOTER END   >> ---------->
	
<!--  wrap >> -->
</div>

</body>
</html>
