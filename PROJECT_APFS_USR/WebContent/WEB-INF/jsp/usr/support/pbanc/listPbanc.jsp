<%--
*******************************************************************************
***    명칭: listPbanc.jsp
***    설명: 지원서비스 - 지원사업 통합검색 화면
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.04.30    LSH        First Coding.
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

<script type="text/javascript">
	SetSearch({
		page          : '<c:out value="${search.page         }"/>',
		showMode      : '<c:out value="${search.showMode     }"/>',
		ordrField     : '<c:out value="${search.ordrField    }"/>',
		srchText      : '<c:out value="${search.srchText     }"/>',
		exPrgrsCd     : '<c:out value="${search.exPrgrsCd    }"/>',
		prgrsSttsCd   : '<c:out value="${search.prgrsSttsCd  }"/>',
		sprtFldCd     : '<c:out value="${search.sprtFldCd    }"/>',
		bizFld        : '<c:out value="${search.bizFld       }"/>',
		bizTrgt       : '<c:out value="${search.bizTrgt      }"/>',
		bizTrgtAge    : '<c:out value="${search.bizTrgtAge   }"/>',
		bizTrgtFntnPd : '<c:out value="${search.bizTrgtFntnPd}"/>',
		rcptSeCd      : '<c:out value="${search.rcptSeCd     }"/>',
		rcptMthdCd    : '<c:out value="${search.rcptMthdCd   }"/>',
		pbancSttsCd   : '<c:out value="${search.pbancSttsCd  }"/>',
	});
</script>

<form id="searchForm" name="searchForm" method="post" onsubmit="return false;">

	<!-- 목록검색결과 영역 -->
	<div id="appGridResult" class="tit-box-2 mb-16px"></div>
	
	<!-- 목록툴바 영역 -->
	<div class="layerbox mb-16px">
		<div id="appGridToolbar" ></div>
		<div id="appGridExtrbar" ></div>
	</div>
</form>

<!-- 목록 영역 -->
<div id="appGridPanel">
	<div class="shadow-box-1 p-24px">
		<!-- 목록 컨텐츠 영역 -->
		<div id="appGrid"></div>
		<!-- 목록 페이징 영역 -->
		<div class="mb-0 paging-box pb-24px">
			<ul id="appGridPagination"></ul>
		</div>
	</div>
</div>

<%-- ############################ 내용 (종료) ############################ --%>
