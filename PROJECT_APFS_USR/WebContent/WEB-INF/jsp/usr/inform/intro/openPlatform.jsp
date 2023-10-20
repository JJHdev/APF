<%--
*******************************************************************************
***    명칭: openPlatform.jsp
***    설명: 플랫폼소개 - 플랫폼 소개 화면
***
***    -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        		     수정자       	     내용
*** ---------------------------------------------------------------------------
***    1.0      2023.07.14     J H        작업완료.
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
<div class="border-bottom-0 position-relative p-8px shadow-box-1 mb-32px" style="z-index: 10;">
	<div class="capsule-box">
		<div class="row">
			<div class="col active fs-md-16px lh-md-38px div-tab" style="border-bottom-right-radius: 0px; border-bottom-left-radius: 0px;">
				주요기능 소개
			</div>
			<div class="col fs-md-16px lh-md-38px div-tab" style="border-bottom-right-radius: 0px; border-bottom-left-radius: 0px;">
				BI 소개
			</div>
		</div>
	</div>
</div>

<div id="div1" class="contentDiv">
	<div class="only-view-design1">
		<div class="box1 num1">
			<div class="txtbox">
				<label>Action 1</label>
				<p class="txt1">투자자에게 직접 사업을 홍보하고<br/>
					투자제안을 해보세요!</p>
				<p class="txt2">간단한 사업정보를 입력하면 공공기관 등이 보유하고 있는 공공데이터*를 자동 매칭하여<br/>
					손쉽게 투자제안서를 작성하고 투자자에게 직접 투자제안도 할 수 있어요
					<span>* 투자정보, 핵심기술, R&D기술인증, 재무제표, 특허상표권, 주요인력 등</span>
				</p>
				<div class="tip">
					<span class="">
						<img src="<c:url value="/images/sub/Introduction-icom16.png"/>" alt="">
					</span>
					<p class="txt3"><span>TIP</span>
						플랫폼에 가입된 경영체에 대해서는 투자자가 경영체 정보를 검색할 수 있으며, 투자제안 및 미팅 등을 신청할 수 있어요
					</p>
				</div>
			</div>
			<img src="<c:url value="/images/sub/Introduction-img18.png"/>" alt="" class="img1">
			<img src="<c:url value="/images/sub/Introduction-num1.svg"/>" alt="" class="img2">
		</div>
		<div class="box1 num2">
			<div class="txtbox">
				<label>Action 2</label>
				<p class="txt1">다양한 지원사업을 한 눈에 확인하고<br/>
					나에게 꼭 맞는 지원사업을 검색해 보세요!</p>
				<p class="txt2">농식품 창업·투자 지원사업 공고를 확인하고 농식품경영체의 사업분야, 연령, 창업기간 등에 따른맞춤형 지원 사업을 필터링하여 검색할 수 있어요</p>
				
				<div class="tip">
					<span class="">
						<img src="<c:url value="/images/sub/Introduction-icom16.png"/>" alt="">
					</span>
					<p class="txt3"><span>TIP</span>
						농업정책보험금융원에서 주관하는 지원사업은 플랫폼에서 직접 신청이 가능하고 마이페이지에서 진행현황을 확인/관리할 수 있어요.
					</p>
	
				</div>
			</div>
			<img src="<c:url value="/images/sub/Introduction-img19.png"/>"  alt="" class="img1">
			<img src="<c:url value="/images/sub/Introduction-num2.svg"/>"   alt="" class="img2">
		</div>
		<div class="box1 num3">
			<div class="txtbox">
				<label>Action 3</label>
				<p class="txt1">경영체 별 핵심 사업아이템을 확인하고<br/>
					농식품분야 예비 유니콘 기업을 찾아보세요!</p>
				<p class="txt2">농림수산식품모태펀드 투자분야, 투자대상 별로 경영체를 검색하고 경영체의 투자 라운드* 를 확인하여 투자 검토를 할 수 있어요
					<span>* 시드단계, 시리즈A, 시리즈B, 시리즈C 등</span>
				</p>
				<div class="tip">
					<span class="">
						<img src="<c:url value="/images/sub/Introduction-icom16.png"/>" alt="">
					</span>
					<p class="txt3"><span>TIP</span>
						플랫폼에 가입한 농림수산식품모태펀드 운용사(VC)는 경영체가 플랫폼에 <br/>업로드한 사업계획서를 언제나 볼 수 있어요
					</p>
	
				</div>
			</div>
			<img src="<c:url value="/images/sub/Introduction-img20.png"/>" alt="" class="img1">
			<img src="<c:url value="/images/sub/Introduction-num3.svg"/>" alt="" class="img2">
		</div>
		<div class="box1 num4">
			<div class="txtbox">
				<label>Action 4</label>
				<p class="txt1">농식품 산업 동향 · 유망 성장 분야를 파악하고<br/>
					우수경영체 정보를 확인 해보세요!</p>
				<p class="txt2">농협, 한국농촌경제연구원 등 금융·연구기관이 생산한 산업동향·우수경영체 분석보고서와 농정이슈를 확인할 수 있어요
				</p>
			</div>
			<img src="<c:url value="/images/sub/Introduction-img21.png"/>" alt="" class="img1">
			<img src="<c:url value="/images/sub/Introduction-num4.svg" />" alt="" class="img2">
		</div>
	</div>
