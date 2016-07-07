/**
 * 
 */
package tfg.web.pages.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import tfg.model.route.Route;
import tfg.model.routeservice.RouteBlock;
import tfg.model.routeservice.RouteService;
import tfg.web.services.AuthenticationPolicy;
import tfg.web.services.AuthenticationPolicyType;
import tfg.web.util.UserSession;

/**
 * @author Fran
 *
 */
@AuthenticationPolicy(AuthenticationPolicyType.ALL_USERS)

public class SearchRoute {

    @Property
    private String nameSearchRoute;

       
    @SessionState(create=true)
    private UserSession userSession;

    @Property
    private String language;

    @Inject
    private RouteService routeService;

    @Component
    private Form searchRouteForm;

    @Component(id = "nameSearchRoute")
    private TextField nameSearchRouteField;
    
    @Inject
    private Locale locale;
    
    @Inject
    private Messages messages;

    @InjectPage
    private SearchedRoutes searchedRoutes;
    
    void onValidateFromSearchRouteForm() {

        if (!searchRouteForm.isValid()) {
            return;
        }
        	
    	this.searchedRoutes.setKeywords(nameSearchRoute);
    }
    
    void onPrepareForRender() {
        language = locale.getLanguage();
    }
   
    List<String> onProvideCompletionsFromNameSearchRoute(String partial) {
        List<String> matches = new ArrayList<String>();
        RouteBlock routeBlock=routeService.findRouteByName(partial, 0, 10);
        List<Route> routes=routeBlock.getRoutes();
        int contador=10;
        while (routeBlock.getExistMoreRoutes()){
        	contador=contador+10;
        	routeBlock=routeService.findRouteByName(partial, 0, 0);
        	routes.addAll(routeBlock.getRoutes());
        }

        for (Route route : routes) {
            if (route.getRouteName().contains(partial)) {
                matches.add(route.getRouteName());
            }
        }

        return matches;
    }
    
    Object onSuccess() {
        return this.searchedRoutes;

    }
}