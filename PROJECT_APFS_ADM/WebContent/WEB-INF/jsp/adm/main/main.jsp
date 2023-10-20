<%--
*******************************************************************************
***	명칭: main.jsp
***	설명: 메인 페이지
***
***	-----------------------------    Modified Log   ---------------------------
***	버전     수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***	1.0      2023.05.02      LSH           First Coding.
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
<!-- Chart.js Chart Plugin -->
<script src="<c:url value='/plugins/charts/chartjs-4.3.0/chart.umd.js'/>"></script>
<script src="<c:url value='/plugins/charts/chartjs-4.3.0/chartjs-plugin-datalabels.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/com/comm_chart.js'/>"></script>

<!-- 처리할 일 url, params -->
<c:url value="/adm/invest/ent/openEnt.do" var="entUrl">
	<c:param name="initParams" value="{srchAprvSttsCd:0}" />
</c:url>
<c:url value="/adm/support/support/openInvtSprtAply.do" var="sprtBUrl">
	<c:param name="initParams" value='{sprtAplySeCd:"SB",prgrsSttsCd:"B10"}' />
</c:url>
<c:url value="/adm/support/support/openInvtSprtAply.do" var="sprtAUrl">
	<c:param name="initParams" value='{sprtAplySeCd:"SA",prgrsSttsCd:"A10"}' />
</c:url>
<c:url value="/adm/support/support/openInvtSprtAply.do" var="sprtCUrl">
	<c:param name="initParams" value='{sprtAplySeCd:"SC",prgrsSttsCd:"C10"}' />
</c:url>

