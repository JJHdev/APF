package business.usr.invest.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import business.batch.service.LinkKodataService;
import business.com.CommConst;
import business.usr.CodeConst;
import business.usr.file.service.EntFileService;
import business.usr.file.service.EntFileVO;
import business.usr.invest.service.EntCoHstService;
import business.usr.invest.service.EntFnnrService;
import business.usr.invest.service.EntInvtAmtService;
import business.usr.invest.service.EntIrService;
import business.usr.invest.service.EntLwstService;
import business.usr.invest.service.EntMgmtService;
import business.usr.invest.service.EntPtntService;
import business.usr.invest.service.EntRprsvHstService;
import business.usr.invest.service.EntService;
import business.usr.invest.service.EntShrholdrService;
import business.usr.invest.service.EntVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseService;
import common.util.CommUtils;

/**
 * [서비스클래스] - 업체정보 Service 구현 클래스
 * 
 * @class   : EntServiceImpl
 * @author  : LSH
 * @since   : 2023.04.27
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */

@Service("EntService")
@SuppressWarnings({"all"})
public class EntServiceImpl extends BaseService implements EntService {

    @Resource(name = "EntDAO")
    private EntDAO entDAO;

    @Resource(name="EntFileService")
    protected EntFileService entFileService;

    @Resource(name="LinkKodataService")
    protected LinkKodataService linkKodataService;
    // 업체IR정보
	@Resource(name="EntIrService")
    protected EntIrService entIrService;
    // 회사연혁
    @Resource(name="EntCoHstService")
    protected EntCoHstService entCoHstService;
    // 손익계산서/재무상태표
    @Resource(name="EntFnnrService")
    protected EntFnnrService entFnnrService;
    // 투자금액
    @Resource(name="EntInvtAmtService")
    protected EntInvtAmtService entEtcinvtAmtService;
    // 소송현황
    @Resource(name="EntLwstService")
    protected EntLwstService entLwstService;
    // 경영진정보
    @Resource(name="EntMgmtService")
    protected EntMgmtService entMgmtService;
    // 특허상표권현황
    @Resource(name="EntPtntService")
    protected EntPtntService entPtntService;
    // 대표자이력
    @Resource(name="EntRprsvHstService")
    protected EntRprsvHstService entRprsvHstService;
    // 주주현황
    @Resource(name="EntShrholdrService")
    protected EntShrholdrService entShrholdrService;
    
    /**
     * 업체정보 페이징목록조회
     */
    @Override
    public PaginatedArrayList listEnt(EntVO entVO, int currPage, int pageSize) throws Exception {
    	return entDAO.listEnt(entVO, currPage, pageSize);
    }

    /**
     * 북마크한 업체정보 페이징목록 조회
     */
    public PaginatedArrayList listEntBkmk(EntVO entVO, int currPage, int pageSize) throws Exception {
    	return entDAO.listEntBkmk(entVO, currPage, pageSize);
    }

    /**
     * 업체정보 목록조회
     */
    @Override
    public List listEnt(EntVO entVO) throws Exception {
    	return entDAO.listEnt(entVO);
    }

    /**
     * 업체정보 상세조회
     */
	@Override
	public EntVO viewEnt(EntVO entVO) throws Exception {
		return entDAO.viewEnt(entVO);
	}

    /**
     * 2023.07.25 LSH
     * IR작성용 업체정보 상세조회
     */
	@Override
    public EntVO viewEntForIr(EntVO entVO) throws Exception {
		return entDAO.viewEntForIr(entVO);
    }

    /**
     * 업체정보 상세조회
     */
	@Override
	public EntVO getEnt(String bzentyNo) throws Exception {
		return entDAO.viewEnt(EntVO.builder().bzentyNo(bzentyNo).gsBzentyNo(bzentyNo).build());
	}

