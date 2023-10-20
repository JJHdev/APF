package business.usr.file.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import commf.paging.PaginatedArrayList;
import common.file.FileInfo;

/**
 * [서비스인터페이스] - 게시판첨부파일 Service Interface
 * 
 * @class   : BbsFileService
 * @author  : LSH
 * @since   : 2023.04.23
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@SuppressWarnings("all")
public interface BbsFileService {

    /**
     * 게시판첨부파일 목록조회
     */
    public List listBbsFile(BbsFileVO bbsFileVO) throws Exception;

    /**
     * 게시판첨부파일 상세조회
     */
    public BbsFileVO viewBbsFile(Long sn) throws Exception;
   
    /**
     * 게시판첨부파일 단일삭제
     */
    public String deltBbsFile(BbsFileVO bbsFileVO) throws Exception;

    /**
     * 게시판첨부파일 등록,수정,삭제
     */
    public void saveBbsFile(BbsFileVO bbsFileVO, List<FileInfo> saveFiles) throws Exception;

    /**
     * 게시판첨부파일 이미지 표출 조회
     */
	public BbsFileVO viewBbsFile(BbsFileVO build);
 }