<%--
*******************************************************************************
***  명칭: openInvestGuide.jsp
***  설명: 정보서비스 - 투자유치가이드 화면
***
***  -----------------------------  Modified Log  ---------------------------
***  버전    수정일자    수정자    내용
*** ---------------------------------------------------------------------------
***  1.0   2023.08.02   JH    작업완료.
*******************************************************************************
--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="app"  uri="/WEB-INF/tld/app.tld" %>
<%@ taglib prefix="f"   uri="/WEB-INF/tld/f.tld" %>
<%@ taglib prefix="fn"   uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"  uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec"  uri="http://www.springframework.org/security/tags" %>

<%-- ############################# 내용 (시작) ############################# --%>
<div class="capsule-tabmenu-1">
	<div class="border-bottom-0 position-relative p-8px shadow-box-1" style="border-radius: 16px 16px 0 0;z-index: 10;">
		<div class="capsule-box">
		  <div class="row">
		    <div class="col active fs-md-16px lh-md-38px" id="APFS_FAFF" data-target="#APFS_FAFF_Data" style="border-bottom-right-radius: 0px; border-bottom-left-radius: 0px;">
		      농림수산식품 모태펀드
		    </div>
		    <div class="col fs-md-16px lh-md-38px" id="APFSCrowdfunding" data-target="#APFSCrowdfunding_Data" style="border-bottom-right-radius: 0px; border-bottom-left-radius: 0px;">
		      농식품 크라우드펀딩
		    </div>
		  </div>
		</div>
	</div>
	<div id="firstTab">
		<div class="shadow-box-1 border-top-0 mb-56px px-0" style="border-radius: 0;">
			<div class="tabmenu-1">
				<ul>
				  <li><a href="#introduceFAFF" 	id="btnIntroduceFAFF" 	 	class="active">소개</a></li>
				  <li><a href="#investment" 		id="btnInvestment" 			class="bg-gray-t10">투자절차</a></li>
				  <li><a href="#investmentTarget" id="btnInvestmentTarget" 	class="bg-gray-t10">투자대상 및 분야</a></li>
				  <li><a href="#supportProjectBA" id="btnSupportProjectBA" 	class="bg-gray-t10">투자 전·후 지원사업 안내</a></li>
				</ul>
			</div>
		</div>
	</div>
	<div id="secondTab" style="display: none;">
		<div class="shadow-box-1 border-top-0 mb-56px px-0" style="border-radius: 0;">
			<div class="tabmenu-1">
				<ul>
				  <li><a href="#introduceCrowd" 	id="btnIntroduceCrowd" class="active">소개</a></li>
				  <li><a href="#supportProject" 	id="btnSupportProject" class="bg-gray-t10">지원사업</a></li>
				  <li><a href="#application" 		id="btnApplication" 	class="bg-gray-t10">신청하기</a></li>
				  <li><a href="#fundingDedicated" id="btnFundingDedicated" class="bg-gray-t10">펀딩전용관 바로가기</a></li>
				</ul>
			</div>
		</div>
	</div>
</div>	
	
