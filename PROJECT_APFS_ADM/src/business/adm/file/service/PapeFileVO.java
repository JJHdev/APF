package business.adm.file.service;

import java.util.ArrayList;
import java.util.List;

import business.adm.CodeConst;
import business.adm.invest.service.EntVO;
import business.adm.invest.service.EventVO;
import business.adm.invest.service.FundVO;
import business.adm.support.service.PbancVO;
import business.adm.support.service.SupportVO;
import business.com.CommConst;
import business.com.common.service.FileVO;
import common.base.BaseModel;
import common.file.FileDirectory;
import common.file.FileInfo;
import common.util.CommUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * [VO클래스] - 업무첨부파일 모델 클래스
 *
 * @class   : PapeFileVO
 * @author 	: JH
 * @since 	: 2023.08.04
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
public class PapeFileVO extends BaseModel implements FileVO {
    // 일련번호
    private Long sn;
    // 업무구분코드
    private String taskSeCd;
    // 문서번호
    private String docNo;
    // 세부문서번호
    private String dtlDocNo;
    // 파일구분코드
    private String fileSeCd;
    // 파일경로
    private String filePath;
    // 저장파일명
    private String strgFileNm;
    // 파일명
    private String fileNm;
    // 파일크기
    private Long fileSz;
    // 처리상태코드
    private String prcsSttsCd;
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
    
    // 세션사용자번호
    private String gsUserNo;
    // 세션사용자권한
    private String gsRoleId;
    
    // IR자료 제출여부 (화면변수)
    private String sbmtYn;
    
    // 임시파일여부
    private String tempYn;

    // 서류코드정보
    //---------------------------------
    // 서류코드
    private String dcmntCd;
    private String dcmntNm;
    // 상위서류코드
    private String upDcmntCd;
    private String upDcmntNm;
    // 서류주석명
    private String dcmntCmNm;
    // 서류내용
    private String dcmntCn;
   
    // 제한수
    private Integer lmtCnt;
    // 다운로드수
    private Integer dwnldCnt;
    // 다운로드대상여부
    private String dwnldTrgtYn;
    // 사용여부
    private String useYn;
    
    // 세부구분코드
    private String dtlSeCd;
    // 신청구분코드
    private String aplySeCd;
    // 필수여부
    private String esntlYn;
    // 양식파일 유무
    private String formYn;
    // 순서
    private Long cdOrdr;
    // 상위서류코드 (수정시 PK조건)
    private String orgUpDcmntCd;
    // 사용 상태 값
    private String state;
    private String rgtrNm;
    // 수정자번호
    private String mdfrNm;
	// 검색조건
    private String srchType;
    private String srchText;
    private String srchUseYn;
    private String gsUserNm;
    
    // 파일목록
    private List<PapeFileVO> files;

    public void addFiles(PapeFileVO fileVO) {
    	if (files == null)
    		files = new ArrayList<PapeFileVO> ();
    	files.add(fileVO);
    }
    
	@Override
	public FileDirectory getFileDirectory() {
		return FileDirectory.BIZ;
	}
    
	// FileInfo 객체 생성 반환
	@Override
	public FileInfo getFileInfo() {
		FileInfo f = FileInfo.builder()
				.fullPath(getFileDirectory().getRealPath(getFilePath()))
				.filePath(getFilePath())
				.saveName(getStrgFileNm())
				.fileName(getFileNm())
				.docuNo  (getDocNo())
				.docuCd  (getTaskSeCd())
				.docuSeq (getDtlDocNo())
				.fileSe  (getFileSeCd())
				.papeCd  (getDcmntCd())
				.sttsCd  (getPrcsSttsCd())
				.docuCn  (getPrcsCn())
				.docuYn  (getSbmtYn())
				.build();
        // 임시경로인 경우
        if (CommConst.YES.equals(getTempYn())) {
        	f.setFullPath(getFileDirectory().getTempDir());
        }
		return f;
	}

	@Override
	public String getUserNo() {
		if (getUserInfo() == null)
			return null;
		return getUserInfo().getUserNo();
	}

	@Override
	public String getRoleId() {
		if (getUserInfo() == null)
			return null;
		return getUserInfo().getRoleId();
	}

	@Override
	public void setFileInfo(FileInfo fileInfo) {
		setFilePath  (fileInfo.getFilePath());
		setFileNm    (fileInfo.getFileName());
		setStrgFileNm(fileInfo.getSaveName());
		setFileSz    (fileInfo.getFileSize());
		
		if (CommUtils.isNotEmpty(fileInfo.getDocuNo()))
			setDocNo      (fileInfo.getDocuNo());
		if (CommUtils.isNotEmpty(fileInfo.getDocuSeq()))
			setDtlDocNo   (fileInfo.getDocuSeq());
		if (CommUtils.isNotEmpty(fileInfo.getDocuCd()))
			setTaskSeCd   (fileInfo.getDocuCd());
		if (CommUtils.isNotEmpty(fileInfo.getFileSe()))
			setFileSeCd   (fileInfo.getFileSe());
		if (CommUtils.isNotEmpty(fileInfo.getPapeCd()))
			setDcmntCd    (fileInfo.getPapeCd());
		if (CommUtils.isNotEmpty(fileInfo.getSttsCd()))
			setPrcsSttsCd (fileInfo.getSttsCd());
		if (CommUtils.isNotEmpty(fileInfo.getDocuCn()))
			setPrcsCn     (fileInfo.getDocuCn());
		if (CommUtils.isNotEmpty(fileInfo.getDocuYn()))
			setSbmtYn     (fileInfo.getDocuYn());
		
		if (CommUtils.isNotEmpty(fileInfo.getFileNo()))
			setSn (new Long(fileInfo.getFileNo()));
	}
	
