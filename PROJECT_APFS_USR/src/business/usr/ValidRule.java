package business.usr;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.validation.Errors;

import common.util.CommUtils;

public enum ValidRule {
	
	NEED      ("required"   , "필수항목", "필수값입니다."),
	MINL      ("minlength"  , "최소길이", "{len}자 이내여야 합니다."),
	MAXL      ("maxlength"  , "최대길이", "{len}자 이상이어야 합니다."),
	SIZE      ("size"       , "크기범위", "{min} 보다 크거나 같고, {max}보다 작거나 같아야 합니다."),
	EQUAL     ("equal"      , "동일비교", "{val} 값과 같아야 합니다."),
	DATE      ("date"       , "날짜"    , "형식에 맞지 않습니다.", "^\\d{4}((0\\d)|(1[012]))(([012]\\d)|3[01])$"),
	PHONE     ("phone"      , "전화번호", "형식에 맞지 않습니다.", "^(01[016789]{1}|02|0[3-9]{1}[0-9]{1})([0-9]{3,4})([0-9]{4})$"),
	EMAIL     ("email"      , "이메일"  , "형식에 맞지 않습니다.", "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$"),
	BZNO      ("bzno"       , "사업자등록번호", "형식에 맞지 않습니다.", "^\\d{10}$"),
	CRNO      ("crno"       , "법인등록번호"  , "형식에 맞지 않습니다.", "^\\d{13}$"),
	URL       ("url"        , "URL"     , "형식에 맞지 않습니다.", "^(((http(s?))\\:\\/\\/)?)([0-9a-zA-Z\\-]+\\.)+[a-zA-Z]{2,6}(\\:[0-9]+)?(\\/\\S*)?"),
	NUMBER    ("number"     , "숫자"    , "숫자로만 입력하셔야 합니다.", "^\\d+$"),
	SXDST     ("sxdst"      , "성별"    , "성별 코드값이 맞지 않습니다.", new String[] {"M","F"}),
	GROUPCD   ("groupcd"    , "그룹코드", "형식에 맞지 않습니다 (영문 대문자+숫자 10자리).", "^[0-9A-Z]{10}$"),
	PSWD      ("pswd"       , "비밀번호", "형식에 맞지 않습니다 (영대소문자+숫자+특수문자 조합 8자이상 30자이내).", "^(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=~*_-`<>,/\\{\\}\\[\\]\\:\\;\\\"\\'\\\\\\|\\.]).{8,30}$"),
	;
	
	private String code;
	private String name;
	private String message;
	private String regex;
	private String[] matches;
	
	private ValidRule(String code, String name, String message) {
		this.code = code;
		this.name = name;
		this.message = message;
	}
	private ValidRule(String code, String name, String message, String regex) {
		this(code, name, message);
		this.regex = regex;
	}
	private ValidRule(String code, String name, String message, String[] matches) {
		this(code, name, message);
		this.matches = matches;
	}

	public static ValidRule get(String code) {
		for (ValidRule rule : values()) {
			if (code.equals(rule.code))
				return rule;
		}
		return null;
	}
    
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@SuppressWarnings("rawtypes")
	public void validate(Errors errors, Object object, String field, String name) {
		
		Object value = CommUtils.getMethodValue(object, field);
		
		if ("required".equals(code)) {
			if (value instanceof List) {
				if (CommUtils.isEmptyList((List)value))
					errors.reject(field, getPostWord(name,"은","는")+" "+getMessage());
			}
			else {
				if (CommUtils.isEmpty(value))
					errors.reject(field, getPostWord(name,"은","는")+" "+getMessage());
			}
		}
		else if ("sxdst".equals(code)) {
			if (CommUtils.isEmpty(value))
				return;
			if (!CommUtils.exist(matches, (String)value))
				errors.reject(field, name+"의 "+getMessage());
		}
		else if (CommUtils.exist(new String[] {"number","url","date","phone","email","bzno","crno","groupcd","pswd"}, code)) {
			if (CommUtils.isEmpty(value))
				return;
			if (!validRegex(value.toString())) {
				errors.reject(field, getPostWord(name,"이","가")+" "+getMessage());
			}
		} 
	}
	
