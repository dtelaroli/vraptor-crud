package br.com.flexait.crud.observer.redirect;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.core.MethodInfo;

@ApplicationScoped
public class RedirectToIndexObserver extends AbstractRedirect {

	/**
	 * @deprecated CDI eyes only
	 */
	public RedirectToIndexObserver() {
	}
	
	@Inject
	public RedirectToIndexObserver(Result result) {
		super(result);
	}

	@Override
	public boolean accepts(ControllerMethod method) {
		return method.containsAnnotation(RedirectToIndex.class) || isDestroy(method);
	}

	private boolean isDestroy(ControllerMethod method) {
		return method.getMethod().getName().equals("destroy");
	}

	@Override
	public void redirect(MethodInfo methodInfo) {
		result().redirectTo(getController(methodInfo)).index();
	}

}