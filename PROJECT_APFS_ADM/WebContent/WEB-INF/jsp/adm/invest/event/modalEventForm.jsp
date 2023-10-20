<%--
*******************************************************************************
***    명칭: modalEventForm.jsp
***    설명: 투자정보관리 - 투자설명회등록 등록/수정 모달팝업 화면
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.06.02      LHB     First Coding.
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
	<form:hidden path="evntNo"	/><%-- 투자설명회번호 --%>
	<form:hidden path="mode"	/><%-- 처리모드 --%>
	
	<div class="w-100">
		<!-- 컨텐츠시작 -->
		<div class="box mb-16px">
			<div class="input-grid-1">
				<div class="row">
					<div class="col-12">
						<div class="form-area-box1">
							<label>행사명<em>*</em></label>
							<div class="ele-icon-box">
								<form:input path="evntNm"/>
							</div>
						</div>
					</div>
					<div class="col-12">
						<div class="form-area-box1">
							<label>행사일자<em>*</em></label>
							<div class="ele-icon-box">
								<form:input path="evntBgngYmd" placeholder="행사일자를 선택하세요" class="datepicker-input"/>
								<a href="#" class="icon-calendar"></a>
							</div>
						</div>
					</div>
					<div class="col-12">
						<div class="form-area-box1-input2 ">
							<label>플랫폼 게시<em></em></label>
							<div class="ele-icon-box" id="appUseYn" data-value="<c:out value='${model.useYn}'/>"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- 컨텐츠종료 -->
		<div class="bottom-btn-box">
			<div class="row">
				<div class="col flex-grow-0"><a id="btnCancel" href="javascript:void(0)" class="bs-l btn-combi-3"><i class="icon-times"></i>취소</a></div>
				<c:if test="${model.mode eq 'U'}">
					<div class="col flex-grow-0"><a id="btnRemove" href="javascript:void(0)" class="bs-l btn-combi-2 w-100"><i class="icon-floppy-disk"></i>삭제</a></div>
				</c:if>
				<div class="col flex-grow-0"><a id="btnSave"   href="javascript:void(0)" class="bs-l btn-primary px-14px"><i class="icon-copy-alt"></i> 저장</a></div>
			</div>
		</div>
	</div>
</form:form>
<!-- FORM END -->

<!-- MODAL CONTENT END << ----------->

<%-- ############################ 내용 (종료) ############################ --%>
