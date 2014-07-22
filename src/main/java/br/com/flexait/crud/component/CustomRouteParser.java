package br.com.flexait.crud.component;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Specializes;
import javax.inject.Inject;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.http.route.Router;
import br.com.caelum.vraptor.i18n.routes.I18nRoutesParser;
import br.com.caelum.vraptor.i18n.routes.RoutesResources;

@ApplicationScoped @Specializes
public class CustomRouteParser extends I18nRoutesParser {
 
	/**
	 * @deprecated CDI eyes only
	 */
	public CustomRouteParser() {
	}
	
	@Inject
	public CustomRouteParser(Router router, RoutesResources routesResources) {
		super(router, routesResources);
	}
	
    @Override
    protected String extractPrefix(Class<?> type) {
    	String prefix = super.extractPrefix(type);
    	if(!type.isAnnotationPresent(Path.class) && prefix.isEmpty()) {
    		return "/" + type.getSimpleName().toLowerCase().replace("controller", "");
    	}
    	return prefix;
    }
    
}
