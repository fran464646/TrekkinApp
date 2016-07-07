package tfg.web.services;

import java.io.IOException;

import org.apache.tapestry5.services.ApplicationStateManager;
import org.apache.tapestry5.services.Cookies;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestFilter;
import org.apache.tapestry5.services.RequestHandler;
import org.apache.tapestry5.services.Response;

import tfg.model.userprofile.UserProfile;
import tfg.model.userprofileservice.IncorrectPasswordException;
import tfg.model.userprofileservice.UserProfileService;
import tfg.model.util.InstanceNotFoundException;
import tfg.web.util.CookiesManager;
import tfg.web.util.UserSession;

public class SessionFilter implements RequestFilter {

	private ApplicationStateManager applicationStateManager;
	private Cookies cookies;
	private UserProfileService userService;

	public SessionFilter(ApplicationStateManager applicationStateManager,
			Cookies cookies, UserProfileService userService) {

		this.applicationStateManager = applicationStateManager;
		this.cookies = cookies;
		this.userService = userService;

	}

	public boolean service(Request request, Response response,
			RequestHandler handler) throws IOException {

		if (!applicationStateManager.exists(UserSession.class)) {

			String loginName = CookiesManager.getLoginName(cookies);
			if (loginName != null) {

				String encryptedPassword = CookiesManager
						.getEncryptedPassword(cookies);
				if (encryptedPassword != null) {

					try {

						UserProfile userProfile = userService.login(loginName,
								encryptedPassword, true);
						UserSession userSession = new UserSession();
						userSession.setUserProfileId(userProfile
								.getUserProfileId());
						userSession.setFirstName(userProfile.getFirstName());
						applicationStateManager.set(UserSession.class,
								userSession);

					} catch (InstanceNotFoundException e) {
						CookiesManager.removeCookies(cookies);
					} catch (IncorrectPasswordException e) {
						CookiesManager.removeCookies(cookies);
					}

				}

			}

		}

		handler.service(request, response);

		return true;
	}

}
