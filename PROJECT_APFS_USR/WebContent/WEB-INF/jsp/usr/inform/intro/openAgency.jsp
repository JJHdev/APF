<%--
*******************************************************************************
***    명칭: openAgency.jsp
***    설명: 플랫폼소개 - 유관기관 안내 화면
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
<div class="capsule-tabmenu-1">
	<div class="border-bottom-0 position-relative p-8px shadow-box-1" style="border-radius: 16px 16px 0 0;z-index: 10;">
	    <div class="capsule-box">
		    <div class="row">
		        <div class="col active fs-md-16px lh-md-38px" id="AF" data-target="#AF" style="border-bottom-right-radius: 0px; border-bottom-left-radius: 0px;">
		           농림식품기술기획평가원
		        </div>
		        <div class="col fs-md-16px lh-md-38px" id="RDA" data-target="#RDA" style="border-bottom-right-radius: 0px; border-bottom-left-radius: 0px;">
		            한국농업기술진흥원
		        </div>
		        <div class="col fs-md-16px lh-md-38px" id="IPET" data-target="#IPET" style="border-bottom-right-radius: 0px; border-bottom-left-radius: 0px;">
		            한국식품산업클러스터진흥원
		        </div>
		    </div>
		</div>
	</div>
</div>
<div class="shadow-box-1 border-top-0 mb-56px px-0" style="border-radius: 0;">
	<div class="tabmenu-1"></div>
</div>
<div id="AFInfo" >
	<div class="mb-16px">
		<div class="tit-top-1 mb-16px">
			<div class="row">
				<div class="col">
					<p class="txt1">농림식품기술기획평가원 </p>
				</div>
			</div>
		</div>
		<div class="shadow-box-1 p-16px">
			<div class="row mx-0">
				<div class="col-12 col-md flex-md-grow-0 px-0">
					<img src="<c:url value="/images/sub/Introduction-img5.png"/>" class="w-100 d-md-none" alt="introduction">
					<img src="<c:url value="/images/sub/Introduction-img5.png"/>" class="d-none d-md-block" alt="introduction">
				</div>
				<div class="col pl-32px pr-0 py-16px">
					<div class="mb-16px ">
						<div class="txt-lev-box-1 pb-16px">
							<p class="lev1 mb-8px">주요기능 및 역할</p>
							<p class="fs-15px fw-300 lh-15per pl-8px">
								1. 농림식품과학기술 연구개발사업의 기획ㆍ관리 및 평가 등의 지원 <br/>
								2. 농림식품과학기술 육성 종합계획과 시행계획의 수립 지원 <br/>
								3. 농림식품과학기술 개발 인력에 관한 전문교육 및 연수 지원 <br/>
								4. 국내외 농림식품과학기술 정보의 수집ㆍ분석 및 보급 지원 <br/>
								5. 농림식품분야 기술역량진단 관련 사업의 수행 등
							</p>
						</div>
						<div class="bg-ele-Line " style="height:1px;"></div>
					</div>
					<div class="mb-16px">
						<div class="txt-lev-box-1 pb-16px">
							<p class="lev1 mb-8px">미션</p>
							<p class="fs-15px fw-300 lh-15per">
								농림식품 과학기술 혁신을 통한 지속가능한 농산업 실현
							</p>
						</div>
						<div class="bg-ele-Line " style="height:1px;"></div>
					</div>
					<div class="">
						<div class="txt-lev-box-1 ">
							<p class="lev1 mb-8px">비전</p>
							<p class="fs-15px fw-300 lh-15per">
								농식품 산업 및 기술의 미래 가치를 창출하는 연구관리 전문기관
							</p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="text-center">
		<button class="btn-primary bs-l mt-24px mb-40px" id="btnGoAFInfo">농림식품기술기획평가원 바로가기<i class="icon-arrow-right"></i></button>
		<div id="appContent"></div>
	</div>
