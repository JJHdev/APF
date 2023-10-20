<%--
*******************************************************************************
***    명칭: listProg.jsp
***    설명: 프로그램관리 관리 화면
***
***    -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2021.09.05    LSH        First Coding.
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
           	
      			<div class="box">
					<div class="area select">
						<label class="txt1">시스템</label>
						<div class="input-element icons-ver">
							<select name="srchSysCd" id="srchSysCd" title="시스템"></select>
							<i class="icon-angle-down right"></i>
						</div>
					</div>
				</div>
				
				<div class="box">
					<div class="area select">
						<label class="txt1">연결메뉴 선택</label>
						<div class="input-element icons-ver">
							<select name="srchMenuId" id="srchMenuId" title="연결메뉴선택"></select>
							<i class="icon-angle-down right"></i>
						</div>
					</div>
				</div>
				<div class="box">
					<div class="area select ">
						<label class="txt1">검색어 입력</label>					
						<div class="input-element icons-ver">
							<input  id="srchText" name="srchText" type="text" title="검색어입력" placeholder="프로그램ID, 명칭, URL등을 입력하세요." style="width:350px"/>
						</div>
					</div>
				</div>
			</div>
		</form>
		<div class="seach-btn">
			<div class="row">
				<div class="col"><a id="btnReset"  href="javascript:void(0)" class="btn-combi-3 w-100"><i class="fs-18px icon-rotate-left mr-3px"></i> 초기화</a></div>
				<div class="col"><a id="btnSearch" href="javascript:void(0)" class="btn-primary w-100"><i class="fs-18px icon-search mr-3px"></i> 검색</a></div>
			</div>
		</div>
	</div>
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
	<div class="custom-ext-box pb-24px pt-0 px-16px tbox-prent" >
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
				<button id="btnRegist" class="btn-primary bs-m"><i class="icon-edit"></i> 신규등록</button>	
			</div>
		</div>
	</div>
</div>
<!-- CENTER CONTENT END   >> ---------->
<%-- ############################# 내용 (종료) ############################# --%>