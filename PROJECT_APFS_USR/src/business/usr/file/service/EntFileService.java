package business.usr.file.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import commf.paging.PaginatedArrayList;
import common.file.FileInfo;

/**
 * [서비스인터페이스] - 업체첨부파일 Service Interface
 * 
 * @class   : EntFileService
 * @author  : LSH
 * @since   : 2023.04.23
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@SuppressWarnings("all")
public interface EntFileService {

    /**
     * 업체첨부파일 목록조회
     */
    public List listEntFile(EntFileVO entFileVO) throws Exception;

    /**
     * 업체첨부파일 상세조회
     */
    public EntFileVO viewEntFile(Long sn) throws Exception;
    
    /**
     * 업체첨부파일 단일삭제
     */
    public String deltEntFile(EntFileVO entFileVO) throws Exception;

    /**
     * 업체첨부파일 등록,수정,삭제
     */
    public void saveEntFile(EntFileVO entFileVO, String[] docuCds, List<FileInfo> saveFiles) throws Exception;
}