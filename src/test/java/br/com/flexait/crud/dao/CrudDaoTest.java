package br.com.flexait.crud.dao;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.flexait.cdi.integration.Db;
import br.com.flexait.crud.dao.CrudDao;
import br.com.flexait.crud.dao.Dao;
import br.com.flexait.crud.dao.DaoProducer;
import br.com.flexait.crud.model.ModelImpl;

@RunWith(CdiRunner.class)
@AdditionalClasses({DaoProducer.class})
public class CrudDaoTest {

	@Inject Db db;
	@Inject @Dao CrudDao<ModelImpl> dao;
	
	@Before
	public void setUp() throws Exception {
		db.init(ModelImpl.class);
	}

	@After
	public void tearDown() throws Exception {
		db.clean();
	}
	
	@Test
	public void shouldReturnEntityManagerInstance() {
		assertThat(dao.em(), instanceOf(EntityManager.class));
	}
	
	@Test
	public void shouldReturnTransactionInstance() {
		EntityTransaction tx = dao.tx();
		assertThat(tx, instanceOf(EntityTransaction.class));
		assertThat(tx.isActive(), equalTo(false));
	}
	
	@Test
	public void shouldReturnTransactionActive() {
		EntityTransaction tx = dao.beginTransaction();
		assertThat(tx.isActive(), equalTo(true));
		tx.rollback();
	}
	
	@Test
	public void shouldReturnListOfModel() {
		List<ModelImpl> all = dao.all();
		assertThat(all.size(), equalTo(2));
		assertThat(all.get(0).getName(), equalTo("John"));
		assertThat(all.get(1).getName(), equalTo("Petter"));
	}
	
	@Test
	public void shouldGetModelWithId1() {
		ModelImpl model = dao.get(1L);
		assertThat(model.getId(), equalTo(1L));
		assertThat(model.getName(), equalTo("John"));
	}

	@Test
	public void shouldSaveAModel() {
		EntityTransaction tx = dao.beginTransaction();
		
		ModelImpl model = new ModelImpl();
		model.setName("Maria");
		ModelImpl saved = dao.save(model);
		tx.commit();		
		
		assertThat(saved, notNullValue());
		assertThat(saved.getId(), equalTo(3L));
	}
	
	@Test
	public void shouldRemoveModel1() {
		EntityTransaction tx = dao.beginTransaction();
		
		List<ModelImpl> all = dao.all();
		assertThat(all.size(), equalTo(2));
		
		dao.remove(1L);
		tx.commit();
		
		List<ModelImpl> all2 = dao.all();
		assertThat(all2.size(), equalTo(1));
	}
}
