<%--
*******************************************************************************
***    명칭: openGIS.jsp
***    설명: 투자서비스 - GIS 화면
***
***    -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.04.04    LSH        First Coding.
*******************************************************************************
--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="app"    uri="/WEB-INF/tld/app.tld" %>
<%@ taglib prefix="f"      uri="/WEB-INF/tld/f.tld" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec"    uri="http://www.springframework.org/security/tags" %>

<link rel="stylesheet" type="text/css" href="<c:url value='/css/gis.css'/>" />

<%-- ############################# 내용 (시작) ############################# --%>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=<c:out value="${OAUTH_KAKAO_CLIENT_JS_ID}"/>&libraries=services"></script>
	<!-- head-GIS << -->
	<div class="head-GIS">
		<div class="datum-point">
			<div class="row">
				<div class="col menu-btn">
					<a href="javascript:void(0)" class="" onclick="m_menu();"><i class="icon-menu"></i><span class="hiddenATag">숨김</span></a>
				</div>
				<div class="col logo-box">
					<div class="logo-img">
						<a class="d-block h-100" href="<c:url value="/usr/main/main.do"/>">
							<img src="<c:url value="/images/logo/Property2color.svg"/>" class="" alt="ASSIST GIS">								
						</a>							
					</div>
				</div>						
				<div class="col d-xl-inline-flex search-box">
					<div class="form-area-box1">
						<div class="ele-icon-box">
							<i class="icon-search"></i>
							<input type="text" name="srchText" id="srchText" placeholder="검색해주세요" title="srchText">
						</div>
						
					</div>
					
				</div>
				<div class="col d-xl-inline-flex">
					<button class="searchBtn" onclick="textSearch();"><i class="icon-search mr-5px"></i>조회</button>	
				</div>			
				<div class="col d-xl-inline-flex suport">
					<div class="capsule-box">
						<div class="row">
							<div class="col active fs-15px lh-32px btnBase" onclick="baseChange('base');">
								<i class="icon-map-location mr-5px"></i>일반지도</div>
							<div class="col fs-15px lh-32px btnSatellite" onclick="baseChange('satellite');">
								<i class="icon-globe-earth mr-5px"></i>위성지도
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<img src="<c:url value="/images/logo/hovermeunimg1.png"/>" class="img1" alt="">
	<!-- head-GIS >> -->
	</div>
	
	<!-- sub-GIS << -->
	<div class="sub-GIS">
		<!--map-->
		<div id="map" style="width:100%;height:100%;"></div>
		<!--//map-->
		<div class="GIS-Tool-box">
			<div class="leftbox1">
				<button class="sidebtn position1" onclick="complex_show();">
					<i class="icon-angle-right"></i>
				</button>
				<div class="table-box-of-box">
					<div class="top-box">
						<div class="row">
							<div class="col">
								<p class="tit" style="display: none;">
									농림수산식품 경영체
									<span class="bg-mint text-white">2,501</span>
								</p>
							</div>
							<div class="col btnbox">
								<button class="bg-primary-t10 text-primary" onclick="fillter_show();"><i class="icon-sliders-horizontal"></i>필터</button>
							</div>
						</div>
					</div>
					
					<div id="appGridPanel" class="table-box-1">		
						<div id="appGrid"></div>						
					<%--			
						<table>
							<colgroup>								
								<col width="10%">	
								<col width="*">	
								<col width="*">	
								<col width="*">									
							</colgroup>
							<thead class="t-t-c ts-m">
								<tr>
									<th>
										<div class="check-radio-box ver1 only-ver">
											<input type="checkbox" id="ab-1" name="aa">
											<label class="" for="ab-1"></label>
										</div>
									</th>
									<th class="">경영체명</th>
									<th>사업분야</th>
									<th>투자분야</th>
									<th>투자희망금액</th>
								</tr>
							</thead>
							<tbody class="px-v-s t-t-c ts-s">
								<tr onclick="detail_show();">
									<td>
										<div class="check-radio-box ver1 only-ver">
											<input type="checkbox" id="ab-1" name="aa">
											<label class="" for="ab-1"></label>
										</div>
									</td>
									<td>바른먹거리</td>
									<td>바이오</td>
									<td>수출</td>
									<td class="numtxt-1"><em class="">3</em>억원</td>
								</tr>
							</tbody>
						</table>--%>
					</div>
					
					<div class="mb-0 paging-box pb-24px">
						<ul id="appGridPagination"></ul>
					</div>
					
					<button class="sidebtn position2" onclick="complex_show_hide1();">
						<i class="icon-angle-left"></i>
					</button>
				</div>
				

				<div class="fillter-box">
					<button class="close" onclick="fillter_updown();"></button>
					<div class="top1">
						<div class="row">
							<div class="col iconbox">
								<i class="icon-sliders-horizontal"></i>
							</div>
							<div class="col">
								<p class="tit">필터</p>
							</div>
							<div class="col closebox">
								<button class="icon-times" onclick="fillter_close()"></button>
							</div>
							
						</div>
					</div>
					<div class="sub-box-2">
						<form id="searchForm" name="searchForm" method="post" onsubmit="return false;">
							<div id="appSmartSearch"></div>
						</form>					
					</div>
				</div>
				<div class="leftdetailbox">
					<img src="<c:url value="/images/sub/img4.svg"/>" alt="" class="img1 app-ent-listimage">
					<div class="topbox">
						<p class="tit1 bzentyNm"></p>
						<div class="info-1 bizListBox">
						</div>
					</div>
					<%--
					<div class="centerbox">
						<div class="row">
							<div class="col">
								<div class="form-area-box">
									<label>출발지 명</label>
									<div class="ele-icon-box">
										<input type="text" name="" id="">
									</div>
									
								</div>
							</div>
							<div class="col btnbox">
								<button><i class="icon-arrow-left-arrow-right"></i></button>								
							</div>
							<div class="col">
								<div class="form-area-box">
									<label>도착지 명</label>
									<div class="ele-icon-box ">
										<input type="text" name="" id="" >
									</div>										
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col">
								<button class="btn-black-ouline"><i class="icon-rotate-left"></i>다시 입력</button>
							</div>
						</div>
					</div>--%>
					<div class="company-info">
						<div class="tit-box-4">
							<div class="row">
								<div class="col iconbox">
									<i class="icon-building-tree"></i>
								</div>
								<div class="col">
									<p class="txt1">기업정보</p>
								</div>
								<div class="col" style="margin-left: 50%;">
									<button class="btn-black" onclick="findAddr()"><i class="icon-route"></i>길찾기</button>
								</div>
							</div>
						</div>
						
						<div class="content mb-16px">
							<div class="row">
								<div class="col-12">
									<div class="form-area-box-input">
										<label>사업자번호</label>
										<div class="ele-icon-box">
											<div class="value-box brno"></div>
										</div>
										
									</div>
								</div>
								<div class="col-12">
									<div class="form-area-box-input">
										<label>업종
										</label>
										<div class="ele-icon-box">
											<div class="value-box tpbizNm"></div>
										</div>
										
									</div>
								</div>
								<div class="col-12">
									<div class="form-area-box-input">
										<label>설립일</label>
										<div class="ele-icon-box">
											<div class="value-box fndnYmd"></div>
										</div>
										
									</div>
								</div>
								<div class="col-12">
									<div class="form-area-box-input">
										<label>소재지</label>
										<div class="ele-icon-box">
											<div class="value-box lctnAddr"></div>
										</div>
										
									</div>
								</div>
								<div class="col-12">
									<div class="form-area-box-input">
										<label>홈페이지</label>
										<div class="ele-icon-box">
											<div class="value-box hmpgAddr"></div>
										</div>
										
									</div>
								</div>
								<div class="col-12">
									<div class="form-area-box-input">
										<label>TEL / FAX
										</label>
										<div class="ele-icon-box">
											<div class="value-box rprsTelno"></div>
										</div>
										
									</div>
								</div>
								
								<div class="col-12">
									<div class="form-area-box-input">
										<label>주요사업</label>
										<div class="ele-icon-box">
											<div class="value-box mainBizCn"></div>
										</div>
										
									</div>
								</div>
								<div class="col-12">
									<div class="form-area-box-input">
										<label>핵심아이템</label>
										<div class="ele-icon-box">
											<div class="value-box coreItmCn"></div>
										</div>
										
									</div>
								</div>
								<div class="col-12">
									<div class="form-area-box-input">
										<label>담당자</label>
										<div class="ele-icon-box">
											<div class="value-box picNm"></div>
										</div>
										
									</div>
								</div>
								<div class="col-12">
									<div class="form-area-box-input">
										<label>담당자연락처
										</label>
										<div class="ele-icon-box">
											<div class="value-box picTelno"></div>
										</div>
										
									</div>
								</div>
							</div>
						</div>
						<div class="bottom-box-btn py-8px">
							<div class="row">
								<div class="col">
									<button class="btn-primary w-100 bs-l" onclick="showViewEntInfo();"><i class="icon-search-text"></i>경영체 정보 더보기</button>
								</div>
								
							</div>
						</div>
					</div>
					<button class="sidebtn position3" onclick="complex_show_hide2();">
						<i class="icon-angle-left"></i>
					</button>
				</div>
				
			</div>
			
			<div class="floatingbox">
				<div class="top">
					<div class="tit-box2-1">
						<div class="row">
							<div class="col flex-b24px">
								<i class="fs-16px icon-chart-bar-vertical"></i>
							</div>
							<div class="col">
								<p class="txt">통계자료 조회</p>
							</div>
							<div class="col flex-b24px" onclick="small_floating();">
								<i class="fs-16px icon-minimize trans-hide"></i>
								<i class="fs-16px icon-maximize trans-show"></i>
							</div>
						</div>
					</div>
				</div>
				<div class="second">
					<div class="tit-box2-2">
						<div class="row">
							
							<div class="col">
								<p class="txt">투자분야</p>
							</div>
							<div class="col">
								<div class="form-area-box small-ver">									
									<div class="ele-icon-box selectBoxBorder">										
										<div class="dropdown selectBoxHeight">
											<label for="investTypeCode"></label>
											<select id="investTypeCode" name="investTypeCode" class="w300"></select>
										</div>
									</div>										
								</div>
							</div>
							<div class="col">
								<p class="txt">사업분야</p>
							</div>
							<div class="col">
								<div class="form-area-box small-ver">									
									<div class="ele-icon-box selectBoxBorder">										
										<div class="dropdown selectBoxHeight">
											<label for="bizTypeCode"></label>
											<select id="bizTypeCode" name="bizTypeCode" class="w300"></select>
										</div>
									</div>										
								</div>
							</div>
							<div class="col">
								<p class="txt">현황</p>
							</div>
							<div class="col">
								<div class="form-area-box small-ver">									
									<div class="ele-icon-box selectBoxBorder">										
										<div class="dropdown selectBoxHeight">
											<label for="currentType"></label>
											<select id="currentType" name="currentType" class="w300">
												<option value="">선택</option> 
												<option value="mgmt">경영체 현황</option>    
     											<option value="scale">자본규모 현황</option>   
											</select>
										</div>
									</div>										
								</div>
							</div>
							<div class="col">
								<button class="btn-black bs-s" onclick="getGisData();">
									<i class="icon-search"></i>조회
								</button>
							</div>
							<div class="col">
								<div class="check-radio-box small-ver ">
									<input type="checkbox" id="a1" name="aa">
									<label class="" for="a1">레이어보기</label>
								</div>
							</div>
						</div>
					</div>	
				</div>
				
				<div class="chart align-content-center bg-primary-t10 mx-16px mb-16px" style="height: 184px;display: flex;flex-direction: column;justify-content: center; overflow-x:auto; overflow-y: hidden;">
					<canvas id="myChart" height="180" style="display:none;"></canvas>
					<div class="noChartDiv" style="display: contents; text-align:center;">
						<i class="fs-40px icon-chart-trend-down F text-primary" style=""></i>
						<span class="" style="font-weight: 300;font-size: 13px;">조회한 데이터가 존재하지 않습니다</span>
					</div>			
				</div>
			</div>
			<div class="rightbox1">
				<div class="btnbox">
					<ul>
						<li><%--
							<button class="HoverIcon active">
								<i class="icon-layer-group"></i>
								레이어
							</button>--%>
							<button class="HoverIcon" onclick="currentLocation();">
								<i class="icon-location-crosshairs"></i>
								위치
							</button>
						</li>
						<li>
							<button class="HoverIcon" onclick="handleZoomInClick();">
								<i class="icon-plus"></i>
								확대
							</button>
							<button class="HoverIcon" onclick="handleZoomOutClick();">
								<i class="icon-minus"></i>
								축소
							</button>
						</li>
						<li>
							<button class="HoverIcon LineString" onclick="addInteraction('LineString');">
								<i class="icon-plus-ruler"></i>
								거리
							</button>
							<button class="HoverIcon Polygon" onclick="addInteraction('Polygon');">
								<i class="icon-vector"></i>
								면적
							</button>
						</li>
					</ul>
				</div>
			</div>
		</div>
	<!-- sub-GIS >> -->
	</div>
	<div id="popup" class="ol-popup">
	      <div id="popup-content"></div>
    </div>
    
    <div id="divLoading" style="position: fixed; left: 0; top: 0; width: 100%; height: 100%; background-color: black; opacity: .5; display: none;">
		<div style="position: fixed; top:50%; left:50%; margin-left: -21px; margin-top: -21px;">
			<img src="<c:url value='/images/sub/loading_bar.gif' />" alt=""/>
		</div>
	</div>

<%-- ############################# 내용 (종료) ############################# --%>

