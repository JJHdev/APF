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
								<img src="<c:url value='/images/logo-W.png'/>"/>
								<a class="menu-collapse"><i class="icon-menu-duo-square"></i></a>
							</p>
						</div>
						<!-- 좌측메뉴 -->
						<div class="lev-menu-box" id="sidebar" ></div>
					</div>
				</div>

				<!-- Tiles FOOTER START << ---------->
				<tiles:insertAttribute name="footer" />
				<!-- Tiles FOOTER END   >> ---------->
				
			<!-- LEFT END   >> ---------->
			</div> 

			<!-- RIGHT START << ---------->
			<div class="col right">

				<!-- HEADER START << ---------->
				<div class="headadmin1">
					<div class="row">
						<div class="col page-title">
							<p class="txt1">
								<i class="icon-info-circle"></i>
								<c:if test="${ exceptionMessage == null }">
									${errorTitle}
								</c:if>
								<c:if test="${ exceptionMessage != null }">
									안내 메시지
								</c:if>
								
							</p>
						</div>
					</div>
				<!-- HEADER END   >> ---------->
				</div>

				<!-- CONTENT START << ---------->
				<div class="row content-wrap">

					<!-- CENTER CONTENT START << ---------->
					<div class="content">
					
						<div class="detail-input-box">
								
							<%-- 오류 메시지 START ================================================== --%>

							<div class="m-20px">
								<div class="error-wrap">
									<c:if test="${ exceptionMessage == null }">
										<p class="exception_message">
											${errorMessage}
										</p>
									</c:if>
									<c:if test="${ exceptionMessage != null }">
										<p class="exception_message">
											<span class="exception_detail_message">${exceptionMessage}</span>
										</p>
									</c:if>
								</div>
							</div>
								
							<!-- 버튼영역 -->
							<div class="row text-center">
								<div class="col">
									<div class="bottom-btn-box">
										<a href="javascript:history.back();" class="bs-l btn-combi-3 w-100 mx-2px"><i class="icon-arrow-back-square mr-3px"></i> 이전페이지로 이동</a>
									</div>
								</div>
							</div>
							<%-- 오류 메시지 END ==================================================== --%>
							
						</div>
					
					<!-- CENTER CONTENT END   >> ---------->
					</div>

				<!-- CONTENT END   >> ---------->
				</div>

			<!-- RIGHT END   >> ---------->
			</div>

		</div>

	</div>

</body>
</html>
