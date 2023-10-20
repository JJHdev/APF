<%--
*******************************************************************************
***    명칭: openAPFS.jsp
***    설명: 플랫폼소개 - 농금원 안내 화면
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
<div class="capsule-tabmenu-1">
	<div class="border-bottom-0 position-relative p-8px shadow-box-1" style="border-radius: 16px 16px 0 0;z-index: 10;">
	    <div class="capsule-box">
		    <div class="row">
		        <div class="col active fs-md-16px lh-md-38px" id="APFSInfo" data-target="#APFSInfomation" style="border-bottom-right-radius: 0px; border-bottom-left-radius: 0px;">
		            농금원 안내
		        </div>
		        <div class="col fs-md-16px lh-md-38px" id="INVEInfo" data-target="#INVEInfomation" style="border-bottom-right-radius: 0px; border-bottom-left-radius: 0px;">
		            가온누리 인베지움(농식품 투자지원센터)
		        </div>
		    </div>
		</div>
	</div>
	<div id="firstTab">
		<div class="shadow-box-1 border-top-0 mb-56px px-0" style="border-radius: 0;">
			<div class="tabmenu-1">
				<ul>
				    <li><a href="#overview" 	id="btnOverview" 	 class="active">개요</a></li>
				    <!-- <li><a href="#organization" id="btnOrganization" class="bg-gray-t10">조직도</a></li> -->
				    <li><a href="#directions" 	id="btnDirections" 	 class="bg-gray-t10">찾아오시는 길</a></li>
				</ul>
			</div>
		</div>
	</div>
	<div id="secondTab" style="display: none;">
		<div class="shadow-box-1 border-top-0 mb-56px px-0"  style="border-radius: 0;">
			<div class="tabmenu-1">
				<ul>
				    <li><a href="#outline" id="btnOutline" class="active">센터소개</a></li>
				    <li><a href="#INVEDirections" id="btnINVEDirections" class="bg-gray-t10">찾아오시는 길</a></li>
				</ul>
			</div>
		</div>
	</div>
