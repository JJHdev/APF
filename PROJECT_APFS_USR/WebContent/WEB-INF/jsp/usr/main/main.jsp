<%--
*******************************************************************************
***	명칭: main.jsp
***	설명: 사용자시스템 메인 페이지
***
***	-----------------------------    Modified Log   ---------------------------
***	버전     수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***	1.0      2023.03.10      LSH           First Coding.
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

<!-- ############################# 내용 (시작) ############################# -->
<%-- 처리할 일 url, params --%>
<c:url value="/usr/inform/bbs/listData.do" var="dataUrl">
	<c:param name="initParams" value='{pstClsfCd:"B303"}' />
</c:url>

<section class="section">
	<!-- <div id="popupDiv"></div>-->
	<img src="<c:url value='/images/bg/main1bg.png'/>" alt="" class="bg1" />
	<div class="content-box">
		<div class="row">
			<div class="col-12 col-xl">
			
				<p class="txt1">농식품 투자정보 플랫폼</p>
				<span class="txt2">농림수산식품 투자정보, 탐색부터 지원까지!</span>
				<!-- <div class="form-area-box1">											
					<div class="ele-icon-box">									
						<input type="text" name="srchText" id="srchText" placeholder="투자자, 경영체명, 키워드를 검색해보세요!" />
						<a href="#" class="icon-search" id="srchIcon"></a>
					</div>								
				</div> -->
				<div class="icons mt-16px mt-md-56px">
					<div class="row">
						<div class="col-6 col-md-6">
							<div class="box" onclick="location.href='<c:url value='/usr/invest/fund/listFund.do'/>'">
								<img src="<c:url value='/images/bg/icon1.png'/>" alt="icon1"/>
								<p>투자자검색</p>
								
							</div>
						</div>
						<div class="col-6 col-md-6">
							<div class="box" onclick="location.href='<c:url value='/usr/invest/ent/listEnt.do'/>'">
								<img src="<c:url value='/images/bg/icon2.png'/>" alt="icon2"/>
								<p>경영체검색</p>
							</div>
						</div>
						<div class="col-6 col-md-4">
							<div class="box" onclick="location.href='<c:url value='/usr/invest/matching/openMatchingInvt.do'/>'">
								<img src="<c:url value='/images/bg/icon3.png'/>" alt="icon3"/>
								<p>매칭서비스</p>
							</div>
						</div>
						<div class="col-6 col-md-4">
							<div class="box" onclick="location.href='<c:url value='/usr/support/pbanc/listPbanc.do'/>'">
								<img src="<c:url value='/images/bg/icon4.png'/>" alt="icon4"/>
								<p>지원사업</p>
							</div>
							
						</div>
						<div class="col-12 col-md-4">
							<div class="box"  onclick="location.href='${dataUrl}'">
								<img src="<c:url value='/images/bg/icon5.png'/>" alt="icon5"/>
								<p>산업·경영체 분석 보고서 </p>
							</div>
							
						</div>
					</div>
				</div>
			</div>
			<div class="col-12 col-xl d-none d-xl-block">
				<div class="swiper mySwiper4 overflow-hidden">
					<div class="swiper-wrapper"> 
						<div class="swiper-slide">
							<div class="imgbox"><img src="<c:url value='/images/bg/main1img.png' />" class="h-auto w-100"></div>
						</div>
						<!-- If we need navigation buttons -->
					</div>
					<div class="swiper-pagination"></div>
					
				</div>
				
			</div>
		</div>

		<div class="mousescroll">
			<i class="icon-mouse"></i>
			<span>아래로 스크롤 해주세요</span>
		</div>
	</div>
