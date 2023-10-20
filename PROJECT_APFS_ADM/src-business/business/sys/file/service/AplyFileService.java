package business.sys.file.service;

import java.util.List;

import commf.paging.PaginatedArrayList;
import common.file.FileModel;
import common.user.UserInfo;

/**
 * [서비스인터페이스] - 신청첨부파일 Service Interface
 *
 * @class   : AplyFileService
 * @author  : LSH
 * @since   : 2021.10.07
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@SuppressWarnings("all")
public interface AplyFileService {

    /**
     * 신청첨부파일 페이징목록 조회
     */
    public PaginatedArrayList listAplyFile(AplyFileVO aplyFileVO, int currPage, int pageSize) throws Exception;

    /**
     * 신청첨부파일 목록조회
     */
    public List listAplyFile(AplyFileVO aplyFileVO) throws Exception;

    /**
     * 신청첨부파일 상세조회
     */
    public AplyFileVO viewAplyFile(String sn) throws Exception;

    /**
     * 신청첨부파일 등록,수정,삭제
     */
    public String saveAplyFile(AplyFileVO aplyFileVO) throws Exception;
    
    /**
     * 신청정보에서 호출할 파일저장 함수
     */
	public int saveAplyFile(FileModel model, List<AplyFileVO> saveFiles, List<AplyFileVO> removeFiles, UserInfo userInfo) throws Exception;
}