<div id="APFS_FAFF_Data" >
	<div id="introduceFAFF">	
		<div class=" mb-56px">
			<div class="datum-point">
				<div class="introduction-imgbox-1">
					<div class="row">
						<div class="col-12 col-md imgbox">
							<div class="col-12 col-md flex-md-grow-0 p-10px">
								<video class="d-none d-md-block" controls width="588" height="364">
									<source src="<c:url value='/images/media/fund.mp4'/>" type="video/mp4">
								</video>
							</div>
						</div>
						<div class="col-12 col-md txtbox">
							<p class="txt1">농림수산식품모태펀드란?</p>
							<p class="txt2">FAFF fund of funds</p>
							<span class="txt3">
								농림수산식품산업에 대한 투자를 촉진하고,농림수산식품산업의 규모화 및 경쟁력 강화를 위하여 정부가 조성하는 투자펀드시스템으로 농어업경영체, 식품사업자 등
								농림수산식품경영체에 대한 투자를 목적으로 설립된 농림수산식품투자조합 또는 경영참여형사모집합투자기구에 출자하는 방식의 Fund of Funds 입니다.
							</span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="py-56px">
			<div class="datum-point">
				<div class="tit-box-1 text-center mb-40px">
					<p class="txt1 mb-8px text-black">농림수산식품모태펀드 운용구조</p>
					<span class="fs-15px fw-300">법적 자격을 갖춘 펀드운용회사가 농림수산식품모태펀드의 출자금과 민간자금을 결합하여 결성하며, 성장가능성 있는
						농림수산식품경영체의 사업성을 검토·선발하여 투자합니다</span>
				</div>
				<div class="px-53px pt-53px pb-52px round-16px border border-ele-Line">
					<img src="<c:url value='/images/sub/Introduction-img9.png'/>" alt="" style="max-width: 100%;">
				</div>
			</div>
		</div>
		<div class="bg-primary-t5 px-56px py-56px mx-mius-30px mx-md-0">
			<div class="datum-point">
				<div class="tit-box-1 mb-32px">
					<p class="txt1 mb-16px text-black">출자자 구성</p>
					<div class="bg-ele-Line " style="height:1px;"></div>
				</div>
				<div class="introduction-imgbox-2">
					<div class="row">
						<div class="col-12 col-md imgbox">
							<img src="<c:url value='/images/sub/Introduction-img10.svg'/>" alt="">
						</div>
						<div class="col-12 col-md ">
							<div class="tit-box-1 mb-16px">
								<p class="txt1 text-black">투자조합</p>
							</div>
							<div class="capsule-design-1">
								<div class="box">
									<div class="row">
										<div class="col-12 col-md leftbox">
											<p class="txt1">VC</p>
											<p class="txt2">Venture Capital</p>
										</div>
										<div class="col-12 col-md">
											<p class="txt3">벤처기업에 주식투자형식으로 투자하는기업 또는 기업의 자본</p>
										</div>
									</div>
								</div>
								<div class="box">
									<div class="row">
										<div class="col-12 col-md leftbox">
											<p class="txt1">LP</p>
											<p class="txt2">Limited Partner</p>
										</div>
										<div class="col-12 col-md">
											<p class="txt3">투자조합을 구성하는 출자자 중출자액 한도로 유한책임을 지는 조합원</p>
										</div>
									</div>
								</div>
								<div class="box">
									<div class="row">
										<div class="col-12 col-md leftbox py-19px">
											<p class="txt1">GP</p>
											<p class="txt2">General Partner</p>
										</div>
										<div class="col-12 col-md">
											<p class="txt3">투자조합을 구성하는 출자자 중 조합의 채무에 대하여 무한으로 책임을 지는 조합원 , LP가 출자한
												펀드를 운용하는 운용사가 해당 펀드를 운용하는 운용사가 해당</p>
										</div>
									</div>
								</div>
							</div>


						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="px-56px py-56px bg-ele-DisableBG">
			<div class="datum-point">
				<div class="tit-box-1 mb-24px">
					<p class="txt1 mb-16px text-black">농림수산식품 투자조합 결성현황</p>
				</div>
				<div class="shadow-box-1 px-16px pb-24px">
					<div class="table-box-1 m-overbox no-hover">

						<table id="ContactVCPage">
							<caption>
								민원서식 목록
							</caption>
							<colgroup>
								<col style="width:110px">
								<col style="width:244px">
								<col style="width:165px">
								<col style="width:167px">
								<col style="width:244px">
								<col style="width:138px">
								<col style="width:100px">
							</colgroup>
							<thead class="bs-1 ts-l">
								<tr class="px-v-l">

									<th scope="col" class="border-top-0">조성년도</th>
									<th scope="col" class="border-top-0 ">펀드명</th>
									<th scope="col" class="border-top-0">투자분야</th>
									<th scope="col" class="border-top-0">투자기간</th>
									<th scope="col" class="border-top-0">운용사</th>
									<th scope="col" class="border-top-0">전화</th>
									<th scope="col" class="border-top-0">펀드규모</th>
								</tr>
							</thead>
							<tbody class="bs-1 t-t-c ts-m py-v-m">
								<tr class="px-v-xl t-t-c">
									<td  rowspan="17" class="before-none" style="border-bottom-color:var(--primary)">2022</td>
										<td class="text-start">농식품새싹키움매칭펀드</td>
										<td class="">농식품 통합지원</td>
										<td class="">2022.12~2026.12</td>
										<td class="text-start">농업정책보험금융원</td>
										<td class="">02-3775-6754</td>
										<td class="">10억
									</td>
								</tr>
								<tr>
									<td class="text-start">비엔케이 그린바이오 투자조합</td>
									<td class="">
										그린바이오</td>
									<td class="">
										2022.12~2023.06</td>
									<td class="text-start">
										비엔케이벤처투자</td>
									<td class="">
										02-508-8187</td>
									<td class="">
										200억</td>
								</tr>
								<tr>
									<td class="text-start">
										엔에이치나이스농식품투자조합1호</td>
									<td class="">
										농림축산식품</td>
									<td class="">
										2022.12~2026.12</td>
									<td class="text-start">
										나이스투자파트너스 & 농협은행</td>
									<td class="">
										02-2122-5835</td>
									<td class="">
										500억</td>
								</tr>
								<tr>
									<td class="text-start">
										인라이트 애그테크플러스펀드 2호</td>
									<td class="">
										마이크로</td>
									<td class="">
										2022.10~2026.01</td>
									<td class="text-start">
										인라이트벤처스</td>
									<td class="">
										053-341-9222</td>
									<td class="">
										65억</td>
								</tr>
								<tr>
									<td class="text-start">
										임팩트-이크럭스 농식품벤처 투자조합</td>
									<td class="">
										농식품벤처펀드</td>
									<td class="">
										2022.10~2026.01</td>
									<td class="text-start">
										임팩트파트너스 & 이크럭스벤처<br />파트너스</td>
									<td class="">
										02-569-4560</td>
									<td class="">
										125억</td>
								</tr>
								<tr>
									<td class="text-start">
										패스파인더 그린바이오 투자조합</td>
									<td class="">
										그린바이오</td>
									<td class="">
										2022.10~2026.01</td>
									<td class="text-start">
										패스파인더에이치</td>
									<td class="">
										02-739-9041</td>
									<td class="">
										200억</td>
								</tr>
								<tr>
									<td class="text-start">
										CKD Smart Farm 2호 농식품투자조</td>
									<td class="">
										스마트농업</td>
									<td class="">
										2022.10~2026.01</td>
									<td class="text-start">
										씨케이디창업투자</td>
									<td class="">
										02-3453-3918</td>
									<td class="">
										200억</td>
								</tr>
								<tr>
									<td class="text-start">
										2022 원익 스마트 혁신 Agtech투자</td>
									<td class="">
										스마트농업</td>
									<td class="">
										2022.10~2026.01</td>
									<td class="text-start">
										원익투자파트너스</td>
									<td class="">
										02-6446-7125</td>
									<td class="">
										210억</td>
								</tr>
								<tr>
									<td class="text-start">
										오라클프레쉬펀드</td>
									<td class="">
										마이크로</td>
									<td class="">
										2022.10~2026.01</td>
									<td class="text-start">
										오라클벤처투자</td>
									<td class="">
										02-2039-1643</td>
									<td class="">
										65억</td>
								</tr>
								<tr>
									<td class="text-start">
										빌랑스 징검다리 농식품 투자조합</td>
									<td class="">
										징검다리</td>
									<td class="">
										2022.10~2025.04</td>
									<td class="text-start">
										빌랑스인베스트먼트</td>
									<td class="">
										070-4736-6660</td>
									<td class="">
										216억</td>
								</tr>
								<tr>
									<td class="text-start">
										마그나 FUTURE 펀드</td>
									<td class="">
										농림축산식품</td>
									<td class="">
										2022.10~2026.01</td>
									<td class="text-start">
										마그나인베스트먼트</td>
									<td class="">
										02-554-2222</td>
									<td class="">
										300억</td>
								</tr>
								<tr>
									<td class="text-start">
										나우농식품투자펀드 5호</td>
									<td class="">
										농식품벤처펀드</td>
									<td class="">
										2022.08~2026.08</td>
									<td class="text-start">
										나우아이비캐피탈</td>
									<td class="">
										02-565-6234</td>
									<td class="">
										140억</td>
								</tr>
								<tr>
									<td class="text-start">
										유니온수산투자조합</td>
									<td class="">
										수산업</td>
									<td class="">
										2022.07~2026.07</td>
									<td class="text-start">
										유니온투자파트너스</td>
									<td class="">
										02-594-8470</td>
									<td class="">
										100억</td>
								</tr>
								<tr>
									<td class="text-start">
										넥스트웨이브 2022 수산벤처 <br />투자조합</td>
									<td class="">
										수산벤처창업</td>
									<td class="">
										2022.07~2026.07</td>
									<td class="text-start">
										엔브이씨파트너스 & 마이다스 동아인베스트먼트</td>
									<td class="">
										051-914-0938</td>
									<td class="">
										96억</td>
								</tr>
								<tr>
									<td class="text-start">
										세종 농식품 벤처펀드</td>
									<td class="">
										농식품벤처펀드</td>
									<td class="">
										2022.07~2026.07</td>
									<td class="text-start">
										세종벤처파트너스</td>
									<td class="">
										070-4667-0760</td>
									<td class="">
										130억</td>
								</tr>
								<tr>
									<td class="text-start">
										엔에이치영파머스투자조합</td>
									<td class="">
										영파머스</td>
									<td class="">
										2022.07~2026.07</td>
									<td class="text-start">
										엔에이치벤처투자</td>
									<td class="">
										02-6226-6808</td>
									<td class="">
										105억</td>
								</tr>
								<tr>
									<td class="text-start">
										비하이농식품마이크로투자조합</td>
									<td class="">
										마이크로</td>
									<td class="">
										2022.07~2026.07</td>
									<td class="text-start">
										비하이인베스트먼트(유)</td>
									<td class="">
										02-539-7997</td>
									<td class="">
										65억</td>
								</tr>

							</tbody>
							<tbody class="bs-1 t-t-c ts-m py-v-m">
								<tr>
									<td rowspan="12" class="before-none" style="border-bottom-color:var(--primary)">2021</td>
									<td class="text-start">
										씨엔티테크 제13호 농식품 투자조합</td>
									<td>
										창업보육</td>
									<td>
										2022.01~2026.01</td>
									<td class="text-start">
										씨엔티테크</td>
									<td>
										02-3152-5830</td>
									<td>
										71억</td>
								</tr>
								<tr>
									<td class="text-start">
										동훈 농식품벤처스타 2호 투자조합</td>
									<td>
										농식품벤처펀드</td>
									<td>
										2021.12~2025.12</td>
									<td class="text-start">
										동훈인베스트먼트</td>
									<td>
										02-6250-1500</td>
									<td>
										100억</td>
								</tr>
								<tr>
									<td class="text-start">
										하이브리드 ESG 세컨더리펀드 제일호</td>
									<td>
										세컨더리(혼합형)</td>
									<td>
										2021.12~2025.06</td>
									<td class="text-start">
										포스코기술투자 & 메타인베스트먼트</td>
									<td>
										02-3457-6300</td>
									<td>
										520억</td>
								</tr>
								<tr>
									<td class="text-start">
										아이디브이-아이피 수산전문<br />투자조합 3호</td>
									<td>
										수산업</td>
									<td>
										2021.11~2025.11</td>
									<td class="text-start">
										아이디벤처스(주)</td>
									<td>
										02-556-9300</td>
									<td>
										150억</td>
								</tr>
								<tr>
									<td class="text-start">
										신세계웰니스투자조합</td>
									<td>
										농림축산식품</td>
									<td>
										2021.10~2025.10</td>
									<td class="text-start">
										시그나이트파트너스(주)</td>
									<td>
										02-6981-3939</td>
									<td>
										182억</td>
								</tr>
								<tr>
									<td class="text-start">
										마그나 GREEN 펀드</td>
									<td>
										그린바이오</td>
									<td>
										2021.09~2025.09</td>
									<td class="text-start">
										마그나인베스트먼트</td>
									<td>
										02-554-2222</td>
									<td>
										150억</td>
								</tr>
								<tr>
									<td class="text-start">
										비엔케이 농식품투자조합 제3호</td>
									<td>
										스마트농업</td>
									<td>
										2021.09~2025.08</td>
									<td class="text-start">
										비엔케이벤처투자</td>
									<td>
										02-508-8187</td>
									<td>
										150억</td>
								</tr>
								<tr>
									<td class="text-start">
										패스타인더 영파머스투자조합</td>
									<td>
										영파머스</td>
									<td>
										2021.08~2025.08</td>
									<td class="text-start">
										(주)패스파인더에이치</td>
									<td>
										02-739-9041</td>
									<td>
										105억</td>
								</tr>
								<tr>
									<td class="text-start">
										메가농식품벤처투자조합3호</td>
									<td>
										농식품벤처펀드</td>
									<td>
										2021.07~2025.07</td>
									<td class="text-start">
										JB인베스트먼트</td>
									<td>
										02-3453-2540</td>
									<td>
										125억</td>
								</tr>
								<tr>
									<td class="text-start">
										빙그레농식품투자조합2호</td>
									<td>
										마이크로</td>
									<td>
										2021.07~2025.07</td>
									<td class="text-start">
										(유)동문파트너즈</td>
									<td>
										02-2265-0566</td>
									<td>
										65억</td>
								</tr>
								<tr>
									<td class="text-start">
										현대-GS리테일 Agro-Bio펀드 3호</td>
									<td>
										농림축산식품</td>
									<td>
										2021.07~2025.07</td>
									<td class="text-start">
										현대기술투자</td>
									<td>
										02-728-8972</td>
									<td>
										210억</td>
								</tr>
								<tr>
									<td class="text-start">
										엔브이씨 2021 수산벤처투자조합</td>
									<td>
										수산벤처창업</td>
									<td>
										2021.07~2025.07</td>
									<td class="text-start">
										엔브이씨파트너스주식회사</td>
									<td>
										051-914-0938</td>
									<td>
										105억</td>
								</tr>

							</tbody>
							<tbody class="bs-1 t-t-c ts-m py-v-m">
								<tr>
									<td rowspan="10" class="before-none" style="border-bottom-color:var(--primary)">2020
									</td>
									<td class="text-start">
										롯데농식품테크펀드1호</td>
									<td>
										농림축산식품</td>
									<td>
										2020.12~2024.12</td>
									<td class="text-start">
										롯데액셀러레이터(주)</td>
									<td>
										02-2051-1523</td>
									<td>
										152억</td>
								</tr>
								<tr>
									<td class="text-start">
										아이에스유(ISU)-<br />힘내라경북애그리푸드투자조합</td>
									<td>
										지역특성화펀드(경북)</td>
									<td>
										2020.11~2024.11</td>
									<td class="text-start">
										(주)이수창업투자</td>
									<td>
										02-6207-7990</td>
									<td>
										110억</td>
								</tr>
								<tr>
									<td class="text-start">
										엔에이치농식품벤처투자조합</td>
									<td>
										농식품 벤처펀드</td>
									<td>
										2020.10~2024.10</td>
									<td class="text-start">
										NH벤처투자(주)</td>
									<td>
										02-6226-6808</td>
									<td>
										130억</td>
								</tr>
								<tr>
									<td class="text-start">
										나이스앤영파머스투자조합</td>
									<td>
										영파머스</td>
									<td>
										2020.10~2024.10</td>
									<td class="text-start">
										나이스투자파트너스(주)</td>
									<td>
										02-2122-5835</td>
									<td>
										100억</td>
								</tr>
								<tr>
									<td class="text-start">
										엔에이치나우농식품2호사모투자<br />합자회사</td>
									<td>
										농림축산식품</td>
									<td>
										2020.09~2024.09</td>
									<td class="text-start">
										나우아이비캐피탈&농협은행</td>
									<td>
										02-565-6234<br />
										02-2080-7595
									</td>
									<td>
										400억</td>
								</tr>
								<tr>
									<td class="text-start">
										인라이트8호 애그테크플러스펀드</td>
									<td>
										마이크로</td>
									<td>
										2020.09~2024.09</td>
									<td class="text-start">
										인라이트벤처스</td>
									<td>
										053-341-9222</td>
									<td>
										65억</td>
								</tr>
								<tr>
									<td class="text-start">
										농식품 스텝업 투자조합</td>
									<td>
										징검다리</td>
									<td>
										2020.09~2024.09</td>
									<td class="text-start">
										세종벤처파트너스& 대성창업투자</td>
									<td>
										070-4667-0760<br />02-559-2900</td>
									<td>
										220억</td>
								</tr>
								<tr>
									<td class="text-start">
										마그나 FRESH펀드</td>
									<td>
										농식품 벤처펀드</td>
									<td>
										2020.07~2024.07</td>
									<td class="text-start">
										마그나인베스트먼트</td>
									<td>
										02-554-2222</td>
									<td>
										130억</td>
								</tr>
								<tr>
									<td class="text-start">
										가이아수산벤처창업투자조합1호</td>
									<td>
										수산벤처창업</td>
									<td>
										2020.07~2024.07</td>
									<td class="text-start">
										가이아벤처파트너스</td>
									<td>
										02-6451-5656</td>
									<td>
										150억</td>
								</tr>
								<tr>
									<td class="text-start">
										비엔케이 수산투자조합 제1호</td>
									<td>
										수산벤처창업</td>
									<td>
										2020.07~2024.07</td>
									<td class="text-start">
										비엔케이벤처투자</td>
									<td>
										02-508-8187</td>
									<td>
										150억 </td>
								</tr>
							</tbody>
							<tbody class="bs-1 t-t-c ts-m py-v-m">
								<tr>
									<td rowspan="8" class="before-none" style="border-bottom-color:var(--primary)">2019
									</td>
									<td class="text-start">
										마이다스동아-엔에스씨 수산펀드 2호</td>
									<td>
										수산업</td>
									<td>
										2019.12~2023.12</td>
									<td class="text-start">
										마이다스동아인베스트먼트 & <br />농심캐피탈</td>
									<td>
										02-2020-0906<br />02-827-1600</td>
									<td>
										200억</td>
								</tr>
								<tr>
									<td class="text-start">
										IDV-IP수산전문투자조합 2호</td>
									<td>
										수산업</td>
									<td>
										2019.12~2023.12</td>
									<td class="text-start">
										아이디벤처스</td>
									<td>
										02-556-9300</td>
									<td>
										150억</td>
								</tr>
								<tr>
									<td class="text-start">
										엔에이치나우농식품1호사모투자<br />합자회사</td>
									<td>
										농림축산식품</td>
									<td>
										2019.08~2023.08</td>
									<td class="text-start">
										나우아이비캐피탈 & 농협은행</td>
									<td>
										02-565-6234<br />
										02-2080-7595</td>
									<td>
										400억</td>
								</tr>
								<tr>
									<td class="text-start">
										유엔그린시너지투자조합</td>
									<td>
										농림축산식품</td>
									<td>
										2019.08~2023.08</td>
									<td class="text-start">
										유티씨인베스트먼트 & <br />나이스에프앤아이</td>
									<td>
										02-783-3347<br />
										02-2122-5800</td>
									<td>
										230억</td>
								</tr>
								<tr>
									<td class="text-start">
										타임와이즈농식품벤처펀드</td>
									<td>
										농식품벤처펀드</td>
									<td>
										2019.07~2023.07</td>
									<td class="text-start">
										타임와이즈인베스트먼트</td>
									<td>
										02-557-1187</td>
									<td>
										125억</td>
								</tr>
								<tr>
									<td class="text-start">
										동훈 농식품벤처스타 1호투자조합</td>
									<td>
										마이크로</td>
									<td>
										2019.07~2023.07</td>
									<td class="text-start">
										동훈인베스트먼트</td>
									<td>
										02-6250-1500</td>
									<td>
										63억</td>
								</tr>
								<tr>
									<td class="text-start">
										현대 Agro-Bio펀드 2호</td>
									<td>
										ABC펀드</td>
									<td>
										2019.07~2023.07</td>
									<td class="text-start">
										현대기술투자</td>
									<td>
										02-728-8990</td>
									<td>
										100억</td>
								</tr>
								<tr>
									<td class="text-start">
										빙그레농식품투자조합</td>
									<td>
										마이크로</td>
									<td>
										2019.06~2023.06</td>
									<td class="text-start">
										동문파트너즈</td>
									<td>
										02-2265-0566</td>
									<td>
										62억 </td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="investment" >
		<div class="row" style="margin:-20px;">
			<div class="col p-20px">
				<div class="tit-box-1 text-center mb-16px">
					<p class="txt1 text-black mb-16px text-start">
						<span class="square-box-1">
							01
						</span>
						투자검토요청
					</p>
					<div class="shadow-box-4 p-16px">
						<div class="icontxt-design-1">
							<div class="box mb-16px">
								<img src="<c:url value='/images/sub/Introduction-icom6.svg'/>" alt="">
								<p class="txt1">VC<em>간접</em>접촉</p>
								<button class="btn-black bs-l" id="btnSprtAplyVCBefore">공개IR 참여를 통한 VC접촉 <i class="icon-arrow-right ml-4px"></i></button>
								<!-- 23.08.18 LHB 투자유치 전 지원 신청하기와 ID 중복됨. 처리 필요 -->
							</div>
							<div class="box mb-16px">
								<img src="<c:url value='/images/sub/Introduction-icom7.svg'/>" alt="">
								<p class="txt1">VC<em>직접</em>접촉</p>
								<button class="btn-black bs-l" id="btnIntroduceFAFF2" >펀드운용사 연락망을 통해 VC 접촉 <i class="icon-arrow-right ml-4px"></i></button>
							</div>
							<div class="txtbox-design-1">
								<p class="tit">
									<img src="<c:url value='/images/sub/Introduction-icom8.svg'/>" alt="">KEY POINTS
								</p>
								<div class="txt-lev-box-2">
									<p class="dot lev1 mb-8px">사업계획 수립
										<button class="border-0 bs-s btn-primary-ouline-hover fs-12px ml-8px px-16px round-24px" onclick="$('#exampleModal').addClass('show');">사업계획서</button>
									</p>
									<p class="lev1 dot mb-8px">투명한 재무현황</p>
									<p class="lev1 dot mb-4px">명확한 자금소요계획 수립</p>
									<p class="lev2">
										- 컨설팅, 회계법인 등 활용<br>
										- 자금의 용도<br>
										- 자금조달 계획(부족자금)<br>
										- 투자유치 방법
									</p>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col p-20px">
				<div class="tit-box-1 text-center mb-16px">
					<p class="txt1 text-black mb-16px text-start">
						<span class="square-box-1">
							02
						</span>
						투자검토
					</p>
					<div class="shadow-box-4 p-16px">
						<div class="icontxt-design-2">
							<div class="box mb-16px">
								<img src="<c:url value='/images/sub/Introduction-icom9.svg'/>" alt="">
								<p class="txt1">VC의 <em>기업방문</em>
									<span>PT & 경영진 인터뷰</span>
								</p>
							</div>
							<div class="box mb-16px">
								<img src="<c:url value='/images/sub/Introduction-icom10.svg'/>" alt="">
								<p class="txt1">VC의 시장 및 <em>기술력 검증</em>
									<span>경쟁력 입증 자료 제공</span>
								</p>
							</div>
							<div class="box mb-16px">
								<img src="<c:url value='/images/sub/Introduction-icom11.svg'/>" alt="">
								<p class="txt1">VC의 <em>실사 및 현장점검</em></p>
							</div>
							<div class="box mb-16px">
								<img src="<c:url value='/images/sub/Introduction-icom12.svg'/>" alt="">
								<p class="txt1"><em>투자조건에 대한 협상</em></p>
							</div>
						</div>
						<div class="txtbox-design-1">
							<p class="tit">
								<img src="<c:url value='/images/sub/Introduction-icom8.svg'/>" alt="">KEY POINTS
							</p>
							<div class="txt-lev-box-2">
								<p class="lev1 dot mb-8px">임직원의 사업에 대한 의지</p>
								<p class="lev1 dot mb-8px">시장 전망 및 기술 경쟁력</p>
								<p class="lev1 dot mb-8px">정확하고 신속한 자료 제공</p>
								<p class="lev1 dot mb-8px">이행가능한 투자조건 협상</p>
								<p class="lev2">
									- 투자 방법 / 주식, PF<br/>
									- 투자 조석 / 금리, 회수시점
								</p>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col p-20px">
				<div class="tit-box-1 text-center mb-16px">
					<p class="txt1 text-black mb-16px text-start">
						<span class="square-box-1">
							03
						</span>
						투자결정
					</p>
					<div class="shadow-box-4 p-16px">
						<div class="icontxt-design-3">
							<div class="box mb-16px">
								<img src="<c:url value='/images/sub/Introduction-icom13.svg'/>" alt="">
								<p class="txt1"><em>회계·법무</em><em>실사</em></p>
							</div>
							<div class="box mb-16px">
								<img src="<c:url value='/images/sub/Introduction-icom14.svg'/>" alt="">
								<p class="txt1"><em>투자계약서</em> 작성</p>
							</div>
							<div class="box mb-16px">
								<img src="<c:url value='/images/sub/Introduction-icom8_1.svg'/>" alt="">
								<p class="txt1"><em>투자결정</em></p>
							</div>
						</div>
						<div class="txtbox-design-1">
							<p class="tit">
								<img src="<c:url value='/images/sub/Introduction-icom8.svg'/>" alt="">KEY POINTS
							</p>
							<div class="txt-lev-box-2">
								<p class="lev1 dot mb-4px">투자계약서 검토
								</p>
								<p class="lev2 mb-8px">
									- 표준계약서 활용 / 법무법인 활용
								</p>
								<p class="lev1 dot mb-4px">투자유치 이후에 서류 제출
								</p>
								<p class="lev2 mb-8px">
									- 증자 또는 사채발행 서류
								</p>
								<p class="lev1 dot mb-16px">투자자금의 안정적 관리
								</p>
								<p class="lev1 dot mb-4px">투자 이후 관리
								</p>
								<p class="lev2">
									- 정기회의 및 자료 제출
								</p>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="txtbox-design-2">
			<div class="row align-items-center">
				<div class="col flex-b64px flex-md-grow-0 pr-8px">
					<i class="icon-flag cycle"></i>
				</div>
				<div class="col pl-0">
					<p class="txt2">투자에 대한 의사결정은 펀드 운용사에 있습니다.</p>
				</div>
			</div>
		</div>
	</div>
	
	<div id="investmentTarget" >
		<div class="mb-56px">
			<div class="tit-box-1 mb-8px">
				<p class="txt1 mb-8px text-black">투자대상</p>
			</div>
			<div class="shadow-box-1 px-16px pb-24px">
				<div class="table-box-1 m-overbox no-hover">
					<table>
						<caption>
							민원서식 목록
						</caption>
						<colgroup>
							<col style="width:110px">
							<col style="width:280px">
							<col style="width:auto">
						</colgroup>
						<thead class="bs-1 ts-l">
							<tr class="px-v-l">
								<th scope="col" class="border-top-0">사업분야</th>
								<th scope="col" class="border-top-0">투자대상</th>
								<th scope="col" class="border-top-0">관련 공고 및 법률</th>
							</tr>
						</thead>
						<tbody class="bs-1 t-t-c ts-m py-v-m">
							<tr class="px-v-xl t-t-c">
								<td rowspan="2" class="before-none" >농업</td>
								<td class="">농작물 재배업</td>
								<td class="text-start">식량작물 재배업, 채소작물 재배업, 과실작물 재배업, 화훼작물 재배업, 특용작물 재배업, 약용작물 재배업, 버섯 재배업, 양잠업 및 종자·묘목 재배업 등</td>									
							</tr>
							<tr class="px-v-xl t-t-c">				
								<td class="">임업</td>
								<td class="text-start">육림업(자연휴양림·자연수목원의 조성·관리·운영업 포함), 임산물 생산·채취업 및 임업용 종자·묘목 재배업 등</td>									
							</tr>
							<tr class="px-v-xl t-t-c">
								<td  class="before-none" >축산업</td>
								<td class="">축산업</td>
								<td class="text-start">
									동물(수생동물 제외)의 사육업·증식업·부화업·종축업<span style="color: #808080">(種畜業)</span> 등
								</td>									
							</tr>
							<tr class="px-v-xl t-t-c">
								<td  class="before-none" >수산업</td>
								<td >수산업</td>
								<td class="text-start">
									수산동식물을 포획·채취하거나 양식하는 산업, 염전에서 바닷물을 자연 증발시켜 제조하는 염산업 및 관련 산업 등	
								</td>									
							</tr>
							<tr class="px-v-xl t-t-c">
								<td  class="before-none" >
									식품산업
								</td>
								<td class="">식품을 생산/가공/제조/조리/포장/보관/수송 또는 판매하는 산업</td>
								<td class="text-start">
									농수산물에 인공을 가하여 생산·가공·제조·조리하는 산업 및 이 산업으로 생산된 산물을 포장·보관·수송 또는 판매하는 산업 등									</td>									
							</tr>
							<tr class="px-v-xl t-t-c">
								<td rowspan="3" class="before-none" >
									소재 및 생산
									설비 산업
								</td>
								<td class="">소재</td>
								<td class="text-start">
									농림수산식품의 생산에 사용되는 원재료 또는 중간생산물 중 농림수산식품의 고부가가치화에 기여가 큰 것으로 농약, 비료, 양액, 사료, 동물약품, 포대, 필름 또는 포장재와 그 밖에 유사한 것
								</td>									
							</tr>
							<tr class="px-v-xl t-t-c">
								<td class="">생산설비</td>
								<td class="text-start">
									농소재를 제조·가공하는 설비 중 농림수산식품의 고부가가치화에 기여가 큰 것으로서 기계 또는 공구와 그 밖에 이와 유사한 것
								</td>									
							</tr>
							<tr class="px-v-xl t-t-c">
								<td class="">매출액 중 소재 또는 생산설비의 매출액이 50% 이상이고 상호출자제한기업집단에 속하지 않는 기업</td>
								<td class="text-start">
									-
								</td>									
							</tr>
							<tr class="px-v-xl t-t-c">
								<td rowspan="9" class="before-none" style="border-bottom-color:var(--primary)">
									기타 농림수산식품 <br />관련 산업
								</td>
								<td class="">농업 관련 산업</td>
								<td class="text-start">농산물 유통·가공업, 농업·그린바이오, 야생조수사육업, 분재생산업, 조경업 등</td>
							</tr>
							<tr class="px-v-xl t-t-c">
								<td class="">임업 관련 산업</td>
								<td class="text-start">임산물 유통·가공업, 야생조수 사육업, 분재생산업 및 조경업, 수목조사업 등</td>
							</tr>
							<tr class="px-v-xl t-t-c">
								<td class="">수산업 관련 산업</td>
								<td class="text-start">수산물 유통·가공업, 수산바이오, 종묘산업, 해양심층수산업, 낚시 등 수산레저산업 및 어업 컨설팅업 등 수산업 관련 서비스업
							</tr>
							<tr class="px-v-xl t-t-c">
								<td class="">농업 관련 서비스업</td>
								<td class="text-start">애완동물산업, 말산업, 농촌관광 등</td>
							</tr>
							<tr class="px-v-xl t-t-c">
								<td class="">식품업 관련 산업</td>
								<td class="text-start">식품포장용기 제조업 및 음식료품 가공기계 제조업 / 음식료품 도소매업 등 식품산업 관련 서비스업</td>
							</tr>
							<tr class="px-v-xl t-t-c">
								<td class="">곤충산업</td>
								<td class="text-start">「곤충산업의 육성 및 지원에 관한 법률」제12조 제1항에 따라 곤충의 사육, 생산, 가공 또는 유통의 신고를 한 자</td>
							</tr>
							<tr class="px-v-xl t-t-c">
								<td class="">해외농업개발사업자</td>
								<td class="text-start">「해외농업개발협력법」제2조 제6호의 해외농업개발사업자</td>
							</tr>
							<tr class="px-v-xl t-t-c">
								<td class="">원양산업자</td>
								<td class="text-start">「원양산업발전법」제2조 제4호의 원양산업자</td>
							</tr>
							<tr class="px-v-xl t-t-c">
								<td class="">연구개발(R&D)</td>
								<td class="text-start">「과학기술기본법」제27조 및 같은 법 시행령 41조에 따른 「국가과학기술분류체계」의 농림수산식품분야 연구개발(R&D)에 종사하는 자</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<div class="mb-56px">
			<div class="tit-box-1 mb-8px">
				<p class="txt1 mb-8px text-black">투자분야</p>
			</div>
			<div class="shadow-box-1 px-16px pb-24px">
				<div class="table-box-1 m-overbox no-hover">
					<table>
						<caption>
							민원서식 목록
						</caption>
						<colgroup>
							<col style="width:150px">
							<col style="width:auto">
							<col style="width:338px">
						</colgroup>
						<thead class="bs-1 ts-l">
							<tr class="px-v-l">
								<th scope="col" class="border-top-0">사업분야</th>
								<th scope="col" class="border-top-0">투자대상</th>
								<th scope="col" class="border-top-0">실제 해당 펀드에 투자된 경영체의 아이템</th>
							</tr>
						</thead>
						<tbody class="bs-1 t-t-c ts-m py-v-m">
							<tr class="px-v-xl t-t-c">
								<td class="before-none" >(19~23) 수산일반</td>
								<td class="text-start">농림수산식품경영체 중 수산업, 수산식품분야 사업을 영위하(려)는 경영체 또는 수산업 관련 업무에 종사하는 자, 원양산업자 및 수산 관련 R&D에 종사하는 경영체</td>
								<td class="">어류, 활어, 수산물, 건어물, 어묵, 유통, 건기식</td>									
							</tr>
							<tr class="px-v-xl t-t-c">
								<td class="before-none" >(20~22) 수산벤처창업</td>
								<td class="text-start">
									농림수산식품경영체 중 수산업, 수산식품분야 사업을 영위하(려)는 경영체 또는 수산업 관련 업무에 종사하는 자, 원양산업자 및 수산 관련 R&D에 종사하는 경영체로서, 다음 ① ∼ ⑥ 중 하나의 요건을 충족하는 경영체
									<span class="d-block pl-10px fw-300">
										① 사업 준비단계 또는 사업개시 후 7년이 지나지 아니한 기업<br/>
										② 정부‧공공기관이 인증‧확인한 수산분야 벤처기업, 스타트업 기업 등<br/>
										③ 정부‧지자체‧공공기관이 주관한 창업경진대회에서 입상한 수산분야 창업아이템을 활용하는 기업<br/>
										④ 수산 관련 공공‧민간의 연구개발 성과 또는 정부‧공공기관 인증을 받은 신기술을 사업화‧활용하는 기업<br/>
										⑤ 수산물‧수산업과 관련된 ICT 등 융복합 기술을 사업화‧활용하는 기업<br/>
										⑥ 수산분야 청년기업(대표자 또는 임직원의 50% 이상이 만 39세 이하)
									</span>
								</td>
								<td >수산물 가공, 응용소프트웨어, 연구개발</td>
							</tr>
							<tr class="px-v-xl t-t-c">
								<td class="before-none" >(23) 스마트양식</td>
								<td class="text-start">
									미래지향적 스마트양식 및 수산부산물 관련 사업을 영위하(려)는 경영체 중 다음 ①, ②중 하나에 해당하는 경영체
									<span class="d-block pl-10px fw-300">
										① 스마트양식 : ICT 및 각종 첨단 기술을 양식 산업에 접목하여 양식의 자동화ㆍ지능화 등을 선도할 수산경영체<br/>
										② 수산부산물 : 수산부산물의 친환경적ㆍ위생적 처리 및 고부가가치 재활용을 통해 폐기율 저감 효과를 가져올 수 있는 수산경영체
									</span>
								</td>
								<td >양식, 스마트, ict, 부산물</td>
							</tr>
							<tr class="px-v-xl t-t-c">
								<td class="before-none" >농림축산식품일반</td>
								<td class="text-start">
									농식품경영체 중, 농림축산식품분야 사업을 영위하(려)는 경영체
								</td>
								<td >농업, 식품, 제조, 유통</td>
							</tr>
							<tr class="px-v-xl t-t-c">
								<td class="before-none" >(23) 수출</td>
								<td class="text-start">농식품경영체 중 국내산 농·임산물, 가공식품 및 스마트팜(시공·시설자재·기자재 등) 등을 해외에 수출하(려)는 경영체 중 상호출자제한 기업집단에 속하지 않는 경영체</td>
								<td >농업, 식품, 제조, 유통</td>
							</tr>
							<tr class="px-v-xl t-t-c">
								<td class="before-none" >(20~22) 영파머스</td>
								<td class="text-start">
									다음 ① ∼ ③ 중 하나에 해당하며 1차 농산업을 영위하(려)는 농식품경영체
									<span class="d-block pl-10px fw-300">
										① 대표자가 만 49세 이하이거나, 만 49세 이하 임직원 비중이 50% 이상인 경영체<br/>
										② 대표자가 만 49세 이하이며 청년창업농 또는 후계 농업인에 선정된 경영체 <br/>
										③ 창업 7년 미만이고 대표자가 만 49세 이하인 농업법인
									</span>
								</td>
								<td >농업, 식품, 제조, 유통 (아이스크림, 김, 땅콩, 청과, 건기식)</td>
							</tr>
							<tr class="px-v-xl t-t-c">
								<td class="before-none" >(23) 영파머스</td>
								<td class="text-start">
									다음 ① ∼ ③ 중 하나에 해당하며 1차 농산업을 영위하(려)는 농식품경영체
									<span class="d-block pl-10px fw-300">
										① 대표자가 만 49세 이하이거나, 만 49세 이하 임직원 비중이 50% 이상인 경영체<br/>
										② 대표자가 만 49세 이하이며 청년창업농 또는 후계 농업인에 선정된 경영체 <br/>
										③ 창업 7년 미만이고 대표자가 만 49세 이하인 농업법인
									</span>
								</td>
								<td >농업, 식품, 제조, 유통 (한우가공, HRM, 버섯, 차류, 식자재)</td>
							</tr>
							<tr class="px-v-xl t-t-c">
								<td class="before-none" >(20~21) 농식품벤처</td>
								<td class="text-start">
									사업 개시 후 5년 미만으로 R&D기술, 새로운 비즈니스 모델 등을 활용하여 농업분야 가치창조를 모색하면서 다음 ① ~ ④ 중 하나의 조건을 충족하는 농식품경영체
									<span class="d-block pl-10px fw-300">
										① 농업계열 고교 및 농식품계열 대학 졸업자가 대표자, 최대주주이거나, 등기임원 중 2인 이상인 경영체<br/>
										② 대표자가 만 39세 이하 이거나, 만 39세 이하 임직원 비중이 50% 이상인 경영체 <br/>
										③ 사업 준비단계 또는 사업개시 후 3년 미만의 농식품경영체<br/>
										④ 스마트팜 보육센터 수료생 창업 경영체
									</span>
								</td>
								<td >농업, 식품, 제조, 유통 (한우가공, HRM, 버섯, 차류, 식자재)</td>
							</tr>
							<tr class="px-v-xl t-t-c">
								<td class="before-none" >(22~23) 농식품벤처</td>
								<td class="text-start">
									사업 개시 후 5년 미만으로 R&D기술, 새로운 비즈니스 모델 등을 활용하여 농업분야 가치창조를 모색하는 농식품경영체로서 다음 ① ∼ ⑤ 중 하나에 해당하는 농식품경영체
									<span class="d-block pl-10px fw-300">
										① 농업계열 고교 및 농식품계열 대학 졸업자가 대표자, 최대주주이거나, 등기임원 중 2인 이상인 경영체<br/>
										② 대표자가 만 39세 이하 이거나, 만 39세 이하 임직원 비중이 50% 이상인 경영체<br/>
										③ 사업 준비단계 또는 사업개시 후 3년 미만의 농식품경영체<br/>
										④ 스마트팜 보육센터 수료생이 창업한 스마트팜 경영체<br/>
										⑤ 우수기술 등급(투자용 기술평가모형에 근거한 T1∼T4) 보유 경영체
									</span>
								</td>
								<td >농업, 식품, 제조, 유통 (한우가공, HRM, 버섯, 차류, 식자재)</td>
							</tr>
							<tr class="px-v-xl t-t-c">
								<td class="before-none" >(19~21) 마이크로</td>
								<td class="text-start">
									사업준비 단계 또는 사업 개시 후 5년 미만의 농식품경영체
								</td>
								<td >농업, 식품, 제조, 유통 (단백질, HRM, 전자상거래, 밀키트)</td>
							</tr>
							<tr class="px-v-xl t-t-c">
								<td class="before-none" >(22~23) 마이크로</td>
								<td class="text-start">
									다음 ①, ② 중 하나에 해당하는 농식품경영체
									<span class="d-block pl-10px fw-300">
										① 사업준비 단계 또는 사업 개시 후 5년 미만의 농식품경영체<br/>
										② 사업 개시 후 7년 미만으로 투자 직전년도까지 각 연도별 연간 매출액이 20억 원 이하인 경영체
									</span>
								</td>
								<td >농업, 식품, 제조, 유통 (단백질, HRM, 전자상거래, 밀키트)</td>
							</tr>
							<tr class="px-v-xl t-t-c">
								<td class="before-none" >(20) 경북지역특성화</td>
								<td class="text-start">
									농식품경영체로서
									<span class="d-block pl-10px fw-300">
										농식품경영체로서 ① 출자한 지자체(경북) 내 본점이나 주된 사무소의 소재지를 두고 있는(두려는) 경영체 또는 ② 출자 지자체(경북) 내 투자를 통해 고용창출 효과가 예상되는 경영체
									</span>
								</td>
								<td >양계, 데이터, 플랫폼, 스마트팜</td>
							</tr>
							<tr class="px-v-xl t-t-c">
								<td class="before-none" >(21~22) 스마트농업</td>
								<td class="text-start">
									다음 ①, ② 중 하나에 해당하는 농식품경영체
									<span class="d-block pl-10px fw-300">
										① ICT 및 각종 첨단 기술과 농업 생산을 비롯해 농업 밸류체인(생산준비단계, 생산, 유통 등) 전반을 접목한 농업의 스마트화를 선도할 농식품경영체 <br/>
										② 혁신기술을 활용하여 농업분야의 탄소저감 효과를 가져올 수 있는 농식품경영체
									</span>
								</td>
								<td >양돈, 스마트팜, 축산물, 동물용의약품</td>
							</tr>
							<tr class="px-v-xl t-t-c">
								<td class="before-none" >(23) 스마트농업</td>
								<td class="text-start">
									① 스마트농업 : ICT 및 각종 첨단 기술과 농업 생산을 비롯해 농업 밸류체인(생산준비, 생산, 유통 등) 전반을 접목한 농업의 스마트화를 선도할 경영체  <br/>
									② 탄소중립 : 혁신기술을 활용해 농업분야의 탄소저감 효과를 가져올 수 있는 경영체  <br/>
									③ ①, ② 중 하나를 수출하(려)는 경영체
								</td>
								<td >양돈, 스마트팜, 축산물, 동물용의약품</td>
							</tr>
							<tr class="px-v-xl t-t-c">
								<td class="before-none" >(21~22) 그린바이오</td>
								<td class="text-start">
									농식품경영체 중 그린바이오 5대 분야(① ~ ⑤) 사업을 영위하(려)는 농식품경영체<br/>
									①마이크로바이옴, ②대체식품·메디푸드, ③종자산업, ④동물용 의약품, ⑤기타 생명소재
								</td>
								<td >한약재, 건기식, 식용유지</td>
							</tr>
							<tr class="px-v-xl t-t-c">
								<td class="before-none" >(23) 그린바이오</td>
								<td class="text-start">
									생명자원 및 정보에 생명공학기술을 적용하여 다양한 부가가치를 창출하는 산업으로 다음 5대 분야(① ~ ⑤)로 구분되는 그린바이오산업 분야의 사업을 영위하(려)는 농식품경영체<br/>
									①마이크로바이옴, ②대체식품·메디푸드, ③종자산업, ④동물용 의약품, ⑤기타 생명소재
								</td>
								<td >한약재, 건기식, 식용유지</td>
							</tr>
							<tr class="px-v-xl t-t-c">
								<td class="before-none" >푸드테크</td>
								<td class="text-start">
									푸드테크 10대 핵심분야(① ~ ⑩)에 종사하(려)는 사업 준비 또는 사업 개시 후 7년 미만 농식품경영체<br/>
									①세포배양식품 생산기술, ②식물기반식품 제조기술, ③간편식제조기술, ④식품프린팅 기술, ⑤식품 스마트제조기술, ⑥식품 스마트 유통기술, ⑦식품 커스터마이징, ⑧외식 푸드테크 기술, ⑨식품 업사이클링 기술, ⑩친환경 식품포장기술
								</td>
								<td >배양육, HRM, 유통, 푸드테크, 친환경</td>
							</tr>
							<tr class="px-v-xl t-t-c">
								<td class="before-none" >징검다리</td>
								<td class="text-start">
									농식품투자조합으로부터 기존에 투자금을 받은 농식품경영체 중 향후 일자리 창출이 기대되는 농식품경영체
								</td>
								<td >브랜드커머스, 배송, 축산, 가공</td>
							</tr>
							<tr class="px-v-xl t-t-c">
								<td class="before-none" >창업보육</td>
								<td class="text-start">
									벤처·창업 지원 수혜(농식품 벤처·창업 활성화 지원사업 또는 청년식품창업Lab 사업을 통해 초기 창업보육을 거친 창업 7년 이내 농식품 기업)를 받은 농식품경영체(3억원 이내로 투자)
								</td>
								<td >스마트팜, 배송, 건기식, 양액, 가공</td>
							</tr>
							<tr class="px-v-xl t-t-c">
								<td class="before-none" >(23) 농식품혁신스타트업</td>
								<td class="text-start">
									농식품투자조합법 제3조, 동법 시행령 제3조 및 시행규칙 제2조에 따른 농식품경영체 중 ① 투자유치 이력이 없는 경영체로서 
									중소기업창업 지원법」에 따른 창업기업 또는 ② 「후계농어업인 및 청년농어업인 육성지원에 관한 법률」에 따른 청년농업인이 대표권이 있는 임원으로 투자 시점
									6개월 전부터 계속하여 등기되어 있는 경영체
								</td>
								<td >-</td>
							</tr>
							<tr class="px-v-xl t-t-c">
								<td class="before-none" >세컨더리</td>
								<td class="text-start">
									① 농식품모태펀드로부터 출자받은 자펀드가 농식품경영체에 신규 투자한 투자자산의 인수* 또는 <br/>
									② 농식품모태펀드로부터 출자받은 자펀드의 지분 매입 <br/>
									* ①의 경우, 프로젝트 투자 방식의 투자 지분은 인수대상에서 제외<br/>
									 ※ 단, 농식품투자계정에 한함
								</td>
								<td >-</td>
							</tr>
						</tbody>
						
					</table>
				</div>
			</div>
		</div>
		<div class="">
			<div class="tit-box-1 mb-8px">
				<p class="txt1 mb-8px text-black">관련법령</p>
			</div>
			<div class="shadow-box-1 px-16px pb-24px">
				<div class="table-box-1 m-overbox no-hover">
					<table>
						<caption>
							민원서식 목록
						</caption>
						<colgroup>
							<col style="width:85px">
							<col style="width:auto">
							<col style="width:180px">
							
						</colgroup>
						<thead class="bs-1 ts-l">
							<tr class="px-v-l">
								<th scope="col" class="border-top-0">번호</th>
								<th scope="col" class="border-top-0 text-start">제목</th>
								<th scope="col" class="border-top-0">바로가기</th>
							</tr>
						</thead>
						<tbody class="bs-1 t-t-c ts-m py-v-m">
							<tr class="px-v-xl t-t-c">
								<td >1
								</td>
								<td  class="text-start">농림수산식품투자조합 결성 및 운용에 관한 법률
								</td>
								<td >
									<button class="btn-black bs-s w-100" id="btnStatuteFirst">바로가기 <i class="icon-arrow-right ml-4px"></i></button>
								</td>
							</tr>
							<tr class="px-v-xl t-t-c">
								<td >2
								</td>
								<td  class="text-start">농림수산식품투자조합 결성 및 운용에 관한 법률 시행령
								</td>
								<td >
									<button class="btn-black bs-s w-100" id="btnStatuteSecond">바로가기 <i class="icon-arrow-right ml-4px"></i></button>
								</td>
							</tr>
							<tr class="px-v-xl t-t-c">
								<td >3
								</td>
								<td  class="text-start">농림수산식품투자조합 결성 및 운용에 관한 법률 시행규칙
								</td>
								<td >
									<button class="btn-black bs-s w-100" id="btnStatuteThird">바로가기 <i class="icon-arrow-right ml-4px"></i></button>
								</td>
							</tr>
							<tr class="px-v-xl t-t-c">
								<td >4
								</td>
								<td  class="text-start">농림수산식품투자모태조합 및 농림수산식품투자조합의 운용 등에 관한 기본규정
								</td>
								<td >
									<button class="btn-black bs-s w-100" id="btnStatuteFourth">바로가기 <i class="icon-arrow-right ml-4px"></i></button>
								</td>
							</tr>
							<tr class="px-v-xl t-t-c">
								<td >5
								</td>
								<td  class="text-start">그 밖의 농림수산식품산업 관련 업무에 종사하는 자
								</td>
								<td >
									<button class="btn-black bs-s w-100" id="btnStatuteFifth">바로가기 <i class="icon-arrow-right ml-4px"></i></button>
								</td>
							</tr>
							<tr class="px-v-xl t-t-c">
								<td >6
								</td>
								<td  class="text-start">국가과학기술표준분류체계
								</td>
								<td >
									<button class="btn-black bs-s w-100" id="btnStatuteSixth">바로가기 <i class="icon-arrow-right ml-4px"></i></button>
								</td>
							</tr>
							<tr class="px-v-xl t-t-c">
								<td >7
								</td>
								<td  class="text-start">조세특례제한법
								</td>
								<td >
									<button class="btn-black bs-s w-100" id="btnStatuteSeventh">바로가기 <i class="icon-arrow-right ml-4px"></i></button>
								</td>
							</tr>
							<tr class="px-v-xl t-t-c">
								<td >8
								</td>
								<td  class="text-start">벤처투자 촉진에 관한 법률
								</td>
								<td >
									<button class="btn-black bs-s w-100" id="btnStatuteEighth">바로가기 <i class="icon-arrow-right ml-4px"></i></button>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>	
	</div>
	<div id="supportProjectBA" >
		<div class="row" style="margin:-20px;">
			<div class="col-12 col-md flex-md-grow-0 p-20 flex-b-md-344px">
				<div class="tit-box-1 mb-8px">
					<p class="txt1 mb-16px text-black">투자유치 전 지원</p>
					<button class="border-0 bs-m btn-primary-ouline-hover round-24px" id="btnSprtAplyBefore">
					<i class="icon-edit"></i>	신청하기
					</button>
				</div>
			</div>
			<div class="col p-20">
				<div class="mb-24px">
					<div class="tit-top-1 mb-8px">
						<div class="row">
							<div class="col">
								<p class="txt1">
									<span class="square-box-2">
										01
									</span>
									투자유치 상담지원
								</p>
							</div>
						</div>
					</div>
					<div class="shadow-box-1 card-box-txt-1 hover-ani-none">
						<img src="<c:url value='/images/sub/investguide-img01.png'/>" alt="">
						<ul class="txt-list">
							<li>
								<p>사업 개요</p>
								<span>농림수산식품모태펀드 제도, 투자 대상 및 절차, 투자지원사업 등 안내</span>
							</li>
							<li>
								<p>지원 대상</p>
								<span>농림수산식품경영체</span>
							</li>
							<li>
								<p>지원 내용</p>
								<span>경영체 기초 정보 및 사업 내용 파악 후 투자지원사업 등 투자 유치를 위한 정보 제공</span>
							</li>
						</ul>
					</div>
				</div>
				<div class="mb-24px">
					<div class="tit-top-1 mb-8px">
						<div class="row">
							<div class="col">
								<p class="txt1">
									<span class="square-box-2">
										02
									</span>
									현장코칭 지원
								</p>
							</div>
						</div>
					</div>
					<div class="shadow-box-1 card-box-txt-1 hover-ani-none">
						<img src="<c:url value='/images/sub/investguide-img02.png'/>" alt="">
						<ul class="txt-list">
							<li>
								<p>사업 개요</p>
								<span>투자유치 지원을 위한 기초 컨설팅</span>
							</li>
							<li>
								<p>지원 대상</p>
								<span>농림수산식품경영체</span>
							</li>
							<li>
								<p>지원 내용</p>
								<span>사업 비즈니스 모델, 제품/서비스, 대표자 역량 등 경영체 분석 등 기초 컨설팅 지원</span>
							</li>
						</ul>
					</div>
				</div>
				<div class="mb-24px">
					<div class="tit-top-1 mb-8px">
						<div class="row">
							<div class="col">
								<p class="txt1">
									<span class="square-box-2">
										03
									</span>
									맞춤형 컨설팅 지원
								</p>
							</div>
						</div>
					</div>
					<div class="shadow-box-1 card-box-txt-1 hover-ani-none">
						<img src="<c:url value='/images/sub/investguide-img03.png'/>" alt="">
						<ul class="txt-list">
							<li>
								<p>사업 개요</p>
								<span>경영체별 맞춤형 컨설팅을 통한 투자유치 역량 강화 지원</span>
							</li>
							<li>
								<p>지원 대상</p>
								<span>농림수산식품경영체</span>
							</li>
							<li>
								<p>지원 내용</p>
								<span>투자유치 기본 교육, 기업 종합 진단, 투자유치 전략 수립, 사업계획서 및 IR자료 작성 등 경영체의 특성과 여건에 따른 컨설팅 지원</span>
							</li>
						</ul>
					</div>
				</div>
				<div class="">
					<div class="tit-top-1 mb-8px">
						<div class="row">
							<div class="col">
								<p class="txt1">
									<span class="square-box-2">
										04
									</span>
									사업설명회(IR) 참가 지원
								</p>
							</div>
						</div>
					</div>
					<div class="shadow-box-1 card-box-txt-1 hover-ani-none">
						<img src="<c:url value='/images/sub/investguide-img04.png'/>" alt="">
						<ul class="txt-list">
							<li>
								<p>사업 개요</p>
								<span>성장성 있는 우수 비즈니스모델을 보유한 농림수산식품경영체 대상 투자 유치 기회 제공</span>
							</li>
							<li>
								<p>지원 대상</p>
								<span>농림수산식품경영체</span>
							</li>
							<li>
								<p>지원 내용</p>
								<span>농식품투자조합 운용사 등 농식품 분야 투자자 대상 사업설명 및 네트워킹 기회 지원</span>
							</li>
						</ul>
					</div>
				</div> 
			</div>
		</div>
		<div class="bg-ele-Line my-56px" style="height:1px;"></div>
		<div class="row" style="margin:-20px;">
			<div class="col-12 col-md flex-md-grow-0 p-20 flex-b-md-344px">
				<div class="tit-box-1 mb-8px">
					<p class="txt1 mb-16px text-black">투자유치 후 지원</p>
					<button class="border-0 bs-m btn-primary-ouline-hover round-24px" id="btnSprtAplyAfter"><i class="icon-edit"></i>	신청하기</button>
				</div>
			</div>
			<div class="col p-20">
				<div class="mb-24px">
					<div class="tit-top-1 mb-8px">
						<div class="row">
							<div class="col">
								<p class="txt1">
									<span class="square-box-2">
										01
									</span>
									온라인 마케팅 지원
								</p>
							</div>
						</div>
					</div>
					<div class="shadow-box-1 card-box-txt-1 hover-ani-none">
						<img src="<c:url value='/images/sub/investguide-img05.png'/>" alt="">
						<ul class="txt-list">
							<li>
								<p>사업 개요</p>
								<span>피투자 농림수산식품경영체별 맞춤형 온라인 홍보 지원을 통한 마케팅 역량 강화 지원</span>
							</li>
							<li>
								<p>지원 대상</p>
								<span>농림수산식품투자조합이 투자한 농림수산식품경영체</span>
							</li>
							<li>
								<p>지원 내용</p>
								<span>경영체별 마케팅 역량 진단 및 온라인 마케팅 교육, 인플루언서·PR 등 온라인 마케팅 지원</span>
							</li>
						</ul>						
					</div>
				</div>
				<div class="mb-24px">
					<div class="tit-top-1 mb-8px">
						<div class="row">
							<div class="col">
								<p class="txt1">
									<span class="square-box-2">
										02
									</span>
									오프라인 마케팅 지원
								</p>
							</div>
						</div>
					</div>
					<div class="shadow-box-1 card-box-txt-1 hover-ani-none">
						<img src="<c:url value='/images/sub/investguide-img06.png'/>" alt="">
						<ul class="txt-list">
							<li>
								<p>사업 개요</p>
								<span>피투자 농림수산식품경영체의 제품 판로 확대 및 사업역량 강화 지원</span>
							</li>
							<li>
								<p>지원 대상</p>
								<span>농림수산식품투자조합이 투자한 농림수산식품경영체</span>
							</li>
							<li>
								<p>지원 내용</p>
								<span>국·내외 해외 박람회 참가 지원, 구매·수출 상담회 등을 통한 사업소개 및 홍보 등 현장마케팅 지원</span>
							</li>
						</ul>
					</div>
				</div>
				<div class="mb-24px">
					<div class="tit-top-1 mb-8px">
						<div class="row">
							<div class="col">
								<p class="txt1">
									<span class="square-box-2">
										03
									</span>
									스케일업프로그램 지원
								</p>
							</div>
						</div>
					</div>
					<div class="shadow-box-1 card-box-txt-1 hover-ani-none">
						<img src="<c:url value='/images/sub/investguide-img07.png'/>" alt="">
						<ul class="txt-list">
							<li>
								<p>사업 개요</p>
								<span>피투자 농림수산식품경영체의 가치 상승을 위한 성장단계별 맞춤 컨설팅 지원</span>
							</li>
							<li>
								<p>지원 대상</p>
								<span>농림수산식품투자조합이 투자한 농림수산식품경영체</span>
							</li>
							<li>
								<p>지원 내용</p>
								<span>경영·재무·회계·법률·사업전략·IPO·M&A 등 경영체별 맞춤 컨설팅 지원</span>
							</li>
						</ul>
					</div>
				</div>
				<div class="mb-24px">
					<div class="tit-top-1 mb-8px">
						<div class="row">
							<div class="col">
								<p class="txt1">
									<span class="square-box-2">
										04
									</span>
									후속투자 유치 지원
								</p>
							</div>
						</div>
					</div>
					<div class="shadow-box-1 card-box-txt-1 hover-ani-none">
						<img src="<c:url value='/images/sub/investguide-img08.png'/>" alt="">
						<ul class="txt-list">
							<li>
								<p>사업 개요</p>
								<span>피투자 농림수산식품경영체의 지속 성장을 위한 후속 투자 유치 지원</span>
							</li>
							<li>
								<p>지원 대상</p>
								<span>농림수산식품투자조합이 투자한 농림수산식품경영체</span>
							</li>
							<li>
								<p>지원 내용</p>
								<span>징검다리펀드 운용사 및 농식품 분야 투자자 대상 사업설명 및 네트워킹 기회 제공</span>
							</li>
						</ul>
					</div>
				</div>
				<div class="">
					<div class="tit-top-1 mb-8px">
						<div class="row">
							<div class="col">
								<p class="txt1">
									<span class="square-box-2">
										05
									</span>
									투자자산 회수 지원
								</p>
							</div>
						</div>
					</div>
					<div class="shadow-box-1 card-box-txt-1 hover-ani-none">
						<img src="<c:url value='/images/sub/investguide-img09.png'/>" alt="">
						<ul class="txt-list">
							<li>
								<p>사업 개요</p>
								<span>농식품 산업의 투자자산 회수시장 조성 등 농식품모태펀드의 선순환 투자구조 조성 지원</span>
							</li>
							<li>
								<p>지원 대상</p>
								<span>농림수산식품투자조합이 투자한 농림수산식품경영체</span>
							</li>
							<li>
								<p>지원 내용</p>
								<span>IPO, M&A 전문가 강연 및 구매 희망 수요기업과의 비즈니스 미팅 등 컨퍼런스 개최</span>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<%-- ############################# 조직도 내용 종료 ############################# --%>
