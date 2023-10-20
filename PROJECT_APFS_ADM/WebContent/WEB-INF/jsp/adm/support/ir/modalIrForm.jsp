<%--
*******************************************************************************
***    명칭: modalIrForm.jsp
***    설명: 운영관리-IR지원현황 등록/수정 모달팝업 화면
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.06.08      LHB     First Coding.
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
	<form:hidden path="fundNo"	 	/><%-- 펀드번호 --%>
	<form:hidden path="bzentyNo" 	/><%-- 업체번호 --%>
	<form:hidden path="irRlsYn"	 	/><%-- IR공개여부 --%>
	<form:hidden path="sprtSttsCd"	/><%-- 지원상태코드 --%>
	<form:hidden path="sprtYmd"	 	/><%-- 지원일자 --%>
	<form:hidden path="rvwYmd"	 	/><%-- 검토일자 --%>
	<form:hidden path="type"	 	/><%-- 타입 --%>
	<form:hidden path="mode"	 	/><%-- 처리모드 --%>
	
	<div class="w-100">
		<!-- 컨텐츠시작 -->
		<div class="tabmenu2 border-0 m-0 mb-32px rounding-1" style="border-radius: 0;">
			<ul class="">
				<li class="type">
					<a href="#" class="<c:if test='${model.type eq "ENT" }'>active</c:if>">경영체 정보</a>
				</li>
				<li class="type">
					<a href="#" class="<c:if test='${model.type eq "INVT"}'>active</c:if>">투자자 정보</a>
				</li>
			</ul>
		</div>
		<div class="box-max-height pt-8px">
			<c:if test="${model.type eq 'ENT'}">
			<!-- 경영체 정보 -->
				<div class="box mb-24px">
					<div class="eletabletop1" data-v-2eb60c26="">
						<div class="row">
							<div class="col">
								<p class="txt2">
									<i class="icon-note"></i>개인정보
								</p>
							</div>
						</div>
					</div>
					<div class="input-grid-1 box-in-box">
						<div class="row">
							<div class="col-12">
								<div class="form-area-box-input">
									<label>사용자 ID</label>
									<div class="ele-icon-box">
										<div class="value-box">
											<c:out value="${model.rgtrId}" />
										</div>
									</div>										
								</div>
							</div>
							<div class="col-12">
								<div class="form-area-box-input">
									<label>성명</label>
									<div class="ele-icon-box">
										<div class="value-box">
											<c:out value="${model.rgtrNm}" />
										</div>
									</div>
								</div>
							</div>
							<div class="col-12">
								<div class="form-area-box-input">
									<label>휴대폰 번호</label>
									<div class="ele-icon-box">
										<div class="value-box">
											<c:out value="${model.mblTelno}" />
										</div>
									</div>
									
								</div>
							</div>												
						</div>
					</div>
				</div>
				<div class="box ">
					<div class="eletabletop1" data-v-2eb60c26="">
						<div class="row">
							<div class="col">
								<p class="txt2">
									<i class="icon-note"></i>기업정보
								</p>
							</div>
						</div>
					</div>
					<div class="input-grid-1 box-in-box">
						<div class="row">
							<div class="col-12">
								<div class="form-area-box-input">
									<label>사업자번호</label>
									<div class="ele-icon-box">
										<div class="value-box">
											<c:out value="${model.brno}" />
										</div>
									</div>										
								</div>
							</div>
							<div class="col-12">
								<div class="form-area-box-input">
									<label>소속</label>
									<div class="ele-icon-box">
										<div class="value-box">
											<c:out value="${model.bzentyNm}" />
										</div>
									</div>
								</div>
							</div>
							<div class="col-12">
								<div class="form-area-box-input">
									<label>대표자</label>
									<div class="ele-icon-box">
										<div class="value-box">
											<c:out value="${model.rprsvNm}" />
										</div>
									</div>
								</div>
							</div>	
							<div class="col-12">
								<div class="form-area-box-input">
									<label>설립일</label>
									<div class="ele-icon-box">
										<div class="value-box">
											<c:out value="${model.fndnYmd}" />
										</div>
									</div>
								</div>
							</div>	
							<div class="col-12">
								<div class="form-area-box-input">
									<label>대표번호</label>
									<div class="ele-icon-box">
										<div class="value-box">
											<c:out value="${model.rprsTelno}" />
										</div>
									</div>
								</div>
							</div>												
						</div>
					</div>
				</div>
			</c:if>
			<c:if test="${model.type eq 'INVT'}">
			<!-- 투자자 정보 -->
				<div class="box ">
					<div class="eletabletop1" data-v-2eb60c26="">
						<div class="row">
							<div class="col">
								<p class="txt2">
									<i class="icon-note"></i>펀드정보
								</p>
							</div>
						</div>
					</div>
					<div class="input-grid-1 box-in-box">
						<div class="row">
							<div class="col-12">
								<div class="form-area-box-input">
									<label>펀드명</label>
									<div class="ele-icon-box">
										<div class="value-box">
											<c:out value="${model.fundVO.fundNm}" />
										</div>
									</div>										
								</div>
							</div>
							<div class="col-12">
								<div class="form-area-box-input">
									<label>투자분야</label>
									<div class="ele-icon-box">
										<div class="value-box">
											<c:out value="${model.fundVO.invtFldCdNm}" />
										</div>
									</div>
								</div>
							</div>
							<div class="col-12">
								<div class="form-area-box-input">
									<label>결성일</label>
									<div class="ele-icon-box">
										<div class="value-box">
											<c:out value="${model.fundVO.orgzYmd}" />
										</div>
									</div>
								</div>
							</div>
							<div class="col-12">
								<div class="form-area-box-input">
									<label>투자기간</label>
									<div class="day">
										<div class="row">
											<div class="col">
												<div class="ele-icon-box">
													<div class="value-box">
														<c:out value="${model.fundVO.invtBgngYmd}" />
													</div>
												</div>
											</div>
											<div class="col wave">
												<span>~</span>
											</div>
											<div class="col">
												<div class="ele-icon-box">
													<div class="value-box">
														<c:out value="${model.fundVO.invtEndYmd}" />
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="col-12">
								<div class="form-area-box-input">
									<label>펀드 운영규모</label>
									<div class="day">
										<div class="row align-items-end">
											<div class="col">
												<div class="ele-icon-box">
													<div class="value-box">
														<c:out value="${model.fundVO.fundOperScale}" />
													</div>
												</div>
											</div>
											<div class="col flex-grow-0 white-space-nowrap">
												<span>백만원</span>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="col-12">
								<div class="form-area-box-input">
									<label>조합원1</label>
									<div class="ele-icon-box">
										<div class="value-box">
											<c:out value="${model.fundVO.mmbs1}" />
										</div>
									</div>										
								</div>
							</div>
							<div class="col-12">
								<div class="form-area-box-input">
									<label>조합원2</label>
									<div class="ele-icon-box">
										<div class="value-box">
											<c:out value="${model.fundVO.mmbs2}" />
										</div>
									</div>										
								</div>
							</div>
							<div class="col-12">
								<div class="form-area-box-input">
									<label>대표연락처</label>
									<div class="ele-icon-box">
										<div class="value-box">
											<c:out value="${model.fundVO.rprsTelno}" />
										</div>
									</div>
								</div>
							</div>
							<div class="col-12">
								<div class="form-area-box-input">
									<label>대표홈페이지</label>
									<div class="ele-icon-box">
										<div class="value-box">
											<c:out value="${model.fundVO.rprsHmpgAddr}" />
										</div>
									</div>										
								</div>
							</div>
						</div>
					</div>
				</div>
			</c:if>
		</div>
		<!-- 컨텐츠종료 -->
	</div>
</form:form>
<!-- FORM END -->

<%-- ############################ 내용 (종료) ############################ --%>