</section>
<section class="section ">
	<img src="<c:url value='/images/bg/main2bg.jpg'/>" class="bg1" alt="bg1"/>
	<img src="<c:url value='/images/bg/main2img.png'/>" class="bg2" alt="bg2"/>
	<div class="content-box-size">
		
		  <!-- Swiper -->
		<div class="swiper mySwiper overflow-hidden">
			<div class="swiper-wrapper ">
				<div class="swiper-slide ">
					<div class="content-box">
						<p class="txt1">경영체 IR 검색 서비스</p>
						<span class="txt2">성공적 투자 매칭을 위한 다양한 기능을 이용해 보세요.</span>
						<div class="row">
							<div class="col-12 col-xl text-center text-xl-end">
								<p class="txt3">기업 IR 정보<br/>검색부터 미팅까지!</p>
								<span class="txt4">투자유치 희망기업의 다양한 정보들을<br/>
									간편하게 검색·확인하고 미팅신청 할 수 있습니다</span>
									<a class="btn" href="<c:url value='/usr/invest/ent/listEnt.do'/>">
										<i class="icon-building-tree"></i>경영체 IR 자료 보러가기
									</a>																	
							</div>
							<div class="col-12 col-xl text-center text-xl-end">
								<img src="<c:url value='/images/bg/main2-1img.png'/>" alt="" class="">
							</div>
						</div>

						
					</div>
				</div>
				<div class="swiper-slide">
					<div class="content-box">
						<p class="txt1">경영체 매칭 서비스</p>
						<span class="txt2">성공적 투자 매칭을 위한 다양한 기능을 이용해 보세요.</span>
						<div class="row">										
							<div class="col-12 col-xl d-none d-xl-block">
								<img src="<c:url value='/images/bg/main2-1img.png'/>" alt="" class="">
							</div>
							<div class="col-12 col-xl text-center text-xl-start">
								<p class="txt3">투자 매칭 서비스</p>
								<span class="txt4">발굴희망기업 정보를 설정해 보세요.
									적합한 경영체를 추천해드립니다.</span>
									<a class="btn" href="<c:url value='/usr/invest/matching/openMatchingInvt.do'/>">
										<i class="icon-scanner"></i>매칭 서비스
									</a>																		
							</div>
							<div class="col-12 col-xl text-center text-xl-start d-xl-none">
								<img src="<c:url value='/images/bg/main2-1img.png'/>" alt="" class="">
							</div>
						</div>

						
					</div>
				</div>
				<!-- If we need navigation buttons -->
				
			</div>
			
		</div>
		<div class="content-box-size position-absolute" style="top: 50%;left: 50%; z-index:100;transform: translateX(-50%);">
			<div class="swiper-button-next ver1"><i class='icon-angle-right-circle'></i></div>
			<div class="swiper-button-prev ver1"><i class='icon-angle-left-circle'></i></div>
		</div>
		
		
		<!-- Initialize Swiper -->
		<script>
			var swiper = new Swiper(".mySwiper", {
			slidesPerView: "auto",
			spaceBetween: 0,
			slidesPerView:1,
							
			pagination: {
				el: ".swiper-pagination",
				clickable: true,
			},
			navigation: {
				nextEl: ".swiper-button-next.ver1",
				prevEl: ".swiper-button-prev.ver1",
			},
			});
		</script>
		
	</div>
</section>
<section class="section ">
	
	<img src="<c:url value='/images/bg/main3img.png'/>" class="bg2" alt="bg2"/>
	<div class="h-100 w-100">
		
		  <!-- Swiper -->
		<div class="swiper mySwiper2 overflow-hidden h-100">
			<div class="swiper-wrapper">
				<div class="swiper-slide">
					<img src="<c:url value='/images/bg/main3-1bg.png'/>" class="bg1" alt="bg1"/>
					<img src="<c:url value='/images/bg/main3-3bg.png'/>" class="bg3" alt="bg3"/>
					<div class="content-box">
						<p class="txt1">경영체 IR 작성 서비스</p>
						<span class="txt2">정부지원사업부터 투자유치까지 농림수산식품경영체의 여정을 함께합니다.</span>
						<div class="row">
							<div class="col-12 col-xl text-center text-xl-end">
								<p class="txt3">기업 정보를 간편하게 작성</p>
								<span class="txt4">번거로운 기업정보 및 IR 정보 입력을 <br/>
									쉽게 작성할 수 있습니다.</span>
									<a class="btn" href="<c:url value='/usr/mypage/ent/openEntIr.do'/>">
										<i class="icon-edit"></i>IR 작성하기
									</a>																	
							</div>
							<div class="col-12 col-xl text-center text-xl-end">
								<img src="<c:url value='/images/bg/main2-1img.png'/>" alt="" class="">
							</div>
						</div>

						
					</div>
				</div>
				<div class="swiper-slide">
					<img src="<c:url value='/images/bg/main3-1bg.png'/>" class="bg1" alt=""/>
					<img src="<c:url value='/images/bg/main3-2bg.png'/>" class="bg4" alt=""/>
					<div class="content-box">
						<p class="txt1">투자자 검색 서비스</p>
						<span class="txt2">정부지원사업부터 투자유치까지 농림수산식품경영체의 여정을 함께합니다.</span>
						<div class="row">										
							<div class="col-12 col-xl d-none d-xl-block">
								<img src="<c:url value='/images/bg/main2-1img.png'/>" alt="" class="">
							</div>
							<div class="col-12 col-xl text-center text-xl-start">
								<p class="txt3">투자자 검색부터 IR 제출까지</p>
								<span class="txt4">투자분야별 모태펀드를 검색하고 간편하게 <br/>
									IR자료를 제출하세요<br/>
									투자유치 성공을 기원합니다.</span>
									<a class="btn" href="<c:url value='/usr/invest/fund/listFund.do'/>">
										<i class="icon-user-search"></i>투자자 검색 바로가기
									</a>																		
							</div>
							<div class="col-12 col-xl d-xl-none">
								<img src="<c:url value='/images/bg/main2-1img.png'/>" alt="" class="">
							</div>
						</div>

						
					</div>
				</div>
				<div class="swiper-slide">
					<img src="<c:url value='/images/bg/main3-1bg.png'/>" class="bg1" alt=""/>
					<img src="<c:url value='/images/bg/main3-3bg.png'/>" class="bg3" alt=""/>
					<div class="content-box">
						<p class="txt1">정부 지원사업 검색 서비스</p>
						<span class="txt2">정부지원사업부터 투자유치까지 농림수산식품경영체의 여정을 함께합니다.</span>
						<div class="row">
							<div class="col-12 col-xl text-center text-xl-end">
								<p class="txt3">정부지원사업 매칭서비스</p>
								<span class="txt4">농금원 및 유관기관에서 농림수산식품경영체 대상 <br/>
									다양한 지원사업을 운영하고 있습니다.<br/>
									내게 맞는 정부지원사업을 검색해 보세요.</span>
									<a class="btn" href="<c:url value='/usr/support/pbanc/listPbanc.do'/>">
										<i class="icon-search-text"></i>지원사업 검색
									</a>																	
							</div>
							<div class="col-12 col-xl text-center text-xl-end">
								<img src="<c:url value='/images/bg/main2-1img.png'/>" alt="" class="">
							</div>
						</div>

						
					</div>
				</div>
				<!-- If we need navigation buttons -->
				
			</div>
			
		</div>
		<div class="content-box-size position-absolute" style="top: 50%;left: 50%; z-index:100;transform: translateX(-50%);">
			<div class="swiper-button-next ver2"><i class='icon-angle-right-circle'></i></div>
			<div class="swiper-button-prev ver2"><i class='icon-angle-left-circle'></i></div>
		</div>
		
		<!-- Initialize Swiper -->
		<script>
			var swiper = new Swiper(".mySwiper2", {
			slidesPerView: "auto",
			spaceBetween: 0,
			slidesPerView:1,					
			pagination: {
				el: ".swiper-pagination",
				clickable: true,
			},
			navigation: {
				nextEl: ".swiper-button-next.ver2",
				prevEl: ".swiper-button-prev.ver2",
			},
			});
		</script>
		
	</div>
