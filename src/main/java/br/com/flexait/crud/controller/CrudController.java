package br.com.flexait.crud.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Validator;
import br.com.flexait.crud.dao.CrudDao;
import br.com.flexait.crud.model.IModel;
import br.com.flexait.crud.observer.redirect.RedirectToIndex;
import br.com.flexait.crud.observer.redirect.RedirectToShow;

public abstract class CrudController<T extends IModel> extends AbstractController<T> {

	@SuppressWarnings("deprecation")
	public CrudController() {
		super();
	}
	
	public CrudController(Result result, CrudDao<T> dao, Validator validator) {
		super(result, dao, validator);
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
	public T create(@NotNull @Valid T model)  {
		validator().onErrorRedirectTo(this).add();
		return save(model);
	}

	private T save(T model) {
		return dao().save(model);
	}
	
	@Put("/{id}")
	@RedirectToShow
	public T update(@NotNull @Valid T model) {
		validator().onErrorRedirectTo(this).edit(model.getId());
		return save(model);
	}
	
	@Delete("/{id}")
	@RedirectToIndex
	public void destroy(Long id) {
		dao().remove(id);
	}
	
}