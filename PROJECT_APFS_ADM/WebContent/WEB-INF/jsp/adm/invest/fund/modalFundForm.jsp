<%--
*******************************************************************************
***    명칭: modalFundForm.jsp
***    설명: 투자정보관리 - 모태펀드등록 등록/수정 모달팝업 화면
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.06.02      LHB     First Coding.
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

<style>
	.detail-pop-1 { max-width: 500px !important; }
</style>

<!-- FORM START -->
<form:form commandName="model" id="registForm" name="registForm" method="post" enctype="multipart/form-data" onsubmit="return false;">
	<form:hidden path="fundNo"	/><%-- 펀드번호 --%>
	<form:hidden path="mode"	/><%-- 처리모드 --%>
	
	<div class="w-100">
		<!-- 컨텐츠시작 -->
		<div class="box mb-16px">
			<div class="input-grid-1 box-in-box">
				<div class="row">
					<div class="col-12">
						<div class="form-area-box1">
							<label>펀드명<em></em></label>
							<div class="ele-icon-box">
								<form:input path="fundNm" htmlEscape="false" maxLength="100"/>
							</div>											
						</div>
					</div>
					<div class="col-12">
						<div class="form-area-box1">
							<input type="hidden" id="invtFldCdHidden" value="${model.invtFldCd}" />
							<label>투자분야<em></em></label>
							<div class="ele-icon-box" id="appInvtFldCd">
								<form:select path="invtFldCd" data-value="${model.invtFldCd}"></form:select>
								<i class="icon-angle-down"></i>
							</div>
							
						</div>
					</div>
					<div class="col-12">
						<div class="form-area-box1">
							<label>결성일<em></em></label>
							<div class="ele-icon-box">
								<form:input path="orgzYmd" placeholder="YYYY-MM-DD" class="datepicker-input"/>
								<a href="#" class="icon-calendar"></a>
							</div>
							
						</div>
					</div>
					<div class="col-12">
						<div class="form-area-box1">
							<label>투자기간<em></em></label>
							<div class="day">
								<div class="row">
									<div class="col">
										<div class="ele-icon-box">					
											<form:input path="invtBgngYmd" placeholder="YYYY-MM" class="datepickerMonth-input" />
											<i class="icon-calendar"></i>										
										</div>
									</div>
									<div class="col wave">
										<span>~</span>
									</div>
									<div class="col">
										<div class="ele-icon-box">					
											<form:input path="invtEndYmd"  placeholder="YYYY-MM" class="datepickerMonth-input" />
											<i class="icon-calendar"></i>										
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-12">
						<div class="form-area-box1">
							<label>펀드 운영규모<em></em></label>
							<div class="day">
								<div class="row align-items-end">
									<div class="col">
										<div class="ele-icon-box">					
											<form:input path="fundOperScale" maxLength="14"/>
										</div>
									</div>
									<div class="col flex-grow-0 white-space-nowrap">
										<span>억원</span>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-12">
						<div class="form-area-box1">
							<label>대표연락처<em></em></label>
							<div class="ele-icon-box">
								<form:input path="rprsTelno" maxLength="14" />
							</div>
						</div>
					</div>
					<div class="col-12">
						<div class="form-area-box1">
							<label>대표홈페이지</label>
							<div class="ele-icon-box">
								<form:input path="rprsHmpgAddr" maxLength="100" />
							</div>											
						</div>
					</div>
					<div class="col-12">
						<div class="form-area-box1-input2 ">
							<label>플랫폼 게시<em></em></label>
							<div class="ele-icon-box" id="appUseYn" data-value="<c:out value='${model.useYn}'/>"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<c:if test="${model.mode ne 'I'}">
			<div id="invstr-div" class="box" style="margin-bottom: 15px;">
				<div class="eletabletop1" data-v-2eb60c26="">
					<div class="row">
						<div class="col">
							<p class="txt2">
								<i class="icon-note"></i>조합원 목록
							</p>
						</div>
						<div class="col element">
							<a href="#" class="bs-s btn-black-ouline fs-12px h-auto px-4px py-2px" data-bs-toggle="modal" data-bs-target="#myModal1"><i class="icon-plus mr-3px"></i>추가</a>
						</div>
					</div>
				</div>
				<div class="table-box-1">
					<table id="invstr-table">
						<colgroup>
							<col width="*">
							<col width="*">
							<col width="*">
							<col width="*">
							<col width="40px">
						</colgroup>
						<thead class="bs-1 ts-s">
							<tr class="">
								<th>소속</th>
								<th>사업자번호</th>
								<th>대표자 </th>
								<th colspan="2">설립일</th>
							</tr>
						</thead>
						<tbody class="bs-1 ts-s t-t-c">
							<c:choose>
								<c:when test="${invstr eq null or invstr.size() < 1}">
									<tr>
										<td colspan="5">속해 있는 조합원이 없습니다.</td>
									</tr>
								</c:when>
								<c:otherwise>
									<c:forEach var="item" items="${invstr}">
										<tr>
											<td><c:out value="${item.invtBzentyNm}"/></td>
											<td><c:out value="${item.brno}"/></td>
											<td><c:out value="${item.rprsvNm}"/></td>
											<td><c:out value="${item.fndnYmd}"/></td>
											<td>
												<button class="btn-black bs-s deltMmbs" onclick="doDeltInvt(this);">삭제</button>
												<input type="hidden" name="sn" value="<c:out value='${item.sn}'/>"/>
											</td>
										</tr>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>
				</div>
			</div>
		</c:if>
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

