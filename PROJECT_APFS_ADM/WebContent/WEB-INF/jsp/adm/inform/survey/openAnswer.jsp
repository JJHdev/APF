<%--
*******************************************************************************
***    명칭: openAnswer.jsp
***    설명: 운영관리-설문결과관리 화면
***
***    -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.04.04    LSH        First Coding.
***    1.0      2023.04.04    KSU        
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

<%-- ############################# 내용 (시작) ############################# --%>
<!-- Chart.js Chart Plugin -->
<script src="<c:url value='/plugins/charts/chartjs-4.3.0/chart.umd.js'/>"></script>
<script src="<c:url value='/plugins/charts/chartjs-4.3.0/chartjs-plugin-datalabels.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/com/comm_chart.js'/>"></script>

<div class="left-search" data-v-2eb60c26="">					
	<div class="top">
		<p class="txt1">
			<i class="icon-search-text"></i>검색조건
			<a id="btn_search_close" href="javascript:void(0)" class="close-btn">
				<i class="icon-times"></i>
				<span class="hiddenATag">닫기</span>
			</a>
		</p>
	</div>
	<div class="searchlist">
		<form id="searchForm" name="searchForm" method="post" onsubmit="return false;">
			<div class="form-label-box">
				<div class="box">
					<div class="area select">
						<label class="txt1">설문제목</label>					
						<div class="input-element icons-ver">
				             <input id="srchText" name="srchSrvyCn" title="설문제목" type="text" placeholder="설문제목을 입력하세요."/>
						</div>
					</div>
				</div>
				<div class="box">
					<div class="area select">
						<label class="txt1">설문대상</label>
						<div class="input-element icons-ver">
							<select name="srchTrgtCd" id="srchTrgt" title="설문대상"></select> 
							<i class="icon-angle-down right"></i>
						</div>
					</div>
				</div>
			</div>
		</form>
		<div class="seach-btn">
			<div class="row">
				<div class="col">
					<a id="btnReset" href="#" class="btn-combi-3 w-100"><i class="fs-18px icon-rotate-left mr-3px"></i> 초기화</a>
				</div>
				<div class="col">
					<a id="btnSearch" href="#" class="btn-primary w-100"><i class="fs-18px icon-search mr-3px"></i> 검색</a>
				</div>
			</div>
		</div>
	</div>
<!-- LEFT SEARCH END   >> ------------->
</div>

<div class="content">
	<div class="eletabletop1">
		<div class="row">
			<div class="col">
				<!-- 목록검색결과 영역 -->
				<p class="txt1" id="appGridResult"></p>
			</div>
			<div class="col element">
				<button class="btn-mint bs-m" id="btnExcel"><i class="icon-file-xls"></i> 엑셀 다운로드</button>
			</div>
		</div>
	</div>
	<div class="custom-ext-box pb-24px pt-0 px-16px tbox-prent">
		<!-- 목록 컨텐츠 영역 -->
		<div id="appGrid" class="pb-8px"></div>
		
		<div class="row justify-content-between">
			<div class="col flex-grow-0 white-space-nowrap">
				<!-- 목록 페이징 영역 -->
				<div class="mb-0 p-0 paging-box">
					<ul id="appGridPagination"></ul>
				</div>
			</div>
		</div>
	</div>

</div>
<%-- ############################# 내용 (종료) ############################# --%>
