package br.com.flexait.crud.interceptor.redirect;

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
public class RedirectToEditInterceptor implements Interceptor {

	private Result result;
	private HttpServletRequest request;

	public RedirectToEditInterceptor() {
	}
	
	@Inject
	public RedirectToEditInterceptor(Result result, HttpServletRequest request) {
		this.result = result;
		this.request = request;
	}
	
	@Override
	public void intercept(InterceptorStack stack, ControllerMethod method,
			Object controllerInstance) throws InterceptionException {
		CrudController<? extends IModel> controller = (CrudController<?>) controllerInstance;
		result.redirectTo(controller.getClass()).edit(getId());
	}

	private Long getId() {
		return (Long) request.getAttribute("model.id");
	}

	@Override
	public boolean accepts(ControllerMethod method) {
		return method.containsAnnotation(RedirectToEdit.class);
	}

}