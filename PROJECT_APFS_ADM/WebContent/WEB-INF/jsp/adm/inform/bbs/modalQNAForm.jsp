<%--
*******************************************************************************
***    명칭: modalQNAForm.jsp
***    설명: 운영관리-공지사항 화면
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.04.04    LSH        First Coding.
***    1.1      2023.05.03    LSH        디자인 적용
***    1.2      2023.06.02    JH         모달팝업 형태로 변경
***    1.3      2023.06.27    JH         작업 완료.
*******************************************************************************
--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ taglib prefix="tiles"  uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="app"    uri="/WEB-INF/tld/app.tld"%>
<%@ taglib prefix="f"      uri="/WEB-INF/tld/f.tld"%>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"    uri="http://www.springframework.org/security/tags"%>

<%-- ############################ 내용 (시작) ############################ --%>

<div class="w-100">
	<div class="box-max-height">
		<!-- CENTER CONTENT START << ---------->
		<form:form commandName="modelUsr" id="selectUsrForm" name="selectUsrForm" method="post" enctype="multipart/form-data" onsubmit="return false;">
			<form:hidden path="pstNo"          /><%-- 게시물번호 --%>
			<form:hidden path="bbsSeCd"        /><%-- 게시물구분 --%>
			<form:hidden path="mode"           /><%-- 처리모드 --%>
			<form:hidden path="pstClsfCd"      /><%-- 문의유형 --%>
			<form:hidden path="isNext"         /><%-- 다음페이지 답글여부 --%>
			<form:hidden path="isBefore"       /><%-- 이전페이지 답글여부--%>
			
			<div class="box mb-24px">
				<!-- CENTER CONTENT START << ---------->
				<div class="eletabletop1" data-v-2eb60c26="">
					<div class="row">
						<div class="col">
							<p class="txt2"><i class="icon-note"></i> [<c:out value="${modelUsr.inqryTypeNm}" />] <c:out value="${modelUsr.pstTtl}" /></p>
						</div>
					</div>
				</div>
				<div class="box-in-box input-grid-1 pb-16px">
					<div class="row">
						<div class="col-4">
							<div class="form-area-box-input">
								<label>작성자</label>
								<div class="ele-icon-box">
									<div class="value-box">
										<c:out value="${modelUsr.rgtrNm}" />
									</div>
								</div>
							</div>
						</div>
						<div class="col-4">
							<div class="form-area-box-input">
								<label>등록일</label>
								<div class="ele-icon-box">
									<div class="value-box">
										<c:out value="${modelUsr.regDate}" />
									</div>
								</div>
							</div>
						</div>
						<div class="col-4">
							<div class="form-area-box-input">
								<label>상태</label>
								<div class="ele-icon-box">
									<div class="value-box">
										<c:out value="${modelUsr.prcsSttsCd}" />
									</div>
								</div>
							</div>
						</div>
						<div class="col-12">
							<div class="form-area-box-input">
								<label>첨부파일</label>
								<div class="ele-icon-box">
									<%-- 첨부파일영역 --%>
									<div id="attachFile"></div>
								</div>
							</div>
						</div>
						<div class="col-12">
							<div class="form-area-box-input">
								<label>내용</label>
								<div class="ele-icon-box h-auto">
									<div style="min-height: 100px" class="ckEdit">
										<c:out value='${modelUsr.pstCn}' escapeXml="false" />
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</form:form>
		<!-- 사용자작성글 viewQNA -->

		<form:form commandName="modelAdm" id="registerAdmForm" name="registerAdmForm" method="post" onsubmit="return false;">
			<form:hidden path="pstNo" /><%-- 게시물번호 --%>
			<form:hidden path="bbsSeCd" /><%-- 게시물구분 --%>
			<form:hidden path="mode" /><%-- 처리모드 --%>
			<div class="box ">

				<div class="eletabletop1" data-v-2eb60c26="">
					<div class="row">
						<div class="col">
							<p class="txt2">
								<i class="icon-note"></i>관리자 답변
							</p>
						</div>
					</div>
				</div>

				<div class="box-in-box input-grid-1 pb-16px">
					<div class="row">
						<div class="col-12">
							<div class="form-area-box1">
								<label>내용<em></em></label>
								<div class="ele-icon-box h-auto">
									<form:textarea path="pstCn" style="height:400px;" />
								</div>
							</div>
						</div>
					</div>
				</div>

			</div>
		</form:form>
	</div>
</div>
<!-- 컨텐츠 종료 -->

<!-- 버튼영역 -->
<div class="bottom-btn-box">
	<div class="row">
		<div class="col flex-grow-0"><a id="btnBefore" href="javascript:void(0)" class="bs-l btn-combi-3"><i class="icon-arrow-left"></i> 이전</a></div>
		<div class="col flex-grow-0"><a id="btnNext" href="javascript:void(0)" class="bs-l btn-combi-3"><i class="icon-arrow-right"></i> 다음</a></div>
		<div class="col flex-grow-1"></div>
		<div class="col flex-grow-0"><a id="btnCancel" href="javascript:void(0)" class="bs-l btn-black"><i class="icon-times"></i> 취소</a></div>
		<div class="col flex-grow-0"><a id="btnSave" href="javascript:void(0)" class="bs-l btn-primary"><i class="icon-edit"></i> 저장</a></div>
	</div>
</div>
<!-- CENTER CONTENT END   >> ---------->

<%-- ############################ 내용 (종료) ############################ --%>

