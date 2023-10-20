package business.com.user.oauth;

import org.springframework.stereotype.Component;

import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;

import business.com.CommConst;

/**
 * 인증 요청문을 구성하는 파라미터
 * CLIENT_ID : 애플리케이션 등록 후 발급받은 클라이언트 아이디
 * response_type: 인증 과정에 대한 구분값, code로 값이 고정
 * redirect_uri: 네이버 로그인 인증의 결과를 전달받을 콜백 URL(URL 인코딩) 
 *               애플리케이션을 등록할 때 Callback URL에 설정한 정보
 * state: 애플리케이션이 생성한 상태 토큰
 */
@Component
public class NaverLoginBO extends OAuthLoginBO {

	@Override
	protected String getClientId() {
		return CommConst.OAUTH_NAVER_CLIENT_ID;
	}

	@Override
	protected String getClientSecret() {
		return CommConst.OAUTH_NAVER_CLIENT_SECRET;
	}

	@Override
	protected String getSessionState() {
		return CommConst.OAUTH_NAVER_SESSION_STATE;
	}

	@Override
	protected String getRedirectUrl() {
		return CommConst.OAUTH_NAVER_CALLBACK_URL;
	}

	@Override
	protected String getProfileUrl() {
		return CommConst.OAUTH_NAVER_PROFILE_URL;
	}

	@Override
	protected String getUnlinkUrl() {
		return CommConst.OAUTH_NAVER_UNLINK_URL;
	}

	@Override
	protected DefaultApi20 getApiInstance() {
		return NaverOAuthApi.instance();
	}

	@Override
	protected OAuthRequest getUnlinkRequest(OAuth20Service oauthService, OAuth2AccessToken oauthToken) {

		OAuthRequest oauthRequest = new OAuthRequest(Verb.POST, getUnlinkUrl(), oauthService);
		oauthRequest.addQuerystringParameter("access_token"    , oauthToken.getAccessToken());
		oauthRequest.addQuerystringParameter("service_provider", "NAVER");
		
		return oauthRequest;
	}
}
