package business.com.common.service;

import common.base.BaseModel;
import fr.opensagres.xdocreport.document.images.IImageProvider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * [VO클래스] - PDF 데이터샘플 모델 클래스
 *
 * @class   : SampleVO
 * @author  : LSH
 * @since   : 2023.03.08
 * @version : 1.0
 *
 *   수정일     수정자              수정내용
 *  -------    --------    ---------------------------
 *
 */
@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class SampleVO extends BaseModel {
	
	// 사용자번호
	private String userNo;
	// 사용자ID
	private String userId;
	// 이름
	private String userNm;
	// 이미지
	private final IImageProvider photo;
	
}