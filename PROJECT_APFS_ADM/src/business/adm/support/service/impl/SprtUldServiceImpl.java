package business.adm.support.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import business.adm.CodeConst;
import business.adm.file.service.BizFileService;
import business.adm.file.service.BizFileVO;
import business.adm.invest.service.EntService;
import business.adm.invest.service.EntVO;
import business.adm.invest.service.impl.FundDAO;
import business.adm.support.service.SprtUldService;
import business.adm.support.service.SprtBizVO;
import business.adm.support.service.SupportVO;
import business.batch.service.LinkKodataService;
import business.com.CommConst;
import business.com.CommExcel;
import business.com.common.service.CommService;
import business.com.exception.ModalBusinessException;
import business.com.kodata.service.KodataAPI;
import business.com.kodata.service.KodataBizVO;
import business.com.kodata.service.KodataEntity;
import commf.exception.BusinessException;
import commf.paging.PaginatedArrayList;
import common.base.BaseService;
import common.file.FileDirectory;
import common.file.FileInfo;
import common.file.FileManager;
import common.util.CommUtils;

/**
 * [서비스클래스] - 운영관리-경영체데이터업로드 Service 구현 클래스
 * 
 * @class   : SprtBizServiceImpl
 * @author  : LHB
 * @since   : 2023.07.06
 * @version : 1.0
 *
 *   수정일       수정자                 수정내용
 *  --------   --------    ---------------------------
 * 23.07.06      LHB               First Coding.
 */

@Service("SprtUldService")
@SuppressWarnings({"all"})
public class SprtUldServiceImpl extends BaseService implements SprtUldService {

    // 업무첨부파일
    @Resource(name = "BizFileService")
    private BizFileService bizFileService;
    
    @Resource(name = "CommService")
    private CommService commService;
    
    @Resource(name = "SprtUldDAO")
    private SprtUldDAO sprtUldDAO;
    
    @Autowired
	protected EntService entService;
    
    @Autowired
    private LinkKodataService linkKodataService;
    
    @Resource(name = "fileManager")
	private FileManager fileManager;
	
    /**
     * 운영관리-경영체데이터업로드 페이징목록조회
     */
    @Override
    public PaginatedArrayList listSprtUld(SprtBizVO sprtBizVO, int currPage, int pageSize) throws Exception {
    	return sprtUldDAO.listSprtUld(sprtBizVO, currPage, pageSize);
    }

    /**
     * 운영관리-경영체데이터업로드 목록조회
     */
    @Override
    public List listSprtUld(SprtBizVO sprtBizVO) throws Exception {
    	return sprtUldDAO.listSprtUld(sprtBizVO);
    }

    /**
     * 운영관리-경영체데이터업로드 상세조회
     */
	@Override
	public SprtBizVO viewSprtUld(SprtBizVO sprtBizVO) throws Exception {
		return sprtUldDAO.viewSprtUld(sprtBizVO);
	}

