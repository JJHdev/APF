<%--
*******************************************************************************
***    명칭: modalIrOpnn.jsp
***    설명: 모달 - 투자자별
***
***    -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.06.26      LHB     First Coding.
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

<%-- ############################# 내용 (시작) ############################# --%>

<form:form commandName="model" id="modalForm1" name="modalForm1" method="post" enctype="multipart/form-data" onsubmit="return false;">
	<form:hidden path="evntNo"			/> <%-- 행사번호		--%>
	<form:hidden path="bzentyNo"		/> <%-- 업체번호		--%>
	<form:hidden path="userNo"			/> <%-- 사용자번호		--%>
	<form:hidden path="evntPartcptnNo"	/> <%-- 행사참여번호	--%>
	<form:hidden path="page"			/> <%-- 페이지		--%>
	
	<div class="modal-body">
		<div class="designbox1 mb-8px">
			<div class="row align-items-center">
				<div class="col-12 col-md input-grid-1">
					<div class="row">
						<div class="col-12">
							<div class="form-area-box-input">
								<label>행사명</label>
								<div class="ele-icon-box">
									<div class="value-box">
										<c:out value="${model.evntNm}"/>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-12">
							<div class="form-area-box-input">
								<label>경영체</label>
								<div class="ele-icon-box">
									<div class="value-box">
										<c:out value="${model.bzentyNm}"/>
									</div>
								</div>
								
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-6">
							<div class="form-area-box-input">
								<label>운용사명</label>
								<div class="ele-icon-box">
									<div class="value-box">
										<c:out value="${model.bzentyInvtNm}"/>
									</div>
								</div>
							</div>
						</div>
						<div class="col-6">
							<div class="form-area-box-input">
								<label>작성자</label>
								<div class="ele-icon-box">
									<div class="value-box">
										<c:out value="${model.rgtrNm}"/>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="col-12 col-md flex-b-md-174px">
					<div class="designbox">
						<p><c:out value="${page}"/>/<c:out value="${total}"/></p>
						<div class="row">
							<div class="col">
								<c:if test="${page > 1}">
									<button onclick="loadIrOpnnInvt(<c:out value='${page-1}'/>)"><i class="icon-angle-left"></i></button>
								</c:if>
							</div>
							<div class="col">
								<c:if test="${page < total}">
									<button onclick="loadIrOpnnInvt(<c:out value='${page+1}'/>)"><i class="icon-angle-right"></i></button>
								</c:if>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="table-box-1 overflow-hidden">
			<table class="">
				<colgroup>
					<col width="162px">
					<col width="*">
				</colgroup>
				<thead class="bs-1 ts-m">
					<tr class="px-v-xl">
						<th>검토항목</th>
						<th class="text-start">검토의견</th>
					</tr>
				</thead>
				<tbody class="bs-1 px-v-xl ts-s t-t-l">
					<tr class="">													
						<td class="fw-600">BUSINESS</td>
						<td>
							<c:out value="${model.bizCn}"/>
						</td>													
					</tr>
					<tr>													
						<td class="fw-600">PRODUCT (TECH)</td>
						<td>
							<c:out value="${model.prdctCn}"/>
						</td>													
					</tr>
					<tr>													
						<td class="fw-600">COMPANY</td>
						<td>
							<c:out value="${model.coCn}"/>
						</td>													
					</tr>
					<tr>													
						<td class="fw-600">종합의견</td>
						<td>
							<c:out value="${model.gnrlzOpnn}"/>
						</td>													
					</tr>
					<tr>													
						<td class="fw-600">투자관심도</td>
						<td>
							<div class="row justify-content-between">
								<div class="col">
									<div class="check-radio-box">
										<form:radiobutton path="invtItrstDgreeCd" id="invtItrstDgreeCd1" value="1" onclick="return false;"/>
										<label for="invtItrstDgreeCd1">1</label>
									</div>
								</div>
								<div class="col">
									<div class="check-radio-box">
										<form:radiobutton path="invtItrstDgreeCd" id="invtItrstDgreeCd2" value="2" onclick="return false;"/>
										<label for="invtItrstDgreeCd2">2</label>
									</div>
								</div>
								<div class="col">
									<div class="check-radio-box">
										<form:radiobutton path="invtItrstDgreeCd" id="invtItrstDgreeCd3" value="3" onclick="return false;"/>
										<label for="invtItrstDgreeCd3">3</label>
									</div>
								</div>
								<div class="col">
									<div class="check-radio-box">
										<form:radiobutton path="invtItrstDgreeCd" id="invtItrstDgreeCd4" value="4" onclick="return false;"/>
										<label for="invtItrstDgreeCd4">4</label>
									</div>
								</div>
								<div class="col">
									<div class="check-radio-box">
										<form:radiobutton path="invtItrstDgreeCd" id="invtItrstDgreeCd5" value="5" onclick="return false;"/>
										<label for="invtItrstDgreeCd5">5</label>
									</div>
								</div>
							</div>
						</td>													
					</tr>
					<tr>													
						<td class="fw-600">후속미팅 의향</td>
						<td>
							<div class="row">
								<div class="col ">
									<div class="check-radio-box">
										<form:radiobutton path="fllwMtgIntenYn" id="fllwMtgIntenYn1" value="Y" onclick="return false;"/>
										<label for="fllwMtgIntenYn1">Yes</label>
									</div>
								</div>
								<div class="col ">
									<div class="check-radio-box">
										<form:radiobutton path="fllwMtgIntenYn" id="fllwMtgIntenYn2" value="N" onclick="return false;"/>
										<label for="fllwMtgIntenYn2">No</label>
									</div>
								</div>
								<div class="col "></div>
								<div class="col "></div>
								<div class="col "></div>
							</div>
						</td>													
					</tr>

				</tbody>
			</table>
		</div><div class="bottom-box-btn pt-24px">
			<div class="row">
				<div class="col">
					<a href="#" onclick="P_MODAL_INVT.close();" class="bs-m btn-primary w-100" aria-label="Close"><i class="icon-times"></i>닫기</a>
				</div>
			</div>
		</div>
	</div>
</form:form>

<%-- ############################# 내용 (종료) ############################# --%>