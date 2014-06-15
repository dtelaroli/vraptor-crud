package br.com.flexait.crud.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.util.test.MockResult;
import br.com.flexait.crud.dao.CrudDao;
import br.com.flexait.crud.model.ModelImpl;

public class CrudControllerTest {

	CrudControllerImpl controller;
	@Mock CrudDao<ModelImpl> dao;
	Result result;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		result = spy(new MockResult());
		controller = new CrudControllerImpl(result, dao);
		
		ModelImpl model = new ModelImpl();
		model.setId(1L);
		model.setName("foo");
		List<ModelImpl> list = Arrays.asList(model);
		
		when(dao.all()).thenReturn(list);
		when(dao.get(anyLong())).thenReturn(model);
		when(dao.save(any(ModelImpl.class))).thenReturn(model);
	}
	
	@Test
	public void shouldReturnListOfModelImpl() {
		List<ModelImpl> list = controller.index();
		assertThat(list.get(0).getName(), equalTo("foo"));
	}
	
	@Test
	public void shouldReturnModel1OnShow() {
		ModelImpl show = controller.show(1L);
		assertThat(show.getName(), equalTo("foo"));
	}
	
	@Test
	public void shouldReturnModel1Edit() {
		ModelImpl show = controller.edit(1L);
		assertThat(show.getName(), equalTo("foo"));
	}

	@Test
	public void shouldRedirectToShowAfterCreate() {
		CrudControllerImpl spy = spy(controller);
		when(result.redirectTo(CrudControllerImpl.class)).thenReturn(spy);
		
		ModelImpl m = new ModelImpl();
		m.setName("Name");
		
		controller.create(m);
		verify(spy).show(1L);
	}
	
	@Test
	public void shouldRedirectToShowAfterUpdate() {
		CrudControllerImpl spy = spy(controller);
		when(result.redirectTo(CrudControllerImpl.class)).thenReturn(spy);
		
		ModelImpl m = new ModelImpl();
		m.setName("Name");
		
		controller.update(m);
		verify(spy).show(1L);
	}
	
	@Test
	public void shouldRedirectToIndexAfterDestroy() {
		CrudControllerImpl spy = spy(controller);
		when(result.redirectTo(CrudControllerImpl.class)).thenReturn(spy);
		
		controller.destroy(1L);
		verify(spy).index();
	}
}