<form id="searchForm" name="searchForm" method="post" onsubmit="return false;">
	<div class="dashboard-box">
		<div class="row">
			<div class="col-4">
				<div class="box shadow-box p-24px">
					<p class="tit in-border">
						처리할 일
					</p>
					<div class="main-list-box-1">
						<div class="box" onclick="location.href='${entUrl}'">
							<i class="icon-user-check-bottom bg-gradation-green"></i>
							<p>업체 승인대기</p>
							<span>
								${model.bzentyCnt }<em>건</em>
							</span>
						</div>
						<div class="box" onclick="location.href='${sprtBUrl}'">
							<i class="icon-id-badge	bg-gradation-lavendar"></i>
							<p>투자전 지원사업 상담필요</p>
							<span>
								${model.SBCnt }<em>건</em>
							</span>
						</div>
						<div class="box" onclick="location.href='${sprtAUrl}'">
							<i class="icon-document-list-check bg-gradation-primary"></i>
							<p>투자후 지원사업 심사필요</p>
							<span>
								${model.SACnt }<em>건</em>
							</span>
						</div>
						<div class="box" onclick="location.href='${sprtCUrl}'">
							<i class="icon-cloud-connection bg-gradation-mint"></i>
							<p>크라우드펀드 지원사업 심사필요</p>
							<span>
								${model.SCCnt }<em>건</em>
							</span>
						</div>
						<div class="box" onclick="location.href='<c:url value='/adm/inform/bbs/openQNA.do'/>'">
							<i class="icon-chats-dots bg-gradation-orange"></i>
							<p>1:1 문의답변 대기</p>
							<span>
								${model.bbsCnt }<em>건</em>
							</span>
						</div>
					</div>
				</div>
			</div>
			<div class="col-4">
				<div class="box shadow-box p-24px">
					<p class="tit in-border">
						주요통계
					</p>
					<div class="main-list-box-2">
						<div class="row">
							<div class="col-6">
								<div class="box">
									<i class="icon-user-alt"></i>
									<p>개인회원
										<span>
											${cnt.userCnt }<em>명</em>
										</span>
	
									</p>
									
								</div>
							</div>
							<div class="col-6">
								<div class="box">
									<i class="icon-building"></i>
									<p>기업회원
										<span>
											${cnt.compCnt }<em>명</em>
										</span>
	
									</p>
									
								</div>
							</div>
							<div class="col-6">
								<div class="box">
									<i class="icon-coins"></i>
									<p>진행중인 모태펀드
										<span>
											${cnt.fundCnt }<em>개</em>
										</span>
	
									</p>
									
								</div>
							</div>
							<div class="col-6">
								<div class="box">
									<i class="icon-wallet"></i>
									<p>IR 지원 현황
										<span>
											${cnt.irCnt }<em>건</em>
										</span>
	
									</p>
									
								</div>
							</div>
						</div>
						
						
					</div>
				</div>
			</div>
			<div class="col-4">
				<div class="box shadow-box p-24px">
					<p class="tit in-border">
						Quick Menu
					</p>
					<div class="main-list-box-3">
						<div class="row">
							<div class="col-6">
								<div class="box" onclick="location.href='<c:url value='/adm/invest/fund/openFund.do'/>'">
									<p>모태펀드 등록</p>
									<i class="icon-arrow-right"></i>											
								</div>
							</div>
							<div class="col-6">
								<div class="box" onclick="location.href='<c:url value='/adm/sys/user/openUserInfo.do'/>'">
									<p>회원관리</p>
									<i class="icon-arrow-right"></i>										
								</div>
							</div>
							<div class="col-6">
								<div class="box" onclick="location.href='<c:url value='/adm/invest/event/openEvent.do'/>'">
									<p>투자설명회 등록</p>
									<i class="icon-arrow-right"></i>										
								</div>
							</div>
							<div class="col-6">
								<div class="box" onclick="location.href='<c:url value='/adm/support/support/openInvtSprt.do'/>'">
									<p>세부지원사업관리</p>
									<i class="icon-arrow-right"></i>										
								</div>
							</div>
							<div class="col-6">
								<div class="box" onclick="location.href='<c:url value='/adm/invest/event/openIrOpnn.do'/>'">
									<p>IR 검토의견서 관리</p>
									<i class="icon-arrow-right"></i>										
								</div>
							</div>
							<div class="col-6">
								<div class="box" onclick="location.href='<c:url value='/adm/support/support/openInvtSprtAply.do'/>'">
									<p>신청현황관리</p>
									<i class="icon-arrow-right"></i>										
								</div>
							</div>
							<div class="col-6">
								<div class="box" onclick="location.href='<c:url value='/adm/inform/bbs/openData.do'/>'">
									<p>자료실</p>
									<i class="icon-arrow-right"></i>										
								</div>
							</div>
							<div class="col-6">
								<div class="box" onclick="location.href='<c:url value='/adm/support/pbanc/openPbanc.do'/>'">
									<p>사업공고관리</p>
									<i class="icon-arrow-right"></i>										
								</div>
							</div>
							<div class="col-6">
								<div class="box" onclick="location.href='<c:url value='/adm/inform/bbs/openPromotion.do'/>'">
									<p>홍보영상</p>
									<i class="icon-arrow-right"></i>										
								</div>
							</div>
							<div class="col-6">
								<div class="box" onclick="location.href='<c:url value='/adm/invest/investfield/openInvestField.do'/>'">
									<p>투자분야 관리</p>
									<i class="icon-arrow-right"></i>										
								</div>
							</div>
						</div>
						
						
					</div>
				</div>
			</div>
			<div class="col-6">
				<!-- 목록 영역 -->
				<div class="box shadow-box p-24px h-100">
					<p class="tit align-items-center d-flex justify-content-between ">
						경영체 회원가입 현황
						<a href="<c:url value='/adm/invest/ent/openEnt.do'/>" >
							<i class="icon-plus text-primary fs-14px"></i>
							<span class="hiddenATag">더보기</span>
						</a>
					</p>
					<!-- 목록 컨텐츠 영역 -->
					<div id="appGridEnt"></div>
				</div>
			</div>
			<div class="col-6">
				<!-- 목록 영역 -->
				<div class="box shadow-box p-24px h-100">
					<p class="tit align-items-center d-flex justify-content-between ">
						자료실 인기 콘텐츠
						<a href="<c:url value='/adm/inform/bbs/openData.do'/>" >
							<i class="icon-plus text-primary fs-14px"></i>
							<span class="hiddenATag">더보기</span>
						</a>
					</p>
					<!-- 목록 컨텐츠 영역 -->
					<div id="appGridBbs"></div>
				</div>
			</div>
			<div class="col-12">
				<div class="box shadow-box p-24px form-label-box">
					<p class="tit in-border align-items-center d-flex justify-content-between ">
						사용자 유형별 접속 통계
						<span class="box datepickerMonth-input align-items-center d-flex">
	<!-- 						<i class="icon-angle-left " id="datepicker-prev" style="cursor: pointer;"></i>		 -->
							<label for="srchAcsYm"></label>
							<input type="text" name="srchAcsYm" id="srchAcsYm" class="text-primary" style="border: none; font-size: 17px; text-align: center; width: 80px; cursor: pointer; font-weight:700;" readonly>
							<i class="icon-calendar right text-primary" style="cursor: pointer; font-size:15px;"></i>
	<!-- 						<i class="icon-angle-right " id="datepicker-next" style="cursor: pointer;"></i> -->
						</span>
						<span class="d-inline-flex align-items-center">
							<input type="radio" class="roleId" id="roleType1" name="roleId" value="ROLE_USR_EIV" checked/>
							<label for="roleType1" class="ms-1 me-3">투자자회원</label>
							<input type="radio" class="roleId" id="roleType2" name="roleId" value="ROLE_USR_EBZ" />
							<label for="roleType2" class="ms-1 me-3">경영체회원</label>
							<input type="radio" class="roleId" id="roleType3" name="roleId" value="ROLE_USR_USR" />
							<label for="roleType3" class="ms-1 me-3">개인회원</label>
						</span>
					</p>
					<%-- AREA CHART --%>
					<div class="app-chart">
						<canvas id="appAreaChart"></canvas>
					</div>
				</div>
				
			</div>
		
		</div>
	</div>
</form>

<%-- ############################ 내용 (종료) ############################ --%>
