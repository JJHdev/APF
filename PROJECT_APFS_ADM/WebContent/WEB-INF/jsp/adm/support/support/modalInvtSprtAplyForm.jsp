<%--
*******************************************************************************
***    명칭: modalInvtSprtAplyForm.jsp
***    설명: 지원사업관리-신청현황관리 등록/수정 모달팝업 화면
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.06.07      LHB     First Coding.
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
	<form:hidden path="sprtAplyNo"	 /><%-- 지원신청번호 --%>
	<form:hidden path="sprtAplySeCd" /><%-- 지원신청구분 --%>
	<form:hidden path="prgrsSttsCd"	 /><%-- 처리상태 --%>
	<form:hidden path="mode"		 /><%-- 처리모드 --%>
	
	<div class="w-100">
		<!-- 컨텐츠시작 -->
		<div class="box mb-16px">
			<div class="box mb-24px">
				<div class="eletabletop1" data-v-2eb60c26="">
					<div class="row">
						<div class="col">
							<p class="txt2">
								<i class="icon-note"></i>신청정보</p>
						</div>
					</div>
				</div>
				<div class="box-in-box">
					<div class="input-grid-1">
						<div class="row">
							<div class="col-3">
								<div class="form-area-box1">
									<label>지원사업명</label>
									<div class="ele-icon-box">
										<form:input path="sprtAplySeCdNm" style="" disabled="true" />
									</div>
								</div>
							</div>
							<div class="col-3">
								<div class="form-area-box1">
									<label>프로그램분류</label>
									<div class="ele-icon-box">
										<form:input path="prgrmClsfCdNm" style="" disabled="true" />
									</div>
								</div>
							</div>
							<div class="col-3">
								<div class="form-area-box1">
									<label>프로그램명</label>
									<div class="ele-icon-box">
										<form:input path="prgrmNm" style="" disabled="true" />
									</div>
								</div>
							</div>
							<div class="col-3">
								<div class="form-area-box1">
									<label>처리결과</label>
									<div class="ele-icon-box">
										<form:input path="prgrsSttsCdNm" style="" disabled="true" />
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-3">
								<div class="form-area-box1">
									<label>경영체명</label>
									<div class="ele-icon-box">
										<form:input path="bzentyNm" style="" disabled="true" />
									</div>
								</div>
							</div>
							<div class="col-3">
								<div class="form-area-box1">
									<label>사업자번호</label>
									<div class="ele-icon-box">
										<form:input  path="brnoForm" style="" disabled="true" />
										<form:hidden path="brno"     style="" />
									</div>
								</div>
							</div>
							<div class="col-3">
								<div class="form-area-box1">
									<label>설립일</label>
									<div class="ele-icon-box">
										<form:input path="fndnYmd" style="" disabled="true" />
									</div>
								</div>
							</div>
							<div class="col-3">
								<div class="form-area-box1">
									<label>대표자명</label>
									<div class="ele-icon-box">
										<form:input path="rprsvNm" style="" disabled="true" />
									</div>
								</div>
							</div>
	
							<div class="col-3">
								<div class="form-area-box1">
									<label>임직원수(명)</label>
									<div class="ele-icon-box">
										<form:input path="empCnt" style="" disabled="true" />
									</div>
								</div>
							</div>
							<div class="col-3">
								<div class="form-area-box1">
									<label>법인등록번호</label>
									<div class="ele-icon-box">
										<form:input path="crno" id="crno" style="" disabled="true" />
									</div>
								</div>
							</div>
							<div class="col-3">
								<div class="form-area-box1">
									<label>생년월일</label>
									<div class="ele-icon-box">
										<form:input path="brdt" style="" disabled="true" />
									</div>
								</div>
							</div>
							<div class="col-3">
								<div class="form-area-box1-input2">
									<label>성별</label>
									<div class="ele-icon-box" id="appSexdstn" data-value="<c:out value='${model.sexdstn}'/>"></div>
								</div>
							</div>
							<div class="col-3">
								<div class="form-area-box1">
									<label>주소</label>
									<div class="ele-icon-box">
										<form:input path="lctnAddr" style="font-size: 12px;" disabled="true" class="" />
									</div>
								</div>
							</div>
							<div class="col-3">
								<div class="form-area-box1">
									<label>전화번호</label>
									<div class="ele-icon-box">
										<form:input path="rprsTelno" style="" disabled="true" />
									</div>
								</div>
							</div>
							<div class="col-3">
								<div class="form-area-box1">
									<label>기업유형</label>
									<div class="ele-icon-box">
										<form:hidden path="bzentyTypeCd"   style="" disabled="true" />
										<form:input  path="bzentyTypeCdNm" style="" disabled="true" />
									</div>
								</div>
							</div>
							<div class="col-3">
								<div class="form-area-box1">
									<label>FAX</label>
									<div class="ele-icon-box">
										<form:input path="fxno" style="" disabled="true" />
									</div>
								</div>
							</div>
							<div class="col-12">
								<div class="form-area-box1-input2 ">
									<label>사업분야</label>
									<div class="ele-icon-box justify-content-between" id="appBizFldBox" data-value="<c:out value='${model.bizFld}'/>"></div>
								</div>
							</div>
							<div class="col-6">
								<div class="form-area-box1">
									<label>홈페이지</label>
									<div class="ele-icon-box">
										<form:input path="hmpgAddr" style="" disabled="true" />
									</div>
								</div>
							</div>
							<div class="col-6">
								<div class="form-area-box1">
									<label>투자희망금액(백만원)</label>
									<div class="ele-icon-box">
										<form:input path="invtHopeAmt" style="" disabled="true" />
									</div>
								</div>
							</div>
							<div class="col-12">
								<div class="form-area-box1">
									<label>매출액</label>
									<div class="bb-0 bl-0 br-0 ele-icon-box round-1px">
										<div class="mt-13px table-box-1">
											<table class="fnnrAmt">
												<colgroup>
													<col width="141px">
													<col width="*">
												</colgroup>
												<thead class="bs-1 ts-s">
													<tr class="">
														<th>결산년도</th>
														<th>매출액 (원)</th>
													</tr>
												</thead>
												<tbody class="bs-1 px-v-m ts-s">
													<c:choose>
														<c:when test="${listSls.size() < 1}">
															<tr>
																<td colspan="2" class="app-c">매출액 정보가 없습니다.</td>
															</tr>
														</c:when>
														<c:otherwise>
															<c:forEach var="item" items="${listSls}">
																<tr>
																	<td class="app-c"><c:out value="${item.fnnrYear}"/></td>
																	<td><c:out value="${item.fnnrAmt}"/></td>
																</tr>
															</c:forEach>
														</c:otherwise>
													</c:choose>
												</tbody>
											</table>
										</div>
									</div>
								</div>
							</div>
							<div class="col-12">
								<div class="form-area-box1">
									<label>주요사업</label>
									<div class="ele-icon-box">
										<form:input path="mainBizCn" style="" disabled="true" />
									</div>
								</div>
							</div>
							<div class="col-12">
								<div class="form-area-box1">
									<label>핵심아이템</label>
									<div class="ele-icon-box">
										<form:input path="coreItmCn" style="" disabled="true" />
									</div>
								</div>
							</div>
							<div class="col-12">
								<div class="form-area-box1">
									<label>투자요소</label>
									<div class="ele-icon-box">
										<form:textarea path="bizCn" class="pt-4px" style="height: 86px;" disabled="true"></form:textarea>
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
				<div class="box-in-box pt-0" id="attachFile"></div>
			</div>
			<div class="col flex-grow-1 text-end">
				<a href="" class="btn-primary bs-m"  data-bs-toggle="modal" data-bs-target="#myModal8"><i class="fs-18px icon-plus mr-3px"></i>제출 서류 수정하기</a>
			</div>
			<c:if test="${listMxtr.size() > 0}">
				<div class="box mb-24px">
					<div class="eletabletop1" data-v-2eb60c26="">
						<div class="row">
							<div class="col">
								<p class="txt2">
									<i class="icon-note"></i>농식품 투자조합 정보
								</p>
							</div>
						</div>
					</div>
					<div class="box-in-box">
						<div class="input-grid-1">
							<c:forEach var="item" items="${listMxtr}">
								<div class="row">
									<div class="col-3">
										<div class="form-area-box1">
											<label>투자조합명</label>
											<div class="ele-icon-box">
												<input type="text" value="<c:out value='${item.invtMxtrNm}'/>" readonly="true" />
											</div>
										</div>
									</div>
									<div class="col-3">
										<div class="form-area-box1">
											<label>운용사명</label>
											<div class="ele-icon-box">
												<input type="text" value="<c:out value='${item.invstrNm}'/>" readonly="true" />
											</div>
										</div>
									</div>
									<div class="col-3">
										<div class="form-area-box1">
											<label>투자일자</label>
											<div class="ele-icon-box">
												<input type="text" value="<c:out value='${item.invtYmd}'/>" readonly="true" />
											</div>
										</div>
									</div>
									<div class="col-3">
										<div class="form-area-box1">
											<label>투자금액</label>
											<div class="ele-icon-box">
												<input type="text" value="<c:out value='${item.invtAmt}'/>" readonly="true" />
											</div>
										</div>
									</div>
								</div>
							</c:forEach>
						</div>
					</div>
				</div>
			</c:if>
			<c:if test="${model.aplyBzentyNo ne null}">
				<div class="box mb-24px">
					<div class="eletabletop1" data-v-2eb60c26="">
						<div class="row">
							<div class="col">
								<p class="txt2">
									<i class="icon-note"></i>담당자 정보
								</p>
							</div>
						</div>
					</div>
					<div class="box-in-box">
						<div class="input-grid-1">
							<div class="row">
								<div class="col-3">
									<div class="form-area-box1">
										<label>이름</label>
										<div class="ele-icon-box">
											<form:input path="picNm" style="" disabled="true" />
										</div>
									</div>
								</div>
								<div class="col-3">
									<div class="form-area-box1">
										<label>부서/직급</label>
										<div class="ele-icon-box">
											<form:input path="picDeptNm" style="" disabled="true" />
										</div>
									</div>
								</div>
								<div class="col-3">
									<div class="form-area-box1">
										<label>직통번호</label>
										<div class="ele-icon-box">
											<form:input path="picTelno" style="" disabled="true" />
										</div>
									</div>
								</div>
								<div class="col-3">
									<div class="form-area-box1">
										<label>E-mail</label>
										<div class="ele-icon-box">
											<form:input path="picEmlAddr" style="" disabled="true" />
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</c:if>
			<div class="box mb-24px">
				<div class="eletabletop1" data-v-2eb60c26="">
					<div class="row">
						<div class="col">
							<p class="txt2">
								<i class="icon-note"></i>지원사업 진행현황
							</p>
						</div>
					</div>
				</div>
				<div class="box-in-box pb-16px pt-0">
					<!-- <div class="table-box-1"> -->
					<div class="overtable">
						<div style="height: 200px;">
							<table id="grid-prgre" class="easyui-datagrid"></table>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- 컨텐츠종료 -->
	</div>
