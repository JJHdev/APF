<%--
*******************************************************************************
***    명칭: viewEnt.jsp
***    설명: 투자서비스 - 경영체검색 - 경영체 상세보기
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.05.02    LSH        First Coding.
*******************************************************************************
--%>
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

<%-- ############################ 내용 (시작) ############################ --%>

<script type="text/javascript" src="<c:url value='/js/com/app_dashboard.js'/>"></script>

<script src="<c:url value='/plugins/charts/chartjs-4.3.0/chart.umd.js'/>"></script>
<script src="<c:url value='/plugins/charts/chartjs-4.3.0/chartjs-plugin-datalabels.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/com/comm_chart.js'/>"></script>

<form id="selectForm" name="selectForm" method="post" onsubmit="return false;">
<input type="hidden" id="mode"     name="mode"     value="<c:out value="${model.mode    }"/>"/>
<input type="hidden" id="bzentyNo" name="bzentyNo" value="<c:out value="${model.bzentyNo}"/>"/>
</form>

	<!-- 슬라이드정보 -->
	<div id="appSlideCard"></div>
	<div class="app-page-break"></div>
	
	<div class="row mb-24px">
		<div class="col text-end">
			<button type="button" class="btnList btn-black-ouline bs-l app-off"><i class="icon-list-F"></i>목록으로 돌아가기</button>
		</div>
	</div>
	
	<!-- 구분탭 -->
	<div id="appTab" class="appTab"></div>
	
	<!-- 대시보드 TAB << ---------->
	<div id="appDashTab">
	
		<!-- 설명글 -->
		<div id="appDescript"></div>
		
		<!-- 기업 기본정보 -->
		<div id="appInfoCard" class="mb-32px"></div>
		<div class="app-page-break"></div>
		
		<!-- 주요 투자정보 -->
		<div id="appInvtCard" class="mb-32px"></div>
		<div class="app-page-break"></div>

		<!-- 특허 및 상표권 보유현황 -->
		<div id="appPtntCard" class="mb-32px"></div>
		<div class="app-page-break"></div>
		
		<!-- 주요 재무정보 -->
		<div id="appFnnrCard" class="mb-32px"></div>
		<div class="app-page-break"></div>

		<!-- 추가 재무정보 -->
		<div id="appFnnrAddCard" class="mb-32px"></div>
		<div class="app-page-break"></div>
		
		<!-- 정부지원사업 이력 -->
		<div id="appSptHstCard" class="mb-32px"></div>
		<div class="app-page-break"></div>
		
		<!-- 회사연혁 -->
		<div id="appCoHstCard" class="mb-32px"></div>
		<div class="app-page-break"></div>
		
		<!-- 경영진 및 주주 현황 -->
		<div id="appOwnerCard" class="mb-32px"></div>
	
	<!-- 대시보드 TAB >> ---------->
	</div>
	<!-- 상세정보 TAB << ---------->
	<div id="appInfoTab">
		<div class="mb-32px info-filelistbox-of-box">
			<div class="row">
				<!-- 사업계획서 -->
				<div id="appEntPlanFile" class="col-12 col-md"></div>
				<!-- 첨부자료 -->
				<div id="appEntAddFile" class="col-12 col-md"></div>
			</div>
		</div>
		<!-- 홍보영상 -->
		<div id="appEntPromotion" class="mb-32px"></div>

	<!-- 상세정보 TAB >> ---------->
	</div>
	<!-- 2023.08.18 제외처리
	<div class="bottom-box-btn p-0">
		<div class="row">
			<div class="col">
				<button type="button" class="btnList btn-primary w-100 bs-xl app-off"><i class="icon-list"></i>목록으로 돌아가기</button>
			</div>
		</div>
	</div>
	 -->

<%-- ############################ 내용 (종료) ############################ --%>
