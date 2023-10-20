package business.com.user.oauth;

import org.springframework.stereotype.Component;

import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;

import business.com.CommConst;

/**
 * 카카오 로그인 정보
 */
@Component
public class KakaoLoginBO extends OAuthLoginBO {

	@Override
	protected String getClientId() {
		return CommConst.OAUTH_KAKAO_CLIENT_ID;
	}

	@Override
	protected String getClientSecret() {
		return CommConst.OAUTH_KAKAO_CLIENT_SECRET;
	}

	@Override
	protected String getSessionState() {
		return CommConst.OAUTH_KAKAO_SESSION_STATE;
	}

	@Override
	protected String getRedirectUrl() {
		return CommConst.OAUTH_KAKAO_CALLBACK_URL;
	}

	@Override
	protected String getProfileUrl() {
		return CommConst.OAUTH_KAKAO_PROFILE_URL;
	}

	@Override
	protected String getUnlinkUrl() {
		return CommConst.OAUTH_KAKAO_UNLINK_URL;
	}

	@Override
	protected DefaultApi20 getApiInstance() {
		return KakaoOAuthApi.instance();
	}

	@Override
	protected OAuthRequest getUnlinkRequest(OAuth20Service oauthService, OAuth2AccessToken oauthToken) {

		OAuthRequest oauthRequest = new OAuthRequest(Verb.POST, getUnlinkUrl(), oauthService);
		oauthRequest.addHeader("Content-Type",  "application/x-www-form-urlencoded");
		oauthRequest.addHeader("Authorization", "Bearer "+ oauthToken.getAccessToken());
		
		return oauthRequest;
	}
}
