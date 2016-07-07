package tfg.test.route;

import static org.junit.Assert.*;
import static tfg.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;
import static tfg.model.util.GlobalNames.SPRING_CONFIG_FILE;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import tfg.model.route.Route;
import tfg.model.route.RouteDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE })
@Transactional
public class RouteTest {

	 @Autowired
	 private RouteDao routeDao;
	 
	@Test
	public void findByRouteNameTest() {
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
		List<Route> routes=routeDao.findByRouteName("Rutita", 0, 10);
		assertEquals(routes.get(0).getRouteId(),route.getRouteId());
	}
	
	@Test
	public void findRouteIds() {
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
		Route route1=new Route();
		route1.setKilometers(1000l);
		route1.setKmlFile("kmlfile");
		route1.setNegativeSlope(100l);
		route1.setNumberOfHostels(30l);
		route1.setPositiveSlope(50l);
		route1.setRouteEnd("Camariñas");
		route1.setRouteName("Rutita");
		route1.setRouteStart("Cee");
		routeDao.save(route1);
		List<Long> ids=routeDao.findRouteIds();
		assertEquals(ids.get(0),route.getRouteId());
		assertEquals(ids.get(1),route1.getRouteId());
	}
	
	@Test
	public void getAllRouteNamesTest() {
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
		Route route1=new Route();
		route1.setKilometers(1000l);
		route1.setKmlFile("kmlfile");
		route1.setNegativeSlope(100l);
		route1.setNumberOfHostels(30l);
		route1.setPositiveSlope(50l);
		route1.setRouteEnd("Camariñas");
		route1.setRouteName("Rutita");
		route1.setRouteStart("Cee");
		routeDao.save(route1);
		List<String> names=routeDao.getRouteNames();
		assertEquals(names.get(0),route.getRouteName());
		assertEquals(names.get(1),route1.getRouteName());
	}

}
