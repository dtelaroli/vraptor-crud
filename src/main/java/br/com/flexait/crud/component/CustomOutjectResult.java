package br.com.flexait.crud.component;

import static org.slf4j.LoggerFactory.getLogger;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Specializes;
import javax.inject.Inject;

import org.slf4j.Logger;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.core.MethodInfo;
import br.com.caelum.vraptor.events.MethodExecuted;
import br.com.caelum.vraptor.interceptor.TypeNameExtractor;
import br.com.caelum.vraptor.observer.OutjectResult;

@ApplicationScoped @Specializes
public class CustomOutjectResult extends OutjectResult {

	private static final Logger logger = getLogger(OutjectResult.class);
	private TypeNameExtractor extractor;

	@SuppressWarnings("deprecation")
	public CustomOutjectResult() {
	}
	
	@Inject
	public CustomOutjectResult(TypeNameExtractor extractor) {
		super(extractor);
		this.extractor = extractor;
	}
	
	@Override
	public void outject(@Observes MethodExecuted event, Result result,
			MethodInfo methodInfo) {
		if (!event.getMethodReturnType().equals(Void.TYPE)) {
			Object value = methodInfo.getResult();
			String name = getName(event);
			logger.debug("outjecting {}={}", name, value);
			result.include(name, value);
		}
	}

	private String getName(MethodExecuted event) {
		Type type = event.getMethodReturnType();
		String sufix = getSufix(type);
		if(isGeneric(type)) {
			type = getClass(event);
		}
		return extractor.nameFor(type) + sufix;
	}

	private String getSufix(Type type) {
		String sufix = "";
		if(type.getTypeName().equals("java.util.List<T>")) {
			sufix = "List";
		}
		return sufix;
	}

	private boolean isGeneric(Type type) {
		return type instanceof ParameterizedType || type instanceof TypeVariable;
	}

	private Type getClass(MethodExecuted event) {
		ParameterizedType genericSuperclass = (ParameterizedType) event.getControllerMethod().getController().getType().getGenericSuperclass();
		return genericSuperclass.getActualTypeArguments()[0];
	}

}