package business.com.user.oauth;

import com.github.scribejava.core.builder.api.DefaultApi20;

import business.com.CommConst;

public class NaverOAuthApi extends DefaultApi20 {

	protected NaverOAuthApi() {
	}

	private static class InstanceHolder {
		private static final NaverOAuthApi INSTANCE = new NaverOAuthApi();
	}

	public static NaverOAuthApi instance() {
		return InstanceHolder.INSTANCE;
	}
	
    @Override
    public String getAccessTokenEndpoint() {
		return CommConst.OAUTH_NAVER_TOKEN_URL;
    }

    @Override
    public String getAuthorizationBaseUrl() {
		return CommConst.OAUTH_NAVER_AUTHORIZE_URL;
    }
}
