package business.usr.invest.service;

import java.util.List;

/**
 * [서비스인터페이스] - 재무정보 Service Interface
 * 
 * @class   : EntFnnrService
 * @author  : LSH
 * @since   : 2023.05.04
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@SuppressWarnings("all")
public interface EntFnnrService {

    /**
     * 재무정보 목록조회
     */
    public List listEntFnnr(EntVO entVO) throws Exception;

    /**
     * 재무정보 요약 목록 조회
     */
    public List listEntFnnrSummary(EntVO entVO) throws Exception;

    /**
     * 재무정보 세부 목록 조회
     */
    public List listEntFnnrDetails(EntVO entVO) throws Exception;

    /**
     * 재무정보 년도별 목록 조회
     */
    public List listEntFnnrYear(EntVO entVO) throws Exception;
    
    /**
     * 재무정보 기준년도 조회
     */
    public String getEntFnnrYear(EntVO entVO) throws Exception;
    
    /**
     * 재무정보 상세조회
     */
    public EntVO viewEntFnnr(EntVO entVO) throws Exception;

    /**
     * 재무정보 다중저장처리
     */
    public String saveEntFnnr(EntVO entVO, List<EntVO> list) throws Exception;

    /**
     * 2023.07.24 LSH
     * 재무정보 KODATA 등록여부 조회
     */
    public boolean existKodata(String bzentyNo) throws Exception;

    /**
     * 2023.07.24 LSH
     * 재무정보 수정 목록 조회
     * 
     * @param entVO.irNo     IR번호
     * @param entVO.fnnrSeCd 재무구분코드
     * @param entVO.dataSeCd 데이터구분코드
     * @param entVO.fnnrType 합계계정 공통코드
     * 
     * fnnrYr,fnnrAmt1,fnnrAmt2,fnnrAmt3
     */
    public List listEntFnnrUpdates(EntVO entVO) throws Exception;
}