</div>	
<div id="APFSInfomation" >
	<div id="overview">	
	
		<div class="mb-56px">
			<div class="designbox6">
				<img src="<c:url value="/images/설립목적.svg"/>" alt="introduction" class="img1">
				<img src="<c:url value="/images/설립목적일러스트.png"/>" alt="introduction" class="img2">
				<p class="tit">설립목적</p>
				<p class="txt1">
					정부의 농업정책자금을 효율적으로 운용 · 관리 및 <br/>
					감독함으로써 농식품산업의 발전에 기여
				</p>
			</div>
		</div>
		
		<div class="mb-56px">
			<div class="designbox6-1">
				<img src="<c:url value="/images/수행업무.svg"/>" alt="introduction" class="img1">
				<img src="<c:url value="/images/수행업무일러스트.png"/>" alt="introduction" class="img2">
				<p class="tit">수행업무</p>
				<p class="txt1">
					농림수산정책자금(대출, 보험, 펀드)의<br/>
					효율적 운용을 위한 제도연구, 사업관리·점검 등<br/>
					정책금융 관리업무를 수행
				</p>
			</div>
		</div>
		
		<div class="">
			<div class="tit-box-1 mb-16px">
				<div class="row">
					<div class="col">
						<p class="txt1">
							주요 사업
						</p>
					</div>
					
				</div>
			</div>
			<div class="shadow-box-1 mb-8px px-6px px-md-24px" style="border-bottom-right-radius: 0px; border-bottom-left-radius: 0px;">
				<div class="tabmenu-1">
					<ul class="">
						<li class="">
							<a href="javascript:void(0)" class="tab-tit-2" onclick="tab(2);"> 농업재해보험사업관리</a>
						</li>
						<li>
							<a href="javascript:void(0)" class="active tab-tit-1" onclick="tab(1);">농림수산식품모태펀드관리</a>
						</li>
						<li>
							<a href="javascript:void(0)" class="tab-tit-3" onclick="tab(3);">농림수산정책자금관리</a>
						</li>
					</ul>
				</div>
			</div>
			<div class="tab-box mt-16px">
				<div id="tab-2" class="border-0 p-0" style="border-radius: 0;">
					<div class="shadow-box-3 p-32px " style="border-top-right-radius: 0px; border-top-left-radius: 0px;">
						<div class="mb-32px">
							<div class="row mx-0">
								<div class="col-12 px-0 col-md flex-b-md-208px">
									<div class="table-top-1">
										<div class="row">										
											<div class="col">
												<p class="fs-20px txt1"><i class="cycle-ver-1 fs-24px h-32px icon-leaf w-32px"></i>사업목적</p>
											</div>
										</div>
									</div>
								</div>
								<div class="col">
									<div class="txt-lev-box-1">
										<p class="txt-dot fs-15px">농어업재해보험사업의 지도 · 감독을 통해 재해보험사업을 효율적으로 관리·운영하여 농어업 경영안정을 도모</p>
									</div>
								</div>
							</div>
						</div>
						<div class="mb-32px">
							<div class="row mx-0">
								<div class="col-12 px-0 col-md flex-b-md-208px">
									<div class="table-top-1">
										<div class="row">										
											<div class="col">
												<p class="fs-20px txt1"><i class="cycle-ver-1 fs-24px h-32px icon-leaf w-32px"></i>주요업무</p>
											</div>
										</div>
									</div>
								</div>
								<div class="col">
									<div class="txt-lev-box-1">
										<p class="fs-15px mb-4px txt-dot">농업재해보험 및 국가재보험 사업관리</p>
										<p class="fs-15px fw-300 mb-4px pl-16px">- 농업재해보험 및 재보험사업 약정체결, 보험사업 점검 등 관리·감독</p>
										<p class="fs-15px mb-4px txt-dot">농업재해보험상품 연구 · 보급 및 손해평가사 제도 운용</p>
										<p class="fs-15px mb-4px txt-dot">재해보험 관련 통계 생산 및 데이터베이스 구축·분석</p>
									</div>
								</div>
							</div>
						</div>
						<div class="">
							<div class="row mx-0">
								<div class="col-12 px-0 col-md flex-b-md-208px">
									<div class="table-top-1">
										<div class="row">										
											<div class="col">
												<p class="fs-20px txt1"><i class="cycle-ver-1 fs-24px h-32px icon-leaf w-32px"></i>사업 추진체계</p>
											</div>
										</div>
									</div>
								</div>
								<div class="col">
									<div class="row" style="margin:-8px;">
										<div class="col-12 col-md-4 p-8px">
											<div class="align-items-center bg-primary d-flex h-40px justify-content-center text-white" style="border-radius:8px 8px 0 0;">
												행정기관(농식품부, 해양수산부)
											</div>
											<div class="h-138px px-16px py-12px shadow-box-1" style="border-radius: 0 0 8px 8px;">
												<div class="txt-lev-box-1">
													<p class="fs-15px mb-4px txt-dot lh-1">
													재해보험사업 정책수립
													</p>
													<p class="fs-15px mb-4px txt-dot lh-1">사업시행지침 마련</p>
													<p class="fs-15px txt-dot lh-1">재해보험사업 자금배정</p>
												</div>
											</div>
										</div>
										<div class="col-12 col-md-4 p-8px">
											<div class="align-items-center bg-mint d-flex h-40px justify-content-center text-white" style="border-radius:8px 8px 0 0;">
												농업정책보험금융원
											</div>
											<div class="h-138px px-16px py-12px shadow-box-1" style="border-radius: 0 0 8px 8px;">
												<div class="txt-lev-box-1">
													<p class="fs-15px mb-4px txt-dot lh-1">
													재해보험 및 재보험사업 약정 체결</p>
													<p class="fs-15px mb-4px txt-dot lh-1">보험사업 점검 · 정산 · 관리</p>
													<p class="fs-15px mb-4px txt-dot lh-1">재해보험상품 연구 및 보급</p>
													<p class="fs-15px mb-4px txt-dot lh-1">손해평가사제도 운용</p>
													<p class="fs-15px  txt-dot lh-1">재해보험 관련 통계생산 및 데이터베이스 구축 · 분석</p>
												</div>
											</div>
										</div>
										<div class="col-12 col-md-4 p-8px">
											<div class="align-items-center bg-deep-green d-flex h-40px justify-content-center text-white" style="border-radius:8px 8px 0 0;">
												보험사업자(NH손보, KB손보, 수협중앙회 등)
											</div>
											<div class="h-138px px-16px py-12px shadow-box-1" style="border-radius: 0 0 8px 8px;">
												<div class="txt-lev-box-1">
													<p class="fs-15px mb-4px txt-dot lh-1">
														보험상품 개발 및 판매</p>
													<p class="fs-15px mb-4px txt-dot lh-1">손해평가 및 보험금 지급
													<p class="fs-15px mb-4px txt-dot lh-1">손해평가인 운용</p>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div id="tab-1" class="border-0 on p-0" style="border-radius: 0;">
					<div class="shadow-box-3 p-32px " style="border-top-right-radius: 0px; border-top-left-radius: 0px;">
						<div class="mb-32px">
							<div class="row mx-0">
								<div class="col-12 px-0 col-md flex-b-md-208px">
									<div class="table-top-1">
										<div class="row">										
											<div class="col">
												<p class="fs-20px txt1"><i class="cycle-ver-1 fs-24px h-32px icon-leaf w-32px"></i>사업목적</p>
											</div>
										</div>
									</div>
								</div>
								<div class="col">
									<div class="txt-lev-box-1">
										<p class="txt-dot fs-15px">대출위주의 농업금융방식에서 농림수산식품산업의 성장동력을 발굴하고 투자하는 선진 농업금융방식으로 패러다임 전환</p>
										<p class="txt-dot fs-15px">농림수산식품모태펀드 조성 및 출자를 통해 농어업분야의 산업화 및 규모화를 위한 지속적이고 안정적인 투자재원 마련</p>
									</div>
								</div>
							</div>
						</div>
						<div class="mb-32px">
							<div class="row mx-0">
								<div class="col-12 px-0 col-md flex-b-md-208px">
									<div class="table-top-1">
										<div class="row">										
											<div class="col">
												<p class="fs-20px txt1"><i class="cycle-ver-1 fs-24px h-32px icon-leaf w-32px"></i>주요업무</p>
											</div>
										</div>
									</div>
								</div>
								<div class="col">
									<div class="txt-lev-box-1">
										<p class="fs-15px mb-4px txt-dot">농림수산식품모태펀드 기획 및 농림수산식품경영체 지원</p>
										<p class="fs-15px fw-300 mb-4px pl-16px">- 농림수산식품모태펀드 출자계획 수립</p>
										<p class="fs-15px fw-300 mb-4px pl-16px">- 농림수산식품경영체 상담 및 컨설팅, 홍보 및 대외협력 등</p>
										<p class="fs-15px mb-4px txt-dot">농림수산식품투자조합 운용사 선정 및 사후관리</p>
										<p class="fs-15px fw-300 mb-4px pl-16px">- 운용사 선정심사 및 규약준수 · 해산 · 청산 관리 등 사후관리</p>
										<p class="fs-15px mb-4px txt-dot">농림수산식품모태펀드 리스크관리</p>
										<p class="fs-15px fw-300  pl-16px">- 조합 규약 등 심사, 준법감시, 조기경보시스템 운영, 자산평가 등</p>
									</div>
								</div>
							</div>
						</div>
						<div class="">
							<div class="row mx-0">
								<div class="col-12 px-0 col-md flex-b-md-208px">
									<div class="table-top-1">
										<div class="row">										
											<div class="col">
												<p class="fs-20px txt1"><i class="cycle-ver-1 fs-24px h-32px icon-leaf w-32px"></i>사업 추진체계</p>
											</div>
										</div>
									</div>
								</div>
								<div class="col">
									<div class="row" style="margin:-8px;">
										<div class="col-12 col-md-4 p-8px">
											<div class="align-items-center bg-primary d-flex h-40px justify-content-center text-white"
												style="border-radius:8px 8px 0 0;">행정기관(농식품부, 해수부)</div>
											<div class="h-185px px-16px py-12px shadow-box-1" style="border-radius: 0 0 8px 8px;">
												<div class="txt-lev-box-1">
													<p class="fs-15px mb-4px txt-dot lh-1">모태펀드 운용지침 마련</p>
													<p class="fs-15px mb-4px txt-dot lh-1">모태펀드 출자</p>
													<p class="fs-15px txt-dot lh-1">농림수산식품투자조합 등록</p>
												</div>
											</div>
										</div>
										<div class="col-12 col-md-4 p-8px">
											<div class="align-items-center bg-mint d-flex h-40px justify-content-center text-white"
												style="border-radius:8px 8px 0 0;">
												농업정책보험금융원
											</div>
											<div class="h-185px px-16px py-12px shadow-box-1" style="border-radius: 0 0 8px 8px;">
												<div class="txt-lev-box-1">
													<p class="fs-15px mb-4px txt-dot lh-1">모태펀드 운용계획 수립</p>
													<p class="fs-15px mb-4px txt-dot lh-1">출자사업 공고</p>
													<p class="fs-15px mb-4px txt-dot lh-1">투자조합 심사 및 선정</p>
													<p class="fs-15px mb-4px txt-dot lh-1">농림수산식품투자조합 관리 · 감독</p>
													<p class="fs-15px mb-4px txt-dot lh-1">농림수산식품경영체 상담 및 컨설팅</p>
													<p class="fs-15px mb-4px txt-dot lh-1">투자농림수산식품경영체 가치증진</p>
													<p class="fs-15px  txt-dot lh-1">모태펀드 리스크관리 및 준법감시</p>
												</div>
											</div>
										</div>
										<div class="col-12 col-md-4 p-8px">
											<div class="align-items-center bg-deep-green d-flex h-40px justify-content-center text-white" style="border-radius:8px 8px 0 0;">
												투자조합 운용사
											</div>
											<div class="h-185px px-16px py-12px shadow-box-1" style="border-radius: 0 0 8px 8px;">
												<div class="txt-lev-box-1">
													<p class="fs-15px mb-4px txt-dot lh-1">조합 결성 제안서 접수</p>
													<p class="fs-15px mb-4px txt-dot lh-1">조합 결성</p>
													<p class="fs-15px mb-4px txt-dot lh-1">농림수산식품경영체 투자</p>
													<p class="fs-15px mb-4px txt-dot lh-1">농림수산식품경영체 사후관리</p>
													<p class="fs-15px mb-4px txt-dot lh-1">투자금 회수</p>
													<p class="fs-15px txt-dot lh-1">농림수산식품투자조합 해산, 청산</p>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div id="tab-3" class="border-0 p-0" style="border-radius: 0;">
					<div class="shadow-box-3 p-32px " style="border-top-right-radius: 0px; border-top-left-radius: 0px;">
						<div class="mb-32px">
							<div class="row mx-0">
								<div class="col-12 px-0 col-md flex-b-md-208px">
									<div class="table-top-1">
										<div class="row">										
											<div class="col">
												<p class="fs-20px txt1"><i class="cycle-ver-1 fs-24px h-32px icon-leaf w-32px"></i>사업목적</p>
											</div>
										</div>
									</div>
								</div>
								<div class="col">
									<div class="txt-lev-box-1">
										<p class="txt-dot fs-15px">농림수산정책금융의 공정성 · 투명성 제고를 통한 농업인 편익 증진</p>
									</div>
								</div>
							</div>
						</div>
						<div class="mb-32px">
							<div class="row mx-0">
								<div class="col-12 px-0 col-md flex-b-md-208px">
									<div class="table-top-1">
										<div class="row">										
											<div class="col">
												<p class="fs-20px txt1"><i class="cycle-ver-1 fs-24px h-32px icon-leaf w-32px"></i>주요업무</p>
											</div>
										</div>
									</div>
								</div>
								<div class="col">
									<div class="txt-lev-box-1">
										<p class="fs-15px mb-4px txt-dot">농특회계융자금의 수입 · 지출 등 대여금 관리 </p>
										<p class="fs-15px fw-300 mb-4px pl-16px">- 농특회계 사업자금 대여 (농협은행 등 금융기관 본부)</p>
										<p class="fs-15px fw-300 mb-4px pl-16px">- 농특회계 사업자금 회수 (정기상환, 중도상환, 미대출 및 부당사용지급)</p>
										<p class="fs-15px mb-4px txt-dot">정책자금 검사 및 사후관리 지도 </p>
										<p class="fs-15px fw-300 mb-4px pl-16px">- 금융기관이 취급한 정책자금에 대한 집행 및 사용실태 점검</p>
										<p class="fs-15px fw-300 mb-4px pl-16px">- 정책자금 취급 및 사후관리 등에 대한 지도 · 교육 등 부당사용 예방활동</p>
									</div>
								</div>
							</div>
						</div>
						<div class="">
							<div class="row mx-0">
								<div class="col-12 px-0 col-md flex-b-md-208px">
									<div class="table-top-1">
										<div class="row">										
											<div class="col">
												<p class="fs-20px txt1"><i class="cycle-ver-1 fs-24px h-32px icon-leaf w-32px"></i>사업 추진체계</p>
											</div>
										</div>
									</div>
								</div>
								<div class="col">
									<div class="row" style="margin:-8px;">
										<div class="col-12 col-md-4 p-8px">
											<div class="align-items-center bg-primary d-flex h-40px justify-content-center text-white" style="border-radius:8px 8px 0 0;">
												행정기관(농식품부, 해수부, 산림청, 지자체)
											</div>
											<div class="h-100px px-16px py-12px shadow-box-1" style="border-radius: 0 0 8px 8px;">
												<div class="txt-lev-box-1">
													<p class="fs-15px mb-4px txt-dot lh-1">사업계획 수립 및 지침마련</p>
													<p class="fs-15px mb-4px txt-dot lh-1">융자한도액 배정</p>
													<p class="fs-15px txt-dot lh-1">대상자 선정 및 실적확인</p>
												</div>
											</div>
										</div>
										<div class="col-12 col-md-4 p-8px">
											<div class="align-items-center bg-mint d-flex h-40px justify-content-center text-white" style="border-radius:8px 8px 0 0;">
												농업정책보험금융원
											</div>
											<div class="h-100px px-16px py-12px shadow-box-1" style="border-radius: 0 0 8px 8px;">
												<div class="txt-lev-box-1">
													<p class="fs-15px mb-4px txt-dot lh-1">사업자금 대여</p>
													<p class="fs-15px mb-4px txt-dot lh-1">사업자금 회수</p>
													<p class="fs-15px  txt-dot lh-1">검사 및 사후관리 지도</p>
												</div>
											</div>
										</div>
										<div class="col-12 col-md-4 p-8px">
											<div class="align-items-center bg-deep-green d-flex h-40px justify-content-center text-white" style="border-radius:8px 8px 0 0;">
												대출취급 금융기관
											</div>
											<div class="h-100px px-16px py-12px shadow-box-1" style="border-radius: 0 0 8px 8px;">
												<div class="txt-lev-box-1">
													<p class="fs-15px mb-4px txt-dot lh-1">대출심사 및 집행</p>
													<p class="fs-15px mb-4px txt-dot lh-1">대출금 채권보전</p>
													<p class="fs-15px mb-4px txt-dot lh-1">대출 원리금 회수</p>
													<p class="fs-15px txt-dot lh-1">대출금 사후관리</p>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	<div class="text-center">
	    <button id="btnLink" class="btn-primary bs-l mt-24px mb-40px">농업정책보험금융원 바로가기<i class="icon-arrow-right"></i></button>
	    <div id="appContent"></div>
	</div>
	</div>
	<%-- ############################# 개요내용 종료 ############################# --%>
	<%-- ############################# 조직도 내용 시작 ############################# --%>
	<div id="organization" >
		<div class="shadow-box-1 p-16px">
			<div class="bg-primary-t5 p-32px text-center">
				<img src="<c:url value="/images/sub/Introduction-map.svg"/>" alt=""  class="w-100">
			</div>
		</div>
	</div>
	<%-- ############################# 조직도 내용 종료 ############################# --%>
	
	<%-- ############################# 조직도 내용 종료 ############################# --%>
	<div id="directions" >
		<div class="shadow-box-1 p-16px mb-56px">
			<img src="<c:url value="/images/sub/Introduction-map2.png"/>" alt=""  class="w-100">
		</div>
		<div class="mb-56px">
			<div class="table-top-1 mb-8px">
				<div class="row">										
					<div class="col">
						<p class="fs-22px txt1"><i class="cycle-ver-1 fs-24px icon-map-location w-40px h-40px"></i>주소 및 연락처</p>
					</div>
				</div>
			</div>
			<div class="bg-ele-Line " style="height:1px;"></div>
			<div class="py-24px">
				<div class="row align-items-center " style="margin:-4px;">
					<div class="col flex-grow-0 fs-24px lh-1 p-4px"><i class="icon-map-pin"></i></div>
					<div class="col flex-b50px fs-18px fw-600 lh-1 p-4px">주소</div>
					<div class="col fs-14px fw-300 lh-1 p-4px">[우]07241 서울특별시 영등포구 여의공원로 101, 2층 201호 (여의도동, CCMM빌딩)</div>
				</div>
				<div class="row align-items-center " style="margin:-4px;">
					<div class="col flex-grow-0 fs-24px lh-1 p-4px"><i class="icon-phone"></i></div>
					<div class="col flex-b50px fs-18px fw-600 lh-1 p-4px">TEL</div>
					<div class="col fs-14px fw-300 lh-1 p-4px">02-3775-6700</div>
				</div>
				<div class="row align-items-center " style="margin:-4px;">
					<div class="col flex-grow-0 fs-24px lh-1 p-4px"><i class="icon-phone-office"></i></div>
					<div class="col flex-b50px fs-18px fw-600 lh-1 p-4px">FAX</div>
					<div class="col fs-14px fw-300 lh-1 p-4px">02-780-4725 (2층), 02-3771-6870 (3층, 정책보험), 02-3775-6779 (3층,모태펀드)</div>
				</div>
			</div>
			<div class="bg-ele-Line " style="height:1px;"></div>
		</div>
		<div class="mb-56px">
			<div class="table-top-1 mb-8px">
				<div class="row">										
					<div class="col">
						<p class="fs-22px txt1"><i class="cycle-ver-1 fs-24px icon-bus w-40px h-40px"></i>교통안내</p>
					</div>
				</div>
			</div>
			<div class="bg-ele-Line " style="height:1px;"></div>
			<div class="py-24px">
				<div class="align-items-center px-32px row" style="margin: 0 -12px;">
					<div class="col flex-b250px px-12px">
						<div class="align-items-center row" style="margin:-4px;">
							<div class="col flex-grow-0  p-4px">
								<span class="align-items-center bg-lavendar d-flex h-32px justify-content-center round-8px text-white w-32px">5</span>
							</div>								
							<div class="col fs-14px fw-300 lh-1 p-4px">지하철 - 여의나루역 1, 2번출구</div>
						</div>
					</div>
					<div class="col flex-b250px px-12px">
						<div class="row align-items-center " style="margin:-4px;">
							<div class="col flex-grow-0  p-4px">
								<div class="col flex-grow-0  p-4px">
								<span class="align-items-center bg-lavendar d-flex h-32px justify-content-center round-8px text-white w-32px">5</span>
							</div>
							</div>								
							<div class="col fs-14px fw-300 lh-1 p-4px">지하철 - 여의도역 3, 4번출구</div>
						</div>
					</div>
					<div class="col flex-b250px px-12px">
						<div class="row align-items-center " style="margin:-4px;">
							<div class="col flex-grow-0  p-4px">
								<div class="col flex-grow-0  p-4px">
								<span class="align-items-center bg-brown d-flex h-32px justify-content-center round-8px text-white w-32px">9</span>
							</div>
							</div>								
							<div class="col fs-14px fw-300 lh-1 p-4px">지하철 - 국회의사당역 3번 출구</div>
						</div>
					</div>
				</div>
			</div>
			<div class="bg-ele-Line " style="height:1px;"></div>
		</div>
		<div class="">
			<div class="table-top-1 mb-8px">
				<div class="row">										
					<div class="col">
						<p class="fs-22px txt1"><i class="cycle-ver-1 fs-24px icon-parking-circle w-40px h-40px"></i>주차안내</p>
					</div>
				</div>
			</div>
			<div class="bg-ele-Line " style="height:1px;"></div>
			<div class="px-32px py-24px">
				<div class="row align-items-center " style="margin:-4px;">
					<div class="col fs-14px fw-300 lh-1 p-4px">60분 무료주차 가능 (가급적 대중교통을 이용해 주시기 바랍니다.)</div>
				</div>
			</div>
			<div class="bg-ele-Line " style="height:1px;"></div>
		</div>
	</div>
