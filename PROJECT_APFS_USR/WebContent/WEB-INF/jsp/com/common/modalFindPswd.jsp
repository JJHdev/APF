<%--
*******************************************************************************
***    명칭: modalFindPswd.jsp
***    설명: 비밀번호 찾기 팝업
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자                내용
*** ---------------------------------------------------------------------------
***    1.0      2023.07.28      LHB             First Coding.
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
	.find-simple					{ display: flex; }
	.find-simple > div				{ width: 50%; min-height: 100px; display: flex; align-items: center; flex-direction: column; justify-content: center; }
	.find-simple > div:first-child	{ margin-right: 5px; }
	.naver-logo, .kakao-logo		{ height: 30px; }
	
</style>

<script type="text/javascript" src="<c:url value='/js/com/common/modalFindPswd.js'/>"></script>

<form id="searchForm" name="searchForm" method="post" onsubmit="return false;">
	<input type="hidden" name="mode" value="FPSWD" />
	<div class="modal-body pt-0">
		<div class="form-box-1 border-bottom-0 mb-24px pb-0">
			<div class="box mb-24px">
				<div class="form-area-box">
					<label>사용자ID</label>
					<div class="ele-icon-box">
						<input type="text" name="userId" id="userId" placeholder="아이디를 입력하세요.">
					</div>
				</div>
			</div>
			<div class="box mb-24px">
				<div class="form-area-box">
					<label>이름</label>
					<div class="ele-icon-box">
						<input type="text" name="userNm" id="userNm" placeholder="이름을 입력하세요.">
					</div>
				</div>
			</div>
			<div class="box mb-24px">
				<div class="form-area-box">
					<label>휴대전화번호</label>
					<div class="ele-icon-box">
						<input type="text" name="mblTelno" id="mblTelno" placeholder="-제외 숫자만 입력하세요.">
					</div>
				</div>
			</div>
		</div>
		<div id="result-div" class="border-0 box mb-0 pb-0" style="display: none;">
			<div class="fs-16px fw-300 text-center text-red"><i class="icon-info-circle text-red"></i>일치하는 회원정보가 없습니다.</div>
			<div class="fs-16px fw-300 text-center">
				간편로그인의 아이디와 비밀번호는 사용하시는 소셜서비스에서 찾아주시길 바랍니다.
				<div class="find-simple">
					<div class="shadow-box-1">
						<div>
							<img class="naver-logo" src="<c:url value='/images/logo/NAVER_LOGO.png'/>" alt="네이버로고" />
						</div>
						<div>
							<a class="btn-primary bs-l" href="https://nid.naver.com/user2/help/idInquiry.nhn?menu=idinquiry" target="_blank">아이디찾기<i class="icon-arrow-right"  ></i></a>
							<a class="btn-primary bs-l" href="https://nid.naver.com/user2/help/pwInquiry.nhn?menu=pwinquiry" target="_blank">비밀번호 찾기<i class="icon-arrow-right"></i></a>
						</div>
					</div>
					<div class="shadow-box-1">
						<div>
							<img class="kakao-logo" src="<c:url value='/images/logo/KAKAO_LOGO.png'/>" alt="카카오로고" />
						</div>
						<div>
							<a class="btn-primary bs-l" href="https://accounts.kakao.com/weblogin/find_account_guide?continue=https%3A%2F%2Faccounts.kakao.com%2Fweblogin%2Faccount%2Finfo" target="_blank">아이디찾기<i class="icon-arrow-right"></i></a>
							<a class="btn-primary bs-l" href="https://accounts.kakao.com/weblogin/find_password?continue=https%3A%2F%2Faccounts.kakao.com%2Fweblogin%2Faccount%2Finfo"      target="_blank">비밀번호 찾기<i class="icon-arrow-right"></i></a>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="bottom-box-btn py-24px">
			<div class="row">
				<div class="col">
					<button id="btnSearch" class="btn-primary w-100 bs-l"><i class="icon-lock"></i>비밀번호 찾기</button>
				</div>
				<div class="col">
					<button id="btnClose"  class="btn-black w-100 bs-l"><i class="icon-times"></i>닫기</button>
				</div>
			</div>
		</div>
	</div>
</form>

<%-- ############################# 내용 (종료) ############################# --%>