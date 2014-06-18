package br.com.flexait.crud.controller;

import java.util.List;

import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Result;
import br.com.flexait.crud.dao.CrudDao;
import br.com.flexait.crud.model.IModel;
import br.com.flexait.crud.observer.redirect.RedirectToIndex;
import br.com.flexait.crud.observer.redirect.RedirectToShow;

public abstract class CrudController<T extends IModel> extends AbstractController<T> {

	@SuppressWarnings("deprecation")
	public CrudController() {
		super();
	}
	
	public CrudController(Result result, CrudDao<T> dao) {
		super(result, dao);
	}
	
	@Get("/")
	public List<T> index() {
		return dao().all();
	}
	
	@Get("/new")
	public void add() {

	}
	
	@Get("/{id}")
	public T show(Long id) {
		return edit(id);
	}
	
	@Get("/{id}/edit")
	public T edit(Long id) {
		return dao().get(id);
	}
	
	@Post("/")
	@RedirectToShow
	public T create(T model)  {
		return dao().save(model);
	}
	
	@Put("/{id}")
	@RedirectToShow
	public T update(T model) {
		return create(model);
	}
	
	@Delete("/{id}")
	@RedirectToIndex
	public void destroy(Long id) {
		dao().remove(id);
	}
	
}