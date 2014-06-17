package br.com.flexait.crud.observer.redirect;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;

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
import br.com.flexait.crud.observer.redirect.RedirectToEditInterceptor;

public class RedirectToEditInterceptorTest {

	RedirectToEditInterceptor interceptor;
	CrudControllerImpl controller;
	Result result;
	@Mock CrudDao<ModelImpl> dao;
	@Mock HttpServletRequest request;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		result = spy(new MockResult());
		
		when(request.getAttribute("model.id")).thenReturn(1L);
		
		interceptor = new RedirectToEditInterceptor(result, request);
		controller = new CrudControllerImpl(result, dao);
	}

	@Test
	public void shouldNotAcceptWithAnnotation() throws NoSuchMethodException, SecurityException {
		ControllerMethod method = DefaultControllerMethod.instanceFor(CrudControllerImpl.class, CrudControllerImpl.class.getMethod("toShow"));

		assertThat(interceptor.accepts(method), equalTo(false));
	}
	
	@Test
	public void shouldAcceptWithAnnotationRedirectToShow() throws NoSuchMethodException, SecurityException {
		ControllerMethod method = DefaultControllerMethod.instanceFor(CrudControllerImpl.class, CrudControllerImpl.class.getMethod("toEdit"));

		assertThat(interceptor.accepts(method), equalTo(true));
	}
	
	@Test
	public void shouldRedirectToEdit() throws NoSuchMethodException, SecurityException {
		ControllerMethod method = DefaultControllerMethod.instanceFor(CrudControllerImpl.class, CrudControllerImpl.class.getMethod("toEdit"));
		
		CrudControllerImpl spy = spy(controller);
		
		when(result.redirectTo(CrudControllerImpl.class)).thenReturn(spy);
		
		interceptor.intercept(null, method, controller);
		verify(spy).edit(1L);
	}

}