<%--
*******************************************************************************
***    명칭: modalEventEntForm.jsp
***    설명: 투자정보관리-참여기업등록 등록/수정 모달팝업 화면
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.06.05      LHB     First Coding.
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

<!-- FORM START -->
<form:form commandName="model" id="registForm" name="registForm" method="post" enctype="multipart/form-data" onsubmit="return false;">
	<form:hidden path="evntNo"	 /><%-- 투자설명회번호 --%>
	<form:hidden path="evntType" /><%-- 투자설명회번호 --%>
	<form:hidden path="mode"	 /><%-- 처리모드 --%>
	
	<div class="w-100">
		<!-- 컨텐츠시작 -->
		<div class="tabmenu2 border-0 m-0 mb-32px rounding-1" style="border-radius: 0;">
			<ul class="">
				<li class="evntType">
					<a href="#" class="<c:if test='${model.evntType eq "INVT"}'>active</c:if>">참여 투자자</a>
				</li>
				<li class="evntType">
					<a href="#" class="<c:if test='${model.evntType eq "ENT" }'>active</c:if>">참여 경영체 사업계획서</a>
				</li>
			</ul>
		</div>
		<div class="box-max-height pt-8px">
			<div class="input-grid-1">
				<div class="row">
					<div class="col-12">
						<div class="form-area-box1">
							<label>행사명</label>
							<div class="ele-icon-box">
								<form:input path="evntNm" style="" readonly="true" />
							</div>
						</div>
					</div>
					<div class="col-12">
						<div class="form-area-box1">
							<label>행사일자</label>
							<div class="ele-icon-box">
								<form:input path="evntBgngYmd" class="" readonly="true" />
								<a href="#" class="icon-calendar"></a>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="box mb-24px" style="max-height: 500px;">
				<div class="eletabletop1" data-v-2eb60c26="">
					<div class="row">
						<div class="col">
							<p class="txt2">
								<i class="icon-note"></i>참여 투자운용사
								<span class="ml-4px text-primary" id="appGridResult-ent"></span>
							</p>
						</div>
						<div class="col element">
							<c:choose>
								<c:when test="${model.evntType eq 'INVT'}">
									<a id="btnRemoveInvt" href="#" class="bs-s btn-black fs-12px h-auto px-4px py-2px"><i class="icon-trash mr-3px"></i>선택한 투자자를 삭제</a>
								</c:when>
								<c:when test="${model.evntType eq 'ENT'}">
									<a id="btnRemoveEnt"  href="#" class="bs-s btn-black fs-12px h-auto px-4px py-2px"><i class="icon-trash mr-3px"></i>선택한 경영체를 삭제</a>
								</c:when>
							</c:choose>
						</div>
					</div>
				</div>
				<div class="box-in-box pb-16px">
					<div class="overtable">
						<div class="" style="height: 300px;">
							<!-- 목록 컨텐츠 영역 -->
							<table id="appGrid-ent" class="easyui-datagrid"></table>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<!-- 컨텐츠종료 -->
		<div class="bottom-btn-box">
			<div class="row">
				<div class="col">
					<c:choose>
						<c:when test="${model.evntType eq 'INVT'}">
							<button type="button" id="btnAddInvt" class="btn-primary w-100 mt-20px" data-bs-toggle="modal" data-bs-target="#myModal1">
								<i class="fs-18px icon-plus mr-3px"></i>참여 투자자 추가하기
							</button>
						</c:when>
						<c:when test="${model.evntType eq 'ENT'}">
							<!-- <button type="button" id="btnAddEnt"  class="btn-primary w-100 mt-20px" data-bs-toggle="modal" data-bs-target="#myModal2"> -->
							<button type="button" id="btnAddEnt"  class="btn-primary w-100 mt-20px">
								<i class="fs-18px icon-plus mr-3px"></i>참여 경영체 추가하기
							</button>
						</c:when>
					</c:choose>
				</div>
			</div>
		</div>
	</div>
</form:form>
<!-- FORM END -->

<!-- MODAL CONTENT START << --------->
<!-- 참여투자자 Modal -->
<form id="modalForm1" name="modalForm1" method="post" onsubmit="return false;">
	<div style="display: none;" class="hidden-div">
	</div>
	<div class="modal fade" id="myModal1" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog custom-modal design1" role="document">
			<div class="modal-content" style="width: 531px;">
				<div class="modal-header">
					<p class="txt1">
						<i class="icon-copy-alt"></i>참여 투자자 등록
						<button type="button" class="close" data-bs-dismiss="modal" aria-label="Close"><i class="icon-times"></i></button>
					</p>
				</div>
				<div class="modal-body">
					<div class="input-grid-2">
						<div class="row">
							<div class="col">
								<div class="form-area-box1">
									<div class="input-btn">
										<div class="row">
											<div class="col">
												<div class="ele-icon-box">
													<input type="text" name="srchTextInvt" id="srchTextInvt" placeholder="내용입력">
												</div>
											</div>
											<div class="col box-btn">
												<button id="btnSearchInvt" class="btn-combi-1 bs-m">검색</button>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="input-grid-2">
						<div class="row">
							<div class="col">
								<div class="form-area-box">
									<div style="height: 300px;">
										<table id="appGrid-invt" class="easyui-datagrid"></table>
									</div>
								</div>
							</div>
						</div>
					</div>
					<!-- 
					<div class="box-in-box mt-16px" >
						<div class="table-box-1">
							<table class="">
								<colgroup>
									<col width="60px"/>
									<col width="*"/>
									<col width="*"/>
									<col width="*"/>
								</colgroup>
								<thead class="bs-1 ts-s">
									<tr class="">
										<th>
											<div class="check-radio-box">
												<input type="checkbox" name="" id="">
												<label for=""></label>
											</div>
										</th>
										<th>소속(운용사) </th>
										<th>투자자명(ID)</th>
										<th>연락처</th>
									</tr>
								</thead>
								<tbody class="bs-1 ts-s t-t-c">
									<tr>
										<td>
											<div class="check-radio-box">
												<input type="checkbox" name="" id="">
												<label for=""></label>
											</div>
										</td>
										<td>소속(운용사) </td>
										<td>투자자명(ID)</td>
										<td>연락처</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					 -->
				</div>
				<div class="modal-footer">
					<div class="bottom-btn-box mt-24px">
						<div class="row">
							<div class="col">
								<a href="" class="btn-combi-3 bs-m " data-bs-dismiss="modal"><i class="icon-times"></i>취소</a>
							</div>
							<div class="col">
								<a id="btnSaveInvt" href="" class="btn-primary bs-m"><i class="icon-floppy-disk"></i> 등록</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