</form:form>
<!-- FORM END -->

<div class="bottom-btn-box">
	<div class="row">
		<c:if test="${model.prgrsSttsCd eq 'B10'}">
			<div class="col flex-grow-0 white-space-nowrap">
				<a href="" class="bs-l btn-primary px-14px"  data-bs-toggle="modal" data-bs-target="#myModal1"><i class="icon-check-double"></i>상담완료</a>
			</div>
		</c:if>
		<c:if test="${model.prgrsSttsCd eq 'B20'}">
			<div class="col flex-grow-0 white-space-nowrap">
				<a href="" class="bs-l btn-primary  px-14px" data-bs-toggle="modal" data-bs-target="#myModal2"><i class="icon-edit"></i>진행현황등록</a>
			</div>
		</c:if>
		<c:if test="${model.prgrsSttsCd eq 'A10' or model.prgrsSttsCd eq 'C10'}">
			<div class="col flex-grow-0 white-space-nowrap">
				<a href="" class="bs-l btn-primary  px-14px" data-bs-toggle="modal" data-bs-target="#myModal3"><i class="icon-edit"></i>반려</a>
			</div>
			<div class="col flex-grow-0 white-space-nowrap">
				<a href="" class="bs-l btn-primary  px-14px" data-bs-toggle="modal" data-bs-target="#myModal4"><i class="icon-edit"></i>보완요청</a>
			</div>
			<div class="col flex-grow-0 white-space-nowrap">
				<a href="" class="bs-l btn-lavendar px-14px" data-bs-toggle="modal" data-bs-target="#myModal5"><i class="icon-calendar-minus"></i>심사완료</a>
			</div>
		</c:if>
		<c:if test="${model.prgrsSttsCd eq 'B20' or model.prgrsSttsCd eq 'A40'}">
			<div class="col flex-grow-0 white-space-nowrap">
				<a href="" class="bs-l btn-lavendar px-14px" data-bs-toggle="modal" data-bs-target="#myModal6"><i class="icon-calendar-minus"></i>사업종료</a>
			</div>
		</c:if>
		<div class="col flex-grow-1 text-end">
			<a href="" class="bs-l btn-black" id="btnRemove"><i class="icon-trash"></i>신청서 삭제</a>
		</div>
	</div>
