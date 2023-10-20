<%--
*******************************************************************************
***    명칭: openInvtSprt.jsp
***    설명: 지원사업관리-세부지원사업관리 화면
***
***    -----------------------------    Modified Log   ------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.05.03      LHB     First Coding.
***    1.1      2023.06.07      LHB     파일명 listInvtSprt -> openInvtSprt 변경
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

<div class="tabmenu-1">
	<form id="searchForm" name="searchForm" method="post" onsubmit="return false;">
		<input name="sprtAplySeCd" id="sprtAplySeCd_S" type="hidden" />
		<ul class="">
			<li>
				<a href="#" class="active">투자유치 후 지원사업</a>
			</li>
			<li>
				<a href="#;" class="">농식품크라우드펀딩 지원사업</a>
			</li>
			
		</ul>
	</form>
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