<%-- ############################# 가온누리 인베지움 안내 내용 시작 ############################# --%>
<div id="APFSCrowdfunding_Data">
	<div id="introduceCrowd">
		<div class="introduction-imgbox-1">
					<div class="row">
						<div class="col-12 col-md imgbox">
							<div class="bg-primary-t5 py-16px round-8px text-center w-auto w-md-588px">
								<img src="<c:url value='/images/sub/Introduction-img16.png'/>" alt="" class="">
							</div>
						</div>
						<div class="col-12 col-md txtbox">
							<p class="txt1">농식품 크라우드펀딩이란?</p>
							<p class="txt2">Crowd Funding</p>
							<span class="txt3">
								농식품 크라우드펀딩은 농식품 기업이 창업 초기에 필요한 자금을 마련할 수 있도록 중개업자의 온라인플랫폼에서 “집단지성(The Wisdom of Crowds)”을 활용하여 다수의 소액투자자로부터 자금을 조달하는 행위를 말합니다.
							</span>
						</div>
					</div>
					<div class="bg-ele-Line my-56px" style="height:1px;"></div>
				</div>
				<div class="">
					<div class="tit-box-1 mb-8px">
						<p class="txt1 mb-8px text-black">농식품 크라우드펀딩 유형별 분류</p>
					</div>
					<div class="shadow-box-1 px-16px pb-24px">
						<div class="table-box-1 m-overbox no-hover">
							<table>
								<caption>
									민원서식 목록
								</caption>
								<colgroup>
									<col style="width:auto">
									<col style="width:auto">
									<col style="width:auto">
								</colgroup>
								<thead class="bs-1 ts-l">
									<tr class="px-v-l">
										<th scope="col" class="border-top-0">유형</th>
										<th scope="col" class="border-top-0">자금모집방식</th>
										<th scope="col" class="border-top-0">보상방식</th>										
									</tr>
								</thead>
								<tbody class="bs-1 t-t-c ts-m py-v-m">
									<tr class="px-v-xl t-t-c">
										<td  class="">증권형 (투자형)</td>
										<td  class="">증권(주식·채권) 발행</td>
										<td  class="">금전적 보상 (배당금, 원금/이자 등)</td>
									</tr>
									<tr class="px-v-xl t-t-c">
										<td  class="">후원형 (리워드형)</td>
										<td  class="">후원 / 기부금 납입</td>
										<td  class="">농식품 기업이 생산/유통하는 재화·서비스 등</td>																			
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<div class="bg-ele-Line my-56px" style="height:1px;"></div>
				<div class="">
					<div class="tit-box-1 mb-8px">
						<p class="txt1 mb-16px text-black">농식품 크라우드펀딩 중개업체
							<span class="d-block fs-15px fw-300">농심품 분야에 창의적인 아이디어를 가진 개인이나 기업은 프로젝트를 개설하고 이를 많은 대중들에게 알려 자금을 모집하게 됩니다. 이때 프로젝트 개설자와 대중들을 연결해주는 통로 역할을 하는 것이 바로 크라우드펀딩 플랫폼(중개업체)입니다.</span>
						</p>
					</div>
					<div class="shadow-box-1 px-16px pb-24px">
						<div class="table-box-1 m-overbox no-hover">
							<table>
								<caption>
									민원서식 목록
								</caption>
								<colgroup>
									<col style="width:auto">
									<col style="width:auto">
									<col style="width:auto">
								</colgroup>
								<thead class="bs-1 ts-l">
									<tr class="px-v-l">
										<th scope="col" class="border-top-0">
											구분</th>
										<th scope="col" class="border-top-0">중개업체</th>
										<th scope="col" class="border-top-0">홈페이지 바로가기</th>										
									</tr>
								</thead>
								<tbody class="bs-1 t-t-c ts-m py-v-m">
									<tr class="px-v-xl t-t-c">
										<td  rowspan="4" class="before-none" >후원형·증권형 모든 프로젝트 진행 가능</td>
										<td  class="">와디즈</td>
										<td  class="text-start"><a href="https://www.wadiz.kr" target="_blank">https://www.wadiz.kr</a></td>
									</tr>																		
									<tr class="px-v-xl t-t-c">
										<td  class="">크라우디</td>
										<td  class="text-start"><a href="https://www.ycrowdy.com" target="_blank">https://www.ycrowdy.com</a></td>
									</tr>
									<tr class="px-v-xl t-t-c">
										
										<td  class="">오마이컴퍼니</td>
										<td  class="text-start"><a href="https://www.ohmycompany.com" target="_blank">https://www.ohmycompany.com</a></td>
									</tr>
									<tr class="px-v-xl t-t-c">
										<td  class="">펀딩포유</td>
										<td  class="text-start"><a href="https://www.funding4u.co.kr" target="_blank">https://www.funding4u.co.kr</a></td>
									</tr>
									<tr class="px-v-xl t-t-c">
										<td  rowspan="2" class="before-none" >후원형 프로젝트만 진행 가능</td>
										<td  class="">텀블벅</td>
										<td  class="text-start"><a href="https://www.tumblbug.com" target="_blank">https://tumblbug.com</a></td>
									</tr>
									<tr class="px-v-xl t-t-c">
										<td  class="">해피빈</td>
										<td  class="text-start"><a href="https://happybean.naver.com/crowdFunding" target="_blank">https://happybean.naver.com/crowdFunding</a></td>
									</tr>
									<tr class="px-v-xl t-t-c">
										<td  rowspan="2" class="before-none" >증권형 프로젝트만 진행 가능</td>
										<td  class="">IBK투자증권</td>
										<td  class="text-start"><a href="https://crowd.ibks.com/" target="_blank">https://crowd.ibks.com/</a></td>
									</tr>
									<tr class="px-v-xl t-t-c">
										<td  class="">코리아에셋투자증권</td>
										<td  class="text-start"><a href="https://crowd.kasset.co.kr/main" target="_blank">https://crowd.kasset.co.kr/main</a></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<div class="bg-ele-Line my-56px" style="height:1px;"></div>
				<div class="">
					<div class="tit-box-1 mb-8px">
						<p class="txt1 mb-8px text-black">투자자격 및 한도</p>
					</div>
					<div class="shadow-box-1 px-16px pb-24px">
						<div class="table-box-1 m-overbox no-hover">
							<table>
								<caption>
									민원서식 목록
								</caption>
								<colgroup>
									<col style="width:auto">
									<col style="width:auto">
									<col style="width:auto">
									<col style="width:auto">
								</colgroup>
								<thead class="bs-1 ts-l">
									<tr class="px-v-l">
										<th scope="col" class="border-top-0">구분</th>
										<th scope="col" class="border-top-0">투자자구분</th>
										<th scope="col" class="border-top-0">동일기업당 투자 한도</th>
										<th scope="col" class="border-top-0">연간 총 투자한도</th>										
									</tr>
								</thead>
								<tbody class="bs-1 t-t-c ts-m py-v-m">
									<tr class="px-v-xl t-t-c">
										<td  class="before-none" rowspan="3">증권형</td>
										<td  class="">일반투자자</td>
										<td  class="">500만 원</td>
										<td  class="">1,000만 원</td>
									</tr>
									<tr class="px-v-xl t-t-c">
										<td  class="">적격투자자</td>
										<td  class="">1,000만 원</td>
										<td  class="">2,000만 원</td>																			
									</tr>
									<tr class="px-v-xl t-t-c">
										<td  class="">전문투자자</td>
										<td  class="">한도 없음</td>
										<td  class="">한도 없음</td>																			
									</tr>
									<tr class="px-v-xl t-t-c">
										<td  class="before-none" >후원형</td>
										<td  class="" colspan="3">제한 없음</td>																			
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					<div class="my-16px fs-13px fw-300 text-red" >
						※ 적격투자자 : 금융소득종합과세 대상자, 사업소득+근로소득이 1억원 이상인 자<br/>
 						※ 전문투자자 : 창투조합, KVF, 신기술조합, 개인투자조합, 전문엔젤, 적격엔젤 등
					</div>
				</div>
				<div class="bg-ele-Line my-56px" style="height:1px;"></div>
				<div class="mb-56px">
					<div class="tit-box-1 mb-8px">
						<p class="txt1 mb-8px text-black">농식품 크라우드펀딩 주요 발행방법</p>
					</div>
					<div class="shadow-box-1 px-16px pb-24px">
						<div class="table-box-1 m-overbox no-hover">
							<table>
								<caption>
									민원서식 목록
								</caption>
								<colgroup>
									<col style="width:auto">
									<col style="width:auto">
									<col style="width:auto">
									<col style="width:auto">
								</colgroup>
								<thead class="bs-1 ts-l">
									<tr class="px-v-l">
										<th scope="col" class="border-top-0">유형</th>
										<th scope="col" class="border-top-0" colspan="2">증권형</th>
										<th scope="col" class="border-top-0">후원형</th>										
									</tr>
								</thead>
								<tbody class="bs-1 t-t-c ts-m py-v-m">
									<tr class="px-v-xl t-t-c">
										<td >자격요건</td>
										<td class="text-start" colspan="2">
											자본시장법에 따른 창업 7년 이내 비상장 중소기업 또는 코넥스 상장 후 3년이 경과하지 않고 공모방식으로 자금을 조달한 경험이 없는 기업. 단, 아래의 조건을 충족하는 기업은 업력과 무관하게 진행 가능<br/>
											1) 벤처기업, 이노비즈기업, 메인 비즈기업, 인증 사회적기업<br/>
											2) 프로젝트성 사업 수행자<br/>
											* 상기 자격을 갖추었더라도 금융·보험업, 부동산업, 무도장운영업, 갬블링·베팅업 등 특정 업종은 제외
										</td>
										<td class="">농식품 관련분야의 모든 법인 및
											개인 사업자 (개인사업자 포함)
											</td>
									</tr>
									<tr class="px-v-xl t-t-c">
										<td >발행금액</td>
										<td class="text-start" colspan="2">
											연간 30억 원<br/>*크라우드펀딩을 통한 모집가액과 과거 1년간 발행금액의 합산이 발행한도 30억 원을 초과하지 않아야 함
										</td>
										<td class="">최저 및 최대 제한한도 없음</td>
									</tr>
									<tr class="px-v-xl t-t-c">
										<td >제출서류</td>
										<td class="text-start before-none">
											<div class="txt-lev-box-1">
												<p class="txt-dot fw-300">사업자등록증</p>
												<p class="txt-dot fw-300">직전 3개년도의 재무제표</p>
												<p class="txt-dot fw-300">정관</p>
											</div>
										</td>
										<td class="text-start">
											<div class="txt-lev-box-1">
												<p class="txt-dot fw-300">법인등기부등본</p>
												<p class="txt-dot fw-300">기타 사업 타당성을 입증할 수 있는 증빙서류 등</p>
											</div>
										</td>
										<td class="">기업소개 자료</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
		<div class="txtbox-design-2">
			<div class="row">
				<div class="col flex-b64px flex-md-grow-0">
					<i class="icon-exclamation-circle cycle"></i>
				</div>
				<div class="col">
					<p class="txt1">꼭! 확인하세요.</p>
					<span class="txt2">농식품 크라우드 펀딩에 대해 온라인 교육자료를 통해 더 자세히 알아보실 수 있습니다.</span>
					<button class="border-0 bs-m btn-primary-ouline-hover mt-2px round-24px" style="margin-left: 40px; " id="btnChecktInfo">동영상 바로가기<i class="icon-arrow-right"></i></button>
				</div>
			</div>
		</div>
	</div>
	<div id="supportProject" >
		<div class="introduction-imgbox-1 mb-56px">
			<div class="row">
				<div class="col-12 col-md imgbox">
					<div class="bg-primary-t5 py-16px round-8px text-center w-auto w-md-588px">
						<img src="<c:url value='/images/sub/Introduction-img17.png'/>" alt="" class="">
					</div>
				</div>
				<div class="col-12 col-md txtbox">
					<p class="txt1">지원대상</p>
					<p class="txt3">농작물 재배업, 축산업, 임업, 식품산업, 반려동물 산업, 곤충산업 분야 등 농식품 분야에 종사하는 개인 및 사업자</p>
					<button class="btn-primary bs-m mt-8px" id="btnOperatingStandards">운영기준 자세히 보기<i class="icon-arrow-right"></i></button>
				</div>
			</div>
		</div>
		<div class="">
			<div class="tit-box-1 mb-24px">
				<p class="align-items-end d-flex mb-16px text-black txt1">지원내용<span class="fs-14px fw-300 ml-8px text-red">※ 모든 지원사업은 중복 신청이 가능합니다.</span></p>
				<div class="bg-ele-Line mt-16px" style="height:1px;"></div>
			</div>
			<div class="tit-top-1 mb-8px">
				<div class="row">
					<div class="col">
						<p class="txt1">
							<span class="square-box-2">
								01
							</span>
							"현장코칭" 이란?
						</p>
						<span class="d-block fs-15px pl-40px">농식품 크라우드펀딩, 대체 무엇인가요? 농식품 크라우드펀딩 제도와 중개사에 대해 알려드립니다! 전문 컨설팅 기관에서 직접 경영체에 방문하여 크라우드펀딩 제도, 유형, 전략을 1:1 안내해드립니다.</span>	
					</div>
				</div>
			</div>
			<div class="shadow-box-1 px-16px pb-24px">
				<div class="table-box-1 m-overbox no-hover">
					<table>
						<caption>
							민원서식 목록
						</caption>
						<colgroup>
							<col style="width:195px">
							<col style="width:195px">
							<col style="width:auto">
							<col style="width:auto">
						</colgroup>
						<thead class="bs-1 ts-l">
							<tr class="px-v-l">
								<th scope="col" class="border-top-0" colspan="2">구분</th>
								<th scope="col" class="border-top-0">증권형</th>
								<th scope="col" class="border-top-0">후원형</th>										
							</tr>
						</thead>
						<tbody class="bs-1 t-t-c ts-m py-v-m">
							<tr class="px-v-xl t-t-c">
								<td class="before-none" rowspan="2">지원내용</td>
								<td class="">기본</td>
								<td class="" colspan="2">(공통) 기업분석, 펀딩유형 및 전략 수립, 투자 전략 코칭 등</td>
							</tr>
							<tr class="px-v-xl t-t-c">
								<td class="">심화</td>
								<td class="">증권형 구조 설계(형태, 조건, 시기, 투자포인트 등),<br>IR 자료 자문, 투자 상담 등</td>
								<td class="">후원형 상품 구성 및 마케팅 콘텐츠 관련 상담,<br>사업자에 맞는 중개사 안내 등</td>																			
							</tr>
							<tr class="px-v-xl t-t-c">
								<td class="" colspan="2">지원기업</td>
								<td class="" colspan="2">선착순 50개사</td>																			
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="text-center">
				<button class="btn-primary bs-l mt-24px mb-40px" id="btnPrgrmCoach">신청바로가기<i class="icon-arrow-right"></i></button>
				<div id="appContent"></div>
			</div>
		</div>
		<div class="bg-ele-Line mb-24px" style="height:1px;"></div>
			<div class="">
				<div class="tit-top-1 mb-8px">
					<div class="row">
						<div class="col">
							<p class="txt1">
								<span class="square-box-2">
									02
								</span>
								“컨설팅 비용지원” 이란?
							</p>
							<span class="d-block fs-15px pl-40px">크라우드펀딩, 프로젝트 오픈을 위한 비용이 부담되신다면? 농식품 크라우드펀딩 프로젝트 오픈을 위한 법률 및 회계자문, 기업 및 제품의 홍보 콘텐츠 제작 (사진, 동영상, 상세페이지 제작 등), SNS<br/>홍보 비용을 지원해드립니다!
							</span>							
						</div>
					</div>
				</div>
				<div class="shadow-box-1 px-16px pb-24px">
					<div class="table-box-1 m-overbox no-hover">
						<table>
							<caption>
								민원서식 목록
							</caption>
							<colgroup>
								<col style="width:auto">
								<col style="width:auto">
								<col style="width:auto">
							</colgroup>
							<thead class="bs-1 ts-l">
								<tr class="px-v-l">
									<th scope="col" class="border-top-0">
										구분</th>
									<th scope="col" class="border-top-0">증권형</th>
									<th scope="col" class="border-top-0">후원형</th>										
								</tr>
							</thead>
							<tbody class="bs-1 t-t-c ts-m py-v-m">
								<tr class="px-v-xl t-t-c">
									<td class="">지원금액</td>
									<td class="">250만원이내 (vat 제외 100%)</td>
									<td class="">100만원이내 (vat 제외 100%)</td>
								</tr>		
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<div class="my-16px fs-13px fw-300 text-red" >
				※ 컨설팅 비용지원, 수수료지원은 선착순으로 지원되며 예산 소진시 조기마감될 수 있습니다.
			</div>
			<div class="">
				<div class="shadow-box-1 p-24px">
					<div class="bg-primary fs-18 fw-600 lh-1 mb-16px p-16px round-8px text-center text-white">신청 및 청구 순서</div>
					<div class="step-box-1 content-none">
						<div class="row">
							<div class="col active">
								<i class="icon-location-crosshairs F w-40px h-40px "></i>
								<p class="txt1">STEP 1</p>
								<span class="txt2">컨설팅 업체 선정</span>
							</div>
							<div class="col active">
								<i class="icon-file-text-edit F w-40px h-40px "></i>
								<p class="txt1">STEP 2</p>
								<span class="txt2">기업정보입력</span>
								<span class="d-block fs-12px txt3">* 증빙서류 : 거래내역서, <br/>컨설팅 업체 사업자등록증, <br/>지출증빙(하단참조)</span>
							</div>
							<div class="col active">
								<i class="icon-cloud-check F w-40px h-40px "></i>
								<p class="txt1">STEP 3</p>
								<span class="txt2">크라우드펀딩오픈!</span>
							</div>
							<div class="col active">
								<i class="icon-receipt-list F w-40px h-40px "></i>
								<p class="txt1">STEP 4</p>
								<span class="txt2">청구서 제출</span>
								<span class="d-block fs-12px txt3">* 신청일로부터 30일 이내</span>
							</div>
						</div>
					</div>
				</div>
				<div class="txtbox-design-2 my-16px">
					<div class="row">
						<div class="col flex-b64px flex-md-grow-0">
							<i class="icon-exclamation-circle cycle"></i>
						</div>
						<div class="col">
							<p class="txt1">컨설팅 비용지원 안내사항</p>
							<p class="txt2 lh-13per">
								1. 신청 후 30일 이내 청구서 미제출시, 지원사업 신청이 자동 취소됩니다.<br/>
								2. (컨설팅 업체 선정) 농식품 경영체에서 직접 선정하여 진행하되, 해당 분야의 전문 업체임을 확인할 수 있는 사업자등록증을 보유하고 있어야 합니다.<br/>
								3. (청구서류 제출) (선택1)견적서 또는 (선책2)거래내역서(농식품 경영체-컨설팅 업체), 컨설팅 업체 사업자등록증, 지출증빙 (선택1) 카드전표 또는 (선택2) 세금계산서, 입금내역서<br/>
								4. (콘텐츠 제출) 제작하신 콘텐츠는 압축하여 이메일(805@apfs.kr)로 별도 제출하셔야 접수가 완료됩니다.<br/>
								5. (제출 시점) 크라우드펀딩 오픈일자부터 제출이 가능합니다.<br/>
								6. (지원 한도) 사업자당 펀딩 유형별 1회(증권형 250만원, 후원형 100만원)까지 지원 가능합니다.<br/>
								*(예시) A기업이 증권형, 후원형 모두 신청할 경우 각 1회씩 총 2회 지원 가능
							</p>
						</div>
					</div>
				</div>
				<div class="text-center">
					<button class="btn-primary bs-l mb-40px" id="btnPrgrmConsult">신청바로가기<i class="icon-arrow-right"></i></button>
				</div>
			</div>
			<div class="bg-ele-Line mb-56px" style="height:1px;"></div>
		<div class="">
			<div class="tit-top-1 mb-8px">
				<div class="row">
					<div class="col">
						<p class="txt1">
							<span class="square-box-2">
								03
							</span>
							"수수료지원" 이란?
						</p>
						<span class="d-block fs-15px pl-40px">
							농식품 크라우드펀딩, 펀딩 성공 이후 플랫폼사(중개사)에 지급하는 수수료가 부담되신다면?<br/><br/>
							크라우드펀딩 성공 시 사업자가 플랫폼사(중개사)에 지급하는 광고,중개수수료(모집성공금액의 일정비율 적용)를 지원해드립니다!단, 펀딩 목표금액 미달로 인해 프로젝트가 무산될 시 중개수수료가 발생하지 않으므로 수수료지원은 미지급되며, 광고수수료의 경우 펀딩 실패시에도 한도내 지원 가능합니다!          
						</span>							
					</div>
				</div>
			</div>
			<div class="shadow-box-1 px-16px pb-24px">
				<div class="table-box-1 m-overbox no-hover">
					<table>
						<caption>
							민원서식 목록
						</caption>
						<colgroup>
							<col style="width:auto">
							<col style="width:auto">
							<col style="width:auto">
						</colgroup>
						<thead class="bs-1 ts-l">
							<tr class="px-v-l">
								<th scope="col" class="border-top-0">구분</th>
								<th scope="col" class="border-top-0">증권형</th>
								<th scope="col" class="border-top-0">후원형</th>										
							</tr>
						</thead>
						<tbody class="bs-1 t-t-c ts-m py-v-m">
							<tr class="px-v-xl t-t-c">
								<td class="">지원금액</td>
								<td class="">300만원이내 (vat 제외 100%)</td>
								<td class="">100만원이내 (vat 제외 100%)</td>
							</tr>																		
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<div class="my-16px fs-13px fw-300 text-red">
			※ 수수료지원 기업별 한도는 동일하며(증권형 3백만원, 후원형 1백만원), 광고수수료의 경우 프로젝트당 10만원의 별도 한도 책정<br/>
			※ 컨설팅 비용지원, 수수료지원은 선착순으로 지원되며 예산 소진시 조기마감될 수 있습니다.
		</div>
		<div class="mb-24px">
			<div class="shadow-box-1 p-24px">
				<div class="bg-primary fs-18 fw-600 lh-1 mb-16px p-16px round-8px text-center text-white">신청 및 청구 순서</div>
				<div class="step-box-4 content-none">
					<div class="row">
						<div class="col">
							<div class="">
								<i class="icon-clipboard-list-check F w-40px h-40px "></i>
								<p class="txt1">STEP 1</p>
								<span class="txt2">크라우드펀딩 <br/>프로젝트 오픈 및 진행</span>
							</div>														
							<div class="">
								<i class="icon-receipt-list F w-40px h-40px "></i>
								<p class="txt1">STEP 2</p>
								<span class="txt2">모집성공</span>
								<!-- <span class="d-block fs-12px txt3">* 신청일로부터 30일 이내</span> -->
							</div>
						</div>
						<div class="col">
							<img src="<c:url value='/images/sub/Introduction-left-arrow.svg'/>" alt="">
							<p>약 2주 소요</p>
							<img src="<c:url value='/images/sub/Introduction-right-arrow.svg'/>" alt="">
						</div>
						<div class="col">
							<div class="">
								<i class="icon-clipboard-list-check F w-40px h-40px "></i>
								<p class="txt1">STEP 3</p>
								<span class="txt2">중개사 정산내역서 <br/> 및 세금계산서 발행</span>
							</div>														
							<div class="">
								<i class="icon-receipt-list F w-40px h-40px "></i>
								<p class="txt1">STEP 4</p>
								<span class="txt2">청구서 제출</span>
								<span class="d-block fs-12px txt3">* 신청일로부터 30일 이내</span>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="txtbox-design-2 my-16px">
				<div class="row">
					<div class="col flex-b64px flex-md-grow-0">
						<i class="icon-exclamation-circle cycle"></i>
					</div>
					<div class="col">
						<p class="txt1">수수료지원 안내사항</p>
						<p class="txt2 lh-13per">
							1. 프로젝트 종료 후 30일 이내 제출하시기 바랍니다.<br/>
							*기한 내 제출이 불가능할 경우, 농금원에 문의주시기 바랍니다.<br/>
							2. (청구서류 제출) 세금계산서(중개사 발행), 정산내역서(중개사 발행)<br/>
							5. (제출 시점) 크라우드펀딩 모집 성공 및 정산내역서 발행 이후 제출이 가능합니다.<br/>
							6. (지원 한도) 사업자당 펀딩 유형별로(증권형 300만원, 후원형 100만원)까지 지원 가능합니다. (횟수 제한 없음)
						</p>
					</div>
				</div>
			</div>
			<div class="text-center">
				<button class="btn-primary bs-l mb-40px" id="btnPrgrmFee">신청바로가기<i class="icon-arrow-right"></i></button>
				<div class="bg-ele-Line mb-56px" style="height:1px;"></div>
			</div>
		</div>
		<div class="mb-24px">
			<div class="tit-top-1 mb-40px">
				<div class="row">
					<div class="col">
						<p class="txt1">
							<span class="square-box-2">
								04
							</span>
							"데모데이" 이란?
						</p>
						<span class="d-block fs-15px pl-40px">
							농식품 크라우드펀딩 성공 기업들의 후속 투자유치 지원을 위한 사업설명회(IR) 기회를 제공하고 투자자와의 네트워크를 지원합니다.(연 2회 개최 예정이며 상세 일정 추후 공지)
						</span>							
					</div>
				</div>
			</div>
			<div class="bg-ele-Line mb-56px" style="height:1px;"></div>
		</div>
		<div class="mb-24px">
			<div class="tit-top-1 mb-40px">
				<div class="row">
					<div class="col">
						<p class="txt1">
							<span class="square-box-2">
								05
							</span>
							"맞춤형 IR 컨설팅" 이란?
						</p>
						<span class="d-block fs-15px pl-40px">
							데모데이(IR) 참여기업 대상으로 IR 자료 분석 및 피칭 컨설팅을 진행합니다. (연 2회 개최 예정이며 상세 일정 추후 공지)
						</span>							
					</div>
				</div>
			</div>
			<div class="bg-ele-Line mb-56px" style="height:1px;"></div>
		</div>
		<div class="">
			<div class="tit-top-1 ">
				<div class="row">
					<div class="col">
						<p class="txt1">
							<span class="square-box-2">
								06
							</span>
							"구매상담회 및 팝업스토어 지원" 이란?
						</p>
						<span class="d-block fs-15px pl-40px">
							지원사업을 통해 펀딩에 성공한 농식품 기업의 유통채널 확대 및 매출 성장 지원을 위한 판로 확대와 네트워크 구축을 지원합니다. (연 1회 개최 예정이며 상세 일정 추후 공지)
						</span>							
					</div>
				</div>
			</div>
		</div>
	</div>
