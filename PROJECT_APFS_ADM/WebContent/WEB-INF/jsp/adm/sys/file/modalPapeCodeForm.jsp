<%--
*******************************************************************************
***    명칭: modalPapeCodeForm.js.jsp
***    설명: 지원사업관리-신청현황관리 등록/수정 모달팝업 화면
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***  1.0    2023.07.01    LSH   First Coding.
***  1.1    2023.07.21    JH   작업 완료.
***  1.1    2023.08.04    JH   파일 경로이동및 다운로드기능 추가.
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
<div class="w-100">
	<!-- FORM START -->
	<form:form commandName="model" id="registForm" name="registForm" method="post" enctype="multipart/form-data" onsubmit="return false;" class="box-max-height" style="max-height: 95% !important;">
		<form:hidden path="mode"		 			/><%-- 처리모드 --%>
		
			<!-- 컨텐츠시작 -->
			<div class="box mb-16px">
				<div class="input-grid-1 box-in-box">
					<div class="row">
						<div class="col-12" id="appUpDcmntCd">
							<div class="form-area-box1">
								<label>상위코드<em></em></label>
								<div class="ele-icon-box">
									<form:select path="upDcmntCd" data-value="${model.upDcmntCd}"></form:select>
									<i class="icon-angle-down right"></i>
								</div>
							</div>
						</div>
						
						<div class="col-12">
							<div class="form-area-box1">
								<label>서류코드<em></em></label>
								<div class="ele-icon-box">
									<form:input path="dcmntCd"  data-value="<c:out value='${model.dcmntCd}'/>"/>
								</div>
							</div>
						</div>
						
						<div class="col-12">
							<div class="form-area-box1">
								<label>서류명<em></em></label>
								<div class="ele-icon-box">
									<form:input path="dcmntNm"  data-value="<c:out value='${model.dcmntNm}'/>"/>
								</div>
							</div>
						</div>
						
						<div class="col-12">
							<div class="form-area-box1">
								<label>순서</label>
								<div class="ele-icon-box">
									<form:input path="cdOrdr" data-value="<c:out value='${model.cdOrdr}'/>"/>
								</div>
							</div>
						</div>
						
						<div class="col-12">
							<div class="form-area-box1">
								<label>사용 여부<em></em></label>
								<div class="ele-icon-box">
									<div class="ele-icon-box" id="appUseYn" data-value="<c:out value='${model.useYn}'/>"></div>
								</div>
							</div>
						</div>
						
						<div class="col-12">
							<div class="form-area-box1">
								<label>다운로드 대상<em></em></label>
								<div class="ele-icon-box">
									<div class="ele-icon-box" id="appDwnldTrgtYn" data-value="<c:out value='${model.dwnldTrgtYn}'/>"></div>
								</div>
							</div>
						</div>
						<div class="col-12">
							<div class="form-area-box1">
								<label>첨부파일</label>
								<div id="attachFile"></div>
							</div>
						</div>
						<c:if test="${not empty model.fileNm}">
							<div class="col-12">
								<div class="form-area-box-input">
									<label>첨부파일</label>
									<div class="ele-icon-box">
										<%-- 첨부파일영역 --%>
										<div id="attachFile2"></div>
									</div>
								</div>
							</div>
						</c:if>
					</div>
				</div>
			</div>
			<!-- 컨텐츠종료 -->
			<div class="bottom-btn-box">
				<div class="row">
					<c:if test="${model.mode eq 'I'}">
						<div class="col flex-grow-0"><a id="btnCancel" href="javascript:void(0)" class="bs-l btn-combi-3"><i class="icon-times"></i>취소</a></div>
						<div class="col flex-grow-0"><a id="btnSave"   href="javascript:void(0)" class="bs-l btn-primary px-14px"><i class="icon-copy-alt"></i>저장</a></div>
					</c:if>
					<c:if test="${model.mode eq 'U'}">
						<div class="col flex-grow-0"><a id="btnSave"   href="javascript:void(0)" class="bs-l btn-primary px-14px"><i class="icon-copy-alt"></i>수정</a></div>
						<div class="col flex-grow-0"><a id="btnRemove" href="javascript:void(0)" class="bs-l btn-combi-2 w-100"><i class="icon-floppy-disk"></i>삭제</a></div>
					</c:if>
				</div>
			</div>
	</form:form>
</div>
<!-- FORM END -->

<%-- ############################ 내용 (종료) ############################ --%>
