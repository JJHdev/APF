package business.com.exception;

import commf.exception.BusinessException;

public class AjaxBusinessException extends BusinessException {

	private static final long serialVersionUID = 8625483721884324630L;

	public AjaxBusinessException(String message) {
		super(message);
	}

}
