package br.com.caelum.vraptor.crud.components;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Specializes;
import javax.inject.Inject;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.http.route.PathAnnotationRoutesParser;
import br.com.caelum.vraptor.http.route.Router;

@Specializes @ApplicationScoped
public class CustomRouteParser extends PathAnnotationRoutesParser {
 
	@SuppressWarnings("deprecation")
	public CustomRouteParser() {
	}
	
	@Inject
	public CustomRouteParser(Router router) {
		super(router);
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
