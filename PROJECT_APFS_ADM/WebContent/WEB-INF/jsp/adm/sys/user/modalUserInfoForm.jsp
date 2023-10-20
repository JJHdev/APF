<%--
*******************************************************************************
***    명칭: modalUserInfoForm.jsp
***    설명: 회원관리-사용자관리 등록/수정 모달팝업 화면
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.06.12      LHB     First Coding.
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
	<form:hidden path="userNo"	/><%-- 유저번호 --%>
	<form:hidden path="mode"	/><%-- 처리모드 --%>
	<input type="hidden" id="bzentySeCdOrg" name="bzentySeCdOrg" value="${model.bzentySeCd}" /><%-- 업체구분 --%>
	<input type="hidden" id="rprsYnOrg"     name="rprsYnOrg"     value="${model.rprsYn    }" /><%-- 대표여부 --%>
	<input type="hidden" id="useSttsOrg"    name="useSttsOrg"    value="${model.useSttsCd }" /><%-- 사용상태 --%>
	
	<div class="w-100">
		<!-- 컨텐츠시작 -->
		<div class="box mb-16px">
			<div class="input-grid-1">
				<div class="row">
					<div class="col-12">
						<div class="form-area-box1">
							<label>사용자 ID<em>*</em></label>
							<div class="input-btn">
								<div class="row">
									<div class="col">
										<div class="ele-icon-box">
											<i class="icon-edit"></i>
											<form:input path="userId" placeholder="30자 이내로 입력" maxLength="30" />
										</div>
									</div>
									<div class="col box-btn">
										<c:choose>
											<c:when test="${model.mode eq 'I'}">
												<button type="button" id="btnDuplicate" class="bs-m btn-combi-2 w-100">중복확인</button>
											</c:when>
											<c:otherwise>
												<c:choose>
													<c:when test="${model.lgnSeCd eq 'N'}">
														<button type="button" class="bs-m btn-White fs-20px px-0 text-white" style="background: #1EC800;"><i class="F icon-naver"></i></button>
													</c:when>
													<c:when test="${model.lgnSeCd eq 'K'}">
														<button type="button" class="bs-m btn-White fs-20px px-0 text-white" style="background: #FAE100;"><i class="F icon-kakao"></i></button>
													</c:when>
												</c:choose>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-12">
						<div class="form-area-box1">
							<label>성명<em>*</em></label>
							<div class="ele-icon-box">
							    <form:input path="userNm" maxLength="40" placeholder="성명" />
							</div>
						</div>
					</div>
					<div class="col-12">
						<div class="form-area-box1">
							<label>비밀번호<em>*</em></label>
							<div class="input-btn">
								<div class="row">
									<div class="col">
										<div class="ele-icon-box">
											<form:password path="pswd" maxLength="30" placeholder="비밀번호" />
										</div>
									</div>
								<c:if test="${model.mode eq 'U'}">
									<div class="col box-btn">
										<button type="button" id="btnResetPswd" class="bs-m btn-combi-2 w-100">초기화</button>
									</div>
								</c:if>
								</div>
							</div>
							<div class="bottom-lable">
								<div class="row">
									<div class="col">
										<p class="fs-10px text-red"><i class="icon-exclamation-circle"></i>8자 이상 영대소문자+숫자+특수문자 조합</p>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-12">
						<div class="form-area-box1">
							<label>비밀번호확인<em>*</em></label>
							<div class="ele-icon-box">
							    <input type="password" id="pswdCnfm" name="pswdCnfm" placeholder="비밀번호 확인" maxLength="30" />
							</div>
						</div>
					</div>
					<div class="col-12">
						<div class="form-area-box1">
							<label>휴대전화<em>*</em></label>
							<form:hidden path="mblTelno" />
							<div class="day">
								<div class="row">
									<div class="col"><div class="ele-icon-box"><select id="mblTelno1" name="mblTelno1"></select><i class="icon-angle-down"></i></div></div>
									<div class="col wave"><span>-</span></div>
									<div class="col"><div class="ele-icon-box"><input  id="mblTelno2" name="mblTelno2" type="text" maxLength="4"/></div></div>
									<div class="col wave"><span>-</span></div>
									<div class="col"><div class="ele-icon-box"><input  id="mblTelno3" name="mblTelno3" type="text" maxLength="4"/></div></div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-12 role">
						<div class="form-area-box1-input2">
							<label>회원구분<em>*</em></label>
							<div class="ele-icon-box">
								<div class="check-radio-box">
									<input type="radio" id="joinCdU" name="joinCd" value="U">
									<label for="joinCdU">개인회원</label>
								</div>
								<div class="check-radio-box">
									<input type="radio" id="joinCdB" name="joinCd" value="B">
									<label for="joinCdB">기업회원</label>
								</div>
							</div>
						</div>
					</div>
					<div class="col-12 ent">
						<div class="form-area-box1">
							<label>소속기업명<em>*</em></label>
							<div class="input-btn">
								<div class="row">
									<div class="col">
										<div class="ele-icon-box">
											<i class="icon-edit"></i>
											<form:hidden path="bzentySeCd" />
											<form:hidden path="bzentyNo" />
											<form:input  path="bzentyNm" placeholder="클릭해주세요." maxLength="20" readonly="true" />
										</div>
									</div>
								<c:if test="${model.rprsYn != 'Y'}"><%-- 업체대표는 업체를 변경할 수 없음 --%>
									<div class="col box-btn">
										<button type="button" id="btnBzenty" class="bs-m btn-combi-2 w-100">조회</button>
									</div>
								</c:if>
								</div>
							</div>
						</div>
					</div>
					<div class="col-12">
						<div class="form-area-box1">
							<label>관리자 권한<em>*</em></label>
							<div class="ele-icon-box">
								<form:select path="admRoleId" data-value="${model.admRoleId}" style="width:200px;"></form:select>
								<i class="icon-angle-down"></i>
							</div>
						</div>
					</div>
					<div class="col-12">
						<div class="form-area-box1">
							<label>사용자 권한<em>*</em></label>
							<div class="ele-icon-box">
								<form:select path="usrRoleId" data-value="${model.usrRoleId}" style="width:200px;"></form:select>
								<i class="icon-angle-down"></i>
							</div>
						</div>
					</div>
					<div class="col-12 ent">
						<div class="form-area-box1-input2">
							<label>계정구분<em>*</em></label>
							<div class="ele-icon-box" id="appRprsYn" data-value="${model.rprsYn}"></div>
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

