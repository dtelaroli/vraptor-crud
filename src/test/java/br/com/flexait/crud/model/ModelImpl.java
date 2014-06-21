package br.com.flexait.crud.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class ModelImpl extends Model {
	
	private static final long serialVersionUID = 2349840598030756852L;
	
	@NotEmpty
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
