package br.com.flexait.crud.dao;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

import br.com.caelum.vraptor.validator.Validator;

@Named("dao")
public class CrudDao<T> {

	@Inject private EntityManager em;
	@Inject private Validator validator;
	private Class<T> clazz;
	
	public CrudDao() {
	}
	
	public CrudDao(EntityManager em, Validator validator) {
		this.em = em;
		this.validator = validator;
	}
	
	public void setEntityClass(Class<T> clazz) {
		this.clazz = clazz;
	}
	
	public T get(Long id) {
		return em().find(clazz, id);
	}
	
	public T save(T model) {
		validate(model);
		
		return em().merge(model);
	}

	public void validate(T model) {
		validator.validate(model);
		validator.onErrorSendBadRequest();
	}

	public List<T> all() {
		CriteriaQuery<T> criteria = em().getCriteriaBuilder().createQuery(clazz);
		criteria.select(criteria.from(clazz));
		return em().createQuery(criteria).getResultList();
	}

	public void remove(Long id) {
		em().remove(get(id));	
	}

	public EntityManager em() {
		return em;
	}

	public Validator validator() {
		return validator;
	}

}