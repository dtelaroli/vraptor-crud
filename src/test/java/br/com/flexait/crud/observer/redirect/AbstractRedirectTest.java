package br.com.flexait.crud.observer.redirect;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;

import net.vidageek.mirror.dsl.Mirror;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.core.MethodInfo;
import br.com.caelum.vraptor.events.MethodExecuted;
import br.com.flexait.crud.controller.CrudControllerImpl;
import br.com.flexait.crud.dao.CrudDao;
import br.com.flexait.crud.model.ModelImpl;

public class AbstractRedirectTest {

	AbstractRedirect observer;
	
	@Mock Result result;
	@Mock MethodInfo methodInfo;
	@Mock ControllerMethod controllerMethod;
	ModelImpl object;
	Method method;
	@Mock CrudControllerImpl controller;
	@Mock CrudDao<ModelImpl> dao;
	
	class AbstractRedirectImpl extends AbstractRedirect {
		public AbstractRedirectImpl(Result result) {
			super(result);
		}
		@Override
		public boolean accepts(ControllerMethod method) {
			return true;
		}
		@Override
		public void redirect(MethodInfo methodInfo) {
		}
	}
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		observer = spy(new AbstractRedirectImpl(result));
		object = new ModelImpl();
		
		method = new Mirror().on(AbstractRedirectImpl.class).reflect().method("redirect").withAnyArgs();
		when(controllerMethod.getMethod()).thenReturn(method);
		when(methodInfo.getResult()).thenReturn(object);
		when(methodInfo.getControllerMethod()).thenReturn(controllerMethod);
	}

	@Test
	public void shouldExecuteRedirectWithoutArgs() throws NoSuchMethodException, SecurityException {
		observer.observe(new MethodExecuted(controllerMethod, methodInfo), result, methodInfo);
		
		verify(observer).redirect(methodInfo);
	}
	
	@Test
	public void shouldReturnResult() {
		assertThat(observer.result(), equalTo(result));
	}
	
	@Test
	public void shouldNotExecuteIfNotAccept() {
		when(observer.accepts(any(ControllerMethod.class))).thenReturn(false);
		
		observer.observe(new MethodExecuted(controllerMethod, methodInfo), result, methodInfo);
		
		verify(observer, never()).redirect(any(MethodInfo.class));
	}

}
