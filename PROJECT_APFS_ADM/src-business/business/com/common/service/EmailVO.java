package business.com.common.service;

import java.util.List;
import java.util.Map;

import common.base.BaseModel;
import common.file.FileDirectory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * [VO클래스] - Email 모델 클래스
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
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class EmailVO extends BaseModel {
	
	private String gsContext;
	private String gsUserNo;
	
	private boolean useTemplate;
	private boolean useHtml;
	
	private String toName;
	private String toAddress;

	private String ccName;
	private String ccAddress;
	
	private String fromName;
	private String fromAddress;
	
	private String subject;
	
	private String content;

	private String sendDt;
	private String sendCd;
	
	// 템플릿형식일 경우 템플릿파일명
	private String template;
	
	// 템플릿형식일 경우 템플릿파일에 표시될 데이터
	private Map<String, Object> data;
	
	// 템플릿형식일 경우 템플릿파일에 표시되는 이미지 목록
	// Map : label, name
	private List<Map<String,String>> images;

	// 이미지파일 저장경로
	private FileDirectory imageDirectory;
}
