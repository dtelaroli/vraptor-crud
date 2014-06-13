package br.com.flexait.crud.model;

import static org.junit.Assert.fail;

import javax.inject.Inject;

import org.jglue.cdiunit.CdiRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.ioc.Container;

@RunWith(CdiRunner.class)
public class Foo {

	@Inject Container container;
	
	@Before
	public void setUp() throws Exception {
		container.instanceFor(Result.class);
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
