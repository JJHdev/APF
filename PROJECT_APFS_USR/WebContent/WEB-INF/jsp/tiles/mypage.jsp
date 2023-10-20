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

<%@ page import="common.user.UserInfo" %>
<%@ page import="common.util.UserUtils" %>
<%
// UserInfo 세션
UserInfo userInfo = UserUtils.getUserInfo(request);
%>
<c:set var="userInfo" value="<%=userInfo%>" />
<!DOCTYPE html>
<html lang="ko">
<head>
  	<title><spring:message code="title.sysname"/></title>

	<!-- Tiles RESOURCE -->
	<tiles:insertAttribute name="resource"/>

	<script type="text/javascript">
	$(document).ready(function() {
		// 서브메뉴바 로딩
		loadSubBar('#mypage-subbar', getUrl('/com/common/listMypageMenu.do'));
	});
	</script>

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
	<div class="sub-banner <c:out value="${PAGEINFO.pageInfo.firstMenuId}"/>">
		<div class="row">
			<div class="col-12 col-xl">
				<h2><span>${PAGEINFO.pageInfo.firstMenuNm}</span>
					${PAGEINFO.pageInfo.titleNm}
					<span class="white-space-nowrap"><em class="fw-600 text-white"><c:out value="${userInfo.userNm}"/>님</em>, 반갑습니다.<br>
						<!-- 질문 전 자주 묻는 질문을 먼저 확인해 주세요  -->
					</span>
				</h2>
			</div>
			<div class="col-12 col-xl"> </div>
			<div class="col-12 col-xl d-none d-xl-inline-flex">
				<img src="<c:url value="/images/menu/"/><c:out value="${PAGEINFO.pageInfo.firstMenuId}"/>.png" class="w-auto" alt="mypage">
			</div>
		</div>
	</div>
			
	<!--  sub-content << -->
	<div class="sub-content">
		
		<!--  datum-point << -->
		<div class="datum-point">
			
			<!-- TOP NAVIGATION << -->
			<div class="sub-menu-box">
				<div class="row">
					<div class="col" 
						id="mypage-subbar" 
						data-first="<c:out value="${PAGEINFO.pageInfo.firstMenuId}"/>"
						data-menu="<c:out value="${PAGEINFO.pageInfo.menuId}"/>"
					></div>
					
					<div class="col d-none d-xl-block">
						<div class="navigation" 
							id="page-breadcrumbs"
							data-path="<c:out value="${PAGEINFO.pageInfo.menuPath}"/>"
							data-menu="<c:out value="${PAGEINFO.pageInfo.menuId}"/>"
							data-url="<c:out value="${PAGEINFO.pageInfo.urlPath}"/>"
						></div>
					</div>
				</div>
			<!-- TOP NAVIGATION >> -->
			</div>
			
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
