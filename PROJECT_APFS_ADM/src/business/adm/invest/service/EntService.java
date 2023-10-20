package business.adm.invest.service;

import java.util.List;

import business.com.user.service.UserVO;
import commf.paging.PaginatedArrayList;
import common.file.FileInfo;

/**
 * [서비스인터페이스] - 회원관리-업체관리 Service Interface
 * 
 * @class   : EntService
 * @author  : LHB
 * @since   : 2023.06.12
 * @version : 1.0
 *
 *   수정일       수정자             수정내용
 *  --------   --------    ---------------------------
 *  23.06.12     LHB           First Coding.
 */
@SuppressWarnings("all")
public interface EntService {

    /**
     * 회원관리-업체관리 페이징목록 조회
     */
    public PaginatedArrayList listEnt(EntVO entVO, int currPage, int pageSize) throws Exception;

    /**
     * 회원관리-업체관리 목록조회
     */
    public List listEnt(EntVO entVO) throws Exception;

    /**
     * 회원관리-업체관리 상세조회
     */
    public EntVO viewEnt(EntVO entVO) throws Exception;

    /**
     * 회원관리-업체관리 등록,수정,삭제
     */
    public String saveEnt(EntVO entVO) throws Exception;
    
    
    /**
     *  2023.09.04 LHB
     *  업체 대표정보 조회
     */
    public UserVO getRprsInfo(String bzentyNo) throws Exception;

}