</div>
<%-- ############################# 조직도 내용 종료 ############################# --%>
<%-- ############################# 가온누리 인베지움 안내 내용 시작 ############################# --%>
<div id="INVEInfomation">
	<div id="outline">
	
	
		<div class=" mb-56px">
			<div class="datum-point">
				<div class="introduction-imgbox-1">
					<img src="<c:url value='/images/가온누리-메인-이미지.jpg'/>" alt="introduce" class="img1">
					<div class=" txtbox">
						<p class="txt1">가온누리 인베지움(농식품 투자지원센터)은</p>
						<p class="txt2">농림수산식품모태펀드의 투자지원사업을 총괄하는<br/>
							컨트롤 타워로서 2020년 11월에 출범하였습니다.
						</p>
						<span class="txt3 d-block">
							가온누리 인베지움은 농림수산식품모태펀드 투자유치 상담 및 투자유치 역량 강화 컨설팅, 사업설명회(IR) 개최, 피투자기업 지원, 농식품 크라우드 펀딩 지원사업 운영 등 다양한 업무를 통해 농수산식품기업의 투자유치 및 성장을 지원하고 있습니다. 앞으로도 농수산업의 특수성과 전문성을 녹여낸 창업‧투자 생태계 조성을 견인하여 농수산식품기업의 혁신성장에 든든한 조력자로 함께 하겠습니다.
						</span>
					</div>
				</div>
			</div>
		</div>
		
		<div class="mb-56px">
			<div class="datum-point">
				<div class="introduction-imgbox-2">
					<div class="imgbox1">
						<img src="<c:url value='/images/소개영상-아이콘.png'/>" alt="introduce">
						<p class="txt1">
							가온누리 인베지움 소개영상
						</p>
					</div>
 					<div class="imgbox2"> 
						<video class="introduceVideo" controls width="588" height="364">
							<source src="<c:url value='/images/media/intro.mp4'/>" type="video/mp4">
						</video>
					</div>
				</div>
			</div>
		</div>
		
		<!-- -------------------------------------------------------------------------------------------------------------------------------------------- -->
		<div class="col px-24px">
			<div class="mb-48px">
				<div class="datum-point">
					<div class="designbox6-2">
						<img src="<c:url value="/images/1.투자유치 상담.svg"/>" alt="introduce" class="img1">
						<img src="<c:url value="/images/일러스트/투자유치-상담.png"/>" alt="introduce" class="img2">
						<div class="txtbox">
							<p class="tit">투자유치 상담</p>
							<p class="txt-dot txt2" style="font-size:15px">농림수산식품모태펀드 구조, 제도, 투자대상, 절차 등 안내</p>
							<p class="txt-dot txt2" style="font-size:15px">투자 전·후 지원사업 소개 및 투자 유치 관련 1:1 상담</p>
							<p class="txt-dot txt2" style="font-size:15px">농식품 분야 관련 타 지원사업 안내</p>
						</div>
					</div>
				</div>
			</div>
			<div class="mb-48px">
				<div class="datum-point">
				<div class="designbox6-3">
					<img src="<c:url value="/images/2.투자 전 지원사업.svg"/>" alt="introduce" class="img1">
					<img src="<c:url value="/images/일러스트/원스톱-투자지원.png"/>" alt="introduce" class="img2">
					<div class="txtbox">
					<p class="tit">투자 전 지원사업</p>
						<p class="txt-dot txt2" style="font-size:15px">(현장코칭) 사업 비즈니스 모델, 제품/서비스, 대표자 역량 등 경영체 분석 등 기초 컨설팅 지원</p>
						<p class="txt-dot txt2" style="font-size:15px">(맞춤형컨설팅) 투자유치 기본 교육 및 전략 수립, 기업 진단, 사업계획서 및 IR자료 작성 등 경영체 특성과 여건에 따른 컨설팅 지원</p>
						<p class="txt-dot txt2" style="font-size:15px">(사업설명회(IR) 농식품투자조합 운용사 등 농식품 분야 투자자 대상 사업설명 및 네트워킹 기회 지원</p>
					</div>
				</div>
				</div>
			</div>
			<div class="mb-48px">
				<div class="datum-point">
				<div class="designbox6-2 changeFont">
					<img src="<c:url value="/images/3.투자 후 지원사업.svg"/>" alt="introduce" class="img1">
					<img src="<c:url value="/images/일러스트/우수사례-소개.png"/>" alt="introduce" class="img2">
					<div class="txtbox">
						<p class="tit">투자 후 지원사업</p>
					<p class="txt-dot txt2" style="font-size:15px">(온라인 마케팅 지원) 경영체별 마케팅 역량 진단 및 온라인 마케팅 교육, 인플루언서·PR 등 온라인 마케팅 지원</p>
					<p class="txt-dot txt2" style="font-size:15px">(오프라인 마케팅 지원) 국·내외 해외 박람회 참가 지원, 구매·수출 상담회 등을 통한 사업소개 및 홍보 등 현장마케팅 지원</p>
					<p class="txt-dot txt2" style="font-size:15px">(스케일업프로그램 지원) 경영·재무·회계·법률·사업전략·IPO·M&A 등 경영체별 맞춤 컨설팅 지원</p>
					<p class="txt-dot txt2" style="font-size:15px">(후속투자 유치 지원) 징검다리펀드 운용사 및 농식품 분야 투자자 대상 사업설명 및 네트워킹 기회 제공</p>
					<p class="txt-dot txt2" style="font-size:15px">(투자자산 회수 지원) IPO, M&A 전문가 강연 및 구매 희망 수요기업과의 비즈니스 미팅 등 컨퍼런스 개최</p>
					</div>
				</div>
				</div>
			</div>
			<div class="mb-48px">
				<div class="datum-point">
				<div class="designbox6-5">
					<img src="<c:url value="/images/4.농식품크라우드 펀딩.svg"/>" alt="introduce" class="img1">
					<img src="<c:url value="/images/일러스트/온라인-IR.png"/>" alt="introduce" class="img2">
					<div class="txtbox">
						<p class="tit">농식품크라우드 펀딩</p>
						<p class="txt-dot txt2" style="font-size:15px">(현장코칭) 전문 컨설팅 기관이 농식품 기업을 방문하여 크라우드펀딩 제도에 대한 기본 교육 및 투자유치 전략 등 1:1 방문 코칭</p>
						<p class="txt-dot txt2" style="font-size:15px">(컨설팅 비용 지원) 크라우드펀딩을 위한 법률&회계 자문, 기업 및 제품의 홍보 콘텐츠 제작, SNS홍보 등의 전문 컨설팅 비용 지원</p>
						<p class="txt-dot txt2" style="font-size:15px">(수수료 지원) 펀딩 성공 시 투자유치 금액에 근거하여 산출하는 금액으로 사업자가 중개사에 지급하는 광고 및 중개수수료 지원</p>
					</div>
				</div>
				</div>
			</div>
		</div>
				
		<!---------------------------------------------------------------------------------------------------------------------------------------------- -->
				
		<div class="pt-56px">
			<div class="datum-point">
				<p class="fs-24px fw-600 mb-16px">사업설명</p>
				<div class="bipage" style="margin-top: 50px">
					<div class="top">
						<div class="row" style="">
							<div class="col-12 col-md">
								<div class="box text-center">
									<img src="<c:url value="/images/logo/APFSBI-img01.svg"/>" class="chage2" style="width: auto;height: -webkit-fill-available;" alt="로고" />
								</div>
							</div>
							<div class="col-12 col-md">
								<p class="txt1">로고 소개</p>
								<span class="d-block fs-15px fw-300 lh-19px">농금원의 투자지원센터인 가온누리 인베지움은 가운데를 의미하는 ‘가온’과 세상을 의미하는 ‘누리’의 합성어로 세상의 중심을 의미하는 우리말 가온누리와, 
												투자(Investment)와 박물관(Museum)의 합성어로 ‘투자에 관한 모든 것이 모여있는 공간’이라는 뜻을 담고 있는 인베지움이 결합한 새로운 이름입니다. 
								</span>
							</div>
						</div>
					</div>
				</div>
				<div class="bottom-box-btn pb-0">
					<div class="row">						
						<div class="col">
							<button id="btnRogoAi"  class="btn-primary w-100 bs-l"><i class="icon-download"></i>AI 파일 다운로드</button>
						</div>
						<div class="col">
							<button id="btnRogoPng" class="btn-primary w-100 bs-l"><i class="icon-download"></i>PNG 파일 다운로드</button>
						</div>
						<div class="col">
							<button id="btnRogoJpg" class="btn-primary w-100 bs-l"><i class="icon-download"></i>JPG 파일 다운로드</button>
						</div>						
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="box" id="INVEDirections1">
		<div class="mb-16px">
			<div class="table-top-1 mb-8px">
				<div class="row">										
					<div class="col">
						<p class="fs-22px txt1"><i class="bg-mint cycle-ver-1 fs-24px h-40px icon-map-location w-40px"></i>서울센터 주소</p>
					</div>
				</div>
			</div>
			<div class="bg-ele-Line " style="height:1px;"></div>
			<div class="py-16px">
				<div class="row align-items-center " style="margin:-4px;">
					<div class="col flex-grow-0 fs-24px lh-1 p-4px"><i class="icon-map-pin"></i></div>
					<div class="col flex-b50px fs-18px fw-600 lh-1 p-4px">주소</div>
					<div class="col fs-14px fw-300 lh-1 p-4px">[우]07241 서울특별시 영등포구 여의공원로 101, 2층 201호 (여의도동, CCMM빌딩)</div>
				</div>
			</div>
		</div>
		<div class="shadow-box-1 p-16px mb-24px">
			<img src="<c:url value="/images/sub/Introduction-map3.png"/>" class="w-100" alt="">
		</div>
		<div class="px-16px mb-16px">
			<div class="table-top-1 mb-8px">
				<div class="row">										
					<div class="col">
						<p class="fs-22px txt1"><i class="cycle-ver-1 fs-20px icon-bus w-36px h-36px"></i>교통안내</p>
					</div>
				</div>
			</div>
			<div class="bg-ele-Line " style="height:1px;"></div>
			<div class="py-16px">
				<div class="align-items-center px-32px row" style="margin:-12px;">
					<div class="col flex-b250px p-12px">
						<div class="align-items-center row" style="margin:-4px;">
							<div class="col flex-grow-0  p-4px">
								<span class="align-items-center bg-lavendar d-flex h-32px justify-content-center round-8px text-white w-32px">5</span>
							</div>								
							<div class="col fs-14px fw-300 lh-1 p-4px">지하철 - 여의나루역 1, 2번출구</div>
						</div>
					</div>
					<div class="col flex-b250px p-12px">
						<div class="row align-items-center " style="margin:-4px;">
							<div class="col flex-grow-0  p-4px">
								<span class="align-items-center bg-lavendar d-flex h-32px justify-content-center round-8px text-white w-32px">5</span>
							</div>								
							<div class="col fs-14px fw-300 lh-1 p-4px">지하철 - 여의도역 3, 4번출구</div>
						</div>
					</div>
					<div class="col flex-b250px p-12px">
						<div class="row align-items-center " style="margin:-4px;">
							<div class="col flex-grow-0  p-4px">								
								<span class="align-items-center bg-brown d-flex h-32px justify-content-center round-8px text-white w-32px">9</span>										
							</div>								
							<div class="col fs-14px fw-300 lh-1 p-4px">지하철 - 국회의사당역 3번 출구</div>
						</div>
					</div>
				</div>
			</div>
			<div class="bg-ele-Line " style="height:1px;"></div>
		</div>
		<div class="px-16px">
			<div class="table-top-1 mb-8px">
				<div class="row">										
					<div class="col">
						<p class="fs-22px txt1"><i class="cycle-ver-1 fs-20px icon-bus w-36px h-36px"></i>버스를 이용하실 경우</p>
					</div>
				</div>
			</div>
			<div class="bg-ele-Line " style="height:1px;"></div>
			<div class="py-16px">
				<div class="mb-16px pl-44px">
					<div class="row " style="margin:-4px;">							
						<div class="col-12 col-md flex-b-md-238px fs-15px fw-600 lh-1 p-4px">여의도순복음교회 하차</div>
						<div class="col fs-14px fw-300 lh-1 p-4px lh-15per">[간선]261, [간선]463, [지선]5534, [지선]5633, [일반]10, [마을]영등포10, [직행]7007-1</div>
					</div>
				</div>
				<div class="mb-16px pl-44px">
					<div class="row " style="margin:-4px;">							
						<div class="col-12 col-md flex-b-md-238px fs-15px fw-600 lh-1 p-4px">여의도환승센터 3~4번 승강장 하차</div>
						<div class="col fs-14px fw-300 lh-1 p-4px lh-15per">[일반]88, [간선]160, [간선]260, [간선]360, [간선]600, [간선]662, [지선]5012, [지선]6623, [지선]6628, [직행]8600, [직행]8601, [간선]N16(심야), [직행]8601A</div>
					</div>
				</div>
				<div class="pl-44px">
					<div class="row " style="margin:-4px;">							
						<div class="col-12 col-md flex-b-md-238px fs-15px fw-600 lh-1 p-4px">여의도환승센터 1~2번 승강장 하차</div>
						<div class="col fs-14px fw-300 lh-1 p-4px lh-15per">[일반]10, [일반]11-1, [일반]11-2, [일반]530, [일반]83, [좌석]301, [좌석]320, [좌석]871, [간선]160, [간선]162, [간선]260, [간선]261, [간선]262, [간선]360, [간선]662. [지선]5012, [지선]5615, [지선]5618, [지선]5623, [지선]6513, [지선]6654, [직행]5609, [직행]8600, [직행]8601, [간선]N16(심야), [직행]8601A</div>
					</div>
				</div>
			</div>
			<div class="bg-ele-Line " style="height:1px;"></div>
		</div>
	</div>
<div class="box" id="INVEDirections2">
	<div class="mb-16px">
		<div class="table-top-1 mb-8px">
			<div class="row">										
				<div class="col">
					<p class="fs-22px txt1"><i class="bg-mint cycle-ver-1 fs-24px h-40px icon-map-location w-40px"></i>익산센터 주소</p>
				</div>
			</div>
		</div>
		<div class="bg-ele-Line " style="height:1px;"></div>
		<div class="py-16px">
			<div class="row align-items-center " style="margin:-4px;">
				<div class="col flex-grow-0 fs-24px lh-1 p-4px"><i class="icon-map-pin"></i></div>
				<div class="col flex-b50px fs-18px fw-600 lh-1 p-4px">주소</div>
				<div class="col fs-14px fw-300 lh-1 p-4px">전북 익산시 왕궁면 국가식품로 100. 한국식품산업클러스터진흥원 본관 1층</div>
			</div>
		</div>
	</div>
	<div class="shadow-box-1 p-16px mb-24px">
		<img src="<c:url value="/images/sub/Introduction-map4.png"/>" class="w-100" alt="">
	</div>
	<div class="px-16px mb-24px">
		<div class="table-top-1 mb-8px">
			<div class="row">										
				<div class="col">
					<p class="fs-22px txt1"><i class="cycle-ver-1 fs-20px icon-map w-36px h-36px"></i>자동차(네비게이션) 이용하실 경우</p>
				</div>
			</div>
		</div>
		<div class="bg-ele-Line " style="height:1px;"></div>
		<div class="py-16px">
			<div class="pl-44px">
				<div class="row " style="margin:-4px;">																
					<div class="col-12 fs-14px fw-300 lh-1 p-4px lh-15per">1. 국가식품클러스터 진입로 주소로 검색하여 도착 후, 단지내 진흥원 안내판 참고[약 2분소요]</div>
					<div class="col-12 fs-14px fw-300 lh-1 p-4px lh-15per">2. 한국식품산업클러스터진흥원 도착</div>
				</div>
			</div>								
		</div>
		<div class="bg-ele-Line " style="height:1px;"></div>
	</div>
	<div class="px-16px mb-24px">
		<div class="table-top-1 mb-8px">
			<div class="row">										
				<div class="col">
					<p class="fs-22px txt1"><i class="cycle-ver-1 fs-20px icon-bus w-36px h-36px"></i>버스를 이용하실 경우(익산 KTX역, 터미널 기준)</p>
				</div>
			</div>
		</div>
		<div class="bg-ele-Line " style="height:1px;"></div>
		<div class="py-16px">
			<div class="pl-44px">
				<div class="row " style="margin:-4px;">																
					<div class="col-12 fs-14px fw-300 lh-1 p-4px lh-15per">1. 좌석버스 555(역전, 북부시장, 금마, 봉동, 금마, 북부시장, 역전)(대한통운)승차</div>
					<div class="col-12 fs-14px fw-300 lh-1 p-4px lh-15per">2. 한국식품산업클러스터진흥원 입구에서 하차</div>
				</div>
			</div>								
		</div>
		<div class="bg-ele-Line " style="height:1px;"></div>
	</div>
	<div class="px-16px ">
		<div class="table-top-1 mb-8px">
			<div class="row">										
				<div class="col">
					<p class="fs-22px txt1"><i class="cycle-ver-1 fs-20px icon-taxi w-36px h-36px"></i>택시를 이용하실 경우</p>
				</div>
			</div>
		</div>
		<div class="bg-ele-Line " style="height:1px;"></div>
		<div class="py-16px">
			<div class="pl-44px">
				<div class="row " style="margin:-4px;">																
					<div class="col-12 fs-14px fw-300 lh-1 p-4px lh-15per">익산 KTX역, 터미널에서 한국식품산업클러스터진흥원까지 택시로 약 30분 소요</div>
				</div>
			</div>								
		</div>
		<div class="bg-ele-Line " style="height:1px;"></div>
	</div>
</div>
<div class="box" id="INVEDirections3">
			<div class="mb-16px">
				<div class="table-top-1 mb-8px">
					<div class="row">										
						<div class="col">
							<p class="fs-22px txt1"><i class="bg-mint cycle-ver-1 fs-24px h-40px icon-map-location w-40px"></i>경북센터 주소</p>
						</div>
					</div>
				</div>
				<div class="bg-ele-Line " style="height:1px;"></div>
				<div class="py-16px">
					<div class="row align-items-center " style="margin:-4px;">
						<div class="col flex-grow-0 fs-24px lh-1 p-4px"><i class="icon-map-pin"></i></div>
						<div class="col flex-b50px fs-18px fw-600 lh-1 p-4px">주소</div>
						<div class="col fs-14px fw-300 lh-1 p-4px">경상북도 예천군 호명면 수변로 125, 서진타워 2층</div>
					</div>
				</div>
			</div>
			<div class="shadow-box-1 p-16px mb-24px">
				<img src="<c:url value="/images/sub/Introduction-map5.png"/>" class="w-100" alt="">
			</div>
			<div class="px-16px mb-24px">
				<div class="table-top-1 mb-8px">
					<div class="row">										
						<div class="col">
							<p class="fs-22px txt1"><i class="cycle-ver-1 fs-20px icon-bus w-36px h-36px"></i>버스를 이용하실 경우(익산 KTX역, 터미널 기준)</p>
						</div>
					</div>
				</div>
				<div class="bg-ele-Line " style="height:1px;"></div>
				<div class="py-16px">
					<div class="mb-16px pl-44px">
						<div class="row " style="margin:-4px;">							
							<div class="col-12 flex-b238px fs-15px fw-600 lh-1 p-4px text-primary">안동터미널 기준</div>
							<div class="col-12 fs-14px fw-300 lh-1 p-4px lh-15per">1. 일반버스 11 (안동역, 터미널, 풍산)(안동터미널) 또는 일반버스 244 (갈전, 움골)(안동터미널) 승차<br/>
								2. 현대아이파크 정류장 하차<br/>
								3. 경북센터까지 도보로 463m 거리(약 8분소요)</div>
						</div>
					</div>
					<div class="pl-44px">
						<div class="row " style="margin:-4px;">							
							<div class="col-12 flex-b238px fs-15px fw-600 lh-1 p-4px text-primary">예천시외버스정류장 기준</div>
							<div class="col-12 fs-14px fw-300 lh-1 p-4px lh-15per">1. 농어촌버스 77 (경북도청, 호명초등)(예천시내버스터미널-예천시내버스터미널) 승차<br/>
								2. 우방아이유쉘2차 정류장 하차<br/>3. 경북센터까지 도보로 432m 거리(약 7분소요)</div>
						</div>
					</div>
				</div>
				<div class="bg-ele-Line " style="height:1px;"></div>
			</div>
			<div class="px-16px mb-24px">
				<div class="table-top-1 mb-8px">
					<div class="row">										
						<div class="col">
							<p class="fs-22px txt1"><i class="cycle-ver-1 fs-20px icon-taxi w-36px h-36px"></i>택시를 이용하실 경우</p>
						</div>
					</div>
				</div>
				<div class="bg-ele-Line " style="height:1px;"></div>
				<div class="py-16px">
					<div class=" pl-44px">
						<div class="txt-lev-box-1">
							<p class="txt-dot fs-14px mb-4px">
								안동역에서 경북센터까지 택시로 약 20분 소요
							</p>
							<p class="txt-dot fs-14px">
								경북도청시외버스정류장에서 경북센터까지 택시로 약 5분 소요
							</p>
						</div>
					</div>
				</div>
				<div class="bg-ele-Line " style="height:1px;"></div>
			</div>
		</div>

<%-- ############################# 가온누리 인베지움 안내 내용 종료 ############################# --%>