package business.usr.invest.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import business.com.CommConst;
import business.com.common.service.CommService;
import business.com.common.service.EmailService;
import business.com.user.service.UserService;
import business.com.user.service.UserVO;
import business.usr.CodeConst;
import business.usr.file.service.BizFileService;
import business.usr.file.service.BizFileVO;
import business.usr.invest.service.EntService;
import business.usr.invest.service.EntVO;
import business.usr.invest.service.FundService;
import business.usr.invest.service.FundVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseService;
import common.file.FileInfo;
import common.util.CommUtils;

/**
 * [서비스클래스] - 펀드정보(모태펀드) Service 구현 클래스
 * 
 * @class   : FundServiceImpl
 * @author  : LSH
 * @since   : 2023.04.21
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */

@Service("FundService")
@SuppressWarnings({"all"})
public class FundServiceImpl extends BaseService implements FundService {

    @Resource(name = "FundDAO")
    private FundDAO fundDAO;

    @Resource(name = "FundSprtDAO")
    private FundSprtDAO fundSprtDAO;

    @Resource(name = "FundInvstrDAO")
    private FundInvstrDAO fundInvstrDAO;

    @Resource(name = "BizFileService")
    private BizFileService bizFileService;
	
    @Resource(name = "EntService")
	private EntService entService;
	
    @Resource(name = "UserService")
	private UserService userService;
	
    @Resource(name = "CommService")
	private CommService commService;
	
    @Resource(name = "EmailService")
	private EmailService emailService;

    /**
     * 펀드정보(모태펀드) 페이징목록조회
     */
    @Override
    public PaginatedArrayList listFund(FundVO fundVO, int currPage, int pageSize) throws Exception {
    	return fundDAO.listFund(fundVO, currPage, pageSize);
    }

    /**
     * 펀드정보(모태펀드) 목록조회
     */
    @Override
    public List listFund(FundVO fundVO) throws Exception {
    	return fundDAO.listFund(fundVO);
    }

    /**
     * 펀드정보(모태펀드) 상세조회
     */
	@Override
	public FundVO viewFund(String fundNo) throws Exception {
		return fundDAO.viewFund(fundNo);
	}

    /**
     * 펀드정보(모태펀드) 등록,수정,삭제
     */
	@Override
	public String saveFund(FundVO fundVO) throws Exception {
		
		if (fundVO == null)
			throw processException("error.comm.notTarget");
		
		int ret = 0;
		String mode = fundVO.getMode();
		
		if (CommConst.UPDATE.equals(mode)) {
			// 펀드정보(모태펀드) 수정
			ret = fundDAO.updtFund(fundVO);
		}
		else if (CommConst.INSERT.equals(mode)) {
			// 펀드정보(모태펀드) 등록
			ret = fundDAO.regiFund(fundVO);
		}
		else if (CommConst.DELETE.equals(mode)) {
			// 펀드정보(모태펀드) 삭제
			ret = fundDAO.deltFund(fundVO);
		}
        // 저장결과를 반환한다.
		if (ret > 0)
	        return message.getMessage("prompt.success");
		else
			throw processException("error.comm.notProcess");
	}
	
    /**
     * 펀드투자자(조합원) 페이징목록조회
     */
    @Override
    public PaginatedArrayList listFundInvstr(FundVO fundVO, int currPage, int pageSize) throws Exception {
    	return fundInvstrDAO.listFundInvstr(fundVO, currPage, pageSize);
    }

    /**
     * 펀드투자자(조합원) 목록조회
     */
    @Override
    public List listFundInvstr(FundVO fundVO) throws Exception {
    	return fundInvstrDAO.listFundInvstr(fundVO);
    }

    /**
     * 펀드투자자(조합원) 상세조회
     */
	@Override
	public FundVO viewFundInvstr(FundVO fundVO) throws Exception {
		return fundInvstrDAO.viewFundInvstr(fundVO);
	}

    /**
     * 펀드투자자(조합원) 등록,수정,삭제
     */
	@Override
	public String saveFundInvstr(FundVO fundVO) throws Exception {
		
		if (fundVO == null)
			throw processException("error.comm.notTarget");
		
		int ret = 0;
		String mode = fundVO.getMode();
		
		if (CommConst.UPDATE.equals(mode)) {
			// 펀드투자자(조합원) 수정
			ret = fundInvstrDAO.updtFundInvstr(fundVO);
		}
		else if (CommConst.INSERT.equals(mode)) {
			// 펀드투자자(조합원) 등록
			ret = fundInvstrDAO.regiFundInvstr(fundVO);
		}
		else if (CommConst.DELETE.equals(mode)) {
			// 펀드투자자(조합원) 삭제
			ret = fundInvstrDAO.deltFundInvstr(fundVO);
		}
        // 저장결과를 반환한다.
		if (ret > 0)
	        return message.getMessage("prompt.success");
		else
			throw processException("error.comm.notProcess");
	}

    /**
     * 펀드지원정보(IR지원) 페이징목록조회
     */
    @Override
    public PaginatedArrayList listFundSprt(FundVO fundVO, int currPage, int pageSize) throws Exception {
    	return fundSprtDAO.listFundSprt(fundVO, currPage, pageSize);
    }

    /**
     * 펀드지원정보(IR지원) 목록조회
     */
    @Override
    public List listFundSprt(FundVO fundVO) throws Exception {
    	return fundSprtDAO.listFundSprt(fundVO);
    }