</div>

<!-- 모달 영역 -->
<!-- 상담완료 -->
<div class="modal fade" id="myModal1" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	<form id="prgrsForm1" name="prgrsForm" method="post" onsubmit="return false;">
		<input type="hidden" name="sprtAplyNo"        value=""/>
		<input type="hidden" name="sprtAplySeCd"      value=""/>
		<input type="hidden" name="prgrmSeCd"         value="SB01"/>
		<input type="hidden" name="prgrsSttsCd"       value="20"/>
		<input type="hidden" name="prgrsDetailSttsCd" value="40"/>
		<div class="modal-dialog custom-modal design1" role="document">
			<div class="modal-content" style="width: 617px;">
				<div class="modal-header">
					<p class="txt1">
						<i class="icon-copy-alt"></i>상담완료
						<button type="button" class="close" data-bs-dismiss="modal" aria-label="Close"><i class="icon-times"></i></button>
					</p>
				</div>
				<div class="modal-body">
					<div class="input-grid-2">
						<div class="row">
							<div class="col">
								<div class="form-area-box">
									<label>상담센터<em></em></label>
									<div class="ele-icon-box">
										<select name="dscsnCntrCd" id="dscsnCntrCd"></select>
										<i class="icon-angle-down"></i>
									</div>
								</div>
							</div>
							<div class="col">
								<div class="form-area-box">
									<label>상담담당자<em></em></label>
									<div class="ele-icon-box">
										<input type="text" name="picNm" id="picNm" maxlength="20" style="">
										<i class="icon-times-circle F text-ele-TextDisable"></i>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col">
								<div class="form-area-box">
									<label>상담일<em></em></label>
									<div class="ele-icon-box">
										<input type="text" name="prcsYmd" id="prcsYmd1" style="" placeholder="날짜를 선택하세요" class="datepicker-input">
										<i class="icon-calendar"></i>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-12">
								<div class="form-area-box">
									<label>상담내용<em></em></label>
									<div class="ele-icon-box">
										<textarea name="prgrsCn" id="prgrsCn" class="" maxlength="300" style="height: 78px;"></textarea>
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
								<a href="" class="btn-combi-3 bs-m " data-bs-dismiss="modal"><i class="icon-times"></i>취소</a>
							</div>
							<div class="col">
								<a href="" id="myModalSave1" class="btn-primary bs-m"><i class="icon-floppy-disk"></i>저장</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
