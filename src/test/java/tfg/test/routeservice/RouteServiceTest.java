package tfg.test.routeservice;

import static org.junit.Assert.*;
import static tfg.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;
import static tfg.model.util.GlobalNames.SPRING_CONFIG_FILE;

import java.util.List;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import tfg.model.route.Route;
import tfg.model.route.RouteDao;
import tfg.model.routeservice.RouteBlock;
import tfg.model.routeservice.RouteService;
import tfg.model.util.InstanceNotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE })
@Transactional
public class RouteServiceTest {

	 @Autowired
	 private RouteService routeService;
	 
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
		Route route1=new Route();
		route1.setKilometers(1000l);
		route1.setKmlFile("kmlfile");
		route1.setNegativeSlope(100l);
		route1.setNumberOfHostels(30l);
		route1.setPositiveSlope(50l);
		route1.setRouteEnd("Camariñas");
		route1.setRouteName("Kronoescalada");
		route1.setRouteStart("Vimianzo");
		routeDao.save(route1);
		RouteBlock routes=routeService.findRouteByName("Rutita", 0, 10);
		assertEquals(routes.getRoutes().size(),1);
		assertEquals(routes.getRoutes().get(0).getRouteId(),route.getRouteId());
		routes=routeService.findRouteByName("Kronoescalada", 0, 10);
		assertEquals(routes.getRoutes().size(),1);
		assertEquals(routes.getRoutes().get(0).getRouteId(),route1.getRouteId());
	}
	
	@Test
	public void findByRouteIdTest() throws InstanceNotFoundException {
		
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
		route1.setRouteName("Kronoescalada");
		route1.setRouteStart("Vimianzo");
		routeDao.save(route1);
		Route routeBD=routeService.findByRouteId(route.getRouteId());
		assertEquals(routeBD,route);
		routeBD=routeService.findByRouteId(route1.getRouteId());
		assertEquals(routeBD,route1);
	}
	
	@Test
	public void getAllRouteIdsTest() {
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
		List<Long> ids=routeService.getAllRouteIds();
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
		List<String> names=routeService.getRouteNames();
		assertEquals(names.get(0),route.getRouteName());
		assertEquals(names.get(1),route1.getRouteName());
	}

}
