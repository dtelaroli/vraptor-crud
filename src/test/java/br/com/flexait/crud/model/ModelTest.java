package br.com.flexait.crud.model;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;

import org.jglue.cdiunit.CdiRunner;
import org.jglue.cdiunit.InRequestScope;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.flexait.cdi.integration.Db;

@RunWith(CdiRunner.class)
public class ModelTest {
	
	@Inject	Db db;
	
	@Before
	public void setUp() throws Exception {
		
	}

	@Test @InRequestScope
	public void shouldSaveAModelImplementation() throws Exception {
		db.init(ModelImpl.class);
		
		ModelImpl model = new ModelImpl();
		model.name = "Foo";
		
		ModelImpl merged = db.em().merge(model);
		
		assertThat(merged.getId(), notNullValue());
	}

}