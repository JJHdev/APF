<%--
*******************************************************************************
***    명칭: modalSprtUldForm.jsp
***    설명: 운영관리 - 경영체 데이터 업로드 VIEW 모달팝업 화면
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.07.13      LHB     First Coding.
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

<!-- FORM START -->
<div class="w-100 row">
	<form:form commandName="model" id="viewForm" name="viewForm" method="post" enctype="multipart/form-data" onsubmit="return false;">
	<form:hidden path="uldNo" />
		<!-- 컨텐츠시작 -->
		<div class="row mb-16px">
			<div class="col-6">
				<div class="form-area-box1">
					<label>파일명</label>
					<div class="ele-icon-box">
						<i class="icon-paperclip"></i><form:input path="fileNm" class="app-pointer" readonly="true"/>
					</div>											
				</div>
			</div>
			<div class="col-6">
				<div class="form-area-box1">
					<label>작성자</label>
					<div class="ele-icon-box">
						<form:input path="rgtrNmNo" readonly="true"/>
					</div>											
				</div>
			</div>
		</div>
		
		<div id="invstr-div" class="box" style="margin-bottom: 15px;">
			<div class="eletabletop1" data-v-2eb60c26="">
				<div class="row">
					<div class="col">
						<p class="txt2">
							<i class="icon-note"></i>경영체 목록
						</p>
					</div>
				</div>
			</div>
			<div id="appGrid2" class="pb-8px"></div>
	
			<div class="row justify-content-center">
				<div class="col flex-grow-0 white-space-nowrap">
					<!-- 목록 페이징 영역 -->
					<div class="mb-0 p-0 paging-box">
						<ul id="appGridPagination2"></ul>
					</div>
				</div>
			</div>
			
			<div class="modal-footer mt-16px">
				<div class="bottom-btn-box">
					<div class="row">
						<div class="col">
							<a id="btnDelete" href="#void" class="bs-l btn-black"><i class="icon-trash"></i>삭제</a>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- 컨텐츠종료 -->
	</form:form>
	<!-- FORM END -->
</div>

<%-- ############################ 내용 (종료) ############################ --%>
