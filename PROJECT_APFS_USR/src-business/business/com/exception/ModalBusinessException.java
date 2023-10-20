package business.com.exception;

import commf.exception.BusinessException;

public class ModalBusinessException extends BusinessException {

	private static final long serialVersionUID = 8625483721884324630L;

	public ModalBusinessException(String message) {
		super(message);
	}

}
