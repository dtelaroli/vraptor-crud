package br.com.flexait.crud.model;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Calendar;

import javax.inject.Inject;

import org.jglue.cdiunit.CdiRunner;
import org.jglue.cdiunit.ContextController;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.flexait.cdi.integration.Db;

@RunWith(CdiRunner.class)
public class ModelTest {
	
	@Inject	Db db;
	@Inject ContextController ctx;
	ModelImpl model;
	
	@Before
	public void setUp() throws Exception {
		ctx.openRequest();
		db.init(ModelImpl.class);
		
		model = new ModelImpl();
		model.name = "Foo";
	}

	@After
	public void tearDown() throws Exception {
		db.clean();
		ctx.closeRequest();
	}
	
	@Test
	public void shouldSaveAModelImplementation() throws Exception {
		ModelImpl merged = db.begin().merge(model);
		db.commit();
		
		assertThat(merged.getId(), notNullValue());
	}
	
	@Test
	public void shouldConfigDateOnInsert() {
		ModelImpl merged = db.begin().merge(model);
		db.commit();
		
		assertThat(merged.getCreatedAt(), notNullValue());
	}
	
	@Test
	public void shouldConfigDateOnUpdate() {
		ModelImpl merged = db.begin().merge(model);
		assertThat(merged.getUpdatedAt(), nullValue());
		Calendar createdAt = merged.getCreatedAt();
		assertThat(createdAt, notNullValue());
		db.commit();
		
		merged.name = "Bar";
		merged = db.begin().merge(merged);
		db.commit();
		assertThat(merged.getUpdatedAt(), notNullValue());
		assertThat(merged.getCreatedAt(), equalTo(createdAt));
	}

}