package br.com.flexait.crud.controller;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Validator;
import br.com.flexait.crud.dao.CrudDao;
import br.com.flexait.crud.model.ModelImpl;
import br.com.flexait.crud.observer.redirect.RedirectToEdit;
import br.com.flexait.crud.observer.redirect.RedirectToIndex;
import br.com.flexait.crud.observer.redirect.RedirectToShow;

@Controller
public class CrudControllerImpl extends CrudController<ModelImpl>{

	/**
	 * @deprecated CDI eyes only
	 */
	public CrudControllerImpl() {
	}
	
	public CrudControllerImpl(Result result, CrudDao<ModelImpl> dao, Validator validator) {
		super(result, dao, validator);
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