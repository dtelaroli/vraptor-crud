package br.com.flexait.crud.controller;

import javax.inject.Inject;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Validator;
import br.com.flexait.crud.dao.CrudDao;
import br.com.flexait.crud.dao.Dao;
import br.com.flexait.crud.model.IModel;

public abstract class AbstractController<T extends IModel> {

	@Inject @Dao private CrudDao<T> dao;
	@Inject private Result result;
	@Inject private Validator validator;
	
	/**
	 * @deprecated CDI eyes only
	 */
	public AbstractController() {
	}
	
	public AbstractController(Result result, CrudDao<T> dao, Validator validator) {
		this.result = result;
		this.dao = dao;
		this.validator = validator;
	}
	
	protected CrudDao<T> dao() {
		return dao;
	}
	
	protected Result result() {
		return result;
	}
	
	protected Validator validator() {
		return validator;
	}

	
}