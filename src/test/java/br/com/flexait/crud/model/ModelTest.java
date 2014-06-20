package br.com.flexait.crud.model;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Calendar;

import javax.inject.Inject;
import javax.persistence.EntityTransaction;

import org.jglue.cdiunit.CdiRunner;
import org.jglue.cdiunit.ContextController;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.flexait.cdi.integration.Jpa;

@RunWith(CdiRunner.class)
public class ModelTest {
	
	@Inject ContextController ctx;
	@Inject Jpa jpa;
	
	public ModelTest() {
	}
	
	@Before
	public void setUp() throws Exception {
		ctx.openRequest();
	}

	@After
	public void tearDown() throws Exception {
		ctx.closeRequest();
	}
	
	private ModelImpl buildModel() {
		ModelImpl model = new ModelImpl();
		model.setName("Foo");
		return model;
	}
	
	@Test
	public void shouldSaveAModelImplementation() throws Exception {
		EntityTransaction tx = jpa.beginTransaction();
		
		ModelImpl merged = jpa.em().merge(buildModel());
		tx.commit();
		
		assertThat(merged.getId(), notNullValue());
	}
	
	@Test
	public void shouldConfigDateOnInsert() {
		EntityTransaction tx = jpa.beginTransaction();
		ModelImpl merged = jpa.em().merge(buildModel());
		tx.commit();
		
		assertThat(merged.getCreatedAt(), notNullValue());
	}
	
	@Test
	public void shouldConfigDateOnUpdate() {
		EntityTransaction tx = jpa.beginTransaction();
		ModelImpl merged = jpa.em().merge(buildModel());
		Calendar createdAt = merged.getCreatedAt();
		tx.commit();
		assertThat(merged.getUpdatedAt(), notNullValue());
		assertThat(createdAt, notNullValue());
		
		tx.begin();
		merged.setName("Bar");
		merged = jpa.em().merge(merged);
		tx.commit();
		
		assertThat(merged.getUpdatedAt(), notNullValue());
		assertThat(merged.getCreatedAt().getTime().toString(), equalTo(createdAt.getTime().toString()));
	}
}