    /**
     * 기업정보 등록/수정
	 * 1) [mode:I / act:BI] 회원가입 - 기업정보 등록
	 * 3) [mode:U / act:BI] 마이페이지 - 기본정보 - 기업기본정보 수정
	 * 4) [mode:U / act:BF] 마이페이지 - 기본정보 - 기업상세정보 수정
	 * 5) [mode:I / act:II] 마이페이지 - IR작성하기 - 대시보드 등록
	 * 6) [mode:U / act:II] 마이페이지 - IR작성하기 - 대시보드 수정
	 * 7) [mode:I / act:IF] 마이페이지 - IR작성하기 - 상세정보 등록
	 * 8) [mode:U / act:IF] 마이페이지 - IR작성하기 - 상세정보 수정
	 * 9) [mode:I / act:CF] 정보서비스 - 경영체 데이터 업로드 - 파일 업로드
     */
	@Override
	public String saveEnt(EntVO entVO) throws Exception {
		
		if (entVO == null)
			throw processException("error.comm.notTarget");
		
		int ret = 0;
		
    	String act  = entVO.getAct();
    	String mode = entVO.getMode();
    	
    	// IR작성하기인 경우
    	if (CommUtils.exist(new String[] {
    		CommConst.ACT_IR_INFO, 
    		CommConst.ACT_IR_FILE}, act)) {
    		ret = _saveEntByIR(entVO);
    	}
    	// 경영체 데이터 업로드인 경우
    	else if (CommUtils.exist(new String[] {CommConst.ACT_CRDNS_FILE}, act))
    		ret = _saveEntBySprtUld(entVO);
   		// 회원가입인 경우
    	else if (CommConst.INSERT.equals(mode))
    		ret = _saveEntByJoin(entVO);
   		// 기업정보수정인 경우
    	else if (CommConst.UPDATE.equals(mode))
    		ret = _saveEntByMyPage(entVO);
    	else
    		throw processException("error.comm.notProcess");

        // 저장결과를 반환한다.
		if (ret > 0)
	        return message.getMessage("prompt.success");
		else
			throw processException("error.comm.notProcess");
	}
	
	/**
	 * 회원가입시 등록처리 (mode:I)
	 * 
	 * - [act: BI] 회원가입 - 기업정보 등록
	 * 
	 * - 신규등록이면 
	 * -   : 업체정보 등록처리
	 * -   : KODATA인 경우 사업자번호 기준 KODATA 연계처리
	 * - 업체정보가 있으면
	 * -   KODATA인 경우 업체정보 수정처리
	 * 
	 * KODATA인 경우 
	 * - 지원신청정보 업데이트 처리 (사업자번호 기준)
	 * 
	 * 대표계정인 경우
	 * - 사업자등록증 파일 등록
	 * - 경영체의 경우 위임장 파일 등록
	 * - 경영체의 경우 썸네일 파일 등록
	 * - 경영체의 경우 기업상세정보 등록
	 */
	private int _saveEntByJoin(EntVO entVO) throws Exception {
		
		// 수정여부
		String existYn  = entVO.getExistYn();
		// 대표여부
		String rprsYn   = entVO.getRprsYn();
		// KODATA여부
		String kodataYn = entVO.getKodataYn();
		// KODATA연계처리여부
		boolean writeKodata = false;
		
		// 업체정보가 있으면
		if (CommConst.YES.equals(existYn)) {
			// KODATA인 경우
			if (CommConst.YES.equals(kodataYn)) {
				// 업체정보 수정처리
				entDAO.updtEnt(entVO);
			}
		}
		// 신규등록이면
		else {
			// 업체정보 등록처리
			if (entDAO.regiEnt(entVO) == 0)
				throw processException("exception.proc.err");
			// KODATA인 경우
			// 2023.08.21 LSH 경영체의 경우에만 KODATA연계처리
			if (CommConst.YES.equals(kodataYn) &&
				CodeConst.ENT_EBZ.equals(entVO.getBzentySeCd())) {
				// KODATA연계처리 설정
				writeKodata = true;
			}
		}
		// KODATA인 경우
		if (CommConst.YES.equals(entVO.getKodataYn())) {
			// 경영체인 경우
    		if (CodeConst.ENT_EBZ.equals(entVO.getBzentySeCd())) {
    			//사업자번호 기준으로 투자지원신청정보 업데이트 처리
    			entDAO.updtSprtByBrno(entVO);
    			//사업자번호 기준으로 행사참여경영체 업데이트 처리
    			entDAO.updtEventByBrno(entVO);
            }
			// 투자자인 경우
    		else if (CodeConst.ENT_EIV.equals(entVO.getBzentySeCd())) {
    			//사업자번호 기준으로 펀드투자자 업데이트 처리
    			entDAO.updtFundInvstrByBrno(entVO);
    		}
		}
		// 대표계정인 경우
		if (CommConst.YES.equals(rprsYn)) {
    		// 경영체인 경우
    		if (CodeConst.ENT_EBZ.equals(entVO.getBzentySeCd())) {
    			// 기업분야 저장처리
    			_saveEntRlms(entVO);
            }
			// 업체파일 저장처리
			_saveEntFiles(entVO, new String[] {
					CodeConst.FILE_RPRS,
					CodeConst.FILE_BREG,
					CodeConst.FILE_DLGT
			});
		}
		// KODATA연계처리 필요시
		if (writeKodata) {
			// 사업자번호 기준 KODATA 연계처리
			linkKodataService.writeKodata(null, entVO.buildForWriteKodata());
		}
		return 1;
	}

