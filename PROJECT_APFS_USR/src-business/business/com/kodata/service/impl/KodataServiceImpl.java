package business.com.kodata.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import business.com.CommConst;
import business.com.common.service.CommService;
import business.com.kodata.service.KodataAPI;
import business.com.kodata.service.KodataBizVO;
import business.com.kodata.service.KodataEntity;
import business.com.kodata.service.KodataService;
import common.base.BaseService;
import common.util.CommUtils;

/**
 * [서비스클래스] - KODATA ServiceImpl
 *
 * @class   : KodataServiceImpl
 * @author  :
 * @since   : 2023.09.06
 * @version : 1.0
 *
 *   수정일            수정자                             수정내용
 *  -------    --------    ---------------------------
 *
 */
@Service("KodataService")
public class KodataServiceImpl extends BaseService implements KodataService {

	@Autowired
    private CommService commService;
    
    @Override
    public KodataBizVO getKodata(String brno) throws Exception {
    	
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
    	
    	// 샘플로 사용할 경우
    	if (CommConst.isDev() && 
    		CommConst.IS_KODATA_SAMPLE) {
    		return loadKodataSample(brno);
    	}
    	else {
    		KodataBizVO data = KodataBizVO.builder().bzno(brno).build();
        	KodataAPI api = new KodataAPI(entity);
        	api.execute(data);
			return data;
    	}
    }
    
    private KodataBizVO loadKodataSample(String brno) throws Exception {
    	Map<String,Object> data = commService.viewSampleKodata(brno);
		if (data != null) {
			
			KodataBizVO kodataBizVO = KodataBizVO.builder().build();
    		// 맵데이터를 KodataBizVO에 담는다.
    		CommUtils.mapToBean(data, kodataBizVO);
			/*
			kodataBizVO.setKedcd    ((String)data.get("kedcd"));          // KED코드 : 기업식별ID
			kodataBizVO.setEnpNm    ((String)data.get("enp_nm"));          // 기업체명 : 정식기업체명 (예 : 엘지씨엔에스)
			kodataBizVO.setEstbDt   ((String)data.get("estb_dt"));          // 설립일자 : 법인의 설립일 또는 개인사업자의 영업개시일 (YYYYMMDD)
			kodataBizVO.setBzno	    ((String)data.get("bzno"));          // 사업자번호 : 본사 사업장의 사업자번호
			kodataBizVO.setLocAddra	((String)data.get("loc_addra"));          // 소재지우편번호주소 : 본사 소재지 우편번호 주소
			kodataBizVO.setLocAddrb	((String)data.get("loc_addrb"));          // 소재지_나머지주소 : 본사 소재지 우편번호 이하 주소
			kodataBizVO.setTelNo	((String)data.get("tel_no"));              // 전화번호 : 본사 전화번호
			kodataBizVO.setReperName((String)data.get("reper_name"));	          // 대표자명 : 수동추가
			*/
    		return kodataBizVO;
		}
		return null;
    }

    @Override
    public Map<String,Object> getEnp(String brno) {
    	
    	Map<String,Object> contents = new HashMap<String,Object> ();
    	
    	try {
    		Map<String,Object> data = commService.viewSampleKodata(brno);
    		contents.put("ENPlntr0000", data);
		} catch (Exception e) {
			// e.printStackTrace();
			// 230621 LHB
			logger.error("apikodata error : ", e);
		}
    	Map<String,Object> header = new HashMap<String,Object> ();
    	header.put("errcd", "00");
    	header.put("errtt", "00");
    	header.put("resptm", CommUtils.getCurrDateTime2());
    	
    	Map<String,Object> ked = new HashMap<String,Object> ();
    	ked.put("HEADER", header);
    	ked.put("CONTENTS", contents);
    	
    	Map<String,Object> result = new HashMap<String,Object> ();
    	result.put("KED", ked);
    	
    	return result;
    }
}
