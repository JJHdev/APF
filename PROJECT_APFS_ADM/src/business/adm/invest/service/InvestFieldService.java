package business.adm.invest.service;

import java.util.List;

import javax.annotation.Resource;

import business.adm.CodeConst;
import business.adm.file.service.BbsFileVO;
import business.adm.invest.service.InvestFieldVO;
import business.adm.invest.service.impl.InvestFieldDAO;
import business.com.CommConst;
import commf.paging.PaginatedArrayList;
import common.file.FileInfo;

/**
 * [서비스인터페이스] - 운영관리-투자분야관리 Service Interface
 * 
 * @class   : InvestFieldService
 * @author  : LHB
 * @since   : 2023.06.09
 * @version : 1.0
 *
 *   수정일        수정자             수정내용
 *  --------   --------    --------------------------
 *   23.06.09    LHB            First Coding.
 */
@SuppressWarnings("all")
public interface InvestFieldService {

    /**
     * 운영관리-투자분야관리 페이징목록 조회
     */
    public PaginatedArrayList listInvestField(InvestFieldVO investFieldVO, int currPage, int pageSize) throws Exception;

    /**
     * 운영관리-투자분야관리 목록조회
     */
    public List listInvestField(InvestFieldVO investFieldVO) throws Exception;

    /**
     * 운영관리-투자분야관리 상세조회
     */
    public InvestFieldVO viewInvestField(InvestFieldVO investFieldVO) throws Exception;
    
    /**
     * 운영관리-투자분야관리 등록,수정,삭제
     */
    public String saveInvestField(InvestFieldVO bannerVO) throws Exception;
    
}