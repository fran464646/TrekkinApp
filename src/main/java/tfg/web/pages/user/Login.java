package tfg.web.pages.user;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Cookies;

import tfg.model.userprofile.UserProfile;
import tfg.model.userprofileservice.IncorrectPasswordException;
import tfg.model.userprofileservice.UserProfileService;
import tfg.model.util.InstanceNotFoundException;
import tfg.web.pages.user.Profile;
import tfg.web.services.AuthenticationPolicy;
import tfg.web.services.AuthenticationPolicyType;
import tfg.web.util.CookiesManager;
import tfg.web.util.UserSession;

@AuthenticationPolicy(AuthenticationPolicyType.NON_AUTHENTICATED_USERS)
public class Login {

    @Property
    private String loginName;

    @Property
    private String password;

    @Property
    private boolean rememberMyPassword;

    @SessionState(create=false)
    private UserSession userSession;

    @Inject
    private Cookies cookies;

    @Component
    private Form loginForm;

    @Inject
    private Messages messages;

    @Inject
    private UserProfileService userService;

    private UserProfile userProfile = null;
    
    private Long productId;
    private Float ammount;
    
    public void setProductId(Long productId){
    	this.productId=productId;
    }

    public Long getProductId(){
    	return this.productId;
    }
    
    public void setAmmount(Float ammount){
    	this.ammount=ammount;
    }

    public Float getAmmount(){
    	return this.ammount;
    }

    void onValidateFromLoginForm() {

        if (!loginForm.isValid()) {
            return;
        }

        try {
            userProfile = userService.login(loginName, password, false);
        } catch (InstanceNotFoundException e) {
            loginForm.recordError(messages.get("error-authenticationFailed"));
        } catch (IncorrectPasswordException e) {
            loginForm.recordError(messages.get("error-authenticationFailed"));
        }

    }
    
    Object[] onPassivate() {
		return new Object[] {productId,ammount};
	}
    
    void onActivate() {
    }

    Object onSuccess() {

    	userSession = new UserSession();
        userSession.setUserProfileId(userProfile.getUserProfileId());
        userSession.setFirstName(userProfile.getFirstName());

        if (rememberMyPassword) {
            CookiesManager.leaveCookies(cookies, loginName, userProfile
                    .getEncryptedPassword());
        }
    	return Profile.class;
    }

}