    /**
     * 운영관리-경영체데이터업로드 등록
     */
	@Override
	public HashMap saveSprtUld(SprtBizVO sprtBizVO, List<FileInfo> files) throws Exception {
		HashMap resMap = new HashMap();
		
		if (sprtBizVO == null) {
			throw processException("error.comm.notTarget");
		}
		
		String mode = sprtBizVO.getMode();
		
		int ret = 0;
		
		if (CommConst.INSERT.equals(mode)) {
			// 파일 업로드(등록)
			
			// 파일 물리적 경로 포함 명칭
			FileInfo file = files.get(0);
			String fullName = file.getFilePath() + file.getSaveName();
			
			sprtBizVO.setFileNm(file.getFileName());
			sprtBizVO.setFilePath(file.getFilePath());
			sprtBizVO.setStrgFileNm(file.getSaveName());
			
			// 엑셀로드타입C
			CommExcel ce = CommExcel.SPRT;
			
			// 엑셀로드된 List<Map> 데이터
			List<Map> dataList = ce.parseData(fullName);
			
			if (dataList.size() < 1) {
				throw new ModalBusinessException(message.getMessage("error.comm.notTarget"));
			}
			
			List entList			= new ArrayList();
			List mappingFailList	= new ArrayList();
			
			int sn	= 1;
			long uldNo = 0;
			
			for (Map data : dataList) {
				SprtBizVO model = new SprtBizVO();
	    		CommUtils.mapToBean(data,  model);
	    		
	    		// 23.09.19 LHB 엑셀상의 유관기관번호 저장
	    		model.setCrdnsBzentyNoPrev(model.getCrdnsBzentyNo());
	    		
	    		// 유관기관 업체번호 및 업체명 세팅
	    		Map crdnsMap = commService.getCode(CodeConst.CRDNS_SE, model.getCrdnsBzentyNo());
	    		model.setCrdnsBzentyNo((String) crdnsMap.get("cdCn"));
	    		model.setCrdnsBzentyNm((String) crdnsMap.get("cdNm"));
	    		
				
				// 필수값 입력 체크
				List<String> requireErrors = ce.validateErrors(data);
				if (requireErrors != null) {
					throw processException("error.adm.sprt.notExistExcel", new String[] { String.valueOf(sn), requireErrors.toString() });
				}
				// 최대길이 검증
	    		List<String> lengthErrors = ce.validateLengthErrors(data);
	    		if (lengthErrors != null) {
	    			throw processException("error.adm.sprt.overMaxLength", new String[] { String.valueOf(sn), lengthErrors.toString() });
	    		}
	    		
	    		// 입력된 업체명
	    		String bzentyNm = model.getBzentyNm();
	    		// 사업자번호 안에 있는 하이픈 삭제
	    		String brno = model.getBrno().replace("-", "");
	    		model.setBrno(brno);
				
	    		// model의 사업자 번호의 업체정보가 TB_ENT에 있는지 확인
				EntVO temp = EntVO.builder()
						.brno(brno)
						.build();
				EntVO entVO = entService.viewEnt(temp);
				
				if (entVO == null) {
					// TB_ENT에 해당 사업자번호가 없는 경우
					KodataEntity entity = KodataEntity.builder()
			    			.url      (CommConst.KODATA_API_URL)
			    			.path     (CommConst.KODATA_API_PATH) 
			    			.format   (CommConst.KODATA_API_FORMAT)
			    			.process  (CommConst.KODATA_API_PROCESS)
			    			.pidagryn (CommConst.KODATA_API_PIDAGRYN)
			    			.userid   (CommConst.KODATA_API_USERID)
			    			.jmno     (CommConst.KODATA_API_JMNO)
			    			.bzno     (brno)
			    			.build();
					KodataBizVO kodataBizVO = KodataBizVO.builder()
												.bzno(brno)
												.build();
					
					boolean isExist = false;
					
			    	if (CommConst.isDev() && CommConst.IS_KODATA_SAMPLE) {
			    		// 샘플로 사용할 경우
			    		KodataBizVO result = loadKodataSample(kodataBizVO);
			    		if (result != null) {
			    			isExist = true;
			    		}
			    	} else {
				    	try {
				        	KodataAPI api = new KodataAPI(entity);
				        	api.execute(kodataBizVO);
				        	
				        	isExist = true;
				    	} catch (BusinessException be) {
				    		isExist = false;
				    	}
			    	}
			    	
			    	if (isExist) {
			    		// KODATA 에 존재하는 경우
			    		model.setBzentyNo(null);
			    		entList.add(model);
			    	} else {
			    		// KODATA 에 없는 데이터인 경우
			    		String directInptYn = CommUtils.nvlTrim(model.getDirectInptYn());
			    		if (CommUtils.isNotEmpty(directInptYn) && directInptYn.equals(CommConst.YES)) {
			    			model.setBzentyNo(null);
			    			model.setDirectInptYn(CommConst.YES);
				    		entList.add(model);
			    		} else {
			    			HashMap fail = new HashMap();
			    			fail.put("rowNo", sn);
			    			fail.put("brno" , model.getBrno());
			    			// 매핑 실패
			    			mappingFailList.add(fail);
			    		}
			    	}
				} else {
					// TB_ENT에 해당 사업자번호가 있는 경우
					model.setBzentyNo(entVO.getBzentyNo());
					entList.add(model);
				}
				
				sn++;
			}
			
			List newEntList = new ArrayList<>();
			sn = 0;
			
			if (mappingFailList.size() > 0) {
				// 매핑 실패건이 1건이라도 존재하는 경우
				resMap.put("Code", -1);
				resMap.put("Message", "업로드 실패");
				resMap.put("Rows", mappingFailList);
			} else {
				// 업로드 이력 등록 및 업로드 번호 채번
				ret = sprtUldDAO.regiSprtUld(sprtBizVO);
				if (ret > 0) {
					uldNo = sprtBizVO.getUldNo();
				} else {
					// 업로드 번호를 채번하지 못한 경우
					throw processException("error.comm.notTarget");
				}
				
				for (int i=0 ; i<entList.size(); i++) {
					SprtBizVO data = (SprtBizVO) entList.get(i);
					String brno = data.getBrno();
					
					EntVO temp = EntVO.builder()
							.brno(brno)
							.build();
					
					// 업체 조회
					EntVO entVO = entService.viewEnt(temp);
					
					if (entVO == null || CommUtils.isEmpty(CommUtils.nvlTrim(entVO.getBzentyNo()))) {
						// 업체번호가 없는 경우
		    			temp.setMode(CommConst.INSERT);
						temp.setBzentyNm(data.getBzentyNm());
						temp.setBzentySeCd(CodeConst.ENT_EBZ);
						// 승인 상태로 등록
						temp.setUseSttsCd(CodeConst.APRV_STTS_1);
						temp.setAct(CommConst.ACT_CRDNS_FILE);
						// 강제 업로드 여부 세팅
						temp.setDirectInptYn(data.getDirectInptYn());
						
						entService.saveEnt(temp);
						
						newEntList.add(new String[] { brno, brno, "", data.getBzentyNm(), "1", "N" });
						
						entVO = entService.viewEnt(temp);
					}
					
					// 업체번호, 업체명 세팅
					data.setBzentyNo(entVO.getBzentyNo());
					data.setBzentyNm(entVO.getBzentyNm());
					data.setUldNo(uldNo);
					data.setRlsYn(CommConst.YES);
					
					// 작성자 세팅
					data.setGsUserNo(sprtBizVO.getGsUserNo());
					
					ret = sprtUldDAO.regiSprtBiz(data);
					
					// 농금원이면서 항목명3이 투자받은금액 일 경우 투자금액 등록
					if (data.getCrdnsBzentyNoPrev().equals("01") && data.getArtclNm3().equals("투자받은금액")) {
						String irNo = sprtUldDAO.viewIrNo(data); // IR번호 가져오기
						
						if (irNo == null) {
							// TB_ENT_IR 테이블에 정보 없는 경우 등록
							ret = sprtUldDAO.regiEntIr(data);
						} else {
							data.setIrNo(irNo);
						}
						
						data.setInvtSeCd(CodeConst.INVT_AMT_FUND);
						data.setInvtYr(data.getBizYr());
						data.setInvtAmt(data.getArtclCn3());
						
						// TB_ENT_INVT_AMT 테이블 데이터 등록
						sprtUldDAO.regiEntInvtAmt(data);
					}
					
					sn++;
				}
				
				// SFTP 업로드
				if (newEntList.size() > 0) {
					linkKodataService.writeKodata(null, newEntList);
				}
				
				saveFile(sprtBizVO, files);
			}
		}

        return resMap;
	}
	
	
	
