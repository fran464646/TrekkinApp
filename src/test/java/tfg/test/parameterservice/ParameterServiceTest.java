package tfg.test.parameterservice;

import static org.junit.Assert.*;
import static tfg.model.util.GlobalNames.SPRING_CONFIG_FILE;
import static tfg.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import tfg.model.alert.AlertDao;
import tfg.model.parameter.Parameter;
import tfg.model.parameter.ParameterDao;
import tfg.model.parameterservice.ParameterService;
import tfg.model.route.Route;
import tfg.model.route.RouteDao;
import tfg.model.userprofile.UserProfile;
import tfg.model.userprofileservice.UserProfileDetails;
import tfg.model.userprofileservice.UserProfileService;
import tfg.model.util.DuplicateInstanceException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE })
@Transactional
public class ParameterServiceTest {

	@Autowired
	private RouteDao routeDao;

	@Autowired
	private ParameterService parameterService;
	
	@Autowired
	private UserProfileService userService;
	
	@Test
	public void findParameters() {
		UserProfile userProfile=new UserProfile();
		try {
			 userProfile = userService.registerUser(
			        "user", "userPassword",
			        new UserProfileDetails("name", "lastName", "user@udc.es"));
			
		} catch (DuplicateInstanceException e) {
		}
		Date date=new Date();
		Date dateStart;
		Date dateEnd;
		Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.add(Calendar.DAY_OF_MONTH, -1);
	    dateStart=cal.getTime();
	    cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.add(Calendar.DAY_OF_MONTH, 1);
	    dateEnd=cal.getTime();
	    Route route=new Route();
		route.setKilometers(1000l);
		route.setKmlFile("kmlfile");
		route.setNegativeSlope(100l);
		route.setNumberOfHostels(30l);
		route.setPositiveSlope(50l);
		route.setRouteEnd("Camariñas");
		route.setRouteName("Rutita");
		route.setRouteStart("Vimianzo");
		routeDao.save(route);
		Parameter parameter = new Parameter();
		parameter.setParameterDescription("Parametro");
		parameter.setParameterKey("Clave");
		parameter.setParameterRouteId(route.getRouteId());
		parameter.setParameterRouteName(route.getRouteName());
		parameter.setParameterUserId(userProfile.getUserProfileId());
		parameter.setParameterValue(1l);
		parameterService.saveParameter(parameter);
		List<Parameter> parameters = parameterService.findParameterByUserId(userProfile.getUserProfileId());
		assertTrue(parameters.size()==1);
		assertEquals(parameter,parameters.get(0));
		Parameter parameter1 = new Parameter();
		parameter1.setParameterDescription("Parametro1");
		parameter1.setParameterKey("Clave1");
		parameter1.setParameterRouteId(route.getRouteId());
		parameter1.setParameterRouteName(route.getRouteName());
		parameter1.setParameterUserId(userProfile.getUserProfileId());
		parameter1.setParameterValue(2l);
		parameterService.saveParameter(parameter1);
		parameters = parameterService.findParameterByUserId(userProfile.getUserProfileId());
		assertTrue(parameters.size()==2);
		assertEquals(parameter,parameters.get(0));
		assertEquals(parameter1,parameters.get(1));
		parameters=parameterService.findParameterByType(parameter.getParameterKey(), route.getRouteId());
		assertTrue(parameters.size()==1);
		assertEquals(parameter,parameters.get(0));
		parameters=parameterService.findParameterByType(parameter1.getParameterKey(), route.getRouteId());
		assertTrue(parameters.size()==1);
		assertEquals(parameter1,parameters.get(0));
		parameters=parameterService.getParametersByUser(userProfile.getUserProfileId(), 0, 2).getParameters();
		assertTrue(parameters.size()==2);
		assertEquals(parameter,parameters.get(0));
		assertEquals(parameter1,parameters.get(1));
		parameters=parameterService.getParametersByUser(userProfile.getUserProfileId(), 0, 1).getParameters();
		assertTrue(parameters.size()==1);
		assertEquals(parameter,parameters.get(0));
		
	}
	
	@Test
	public void deleteParameters() {
		UserProfile userProfile=new UserProfile();
		try {
			 userProfile = userService.registerUser(
			        "user", "userPassword",
			        new UserProfileDetails("name", "lastName", "user@udc.es"));
			
		} catch (DuplicateInstanceException e) {
		}
		Date date=new Date();
		Date dateStart;
		Date dateEnd;
		Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.add(Calendar.DAY_OF_MONTH, -1);
	    dateStart=cal.getTime();
	    cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.add(Calendar.DAY_OF_MONTH, 1);
	    dateEnd=cal.getTime();
	    Route route=new Route();
		route.setKilometers(1000l);
		route.setKmlFile("kmlfile");
		route.setNegativeSlope(100l);
		route.setNumberOfHostels(30l);
		route.setPositiveSlope(50l);
		route.setRouteEnd("Camariñas");
		route.setRouteName("Rutita");
		route.setRouteStart("Vimianzo");
		routeDao.save(route);
		Parameter parameter = new Parameter();
		parameter.setParameterDescription("Parametro");
		parameter.setParameterKey("Clave");
		parameter.setParameterRouteId(route.getRouteId());
		parameter.setParameterRouteName(route.getRouteName());
		parameter.setParameterUserId(userProfile.getUserProfileId());
		parameter.setParameterValue(1l);
		parameterService.saveParameter(parameter);
		List<Parameter> parameters = parameterService.findParameterByUserId(userProfile.getUserProfileId());
		assertTrue(parameters.size()==1);
		assertEquals(parameter,parameters.get(0));
		Parameter parameter1 = new Parameter();
		parameter1.setParameterDescription("Parametro1");
		parameter1.setParameterKey("Clave1");
		parameter1.setParameterRouteId(route.getRouteId());
		parameter1.setParameterRouteName(route.getRouteName());
		parameter1.setParameterUserId(userProfile.getUserProfileId());
		parameter1.setParameterValue(2l);
		parameterService.saveParameter(parameter1);
		parameters = parameterService.findParameterByUserId(userProfile.getUserProfileId());
		assertTrue(parameters.size()==2);
		assertEquals(parameter,parameters.get(0));
		assertEquals(parameter1,parameters.get(1));
		parameterService.deleteParameterById(parameter.getParameterId());
		parameters=parameterService.getParametersByUser(userProfile.getUserProfileId(), 0, 2).getParameters();
		assertTrue(parameters.size()==1);
		assertEquals(parameter1,parameters.get(0));
		parameterService.deleteParameter(userProfile.getUserProfileId(), parameter1.getParameterKey());
		parameters=parameterService.getParametersByUser(userProfile.getUserProfileId(), 0, 2).getParameters();
		assertTrue(parameters.size()==0);
	}
}
