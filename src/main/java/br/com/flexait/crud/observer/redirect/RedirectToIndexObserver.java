package br.com.flexait.crud.observer.redirect;

import javax.inject.Inject;

import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.Interceptor;
import br.com.flexait.crud.controller.CrudController;
import br.com.flexait.crud.model.IModel;

@Intercepts
public class RedirectToIndexObserver implements Interceptor {

	private Result result;

	public RedirectToIndexObserver() {
	}
	
	@Inject
	public RedirectToIndexObserver(Result result) {
		this.result = result;
	}
	
	@Override
	public void intercept(InterceptorStack stack, ControllerMethod method,
			Object controllerInstance) throws InterceptionException {
		CrudController<? extends IModel> controller = (CrudController<?>) controllerInstance;
		result.redirectTo(controller.getClass()).index();
	}

	@Override
	public boolean accepts(ControllerMethod method) {
		return method.containsAnnotation(RedirectToIndex.class) || isDestroy(method);
	}

	private boolean isDestroy(ControllerMethod method) {
		return method.getMethod().getName().equals("destroy");
	}

}