	/**
	 * 각 VO별로 연결KEY와 파일경로를 정의한다.
	 */
	public void buildPath(BaseModel modelVO) {
		
        // 첨부파일 경로
        FileDirectory d = getFileDirectory();
        // 세션사용자
        String gsUserNo = modelVO.getUserInfo().getGsUserNo();
		// 파일경로
		StringBuilder filePath = new StringBuilder(d.getPath());
    	/**
    	 * 업무구분별 파일경로 정의
    	 * 각 업무별로 파일경로가 상이하며 규칙에 따라 변경해야 한다.
    	 */
		switch (getTaskSeCd()) {
			// TODO 펀드정보 파일경로 : /biz/업무구분/문서번호
			case CodeConst.TASK_FUND :
				FundVO fundVO = (FundVO)modelVO;
		        setDocNo   (fundVO.getFundNo());
				
				filePath.append("/"+getTaskSeCd());
		        filePath.append("/"+getDocNo   ());
				
				break;
			// TODO 기업IR정보 파일경로 : /biz/업무구분/문서번호
			case CodeConst.TASK_BZIR : 
				EntVO entVO = (EntVO)modelVO;
				setDocNo   (entVO.getIrNo    ());
				setDtlDocNo(entVO.getBzentyNo());

				filePath.append("/"+getTaskSeCd());
		        filePath.append("/"+getDocNo   ());
				break;
			// TODO 사업공고 파일경로 : /biz/업무구분/문서번호
			case CodeConst.TASK_PBNC : 
				PbancVO pbancVO = (PbancVO)modelVO;
		        setDocNo   (pbancVO.getBizPbancNo());
				
		        filePath.append("/"+getTaskSeCd());
		        filePath.append("/"+getDocNo   ());
				break;
			// TODO 투자지원신청 파일경로 : /biz/업무구분/문서번호
			case CodeConst.TASK_SPRT : 
				SupportVO sprtVO = (SupportVO)modelVO;
		        setDocNo   (sprtVO.getSprtAplyNo());
		        setDtlDocNo(sprtVO.getAplyBzentyNo());
				
		        filePath.append("/"+getTaskSeCd());
		        filePath.append("/"+getDocNo   ());
				break;
			// TODO 행사참여경영체 파일경로 : /biz/업무구분/문서번호
			case CodeConst.TASK_EVNT : 
				EventVO eventVO = (EventVO)modelVO;
		        setDocNo   (eventVO.getEvntNo());

		        filePath.append("/"+getTaskSeCd());
		        filePath.append("/"+getDocNo   ());
				break;
		}
		// 임시경로에 업로드된 파일이면
		if ("Y".equals(getTempYn())) {
			// 파일경로정의
			setFilePath(filePath.toString());
		}
		// 기업로드된 파일이면
		else {
			// 등록자번호 맵핑
			setRgtrNo(gsUserNo);
		}
	}
	
    
	// FileInfo 객체 생성 반환
	public FileInfo getPapeCodeFileInfo() {
		FileInfo f = FileInfo.builder()
				.fullPath(getFileDirectory().getRealPath(getFilePath()))
				.filePath(getFilePath())
				.saveName(getStrgFileNm())
				.fileName(getFileNm())
				.docuNo  (getDocNo())
				.docuCd  (getTaskSeCd())
				.docuSeq (getDtlDocNo())
				.fileSe  (getFileSeCd())
				.papeCd  (getDcmntCd())
				.sttsCd  (getPrcsSttsCd())
				.docuCn  (getPrcsCn())
				.docuYn  (getSbmtYn())
				.fileNo  (getFileNo())
				.build();
        // 임시경로인 경우
        if (CommConst.YES.equals(getTempYn())) {
        	f.setFullPath(getFileDirectory().getTempDir());
        }
		return f;
	}
	@Override
	public String getFileNo() {
		return String.valueOf(sn);
	}

	@Override
	public String getDocuCd() {
		return taskSeCd;
	}

	@Override
	public String getDocuNo() {
		return docNo;
	}

	@Override
	public String getDocuSeq() {
		return dtlDocNo;
	}

	@Override
	public String getFileSe() {
		return fileSeCd;
	}

	@Override
	public String getPapeCd() {
		return dcmntCd;
	}

	@Override
	public String getSttsCd() {
		return prcsSttsCd;
	}

	@Override
	public String getDocuCn() {
		return prcsCn;
	}

	@Override
	public String getDocuYn() {
		return sbmtYn;
	}

	@Override
	public void setFileNo(String fileNo) {
		this.sn = CommUtils.getLong(fileNo, 0);
	}

	@Override
	public void setDocuCd(String docuCd) {
		this.taskSeCd = docuCd;
	}

	@Override
	public void setDocuNo(String docuNo) {
		this.docNo = docuNo;
	}

	@Override
	public void setDocuSeq(String docuSeq) {
		this.dtlDocNo = docuSeq;
	}

	@Override
	public void setFileSe(String fileSe) {
		this.fileSeCd = fileSe;
	}

	@Override
	public void setPapeCd(String papeCd) {
		this.dcmntCd = papeCd;
	}

	@Override
	public void setSttsCd(String sttsCd) {
		this.prcsSttsCd = sttsCd;
	}

	@Override
	public void setDocuCn(String docuCn) {
		this.prcsCn = docuCn;
	}

	@Override
	public void setDocuYn(String docuYn) {
		this.sbmtYn = docuYn;
	}
}