</div>
<div id="RDAInfo" >
	<div class="mb-56px">
		<div class="tit-top-1 mb-16px">
			<div class="row">
				<div class="col">
					<p class="txt1">한국농업기술진흥원</p>
				</div>
			</div>
		</div>
		<div class="shadow-box-1 p-16px">
			<div class="row mx-0">
				<div class="col-12 col-md flex-md-grow-0 px-0">
					<img src="<c:url value="/images/sub/Introduction-img6.png"/>" class="w-100 d-md-none" alt="introduction">
					<img src="<c:url value="/images/sub/Introduction-img6.png"/>" class="d-none d-md-block" alt="introduction">
				</div>
				<div class="col pl-32px pr-0 py-16px">
					<div class="mb-16px ">
						<div class="txt-lev-box-1 pb-16px">
							<p class="lev1 mb-8px">주요기능 및 역할</p>
							<p class="fs-15px fw-300 lh-15per pl-8px">
								1. 연구개발성과의 실용화를 위한 중개 및 알선 <br/>2. 연구개발성과의 실용화를 위한 조사 및 연구 <br/>3. 영농 현장에서의 연구개발 성과 활용 지원 <br/>4. 연구개발성과의 사업화 <br/>5. 특허 등 지식재산권의 위탁관리업무 <br/>6. 농가와 농업생산자 단체 등의 연구개발성과 사업화 지원 <br/>7. 농식품 벤처·창 업 활성화 지원 <br/>8. 연구개발성과의 실용화 촉진을 위하여 국가 또는 지방자치단체가 위탁하거나 대행하게 하는 사업 <br/>9. 농촌진흥청과 그 소속기관의 장 및 민원인이 의뢰하는 시험·분석·측정대행업 및 농업용 기자재의 검정 <br/>10. 국가 또는 지방자치단체가 육성하여 긴급하게 보급하고자 하는 종자·종묘의 증식·보급 <br/>11. 종자산업의 육성 및 지원에 필요한 전문인력 양성 <br/>12. 연구개발성과의 권리화 지원 <br/>13. 농식품 기술 실용화, 기술금융 등을 위한 기술평가 <br/>14. 농식품분야 탄소배출권 관련 사업화지원 <br/>15. 농식품 우수기술의 해외 이전 및 수출사업화 촉진 <br/>16. 치유농업 관련 기술의 사업화 및 창업의 지원 <br/>17. 치유농업사 자격시험의 시 행 및 관 리 <br/>18. 디지털농업 기술의 표준화 및 실증 지원 <br/>19. 연구개발성과의 실용화 촉진을 위한 정보화 <br/>20. 연구개발성과의 실용화 촉진을 위한 자본 및 기술 출자 <br/>21. 연구개발성과의 사업화를 위한 전시 및 홍보 지원 업무 <br/>22. 연구개발성과의 실용화 촉진을 위한 시설 등 임대 <br/>23. 직작어린이집 설치·운영 <br/>24. 위 각호와 관련한 출판 등 부대사업
							</p>
						</div>
						<div class="bg-ele-Line " style="height:1px;"></div>
					</div>
					<div class="mb-16px">
						<div class="txt-lev-box-1 pb-16px">
							<p class="lev1 mb-8px">미션</p>
							<p class="fs-15px fw-300 lh-15per">
								농업과학기술 분야 연구개발성과의 실용화 촉진을 통한 농산업 진흥
							</p>
						</div>
						<div class="bg-ele-Line " style="height:1px;"></div>
					</div>
					<div class="">
						<div class="txt-lev-box-1 ">
							<p class="lev1 mb-8px">비전</p>
							<p class="fs-15px fw-300 lh-15per">
								기술혁신으로 농업의 미래를 열어가는 KOAT
							</p>
						</div>
						
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="text-center">
		<button class="btn-primary bs-l mt-24px mb-40px" id="btnGoIPETInfo">한국농업기술진흥원 바로가기<i class="icon-arrow-right"></i></button>
		<div id="appContent2"></div>
	</div>
</div>
<div id="IPETInfo" >
	<div class="">
		<div class="tit-top-1 mb-16px">
			<div class="row">
				<div class="col">
					<p class="txt1">한국식품산업클러스터진흥원</p>
				</div>
			</div>
		</div>
		<div class="shadow-box-1 p-16px">
			<div class="row mx-0">
				<div class="col-12 col-md flex-md-grow-0 px-0">
					<img src="<c:url value="/images/sub/Introduction-img7.png"/>" class="w-100 d-md-none" alt="introduction">
					<img src="<c:url value="/images/sub/Introduction-img7.png"/>" class="d-none d-md-block" alt="introduction">
				</div>
				<div class="col pl-32px pr-0 py-16px">
					<div class="mb-16px ">
						<div class="txt-lev-box-1 pb-16px">
							<p class="lev1 mb-8px">주요기능 및 역할</p>
							<p class="fs-15px fw-300 lh-15per pl-8px">
								1. 국가식품클러스터와 식품산업집적에 관한 정책개발 및 연구 <br/>2. 식품전문산업단지의 조성 및 관리에 관한 사업 <br/>3. 국가식품클러스터 참여기업ㆍ기관들에 대한 지원사업 <br/>4. 국가식품클러스터 참여기업 및 기관들 간의 상호 연계활동 촉진 사업 <br/>5. 국가식품클러스터 활성화를 위한 연구, 대외협력, 홍보사업 <br/>6. 국내외 식품기업, 기관 유치를 위한 투자유치 활동 <br/>7. 진흥원 설립목적에 필요한 사업 <br/>8. 그 밖에 농림축산식품부장관이 위탁하는 사업
							</p>
						</div>
						<div class="bg-ele-Line " style="height:1px;"></div>
					</div>
					<div class="mb-16px">
						<div class="txt-lev-box-1 pb-16px">
							<p class="lev1 mb-8px">미션</p>
							<p class="fs-15px fw-300 lh-15per pl-8px">
								1. 식품산업 혁신 생태계 조성<br/>2. 기업 맞춤형 지원체계 구축 <br/>3. 지속 가능한 경영시스템 구축
							</p>
						</div>
						<div class="bg-ele-Line " style="height:1px;"></div>
					</div>
					<div class="">
						<div class="txt-lev-box-1 ">
							<p class="lev1 mb-8px">비전</p>
							<p class="fs-15px fw-300 lh-15per">
								식품산업 혁신성장의 메카로 자리매김(창업에서 글로벌기업으로 성장생태계 조성)
							</p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="text-center">
		<button class="btn-primary bs-l mt-24px mb-40px" id="btnGoRDAInfo">한국식품산업클러스터진흥원 바로가기<i class="icon-arrow-right"></i></button>
		<div id="appContent3"></div>
	</div>
</div>
<%-- ############################# 내용 (종료) ############################# --%>
