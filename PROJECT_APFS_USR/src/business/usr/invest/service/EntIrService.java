package business.usr.invest.service;

import java.util.List;

/**
 * [서비스인터페이스] - 업체IR정보 Service Interface
 * 
 * @class   : EntIrService
 * @author  : LSH
 * @since   : 2023.05.09
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@SuppressWarnings("all")
public interface EntIrService {

    /**
     * 업체IR정보 목록조회
     */
    public List listEntIr(EntVO entVO) throws Exception;

    /**
     * 업체IR정보 상세조회
     */
    public EntVO viewEntIr(EntVO entVO) throws Exception;

    /**
     * 업체IR정보 저장처리
     */
    public String saveEntIr(EntVO entVO) throws Exception;

    /**
     * 업체IR정보 작성여부 확인
     */
    public boolean existEntIr(String bzentyNo) throws Exception;
    
    /**
     * 업체IR정보 작성여부 확인
     */
    public boolean existEntIr(String bzentyNo, String prgrsSttsCd) throws Exception;
    
    /**
     * 2023.07.28 LSH
     * 업체번호 기준 최종IR번호 조회
     */
    public String getLastEntIrNo(String bzentyNo) throws Exception;

    /**
     * 2023.07.28 LSH
     * 업체번호 기준 최종IR번호 조회
     */
    public String getLastEntIrNo(String bzentyNo, String prgrsSttsCd) throws Exception;

    /**
     * 2023.07.28 LSH
     * 업체IR정보 조회수증가
     */
    public int updtEntIrInqCnt(String irNo) throws Exception;
}