</section>
<section class="section onchgebox fp-auto-height">
	<div class="box">
		<div class="content-box">
			<span class="txt2">투자유치 성공 스토리</span>
			<p class="txt1">우수투자사례</p>
			<!-- 슬라이드정보 -->
			<div id="appSlideCard" class="text-center"></div>
			<div id="swiper-appSlideCard" class="content-box-size position-absolute" style="top: 50%;left: 50%; z-index:100;transform: translateX(-50%);">
				<div class="swiper-button-next ver3"><i class='icon-angle-right-circle'></i></div>
				<div class="swiper-button-prev ver3"><i class='icon-angle-left-circle'></i></div>
			</div>
		</div>						 
	</div>
	<div class="box">
		<div class="content-box">
			<div class="bord-info-box">
				<div class="row">
					<div class="col-12 col-xl">
						<!-- 목록 영역 -->
						<div id="appGridPanel">
							<div class="shadow-box">
								<div class="table-box">
									<div class="tit-top-1">
										<div class="row">
											<div class="col w-ver1">
												<img src="<c:url value='/images/bg/main3img5.png'/>" class="img1" alt="">
											</div>
											<div class="col">
												<p class="txt1">공지사항</p>
												<p class="txt2">투자정보 플랫폼의 최신 공지사항입니다.</p>
											</div>
											<div class="col w-ver2">
												<a class="btn-more-1" href="<c:url value='/usr/inform/bbs/listNotice.do'/>">
													<i class="icon-plus"></i>더보기
												</a>
											</div>
										</div>
									</div>
									<!-- 목록 컨텐츠 영역 -->
									<div id="appGrid"></div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-12 col-xl">
						<div class="row align-content-between h-100">
							<div class="col col-xl-12">
								<div class="color-box-1" onclick="location.href='<c:url value='/usr/inform/bbs/listData.do'/>'" style="cursor:pointer;">
									<img src="<c:url value='/images/bg/main3img6.png'/>" class="img1" alt="">
									<p class="txt1">자료실</p>
									<p class="txt2">농림수산식품 모태펀드 관련 자료를 찾고 계신가요? </p>
								</div>
							</div>
							<div class="col col-xl-12">
								<div class="color-box-2" onclick="location.href='<c:url value='/usr/inform/bbs/listFAQ.do'/>'" style="cursor:pointer;">
									<img src="<c:url value='/images/bg/main3img7.png'/>" class="img1" alt="">
									<p class="txt1">자주 묻는 질문</p>
									<p class="txt2">농림수산식품 모태펀드에 대해 <br>더 자세히 알고싶으신가요? </p>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>						
	</div>
</section>


<%-- ############################# 내용 (종료) ############################# --%>
