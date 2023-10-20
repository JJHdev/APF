package business.com.user.oauth;

import com.github.scribejava.core.builder.api.DefaultApi20;

import business.com.CommConst;

public class KakaoOAuthApi extends DefaultApi20 {
	
	protected KakaoOAuthApi() {
	}

	private static class InstanceHolder {
		private static final KakaoOAuthApi INSTANCE = new KakaoOAuthApi();
	}

	public static KakaoOAuthApi instance() {
		return InstanceHolder.INSTANCE;
	}

	@Override
	public String getAccessTokenEndpoint() {
		return CommConst.OAUTH_KAKAO_TOKEN_URL;
	}

	@Override
	protected String getAuthorizationBaseUrl() {
		return CommConst.OAUTH_KAKAO_AUTHORIZE_URL;
	}
}