</div>
<!-- 진행현황등록 -->
<div class="modal fade" id="myModal2" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	<form id="prgrsForm2" name="prgrsForm" method="post" onsubmit="return false;">
		<input type="hidden" name="sprtAplyNo"        value=""/>
		<input type="hidden" name="sprtAplySeCd"      value=""/>
		<input type="hidden" name="prgrsSttsCd"       value="20"/>
		<div class="modal-dialog custom-modal design1" role="document">
			<div class="modal-content" style="width: 617px;">
				<div class="modal-header">
					<p class="txt1">
						<i class="icon-copy-alt"></i>진행현황등록
						<button type="button" class="close" data-bs-dismiss="modal" aria-label="Close"><i class="icon-times"></i></button>
					</p>
				</div>
				<div class="modal-body">
					<div class="input-grid-2">
						<div class="row">
							<div class="col">
								<div class="form-area-box">
									<label>프로그램명<em></em></label>
									<div class="ele-icon-box">
										<select name="prgrmSeCd" id="prgrmSeCd"></select>
										<i class="icon-angle-down"></i>
									</div>
								</div>
							</div>
							<div class="col">
								<div class="form-area-box">
									<label>진행상태<em></em></label>
									<div class="ele-icon-box">
										<select name="prgrsDetailSttsCd" id="prgrsDetailSttsCd"></select>
										<i class="icon-angle-down"></i>
									</div>
								</div>
							</div>
						</div>
						<div class="row event">
							<div class="col">
								<div class="form-area-box">
									<label>투자설명회명<em></em></label>
									<div class="ele-icon-box" id="eventDiv">
										<input type="text" name="evntNm" id="evntNm" value=""/>
										<input type="hidden" name="evntNo" id="evntNo"/>
									</div>
								</div>
							</div>
						</div>
						<div class="row prcsCycl">
							<div class="col">
								<div class="form-area-box">
									<label>차수<em></em></label>
									<div class="ele-icon-box">
										<select name="prcsCycl" id="prcsCycl"></select>
										<i class="icon-angle-down"></i>
									</div>
								</div>
							</div>
						</div>
						<div class="row ftfYn">
							<div class="col">
								<div class="form-area-box">
									<label>대면여부<em></em></label>
									<div class="ele-icon-box">
										<select name="ftfYn" id="ftfYn">
											<option value="">선택</option>
											<option value="Y">대면</option>
											<option value="N">비대면</option>
										</select>
										<i class="icon-angle-down"></i>
									</div>
								</div>
							</div>
						</div>
						<div class="row fixed-row">
							<div class="col">
								<div class="form-area-box">
									<label>담당자<em></em></label>
									<div class="ele-icon-box">
										<input type="text" name="picNm" id="" maxlength="20" style="" />
										<i class="icon-times-circle F text-ele-TextDisable"></i>
									</div>
								</div>
							</div>
							<div class="col">
								<div class="form-area-box">
									<label>담당자 연락처<em></em></label>
									<input id="picTelnoModal2"  name="picTelnoModal2"  type="hidden"/>
									<div class="day">
										<div class="row">
											<div class="col"><div class="ele-icon-box"><input id="picTelnoModal21" name="picTelnoModal21" type="text" maxlength="4" style="font-size: 11px;"/></div></div>
											<div class="col wave"><span>-</span></div>
											<div class="col"><div class="ele-icon-box"><input id="picTelnoModal22" name="picTelnoModal22" type="text" maxlength="4" style="font-size: 11px;"/></div></div>
											<div class="col wave"><span>-</span></div>
											<div class="col"><div class="ele-icon-box"><input id="picTelnoModal23" name="picTelnoModal23" type="text" maxlength="4" style="font-size: 11px;"/></div></div>
										</div>
									</div>
								</div>
							</div>
							<div class="col">
								<div class="form-area-box">
									<label>처리일자<em></em></label>
									<div class="ele-icon-box">
										<input type="text" name="prcsYmd" id="prcsYmd2" style="" placeholder="날짜를 선택하세요" class="datepicker-input">
										<i class="icon-calendar"></i>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-12">
								<div class="form-area-box">
									<label>코멘트<em></em></label>
									<div class="ele-icon-box">
										<textarea name="prgrsCn" class="" maxlength="300" style="height: 78px;"></textarea>
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
								<a href="" class="btn-combi-3 bs-m " data-bs-dismiss="modal"><i class="icon-times"></i>취소</a>
							</div>
							<div class="col">
								<a href="" id="myModalSave2" class="btn-primary bs-m"><i class="icon-floppy-disk"></i>저장</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
