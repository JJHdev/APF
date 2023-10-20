<%--
*******************************************************************************
***    명칭: modalPbancForm.jsp
***    설명: 운영관리-사업공고관리 등록/수정 모달팝업 화면
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
<form:form commandName="model" id="registForm" name="registForm" method="post" enctype="multipart/form-data" onsubmit="return false;" class="box-max-height" style="max-height: 95% !important;">
	<form:hidden path="bizPbancNo"	/><%-- 배너번호 --%>
	<form:hidden path="mode"		/><%-- 처리모드 --%>
	
	<div class="w-100">
		<!-- 컨텐츠시작 -->
		<c:choose>
			<c:when test="${model.mode eq 'I' or model.mode eq 'U'}">
				<!-- 상세보기 -->
				<div class="detail-input-box p-16px">
					<div class="box-max-height">
						<div class="box mb-24px">
							<div class="eletabletop1" data-v-2eb60c26="">
								<div class="row">
									<div class="col">
										<p class="txt2">
											<i class="icon-note"></i>주관기관 정보
										</p>
									</div>
								</div>
							</div>
							<div class="box-in-box">
								<div class="input-grid-1">
									<div class="row">
										<div class="col-9">
											<div class="form-area-box1">
												<label>등록기관명<em></em></label>
												<div class="ele-icon-box">
													<form:select path="crdnsBzentyNo" data-value="${model.crdnsBzentyNo}"></form:select>
													<i class="icon-angle-down"></i>
												</div>
											</div>
										</div>
										<div class="col-4">
											<div class="form-area-box1">
												<label>사업담당부서<em></em></label>
												<div class="ele-icon-box">
													<form:input path="tkcgDeptNm" style="" />
												</div>
											</div>
										</div>
										<div class="col-4">
											<div class="form-area-box1">
												<label>사업담당자<em></em></label>
												<div class="ele-icon-box">
													<form:input path="picNm" id="picNm" style="" />
													<i class="icon-times-circle F text-ele-TextDisable"></i>
												</div>
											</div>
										</div>
										<div class="col-4">
											<div class="form-area-box1">
												<label>담당자 연락처<em></em></label>
												<form:hidden path="picTelno" />
												<div class="day">
													<div class="row">
														<div class="col"><div class="ele-icon-box"><select id="picTelno1" name="picTelno1" type="text" maxlength="4"/></select><i class="icon-angle-down right"></i></div></div>
														<div class="col wave"><span>-</span></div>
														<div class="col"><div class="ele-icon-box"><input  id="picTelno2" name="picTelno2" type="text" maxlength="4"/></div></div>
														<div class="col wave"><span>-</span></div>
														<div class="col"><div class="ele-icon-box"><input  id="picTelno3" name="picTelno3" type="text" maxlength="4"/></div></div>
													</div>
												</div>
												<div class="bottom-lable">
													<div class="row">
														<div class="col"><p class="fs-10px text-red">개인 휴대 전화번호 입력 금지</p></div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="box mb-24px">
							<div class="eletabletop1" data-v-2eb60c26="">
								<div class="row">
									<div class="col">
										<p class="txt2">
											<i class="icon-note"></i>사업정보
										</p>
									</div>
								</div>
							</div>
							<div class="box-in-box">
								<div class="input-grid-1">
									<div class="row">
										<div class="col-6">
											<div class="form-area-box1">
												<label>공고명<em></em></label>
												<div class="ele-icon-box">
													<form:input path="bizPbancNm" style="" />
												</div>											
											</div>
										</div>
										<div class="col-6">
											<div class="form-area-box1">
												<label>안내URL</label>
												<div class="ele-icon-box">
													<form:input path="bizGuidanceUrl" style="" />
												</div>											
											</div>
										</div>
										<div class="col-12">
											<div class="form-area-box1-input2">
												<label>접수기간<em></em></label>
												<div class="ele-icon-box justify-content-between" id="appRcptSeCd" data-value="${model.rcptSeCd}" style="min-height: 38px;"></div>
											</div>
										</div>
										<div class="col-12" id="rcptBox">
											<div class="form-area-box1">
												<label for="rcptSeCd3" class="d-inline-flex">기간 입력</label>
												<div class="flex-grow-0 white-space-nowrap">
													<div class="d-inline-flex mx-0 row w-100 white-space-nowrap">
														<input type="hidden" name="rcptBgngDt" id="rcptBgngDt" />
														<div class="col flex-b100px form-label-box px-4px white-space-nowrap">
															<div class="box pb-0">
															<div class="area select">												
																<div class="input-element icons-ver">
																	<form:input path="rcptBgngYmd" class="datepicker-custom" style="font-size: 11px; padding-left: 7px;" />
																	<i class="icon-calendar right"></i>
																</div>
															</div>
															</div>
														</div>
														<div class="col flex-b80px form-label-box px-4px white-space-nowrap">
															<div class="box pb-0">
															<div class="area select">												
																<div class="input-element icons-ver">
																	<select name="rcptBgngTm" id="rcptBgngTm">
																		<c:forEach var="item" begin="0" end="23" step="1">
																			<fmt:formatNumber var="time" minIntegerDigits="2" value="${item}" type="number"/>
																			<option value="${time}" <c:if test="${model.rcptBgngTm eq time}">selected</c:if>><c:out value="${item}시"/></option>
																		</c:forEach>
																	</select>
																	<i class="icon-angle-down right"></i>
																</div>
															</div>
															</div>
														</div>
														<div class="col flex-grow-0 px-4px white-space-nowrap">
															~
														</div>
														<input type="hidden" name="rcptEndDt" id="rcptEndDt" />
														<div class="col flex-b100px form-label-box px-4px white-space-nowrap">
															<div class="box pb-0">
															<div class="area select">												
																<div class="input-element icons-ver">
																	<form:input path="rcptEndYmd" class="datepicker-custom" style="font-size: 11px; padding-left: 7px;" />
																	<i class="icon-calendar right"></i>
																</div>
															</div>
															</div>
														</div>
														<div class="col flex-b80px form-label-box px-4px white-space-nowrap">
															<div class="box pb-0">
																<div class="area select">												
																	<div class="input-element icons-ver">
																		<select name="rcptEndTm" id="rcptEndTm">
																			<c:forEach var="item" begin="0" end="23" step="1">
																				<fmt:formatNumber var="time" minIntegerDigits="2" value="${item}" type="number"/>
																				<option value="${time}" <c:if test="${model.rcptEndTm eq time}">selected</c:if>><c:out value="${item}시"/></option>
																			</c:forEach>
																		</select>
																		<i class="icon-angle-down right"></i>
																	</div>
																</div>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
										<div class="col-12">
											<div class="form-area-box1-input2">
												<label>사업분야<em></em></label>
												<form:hidden path="bizFld" />
												<form:hidden path="bizFldNm" />
												<div class="ele-icon-box justify-content-between" id="appBizFld" data-value="${model.bizFld}"></div>
											</div>
										</div>
										<div class="col-12">
											<div class="form-area-box1-input2">
												<label>사업대상<em></em></label>
												<form:hidden path="bizTrgt" />
												<form:hidden path="bizTrgtNm" />
												<div class="ele-icon-box justify-content-between" id="appBizTrgt" data-value="${model.bizTrgt}"></div>
											</div>
										</div>
										<div class="col-12">
											<div class="form-area-box1-input2 ">
												<label>사업대상연령<em></em></label>
												<div class="ele-icon-box justify-content-between" id="appBizTrgtAge" data-value="${model.bizTrgtAge}"></div>
											</div>
										</div>
										<div class="col-12">
											<div class="form-area-box1-input2 ">
												<label>사업대상업력<em></em></label>
												<div class="ele-icon-box justify-content-between" id="appBizTrgtFntnPd" data-value="${model.bizTrgtFntnPd}"></div>
											</div>
										</div>
										<div class="col-12">
											<div class="form-area-box1">
												<label>공고접수방법<em></em></label>
												<div class="ele-icon-box">
													<form:input type="text" path="rcptMthdCn" placeholder="방문접수, 우편접수, 이메일접수, 팩스접수, 온라인접수등공고접수 방법을입력해주세요" />
												</div>											
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="box">
							<div class="eletabletop1" data-v-2eb60c26="">
								<div class="row">
									<div class="col">
										<p class="txt2">
											<i class="icon-note"></i>공고본문 내용
										</p>
									</div>
								</div>
							</div>
							<div class="box-in-box">
								<div class="input-grid-1">
									<div class="row">
										<div class="col-12">
											<div class="form-area-box1">
												<label>신청자격<em></em></label>
												<div class="ele-icon-box h-auto">
													<form:textarea path="aplyQlfcCn" cols="30" rows="10"></form:textarea>
												</div>											
											</div>
										</div>
										<div class="col-12">
											<div class="form-area-box1">
												<label>신청제외대상</label>
												<div class="ele-icon-box h-auto">
													<form:textarea path="aplyExclTrgtCn" cols="30" rows="10"></form:textarea>
												</div>											
											</div>
										</div>
										<div class="col-12">
											<div class="form-area-box1">
												<label>제출서류<em></em></label>
												<div class="ele-icon-box h-auto">
													<form:textarea path="sbmsnDcmntCn" cols="30" rows="10"></form:textarea>
												</div>											
											</div>
										</div>
										<div class="col-12">
											<div class="form-area-box1">
												<label>지원내용<em></em></label>
												<div class="ele-icon-box h-auto">
													<form:textarea path="sprtCn" cols="30" rows="10"></form:textarea>
												</div>											
											</div>
										</div>
										<div class="col-12">
											<div class="form-area-box1">
												<label>첨부파일</label>
												<div class="input-btn" id="attachFile"></div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<!-- 미리보기 -->
				<div class="detail-input-box p-16px">
					<div class="box-max-height">
						<div class="box mb-24px">									
							<div class="box-in-box input-grid-1 p-24px pb-8px">
								<div class="row">
									<div class="col-12">
										<div class="form-area-box-input">
											<label>사업분야</label>
											<div class="ele-icon-box">
												<div class="value-box bizFldNm">
													<c:out value="${model.bizFldNm}" />
												</div>
											</div>										
										</div>
									</div>
									<div class="col-6">
										<div class="form-area-box-input">
											<label>창업기간</label>
											<div class="ele-icon-box">
												<div class="value-box bizTrgtFntnPdNm">
													<c:out value="${model.bizTrgtFntnPdNm}" />
												</div>
											</div>										
										</div>
									</div>
									<div class="col-6">
										<div class="form-area-box-input">
											<label>대상연령</label>
											<div class="ele-icon-box">
												<div class="value-box bizTrgtAge">
													<c:out value="${model.bizTrgtAgeNm}" />
												</div>
											</div>
										</div>
									</div>
									<div class="col-12">
										<div class="form-area-box-input">
											<label>지원대상
											</label>
											<div class="ele-icon-box">
												<div class="value-box bizTrgtNm">
													<c:out value="${model.bizTrgtNm}" />
												</div>
											</div>
										</div>
									</div>
									<div class="col-3">
										<div class="form-area-box-input">
											<label>기관명</label>
											<div class="ele-icon-box">
												<div class="value-box crdnsBzentyNm">
													<c:out value="${model.crdnsBzentyNm}" />
												</div>
											</div>										
										</div>
									</div>
									<div class="col-3">
										<div class="form-area-box-input">
											<label>담당부서</label>
											<div class="ele-icon-box">
												<div class="value-box tkcgDeptNm">
													<c:out value="${model.tkcgDeptNm}" />
												</div>
											</div>										
										</div>
									</div>
									<div class="col-3">
										<div class="form-area-box-input">
											<label>담당자명</label>
											<div class="ele-icon-box">
												<div class="value-box picNm">
													<c:out value="${model.picNm}" />
												</div>
											</div>
											
										</div>
									</div>
									<div class="col-3">
										<div class="form-area-box-input">
											<label>연락처</label>
											<div class="ele-icon-box">
												<div class="value-box picTelno">
													<c:out value="${model.picTelnoForm}" />
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="box mb-24px">
							<div class="eletabletop1" data-v-2eb60c26="">
								<div class="row">
									<div class="col">
										<p class="txt2">
											<i class="icon-note"></i>신청방법 및 대상
										</p>
									</div>
								</div>
							</div>
							<div class="box-in-box py-24px">
								<div class="post-details-1">					
									<div class="box pb-0 mb-0">											
										<div class="body">
											<div class="row mb-16px">
												<div class="col w-ver1">
													<p class="txt1">신청기간</p>
												</div>
												<div class="col">
													<p class="txt2">
														<span class="dot-1 value-box rcptSeCdNm">
															<c:out value="${model.rcptSeCdNm}" />
														</span>
													</p>
												</div>
											</div>
											<div class="row mb-16px">
												<div class="col w-ver1">
													<p class="txt1">신청방법</p>
												</div>
												<div class="col">
													<p class="txt2">
														<span class="dot-1 value-box rcptMthdCn">
															<c:out value="${model.rcptMthdCn}" />
														</span>
													</p>
												</div>
											</div>
											<div class="row mb-16px">
												<div class="col w-ver1">
													<p class="txt1">신청자격</p>
												</div>
												<div class="col">
													<p class="txt2">
														<span class="dot-1 value-box aplyQlfcCn">
															<c:out value="${model.aplyQlfcCn}" />
														</span>
													</p>
												</div>
											</div>
											<div class="row mb-16px">
												<div class="col w-ver1">
													<p class="txt1">제외대상</p>
												</div>
												<div class="col">
													<p class="txt2 value-box aplyExclTrgtCn">
														<c:out value="${model.aplyExclTrgtCn}" />
													</p>
												</div>
											</div>
											<div class="text-box-1">
												<div class="row">
													<div class="col text-red"><i class="icon-octagon-exclamation text-red"></i>
														신청 시 요청하는 정보(개인정보포함)는 사업운영기관에서 관리되오니 이점 반드시 유의하여 주시기 바랍니다.
													</div>
												</div>
											</div>
										</div>				
									</div>
								</div>
							</div>
						</div>
						<div class="box mb-24px">
							<div class="eletabletop1" data-v-2eb60c26="">
								<div class="row">
									<div class="col">
										<p class="txt2">
											<i class="icon-note"></i>제출서류
										</p>
									</div>
								</div>
							</div>
							<div class="box-in-box py-24px">
								<div class="post-details-1">					
									<div class="box pb-0 mb-0">											
										<div class="body">
											<div class="row mb-16px">							
												<div class="col">
													<p class="px-0 txt2 value-box sbmsnDcmntCn">
														<c:out value="${model.sbmsnDcmntCn}" />
													</p>
												</div>
											</div>
											<div class="text-box-1">
												<div class="row">
													<div class="col text-red">
														<i class="icon-octagon-exclamation text-red"></i>제출하신 서류는 사업운영기관에서 관리되오니 서류 반황 등 문의는 해당기관으로 하시기 바랍니다.
													</div>
												</div>
											</div>
										</div>				
									</div>
								</div>
							</div>
						</div>
						<div class="box mb-24px">
							<div class="eletabletop1" data-v-2eb60c26="">
								<div class="row">
									<div class="col">
										<p class="txt2">
											<i class="icon-note"></i>지원내용
										</p>
									</div>
								</div>
							</div>
							<div class="box-in-box py-24px">
								<div class="post-details-1">					
									<div class="box pb-0 mb-0">											
										<div class="body">
											<div class="row">							
												<div class="col">
													<p class="px-0 txt2 value-box sprtCn">
														<c:out value="${model.sprtCn}" />
													</p>
												</div>
											</div>
											
										</div>				
									</div>
								</div>
							</div>
						</div>
						<div class="box mb-24px">
							<div class="eletabletop1" data-v-2eb60c26="">
								<div class="row">
									<div class="col">
										<p class="txt2">
											<i class="icon-note"></i>첨부파일
										</p>
									</div>
								</div>
							</div>
							<div class="box-in-box py-24px">
								<div class="post-details-1">					
									<div class="box pb-0 mb-0">											
										<div class="body">
											<div class="row">							
												<div class="col">
													<p class="align-items-center d-flex fs-15px px-0 txt2">
														<!-- i class="fs-18px icon-file-hwp mr-6px"></i> -->
														<div id="attachFile2"></div>					
													</p>
												</div>
											</div>
											
										</div>				
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</c:otherwise>
		</c:choose>
		<!-- 컨텐츠종료 -->
	</div>
