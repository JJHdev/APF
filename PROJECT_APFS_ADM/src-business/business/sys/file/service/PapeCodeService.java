package business.sys.file.service;

import java.util.List;

import business.sys.code.service.CodeVO;
import commf.paging.PaginatedArrayList;
import common.file.FileInfo;

/**
 * [서비스인터페이스] - 서류코드관리 Service Interface
 *
 * @class   : PapeCodeService
 * @author : JH
 * @since : 2023.08.04
 * @version : 1.2
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@SuppressWarnings("all")
public interface PapeCodeService {
    /**
     * 서류코드관리 목록조회
     */
    public List listPapeCode(PapeCodeVO papeCodeVO) throws Exception;
    /**
     * 코드 페이징리스트 조회
     */
    public PaginatedArrayList listPapeCode(PapeCodeVO papeCodeVO, int currPage, int pageSize) throws Exception;


    /**
     * 서류코드관리 상세조회
     */
    public PapeCodeVO viewPapeCode(PapeCodeVO papeCodeVO) throws Exception;

    /**
     * 서류코드관리 등록,수정,삭제
     */
    public String savePapeCode(PapeCodeVO papeCodeVO, List<FileInfo> files) throws Exception;

    /**
     * 다운로드 카운트 층가 저장
     */
    public int updtPapeCodeDownCount(PapeCodeVO papeCodeVO) throws Exception ;
}