package au.com.gsn.outlook.service;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.StringUtils;

import com.microsoft.aad.adal4j.AuthenticationResult;

import au.com.gsn.outlook.api.AuthAPIImp;
import au.com.gsn.outlook.exception.APIAuthException;

public class AuthorityService {

	private Logger LOGGER = LogManager.getLogger(AuthorityService.class);

	private AuthenticationResult authResult = null;

	private static AuthorityService instance = null;

	private AuthorityService() {
		try {
			this.authResult = AuthAPIImp.getAccessTokenFromUserCredentials();
		} catch (Exception e) {

		}
	}

	public static AuthorityService getInstance() {

		if (instance == null) {
			instance = new AuthorityService();
		}

		return instance;

	}

	public AuthenticationResult getAuthResult() throws APIAuthException {
		
		if (authResult.getExpiresOnDate().before(new Date())) {
			LOGGER.info("TOKEN expired");
			return new AuthorityService().getAuthResult();
		}
		if(authResult == null || StringUtils.isEmpty(authResult.getAccessToken())) {
			throw new APIAuthException("Token is empty");
		}
		return authResult;
		
	}

	public void setAuthResult(AuthenticationResult authResult) {
		this.authResult = authResult;
	}

}