    /**
     * 2023.09.05 LSH 기업회원 반려업체 수정처리 (로그인전 처리)
     * - 대표계정만 처리됨
     * - 업체 유형 변경시
     *   1) 권한정보 리셋
     *   2) 그룹정보 리셋
     *   3) 연결정보 리셋
     * - 상세정보 수정 처리
     * - 첨부파일 등록 처리
     */
	@Override
	public int saveEntCmpl(EntVO entVO) throws Exception {
		
		String bzentyNo = entVO.getBzentyNo();
		String orgSeCd  = entVO.getBzentySeCdOrg();
		String newSeCd  = entVO.getBzentySeCd();
		// 승인상태/업체유형/투자희망금액 업데이트
		entDAO.updtEntCmpl(entVO);
		// KODATA연계처리여부
		boolean writeKodata = false;
		// 조회한 업체정보
		EntVO entObj = null;
		
		// 업체유형 변경시
		if (!CommUtils.isEqual(orgSeCd, newSeCd)) {
			// 업체정보 조회
			entObj = getEnt(bzentyNo);
			// KODATA 여부
			String kodataYn = (CommUtils.isNotEmpty(entObj.getKdCd()) ? CommConst.YES : CommConst.NO);
			if (CommConst.YES.equals(kodataYn)) {
    			//사업자번호 기준으로 투자지원신청정보 업데이트 처리
    			entDAO.updtSprtByBrno(entObj);
    			//사업자번호 기준으로 행사참여경영체 업데이트 처리
    			entDAO.updtEventByBrno(entObj);
    			//사업자번호 기준으로 펀드투자자 업데이트 처리
    			entDAO.updtFundInvstrByBrno(entObj);
			}
			// 경영체로 변경시 KODATA 연계처리
			if (CodeConst.ENT_EBZ.equals(newSeCd)) {
				// KODATA연계처리 설정
				writeKodata = true;
			}
		}
		// 경영체인 경우
		if (CodeConst.ENT_EBZ.equals(newSeCd)) {
			// 기업분야 저장처리
			_saveEntRlms(entVO);
        }
		// 업체파일 저장처리
		_saveEntFiles(entVO, new String[] {
				CodeConst.FILE_RPRS,
				CodeConst.FILE_BREG,
				CodeConst.FILE_DLGT
		});
		// KODATA연계처리 필요시
		if (writeKodata) {
			// 사업자번호 기준 KODATA 연계처리
			linkKodataService.writeKodata(null, entObj.buildForWriteKodata());
		}
        return 1;
	}

	/**
	 * 기업분야 등록/수정
	 * 
	 * @param entVO.bzentyNo
	 * @param entVO.entRlms
	 * 
	 */
	private void _saveEntRlms(EntVO entVO) throws Exception {
		
		// 업체분야정보 업체번호기준 일괄 삭제
		entDAO.deltEntRlmByBzentyNo(entVO.getBzentyNo());
		
		// 업체분야정보 저장처리
		for (EntVO item : entVO.getEntRlms()) {
			// ALL인 경우 SKIP
			if (CommConst.ALL.equals(item.getFldCd()))
				continue;
			item.setBzentyNo(entVO.getBzentyNo());
			item.setGsUserNo(entVO.getGsUserNo());
			entDAO.regiEntRlm(item);
		}
	}
	
	/**
	 * 업체파일 등록/수정
	 * 
	 * @param entVO.bzentyNo
	 * @param entVO.saveFiles
	 * @param entVO.mode
	 * 
	 */
	private void _saveEntFiles(EntVO entVO, String[] docuCds) throws Exception {
		
		// 업체파일 저장처리
		EntFileVO fileVO = EntFileVO.builder()
				.bzentyNo(entVO.getBzentyNo())
				.gsUserNo(entVO.getGsUserNo())
				.build();
		fileVO.setMode(entVO.getMode());

		entFileService.saveEntFile(fileVO, docuCds, entVO.getSaveFiles());
	}
	
