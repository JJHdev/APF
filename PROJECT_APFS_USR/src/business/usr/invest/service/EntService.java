package business.usr.invest.service;

import java.util.List;
import java.util.Map;

import commf.paging.PaginatedArrayList;

/**
 * [서비스인터페이스] - 업체정보 Service Interface
 * 
 * @class   : EntService
 * @author  : LSH
 * @since   : 2023.04.27
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@SuppressWarnings("all")
public interface EntService {

    /**
     * 업체정보 페이징목록 조회
     */
    public PaginatedArrayList listEnt(EntVO entVO, int currPage, int pageSize) throws Exception;

    /**
     * 북마크한 업체정보 페이징목록 조회
     */
    public PaginatedArrayList listEntBkmk(EntVO entVO, int currPage, int pageSize) throws Exception;

    /**
     * 업체정보 목록조회
     */
    public List listEnt(EntVO entVO) throws Exception;

    /**
     * 업체정보 상세조회
     */
    public EntVO viewEnt(EntVO entVO) throws Exception;

    /**
     * 2023.07.25 LSH
     * IR작성용 업체정보 상세조회
     */
    public EntVO viewEntForIr(EntVO entVO) throws Exception;

    /**
     * 업체정보 상세조회 (업체번호기준)
     */
    public EntVO getEnt(String bzentyNo) throws Exception;

    /**
     * 업체정보 등록,수정,삭제
     */
    public String saveEnt(EntVO entVO) throws Exception;

    /**
     * 2023.09.04 LSH 반려업체 보완제출 처리 (로그인전 처리)
     */
	public int saveEntCmpl(EntVO entVO) throws Exception;

	/**
	 * 2023.07.17 LSH
	 * 사업자번호 기준 업체번호/업체유형/대표여부를 조회한다.
	 * 
	 * @param  brno           사업자번호
	 * @return map.bzentyNo   업체번호
	 * @return map.bzentySeCd 업체유형
	 * @return map.bzentyNm   회사명
	 * @return map.rprsvNm    대표자
	 * @return map.fndnYmd    설립일
	 * @return map.rprsTelno  대표번호
	 * @return map.rprsYn     대표여부
	 */
	public Map<String,Object> viewEntByBrno(String brno) throws Exception;
	
    /**
     * 2023.07.28 LSH
     * 업체정보 조회이력 등록
     */
    public int regiEntInqHst(EntVO entVO) throws Exception;
}