</div>
<!-- 반려 -->
<div class="modal fade" id="myModal3" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	<form id="prgrsForm3" name="prgrsForm" method="post" onsubmit="return false;">
		<input type="hidden" name="sprtAplyNo"   value=""/>
		<input type="hidden" name="sprtAplySeCd" value=""/>
		<input type="hidden" name="prgrsSttsCd"  value="20"/>
		<div class="modal-dialog custom-modal design1" role="document">
			<div class="modal-content" style="width: 531px;">
				<div class="modal-header">
					<p class="txt1">
						<i class="icon-copy-alt"></i> 반려
						<button type="button" class="close" data-bs-dismiss="modal" aria-label="Close"><i class="icon-times"></i></button>
					</p>
				</div>
				<div class="modal-body">
					<div class="input-grid-2">
						<div class="row">
							<div class="col-12">
								<div class="form-area-box">
									<label>코멘트<em></em></label>
									<div class="ele-icon-box">
										<textarea name="prgrsCn" class="" style="height: 78px;"></textarea>
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
								<a href="" class="btn-combi-3 bs-m " data-bs-dismiss="modal"><i class="icon-times"></i>취소</a>
							</div>
							<div class="col">
								<a href="" id="myModalSave3" class="btn-primary bs-m"><i class="icon-floppy-disk"></i>저장</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
