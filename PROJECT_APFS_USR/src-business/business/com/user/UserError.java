package business.com.user;

public enum UserError {

	SUCCESS   ("OK", "검증 성공"),
	// 사용자 정보가 없음.
	NOTFOUND  ("E1", "사용자정보가 올바르지 않습니다."),
	// 패스워드 틀림.
	NOTPASSWD ("E2", "사용자정보가 올바르지 않습니다."),
	// 사용하지 않는 아이디
	NOTUSED   ("E3", "사용하지 않는 사용자입니다. 관리자에게 문의 바랍니다."),
	// 승인되지 않은 아이디
	NOTACCESS ("E4", "승인되지 않은 사용자입니다."),
	// 접속불가능IP
	REJECT    ("E5", "접속 불가능한 IP 입니다. 관라자에게 문의 바랍니다."),
	// 로그인 5회이상 실패
	EXPIRED   ("E7", "해당 사용자는 로그인 5회이상 실패로 로그인을 할 수 없습니다."),

	// 이미 가입된 개인회원
	EXIST_USER  ("E8", "이미 가입된 회원입니다. 로그인하세요."),
	// 이미 가입된 기업회원
	EXIST_EMAIL ("E9", "이미 해당 이메일로 가입된 회원이 있습니다. 로그인하세요."),
	// 승인되지 않은 아이디
	NOTAPPROVE  ("EA", "최초로 가입한 대표계정은 농금원 관리자 승인 이후 로그인이 가능합니다.<br>검토 후 빠르게 승인처리 해드리겠습니다.<br>(반려 또는 승인된 경우 가입 신청하신 이메일이나 유선을 통해 안내드리도록 하겠습니다.)"),
	// 반려된 기업회원
	REJECT_ENT  ("ER", "가입신청시 입력한 정보 오류로 가입이 반려되었습니다.<br>정보를 수정하여 다시 신청해 주시기 바랍니다."),
	// 네이버 로그인에서 오류로 넘어온 경우
	ERROR_NAVER ("EN", "네이버 로그인이 정상 처리되지 않았습니다."),
	// 승인되지 않은 아이디
	ERROR_KAKAO ("EK", "카카오 로그인이 정상 처리되지 않았습니다."),
	// ???
	UNKNOWN   ("E6", "알수 없는 오류가 발생하였습니다.")
	;
	 
	private String flag;
	private String message;
	
	UserError(String flag, String message) {
		this.flag = flag;
		this.message = message;
	}
	
	public static UserError get(String flag) {
		for (UserError e : values()) {
			if (e.getFlag().equals(flag))
				return e;
		}
		return null;
	}

	public String getFlag() {
		return flag;
	}

	public String getMessage() {
		return message;
	}
	
	public boolean isSuccess() {
		return (this == SUCCESS);
	}
	public boolean isFailure() {
		return (this != SUCCESS);
	}
	
	
}
