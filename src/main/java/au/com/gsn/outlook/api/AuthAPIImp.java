package au.com.gsn.outlook.api;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.microsoft.aad.adal4j.AuthenticationContext;
import com.microsoft.aad.adal4j.AuthenticationResult;

import au.com.gsn.outlook.config.AuthConfiguration;
import au.com.gsn.outlook.exception.APIAuthException;
import au.com.gsn.outlook.helper.AppContextHelper;

public class AuthAPIImp {

	private static Logger LOGGER = LogManager.getLogger(AuthAPIImp.class);

	public static AuthenticationResult getAccessTokenFromUserCredentials() throws APIAuthException {

		LOGGER.info("Call Access token API");
		AuthConfiguration authConfig = AppContextHelper.getAuthConfiguration();
		String authority = authConfig.getAuthority();

		AuthenticationContext context;
		AuthenticationResult result;
		ExecutorService service = null;
		try {
			service = Executors.newFixedThreadPool(1);

			context = new AuthenticationContext(authority, false, service);
			Future<AuthenticationResult> future = context.acquireToken(authConfig.getAuthResource(),
					authConfig.getClientId(), authConfig.getUsername(), authConfig.getPassword(), null);
			result = future.get();

			if (result == null) {
				throw new APIAuthException("authentication result was null");
			}

		} catch (Exception e) {
			throw new APIAuthException(String.format("Fail to retrive token"));
		} finally {
			service.shutdown();
		}

		return result;
	}

}