<!-- MODAL CONTENT START -->
<div class="modal fade" id="myModal1" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	<form id="mmbsForm" name="mmbsForm" method="post" onsubmit="return false;">
	</form>
	<div class="modal-dialog custom-modal design1" role="document">
		<div class="modal-content" style="width: 531px;">
			<div class="modal-header">
				<p class="txt1">
					<i class="icon-copy-alt"></i>조합원 등록
					<button type="button" class="close" data-bs-dismiss="modal" aria-label="Close"><i class="icon-times"></i></button>
				</p>
			</div>
			<div class="modal-body">
				<form id="mmbsForm-nologin" name="mmbsForm" method="post" onsubmit="return false;">
					<input type="hidden" name="fundNo" />
					<input type="hidden" name="mode"  value="I" />
					<div class="input-grid-2">
						<div class="row">
							<div class="col-12">
								<p class="txt" style="text-align: left;">
									<i class="icon-note"></i>비회원 조합원 추가
								</p>
							</div>
							<div class="col-12">
								<div class="form-area-box1">
									<div class="input-grid-1">
										<div class="row">
											<div class="col-12">
												<div class="form-area-box1">
													<label>조합원명<em></em></label>
													<div class="ele-icon-box">
														<input id="invtBzentyNm" name="invtBzentyNm" maxLength="100" />
													</div>
												</div>
											</div>
											<div class="col-12 pb-8px">
												<div class="form-area-box1">
													<label>사업자 번호<em></em></label>
													<div class="day">
														<div class="row">
															<input id="brno" name="brno" type="hidden" />
															<div class="col col flex-b130px">
																<div class="ele-icon-box">
																	<input id="brno1" name="brno1" maxLength="3" />
																</div>
															</div>
															<div class="col wave"><span>-</span></div>
															<div class="col col flex-b100px">
																<div class="ele-icon-box">
																	<input id="brno2" name="brno2" maxLength="2" />
																</div>
															</div>
															<div class="col wave"><span>-</span></div>
															<div class="col">
																<div class="ele-icon-box">
																	<input id="brno3" name="brno3" maxLength="5" />
																</div>
															</div>
														</div>
													</div>
												</div>
												<div class="col box-btn" style="margin-top: 15px; text-align: right;">
													<a id="btnSaveInvtNoLogin" href="" class="btn-primary bs-m"><i class="icon-floppy-disk"></i>등록</a>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</form>
				<div class="input-grid-2">
					<div class="row">
						<div class="col">
							<div class="form-area-box1">
								<div class="input-btn">
									<div class="row">
										<div class="col">
											<div class="ele-icon-box">
												<input type="text" name="srchTextInvt" id="srchTextInvt" placeholder="내용입력">
											</div>
										</div>
										<div class="col box-btn">
											<button id="btnSearchInvt" class="btn-combi-1 bs-m ">검색</button>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="input-grid-2">
					<div class="row">
						<div class="col">
							<div class="form-area-box">
								<div style="height: 300px;">
									<table id="appGridMmbs" class="easyui-datagrid"></table>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer mt-16px">
				<div class="bottom-btn-box">
					<div class="row">
						<div class="col">
							<a href="" class="btn-combi-3 bs-m " data-bs-dismiss="modal"><i class="icon-times"></i>취소</a>
						</div>
						<div class="col">
							<a id="btnSaveInvt" href="" class="btn-primary bs-m"><i class="icon-floppy-disk"></i>등록</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- MODAL CONTENT END << ----------->

<%-- ############################ 내용 (종료) ############################ --%>