</div>
</form>

<!-- 참여 경영체 Modal -->
<form id="modalForm2" name="modalForm2" method="post" onsubmit="return false;">
	<input type="hidden" name="evntType" id="evntType" value="ENT" />
	<input type="hidden" name="evntNo"   id="evntNo"               />
	<input type="hidden" name="mode"     id="mode"     value="I"   />
	<div class="modal fade" id="myModal2" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog custom-modal design1" role="document">
			<div class="modal-content" style="width: 531px;">
				<div class="modal-header">
					<p class="txt1">
						<i class="icon-copy-alt"></i>참여 경영체 사업계획서 등록
						<button type="button" class="close" data-bs-dismiss="modal" aria-label="Close"><i class="icon-times"></i></button>
					</p>
				</div>
				<div class="modal-body">
					<div class="input-grid-2">
						<div class="row">
							<div class="col-6">
								<div class="form-area-box">
									<label>기업명<em></em></label>
									<div class="ele-icon-box">
										<input type="text" name="bzentyNm" id="bzentyNm" style="" maxLength="100">
									</div>
								</div>
							</div>
							<div class="col-6">
								<div class="form-area-box">
									<label>사업자번호</label>
									<input type="hidden" name="brno" id="brno" />
									<div class="day">
										<div class="row">
											<div class="col">
												<div class="ele-icon-box">
													<input type="text" name="brno1" id="brno1" maxLength="3" placeholder="" class="">
												</div>
											</div>
											<div class="col wave">
												<span>-</span>
											</div>
											<div class="col">
												<div class="ele-icon-box">
													<input type="text" name="brno2" id="brno2" maxLength="2" placeholder="" class="">
												</div>
											</div>
											<div class="col wave">
												<span>-</span>
											</div>
											<div class="col">
												<div class="ele-icon-box">
													<input type="text" name="brno3" id="brno3" maxLength="5" placeholder="" class="">
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-4">
								<div class="form-area-box">
									<label>대표자<em></em></label>
									<div class="ele-icon-box">
										<input type="text" name="rprsvNm" id="rprsvNm" style="" maxLength="20">
									</div>
								</div>
							</div>
							<div class="col-8">
								<div class="form-area-box">
									<label>연락처<em></em></label>
									<input type="hidden" name="telno" id="telno" />
									<div class="day">
										<div class="row">
											<div class="col">
												<div class="ele-icon-box">
													<input type="text" name="telno1" id="telno1" maxLength="3" placeholder="" class="">
												</div>
											</div>
											<div class="col wave">
												<span>-</span>
											</div>
											<div class="col">
												<div class="ele-icon-box">
													<input type="text" name="telno2" id="telno2" maxLength="4" placeholder="" class="">
												</div>
											</div>
											<div class="col wave">
												<span>-</span>
											</div>
											<div class="col">
												<div class="ele-icon-box">
													<input type="text" name="telno3" id="telno3" maxLength="4" placeholder="" class="">
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-12">
								<div class="form-area-box">
									<label>이메일<em></em></label>
									<div class="ele-icon-box">
										<input type="text" name="emlAddr" id="emlAddr" style="">
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-12">
								<div class="form-area-box">
									<label>주요사업내용<em></em></label>
									<div class="ele-icon-box">
										<textarea name="mainBizCn" id="mainBizCn" class="" style="height: 78px;"></textarea>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-12">
								<div class="form-area-box">
									<label class="">첨부파일<em></em></label>
									<div class="day pt-16px">
										<div class="input-btn" id="attachFile"></div>
									</div>
								</div>
							</div>
							<div class="col-12">
								<div class="form-area-box">
									<label class="">썸네일<em></em></label>
									<div class="day pt-16px">
										<div class="input-btn" id="attachFile2"></div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<div class="bottom-btn-box">
						<div class="row">
							<div class="col">
								<a id="btnCloseEnt"  href="" class="btn-combi-3 bs-m " data-bs-dismiss="modal"><i class="icon-times"></i>취소</a>
							</div>
							<div class="col">
								<a id="btnSaveEnt" href="" class="btn-primary bs-m"><i class="icon-floppy-disk"></i>등록</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</form>
<!-- MODAL CONTENT END << ----------->

<%-- ############################ 내용 (종료) ############################ --%>
