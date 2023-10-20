package business.sys.file.service;

import java.util.List;

import common.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * [VO클래스] - 서류코드관리 모델 클래스
 *
 * @class   : PapeCodeVO
 * @author : JH
 * @since : 2023.08.04
 * @version : 1.2
 *
 *   수정일     수정자              수정내용
 *  -------    --------    ---------------------------
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class PapeCodeVO extends BaseModel {
    // 서류코드
    private String dcmntCd;
    // 서류명
    private String dcmntNm;
    // 순서
    private Long cdOrdr;
    // 상위서류코드
    private String upDcmntCd;
    // 상위서류코드 (수정시 PK조건)
    private String orgUpDcmntCd;
    // 상위서류명
    private String upDcmntNm;
    // 제한수
    private String lmtCnt;
    // 파일명
    private String fileNm;
    // 파일경로
    private String filePath;
    // 서류주석명
    private String dcmntCmNm;
    // 서류내용
    private String dcmntCn;
    // 다운로드수
    private String dwnldCnt;
    // 다운로드 대상여부
    private String dwnldTrgtYn;
    // 사용 상태 값
    private String state;
    // 사용여부
    private String useYn;
    // 등록자번호
    private String rgtrNo;
    private String rgtrNm;
    // 등록일시
    private String regDttm;
    // 등록일자
    private String regDate;
    // 수정자번호
    private String mdfrNo;
    private String mdfrNm;
    // 수정일시
    private String mdfDttm;
    // 수정일자
    private String mdfDate;
	// 검색조건
    private String srchType;
    private String srchText;
    private String srchUseYn;
    // 세션사용자번호
    private String gsUserNo;
    private String gsUserNm;
    // 세션사용자권한
    private String gsRoleId;
    
    @SuppressWarnings("rawtypes")
	private List files;
}