	/**
     * 운영관리-경영체데이터업로드-경영체 목록 페이징목록조회
     */
    @Override
    public PaginatedArrayList listSprtBiz(SprtBizVO sprtBizVO, int currPage, int pageSize) throws Exception {
    	return sprtUldDAO.listSprtBiz(sprtBizVO, currPage, pageSize);
    }

    /**
     * 운영관리-경영체데이터업로드-경영체 목록 목록조회
     */
    @Override
    public List listSprtBiz(SprtBizVO sprtBizVO) throws Exception {
    	return sprtUldDAO.listSprtBiz(sprtBizVO);
    }
    
    public String saveSprtBiz(SprtBizVO sprtBizVO, long[] arrSn) throws Exception {
		int ret = 0;
		SprtBizVO data = new SprtBizVO();
		
		for(long sn : arrSn) {
			sprtBizVO.setSn(sn);
			data = sprtUldDAO.viewSprtBiz(sprtBizVO);
			
			if(data != null) { 
				sprtBizVO.setDelYn("Y");
				ret += sprtUldDAO.updtSprtBiz(sprtBizVO);
			}
		}
		
		//저장결과 반환
		if(ret > 0)
			return message.getMessage("prompt.success");
		else
			throw processException("error.comm.notProcess");
	}
    
    private void saveFile(SprtBizVO sprtBizVO, List<FileInfo> saveFiles) throws Exception {
    	if (sprtBizVO == null) {
    		throw processException("error.comm.notTarget");
    	}
        
    	// 경영체 데이터 첨부파일 경로
    	FileDirectory d = FileDirectory.ENT;
    	
    	StringBuilder filePath = new StringBuilder();
        filePath.append(d.getPath()            +"/");
        filePath.append("sprtbiz"              +"/");
        filePath.append(sprtBizVO.getUldNo()   +"/");
        filePath.append(CommUtils.getCurDateString());
        
        // 경영체 데이터 등록, 수정
        if (CommUtils.isNotEmptyList(saveFiles)) {
        	for (FileInfo f : saveFiles) {
        		// 파일경로 정의
        		f.setFilePath(filePath.toString());
        		sprtBizVO.setFilePath(filePath.toString());
        		// 첨부파일 실제경로로 이동처리
        		fileManager.saveFile(d, f);
                // TB_SPRT_BIZ 파일 경로 수정
        		sprtUldDAO.updtSprtUld(sprtBizVO);
        	}
        }
    }
    
	// KODATA API 연동문제로 테스트가 필요한 경우 샘플을 반환한다.
    // 개발시에만 사용되며 운영 중엔 사용되지 않는다.
    private KodataBizVO loadKodataSample(KodataBizVO kodataBizVO) throws Exception {
    	Map<String,Object> data = commService.viewSampleKodata(kodataBizVO.getBzno());
		if (data != null) {
    		// 맵데이터를 KodataBizVO에 담는다.
    		CommUtils.mapToBean(data, kodataBizVO);
    		return kodataBizVO;
		}
		return null;
    }
	
}