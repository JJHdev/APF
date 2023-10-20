<%--
*******************************************************************************
***    명칭: modalIrOpnnForm.jsp
***    설명: 투자정보관리-IR검토의견서관리 등록/수정 모달팝업 화면
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.06.07      LHB     First Coding.
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
<form:form commandName="model" id="registForm" name="registForm" method="post" enctype="multipart/form-data" onsubmit="return false;">
	<form:hidden path="evntNo"	 /><%-- 투자설명회번호 --%>
	<form:hidden path="evntType" /><%-- 투자설명회번호 --%>
	<form:hidden path="mode"	 /><%-- 처리모드 --%>
	<form:hidden path="page"	 /><%-- 페이지 --%>
	
	<div class="w-100">
		<!-- 컨텐츠시작 -->
		<div class="tabmenu2 border-0 m-0 mb-32px rounding-1" style="border-radius: 0;">
			<ul class="">
				<li class="evntType">
					<a href="#" class="<c:if test='${model.evntType eq "INVT"}'>active</c:if>">투자자별</a>
				</li>
				<li class="evntType">
					<a href="#" class="<c:if test='${model.evntType eq "ENT" }'>active</c:if>">경영체별</a>
				</li>
			</ul>
		</div>
		<div class="box-max-height pt-8px">
			<div class="input-grid-1">
				<div class="row">
					<div class="col-12">
						<div class="form-area-box1">
							<label>행사명</label>
							<div class="ele-icon-box">
								<form:input path="evntNm" style="" readonly="true"/>
							</div>
						</div>
					</div>
					<div class="col-12">
						<div class="form-area-box1">
							<label>행사일자</label>
							<div class="ele-icon-box">
								<form:input path="evntBgngYmd" placeholder="설립일을 선택하세요" class="" readonly="true"/>
								<a href="#" class="icon-calendar"></a>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="box mb-24px" style="max-height: 500px;">
				<div class="eletabletop1" data-v-2eb60c26="">
					<div class="row">
						<div class="col">
							<p class="txt2">
								<i class="icon-note"></i>참여 투자자
								<span class="ml-4px text-primary" id="appGridResult-ent"></span>
							</p>
						</div>
						<div class="col element">
						</div>
					</div>
				</div>
				<div class="box-in-box pb-16px">
					<div class="overtable">
						<div class="" style="height: 300px;">
							<!-- 목록 컨텐츠 영역 -->
							<table id="appGrid-ent" class="easyui-datagrid"></table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</form:form>
<!-- FORM END -->

<%-- ############################ 내용 (종료) ############################ --%>
