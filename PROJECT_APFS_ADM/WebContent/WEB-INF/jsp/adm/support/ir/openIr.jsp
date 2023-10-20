<%--
*******************************************************************************
***    명칭: openIr.jsp
***    설명: 운영관리-IR지원현황 화면
***
***    -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.06.08      LHB      First Coding.
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

<!-- LEFT SEARCH START << ------------->
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
						<label class="txt1">펀드명</label>
						<div class="input-element icons-ver">
							<input name="fundNm" id="fundNm" title="펀드명" />
						</div>
					</div>
				</div>
				<div class="box">
					<div class="area select">
						<label class="txt1">검토결과</label>
						<div class="input-element icons-ver">
							<select name="sprtSttsCd" id="sprtSttsCd" title="검토결과" ></select>
							<i class="icon-angle-down right"></i>
						</div>
					</div>
				</div>
				<div class="row multy-box">
					<div class="col">
						<div class="box">
							<div class="area select">
								<label class="txt1">지원일자</label>					
								<div class="input-element icons-ver">
									<input type="text" name="srchBgngYmd" id="srchBgngYmd" title="지원일자" class="datepicker-input fs-10px">
									<i class="icon-calendar right"></i>
								</div>
							</div>
						</div>
					</div>
					<div class="col flex-grow-0 px-0">~</div>
					<div class="col">
						<div class="box">
							<div class="area select">
								<div class="input-element icons-ver">
									<input type="text" name="srchEndYmd" id="srchEndYmd" title="지원일자" class="datepicker-input fs-10px">
									<i class="icon-calendar right"></i>
								</div>
							</div>
						</div>
					</div>
					<div class="col-12 mt-8px">
						<div class="justify-content-between row" style="margin: 0 -2px;">
							<div class="col px-2px">
								<a href="#" class="bs-s btn-black-ouline w-100 btn-date" data-dates="30">30일</a>
							</div>
							<div class="col px-2px">
								<a href="#" class="bs-s btn-black-ouline w-100 btn-date" data-dates="60">60일</a>
							</div>
							<div class="col px-2px">
								<a href="#" class="bs-s btn-black-ouline w-100 btn-date" data-dates="90">90일</a>
							</div>
							<div class="col px-2px">
								<a href="#" class="bs-s btn-black-ouline w-100 btn-date" data-dates="365">1년</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</form>
		<div class="seach-btn">
			<div class="row">
				<div class="col">
					<a href="#" id="btnReset"  class="btn-combi-3 w-100"><i class="fs-18px icon-rotate-left mr-3px"></i> 초기화</a>
				</div>
				<div class="col">
					<a href="#" id="btnSearch" class="btn-primary w-100"><i class="fs-18px icon-search mr-3px"></i> 검색</a>
				</div>
			</div>
		</div>
	</div>
<!-- LEFT SEARCH END   >> ------------->
</div>

<!-- CENTER CONTENT START << ---------->
<div class="content w-ver1">			
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
		</div>
	</div>
</div>				
<!-- CENTER CONTENT END   >> ---------->

<%-- ############################# 내용 (종료) ############################# --%>