</div>
<div id="div2" class="contentDiv" style="display:none;">
	<div class="bipage">
		<div class="top">
			<div class="row" style="">
				<div class="col-12 col-md">
					<div class="box text-center">
						<img src="<c:url value="/images/logo/property1-blue2.svg"/>" class="chage2" alt="로고" />
					</div>
				</div>
				<div class="col-12 col-md">
					<p class="txt1">BI 소개</p>
					<p class="txt2">1. 상승곡선 화살표의 형태는 투자, 도약의 의미</p>
					<p class="txt2">2. 땅, 토지의 형태(농업의 의미)를 형상화하여 간결하게 표현해 농업관련 투자정보를 추천한다는 의미를 담았고, 블루계열의 색상으로 (신뢰감)을 더해 ASSIST의 브랜드 아이덴티티를 상징적으로 디자인하였습니다.</p>
				</div>
			</div>
		</div>
	</div>
	<div class="center">
		<img src="<c:url value="/images/sub/BIpage-img2.svg"/>" alt="">
		<p class="txt1">Agriculture Startup Support Investment Service plaTform</p>
		<p class="txt2">
			ASSIST(동사) : 1. 돕다 2. (스포츠에서)득점할 수 있게 도움이 되다.<br/>
			농식품스타트업의 투자유치와 성장을 도와줄 종합 투자정보 서비스를 제공하는 플랫폼으로 농식품 관련 맞춤형 투자정보 및 지원사업 정보를 추천받아(어시스트 받아) 한단계 도약한다는 의미.
		</p>
	</div>
	<div class="bottom">
		<div class="row">
			<div class="col-6 col-md">
				<div class="box"><img src="<c:url value="/images/sub/BIpage-img3.svg"/>" alt=""></div>
				<p class="txt1">가로형 기본</p>
			</div>
			<div class="col-6 col-md">
				<div class="box"><img src="<c:url value="/images/sub/BIpage-img4.svg"/>" alt=""></div>
				<p class="txt1">가로형 국문 조합</p>
			</div>
			<div class="col-6 col-md">
				<div class="box"><img src="<c:url value="/images/sub/BIpage-img6.svg"/>" alt=""></div>
				<p class="txt1">가로형 영문 조합</p>
			</div>
			<div class="col-6 col-md">
				<div class="box"><img src="<c:url value="/images/sub/BIpage-img5.svg"/>" alt=""></div>
				<p class="txt1">가로형 국영문 혼합</p>
			</div>
		</div>
	</div>
	<div class="bottom-box-btn pb-0">
		<div class="row">						
			<div class="col">
				<button id="btnAi"  class="btn-primary w-100 bs-l"><i class="icon-download"></i>AI 파일 다운로드</button>
			</div>
			<div class="col">
				<button id="btnPng" class="btn-primary w-100 bs-l"><i class="icon-download"></i>PNG 파일 다운로드</button>
			</div>
			<div class="col">
				<button id="btnJpg" class="btn-primary w-100 bs-l"><i class="icon-download"></i>JPG 파일 다운로드</button>
			</div>						
		</div>
	</div>
</div>

<%-- ############################# 내용 (종료) ############################# --%>