</div>
<!-- 보완요청 -->
<div class="modal fade" id="myModal4" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	<form id="prgrsForm4" name="prgrsForm" method="post" onsubmit="return false;">
		<input type="hidden" name="sprtAplyNo"   value=""/>
		<input type="hidden" name="sprtAplySeCd" value=""/>
		<input type="hidden" name="prgrsSttsCd"  value="30"/>
		<div class="modal-dialog custom-modal design1" role="document">
			<div class="modal-content" style="width: 531px;">
				<div class="modal-header">
					<p class="txt1">
						<i class="icon-copy-alt"></i> 보완요청
						<button type="button" class="close" data-bs-dismiss="modal" aria-label="Close"><i class="icon-times"></i></button>
					</p>
				</div>
				<div class="modal-body">
					<div class="input-grid-2">
						<div class="row">
							<div class="col-12">
								<div class="form-area-box">
									<label>코멘트<em></em></label>
									<div class="ele-icon-box">
										<textarea name="prgrsCn" class="" style="height: 78px;"></textarea>
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
								<a href="" class="btn-combi-3 bs-m " data-bs-dismiss="modal"><i class="icon-times"></i>취소</a>
							</div>
							<div class="col">
								<a href="" id="myModalSave4" class="btn-primary bs-m"><i class="icon-floppy-disk"></i>저장</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
</div>
<!-- 심사완료 -->
<div class="modal fade" id="myModal5" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	<form id="prgrsForm5" name="prgrsForm" method="post" onsubmit="return false;">
		<input type="hidden" name="sprtAplyNo"   value=""/>
		<input type="hidden" name="sprtAplySeCd" value=""/>
		<input type="hidden" name="prgrsSttsCd"  value="40"/>
		<div class="modal-dialog custom-modal design1" role="document">
			<div class="modal-content" style="width: 531px;">
				<div class="modal-header">
					<p class="txt1">
						<i class="icon-copy-alt"></i> 심사완료
						<button type="button" class="close" data-bs-dismiss="modal" aria-label="Close"><i class="icon-times"></i></button>
					</p>
				</div>
				<div class="modal-body">
					<div class="input-grid-2">
						<div class="row">
							<div class="col">
								<div class="form-area-box">
									<label>담당자<em></em></label>
									<div class="ele-icon-box">
										<input type="text" name="picNm" id="" style="">
										<i class="icon-times-circle F text-ele-TextDisable"></i>
									</div>
								</div>
							</div>
							<div class="col">
								<div class="form-area-box">
									<label>담당자 연락처<em></em></label>
									<input id="picTelnoModal5"  name="picTelnoModal5"  type="hidden"/>
									<div class="day">
										<div class="row">
											<div class="col"><div class="ele-icon-box"><input id="picTelnoModal51" name="picTelnoModal51" type="text" maxlength="4" style="font-size: 12px;"/></div></div>
											<div class="col wave"><span>-</span></div>
											<div class="col"><div class="ele-icon-box"><input id="picTelnoModal52" name="picTelnoModal52" type="text" maxlength="4" style="font-size: 12px;"/></div></div>
											<div class="col wave"><span>-</span></div>
											<div class="col"><div class="ele-icon-box"><input id="picTelnoModal53" name="picTelnoModal53" type="text" maxlength="4" style="font-size: 12px;"/></div></div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-12">
								<div class="form-area-box">
									<label>코멘트<em></em></label>
									<div class="ele-icon-box">
										<textarea name="prgrsCn" class="" style="height: 78px;"></textarea>
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
								<a href="" class="btn-combi-3 bs-m " data-bs-dismiss="modal"><i class="icon-times"></i>취소</a>
							</div>
							<div class="col">
								<a href="" id="myModalSave5" class="btn-primary bs-m"><i class="icon-floppy-disk"></i>저장</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
