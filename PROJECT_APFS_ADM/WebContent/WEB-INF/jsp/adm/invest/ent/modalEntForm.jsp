<%--
*******************************************************************************
***    명칭: modalEntForm.jsp
***    설명: 회원관리-업체관리 등록/수정 모달팝업 화면
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
	<form:hidden path="bzentyNo"	/><%-- 업체번호 --%>
	<form:hidden path="bzentySeCd"	/><%-- 업체구분 --%>
	<form:hidden path="useSttsCd"	/><%-- 사용상태 --%>
	<form:hidden path="mode"		/><%-- 처리모드 --%>
	
	<div class="w-100">
		<!-- 컨텐츠시작 -->
		<div class="box mb-16px">
			<div class="input-grid-1 box-in-box">
				<div class="row">
					<div class="col-12">
						<div class="form-area-box1">
							<label>업체명</label>
							<div class="ele-icon-box">
								<form:input path="bzentyNm" maxLength="100" disabled="true" />
							</div>											
						</div>
					</div>
					<div class="col-12">
						<div class="form-area-box1">
							<label>사업자등록번호</label>
							<form:hidden path="brno" />
							<div class="day">
								<div class="row">
									<div class="col flex-b140px">
										<div class="ele-icon-box">
											<input type="text" name="brno1" id="brno1" placeholder="" disabled="true" />
										</div>
									</div>
									<div class="col wave">
										<span>-</span>
									</div>
									<div class="col flex-b100px">
										<div class="ele-icon-box">
											<input type="text" name="brno2" id="brno2" placeholder="" disabled="true" />
										</div>
									</div>
									<div class="col wave">
										<span>-</span>
									</div>
									<div class="col">
										<div class="ele-icon-box">
											<input type="text" name="brno3" id="brno3" placeholder="" disabled="true" />
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-12 ent">
						<div class="form-area-box1">
							<label>기업유형</label>
							<div class="ele-icon-box">
								<form:input path="bzentySeNm" disabled="true" />
							</div>
						</div>
					</div>
					<div class="col-12">
						<div class="form-area-box1">
							<label>대표자</label>
							<div class="ele-icon-box">
								<form:input path="rprsvNm" disabled="true" />
							</div>
						</div>
					</div>
					<div class="col-12">
						<div class="form-area-box1">
							<label>설립일</label>
							<div class="ele-icon-box">
								<form:input path="fndnYmd" disabled="true" />
								<a href="#" class="icon-calendar"></a>
							</div>
						</div>
					</div>
					<div class="col-12">
						<div class="form-area-box1">
							<label>대표번호</label>
							<div class="day">
								<div class="row">
									<form:hidden path="rprsTelno" />
									<div class="col">
										<div class="ele-icon-box">
											<input id="rprsTelno1" name="rprsTelno1" maxLength="3" disabled="true" />
										</div>
									</div>
									<div class="col wave">
										<span>-</span>
									</div>
									<div class="col">
										<div class="ele-icon-box">
											<input id="rprsTelno2" name="rprsTelno2" maxLength="4" disabled="true" />
										</div>
									</div>
									<div class="col wave">
										<span>-</span>
									</div>
									<div class="col">
										<div class="ele-icon-box">
											<input id="rprsTelno3" name="rprsTelno3" maxLength="4" disabled="true" />
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<c:choose>
						<c:when test="${model.bzentySeCd eq '20'}">
							<%-- 경영체 첨부파일 --%>
							<div class="col-12">
								<div class="form-area-box1">
									<label>사업자등록증</label>
									<div class="ele-icon-box">
										<%-- 첨부파일영역 --%>
										<div id="attachFile"></div>
									</div>
								</div>
							</div>
							<div class="col-12">
								<div class="form-area-box1">
									<label>위임장</label>
									<div class="ele-icon-box">
										<%-- 첨부파일영역 --%>
										<div id="attachFile2"></div>
									</div>
								</div>
							</div>
						</c:when>
						<c:otherwise>
							<%-- 투자자, 유관기관 첨부파일 --%>
							<div class="col-12">
								<div class="form-area-box1">
									<label>사업자등록증</label>
									<div class="ele-icon-box">
										<%-- 첨부파일영역 --%>
										<div id="attachFile"></div>
									</div>
								</div>
							</div>
						</c:otherwise>
					</c:choose>
					<div class="col-12">
						<div class="form-area-box1">
							<label>승인일자</label>
							<div class="ele-icon-box">
								<form:input path="aprvYmd" disabled="true" />
								<a href="#" class="icon-calendar"></a>
							</div>
						</div>
					</div>
					<div class="col-12">
						<div class="form-area-box1">
							<label>관리자 메모</label>
							<div class="ele-icon-box">
								<form:textarea path="mngrMemo" style="min-height: 60px; "/>
							</div>
						</div>
					</div>
					<c:if test="${model ne null && rprsInfo ne null && model.rmrk4 eq rprsInfo.userNm}">
						<div class="col-12 app-c">
							대표계정 사용자 정보와 KoDATA 업체 대표자명이 <text class="text-red">일치</text>하는 회원입니다. (제출서류 불필요)
						</div>
					</c:if>
				</div>
			</div>
		</div>
		<!-- 컨텐츠종료 -->
		
		<div class="bottom-btn-box">
			<div class="row">
				<c:if test="${model.useSttsCd ne '9'}">
					<div class="col flex-grow-0"><a id="btnReject"  href="" class="bs-l btn-combi-3 w-100"><i class="icon-file-export-alt"></i>반려</a></div>
				</c:if>
				<c:if test="${model.useSttsCd ne '1'}">
					<div class="col flex-grow-0"><a id="btnApprove" href="" class="bs-l btn-primary w-100"><i class="icon-check-double">   </i>승인</a></div>
				</c:if>
				<div class="col flex-grow-0"><a id="btnSave"    href="" class="bs-l btn-primary px-14px"><i class="icon-copy-alt">     </i>저장</a></div>
			</div>
		</div>
	</div>
</form:form>
<!-- FORM END -->

<%-- ############################ 내용 (종료) ############################ --%>
