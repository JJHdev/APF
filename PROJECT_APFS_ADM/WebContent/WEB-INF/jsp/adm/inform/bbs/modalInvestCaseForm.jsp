<%--
*******************************************************************************
***    명칭: modalInvestCaseForm.jsp
***    설명: 게시판관리-우수투자사례 화면
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.06.09    JH        First Coding.
***    1.1      2023.06.27    JH        작업 완료.
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
	<form:form commandName="model" id="registForm" name="registForm" method="post" enctype="multipart/form-data" onsubmit="return false;">
		<form:hidden path="pstNo"   /><%-- 게시물번호 --%>
		<form:hidden path="bbsSeCd" /><%-- 게시물구분 --%>
		<form:hidden path="mode"    /><%-- 처리모드 --%>
		<!-- 컨텐츠시작 -->
		<div class="input-grid-1">
			<div class="row">
				<div class="col-12">
					<div class="form-area-box1">
						<label>제목<em></em></label>
						<div class="ele-icon-box">
						    <form:input path="pstTtl" maxLength="70"/>
						</div>
					</div>
				</div>
				<div class="col-12">
					<div class="form-area-box1">
						<label>썸네일 이미지</label>
						<%-- 첨부파일영역 --%>
						<div id="thumAttachFile"></div>
					</div>
				</div>
				<div class="col-12">
					<div class="form-area-box1">
						<label>작성자</label>
						<div class="ele-icon-box">
							<c:out value='${model.gsUserNm}'/>
						</div>
					</div>
				</div>
				<div class="col-12">
					<div class="form-area-box1">
						<label>첨부 파일</label>
						<%-- 첨부파일영역 --%>
						<div id="attachFile"></div>
					</div>
				</div>
				<div class="col-12">
					<div class="form-area-box1">
						<label>동영상URL</label>
						<div class="ele-icon-box">
						   <form:input path="pstLinkUrl" htmlEscape="false" maxLength="100"/>
						</div>
					</div>
				</div>
				<div class="col-12">
					<div class="form-area-box1">
						<label>에디터<em></em></label>
						<div class="ele-icon-box h-auto">
							<form:textarea path="pstCn" style="height:400px;"/>
						</div>
					</div>
				</div>
			</div>
		<!-- 컨텐츠종료 -->
		</div>
		<div class="bottom-btn-box">
			<div class="row">
				<div class="col flex-grow-0"><a id="btnCancel" href="javascript:void(0)" class="bs-l btn-combi-3"><i class="icon-times"></i> 취소</a></div>
				<div class="col flex-grow-0"><a id="btnSave"   href="javascript:void(0)" class="bs-l btn-primary px-14px"><i class="icon-copy-alt"></i> 저장</a></div>
			</div>
		</div>
	</form:form>
</div>

<%-- ############################ 내용 (종료) ############################ --%>
