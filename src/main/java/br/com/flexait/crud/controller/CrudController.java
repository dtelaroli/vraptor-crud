package br.com.flexait.crud.controller;

import java.util.List;

import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.flexait.crud.model.IModel;

public abstract class CrudController<T extends IModel> extends AbstractController<T> {

	public CrudController() {
		super();
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
	public void create(T model)  {
		model = dao().save(model);
		
		result().redirectTo(this).edit(model.getId());
	}
	
	@Put("/{id}")
	public void update(Long id, T model) {
		create(model);
	}
	
	@Delete("/{id}")
	public void destroy(Long id) {
		dao().remove(id);
		
		result().redirectTo(this).index();
	}
	
}