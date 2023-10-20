package business.com.common.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import business.adm.CodeConst;
import business.com.CommConst;
import business.com.accesslog.AccessControlService;
import business.com.common.service.CommService;
import business.com.common.service.EmailService;
import business.com.common.service.EmailVO;
import commf.exception.BusinessException;
import common.base.BaseService;
import common.file.FileInfo;
import common.util.CommUtils;

/**
 * [서비스클래스] - 이메일 전송 구현 클래스
 *
 * @class   : CommServiceImpl
 * @author  :
 * @since   : 2023.03.08
 * @version : 1.0
 *
 *   수정일            수정자                             수정내용
 *  -------    --------    ---------------------------
 *
 */
@Service("EmailService")
public class EmailServiceImpl extends BaseService implements EmailService {

	@Autowired
	private SpringTemplateEngine templateEngine;
	
	@Autowired
	private JavaMailSenderImpl emailSender;

    @Autowired
    private AccessControlService accessControlService;
	
    @Resource(name = "CommService")
	private CommService commService;

	@Override
	public boolean sendEmail(EmailVO emailVO) throws Exception {
		return sendEmail(emailVO, null);
	}

	@Override
	public boolean sendEmail(EmailVO emailVO, List<FileInfo> files) throws Exception {

	   	MimeMessage       message = emailSender.createMimeMessage();
        MimeMessageHelper helper  = new MimeMessageHelper(message, true, CommConst.SYSTEM_CHARSET);
        //메일 제목 설정 (B는 Base64, Q는 quoted-printable)
        helper.setSubject(
        	MimeUtility.encodeText(
        		emailVO.getSubject(), 
        		CommConst.SYSTEM_CHARSET, 
        		CommConst.EMAIL_BASE64_ENCODE
        	)
        );
        //송신자 설정
        helper.setFrom(
        	new InternetAddress(
        		emailVO.getFromAddress(), 
        		emailVO.getFromName(), 
        		CommConst.SYSTEM_CHARSET
        	)
        );
        //수신자 설정
        helper.setTo(
        	new InternetAddress(
        		emailVO.getToAddress(), 
        		emailVO.getToName(), 
        		CommConst.SYSTEM_CHARSET
        	)
        );
        if (CommUtils.isNotEmpty(emailVO.getCcName())) {
            //참조자 설정
            helper.setCc(
            	new InternetAddress(
            		emailVO.getCcAddress(), 
            		emailVO.getCcName(), 
            		CommConst.SYSTEM_CHARSET
            	)
            );
        }
        // 템플릿 형식의 컨텐츠이면
        if (emailVO.isUseTemplate()) {
	        //템플릿에 전달할 데이터 설정
	        Context ctx = new Context();
	        ctx.setVariables(emailVO.getData());
	        //템플릿 프로세스
	        String html = templateEngine.process(emailVO.getTemplate(), ctx);
	        helper.setText(html, true);
	        //파싱된 내용을 메일객체에 저장한다.
	        emailVO.setContent(html);
	        
	        //템플릿에 들어가는 이미지 cid로 삽입
	        if (!CollectionUtils.isEmpty(emailVO.getImages())) {
	        	for (Map<String,String> image : emailVO.getImages()) {
	                helper.addInline(
	                	(String)image.get("label"), 
	                	new FileSystemResource(
	                    	new File(emailVO.getImageDirectory().getRealName((String)image.get("name"))
	                    )
	                ));
	        	}
	        }
        }
        // 일반 컨텐츠이면
        else {
	        helper.setText(emailVO.getContent(), emailVO.isUseHtml());
        }
        //첨부파일
        if (!CollectionUtils.isEmpty(files)) {
        	
            for (FileInfo f : files) {
                FileSystemResource fileSystemResource = new FileSystemResource(new File(f.getFullFile()));
                helper.addAttachment(
                	MimeUtility.encodeText(
               			f.getFileName(), 
               			CommConst.SYSTEM_CHARSET, 
               			CommConst.EMAIL_BASE64_ENCODE
                	), 
                	fileSystemResource
                );
            }
        }
        // 전송실행이 true인 경우에만 실제 전송됨
        if (CommConst.EMAIL_EXEC_ENABLE) {
        	try {
	            //메일 보내기
	            emailSender.send(message);
	            //발송상태 표시
	            emailVO.setSendCd(CommConst.EMAIL_SEND_SUCCESS);
	            //메일발송 이력 저장
	            accessControlService.regiEmailLog(emailVO);
        	} catch (NullPointerException e) {
        		emailVO.setSendCd(CommConst.EMAIL_SEND_FAILURE);
                //메일발송 이력 저장
                accessControlService.regiEmailLog(emailVO);
                throw new BusinessException("메일전송시 오류가 발생하였습니다.", e);
        	} catch (Exception e) {
        		emailVO.setSendCd(CommConst.EMAIL_SEND_FAILURE);
                //메일발송 이력 저장
                accessControlService.regiEmailLog(emailVO);
                throw new BusinessException("메일전송시 오류가 발생하였습니다.", e);
        	}
        } else {
            //메일발송 이력 저장
    		emailVO.setSendCd(CommConst.EMAIL_SEND_TEST);
        	accessControlService.regiEmailLog(emailVO);
        }

		return true;
	}

