package br.com.flexait.crud.controller;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Result;
import br.com.flexait.crud.dao.CrudDao;
import br.com.flexait.crud.model.ModelImpl;

@Controller
public class CrudControllerImpl extends CrudController<ModelImpl>{

	public CrudControllerImpl() {
	}
	
	public CrudControllerImpl(Result result, CrudDao<ModelImpl> dao) {
		super(result, dao);
	}

}
