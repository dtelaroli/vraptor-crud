package br.com.caelum.vraptor.crud.controller;

import javax.inject.Inject;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.crud.dao.CrudDao;
import br.com.caelum.vraptor.crud.dao.Dao;
import br.com.caelum.vraptor.crud.model.IModel;

public abstract class AbstractController<T extends IModel> {

	@Inject @Dao private CrudDao<T> dao;
	
	@Inject private Result result;
	
	public AbstractController() {
		
	}
	
	protected CrudDao<T> dao() {
		return dao;
	}
	
	protected Result result() {
		return result;
	}
	
}