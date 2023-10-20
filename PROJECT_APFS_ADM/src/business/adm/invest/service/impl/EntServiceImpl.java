package business.adm.invest.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import business.adm.CodeConst;
import business.adm.file.service.BizFileService;
import business.adm.invest.service.EntService;
import business.adm.invest.service.EntVO;
import business.com.CommConst;
import business.com.common.service.EmailService;
import business.com.user.service.UserVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseService;
import common.util.CommUtils;

/**
 * [서비스클래스] - 회원관리-업체관리 Service 구현 클래스
 * 
 * @class   : EntServiceImpl
 * @author  : LHB
 * @since   : 2023.06.12
 * @version : 1.0
 *
 *   수정일       수정자                 수정내용
 *  --------   --------    ---------------------------
 * 23.06.12      LHB              First Coding.
 */

@Service("EntService")
@SuppressWarnings({"all"})
public class EntServiceImpl extends BaseService implements EntService {

    @Resource(name = "EntDAO")
    private EntDAO entDAO;

    // 업무첨부파일
    @Resource(name = "BizFileService")
    private BizFileService bizFileService;
    
    // 이메일
    @Resource(name = "EmailService")
    private EmailService emailService;
	
    /**
     * 모태펀드 페이징목록조회
     */
    @Override
    public PaginatedArrayList listEnt(EntVO entVO, int currPage, int pageSize) throws Exception {
    	return entDAO.listEnt(entVO, currPage, pageSize);
    }

    /**
     * 모태펀드 목록조회
     */
    @Override
    public List listEnt(EntVO entVO) throws Exception {
    	return entDAO.listEnt(entVO);
    }

    /**
     * 모태펀드 상세조회
     */
	@Override
	public EntVO viewEnt(EntVO entVO) throws Exception {
		return entDAO.viewEnt(entVO);
	}

    /**
     * 모태펀드 등록,수정,삭제
     */
	@Override
	public String saveEnt(EntVO entVO) throws Exception {
		
		if (entVO == null)
			throw processException("error.comm.notTarget");
		
		int ret = 0;
		String mode = entVO.getMode();
		
		boolean isSent = false;
		String sentFailMessage = "";
		String useSttsCd = CommUtils.nvlTrim(entVO.getUseSttsCd());
		
		if (CommConst.INSERT.equals(mode)) {
			// 업체 등록 - 운영 관리 경영체 데이터 업로드에서 사용함
			ret = entDAO.regiEnt(entVO);
		} else if (CommConst.UPDATE.equals(mode)) {
			// 업체 수정
			ret = entDAO.updtEnt(entVO);
			
			try {
				if (ret > 0 && CommUtils.isNotEmpty(useSttsCd)) {
					if (useSttsCd.equals(CodeConst.USE_STATUS_USABLE) || useSttsCd.equals(CodeConst.USE_STATUS_REJECT)) {
						// 승인 혹은 반려인 경우에만 메일 발송
						sendMail(entVO);
						isSent = true;
					}
				}
			} catch (NullPointerException e) {
				logger.info("업체 승인 메일 발송 중 Exception :: ", e);
				sentFailMessage = e.getMessage();
			} catch (Exception e) {
				logger.info("업체 승인 메일 발송 중 Exception :: ", e);
				sentFailMessage = e.getMessage();
			}
		} /*else if (CommConst.DELETE.equals(mode)) {
			// 업체 삭제
			ret = entDAO.deltEnt(entVO);
		}*/

        // 저장결과를 반환한다.
		if (ret > 0) {
			if (useSttsCd != null && (useSttsCd.equals(CodeConst.USE_STATUS_USABLE) || useSttsCd.equals(CodeConst.USE_STATUS_REJECT)) && !isSent) {
				// 승인 혹은 반려로 메일 발송이 되었는데, 메일 발송이 되지 않은 경우 오류 메시지 표출
				return sentFailMessage;
			} else {
				return message.getMessage("prompt.success");
			}
		} else {
			throw processException("error.comm.notProcess");
		}
	}
	
	/**
     *  2023.09.04 LHB
     *  업체 대표정보 조회
     */
	@Override
	public UserVO getRprsInfo(String bzentyNo) throws Exception {
		return (UserVO) entDAO.getUserRprs(bzentyNo);
	}
	
	/**
     *  2023.08.01 LHB
     *  승인 및 반려 메일 발송
     */
	public void sendMail(EntVO entVO) throws Exception {
		
		if (CommUtils.isEmpty(entVO.getBzentyNo())) {
			// 업체 번호가 없을 경우
			throw processException("error.mail.invalidBzentyNo");
		}
		
		Map<String,String> params	= new HashMap<String,String> ();
		String useSttsCd			= CommUtils.nvlTrim(entVO.getUseSttsCd());
		String code					= null;
		
		// 업체번호로 해당 업체번호의 대표 계정 호출
		UserVO rprsUserVO = entDAO.getUserRprs(entVO.getBzentyNo());
		
		if (rprsUserVO == null) {
			// 해당 업체의 대표 계정이 없는 경우
			throw processException("error.mail.noRprs");
		}
		
		if (useSttsCd.equals(CodeConst.USE_STATUS_USABLE)) {
			// 승인
			code = CodeConst.BIZMAIL_APPROVE;
		} else if (useSttsCd.equals(CodeConst.USE_STATUS_REJECT)) {
			// 반려
			code = CodeConst.BIZMAIL_REJECT;
		} else {
			throw processException("error.comm.notProcess");
		}
		
		params.put("context"  , entVO.getGsContext());
		params.put("userId"   , rprsUserVO.getUserId());
		params.put("userNm"   , rprsUserVO.getUserNm());
		params.put("toName"   , rprsUserVO.getUserNm());
		params.put("toAddress", rprsUserVO.getEmlAddr());
		
		// 메일 발송자 세팅
		params.put("gsUserNo", entVO.getGsUserNo());

		// 메일발송
		emailService.sendBizEmail(params, code);
		
	}
}