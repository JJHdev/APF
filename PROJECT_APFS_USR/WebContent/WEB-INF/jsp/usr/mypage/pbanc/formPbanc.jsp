<%--
*******************************************************************************
***    명칭: openPbanc.jsp
***    설명: 마이페이지 - 사업공고등록 화면
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.06.14    LSH        First Coding.
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
<form:form commandName="model" id="registForm" name="registForm" method="post" enctype="multipart/form-data" onsubmit="return false;">
	<form:hidden path="bizPbancNo"   	/><%-- 게시물번호 --%>
	<form:hidden path="mode"         	/><%-- 처리모드 --%>
	<form:hidden path="crdnsBzentyNo"   /><%-- 세션 유관기관업체 번호  --%>
	
	<div class="tit-box-3 mb-16px">
		<div class="row">
			<div class="col">
				<p class="txt1"><img id="pbancImage">사업공고 신청</p>
			</div>
		</div>
	</div>
			
	<div class="shadow-box-1 px-32px pt-32px">
		<div class="box-max-height">
			<div class="box mb-24px">
				<div class="table-top-1">
					<div class="row">
						<div class="col">
							<p class="txt1"><i class="cycle-ver-1 icon-buildings"></i>주관기관 정보</p>
						</div>
					</div>
				</div>
				
				<div class="box-in-box">
				<div class="input-grid-1">
						<div class="row">
							
							<div class="col-md-12">
								<div class="form-area-box1">
									<label>등록기관명<em></em></label>
									<div class="ele-icon-box">
										<c:if test="${not empty model.crdnsBzentyNm}">
											<form:input path="crdnsBzentyNm" readonly="true" />
										</c:if>
										<c:if test="${empty model.crdnsBzentyNm}">
											<form:input path="crdnsBzentyNm" />
										</c:if>
										
									</div>
								</div>
							</div>
							
							<div class="col-md-12">
								<div class="form-area-box1">
									<label>사업담당부서<em></em></label>
									<div class="ele-icon-box">
										<form:input path="tkcgDeptNm" placeholder="사업담당자 부서명을 작성해주세요"/>
									</div>
								</div>
							</div>
							
							<div class="col-md-12">
								<div class="form-area-box1">
									<label>사업담당자<em></em></label>
									<div class="ele-icon-box">
										<form:input path="picNm" placeholder="담당자명을 입력해주세요"/>
									</div>
								</div>
							</div>
							
							<div class="col-md-12">
								<div class="form-area-box1">
									<label>담당자 연락처<em></em></label>
									<div class="ele-icon-box">
										<form:input  path="picTelnoForm" placeholder="담당자 연착처를 기재해주세요"/>
										<form:hidden path="picTelno"     />
									</div>
									<div class="bottom-lable">
										<div class="row">
											<div class="col"><p class="fs-10px text-red">* 개인휴대전화번호 입력금지.</p></div>
										</div>
									</div>
								</div>
							</div>
						</div>
						
						<div class="box mb-24px">
							<div class="table-top-1">
								<div class="row">
									<div class="col"><p class="txt1"><i class="cycle-ver-1 icon-document"></i>사업정보</p></div>
								</div>
							</div>
							<div class="box-in-box">
								<div class="input-grid-1">
									<div class="row">
									
										<div class="col-md-12">
											<div class="form-area-box1">
												<label>안내URL</label>
												<div class="ele-icon-box">
													<form:input path="bizGuidanceUrl" placeholder="도메인 주소를 입력해주세요"/>
												</div>											
											</div>
										</div>
										
										<div class="col-md-12">
											<div class="form-area-box1">
												<label>공고명<em></em></label>
												<div class="ele-icon-box">
													<form:input path="bizPbancNm" placeholder="공고명을 입력해주세요"/>
												</div>											
											</div>
										</div>
										
										<div class="col-md-12">
											<div class="form-area-box1-input2">
												<label>접수기간<em></em></label>
												<div class="ele-icon-box justify-content-between py-16px py-md-0">
													<div class="row align-items-center">
														<div class="col-12 col-md flex-grow-0 white-space-nowrap">
															<div class="check-radio-box ">
																<div id="appRcptSeCd" data-value="<c:out value='${model.rcptSeCd}'/>"></div>
															</div>
														</div>
														
														<div class="col-12 col-md flex-b-md-550px" id="rcptDate">
															<div class=" row " style=" margin: 0 -4px;">
																<div class="col px-4px" >
																	<div class="form-area-box small-ver">
																		<div class="ele-icon-box">
																			<form:input  path="rcptBgngYmd" maxlength="10" class="datepicker-input1"/>
																			<form:hidden path="rcptBgngDt" />
																			<a href="javascript:void(0)" class="icon-calendar right"></a>
																		</div>
																	</div>
																</div>
																
																<div class="col flex-b-md-80px px-4px" id="btnBgngDtHour">
																	<div class="form-area-box small-ver">
																		<div class="ele-icon-box">
																			<div class="dropdown">
																				<button class="dropdown-toggle" id="rcptBgngDt_hour"  type="button" data-bs-toggle="dropdown" aria-expanded="true">
																					시간
																				</button>
																				<ul class="dropdown-menu w-100 bg-white myPbancTime" data-popper-placement="bottom-start" style="position: absolute; inset: 0px auto auto 0px; margin: 0px; transform: translate(0px, 28px);">
																				</ul>
																				<form:hidden path="rcptBgngTm" />
																			</div>
																		</div>
																	</div>
																</div>
																<div class="col px-4px flex-grow-0 px-4px white-space-nowrap align-self-center">
																	~
																</div>
																<div class="col px-4px">
																	<div class="form-area-box small-ver">
																		<div class="ele-icon-box">
																		<form:input  path="rcptEndYmd" maxlength="10" class="datepicker-input2"/>
																		<form:hidden path="rcptEndDt" />
																		<a href="javascript:void(0)" class="icon-calendar right"></a>
																		</div>
																	</div>
																</div>
																<div class="col flex-b-md-80px px-4px" id="btnEndDtHour">
																	<div class="form-area-box small-ver">
																		<div class="ele-icon-box">
																			<div class="dropdown">
																				<button class="dropdown-toggle" id="rcptEndDt_hour" type="button" data-bs-toggle="dropdown" aria-expanded="true">
																					시간
																				</button>
																				<ul class="dropdown-menu w-100 bg-white myPbancTime" data-popper-placement="bottom-start" style="position: absolute; inset: 0px auto auto 0px; margin: 0px; transform: translate(0px, 28px);">
																				</ul>
																				<form:hidden path="rcptEndTm" />
																			</div>
																		</div>
																	</div>
																</div>
															</div>
														</div>
													</div>
												</div>									
											</div>
										</div>
											
										<div class="col-12">
											<div class="form-area-box1-input2 ">
												<label>사업분야<em></em></label>
												<form:hidden path="bizFldNm" />
												<div class="ele-icon-box justify-content-between" id="appBizFld" data-value="<c:out value='${model.bizFld}'/>"></div>
											</div>
										</div>
											
										<div class="col-12">
											<div class="form-area-box1-input2">
												<label>사업대상<em></em></label>
												<form:hidden path="bizTrgtNm" />
												<div class="ele-icon-box justify-content-between" id="appBizTrgt" data-value="<c:out value='${model.bizTrgt}'/>"></div>
											</div>
										</div>
										<div class="col-12">
											<div class="form-area-box1-input2 ">
												<label>사업대상연령<em></em></label>
												<div class="ele-icon-box justify-content-between" id="appBizTrgtAge" data-value="<c:out value='${model.bizTrgtAge}'/>"></div>
												<form:hidden path="bizTrgtAgeNm" />
											</div>
										</div>
										
										<div class="col-12">
											<div class="form-area-box1-input2 ">
												<label>사업대상업력<em></em></label>
												<div class="ele-icon-box justify-content-between" id="appBizTrgtFntnPd" data-value="<c:out value='${model.bizTrgtFntnPd}'/>"></div>
												<form:hidden path="bizTrgtFntnPdNm" />
											</div>
										</div>
								
										<div class="col-md-12">
											<div class="form-area-box1">
												<label>공고접수방법<em></em></label>
												<div class="ele-icon-box">
													<form:textarea path="rcptMthdCn" cols="20" rows="10" style="height: 100px;" placeholder="방문접수, 우편접수, 이메일접수, 팩스접수, 온라인접수등공고접수 방법을입력해주세요"></form:textarea>				
												</div>											
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
										
						<div class="box">
							<div class="table-top-1">
								<div class="row">
									<div class="col">
										<p class="txt1"><i class="cycle-ver-1 icon-document"></i>공고본문 내용</p>
									</div>
								</div>
							</div>
								
							<div class="box-in-box">
								<div class="input-grid-1">
									<div class="row">
										<div class="col-md-12">
											<div class="form-area-box1">
												<label>신청자격<em></em></label>
												<div class="ele-icon-box h-auto">
													<form:textarea path="aplyQlfcCn" cols="30" rows="10"></form:textarea>										
												</div>											
											</div>
										</div>
										<div class="col-md-12">
											<div class="form-area-box1">
												<label>신청제외대상</label>
												<div class="ele-icon-box h-auto">
													<form:textarea path="aplyExclTrgtCn" cols="30" rows="10"></form:textarea>										
												</div>											
											</div>
										</div>
										<div class="col-md-12">
											<div class="form-area-box1">
												<label>제출서류<em></em></label>
												<div class="ele-icon-box h-auto">
													<form:textarea path="sbmsnDcmntCn" cols="30" rows="10"></form:textarea>							
												</div>											
											</div>
										</div>
										<div class="col-md-12">
											<div class="form-area-box1">
												<label>지원내용<em></em></label>
												<div class="ele-icon-box h-auto">
													<form:textarea path="sprtCn" cols="30" rows="10"></form:textarea>							
												</div>											
											</div>
										</div>
										<div class="col-12">
											<div class="form-area-box1">
												<%-- 첨부파일영역 --%>
												<div id="attachFile"></div>
											</div>
										</div>
										
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="bottom-box-btn px-0 py-24px">
						<div class="row">	
							<div class="col flex-grow-0 white-space-nowrap">
								<button class="bs-l btn-primary" custum-modal-target="#exampleModal" id="btnPreView"><i class="icon-copy-alt"></i>미리보기</button>
							</div>
							<div class="col flex-grow-1 text-end d-none d-md-inline-flex"></div>
							
							<div class="col flex-grow-0">
								<a href="javascript:void(0)" id="btnSave" class="bs-l btn-black"><i class="icon-edit"></i>공고등록</a>
							</div>
							<div class="col flex-grow-0">
								<a href="javascript:void(0)" id="btnCancel" class="bs-l btn-black"><i class="icon-trash"></i>취소</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
