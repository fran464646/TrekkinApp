package tfg.test.userprofile;

import static tfg.model.util.GlobalNames.SPRING_CONFIG_FILE;
import static tfg.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import tfg.model.userprofile.UserProfile;
import tfg.model.userprofile.UserProfileDao;
import tfg.model.userprofileservice.IncorrectPasswordException;
import tfg.model.userprofileservice.UserProfileDetails;
import tfg.model.userprofileservice.UserProfileService;
import tfg.model.util.DuplicateInstanceException;
import tfg.model.util.InstanceNotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE })
@Transactional
public class UserProfileTest {

    private final long NON_EXISTENT_USER_PROFILE_ID = -1;

    @Autowired
    private UserProfileDao userDao;
    
    @Autowired
    private UserProfileService userService;

    @Test
    public void testFindUserByLoginName()
        throws DuplicateInstanceException, InstanceNotFoundException {

        /* Register user and find profile. */
        UserProfile userProfile = userService.registerUser(
            "user", "userPassword",
            new UserProfileDetails("name", "lastName", "user@udc.es"));

        UserProfile userProfile2 = userDao.findByLoginName(userProfile.getLoginName());

        /* Check data. */
        assertEquals(userProfile, userProfile2);
    }

}