</form:form>
<!-- FORM END -->

<c:choose>
	<c:when test="${model.mode eq 'I' or model.mode eq 'U'}">
		<div class="bottom-btn-box">
			<div class="row">	
				<div class="col flex-grow-0 white-space-nowrap">
					<button id="btnPrvw" class="bs-l btn-primary px-14px"><i class="icon-copy-alt"></i>미리보기</button>
				</div>
				<div class="col flex-grow-1 text-end"></div>
				<div class="col flex-grow-0">
					<c:if test="${model.mode ne 'I'}">
						<a id="btnRemove" href="" class="bs-l btn-black"><i class="icon-trash"></i>삭제하기</a>
					</c:if>
					<a id="btnSave"   href="" class="bs-l btn-black"><i class="icon-edit"></i>공고저장</a>
				</div>
			</div>
		</div>
	</c:when>
	<c:otherwise>
		<div class="bottom-btn-box">
			<div class="row">	
				<div class="col flex-grow-0 white-space-nowrap">
					<c:if test="${model.bizGuidanceUrl ne null and model.bizGuidanceUrl ne ''}">
						<button id="btnLink" class="bs-l btn-primary px-14px" data-value="${model.bizGuidanceUrl}">신청바로가기<i class="icon-arrow-right ml-4px mr-0"></i></button>
					</c:if>
				</div>
				<div class="col flex-grow-1 text-end"></div>
				<div class="col flex-grow-0"></div>
			</div>
		</div>
	</c:otherwise>
</c:choose>
<%-- ############################ 내용 (종료) ############################ --%>
