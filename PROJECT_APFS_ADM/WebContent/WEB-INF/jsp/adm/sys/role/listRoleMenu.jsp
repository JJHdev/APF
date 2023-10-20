<%--
*******************************************************************************
***    명칭: listRoleMenu.jsp
***    설명: 역할별메뉴관리 관리 화면
***
***    -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2021.09.06    LSH        First Coding.
***    1.1      2023.07.23    J H        작업 완료 .
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
					<label class="txt1">역할</label>
					<div class="input-element icons-ver">
						<select name="srchRoleId" id="srchRoleId" title="역할"></select>
						<i class="icon-angle-down right"></i>
					</div>
				</div>
			</div>
			
			<div class="box">
				<div class="area select">
					<label class="txt1">시스템</label>
					<div class="input-element icons-ver">
						<select name="srchSysCd" id="srchSysCd" title="시스템"></select>
						<i class="icon-angle-down right"></i>
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
<div class="content">
	<div class="row grid1">
		<div class="col">
			<div class="eletabletop1">
				<div class="row">
					<div class="col">
						<!-- 목록검색결과 영역 -->
						<p class="txt1" id="appRoleGridResult"></p>
					</div>
				</div>
			</div>
			<div class="custom-ext-box pb-24px pt-0 px-16px tbox-prent" style="overflow:auto; height:900px;">
				<!-- 목록 컨텐츠 영역 -->
				<div id="appRoleGrid" class="pb-8px"></div>
			</div>
		</div>
		<div class="col btnbox">
			<a href="#void" id="btnAppend" class="bs-xl btn-White mb-16px"><i class="icon-angle-left"></i><span class="hiddenATag">이동</span></a>
			<a href="#void" id="btnRemove" class="bs-xl btn-White"><i class="icon-angle-right"></i><span class="hiddenATag">이동</span></a>
		</div>
		<div class="col">
			<div class="eletabletop1" data-v-2eb60c26="">
				<div class="row">
					<div class="col">
						<p class="txt1" id="appMenuGridResult"></p>
					</div>
				</div>
			</div>
			<div class="custom-ext-box pb-24px pt-0 px-16px tbox-prent" style="overflow:auto; height:900px;">
				<!-- 목록 컨텐츠 영역 -->
				<div id="appMenuGrid" class="pb-8px"></div>
			</div>
		</div>
	</div>
</div>
<!-- CENTER CONTENT END   >> ---------->
<%-- ############################# 내용 (종료) ############################# --%>
