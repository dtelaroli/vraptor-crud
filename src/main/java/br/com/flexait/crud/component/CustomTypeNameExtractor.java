package br.com.flexait.crud.component;

import java.lang.reflect.Type;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Specializes;

import br.com.caelum.vraptor.interceptor.DefaultTypeNameExtractor;

@Specializes @ApplicationScoped
public class CustomTypeNameExtractor extends DefaultTypeNameExtractor {

	@Override
	public String nameFor(Type generic) {
		String nameFor = super.nameFor(generic);
		
		if(nameFor.equals("t")) {
			return "model";
		}
		
		return nameFor;
	}
	
}
