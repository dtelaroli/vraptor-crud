package br.com.flexait.crud.dao;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.caelum.vraptor.environment.ServletBasedEnvironment;
import br.com.caelum.vraptor.jpa.EntityManagerCreator;
import br.com.caelum.vraptor.jpa.EntityManagerFactoryCreator;
import br.com.flexait.crud.MockValidator;

@RunWith(CdiRunner.class)
@AdditionalClasses({DaoImpl.class, EntityManagerCreator.class, 
	EntityManagerFactoryCreator.class, ServletBasedEnvironment.class})
@ActivatedAlternatives(MockValidator.class)
public class DaoProducerTest {

	@Inject DaoProducer producer;
	@Inject BeanManager bm;
	@Mock InjectionPoint ip;
	@Mock Annotated a;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		when(ip.getType()).thenReturn(DaoImpl.class.getGenericSuperclass());
		when(ip.getAnnotated()).thenReturn(a);
		when(a.isAnnotationPresent(Dao.class)).thenReturn(true);
	}

	@Test
	public void shouldInjectCrudDaoModelImpl() {
		assertThat(producer.produce(ip, bm), instanceOf(CrudDao.class));
	}

}
