<%--
*******************************************************************************
***    명칭: openPbanc.jsp
***    설명: 운영관리-사업공고관리 화면
***
***    -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.05.25      LHB     First Coding.
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
						<label class="txt1">통합검색</label>
						<div class="input-element icons-ver">
							<input type="text" name="srchText" id="srchText" title="통합검색" placeholder="검색어를 입력하세요">
							<i class="icon-search right"></i>
						</div>
					</div>
				</div>
				<div class="box">
					<div class="area select">
						<label class="txt1">사업분야</label>
						<div class="input-element icons-ver">
							<select name="bizFld" id="bizFld" title="사업분야"></select>
							<i class="icon-angle-down right"></i>
						</div>
					</div>
				</div>
				<div class="box">
					<div class="area select">
						<label class="txt1">사업대상</label>
						<div class="input-element icons-ver">
							<select name="bizTrgt" id="bizTrgt" title="사업대상"></select>
							<i class="icon-angle-down right"></i>
						</div>
					</div>
				</div>
				<div class="box">
					<div class="area select">
						<label class="txt1">사업대상연령</label>
						<div class="input-element icons-ver">
							<select name="bizTrgtAge" id="bizTrgtAge" title="사업대상연령"></select>
							<i class="icon-angle-down right"></i>
						</div>
					</div>
				</div>
				<div class="box">
					<div class="area select">
						<label class="txt1">사업대상업력</label>
						<div class="input-element icons-ver">
							<select name="bizTrgtFntnPd" id="bizTrgtFntnPd" title="사업대상업력">
							</select>
							<i class="icon-angle-down right"></i>
						</div>
					</div>
				</div>
				<div class="box">
					<div class="area select">
						<label class="txt1">접수기간</label>					
						<div class="input-element icons-ver">
							<select name="rcptSeCd" id="rcptSeCd" title="접수기간"></select>
							<i class="icon-angle-down right"></i>
						</div>
					</div>
					<div class="row mt-6px align-items-center" style="margin:0 -5px;">
						<div class="col px-5px">
							<div class="area select">
								<div class="input-element icons-ver">
									<input type="text" name="rcptBgngYmd" id="rcptBgngYmd" title="접수기간" class="datepicker-input fs-10px">
									<i class="icon-calendar right"></i>
								</div>
							</div>
						</div>
						<div class="col flex-grow-0 px-0">~</div>
						<div class="col px-5px">
							<div class="area select">
								<div class="input-element icons-ver">
									<input type="text" name="rcptEndYmd" id="rcptEndYmd" title="접수기간" class="datepicker-input fs-10px">
									<i class="icon-calendar right"></i>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="row multy-box">
					<div class="col">
						<div class="box">
							<div class="area select">
								<label class="txt1">작성일</label>						
								<div class="input-element icons-ver">
									<input type="text" name="srchBgngYmd" id="srchBgngYmd" title="작성일" class="datepicker-input fs-10px">
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
									<input type="text" name="srchEndYmd" id="srchEndYmd" title="작성일" class="datepicker-input fs-10px">
									<i class="icon-calendar right"></i>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</form>
		<div class="seach-btn">
			<div class="row">
				<div class="col">
					<a id="btnReset"  href="#" class="btn-combi-3 w-100"><i class="fs-18px icon-rotate-left mr-3px"></i> 초기화</a>
				</div>
				<div class="col">
					<a id="btnSearch" href="#" class="btn-primary w-100"><i class="fs-18px icon-search mr-3px"></i> 검색</a>
				</div>
			</div>
		</div>
	</div>
<!-- LEFT SEARCH END   >> ------------->
</div>

<!-- CENTER CONTENT START << ---------->
<div class="content">
	<div class="eletabletop1">
		<div class="row">
			<div class="col">
				<!-- 목록검색결과 영역 -->
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
