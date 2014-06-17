package br.com.flexait.crud.observer.redirect;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import br.com.caelum.vraptor.AfterCall;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.core.MethodInfo;
import br.com.flexait.crud.model.IModel;

@ApplicationScoped
public class RedirectToEditObserver extends AbstractRedirect {

	/**
	 * @deprecated CDI eyes only
	 */
	public RedirectToEditObserver() {
	}
	
	@Inject
	public RedirectToEditObserver(Result result) {
		super(result);
	}

	@Override
	public boolean accepts(ControllerMethod method) {
		return method.containsAnnotation(RedirectToEdit.class);
	}
	
	@AfterCall
	@Override
	public void redirect(MethodInfo methodInfo) {
		IModel model = (IModel) methodInfo.getResult();
		result().redirectTo(getController(methodInfo)).edit(model.getId());
	}

}