	public void validate(Errors errors, Object object, String field, String name, String val) {
		
		Object value = CommUtils.getMethodValue(object, field);
		
		if (CommUtils.isEmpty(value))
			return;
		
		if ("equal".equals(code)) {
			if (!value.toString().equals(val))
				errors.reject(field, getPostWord(name,"은","는")+" "+CommUtils.replace(getMessage(), "{val}", val));
		}
	}
	
	public void validate(Errors errors, Object object, String field, String name, int len) {
		
		Object value = CommUtils.getMethodValue(object, field);

		if (CommUtils.isEmpty(value))
			return;
		
		if ("minlength".equals(code)) {
			if (value.toString().length() < len)
				errors.reject(field, getPostWord(name,"은","는")+" "+CommUtils.replace(getMessage(), "{len}", String.valueOf(len)));
		}
		else if ("maxlength".equals(code)) {
			if (value.toString().length() > len)
				errors.reject(field, getPostWord(name,"은","는")+" "+CommUtils.replace(getMessage(), "{len}", String.valueOf(len)));
		}
	}

	public void validate(Errors errors, Object object, String field, String name, int min, int max) {
		
		Object value = CommUtils.getMethodValue(object, field);

		if (CommUtils.isEmpty(value))
			return;
		
		if ("size".equals(code)) {
			if (value.toString().length() < min || value.toString().length() > max ) {
				String msg = getMessage();
				msg = CommUtils.replace(msg, "{min}", String.valueOf(min));
				msg = CommUtils.replace(msg, "{max}", String.valueOf(max));
				errors.reject(field, getPostWord(name,"은","는")+" "+msg);
			}
		}
	}

    private boolean validRegex(String number) {
    	if (CommUtils.isEmpty(regex))
    		return false;
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(number);
		return matcher.matches();
    }

	/**
	 * 한글 조사 연결 (을/를,이/가,은/는,로/으로)
	 * 1. 종성에 받침이 있는 경우 '을/이/은/으로/과'
	 * 2. 종성에 받침이 없는 경우 '를/가/는/로/와'
	 * 3. '로/으로'의 경우 종성의 받침이 'ㄹ' 인경우 '로'
	 * 참고 1 : http://gun0912.tistory.com/65 (소스 참고)
	 * 참고 2 : http://www.klgoodnews.org/board/bbs/board.php?bo_table=korean&wr_id=247 (조사 원리 참고)
	 * @param name
	 * @param firstValue
	 * @param secondValue
	 * @return
	 */
	public String getPostWord(String str, String firstVal, String secondVal) {
		try {
			char laststr = str.charAt(str.length() - 1);
			// 한글의 제일 처음과 끝의 범위밖일 경우는 오류
			if (laststr < 0xAC00 || laststr > 0xD7A3) {
			    return str;
			}
	
			int lastCharIndex = (laststr - 0xAC00) % 28;
	
			// 종성인덱스가 0이상일 경우는 받침이 있는경우이며 그렇지 않은경우는 받침이 없는 경우
			if(lastCharIndex > 0) {
				// 받침이 있는경우
				// 조사가 '로'인경우 'ㄹ'받침으로 끝나는 경우는 '로' 나머지 경우는 '으로'
				if(firstVal.equals("으로") && lastCharIndex == 8) {
					str += secondVal;
				} else {
					str += firstVal;
				}
			} else {
				// 받침이 없는 경우
				str += secondVal;
			}
		} catch (NullPointerException e) {
			Logger.getLogger(ValidRule.class.getName()).error("error : ", e);
		} catch (Exception e) {
			Logger.getLogger(ValidRule.class.getName()).error("error : ", e);
		}

		return str;
	}

}
