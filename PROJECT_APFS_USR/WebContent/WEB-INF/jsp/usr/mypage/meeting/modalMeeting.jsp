<%--
*******************************************************************************
***    명칭: modalMeeting.jsp
***    설명: 미팅신청 팝업
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.05.16    LSH        First Coding.
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

<form id="meetingForm" name="meetingForm" method="post" onsubmit="return false;">
	<input type="hidden" id="p_mode" name="mode" value=""/>
	<div class="form-box-2">
		<div class="box">
			<div class="form-area-box">
				<label>받는 사람</label>
				<div class="ele-icon-box disabled">
					<input type="text"   name="trgtBzentyNm" id="p_trgtBzentyNm" value="<c:out value="${model.trgtBzentyNm}"/>" disabled />
					<input type="hidden" name="trgtBzentyNo" id="p_trgtBzentyNo" value="<c:out value="${model.trgtBzentyNo}"/>"/>
					<input type="hidden" name="aplcntNo"     id="p_aplcntNo"     value="<c:out value="${model.gsUserNo    }"/>"/>
				</div>
			</div>
		</div>
		<div class="box">
			<div class="form-area-box">
				<label>내용</label>
				<div class="ele-icon-box h-auto">
					<textarea name="aplyCn" id="p_aplyCn" maxlength="200"></textarea>
				</div>
				<div class="bottom-lable">
					<div class="row">
						<div class="col"></div>
						<div class="col"><p id="p_aplyCnLength">0 / 200</p></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</form>

<div class="bottom-box-btn py-24px">
	<div class="row">
		<div class="col">
			<button id="p_btnSubmit" class="btn-primary w-100 bs-l"><i class="icon-send"></i>보내기</button>
		</div>
		<div class="col">
			<button id="p_btnCancel" class="btn-combi-3 w-100 bs-l"><i class="icon-times"></i>취소</button>
		</div>
	</div>
</div>
			
<%-- ############################ 내용 (종료) ############################ --%>

