<%--
*******************************************************************************
***    명칭: openSubmitFile.jsp
***    설명: 지원사업관리-신청현황관리 화면
***
***    -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***  1.0    2023.06.26    JH    First Coding.
***  1.1    2023.06.27    JH    작업 완료.
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

<div class="left-search" data-v-2eb60c26="">					
	<div class="top">
		<p class="txt1"><i class="icon-search-text"></i>검색조건
			<a href="#" onclick="close_btn(this);" class="close-btn">
				<i class="icon-times-circle"></i>
				<span class="hiddenATag">닫기</span>
			</a>
		</p>
	</div>
	<div class="searchlist">
		<form id="searchForm" name="searchForm" method="post" onsubmit="return false;">
			<div class="form-label-box">
			
				<div class="box search-dcmntTaskSeCd">
					<div class="area select">
						<label class="txt1">지원사업명</label>
						<div class="input-element icons-ver">
							<select name="dcmntTaskSeCd" id="openDcmntTaskSeCd" title="지원사업명"></select>
							<i class="icon-angle-down right"></i>
						</div>
					</div>
				</div>
				
				<div class="box">
					<div class="area select">
						<label class="txt1">프로그램명</label>
						<div class="input-element icons-ver">
							<select name="dtlSeCd" id="openDtlSeCd" title="프로그램명">
								<option value="">전체</option>
							</select>
							<i class="icon-angle-down right"></i>
						</div>
					</div>
				</div>
				
				<div class="box">
					<div class="area select">
						<label class="txt1">신청구분</label>
						<div class="input-element icons-ver">
							<select name="aplySeCd" id="openAplySeCd" title="신청구분">
								<option value="">전체</option>
							</select>
							<i class="icon-angle-down right"></i>
						</div>
					</div>
				</div>
				
				
			</div>
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
		</form>
	</div>
</div>

<!-- CENTER CONTENT START << ---------->
<div class="content">
	
	<div class="eletabletop1" data-v-2eb60c26="">
		<div class="row">
			<div class="col">
				<p class="txt1" id="appGridResult"></p>
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
			<div class="col flex-grow-0 white-space-nowrap text-end">
				<button id="btnRegist" class="btn-primary bs-m"><i class="icon-edit"></i>신규등록</button>	
			</div>
		</div>
	</div>
</div>
<!-- CENTER CONTENT END   >> ---------->

<%-- ############################# 내용 (종료) ############################# --%>
