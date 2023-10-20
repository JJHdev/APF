<%--
*******************************************************************************
***    명칭: modalDscsnForm.jsp
***    설명: 운영관리-제출서류 등록/수정 모달팝업 화면
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***	   1.0 2023.06.27   J H    작업완료.
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
	<div class="box-max-height">
		<!-- FORM START -->
		<form:form commandName="model" id="registForm" name="registForm" method="post" enctype="multipart/form-data" onsubmit="return false;" class="box-max-height" style="max-height: 95% !important;">
			<form:hidden path="sn"	 		 /><%-- 상담신청 번호  --%>
			<form:hidden path="mode"		 /><%-- 처리모드 --%>
			<div class="input-grid-1 box-in-box">
				<div class="row">
					
					<div class="col-6">
						<div class="form-area-box1">
							<label>상담 일자</label>
							<div class="day">
								<div class="row">
									<div class="col">
										<div class="ele-icon-box">
											<form:input  path="dscsnYmd" maxlength="10" class="datepicker-input"/>
											<a href="javascript:void(0)" class="icon-calendar"></a>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					
					<div class="col-6" id="appDscsnCntrCd">
						<div class="form-area-box1">
							<label>상담 센터</label>
							<div class="ele-icon-box">
								<form:select path="dscsnCntrCd" data-value="${model.dscsnCntrCd}"></form:select>
								<i class="icon-angle-down right"></i>
							</div>
						</div>
					</div>
					<div class="col-6" id="appDscsnMthdCd">
						<div class="form-area-box1">
							<label>상담 종류</label>
							<div class="ele-icon-box">
								<form:select path="dscsnMthdCd" data-value="${model.dscsnMthdCd}"></form:select>
								<i class="icon-angle-down right"></i> 
							</div>
						</div>
					</div>
					<div class="col-6">
						<div class="form-area-box1">
							<label>상담자</label>
							<div class="ele-icon-box">
								<form:input path="cnslrNm" style="" />
							</div>
						</div>
					</div>
					<div class="col-6">
						<div class="form-area-box1">
							<label>경영체명</label>
							<div class="ele-icon-box">
								<form:input path="bzentyNm" style=""/>
							</div>
						</div>
					</div>
					<div class="col-6">
						<div class="form-area-box1">
							<label>사업자번호</label>
							<div class="ele-icon-box">
								<form:input path="brno"     style="" />
							</div>
						</div>
					</div>
					<div class="col-6">
						<div class="form-area-box1">
							<label>대표자명</label>
							<div class="ele-icon-box">
								<form:input path="rprsvNm" style=""  />
							</div>
						</div>
					</div>
					<div class="col-6">
						<div class="form-area-box1">
							<label>성별</label>
							<div class="ele-icon-box">
								<div class="ele-icon-box" id="appSexdstn" data-value="<c:out value='${model.sexdstn}'/>"></div>
							</div>
						</div>
					</div>
					
					<div class="col-6">
						<div class="form-area-box1">
							<label>대표자 생년월일</label>
							<div class="day">
								<div class="row">
									<div class="col">
										<div class="ele-icon-box">
											<form:input  path="brdt" maxlength="10" class="datepickerNotTomorrow-input"/>
											<a href="javascript:void(0)" class="icon-calendar"></a>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					
					<div class="col-6">
						<div class="form-area-box1">
							<label>담당자명</label>
							<div class="ele-icon-box">
								<form:input path="picNm" id="picNm" style=""  />
							</div>
						</div>
					</div>
					<div class="col-6">
						<div class="form-area-box1">
							<label>부서/직책</label>
							<div class="ele-icon-box">
								<form:input path="picDeptNm" style=""  />
							</div>
						</div>
					</div>
					<div class="col-6">
						<div class="form-area-box1">
							<label>연락처</label>
							<div class="ele-icon-box">
								<form:input path="picTelno"  style="" />
							</div>
						</div>
					</div>
					
					<div class="col-6">
						<div class="form-area-box1">
							<label>이메일</label>
							<div class="ele-icon-box">
								<form:input path="picEmlAddr" style=""  />
							</div>
						</div>
					</div>
					
					<div class="col-6">
						<div class="form-area-box1">
							<label>설립 연도</label>
							<div class="day">
								<div class="row">
									<div class="col">
										<div class="ele-icon-box">
											<form:input  path="fndnYmd" maxlength="10" class="datepickerYear-input"/>
											<a href="javascript:void(0)" class="icon-calendar"></a>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					
					<div class="col-6">
						<div class="form-area-box1">
							<label>임직원수(명)</label>
							<div class="ele-icon-box">
								<form:input  path="empCntForm" style=""  />
								<form:hidden path="empCnt" style=""  />
							</div>
						</div>
					</div>
					
					<div class="col-6" id="appBzentyTypeCd">
						<div class="form-area-box1">
							<label>경영체 유형</label>
							<div class="ele-icon-box">
								<form:select path="bzentyTypeCd" data-value="${model.bzentyTypeCd}"></form:select>
								<i class="icon-angle-down right"></i>
							</div>
						</div>
					</div>
					
					<div class="col-12">
						<div class="form-area-box1-input2 ">
							<label>사업분야</label>
							<div class="ele-icon-box justify-content-between" id="appBizFldBox" data-value="<c:out value='${model.bizFld}'/>"></div>
						</div>
					</div>
					<div class="col-4">
						<div class="form-area-box1">
							<label>투자분야</label>
							<div class="ele-icon-box">
								<form:input path="invtFldNm" style=""  />
							</div>
						</div>
					</div>
					<div class="col-4">
						<div class="form-area-box1">
							<label>업종구분</label>
							<div class="ele-icon-box">
								<form:input path="tpbizSeNm" style=""  />
							</div>
						</div>
					</div>
					<div class="col-4">
						<div class="form-area-box1">
							<label>산업구분</label>
							<div class="ele-icon-box">
								<form:input path="industSeNm" id="industSeNm" style=""  />
							</div>
						</div>
					</div>
					
					<div class="col-6">
						<div class="form-area-box1">
							<label>전년도 매출액(백만원)</label>
							<div class="ele-icon-box">
								<form:input  path="slsAmtForm" style=""  />
								<form:hidden path="slsAmt" style=""  />
							</div>
						</div>
					</div>
					
					<div class="col-6">
						<div class="form-area-box1">
							<label>홈페이지</label>
							<div class="ele-icon-box">
								<form:input path="hmpgAddr" style=""  />
							</div>
						</div>
					</div>
					
					<div class="col-12">
						<div class="form-area-box1">
							<label>본사소재지</label>
							<div class="ele-icon-box">
								<form:input path="lctnAddr1" style=""  />
								
							</div>
						</div>
					</div>
					
					<div class="col-12">
						<div class="form-area-box1">
							<label>대표공장 소재지</label>
							<div class="ele-icon-box">
								<form:input path="lctnAddr2" style=""  />
								
							</div>
						</div>
					</div>
					
					<div class="col-12">
						<div class="form-area-box1">
							<label>사업내용(주 생산품)</label>
							<div class="ele-icon-box">
								<form:textarea path="bizCn" class="" style="height: 86px;" ></form:textarea>
							</div>
						</div>
					</div>
					
					<div class="col-12">
						<div class="form-area-box1">
							<label>상담내용</label>
							<div class="ele-icon-box">
								<form:textarea path="dscsnCn" class="" style="height: 86px;" ></form:textarea>
							</div>
						</div>
					</div>
				</div>
			</div>
		</form:form>
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
<!-- FORM END -->


<%-- ############################ 내용 (종료) ############################ --%>
