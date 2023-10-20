<%--
*******************************************************************************
***    명칭: openSprtUld.jsp
***    설명: 운영관리-경영체 데이터 업로드 화면
***
***    -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.07.06      LHB      First Coding.
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
			<a id="btn_search_close" href="javascript:void(0)" class="close-btn"><i class="icon-times"></i></a>
		</p>
	</div>
	<div class="searchlist">
		<form id="searchForm" name="searchForm" method="post" onsubmit="return false;">
			<div class="form-label-box">
				<div class="box">
					<div class="area select">
						<label class="txt1">경영체검색</label>
						<div class="input-element icons-ver">
							<input name="srchText" id="srchText" placeholder="경영체명 또는 사업자번호 입력" />
						</div>
					</div>
				</div>
				<div class="box">
					<div class="area select">
						<label class="txt1">기관명</label>
						<div class="input-element icons-ver">
							<select name="crdnsBzentyNo" id="crdnsBzentyNo"></select>
							<i class="icon-angle-down right"></i>
						</div>
					</div>
				</div>
				<div class="row multy-box">
					<div class="col">
						<div class="box">
							<div class="area select">
								<label class="txt1">업로드일자</label>					
								<div class="input-element icons-ver">
									<input type="text" name="srchBgngYmd" id="srchBgngYmd" class="datepicker-input" style="font-size: 11px;">
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
									<input type="text" name="srchEndYmd" id="srchEndYmd" class="datepicker-input" style="font-size: 11px;">
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
					<a href="#" id="btnReset"  class="btn-combi-3 w-100"><i class="fs-18px icon-rotate-left mr-3px"></i>초기화</a>
				</div>
				<div class="col">
					<a href="#" id="btnSearch" class="btn-primary w-100"><i class="fs-18px icon-search mr-3px"></i>검색</a>
				</div>
			</div>
			<div class="row mt-15px">
				<div class="col">
					<a href="#" id="btnDownload" class="btn-primary w-100"><i class="fs-18px icon-print mr-3px"></i>양식 다운로드</a>
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
			<div class="col flex-grow-0 white-space-nowrap text-end">
				<button id="btnUpload" class="btn-primary bs-m"><i class="icon-edit"></i>파일업로드</button>	
			</div>
		</div>
	</div>
</div>				
<!-- CENTER CONTENT END   >> ---------->

<%-- ############################# 내용 (종료) ############################# --%>
