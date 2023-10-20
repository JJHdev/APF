<%--
*******************************************************************************
***    명칭: modalSurveyForm.jsp
***    설명: 설문관리 - 신규등록 화면
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.07.13     KYW        First Coding.
*******************************************************************************
--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="app" uri="/WEB-INF/tld/app.tld"%>
<%@ taglib prefix="f" uri="/WEB-INF/tld/f.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<%-- ############################ 내용 (시작) ############################ --%>

<form:form commandName="model" id="registForm" name="registForm" method="post" enctype="multipart/form-data"
	onsubmit="return false;" class="h-100 overflow-auto">
	<form:hidden path="mode"    /> 		<!-- 처리모드 -->
	<form:hidden path="srvyNo"    /> 	<!-- 설문번호 -->
	<div class="contentBox w-100">
		<div class="box mb-24px">
			<div class="box-in-box">
				<div class="input-grid-1 ">
					<div class="row">
						<div class="col-12">
							<div class="form-area-box1">
								<label>설문제목<em>*</em></label>
								<div class="ele-icon-box">
									<input type="text" id="srvyCn" name="srvyCn" class="" value='<c:out value="${model.srvyCn }" />' />
								</div>
							</div>
						</div>
						<div class="col-3">
							<div class="form-area-box1">
								<label>문항수<em>*</em></label>
								<div class="ele-icon-box">
									<select name="qtNum" id="qtNum" class="srvy-info">
										<c:forEach begin="1" end="20" var="qNum" step="1">
											<option value="${qNum}" class="text-center" <c:if test="${model.qtNum == qNum }">selected</c:if> >
												<c:out value="${qNum}"/>
											</option>
										</c:forEach>
									</select> 
									<i class="icon-angle-down"></i>
								</div>
		
							</div>
						</div>
						<div class="col-9">
							<div class="form-area-box1">
								<label>설문대상<em>*</em></label>
								<div class="ele-icon-box">
									<select id="srchTrgtModal" name="srvyTrgtCd" class="" ></select> 
									<i class="icon-angle-down"></i>
								</div>
							</div>
						</div>
						<div class="col-12">
							<div class="form-area-box1">
								<label>설문기간<em>*</em></label>
								<div class="day">
									<div class="row">
										<div class="col">
											<div class="ele-icon-box">
												<i class="icon-edit"></i> 
												<input type="text" name="srvyBgngYmd" id="srvyBgngYmd" value='<c:out value="${model.srvyBgngYmd }"/>' placeholder="날짜입력" class="datepicker-input" readonly> 
												<a href="#" class="icon-calendar"></a>
											</div>
										</div>
										<div class="col wave">
											<span>~</span>
										</div>
										<div class="col">
											<div class="ele-icon-box">
												<i class="icon-edit"></i> 
												<input type="text" name="srvyEndYmd" id="srvyEndYmd" value='<c:out value="${model.srvyEndYmd }"/>'  placeholder="날짜입력" class="datepicker-input" readonly> 
												<a href="#" class="icon-calendar"></a>
											</div>
										</div>
									</div>
								</div>
		
							</div>
						</div>
						<input type="hidden" name="qitemTypeArr" />
						<input type="hidden" name="qitemCnArr" />
						<input type="hidden" name="artclCnArr" />
						
						<%-- <div class="col-12">
							<div class="form-area-box1">
								첨부파일영역
								<div id="attachFile"></div>
							</div>
						</div> --%>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="modal-footer mt-3">
		<div class="bottom-btn-box">
			<div class="row">
				<div class="col">
					<a href="#" class="btn-combi-3 bs-m " onclick="P_MODAL.doClose();">
						<i class="icon-times"></i>
						취소
					</a>
				</div>
				<div class="col">
					<a href="#" class="btn-primary bs-m" id="btnSave">
						<i class="icon-edit"></i>
						저장
					</a>
				</div>
			</div>
		</div>
	</div>

</form:form>

<!-- CENTER CONTENT END << ---------->
<%-- ############################ 내용 (종료) ############################ --%>

