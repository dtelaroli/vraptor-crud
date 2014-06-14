package br.com.flexait.crud.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
public class ModelImpl extends Model {
	private static final long serialVersionUID = 2349840598030756852L;
	
	@Access(AccessType.FIELD)
	String name;
}
