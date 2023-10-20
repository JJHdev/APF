package business.com.user.oauth;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.util.StringUtils;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;

public abstract class OAuthLoginBO {
	
	// API CLIENT ID 반환
	protected abstract String getClientId();
	// API CLIENT SECRET 반환
	protected abstract String getClientSecret();
	// API STATE SESSION 명칭 반환
	protected abstract String getSessionState();
	// API CALLBACK URL 반환
	protected abstract String getRedirectUrl();
	// API PROFILE 조회 URL 반환
	protected abstract String getProfileUrl();
	// API 연동해제 URL 반환
	protected abstract String getUnlinkUrl();
	// API INSTANCE 반환
	protected abstract DefaultApi20 getApiInstance();
	// API 연동해제 REQUEST 반환
	protected abstract OAuthRequest getUnlinkRequest(OAuth20Service oauthService, OAuth2AccessToken oauthToken);

    // 인증  URL 생성
    public String getAuthorizationUrl(HttpSession session) {

    	// 세션 유효성 검증을 위하여 난수를 생성
        String state = generateRandomString();
        // 생성한 난수 값을 session에 저장
        setSession(session,state);        

        // Scribe에서 제공하는 인증 URL 생성 기능을 이용하여 네아로 인증 URL 생성
        OAuth20Service oauthService = new ServiceBuilder()                                                   
                .apiKey   (getClientId     ())
                .apiSecret(getClientSecret ())
                .callback (getRedirectUrl  ())
                .state    (state             ) //앞서 생성한 난수값을 인증 URL생성시 사용함
                .build    (getApiInstance  ());

        return oauthService.getAuthorizationUrl();
    }

    // Callback 처리 및  AccessToken 획득
    public OAuth2AccessToken getAccessToken(HttpSession session, String code, String state) throws IOException{

        // Callback으로 전달받은 세선검증용 난수값과 세션에 저장되어있는 값이 일치하는지 확인
        String sessionState = getSession(session);
        
        if (StringUtils.pathEquals(sessionState, state)){

            OAuth20Service oauthService = new ServiceBuilder()                                                   
                    .apiKey   (getClientId     ())
                    .apiSecret(getClientSecret ())
                    .callback (getRedirectUrl  ())
                    .state    (state             ) //앞서 생성한 난수값을 인증 URL생성시 사용함
                    .build    (getApiInstance  ());

            // Scribe에서 제공하는 AccessToken 획득 기능으로 네아로 Access Token을 획득
            return oauthService.getAccessToken(code);
        }
        return null;
    }

    // Access Token을 이용하여 네이버 사용자 프로필 API를 호출
    public String getUserProfile(OAuth2AccessToken oauthToken) throws IOException{

        OAuth20Service oauthService = new ServiceBuilder()                                                   
                .apiKey   (getClientId     ())
                .apiSecret(getClientSecret ())
                .build    (getApiInstance  ());
        
        OAuthRequest oauthRequest = new OAuthRequest(Verb.GET, getProfileUrl(), oauthService);
            
        oauthService.signRequest(oauthToken, oauthRequest);
        Response response = oauthRequest.send();
        return response.getBody();
    }

	public void unlink(OAuth2AccessToken oauthToken) throws Exception {

        OAuth20Service oauthService = new ServiceBuilder()                                                   
                .apiKey   (getClientId     ())
                .apiSecret(getClientSecret ())
                .build    (getApiInstance  ());

		OAuthRequest oauthRequest = getUnlinkRequest(oauthService, oauthToken);

        oauthService.signRequest(oauthToken, oauthRequest);
	}
	
	// 세션 유효성 검증을 위한 난수 생성기
	protected String generateRandomString() {
		return UUID.randomUUID().toString();
	}

	// http session에 데이터 저장
	protected void setSession(HttpSession session, String state) {
		session.setAttribute(getSessionState(), state);
	}

	// http session에서 데이터 가져오기
	protected String getSession(HttpSession session) {
		return (String) session.getAttribute(getSessionState());
	}
}
