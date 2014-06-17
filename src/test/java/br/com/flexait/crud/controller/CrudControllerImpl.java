package br.com.flexait.crud.controller;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Result;
import br.com.flexait.crud.dao.CrudDao;
import br.com.flexait.crud.interceptor.redirect.RedirectToEdit;
import br.com.flexait.crud.interceptor.redirect.RedirectToIndex;
import br.com.flexait.crud.interceptor.redirect.RedirectToShow;
import br.com.flexait.crud.model.ModelImpl;

@Controller
public class CrudControllerImpl extends CrudController<ModelImpl>{

	public CrudControllerImpl() {
	}
	
	public CrudControllerImpl(Result result, CrudDao<ModelImpl> dao) {
		super(result, dao);
	}

	@Override
	public ModelImpl show(Long id) {
		return super.show(id);
	}
	
	@Override
	@RedirectToIndex
	public ModelImpl edit(Long id) {
		return super.edit(id);
	}
	
	@RedirectToShow
	public void toShow() {
	}
	
	@RedirectToEdit
	public void toEdit() {
	}
}