package business.sys.file.service;

import common.base.BaseModel;
import common.file.FileDirectory;
import common.util.CommUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * [VO클래스] - 신청첨부파일 모델 클래스
 *
 * @class   : AplyFileVO
 * @author  : LSH
 * @since   : 2021.10.07
 * @version : 1.0
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
public class AplyFileVO extends BaseModel {

    // 일련번호
    private String sn;
    // 이전일련번호
    private String orgSn;
    // 업무구분코드
    private String dtySeCd;
    // 문서번호
    private String dcmtNo;
    // 세부문서번호
    private String dtlDcmtNo;
    // 상위서류코드
    private String upPapeCd;
    private String upPapeNm;
    // 서류코드
    private String papeCd;
    private String papeNm;
    // 파일경로
    private String filePath;
    // 저장파일명
    private String strgNm;
    // 파일명
    private String fileNm;
    // 파일크기
    private String fileSz;
    // 처리상태코드
    private String prcsStusCd;
    private String prcsStusNm;
    // 처리내용
    private String prcsCn;
    // 삭제여부
    private String delYn;
    // 등록자번호
    private String rgtrNo;
    // 등록일시
    private String regDttm;
    // 등록일자
    private String regDate;
    // 수정자번호
    private String mdfrNo;
    // 수정일시
    private String mdfDttm;
    // 수정일자
    private String mdfDate;

	// 검색조건
    private String srchType;
    private String srchText;

    // 세션사용자번호
    private String gsUserNo;

    private int fileIdx;

    // 임시파일여부
    private String tempYn;
    // 권한확인을 위한 식별ID
    private String idntfcId;

    // 다중 sn
    // 2021.12.10 CSLEE
    private String[] sns;

    // 검색 조건
    private String srchDtySeCd;     // 업무구분
    private String srchUpPapeCd;    // 상위 서류분류
    private String srchPapeCd;      // 하위 서류분류
    private String srchDcmtNo;      // 접수번호
    private String srchRcptStdt;    // 접수일자 (시작일)
    private String srchRcptEndt;    // 접수일자 (종료일)
    private String srchBizAreaCd;   // 피해지역 (구제급여)
    private String srchEtcDmgeArea; // 피해지역 (취약계층 소송지원)
    private String srchIdntfcId;    // 식별아이디(구제급여)

    // 2022.12.06 관리자등록여부
    private String mngrRegYn;
    
    /**
     * 2023.01.31 LSH 파일경로를 포함한 전체 명칭을 반환한다.
     */
    public String getRealFilePath(FileDirectory d) {
        // 임시경로인 경우
        if (CommUtils.isEmpty(getDcmtNo())) {
            return d.getTempDir();
        } else {
            return d.getRealPath(getFilePath());
        }
    }
}
