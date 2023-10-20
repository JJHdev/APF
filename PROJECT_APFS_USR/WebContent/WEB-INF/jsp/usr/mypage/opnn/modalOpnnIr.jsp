<%--
*******************************************************************************
***    명칭: modalOpnnIr.jsp
***    설명: 마이페이지 - IR검토의견등록 - 검토의견서 모달
***
***    -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.06.26    KYW        First Coding.
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
<form:form commandName="model" id="modalForm" name="modalForm" method="post" enctype="multipart/form-data" onsubmit="return false;">
 	<form:hidden path="mode" 				/> <%-- 모드			--%>			 
	<form:hidden path="evntNo"				/> <%-- 행사번호		--%>
	<form:hidden path="evntPartcptnNoArr"	/> <%-- 행사참여번호	--%>
	<form:hidden path="page"				/> <%-- 페이지		--%>
	<form:hidden path="srchYr"				/> <%-- 검색년도		--%>
	<form:hidden path="prgrsSttsCd"			/> <%-- 진행상태		--%>
	<form:hidden path="irRgstYn"			/> <%-- 수정가능여부	 --%>
	
	<div class="designbox5 mb-8px">
		<div class="row align-items-center">
			<div class="col-12 col-md input-grid-1">
				<div class="row">
					<div class="col-12">
						<div class="form-area-box-input">
							<label>행사명</label>
							<div class="ele-icon-box">
								<div class="value-box evntNm"></div>
							</div>
							
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-12">
						<div class="form-area-box-input">
							<label>경영체</label>
							<div class="ele-icon-box">
								<div class="value-box bzentyNm"></div>
							</div>
							
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-6">
						<div class="form-area-box-input">
							<label>운용사명</label>
							<div class="ele-icon-box">
								<div class="value-box bzentyInvtNm"></div>
							</div>
							
						</div>
					</div>
					<div class="col-6">
						<div class="form-area-box-input">
							<label>작성자</label>
							<div class="ele-icon-box">
								<div class="value-box rgtrNmNo"></div>
							</div>
							
						</div>
					</div>
				</div>
			</div>
			<div class="col-12 col-md flex-b-md-174px">
				<div class="designbox">
					<p id="pageBox"></p>
					<div class="row">
						<div class="col">
							<button class="btnPrev"><i class="icon-angle-left"></i></button>
						</div>
						<div class="col">
							<button class="btnNext"><i class="icon-angle-right"></i></button>
						</div>
					</div>

				</div>
			</div>
			
			
		</div>
		
	</div>
	<div class=" table-box-1 overflow-hidden">
		<table class="rgstTable">
			<colgroup>
				<col width="162px">

				<col width="*">
				
			</colgroup>
			<thead class="bs-1 ts-m">
				<tr class="px-v-xl">
					<th>검토항목</th>
					<th class="text-start">검토의견</th>
					
				</tr>
			</thead>
			<tbody class="bs-1 px-v-xl ts-s t-t-l">
				<tr class="">													
					<td class="fw-600">BUSINESS</td>
					<td>
						<textarea id="bizCn" name="bizCn" class="value-box bizCn" style="width:100%; border:0; min-height: 80px;" 
							placeholder="(시장) 다양한 기회가 존재하며, 확장 가능성이 있는 목표지상을 보유하고 있는가&#13;&#10;(전략) 목표시장에  진출하여 성과를 창출할 수 있는 구체적 계획 등을 보유하고 있는가&#13;&#10;(수익성) 현재 혹은 미래에 실현가능한 수익모델(매출구조)을 갖고 있는가"></textarea>
						<input type="hidden" name="bizCnArr" />												
					</td>		
				</tr>
				<tr>													
					<td class="fw-600">PRODUCT (TECH)</td>
					<td>
						<textarea id="prdctCn" name="prdctCn" class="value-box prdctCn" style="width:100%; border:0; min-height: 80px;"
							placeholder="(문제인식) 시장의 문제나 고객의 니즈에 대한 분석에 공감할 수 있는가&#13;&#10;(해결방식) 제품이나 기술은 문제나 니즈를 충족시키기에 충분한가&#13;&#10;(경쟁우위) 해결방식을 적용한 제품이나 기술은 고객에게 새로운 가치를 제공하거나 타제품 대비 차별적인 경쟁력을 갖고 있는가"></textarea>
						<input type="hidden" name="prdctCnArr" />												
					</td>		
				</tr>
				<tr>													
					<td class="fw-600">COMPANY</td>
					<td>
						<textarea id="coCn" name="coCn" class="value-box coCn" style="width:100%; border:0; min-height: 80px;"
							placeholder="(인적역량) 제품과 사업을 현실화 할 수 있는 인적역량을 갖고 있는가&#13;&#10;(핵심자산) 특허,기술,인프라,네트워크 등을 보유하고 있는가&#13;&#10;(시장검증) 완성된 제품에 대한 시장이나 고객의 반응은 긍정적인가"></textarea>
						<input type="hidden" name="coCnArr" />												
					</td>		
				</tr>
				<tr>													
					<td class="fw-600">종합의견</td>
					<td>
						<textarea id="gnrlzOpnn" name="gnrlzOpnn" class="value-box gnrlzOpnn" style="width:100%; border:0; min-height: 80px;"
							placeholder="투자관심도를 결정하는데 영향을 미치는 핵심적인 요인 등을 요약"></textarea>
						<input type="hidden" name="gnrlzOpnnArr" />												
					</td>	
				</tr>
				<tr>													
					<td class="fw-600">투자관심도</td>
					<td>
						<div class="row justify-content-between">
							<div class="col">
								<div class="check-radio-box" title="(1수준) 투자에 전혀 관심 없다">
									<input type="radio" class="invtItrstDgreeCd" id="invtItrstDgreeCd1" name="invtItrstDgreeCd" value="1"/>
									<label for="invtItrstDgreeCd1">1</label>
								</div>
							</div>
							<div class="col">
								<div class="check-radio-box"  title="(2수준) 투자에 관심 없다">
									<input type="radio" class="invtItrstDgreeCd" id="invtItrstDgreeCd2" name="invtItrstDgreeCd" value="2" />
									<label for="invtItrstDgreeCd2">2</label>
								</div>
							</div>
							<div class="col">
								<div class="check-radio-box"  title="(3수준) 그저 그렇다">
									<input type="radio" class="invtItrstDgreeCd" id="invtItrstDgreeCd3" name="invtItrstDgreeCd" value="3" />
									<label for="invtItrstDgreeCd3">3</label>
								</div>
							</div>
							<div class="col">
								<div class="check-radio-box"  title="(4수준) 투자하고 싶다">
									<input type="radio" class="invtItrstDgreeCd" id="invtItrstDgreeCd4" name="invtItrstDgreeCd" value="4"  />
									<label for="invtItrstDgreeCd4">4</label>
								</div>
							</div>
							<div class="col">
								<div class="check-radio-box"  title="(5수준) 반드시 투자하고 싶다">
									<input type="radio" class="invtItrstDgreeCd" id="invtItrstDgreeCd5" name="invtItrstDgreeCd" value="5"/>
									<label for="invtItrstDgreeCd5">5</label>
								</div>
							</div>
							<input type="hidden" name="invtItrstDgreeCdArr" />
						</div>
					</td>													
				</tr>
				<tr>													
					<td class="fw-600">후속미팅 의향</td>
					<td>
						<div class="row">
							<div class="col ">
								<div class="check-radio-box" >
									<input type="radio" class="fllwMtgIntenYn" id="fllwMtgIntenYn1" name="fllwMtgIntenYn" value="Y"  />
									<label for="fllwMtgIntenYn1">Yes</label>
								</div>
							</div>
							<div class="col ">
								<div class="check-radio-box">
									<input type="radio" class="fllwMtgIntenYn" id="fllwMtgIntenYn2" name="fllwMtgIntenYn" value="N"  />
									<label for="fllwMtgIntenYn2">No</label>
								</div>
							</div>
							<div class="col "></div>
							<div class="col "></div>
							<div class="col "></div>
							<input type="hidden" name="fllwMtgIntenYnArr" />
						</div>
					</td>													
				</tr>

			</tbody>
		</table>
	</div><div class="bottom-box-btn py-24px">
		<div class="row">
			<div class="col btnForInsert" id="btnRgstTempOpnn">
				<a href="#" class="bs-m btn-combi-3 w-100"><i class="icon-floppy-disk"></i> 임시저장</a>
			</div>
			<div class="col">
				<a href="#" class="bs-m btn-primary-ouline w-100  btnPrev"><i class="icon-angle-left"></i> 이전</a>
			</div>
			<div class="col">
				<a href="#" class="bs-m btn-primary-ouline w-100 btnNext" id="btnNextBtm">다음 <i class="icon-angle-right"></i></a>
			</div>
			<div class="col d-none btnForInsert" id="btnRgstOpnn">
				<a href="#" class="bs-m btn-primary w-100" onclick=""><i class="icon-edit"></i> 등록</a>
			</div>
			<div class="col d-none btnForView" id="btnMdfyOpnn">
				<a href="#" class="bs-m btn-primary w-100" onclick=""><i class="icon-edit"></i> 수정</a>
			</div>
		</div>
	</div>
</form:form>

<%-- ############################# 내용 (종료) ############################# --%>