	/**
	 * 마이페이지 - 기업정보 수정처리 (mode:U)
	 * 
	 * - [act: BI] 마이페이지 - 기본정보 - 기업기본정보 수정
	 * - [act: BF] 마이페이지 - 기본정보 - 기업상세정보 수정
	 */
	private int _saveEntByMyPage(EntVO entVO) throws Exception {
		
    	String act = entVO.getAct();
    	
		// 기업기본정보 수정인 경우
    	if (CommConst.ACT_BZ_INFO.equals(act))
    		return entDAO.updtEnt(entVO);
   		// 기업상세정보 수정인 경우
    	else if (CommConst.ACT_BZ_FILE.equals(act)) {
    		
			if (entDAO.updtEnt(entVO) > 0) {
				// 기업분야 저장처리
				_saveEntRlms(entVO);
				// 업체파일 저장처리
				_saveEntFiles(entVO, new String[] {
						CodeConst.FILE_RPRS
				});
				return 1;
			}
    	}
   		throw processException("error.comm.notProcess");
	}

	/**
	 * IR작성하기 저장처리
	 * 
	 * - [act: II] 마이페이지 - IR작성하기 - 대시보드 수정
	 * 
	 * - [act: IF] 마이페이지 - IR작성하기 - 상세정보 수정
	 */
	private int _saveEntByIR(EntVO entVO) throws Exception {
		
		if (!CommConst.UPDATE.equals(entVO.getMode()))
			throw processException("error.comm.notValid");
		
    	String act = entVO.getAct();
    	
		// 대시보드 수정인 경우
    	if (CommConst.ACT_IR_INFO.equals(act)) {
    		// IR 정보 업데이트
    		entIrService.saveEntIr(entVO);
    	    // 투자금액 저장처리
   			entEtcinvtAmtService.saveEntInvtAmt(entVO, entVO.getInvtList());
    	    // 재무정보 저장처리
   			entFnnrService.saveEntFnnr(entVO, entVO.getFnnrList());
    	    // 회사연혁 저장처리
   			entCoHstService.saveEntCoHst(entVO, entVO.getCoHstList());
    	    // 특허상표권현황 저장처리
   			entPtntService.saveEntPtnt(entVO, entVO.getPtntList());
    	    // 대표자이력 저장처리
   			entRprsvHstService.saveEntRprsvHst(entVO, entVO.getRprsHstList());
    	    // 경영진정보 저장처리
   			entMgmtService.saveEntMgmt(entVO, entVO.getMgmtList());
    	    // 주주현황 저장처리
   			entShrholdrService.saveEntShrholdr(entVO, entVO.getShroldrList());
    	    // 소송현황 저장처리
   			entLwstService.saveEntLwst(entVO, entVO.getLwstList());
    		return 1;
    	}
   		// 상세정보 수정인 경우
    	else if (CommConst.ACT_IR_FILE.equals(act)) {
    		// IR 정보 업데이트 (홍보영상 URL)
    		entIrService.saveEntIr(entVO);
    		
    		String[] types = new String[] {
					CodeConst.FILE_PLAN,
					CodeConst.FILE_ADOC
			};
    		// 관리자인 경우 썸네일도 처리함
    		if (entVO.isAdmin()) {
    			types = new String[] {
    					CodeConst.FILE_RPRS,
    					CodeConst.FILE_PLAN,
    					CodeConst.FILE_ADOC
    			};
    		}
			// 업체파일 저장처리
			_saveEntFiles(entVO, types);
			return 1;
    	}
    	else
    		throw processException("error.comm.notProcess");
	}
	
	/**
	 * 정보서비스 - 경영체 데이터 업로드 등록처리 (mode:I)
	 * 
	 * - [act: CF] 정보서비스 - 경영체 데이터 업로드 - 파일 업로드 (등록)
	 */
	private int _saveEntBySprtUld(EntVO entVO) throws Exception {
		
    	String act = entVO.getAct();
    	
		// 경영체 데이터 업로드 등록인 경우
    	if (CommConst.ACT_CRDNS_FILE.equals(act))
    		return entDAO.regiEnt(entVO);
    	
   		throw processException("error.comm.notProcess");
	}
	
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
	@Override
	public Map<String,Object> viewEntByBrno(String brno) throws Exception {
		return entDAO.viewEntByBrno(brno);
	}
    
    /**
     * 2023.07.28 LSH
     * 업체정보 조회이력 등록
     */
	@Override
    public int regiEntInqHst(EntVO entVO) throws Exception {
    	return entDAO.regiEntInqHst(entVO);
    }
}