</div>
<!-- 사업종료 -->
<div class="modal fade" id="myModal6" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	<form id="prgrsForm6" name="prgrsForm" method="post" onsubmit="return false;">
		<input type="hidden" name="sprtAplyNo"   value=""/>
		<input type="hidden" name="sprtAplySeCd" value="" />
		<input type="hidden" name="prgrsSttsCd"  value="90"/>
		<div class="modal-dialog custom-modal design1" role="document">
			<div class="modal-content" style="width: 404px;">
				<div class="modal-header">
					<p class="txt1">
						<i class="icon-copy-alt"></i>사업종료
						<button type="button" class="close" data-bs-dismiss="modal" aria-label="Close"><i class="icon-times"></i></button>
					</p>
				</div>
				<div class="modal-body">
					<div class="input-grid-2">
						<div class="row">
							<div class="col">
								<div class="form-area-box">
									<label>지원사업 완료일자<em></em></label>
									<div class="ele-icon-box">
										<input type="text" name="prcsYmd" id="" style="" placeholder="날짜를 선택하세요" class="datepicker-input">
										<i class="icon-calendar"></i>
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
								<a href="" class="btn-combi-3 bs-m " data-bs-dismiss="modal"><i class="icon-times"></i>취소</a>
							</div>
							<div class="col">
								<a href="" id="myModalSave6" class="btn-primary bs-m"><i class="icon-floppy-disk"></i>저장</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
</div>
<!-- 투자설명회 -->
<div class="modal fade" id="myModal7" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	<form id="prgrsForm7" name="prgrsForm" method="post" onsubmit="return false;">
		<div class="modal-dialog custom-modal design1" role="document">
			<div class="modal-content" style="width: 617px;">
				<div class="modal-header">
					<p class="txt1">
						<i class="icon-copy-alt"></i>투자설명회 검색
						<button type="button" class="close" data-bs-dismiss="modal" aria-label="Close"><i class="icon-times"></i></button>
					</p>
				</div>
				<div class="modal-body">
					<div class="input-grid-2">
						<div class="row">
							<div class="col">
								<div class="form-area-box">
									<label>투자설명회명<em></em></label>
									<div class="ele-icon-box">
										<input type="text" name="srchText" id="" style="" placeholder="투자설명회명을 입력하세요." class="">
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
									<div style="height: 200px;">
										<table id="grid-event" class="easyui-datagrid"></table>
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
								<a href="" class="btn-combi-3 bs-m " data-bs-dismiss="modal"><i class="icon-times"></i>취소</a>
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

<!-- 지원서비스 신청현황관리 2023.09.19 제출서류 수정하기 로직  / JH -->
<div class="modal fade" id="myModal8" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	<form id="prgrsForm8" name="prgrsForm" method="post" onsubmit="return false;">
		<div class="modal-dialog custom-modal design1" role="document">
			<div class="modal-content" style="width: 1217px; transform: scale(0.9);">
				<div class="modal-header">
					<p class="txt1">
						<i class="icon-copy-alt"></i>제출서류 수정하기
						<button type="button" class="close" data-bs-dismiss="modal" aria-label="Close"><i class="icon-times"></i></button>
					</p>
				</div>
				<div class="modal-body">
					<div class="input-grid-2">
						<div class="row">
							<div class="col">
								<div class="form-area-box1">
									<div id="appContent"></div>
									<div id="appButtons" class="bottom-box-btn"></div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
</div>

<%-- ############################ 내용 (종료) ############################ --%>