</form:form>

<!-- popup -->
<div class="custum-modal fade " id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="cutum-modal-dialog position-nomal" style="max-width:960px;">
		<div class="modal-content">
			<div class="border-0 modal-header">
				<h4 class="modal-title d-none d-xl-inline-flex">
					<img id="popupPbancImage">
					<span>사업공고 상세정보</span>
				</h4>
				<button type="button" class="close-btn d-none d-xl-block" custum-modal-dismiss="modal">
					<i class="icon-times F"></i>
				</button>
				<button type="button" class="close-btn d-xl-none" custum-modal-dismiss="modal">
					<i class="icon-angle-left-circle"></i>
				</button>
				<h4 class="modal-title d-xl-none">
					<span>사업공고 상세정보</span>
				</h4>
			</div>
			
			
			
			
			<div class="modal-body">
				<div class="box-max-height">
					<div class="post-details-1">
						<div class="top">
							<div class="infobox">
								<div class="row">
									<div class="col-12">
										<p class="txt1">
											<span style="font-weight: 600;">사업분야</span>
											<em id="p_bizFldNm"></em>
										</p>
									</div>
								</div>
								<div class="row">
									<div class="col-12">
										<p class="txt1">
											<span style="font-weight: 600;">지원대상</span>
											<em id="p_bizTrgtNm"></em>
										</p>
									</div>
								</div>
								<div class="row">
									<div class="col-12 col-l-4">
										<p class="txt1">
											<span style="font-weight: 600;">창업기간</span>
											<em id="p_bizTrgtFntnPdNm"></em>
										</p>
									</div>
									<div class="col-12 col-l-4">
										<p class="txt1">
											<span style="font-weight: 600;">대상연령</span>
											<em id="p_bizTrgtAgeNm"></em>
										</p>
									</div>
									<div class="col-12 col-l-4">
										<p class="txt1">
											<span style="font-weight: 600;">기관명</span>
											<em id="p_crdnsBzentyNm"></em>
										</p>
									</div>
								</div>
								<div class="row">
									<div class="col-12 col-l-4">
										<p class="txt1">
											<span style="font-weight: 600;">담당부서</span>
											<em id="p_bizTrgtNm"></em>
											<em id="p_tkcgDeptNm"></em>
										</p>
									</div>
									<div class="col-12 col-l-4">
										<p class="txt1">
											<span style="font-weight: 600;">담당자명</span>
											<em id="p_picNm"></em>
										</p>
									</div>
									<div class="col-12 col-l-4">
										<p class="txt1">
											<span style="font-weight: 600;">연락처</span>
											<em id="p_picTelnoForm"></em>
										</p>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="box mb-24px">
						<div class="table-top-1">
							<div class="row">
								<div class="col">
									<p class="txt1">
										<i class="cycle-ver-1 icon-document"></i>신청방법 및 대상
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
													<span class=" dot-1">
														<span id="p_rcptBgngYmd"></span>
														<span id="p_rcptBgngTm"></span>
														<span id="p_rcptEndYmd"></span>
														<span id="p_rcptEndTm"></span>
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
													<span id="p_rcptMthdCn"></span>
												</p>
											</div>
										</div>
										
										<div class="row mb-16px">
											<div class="col w-ver1">
												<p class="txt1">신청자격</p>
											</div>
											<div class="col">
												<p class="txt2">
													<span id="p_aplyQlfcCn"></span>
												</p>
											</div>
										</div>
										<div class="row mb-16px">
											<div class="col w-ver1">
												<p class="txt1">제외대상</p>
											</div>
											<div class="col">
												<p class="txt2">
													<span id="p_aplyExclTrgtCn"></span>
												</p>
											</div>
										</div>
										<div class="text-box-1">
											<div class="row">
												<div class="col-12 text-red">
												<i class="icon-octagon-exclamation text-red"></i>
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
						<div class="table-top-1">
							<div class="row">
								<div class="col">
									<p class="txt1">
										<i class="cycle-ver-1 icon-document"></i>제출서류
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
												<p class="px-0 txt2">
													<span id="p_sbmsnDcmntCn"></span>
												</p>
											</div>
										</div>
										
										<div class="text-box-1">
											<div class="row">
												<div class="col-12 text-red"><i class="icon-octagon-exclamation text-red"></i>제출하신 서류는 사업운영기관에서 관리되오니 서류 반황 등 문의는 해당기관으로 하시기 바랍니다.</div>
											</div>
										</div>
									</div>				
								</div>
							</div>
						</div>
					</div>
					<div class="box mb-24px">
						<div class="table-top-1">
							<div class="row">
								<div class="col">
									<p class="txt1">
										<i class="cycle-ver-1 icon-document"></i>지원내용
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
												<p class="px-0 txt2">
													<span class="mb-8px">
														<span id="p_sprtCn"></span>
													</span>
												</p>
											</div>
										</div>
									</div>				
								</div>
							</div>
						</div>
					</div>
					<div class="box mb-24px">
						<div class="table-top-1">
							<div class="row">
								<div class="col">
									<p class="txt1">
										<i class="cycle-ver-1 icon-document"></i>첨부파일
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
												<div  id="attachFile2"></div>
												<span id="downloadLinkDom" ></span>
											</div>
										</div>
									</div>				
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<%-- ############################ 내용 (종료) ############################ --%>
