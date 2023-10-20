package business.com.kodata.service;

import java.util.Map;

/**
 * [서비스인터페이스] - KODATA Service IF
 *
 * @class   : KodataService
 * @author  :
 * @since   : 2023.09.06
 * @version : 1.0
 *
 *   수정일            수정자                             수정내용
 *  -------    --------    ---------------------------
 *
 */
public interface KodataService {

    public KodataBizVO getKodata(String brno) throws Exception;

    public Map<String,Object> getEnp(String brno);
}
