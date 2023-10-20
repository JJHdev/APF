<%--
*******************************************************************************
***    명칭: modalNoticeForm.jsp
***    설명: 공지사항 - 등록/수정 모달팝업 화면
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.04.04    LSH        First Coding.
***    1.1      2023.05.03    LSH        디자인 적용
***    1.2      2023.06.01    LSH        모달팝업 형태로 변경
***    1.3      2023.06.27    J H        작업 완료
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
		<form:hidden path="pstNo"        /><%-- 게시물번호 --%>
		<form:hidden path="bbsSeCd"      /><%-- 게시물구분 --%>
		<form:hidden path="mode"         /><%-- 처리모드 --%>
			<!-- 컨텐츠시작 -->
			<div class="input-grid-1">
				<div class="row">
					<div class="col-12">
						<div class="form-area-box1">
							<label>제목<em></em></label>
							<div class="ele-icon-box">
							    <form:input path="pstTtl" maxLength="100"/>
							</div>
						</div>
					</div>
					<div class="col-12">
						<div class="form-area-box1-input2 ">
							<label>분류<em></em></label>
							<div class="ele-icon-box" id="appPstClsfCd" data-value="<c:out value='${model.pstClsfCd}'/>"></div>
						</div>
					</div>
					
					<div class="col-2">
						<div class="form-area-box1-input2 ">
							<label>상단고정</label>
							<div class="ele-icon-box" id="appFixingYn" data-value="<c:out value='${model.fixingYn}'/>"></div>
						</div>
					</div>
					<div class="col-10">
						<div class="form-area-box1">
							<label>상단고정 기간</label>
							<div class="day">
								<div class="row">
									<div class="col">
										<div class="ele-icon-box">
											<input type="text" name="fixingBgngYmd" id="fixingBgngYmd" value="<c:out value='${model.fixingBgngYmd}'/>" maxlength="10" class="datepicker-input">
											<a href="javascript:void(0)" class="icon-calendar"></a>
										</div>
									</div>
									<div class="col wave"><span>~</span></div>
									<div class="col">
										<div class="ele-icon-box">
											<input type="text" name="fixingEndYmd" id="fixingEndYmd" value="<c:out value='${model.fixingEndYmd}'/>" maxlength="10" class="datepicker-input">
											<a href="javascript:void(0)" class="icon-calendar"></a>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
	
					<div class="col-2">
						<div class="form-area-box1-input2 ">
							<label>팝업여부<em></em></label>
							<div class="ele-icon-box" id="appPopupYn" data-value="<c:out value='${model.popupYn}'/>"></div>
						</div>
					</div>
					<div class="col-10">
						<div class="form-area-box1">
							<label>팝업 게시기간<em></em></label>
							<div class="day">
								<div class="row">
									<div class="col">
										<div class="ele-icon-box">
											<input type="text" name="pstgBgngYmd" id="pstgBgngYmd" value="<c:out value='${model.pstgBgngYmd}'/>" maxlength="10" class="datepicker-input">
											<a href="javascript:void(0)" class="icon-calendar"></a>
										</div>
									</div>
									<div class="col wave"><span>-</span></div>
									<div class="col">
										<div class="ele-icon-box">
											<input type="text" name="pstgEndYmd" id="pstgEndYmd" value="<c:out value='${model.pstgEndYmd}'/>" maxlength="10" class="datepicker-input">
											<a href="javascript:void(0)" class="icon-calendar"></a>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-6">
						<div class="form-area-box1">
							<label>팝업창 가로</label>
							<div class="ele-icon-box">
							    <form:input path="popupAr" />
							</div>
						</div>
					</div>
					<div class="col-6">
						<div class="form-area-box1">
							<label>팝업창 세로</label>
							<div class="ele-icon-box">
							    <form:input path="popupHg" />
							</div>
						</div>
					</div>
					<div class="col-12">
						<div class="form-area-box1">
							<label>첨부파일</label>
							<%-- 첨부파일영역 --%>
							<div id="attachFile"></div>
						</div>
					</div>
					<div class="col-12">
						<div class="form-area-box1">
							<label>내용<em></em></label>
							<div class="ele-icon-box h-auto" id="ckEdit_pstCn">
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