	/**
	 * 2023.07.31 LSH 업무용 메일발송
	 * 
	 * @param params.context      컨텍스트
	 * @param params.toName       받는사람이름
	 * @param params.toAddress    받는사람이메일
	 * @param params.userNm       회원이름  : 회원가입/업체승인
	 * @param params.bzentyNm     업체명    : 미팅신청/회원가입
	 * @param params.invtBzentyNm 투자업체명: 미팅신청
	 * @param cdId                업무메일구분코드
	 */
	@Override
	public boolean sendBizEmail(Map<String, String> params, String cdId) throws Exception {
		
		// 메일발송실행 비활성화인 경우
		if (!CommConst.EMAIL_SEND_ENABLE)
			return true;
		
		if (params.get("context") == null)
			throw new BusinessException("context 항목이 설정되지 않았습니다.");
		if (params.get("toName") == null)
			throw new BusinessException("받는사람 이름[toName] 항목이 설정되지 않았습니다.");
		if (params.get("toAddress") == null)
			throw new BusinessException("받는사람 이메일[toAddress] 항목이 설정되지 않았습니다.");
		
		// 플랫폼명 기본설정
		params.put("platform", message.getMessage("title.mail.subject"));
		// HOME URL 기본설정
		params.put("homeUrl", message.getMessage("title.mail.url"));
		// 보내는사람 이름 기본설정
		params.put("fromName", message.getMessage("title.mail.fromName"));
		// 보내는사람 이메일L 기본설정
		params.put("fromAddress", message.getMessage("title.mail.fromAddr"));
		
		// 미팅신청 업무메일정보 조회
		Map<String,Object> code = commService.getCode(CodeConst.BIZMAIL_SE, cdId); 
		if (code == null)
			return false;
		
		// 메일제목
		String subject = (String)code.get("cdNm");
		// 메일내용
		String content = (String)code.get("cdCn");
		
		// 변수바인딩 ({platform},{homeUrl},{userNm},{bzentyNm},{invtBzentyNm})
		subject = CommUtils.mergeContent(subject, params);
		content = CommUtils.mergeContent(content, params);

		// 업무메일 발송
		EmailVO emailVO = EmailVO.builder()
				.useTemplate (true)
				.gsContext   (params.get("context"    ))
				.toName      (params.get("toName"     ))
				.toAddress   (params.get("toAddress"  ))
				.fromName    (params.get("fromName"   ))
				.fromAddress (params.get("fromAddress"))
				.template    (CommConst.EMAIL_TEMPLATE_FILE)
				.subject     (subject)
				.content     (content)
				.build       ();

		// 내용생성
		Map<String,Object> data = new HashMap<String,Object> ();
		data.put("name"   , emailVO.getToName ());
		data.put("subject", emailVO.getSubject());
		data.put("content", emailVO.getContent());
		data.put("context", emailVO.getGsContext());
		// FOOTER 정보
		String footerImage = emailVO.getGsContext()+"/images/app/footer-logo-sub.png";
		
		data.put("footerAddr", message.getMessage("title.com.footer.zip")+" "+message.getMessage("title.com.footer.addr"));
		data.put("footerTel" , message.getMessage("title.com.footer.tel"));
		data.put("footerFax" , message.getMessage("title.com.footer.fax"));
		data.put("footerMail", message.getMessage("title.com.footer.mail"));
		data.put("copyright" , message.getMessage("title.com.footer.copyright"));
		data.put("footerImg" , footerImage);
		logger.debug("FOOTER IMAGE =>"+footerImage);
		
		emailVO.setData(data);
		emailVO.setGsUserNo(params.get("gsUserNo"));
		// 메일발송
		return sendEmail(emailVO);
	}
}
