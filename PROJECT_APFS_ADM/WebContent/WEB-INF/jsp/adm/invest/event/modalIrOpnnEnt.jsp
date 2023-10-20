<%--
*******************************************************************************
***    명칭: modalIrOpnn.jsp
***    설명: 투자정보관리 - IR검토의견서관리 - 경영체별 모달
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

<style>
	td { word-wrap: break-word; }
</style>

<!-- IR 투자자 검토의견서 (경영체별) Modal -->
<form id="modalForm2" name="modalForm2" method="post" onsubmit="return false;">
	<input type="hidden" name="evntType" id="evntType" value="ENT" />
	<input type="hidden" name="evntNo"   id="evntNo"               />
	<input type="hidden" name="mode"     id="mode"     value="I"   />
	
	<div class="modal-body" style="max-height: 600px;">
		<div class="input-grid-1 mb-8px">
			<div class="row ">
				<div class="col">
					<div class="form-area-box-input">
						<label>경영체명</label>
						<div class="ele-icon-box">
							<div class="value-box text-start bzentyNm">
								<c:out value="${model.get(0).bzentyNm}"/>
							</div>
						</div>
					</div>
				</div>
				<div class="col">
					<div class="form-area-box-input">
						<label>행사명</label>
						<div class="ele-icon-box">
							<div class="value-box text-start evntNm">
								<c:out value="${model.get(0).evntNm}"/>
							</div>
						</div>
					</div>
				</div>									
			</div>
		</div>
		<div class="table-box-1 overflow-scroll" style="max-height: 400px;">
			<table id="irOpnnList" class="">
				<colgroup>
					<col width="*">
					<col width="*">
					<col width="*">
					<col width="*">
					<col width="*">
					<col width="*">
					<col width="80px">
					<col width="80px">
				</colgroup>
				<thead class="bs-1 ts-s py-v-m">
					<tr class="">
						<th>투자운용사명</th>
						<th>작성자명</th>
						<th>BUSINESS</th>
						<th>PRODUCT<br/>(TECH)</th>
						<th>COMPANY</th>
						<th>종합의견</th>
						<th>투자관심도</th>
						<th>후속미팅의향</th>
					</tr>
				</thead>
				<tbody class="bs-1 px-v-s ts-s t-t-c">
					<c:forEach var="item" items="${model}">
						<tr>
							<td>
								<c:out value="${item.bzentyInvtNm}"/>
							</td>
							<td>
								<c:out value="${item.userNmNo}"/>
							</td>
							<td>
								<c:out value="${item.bizCn}"/>
							</td>
							<td>
								<c:out value="${item.prdctCn}"/>
							</td>
							<td>
								<c:out value="${item.coCn}"/>
							</td>
							<td>
								<c:out value="${item.gnrlzOpnn}"/>
							</td>
							<td>
								<c:out value="${item.invtItrstDgreeCd}"/>
							</td>
							<td>
								<c:out value="${item.fllwMtgIntenYn}"/>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</form>
<!-- MODAL CONTENT END << ----------->

<%-- ############################# 내용 (종료) ############################# --%>