package tfg.test.stat;

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
public class StatTest {

	@Autowired
	private AlertDao alertDao;
	
	@Autowired
	private RouteDao routeDao;
	
	@Autowired
	private StatDao statDao;
	
	@Autowired
	private UserProfileService userService;
	
	@Test
	public void findStatsTest(){
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
		List<Stat> stats = statDao.findByRoute(route.getRouteId(), 0, 2);
		assertTrue(stats.size()==1);
		assertEquals(stats.get(0),stat);
		Stat stat1=new Stat();
		stat1.setStatDate(date);
		stat1.setStatNewNegative(false);
		stat1.setStatNNumber(4l);
		stat1.setStatOldMiddle(5.3);
		stat1.setStatOpinionBalance(5l);
		stat1.setStatPNumber(9l);
		stat1.setStatRouteId(route.getRouteId());
		stat1.setStatTipicalDeviation(3.6);
		statDao.save(stat1);
		stats = statDao.findByRoute(route.getRouteId(), 0, 2);
		assertTrue(stats.size()==2);
		assertEquals(stats.get(0),stat);
		assertEquals(stats.get(1),stat1);
		stats = statDao.findByRoute(route.getRouteId(), 0, 1);
		assertTrue(stats.size()==1);
		assertEquals(stats.get(0),stat);
		stats = statDao.findStatByRouteAndDate(route.getRouteId(), 0, 2, dateStart, dateEnd);
		assertTrue(stats.size()==2);
		assertEquals(stats.get(0),stat);
		assertEquals(stats.get(1),stat1);
		stats = statDao.findStatByRouteAndDate(route.getRouteId(), 0, 2, null, dateEnd);
		assertTrue(stats.size()==2);
		assertEquals(stats.get(0),stat);
		assertEquals(stats.get(1),stat1);
		stats = statDao.findStatByRouteAndDate(route.getRouteId(), 0, 2, dateStart, null);
		assertTrue(stats.size()==2);
		assertEquals(stats.get(0),stat);
		assertEquals(stats.get(1),stat1);
		stats = statDao.findStatByRouteAndDate(route.getRouteId(), 0, 2, null, null);
		assertTrue(stats.size()==2);
		assertEquals(stats.get(0),stat);
		assertEquals(stats.get(1),stat1);
		stats = statDao.findStatByRouteAndDate(route.getRouteId(), 0, 1, dateStart, dateEnd);
		assertTrue(stats.size()==1);
		assertEquals(stats.get(0),stat);
	}
}
