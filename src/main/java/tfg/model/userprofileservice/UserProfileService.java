package tfg.model.userprofileservice;

import tfg.model.userprofile.UserProfile;
import tfg.model.util.DuplicateInstanceException;
import tfg.model.util.InstanceNotFoundException;

public interface UserProfileService {

    public UserProfile registerUser(String loginName, String clearPassword,
            UserProfileDetails userProfileDetails)
            throws DuplicateInstanceException;

    public UserProfile login(String loginName, String password,
            boolean passwordIsEncrypted) throws InstanceNotFoundException,
            IncorrectPasswordException;

    public UserProfile findUserProfile(Long userProfileId)
            throws InstanceNotFoundException;

    public void updateUserProfileDetails(Long userProfileId,
            UserProfileDetails userProfileDetails)
            throws InstanceNotFoundException;

    public void changePassword(Long userProfileId, String oldClearPassword,
            String newClearPassword) throws IncorrectPasswordException,
            InstanceNotFoundException;

}
