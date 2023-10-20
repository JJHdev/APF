<%--
*******************************************************************************
***    명칭: modalApplyFund.jsp
***    설명: 지원하기 팝업
***
***    -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.04.22    LSH        First Coding.
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

<form id="applyForm" name="applyForm" method="post" onsubmit="return false;">
	<input type="hidden" id="p_mode" name="mode" value=""/>
	<div class="form-box-1">
		<div class="box">
			<div class="form-area-box">
				<label>투자자명</label>
				<div class="ele-icon-box">
					<input type="hidden" name="fundNo"       id="p_fundNo"       value="<c:out value="${model.fundNo      }"/>"/>
					<input type="text"   name="invtBzentyNm" id="p_invtBzentyNm" value="<c:out value="${model.invtBzentyNm}"/>" readonly="readonly"/>
					<input type="hidden" name="invtBzentyNo" id="p_invtBzentyNo" value="<c:out value="${model.invtBzentyNo}"/>"/>
				</div>
			</div>
		</div>
		<div class="box">
			<div class="form-area-box">
				<label>지원정보</label>
				<div class="ele-icon-box">
					<input type="text"   name="bzentyNm" id="p_bzentyNm" value="<c:out value="${model.bzentyNm}"/>" readonly="readonly"/>
					<input type="hidden" name="bzentyNo" id="p_bzentyNo" value="<c:out value="${model.bzentyNo}"/>"/>
				</div>
			</div>
		</div>
		<%-- 첨부파일영역 --%>
		<div class="box" id="p_appBizFile"></div>
	</div>
</form>

<div class="bottom-box-btn py-24px">
	<div class="row">
		<div class="col">
			<button type="button" id="p_btnSubmit" class="btn-primary w-100 bs-l"><i class="icon-check"></i>제출</button>
		</div>
		<div class="col">
			<button type="button" id="p_btnCancel" class="btn-combi-3 w-100 bs-l"><i class="icon-times F"></i>취소</button>
		</div>
	</div>
</div>

<%-- ############################# 내용 (종료) ############################# --%>

