package br.com.flexait.crud.observer.redirect;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.controller.BeanClass;
import br.com.caelum.vraptor.controller.ControllerInstance;
import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.controller.DefaultControllerInstance;
import br.com.caelum.vraptor.controller.DefaultControllerMethod;
import br.com.caelum.vraptor.core.MethodInfo;
import br.com.caelum.vraptor.http.ParameterNameProvider;
import br.com.caelum.vraptor.validator.Validator;
import br.com.flexait.crud.controller.CrudControllerImpl;
import br.com.flexait.crud.dao.CrudDao;
import br.com.flexait.crud.model.ModelImpl;

public class RedirectToEditObserverTest {

	RedirectToEditObserver observer;
	@Mock Result result;
	@Mock MethodInfo methodInfo;
	@Mock CrudDao<ModelImpl> dao;
	@Mock ControllerMethod controllerMethod;
	@Mock ParameterNameProvider parameterNameProvider;
	BeanClass beanClass;
	ModelImpl object;
	CrudControllerImpl controller;
	Method method;
	@Mock Validator validator;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		observer = spy(new RedirectToEditObserver(result));
		object = new ModelImpl();
		object.setId(1L);
		
		controller = new CrudControllerImpl(result, dao, validator);
		
		method = CrudControllerImpl.class.getMethod("toEdit");
		
		ControllerInstance instance = new DefaultControllerInstance(controller);
		
		when(methodInfo.getResult()).thenReturn(object);
		doReturn(instance.getBeanClass()).when(controllerMethod).getController();
		doReturn(controllerMethod).when(methodInfo).getControllerMethod();
	}

	@Test
	public void shouldNotAcceptWithAnnotation() throws NoSuchMethodException, SecurityException {
		ControllerMethod method = DefaultControllerMethod.instanceFor(CrudControllerImpl.class, CrudControllerImpl.class.getMethod("toShow"));

		assertThat(observer.accepts(method), equalTo(false));
	}
	
	@Test
	public void shouldAcceptWithAnnotationRedirectToShow() throws NoSuchMethodException, SecurityException {
		ControllerMethod method = DefaultControllerMethod.instanceFor(CrudControllerImpl.class, CrudControllerImpl.class.getMethod("toEdit"));

		assertThat(observer.accepts(method), equalTo(true));
	}
	
	@Test
	public void shouldRedirectToEdit() throws NoSuchMethodException, SecurityException {
		CrudControllerImpl spy = spy(controller);
		when(result.redirectTo(CrudControllerImpl.class)).thenReturn(spy);
		
		observer.redirect(methodInfo);
		verify(spy).edit(1L);
	}

}