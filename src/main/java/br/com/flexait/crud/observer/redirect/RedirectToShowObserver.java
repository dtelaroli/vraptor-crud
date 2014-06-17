package br.com.flexait.crud.observer.redirect;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.Interceptor;
import br.com.flexait.crud.controller.CrudController;
import br.com.flexait.crud.model.IModel;

@Intercepts
public class RedirectToShowObserver implements Interceptor {

	private Result result;
	private HttpServletRequest request;

	public RedirectToShowObserver() {
	}
	
	@Inject
	public RedirectToShowObserver(Result result, HttpServletRequest request) {
		this.result = result;
		this.request = request;
	}
	
	@Override
	public void intercept(InterceptorStack stack, ControllerMethod method,
			Object controllerInstance) throws InterceptionException {
		CrudController<? extends IModel> controller = (CrudController<?>) controllerInstance;
		result.redirectTo(controller.getClass()).show(getId());
	}

	private Long getId() {
		return (Long) request.getAttribute("model.id");
	}

	@Override
	public boolean accepts(ControllerMethod method) {
		return method.containsAnnotation(RedirectToShow.class) || isSave(method);
	}

	private boolean isSave(ControllerMethod method) {
		String name = method.getMethod().getName();
		return name.equals("update") || name.equals("create");
	}

}
