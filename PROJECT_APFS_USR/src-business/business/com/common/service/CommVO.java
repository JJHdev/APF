package business.com.common.service;

import common.base.BaseModel;

/**
 * [VO클래스] - 공통 VO
 *
 * @class   : CommVO
 * @author  : ntarget
 * @since   : 2023.03.08
 * @version : 1.0
 *
 *   수정일     수정자               수정내용
 *  -------    --------    ---------------------------
 *
 */
public class CommVO extends BaseModel {

    private String srchNm           = null;
    private String popSrchNm        = null;    // 팝업 검색어
    private String popSrchOrgId     = null;    // 팝업 검색 기관ID
    private String callback         = null;



    public String getSrchNm() {
        return srchNm;
    }

    public void setSrchNm(String srchNm) {
        this.srchNm = srchNm;
    }

    public String getPopSrchNm() {
        return popSrchNm;
    }

    public void setPopSrchNm(String popSrchNm) {
        this.popSrchNm = popSrchNm;
    }

    public String getPopSrchOrgId() {
        return popSrchOrgId;
    }

    public void setPopSrchOrgId(String popSrchOrgId) {
        this.popSrchOrgId = popSrchOrgId;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

}
