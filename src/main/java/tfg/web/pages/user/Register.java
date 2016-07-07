package tfg.web.pages.user;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.annotations.InjectPage;

import tfg.model.userprofile.UserProfile;
import tfg.model.userprofileservice.UserProfileDetails;
import tfg.model.userprofileservice.UserProfileService;
import tfg.model.util.DuplicateInstanceException;
import tfg.web.pages.Index;
import tfg.web.services.AuthenticationPolicy;
import tfg.web.services.AuthenticationPolicyType;
import tfg.web.util.UserSession;

@AuthenticationPolicy(AuthenticationPolicyType.NON_AUTHENTICATED_USERS)
public class Register {

    @Property
    private String loginName;

    @Property
    private String password;

    @Property
    private String retypePassword;

    @Property
    private String firstName;

    @Property
    private String lastName;

    @Property
    private String email;

    @SessionState(create=false)
    private UserSession userSession;

    @Inject
    private UserProfileService userService;

    @Component
    private Form registrationForm;

    @Component(id = "loginName")
    private TextField loginNameField;

    @Component(id = "password")
    private PasswordField passwordField;

    @Inject
    private Messages messages;

    private Long userProfileId;
    private Long productId=null;
    private Float ammount;

    void onValidateFromRegistrationForm() {

        if (!registrationForm.isValid()) {
            return;
        }

        if (!password.equals(retypePassword)) {
            registrationForm.recordError(passwordField, messages
                    .get("error-passwordsDontMatch"));
        } else {

            try {
                UserProfile userProfile = userService.registerUser(loginName, password,
                    new UserProfileDetails(firstName, lastName, email));
                userProfileId = userProfile.getUserProfileId();
            } catch (DuplicateInstanceException e) {
                registrationForm.recordError(loginNameField, messages
                        .get("error-loginNameAlreadyExists"));
            }

        }

    }
    
    Object[] onPassivate() {
		return new Object[] {productId,ammount};
	}
    
    void onActivate(Long productId, Float ammount) {
		this.productId = productId;
		this.ammount = ammount;
    }

    Object onSuccess() {

        userSession = new UserSession();
        userSession.setUserProfileId(userProfileId);
        userSession.setFirstName(firstName);
        return Index.class;


    }

}
