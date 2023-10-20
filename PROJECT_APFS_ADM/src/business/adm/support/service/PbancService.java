package business.adm.support.service;

import java.util.List;

import commf.paging.PaginatedArrayList;
import common.file.FileInfo;

/**
 * [서비스인터페이스] - 운영관리 - 사업공고관리 Service Interface
 * 
 * @class   : PbancService
 * @author  : LHB
 * @since   : 2023.04.24
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@SuppressWarnings("all")
public interface PbancService {

    /**
     * 사업공고 페이징목록 조회
     */
    public PaginatedArrayList listPbanc(PbancVO pbancVO, int currPage, int pageSize) throws Exception;

    /**
     * 사업공고 목록조회
     */
    public List listPbanc(PbancVO pbancVO) throws Exception;

    /**
     * 사업공고 상세조회
     */
    public PbancVO viewPbanc(PbancVO pbancVO) throws Exception;

    /**
     * 사업공고 등록,수정,삭제
     */
    public String savePbanc(PbancVO pbancVO, List<FileInfo> files) throws Exception;

    
}