    /**
     * 펀드지원정보(IR지원) 상세조회
     */
	@Override
	public FundVO viewFundSprt(FundVO fundVO) throws Exception {
		return fundSprtDAO.viewFundSprt(fundVO);
	}

    /**
     * 펀드지원정보(IR지원) 등록
     * 
     * 2023.09.11 펀드지원 메일발송처리 추가
     */
	@Override
	public String registFundSprt(FundVO fundVO, List<FileInfo> files) throws Exception {
		
		if (fundVO == null)
			throw processException("error.comm.notTarget");
		
		int ret = 0;
		String mode = fundVO.getMode();
		
		if (CommConst.INSERT.equals(mode)) {
			// 미검토 상태
			fundVO.setSprtSttsCd(CodeConst.SPRT_SUBMIT);
			// 지원일자
			fundVO.setSprtYmd   (CommUtils.getCurDateString());
			// 펀드지원정보(IR지원) 등록
			ret = fundSprtDAO.regiFundSprt(fundVO);
		}
		if (ret > 0) {
			// 첨부파일 저장처리
			BizFileVO fileVO = BizFileVO.builder()
					.taskSeCd(CodeConst.TASK_FUND)
					.docNo   (fundVO.getFundNo())
					.dtlDocNo(fundVO.getBzentyNo())
					.gsUserNo(fundVO.getGsUserNo())
					.build();
			
			fileVO.setMode(mode);
			
			bizFileService.saveBizFile(fileVO, files);
			
			// 2023.09.12 메일발송처리
			_sendMailAll(fundVO);
		}
        // 저장결과를 반환한다.
		if (ret > 0)
	        return message.getMessage("prompt.success");
		else
			throw processException("error.comm.notProcess");
		
		
	}
	
	// 펀드지원하기 메일발송처리
	private void _sendMailAll(FundVO fundVO) throws Exception {
		
		// 지원업체정보 조회
		EntVO ent = entService.getEnt(fundVO.getBzentyNo());
		
		if (ent == null)
			return;
		
		// 메일발송 대상업체목록 조회
		List<FundVO> targets = fundDAO.listFundEnt(fundVO.getFundNo());
		
		if (CommUtils.isEmptyList(targets))
			return;
		
		for (FundVO fund : targets) {
			
			// 업체명 매핑
			fund.setBzentyNm(ent.getBzentyNm());
			// 컨텍스트 경로맵핑
			fund.setGsContext(fundVO.getGsContext());
			
			// 농금원 투자자인 경우
			if (CommConst.YES.equals(fund.getApfsYn())) {
				// 농금원 발송대상 목록조회
				List<Map<String,Object>> users = commService.listCode(CodeConst.MNG_MAIL_SE);
				if (users != null) {
					for (Map<String,Object> user : users) {
						// 투자자회원에게 메일발송
						_sendMail(fund, (String)user.get("cdNm"), (String)user.get("cdCn"));
					}
				}
			}
			else {
				List<UserVO> users = userService.listUserByEnt(fund.getInvtBzentyNo());
				if (users != null) {
					for (UserVO user : users) {
						// 투자자회원에게 메일발송
						_sendMail(fund, user.getUserNm(), user.getUserId());
					}
				}
			}
		}
	}
	
	// 펀드지원하기 메일발송처리
	private void _sendMail(FundVO fundVO, String userNm, String emlAddr) throws Exception {

		Map<String,String> params = new HashMap<String,String> ();
		params.put("context"     , fundVO.getGsContext());
		params.put("bzentyNm"    , fundVO.getBzentyNm ());
		params.put("fundNm"      , fundVO.getFundNm   ());
		params.put("toName"      , userNm);
		params.put("toAddress"   , emlAddr);

		// 메일발송
		emailService.sendBizEmail(params, CodeConst.BIZMAIL_FUNDAPR);
	}

    /**
     * 마이페이지(투자자) - 경영체지원현황 - 상태변경처리 (다중처리)
     */
	@Override
    public String updateFundSprt(FundVO fundVO) throws Exception {
		
		if (fundVO == null)
			throw processException("error.comm.notTarget");
		
		int ret = 0;
		String mode = fundVO.getMode();
		List<FundVO> list = fundVO.getSprtList();
		
		if (list == null)
			throw processException("error.comm.notTarget");
		if (list.size() == 0)
			throw processException("error.comm.notTarget");
		
		if (CommConst.UPDATE.equals(mode)) {
			
			for (FundVO data : list) {
				data.setSprtSttsCd (fundVO.getSprtSttsCd      ());
				data.setGsUserNo   (fundVO.getGsUserNo        ());
				data.setRvwYmd     (CommUtils.getCurDateString());
				
				// 펀드지원정보(IR지원) 수정
				ret += fundSprtDAO.updtFundSprt(data);
			}
		}
        // 저장결과를 반환한다.
		if (ret > 0)
	        return message.getMessage("prompt.success");
		else
			throw processException("error.comm.notProcess");
    }

    /**
     * 펀드조합 업체번호 기준 펀드및조합정보 목록조회
     */
	@Override
    public List listFundByBzenty(String bzentyNo) throws Exception {
    	return fundDAO.listFundByBzenty(bzentyNo);
    }
	
}