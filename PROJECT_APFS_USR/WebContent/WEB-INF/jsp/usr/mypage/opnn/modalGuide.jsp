<%--
*******************************************************************************
***    명칭: modalGuide.jsp
***    설명: 마이페이지 - IR검토의견등록 - 작성가이드 팝업
***
***    -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.06.23    KYW        First Coding.
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

<div class="modal-body pt-0">
	<div class="form-box-1 border-bottom-0 mb-24px pb-0">
		<div class="designbox4 mb-24px">
			<div class="align-items-center row">
				<div class="col-12">
					<img src="<c:url value='/images/sub/MyPage5.png' />" alt="" />
				</div>
				<div class="col-12">
					<p class="txt1">현재는 투자에 대한 관심이 적은 경우라도 농식품경영체의 성장과 발전을 위해 성심성의껏 <br>작성해주실 것을 부탁드립니다.</p>										
				</div>
				
			</div>
		</div>
		
		<div class="border-0 box mb-24px pb-0">
			<div class="txt-lev-box-1">
				<p class="lev1 mb-8px">1. BUSINESS•PRODUCT•COMPANY</p>
				<p class="txt-dot mb-24px">아래의 핵심질문사항과 경영체의 발표내용 등을 고려하였을 때, 경쟁업체 대비 해당 경영체가 보유하고 있다고 판단되는 비교우위의 역량(강점), 개선 이 필요하거나 부족한 부분(약점) 등을 기술</p>
				<div class="p-16px bg-primary-t5">
					<p class="mb-16px tit-txt">
					 <i class="icon-question-circle mr-8px"></i>핵심질문사항
					</p>
					<p class="lev2 mb-8px">Business</p>
					<p class="txt-dot">(시장) 다양한 기회가 존재하며, 확장 가능성이 있는 목표시장을 보유하고 있는가</p>
					<p class="txt-dot">(전략) 목표시장에 잔출하여 성과를 창출할 수 있는 구체적 계획 등을 보유하고 있는가</p>
					<p class="txt-dot">(수익성) 현재 혹은 미래에 실현 가능한 수익모델(매출구조)을 갖고 있는가</p>
				</div>
			</div>
		</div>
		<div class="border-0 box mb-0 pb-0">
			<div class="txt-lev-box-1">
				<p class="lev1 mb-8px">2. 내용</p>
				<p class="txt-dot mb-24px">아래의 핵심질문사항과 경영체의 발표내용 등을 고려하였을 때, 경쟁업체 대비 해당 경영체가 보유하고 있다고 판단되는 비교우위의 역량(강점), 개선 이 필요하거나 부족한 부분(약점) 등을 기술</p>
				<div class="p-16px bg-primary-t5">
					<p class="mb-16px tit-txt">
					 <i class="icon-question-circle mr-8px"></i>핵심질문사항
					</p>
					<p class="lev2 mb-8px">Business</p>
					<p class="txt-dot">(시장) 다양한 기회가 존재하며, 확장 가능성이 있는 목표시장을 보유하고 있는가</p>
					<p class="txt-dot">(전략) 목표시장에 잔출하여 성과를 창출할 수 있는 구체적 계획 등을 보유하고 있는가</p>
					<p class="txt-dot">(수익성) 현재 혹은 미래에 실현 가능한 수익모델(매출구조)을 갖고 있는가</p>
				</div>
			</div>
		</div>
		

	</div>
	
</div>


<%-- ############################# 내용 (종료) ############################# --%>

