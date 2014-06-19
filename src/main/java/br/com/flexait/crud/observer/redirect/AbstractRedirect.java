package br.com.flexait.crud.observer.redirect;

import javax.enterprise.event.Observes;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.controller.BeanClass;
import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.core.MethodInfo;
import br.com.caelum.vraptor.events.MethodExecuted;
import br.com.flexait.crud.controller.CrudController;

public abstract class AbstractRedirect {

	private Result result;
	
	/**
	 * @deprecated CDI eyes only
	 */
	public AbstractRedirect() {
	}
	
	public AbstractRedirect(Result result) {
		this.result = result;
	}

	public void observe(@Observes MethodExecuted event, MethodInfo methodInfo) {
		if(accepts(methodInfo.getControllerMethod())) {
			redirect(methodInfo);
		}
	}
	
	public abstract void redirect(MethodInfo methodInfo);

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Class<? extends CrudController> getController(MethodInfo methodInfo) {
		ControllerMethod controllerMethod = methodInfo.getControllerMethod();
		BeanClass controller = controllerMethod.getController();
		return (Class<? extends CrudController>) controller.getType();
	}

	public abstract boolean accepts(ControllerMethod method);

	public Result result() {
		return result;
	}
}
