package tfg.test.alert;

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

import tfg.model.alert.Alert;
import tfg.model.alert.AlertDao;
import tfg.model.route.Route;
import tfg.model.route.RouteDao;
import tfg.model.stat.Stat;
import tfg.model.stat.StatDao;
import tfg.model.userprofile.UserProfile;
import tfg.model.userprofileservice.UserProfileDetails;
import tfg.model.userprofileservice.UserProfileService;
import tfg.model.util.DuplicateInstanceException;
import tfg.model.util.InstanceNotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE })
@Transactional
public class AlertTest {

	@Autowired
	private AlertDao alertDao;
	
	@Autowired
	private RouteDao routeDao;
	
	@Autowired
	private StatDao statDao;
	
	@Autowired
	private UserProfileService userService;
	
	@Test
	public void findAlertTest(){
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
		Stat stat=new Stat();
		stat.setStatDate(date);
		stat.setStatNewNegative(false);
		stat.setStatNNumber(4l);
		stat.setStatOldMiddle(5.3);
		stat.setStatOpinionBalance(5l);
		stat.setStatPNumber(9l);
		stat.setStatRouteId(route.getRouteId());
		stat.setStatTipicalDeviation(3.6);
		statDao.save(stat);
		Alert alert=new Alert();
		alert.setAlertCompleteDescription("Description");
		alert.setAlertDate(date);
		alert.setAlertDescription("Desc");
		alert.setAlertRouteId(route.getRouteId());
		alert.setAlertStatId(stat.getStatId());
		alert.setAlertUserId(userProfile.getUserProfileId());
		alert.setAlertVisited(false);
		alertDao.save(alert);
		List<Alert> alerts = alertDao.findAlertByRoute(route.getRouteId(), userProfile.getUserProfileId(), 0, 2);
		assertTrue(alerts.size()==1);
		assertEquals(alert, alerts.get(0));
		alerts = alertDao.findAlerts(userProfile.getUserProfileId(), 0, 2);
		assertTrue(alerts.size()==1);
		assertEquals(alert, alerts.get(0));
		Alert alert1=new Alert();
		alert1.setAlertCompleteDescription("Description");
		alert1.setAlertDate(date);
		alert1.setAlertDescription("Desc");
		alert1.setAlertRouteId(route.getRouteId());
		alert1.setAlertStatId(stat.getStatId());
		alert1.setAlertUserId(userProfile.getUserProfileId());
		alert1.setAlertVisited(false);
		alertDao.save(alert1);
		alerts = alertDao.findAlertByRoute(route.getRouteId(), userProfile.getUserProfileId(), 0, 2);
		assertTrue(alerts.size()==2);
		assertEquals(alert, alerts.get(0));
		assertEquals(alert1, alerts.get(1));
		alerts = alertDao.findAlerts(userProfile.getUserProfileId(), 0, 2);
		assertTrue(alerts.size()==2);
		assertEquals(alert, alerts.get(0));
		assertEquals(alert1, alerts.get(1));
		alerts = alertDao.findSameAlerts("Description", dateStart);
		assertTrue(alerts.size()==2);
		assertEquals(alert, alerts.get(0));
		assertEquals(alert1, alerts.get(1));
		alerts = alertDao.findSameAlerts("No", dateStart);
		assertTrue(alerts.size()==0);
		alerts = alertDao.findAlertByRoute(route.getRouteId(), userProfile.getUserProfileId(), 0, 1);
		assertTrue(alerts.size()==1);
		assertEquals(alert, alerts.get(0));
		alerts = alertDao.findAlerts(userProfile.getUserProfileId(), 0, 1);
		assertTrue(alerts.size()==1);
		assertEquals(alert, alerts.get(0));
		alerts = alertDao.findAlertByRouteAndDate(route.getRouteId(), userProfile.getUserProfileId(), 0, 2, dateStart, dateEnd);
		assertTrue(alerts.size()==2);
		assertEquals(alert, alerts.get(0));
		assertEquals(alert1, alerts.get(1));
		alerts = alertDao.findAlertByRouteAndDate(route.getRouteId(), userProfile.getUserProfileId(), 0, 1, dateStart, dateEnd);
		assertTrue(alerts.size()==1);
		assertEquals(alert, alerts.get(0));
		alerts = alertDao.findAlertByDate(userProfile.getUserProfileId(), 0, 2, dateStart, dateEnd);
		assertTrue(alerts.size()==2);
		assertEquals(alert, alerts.get(0));
		assertEquals(alert1, alerts.get(1));
		alerts = alertDao.findAlertByDate(userProfile.getUserProfileId(), 0, 1, dateStart, dateEnd);
		assertTrue(alerts.size()==1);
		assertEquals(alert, alerts.get(0));
		alertDao.deleteAlert(alert.getAlertId());
		alerts = alertDao.findAlertByDate(userProfile.getUserProfileId(), 0, 2, dateStart, dateEnd);
		assertTrue(alerts.size()==1);
		assertEquals(alert1, alerts.get(0));
	}
	
	@Test
	public void deleteAlert(){
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
		Stat stat=new Stat();
		stat.setStatDate(date);
		stat.setStatNewNegative(false);
		stat.setStatNNumber(4l);
		stat.setStatOldMiddle(5.3);
		stat.setStatOpinionBalance(5l);
		stat.setStatPNumber(9l);
		stat.setStatRouteId(route.getRouteId());
		stat.setStatTipicalDeviation(3.6);
		statDao.save(stat);
		Alert alert=new Alert();
		alert.setAlertCompleteDescription("Description");
		alert.setAlertDate(date);
		alert.setAlertDescription("Desc");
		alert.setAlertRouteId(route.getRouteId());
		alert.setAlertStatId(stat.getStatId());
		alert.setAlertUserId(userProfile.getUserProfileId());
		alert.setAlertVisited(false);
		alertDao.save(alert);
		List<Alert> alerts = alertDao.findAlertByRoute(route.getRouteId(), userProfile.getUserProfileId(), 0, 2);
		assertTrue(alerts.size()==1);
		assertEquals(alert, alerts.get(0));
		alerts = alertDao.findAlerts(userProfile.getUserProfileId(), 0, 2);
		assertTrue(alerts.size()==1);
		assertEquals(alert, alerts.get(0));
		Alert alert1=new Alert();
		alert1.setAlertCompleteDescription("Description");
		alert1.setAlertDate(date);
		alert1.setAlertDescription("Desc");
		alert1.setAlertRouteId(route.getRouteId());
		alert1.setAlertStatId(stat.getStatId());
		alert1.setAlertUserId(userProfile.getUserProfileId());
		alert1.setAlertVisited(false);
		alertDao.save(alert1);
		alertDao.deleteAlert(alert.getAlertId());
		alerts = alertDao.findAlertByDate(userProfile.getUserProfileId(), 0, 2, dateStart, dateEnd);
		assertTrue(alerts.size()==1);
		assertEquals(alert1, alerts.get(0));
	}


}
