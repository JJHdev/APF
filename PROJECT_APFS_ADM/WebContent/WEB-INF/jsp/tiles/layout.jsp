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

<c:if test="${not empty scriptPage}">
    <!-- Business Script-->
    <script type="text/javascript" src="<c:url value='/js${scriptPage}.js?version=${ver}'/>"></script>
</c:if>

</head>
<body>

	<div class="wrap">
	
		<div class="row addmin-page-wrap">

			<!-- LEFT START << ---------->
			<div class="col left">
				<!--menu-->
				<div id="menu">
					<div class="left-menu">
						<div class="top">
							<p class="txt1">
								<%-- 로고이미지 --%>
								<img src="<c:url value='/images/logo-W.svg'/>" alt="ASSIST관리자"/>
								<a class="menu-collapse"><i class="icon-menu-min"></i></a>
							</p>
						</div>
						<!-- 좌측메뉴 -->
						<div class="lev-menu-box" id="sidebar" 
							data-first="<c:out value="${PAGEINFO.pageInfo.firstMenuId}"/>"
							data-menu="<c:out value="${PAGEINFO.pageInfo.menuId}"/>"						
						></div>
					</div>
				</div>

				<!-- Tiles FOOTER START << ---------->
				<tiles:insertAttribute name="footer" />
				<!-- Tiles FOOTER END   >> ---------->
				
			<!-- LEFT END   >> ---------->
			</div> 

			<!-- RIGHT START << ---------->
			<div class="col right">

				<!-- Tiles HEADER START << ---------->
				<tiles:insertAttribute name="header"/>
				<!-- Tiles HEADER END   >> ---------->

				<!-- CONTENT START << ---------->
				<div class="row content-wrap">

<!-- =============================  BODY START ============================= -->
				<tiles:insertAttribute name="body"/>
<!-- =============================  BODY END   ============================= -->

				<!-- CONTENT END   >> ---------->
				</div>

			<!-- RIGHT END   >> ---------->
			</div>

		</div>

	</div>

</body>
</html>