<!-- MODAL START -->
<!-- 투자설명회 -->
<div class="modal fade" id="myModal1" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	<form id="prgrsForm1" name="prgrsForm" method="post" onsubmit="return false;">
		<div class="modal-dialog custom-modal design1" role="document">
			<div class="modal-content" style="width: 617px;">
				<div class="modal-header">
					<p class="txt1">
						<i class="icon-copy-alt"></i>업체 검색
						<button type="button" class="close" data-bs-dismiss="modal" aria-label="Close"><i class="icon-times"></i></button>
					</p>
				</div>
				<div class="modal-body">
					<div class="input-grid-2">
						<div class="row">
							<div class="col">
								<div class="form-area-box">
									<label>업체명/사업자 등록번호<em></em></label>
									<div class="ele-icon-box">
										<input type="text" name="srchText" id="srchTextEnt" style="" placeholder="업체명 혹은 사업자등록번호를 입력하세요." class="">
										<i class="icon-times-circle F text-ele-TextDisable"></i>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="input-grid-2">
						<div class="row">
							<div class="col">
								<div class="form-area-box">
									<div>
										<!-- 목록 컨텐츠 영역 -->
										<div id="grid-ent" class="pb-8px"></div>
									
										<div class="row justify-content-between">
											<div class="col white-space-nowrap">
												<!-- 목록 페이징 영역 -->
												<div class="mb-0 p-0 paging-box">
													<ul id="appGridPagination-ent"></ul>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<div class="bottom-btn-box">
						<div class="row">
							<div class="col">
								<a href="" class="btn-combi-3 bs-m" data-bs-dismiss="modal"><i class="icon-times"></i>취소</a>
							</div>
							<div class="col">
								<a href="" id="myModalSave2" class="btn-primary bs-m"><i class="icon-floppy-disk"></i>등록</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
</div>
<!-- MODAL END -->

<%-- ############################ 내용 (종료) ############################ --%>
