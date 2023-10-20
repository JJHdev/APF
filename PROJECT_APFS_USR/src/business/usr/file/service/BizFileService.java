package business.usr.file.service;

import java.util.List;

import common.base.BaseModel;
import common.file.FileInfo;

/**
 * [서비스인터페이스] - 업무첨부파일 Service Interface
 * 
 * @class   : BizFileService
 * @author  : LSH
 * @since   : 2023.04.23
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@SuppressWarnings("all")
public interface BizFileService {

    /**
     * 업무첨부파일 목록조회
     */
    public List listBizFile(BizFileVO bizFileVO) throws Exception;

    /**
     * 업무첨부파일 상세조회
     */
    public BizFileVO viewBizFile(Long sn) throws Exception;

    /**
     * 2023.09.12 LSH
     * 업무첨부파일 상세조회
     */
    public BizFileVO viewBizFileByParams(BizFileVO bizFileVO) throws Exception;
    
    /**
     * 업무첨부파일 단일삭제
     */
    public String deltBizFile(BizFileVO bizFileVO) throws Exception;

    /**
     * 업무첨부파일 등록,수정,삭제
     */
    public void saveBizFile(BizFileVO bizFileVO, List<FileInfo> saveFiles) throws Exception;

    /**
     * 서류코드맵핑이 필요한 업무첨부파일 임시저장처리
     */
    public BizFileVO saveTempFile(FileInfo fileInfo) throws Exception;
    /**
     * 서류코드맵핑이 필요한 업무첨부파일 실제저장처리
     * - 임시경로의 파일을 실제 경로로 이동한다.
     * - 파일의 업무연결 KEY를 맵핑한다.
     */
    public int savePapeFile(BaseModel modelVO, List<BizFileVO> saveFiles, List<BizFileVO> removeFiles) throws Exception;

    /**
     * 서류양식 목록 조회
     */
    public List listPape(BizFileVO bizFileVO) throws Exception;
    
    /**
     * 서류양식그룹 목록 조회
     */
    public List listPapeGroup(BizFileVO bizFileVO) throws Exception;

    /**
     * 서류양식 상세 조회
     */
    public BizFileVO viewPape(String dcmntCd) throws Exception;
}