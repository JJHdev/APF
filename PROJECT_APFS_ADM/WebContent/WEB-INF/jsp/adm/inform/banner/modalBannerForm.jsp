<%--
*******************************************************************************
***    명칭: modalBannerForm.jsp
***    설명: 운영관리-배너관리 등록/수정 모달팝업 화면
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.06.09      LHB     First Coding.
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
	<form:hidden path="bannerNo"	/><%-- 배너번호 --%>
	<form:hidden path="mode"		/><%-- 처리모드 --%>
	
	<div class="w-100">
		<!-- 컨텐츠시작 -->
		<div class="box mb-16px">
			<div class="input-grid-1 box-in-box">
				<div class="row">
					<div class="col-12">
						<div class="form-area-box1">
							<label>배너명<em></em></label>
							<div class="ele-icon-box">
								<form:input path="bannerNm" htmlEscape="false" maxLength="100"/>
							</div>											
						</div>
					</div>
					<div class="col-12">
						<div class="form-area-box1">
							<label>배너설명</label>
							<div class="ele-icon-box">
								<form:textarea path="bannerExpln" maxLength="1000" style="min-height: 300px;"/>
							</div>
						</div>
					</div>
					<div class="col-12">
						<div class="form-area-box1">
							<label>링크URL</label>
							<div class="ele-icon-box">
								<form:input path="bannerUrl" htmlEscape="false" maxLength="500" />
							</div>											
						</div>
					</div>
					<div class="col-12">
						<div class="form-area-box1">
							<label>게시기간<em></em></label>
							<div class="day">
								<div class="row">
									<div class="col">
										<div class="ele-icon-box">					
											<form:input path="pstgBgngYmd" placeholder="시작일을 선택하세요" class="datepicker-input"/>
											<i class="icon-calendar"></i>										
										</div>
									</div>
									<div class="col wave">
										<span>~</span>
									</div>
									<div class="col">
										<div class="ele-icon-box">					
											<form:input path="pstgEndYmd" placeholder="종료일을 선택하세요" class="datepicker-input"/>
											<i class="icon-calendar"></i>										
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-12">
						<div class="form-area-box1-input2 ">
							<label>노출 여부<em></em></label>
							<div class="ele-icon-box" id="appUseYn" data-value="<c:out value='${model.useYn}'/>"></div>
						</div>
					</div>
					<div class="col-12">
						<div class="form-area-box1">
							<label>라벨이미지<em></em></label>
							<div class="input-btn" id="attachFile"></div>							
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
				<div class="col flex-grow-0"><a id="btnSave"   href="javascript:void(0)" class="bs-l btn-primary px-14px"><i class="icon-copy-alt"></i>저장</a></div>
			</div>
		</div>
	</div>
</form:form>
<!-- FORM END -->

<%-- ############################ 내용 (종료) ############################ --%>
