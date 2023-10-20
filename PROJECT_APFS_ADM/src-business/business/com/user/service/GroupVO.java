package business.com.user.service;

import java.util.ArrayList;
import java.util.List;

import business.com.CommConst;
import business.adm.CodeConst;
import common.base.BaseModel;
import common.util.CommUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * [VO클래스] - 그룹관리 모델 클래스
 *
 * @class   : GroupVO
 * @author  : LSH
 * @since   : 2023.06.14
 * @version : 1.0
 *
 *   수정일     수정자              수정내용
 *  -------    --------    ---------------------------
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class GroupVO extends BaseModel {
	
    // 메뉴정보
    //---------------------------------
    // 메뉴ID
    private String menuId;
    // 메뉴명
    private String menuNm;
    // 타겟URL
    private String trgtUrl;
    // 시스템권한정보
    private String sysAuthrtInfo;
    
    // 권한정보
    //---------------------------------
    // 그룹권한코드
    private String groupAuthrtCd;
    
    // 대표정보
    //---------------------------------
    // 대표여부
    private String rprsYn;

    // 공통정보
    //---------------------------------
    // 권한ID
    private String roleId;
    // 업체번호
    private String bzentyNo;
    // 사용자번호
    private String userNo;
    // 등록자번호
    private String rgtrNo;
    // 등록일시
    private String regDttm;
    // 등록일자
    private String regDate;
    // 수정자번호
    private String mdfrNo;
    // 수정일시
    private String mdfDttm;
    // 수정일자
    private String mdfDate;
    
    // 화면구분코드
    private String pageCd;
    // 화면구분명칭
    private String pageNm;
    // 승인여부
    private String accessYn;
    // 그룹코드
    private String groupCd;
    // 멤버이름검색
    private String srchName;
    
    // 세션사용자번호
    private String gsUserNo;
    // 세션사용자권한
    private String gsRoleId;
    // 세션업체번호
    private String gsBzentyNo;
    
    // 권한정보
    private String menu00; //기업정보 
    private String menu01; //IR작성하기
    private String menu02; //매칭설정
    private String menu03; //신청내역
    private String menu04; //IR검토의견등록

    private List<GroupVO> saveList;
    // 저장용 그룹코드
    private String saveGroupCd;
    
	// 저장데이터에 맞게 REUILD
    public void rebuildProperties() {
    	
    	String mode = getMode();
    	String act  = getAct ();
    	
    	// 삭제인 경우 REBUILD
    	if (CommUtils.isEqual(CommConst.DELETE, mode)) {
    		for (GroupVO item : saveList) {
    			// 항목별 세션정보 담기
    			item.setGsUserNo  (getGsUserNo  ());
    			item.setGsRoleId  (getGsRoleId  ());
    			item.setGsBzentyNo(getGsBzentyNo());
    		}
    	}
    	// 권한설정저장인 경우 REBUILD
    	else if (CommUtils.isEqual(CommConst.ACT_GROUP_ROLE, act)) {

    		List<GroupVO> list = new ArrayList<GroupVO> ();
    		
    		for (GroupVO item : saveList) {
    			
    			String[] arr = new String[] {
					item.getMenu00(),
					item.getMenu01(),
					item.getMenu02(),
					item.getMenu03(),
					item.getMenu04()
    			};
    			for (int i = 0; i < arr.length; i++) {
    				String auth = arr[i];
	    			if (CommUtils.isEmpty(auth))
	    				continue;
	    			// 권한별 항목 생성
    				list.add(GroupVO.builder()
   						.gsUserNo     (getGsUserNo   ())
   						.gsRoleId     (getGsRoleId   ())
   						.gsBzentyNo   (getGsBzentyNo ())
						.userNo       (item.getUserNo())
						.menuId       (CodeConst.GROUP_AUTH_MENUS[i])
						.groupAuthrtCd(auth)
						.build()
    				);
    			}
    		}
    		// 권한별 생성된 목록으로 치환
    		setSaveList(list);
    	}
	}
}
