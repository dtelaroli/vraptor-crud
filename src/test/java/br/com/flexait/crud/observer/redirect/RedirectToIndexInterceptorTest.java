package br.com.flexait.crud.observer.redirect;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.controller.DefaultControllerMethod;
import br.com.caelum.vraptor.util.test.MockResult;
import br.com.flexait.crud.controller.CrudControllerImpl;
import br.com.flexait.crud.dao.CrudDao;
import br.com.flexait.crud.model.ModelImpl;
import br.com.flexait.crud.observer.redirect.RedirectToIndexInterceptor;

public class RedirectToIndexInterceptorTest {

	RedirectToIndexInterceptor interceptor;
	CrudControllerImpl controller;
	Result result;
	@Mock CrudDao<ModelImpl> dao;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		result = spy(new MockResult());
		
		interceptor = new RedirectToIndexInterceptor(result);
		controller = new CrudControllerImpl(result, dao);
	}

	@Test
	public void shouldAcceptIfNameIsDestroyWithoutAnnotation() throws NoSuchMethodException, SecurityException {
		ControllerMethod method = DefaultControllerMethod.instanceFor(CrudControllerImpl.class, CrudControllerImpl.class.getMethod("destroy", Long.class));

		assertThat(interceptor.accepts(method), equalTo(true));
	}
	
	@Test
	public void shouldNotAcceptIfNameIsIndexWithoutAnnotation() throws NoSuchMethodException, SecurityException {
		ControllerMethod method = DefaultControllerMethod.instanceFor(CrudControllerImpl.class, CrudControllerImpl.class.getMethod("index"));

		assertThat(interceptor.accepts(method), equalTo(false));
	}
	
	@Test
	public void shouldAcceptWithAnnotationRedirectToIndex() throws NoSuchMethodException, SecurityException {
		ControllerMethod method = DefaultControllerMethod.instanceFor(CrudControllerImpl.class, CrudControllerImpl.class.getMethod("edit", Long.class));

		assertThat(interceptor.accepts(method), equalTo(true));
	}
	
	@Test
	public void shouldRedirectToIndex() throws NoSuchMethodException, SecurityException {
		ControllerMethod method = DefaultControllerMethod.instanceFor(CrudControllerImpl.class, CrudControllerImpl.class.getMethod("destroy", Long.class));
		
		CrudControllerImpl spy = spy(controller);
		
		when(result.redirectTo(CrudControllerImpl.class)).thenReturn(spy);
		
		interceptor.intercept(null, method, controller);
		verify(spy).index();
	}

}