</div>	

<!-- popup -->
<div class="custum-modal fade " id="exampleModal" tabindex="-1" aria-hidden="true">
	<div class="cutum-modal-dialog position-nomal" style="max-width:560px;">
		<div class="modal-content">
			<div class="border-0 modal-header">
				<h4 class="modal-title d-none d-xl-inline-flex">
					<img src="<c:url value='/images/sub/Sketch_on_smartphone.svg'/>" alt=""/>
					<span>사업계획서 작성요령 안내</span>
				</h4>
				<button type="button" class="close-btn d-none d-xl-block" onclick="$('#exampleModal').removeClass('show');">
					<i class="icon-times F"></i>
				</button>
			</div>
			<div class="modal-body">
				<div class="box-max-height">
					<div class="box mb-24px">
					
						<div class="table-top-1">
							<div class="row">
								<div class="col">
									<p class="txt1">
										<i class="cycle-ver-1 icon-document"></i>사업계획서의 필요성
									</p>
								</div>
							</div>
						</div>
						<div class="box-in-box py-24px">
							<div class="post-details-1">					
								<div class="box pb-0 mb-0">											
									<div class="body">
										<div class="row mb-16px">
											<div class="col">
												<p class="txt2">
													<span class=" dot-1">
														사업시작 전 또는 새로운 사업 진행 전 가장 먼저 해야할 일
													</span>
													<span class=" dot-1">
														사업에 대한 내부적, 외부적 객관성 확보 목적
													</span>
													<span class=" dot-1">
														사업의 장.단점 파악 및 사업흐름 정리
													</span>
													<span class=" dot-1">
														사업 가능성을 현실적으로 점검 하는 지침서
													</span>
												</p>
											</div>
										</div>
									</div>				
								</div>
							</div>
						</div>
						
						<div class="table-top-1">
							<div class="row">
								<div class="col">
									<p class="txt1">
										<i class="cycle-ver-1 icon-document"></i>사업계획서의 용도
									</p>
								</div>
							</div>
						</div>
						<div class="box-in-box py-24px">
							<div class="post-details-1">					
								<div class="box pb-0 mb-0">											
									<div class="body">
										<div class="row mb-16px">
											<div class="col">
												<p class="txt2">
													<span class=" dot-1">
														사업의 타당성을 검토하고 행동지침의 역할을 수행하는 내부관리용
													</span>
													<span class=" dot-1">
														사업의 가능성과 문제점을 발견 하는 기회
													</span>
													<span class=" dot-1">
														사업의 방향성을 잡아주는 가이드라인 역할
													</span>
													<span class=" dot-1">
														투자자, 동업자, 경영진들과 의사소통하는 역할의 외부제출용
													</span>
													<span class=" dot-1">
														사업에 대한 신뢰도를 높이는 자료
													</span>
												</p>
											</div>
										</div>
									</div>				
								</div>
							</div>
						</div>
						
						<div class="table-top-1">
							<div class="row">
								<div class="col">
									<p class="txt1">
										<i class="cycle-ver-1 icon-document"></i>사업계획서 작성
									</p>
								</div>
							</div>
						</div>
						<div class="box-in-box py-24px">
							<div class="post-details-1">					
								<div class="box pb-0 mb-0">											
									<div class="body">
										<div class="row mb-16px">
											<div class="col">
												<p class="txt2">
													<span class=" dot-1">
														사업의 목적과 방향에 맞는 체계적인 작성 필요
													</span>
													<span class=" dot-1">
														사업계획서의 형태에 맞는 기본내용과 목차정리
													</span>
													<span class=" dot-1">
														사업의 분야나 용도에 따라 사업계획서의 형태는 변경됨
													</span>
												</p>
											</div>
										</div>
									</div>				
								</div>
							</div>
						</div>
						<div class="tit-box-1 text-center mb-40px">
							<button class="btn-primary bs-l mb-20px" id="btnBusinPlanStandards" style="">사업계획 작성요령 다운로드</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<%-- ############################# 내용 (종료) ############################# --%>
