package br.com.flexait.crud.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionPoint;

import br.com.flexait.crud.model.IModel;

public class DaoProducer implements Serializable {
	
	private static final long serialVersionUID = -1209520122539570918L;

	@Produces @Dao @Dependent
	public <T extends IModel> CrudDao<T> produce(InjectionPoint ip, BeanManager bm) {
        if (ip.getAnnotated().isAnnotationPresent(Dao.class)) {
        	return getDao(ip, bm);
        }
        throw new IllegalArgumentException("Annotation @Dao is required when injecting AbstractDao");
    }

	@SuppressWarnings("unchecked")
	private <T extends IModel> CrudDao<T> getDao(InjectionPoint ip,
			BeanManager bm) {
		CrudDao<T> dao = (CrudDao<T>) getBeanByName("dao", bm);
		Class<T> entityClass = getType(ip);
		dao.setEntityClass(entityClass);
		return dao;
	}

	@SuppressWarnings("unchecked")
	private <T extends IModel> Class<T> getType(InjectionPoint ip) {
		ParameterizedType type = (ParameterizedType) ip.getType();
		Type[] typeArgs = type.getActualTypeArguments();
		Class<T> entityClass = (Class<T>) typeArgs[0];
		return entityClass;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends IModel> Object getBeanByName(String name, BeanManager bm) { 
        Bean<T> bean = (Bean<T>) bm.getBeans(name).iterator().next();
        CreationalContext<T> ctx = bm.createCreationalContext(bean); 
        return bm.getReference(bean, bean.getBeanClass(), ctx); 
    }

}
